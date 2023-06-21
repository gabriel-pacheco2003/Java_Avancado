package br.com.trier.springvespertino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.models.Equip;
import br.com.trier.springvespertino.models.Racer;

@Repository
public interface RacerRepository extends JpaRepository<Racer, Integer>{

	List<Racer> findByNameStartsWithIgnoreCase(String name);
	
	List<Racer> findByCountry(Country country);
	
	List<Racer> findByEquipOrderByName(Equip equip);
	
}