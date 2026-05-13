import { Component, OnInit } from '@angular/core';
import { TarefaService } from '../../../services/tarefa.service';
import { Tarefa } from '../../../models/tarefa';
import { NgForOf } from "@angular/common";

@Component({
  selector: 'app-tarefa',
  imports: [],
  templateUrl: './tarefa.html',
  styleUrl: './tarefa.css',
})
export class TarefaComponent implements OnInit{
  tarefas: Tarefa[] = [];

  constructor(private service: TarefaService){}

  ngOnInit(): void {
    this.listar();
  }

  listar(): void{
    this.service.listar().subscribe(retorno => {
        this.tarefas = retorno;
        console.log(this.tarefas.length)
      });
  }
}
