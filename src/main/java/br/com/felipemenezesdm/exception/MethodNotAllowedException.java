package br.com.felipemenezesdm.exception;

import org.springframework.http.HttpStatus;

public class MethodNotAllowedException extends RuntimeException {
    public MethodNotAllowedException() {
        super();
    }

    public MethodNotAllowedException(String message) {
        super(message);
    }

    public MethodNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodNotAllowedException(Throwable cause) {
        super(cause);
    }

    public HttpStatus getStatusCode() {
        return HttpStatus.METHOD_NOT_ALLOWED;
    }
}
