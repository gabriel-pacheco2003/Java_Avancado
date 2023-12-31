package br.com.trier.springvespertino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Equip;
import br.com.trier.springvespertino.repositories.EquipRepository;
import br.com.trier.springvespertino.services.EquipService;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;

@Service
public class EquipServiceImpl implements EquipService {

	@Autowired
	private EquipRepository repository;

	private void findByNameExists(Equip equip) {
		Equip find = repository.findByName(equip.getName());
		if (find != null && find.getId() != equip.getId()) {
			throw new IntegrityViolation("Equipe já existente");
		}
	}

	@Override
	public Equip findById(Integer id) {
		Optional<Equip> equip = repository.findById(id);
		return equip.orElseThrow(() -> new ObjectNotFound("Equipe %s não encontrada".formatted(id)));
	}

	@Override
	public Equip insert(Equip equip) {
		findByNameExists(equip);
		return repository.save(equip);
	}

	@Override
	public List<Equip> listAll() {
		if (repository.findAll().isEmpty()) {
			throw new ObjectNotFound("Nenhuma equipe cadastrada");
		}
		return repository.findAll();
	}

	@Override
	public Equip update(Equip equip) {
		findById(equip.getId());
		findByNameExists(equip);
		return repository.save(equip);
	}

	@Override
	public void delete(Integer id) {
		Equip equip = findById(id);
		repository.delete(equip);
	}

	@Override
	public List<Equip> findByName(String name) {
		List<Equip> lista = repository.findByNameIgnoreCase(name);
		if (lista.isEmpty()) {
			throw new ObjectNotFound("Nenhuma equipe foi encontrada com o nome %s".formatted(name));
		}
		return lista;
	}
}
