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
import br.com.trier.springvespertino.models.Country;
import jakarta.transaction.Transactional;

@Transactional
public class CountryServiceTest extends BaseTests{
	
	@Autowired
	CountryService countryService;
	
	@Test
	@DisplayName("Teste busca por ID")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByIdTest() {
		var pais = countryService.findById(1);
		assertNotNull(pais);
		assertEquals(1, pais.getId());
		assertEquals("Brasil", pais.getName());
	}

	@Test
	@DisplayName("Teste busca por ID inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByIdNonExistsTest() {
		var pais = countryService.findById(10);
		assertNull(pais);
	}
	
	@Test
	@DisplayName("Teste inserir país")
	void insertCountryTest() {
		Country pais = new Country(null, "insert");
		assertEquals("insert", pais.getName());
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void listAllTest() {
		var pais = countryService.listAll();
		assertEquals(3, pais.size());
	}
	
	@Test
	@DisplayName("Teste alterar país")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void updateCountryTest() {
		var pais = countryService.findById(1);
		assertEquals("Brasil", pais.getName());
		Country paisUpdate = new Country(1, "update");
		countryService.update(paisUpdate);
		pais = countryService.findById(1);
		assertEquals("update", pais.getName());
	}
	
	@Test
	@DisplayName("Teste remover país")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void deleteCountryTest() {
		countryService.delete(2);
		List<Country> pais = countryService.listAll();
		assertEquals(2, pais.size());
	}
	
	@Test
	@DisplayName("Teste remover pais inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void deleteCountryNonExists() {
		countryService.delete(6);
		List<Country> pais = countryService.listAll();
		assertEquals(3, pais.size());
		assertEquals(1, pais.get(0).getId());
	}
}
