package br.com.trier.springvespertino.models.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Aposta_dto {
	
	private List<Integer> dice;
	private Integer soma;
	private Double percentual;
	private String message;
	
}
