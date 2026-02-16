package by.gabriel.gerenciadorEstoque.Domain.Exception.User;


public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String mensagem) {
        super(mensagem);
    }
}
