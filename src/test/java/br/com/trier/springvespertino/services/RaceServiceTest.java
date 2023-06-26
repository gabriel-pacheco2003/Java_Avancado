package br.com.trier.springvespertino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
	@Sql("classpath:/resources/sqls/campeonato.sql")
	@Sql("classpath:/resources/sqls/pais.sql")
	@Sql("classpath:/resources/sqls/pista.sql")
	@Sql("classpath:/resources/sqls/corrida.sql")
	void findByIdTest() {
		var race = raceService.findById(1);
		assertEquals(1, race.getId());
		assertEquals(2000, race.getDate().getYear());
		assertEquals(speedwayService.findById(1), race.getSpeedway());
		assertEquals(championshipService.findById(1), race.getChampionship());

	}

	@Test
	@DisplayName("Teste buscar por ID inexistente")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	@Sql("classpath:/resources/sqls/pais.sql")
	@Sql("classpath:/resources/sqls/pista.sql")
	@Sql("classpath:/resources/sqls/corrida.sql")
	void ListByIdNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> raceService.findById(10));
		assertEquals("Corrida 10 não encontrada", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir corrida")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	@Sql("classpath:/resources/sqls/pais.sql")
	@Sql("classpath:/resources/sqls/pista.sql")
	@Sql("classpath:/resources/sqls/corrida.sql")
	void insertRaceTest() {
		Race race = new Race(1, DateUtils.stringToDate("01/01/2000"), speedwayService.findById(1),
				championshipService.findById(1));
		raceService.insert(race);
		assertEquals(1, race.getId());
		assertEquals(2000, race.getDate().getYear());
		assertEquals(speedwayService.findById(1), race.getSpeedway());
		assertEquals(championshipService.findById(1), race.getChampionship());
	}

	@Test
	@DisplayName("Teste inserir corrida com campeonato inválido")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	@Sql("classpath:/resources/sqls/pais.sql")
	@Sql("classpath:/resources/sqls/pista.sql")
	@Sql("classpath:/resources/sqls/corrida.sql")
	void insertRaceWithInvalidChampTest() {
		Race race = new Race(1, DateUtils.stringToDate("01/01/2000"), speedwayService.findById(1),
				null);
		var exception = assertThrows(IntegrityViolation.class, () -> raceService.insert(race));
		assertEquals("Campeonato inválido", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir corrida com data diferente do campeonato")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	@Sql("classpath:/resources/sqls/pais.sql")
	@Sql("classpath:/resources/sqls/pista.sql")
	@Sql("classpath:/resources/sqls/corrida.sql")
	void insertRaceWithInvalidDateTest() {
		Race race = new Race(1, DateUtils.stringToDate("01/01/1990"), speedwayService.findById(1),
				championshipService.findById(1));
		var exception = assertThrows(IntegrityViolation.class, () -> raceService.insert(race));
		assertEquals("Data informada difere do Campeonato", exception.getMessage());
	}

	@Test
	@DisplayName("Teste listar todos")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	@Sql("classpath:/resources/sqls/pais.sql")
	@Sql("classpath:/resources/sqls/pista.sql")
	@Sql("classpath:/resources/sqls/corrida.sql")
	void listAllTest() {
		assertEquals(3, raceService.listAll().size());
	}

	@Test
	@DisplayName("Teste listar todos sem possuir cadastros existentes")
	void listAllNonExistxTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> raceService.listAll());
		assertEquals("Nenhuma corrida cadastrada", exception.getMessage());
	}

	@Test
	@DisplayName("Teste remover corrida")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	@Sql("classpath:/resources/sqls/pais.sql")
	@Sql("classpath:/resources/sqls/pista.sql")
	@Sql("classpath:/resources/sqls/corrida.sql")
	void removeRaceTest() {
		raceService.delete(1);
		assertEquals(2, raceService.listAll().size());
	}

	@Test
	@DisplayName("Teste remover corrida inexistente")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	@Sql("classpath:/resources/sqls/pais.sql")
	@Sql("classpath:/resources/sqls/pista.sql")
	@Sql("classpath:/resources/sqls/corrida.sql")
	void removeRaceNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> raceService.findById(100));
		assertEquals("Corrida 100 não encontrada", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar corrida")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	@Sql("classpath:/resources/sqls/pais.sql")
	@Sql("classpath:/resources/sqls/pista.sql")
	@Sql("classpath:/resources/sqls/corrida.sql")
	void updatedRaceTest() {
		assertEquals(2000, raceService.findById(1).getDate().getYear());
		Race raceUpdated = new Race(1, DateUtils.stringToDate("01/01/2005"), speedwayService.findById(1),
				championshipService.findById(2));
		raceService.update(raceUpdated);
		assertEquals(2005, raceUpdated.getDate().getYear());
	}

	@Test
	@DisplayName("Teste alterar corrida com data inválida")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	@Sql("classpath:/resources/sqls/pais.sql")
	@Sql("classpath:/resources/sqls/pista.sql")
	@Sql("classpath:/resources/sqls/corrida.sql")
	void updatedRaceWithInvalidDateTest() {
		Race race = new Race(1, DateUtils.stringToDate("01/01/2020"), speedwayService.findById(2),
				championshipService.findById(1));
		var exception = assertThrows(IntegrityViolation.class, () -> raceService.update(race));
		assertEquals("Data informada difere do Campeonato", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca por corrida entre datas")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	@Sql("classpath:/resources/sqls/pais.sql")
	@Sql("classpath:/resources/sqls/pista.sql")
	@Sql("classpath:/resources/sqls/corrida.sql")
	void findRaceByDateTest() {
		assertEquals(2, raceService.findByDateBetweenOrderByDateDesc(DateUtils.stringToDate("01/01/1990"),
				DateUtils.stringToDate("01/01/2007")).size());
	}

	@Test
	@DisplayName("Teste busca por corrida entre datas não encontrada")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	@Sql("classpath:/resources/sqls/pais.sql")
	@Sql("classpath:/resources/sqls/pista.sql")
	@Sql("classpath:/resources/sqls/corrida.sql")
	void findRaceByDateNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> raceService.findByDateBetweenOrderByDateDesc(DateUtils.stringToDate("01/01/1990"),
						DateUtils.stringToDate("01/01/1991")));
		assertEquals("Nenhuma corrida foi encontrada", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca corrida por campeonato")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	@Sql("classpath:/resources/sqls/pais.sql")
	@Sql("classpath:/resources/sqls/pista.sql")
	@Sql("classpath:/resources/sqls/corrida.sql")
	void findRaceByChampTest() {
		assertEquals(1, raceService.findByChampionshipOrderByDate(championshipService.findById(1)).size());
	}

	@Test
	@DisplayName("Teste busca corrida por campeonato não encontrado")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	@Sql("classpath:/resources/sqls/pais.sql")
	@Sql("classpath:/resources/sqls/pista.sql")
	@Sql("classpath:/resources/sqls/corrida.sql")
	void findRaceByChampNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> raceService.findByChampionshipOrderByDate(championshipService.findById(3)));
		assertEquals("Nenhuma corrida foi encontrada", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca corrida por pista")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	@Sql("classpath:/resources/sqls/pais.sql")
	@Sql("classpath:/resources/sqls/pista.sql")
	@Sql("classpath:/resources/sqls/corrida.sql")
	void findRaceBySpeedwayTest() {
		assertEquals(1, raceService.findBySpeedwayOrderByDate(speedwayService.findById(2)).size());
	}

	@Test
	@DisplayName("Teste busca corrida por pista não encontrada")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	@Sql("classpath:/resources/sqls/pais.sql")
	@Sql("classpath:/resources/sqls/pista.sql")
	@Sql("classpath:/resources/sqls/corrida.sql")
	void findRaceBySpeedwayNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> raceService.findBySpeedwayOrderByDate(speedwayService.findById(3)));
		assertEquals("Nenhuma corrida foi encontrada", exception.getMessage());
	}

}
