package br.com.attornatus.pessoa.repository.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.attornatus.pessoa.entity.Pessoa;

@Repository
public interface IPessoaRepository extends JpaRepository<Pessoa, Long> {
	
	@Query("from Pessoa p Left join p.enderecos")
	Optional<Pessoa> findAll(Long pessoaId);
}
