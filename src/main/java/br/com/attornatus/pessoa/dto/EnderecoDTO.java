package br.com.attornatus.pessoa.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.attornatus.pessoa.entity.Pessoa;
import lombok.Data;

@Data
@Component
public class EnderecoDTO {

	private String cep;
	private String logradouro;
	private int numero;
	private String cidade;
	@JsonIgnore
	private Pessoa pessoa;
	
}
