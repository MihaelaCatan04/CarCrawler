package org.example.bot;

import java.util.HashMap;
import java.util.Map;

public class SessionService {
    private final Map<Long, UserSession> sessions = new HashMap<>();

    public UserSession get(long chatId) {
        return sessions.computeIfAbsent(chatId, _ -> new UserSession());
    }
}
