package br.com.trier.springvespertino.services.impl;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.models.Speedway;
import br.com.trier.springvespertino.repositories.RaceRepository;
import br.com.trier.springvespertino.services.ChampionshipService;
import br.com.trier.springvespertino.services.RaceService;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;

@Service
public class RaceServiceImpl implements RaceService {

	@Autowired
	private RaceRepository repository;
	
	@Autowired
	private ChampionshipService championshipService;

	private void validateDate(Race race) {
		Championship champ = championshipService.findById(race.getChampionship().getId());
		
		if (race.getDate() == null ){
			throw new IntegrityViolation("Data inválida"); 
		}
		if (race.getDate().getYear() != champ.getYear()) {
			throw new IntegrityViolation("Data informada difere do Campeonato");
		}
	}

	public Race findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Corrida %s não encontrada".formatted(id)));
	}

	public Race insert(Race race) {
		validateDate(race);
		return repository.save(race);
	}

	public List<Race> listAll() {
		if (repository.findAll().isEmpty()) {
			throw new ObjectNotFound("Nenhuma corrida cadastrada");
		}
		return repository.findAll();
	}

	public Race update(Race race) {
		findById(race.getId());
		validateDate(race);
		return repository.save(race);
	}

	public void delete(Integer id) {
		repository.delete(findById(id));
	}

	public List<Race> findByDateBetweenOrderByDateDesc(ZonedDateTime dateIn, ZonedDateTime dateFin) {
		if (repository.findByDateBetweenOrderByDateDesc(dateIn, dateFin).isEmpty()) {
			throw new ObjectNotFound("Nenhuma corrida foi encontrada");
		}
		return repository.findByDateBetweenOrderByDateDesc(dateIn, dateFin);
	}

	public List<Race> findByChampionshipOrderByDate(Championship championship) {
		if (repository.findByChampionshipOrderByDate(championship).isEmpty()) {
			throw new ObjectNotFound("Nenhuma corrida foi encontrada");
		}
		return repository.findByChampionshipOrderByDate(championship);
	}

	public List<Race> findBySpeedwayOrderByDate(Speedway speedway) {
		if (repository.findBySpeedwayOrderByDate(speedway).isEmpty()) {
			throw new ObjectNotFound("Nenhuma corrida foi encontrada");
		}
		return repository.findBySpeedwayOrderByDate(speedway);
	}

}
