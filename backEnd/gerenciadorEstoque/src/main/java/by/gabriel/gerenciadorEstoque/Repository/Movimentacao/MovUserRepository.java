package by.gabriel.gerenciadorEstoque.Repository.Movimentacao;

import by.gabriel.gerenciadorEstoque.Api.DTO.Movimentacao.MovUserDTO;
import by.gabriel.gerenciadorEstoque.Domain.Model.Movimentacoes.MovUser;
import by.gabriel.gerenciadorEstoque.Enum.Movimentacao.Usuario.MovUserAcao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovUserRepository extends JpaRepository<MovUser, Long> {

    @Query("""
        SELECT new by.gabriel.gerenciadorEstoque.Api.DTO.Movimentacao.MovUserDTO(
            m.movId,
            m.usuario.userId,
            m.novoUser,
            m.userCargo,
            m.acaoMov,
            m.campoAfetado,
            m.dataMov,
            m.responsavel
        )
        FROM MovUser m
        ORDER BY m.dataMov DESC
        """)
    List<MovUserDTO> listAllMov();

    @Query("""
        SELECT new by.gabriel.gerenciadorEstoque.Api.DTO.Movimentacao.MovUserDTO(
            m.movId,
            m.usuario.userId,
            m.novoUser,
            m.userCargo,
            m.acaoMov,
            m.campoAfetado,
            m.dataMov,
            m.responsavel
        )
        FROM MovUser m
        WHERE (:acao IS NULL OR m.acaoMov = :acao)
          AND (:responsavel IS NULL OR LOWER(m.responsavel) LIKE LOWER(CONCAT('%', :responsavel, '%')))
          AND (:usuarioAfetado IS NULL OR LOWER(m.novoUser) LIKE LOWER(CONCAT('%', :usuarioAfetado, '%')))
          AND (m.dataMov BETWEEN :dataInicio AND :dataFim)
        ORDER BY m.dataMov DESC
        """)
    List<MovUserDTO> filtrarMovimentacoes(
            @Param("acao") MovUserAcao acao,
            @Param("responsavel") String responsavel,
            @Param("usuarioAfetado") String usuarioAfetado,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim
    );
}