package br.com.trier.springvespertino.resources;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.models.RacerRace;
import br.com.trier.springvespertino.models.dto.RaceCountryYearDTO;
import br.com.trier.springvespertino.models.dto.RaceDTO;
import br.com.trier.springvespertino.models.dto.RacerRaceCountryRankDTO;
import br.com.trier.springvespertino.services.CountryService;
import br.com.trier.springvespertino.services.RaceService;
import br.com.trier.springvespertino.services.RacerRaceService;
import br.com.trier.springvespertino.services.RacerService;
import br.com.trier.springvespertino.services.SpeedwayService;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;

public class ReportResource {
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private RaceService raceService;
	
	@Autowired
	private RacerService racerService;
	
	@Autowired
	private RacerRaceService racerRaceService;
	
	@Autowired
	private SpeedwayService speedwayService;
	
	@GetMapping("/races-by-country-year/{countryId}/{year}")
	public ResponseEntity<RaceCountryYearDTO> findRaceByCountryAndYear(@PathVariable Integer countryId, @PathVariable Integer year){
		
		Country country = countryService.findById(countryId);
		
		List<RaceDTO> raceDTOs = speedwayService.findByCountryOrderBySizeDesc(country).stream()
		        .flatMap(speedway -> {
		            try {
		                return raceService.findBySpeedwayOrderByDate(speedway).stream();
		            } catch (ObjectNotFound e) {
		                return Stream.empty();
		            }
		        })
		        .filter(race -> race.getDate().getYear() == year)
		        .map(Race::toDTO)
		        .toList();
		
				
		return ResponseEntity.ok(new RaceCountryYearDTO(year, country.getName(), raceDTOs.size(), raceDTOs));
	}
	
	@GetMapping("/racer-race-rank-country/{rank}/{countryId}")
	public ResponseEntity<List<RacerRaceCountryRankDTO>> findRacerRaceByRankGraterThanAndCountry(@PathVariable Integer rank, @PathVariable Integer countryId){
		
		Country country = countryService.findById(countryId);
		
		List<RacerRaceCountryRankDTO> racerRaceDTOs = racerService.findByCountry(country).stream()
		        .flatMap(racer -> {
		            try {
		                return racerRaceService.findByRacerOrderByRank(racer).stream();
		            } catch (ObjectNotFound e) {
		                return Stream.empty();
		            }
		        })
		        .filter(racerRace -> racerRace.getRank() >= rank)
		        .map(RacerRace::toDTO)
		        .map(racerRaceDTO -> {
		        	return new RacerRaceCountryRankDTO(racerRaceDTO, country.toDTO());
		        })
		        .toList();
		
				
		return ResponseEntity.ok(racerRaceDTOs);
	}
}
