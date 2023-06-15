package br.com.trier.springvespertino.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dado {
	
	private List<Integer> dadosRolados;

	private Integer resultado;
	
	private Integer porcentagem;
	
}
