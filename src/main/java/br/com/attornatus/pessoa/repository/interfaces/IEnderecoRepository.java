package br.com.attornatus.pessoa.repository.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.attornatus.pessoa.entity.Endereco;
import br.com.attornatus.pessoa.entity.Pessoa;

@Repository
public interface IEnderecoRepository extends JpaRepository<Endereco, Long> {
	Endereco findEnderecoByCep(String cep);
	
	List<Endereco> findByPessoa(Pessoa pessoa);
	
	@Query("from Endereco where cep = :cep and pessoa.id = :pessoaId")
	Endereco consultarPorPessoaAndCep(String cep, Long pessoaId);

	@Modifying
	@Transactional
	@Query("update Endereco e set e.principal = FALSE where pessoa.id = ?1")
	int retirarEnderecoPrincipal(Long id);
	
}
