package by.gabriel.gerenciadorEstoque.Domain.ExceptionProd;

public class CostOrSellBellowZero extends RuntimeException {

    public CostOrSellBellowZero(String message) {
        super(message);
    }
}
