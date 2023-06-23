package br.com.trier.springvespertino.resources;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.models.dto.RaceCountryYearDTO;
import br.com.trier.springvespertino.models.dto.RaceDTO;
import br.com.trier.springvespertino.services.CountryService;
import br.com.trier.springvespertino.services.RaceService;
import br.com.trier.springvespertino.services.SpeedwayService;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;

public class ReportResource {
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private RaceService raceService;
	
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
		
		

	
	/*
	 * @GetMapping("/corrida-por-pais-ano/{paisId}/{ano}") public
	 * ResponseEntity<RaceCountryYearDTO> findRaceByCountryAndYear(@PathVariable
	 * Integer countryId, Integer year){
	 * 
	 * Country country = countryService.findById(countryId);
	 * 
	 * List<Speedway> pistasDoPais =
	 * speedwayService.findByCountryOrderBySizeDesc(country);
	 * 
	 * List<Race> corridasDoPais = new ArrayList<Race>();
	 * 
	 * for(Speedway speedway : pistasDoPais) { try {
	 * corridasDoPais.addAll(raceService.findBySpeedwayOrderByDate(speedway)); }
	 * catch (ObjectNotFound e) { corridasDoPais.addAll(new ArrayList<>()); } }
	 * 
	 * List<Race> corridasSelecionadas = new ArrayList<>(); for(Race race :
	 * corridasDoPais) { if(race.getDate().getYear() == year) {
	 * corridasSelecionadas.add(race); } }
	 * 
	 * List<RaceDTO> corridasDto = new ArrayList<>(); for(Race race:
	 * corridasSelecionadas) { corridasDto.add(race.toDTO()); }
	 * 
	 * RaceCountryYearDTO retorno = new RaceCountryYearDTO(year, null, year,
	 * corridasDto);
	 * 
	 * return ResponseEntity.ok(retorno); }
	 */
	}
}
