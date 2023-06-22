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
import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.models.Country;
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
		var country = countryService.findById(1);
		assertNotNull(speedway);
		assertEquals(1, speedway.getId());
		assertEquals("Pista1", speedway.getName());
		assertEquals(5000, speedway.getSize());
		assertEquals(country, speedway.getCountry());
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
		var country = countryService.findById(1);
		Speedway speedway = new Speedway(1, "insert", 5000, country);
		speedwayService.insert(speedway);
		assertEquals(1, speedway.getId());
		assertEquals("insert", speedway.getName());
		assertEquals(5000, speedway.getSize());
		assertEquals(country, speedway.getCountry());
	}

	@Test
	@DisplayName("Teste inserir pista com tamanho inválido")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void insertSpeedwayWithInvalidSizeTest() {
		var country = countryService.findById(1);
		Speedway speedway = new Speedway(1, "insert", 0, country);
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
	@DisplayName("Teste listar todos sem possuir pistas cadastradas")
	void listAllNonExistxTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> speedwayService.listAll());
		assertEquals("Nenhuma pista cadastrada", exception.getMessage());
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
		var exception = assertThrows(ObjectNotFound.class, () -> speedwayService.findById(5));
		assertEquals("Pista 5 não encontrada", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar pista")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void updatedSpeedwaysTest() {
		var speedway = speedwayService.findById(1);
		assertEquals("Pista1", speedway.getName());
		speedway.setName("update");
		speedwayService.update(speedway);
		var speedwayUpdated = speedwayService.findById(1);
		assertEquals("update", speedwayUpdated.getName());
	}

	@Test
	@DisplayName("Teste buscar pista por nome")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void findByNameTest() {
		var speedway = speedwayService.findByNameStartsWithIgnoreCase("pist");
		assertNotNull(speedway);
		assertEquals(3, speedway.size());
		var speedway1 = speedwayService.findByNameStartsWithIgnoreCase("pista1");
		assertEquals(1, speedway1.size());
	}

	@Test
	@DisplayName("Teste buscar por nome inexistente")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void findByNameNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> speedwayService.findByNameStartsWithIgnoreCase("fdanajks"));
		assertEquals("Nenhuma pista encontrada com o nome fdanajks", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca por tamanho")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void findBySizeBetweenTest() {
		List<Speedway> lista = speedwayService.findBySizeBetween(5000, 6000);
		assertNotNull(lista);
		assertEquals(2, lista.size());
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
		var country = countryService.findById(2);
		var speedway = speedwayService.findByCountryOrderBySizeDesc(country);
		assertEquals(2, speedway.size());
	}

	@Test
	@DisplayName("Teste busca pista por país não encontrado")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/pista.sql" })
	void findSpeedwayByCountryNotFoundTest() {
		var country = countryService.findById(3);
		var exception = assertThrows(ObjectNotFound.class, () -> speedwayService.findByCountryOrderBySizeDesc(country));
		assertEquals("Nenhuma pista foi encontrada", exception.getMessage());
	}
}