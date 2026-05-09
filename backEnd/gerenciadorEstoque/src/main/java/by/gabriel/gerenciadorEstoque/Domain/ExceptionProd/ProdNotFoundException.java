package by.gabriel.gerenciadorEstoque.Domain.ExceptionProd;

public class ProdNotFoundException extends RuntimeException {
    public ProdNotFoundException(String message) {
        super(message);
    }
}
