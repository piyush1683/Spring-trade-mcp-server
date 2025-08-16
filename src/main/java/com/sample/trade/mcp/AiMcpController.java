package com.sample.trade.mcp;

import com.sample.trade.ai.TradeAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class AiMcpController {

    @Autowired
    private TradeAiService tradeAiService;

    @MessageMapping("/ai/analyze")
    @SendTo("/topic/ai")
    public McpMessage analyzeTrades(McpMessage message) {
        try {
            Map<String, Object> result = tradeAiService.analyzeTrades();
            McpMessage response = new McpMessage();
            response.setId(message.getId());
            response.setResult(result);
            return response;
        } catch (Exception e) {
            return createErrorResponse(message.getId(), -1, "Failed to analyze trades: " + e.getMessage());
        }
    }

    @MessageMapping("/ai/recommendations")
    @SendTo("/topic/ai")
    public McpMessage getRecommendations(McpMessage message) {
        try {
            String tradeType = (String) message.getParams().get("tradeType");
            Map<String, Object> result = tradeAiService.getTradeRecommendations(tradeType);
            McpMessage response = new McpMessage();
            response.setId(message.getId());
            response.setResult(result);
            return response;
        } catch (Exception e) {
            return createErrorResponse(message.getId(), -1, "Failed to get recommendations: " + e.getMessage());
        }
    }

    @MessageMapping("/ai/predict")
    @SendTo("/topic/ai")
    public McpMessage predictTrends(McpMessage message) {
        try {
            Map<String, Object> result = tradeAiService.predictTradeTrends();
            McpMessage response = new McpMessage();
            response.setId(message.getId());
            response.setResult(result);
            return response;
        } catch (Exception e) {
            return createErrorResponse(message.getId(), -1, "Failed to predict trends: " + e.getMessage());
        }
    }

    @MessageMapping("/ai/insights")
    @SendTo("/topic/ai")
    public McpMessage getInsights(McpMessage message) {
        try {
            // Combine multiple AI analyses
            Map<String, Object> analysis = tradeAiService.analyzeTrades();
            Map<String, Object> predictions = tradeAiService.predictTradeTrends();

            Map<String, Object> combinedInsights = new java.util.HashMap<>();
            combinedInsights.put("analysis", analysis);
            combinedInsights.put("predictions", predictions);
            combinedInsights.put("timestamp", System.currentTimeMillis());

            McpMessage response = new McpMessage();
            response.setId(message.getId());
            response.setResult(combinedInsights);
            return response;
        } catch (Exception e) {
            return createErrorResponse(message.getId(), -1, "Failed to get insights: " + e.getMessage());
        }
    }

    private McpMessage createErrorResponse(String id, int code, String message) {
        McpMessage response = new McpMessage();
        response.setId(id);
        McpMessage.McpError error = new McpMessage.McpError(code, message);
        response.setError(error);
        return response;
    }
}
