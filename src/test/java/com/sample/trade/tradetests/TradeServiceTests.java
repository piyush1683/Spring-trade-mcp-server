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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MissingRequestCookieException;

import com.sample.trade.command.TradeAggregate;
import com.sample.trade.command.TradeDTOs.CreateTradeCommand;
import com.sample.trade.controller.TradeController;
import com.sample.trade.repository.TradeRepositoryImpl;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTests {

    private MockMvc mockMvc;

    @Mock
    private TradeRepositoryImpl tradeRepository;

    @Mock
    private TradeAggregate tradeAggregate;

    @InjectMocks
    private TradeController tradeController;

    @BeforeEach
    public void setup() {
        // Initialize MockMvc with the TradeAggregate controller
        mockMvc = MockMvcBuilders.standaloneSetup(tradeController).build();
    }

    @Test
    void getTrades() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trades"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        System.out.println("Piyush printing the response:::");
    }
}