package by.gabriel.gerenciadorEstoque.Services;

import by.gabriel.gerenciadorEstoque.Api.DTO.Movimentacao.MovUserDTO;
import by.gabriel.gerenciadorEstoque.Enum.Movimentacao.Usuario.MovUserAcao;
import by.gabriel.gerenciadorEstoque.Repository.Movimentacao.MovUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovUserService {

    private final MovUserRepository movUserRepository;

    public MovUserService(MovUserRepository movUserRepository){
        this.movUserRepository = movUserRepository;
    }

    public List<MovUserDTO> listAllMov() {
        List<MovUserDTO> movimentacoes = movUserRepository.listAllMov();

        if(movimentacoes.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma movimentação registrada");
        }

        return movimentacoes;
    }

    public List<MovUserDTO> filtrarMovimentacoes(MovUserAcao acao, String responsavel, String usuarioAfetado, LocalDate dataInicioFiltro, LocalDate dataFimFiltro) {

        // Se a data inicial vier nula da tela, jogamos para o ano 2000
        LocalDateTime inicioBusca = (dataInicioFiltro != null)
                ? dataInicioFiltro.atStartOfDay()
                : LocalDateTime.of(2000, 1, 1, 0, 0);

        // Se a data final vier nula da tela, jogamos para o ano 2100
        LocalDateTime fimBusca = (dataFimFiltro != null)
                ? dataFimFiltro.atTime(23, 59, 59)
                : LocalDateTime.of(2100, 12, 31, 23, 59, 59);

        List<MovUserDTO> movimentacoes = movUserRepository.filtrarMovimentacoes(acao, responsavel, usuarioAfetado, inicioBusca, fimBusca);

        if(movimentacoes.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma movimentação encontrada para os filtros informados");
        }

        return movimentacoes;
    }
}