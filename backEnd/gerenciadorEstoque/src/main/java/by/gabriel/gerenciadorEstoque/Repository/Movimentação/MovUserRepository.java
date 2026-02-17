package by.gabriel.gerenciadorEstoque.Repository.Movimentação;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.gabriel.gerenciadorEstoque.Domain.Model.Movimentacoes.MovUser;

@Repository
public interface MovUserRepository extends JpaRepository<MovUser, Long> {

}
