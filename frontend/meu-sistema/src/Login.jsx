import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Login() {
  
  // Estados do Login
  const [usuario, setUsuario] = useState("");
  const [senha, setSenha] = useState("");
  const [erro, setErro] = useState("");
  const [entrando, setEntrando] = useState(false);

  // Estados da Recuperação
  const [modoRecuperacao, setModoRecuperacao] = useState(false);
  const [emailRecuperacao, setEmailRecuperacao] = useState("");
  const [mensagemRecuperacao, setMensagemRecuperacao] = useState("");
  const [carregando, setCarregando] = useState(false);

  const navigate = useNavigate();

 /**
 * handleLogin - Faz o processo de login (teste local ou requisição ao backend)
 * @param {Event} evento - Evento do formulário (submit)
 */
  const handleLogin = async (evento) => {
  // Impede o comportamento padrão do formulário (recarregar a página)
  evento.preventDefault();

  // Limpa mensagens de erro anteriores
  setErro("");

  // Sinaliza que o processo de login está em andamento (ex.: desabilitar botão)
  setEntrando(true);

  // Entrada falsa/master para testes locais sem backend
  // Mantém um "backdoor" temporário para desenvolvimento; remover quando a API estiver pronta
  if (usuario === "admin" && senha === "123") {
    // Marca o usuário como autenticado no localStorage (simulação)
    localStorage.setItem("auth", "true");

    // Redireciona para a rota protegida após login
    navigate("/dashboard");

    // Reseta o estado de carregamento
    setEntrando(false);

    // Interrompe a execução para não continuar com a chamada real ao backend
    return;
  }

  // Monta o objeto de credenciais que será enviado ao backend
  const credenciais = {
    nome: usuario,
    senha: senha
  };

  try {
    // Faz a requisição POST para o endpoint de login do backend
    const resposta = await fetch("http://localhost:8080/usuario/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json" // Indica que o corpo é JSON
      },
      body: JSON.stringify(credenciais) // Serializa as credenciais
    });

    // Se a resposta for 200 OK, processa o retorno
    if (resposta.ok) {
      const dados = await resposta.json();

      localStorage.setItem("auth", "true");

      // EM VEZ DE SALVAR SÓ O 'DADOS', VAMOS SALVAR O NOME QUE VOCÊ DIGITOU
      const usuarioParaSalvar = {
        nome: usuario, // Aqui pegamos o estado 'usuario' do seu formulário
        logadoEm: dados.timestamp
      };

      localStorage.setItem("userLogged", JSON.stringify(usuarioParaSalvar));

      navigate("/dashboard");

    // Se o backend retornar 401, informa credenciais inválidas ao usuário
    } else if (resposta.status === 401) {
      setErro("Usuário ou senha inválidos!");

    // Para outros códigos de erro, lança exceção para cair no catch
    } else {
      throw new Error("Erro no servidor");
    }

  } catch (error) {
    // Captura falhas de rede ou quando o backend está offline
    // Exibe mensagem amigável orientando a usar credenciais de teste
    setErro("Servidor offline. Use as credenciais de teste para entrar.");

    // Loga o erro no console para depuração do desenvolvedor
    console.error("Erro na conexão real:", error);

  } finally {
    // Garante que o estado de carregamento seja resetado independentemente do resultado
    setEntrando(false);
  }
};


  // Função de Recuperar Senha
  const handleEnviarRecuperacao = async (evento) => {
    evento.preventDefault(); 
    setCarregando(true);
    setMensagemRecuperacao("");

    try {
      await new Promise(resolve => setTimeout(resolve, 1500));
      setMensagemRecuperacao(`Um link de recuperação foi enviado para: ${emailRecuperacao}`);
      setEmailRecuperacao("");
    } catch (error) {
      setMensagemRecuperacao("Erro ao tentar enviar o e-mail. Tente novamente.");
    } finally {
      setCarregando(false);
    }
  };

  return (
    <div className="login-container">
      <div className="card">
        
        {modoRecuperacao ? (
          
          /* --- TELA DE RECUPERAÇÃO DE SENHA --- */
          <div>
            <h2>Recuperar Senha</h2>
            
            {/* Trocamos o style pela classe 'texto-recuperacao' */}
            <p className="texto-recuperacao">
              Digite seu e-mail abaixo e enviaremos as instruções para redefinir sua senha.
            </p>

            <form onSubmit={handleEnviarRecuperacao}>
              <input 
                type="email" 
                placeholder="Seu e-mail cadastrado" 
                value={emailRecuperacao} 
                onChange={(evento) => setEmailRecuperacao(evento.target.value)} 
                required 
              />
              
              {/* O CSS agora cuida da cor quando o 'disabled' for verdadeiro! */}
              <button type="submit" disabled={carregando}>
                {carregando ? "Enviando..." : "Enviar link de recuperação"}
              </button>

              {/* Trocamos o style pela classe 'mensagem-sucesso' */}
              {mensagemRecuperacao && (
                <div className="mensagem-sucesso">
                  {mensagemRecuperacao}
                </div>
              )}

              <div className="esqueceu-senha-container">
                <a 
                  href="#" 
                  onClick={(e) => { e.preventDefault(); setModoRecuperacao(false); setMensagemRecuperacao(""); }} 
                  className="esqueceu-senha-link"
                >
                  Voltar para o Login
                </a>
              </div>
            </form>
          </div>

        ) : (
          
          /* --- TELA DE LOGIN PRINCIPAL --- */
          <div>
            <h2>Login</h2>
            <form onSubmit={handleLogin}>
              <input 
                type="text" 
                placeholder="Usuário" 
                value={usuario} 
                onChange={(evento) => setUsuario(evento.target.value)} 
                required 
              />
              <input 
                type="password" 
                placeholder="Senha" 
                value={senha} 
                onChange={(evento) => setSenha(evento.target.value)} 
                required 
              />
              
              {erro && <div className="mensagem-erro">{erro}</div>}
              
              {/* O CSS agora cuida da cor quando o 'disabled' for verdadeiro! */}
              <button type="submit" disabled={entrando}>
                {entrando ? "Entrando..." : "Entrar"}
              </button>

              <div className="esqueceu-senha-container">
                <a 
                  href="#" 
                  onClick={(e) => { e.preventDefault(); setModoRecuperacao(true); }} 
                  className="esqueceu-senha-link"
                >
                  Esqueceu a senha?
                </a>
              </div>
            </form>
          </div>

        )}

      </div>
    </div>
  );
}