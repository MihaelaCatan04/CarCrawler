package org.example.bot;

public class CallbackData {
    public String action;
    public String[] args;

    public static CallbackData parse(String raw) {
        String[] parts = raw.split("\\|");
        CallbackData data = new CallbackData();
        data.action = parts[0];
        data.args = new String[parts.length - 1];
        System.arraycopy(parts, 1, data.args, 0, parts.length - 1);
        return data;
    }
}