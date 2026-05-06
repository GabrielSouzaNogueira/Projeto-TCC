package by.gabriel.gerenciadorEstoque.Repository.Movimentacao;

import by.gabriel.gerenciadorEstoque.Domain.Model.Movimentacoes.MovProd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovProdRepository extends JpaRepository<MovProd, Long> {
}
