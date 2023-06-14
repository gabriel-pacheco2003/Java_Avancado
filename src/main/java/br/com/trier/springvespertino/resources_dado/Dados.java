package br.com.trier.springvespertino.resources_dado;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dado")
public class Dados {

	@GetMapping ("/{dados}/{aposta}")
	public List<Integer> qtdeDadosLancados(@PathVariable Integer dados, @PathVariable Integer aposta) {
		List<Integer> dado = new ArrayList<>();
		
		int min = 1 * dados;
		int max = 6 * dados;
		
		Random random = new Random();
		
		for(int i = 0; i < dados; i++) {
		int randomDado = random.nextInt(max - min + 1) + min;
		dado.add(randomDado);
		}
		return dado;
	}

}
