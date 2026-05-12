package com.in100tiva.jh.todolist.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.in100tiva.jh.todolist.dto.TarefaRequest;
import com.in100tiva.jh.todolist.entity.Tarefa;
import com.in100tiva.jh.todolist.enums.StatusDaTarefa;
import com.in100tiva.jh.todolist.repository.TarefaRepository;

@SpringBootTest
@Transactional
public class TarefaServiceTest {
	
	@Autowired
	private TarefaService tarefaService;
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	private TarefaRequest request;
	
	
	@BeforeEach
	public void setUp() {
		request = new TarefaRequest("titulo", "descricao", StatusDaTarefa.PENDENTE);
		tarefaRepository.deleteAll();
	}
	
	@Test
	public void shouldSaveSucessfully() {
		tarefaService.criarTarefa(request);
		
		List<Tarefa> tarefas = tarefaService.listarTarefas();
		
		assertFalse(tarefas.isEmpty());
		
		Tarefa tarefa = tarefas.getFirst();
		
		assertEquals(tarefa.getTitulo(), request.titulo());
		assertEquals(tarefa.getDescrição(), request.descricao());
		assertEquals(tarefa.getStatus(), StatusDaTarefa.EM_ANDAMENTO);
	}
	
	public void shouldUpdateSucessfully() {
		tarefaService.criarTarefa(request);
		
		TarefaRequest atualizacao = new TarefaRequest("novo titulo", "nova descricao", StatusDaTarefa.FINALIZADA);
		
		List<Tarefa> tarefas = tarefaService.listarTarefas();
		
		assertFalse(tarefas.isEmpty());
		
		Tarefa tarefa = tarefas.getFirst();
		
		tarefaService.atualizarTarefa(atualizacao, tarefa.getId());
		
		tarefa = tarefaService.procurarTarefaPorId(tarefa.getId());
		
		assertEquals(tarefa.getTitulo(), atualizacao.titulo());
		assertEquals(tarefa.getDescrição(), atualizacao.descricao());
		assertEquals(tarefa.getStatus(), atualizacao.status());
	}
	
	public void shouldDeleteSucessfully() {
		tarefaService.criarTarefa(request);
		
		List<Tarefa> tarefas = tarefaService.listarTarefas();
		
		assertFalse(tarefas.isEmpty());
		
		Tarefa tarefa = tarefas.getFirst();
		
		tarefaService.deletarTarefa(tarefa.getId());
		
		assertTrue(tarefas.isEmpty());
	}
}
