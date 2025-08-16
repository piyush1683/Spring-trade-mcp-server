package com.sample.trade.ai;

import com.sample.trade.command.TradeDTOs.TradeState;
import com.sample.trade.repository.TradeRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class TradeAiService {

    @Autowired
    private TradeRepositoryImpl tradeRepository;

    public Map<String, Object> analyzeTrades() {
        List<TradeState> trades = tradeRepository.findallTrades();

        if (trades.isEmpty()) {
            Map<String, Object> result = new HashMap<>();
            result.put("analysis", "No trades found for analysis");
            return result;
        }

        // Calculate basic statistics
        double totalAmount = 0;
        Map<String, Integer> tradeTypeCount = new HashMap<>();

        for (TradeState trade : trades) {
            try {
                totalAmount += Double.parseDouble(trade.amount());
            } catch (NumberFormatException e) {
                // Skip invalid amounts
            }

            tradeTypeCount.merge(trade.tradetype(), 1, Integer::sum);
        }

        // Generate mock AI analysis
        StringBuilder analysis = new StringBuilder();
        analysis.append("AI Analysis Results:\n\n");
        analysis.append("Total Trades: ").append(trades.size()).append("\n");
        analysis.append("Total Amount: $").append(String.format("%.2f", totalAmount)).append("\n");
        analysis.append("Average Amount: $").append(String.format("%.2f", totalAmount / trades.size())).append("\n\n");

        analysis.append("Trade Type Distribution:\n");
        tradeTypeCount.forEach(
                (type, count) -> analysis.append("- ").append(type).append(": ").append(count).append(" trades\n"));

        analysis.append("\nKey Insights:\n");
        analysis.append("1. Trading volume shows ").append(trades.size() > 5 ? "strong" : "moderate")
                .append(" activity\n");
        analysis.append("2. Average trade value indicates ")
                .append(totalAmount / trades.size() > 10000 ? "high" : "moderate").append(" value transactions\n");
        analysis.append("3. Risk assessment: ").append(trades.size() > 10 ? "Low" : "Moderate")
                .append(" risk profile\n");
        analysis.append("4. Market analysis: Diverse trade types suggest healthy market activity");

        Map<String, Object> result = new HashMap<>();
        result.put("analysis", analysis.toString());
        result.put("totalTrades", trades.size());
        result.put("totalAmount", totalAmount);
        result.put("averageAmount", trades.size() > 0 ? totalAmount / trades.size() : 0);

        return result;
    }

    public Map<String, Object> getTradeRecommendations(String tradeType) {
        List<TradeState> trades = tradeRepository.findallTrades();

        // Filter trades by type if specified
        List<TradeState> filteredTrades = trades.stream()
                .filter(trade -> tradeType == null || tradeType.isEmpty() ||
                        trade.tradetype().equalsIgnoreCase(tradeType))
                .toList();

        StringBuilder recommendations = new StringBuilder();
        recommendations.append("AI Recommendations for ").append(tradeType != null ? tradeType : "All")
                .append(" Trades:\n\n");

        recommendations.append("Analyzed ").append(filteredTrades.size()).append(" relevant trades.\n\n");

        recommendations.append("Recommendations:\n");
        recommendations.append("1. Optimal Strategy: Consider diversifying trade types for better risk management\n");
        recommendations.append("2. Risk Management: Implement automated monitoring for large transactions\n");
        recommendations.append("3. Market Opportunities: ").append(filteredTrades.size() > 5 ? "Strong" : "Moderate")
                .append(" market activity detected\n");
        recommendations.append("4. Best Practices: Maintain detailed audit trails and regular compliance checks");

        Map<String, Object> result = new HashMap<>();
        result.put("recommendations", recommendations.toString());
        result.put("tradeType", tradeType);
        result.put("analyzedTrades", filteredTrades.size());

        return result;
    }

    public Map<String, Object> predictTradeTrends() {
        List<TradeState> trades = tradeRepository.findallTrades();

        if (trades.size() < 3) {
            Map<String, Object> result = new HashMap<>();
            result.put("prediction", "Insufficient data for trend prediction. Need at least 3 trades.");
            return result;
        }

        // Calculate basic trends
        double totalAmount = trades.stream()
                .mapToDouble(trade -> {
                    try {
                        return Double.parseDouble(trade.amount());
                    } catch (NumberFormatException e) {
                        return 0.0;
                    }
                })
                .sum();

        StringBuilder prediction = new StringBuilder();
        prediction.append("AI Trend Prediction:\n\n");
        prediction.append("Based on ").append(trades.size()).append(" historical trades:\n\n");

        prediction.append("1. Volume Trends: ").append(trades.size() > 10 ? "Expected increase" : "Stable")
                .append(" in trading volume\n");
        prediction.append("2. Type Distribution: Continued diversity in trade types expected\n");
        prediction.append("3. Market Movements: ").append(totalAmount > 100000 ? "Bullish" : "Neutral")
                .append(" market sentiment\n");
        prediction.append("4. Risk Factors: Monitor for ").append(trades.size() > 20 ? "systemic" : "individual")
                .append(" risk patterns");

        Map<String, Object> result = new HashMap<>();
        result.put("prediction", prediction.toString());
        result.put("dataPoints", trades.size());
        result.put("confidence", "Based on " + trades.size() + " historical trades");

        return result;
    }
}
