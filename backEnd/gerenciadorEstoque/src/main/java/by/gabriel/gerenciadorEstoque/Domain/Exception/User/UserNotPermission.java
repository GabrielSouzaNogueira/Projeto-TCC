package by.gabriel.gerenciadorEstoque.Domain.Exception.User;

//Excessão para usuarios sem permissão
public class UserNotPermission extends RuntimeException{

    public UserNotPermission(String mensagem) {
        super(mensagem);
    }
}
