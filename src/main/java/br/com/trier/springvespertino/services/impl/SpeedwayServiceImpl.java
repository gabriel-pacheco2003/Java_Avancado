package br.com.trier.springvespertino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.models.Speedway;
import br.com.trier.springvespertino.repositories.SpeedwayRepository;
import br.com.trier.springvespertino.services.SpeedwayService;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;

@Service
public class SpeedwayServiceImpl implements SpeedwayService{

	@Autowired
	private SpeedwayRepository repository;
	
	private void validateSpeedway(Speedway speedway) {
		if(speedway.getSize() == null || speedway.getSize() <= 0) {
			throw new IntegrityViolation("Tamanho da pista inválido");
		}
	}
	
	@Override
	public Speedway findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Pista %s não encontrada".formatted(id)));
	}

	@Override
	public Speedway insert(Speedway speedway) {
		validateSpeedway(speedway);
		return repository.save(speedway);
	}

	@Override
	public List<Speedway> listAll() {
		if(repository.findAll().isEmpty()) {
			throw new ObjectNotFound("Nenhuma pista cadastrada");
		}
		return repository.findAll();
	}

	@Override
	public Speedway update(Speedway speedway) {
		findById(speedway.getId());
		validateSpeedway(speedway);
		return repository.save(speedway);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
	}

	@Override
	public List<Speedway> findByNameStartsWithIgnoreCase(String name) {
		if(repository.findByNameStartsWithIgnoreCase(name).isEmpty()){
			throw new ObjectNotFound("Nenhuma pista encontrada com o nome %s".formatted(name));
		}
		return repository.findByNameStartsWithIgnoreCase(name);
	}

	@Override
	public List<Speedway> findBySizeBetween(Integer sizeIn, Integer sizeFin) {
		if(repository.findBySizeBetween(sizeIn, sizeFin).isEmpty()){
			throw new ObjectNotFound("Nenhuma pista foi encontrada");
		}
		return repository.findBySizeBetween(sizeIn, sizeFin);
	}

	@Override
	public List<Speedway> findByCountryOrderBySizeDesc(Country country) {
		if(repository.findByCountryOrderBySizeDesc(country).isEmpty()) {
			throw new ObjectNotFound("Nenhuma pista foi encontrada");
		}
		return repository.findByCountryOrderBySizeDesc(country);
	}

	
	
}
