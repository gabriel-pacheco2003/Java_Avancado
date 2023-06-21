package br.com.trier.springvespertino.models.dto;

import br.com.trier.springvespertino.models.Country;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpeedwayDTO {

	private Integer id;
	
	private String name;
	
	private Integer size;
	
	private Country country;
	
}
