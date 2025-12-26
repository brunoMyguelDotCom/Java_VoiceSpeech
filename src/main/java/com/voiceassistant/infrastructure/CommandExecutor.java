package com.voiceassistant.infrastructure;

import java.io.IOException;

public class CommandExecutor {

    public static void exec(String command) {

        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
