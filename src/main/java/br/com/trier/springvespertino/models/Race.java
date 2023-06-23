package br.com.trier.springvespertino.models;

import java.time.ZonedDateTime;

import br.com.trier.springvespertino.models.dto.RaceDTO;
import br.com.trier.springvespertino.utils.DateUtils;
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
@Entity(name = "corrida")
public class Race {

	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_corrida")
	private Integer id;
	
	@Column(name = "data_corrida")
	private ZonedDateTime date;
	 
	@ManyToOne
	private Speedway speedway;
	
	@ManyToOne
	private Championship championship;
	
	public Race(RaceDTO dto, Speedway speedway, Championship championship) {
	this(dto.getId(), DateUtils.stringToDate(dto.getDate()), speedway, championship);
	}
	
	public RaceDTO toDTO() {
		return new RaceDTO(id, DateUtils.dateToString(date), speedway.getId(), speedway.getName(), championship.getId(), championship.getDescription());
	}
	
}
