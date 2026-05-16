import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TarefaListagemComponent } from './tarefa-listagem';

describe('Tarefa', () => {
  let component: TarefaListagemComponent;
  let fixture: ComponentFixture<TarefaListagemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TarefaListagemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TarefaListagemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
