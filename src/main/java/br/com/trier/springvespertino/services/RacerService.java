package br.com.trier.springvespertino.services;

import java.util.List;

import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.models.Equip;
import br.com.trier.springvespertino.models.Racer;

public interface RacerService {

	Racer findById(Integer id);

	Racer insert(Racer racer);

	List<Racer> listAll();

	Racer update(Racer racer);

	void delete(Integer id);

	List<Racer> findByNameStartsWithIgnoreCase(String name);

	List<Racer> findByCountry(Country country);

	List<Racer> findByEquipOrderByName(Equip equip);

}
