package by.gabriel.gerenciadorEstoque.Domain.ExceptionProd;

public class NomeProdJaExistenteException extends RuntimeException {
    public NomeProdJaExistenteException(String message) {
        super(message);
    }
}
