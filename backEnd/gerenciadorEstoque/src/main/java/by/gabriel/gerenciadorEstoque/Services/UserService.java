package by.gabriel.gerenciadorEstoque.Services;


import org.springframework.stereotype.Service;

import by.gabriel.gerenciadorEstoque.Api.DTO.Usuario.UserDTO;
import by.gabriel.gerenciadorEstoque.Domain.Exception.User.EmailAlreadyExistException;
import by.gabriel.gerenciadorEstoque.Domain.Exception.User.InvalidPasswordException;
import by.gabriel.gerenciadorEstoque.Domain.Exception.User.UserAlreadyExistsException;
import by.gabriel.gerenciadorEstoque.Domain.Exception.User.UserInactiveException;
import by.gabriel.gerenciadorEstoque.Domain.Exception.User.UserNameNotNullException;
import by.gabriel.gerenciadorEstoque.Domain.Exception.User.UserNotFoundException;
import by.gabriel.gerenciadorEstoque.Domain.Exception.User.UserPasswordNotNullException;
import by.gabriel.gerenciadorEstoque.Domain.Model.Movimentacoes.MovUser;
import by.gabriel.gerenciadorEstoque.Domain.Model.Usuario.Usuario;
import by.gabriel.gerenciadorEstoque.Enum.Movimentacao.MovUserAcao;
import by.gabriel.gerenciadorEstoque.Enum.Movimentacao.MovUserCampo;
import by.gabriel.gerenciadorEstoque.Enum.Usuario.UserStatus;
import by.gabriel.gerenciadorEstoque.Repository.Movimentação.MovUserRepository;
import by.gabriel.gerenciadorEstoque.Repository.Usuario.UserRepository;

@Service
public class UserService {

   private final UserRepository userRepository;
   private final MovUserRepository movUserRepository;

   //Incializando o usuarioRepository (banco de dados)
    public UserService(UserRepository userRepository,MovUserRepository movUserRepository) {
        this.userRepository = userRepository;
        this.movUserRepository = movUserRepository;
    }

        // Método para logar o usuário
    public boolean logarUsuario(UserDTO dto) {

        // Validações obrigatórias
        if (dto.nome() == null || dto.nome().isBlank()) {
            throw new UserNameNotNullException("Nome é obrigatório");
        }

        // Validações obrigatórias
        if (dto.senha() == null || dto.senha().isBlank()) {
            throw new UserPasswordNotNullException("Senha é obrigatoria");
        }

        // 1. Busca no banco se o nome existe
        Usuario usuario = userRepository.findByNomeIgnoreCase(dto.nome())
        .orElseThrow(() -> new UserNotFoundException("Usuario não encontrado"));

        // 2. Verifica se o status do usuário é INATIVO
        if (usuario.getUserStatus() == UserStatus.INATIVO) {
            throw new UserInactiveException("Usuario está com status inativo. Login bloqueado");
        }

        // 3. Valida a senha digitada comparando com o hash armazenado no banco
        if (!usuario.validarSenha(dto.senha())) {
            throw new InvalidPasswordException("Senha incorreta");
        }

        // 4. Se passou por todas as verificações, retorna true (login válido)
        return true;
    }


    //Metodo para cadastrar Usuario
    public Usuario cadastroUser(UserDTO dto) {

        // Se email foi informado, valida duplicidade
        if (dto.email() != null && !dto.email().isBlank() && userRepository.findByEmailIgnoreCase(dto.email()).isPresent()) {
            throw new EmailAlreadyExistException("Já existe um usuário com este email");
        }

        // Verifica se o nome já existe
        if (userRepository.findByNomeIgnoreCase(dto.nome()).isPresent()) {
            throw new UserAlreadyExistsException("Usuario já existe");
        }

        // Validações obrigatórias
        if (dto.nome() == null || dto.nome().isBlank()) {
            throw new UserNameNotNullException("Nome é obrigatorio");

        }

        if (dto.senha() == null || dto.senha().isBlank()) {
            throw new UserPasswordNotNullException("Senha é obrigatoria");
        }

        // Cria o usuário
        Usuario usuario = new Usuario(
            dto.nome(),
            dto.senha(),
            dto.email(), //Usa o telefone tratado com null
            dto.userCargo(),
            dto.telefone(), // Usa o telefone tratado com mull
            UserStatus.ATIVO
        );

        // Salva e captura o retorno (já com UUID gerenciado)
        Usuario usuarioSalvo = userRepository.save(usuario);

        // Cria a movimentação de criação
        MovUser movUser = new MovUser(
            MovUserAcao.CRIACAO,
            MovUserCampo.NENHUM, // nenhum campo específico afetado
            usuarioSalvo,
            usuario.getUserCargo(),
            usuarioSalvo.getNome()
        );

        // Salva a movimentação
        movUserRepository.save(movUser);

        return usuarioSalvo;
    }



}
