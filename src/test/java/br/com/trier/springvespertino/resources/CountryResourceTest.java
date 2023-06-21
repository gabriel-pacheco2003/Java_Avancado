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
import br.com.trier.springvespertino.models.dto.CountryDTO;
import br.com.trier.springvespertino.resources.exceptions.StandardError;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = SpringVespertinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryResourceTest {

	@Autowired
	protected TestRestTemplate rest;

	private ResponseEntity<CountryDTO> getCountry(String url) {
		return rest.getForEntity(url, CountryDTO.class);
	}

	private ResponseEntity<List<CountryDTO>> getCountries(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CountryDTO>>() {
		});
	}
	
	@Test
	@DisplayName("Buscar por id")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/pais.sql")
	public void getOkTest() {
		ResponseEntity<CountryDTO> response = getCountry("/paises/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals("Brasil", response.getBody().getName());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/pais.sql")
	public void getNotFoundTest() {
		ResponseEntity<CountryDTO> response = getCountry("/paises/100");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Cadastrar país")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/pais.sql")
	public void insertCountryTest() {
		CountryDTO dto = new CountryDTO(1, "nome");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CountryDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<CountryDTO> responseEntity = rest.exchange(
	            "/paises", 
	            HttpMethod.POST,  
	            requestEntity,    
	            CountryDTO.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("nome", responseEntity.getBody().getName());
	}
	
	@Test
	@DisplayName("Cadastrar país já existente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/pais.sql")
	public void insertCountryExistsTest() {
		CountryDTO dto = new CountryDTO(null, "Brasil");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CountryDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<CountryDTO> responseEntity = rest.exchange(
	            "/paises", 
	            HttpMethod.POST,  
	            requestEntity,    
	            CountryDTO.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("Alterar país")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/pais.sql")
	public void udpateCountryTest() {
		CountryDTO dto = new CountryDTO(null, "update");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CountryDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<CountryDTO> responseEntity = rest.exchange(
	            "/paises/1", 
	            HttpMethod.PUT,  
	            requestEntity,    
	            CountryDTO.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("update", responseEntity.getBody().getName());
	}
	
	@Test
	@DisplayName("Alterar país já existente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/pais.sql")
	public void udpateCountryExistsTest() {
		CountryDTO dto = new CountryDTO(1, "Japão");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CountryDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<CountryDTO> responseEntity = rest.exchange(
	            "/paises/1", 
	            HttpMethod.PUT,  
	            requestEntity,    
	            CountryDTO.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("Procurar por nome")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/pais.sql")
	public void findByNameTest() {
		ResponseEntity<List<CountryDTO>> response = getCountries("/paises/name/Brasil");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Procurar por nome inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/pais.sql")
	public void findByNameNonExistsTest() {
		ResponseEntity<StandardError> response = rest.getForEntity("/paises/name/vsk", StandardError.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/pais.sql")
	public void listAllTest() {
		ResponseEntity<List<CountryDTO>> response = getCountries("/paises");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(3, response.getBody().size());
	}
	
	@Test
	@DisplayName("Listar todos sem possuir cadastros")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	public void listAllWithNoRegistersTest() {
		ResponseEntity<StandardError> response = rest.getForEntity("/paises", StandardError.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Remover país")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/pais.sql")
	public void deleteCountryTest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CountryDTO> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<CountryDTO> responseEntity = rest.exchange(
				"/paises/1", 
				HttpMethod.DELETE,  
				requestEntity,    
				CountryDTO.class   
		);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

	}
	
	@Test
	@DisplayName("Remover país inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql("classpath:/resources/sqls/pais.sql")
	public void deleteCountryNonExistsTest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CountryDTO> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<CountryDTO> responseEntity = rest.exchange(
				"/paises/10", 
				HttpMethod.DELETE,  
				requestEntity,    
				CountryDTO.class   
		);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);

	}

}