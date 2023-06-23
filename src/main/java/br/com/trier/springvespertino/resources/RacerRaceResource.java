package br.com.trier.springvespertino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springvespertino.models.RacerRace;
import br.com.trier.springvespertino.models.dto.RacerRaceDTO;
import br.com.trier.springvespertino.services.RaceService;
import br.com.trier.springvespertino.services.RacerRaceService;
import br.com.trier.springvespertino.services.RacerService;

@RestController
@RequestMapping("/pilotos/corridas")
public class RacerRaceResource {

	@Autowired
	private RacerRaceService service;

	@Autowired
	private RacerService racerService;

	@Autowired
	private RaceService raceService;

	@GetMapping("/{id}")
	public ResponseEntity<RacerRaceDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}

	@PostMapping
	public ResponseEntity<RacerRaceDTO> insert(@RequestBody RacerRaceDTO raceRacerDTO) {
		RacerRace racerRace = new RacerRace(raceRacerDTO, racerService.findById(raceRacerDTO.getRacerId()),
				raceService.findById(raceRacerDTO.getRaceId()));
		return ResponseEntity.ok(service.insert(racerRace).toDTO());
	}

	@GetMapping
	public ResponseEntity<List<RacerRaceDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map(pilotR -> pilotR.toDTO()).toList());
	}

	@PostMapping("/{id}")
	public ResponseEntity<RacerRaceDTO> update(@RequestBody RacerRaceDTO raceRaceDTO, @PathVariable Integer id) {
		RacerRace racerRace = new RacerRace(raceRaceDTO, racerService.findById(raceRaceDTO.getRacerId()),
				raceService.findById(raceRaceDTO.getRaceId()));
		racerRace.setId(id);
		return ResponseEntity.ok(service.update(racerRace).toDTO());

	}

	@DeleteMapping
	public ResponseEntity<RacerRace> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/colocacao/{colocacao}")
	public ResponseEntity<List<RacerRace>> findByPlacement(@PathVariable Integer rank) {
		return ResponseEntity.ok(service.findByRank(rank).stream().map((racer) -> racer).toList());
	}

	@GetMapping("/piloto/{id}")
	public ResponseEntity<List<RacerRace>> findByRacerOrderByRank(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findByRacerOrderByRank(racerService.findById(id)).stream()
				.map(racer -> racer).toList());
	}

	@GetMapping("/corrida/{id}")
	public ResponseEntity<List<RacerRace>> findByRaceOrderByRank(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findByRaceOrderByRank(raceService.findById(id)).stream().map(racer -> racer).toList());
	}

}
