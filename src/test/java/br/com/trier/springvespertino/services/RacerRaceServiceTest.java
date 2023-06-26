package br.com.trier.springvespertino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.RacerRace;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class RacerRaceServiceTest extends BaseTests {

	@Autowired
	RacerRaceService racerRaceService;

	@Autowired
	RacerService racerService;

	@Autowired
	RaceService raceService;

	@Test
	@DisplayName("Teste buscar por ID")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	@Sql({ "classpath:/resources/sqls/piloto_corrida.sql" })
	void findByIdTest() {
		var racerRace = racerRaceService.findById(1);
		assertEquals(1, racerRace.getId());
		assertEquals(racerService.findById(1), racerRace.getRacer());
		assertEquals(raceService.findById(1), racerRace.getRace());
		assertEquals(1, racerRace.getRank());
	}

	@Test
	@DisplayName("Teste buscar por ID inexistente")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	@Sql({ "classpath:/resources/sqls/piloto_corrida.sql" })
	void ListByIdNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> racerRaceService.findById(10));
		assertEquals("Piloto/Corrida 10 não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir Piloto/Corrida")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	@Sql({ "classpath:/resources/sqls/piloto_corrida.sql" })
		void insertRacerRaceTest() {
		RacerRace racerRace = new RacerRace(1, racerService.findById(4), raceService.findById(1), 4);
		racerRaceService.insert(racerRace);
		assertEquals(1, racerRace.getId());
		assertEquals(racerService.findById(4), racerRace.getRacer());
		assertEquals(raceService.findById(1), racerRace.getRace());
		assertEquals(4, racerRace.getRank());
	}

	@Test
	@DisplayName("Teste listar todos")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	@Sql({ "classpath:/resources/sqls/piloto_corrida.sql" })
		void listAllTest() {
		assertEquals(9, racerRaceService.listAll().size());
	}

	@Test
	@DisplayName("Teste listar todos sem cadastros existentes")
	void listAllNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> racerRaceService.listAll());
		assertEquals("Nenhum Piloto/Corrida cadastrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar Piloto/Corrida")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	@Sql({ "classpath:/resources/sqls/piloto_corrida.sql" })
		void updateRacerRaceTest() {
		assertEquals(1, racerRaceService.findById(1).getRank());
		RacerRace racerRaceUpdated = new RacerRace(1, racerService.findById(1), raceService.findById(1), 4);
		racerRaceService.update(racerRaceUpdated);
		assertEquals(4, racerRaceService.findById(1).getRank());
	}

	@Test
	@DisplayName("Teste remover Piloto/Corrida")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	@Sql({ "classpath:/resources/sqls/piloto_corrida.sql" })
		void deleteRacerRaceTest() {
		racerRaceService.delete(1);
		assertEquals(8, racerRaceService.listAll().size());
	}

	@Test
	@DisplayName("Teste remover Piloto/Corrida inexistente")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	@Sql({ "classpath:/resources/sqls/piloto_corrida.sql" })
	void deleteRacerRaceNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> racerRaceService.delete(10));
		assertEquals("Piloto/Corrida 10 não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca por colocação")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	@Sql({ "classpath:/resources/sqls/piloto_corrida.sql" })
		void findByRankTest() {
		assertEquals(3, racerRaceService.findByRank(1).size());
	}

	@Test
	@DisplayName("Teste busca por colocação não encontrada")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	@Sql({ "classpath:/resources/sqls/piloto_corrida.sql" })
		void findByRankNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> racerRaceService.findByRank(10));
		assertEquals("Nenhum Piloto/Corrida encontrado com a colocação 10", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca Piloto/Corrida por piloto")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	@Sql({ "classpath:/resources/sqls/piloto_corrida.sql" })
		void findByRacerTest() {
		assertEquals(3, racerRaceService.findByRacerOrderByRank(racerService.findById(1)).size());
	}

	@Test
	@DisplayName("Teste busca Piloto/Corrida por piloto não encontrado")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	@Sql({ "classpath:/resources/sqls/piloto_corrida.sql" })
		void findByRacerNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> racerRaceService.findByRacerOrderByRank(null));
		assertEquals("Nenhum piloto encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca Piloto/Corrida por corrida")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	@Sql({ "classpath:/resources/sqls/piloto_corrida.sql" })
		void findByRaceTest() {
		assertEquals(3, racerRaceService.findByRaceOrderByRank(raceService.findById(1)).size());
	}

	@Test
	@DisplayName("Teste busca Piloto/Corrida por corrida não encontrado")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	@Sql({ "classpath:/resources/sqls/corrida.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	@Sql({ "classpath:/resources/sqls/piloto_corrida.sql" })
		void findByRaceNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> racerRaceService.findByRaceOrderByRank(null));
		assertEquals("Nenhuma corrida encontrada", exception.getMessage());
	}

}
