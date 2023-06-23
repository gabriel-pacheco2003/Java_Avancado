package br.com.trier.springvespertino.services;

import java.util.List;

import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.models.Racer;
import br.com.trier.springvespertino.models.RacerRace;

public interface RacerRaceService {

	RacerRace findById(Integer id);

	RacerRace insert(RacerRace racerRace);

	List<RacerRace> listAll();

	RacerRace update(RacerRace racerRace);

	void delete(Integer id);

	List<RacerRace> findByRank(Integer rank);

	List<RacerRace> findByRacerOrderByRank(Racer racer);

	List<RacerRace> findByRaceOrderByRank(Race race);

}
