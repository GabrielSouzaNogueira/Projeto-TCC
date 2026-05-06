package by.gabriel.gerenciadorEstoque.Domain.Exception.User;

//Execeção lançada quando o usuario não é encontrado

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String mensagem) {
        super(mensagem);
    }
}

