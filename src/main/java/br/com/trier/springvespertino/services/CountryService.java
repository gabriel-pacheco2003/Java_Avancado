package br.com.trier.springvespertino.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Country;

@Service
public interface CountryService {
	
	Country findById (Integer id);
	
	Country insert (Country pais);
	
	List<Country> listAll();
	
	Country update (Country pais);
	
	void delete (Integer id);
	
	List<Country> findByName (String name);

}
