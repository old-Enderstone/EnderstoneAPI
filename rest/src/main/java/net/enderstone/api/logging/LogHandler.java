package net.enderstone.api.logging;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LogHandler extends Handler {

    private final ThreadPoolExecutor executor;
    private final HasStyle owner;

    private volatile Runnable printTask = null;
    private final Queue<LogMessage> logQueue = new ConcurrentLinkedQueue<>();

    public LogHandler(final ThreadPoolExecutor executor, final HasStyle styleProvider) {
        this.executor = executor;
        this.owner = styleProvider;
    }

    private void print() {
        while(!logQueue.isEmpty()) {
            final LogMessage message = logQueue.poll();
            final String formatted = owner.getStyle().format(message);
            System.out.println(formatted);
        }

        printTask = null;
    }

    @Override
    public void publish(final LogRecord record) {
        logQueue.offer(new LogMessage(Thread.currentThread(), record));

        if(printTask == null) {
            final Runnable task = this::print;
            printTask = task;
            executor.execute(task);
        }
    }

    @Override
    public void flush() {
        print();
        System.out.flush();
    }

    @Override
    public void close() throws SecurityException {

    }
}
