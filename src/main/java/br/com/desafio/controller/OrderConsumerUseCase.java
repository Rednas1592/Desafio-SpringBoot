package br.com.desafio.controller;

import org.springframework.messaging.handler.annotation.Payload;

import br.com.desafio.model.CustomerOrder;

public interface OrderConsumerUseCase {

	void consumer(@Payload CustomerOrder order);
}
