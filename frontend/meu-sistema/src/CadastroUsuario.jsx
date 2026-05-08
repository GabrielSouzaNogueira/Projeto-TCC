import React, { useState } from 'react';
import './CadastroUsuario.css';

export default function CadastroUsuario() {
  // ==========================================
  // ESTADOS DO FORMULÁRIO (Ligados ao seu Backend)
  // ==========================================
  const [nome, setNome] = useState("");
  const [senha, setSenha] = useState("");
  const [email, setEmail] = useState("");
  const [telefone, setTelefone] = useState("");
  
  // O cargo começa vazio para obrigar o usuário a escolher uma opção
  const [cargo, setCargo] = useState("");

  // Estados de controle da tela
  const [salvando, setSalvando] = useState(false);
  const [mensagem, setMensagem] = useState({ texto: "", tipo: "" });

  // ==========================================
  // FUNÇÃO QUE CONECTA COM O BACKEND
  // ==========================================
  const handleSalvar = async (evento) => {
    evento.preventDefault();
    setMensagem({ texto: "", tipo: "" });

    // Validação extra por segurança (além do HTML)
    if (!cargo) {
      setMensagem({ texto: "Por favor, selecione um cargo válido.", tipo: "erro" });
      return;
    }

    setSalvando(true);

    // 1. MONTAMOS O PACOTE (O JSON) EXATAMENTE COMO O SPRING BOOT ESPERA
    const novoUsuario = {
      nome: nome,
      senha: senha,
      email: email || null, // Se estiver vazio, manda null para o banco
      telefone: telefone || null,
      userCargo: cargo // BALCONISTA, DEV ou ADMINISTRADOR
    };

    try {
      // ==========================================
      // 🔌 CONEXÃO COM A API (Pronto para o Backend!)
      // Quando seu Spring Boot estiver rodando, basta descomentar as linhas abaixo:
      // ==========================================
      
      
      const resposta = await fetch("http://localhost:8080/usuario/cadastro", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          // Se tiver token de segurança (JWT), você passaria aqui:
          // "Authorization": `Bearer ${localStorage.getItem("token")}`
        },
        body: JSON.stringify(novoUsuario)
      });

      if (!resposta.ok) {
        throw new Error("Erro ao salvar usuário no banco de dados!");
      }

      // --- SIMULAÇÃO DE ESPERA DA API (Apague quando usar o fetch acima) ---
      await new Promise(resolve => setTimeout(resolve, 1000));
      console.log("JSON enviado para o backend:", novoUsuario);
      // --------------------------------------------------------------------

      // Se deu sucesso:
      setMensagem({ texto: "Usuário cadastrado com sucesso!", tipo: "sucesso" });
      
      // Limpa o formulário
      setNome("");
      setSenha("");
      setEmail("");
      setTelefone("");
      setCargo("");

    } catch (erro) {
      setMensagem({ texto: erro.message, tipo: "erro" });
    } finally {
      setSalvando(false);
    }
  };

  // ==========================================
  // RENDERIZAÇÃO (O HTML DA TELA)
  // ==========================================
  return (
    <div className="cadastro-container">
      <h2>Novo Usuário</h2>
      <p className="descricao">Preencha os dados para cadastrar um novo colaborador.</p>

      <form onSubmit={handleSalvar} className="form-cadastro">
        
        {/* NOME (Obrigatório) */}
        <div className="input-group">
          <label>Nome Completo *</label>
          <input 
            type="text" 
            placeholder="Ex: Klaiver Oliveira" 
            value={nome}
            onChange={(e) => setNome(e.target.value)}
            required 
          />
        </div>

        {/* SENHA (Obrigatório) */}
        <div className="input-group">
          <label>Senha *</label>
          <input 
            type="password" 
            placeholder="EX: Senha123" 
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
            required 
          />
        </div>

        {/* CARGO (Obrigatório - Usamos um Select) */}
        <div className="input-group">
          <label>Cargo *</label>
          <select 
            value={cargo} 
            onChange={(e) => setCargo(e.target.value)} 
            required
          >
            <option value="" disabled>Selecione um cargo...</option>
            <option value="BALCONISTA">Balconista</option>
            <option value="ADMINISTRADOR">Administrador</option>
            <option value="DEV">Desenvolvedor (Dev)</option>
          </select>
        </div>

        <div className="linha-dupla">
          {/* E-MAIL (Opcional - Tirei o 'required') */}
          <div className="input-group">
            <label>E-mail (Opcional)</label>
            <input 
              type="email" 
              placeholder="email@empresa.com" 
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>

          {/* TELEFONE (Opcional - Tirei o 'required') */}
          <div className="input-group">
            <label>Telefone (Opcional)</label>
            <input 
              type="tel" 
              placeholder="(62) 90000-0000" 
              value={telefone}
              onChange={(e) => setTelefone(e.target.value)}
            />
          </div>
        </div>

        {/* MENSAGEM DE FEEDBACK */}
        {mensagem.texto && (
          <div className={`mensagem-alerta ${mensagem.tipo}`}>
            {mensagem.texto}
          </div>
        )}

        {/* BOTÃO SALVAR */}
        <button type="submit" disabled={salvando} className="btn-salvar">
          {salvando ? "Salvando no banco..." : "Cadastrar Usuário"}
        </button>

      </form>
    </div>
  );
}