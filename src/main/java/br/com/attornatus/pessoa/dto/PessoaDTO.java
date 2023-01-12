package br.com.attornatus.pessoa.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.attornatus.pessoa.entity.Endereco;
import lombok.Data;

@Component
@Data
public class PessoaDTO {
	private Long id;
	private String nome;
	private String nascimento;
	@JsonIgnore
	private List<Endereco> enderecos = new ArrayList<>();
	private Endereco endereco;
}
