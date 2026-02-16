package by.gabriel.gerenciadorEstoque.Domain.Model.Movimentações;

import java.time.LocalDateTime;

import by.gabriel.gerenciadorEstoque.Enum.Movimentacao.MovUserAcao;
import by.gabriel.gerenciadorEstoque.Enum.Movimentacao.MovUserCampo;
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

    private LocalDateTime dataCriacao;

    @ManyToOne //Relacionamento N x 1 
    @JoinColumn(name = "user_id")
    private Usuario usuario;

    private String nomeUser;

    public MovUser() {}

    public MovUser(MovUserAcao acaoMov, MovUserCampo campoAfetado, Usuario usuario, String nomeUser) {
        this.acaoMov = acaoMov;
        this.campoAfetado = campoAfetado;
        this.usuario = usuario;
        this.nomeUser = nomeUser;
    }

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
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

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
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
   