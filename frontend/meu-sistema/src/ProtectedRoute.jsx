import React, { useState, useEffect } from 'react';
import { Navigate } from 'react-router-dom';

export default function ProtectedRoute({ children }) {
  
  const [temCracha, setTemCracha] = useState(localStorage.getItem("auth") === "true");

  useEffect(() => {
    
    // Função "Sniper": Ela ignora o evento e vai olhar direto no próprio armazenamento do navegador
    const verificarSeguranca = () => {
      const crachaAtual = localStorage.getItem("auth");
      // Se por qualquer motivo o crachá sumiu ou não for 'true', bloqueia!
      if (crachaAtual !== "true") {
        setTemCracha(false);
      }
    };

    // 1. O Alarme entre abas (Storage)
    window.addEventListener("storage", verificarSeguranca);
    
    // 2. A Blindagem Extra (Focus): 
    // Toda vez que o usuário clicar na aba do navegador ou voltar para ela, o React confere o crachá.
    window.addEventListener("focus", verificarSeguranca);

    // Função de limpeza
    return () => {
      window.removeEventListener("storage", verificarSeguranca);
      window.removeEventListener("focus", verificarSeguranca);
    };
    
  }, []);

  if (!temCracha) {
    return <Navigate to="/" replace />;
  }

  return children;
}