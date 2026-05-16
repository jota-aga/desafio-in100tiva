import { Component, OnInit } from '@angular/core';
import { Tarefa } from '../../models/tarefa';
import { StatusDaTarefa } from '../../enums/StatusDaTarefa';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-tarefa-form',
  imports: [FormsModule],
  templateUrl: './tarefa-form.html',
  styleUrl: './tarefa-form.css',
})
export class TarefaForm implements OnInit{

  tarefa: Tarefa = {
    titulo: "",
    descricao:"",
    status: ""
  };
  isEdicao: boolean = false;
  opcoesStatus = Object.entries(StatusDaTarefa)
    .map(
      ([enumName, legenda]) => ({
        enumName: enumName,
        legenda: legenda
      })
    );

  ngOnInit(): void {
    if(this.tarefa.id){
      this.isEdicao = true;
    }
  }
}
