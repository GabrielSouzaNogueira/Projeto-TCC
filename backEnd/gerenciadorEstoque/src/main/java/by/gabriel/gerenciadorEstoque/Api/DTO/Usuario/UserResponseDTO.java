package by.gabriel.gerenciadorEstoque.Api.DTO.Usuario;

import by.gabriel.gerenciadorEstoque.Enum.Usuario.UserStatus;

public record UserResponseDTO(
    String nome,
    String email,
    String numeroTelefone1,
    UserStatus userStatus
) {}

