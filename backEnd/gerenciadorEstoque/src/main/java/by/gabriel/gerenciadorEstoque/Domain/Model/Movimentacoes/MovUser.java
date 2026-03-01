package by.gabriel.gerenciadorEstoque.Domain.Model.Movimentacoes;

import java.time.LocalDateTime;

import by.gabriel.gerenciadorEstoque.Enum.Movimentacao.MovUserAcao;
import by.gabriel.gerenciadorEstoque.Enum.Movimentacao.MovUserCampo;
import by.gabriel.gerenciadorEstoque.Enum.Usuario.UserCargo;
import by.gabriel.gerenciadorEstoque.Domain.Model.Usuario.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

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

    @ManyToOne //Relacionamento N x 1 
    @JoinColumn(name = "user_id")
    private Usuario usuario;

    private String nomeUser;

    public MovUser() {}

    public MovUser(MovUserAcao acaoMov, MovUserCampo campoAfetado, Usuario usuario, UserCargo userCargo, String nomeUser) {
        this.acaoMov = acaoMov;
        this.campoAfetado = campoAfetado;
        this.usuario = usuario;
        this.userCargo = userCargo;
        this.nomeUser = nomeUser;
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
        return nomeUser;
    }

    public void setNomeUser(String nomeUser) {
        this.nomeUser = nomeUser;
    }

    
}
   