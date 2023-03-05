package br.com.felipemenezesdm.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@ControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class ApiExceptionHandler {
    @Autowired
    ObjectMapper objectMapper;

    @ExceptionHandler(BadGatewayException.class)
    protected ResponseEntity<Object> handler(BadGatewayException e) {
        return buildResponseEntity(e, e.getStatusCode());
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handler(BadRequestException e) {
        return buildResponseEntity(e, e.getStatusCode());
    }

    @ExceptionHandler(ForbiddenException.class)
    protected ResponseEntity<Object> handler(ForbiddenException e) {
        return buildResponseEntity(e, e.getStatusCode());
    }

    @ExceptionHandler(GatewayTimeoutException.class)
    protected ResponseEntity<Object> handler(GatewayTimeoutException e) {
        return buildResponseEntity(e, e.getStatusCode());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handler(IllegalArgumentException e) {
        return buildResponseEntity(e, e.getStatusCode());
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    protected ResponseEntity<Object> handler(MethodNotAllowedException e) {
        return buildResponseEntity(e, e.getStatusCode());
    }

    @ExceptionHandler(NotAcceptableException.class)
    protected ResponseEntity<Object> handler(NotAcceptableException e) {
        return buildResponseEntity(e, e.getStatusCode());
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handler(NotFoundException e) {
        return buildResponseEntity(e, e.getStatusCode());
    }

    @ExceptionHandler(NotImplementedException.class)
    protected ResponseEntity<Object> handler(NotImplementedException e) {
        return buildResponseEntity(e, e.getStatusCode());
    }

    @ExceptionHandler(RequestTimeoutException.class)
    protected ResponseEntity<Object> handler(RequestTimeoutException e) {
        return buildResponseEntity(e, e.getStatusCode());
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    protected ResponseEntity<Object> handler(ServiceUnavailableException e) {
        return buildResponseEntity(e, e.getStatusCode());
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<Object> handler(UnauthorizedException e) {
        return buildResponseEntity(e, e.getStatusCode());
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    protected ResponseEntity<Object> handler(UnprocessableEntityException e) {
        return buildResponseEntity(e, e.getStatusCode());
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    protected ResponseEntity<Object> handler(HttpClientErrorException.Forbidden e) throws JsonProcessingException {
        return buildResponseEntity(e.getResponseBodyAsString(), e.getStatusCode());
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    protected ResponseEntity<Object> handler(HttpClientErrorException.Unauthorized e) throws JsonProcessingException {
        return buildResponseEntity(e.getResponseBodyAsString(), e.getStatusCode());
    }

    private ResponseEntity<Object> buildResponseEntity(Object payload, HttpStatus httpStatus) {
        return new ResponseEntity<>(payload, httpStatus);
    }

    private ResponseEntity<Object> buildResponseEntity(String payload, HttpStatus status) throws JsonProcessingException {
        return new ResponseEntity<>(objectMapper.readTree(payload), status);
    }
}
