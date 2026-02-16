// Captura o formulário pelo ID (precisa ter <form id="loginForm"> no HTML)
const form = document.getElementById('loginForm');

// Adiciona um "ouvinte" (listener) para quando o formulário for enviado
form.addEventListener('submit', function(event) {
  event.preventDefault(); // impede que o navegador recarregue a página automaticamente

  // Pega os valores digitados nos campos de entrada
  const nome = document.getElementById('nome').value.trim();   // remove espaços extras
  const senha = document.getElementById('senha').value.trim();

  // Validação básica no frontend (não deixa enviar vazio)
  if (nome === "") {
    alert("Por favor, digite seu nome.");
    return; // interrompe o envio
  }

  if (senha === "") {
    alert("Por favor, digite sua senha.");
    return;
  }

  // Se passou nas validações, envia os dados para o backend usando fetch
  fetch("http://localhost:8080/usuario/login", { // URL do endpoint no backend
    method: "POST",                              // método HTTP
    headers: { "Content-Type": "application/json" }, // tipo de conteúdo enviado
    body: JSON.stringify({ nome, senha })        // transforma os dados em JSON
  })
  .then(async response => {
    // Converte a resposta do backend em JSON
    const data = await response.json();
    console.log("Resposta do backend:", data, "Status:", response.status);

    // Se o backend respondeu com sucesso (status 200 OK)
    if (response.ok) {
      alert(data.mensagem); // mostra mensagem de sucesso
    } else {
      // Se o backend respondeu com erro (401, 400, etc.)
      alert(`${data.codigoErro ?? "ERRO"}: ${data.mensagem}`);
    }
  })
  .catch(error => {
    // Se deu erro de conexão (ex: backend desligado)
    console.error("Erro:", error);
    alert("Erro ao conectar com o servidor.");
  });
});