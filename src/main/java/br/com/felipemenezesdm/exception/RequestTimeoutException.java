package br.com.felipemenezesdm.exception;

import org.springframework.http.HttpStatus;

public class RequestTimeoutException extends RuntimeException {
    public RequestTimeoutException() {
        super();
    }

    public RequestTimeoutException(String message) {
        super(message);
    }

    public RequestTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestTimeoutException(Throwable cause) {
        super(cause);
    }

    public HttpStatus getStatusCode() {
        return HttpStatus.REQUEST_TIMEOUT;
    }
}
