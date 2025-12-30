package com.voiceassistant.infrastructure;

import com.voiceassistant.application.AssistantController;
import com.voiceassistant.application.CommandService;
import com.voiceassistant.audio.SoundPlayer;
import com.voiceassistant.domain.AssistantState;
import com.voiceassistant.domain.VoiceEvent;
import org.vosk.Model;
import org.vosk.Recognizer;


import javax.sound.sampled.*;
import java.util.concurrent.BlockingQueue;

public class VoiceRecognizer {

    private final BlockingQueue<VoiceEvent> queue;

    public VoiceRecognizer(BlockingQueue<VoiceEvent> queue) {
        this.queue = queue;
    }

    public void wakeup() {
        try {
            Model model = new Model("vosk-model-small-pt-0.3");
            AudioFormat format = new AudioFormat(16000, 16, 1, true, false);

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);

            microphone.open(format);
            microphone.start();

            Recognizer recognizer = new Recognizer(model, 16000);
            byte[] buffer = new byte[4096];

            while (true) {
                int bytesRead = microphone.read(buffer, 0, buffer.length);

                if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                    queue.put(new VoiceEvent(recognizer.getResult()));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
