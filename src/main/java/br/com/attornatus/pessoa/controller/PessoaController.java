package br.com.attornatus.pessoa.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.attornatus.pessoa.dto.EnderecoDTO;
import br.com.attornatus.pessoa.dto.PessoaDTO;
import br.com.attornatus.pessoa.models.PessoaRequest;
import br.com.attornatus.pessoa.service.PessoaService;

@Controller
@RequestMapping("attornatus/api/pessoa")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;
	
	private ModelMapper mapper = new ModelMapper();
	
	@PostMapping
	public ResponseEntity<PessoaDTO> cadastrarPessoa(@RequestBody PessoaRequest pessoaRequest) {
		PessoaDTO pessoaResponse = pessoaService.cadastrarPessoa(mapper.map(pessoaRequest, PessoaDTO.class));
		return new ResponseEntity<>(pessoaResponse, HttpStatus.CREATED);
		
	}
	
	@GetMapping
	public ResponseEntity<List<PessoaDTO>> listarPessoas() {
		return ResponseEntity.ok(pessoaService.listarPessoas());
	}
	
	@GetMapping("/{pessoaId}")
	public ResponseEntity<PessoaDTO> consultarPessoa(@PathVariable Long pessoaId) {
		return ResponseEntity.ok(pessoaService.consultarPessoa(pessoaId));
	}
	
	@PutMapping("/{pessoaId}")
	public ResponseEntity<PessoaDTO> editarPessoa(
				@PathVariable Long pessoaId,
				@RequestBody PessoaRequest pessoaRequest
			) {
		return ResponseEntity.ok(pessoaService.atualizarPessoa(pessoaId, mapper.map(pessoaRequest, PessoaDTO.class)));
	}
	
	@GetMapping("/endereco/{pessoaId}")
	public ResponseEntity<List<EnderecoDTO>> consultarEnderecos(@PathVariable Long pessoaId) {
		return ResponseEntity.ok(pessoaService.listarEnderecos(pessoaId));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/endereco/cadastro/{pessoaId}")
	public void cadastrarEnderecoParaPessoa(
				@PathVariable Long pessoaId,
				@RequestBody EnderecoDTO enderecoDto
			) {
		pessoaService.cadastrarEndereco(pessoaId, enderecoDto);		
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/endereco/principal/{pessoaId}")
	public void informarEnderecoPrincipal(
				@PathVariable Long pessoaId,
				@RequestBody EnderecoDTO enderecoDto
			) {
		pessoaService.informarEnderecoPrincipal(pessoaId, enderecoDto);		
	}
	
}
