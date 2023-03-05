package br.com.felipemenezesdm.exception;

import org.springframework.http.HttpStatus;

public class GatewayTimeoutException extends RuntimeException {
    public GatewayTimeoutException() {
        super();
    }

    public GatewayTimeoutException(String message) {
        super(message);
    }

    public GatewayTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public GatewayTimeoutException(Throwable cause) {
        super(cause);
    }

    public HttpStatus getStatusCode() {
        return HttpStatus.GATEWAY_TIMEOUT;
    }
}
