package com.project.food.exceptionhandler;

import com.project.food.domain.exception.EntidadeEmUsoException;
import com.project.food.domain.exception.EntidadeNaoEncontradaException;
import com.project.food.domain.exception.NegocioException;
import com.project.food.domain.model.enums.ProblemaTipo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(
            EntidadeNaoEncontradaException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemaTipo problemaTipo = ProblemaTipo.ENTIDADE_NAO_ENCONTRADA;
        String detail = ex.getMessage();

        Problema problema = createProblemaBuilder(status, problemaTipo, detail).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(),
                status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(
            EntidadeEmUsoException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        ProblemaTipo problemaTipo = ProblemaTipo.ENTIDADE_EM_USO;
        String detail = ex.getMessage();

        Problema problema = createProblemaBuilder(status, problemaTipo, detail).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemaTipo problemaTipo = ProblemaTipo.ERRO_NEGOCIO;
        String detail = ex.getMessage();

        Problema problema = createProblemaBuilder(status, problemaTipo, detail).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);

    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if (body == null) {
            body = Problema.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if (body instanceof String) {
            body = Problema.builder()
                    .title((String) body)
                    .status(status.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problema.ProblemaBuilder createProblemaBuilder(HttpStatus status,
                                                           ProblemaTipo problemaTipo,
                                                           String detail) {
        return Problema.builder()
                .status(status.value())
                .type(problemaTipo.getUri())
                .title(problemaTipo.getTitle())
                .detail(detail);

    }
}
