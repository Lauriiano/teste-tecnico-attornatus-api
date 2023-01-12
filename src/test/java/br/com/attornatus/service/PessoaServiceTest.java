package br.com.attornatus.service;

import br.com.attornatus.pessoa.dto.PessoaDTO;
import br.com.attornatus.pessoa.entity.Endereco;
import br.com.attornatus.pessoa.entity.Pessoa;
import br.com.attornatus.pessoa.exception.EntidadeNaoEncontradaException;
import br.com.attornatus.pessoa.exception.ParametrosNaoEnviadosException;
import br.com.attornatus.pessoa.repository.interfaces.IEnderecoRepository;
import br.com.attornatus.pessoa.repository.interfaces.IPessoaRepository;
import br.com.attornatus.pessoa.service.PessoaService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("PessoaServiceTest")
public class PessoaServiceTest {
	
	@MockBean
	private IPessoaRepository pessoaRepository;
	
	@MockBean
	private IEnderecoRepository enderecoRepository;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Captor
	private ArgumentCaptor<Pessoa> captor;
	
	private ModelMapper mapper = new ModelMapper();
	
	@Test
	@DisplayName("deve criar uma pessoa")
	public void should_be_able_to_create_a_new_people() {
		PessoaDTO pessoaDTO = new PessoaDTO();
		Pessoa pessoa = criarPessoa();
		Mockito.when(pessoaRepository.save(ArgumentMatchers.eq(mapper.map(pessoaDTO, Pessoa.class)))).thenReturn(pessoa);
		pessoaService.cadastrarPessoa(pessoaDTO);
		Mockito.verify(pessoaRepository, Mockito.times(1)).save(ArgumentMatchers.any(Pessoa.class));
	}
	
	@Test
	@DisplayName("deve listar pessoas e não retornar endereco")
	public void should_be_able_to_list_a_people_and_not_address_return() {
		List<Pessoa> pessoas = pegarListarDePessoas();
		
		Mockito.when(pessoaRepository.findAll()).thenReturn(pessoas);
		List<PessoaDTO> pessoaDTO = pessoaService.listarPessoas();
		
		PessoaDTO pessoaDto = pessoaDTO.get(0);
		
		Mockito.verify(pessoaRepository, Mockito.times(1)).findAll();
		Assertions.assertEquals(1L, pessoaDto.getId());
		Assertions.assertNull(pessoaDto.getEndereco());
		
	}
	
	@Test
	@DisplayName("deve listar pessoas e retornar endereco principal")
	public void should_be_able_to_list_a_people_and_address_return() {
		
		List<Pessoa> pessoas = pegarListarDePessoas();
		
		Mockito.when(pessoaRepository.findAll()).thenReturn(pessoas);
		List<PessoaDTO> pessoaDTO = pessoaService.listarPessoas();
		
		PessoaDTO pessoaDto = pessoaDTO.get(1);
		
		Mockito.verify(pessoaRepository, Mockito.times(1)).findAll();
		Assertions.assertEquals(2L, pessoaDto.getId());
		Assertions.assertNotNull(pessoaDto.getEndereco());
		
	}
	
	@Test
	@DisplayName("Deve retornar uma pessoa pelo id sem o endereco")
	public void should_be_able_to_return_people_by_id_without_address() {
		List<Pessoa> pessoas = pegarListarDePessoas();
		Optional<Pessoa> pessoaOptional = Optional.of(pessoas.get(0));
		
		Mockito.when(pessoaRepository.findById(pessoaOptional.get().getId())).thenReturn(pessoaOptional);
		PessoaDTO pessoaDto = pessoaService.consultarPessoa(pessoaOptional.get().getId());
		
		Mockito.verify(pessoaRepository, Mockito.times(1)).findById(pessoaOptional.get().getId());
		Assertions.assertEquals(pessoaOptional.get().getId(), pessoaDto.getId());
		Assertions.assertNull(pessoaDto.getEndereco());
	}
	
