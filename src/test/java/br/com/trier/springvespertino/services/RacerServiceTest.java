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
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	void findByIdTest() {
		var racer = racerService.findById(1);
		assertNotNull(racer);
		assertEquals(1, racer.getId());
		assertEquals("Piloto1", racer.getName());
		assertEquals(equipService.findById(1), racer.getEquip());
		assertEquals(countryService.findById(1), racer.getCountry());
	}

	@Test
	@DisplayName("Teste buscar por ID inexistente")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	void ListByIdNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> racerService.findById(10));
		assertEquals("Piloto 10 não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir piloto")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	void insertRacerTest() {
		Racer racer = new Racer(1, "insert", countryService.findById(1), equipService.findById(1));
		racerService.insert(racer);
		assertEquals(1, racer.getId());
		assertEquals("insert", racer.getName());
		assertEquals(equipService.findById(1), racer.getEquip());
		assertEquals(countryService.findById(1), racer.getCountry());
	}

	@Test
	@DisplayName("Teste listar todos")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	void listAllTest() {
		assertEquals(5, racerService.listAll().size());
	}

	@Test
	@DisplayName("Teste listar todos sem possuir pilotos cadastradas")
	void listAllNonExistxTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> racerService.listAll());
		assertEquals("Nenhum piloto cadastrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar piloto")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	void updatedRacerTest() {
		assertEquals("Piloto1", racerService.findById(1).getName());
		Racer racerUpdated = new Racer(1, "update", countryService.findById(1), equipService.findById(1));
		racerService.update(racerUpdated);
		assertEquals("update", racerUpdated.getName());
	}

	@Test
	@DisplayName("Teste remover piloto")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	void removeRacerTest() {
		racerService.delete(1);
		assertEquals(4, racerService.listAll().size());
	}

	@Test
	@DisplayName("Teste remover piloto inexistente")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	void removeSpeedwayNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> racerService.findById(10));
		assertEquals("Piloto 10 não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar por nome")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	void findByNameTest() {
		assertEquals(5, racerService.findByNameStartsWithIgnoreCase("pi").size());
		assertEquals(1, racerService.findByNameStartsWithIgnoreCase("piloto1").size());
	}

	@Test
	@DisplayName("Teste buscar por nome inexistente")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	void findByNameNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> racerService.findByNameStartsWithIgnoreCase("jancskj"));
		assertEquals("Nenhum piloto encontrado com o nome jancskj", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca por equipe")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	void findByEquipTest() {
		assertEquals(3, racerService.findByEquipOrderByName(equipService.findById(1)).size());
	}

	@Test
	@DisplayName("Teste busca por equipe não encontrada")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	void findByEquipNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> racerService.findByEquipOrderByName(equipService.findById(3)));
		assertEquals("Nenhum piloto foi encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca piloto por país")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	void findRacerByCountryTest() {
		assertEquals(3, racerService.findByCountry(countryService.findById(1)).size());
	}

	@Test
	@DisplayName("Teste busca piloto por país não encontrado")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	@Sql({ "classpath:/resources/sqls/piloto.sql" })
	void findRacerByCountryNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> racerService.findByCountry(countryService.findById(3)));
		assertEquals("Nenhum piloto foi encontrado", exception.getMessage());
	}

}
