package by.gabriel.gerenciadorEstoque.Services;

import by.gabriel.gerenciadorEstoque.Api.DTO.Produto.ProdutoDTO;
import by.gabriel.gerenciadorEstoque.Api.DTO.Produto.UpdateProdDTO;
import by.gabriel.gerenciadorEstoque.Domain.Exception.User.UserNotFoundException;
import by.gabriel.gerenciadorEstoque.Domain.ExceptionProd.*;
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

import java.math.BigDecimal;

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

        Usuario usuario = userRepository.findByNomeIgnoreCase(username)
                .orElseThrow(() -> new RuntimeException("Usuario " + username + " não encontrado"));


        if(dto.nome() == null || dto.nome().isBlank()) throw new IllegalArgumentException("Nome obrigatório");
        if(dto.codBarra() == null || dto.codBarra().length() < 13) throw new IllegalArgumentException("Código de barras inválido");
        if(dto.quantidade() == null || dto.quantidade() <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero");


        if(dto.precoCusto() == null || dto.precoVenda() == null) {
            throw new IllegalArgumentException("Preços não podem ser nulos");
        }


        Produto produto = new Produto(
                dto.nome(),
                dto.codBarra(),
                dto.quantidade(),
                dto.precoCusto(),
                dto.precoVenda(),
                ProdStatus.ATIVO
        );

        produto = produtoRepository.save(produto);


        MovProd movProd = new MovProd(
                MovProdAcao.CRIACAO,
                MovProdCampo.NENHUM,
                produto,
                usuario,
                usuario.getNome(),
                usuario.getUserCargo()
        );

        movProdRepository.save(movProd);

        return produto;
    }


    @Transactional
    public Produto updateProd(Long id, UpdateProdDTO dto, String userName) {

        MovProd movProd;

        Usuario usuario = userRepository.findByNomeIgnoreCase(userName)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));


        if (!(usuario.getUserCargo().toString().equals("ADMINISTRADOR") || usuario.getUserCargo().toString().equals("DEV"))) {
            throw new AccessDeniedException("Apenas Usuários Administradores podem realizar esta ação");
        }


        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdNotFoundException("Id " + id + " é inválido, produto não encontrado"));


        // NOME
        if (dto.nome() != null) {

            if (dto.nome().isBlank()) {
                throw new NomeProdVazioException("Nome do produto não pode ser vazio");
            }

            produto.setNome(dto.nome().toLowerCase());

            movProd = new MovProd(MovProdAcao.ATUALIZACAO, MovProdCampo.NOME, produto, usuario, usuario.getNome(), usuario.getUserCargo());
            movProdRepository.save(movProd);
        }

        // CÓDIGO DE BARRAS
        if (dto.codBarra() != null) {

            if (dto.codBarra().isBlank()) {
                throw new CodBarraVazioException("Codigo de barras não pode estar vazio");
            }

            if (dto.codBarra().length() < 13) {
                throw new CodBarraMenorException("Código de barras deve conter ao menos 13 dígitos");
            }
            produto.setCodBarra(dto.codBarra());

            movProd = new MovProd(MovProdAcao.ATUALIZACAO, MovProdCampo.CODBARRA, produto, usuario, usuario.getNome(), usuario.getUserCargo());
            movProdRepository.save(movProd);
        }

        // QUANTIDADE
        if (dto.quantidade() != null) {
            if (dto.quantidade() <= 0) {
                throw new QuantidadeMenorZeroException("Quantidade não pode ser menor ou igual a zero");
            }
            produto.setQuantidade(dto.quantidade());

            movProd = new MovProd(MovProdAcao.ATUALIZACAO, MovProdCampo.QUANTIDADE, produto, usuario, usuario.getNome(), usuario.getUserCargo());
            movProdRepository.save(movProd);
        }

        // PREÇO DE CUSTO
        if (dto.precoCusto() != null) {

            if (dto.precoCusto().compareTo(BigDecimal.ZERO) <= 0 || dto.precoCusto().compareTo(produto.getPrecoVenda()) > 0) {
                throw new CostOrSellBellowZeroException("Preço de Custo inválido: deve ser maior que zero e menor que a venda");
            }
            produto.setPrecoCusto(dto.precoCusto());

            movProd = new MovProd(MovProdAcao.ATUALIZACAO, MovProdCampo.PRECOCUSTO, produto, usuario, usuario.getNome(), usuario.getUserCargo());
            movProdRepository.save(movProd);
        }

        // PREÇO DE VENDA
        if (dto.precoVenda() != null) {

            if (dto.precoVenda().compareTo(BigDecimal.ZERO) <= 0 || dto.precoVenda().compareTo(produto.getPrecoCusto()) < 0) {
                throw new CostOrSellBellowZeroException("Preço de Venda inválido: deve ser maior que zero e maior que o custo");
            }
            produto.setPrecoVenda(dto.precoVenda());

            movProd = new MovProd(MovProdAcao.ATUALIZACAO, MovProdCampo.PRECOVENDA, produto, usuario, usuario.getNome(), usuario.getUserCargo());
            movProdRepository.save(movProd);
        }

        return produtoRepository.save(produto);
    }


}