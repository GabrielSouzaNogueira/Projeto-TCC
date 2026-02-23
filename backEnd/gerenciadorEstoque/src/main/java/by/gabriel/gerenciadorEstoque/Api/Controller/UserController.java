package by.gabriel.gerenciadorEstoque.Api.Controller;

import java.time.Instant;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.gabriel.gerenciadorEstoque.Api.DTO.ResponseDTO;
import by.gabriel.gerenciadorEstoque.Api.DTO.Usuario.UpdateUserDTO;
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
    public ResponseEntity<ResponseDTO> login(@RequestBody UserDTO dto) { // RequestBody será a condição que será preenchida no corpo da requisição
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

    //END POINT PARA ATUALIAR UM DADO
    @PatchMapping("/update/{id}") //Pahtch utilizado para atualização parcial dos dados
    public ResponseEntity<ResponseDTO> update(@PathVariable UUID id, @Validated @RequestBody UpdateUserDTO dto) {

        boolean atualizado = userService.atualizarDados(id, dto);

        if (atualizado) {
            return ResponseEntity.ok(
            new ResponseDTO(true,"Dados atualizados com sucesso!",Instant.now().toString()));
        }
        
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseDTO(false, "Ouve algum erro ao atualizar os dados","INVALID_UPDATE", Instant.now().toString())
            );
        }
    }

    //END POINT PARA DELETAR O USUARIO
    @PatchMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> softDeleteUser(@PathVariable UUID id) {

        boolean deleteado = userService.deletarUsuario(id);

        if (deleteado) {
            return ResponseEntity.ok(new ResponseDTO(true,"Usuario deletado com sucesso", Instant.now().toString()));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(false,"Algo deu Errado na exclusão dos dados","BAD_DELETE", Instant.now().toString()));
        }
    }

}
