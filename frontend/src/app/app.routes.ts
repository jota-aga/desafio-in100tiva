import { Routes } from '@angular/router';
import { TarefaForm } from './components/tarefa-form/tarefa-form';
import { TarefaListagem } from './components/tarefa-listagem/tarefa-listagem';

export const routes: Routes = [
  {
    path: 'form',
    component: TarefaForm
  },
  {
    path: '',
    component: TarefaListagem
  }
];
