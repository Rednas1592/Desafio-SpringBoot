package br.com.desafio.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import br.com.desafio.controller.OrderConsumerUseCase;
import br.com.desafio.model.CustomerOrder;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderConsumer {
	
	@Autowired
	private OrderConsumerUseCase orderConsumerUseCase;	
	
	@KafkaListener(topics = "${order.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumer(@Payload CustomerOrder order) {
		orderConsumerUseCase.consumer(order);
		log.info("Mensagem lida com sucesso");
    }
}

