import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { movimentacaoDTO } from '../../models/movimentacaoDTO'; // <-- Import atualizado
import { AuthMovimentacao } from '../../services/auth-movimentacao';

@Component({
  selector: 'app-movimentacao',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './movimentacao.html',
  styleUrl: './movimentacao.css',
})
export class Movimentacao implements OnInit {
  carregando: boolean = false;
  buscaRealizada: boolean = false;

  // Atualizado para o novo DTO unificado
  listaMovimentacoes: movimentacaoDTO[] = [];
  movimentacoesExibidas: movimentacaoDTO[] = [];

  dataInicio: string = '';       
  dataFim: string = ''; 
  filtroTipo: string = '';        // <-- NOVO: Filtro para escolher PRODUTO ou USUARIO      
  filtroAcao: string = '';       
  filtroRegistro: string = '';    // <-- RENOMEADO: Antes era filtroUsuario    
  filtroResponsavel: string = ''; 

  constructor(
    private authMovimentacaoService: AuthMovimentacao,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {}

  limparFiltro(): void {
    this.dataInicio = '';
    this.dataFim = '';
    this.filtroTipo = '';         // <-- Adicionado para limpar
    this.filtroAcao = '';
    this.filtroRegistro = '';     // <-- Renomeado
    this.filtroResponsavel = '';
    this.buscaRealizada = false;
    this.listaMovimentacoes = [];
    this.movimentacoesExibidas = [];
  }

  buscarMovimentacoes(): void {
    this.carregando = true;

    const filtros: any = {};
    if (this.filtroTipo) filtros.tipo = this.filtroTipo;                    // <-- Mapeia para o backend
    if (this.dataInicio) filtros.dataInicio = this.dataInicio;
    if (this.dataFim) filtros.dataFim = this.dataFim;
    if (this.filtroAcao) filtros.acao = this.filtroAcao;
    if (this.filtroRegistro) filtros.registroAfetado = this.filtroRegistro; // <-- Mapeia para o backend
    if (this.filtroResponsavel) filtros.responsavel = this.filtroResponsavel;

    this.authMovimentacaoService.filtrarMovimentacoes(filtros).subscribe({
      next: (dados) => {
        this.listaMovimentacoes = dados;
        this.movimentacoesExibidas = dados; 
        this.buscaRealizada = true;
        this.carregando = false;
        this.cdr.detectChanges();
      },
      error: (erro) => {
        console.error('Erro ao buscar movimentações:', erro);
        this.movimentacoesExibidas = [];
        this.buscaRealizada = true;
        this.carregando = false;
        this.cdr.detectChanges();
      }
    });
  }
}