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
import br.com.trier.springvespertino.models.Equip;
import jakarta.transaction.Transactional;

@Transactional
public class EquipServiceTest extends BaseTests{
	
	@Autowired
	EquipService equipService;
	
	@Test
	@DisplayName("Teste busca por ID")
	@Sql("classpath:/resources/sqls/equipe.sql")
	void findByIdTest() {
		var equipe = equipService.findById(1);
		assertNotNull(equipe);
		assertEquals(1, equipe.getId());
		assertEquals("Redbull", equipe.getName());
	}

	@Test
	@DisplayName("Teste busca por ID inexistente")
	@Sql("classpath:/resources/sqls/equipe.sql")
	void findByIdNonExistsTest() {
		var equipe = equipService.findById(10);
		assertNull(equipe);
	}

	@Test
	@DisplayName("Teste inserir equipe")
	void insertEquipTest() {
		Equip equip = new Equip(null, "insert");
		equipService.insert(equip);
		assertEquals(1, equip.getId());
		assertEquals("insert", equip.getName());
	}
	
	@Test
	@DisplayName("Teste listar todas as equipes")
	@Sql("classpath:/resources/sqls/equipe.sql")
	void listAllTest() {
		List<Equip> lista = equipService.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste alterar equipe")
	@Sql("classpath:/resources/sqls/equipe.sql")
	void updateEquipTest() {
		var equipe = equipService.findById(1);
		assertEquals("Redbull", equipe.getName());
		Equip equipeUpdate = new Equip(1, "update");
		equipService.update(equipeUpdate);
		equipe = equipService.findById(1);
		assertEquals("update", equipe.getName());
	}
	
	@Test
	@DisplayName("Teste remover equipe")
	@Sql("classpath:/resources/sqls/equipe.sql")
	void deleteEquipTest() {
		equipService.delete(1);
		List<Equip> lista = equipService.listAll();
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste remover equipe inexistente")
	@Sql("classpath:/resources/sqls/equipe.sql")
	void deleteEquipNonExistsTest() {
		equipService.delete(3);
		List<Equip> lista = equipService.listAll();
		assertEquals(2, lista.size());
		assertEquals(2, lista.get(0).getId());
	}
	
	@Test
	@DisplayName("Teste busca por nome")
	@Sql("classpath:/resources/sqls/equipe.sql")
	void findByNameTest() {
		var equipe = equipService.findByName("Ferrari");
		assertNotNull(equipe);
		assertEquals("Ferrari", equipe.get(0).getName());
	}
	
	@Test
	@DisplayName("Teste busca por nome inexistente")
	@Sql("classpath:/resources/sqls/equipe.sql")
	void findByNameNonExistsTest() {
		List<Equip> equipe = equipService.findByName("asnkjvfd");
		assertEquals(0, equipe.size());
	}
}
