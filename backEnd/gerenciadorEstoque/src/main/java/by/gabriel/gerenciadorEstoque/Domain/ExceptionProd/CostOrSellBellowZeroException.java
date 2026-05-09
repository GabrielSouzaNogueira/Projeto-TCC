package by.gabriel.gerenciadorEstoque.Domain.ExceptionProd;

public class CostOrSellBellowZeroException extends RuntimeException {

    public CostOrSellBellowZeroException(String message) {
        super(message);
    }
}
