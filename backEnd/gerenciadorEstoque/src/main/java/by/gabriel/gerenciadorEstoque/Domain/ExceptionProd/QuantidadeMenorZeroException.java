package by.gabriel.gerenciadorEstoque.Domain.ExceptionProd;

public class QuantidadeMenorZeroException extends RuntimeException {
    public QuantidadeMenorZeroException(String message) {
        super(message);
    }
}
