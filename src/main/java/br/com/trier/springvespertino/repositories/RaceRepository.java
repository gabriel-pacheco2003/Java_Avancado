package br.com.trier.springvespertino.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.models.Speedway;

@Repository
public interface RaceRepository extends JpaRepository<Race, Integer>{

	List<Race> findByDateBetweenOrderByDateDesc(ZonedDateTime dateIn, ZonedDateTime dateFin);
	
	List<Race> findByChampionshipOrderByDate(Championship championship);
	
	List<Race> findBySpeedwayOrderByDate(Speedway speedway);
	
}