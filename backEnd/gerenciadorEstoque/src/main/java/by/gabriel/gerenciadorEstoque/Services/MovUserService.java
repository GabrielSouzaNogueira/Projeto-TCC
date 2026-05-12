package by.gabriel.gerenciadorEstoque.Services;

import by.gabriel.gerenciadorEstoque.Api.DTO.Movimentacao.MovUserDTO;
import by.gabriel.gerenciadorEstoque.Repository.Movimentacao.MovUserRepository;
import org.springframework.stereotype.Service;

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
}
