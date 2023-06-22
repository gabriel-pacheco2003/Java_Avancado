package br.com.trier.springvespertino.models;

import br.com.trier.springvespertino.models.dto.ChampionshipDTO;
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
@Entity(name = "campeonato")
public class Championship {
	
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_campeonato")
	private Integer id;
	
	@Column(name = "descricao_campeonato")
	private String description;
	
	@Column(name = "ano_campeonato")
	private Integer year;

	public Championship(ChampionshipDTO dto) {
		this(dto.getId(), dto.getDescription(), dto.getYear());
	}

	public ChampionshipDTO toDTO() {
		return new ChampionshipDTO(id, description, year);
	}
	
}
