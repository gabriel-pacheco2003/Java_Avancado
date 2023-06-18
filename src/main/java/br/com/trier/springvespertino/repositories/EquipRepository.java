package br.com.trier.springvespertino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.Equip;

@Repository
public interface EquipRepository extends JpaRepository<Equip, Integer>{
	
	List<Equip> findByName(String nome); 

}
