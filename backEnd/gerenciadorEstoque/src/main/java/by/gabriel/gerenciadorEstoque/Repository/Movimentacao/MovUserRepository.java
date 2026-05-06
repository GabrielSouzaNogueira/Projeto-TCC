package by.gabriel.gerenciadorEstoque.Repository.Movimentacao;

import by.gabriel.gerenciadorEstoque.Api.DTO.Movimentacao.MovUserDTO;
import by.gabriel.gerenciadorEstoque.Domain.Model.Movimentacoes.MovUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovUserRepository extends JpaRepository<MovUser, Long> {

    @Query("""
        SELECT new by.gabriel.gerenciadorEstoque.Api.DTO.Movimentacao.MovUserDTO(
            m.movId,
            m.usuario.userId,
            m.nomeUser,
            m.userCargo,
            m.acaoMov,
            m.campoAfetado,
            m.dataMov
        )
        FROM MovUser m
        """)
    List<MovUserDTO> listAllMov();
}