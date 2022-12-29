package net.enderstone.api.logging;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;

public class Logger extends java.util.logging.Logger implements HasStyle {

    private LogStyle style = new BasicStyle();

    public Logger(final ThreadPoolExecutor executor) {
        super(null, null);

        setUseParentHandlers(false);
        setLevel(Level.INFO);
        addHandler(new LogHandler(executor, this));
    }

    /**
     * Set the message formatter used to format log messages
     * @see #getStyle()
     */
    @SuppressWarnings("unused")
    public void setStyle(final LogStyle style) {
        this.style = style;
    }

    /**
     * Get the message formatter used to format log messages
     * @see #setStyle(LogStyle)
     */
    @Override
    public LogStyle getStyle() {
        return style;
    }
}