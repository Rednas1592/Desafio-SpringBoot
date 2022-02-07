package br.com.desafio.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.desafio.model.CustomerOrder;
import br.com.desafio.model.CustomerOrderDTO;
import br.com.desafio.model.CustomerOrderDTOUpdate;
import br.com.desafio.util.OrderProducer;

@RestController
@RequestMapping("/orders")
public class OrderController extends OrderProducer {

	@Autowired
	private OrderConsumerUseCase orderConsumerUseCase;	
	
	@GetMapping
	public List<CustomerOrderDTO> listar() {
		
		return orderConsumerUseCase.listar();
	}
	
	@GetMapping("/search") 
	public List<CustomerOrderDTO> filtrar(
			@RequestParam(value = "max_total", required = false) String maxTotal, 
			@RequestParam(value = "min_total", required = false) String minTotal, 
			@RequestParam(value = "status", required = false) String status, 
			@RequestParam(value = "q", required = false) String q) {
		
		return orderConsumerUseCase.filtrar(maxTotal, minTotal, status, q); 
	}
	 
	@GetMapping("/{id}")
	public ResponseEntity<CustomerOrderDTO> consultarPorId(@PathVariable Long id) {
		
		return orderConsumerUseCase.consultarPorId(id);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<CustomerOrderDTO> cadastrar(@RequestBody @Validated CustomerOrderDTO customerOrder,  UriComponentsBuilder uriBuilder) throws InterruptedException, ExecutionException {
		
		return orderConsumerUseCase.cadastrar(customerOrder, uriBuilder);
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<CustomerOrderDTO> atualizar(@PathVariable Long id, @Validated @RequestBody CustomerOrderDTOUpdate customerOrder) {
		
		return orderConsumerUseCase.atualizar(id, customerOrder);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<CustomerOrderDTO> remover(@PathVariable Long id) {
		
		return orderConsumerUseCase.remover(id);
	}
}
