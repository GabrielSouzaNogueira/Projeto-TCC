package by.gabriel.gerenciadorEstoque.Domain.ExceptionFormPag;

public class FormPagAlreadyExistException extends RuntimeException {
    public FormPagAlreadyExistException(String message) {
        super(message);
    }
}
