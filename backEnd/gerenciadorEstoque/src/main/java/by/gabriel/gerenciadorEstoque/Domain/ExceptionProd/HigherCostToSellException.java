package by.gabriel.gerenciadorEstoque.Domain.ExceptionProd;

public class HigherCostToSellException extends RuntimeException {

    public HigherCostToSellException(String message) {
        super(message);
    }
}
