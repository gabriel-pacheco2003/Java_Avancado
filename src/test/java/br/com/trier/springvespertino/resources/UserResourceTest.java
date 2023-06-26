package br.com.trier.springvespertino.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springvespertino.SpringVespertinoApplication;
import br.com.trier.springvespertino.config.jwt.LoginDTO;
import br.com.trier.springvespertino.models.dto.UserDTO;
import br.com.trier.springvespertino.resources.exceptions.StandardError;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = SpringVespertinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceTest {

	@Autowired
	protected TestRestTemplate rest;

	private ResponseEntity<UserDTO> getUser(String url) {
		return rest.getForEntity(url, UserDTO.class);
	}

	private ResponseEntity<List<UserDTO>> getUsers(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDTO>>() {
		});
	}
	
	@Test
	@DisplayName("Buscar por id")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void getOkTest() {
		ResponseEntity<UserDTO> response = getUser("/usuarios/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		UserDTO user = response.getBody();
		assertEquals("User 1", user.getName());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void getNotFoundTest() {
		ResponseEntity<UserDTO> response = getUser("/usuarios/100");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Cadastrar usuário")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	public void insertUserTest() {
		UserDTO dto = new UserDTO(null, "nome", "email", "senha", "permissao");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
	            "/usuarios", 
	            HttpMethod.POST,  
	            requestEntity,    
	            UserDTO.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		UserDTO user = responseEntity.getBody();
		assertEquals("nome", user.getName());
	}
	
	@Test
	@DisplayName("Cadastrar usuario com o email duplicado")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void insertUserWithDuplicatedEmail() {
		UserDTO dto = new UserDTO(null, "nome", "email2", "senha", "permissao");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
	            "/usuarios", 
	            HttpMethod.POST,  
	            requestEntity,    
	            UserDTO.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("Alterar usuario")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void udpateUserTest() {
		UserDTO dto = new UserDTO(1, "update", "update", "update", "update");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
	            "/usuarios/1", 
	            HttpMethod.PUT,  
	            requestEntity,    
	            UserDTO.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		dto = responseEntity.getBody();
		assertEquals("update", dto.getName());
	}
	
	@Test
	@DisplayName("Alterar usuario com email duplicado")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void udpateUserWithDuplicatedEmailTest() {
		UserDTO dto = new UserDTO(1, "update", "email2", "update", "update");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
	            "/usuarios/1", 
	            HttpMethod.PUT,  
	            requestEntity,    
	            UserDTO.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("Procurar por nome")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void findByNameTest() {
		ResponseEntity<List<UserDTO>> response = getUsers("/usuarios/name/User 1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Procurar por nome inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void findByNameNonExistsTest() {
		ResponseEntity<StandardError> response = rest.getForEntity("/usuarios/name/asdbvsk", StandardError.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void listAllTest() {
		ResponseEntity<List<UserDTO>> response = getUsers("/usuarios");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(2, response.getBody().size());
	}
	
	@Test
	@DisplayName("Listar todos sem possuir cadastros")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	public void listAllWithNoRegistersTest() {
		ResponseEntity<StandardError> response = rest.getForEntity("/usuarios", StandardError.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Remover usuário")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void deleteUserTest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
				"/usuarios/1", 
				HttpMethod.DELETE,  
				requestEntity,    
				UserDTO.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Remover usuário inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	public void deleteUserNonExistsTest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
				"/usuarios/6", 
				HttpMethod.DELETE,  
				requestEntity,    
				UserDTO.class   
		);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Obter Token")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void getToken() {
		LoginDTO loginDTO = new LoginDTO("email1", "senha1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		System.out.println("****************"+token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<List<UserDTO>> response =  rest.exchange("/users", HttpMethod.GET, new HttpEntity<>(null, headers),new ParameterizedTypeReference<List<UserDTO>>() {});
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	/*
	 * @Test
	 * 
	 * @DisplayName("Obter Token")
	 * 
	 * @Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	 * 
	 * @Sql({"classpath:/resources/sqls/usuario.sql"}) public void getToken() {
	 * LoginDTO loginDTO = new LoginDTO("email1", "senha1"); HttpHeaders headers =
	 * new HttpHeaders(); headers.setContentType(MediaType.APPLICATION_JSON);
	 * HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
	 * ResponseEntity<String> responseEntity = rest.exchange( "/auth/token",
	 * HttpMethod.POST, requestEntity, String.class );
	 * assertEquals(responseEntity.getStatusCode(), HttpStatus.OK); String token =
	 * responseEntity.getBody(); System.out.println("****************"+token);
	 * headers = new HttpHeaders();
	 * headers.setContentType(MediaType.APPLICATION_JSON);
	 * headers.setBearerAuth(token); ResponseEntity<List<UserDTO>> response =
	 * rest.exchange("/users", HttpMethod.GET, null,new
	 * ParameterizedTypeReference<List<UserDTO>>() {} , headers);
	 * assertEquals(response.getStatusCode(), HttpStatus.OK); }
	 */
}