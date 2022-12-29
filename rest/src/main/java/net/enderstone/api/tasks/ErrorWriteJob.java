package net.enderstone.api.tasks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class ErrorWriteJob implements Runnable {

    public static final File ERROR_DIR = new File("./errors/");

    private final String label;
    private final Throwable error;
    private final String extra;

    public ErrorWriteJob(final String label, final Throwable error) {
        this.label = label;
        this.error = error;
        this.extra = null;
    }

    public ErrorWriteJob(final String label, final Throwable error, final String extra) {
        this.label = label;
        this.error = error;
        this.extra = extra;
    }

    @Override
    public void run() {
        if(!ERROR_DIR.exists()) ERROR_DIR.mkdir();
        Objects.requireNonNull(label, "Label may not be null");
        Objects.requireNonNull(error, "Error may not be null");

        try {
            final File outFile = new File(ERROR_DIR + "/" + label);
            final PrintWriter writer = new PrintWriter(new FileOutputStream(outFile));

            if(extra != null && !extra.isEmpty()) {
                writer.println(extra);
            }

            error.printStackTrace(writer);
            writer.flush();
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
