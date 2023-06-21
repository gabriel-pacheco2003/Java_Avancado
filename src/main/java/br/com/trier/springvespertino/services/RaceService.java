package br.com.trier.springvespertino.services;

import java.time.ZonedDateTime;
import java.util.List;

import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.models.Speedway;

public interface RaceService {

	Race findById(Integer id);

	Race insert(Race race);

	List<Race> listAll();

	Race update(Race race);

	void delete(Integer id);

	List<Race> findByDateBetweenOrderByDateDesc(ZonedDateTime dateIn, ZonedDateTime dateFin);

	List<Race> findByChampionshipOrderByDate(Championship championship);

	List<Race> findBySpeedwayOrderByDate(Speedway speedway);
}
