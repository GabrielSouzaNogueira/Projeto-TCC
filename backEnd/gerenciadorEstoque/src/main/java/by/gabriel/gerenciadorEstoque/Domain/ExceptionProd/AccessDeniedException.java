package by.gabriel.gerenciadorEstoque.Domain.ExceptionProd;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
