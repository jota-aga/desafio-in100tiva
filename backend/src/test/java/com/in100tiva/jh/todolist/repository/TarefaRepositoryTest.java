package com.in100tiva.jh.todolist.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.in100tiva.jh.todolist.entity.Tarefa;
import com.in100tiva.jh.todolist.enums.StatusDaTarefa;

@DataJpaTest
public class TarefaRepositoryTest {
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	private Tarefa tarefa;
	
	
	@BeforeEach
	public void setUp() {
		tarefa = Tarefa.builder()
				.titulo("titulo")
				.descricao("descrição")
				.status(StatusDaTarefa.PENDENTE)
				.build();
		tarefaRepository.deleteAll();
	}
	
	@Test
	public void shouldSaveSucessfully() {
		tarefaRepository.save(tarefa);
		
		List<Tarefa> tarefas = tarefaRepository.findAll();
		
		assertFalse(tarefas.isEmpty());
	}
	
	@Test
	public void shouldFindOnlyByStatus() {
		Tarefa tarefa2 = Tarefa.builder()
				.titulo("titulo")
				.descricao("descrição")
				.status(StatusDaTarefa.FINALIZADA)
				.build();
		tarefaRepository.saveAll(List.of(tarefa, tarefa2));
		
		List<Tarefa> tarefas = tarefaRepository.findAllByStatus(StatusDaTarefa.FINALIZADA);
		
		assertEquals(tarefas.size(), 1);
	}
	
	@Test
	public void shouldFindByTituloContain() {
		tarefaRepository.save(tarefa);

		
		List<Tarefa> tarefas = tarefaRepository.findAllByTituloContains("tit");
		
		assertEquals(tarefas.size(), 1);
	}
	
	@Test
	public void shouldFindByTituloAndStatus() {
		Tarefa tarefaFinalizada = Tarefa.builder()
				.titulo("titulo")
				.descricao("descrição")
				.status(StatusDaTarefa.FINALIZADA)
				.build();
		Tarefa tarefaEmAndamento = Tarefa.builder()
				.titulo("titulo")
				.descricao("descrição")
				.status(StatusDaTarefa.EM_ANDAMENTO)
				.build();
		tarefaRepository.saveAll(List.of(tarefa, tarefaEmAndamento, tarefaFinalizada));
		
		List<Tarefa> tarefas = tarefaRepository.findAllByTituloContainsAndStatus("tit",StatusDaTarefa.FINALIZADA);
		
		assertEquals(tarefas.size(), 1);
	}
}
