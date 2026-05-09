package by.gabriel.gerenciadorEstoque.Api.Controller;

import by.gabriel.gerenciadorEstoque.Api.DTO.Produto.ProdutoDTO;
import by.gabriel.gerenciadorEstoque.Api.DTO.Produto.UpdateProdDTO;
import by.gabriel.gerenciadorEstoque.Api.DTO.Response.ResponseDTO;
import by.gabriel.gerenciadorEstoque.Domain.Model.Produto.Produto;
import by.gabriel.gerenciadorEstoque.Domain.Model.Usuario.Usuario;
import by.gabriel.gerenciadorEstoque.Services.ProdService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/produto")
public class ProdController {

    private final ProdService prodService;

    public  ProdController(ProdService prodService) {
        this.prodService = prodService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<ResponseDTO> cadastroProd(@RequestBody @Validated ProdutoDTO dto, @RequestHeader("X-Usuario-Logado") String usuarioLogado) {

        prodService.cadastrarProduto(dto, usuarioLogado);

        ResponseDTO sucesso = new ResponseDTO(

                true,
                "Produto cadastrado com sucesso",
                LocalDateTime.now().toString()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(sucesso);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateProd(@PathVariable Long id, @RequestBody @Validated UpdateProdDTO dto, @RequestHeader("X-Usuario") String username ) {

        Produto produtoAtualiazdo = prodService.updateProd(id, dto, username);

        ResponseDTO sucesso = new ResponseDTO(

                true,
                "Produto Atualizado com sucesso",
                LocalDateTime.now().toString()
        );

        return ResponseEntity.ok(sucesso);

    }


}
