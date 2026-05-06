package by.gabriel.gerenciadorEstoque.Domain.Model.Movimentacoes;

import by.gabriel.gerenciadorEstoque.Domain.Model.Produto.Produto;
import by.gabriel.gerenciadorEstoque.Domain.Model.Usuario.Usuario;
import by.gabriel.gerenciadorEstoque.Enum.Movimentacao.Produto.MovProdAcao;
import by.gabriel.gerenciadorEstoque.Enum.Movimentacao.Produto.MovProdCampo;
import by.gabriel.gerenciadorEstoque.Enum.Usuario.UserCargo;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class MovProd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movId;

    @Enumerated(EnumType.STRING)
    private MovProdAcao acaoMov;

    @Enumerated(EnumType.STRING)
    private MovProdCampo campoAfetado = MovProdCampo.NENHUM;

    private LocalDateTime dataMov;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prod_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Usuario usuario;

    private String nomeUser;

    @Enumerated(EnumType.STRING)
    private UserCargo userCargo;

    public MovProd() {

    }

    public MovProd(MovProdAcao acaoMov, MovProdCampo campoAfetado, Produto produto, Usuario usuario,String nomeUser, UserCargo userCargo) {
        this.acaoMov = acaoMov;
        this.campoAfetado = campoAfetado;
        this.produto = produto;
        this.usuario = usuario;
        this.nomeUser = nomeUser;
        this.userCargo = userCargo;

    }

    @PrePersist
    public void prePersist(){

        this.dataMov = LocalDateTime.now();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNomeUser() {
        return nomeUser;
    }

    public void setNomeUser(String nomeUser) {
        this.nomeUser = nomeUser;
    }

    public UserCargo getUserCargo() {
        return userCargo;
    }

    public void setUserCargo(UserCargo userCargo) {
        this.userCargo = userCargo;
    }

    public Long getMovId() {
        return movId;
    }

    public void setMovId(Long movId) {
        this.movId = movId;
    }

    public MovProdAcao getAcaoMov() {
        return acaoMov;
    }

    public void setAcaoMov(MovProdAcao acaoMov) {
        this.acaoMov = acaoMov;
    }

    public MovProdCampo getCampoAfetado() {
        return campoAfetado;
    }

    public void setCampoAfetado(MovProdCampo campoAfetado) {
        this.campoAfetado = campoAfetado;
    }

    public LocalDateTime getDataMov() {
        return dataMov;
    }

    public void setDataMov(LocalDateTime dataMov) {
        this.dataMov = dataMov;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
