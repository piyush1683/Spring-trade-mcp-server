package com.sample.trade.mcp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class McpController {

    @Autowired
    private TradeMcpService tradeMcpService;

    @Autowired
    private ObjectMapper objectMapper;

    @MessageMapping("/trades/getAll")
    @SendTo("/topic/trades")
    public McpMessage getAllTrades(McpMessage message) {
        try {
            Map<String, Object> result = tradeMcpService.getAllTrades();
            McpMessage response = new McpMessage();
            response.setId(message.getId());
            response.setResult(result);
            return response;
        } catch (Exception e) {
            return createErrorResponse(message.getId(), -1, "Failed to get trades: " + e.getMessage());
        }
    }

    @MessageMapping("/trades/getById")
    @SendTo("/topic/trades")
    public McpMessage getTradeById(McpMessage message) {
        try {
            String tradeId = (String) message.getParams().get("tradeId");
            Map<String, Object> result = tradeMcpService.getTradeById(tradeId);
            McpMessage response = new McpMessage();
            response.setId(message.getId());
            response.setResult(result);
            return response;
        } catch (Exception e) {
            return createErrorResponse(message.getId(), -1, "Failed to get trade: " + e.getMessage());
        }
    }

    @MessageMapping("/trades/create")
    @SendTo("/topic/trades")
    public McpMessage createTrade(McpMessage message) {
        try {
            Map<String, Object> tradeData = (Map<String, Object>) message.getParams().get("tradeData");
            Map<String, Object> result = tradeMcpService.createTrade(tradeData);
            McpMessage response = new McpMessage();
            response.setId(message.getId());
            response.setResult(result);
            return response;
        } catch (Exception e) {
            return createErrorResponse(message.getId(), -1, "Failed to create trade: " + e.getMessage());
        }
    }

    @MessageMapping("/trades/statistics")
    @SendTo("/topic/trades")
    public McpMessage getTradeStatistics(McpMessage message) {
        try {
            Map<String, Object> result = tradeMcpService.getTradeStatistics();
            McpMessage response = new McpMessage();
            response.setId(message.getId());
            response.setResult(result);
            return response;
        } catch (Exception e) {
            return createErrorResponse(message.getId(), -1, "Failed to get statistics: " + e.getMessage());
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
