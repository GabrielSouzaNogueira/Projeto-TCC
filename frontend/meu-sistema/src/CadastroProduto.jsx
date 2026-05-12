import React, { useState } from "react";

export default function CadastroProduto() {
    // Estados para armazenar os valores dos inputs do formulário
    const [nome, setNome] = useState("");          // Nome do produto
    const [codBarra, setCodBarra] = useState("");  // Código de barras
    const [quantidade, setQuantidade] = useState(""); // Quantidade em estoque
    const [precoCusto, setPrecoCusto] = useState(""); // Preço de custo
    const [precoVenda, setPrecoVenda] = useState(""); // Preço de venda
    const [salvando, setSalvando] = useState(false);  // Estado para indicar se está salvando

    // Função chamada ao submeter o formulário
    const handleSalvarProduto = async (e) => {
        e.preventDefault(); // Evita o recarregamento da página
        setSalvando(true);  // Ativa o estado de "salvando"

        // --- PARTE ADICIONADA: BUSCANDO USUÁRIO LOGADO ---
        const userLoggedData = localStorage.getItem("userLogged");
        const userObject = userLoggedData ? JSON.parse(userLoggedData) : null;

        const usuarioIdentificador = userObject ? userObject.nome : "admin";

        // Cria objeto com os dados do produto
        const novoProduto = {
            nome: nome,
            codBarra: codBarra,
            quantidade: parseInt(quantidade),   // Converte para número inteiro
            precoCusto: parseFloat(precoCusto), // Converte para número decimal
            precoVenda: parseFloat(precoVenda)  // Converte para número decimal
        };

        try {
            console.log("Enviando para o Spring Boot:", novoProduto);
            
            // 1. Faz a chamada para a API do seu Backend
            const resposta = await fetch("http://localhost:8080/produto/cadastro", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json", // Avisa o Java que é um JSON
                    "X-Usuario-Logado": usuarioIdentificador // Adiciona o Header obrigatório
                },
                body: JSON.stringify(novoProduto) // Converte o objeto para texto
            });

            // 2. Verifica se o Java aceitou o cadastro (Status 201 ou 200)
            if (resposta.ok) {
                const sucessoServidor = await resposta.json();
                alert(sucessoServidor.mensagem || "Produto cadastrado com sucesso no banco!");
                
                // Limpa os campos após o sucesso
                setNome("");
                setCodBarra("");
                setQuantidade("");
                setPrecoCusto("");
                setPrecoVenda("");
            } else {
                // Se o Java retornar erro (ex: código de barra duplicado)
                const erroServidor = await resposta.json();
                alert("Erro no cadastro: " + (erroServidor.mensagem || "Falha no servidor"));
            }

        } catch (error) {
            console.error("Erro de conexão:", error);
            alert("Não consegui falar com o servidor Java. Ele está rodando?");

        } finally {
            setSalvando(false);
        }
    }

    return (
        <div className="Cadastro-Produto-Container">
            <div className="Cadastro-Produto-Box">
                <h2>Cadastrar Produto</h2>
                {/* O formulário chama handleSalvarProduto ao ser submetido */}
                <form onSubmit={handleSalvarProduto}>
                    
                    {/* Campo: Nome do Produto */}
                    <div className="input-group">
                        <label>Nome do Produto</label>
                        <input 
                            type="text" 
                            placeholder="Ex: Tênis Esportivo" 
                            value={nome} 
                            onChange={(evento) => setNome(evento.target.value)} 
                            required
                        />
                    </div>

                    {/* Campo: Código de Barras */}
                    <div className="input-group">
                        <label>Código de Barras</label>
                        <input 
                            type="text" 
                            placeholder="000000000000" 
                            value={codBarra} 
                            onChange={(evento) => setCodBarra(evento.target.value)} 
                            required 
                        />
                    </div>

                    {/* Campo: Quantidade em Estoque */}
                    <div className="input-group">
                        <label>Quantidade em Estoque</label>
                        <input 
                            type="number" 
                            placeholder="0" 
                            value={quantidade} 
                            onChange={(evento) => setQuantidade(evento.target.value)} 
                            required 
                        />
                    </div>

                    {/* Campos de preços (custo e venda) */}
                    <div className="linha-precos">
                        <div className="input-group">
                            <label>Preço de Custo</label>
                            <input 
                                type="number" 
                                step="0.01" 
                                placeholder="0.00" 
                                value={precoCusto} 
                                onChange={(evento) => setPrecoCusto(evento.target.value)} 
                                required
                            />
                        </div>

                        <div className="input-group">
                            <label>Preço de Venda</label>
                            <input 
                                type="number" 
                                step="0.01" 
                                placeholder="0.00" 
                                value={precoVenda} 
                                onChange={(evento) => setPrecoVenda(evento.target.value)} 
                                required 
                            />
                        </div>
                    </div>

                    {/* Botão de envio do formulário */}
                    <button type="submit" disabled={salvando} className="btn-salvar">
                        {salvando ? "Salvando no sistema..." : "Cadastrar Produto"}
                    </button>
                </form>
            </div>
        </div>
    );
}
