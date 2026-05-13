import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TarefaComponent } from './components/tarefa/tarefa/tarefa';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, TarefaComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('todolist-frontend');
}
