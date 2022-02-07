package br.com.desafio.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.desafio.controller.OrderConsumerUseCase;
import br.com.desafio.model.CustomerOrder;
import br.com.desafio.model.CustomerOrderDTO;
import br.com.desafio.model.CustomerOrderDTOUpdate;
import br.com.desafio.model.StatusOrder;
import br.com.desafio.repository.OrderRepository;
import br.com.desafio.util.OrderProducer;

@Service
public class CustomerOrderImpl implements OrderConsumerUseCase {

	@Autowired
	private OrderRepository orderRepository;	
	private OrderProducer orderProducer = new OrderProducer();
	
	@Override
	@Transactional
	public void consumer(CustomerOrder order) {
		
		Optional<CustomerOrder> optional = orderRepository.findById(order.getId());
		if(optional.isPresent()) {
			optional.get().setStatus(StatusOrder.PROCESSED);
		}
	}
	
	@Override
	public List<CustomerOrderDTO> listar() {
		
		List<CustomerOrder> customerOrder = orderRepository.findAll();			
		return CustomerOrderDTO.converter(customerOrder);	
	}
	
	@Override
	public List<CustomerOrderDTO> filtrar(
			String maxTotal, 
			String minTotal, 
			String status, 
			String q){
		 
		List<CustomerOrder> customerOrder = orderRepository.findByFilter(q.toLowerCase(), Double.parseDouble(minTotal), Double.parseDouble(maxTotal), status.toLowerCase());		
		return CustomerOrderDTO.converter(customerOrder);	
	}
	
	@Override
	public ResponseEntity<CustomerOrderDTO> consultarPorId(Long id) {
		
		if(!orderRepository.getById(id).getDescription().isEmpty()) {
			CustomerOrderDTO customerOrderDTO = new CustomerOrderDTO(orderRepository.getById(id));
			return ResponseEntity.ok(customerOrderDTO);
		}
		return ResponseEntity.ok(null);
	}
	
	@Override
	public ResponseEntity<CustomerOrderDTO> cadastrar(CustomerOrderDTO customerOrderDTO,  UriComponentsBuilder uriBuilder) throws InterruptedException, ExecutionException {
		
		CustomerOrder customerOrder = new CustomerOrder(customerOrderDTO);
		
		orderRepository.save(customerOrder);				
		URI uri = uriBuilder.path("/orders/{id}").buildAndExpand(customerOrder.getId()).toUri();
		 
		orderProducer.publishKafka(customerOrder);
		
		customerOrderDTO.setId(customerOrder.getId());
		return ResponseEntity.created(uri).body(customerOrderDTO);
	}

	@Override
	public ResponseEntity<CustomerOrderDTO> atualizar(Long id, CustomerOrderDTOUpdate customerOrder) {
		
		if(!orderRepository.getById(id).getDescription().isEmpty()) {
			CustomerOrder customerOrder2 = orderRepository.getById(id);
			customerOrder2.setName(customerOrder.getName());
			customerOrder2.setDescription(customerOrder.getDescription());
			customerOrder2.setTotal(customerOrder.getTotal());
			
			CustomerOrderDTO customerOrderDTO = new CustomerOrderDTO(customerOrder2);
			return ResponseEntity.ok(customerOrderDTO);
		}
		return ResponseEntity.ok(null);
	}
	
	@Override
	public ResponseEntity<CustomerOrderDTO> remover(Long id) {
		
		if(!orderRepository.getById(id).getDescription().isEmpty()) {
			orderRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(null);
	}
}
