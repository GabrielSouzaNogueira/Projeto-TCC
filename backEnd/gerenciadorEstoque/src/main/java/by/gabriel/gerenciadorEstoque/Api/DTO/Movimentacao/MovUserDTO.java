package by.gabriel.gerenciadorEstoque.Api.DTO.Movimentacao;

import java.time.LocalDateTime;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonFormat;

import by.gabriel.gerenciadorEstoque.Enum.Movimentacao.Usuario.MovUserAcao;
import by.gabriel.gerenciadorEstoque.Enum.Movimentacao.Usuario.MovUserCampo;
import by.gabriel.gerenciadorEstoque.Enum.Usuario.UserCargo;

public record MovUserDTO(
        Long movId,
        UUID userId,
        String nomeUser,
        UserCargo userCargo,   // Alterado de String para o Enum real
        MovUserAcao acaoMov,    // Alterado de String para o Enum real
        MovUserCampo campoAfetado, // Alterado de String para o Enum real
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataMov
) {}