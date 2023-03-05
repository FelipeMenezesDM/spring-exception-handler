package br.com.felipemenezesdm.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }

    public HttpStatus getStatusCode() {
        return HttpStatus.UNAUTHORIZED;
    }
}
