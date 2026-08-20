package by.gabriel.gerenciadorEstoque.Domain.ExceptionProd;

public class MarcaNotExistException extends RuntimeException {
    public MarcaNotExistException(String message) {
        super(message);
    }
}
