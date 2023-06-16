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
import br.com.trier.springvespertino.models.User;
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
	@DisplayName("Teste buscar por ID")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void ListByIdNonExistsTest() {
		var usuario = userService.findById(3);
		assertNull(usuario);
	}
	
	@Test
	@DisplayName("Teste inserir usuario")
	void insertUserTest() {
		User usuario = new User(null, "insert", "insert", "insert");
		userService.insert(usuario);
		userService.insert(usuario);
		assertEquals(1, usuario.getId());
		assertEquals("insert", usuario.getName());
		assertEquals("insert", usuario.getEmail());
		assertEquals("insert", usuario.getPassword());
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void listAllTest() {
		var usuario = userService.listAll();
		assertEquals(2, usuario.size());
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
		userService.delete(3);
		List<User> lista = userService.listAll();
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId());
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
		List<User> lista = userService.findByName("c");
		assertEquals(0, lista.size());
	} 
	
}
