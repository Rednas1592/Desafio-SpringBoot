package br.com.desafio.model;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrderDTO {

	private Long id;
	@NotNull 
	@NotBlank(message = "O campo name precisa ser preenchido.")
	private String name;
	@NotNull
	@NotBlank(message = "O campo descrição precisa ser preenchido.")
	private String description;
	private StatusOrder status = StatusOrder.NOT_PROCESSED;
	@NotNull
	@Min(value = 10)
	private double total;
	
	public CustomerOrderDTO(CustomerOrder customerOrder) {
		this.id = customerOrder.getId();
		this.name = customerOrder.getName();
		this.description = customerOrder.getDescription();
		this.status = customerOrder.getStatus();
		this.total = customerOrder.getTotal();
	}
	
	public static List<CustomerOrderDTO> converter(List<CustomerOrder> customerOrder) {
		return customerOrder.stream().map(CustomerOrderDTO::new).collect(Collectors.toList());
	}
}
