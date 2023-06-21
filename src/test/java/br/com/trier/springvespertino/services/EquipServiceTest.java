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
		assertNotNull(equipe);
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
	@DisplayName("Teste listar todas as equipes")
	@Sql("classpath:/resources/sqls/equipe.sql")
	void listAllTest() {
		List<Equip> lista = equipService.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste listar todos sem equipes cadastradas")
	void listAllNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> equipService.listAll());
		assertEquals("Nenhuma equipe cadastrada", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar equipe")
	@Sql("classpath:/resources/sqls/equipe.sql")
	void updateEquipTest() {
		var equip = equipService.findById(1);
		assertEquals("Redbull", equip.getName());
		Equip equipUpdate = new Equip(1, "update");
		equipService.update(equipUpdate);
		equip = equipService.findById(1);
		assertEquals("update", equip.getName());
	}
	
	@Test
	@DisplayName("Teste alterar equipe já existente")
	@Sql("classpath:/resources/sqls/equipe.sql")
	void updateEquipExistsTest() {
		var equip = equipService.findById(1);
		assertEquals("Redbull", equip.getName());
		Equip equipUpdate = new Equip(1, "Ferrari");
		var exception = assertThrows(IntegrityViolation.class, () -> equipService.update(equipUpdate));
		assertEquals("Equipe já existente", exception.getMessage());
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
		var exception = assertThrows(ObjectNotFound.class, () -> equipService.findById(100));
		assertEquals("Equipe 100 não encontrada", exception.getMessage());
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
		var exception = assertThrows(ObjectNotFound.class, () -> equipService.findByName("abc"));
		assertEquals("Nenhuma equipe foi encontrada com o nome abc", exception.getMessage());
	}
}
