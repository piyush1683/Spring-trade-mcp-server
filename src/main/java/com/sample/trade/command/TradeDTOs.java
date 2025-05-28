package com.sample.trade.command;

import java.util.UUID;

public class TradeDTOs {

        public record CreateTradeCommand(
                        UUID id,
                        String tradeId,
                        String party1,
                        String party2,
                        String amount,
                        String tradetype) {
        }

        public record TradeBody(
                        UUID id,
                        String tradeId,
                        String party1,
                        String party2,
                        String amount,
                        String tradetype) {
        }

        public record TradeCreatedEvent(
                        UUID id,
                        String tradeId,
                        String party1,
                        String party2,
                        String amount,
                        String tradetype) {
        }

        public record TradeState(
                        UUID id,
                        String tradeId,
                        String party1,
                        String party2,
                        String amount,
                        String tradetype) {
        }

}
