package br.com.trier.springvespertino.resources_dado;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springvespertino.models.Dado;

@RestController
@RequestMapping("/dado")
public class Dados {

	@GetMapping()
	public String qtdeDadosLancados(@RequestParam Integer dados, @RequestParam Integer aposta) {
		List<Integer> dado = new ArrayList<>();
		
		StringBuilder sb = new StringBuilder();
		
		if(dados < 1 || dados > 4 ) {
			throw new IllegalArgumentException("Quantidade de dados inválida");
		} 
		if(aposta < dados || aposta > dados * 6){
			throw new IllegalArgumentException("Aposta inválida");
		}

		Integer min = 1;
		Integer max = 6;
		Integer soma = 0;

		Random random = new Random();

		for (int i = 0; i < dados; i++) {
			int randomDado = random.nextInt(max - min + 1) + min;
			dado.add(randomDado);
			soma += randomDado;
			sb.append(String.format("Dado %s : %s\n", i+1, randomDado));
		}
		
		sb.append(String.format("Soma dos dados : %s\n", soma));
		
		if(aposta > soma) {
			int tempSoma = soma;
			soma = aposta;
			aposta = tempSoma;
		}
		
		Integer porcentagem = aposta * 100 / soma;
		sb.append(String.format("Porcentagem em relação ao resultado dos dados : %s%%", porcentagem));
		
		
		return sb.toString();
	}

}
