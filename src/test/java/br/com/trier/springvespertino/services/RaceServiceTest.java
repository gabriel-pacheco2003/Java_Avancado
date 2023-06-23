package br.com.trier.springvespertino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
import br.com.trier.springvespertino.utils.DateUtils;
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

	@Test
	@DisplayName("Teste inserir corrida")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void insertRaceTest() {
		var champ = championshipService.findById(1);
		var speedway = speedwayService.findById(1);
		Race race = new Race(1, DateUtils.stringToDate("01/01/2000"), speedway, champ);
		raceService.insert(race);
		assertEquals(1, race.getId());
		assertEquals(champ, race.getChampionship());
		assertEquals(speedway, race.getSpeedway());
	}

	@Test
	@DisplayName("Teste inserir corrida com campeonato inválido")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void insertRaceWithInvalidChampTest() {
		var speedway = speedwayService.findById(1);
		Race race = new Race(1, DateUtils.stringToDate("01/01/2000"), speedway, null);
		var exception = assertThrows(IntegrityViolation.class, () -> raceService.insert(race));
		assertEquals("Campeonato inválido", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir corrida com data diferente do campeonato")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void insertRaceWithInvalidDateTest() {
		var champ = championshipService.findById(1);
		var speedway = speedwayService.findById(1);
		Race race = new Race(1, DateUtils.stringToDate("01/01/1990"), speedway, champ);
		var exception = assertThrows(IntegrityViolation.class, () -> raceService.insert(race));
		assertEquals("Data informada difere do Campeonato", exception.getMessage());
	}

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

	@Test
	@DisplayName("Teste alterar corrida")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void updatedRaceTest() {
		Race race = new Race(1, DateUtils.stringToDate("01/01/2000"), speedwayService.findById(2), championshipService.findById(1));
		raceService.update(race);
		assertEquals(1, race.getId());
		assertEquals(2000, race.getDate().getYear());
    }
	
	@Test
	@DisplayName("Teste alterar corrida com data inválida")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void updatedRaceWithInvalidDateTest() {
		Race race = new Race(1, DateUtils.stringToDate("01/01/2020"), speedwayService.findById(2), championshipService.findById(1));
		var exception = assertThrows(IntegrityViolation.class, () -> raceService.update(race));
		assertEquals("Data informada difere do Campeonato", exception.getMessage());
    }

	@Test
	@DisplayName("Teste busca por corrida entre datas")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void findRaceByDateTest() {
		List<Race> lista = raceService.findByDateBetweenOrderByDateDesc(DateUtils.stringToDate("01/01/1990"), DateUtils.stringToDate("01/01/2007"));
		assertEquals(2, lista.size());
	}

	@Test
	@DisplayName("Teste busca por corrida entre datas não encontrada")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void findRaceByDateNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> raceService.findByDateBetweenOrderByDateDesc(
				DateUtils.stringToDate("01/01/1990"), DateUtils.stringToDate("01/01/1991")));
		assertEquals("Nenhuma corrida foi encontrada", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste busca corrida por campeonato")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void findRaceByChampTest() {
		var champ = championshipService.findById(1);
		var race = raceService.findByChampionshipOrderByDate(champ);
		assertEquals(1, race.size());
	}
	
	@Test
	@DisplayName("Teste busca corrida por campeonato não encontrado")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void findRaceByChampNotFoundTest() {
		var champ = championshipService.findById(3);
		var exception = assertThrows(ObjectNotFound.class, () -> raceService.findByChampionshipOrderByDate(champ));
		assertEquals("Nenhuma corrida foi encontrada", exception.getMessage());
	}
	
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
