// --- FUNÇÃO VISUAL: Mostrar/Ocultar Senha ---
function togglePassword() {
  const passwordInput = document.getElementById('senha'); // ajustado para o id atual
  const toggleBtn = document.querySelector('.password-toggle');

  if (!passwordInput) return;

  if (passwordInput.type === 'password') {
    passwordInput.type = 'text';
    toggleBtn.textContent = '🙈';
  } else {
    passwordInput.type = 'password';
    toggleBtn.textContent = '👁';
  }
}

// --- LÓGICA: ESQUECI MINHA SENHA ---
const btnEsqueci = document.getElementById('btnEsqueciSenha');
if (btnEsqueci) {
  btnEsqueci.addEventListener('click', async function(e) {
    e.preventDefault();
    const email = prompt("Digite seu e-mail para receber o link de recuperação:");

    if (email && email.trim() !== "") {
      try {
        const response = await fetch('http://localhost:8080/api/recuperar-senha', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ email: email.trim() })
        });

        if (response.ok) {
          alert("Sucesso! Verifique sua caixa de e-mail.");
        } else if (response.status === 404) {
          alert("Este e-mail não está cadastrado no sistema.");
        } else {
          alert("Erro ao tentar enviar o e-mail.");
        }
      } catch (erro) {
        console.error('Erro fetch recuperar-senha:', erro);
        alert("Erro ao conectar com o servidor.");
      }
    }
  });
}

// --- LÓGICA: LOGIN ---
document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('loginForm');
  if (!form) return;

  form.addEventListener('submit', async function(e) {
    e.preventDefault();

    console.log('submit disparado');

    const button = document.querySelector('.login-btn');
    const btnText = document.querySelector('.btn-text');
    const nomeInput = document.getElementById('nome').value.trim();   // nome do campo
    const senhaInput = document.getElementById('senha').value.trim(); // senha do campo

    if (!nomeInput || !senhaInput) {
      alert('Preencha nome e senha.');
      return;
    }

    const textoOriginal = btnText ? btnText.innerText : 'Entrar';
    if (btnText) btnText.innerText = "Conectando...";
    if (button) button.disabled = true;

    try {
      console.log('Enviando fetch para backend...', { nome: nomeInput, senha: senhaInput });

      const response = await fetch('http://localhost:8080/usuario/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        // credentials: 'include', // descomente se usar cookies/sessão
        body: JSON.stringify({
          nome: nomeInput,   // chaves devem ser as do backend (UserDTO)
          senha: senhaInput
        })
      });

      console.log('Status da resposta:', response.status);

      if (response.ok) {
        const data = await response.json().catch(()=>null);
        console.log('Resposta do backend:', data);
        alert("Login realizado com sucesso!");
        
        // dentro do código de sucesso do login
        window.location.href = '/frontEnd/Menu/dashboard.html';

      } else if (response.status === 401) {
        alert("Usuário ou senha incorretos!");
      } else {
        const text = await response.text();
        console.error('Erro no servidor:', response.status, text);
        alert("Usuario nao existente no sistema");
      }

    } catch (erro) {
      console.error('Erro no fetch ou exceção JS:', erro);
      alert("Erro ao conectar com o servidor. Verifique se o backend está rodando e veja o console.");
    } finally {
      if (btnText) btnText.innerText = textoOriginal;
      if (button) button.disabled = false;
    }
  });
});