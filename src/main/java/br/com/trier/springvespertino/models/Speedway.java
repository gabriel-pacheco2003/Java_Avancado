package br.com.trier.springvespertino.models;

import br.com.trier.springvespertino.models.dto.SpeedwayDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (of = "id")
@Entity(name = "pista")
public class Speedway { 
	
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pista")
	private Integer id;
	
	@Column(name = "nome_pista")
	private String name;
	
	@Column(name = "tamanho_pista")
	private Integer size;

	@ManyToOne
	private Country country;
	
	public Speedway(SpeedwayDTO dto) {
		this(dto.getId(), dto.getName(), dto.getSize(), dto.getCountry());
	}

	public SpeedwayDTO toDTO() {
		return new SpeedwayDTO(id, name, size, country);
	}
	
}
