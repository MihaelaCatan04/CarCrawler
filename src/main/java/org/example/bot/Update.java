package org.example.bot;

import java.io.IOException;

public enum Update {
    BRAND {
        @Override
        public void flowRun(CarFlowHandler flow, long chatId, int messageId, CallbackData cb) {
            try {
                flow.onBrand(chatId, messageId, Integer.parseInt(cb.getArgs().getFirst()));
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }, MODEL {
        @Override
        public void flowRun(CarFlowHandler flow, long chatId, int messageId, CallbackData cb) {
            flow.onModel(chatId, messageId, Integer.parseInt(cb.getArgs().getFirst()));
        }
    }, GEN {
        @Override
        public void flowRun(CarFlowHandler flow, long chatId, int messageId, CallbackData cb) {
            flow.onGeneration(chatId, messageId, Integer.parseInt(cb.getArgs().getFirst()));
        }
    };

    public abstract void flowRun(CarFlowHandler flow, long chatId, int messageId, CallbackData cb);
}
