package br.com.trier.springvespertino.resources;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.models.Speedway;
import br.com.trier.springvespertino.services.ChampionshipService;
import br.com.trier.springvespertino.services.RacerService;
import br.com.trier.springvespertino.services.SpeedwayService;

@RestController
@RequestMapping("/corridas")
public class RaceResource {

	@Autowired
	private RacerService service;
	
	@Autowired
	private SpeedwayService speedwayService;
	
	@Autowired
	private ChampionshipService champService;
	

}
