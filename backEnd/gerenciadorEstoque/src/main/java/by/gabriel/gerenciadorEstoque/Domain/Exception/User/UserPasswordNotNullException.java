package by.gabriel.gerenciadorEstoque.Domain.Exception.User;


//Exceção lança quando o Usuaro está nulo

public class UserPasswordNotNullException extends RuntimeException {
    public UserPasswordNotNullException(String mensagem) {
        super(mensagem);
    }
}

