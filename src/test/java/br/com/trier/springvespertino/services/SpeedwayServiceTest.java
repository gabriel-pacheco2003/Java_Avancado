package br.com.trier.springvespertino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
public class SpeedwayServiceTest extends BaseTests {

	@Autowired
	SpeedwayService speedwayService;

	@Autowired
	CountryService countryService;

	@Test
	@DisplayName("Teste buscar por ID")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void findByIdTest() {
		var speedway = speedwayService.findById(1);
		assertNotNull(speedway);
		assertEquals(1, speedway.getId());
		assertEquals("Pista1", speedway.getName());
		assertEquals(5000, speedway.getSize());
		assertEquals(countryService.findById(1), speedway.getCountry());
	}

	@Test
	@DisplayName("Teste buscar por ID inexistente")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void ListByIdNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> speedwayService.findById(5));
		assertEquals("Pista 5 não encontrada", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir pista")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void insertSpeedwayTest() {
		Speedway speedway = new Speedway(1, "insert", 5000, countryService.findById(1));
		speedwayService.insert(speedway);
		assertEquals(1, speedway.getId());
		assertEquals("insert", speedway.getName());
		assertEquals(5000, speedway.getSize());
		assertEquals(countryService.findById(1), speedway.getCountry());
	}

	@Test
	@DisplayName("Teste inserir pista com tamanho inválido")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void insertSpeedwayWithInvalidSizeTest() {
		Speedway speedway = new Speedway(1, "insert", 0, countryService.findById(1));
		var exception = assertThrows(IntegrityViolation.class, () -> speedwayService.insert(speedway));
		assertEquals("Tamanho da pista inválido", exception.getMessage());
	}

	@Test
	@DisplayName("Teste listar todos")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void listAllTest() {
		var speedway = speedwayService.listAll();
		assertEquals(3, speedway.size());
	}

	@Test
	@DisplayName("Teste listar todos sem cadastros de pistas")
	void listAllNonExistxTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> speedwayService.listAll());
		assertEquals("Nenhuma pista cadastrada", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar pista")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void updatedSpeedwayTest() {
		assertEquals("Pista1", speedwayService.findById(1).getName());
		Speedway speedway = new Speedway(1, "update", 1000, countryService.findById(1));
		speedwayService.update(speedway);
		assertEquals("update", speedwayService.findById(1).getName());

	}

	@Test
	@DisplayName("Teste alterar pista com tamanho inválido")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void updatedSpeedwayWithInvalidSizeTest() {
		Speedway speedway = new Speedway(1, "update", 0, countryService.findById(1));
		var exception = assertThrows(IntegrityViolation.class, () -> speedwayService.insert(speedway));
		assertEquals("Tamanho da pista inválido", exception.getMessage());
	}

	@Test
	@DisplayName("Teste remover pista")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void removeSpeedwayTest() {
		speedwayService.delete(1);
		assertEquals(2, speedwayService.listAll().size());
	}

	@Test
	@DisplayName("Teste remover pista inexistente")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void removeSpeedwayNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> speedwayService.delete(5));
		assertEquals("Pista 5 não encontrada", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca pista por nome")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void findByNameTest() {
		assertEquals(3, speedwayService.findByNameStartsWithIgnoreCase("pist").size());
		assertEquals(1, speedwayService.findByNameStartsWithIgnoreCase("pista1").size());
	}

	@Test
	@DisplayName("Teste busca por nome inexistente")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void findByNameNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> speedwayService.findByNameStartsWithIgnoreCase("fdanajks"));
		assertEquals("Nenhuma pista encontrada com o nome fdanajks", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca por tamanho entre")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void findBySizeBetweenTest() {
		assertEquals(2, speedwayService.findBySizeBetween(5000, 6000).size());
	}

	@Test
	@DisplayName("Teste busca por tamanho não encontrado")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void findBySizeBetweenNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> speedwayService.findBySizeBetween(1000, 3000));
		assertEquals("Nenhuma pista foi encontrada", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca pista por país")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void findSpeedwayByCountryTest() {
		assertEquals(2, speedwayService.findByCountryOrderBySizeDesc(countryService.findById(2)).size());
	}

	@Test
	@DisplayName("Teste busca pista por país não encontrado")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void findSpeedwayByCountryNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> speedwayService.findByCountryOrderBySizeDesc(countryService.findById(3)));
		assertEquals("Nenhuma pista foi encontrada", exception.getMessage());
	}
}