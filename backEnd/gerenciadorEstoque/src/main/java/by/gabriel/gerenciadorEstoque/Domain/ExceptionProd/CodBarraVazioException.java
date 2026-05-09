package by.gabriel.gerenciadorEstoque.Domain.ExceptionProd;

public class CodBarraVazioException extends RuntimeException {
    public CodBarraVazioException(String message) {
        super(message);
    }
}
