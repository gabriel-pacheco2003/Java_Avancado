package br.com.trier.springvespertino.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Championship;

@Service
public interface ChampionshipService {

	Championship findById (Integer id);
	
	Championship insert (Championship campeonato);
	
	List<Championship> listAll();
	
	Championship update (Championship campeonato);
	
	void delete (Integer id);
	
	List<Championship> findByDescriptionIgnoreCase(String description);
	
	List<Championship> findByYearBetweenOrderByYearAsc(Integer startYear, Integer endYear);
	
}
