package com.sample.trade.mcp;

import com.sample.trade.command.TradeDTOs.TradeBody;
import com.sample.trade.command.TradeDTOs.TradeState;
import com.sample.trade.repository.TradeRepositoryImpl;
import com.sample.trade.service.TradeAggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TradeMcpService {

    @Autowired
    private TradeRepositoryImpl tradeRepository;

    @Autowired
    private TradeAggregate tradeAggregate;

    public Map<String, Object> getAllTrades() {
        List<TradeState> trades = tradeRepository.findallTrades();
        Map<String, Object> result = new HashMap<>();
        result.put("trades", trades);
        result.put("count", trades.size());
        return result;
    }

    public Map<String, Object> getTradeById(String tradeId) {
        TradeState trade = tradeRepository.findTradeById(tradeId);
        Map<String, Object> result = new HashMap<>();
        if (trade != null) {
            result.put("trade", trade);
            result.put("found", true);
        } else {
            result.put("found", false);
            result.put("message", "Trade not found with ID: " + tradeId);
        }
        return result;
    }

    public Map<String, Object> createTrade(Map<String, Object> tradeData) {
        try {
            String tradeId = (String) tradeData.get("tradeId");
            String party1 = (String) tradeData.get("party1");
            String party2 = (String) tradeData.get("party2");
            String amount = (String) tradeData.get("amount");
            String tradeType = (String) tradeData.get("tradetype");

            UUID id = UUID.randomUUID();
            TradeBody tradeBody = new TradeBody(id, tradeId, party1, party2, amount, tradeType);

            tradeAggregate.handle(new com.sample.trade.command.TradeDTOs.CreateTradeCommand(
                    id, tradeId, party1, party2, amount, tradeType));

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("tradeId", id.toString());
            result.put("message", "Trade created successfully");
            return result;
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("error", e.getMessage());
            return result;
        }
    }

    public Map<String, Object> getTradeStatistics() {
        List<TradeState> trades = tradeRepository.findallTrades();
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalTrades", trades.size());

        // Calculate total amount
        double totalAmount = trades.stream()
                .mapToDouble(trade -> {
                    try {
                        return Double.parseDouble(trade.amount());
                    } catch (NumberFormatException e) {
                        return 0.0;
                    }
                })
                .sum();

        stats.put("totalAmount", totalAmount);
        stats.put("averageAmount", trades.isEmpty() ? 0.0 : totalAmount / trades.size());

        return stats;
    }
}
