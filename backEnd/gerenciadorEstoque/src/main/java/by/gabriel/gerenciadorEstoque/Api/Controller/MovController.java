package by.gabriel.gerenciadorEstoque.Api.Controller;

import by.gabriel.gerenciadorEstoque.Api.DTO.Movimentacao.MovUserDTO;
import by.gabriel.gerenciadorEstoque.Enum.Movimentacao.Usuario.MovUserAcao;
import by.gabriel.gerenciadorEstoque.Services.MovUserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("movimentacao")
public class MovController {

    private final MovUserService movUserService;

    public MovController(MovUserService movUserService) {
        this.movUserService = movUserService;
    }

    @GetMapping("/listAllMov")
    public ResponseEntity<List<MovUserDTO>> listAllMov() {
        List<MovUserDTO> listados = movUserService.listAllMov();
        return ResponseEntity.ok(listados);
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<MovUserDTO>> filtrarMovimentacoes(
            @RequestParam(required = false) MovUserAcao acao,
            @RequestParam(required = false) String responsavel,
            @RequestParam(required = false) String usuarioAfetado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim
    ) {
        List<MovUserDTO> listados = movUserService.filtrarMovimentacoes(acao, responsavel, usuarioAfetado, dataInicio, dataFim);
        return ResponseEntity.ok(listados);
    }
}