package br.com.trier.springvespertino.services.impl;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.models.Speedway;
import br.com.trier.springvespertino.repositories.RaceRepository;
import br.com.trier.springvespertino.services.RaceService;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;

@Service
public class RaceServiceImpl implements RaceService {

	@Autowired
	private RaceRepository repository;

	private void validateDate(Race race) {
		if (race.getChampionship() == null){
			throw new IntegrityViolation("Campeonato inválido"); 
		}
		if (race.getDate().getYear() != race.getChampionship().getYear()) {
			throw new IntegrityViolation("Data informada difere do Campeonato");
		}
	}

	@Override
	public Race findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Corrida %s não encontrada".formatted(id)));
	}

	@Override
	public Race insert(Race race) {
		validateDate(race);
		return repository.save(race);
	}

	@Override
	public List<Race> listAll() {
		if (repository.findAll().isEmpty()) {
			throw new ObjectNotFound("Nenhuma corrida cadastrada");
		}
		return repository.findAll();
	}

	@Override
	public Race update(Race race) {
		findById(race.getId());
		validateDate(race);
		return repository.save(race);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
	}

	@Override
	public List<Race> findByDateBetweenOrderByDateDesc(ZonedDateTime dateIn, ZonedDateTime dateFin) {
		if (repository.findByDateBetweenOrderByDateDesc(dateIn, dateFin).isEmpty()) {
			throw new ObjectNotFound("Nenhuma corrida foi encontrada");
		}
		return repository.findByDateBetweenOrderByDateDesc(dateIn, dateFin);
	}

	@Override
	public List<Race> findByChampionshipOrderByDate(Championship championship) {
		if (repository.findByChampionshipOrderByDate(championship).isEmpty()) {
			throw new ObjectNotFound("Nenhuma corrida foi encontrada");
		}
		return repository.findByChampionshipOrderByDate(championship);
	}

	@Override
	public List<Race> findBySpeedwayOrderByDate(Speedway speedway) {
		if (repository.findBySpeedwayOrderByDate(speedway).isEmpty()) {
			throw new ObjectNotFound("Nenhuma corrida foi encontrada");
		}
		return repository.findBySpeedwayOrderByDate(speedway);
	}

}
