package br.com.nubank.capital.gain.integration.exception;

import br.com.nubank.capital.gain.domain.exception.OperationException;

public class StdoutException extends OperationException {

    public StdoutException(Exception exception) {
        super(exception);
    }

}
