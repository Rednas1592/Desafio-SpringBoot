package br.com.desafio.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Entity
@Table(name = "CUSTOMER_ORDER")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CustomerOrder {

	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull 
	@NotBlank(message = "O campo name precisa ser preenchido.")
	private String name;
	@NotNull
	@NotBlank(message = "O campo descrição precisa ser preenchido.")
	private String description;
	@Enumerated(EnumType.STRING)
	private StatusOrder status = StatusOrder.NOT_PROCESSED;
	private double total;
	
}
