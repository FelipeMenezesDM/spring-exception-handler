package br.com.felipemenezesdm.exception;

import org.springframework.http.HttpStatus;

public class UnprocessableEntityException extends RuntimeException {
    public UnprocessableEntityException() {
        super();
    }

    public UnprocessableEntityException(String message) {
        super(message);
    }

    public UnprocessableEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnprocessableEntityException(Throwable cause) {
        super(cause);
    }

    public HttpStatus getStatusCode() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }
}
