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

import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.models.Equip;
import br.com.trier.springvespertino.models.Racer;
import br.com.trier.springvespertino.services.CountryService;
import br.com.trier.springvespertino.services.EquipService;
import br.com.trier.springvespertino.services.RacerService;

@RestController
@RequestMapping("/pilotos")
public class RacerResource {

	@Autowired
	private RacerService service;

	@Autowired
	private CountryService countryService;

	@Autowired
	private EquipService equipService;

	@GetMapping("/{id}")
	public ResponseEntity<Racer> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<Racer> insert(@RequestBody Racer racer) {
		countryService.findById(racer.getCountry().getId());
		equipService.findById(racer.getEquip().getId());
		return ResponseEntity.ok(service.insert(racer));
	}

	@GetMapping()
	public ResponseEntity<List<Racer>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((racer) -> racer).toList());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Racer> update(@PathVariable Integer id, @RequestBody Racer racer) {
		racer.setId(id);
		countryService.findById(racer.getCountry().getId());
		equipService.findById(racer.getEquip().getId());
		return ResponseEntity.ok(service.update(racer));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Racer> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build(); 
}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<Racer>> findByNameStartsWithIgnoreCase(@PathVariable String name) {
		return ResponseEntity.ok(service.findByNameStartsWithIgnoreCase(name).stream().map((racer) -> racer).toList());
	}

	@GetMapping("/pais/{idPais}")
	public ResponseEntity<List<Racer>> findByCountry(Country country) {
		return ResponseEntity.ok(service.findByCountry(countryService.findById(country.getId())));
	}

	@GetMapping("/equipe/{idEquipe}")
	public ResponseEntity<List<Racer>> findByEquipOrderByName(Equip equip){
		return ResponseEntity.ok(service.findByEquipOrderByName(equipService.findById(equip.getId())));

	}

}
