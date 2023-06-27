package br.com.trier.springvespertino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.models.Equip;
import br.com.trier.springvespertino.models.Racer;
import br.com.trier.springvespertino.repositories.RacerRepository;
import br.com.trier.springvespertino.services.RacerService;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;

@Service
public class RacerServiceImpl implements RacerService{

	@Autowired
	private RacerRepository repository;

	@Override
	public Racer findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Piloto %s não encontrado".formatted(id)));
	}

	@Override
	public Racer insert(Racer racer) {
		return repository.save(racer);
	}

	@Override
	public List<Racer> listAll() {
		if (repository.findAll().isEmpty()) {
			throw new ObjectNotFound("Nenhum piloto cadastrado");
		}
		return repository.findAll();
	}

	@Override
	public Racer update(Racer racer) {
		findById(racer.getId());
		return repository.save(racer);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
	}

	@Override
	public List<Racer> findByNameStartsWithIgnoreCase(String name) {
		if (repository.findByNameStartsWithIgnoreCase(name).isEmpty()) {
			throw new ObjectNotFound("Nenhum piloto encontrado com o nome %s".formatted(name));
		}
		return repository.findByNameStartsWithIgnoreCase(name);
	}

	@Override
	public List<Racer> findByCountry(Country country) {
		if (repository.findByCountry(country).isEmpty()) {
			throw new ObjectNotFound("Nenhum piloto foi encontrado");
		}
		return repository.findByCountry(country);
	}

	@Override
	public List<Racer> findByEquipOrderByName(Equip equip) {
		if (repository.findByEquipOrderByName(equip).isEmpty()) {
			throw new ObjectNotFound("Nenhum piloto foi encontrado");
		}
		return repository.findByEquipOrderByName(equip);

	}

}
