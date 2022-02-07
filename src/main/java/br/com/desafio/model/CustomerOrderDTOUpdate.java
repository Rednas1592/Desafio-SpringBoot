package br.com.desafio.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrderDTOUpdate {

	@NotNull 
	@NotBlank(message = "O campo name precisa ser preenchido.")
	private String name;
	@NotNull
	@NotBlank(message = "O campo descrição precisa ser preenchido.")
	private String description;
	@NotNull
	@Min(value = 10)
	private double total;
}
