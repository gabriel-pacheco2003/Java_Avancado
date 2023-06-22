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
import br.com.trier.springvespertino.models.Speedway;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class RaceServiceTest extends BaseTests{

	@Autowired
	RaceService raceService;
	
	@Autowired
	ChampionshipService championshipService;
	
	@Autowired
	SpeedwayService speedwayService;
	
	/*
	 * @Test
	 * 
	 * @DisplayName("Teste buscar por ID")
	 * 
	 * @Sql({ "classpath:/resources/sqls/tabelas.sql" }) void findByIdTest() { var
	 * race = raceService.findById(1); var champ = championshipService.findById(1);
	 * var speedway = speedwayService.findById(1); assertNotNull(race);
	 * assertEquals(1, race.getId()); assertEquals("2000-01-01T10:00:00Z",
	 * race.getDate()); assertEquals(speedway, race.getSpeedway());
	 * assertEquals(champ, race.getChampionship()); }
	 * 
	 * @Test
	 * 
	 * @DisplayName("Teste buscar por ID inexistente")
	 * 
	 * @Sql({ "classpath:/resources/sqls/campeonato.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/pista.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/corrida.sql" }) void
	 * ListByIdNonExistsTest() { var exception = assertThrows(ObjectNotFound.class,
	 * () -> raceService.findById(5)); assertEquals("Pista 5 não encontrada",
	 * exception.getMessage()); }
	 * 
	 * @Test
	 * 
	 * @DisplayName("Teste inserir pista")
	 * 
	 * @Sql({ "classpath:/resources/sqls/campeonato.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/pista.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/corrida.sql" }) void insertSpeedwayTest() {
	 * var champ = championshipService.findById(1); Race race = new Race(1,
	 * "insert", 5000, champ); raceService.insert(race); assertEquals(1,
	 * race.getId()); assertEquals("insert", race.getName()); assertEquals(5000,
	 * race.getSize()); assertEquals(champ, race.getCountry()); }
	 * 
	 * @Test
	 * 
	 * @DisplayName("Teste inserir pista com tamanho inválido")
	 * 
	 * @Sql({ "classpath:/resources/sqls/campeonato.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/pista.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/corrida.sql" }) void
	 * insertSpeedwayWithInvalidSizeTest() { var champ =
	 * championshipService.findById(1); Race speedway = new Race(1, "insert", 0,
	 * champ); var exception = assertThrows(IntegrityViolation.class, () ->
	 * raceService.insert(speedway)); assertEquals("Tamanho da pista inválido",
	 * exception.getMessage()); }
	 * 
	 * @Test
	 * 
	 * @DisplayName("Teste listar todos")
	 * 
	 * @Sql({ "classpath:/resources/sqls/campeonato.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/pista.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/corrida.sql" }) void listAllTest() { var
	 * speedway = raceService.listAll(); assertEquals(3, speedway.size()); }
	 * 
	 * @Test
	 * 
	 * @DisplayName("Teste listar todos sem possuir pistas cadastradas")
	 * 
	 * @Sql({ "classpath:/resources/sqls/campeonato.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/pista.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/corrida.sql" }) void listAllNonExistxTest()
	 * { var exception = assertThrows(ObjectNotFound.class, () ->
	 * raceService.listAll()); assertEquals("Nenhuma pista cadastrada",
	 * exception.getMessage()); }
	 * 
	 * @Test
	 * 
	 * @DisplayName("Teste remover pista")
	 * 
	 * @Sql({ "classpath:/resources/sqls/campeonato.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/pista.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/corrida.sql" }) void removeSpeedwayTest() {
	 * raceService.delete(1); assertEquals(2, raceService.listAll().size()); }
	 * 
	 * @Test
	 * 
	 * @DisplayName("Teste remover pista inexistente")
	 * 
	 * @Sql({ "classpath:/resources/sqls/campeonato.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/equipe.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/corrida.sql" }) void
	 * removeSpeedwayNonExistsTest() { var exception =
	 * assertThrows(ObjectNotFound.class, () -> raceService.findById(5));
	 * assertEquals("Pista 5 não encontrada", exception.getMessage()); }
	 * 
	 * @Test
	 * 
	 * @DisplayName("Teste alterar pista")
	 * 
	 * @Sql({ "classpath:/resources/sqls/campeonato.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/pista.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/corrida.sql" }) void updatedSpeedwaysTest()
	 * { var speedway = raceService.findById(1); assertEquals("Pista1",
	 * speedway.getName()); speedway.setName("update");
	 * raceService.update(speedway); var speedwayUpdated = raceService.findById(1);
	 * assertEquals("update", speedwayUpdated.getName()); }
	 * 
	 * @Test
	 * 
	 * @DisplayName("Teste buscar pista por nome")
	 * 
	 * @Sql({ "classpath:/resources/sqls/campeonato.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/pista.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/corrida.sql" }) void findByNameTest() { var
	 * speedway = raceService.findByNameStartsWithIgnoreCase("pist");
	 * assertNotNull(speedway); assertEquals(3, speedway.size()); var speedway1 =
	 * raceService.findByNameStartsWithIgnoreCase("pista1"); assertEquals(1,
	 * speedway1.size()); }
	 * 
	 * @Test
	 * 
	 * @DisplayName("Teste buscar por nome inexistente")
	 * 
	 * @Sql({ "classpath:/resources/sqls/campeonato.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/pista.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/corrida.sql" }) void
	 * findByNameNonExistsTest() { var exception =
	 * assertThrows(ObjectNotFound.class, () ->
	 * raceService.findByNameStartsWithIgnoreCase("fdanajks"));
	 * assertEquals("Nenhuma pista encontrada com o nome fdanajks",
	 * exception.getMessage()); }
	 * 
	 * @Test
	 * 
	 * @DisplayName("Teste busca por tamanho")
	 * 
	 * @Sql({ "classpath:/resources/sqls/campeonato.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/pista.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/corrida.sql" }) void
	 * findBySizeBetweenTest() { List<Race> lista =
	 * raceService.findByDateBetweenOrderByDateDesc(5000, 6000);
	 * assertNotNull(lista); assertEquals(2, lista.size()); }
	 * 
	 * @Test
	 * 
	 * @DisplayName("Teste busca por tamanho não encontrado")
	 * 
	 * @Sql({ "classpath:/resources/sqls/campeonato.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/pista.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/corrida.sql" }) void
	 * findBySizeBetweenNotFoundTest() { var exception =
	 * assertThrows(ObjectNotFound.class, () -> raceService.findBySizeBetween(1000,
	 * 3000)); assertEquals("Nenhuma pista foi encontrada", exception.getMessage());
	 * }
	 * 
	 * @Test
	 * 
	 * @DisplayName("Teste busca pista por país")
	 * 
	 * @Sql({ "classpath:/resources/sqls/campeonato.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/pista.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/corrida.sql" }) void
	 * findSpeedwayByCountryTest() { var champ = championshipService.findById(2);
	 * var speedway = raceService.findByCountryOrderBySizeDesc(champ);
	 * assertEquals(2, speedway.size()); }
	 * 
	 * @Test
	 * 
	 * @DisplayName("Teste busca pista por país não encontrado")
	 * 
	 * @Sql({ "classpath:/resources/sqls/campeonato.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/pista.sql" })
	 * 
	 * @Sql({ "classpath:/resources/sqls/corrida.sql" }) void
	 * findSpeedwayByCountryNotFoundTest() { var champ =
	 * championshipService.findById(3); var exception =
	 * assertThrows(ObjectNotFound.class, () ->
	 * raceService.findByCountryOrderBySizeDesc(champ));
	 * assertEquals("Nenhuma pista foi encontrada", exception.getMessage()); }
	 */
}
