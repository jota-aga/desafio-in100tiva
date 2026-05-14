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
  statusFiltro: string = "";
  sortBy: string = "status";

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
    this.service.filtrar(this.tituloFiltro, this.statusFiltro, this.sortBy).subscribe(retorno => {
        this.tarefas = retorno;
      });
  }
}

