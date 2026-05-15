import { StatusDaTarefa } from "../enums/StatusDaTarefa";


export interface Tarefa{
    id?: number;
    titulo: string;
    descricao:string;
    status: string;
}