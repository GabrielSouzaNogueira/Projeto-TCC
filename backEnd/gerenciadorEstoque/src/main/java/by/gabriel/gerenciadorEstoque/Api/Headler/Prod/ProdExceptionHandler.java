package by.gabriel.gerenciadorEstoque.Api.Headler.Prod;

import by.gabriel.gerenciadorEstoque.Api.DTO.Response.ResponseDTO;
import by.gabriel.gerenciadorEstoque.Domain.ExceptionProd.CostOrSellBellowZero;
import by.gabriel.gerenciadorEstoque.Domain.ExceptionProd.HigherCostToSellException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ProdExceptionHandler {

    @ExceptionHandler(HigherCostToSellException.class)
    public ResponseEntity<ResponseDTO> handlerHigherCostToSell(HigherCostToSellException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ResponseDTO(false, "Custo maior que venda", "HIGHER_COST_TO_SELL", Instant.now().toString()));

    }

    @ExceptionHandler(CostOrSellBellowZero.class)
    public ResponseEntity<ResponseDTO> handlerostOrSellBellowZero(HigherCostToSellException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ResponseDTO(false, "Preco de custo ou venda abaixo de zero ou igual a zero", "COST_SELL_BELLOW_ZERO", Instant.now().toString()));

    }

}
