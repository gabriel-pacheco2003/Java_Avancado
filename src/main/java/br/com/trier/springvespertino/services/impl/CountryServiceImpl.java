package br.com.trier.springvespertino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.repositories.CountryRepository;
import br.com.trier.springvespertino.services.CountryService;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;

@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryRepository repository;

	private void findByNameNonExists(Country country) {
		Country find = repository.findByName(country.getName());
		if (find != null && find.getId() != country.getId()) {
			throw new IntegrityViolation("País já existente");
		}
	}

	@Override
	public Country findById(Integer id) {
		Optional<Country> country = repository.findById(id);
		return country.orElseThrow(() -> new ObjectNotFound("País %s não encontrado".formatted(id)));
	}

	@Override
	public Country insert(Country country) {
		findByNameNonExists(country);
		return repository.save(country);
	}

	@Override
	public List<Country> listAll() {
		List<Country> lista = repository.findAll();
		if (lista.isEmpty()) {
			throw new ObjectNotFound("Nenhum país cadastrado");
		}
		return repository.findAll();
	}

	@Override
	public Country update(Country country) {
		findById(country.getId());
		findByNameNonExists(country);
		return repository.save(country);
	}

	@Override
	public void delete(Integer id) {
		Country country = findById(id);
		repository.delete(country);
	}

	@Override
	public List<Country> findByName(String name) {
		List<Country> lista = repository.findByNameIgnoreCase(name);
		if (lista.isEmpty()) {
			throw new ObjectNotFound("Nenhum país encontrado com o nome %s".formatted(name));
		}
		return lista;
	}

}
