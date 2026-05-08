// 1. Adicionamos o useEffect na importação do React
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './Dashboard.css';

import CadastroUsuario from './CadastroUsuario';

export default function Dashboard() {
  const navigate = useNavigate();
  const [telaAtiva, setTelaAtiva] = useState("home");

  // ==========================================
  // O ALARME DEFINITIVO (Sincronização de Abas)
  // ==========================================
  useEffect(() => {
    const escutarOutrasAbas = (evento) => {
      // O navegador avisa: "A chave 'auth' mudou!"
      // Se o novo valor for null (ou seja, alguém clicou em Sair em outra aba)
      if (evento.key === "auth" && evento.newValue === null) {
        // Chuta o usuário para a tela de login na mesma fração de segundo
        navigate("/");
      }
    };

    // Liga o escutador no navegador
    window.addEventListener("storage", escutarOutrasAbas);

    // Desliga o escutador se a página for fechada
    return () => {
      window.removeEventListener("storage", escutarOutrasAbas);
    };
  }, [navigate]); 
  // O colchete com 'navigate' avisa ao React que essa função depende do roteador.

  // ==========================================
  // FUNÇÃO DO BOTÃO SAIR DESTA ABA
  // ==========================================
  const handleLogout = () => {
    localStorage.removeItem("auth");
    navigate("/");
  };

  const renderizarConteudo = () => {
    if (telaAtiva === "home") {
      return (
        <div style={{ textAlign: 'center', marginTop: '50px' }}>
          <h2 style={{ fontSize: '2rem', color: '#2c3e50' }}>Bem-vindo ao Sistema! </h2>
          <p style={{ color: '#7f8c8d', fontSize: '1.2rem' }}>
            Selecione uma opção no menu lateral para começar a trabalhar.
          </p>
        </div>
      );
    }
    
    if (telaAtiva === "cadastroUsuario") {
      return <CadastroUsuario />;
    }

    if (telaAtiva === "produtos") {
      return <h2>Área de Produtos</h2>;
    }
  };

  return (
    <div className="dashboard-layout">
      
      {/* ================= BARRA LATERAL ================= */}
      <aside className="sidebar">
        <h3>Meu Sistema</h3>
        
        <nav className="menu-lateral">
          {/* O botão agora faz duas coisas:
            1. onClick: Muda a tela ativa para "home"
            2. className dinâmica: Se a tela ativa for "home", ele ganha a classe 'ativo' (que deixa ele azul escuro)
          */}
          <button 
            className={`menu-btn ${telaAtiva === "home" ? "ativo" : ""}`}
            onClick={() => setTelaAtiva("home")}
          >
            Início
          </button>
          
          <button 
            className={`menu-btn ${telaAtiva === "cadastroUsuario" ? "ativo" : ""}`}
            onClick={() => setTelaAtiva("cadastroUsuario")}
          >
            Novo Usuário
          </button>
          
          <button 
            className={`menu-btn ${telaAtiva === "produtos" ? "ativo" : ""}`}
            onClick={() => setTelaAtiva("produtos")}
          >
            Produtos
          </button>
        </nav>
        
        <button className="logout-btn" onClick={handleLogout}>Sair</button>
      </aside>

      {/* ================= ÁREA PRINCIPAL ================= */}
      <main className="conteudo-principal">
        
        {/* Aqui nós simplesmente chamamos a função que decide o que desenhar! */}
        {renderizarConteudo()}

      </main>
    </div>
  );
}