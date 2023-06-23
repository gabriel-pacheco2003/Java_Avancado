package br.com.trier.springvespertino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.models.Racer;
import br.com.trier.springvespertino.models.RacerRace;
import br.com.trier.springvespertino.repositories.RacerRaceRepository;
import br.com.trier.springvespertino.services.RacerRaceService;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;

@Service
public class RacerRaceServiceImpl implements RacerRaceService {

	@Autowired
	private RacerRaceRepository repository;

	@Override
	public RacerRace findById(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ObjectNotFound("Piloto/Corrida %s não encontrado".formatted(id)));
	}

	@Override
	public RacerRace insert(RacerRace racerRace) {
		return repository.save(racerRace);
	}

	@Override
	public List<RacerRace> listAll() {
		if (repository.findAll().isEmpty()) {
			throw new ObjectNotFound("Nenhum Piloto/Corrida cadastrado");
		}
		return repository.findAll();
	}

	@Override
	public RacerRace update(RacerRace racerRace) {
		findById(racerRace.getId());
		return repository.save(racerRace);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
	}

	@Override
	public List<RacerRace> findByRank(Integer rank) {
		if (repository.findByRank(rank).isEmpty()) {
			throw new ObjectNotFound("Nenhum Piloto/Corrida encontrado com a colocação %s".formatted(rank));
		}
		return repository.findByRank(rank);
	}

	@Override
	public List<RacerRace> findByRacerOrderByRank(Racer racer) {
		if (repository.findByRacerOrderByRank(racer).isEmpty()) {
			throw new ObjectNotFound("Nenhum Piloto encontrado");
		}
		return repository.findByRacerOrderByRank(racer);
	}

	@Override
	public List<RacerRace> findByRaceOrderByRank(Race race) {
		if (repository.findByRaceOrderByRank(race).isEmpty()) {
			throw new ObjectNotFound("Nenhuma corrida encontrada");
		}
		return repository.findByRaceOrderByRank(race);
	}

}
