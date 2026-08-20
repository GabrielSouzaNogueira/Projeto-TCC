package by.gabriel.gerenciadorEstoque.Domain.ExceptionProd;

public class MarcaNotNullException extends RuntimeException {
    public MarcaNotNullException(String message) {
        super(message);
    }
}
