package br.com.attornatus.pessoa.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PessoaRequest {
	
	@NotNull
	public String nome;
	
	@NotNull
	public String nascimento;

}
