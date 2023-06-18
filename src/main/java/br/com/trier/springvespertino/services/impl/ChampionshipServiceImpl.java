package br.com.trier.springvespertino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.repositories.ChampionshipRepository;
import br.com.trier.springvespertino.services.ChampionshipService;

@Service
public class ChampionshipServiceImpl implements ChampionshipService {

	@Autowired
	private ChampionshipRepository repository;

	@Override
	public Championship findById(Integer id) {
		Optional<Championship> championship = repository.findById(id);
		return championship.orElse(null);
	}

	@Override
	public Championship insert(Championship campeonato) {
		return repository.save(campeonato);
	}

	@Override
	public List<Championship> listAll() {
		return repository.findAll();
	}

	@Override
	public Championship update(Championship campeonato) {
		return repository.save(campeonato);
	}

	@Override
	public void delete(Integer id) {
		Championship championship = findById(id);
		if (championship != null) {
			repository.delete(championship);
		}
	}

	@Override
	public List<Championship> findByDescription(String description) {
		return repository.findByDescription(description);
	}

	@Override
	public List<Championship> findByYearBetweenOrderByYearAsc(Integer startYear, Integer endYear) {
		return repository.findByYearBetweenOrderByYearAsc(startYear, endYear);
	}

}
