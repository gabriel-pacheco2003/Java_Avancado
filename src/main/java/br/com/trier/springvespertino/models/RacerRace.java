package br.com.trier.springvespertino.models;

import br.com.trier.springvespertino.models.dto.RacerRaceDTO;
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
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (of = "id")
@Entity(name = "pilotoCorrida")
public class RacerRace {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pilotoCorrida")
	private Integer id;
	
	@ManyToOne
	private Racer racer;
	
	@ManyToOne
	private Race race;

	@Column(name = "colocacao")
	private Integer rank;
	
	public RacerRace(RacerRaceDTO dto, Racer racer, Race race) {
		this(dto.getId(), racer, race, dto.getRank());	
	}
	
	public RacerRaceDTO toDTO() {
		return new RacerRaceDTO(id, rank, racer.getId(), racer.getName(), race.getId(), DateUtils.dateToString(race.getDate()), race.getSpeedway());
	}

}
