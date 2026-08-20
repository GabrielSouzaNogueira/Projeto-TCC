package by.gabriel.gerenciadorEstoque.Api.DTO.Movimentacao;

import by.gabriel.gerenciadorEstoque.Enum.Movimentacao.AcaoMovimentacao;
import by.gabriel.gerenciadorEstoque.Enum.Movimentacao.TipoEntidade;
import java.time.LocalDateTime;

public record MovimentacaoDTO(
        Long movId,
        TipoEntidade tipoEntidade, // Diz se é PRODUTO, USUARIO, CLIENTE...
        String registroAfetadoId,
        String nomeRegistroAfetado,
        AcaoMovimentacao acaoMov,
        String campoAfetado,
        LocalDateTime dataMov,
        String responsavel // Nome do autor (UserLogado)
) {}