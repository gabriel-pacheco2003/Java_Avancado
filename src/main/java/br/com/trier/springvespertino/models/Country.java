package br.com.trier.springvespertino.models;

import br.com.trier.springvespertino.models.dto.CountryDTO;
import br.com.trier.springvespertino.models.dto.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "pais")
public class Country {

	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pais")
	private Integer id;
	
	@Column(name = "nome_pais", unique = true)
	private String name;
	
	public Country(CountryDTO dto) {
		this(dto.getId(), dto.getName());
	}

	public CountryDTO toDTO() {
		return new CountryDTO(id, name);
	}
	
}
