package com.voiceassistant.application;

import com.voiceassistant.audio.SoundPlayer;
import com.voiceassistant.infrastructure.CommandExecutor;
import com.voiceassistant.domain.Config;


public class CommandService {

    private Config config;

    // flag
    private boolean commandMode = false;
    public short trysNum = 0;

    public CommandService(Config config) {
        this.config = config;
    }

    public void activeCommandMode() {
        commandMode = true;
        SoundPlayer.play("src/main/resources/sounds/activate.wav");
    }

    public void processText(String text) {
        text = text.toLowerCase();
        if (text.contains(config.wakeWord.toLowerCase()) && !commandMode) {
            activeCommandMode();
            return;
        }

        if (commandMode && config.commands != null) {
            for (String keyword : config.commands.keySet()) {
                if (text.contains(keyword.toLowerCase())) {
                    CommandExecutor.exec(config.commands.get(keyword));
                    commandMode = false;
                    trysNum = 0;
                    SoundPlayer.play("src/main/resources/sounds/disable.wav");

                    return;
                }
            }

            // nenhum comando encontrado
            SoundPlayer.play("src/main/resources/sounds/error.wav");
            trysNum++;

            if (trysNum >= 3) {
                SoundPlayer.play("src/main/resources/sounds/disable.wav");
                trysNum = 0;
                commandMode = false;
            }
        }
    }
}
