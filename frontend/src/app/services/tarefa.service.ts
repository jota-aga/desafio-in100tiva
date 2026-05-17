import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
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

     filtrar(titulo: string, status: string[], sortBy: string):Observable<Tarefa[]>{
        let params = new HttpParams()
            .set("titulo", titulo)
            .set("sortBy", sortBy);
            status.forEach(status => 
                params = params.append("status", status)
            );

        return this.http.get<Tarefa[]>(this.api+"/filter", {params});
    }

    criar(tarefa: Tarefa): Observable<void>{
        return this.http.post<void>(this.api, tarefa);
    }
}