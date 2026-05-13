package by.gabriel.gerenciadorEstoque.Domain.ExceptionProd;

public class CodBarraExistenteException extends RuntimeException {
    public CodBarraExistenteException(String message) {
        super(message);
    }
}
