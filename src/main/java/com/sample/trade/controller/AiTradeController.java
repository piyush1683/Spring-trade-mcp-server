package com.sample.trade.controller;

import com.sample.trade.ai.TradeAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AiTradeController {

    @Autowired
    private TradeAiService tradeAiService;

    @GetMapping("/analyze")
    public ResponseEntity<Map<String, Object>> analyzeTrades() {
        try {
            Map<String, Object> result = tradeAiService.analyzeTrades();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                    "error", "Failed to analyze trades",
                    "message", e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @GetMapping("/recommendations")
    public ResponseEntity<Map<String, Object>> getRecommendations(
            @RequestParam(required = false) String tradeType) {
        try {
            Map<String, Object> result = tradeAiService.getTradeRecommendations(tradeType);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                    "error", "Failed to get recommendations",
                    "message", e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @GetMapping("/predict")
    public ResponseEntity<Map<String, Object>> predictTrends() {
        try {
            Map<String, Object> result = tradeAiService.predictTradeTrends();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                    "error", "Failed to predict trends",
                    "message", e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @GetMapping("/insights")
    public ResponseEntity<Map<String, Object>> getInsights() {
        try {
            // Combine multiple AI analyses
            Map<String, Object> analysis = tradeAiService.analyzeTrades();
            Map<String, Object> predictions = tradeAiService.predictTradeTrends();

            Map<String, Object> combinedInsights = Map.of(
                    "analysis", analysis,
                    "predictions", predictions,
                    "timestamp", System.currentTimeMillis());

            return ResponseEntity.ok(combinedInsights);
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                    "error", "Failed to get insights",
                    "message", e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
