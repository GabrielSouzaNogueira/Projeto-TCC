package by.gabriel.gerenciadorEstoque.Domain.Exception.User;


public class UserNameNotNullException extends RuntimeException {
    
    public UserNameNotNullException(String mensagem) {
        super(mensagem);
    }
}

