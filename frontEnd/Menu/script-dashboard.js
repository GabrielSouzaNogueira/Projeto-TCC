// --- 1. Inicialização ---
$(document).ready(function() {
    // Inicializa Select2 para selects bonitos
    $('.select2-padrao').select2({
        width: '100%'
    });
});

// --- 2. Controle de Navegação (SPA) ---
const mapTelas = {
    'menu': 'menuInicial',
    'novo_usuario': 'telaCadastroUsuario',
    'auditoria': 'telaAuditoria',
    'ajuda': 'telaAjuda'
};

document.querySelectorAll('.sidebar nav a[data-action]').forEach(link => {
    link.addEventListener('click', function(e) {
        e.preventDefault();
        const acao = this.getAttribute('data-action');

        if (acao === 'sair') {
            if(confirm("Deseja realmente sair?")) {
                window.location.href = "index.html"; // Redireciona para o login
            }
            return;
        }

        if (mapTelas[acao]) {
            // Esconde todas as telas e remove active do menu
            document.querySelectorAll('.tela').forEach(t => t.style.display = 'none');
            document.querySelectorAll('.sidebar nav a').forEach(l => l.classList.remove('active'));
            
            // Mostra a tela clicada e ativa o link
            document.getElementById(mapTelas[acao]).style.display = 'block';
            this.classList.add('active'); 
            
            if(acao === 'auditoria') {
                carregarAuditoria();
            }
        }
    });
});

// --- 3. Sidebar Toggle ---
const btnToggle = document.getElementById('btnToggleSidebar');
const sidebar = document.querySelector('.sidebar');
if(btnToggle) {
    btnToggle.addEventListener('click', () => {
        sidebar.classList.toggle('collapsed');
    });
}

// --- 4. Submenus (Accordion) ---
document.querySelectorAll('.menu-toggle').forEach(item => {
    item.addEventListener('click', function(e) {
        e.preventDefault();
        this.parentElement.classList.toggle('open');
    });
});

// --- 5. Funções de Negócio ---

function salvarUsuario() {
    const nome = $('#userNome').val();
    const email = $('#userEmail').val();
    const senha = $('#userSenha').val();
    const telefone = $('#userTelefone').val();

    if (!nome || !senha) {
        mostrarModal("Erro", "Por favor, preencha os campos obrigatórios (Nome e Senha).");
        return;
    }

    const usuarioDTO = {
        nome: nome,
        email: email,
        senha: senha, 
        telefone: telefone
    };

    $.ajax({
        url: 'http://localhost:8080/usuario/cadastro', 
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(usuarioDTO),
        success: function(response) {
            mostrarModal("Sucesso", "Usuário cadastrado com sucesso!");
            // Limpa o formulário
            $('#userNome').val(''); $('#userEmail').val(''); $('#userSenha').val(''); $('#userTelefone').val('');
        },
        error: function() {
            mostrarModal("Simulação/Erro", "Não foi possível conectar ao Java. JSON: " + JSON.stringify(usuarioDTO));
        }
    });
}

function carregarAuditoria() {
    const tbody = document.getElementById('tbodyAuditoria');
    if(tbody) tbody.innerHTML = '<tr><td colspan="5" style="text-align:center;">Buscando dados no servidor...</td></tr>'; 
    
    // Aqui você faria o seu $.get para listar o histórico
}

function mostrarModal(titulo, msg) {
    document.getElementById('modalTitulo').innerText = titulo;
    document.getElementById('modalMensagem').innerText = msg;
    document.getElementById('modalGlobal').style.display = 'block';
}

function fecharModal() {
    document.getElementById('modalGlobal').style.display = 'none';
}