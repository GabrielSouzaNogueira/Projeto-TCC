package by.gabriel.gerenciadorEstoque.Domain.ExceptionFormPag;

public class FormPagNotExistException extends RuntimeException {
    public FormPagNotExistException(String message) {
        super(message);
    }
}
