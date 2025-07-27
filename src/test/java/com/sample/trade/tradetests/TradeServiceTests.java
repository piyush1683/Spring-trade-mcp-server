package com.sample.trade.tradetests;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MissingRequestCookieException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.trade.command.TradeDTOs.CreateTradeCommand;
import com.sample.trade.command.TradeDTOs.TradeBody;
import com.sample.trade.controller.TradeController;
import com.sample.trade.repository.TradeRepositoryImpl;
import com.sample.trade.service.TradeAggregate;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTests {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TradeRepositoryImpl tradeRepository;

    @Mock
    private TradeAggregate tradeAggregate;

    @InjectMocks
    private TradeController tradeController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        // Initialize MockMvc with the TradeAggregate controller
        mockMvc = MockMvcBuilders.standaloneSetup(tradeController).build();
    }

    @Test
    void getTrades() throws Exception {
        System.out.println("Piyush Starting the test:::");

        mockMvc.perform(MockMvcRequestBuilders.get("/trades").header("Authorisation",
                "Bearer eyJhbGciOiJSUzUxMiJ9.eyJhdWRpZW5jZSI6Ik1ZQXVkaWVuY2UiLCJpc3N1ZXIiOiJNWUxvY2FsQ0EiLCJzdWIiOiJwaXl1c2gxMTEiLCJpYXQiOjE3NTI5NDQzNDgsImV4cCI6MTc1Mjk0NDM4NH0.KVVQBRZUaq0xarFsy7EHrN7FkKQA_qywyViMcVXQbockGKYBRQ0RX0eA9BZPa2zIlI8myBCf45fehPtPexj_ric6oXEzHj4usOLdWDeH-wyX-nYH1yGSFgMRCpRsr9UnwV4xbXINsv1kHk0anKmpLSYyWPKXYGFn7nfzIUL5f0AD8vxVNzLUAfnBcBDF0JvC2WHBL2pDNCxjMT1t-xbt9x_X8zBzxbV_yWfmwtTxQKR-fuP4VRjfEMKLsayiE27yWX5yjNOUvC4KAYJyvGbO8xM58bv8g6u4HIang3C3TmRZ5_tQGC1--GTxjy8Ad1qgxnmm_ExUq1j-QZoTMzBYwOL9poHU_UcylemL-_xR2zMejTISrKfYKy1og9yP1ydCXNlbkbvEANJkCFF4RB3DBFjkNT2k8gyrqEIONjO8hix3EJ89vcL1HhxnmyllcuACZ4QOqH8b2IsnK6CagmZMhUNdmgTo3Q44TbORSrMx_ag1sUIYR-FtuRKOXjgVg1QW21uPWYbizIPXcuQouwjlAHDh4iiPS9esjBKpxFs0rgHUV5NSq-NRPGGgDUD1cSdP2QNJV802iqOnVsMV6drJUuKrPB5oZ0fIogCqYhjMiZu32Phm1GxEp1p4kAN-h8qRt3x4iinSKRxH4nN9xWzeFraoG60zxX45JU_PRXHRjWc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        System.out.println("Piyush printing the response:::");
    }

    @Test
    void postTrade() throws Exception {
        System.out.println("Piyush Starting the Post test:::");

        mockMvc.perform(MockMvcRequestBuilders.post("/trade")
                .content(objectMapper.writeValueAsString(new TradeBody(
                        UUID.randomUUID(),
                        "301",
                        "HDFC",
                        "ICICI",
                        "1000.0",
                        "Fixed Income Financing")))
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorisation",
                        "Bearer eyJhbGciOiJSUzUxMiJ9.eyJhdWRpZW5jZSI6Ik1ZQXVkaWVuY2UiLCJpc3N1ZXIiOiJNWUxvY2FsQ0EiLCJzdWIiOiJwaXl1c2gxMTEiLCJpYXQiOjE3NTI5NDQzNDgsImV4cCI6MTc1Mjk0NDM4NH0.KVVQBRZUaq0xarFsy7EHrN7FkKQA_qywyViMcVXQbockGKYBRQ0RX0eA9BZPa2zIlI8myBCf45fehPtPexj_ric6oXEzHj4usOLdWDeH-wyX-nYH1yGSFgMRCpRsr9UnwV4xbXINsv1kHk0anKmpLSYyWPKXYGFn7nfzIUL5f0AD8vxVNzLUAfnBcBDF0JvC2WHBL2pDNCxjMT1t-xbt9x_X8zBzxbV_yWfmwtTxQKR-fuP4VRjfEMKLsayiE27yWX5yjNOUvC4KAYJyvGbO8xM58bv8g6u4HIang3C3TmRZ5_tQGC1--GTxjy8Ad1qgxnmm_ExUq1j-QZoTMzBYwOL9poHU_UcylemL-_xR2zMejTISrKfYKy1og9yP1ydCXNlbkbvEANJkCFF4RB3DBFjkNT2k8gyrqEIONjO8hix3EJ89vcL1HhxnmyllcuACZ4QOqH8b2IsnK6CagmZMhUNdmgTo3Q44TbORSrMx_ag1sUIYR-FtuRKOXjgVg1QW21uPWYbizIPXcuQouwjlAHDh4iiPS9esjBKpxFs0rgHUV5NSq-NRPGGgDUD1cSdP2QNJV802iqOnVsMV6drJUuKrPB5oZ0fIogCqYhjMiZu32Phm1GxEp1p4kAN-h8qRt3x4iinSKRxH4nN9xWzeFraoG60zxX45JU_PRXHRjWc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        System.out.println("Piyush printing the response:::");
    }
}