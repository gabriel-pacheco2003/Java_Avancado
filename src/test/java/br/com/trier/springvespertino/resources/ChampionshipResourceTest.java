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
import br.com.trier.springvespertino.models.dto.ChampionshipDTO;
import br.com.trier.springvespertino.resources.exceptions.StandardError;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = SpringVespertinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChampionshipResourceTest {

	@Autowired
	protected TestRestTemplate rest;

	private ResponseEntity<ChampionshipDTO> getChampionship(String url) {
		return rest.getForEntity(url, ChampionshipDTO.class);
	}

	private ResponseEntity<List<ChampionshipDTO>> getChampionships(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ChampionshipDTO>>() {
		});
	}
	
	@Test
	@DisplayName("Buscar por id")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/campeonato.sql")
	public void getOkTest() {
		ResponseEntity<ChampionshipDTO> response = getChampionship("/campeonatos/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals("Camp1", response.getBody().getDescription());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/campeonato.sql")
	public void getNotFoundTest() {
		ResponseEntity<ChampionshipDTO> response = getChampionship("/campeonatos/100");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Buscar campeonato por ano")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/campeonato.sql")
	public void getChampByYearBetweenTest() {
		ResponseEntity<List<ChampionshipDTO>> response = getChampionships("/campeonatos/anoInicial/2005/anoFinal/2020");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(2, response.getBody().size());
	}
	
	@Test
	@DisplayName("Buscar campeonato por ano não encontrado")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/campeonato.sql")
	public void getChampByYearBetweenNotFoundTest() {
		ResponseEntity<StandardError> response = rest.getForEntity("/campeonatos/anoInicial/1990/anoFinal/1999", StandardError.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Cadastrar campeonato")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	public void insertChampTest() {
		ChampionshipDTO dto = new ChampionshipDTO(null, "insert", 2000);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ChampionshipDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<ChampionshipDTO> responseEntity = rest.exchange(
	            "/campeonatos", 
	            HttpMethod.POST,  
	            requestEntity,    
	            ChampionshipDTO.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("insert", responseEntity.getBody().getDescription());
	}
	
	@Test
	@DisplayName("Cadastrar campeonato com ano abaixo do padrão")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/campeonato.sql")
	public void insertChampBelowYearTest() {
		ChampionshipDTO dto = new ChampionshipDTO(null, "insert", 1989);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ChampionshipDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<ChampionshipDTO> responseEntity = rest.exchange(
	            "/campeonatos", 
	            HttpMethod.POST,  
	            requestEntity,    
	            ChampionshipDTO.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("Cadastrar campeonato com ano acima do padrão")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/campeonato.sql")
	public void insertChampAboveYearTest() {
		ChampionshipDTO dto = new ChampionshipDTO(null, "insert", 2025);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ChampionshipDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<ChampionshipDTO> responseEntity = rest.exchange(
	            "/campeonatos", 
	            HttpMethod.POST,  
	            requestEntity,    
	            ChampionshipDTO.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("Alterar campeonato")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/campeonato.sql")
	public void udpateChampTest() {
		ChampionshipDTO dto = new ChampionshipDTO(1, "update", 2000);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ChampionshipDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<ChampionshipDTO> responseEntity = rest.exchange(
	            "/campeonatos/1", 
	            HttpMethod.PUT,  
	            requestEntity,    
	            ChampionshipDTO.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("update", responseEntity.getBody().getDescription());
	}
	
	@Test
	@DisplayName("Alterar campeonato com ano abaixo do padrão")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/campeonato.sql")
	public void udpateChampBelowYearTest() {
		ChampionshipDTO dto = new ChampionshipDTO(1, "update", 1989);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ChampionshipDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<ChampionshipDTO> responseEntity = rest.exchange(
	            "/campeonatos/1", 
	            HttpMethod.PUT,  
	            requestEntity,    
	            ChampionshipDTO.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("Alterar campeonato com ano acima do padrão")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/campeonato.sql")
	public void udpateChampAboveYearTest() {
		ChampionshipDTO dto = new ChampionshipDTO(1, "update", 2025);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ChampionshipDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<ChampionshipDTO> responseEntity = rest.exchange(
	            "/campeonatos/1", 
	            HttpMethod.PUT,  
	            requestEntity,    
	            ChampionshipDTO.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("Procurar por descrição")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/campeonato.sql")
	public void findByDescriptionTest() {
		ResponseEntity<List<ChampionshipDTO>> response = getChampionships("/campeonatos/descricao/Camp1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Procurar por descrição inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/campeonato.sql")
	public void findByDescriptionNonExistsTest() {
		ResponseEntity<StandardError> response = rest.getForEntity("/campeonatos/descricao/svbrfj", StandardError.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Listar todas os campeonatos")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/campeonato.sql")
	public void listAllTest() {
		ResponseEntity<List<ChampionshipDTO>> response = getChampionships("/campeonatos");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(3, response.getBody().size());
	}
	
	@Test
	@DisplayName("Listar todos sem possuir cadastros")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	public void listAllWithNoRegistersTest() {
		ResponseEntity<StandardError> response = rest.getForEntity("/campeonatos", StandardError.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Remover campeonato")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/campeonato.sql")
	public void deleteChampTest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ChampionshipDTO> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<ChampionshipDTO> responseEntity = rest.exchange(
				"/campeonatos/1", 
				HttpMethod.DELETE,  
				requestEntity,    
				ChampionshipDTO.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Remover campeonato inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	public void deleteChampNonExistsTest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ChampionshipDTO> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<ChampionshipDTO> responseEntity = rest.exchange(
				"/campeonatos/1", 
				HttpMethod.DELETE,  
				requestEntity,    
				ChampionshipDTO.class   
		);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
}