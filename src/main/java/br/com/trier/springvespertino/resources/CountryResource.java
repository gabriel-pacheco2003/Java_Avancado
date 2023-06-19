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
import br.com.trier.springvespertino.services.CountryService;

@RestController
@RequestMapping("/paises")
public class CountryResource {

	@Autowired
	private CountryService service;

	@PostMapping
	public ResponseEntity<Country> insert(@RequestBody Country country) {
		Country newCountry = service.insert(country);
		return newCountry != null ? ResponseEntity.ok(newCountry) : ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Country> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<Country>> findByName(@PathVariable String name){
		return ResponseEntity.ok(service.findByName(name));
	}
	
	@GetMapping()
	public ResponseEntity<List<Country>> listAll() {
		return ResponseEntity.ok(service.listAll());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Country> update(@PathVariable Integer id, @RequestBody Country country) {
		country.setId(id);
		return ResponseEntity.ok(service.update(country));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Country> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
}
