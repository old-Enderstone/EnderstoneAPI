package net.enderstone.api.commands.command;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Daemon thread, reading System.in and then running commands using the provided CommandManager instance
 */
public class CommandDispatcher extends Thread {

    private CommandManager commandManager = null;

    public CommandDispatcher() {
        super("CommandDispatcher");
        this.setDaemon(true);
        this.setPriority(Thread.MIN_PRIORITY);
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                String cmd = reader.readLine();
                if (commandManager == null) continue;

                commandManager.dispatch(cmd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
