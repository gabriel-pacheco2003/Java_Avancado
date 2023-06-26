package br.com.trier.springvespertino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.User;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class UserServiceTest extends BaseTests {

	@Autowired
	UserService userService;

	@Test
	@DisplayName("Teste buscar por ID")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByIdTest() {
		var user = userService.findById(1);
		assertEquals(1, user.getId());
		assertEquals("User 1", user.getName());
		assertEquals("email1", user.getEmail());
		assertEquals("senha1", user.getPassword());
	}

	@Test
	@DisplayName("Teste buscar por ID inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void ListByIdNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> userService.findById(5));
		assertEquals("Usuário 5 não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir usuario")
	void insertUserTest() {
		User user = new User(null, "insert", "insert", "insert", "ADMIN");
		userService.insert(user);
		assertEquals(1, user.getId());
		assertEquals("insert", user.getName());
		assertEquals("insert", user.getEmail());
		assertEquals("insert", user.getPassword());
		assertEquals("ADMIN", user.getRoles());
	}

	@Test
	@DisplayName("Teste inserir usuario com email existente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void insertUserWithRegisteredEmailTest() {
		User usuario = new User(null, "insert", "email1", "insert", "ADMIN");
		var exception = assertThrows(IntegrityViolation.class, () -> userService.insert(usuario));
		assertEquals("Email já existente", exception.getMessage());
	}

	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void listAllTest() {
		assertEquals(2, userService.listAll().size());
	}

	@Test
	@DisplayName("Teste listar todos sem cadastros existentes")
	void listAllNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> userService.listAll());
		assertEquals("Nenhum usuário cadastrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar usuario")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void updateUserTest() {
		assertEquals("User 1", userService.findById(1).getName());
		User userUpdated = new User(1, "update", "update", "update", "ADMIN");
		userService.update(userUpdated);
		assertEquals("update", userService.findById(1).getName());
	}

	@Test
	@DisplayName("Teste alterar usuario com email cadastrado")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void updateUserWithRegisteredEmailTest() {
		User userUpdated = new User(1, "update", "email2", "update", "ADMIN");
		var exception = assertThrows(IntegrityViolation.class, () -> userService.update(userUpdated));
		assertEquals("Email já existente", exception.getMessage());
	}

	@Test
	@DisplayName("Teste remover usuario")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void removeUserTest() {
		userService.delete(1);
		assertEquals(1, userService.listAll().size());
	}

	@Test
	@DisplayName("Teste remover usuario inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void removeUserNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> userService.delete(5));
		assertEquals("Usuário 5 não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar usuario por nome")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByNameTest() {
		assertEquals(2, userService.findByNameStartsWithIgnoreCase("Use").size());
		assertEquals(1, userService.findByNameStartsWithIgnoreCase("User 1").size());
	}

	@Test
	@DisplayName("Teste busca por nome inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByNameNonExists() {
		var exception = assertThrows(ObjectNotFound.class, () -> userService.findByNameStartsWithIgnoreCase("c"));
		assertEquals("Nenhum nome de usuário inicia com c", exception.getMessage());
	}

}
