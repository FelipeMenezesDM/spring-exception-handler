package br.com.felipemenezesdm.exception;

import br.com.felipemenezesdm.dto.ExceptionDetailFieldDTO;
import br.com.felipemenezesdm.dto.ExceptionResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import static java.time.Instant.now;
import static java.time.ZoneId.systemDefault;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@ControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class ApiExceptionHandler {
    @Autowired
    ObjectMapper objectMapper;

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handler(MethodArgumentNotValidException e) {
        return buildResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handler(ConstraintViolationException e, WebRequest request) {
        return buildResponseEntity(e, HttpStatus.BAD_REQUEST, ((ServletWebRequest) request).getRequest());
    }

    private ResponseEntity<Object> buildResponseEntity(Throwable e, HttpStatus httpStatus) {
        return buildResponseEntity(e, httpStatus, null);
    }

    private ResponseEntity<Object> buildResponseEntity(Throwable e, HttpStatus httpStatus, HttpServletRequest request) {
        ExceptionResponseDTO dto = new ExceptionResponseDTO();
        List<ExceptionDetailFieldDTO> details = new ArrayList<>();
        HashMap<String, String> links = new HashMap<>();
        StringBuilder endPoint = new StringBuilder();

        if(!Objects.isNull(request)) {
            String queryString = request.getQueryString().isEmpty() ? "" : ("?").concat(request.getQueryString());

            endPoint.append(request.getRequestURI());
            links.put("self", request.getRequestURL().toString().concat(queryString));

            if(e instanceof ConstraintViolationException) {
                ((ConstraintViolationException) e).getConstraintViolations().forEach((violation) -> {
                    ExceptionDetailFieldDTO field = new ExceptionDetailFieldDTO();
                    field.setField(violation.getPropertyPath().toString());
                    field.setValue(violation.getInvalidValue().toString());
                    field.setMessage(violation.getMessage());

                    details.add(field);
                });
            }
        }

        dto.setPath(endPoint.toString());
        dto.setDetails(details);
        dto.setLinks(links);
        dto.setStatus(httpStatus.value());
        dto.setError(httpStatus.name());
        dto.setMessage(e.getMessage());
        dto.setTimestamp(ofPattern(DATE_TIME_FORMAT).withZone(systemDefault()).format(now()));

        return new ResponseEntity<>(dto, httpStatus);
    }

    private ResponseEntity<Object> buildResponseEntity(String payload, HttpStatus status) throws JsonProcessingException {
        return new ResponseEntity<>(objectMapper.readTree(payload), status);
    }
}
