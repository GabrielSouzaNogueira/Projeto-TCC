package by.gabriel.gerenciadorEstoque.Repository.Usuario;

import java.util.UUID;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.gabriel.gerenciadorEstoque.Domain.Model.Usuario.Usuario;
import by.gabriel.gerenciadorEstoque.Enum.Usuario.UserStatus;

@Repository
public interface UserRepository extends JpaRepository<Usuario, UUID>{

    // Busca usuários pelo status (ATIVO ou INATIVO)
    Optional<Usuario> findByUserStatus(UserStatus userStatus);

    // Busca usuário pelo nome (para login)
    Optional<Usuario> findByNomeIgnoreCase(String nome);

    Optional<Usuario> findByEmailIgnoreCase(String email);

}
