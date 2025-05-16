package com.sample.iban;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.sample.iban.command.TradeAggregate;
import com.sample.iban.repository.TradeRepositoryImpl;

@SpringBootTest
@AutoConfigureMockMvc
class IbanApplicationTests {
	@Autowired
	private MockMvc mvc;

	@MockitoBean
	TradeAggregate tradeAggregate;
	@MockitoBean
	TradeRepositoryImpl tradeRepository;

	@BeforeEach
	public void setUp() {
	}

	@Test
	void getibantest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/IBAN"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());

		System.out.println("Piyush printing the response:::");
	}

}
