package br.com.trier.springvespertino.services;

import java.util.List;

import br.com.trier.springvespertino.models.Equip;

public interface EquipService {

	Equip findById(Integer id);

	Equip insert(Equip equip);

	List<Equip> listAll();

	Equip update(Equip equip);

	void delete(Integer id);

	List<Equip> findByName(String name);

}
