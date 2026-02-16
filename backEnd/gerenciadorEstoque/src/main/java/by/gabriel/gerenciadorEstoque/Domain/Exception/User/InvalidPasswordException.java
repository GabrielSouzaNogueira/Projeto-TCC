package by.gabriel.gerenciadorEstoque.Domain.Exception.User;

//Execeção lançada quando a senha do usuário está invalida
public class InvalidPasswordException extends RuntimeException {
    
    public InvalidPasswordException(String mensagem) {
        super(mensagem);
    }
}
