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
import br.com.trier.springvespertino.models.dto.CountryDTO;
import br.com.trier.springvespertino.services.CountryService;

@RestController
@RequestMapping("/contries")
public class CountryResource {

	@Autowired
	private CountryService service;

	@PostMapping
	public ResponseEntity<CountryDTO> insert(@RequestBody CountryDTO country) {
		return ResponseEntity.ok(service.insert(new Country(country)).toDTO());
	}

	@GetMapping("/{id}")
	public ResponseEntity<CountryDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}

	@GetMapping()
	public ResponseEntity<List<CountryDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((country) -> country.toDTO()).toList());
	}

	@PutMapping("/{id}")
	public ResponseEntity<CountryDTO> update(@PathVariable Integer id, @RequestBody CountryDTO countryDTO) {
		Country country = new Country(countryDTO);
		country.setId(id);
		return ResponseEntity.ok(service.update(country).toDTO());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Country> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<CountryDTO>> findByName(@PathVariable String name) {
		return ResponseEntity.ok(service.findByName(name).stream().map((country) -> country.toDTO()).toList());

	}
	
}
