package by.gabriel.gerenciadorEstoque.Domain.Model.Usuario;

import java.util.List;
import java.util.UUID;

import by.gabriel.gerenciadorEstoque.Enum.Usuario.UserCargo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import by.gabriel.gerenciadorEstoque.Enum.Usuario.UserStatus;
import by.gabriel.gerenciadorEstoque.Enum.Usuario.UserStatusLogin;
import by.gabriel.gerenciadorEstoque.Domain.Model.Movimentacoes.MovUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId; 

    @Column(nullable = false) // campo obrigatório
    private String nome;

    @Column(nullable = false)
    private String senha; // será armazenada criptografada

    @Column(nullable = true)
    private String email;

    @Enumerated(EnumType.STRING) // salva o enum como texto
    private UserCargo userCargo;

    @Enumerated(EnumType.STRING) // salva o enum como texto
    private UserStatus userStatus;

    @Enumerated(EnumType.STRING) // salva o enum como texto
    private UserStatusLogin statuslogin;

    @Column(nullable = true)
    private String telefone;

    // Relacionamento com MovUser
    @OneToMany(mappedBy = "usuario") // opção do cascade, ele seria um facilitador para menos código mas nao afeta os dados
    private List<MovUser> movimentacoes;

    
    public Usuario() {

    }
    
    // Construtor com todos os campos
    public Usuario(String nome, String senha, String email, UserCargo userCargo, String telefone, UserStatus userStatus) {
        this.nome = nome;
        setSenhaCriptografada(senha); //Senha já é criptografada no momento da criação do Usuario

        // // Normaliza email: se vier vazio, vira null
        this.email = (email == null  || email.isBlank()) ? null : email;

        this.userCargo = userCargo;

        // //condição ? valorSeVerdadeiro : valorSeFalso
        this.telefone = (telefone == null || telefone.isBlank()) ? null : telefone;
        this.userStatus = userStatus;

    }


    public UserStatusLogin getStatuslogin() {
        return statuslogin;
    }

    public void setStatuslogin(UserStatusLogin statuslogin) {
        this.statuslogin = statuslogin;
    }

    // Criptografa a senha antes de salvar
    public void setSenhaCriptografada(String senhaPura) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.senha = encoder.encode(senhaPura);
    }

    // Verifica se a senha digitada confere com a armazenada
    public boolean validarSenha(String senhaDigitada) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(senhaDigitada, this.senha);
    }


    public UUID getUserId() {
        return userId;
    }


    public void setUserId(UUID userId) {
        this.userId = userId;
    }


    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getSenha() {
        return senha;
    }

    public UserCargo getUserCargo() {
        return userCargo;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }


    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }


    public String getTelefone() {
        return telefone;
    }


    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setUserCargo(UserCargo userCargo) {
        this.userCargo = userCargo;
    }


}
