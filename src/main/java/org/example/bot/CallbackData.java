package org.example.bot;


import java.util.Arrays;
import java.util.List;

public class CallbackData {
    private final Update action;
    private final List<String> args;

    public CallbackData(String raw) {
        List<String> parts = Arrays.asList(raw.split("\\|"));
        this.action = Update.valueOf(parts.getFirst());
        this.args = parts.subList(1, parts.size());
    }

    public Update getAction() {
        return action;
    }

    public List<String> getArgs() {
        return args;
    }
}