package br.com.trier.springvespertino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class RaceServiceTest extends BaseTests {

	@Autowired
	RaceService raceService;

	@Autowired
	ChampionshipService championshipService;

	@Autowired
	SpeedwayService speedwayService;

	@Test
	@DisplayName("Teste buscar por ID")
	@Sql("classpath:/resources/sqls/tabelas.sql")
	void findByIdTest() {
		var race = raceService.findById(1);
		assertNotNull(race);
		assertEquals(1, race.getId());

	}

	@Test
	@DisplayName("Teste buscar por ID inexistente")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void ListByIdNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> raceService.findById(10));
		assertEquals("Corrida 10 não encontrada", exception.getMessage());
	}

	/*
	 * @Test
	 * 
	 * @DisplayName("Teste inserir pista")
	 * 
	 * @Sql({ "classpath:/resources/sqls/tabelas.sql" }) void insertSpeedwayTest() {
	 * var champ = championshipService.findById(1); var speedway =
	 * speedwayService.findById(1); Race race = new Race(1, 2000-01-01 10:00:00,
	 * speedway, champ); raceService.insert(race); assertEquals(1, race.getId());
	 * assertEquals(champ, race.getChampionship()); assertEquals(speedway,
	 * race.getSpeedway()); }
	 */

	@Test
	@DisplayName("Teste listar todos")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void listAllTest() {
		var speedway = raceService.listAll();
		assertEquals(3, speedway.size());
	}

	@Test
	@DisplayName("Teste listar todos sem possuir corridas cadastradas")
	void listAllNonExistxTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> raceService.listAll());
		assertEquals("Nenhuma corrida cadastrada", exception.getMessage());
	}

	@Test
	@DisplayName("Teste remover corrida")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void removeRaceTest() {
		raceService.delete(1);
		assertEquals(2, raceService.listAll().size());
	}

	@Test
	@DisplayName("Teste remover corrida inexistente")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void removeRaceNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> raceService.findById(100));
		assertEquals("Corrida 100 não encontrada", exception.getMessage());
	}

	/*
	 * @Test
	 * 
	 * @DisplayName("Teste alterar corrida")
	 * 
	 * @Sql({ "classpath:/resources/sqls/tabelas.sql" }) void updatedSpeedwaysTest()
	 * { var corrida = raceService.findById(1); assertEquals("Pista1",
	 * corrida.getDate()); corrida.setDate(2000-01-01*00*00*00);
	 * raceService.update(corrida); var corridaUpdated = raceService.findById(1);
	 * assertEquals("update", corridaUpdated.getName()); }
	 */


	@Test
	@DisplayName("Teste busca corrida por pista")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void findRaceBySpeedwayTest() {
		var speedway = speedwayService.findById(2);
		var race = raceService.findBySpeedwayOrderByDate(speedway);
		assertEquals(1, race.size());
	}

	@Test

	@DisplayName("Teste busca corrida por pista não encontrada")

	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void findRaceBySpeedwayNotFoundTest() {
		var speedway = speedwayService.findById(3);
		var exception = assertThrows(ObjectNotFound.class, () -> raceService.findBySpeedwayOrderByDate(speedway));
		assertEquals("Nenhuma corrida foi encontrada", exception.getMessage());
	}

}
