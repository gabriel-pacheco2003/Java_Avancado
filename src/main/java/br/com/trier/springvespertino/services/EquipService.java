package br.com.trier.springvespertino.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Equip;

@Service
public interface EquipService {
	
	Equip findById (Integer id);

	Equip insert (Equip equipe);
	
	List<Equip> listAll();
	
	Equip update (Equip equipe);
	
	void delete (Integer id);
	
	List<Equip> findByName (String name);
	
}
