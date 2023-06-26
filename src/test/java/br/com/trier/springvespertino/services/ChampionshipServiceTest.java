package br.com.trier.springvespertino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class ChampionshipServiceTest extends BaseTests {

	@Autowired
	ChampionshipService champService;

	@Test
	@DisplayName("Teste busca por ID")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	void findByIdTest() {
		var campeonato = champService.findById(1);
		assertEquals(1, campeonato.getId());
		assertEquals("Camp1", campeonato.getDescription());
	}

	@Test
	@DisplayName("Teste busca por ID inexistente")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	void findByIdNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> champService.findById(5));
		assertEquals("Campeonato inexistente", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir campeonato")
	void insertChampionshipTest() {
		Championship campeonato = new Championship(null, "insert", 2000);
		champService.insert(campeonato);
		assertEquals(1, campeonato.getId());
		assertEquals("insert", campeonato.getDescription());
		assertEquals(2000, campeonato.getYear());
	}

	@Test
	@DisplayName("Teste listar todos")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	void listAllTest() {
		assertEquals(3, champService.listAll().size());
	}

	@Test
	@DisplayName("Teste listar todos sem cadastros existentes")
	void listAllNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> champService.listAll());
		assertEquals("Nenhum campeonato cadastrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar campeonato")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	void updateChampionshipTest() {
		assertEquals("Camp1", champService.findById(1).getDescription());
		Championship campeonatoUpdate = new Championship(1, "update", 2000);
		champService.update(campeonatoUpdate);
		assertEquals("update", champService.findById(1).getDescription());
	}

	@Test
	@DisplayName("Teste alterar campeonato com o ano inválido")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	void updateChampionshipInvalidYearTest() {
		assertEquals("Camp1", champService.findById(1).getDescription());
		Championship champUpdate = new Championship(1, "update", 2025);
		var exception = assertThrows(IntegrityViolation.class, () -> champService.update(champUpdate));
		assertEquals("Ano inválido", exception.getMessage());
	}

	@Test
	@DisplayName("Teste remover campeonato")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	void deleteChampionshipTest() {
		champService.delete(1);
		assertEquals(2, champService.listAll().size());
	}

	@Test
	@DisplayName("Teste remover campeonato inexistente")
	void deleteChampionshipNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> champService.findById(5));
		assertEquals("Campeonato inexistente", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca por descrição")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	void findByDescriptionTest() {
		assertEquals("Camp1", champService.findByDescriptionIgnoreCase("Camp1").get(0).getDescription());
	}

	@Test
	@DisplayName("Teste busca por descrição inexistente")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	void findByDescriptionNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> champService.findByDescriptionIgnoreCase("abc"));
		assertEquals("Nenhum campeonato foi encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca por ano")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	void findByYearBetweenTest() {
		assertEquals(2, champService.findByYearBetweenOrderByYearAsc(2005, 2020).size());
	}

	@Test
	@DisplayName("Teste busca campeonato por ano não encontrado")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	void findByYearBetweenNotfoundTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> champService.findByYearBetweenOrderByYearAsc(1000, 1100));
		assertEquals("Nenhum campeonato foi encontrado", exception.getMessage());
	}
}
