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
import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
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
		var exception = assertThrows(ObjectNotFound.class, () -> countryService.findById(100));
		assertEquals("País 100 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir país")
	void insertCountryTest() {
		Country pais = new Country(null, "insert");
		countryService.insert(pais);
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
	@DisplayName("Teste listar sem paises cadastrados")
	void listAllNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> countryService.listAll());
		assertEquals("Nenhum país cadastrado", exception.getMessage());
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
	@DisplayName("Teste alterar país que já existe")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void updateCountryExistsTest() {
		var pais = countryService.findById(1);
		assertEquals("Brasil", pais.getName());
		Country paisUpdate = new Country(1, "Japão");
		var exception = assertThrows(IntegrityViolation.class, () -> countryService.insert(paisUpdate));
		assertEquals("País já existente", exception.getMessage());
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
		var exception = assertThrows(ObjectNotFound.class, () -> countryService.findById(100));
		assertEquals("País 100 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste busca por nome")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByNameTest () {
		var pais = countryService.findByName("Brasil");
		assertNotNull(pais);
		assertEquals("Brasil", pais.get(0).getName());
	}
	
	@Test
	@DisplayName("Teste busca por nome inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByNameNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> countryService.findByName("asddsf"));
		assertEquals("Nenhum país encontrado com o nome asddsf", exception.getMessage());
	}
}
