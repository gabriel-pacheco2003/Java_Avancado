package br.com.trier.springvespertino.models;

import br.com.trier.springvespertino.models.dto.EquipDTO;
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
@Entity(name = "equipe")
public class Equip {
	
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_equipe")
	private Integer id;
	
	@Column(name = "nome_equipe", unique = true)
	private String name;

	public Equip(EquipDTO dto) {
		this(dto.getId(), dto.getName());
	}

	public EquipDTO toDTO() {
		return new EquipDTO(id, name);
	}
	
}
