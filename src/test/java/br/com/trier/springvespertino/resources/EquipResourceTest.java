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
import br.com.trier.springvespertino.models.dto.EquipDTO;
import br.com.trier.springvespertino.resources.exceptions.StandardError;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = SpringVespertinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EquipResourceTest {

	@Autowired
	protected TestRestTemplate rest;

	private ResponseEntity<EquipDTO> getEquip(String url) {
		return rest.getForEntity(url, EquipDTO.class);
	}

	private ResponseEntity<List<EquipDTO>> getEquips(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<EquipDTO>>() {
		});
	}
	
	@Test
	@DisplayName("Buscar por id")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/equipe.sql")
	public void getOkTest() {
		ResponseEntity<EquipDTO> response = getEquip("/equipes/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals("Redbull", response.getBody().getName());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/equipe.sql")
	public void getNotFoundTest() {
		ResponseEntity<EquipDTO> response = getEquip("/equipes/100");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Cadastrar equipe")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	public void insertUserTest() {
		EquipDTO dto = new EquipDTO(null, "nome");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<EquipDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<EquipDTO> responseEntity = rest.exchange(
	            "/equipes", 
	            HttpMethod.POST,  
	            requestEntity,    
	            EquipDTO.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("nome", responseEntity.getBody().getName());
	}
	
	@Test
	@DisplayName("Cadastrar equipe já existente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/equipe.sql")
	public void insertEquipExistsTest() {
		EquipDTO dto = new EquipDTO(null, "Redbull");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<EquipDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<EquipDTO> responseEntity = rest.exchange(
	            "/equipes", 
	            HttpMethod.POST,  
	            requestEntity,    
	            EquipDTO.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("Alterar equipe")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/equipe.sql")
	public void udpateEquipTest() {
		EquipDTO dto = new EquipDTO(1, "update");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<EquipDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<EquipDTO> responseEntity = rest.exchange(
	            "/equipes/1", 
	            HttpMethod.PUT,  
	            requestEntity,    
	            EquipDTO.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("update", responseEntity.getBody().getName());
	}
	
	@Test
	@DisplayName("Alterar equipe já existente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/equipe.sql")
	public void udpateEquipExistsTest() {
		EquipDTO dto = new EquipDTO(1, "Ferrari");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<EquipDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<EquipDTO> responseEntity = rest.exchange(
	            "/equipes/1", 
	            HttpMethod.PUT,  
	            requestEntity,    
	            EquipDTO.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("Procurar por nome")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/equipe.sql")
	public void findByNameTest() {
		ResponseEntity<List<EquipDTO>> response = getEquips("/equipes/name/Ferrari");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Procurar por nome inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/equipe.sql")
	public void findByNameNonExistsTest() {
		ResponseEntity<StandardError> response = rest.getForEntity("/usuarios/name/gnvri", StandardError.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Listar todas as equipes")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/equipe.sql")
	public void listAllTest() {
		ResponseEntity<List<EquipDTO>> response = getEquips("/equipes");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(2, response.getBody().size());
	}
	
	@Test
	@DisplayName("Listar todas sem possuir cadastros")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	public void listAllWithNoRegistersTest() {
		ResponseEntity<StandardError> response = rest.getForEntity("/equipes", StandardError.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Remover equipe")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/equipe.sql")
	public void deleteEquipTest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<EquipDTO> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<EquipDTO> responseEntity = rest.exchange(
				"/equipes/1", 
				HttpMethod.DELETE,  
				requestEntity,    
				EquipDTO.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Remover equipe inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	public void deleteEquipNonExistsTest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<EquipDTO> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<EquipDTO> responseEntity = rest.exchange(
				"/paises/1", 
				HttpMethod.DELETE,  
				requestEntity,    
				EquipDTO.class   
		);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
}