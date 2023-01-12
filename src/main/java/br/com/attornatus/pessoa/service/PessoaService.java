package br.com.attornatus.pessoa.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.attornatus.pessoa.dto.EnderecoDTO;
import br.com.attornatus.pessoa.dto.PessoaDTO;
import br.com.attornatus.pessoa.entity.Endereco;
import br.com.attornatus.pessoa.entity.Pessoa;
import br.com.attornatus.pessoa.exception.EntidadeExistenteException;
import br.com.attornatus.pessoa.exception.EntidadeNaoEncontradaException;
import br.com.attornatus.pessoa.repository.interfaces.IEnderecoRepository;
import br.com.attornatus.pessoa.repository.interfaces.IPessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private IPessoaRepository pessoaRepository;
	
	@Autowired
	private IEnderecoRepository enderecoRepository;
	
	private ModelMapper mapper = new ModelMapper();
	
	public PessoaDTO cadastrarPessoa(PessoaDTO pessoaDto) {
		pessoaDto.setId(null);
		Pessoa pessoa = mapper.map(pessoaDto, Pessoa.class);
		pessoa = this.pessoaRepository.save(pessoa);
		pessoaDto.setId(pessoa.getId());
		return pessoaDto;
	}
	
	public List<PessoaDTO> listarPessoas() {
		List<Pessoa> pessoas = this.pessoaRepository.findAll();
		return pessoas.stream()
				.map(pessoa -> pegarEnderecoPrincipal(pessoa))
				.collect(Collectors.toList());
	}
	
	public PessoaDTO consultarPessoa(Long id) {
		Pessoa pessoa = this.pessoaRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));
		
		PessoaDTO pessoaDto = pegarEnderecoPrincipal(pessoa);
		
		return pessoaDto;
	}
	
	public PessoaDTO atualizarPessoa(Long id, PessoaDTO pessoaDto) {
		pessoaDto.setId(id);
		Pessoa pessoa = mapper.map(pessoaDto, Pessoa.class);
		pessoa = this.pessoaRepository.save(pessoa);
		return mapper.map(pessoa, PessoaDTO.class);
	}
	
	public void cadastrarEndereco(Long id, EnderecoDTO enderecoDto) { 
		PessoaDTO pessoaDto = this.consultarPessoa(id);
		Endereco endereco = enderecoRepository.consultarPorPessoaAndCep(enderecoDto.getCep(), id);
		if(endereco != null) {
			throw new EntidadeExistenteException("Endereço ja cadastrado para esse usuário");
		}
		enderecoDto.setPessoa(mapper.map(pessoaDto, Pessoa.class));
		enderecoRepository.save(mapper.map(enderecoDto, Endereco.class));
	}
	
	public List<EnderecoDTO> listarEnderecos(Long id) {
		PessoaDTO pessoaDto = this.consultarPessoa(id);
		List<Endereco> enderecos = enderecoRepository.findByPessoa(mapper.map(pessoaDto, Pessoa.class));
		return enderecos.stream()
				.map(endereco -> mapper.map(endereco, EnderecoDTO.class))
				.collect(Collectors.toList());
	}

	public void informarEnderecoPrincipal(Long pessoaId, EnderecoDTO enderecoDto) {
		Endereco endereco = enderecoRepository.findEnderecoByCep(enderecoDto.getCep());
		if(endereco == null) {
			throw new EntidadeNaoEncontradaException("Endereço não encontrado");
		}
		enderecoRepository.retirarEnderecoPrincipal(pessoaId);
		endereco.setPrincipal(true);
		enderecoRepository.save(endereco);
	}

	private PessoaDTO pegarEnderecoPrincipal(Pessoa pessoa) {
		PessoaDTO pessoaDto = mapper.map(pessoa, PessoaDTO.class);
		List<Endereco> enderecos = pessoa.getEnderecos();
		
		if(enderecos != null) {
			for(Endereco endereco: enderecos) {
				if(endereco.isPrincipal()) {
					pessoaDto.setEndereco(endereco);
				}
			}
		}
		return pessoaDto;
	}
}
