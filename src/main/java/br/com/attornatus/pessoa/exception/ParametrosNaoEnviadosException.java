package br.com.attornatus.pessoa.exception;

public class ParametrosNaoEnviadosException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ParametrosNaoEnviadosException(String message) {
		super(message);
	}

}
