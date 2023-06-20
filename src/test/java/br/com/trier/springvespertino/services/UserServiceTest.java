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
import br.com.trier.springvespertino.models.User;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class UserServiceTest extends BaseTests{

	@Autowired
	UserService userService;
	
	@Test
	@DisplayName("Teste buscar por ID")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByIdTest() {
		var usuario = userService.findById(1);
		assertNotNull(usuario);
		assertEquals(1, usuario.getId());
		assertEquals("User 1", usuario.getName());
		assertEquals("email1", usuario.getEmail());
		assertEquals("senha1", usuario.getPassword());
	}
	
	@Test
	@DisplayName("Teste buscar por ID inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void ListByIdNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> userService.findById(5));
		assertEquals("O usuário 5 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir usuario")
	void insertUserTest() {
		User usuario = new User(null, "insert", "insert", "insert");
		userService.insert(usuario);
		assertEquals(1, usuario.getId());
		assertEquals("insert", usuario.getName());
		assertEquals("insert", usuario.getEmail());
		assertEquals("insert", usuario.getPassword());
	}
	
	@Test
	@DisplayName("Teste inserir usuario com email existente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void insertUserWithRegisteredEmailTest() {
		User usuario = new User(null, "insert", "email1", "insert");
		var exception = assertThrows(IntegrityViolation.class, () -> userService.insert(usuario));
		assertEquals("Email já existente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void listAllTest() {
		var usuario = userService.listAll(); 
		assertEquals(2, usuario.size());
	}
	
	@Test
	@DisplayName("Teste listar todos sem possuir usuários cadastrados")
	void listAllNonExistxTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> userService.listAll());
		assertEquals("Nenhum usuário cadastrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste remover usuario")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void removeUserTest() {
		userService.delete(1);
		List<User> lista = userService.listAll();
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste remover usuario inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void removeUserNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> userService.findById(5));
		assertEquals("O usuário 5 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar usuario")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void updatedUsersTest() {
		var usuario = userService.findById(1);
		assertEquals("User 1", usuario.getName());
		User usuarioAltera = new User(1, "altera","altera","altera");
		userService.update(usuarioAltera);
		usuario = userService.findById(1);
		assertEquals("altera", usuario.getName());	
	}
	
	@Test
	@DisplayName("Teste alterar usuario com email cadastrado")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void updatedUsersWithRegisteredEmailTest() {
		var usuario = userService.findById(1);
		assertEquals("User 1", usuario.getName());
		User usuarioAltera = new User(1, "altera","email2","altera");
		var exception = assertThrows(IntegrityViolation.class, () -> userService.update(usuarioAltera));
		assertEquals("Email já existente", exception.getMessage());
	}
	
    @Test
    @DisplayName("Teste buscar usuario por nome")
    @Sql({"classpath:/resources/sqls/usuario.sql"})
    void findByNameTest() {
        var usuario = userService.findByName("Use");
        assertNotNull(usuario);
        assertEquals(2, usuario.size());
        var usuario1 = userService.findByName("User 1");
        assertEquals(1, usuario1.size());
    }
	
	@Test
	@DisplayName("Teset buscar por nome inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByNameNonExists() {
		var exception = assertThrows(ObjectNotFound.class, () -> userService.findByName("c"));
		assertEquals("Nenhum nome de usuário inicia com c", exception.getMessage());
	} 
	
}
