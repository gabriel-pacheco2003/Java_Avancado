package br.com.trier.springvespertino.models;

import br.com.trier.springvespertino.models.dto.RacerDTO;
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
@EqualsAndHashCode(of = "id")
@Entity(name = "piloto")
public class Racer {

	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_piloto")
	private Integer id;

	@Column(name = "nome_piloto")
	private String name;

	@ManyToOne
	private Country country;

	@ManyToOne
	private Equip equip;
	
	public Racer(RacerDTO dto, Country country, Equip equip) {
		this(dto.getId(), dto.getName(), country, equip);
	}

	public RacerDTO toDTO() {
		return new RacerDTO(id, name, country.getId(), country.getName(), equip.getId(), equip.getName());
	}

}
