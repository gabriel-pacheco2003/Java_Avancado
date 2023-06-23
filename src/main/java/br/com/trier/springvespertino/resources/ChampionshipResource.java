package br.com.trier.springvespertino.resources;

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
import br.com.trier.springvespertino.models.dto.ChampionshipDTO;
import br.com.trier.springvespertino.services.ChampionshipService;

@RestController
@RequestMapping("/championships")
public class ChampionshipResource {

	@Autowired
	private ChampionshipService service;

	@PostMapping
	public ResponseEntity<ChampionshipDTO> insert(@RequestBody ChampionshipDTO champ) {
		return ResponseEntity.ok(service.insert(new Championship(champ)).toDTO());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ChampionshipDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}

	@GetMapping()
	public ResponseEntity<List<ChampionshipDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((champ) -> champ.toDTO()).toList());
	}

	@PutMapping("/{id}")
	public ResponseEntity<ChampionshipDTO> update(@PathVariable Integer id, @RequestBody ChampionshipDTO champDTO) {
		Championship champ = new Championship(champDTO);
		champ.setId(id);
		return ResponseEntity.ok((service.insert(champ).toDTO()));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Championship> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/description/{description}")
	public ResponseEntity<List<ChampionshipDTO>> findByDescription(@PathVariable String description) {
		return ResponseEntity
				.ok(service.findByDescriptionIgnoreCase(description).stream().map((champ) -> champ.toDTO()).toList());
	}

	@GetMapping("/inicialYear/{yearIn}/finalYear/{yearFin}")
	public ResponseEntity<List<ChampionshipDTO>> findByYearBetween(@PathVariable Integer yearIn,
			@PathVariable Integer yearFin) {
		return ResponseEntity.ok(service.findByYearBetweenOrderByYearAsc(yearIn, yearFin).stream()
				.map((champ) -> champ.toDTO()).toList());
	}
}
