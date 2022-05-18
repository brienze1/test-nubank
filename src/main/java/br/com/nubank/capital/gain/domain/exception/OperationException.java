package br.com.nubank.capital.gain.domain.exception;

public class OperationException extends RuntimeException {

	public OperationException(Exception exception) {
		super("Operation set could not be executed", exception);
	}

}
