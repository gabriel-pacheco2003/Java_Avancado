package br.com.trier.springvespertino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.Equip;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
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
		assertEquals(1, equipe.getId());
		assertEquals("Redbull", equipe.getName());
	}

	@Test
	@DisplayName("Teste busca por ID inexistente")
	@Sql("classpath:/resources/sqls/equipe.sql")
	void findByIdNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> equipService.findById(100));
		assertEquals("Equipe 100 não encontrada", exception.getMessage());
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
	@DisplayName("Teste listar todos")
	@Sql("classpath:/resources/sqls/equipe.sql")
	void listAllTest() {
		assertEquals(3, equipService.listAll().size());
	}
	
	@Test
	@DisplayName("Teste listar todos sem cadastros existentes")
	void listAllNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> equipService.listAll());
		assertEquals("Nenhuma equipe cadastrada", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar equipe")
	@Sql("classpath:/resources/sqls/equipe.sql")
	void updateEquipTest() {
		assertEquals("Redbull", equipService.findById(1).getName());
		Equip equipUpdate = new Equip(1, "update");
		equipService.update(equipUpdate);
		assertEquals("update", equipService.findById(1).getName());
	}
	
	@Test
	@DisplayName("Teste alterar equipe já existente")
	@Sql("classpath:/resources/sqls/equipe.sql")
	void updateEquipExistsTest() {
		assertEquals("Redbull", equipService.findById(1).getName());
		Equip equipUpdate = new Equip(1, "Ferrari");
		var exception = assertThrows(IntegrityViolation.class, () -> equipService.update(equipUpdate));
		assertEquals("Equipe já existente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste remover equipe")
	@Sql("classpath:/resources/sqls/equipe.sql")
	void deleteEquipTest() {
		equipService.delete(1);
		assertEquals(2, equipService.listAll().size());
	}
	
	@Test
	@DisplayName("Teste remover equipe inexistente")
	@Sql("classpath:/resources/sqls/equipe.sql")
	void deleteEquipNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> equipService.findById(100));
		assertEquals("Equipe 100 não encontrada", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste busca por nome")
	@Sql("classpath:/resources/sqls/equipe.sql")
	void findByNameTest() {
		assertEquals("Ferrari", equipService.findByName("Ferrari").get(0).getName());
	}
	
	@Test
	@DisplayName("Teste busca por nome inexistente")
	@Sql("classpath:/resources/sqls/equipe.sql")
	void findByNameNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> equipService.findByName("nasljkfn"));
		assertEquals("Nenhuma equipe foi encontrada com o nome nasljkfn", exception.getMessage());
	}
}
