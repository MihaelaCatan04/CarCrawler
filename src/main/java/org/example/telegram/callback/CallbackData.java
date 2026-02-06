package org.example.telegram.callback;

import java.util.Arrays;
import java.util.List;

public class CallbackData {
    private final CallbackAction action;
    private final List<String> args;

    public CallbackData(String raw) {
        List<String> parts = Arrays.asList(raw.split("\\|"));
        this.action = CallbackAction.valueOf(parts.getFirst());
        this.args = parts.subList(1, parts.size());
    }

    public CallbackAction getAction() {
        return action;
    }

    public List<String> getArgs() {
        return args;
    }
}
