package br.com.desafio.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.desafio.model.CustomerOrder;
import br.com.desafio.model.StatusOrder;
import br.com.desafio.repository.OrderRepository;
import br.com.desafio.util.OrderProducer;
import br.com.desafio.validation.ErrorResponse;

@RestController
@RequestMapping("/orders")
public class OrderController extends OrderProducer {

	@Autowired
	private OrderRepository orderRepository;
	
	@GetMapping
	public List<CustomerOrder> listar() {
		
		return orderRepository.findAll();
	}
	
	@GetMapping("/search") 
	public List<CustomerOrder> filtrar(String max_total, String	 min_total, String status, String q) {
		
		return orderRepository.findByFilter(q.toLowerCase(), Double.parseDouble(min_total), Double.parseDouble(max_total), status.toLowerCase()); 
	}
	 
	@GetMapping("/{id}")
	public ResponseEntity<?> consultarPorId(@PathVariable Long id) {
		
		Optional<CustomerOrder> optional = orderRepository.findById(id);
		if(optional.isPresent()) {
			return ResponseEntity.ok(orderRepository.findById(id).get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("404", "Pedido não encontrado!"));
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestBody @Validated CustomerOrder customerOrder,  UriComponentsBuilder uriBuilder) throws InterruptedException, ExecutionException {
		
		orderRepository.save(customerOrder);				
		URI uri = uriBuilder.path("/orders/{id}").buildAndExpand(customerOrder.getId()).toUri();
		
		publishKafka(customerOrder);
		
		return ResponseEntity.created(uri).body(customerOrder);
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<?> atualizar(@PathVariable Long id, @Validated @RequestBody CustomerOrder customerOrder) {
		
		Optional<CustomerOrder> optional = orderRepository.findById(id);
		if(optional.isPresent()) {
			CustomerOrder customerOrder2 = orderRepository.getById(id);
			customerOrder2.setName(customerOrder.getName());
			customerOrder2.setDescription(customerOrder.getDescription());
			
			return ResponseEntity.ok(customerOrder2);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("404", "Pedido não encontrado!"));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		
		Optional<CustomerOrder> optional = orderRepository.findById(id);
		if(optional.isPresent()) {
			orderRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("404", "Pedido não encontrado!"));
	}
}
