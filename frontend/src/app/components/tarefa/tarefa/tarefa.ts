import { Component, OnInit} from '@angular/core';
import { TarefaService } from '../../../services/tarefa.service';
import { Tarefa } from '../../../models/tarefa';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-tarefa',
  imports: [FormsModule],
  templateUrl: './tarefa.html',
  styleUrl: './tarefa.css',
})
export class TarefaComponent implements OnInit{
  tarefas: Tarefa[] = [];
  tituloFiltro: string = "";
  statusFiltro: string = "TODOS";

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
    if(!this.tituloFiltro.trim() && !this.statusFiltro.trim()){
      this.listar();
    }
    else if(this.statusFiltro == "TODOS"){
      this.service.procurarPorTitulo(this.tituloFiltro).subscribe(retorno => {
        this.tarefas = retorno;
      });
    }
    else if((!this.tituloFiltro.trim())){
      this.service.procurarPorStatus(this.statusFiltro).subscribe(retorno => {
        this.tarefas = retorno;
      });
    }
    else{
      this.service.procurarPorTituloEStatus(this.statusFiltro, this.tituloFiltro).subscribe(retorno => {
        this.tarefas = retorno;
      });
    }
  }
}
