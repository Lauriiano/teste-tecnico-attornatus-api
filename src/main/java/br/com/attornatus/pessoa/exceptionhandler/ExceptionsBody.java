package br.com.attornatus.pessoa.exceptionhandler;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExceptionsBody {

	private LocalDateTime dataHora;
	private String mensagem;
	
}
