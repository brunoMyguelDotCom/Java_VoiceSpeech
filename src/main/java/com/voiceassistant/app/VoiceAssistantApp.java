package com.voiceassistant.app;

import com.voiceassistant.application.AssistantController;
import com.voiceassistant.application.CommandService;
import com.voiceassistant.application.EventLoop;
import com.voiceassistant.audio.SoundPlayer;
import com.voiceassistant.domain.AssistantState;
import com.voiceassistant.domain.ConfigRepository;
import com.voiceassistant.domain.VoiceEvent;
import com.voiceassistant.domain.Config;
import com.voiceassistant.infrastructure.VoiceRecognizer;
import com.voiceassistant.ui.ConfigWindow;
import com.voiceassistant.ui.TrayIconManager;

import javax.swing.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class VoiceAssistantApp {
    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeLater(() -> {
            ConfigRepository repo = new ConfigRepository();
            ConfigWindow window = new ConfigWindow(repo);
            window.setVisible(false); // começa escondido

            TrayIconManager tray = new TrayIconManager(window);
            tray.init();
        });

        BlockingDeque<VoiceEvent> queue = new LinkedBlockingDeque<>();

        AssistantController controller = new AssistantController();
        controller.setState(AssistantState.LISTENING);

        ConfigRepository commandsReader = new ConfigRepository();
        Config config = commandsReader.loadConfig();

        CommandService commandService = new CommandService(config);

        VoiceRecognizer recognizer = new VoiceRecognizer(queue);
        EventLoop eventLoop = new EventLoop(queue, commandService, controller);

        SoundPlayer.play("src/main/resources/sounds/startup.wav");

        new Thread(recognizer::wakeup).start();
        new Thread(eventLoop).start();
    }
}