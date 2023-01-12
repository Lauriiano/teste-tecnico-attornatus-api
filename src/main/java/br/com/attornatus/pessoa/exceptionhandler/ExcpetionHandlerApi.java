package br.com.attornatus.pessoa.exceptionhandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.attornatus.pessoa.exception.EntidadeExistenteException;
import br.com.attornatus.pessoa.exception.EntidadeNaoEncontradaException;
import br.com.attornatus.pessoa.exception.ParametrosNaoEnviadosException;

@ControllerAdvice
public class ExcpetionHandlerApi {

	private ExceptionsBody bodyExcpetion;
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> entidadeNaoEncontradaException(EntidadeNaoEncontradaException e) {
		bodyExcpetion = ExceptionsBody.builder()
				.dataHora(LocalDateTime.now())
				.mensagem(e.getMessage())
				.build();
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bodyExcpetion);
	}
	
	@ExceptionHandler(ParametrosNaoEnviadosException.class)
	public ResponseEntity<?> ParametrosNaoEnviadosException(ParametrosNaoEnviadosException e) {
		bodyExcpetion = ExceptionsBody.builder()
				.dataHora(LocalDateTime.now())
				.mensagem(e.getMessage())
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bodyExcpetion);
	}
	
	@ExceptionHandler(EntidadeExistenteException.class)
	public ResponseEntity<?> EntidadeExistenteException(EntidadeExistenteException e) {
		bodyExcpetion = ExceptionsBody.builder()
				.dataHora(LocalDateTime.now())
				.mensagem(e.getMessage())
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bodyExcpetion);
	}
	
}
