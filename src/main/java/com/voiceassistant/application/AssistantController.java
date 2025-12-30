package com.voiceassistant.application;

import com.voiceassistant.domain.AssistantState;

public class AssistantController {

    private AssistantState state = AssistantState.IDLE;

    public void setState(AssistantState newState) {
        this.state = newState;
    }

    public AssistantState getState() {
        return this.state;
    }
}
