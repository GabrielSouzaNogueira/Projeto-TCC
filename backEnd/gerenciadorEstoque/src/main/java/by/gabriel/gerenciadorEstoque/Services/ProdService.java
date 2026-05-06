package by.gabriel.gerenciadorEstoque.Services;

import by.gabriel.gerenciadorEstoque.Api.DTO.Produto.ProdutoDTO;
import by.gabriel.gerenciadorEstoque.Domain.Model.Movimentacoes.MovProd;
import by.gabriel.gerenciadorEstoque.Domain.Model.Produto.Produto;
import by.gabriel.gerenciadorEstoque.Domain.Model.Usuario.Usuario;
import by.gabriel.gerenciadorEstoque.Enum.Movimentacao.Produto.MovProdAcao;
import by.gabriel.gerenciadorEstoque.Enum.Movimentacao.Produto.MovProdCampo;
import by.gabriel.gerenciadorEstoque.Enum.Produto.ProdStatus;
import by.gabriel.gerenciadorEstoque.Repository.Movimentacao.MovProdRepository;
import by.gabriel.gerenciadorEstoque.Repository.Produto.ProdutoRepository;
import by.gabriel.gerenciadorEstoque.Repository.Usuario.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovProdRepository movProdRepository;

    @Transactional
    public Produto cadastrarProduto(ProdutoDTO dto, String username) {

        Usuario usuario = userRepository.findByNomeIgnoreCase(username).orElseThrow(() -> new RuntimeException("Usuario " + username + " não encontrado"));

        // Validações básicas
        if(dto.nome() == null || dto.nome().isBlank()) throw new IllegalArgumentException("Nome obrigatório");
        if(dto.codBarra() == null || dto.codBarra().length() < 13) throw new IllegalArgumentException("Código de barras inválido");
        if(dto.quantidade() == null || dto.quantidade() <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        if(dto.precoCusto() == null || dto.precoVenda() == null) throw new IllegalArgumentException("Preços não podem ser nulos");

        // Cria o Produto
        Produto produto = new Produto(
                dto.nome(),
                dto.codBarra(),
                dto.quantidade(),
                dto.precoCusto(),
                dto.precoVenda(),
                ProdStatus.ATIVO
        );

        produto = produtoRepository.save(produto);

        // Registra a Movimentação com o Cargo do Momento
        MovProd movProd = new MovProd(
                MovProdAcao.CRIACAO,
                MovProdCampo.NENHUM,
                produto,
                usuario,
                usuario.getNome(),
                usuario.getUserCargo() // O Enum que configuramos como String na Entity
        );

        movProdRepository.save(movProd);

        return produto;
    }
}