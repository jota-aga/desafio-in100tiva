import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TarefaListagemComponent } from './components/tarefa.listagem/tarefa-listagem';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, TarefaListagemComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('todolist-frontend');
}
