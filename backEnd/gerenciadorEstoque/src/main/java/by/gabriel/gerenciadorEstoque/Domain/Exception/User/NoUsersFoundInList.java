package by.gabriel.gerenciadorEstoque.Domain.Exception.User;

public class NoUsersFoundInList extends RuntimeException  {
    public NoUsersFoundInList(String message) {
        super(message);
    }
}
