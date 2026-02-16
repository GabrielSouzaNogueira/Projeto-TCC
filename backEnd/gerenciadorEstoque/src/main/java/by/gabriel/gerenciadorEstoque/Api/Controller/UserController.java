package by.gabriel.gerenciadorEstoque.Api.Controller;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.gabriel.gerenciadorEstoque.Api.DTO.ResponseDTO;
import by.gabriel.gerenciadorEstoque.Api.DTO.Usuario.UserDTO;
import by.gabriel.gerenciadorEstoque.Domain.Model.Usuario.Usuario;
import by.gabriel.gerenciadorEstoque.Services.UserService;

@RestController
@RequestMapping("/usuario")
public class UserController {

    Usuario usuario;
    private final UserService userService;

    //INICIALIZANDO A CLASSE DO SERVICE
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint de cadastro
    @PostMapping("/cadastro")
    public ResponseEntity<ResponseDTO> cadastrar(@RequestBody UserDTO dto) {

        // Chama o service para realizar o cadastro do usuário
        usuario = userService.cadastroUser(dto);

        //Caso tenha conseguido realizar o login normalmente eu devolvo a resposta para o cliente
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(
            true, 
            "Usuario cadastrado com sucesso", 
            Instant.now().toString())
        );
    }

    //EndPoint de Login
    @PostMapping("/login")
        public ResponseEntity<ResponseDTO> login(@RequestBody UserDTO dto) {
            boolean loginValido = userService.logarUsuario(dto);

            if (loginValido) {
                return ResponseEntity.ok(
                    new ResponseDTO(true, "Usuario logado com sucesso", Instant.now().toString())
                );
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseDTO(false, "Nome ou senha inválidos", "INVALID_LOGIN", Instant.now().toString())
                );
            }
        }
    }
