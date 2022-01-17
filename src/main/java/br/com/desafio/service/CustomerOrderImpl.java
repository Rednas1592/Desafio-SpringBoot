package br.com.desafio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.desafio.controller.OrderConsumerUseCase;
import br.com.desafio.model.CustomerOrder;
import br.com.desafio.model.StatusOrder;
import br.com.desafio.repository.OrderRepository;

@Service
public class CustomerOrderImpl implements OrderConsumerUseCase {

	@Autowired
	private OrderRepository orderRepository;	
	
	@Override
	@Transactional
	public void consumer(CustomerOrder order) {
		
		CustomerOrder customerOrder2 = orderRepository.findById(order.getId()).get();
		customerOrder2.setStatus(StatusOrder.PROCESSED);		
	}
}
