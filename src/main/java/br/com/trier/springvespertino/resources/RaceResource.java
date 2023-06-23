package br.com.trier.springvespertino.resources;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.models.Speedway;
import br.com.trier.springvespertino.models.dto.RaceDTO;
import br.com.trier.springvespertino.services.ChampionshipService;
import br.com.trier.springvespertino.services.RaceService;
import br.com.trier.springvespertino.services.SpeedwayService;

@RestController
@RequestMapping("/races")
public class RaceResource {

	@Autowired
	private RaceService service;

	@Autowired
	private SpeedwayService speedwayService;

	@Autowired
	private ChampionshipService champService;

	@GetMapping("/{id}")
	public ResponseEntity<RaceDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}

	@PostMapping
	public ResponseEntity<RaceDTO> insert(@RequestBody RaceDTO raceDTO) {
		return ResponseEntity.ok(service.insert(new Race(raceDTO, speedwayService.findById(raceDTO.getSpeedwayId()),
				champService.findById(raceDTO.getChampionshipId()))).toDTO());
	}

	@GetMapping()
	public ResponseEntity<List<RaceDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((race) -> race.toDTO()).toList());
	}

	@PutMapping("/{id}")
	public ResponseEntity<RaceDTO> update(@PathVariable Integer id, @RequestBody RaceDTO raceDTO) {
		Race race = new Race(raceDTO, speedwayService.findById(raceDTO.getSpeedwayId()),
				champService.findById(raceDTO.getChampionshipId()));
		race.setId(id);
		return ResponseEntity.ok(service.update(race).toDTO());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Race> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/inicialDate/{dateIn}/finalDate/{dateFin}")
	public ResponseEntity<List<Race>> findByDateBetweenOrderByDateDesc(@PathVariable ZonedDateTime dateIn,
			@PathVariable ZonedDateTime dateFin) {
		return ResponseEntity
				.ok(service.findByDateBetweenOrderByDateDesc(dateIn, dateFin).stream().map((race) -> race).toList());
	}

	@GetMapping("/championship/{championshipId}")
	public ResponseEntity<List<Race>> findByChampionshipOrderByDate(@PathVariable Championship championshipId) {
		return ResponseEntity
				.ok(service.findByChampionshipOrderByDate(championshipId).stream().map((race) -> race).toList());
	}

	@GetMapping("/speedway/{speedwayId}")
	public ResponseEntity<List<Race>> findBySpeedwayOrderByDate(@PathVariable Speedway speedwayId) {
		return ResponseEntity.ok(service.findBySpeedwayOrderByDate(speedwayId).stream().map((race) -> race).toList());
	}
}
