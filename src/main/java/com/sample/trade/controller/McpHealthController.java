package com.sample.trade.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/mcp")
@CrossOrigin(origins = "*")
public class McpHealthController {

    @Value("${mcp.server.enabled:true}")
    private boolean mcpServerEnabled;

    @Value("${mcp.server.websocket.path:/mcp}")
    private String mcpWebSocketPath;

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> getHealth() {
        Map<String, Object> health = Map.of(
                "status", "UP",
                "mcpServerEnabled", mcpServerEnabled,
                "websocketPath", mcpWebSocketPath,
                "timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(health);
    }

    @GetMapping("/endpoints")
    public ResponseEntity<Map<String, Object>> getEndpoints() {
        Map<String, Object> endpoints = Map.of(
                "tradeOperations", Map.of(
                        "getAllTrades", "/app/trades/getAll",
                        "getTradeById", "/app/trades/getById",
                        "createTrade", "/app/trades/create",
                        "getStatistics", "/app/trades/statistics"),
                "aiOperations", Map.of(
                        "analyzeTrades", "/app/ai/analyze",
                        "getRecommendations", "/app/ai/recommendations",
                        "predictTrends", "/app/ai/predict",
                        "getInsights", "/app/ai/insights"),
                "topics", Map.of(
                        "trades", "/topic/trades",
                        "ai", "/topic/ai"),
                "protocol", "JSON-RPC 2.0 over WebSocket");
        return ResponseEntity.ok(endpoints);
    }
}
