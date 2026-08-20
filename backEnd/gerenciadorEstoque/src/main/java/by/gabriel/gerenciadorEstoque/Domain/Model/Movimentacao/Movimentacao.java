package by.gabriel.gerenciadorEstoque.Domain.Model.Movimentacao;

import by.gabriel.gerenciadorEstoque.Domain.Model.Usuario.Usuario;
import by.gabriel.gerenciadorEstoque.Enum.Movimentacao.AcaoMovimentacao;
import by.gabriel.gerenciadorEstoque.Enum.Movimentacao.TipoEntidade;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @Enumerated(EnumType.STRING)
    private AcaoMovimentacao acao;

    @Enumerated(EnumType.STRING)
    private TipoEntidade tipoEntidade;

    // ID do produto/usuário afetado (como String para aceitar UUID e Long)
    private String registroAfetadoId;

    // NOVO CAMPO: O nome do produto ou usuário
    private String nomeRegistroAfetado;

    private String campoAfetado;

    public Movimentacao() {}

    // Construtor atualizado
    public Movimentacao(AcaoMovimentacao acao, TipoEntidade tipoEntidade, String registroAfetadoId, String nomeRegistroAfetado, String campoAfetado, Usuario autor) {
        this.acao = acao;
        this.tipoEntidade = tipoEntidade;
        this.registroAfetadoId = registroAfetadoId;
        this.nomeRegistroAfetado = nomeRegistroAfetado; // <--- Não esqueça do this!
        this.campoAfetado = campoAfetado;
        this.autor = autor;
    }

    @PrePersist
    public void prePersist() {
        this.dataHora = LocalDateTime.now();
    }

    // --- GETTERS E SETTERS ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public Usuario getAutor() { return autor; }
    public void setAutor(Usuario autor) { this.autor = autor; }

    public AcaoMovimentacao getAcao() { return acao; }
    public void setAcao(AcaoMovimentacao acao) { this.acao = acao; }

    public TipoEntidade getTipoEntidade() { return tipoEntidade; }
    public void setTipoEntidade(TipoEntidade tipoEntidade) { this.tipoEntidade = tipoEntidade; }

    public String getRegistroAfetadoId() { return registroAfetadoId; }
    public void setRegistroAfetadoId(String registroAfetadoId) { this.registroAfetadoId = registroAfetadoId; }

    // --- OS GETTERS E SETTERS DO NOVO CAMPO ---
    public String getNomeRegistroAfetado() { return nomeRegistroAfetado; }
    public void setNomeRegistroAfetado(String nomeRegistroAfetado) { this.nomeRegistroAfetado = nomeRegistroAfetado; }

    public String getCampoAfetado() { return campoAfetado; }
    public void setCampoAfetado(String campoAfetado) { this.campoAfetado = campoAfetado; }
}