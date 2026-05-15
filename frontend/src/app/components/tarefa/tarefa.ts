import { Component, OnInit} from '@angular/core';
import { TarefaService } from '../../../services/tarefa.service';
import { Tarefa } from '../../../models/tarefa';
import { FormsModule } from '@angular/forms';
import { StatusDaTarefa } from '../../../enums/StatusDaTarefa';

@Component({
  selector: 'app-tarefa',
  imports: [FormsModule],
  templateUrl: './tarefa.html',
  styleUrl: './tarefa.css',
})
export class TarefaComponent implements OnInit{
  tarefas: Tarefa[] = [];
  tituloFiltro: string = "";
  sortBy: string = "status"
  opcoesStatusCheckBox = Object.entries(StatusDaTarefa).map(([enumName, legenda]) => ({
    enumName: enumName,
    legenda: legenda,
    selecionado: true,
  }));

  constructor(private service: TarefaService){}

  ngOnInit(): void {
    this.listar();
  }

  listar(): void{
    this.service.listar().subscribe(retorno => {
        this.tarefas = retorno;
      });
  }

  filtrar(){
    this.service.filtrar(this.tituloFiltro, this.getEnumsParaFiltro(), this.sortBy).subscribe(retorno => {
        this.tarefas = retorno;
      });
  }

  private getEnumsParaFiltro(): string[]{
    return this.opcoesStatusCheckBox
    .filter(opcao => opcao.selecionado)
    .map(opcao => opcao.enumName);
  }
}

