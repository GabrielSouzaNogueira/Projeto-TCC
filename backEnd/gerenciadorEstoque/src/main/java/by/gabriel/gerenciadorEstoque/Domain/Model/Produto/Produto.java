package by.gabriel.gerenciadorEstoque.Domain.Model.Produto;

import by.gabriel.gerenciadorEstoque.Domain.ExceptionProd.CostOrSellBellowZero;
import by.gabriel.gerenciadorEstoque.Domain.ExceptionProd.HigherCostToSellException;
import by.gabriel.gerenciadorEstoque.Domain.Model.Movimentacoes.MovProd;
import by.gabriel.gerenciadorEstoque.Enum.Produto.ProdStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prodId;

    private String nome;

    private String codBarra;

    private Integer quantidade;

    private BigDecimal precoCusto;

    private BigDecimal precoVenda;

    @Enumerated(EnumType.STRING)
    private ProdStatus prodStatus;

    @OneToMany(mappedBy = "produto")
    private List<MovProd> movProd;

    public Produto() {

    }

    public Produto(String nome, String codBarra, Integer quantidade, BigDecimal precoCusto, BigDecimal precoVenda, ProdStatus prodStatus) {
        this.nome = nome;
        this.codBarra = codBarra;
        this.quantidade = quantidade;
        validarPreco(precoCusto, precoVenda);
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
        this.prodStatus = prodStatus;
    }

    //Metodo para validar os preços no momento do cadastro
    public void validarPreco(BigDecimal custo, BigDecimal venda) {

        if (precoCusto.compareTo(precoVenda) > 0 || precoVenda.compareTo(precoCusto) < 0) {

            throw new HigherCostToSellException("Preco de custo maior que o de Venda");

        }

        if (precoCusto.compareTo(BigDecimal.ZERO) <= 0 || precoVenda.compareTo(BigDecimal.ZERO) <= 0) {

            throw new CostOrSellBellowZero("Preço de custo ou venda menor ou igual a zero");

        }
    }

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodBarra() {
        return codBarra;
    }

    public void setCodBarra(String codBarra) {
        this.codBarra = codBarra;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(BigDecimal precoCusto) {
        this.precoCusto = precoCusto;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    public ProdStatus getProdStatus() {
        return prodStatus;
    }

    public void setProdStatus(ProdStatus prodStatus) {
        this.prodStatus = prodStatus;
    }

}
