package by.gabriel.gerenciadorEstoque.Domain.Exception.User;

public class UserLogadoNotNull extends RuntimeException{
    
    public UserLogadoNotNull(String mensagem) {
        super(mensagem);
    }
}
