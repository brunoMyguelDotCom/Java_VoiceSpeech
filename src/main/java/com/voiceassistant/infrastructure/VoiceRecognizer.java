package com.voiceassistant.infrastructure;

import com.voiceassistant.application.CommandService;
import com.voiceassistant.audio.SoundPlayer;
import org.vosk.Model;
import org.vosk.Recognizer;


import javax.sound.sampled.*;

public class VoiceRecognizer {

    private CommandService commandService;

    public VoiceRecognizer(CommandService commandService) {
        this.commandService = commandService;
    }

    public void wakeup() throws Exception {

        Model model = new Model("vosk-model-small-pt-0.3");

        AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);
        microphone.open(format);
        microphone.start();

        Recognizer recognizer = new Recognizer(model, 16000);

        byte[] buffer = new byte[4096];

        // som de inicializacao do software
        SoundPlayer.play("src/main/resources/sounds/startup.wav");
        while (true) {
            int bytesRead = microphone.read(buffer, 0, buffer.length);
            if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                String jsonResult = recognizer.getResult();
                commandService.processText(jsonResult);
            }
        }
    }
}
