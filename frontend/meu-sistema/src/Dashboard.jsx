import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './Dashboard.css';

// Importação dos seus componentes de formulário
import CadastroUsuario from './CadastroUsuario';
import CadastroProduto from './CadastroProduto';

export default function Dashboard() {
  const navigate = useNavigate();
  
  // Estado que controla qual componente será exibido na área principal
  const [telaAtiva, setTelaAtiva] = useState("home");

  // Recupera o nome do usuário salvo no login para personalizar a recepção
  const [nomeUsuario, setNomeUsuario] = useState("");

  useEffect(() => {
    // Busca os dados do usuário no localStorage para exibir o nome no Dashboard
    const user = JSON.parse(localStorage.getItem("userLogged"));
    if (user && user.nome) {
      setNomeUsuario(user.nome);
    }

    // ==========================================
    // SINCRONIZAÇÃO DE LOGOUT ENTRE ABAS
    // ==========================================
    const escutarOutrasAbas = (evento) => {
      // Se a chave 'auth' sumir de qualquer aba, desloga esta aba também
      if (evento.key === "auth" && evento.newValue === null) {
        navigate("/");
      }
    };

    window.addEventListener("storage", escutarOutrasAbas);

    // Cleanup: Remove o escutador ao desmontar o componente
    return () => {
      window.removeEventListener("storage", escutarOutrasAbas);
    };
  }, [navigate]);

  // ==========================================
  // FUNÇÃO DE LOGOUT
  // ==========================================
  const handleLogout = () => {
    localStorage.removeItem("auth"); // Remove a permissão
    localStorage.removeItem("userLogged"); // Limpa dados do usuário
    navigate("/"); // Redireciona para login
  };

  // ==========================================
  // LÓGICA DE NAVEGAÇÃO INTERNA (SWITCH CASE)
  // ==========================================
  const renderizarConteudo = () => {
    switch (telaAtiva) {
      case "home":
        return (
          <div className="home-welcome">
            <h2>Olá, {nomeUsuario || "Bem-vindo"}!</h2>
            <p>Selecione uma opção no menu lateral para gerenciar seu sistema.</p>
            {/* Você pode adicionar cards de resumo aqui no futuro */}
          </div>
        );
      
      case "cadastroUsuario":
        return <CadastroUsuario />;

      case "produtos":
        return <CadastroProduto />;

      default:
        return <div className="error-screen">Ops! Tela não encontrada.</div>;
    }
  };

  return (
    <div className="dashboard-layout">
      
      {/* ================= BARRA LATERAL (SIDEBAR) ================= */}
      <aside className="sidebar">
        <div className="sidebar-logo">
          <h3>Controle de Estoque</h3>
        </div>
        
        <nav className="menu-lateral">
          {/* GRUPO PRINCIPAL */}
          <span className="menu-category">Navegação</span>
          <button 
            className={`menu-btn ${telaAtiva === "home" ? "ativo" : ""}`}
            onClick={() => setTelaAtiva("home")}
          >
            Início
          </button>
          
          {/* GRUPO DE OPERAÇÕES */}
          <span className="menu-category">Operacional</span>
          <button 
            className={`menu-btn ${telaAtiva === "produtos" ? "ativo" : ""}`}
            onClick={() => setTelaAtiva("produtos")}
          >
            Estoque de Produtos
          </button>

          {/* GRUPO ADMINISTRATIVO */}
          <span className="menu-category">Administração</span>
          <button 
            className={`menu-btn ${telaAtiva === "cadastroUsuario" ? "ativo" : ""}`}
            onClick={() => setTelaAtiva("cadastroUsuario")}
          >
            Gerenciar Usuários
          </button>
        </nav>
        
        {/* RODAPÉ DA SIDEBAR */}
        <div className="sidebar-footer">
          <button className="logout-btn" onClick={handleLogout}>
            Sair do Sistema
          </button>
        </div>
      </aside>

      {/* ================= ÁREA DE CONTEÚDO PRINCIPAL ================= */}
      <main className="conteudo-principal">
        {/* Cabeçalho superior (Breadcrumb) para indicar onde o usuário está */}
        <header className="content-header">
          <p>Dashboard / <strong>{telaAtiva === "home" ? "Início" : telaAtiva}</strong></p>
        </header>

        {/* Injeção do componente selecionado */}
        <div className="content-body">
          {renderizarConteudo()}
        </div>
      </main>

    </div>
  );
}