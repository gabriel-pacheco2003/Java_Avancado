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
import br.com.trier.springvespertino.models.Speedway;
import br.com.trier.springvespertino.models.dto.SpeedwayDTO;
import br.com.trier.springvespertino.services.CountryService;
import br.com.trier.springvespertino.services.SpeedwayService;

@RestController
@RequestMapping("/pistas")
public class SpeedwayResource {
	
	@Autowired
	private SpeedwayService service;
	
	@Autowired
	private CountryService countryService;
	
	@PostMapping
	public ResponseEntity<SpeedwayDTO> insert(@RequestBody SpeedwayDTO speedway) {
		countryService.findById(speedway.getCountry().getId());
		return ResponseEntity.ok(service.insert(new Speedway(speedway)).toDTO());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SpeedwayDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<SpeedwayDTO>> findByNameStartsWithIgnoreCase(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameStartsWithIgnoreCase(name).stream().map((speedway) -> speedway.toDTO()).toList()); 
	}
	
	@GetMapping
	public ResponseEntity<List<SpeedwayDTO>> listAll(){
		return ResponseEntity.ok(service.listAll().stream().map((speedway) -> speedway.toDTO()).toList()); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<SpeedwayDTO> update(@PathVariable Integer id, @RequestBody SpeedwayDTO speedwayDTO){
		speedwayDTO.setId(id);
		countryService.findById(speedwayDTO.getCountry().getId());
		return ResponseEntity.ok(service.update(new Speedway(speedwayDTO)).toDTO());
	}
	
	@GetMapping("/tamanhoInicial/{sizeIn}/tamanhoFinal/{sizeFin}")
	public ResponseEntity<List<SpeedwayDTO>> findBySizeBetween(@PathVariable Integer sizeIn, @PathVariable Integer sizeFin){
		return ResponseEntity.ok(service.findBySizeBetween(sizeIn, sizeFin).stream().map((speedway) -> speedway.toDTO()).toList());
	}
	
	@GetMapping("/pais/{idPais}")
	public ResponseEntity<List<Speedway>> findByCountryOrderBySizeDesc(Country country){
		return ResponseEntity.ok(service.findByCountryOrderBySizeDesc(countryService.findById(country.getId())));
	}

	@DeleteMapping("/{id}")
		public ResponseEntity<Speedway> delete(@PathVariable Integer id){
			service.delete(id);
			return ResponseEntity.ok().build(); 
	}

}
