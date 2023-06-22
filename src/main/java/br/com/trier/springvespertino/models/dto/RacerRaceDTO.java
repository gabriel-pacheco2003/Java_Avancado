package br.com.trier.springvespertino.models.dto;

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
	private Integer racerId;
	private String racerName;
	private String raceEquip;
	private Integer raceId;
	private String raceDate;
	private String raceSpeedway;

}
