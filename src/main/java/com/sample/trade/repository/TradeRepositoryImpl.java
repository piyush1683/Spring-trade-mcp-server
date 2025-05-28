package com.sample.trade.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sample.trade.command.TradeDTOs;
import com.sample.trade.command.TradeDTOs.TradeState;

@Repository
public class TradeRepositoryImpl {

    @Autowired
    private final JdbcTemplate jdbcTemplate;
    // This is a placeholder for the actual implementation of the TradeRepository.
    // In a real application, this would interact with a database or other data
    // source.
    // For example, it could use JPA, JDBC, or any other data access technology.

    // Example method to save a trade

    // RowMapper to map result set to Student object
    private final RowMapper<TradeState> rowMapper = (rs, rowNum) -> {
        TradeState obj = new TradeState(
                (java.util.UUID) rs.getObject("id"), rs.getString("tradeId"),
                rs.getString("party1"), rs.getString("party2"),
                rs.getString("amount"), rs.getString("tradetype"));
        return obj;
    };

    public TradeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveTrade(TradeDTOs.TradeState state) throws SQLException {
        // Implementation to save the trade
        String queryString = "INSERT INTO trade (id, tradeId, party1, party2, amount, tradetype) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(queryString, state.id(), state.tradeId(), state.party1(), state.party2(),
                state.amount(), state.tradetype());
    }

    public List<TradeState> findallTrades() {
        String selectAllQuery = "select id, tradeId, party1, party2, amount, tradetype from trade";
        List<TradeState> allTradeList = jdbcTemplate.query(selectAllQuery,
                rowMapper);
        return allTradeList;
    }

    public TradeState findTradeById(String id) {
        String selectByIdQuery = "select id, tradeId, party1, party2, amount, tradetype from trade where tradeId = ?";
        TradeState tradeObj = jdbcTemplate.queryForObject(selectByIdQuery,
                rowMapper, id);
        return tradeObj;
    }

    // Example method to find a trade by ID
    public TradeState findTradeById(UUID id) {
        // Implementation to find the trade by ID
        return new TradeState(id, "tradeId", "party1", "party2", "amount", "tradetype");
    }

}
