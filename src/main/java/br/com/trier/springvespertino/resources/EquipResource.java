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

import br.com.trier.springvespertino.models.Equip;
import br.com.trier.springvespertino.models.dto.EquipDTO;
import br.com.trier.springvespertino.services.EquipService;

@RestController
@RequestMapping("/equipes")
public class EquipResource {

	@Autowired
	private EquipService service;

	@PostMapping
	public ResponseEntity<EquipDTO> insert(@RequestBody EquipDTO equip) {
		return ResponseEntity.ok(service.insert(new Equip(equip)).toDTO());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EquipDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<EquipDTO>> findByName(@PathVariable String name){
		return ResponseEntity.ok(service.findByName(name).stream().map((equip) -> equip.toDTO()).toList()); 
	}
	
	@GetMapping()
	public ResponseEntity<List<EquipDTO>> listAll(){
		return ResponseEntity.ok(service.listAll().stream().map((equip) -> equip.toDTO()).toList()); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EquipDTO> update(@PathVariable Integer id, @RequestBody EquipDTO equipDTO){
		Equip equip = new Equip(equipDTO);
		equip.setId(id);
		return ResponseEntity.ok(service.insert(equip).toDTO());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Equip> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
}
