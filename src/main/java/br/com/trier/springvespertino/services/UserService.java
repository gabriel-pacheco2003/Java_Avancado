package br.com.trier.springvespertino.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.User;

@Service
public interface UserService {
	
	User findById (Integer id);
	
	User insert (User user);
	
	List<User> listAll();
	
	User update (User user);
	
	void delete (Integer id);

}
