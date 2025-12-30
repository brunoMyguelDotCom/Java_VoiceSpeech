package com.voiceassistant.infrastructure;

import java.io.IOException;

public class CommandExecutor {

    public static void exec(String command) {

        if (command == null || command.trim().isEmpty()) return;

        try {
            String cmd = command.trim();

            if (cmd.toLowerCase().endsWith(".exe")) {
                Runtime.getRuntime().exec(cmd);

            } else {
                Runtime.getRuntime().exec("explorer.exe \"" + cmd + "\"");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
