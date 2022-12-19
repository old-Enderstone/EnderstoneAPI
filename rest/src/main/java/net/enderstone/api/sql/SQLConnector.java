package net.enderstone.api.sql;

import net.enderstone.api.RestAPI;

import java.sql.*;
import java.util.Properties;

public class SQLConnector {

    private String username;
    private String password;
    private String hostAddress;
    private int hostPort = 3306;
    private String database;

    private String driverClass = "com.mysql.cj.jdbc.Driver";

    private Connection con;

    /**
     * Set the username and password used for logging into the mysql server when calling JSQL.connect();
     * @param username username of your mysql user
     * @param password password of your mysql user
     * @return the current object instance
     */
    public SQLConnector setUser(String username, String password) {
        this.username = username;
        this.password = password;
        return this;
    }

    /**
     * Set the host address and port of the mysql server
     * @param hostAddress the ipv4 address of your mysql server
     * @param hostPort the port of your mysql server
     * @return the current object instance
     */
    public SQLConnector setHostAddress(String hostAddress, int hostPort) {
        this.hostAddress = hostAddress;
        this.hostPort = hostPort;
        return this;
    }

    /**
     * Set the host address of the mysql server, default port if not set is 3306
     * @param hostAddress the ipv4 address of your mysql server
     * @return the current object instance
     */
    public SQLConnector setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
        return this;
    }

    /**
     * Set the host port of the mysql server, default is 3306
     * @param hostPort the port of your mysql server
     * @return the current object instance
     */
    public SQLConnector setHostPort(int hostPort) {
        this.hostPort = hostPort;
        return this;
    }

    /**
     * Select the active database, will connect to this database when set bore calling JSQL.connect();
     * and will execute 'use `$database`;' when isConnected() equals true
     * @param database the name of the database
     * @return the current object instance
     */
    public SQLConnector setDatabase(String database) {
        this.database = database;
        return this;
    }

    /**
     * Set the jdbc driver class, default is "com.mysql.cj.jdbc.Driver"
     * @param driverClass the driver class
     * @return the current object instance
     */
    public SQLConnector setDriverClass(String driverClass) {
        this.driverClass = driverClass;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public int getHostPort() {
        return hostPort;
    }

    public String getDatabase() {
        return database;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void updateBatch(String... command) {
        try {
            Statement st = this.con.createStatement();
            for (String s : command) {
                st.addBatch(s);
            }
            st.executeBatch();
            st.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String command) {
        try {
            Statement st = this.con.createStatement();
            st.execute(command);
            st.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String command, Object... objects) {
        try {
            PreparedStatement st = this.con.prepareStatement(command);
            for (int i = 0; i < objects.length; i++) {
                st.setObject(i+1, objects[i]);
            }
            st.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet query(String command) {
        try {
            PreparedStatement st = this.con.prepareStatement(command);
            return st.executeQuery();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet query(String command, Object... objects) {
        try {
            PreparedStatement st = this.con.prepareStatement(command);
            for (int i = 0; i < objects.length; i++) {
                st.setObject(i+1, objects[i]);
            }

            return st.executeQuery();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Close the connection if isConnected() equals true
     */
    public void disconnect() {
        if(!isConnected()) return;

        try {
            this.con.close();
            RestAPI.logger.info("SQL Connector disconnected.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return true if connection object not equals null and connection isn't closed
     */
    public boolean isConnected() {
        try {
            return this.con != null && !this.con.isClosed();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Connect to the mysql server, will connect to the datbase if database has been set using {@link }
     * @return true if connected successfully, false if not
     */
    public boolean connect() {
        if(this.username == null || this.password == null || this.hostAddress == null) {
            RestAPI.logger.severe("Couldn't connect to mysql server, username/password or hostAddress not set!");
        }

        Properties connectionProps = new Properties();
        connectionProps.put("user", this.username);
        connectionProps.put("password", this.password);
        try {
            Class.forName(this.driverClass).newInstance();
            con = DriverManager.getConnection(
                    "jdbc:" + "mysql" + "://" +
                            this.hostAddress +
                            ":" + this.hostPort +
                            (this.database != null ? "/" + this.database: "") +
                            "?autoReconnect=true",
                    connectionProps);

            if(isConnected()) RestAPI.logger.info("Connected to sql server.");
            if(!isConnected()) RestAPI.logger.severe("Couldn't connect to sql server.");
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return isConnected();
    }


}
