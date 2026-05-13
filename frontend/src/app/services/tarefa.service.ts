import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tarefa } from '../models/tarefa';

@Injectable({
  providedIn: 'root'
})
export class TarefaService {

    private api = "http://localhost:8080/api/tarefa";

    constructor(private http: HttpClient){}

    listar():Observable<Tarefa[]>{
        return this.http.get<Tarefa[]>(this.api);
    }
}