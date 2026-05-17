import { Component, OnInit } from '@angular/core';
import { Tarefa } from '../../models/tarefa';
import { StatusDaTarefa } from '../../enums/StatusDaTarefa';
import { FormsModule } from '@angular/forms';
import { TarefaService } from '../../services/tarefa.service';
import { Router, RouterLink } from '@angular/router';

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
  };
  isEdicao: boolean = false;
  opcoesStatus = Object.entries(StatusDaTarefa)
    .map(
      ([enumName, legenda]) => ({
        enumName: enumName,
        legenda: legenda
      })
    );

  constructor(private service: TarefaService,
              private router: Router
  ){}

  ngOnInit(): void {
    if(this.tarefa.id){
      this.isEdicao = true;
    }
  }

  salvar(): void{
    this.tarefa.status = "PENDENTE";
    this.service.criar(this.tarefa).subscribe(() =>
      this.router.navigate([""])
    );
  }
}
