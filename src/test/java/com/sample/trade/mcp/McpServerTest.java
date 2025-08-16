package com.sample.trade.mcp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class McpServerTest {

    @Autowired
    private TradeMcpService tradeMcpService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testMcpServerConfiguration() {
        assertNotNull(tradeMcpService);
        assertNotNull(objectMapper);
    }

    @Test
    public void testTradeMcpServiceMethods() {
        // Test getAllTrades method
        var result = tradeMcpService.getAllTrades();
        assertNotNull(result);
        assertTrue(result.containsKey("trades"));
        assertTrue(result.containsKey("count"));

        // Test getTradeStatistics method
        var stats = tradeMcpService.getTradeStatistics();
        assertNotNull(stats);
        assertTrue(stats.containsKey("totalTrades"));
        assertTrue(stats.containsKey("totalAmount"));
        assertTrue(stats.containsKey("averageAmount"));
    }

    @Test
    public void testMcpMessageStructure() {
        McpMessage message = new McpMessage();
        message.setId("1");
        message.setMethod("/trades/getAll");
        message.setParams(java.util.Map.of("test", "value"));

        assertEquals("2.0", message.getJsonrpc());
        assertEquals("1", message.getId());
        assertEquals("/trades/getAll", message.getMethod());
        assertNotNull(message.getParams());
    }

    @Test
    public void testMcpErrorStructure() {
        McpMessage.McpError error = new McpMessage.McpError(-1, "Test error");
        assertEquals(-1, error.getCode());
        assertEquals("Test error", error.getMessage());
    }

}
