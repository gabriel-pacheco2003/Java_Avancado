package br.com.trier.springvespertino.models.dto;

import br.com.trier.springvespertino.models.Speedway;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RacerRaceDTO {
	
	private Integer id;
	private Integer rank;
	private Integer racerId;
	private String racerName;
	private Integer raceId;
	private String raceDate;
	private Speedway raceSpeedway;

}
