package br.com.trier.springvespertino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.Championship;
import jakarta.transaction.Transactional;

@Transactional
public class ChampionshipServiceTest extends BaseTests{
	
	@Autowired
	ChampionshipService champService;
	
	@Test
	@DisplayName("Teste busca por ID")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	void findByIdTest() {
		var campeonato = champService.findById(1);
		assertNotNull(campeonato);
		assertEquals(1, campeonato.getId());
		assertEquals("Camp1", campeonato.getDescription());
	}

	@Test
	@DisplayName("Teste busca por ID inexistente")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	void findByIdNonExistsTest() {
		var campeonato = champService.findById(10);
		assertNull(campeonato);
	}

	@Test
	@DisplayName("Teste inserir campeonato")
	void insertChampionshipTest() {
		Championship campeonato = new Championship(null, "insert", null);
		champService.insert(campeonato);
		assertEquals(1, campeonato.getId());
		assertEquals("insert", campeonato.getDescription());
	}
	
	@Test
	@DisplayName("Teste listar todos os campeonatos")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	void listAllTest() {
		List<Championship> lista = champService.listAll();
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName("Teste alterar campeonato")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	void updateChampionshipTest() {
		var campeonato = champService.findById(1);
		assertEquals("Camp1", campeonato.getDescription());
		Championship campeonatoUpdate = new Championship(1, "update", 2000);
		champService.update(campeonatoUpdate);
		campeonato = champService.findById(1);
		assertEquals("update", campeonato.getDescription());
	}
	
	@Test
	@DisplayName("Teste remover campeonato")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	void deleteChampionshipTest() {
		champService.delete(1);
		List<Championship> lista = champService.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste remover campeonato inexistente")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	void deleteChampionshipNonExistsTest() {
		champService.delete(10);
		List<Championship> lista = champService.listAll();
		assertEquals(3, lista.size());
		assertEquals(1, lista.get(0).getId());
	}
	
	@Test
	@DisplayName("Teste busca por descrição")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	void findByDescriptionTest() {
		var campeonato = champService.findByDescription("Camp1");
		assertNotNull(campeonato);
		assertEquals("Camp1", campeonato.get(0).getDescription());
	}
	
	@Test
	@DisplayName("Teste busca por descrição inexistente")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	void findByDescriptionNonExistsTest() {
		List<Championship> campeonato = champService.findByDescription("asnkjvfd");
		assertEquals(0, campeonato.size());
	}
	
	@Test
	@DisplayName("Teste busca por ano")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	void findByYearBetweenTest() {
		List<Championship> lista = champService.findByYearBetweenOrderByYearAsc(2005, 2020);
		assertNotNull(lista);
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste busca por ano não encontrada")
	@Sql("classpath:/resources/sqls/campeonato.sql")
	void findByYearBetweenNotfoundTest() {
		List<Championship> lista = champService.findByYearBetweenOrderByYearAsc(0, 1000);
		assertEquals(0, lista.size());
	}
}

