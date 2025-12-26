package com.voiceassistant.app;

import com.voiceassistant.application.CommandService;
import com.voiceassistant.infrastructure.Config;
import com.voiceassistant.infrastructure.JsonCommandsReader;
import com.voiceassistant.infrastructure.VoiceRecognizer;

public class VoiceAssistantApp {
    public static void main(String[] args) throws Exception {

        JsonCommandsReader commandsReader = new JsonCommandsReader();
        Config config = commandsReader.loadConfig();

        CommandService commandService = new CommandService(config);

        VoiceRecognizer voiceRecognizer = new VoiceRecognizer(commandService);

        try {
            voiceRecognizer.wakeup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}