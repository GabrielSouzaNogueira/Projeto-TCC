package by.gabriel.gerenciadorEstoque.Repository.Produto;

import by.gabriel.gerenciadorEstoque.Domain.Model.Produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
