package by.gabriel.gerenciadorEstoque.Domain.Exception.User;

//Exceção de quando o email já existe
public class EmailAlreadyExistException extends RuntimeException{
    
    public EmailAlreadyExistException(String mensagem) {
        super(mensagem);
    }
}
