package by.gabriel.gerenciadorEstoque.Domain.Model.Movimentacoes;

import java.time.LocalDateTime;

import by.gabriel.gerenciadorEstoque.Enum.Movimentacao.Usuario.MovUserAcao;
import by.gabriel.gerenciadorEstoque.Enum.Movimentacao.Usuario.MovUserCampo;
import by.gabriel.gerenciadorEstoque.Enum.Usuario.UserCargo;
import by.gabriel.gerenciadorEstoque.Domain.Model.Usuario.Usuario;
import jakarta.persistence.*;

@Entity
public class MovUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movId;

    @Enumerated(EnumType.STRING)
    private MovUserAcao acaoMov;

    @Enumerated(EnumType.STRING)
    private MovUserCampo campoAfetado = MovUserCampo.NENHUM;

    @Enumerated(EnumType.STRING)
    private UserCargo userCargo;

    private LocalDateTime dataMov;

    @ManyToOne(fetch = FetchType.LAZY) //Relacionamento N x 1
    @JoinColumn(name = "user_id")
    private Usuario usuario;

    private String novoUser;

    private String responsavel;

    public MovUser() {}

    public MovUser(MovUserAcao acaoMov, MovUserCampo campoAfetado, Usuario usuario, UserCargo userCargo, String novoUser,String responsavel) {
        this.acaoMov = acaoMov;
        this.campoAfetado = campoAfetado;
        this.usuario = usuario;
        this.userCargo = userCargo;
        this.novoUser = novoUser;
        this.responsavel = responsavel;

    }

    @PrePersist
    public void prePersist() {
        this.dataMov = LocalDateTime.now(); //Gera a data de criação atual da maquina
    }

    public Long getMovId() {
        return movId;
    }

    public void setMovId(Long movId) {
        this.movId = movId;
    }

    public MovUserAcao getAcaoMov() {
        return acaoMov;
    }

    public void setAcaoMov(MovUserAcao acaoMov) {
        this.acaoMov = acaoMov;
    }

    public MovUserCampo getCampoAfetado() {
        return campoAfetado;
    }

    public void setCampoAfetado(MovUserCampo campoAfetado) {
        this.campoAfetado = campoAfetado;
    }

    public LocalDateTime getDataMov() {
        return dataMov;
    }

    public void setDataMov(LocalDateTime dataMov) {
        this.dataMov = dataMov;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNomeUser() {
        return novoUser;
    }

    public void setNomeUser(String nomeUser) {
        this.novoUser = novoUser;
    }

    
}
   