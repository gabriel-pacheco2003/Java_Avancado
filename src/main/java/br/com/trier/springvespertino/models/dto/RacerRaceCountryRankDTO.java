package br.com.trier.springvespertino.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RacerRaceCountryRankDTO {
	
	private Integer rank;
	
	private String racerName;
	
	private String countryName;
	
	public RacerRaceCountryRankDTO(RacerRaceDTO racerRaceDTO, CountryDTO countryDTO) {
		this.rank = racerRaceDTO.getRank();
		this.racerName = racerRaceDTO.getRacerName();
		this.countryName = countryDTO.getName();
	}
}