	@Test
	@DisplayName("Deve retornar uma pessoa pelo id sem o endereco")
	public void should_be_able_to_return_person_by_id_with_address() {
		List<Pessoa> pessoas = pegarListarDePessoas();
		Optional<Pessoa> pessoaOptional = Optional.of(pessoas.get(1));
		
		Mockito.when(pessoaRepository.findById(1L)).thenReturn(pessoaOptional);
		PessoaDTO pessoaDto = pessoaService.consultarPessoa(1L);
		
		Mockito.verify(pessoaRepository, Mockito.times(1)).findById(1L);
		Assertions.assertEquals(2L, pessoaDto.getId());
		Assertions.assertNotNull(pessoaDto.getEndereco());
	}
	
	@Test
	@DisplayName("Não deve retornar uma pessoa pelo id")
	public void should_not_be_able_to_return_person_by_id() {		
		Mockito.when(pessoaRepository.findById(Mockito.any()))
		.thenThrow(EntidadeNaoEncontradaException.class);

		try {			
			pessoaService.consultarPessoa(1L);
			Mockito.verifyNoInteractions(pessoaRepository);
		}catch(EntidadeNaoEncontradaException e) {}
		
	}
	
	@Test
	@DisplayName("Não deve editar uma pessoa")
	public void should_not_edit_a_person() {
		try {
			Mockito.when(pessoaService.atualizarPessoa(1L, new HashMap<String,Object>()))
			.thenThrow(ParametrosNaoEnviadosException.class);
			Mockito.verifyNoInteractions(pessoaRepository);
		}catch(ParametrosNaoEnviadosException e) {}
	}
	
	@Test
	@DisplayName("Não deve editar uma pessoa se não achar no banco")
	public void not_should_edit_a_person_if_not_find_in_base() {
		
		Mockito.when(pessoaRepository.findById(Mockito.any()))
		.thenThrow(EntidadeNaoEncontradaException.class);
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("teste", "teste");
		
		try {			
			pessoaService.atualizarPessoa(1L, map);
			Mockito.verifyNoInteractions(pessoaRepository);
		}catch(EntidadeNaoEncontradaException e) {}
		
	}
	
	@Test
	@DisplayName("Deve editar uma pessoa")
	public void should_edit_a_person() {
		
		List<Pessoa> pessoas = pegarListarDePessoas();
		Mockito.when(pessoaRepository.findById(pessoas.get(0).getId()))
			.thenReturn(Optional.of(pessoas.get(0)));
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("nome", pessoas.get(1).getNome());
		
		pessoaService.atualizarPessoa(pessoas.get(0).getId(), map);
		
		Mockito.verify(pessoaRepository).save(captor.capture());
		Pessoa pessoa = captor.getValue();
		Assertions.assertEquals(pessoa.getNome(), pessoas.get(1).getNome());
		
	}
	
	private List<Pessoa> pegarListarDePessoas() {
		List<Pessoa> pessoas = new ArrayList<>();
		List<Endereco> enderecos = new ArrayList<>();
		Endereco endereco = new Endereco();
		enderecos.add(endereco);
		
		endereco.setId(1L);
		endereco.setCep("22770-650");
		endereco.setCidade("Rio de Janeiro");
		endereco.setLogradouro("Rua x");
		endereco.setNumero(33);
		endereco.setPrincipal(true); // true para que o teste passe, caso false não retorna endereço principal
		
		Pessoa pessoa1 = new Pessoa();
		pessoa1.setId(1L);
		pessoa1.setNome("Acran Laureano");
		pessoa1.setNascimento("09/09/1991");
		
		Pessoa pessoa2 = new Pessoa();
		pessoa2.setId(2L);
		pessoa2.setNome("Lara Ribeiro");
		pessoa2.setNascimento("01/04/2015");
		pessoa2.setEnderecos(enderecos);
		
		pessoas.add(pessoa1);
		pessoas.add(pessoa2);
		
		return pessoas;
	}
	
	private Pessoa criarPessoa() {
		Pessoa pessoa = Mockito.mock(Pessoa.class);
		return pessoa;
	}
	
}
