package br.com.trier.springvespertino.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RacerRaceDTO {
	
	private Integer id;
	
	private Integer rank;
	
	private Integer racerId;
	
	private String racerName;
	
	private Integer raceId;
	
	private String raceDate;
	
	private String raceSpeedwayName;

}
