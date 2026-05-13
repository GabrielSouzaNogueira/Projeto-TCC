package by.gabriel.gerenciadorEstoque.Domain.ExceptionProd;

public class ListaProdVaziaException extends RuntimeException {
    public ListaProdVaziaException(String message) {
        super(message);
    }
}
