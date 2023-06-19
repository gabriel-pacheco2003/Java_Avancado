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
import br.com.trier.springvespertino.models.User;
import br.com.trier.springvespertino.services.EquipService;

@RestController
@RequestMapping("/equipes")
public class EquipResource {

	@Autowired
	private EquipService service;

	@PostMapping
	public ResponseEntity<Equip> insert(@RequestBody Equip equip) {
		Equip newEquip = service.insert(equip);
		return newEquip != null ? ResponseEntity.ok(newEquip) : ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Equip> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Equip>> findByName(@PathVariable String name){
		return ResponseEntity.ok(service.findByName(name));
	}
	
	@GetMapping()
	public ResponseEntity<List<Equip>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Equip> update(@PathVariable Integer id, @RequestBody Equip equip){
		equip.setId(id);
		return ResponseEntity.ok(service.insert(equip));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Equip> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
}
