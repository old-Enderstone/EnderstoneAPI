package net.enderstone.api.logging;

import java.util.logging.LogRecord;

public class LogMessage {

    private final Thread thread;
    private final LogRecord record;

    public LogMessage(final Thread thread, final LogRecord record) {
        this.thread = thread;
        this.record = record;
    }

    /**
     * @return thread that created the log message
     */
    public Thread getThread() {
        return thread;
    }

    public LogRecord getRecord() {
        return record;
    }
}