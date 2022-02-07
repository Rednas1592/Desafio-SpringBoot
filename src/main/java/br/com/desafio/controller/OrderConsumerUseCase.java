package br.com.desafio.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.desafio.model.CustomerOrder;
import br.com.desafio.model.CustomerOrderDTO;
import br.com.desafio.model.CustomerOrderDTOUpdate;

public interface OrderConsumerUseCase {

	void consumer(CustomerOrder order);
	List<CustomerOrderDTO> listar();
	List<CustomerOrderDTO> filtrar(
			String maxTotal, 
			String minTotal, 
			String status, 
			String q);
	ResponseEntity<CustomerOrderDTO> consultarPorId(Long id);
	ResponseEntity<CustomerOrderDTO> cadastrar(CustomerOrderDTO customerOrder,  UriComponentsBuilder uriBuilder) throws InterruptedException, ExecutionException;
	ResponseEntity<CustomerOrderDTO> atualizar(Long id, CustomerOrderDTOUpdate customerOrder);
	ResponseEntity<CustomerOrderDTO> remover(Long id);
}
