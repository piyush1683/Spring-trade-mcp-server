package com.sample.iban.command;

import java.sql.SQLException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sample.iban.command.TradeDTOs.CreateTradeCommand;
import com.sample.iban.command.TradeDTOs.TradeCreatedEvent;
import com.sample.iban.command.TradeDTOs.TradeState;
import com.sample.iban.repository.TradeRepositoryImpl;

@Service
public class TradeAggregate implements ApplicationEventPublisherAware {

    @Autowired
    private final TradeRepositoryImpl tradeRepository;
    ApplicationEventPublisher applicationEventPublisher;

    public TradeAggregate(TradeRepositoryImpl tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public TradeCreatedEvent handle(CreateTradeCommand command) {
        // Handle the command to create a trade
        System.out.println("Creating trade with ID: " + command.tradeId());
        TradeCreatedEvent tradeCreatedEvent = new TradeCreatedEvent(
                command.id(),
                command.tradeId(),
                command.party1(),
                command.party2(),
                command.amount(),
                command.tradetype());

        raiseEvent(tradeCreatedEvent);
        return tradeCreatedEvent;

        // Add logic to persist the trade or perform other actions
    }

    private void raiseEvent(TradeCreatedEvent tradeCreatedEvent) {
        applicationEventPublisher.publishEvent(tradeCreatedEvent);
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'raiseEvent'");
    }

    // @EventListener(mode = EventListenerMode.ASYNC)
    // Latest Springboot mode is used to define the error handling mode of the
    // listener
    // the listener is set to asynchronous mode. In this mode,
    // if an exception occurs, it won’t stop the execution of other listeners nor
    // propagate to the publisher.
    @EventListener
    public void On(TradeCreatedEvent event) throws Exception {

        // Handle the event to create a trade
        System.out.println("Creating trade with ID: " + event.tradeId());
        TradeState tradeState = new TradeState(
                event.id(),
                event.tradeId(),
                event.party1(),
                event.party2(),
                event.amount(),
                event.tradetype());
        tradeRepository.saveTrade(tradeState);
        // Add logic to update the trade or perform other actions
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        // TODO Auto-generated method stub
        this.applicationEventPublisher = applicationEventPublisher;
        // throw new UnsupportedOperationException("Unimplemented method
        // 'setApplicationEventPublisher'");
    }

}
