package br.com.trier.springvespertino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.models.Equip;

@Repository
public interface ChampionshipRepository extends JpaRepository<Championship, Integer>{
	
	List<Championship> findByDescription(String description);
	
	List<Championship> findByYearBetweenOrderByYearAsc(Integer startYear, Integer endYear);

}
