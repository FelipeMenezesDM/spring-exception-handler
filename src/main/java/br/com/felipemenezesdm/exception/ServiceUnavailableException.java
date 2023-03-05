package br.com.felipemenezesdm.exception;

import org.springframework.http.HttpStatus;

public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException() {
        super();
    }

    public ServiceUnavailableException(String message) {
        super(message);
    }

    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceUnavailableException(Throwable cause) {
        super(cause);
    }

    public HttpStatus getStatusCode() {
        return HttpStatus.SERVICE_UNAVAILABLE;
    }
}
