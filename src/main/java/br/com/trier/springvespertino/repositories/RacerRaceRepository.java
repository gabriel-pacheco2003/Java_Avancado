package br.com.trier.springvespertino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.models.Racer;
import br.com.trier.springvespertino.models.RacerRace;

@Repository
public interface RacerRaceRepository extends JpaRepository<RacerRace, Integer>{

	List<RacerRace> findByRank(Integer rank);
	
	List<RacerRace> findByRacerOrderByRank(Racer racer);
	
	List<RacerRace> findByRaceOrderByRank(Race race);
	
}
