package com.voiceassistant.application;

import com.voiceassistant.domain.AssistantState;
import com.voiceassistant.domain.VoiceEvent;
import com.voiceassistant.infrastructure.CommandExecutor;
import com.voiceassistant.infrastructure.VoiceRecognizer;

import java.util.concurrent.BlockingQueue;

public class EventLoop implements Runnable {

    private final BlockingQueue<VoiceEvent> queue;
    private final CommandService commandService;
    private final AssistantController assistantController;

    public EventLoop(BlockingQueue<VoiceEvent> queue, CommandService commandService, AssistantController assistantController) {
        this.queue = queue;
        this.commandService = commandService;
        this.assistantController = assistantController;
    }

    @Override
    public void run() {
        while (true) {
            try {
                VoiceEvent event = queue.take();
                assistantController.setState(AssistantState.PROCESSING);

                commandService.processText(event.text());

                assistantController.setState(AssistantState.LISTENING);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
