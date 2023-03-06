package br.com.felipemenezesdm.exception;

import org.springframework.http.HttpStatus;

public class BadGatewayException extends RuntimeException {

    public BadGatewayException() {
        super();
    }

    public BadGatewayException(String message) {
        super(message);
    }

    public BadGatewayException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadGatewayException(Throwable cause) {
        super(cause);
    }

    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_GATEWAY;
    }
}
