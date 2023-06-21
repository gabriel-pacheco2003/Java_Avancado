package br.com.trier.springvespertino.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChampionshipDTO {
	
	private Integer id;
	
	private String description;
	
	private Integer year;

}
