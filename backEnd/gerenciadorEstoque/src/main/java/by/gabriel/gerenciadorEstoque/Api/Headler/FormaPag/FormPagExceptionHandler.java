package by.gabriel.gerenciadorEstoque.Api.Headler.FormaPag;

import by.gabriel.gerenciadorEstoque.Api.DTO.Response.ResponseDTO;
import by.gabriel.gerenciadorEstoque.Domain.ExceptionFormPag.FormPagAlreadyExistException;
import by.gabriel.gerenciadorEstoque.Domain.ExceptionFormPag.FormPagNotExistException;
import by.gabriel.gerenciadorEstoque.Domain.ExceptionFormPag.FormPagNotNullException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@Order(1)
public class FormPagExceptionHandler {

    @ExceptionHandler(FormPagNotNullException.class)
    public ResponseEntity<ResponseDTO> handlerFormPagNotNull(FormPagNotNullException ex) {

        System.out.println("ERRO: " + ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO(false, "FORMPAG_NOT_NULL", Instant.now().toString()));

    }

    @ExceptionHandler(FormPagNotExistException.class)
    public ResponseEntity<ResponseDTO> handlerFormPagNotExist(FormPagNotExistException ex) {

        System.out.println("ERRO: " + ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO(false, "FORMPAG_NOT_EXIST", Instant.now().toString()));
    }

    @ExceptionHandler(FormPagAlreadyExistException.class)
    public ResponseEntity<ResponseDTO> handlerFormPagAlreadyExist(FormPagAlreadyExistException ex) {

        System.out.println("ERRO: " + ex);

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseDTO(false,"FORMPAG_ALREADY_EXIST", Instant.now().toString()));
    }




}
