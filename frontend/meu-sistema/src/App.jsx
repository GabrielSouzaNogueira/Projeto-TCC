// Importa o React (necessário para criar componentes)
import React from 'react';

// Importa os componentes de roteamento do react-router-dom
// BrowserRouter → controla a navegação
// Routes → agrupa as rotas
// Route → define cada rota individual
import { BrowserRouter, Routes, Route } from 'react-router-dom';
// Tela de login (rota pública)
import Login from './Login';

// Tela principal do sistema (rota privada)
import Dashboard from './Dashboard';
// Componente que verifica se o usuário está autenticado
// Ele decide se pode acessar a rota ou não
import ProtectedRoute from './ProtectedRoute';

// Importa o CSS principal da aplicação
import './App.css';
// Componente principal da aplicação
function App() {

  // Aqui estamos retornando toda a estrutura de rotas
  return (

    // BrowserRouter envolve toda aplicação
    // Ele permite que o React controle as rotas sem recarregar a página (SPA)
    <BrowserRouter>

      {/* Routes agrupa todas as rotas do sistema */}
      <Routes>
        
        {/* 
          path="/" → define o caminho da URL
          element={<Login />} → define qual componente será exibido
          Essa rota é pública, qualquer pessoa pode acessar
        */}
        <Route path="/" element={<Login />} />
        
        

        
        {/* 
          rota privada!
          path="/dashboard" → URL da dashboard
          element= → o que será renderizado quando acessar essa rota
        */}
        <Route 
          path="/dashboard" 
          element={
            /*
              ProtectedRoute funciona como um "guarda".
              Ele verifica:
              - Se o usuário está logado → permite acessar Dashboard
              - Se não estiver → redireciona para Login             
              A Dashboard fica como "filha" do ProtectedRoute.
            */
            <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>
          } 
        />
        
      </Routes>
    </BrowserRouter>
  );
}

// Exporta o componente App para ser usado no index.js
export default App;