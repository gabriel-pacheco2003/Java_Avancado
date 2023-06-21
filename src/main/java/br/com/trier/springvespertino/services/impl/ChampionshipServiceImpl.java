package br.com.trier.springvespertino.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.models.User;
import br.com.trier.springvespertino.repositories.ChampionshipRepository;
import br.com.trier.springvespertino.services.ChampionshipService;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;

@Service
public class ChampionshipServiceImpl implements ChampionshipService {

	@Autowired
	private ChampionshipRepository repository;

	private void validateYear(Integer year) {
		LocalDate insert = LocalDate.now().plusYears(1);
		if(year.compareTo(1990) < 0 || year.compareTo(insert.getYear()) > 0) {
			throw new IntegrityViolation("Ano inválido"); 
		}
	}
	
	@Override
	public Championship findById(Integer id) {
		Optional<Championship> championship = repository.findById(id);
		return championship.orElseThrow(() -> new ObjectNotFound("Campeonato inexistente"));
	}

	@Override
	public Championship insert(Championship campeonato) {
		validateYear(campeonato.getYear());
		return repository.save(campeonato);
	}

	@Override
	public List<Championship> listAll() {
		List<Championship> lista = repository.findAll();
		if(lista.isEmpty()){
			throw new ObjectNotFound("Nenhum campeonato cadastrado");
		}
		return repository.findAll();
	}

	@Override
	public Championship update(Championship campeonato) {
		findById(campeonato.getId());
		validateYear(campeonato.getYear());
		return repository.save(campeonato);
	}

	@Override
	public void delete(Integer id) {
		Championship championship = findById(id);
			repository.delete(championship);
	}

	@Override
	public List<Championship> findByDescriptionIgnoreCase(String description) {
		List<Championship> lista = repository.findByDescriptionIgnoreCase(description);
		if(lista.isEmpty()){
			throw new ObjectNotFound("Nenhum campeonato foi encontrado".formatted(description));
		}
		return lista;
	}

	@Override
	public List<Championship> findByYearBetweenOrderByYearAsc(Integer startYear, Integer endYear) {
		List<Championship> lista = repository.findByYearBetweenOrderByYearAsc(startYear, endYear);
		if(lista.isEmpty()){
			throw new ObjectNotFound("Nenhum campeonato foi encontrado");
		}
		return lista;
	}

}
