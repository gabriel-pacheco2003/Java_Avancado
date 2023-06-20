package br.com.trier.springvespertino.resources;

import br.com.trier.springvespertino.models.User;
import br.com.trier.springvespertino.models.dto.UserDTO;
import br.com.trier.springvespertino.services.UserService;

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

@RestController
@RequestMapping("/usuarios")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@PostMapping
	public ResponseEntity<UserDTO> insert(@RequestBody UserDTO user) {
		return ResponseEntity.ok(service.insert(new User(user)).toDTO());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<UserDTO>> findByName(@PathVariable String name){
		return ResponseEntity.ok(service.findByName(name).stream().map((user) -> user.toDTO()).toList()); 
	}
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> listAll(){
		return ResponseEntity.ok(service.listAll().stream().map((user) -> user.toDTO()).toList()); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO userDTO){
		User user = new User(userDTO);
		user.setId(id);
		return ResponseEntity.ok(service.update(user).toDTO());
	}

	@DeleteMapping("/{id}")
		public ResponseEntity<User> delete(@PathVariable Integer id){
			service.delete(id);
			return ResponseEntity.ok().build(); 
	}
}