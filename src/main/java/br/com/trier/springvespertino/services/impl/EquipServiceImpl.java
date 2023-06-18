package br.com.trier.springvespertino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Equip;
import br.com.trier.springvespertino.repositories.EquipRepository;
import br.com.trier.springvespertino.services.EquipService;

@Service
public class EquipServiceImpl implements EquipService {

	@Autowired
	private EquipRepository repository;

	@Override
	public Equip findById(Integer id) {
		Optional<Equip> equip = repository.findById(id);
		return equip.orElse(null);
	}

	@Override
	public Equip insert(Equip equipe) {
		return repository.save(equipe);
	}

	@Override
	public List<Equip> listAll() {
		return repository.findAll();
	}

	@Override
	public Equip update(Equip equipe) {
		return repository.save(equipe);
	}

	@Override
	public void delete(Integer id) {
		Equip equip = findById(id);
		if (equip != null) {
			repository.delete(equip);
		}

	}

	@Override
	public List<Equip> findByName(String name) {
		return repository.findByName(name);
	}

}
