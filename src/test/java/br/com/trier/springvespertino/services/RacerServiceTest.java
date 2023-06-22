package br.com.trier.springvespertino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.Racer;

import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class RacerServiceTest extends BaseTests {

	@Autowired
	RacerService racerService;

	@Autowired
	CountryService countryService;

	@Autowired
	EquipService equipService;

	@Test
	@DisplayName("Teste buscar por ID")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void findByIdTest() {
		var racer = racerService.findById(1);
		var country = countryService.findById(1);
		var equip = equipService.findById(1);
		assertNotNull(racer);
		assertEquals(1, racer.getId());
		assertEquals("Piloto1", racer.getName());
		assertEquals(equip, racer.getEquip());
		assertEquals(country, racer.getCountry());
	}

	@Test
	@DisplayName("Teste buscar por ID inexistente")
	void ListByIdNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> racerService.findById(1));
		assertEquals("Piloto 1 não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir piloto")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void insertRacerTest() {
		var country = countryService.findById(1);
		var equip = equipService.findById(1);
		Racer racer = new Racer(1, "insert", country, equip);
		racerService.insert(racer);
		assertEquals(1, racer.getId());
		assertEquals("insert", racer.getName());
		assertEquals(equip, racer.getEquip());
		assertEquals(country, racer.getCountry());
	}

	@Test
	@DisplayName("Teste listar todos")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void listAllTest() {
		var racer = racerService.listAll();
		assertEquals(5, racer.size());
	}

	@Test
	@DisplayName("Teste listar todos sem possuir pilotos cadastradas")
	void listAllNonExistxTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> racerService.listAll());
		assertEquals("Nenhum piloto cadastrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste remover piloto")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void removeRacerTest() {
		racerService.delete(1);
		assertEquals(4, racerService.listAll().size());
	}

	@Test
	@DisplayName("Teste remover piloto inexistente")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void removeSpeedwayNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> racerService.findById(10));
		assertEquals("Piloto 10 não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar piloto")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void updatedRacerTest() {
		var racer = racerService.findById(1);
		assertEquals("Piloto1", racer.getName());
		racer.setName("update");
		racerService.update(racer);
		var speedwayUpdated = racerService.findById(1);
		assertEquals("update", speedwayUpdated.getName());
	}

	@Test
	@DisplayName("Teste buscar por nome")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void findByNameTest() {
		var racer = racerService.findByNameStartsWithIgnoreCase("pi");
		assertNotNull(racer);
		assertEquals(5, racer.size());
		var racer1 = racerService.findByNameStartsWithIgnoreCase("piloto1");
		assertEquals(1, racer1.size());
	}

	@Test
	@DisplayName("Teste buscar por nome inexistente")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void findByNameNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> racerService.findByNameStartsWithIgnoreCase("jancskj"));
		assertEquals("Nenhum piloto encontrado com o nome jancskj", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca por equipe")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void findByEquipTest() {
		var equip = equipService.findById(1);
		var racer = racerService.findByEquipOrderByName(equip);
		assertEquals(3, racer.size());
	}

	@Test
	@DisplayName("Teste busca por equipe não encontrada")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void findByEquipNotFoundTest() {
		var equip = equipService.findById(3);
		var exception = assertThrows(ObjectNotFound.class, () -> racerService.findByEquipOrderByName(equip));
		assertEquals("Nenhum piloto foi encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca piloto por país")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void findRacerByCountryTest() {
		var country = countryService.findById(1);
		var racer = racerService.findByCountry(country);
		assertEquals(3, racer.size());
	}

	@Test
	@DisplayName("Teste busca piloto por país não encontrado")
	@Sql({ "classpath:/resources/sqls/tabelas.sql" })
	void findRacerByCountryNotFoundTest() {
		var country = countryService.findById(3);
		var exception = assertThrows(ObjectNotFound.class, () -> racerService.findByCountry(country));
		assertEquals("Nenhum piloto foi encontrado", exception.getMessage());
	}

}
