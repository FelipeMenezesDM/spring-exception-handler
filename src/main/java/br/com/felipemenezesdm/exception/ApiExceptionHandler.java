package br.com.felipemenezesdm.exception;

import br.com.felipemenezesdm.props.ApplicationProps;
import br.com.felipemenezesdm.dto.ExceptionDetailFieldDTO;
import br.com.felipemenezesdm.dto.ExceptionResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import static java.time.Instant.now;
import static java.time.ZoneId.systemDefault;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@ControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class ApiExceptionHandler {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ApplicationProps applicationProps;

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    @ExceptionHandler(BadGatewayException.class)
    protected ResponseEntity<Object> handler(BadGatewayException e, WebRequest request) {
        return buildResponseEntity(e, e.getStatusCode(), request);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handler(BadRequestException e, WebRequest request) {
        return buildResponseEntity(e, e.getStatusCode(), request);
    }

    @ExceptionHandler(ForbiddenException.class)
    protected ResponseEntity<Object> handler(ForbiddenException e, WebRequest request) {
        return buildResponseEntity(e, e.getStatusCode(), request);
    }

    @ExceptionHandler(GatewayTimeoutException.class)
    protected ResponseEntity<Object> handler(GatewayTimeoutException e, WebRequest request) {
        return buildResponseEntity(e, e.getStatusCode(), request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handler(IllegalArgumentException e, WebRequest request) {
        return buildResponseEntity(e, e.getStatusCode(), request);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    protected ResponseEntity<Object> handler(MethodNotAllowedException e, WebRequest request) {
        return buildResponseEntity(e, e.getStatusCode(), request);
    }

    @ExceptionHandler(NotAcceptableException.class)
    protected ResponseEntity<Object> handler(NotAcceptableException e, WebRequest request) {
        return buildResponseEntity(e, e.getStatusCode(), request);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handler(NotFoundException e, WebRequest request) {
        return buildResponseEntity(e, e.getStatusCode(), request);
    }

    @ExceptionHandler(NotImplementedException.class)
    protected ResponseEntity<Object> handler(NotImplementedException e, WebRequest request) {
        return buildResponseEntity(e, e.getStatusCode(), request);
    }

    @ExceptionHandler(RequestTimeoutException.class)
    protected ResponseEntity<Object> handler(RequestTimeoutException e, WebRequest request) {
        return buildResponseEntity(e, e.getStatusCode(), request);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    protected ResponseEntity<Object> handler(ServiceUnavailableException e, WebRequest request) {
        return buildResponseEntity(e, e.getStatusCode(), request);
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<Object> handler(UnauthorizedException e, WebRequest request) {
        return buildResponseEntity(e, e.getStatusCode(), request);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    protected ResponseEntity<Object> handler(UnprocessableEntityException e, WebRequest request) {
        return buildResponseEntity(e, e.getStatusCode(), request);
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    protected ResponseEntity<Object> handler(HttpClientErrorException.Forbidden e) throws JsonProcessingException {
        return buildResponseEntity(e.getResponseBodyAsString(), e.getStatusCode());
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    protected ResponseEntity<Object> handler(HttpClientErrorException.Unauthorized e) throws JsonProcessingException {
        return buildResponseEntity(e.getResponseBodyAsString(), e.getStatusCode());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handler(ConstraintViolationException e, WebRequest request) {
        return buildResponseEntity(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<Object> handler(MissingServletRequestParameterException e, WebRequest request) {
        return buildResponseEntity(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<Object> handler(NoHandlerFoundException e, WebRequest request) {
        return buildResponseEntity(e, HttpStatus.NOT_FOUND, request);
    }

    private ResponseEntity<Object> buildResponseEntity(Throwable e, HttpStatus httpStatus, WebRequest webRequest) {
        HttpServletRequest request = castToRequest(webRequest);
        ExceptionResponseDTO dto = new ExceptionResponseDTO();
        List<ExceptionDetailFieldDTO> details = new ArrayList<>();
        HashMap<String, String> links = new HashMap<>();
        StringBuilder endPoint = new StringBuilder();

        if(!Objects.isNull(request)) {
            endPoint.append(request.getRequestURI());
            links.put("self", request.getRequestURL().toString().concat(Objects.isNull(request.getQueryString()) ? "" : ("?").concat(request.getQueryString())));

            if(e instanceof ConstraintViolationException) {
                ((ConstraintViolationException) e).getConstraintViolations().forEach((violation) -> {
                    ExceptionDetailFieldDTO field = new ExceptionDetailFieldDTO();
                    violation.getPropertyPath().iterator().forEachRemaining(name -> field.setField(name.toString()));
                    field.setMessage(violation.getMessage());
                    setFieldValue(field, violation.getInvalidValue());
                    details.add(field);
                });
            }

            if(e instanceof MissingServletRequestParameterException) {
                MissingServletRequestParameterException error = (MissingServletRequestParameterException) e;
                ExceptionDetailFieldDTO field = new ExceptionDetailFieldDTO();
                field.setField(error.getParameterName());
                field.setMessage(error.getMessage());
                details.add(field);
            }
        }

        dto.setPath(endPoint.toString());
        dto.setDetails(details);
        dto.setLinks(links);
        dto.setStatus(httpStatus.value());
        dto.setError(httpStatus.name());
        dto.setMessage(getDefaultMessage(String.valueOf(httpStatus.value())));
        dto.setTimestamp(ofPattern(DATE_TIME_FORMAT).withZone(systemDefault()).format(now()));

        return new ResponseEntity<>(dto, httpStatus);
    }

    private ResponseEntity<Object> buildResponseEntity(String payload, HttpStatus status) throws JsonProcessingException {
        return new ResponseEntity<>(objectMapper.readTree(payload), status);
    }

    private void setFieldValue(ExceptionDetailFieldDTO field, Object value) {
        if(value instanceof HttpHeaders) {
            Optional.ofNullable(((HttpHeaders) value).get(field.getField())).ifPresent(h -> field.setValue(h.get(0)));
            return;
        }

        if(value instanceof Map) {
            Optional.ofNullable(((Map<?, ?>) value).get(field.getField())).ifPresent(h -> field.setValue(h.toString()));
            return;
        }

        field.setValue((String) value);
    }

    private HttpServletRequest castToRequest(WebRequest request) {
        return Objects.isNull(request) ? null : ((ServletWebRequest) request).getRequest();
    }

    private String getDefaultMessage(String httpStatusCode) {
        if(!Objects.isNull(applicationProps.getExceptions()) && applicationProps.getExceptions().containsKey(httpStatusCode)) {
            return applicationProps.getExceptions().get(httpStatusCode);
        }

        return null;
    }
}
