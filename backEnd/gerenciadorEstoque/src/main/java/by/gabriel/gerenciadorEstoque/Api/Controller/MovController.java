package by.gabriel.gerenciadorEstoque.Api.Controller;

import by.gabriel.gerenciadorEstoque.Api.DTO.Movimentacao.MovUserDTO;
import by.gabriel.gerenciadorEstoque.Services.MovUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("movimentacao")
public class MovController {

    private final MovUserService movUserService;

    public MovController(MovUserService movUserService) {
        this.movUserService = movUserService;
    }

    @GetMapping("/listAllMov")
    public ResponseEntity<List<MovUserDTO>> listAllMov() { // Trocado de FilterSelectMovUser para MovUserDTO

        List<MovUserDTO> listados = movUserService.listAllMov();

        return ResponseEntity.ok(listados);
    }
}
