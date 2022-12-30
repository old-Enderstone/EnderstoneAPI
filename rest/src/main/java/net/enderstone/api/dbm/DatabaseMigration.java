package net.enderstone.api.dbm;

import net.enderstone.api.RestAPI;
import net.enderstone.api.sql.SQLConnector;
import net.enderstone.api.tasks.ErrorWriteJob;
import net.enderstone.api.utils.Reflection;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

public class DatabaseMigration {

    public static final String SQL_DELIMITER = "\\s*;\\s*(?=([^']*'[^']*')*[^']*$)";

    public static void setCurrentVersion(final int version) {
        RestAPI.logger.info(String.format("Updated database to version '%d'", version));
        RestAPI.connector.update("replace into `dbm` values ('version', '" + version + "');");
    }

    public static int getCurrentVersion() {
        final SQLConnector connector = RestAPI.connector;
        final boolean tableExists = connector.tableExists("dbm");
        if(!tableExists) return 0;

        try {
            final ResultSet rs = connector.query("select `value` from `dbm` where `key`='version';");
            if(!rs.next()) return 0;
            return rs.getInt("value");
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String[] loadVersions() {
        try {
            return Reflection.getResourceListing(DatabaseMigration.class, "dbm");
        } catch(IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static String[] loadPatch(final int version) {
        final InputStream in = DatabaseMigration.class.getResourceAsStream("/dbm/" + version + ".sql");

        try {
            if(in == null) {
                throw new RuntimeException("Couldn't load file 'dbm/" + version + ".sql'");
            }

            final byte[] data = in.readAllBytes();
            in.close();

            return new String(data, StandardCharsets.UTF_8).split(SQL_DELIMITER);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean applyPatch(final int version) {
        RestAPI.logger.fine(String.format("Applying Database patch version '%d'", version));

        final String[] statements = loadPatch(version);
        final SQLConnector connector = RestAPI.connector;

        try {
            connector.updateBatch(statements);
        } catch(RuntimeException e) {
            final Date date = new Date();
            RestAPI.logger.severe("An error occurred whilst applying a database patch.");
            RestAPI.logger.severe("The error log can be found under '" + new File(ErrorWriteJob.ERROR_DIR + "/" + date.toString()) + "'");
            RestAPI.executor.execute(new ErrorWriteJob(date.toString(), e, "Error occurred whilst applying database patch version '" + version + "'"));
            return false;
        }

        return true;
    }

    /**
     * @return database version after migration, if no patches where applied, returns the current version
     */
    public static int updateDatabase(final int currentVersion) {
        final Integer[] versions = Arrays.stream(loadVersions())
                                         .filter(str -> str.matches("\\d+\\.sql")) // filter all files that are not .sql files
                                         .map(str -> str.substring(0, str.length()-4)) // remove the .sql from each file name
                                         .map(Integer::parseInt) // turn all file names into integers
                                         .filter(i -> i > currentVersion) // filter all version that are older or equals to the current version
                                         .sorted()
                                         .toArray(Integer[]::new);

        for(int i = 0; i < versions.length; i++) {
            final boolean status = applyPatch(versions[i]);
            if(!status) return i - 1 >= 0 ? versions[i - 1]: currentVersion;
        }

        return versions.length == 0 ? currentVersion: versions[versions.length-1];
    }

}
