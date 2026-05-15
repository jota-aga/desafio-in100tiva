package com.in100tiva.jh.todolist.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.in100tiva.jh.todolist.dto.TarefaDTO;
import com.in100tiva.jh.todolist.entity.Tarefa;
import com.in100tiva.jh.todolist.enums.StatusDaTarefa;
import com.in100tiva.jh.todolist.exception.NotFoundException;
import com.in100tiva.jh.todolist.repository.TarefaRepository;

@SpringBootTest
@Transactional
public class TarefaServiceTest {
	
	@Autowired
	private TarefaService tarefaService;
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	private TarefaDTO request;
	
	
	@BeforeEach
	public void setUp() {
		request = new TarefaDTO("titulo", "descricao", StatusDaTarefa.PENDENTE);
		tarefaRepository.deleteAll();
	}
	
	@Test
	public void shouldSaveSucessfully() {
		tarefaService.criarTarefa(request);
		
		List<Tarefa> tarefas = tarefaService.listarTarefas();
		
		assertFalse(tarefas.isEmpty());
		
		Tarefa tarefa = tarefas.getFirst();
		
		assertEquals(tarefa.getTitulo(), request.titulo());
		assertEquals(tarefa.getDescricao(), request.descricao());
		assertEquals(tarefa.getStatus(), StatusDaTarefa.EM_ANDAMENTO);
	}
	
	@Test
	public void findByIdWhenNotFound() {
		assertThrows(NotFoundException.class, () -> tarefaService.procurarTarefaPorId(Long.MAX_VALUE));
	}
	
	@Test
	public void shouldUpdateSucessfully() {
		tarefaService.criarTarefa(request);
		
		TarefaDTO atualizacao = new TarefaDTO("novo titulo", "nova descricao", StatusDaTarefa.FINALIZADA);
		
		List<Tarefa> tarefas = tarefaService.listarTarefas();
		
		assertFalse(tarefas.isEmpty());
		
		Tarefa tarefa = tarefas.getFirst();
		
		tarefaService.atualizarTarefa(atualizacao, tarefa.getId());
		
		tarefa = tarefaService.procurarTarefaPorId(tarefa.getId());
		
		assertEquals(tarefa.getTitulo(), atualizacao.titulo());
		assertEquals(tarefa.getDescricao(), atualizacao.descricao());
		assertEquals(tarefa.getStatus(), atualizacao.status());
	}
	
	@Test
	public void shouldDeleteSucessfully() {
		tarefaService.criarTarefa(request);
		
		List<Tarefa> tarefas = tarefaService.listarTarefas();
		
		assertFalse(tarefas.isEmpty());
		
		Tarefa tarefa = tarefas.getFirst();
		
		tarefaService.deletarTarefa(tarefa.getId());
		
		tarefas = tarefaService.listarTarefas();
		assertTrue(tarefas.isEmpty());
	}
	
	@Test
	public void shouldFindTarefasSucessfully() {
		Tarefa tarefaFinalizada = Tarefa.builder()
				.titulo("titulo abc")
				.descricao("descrição")
				.status(StatusDaTarefa.FINALIZADA)
				.build();
		Tarefa tarefaEmAndamento = Tarefa.builder()
				.titulo("titulo xyz")
				.descricao("descrição")
				.status(StatusDaTarefa.EM_ANDAMENTO)
				.build();

		tarefaService.criarTarefa(request);
		tarefaRepository.save(tarefaEmAndamento);
		tarefaRepository.save(tarefaFinalizada);
		
		
		List<Tarefa> tarefas = tarefaService.procurarTarefasFiltradas("titulo", 
				List.of(StatusDaTarefa.EM_ANDAMENTO.name()), "status");
		
		assertEquals(2, tarefas.size());
	}
	
	@Test
	public void shouldFindTarefasSucessfully_WhenTituloIsNull() {
		Tarefa tarefaFinalizada = Tarefa.builder()
				.titulo("titulo abc")
				.descricao("descrição")
				.status(StatusDaTarefa.FINALIZADA)
				.build();
		Tarefa tarefaEmAndamento = Tarefa.builder()
				.titulo("titulo xyz")
				.descricao("descrição")
				.status(StatusDaTarefa.EM_ANDAMENTO)
				.build();

		tarefaService.criarTarefa(request);
		tarefaRepository.save(tarefaEmAndamento);
		tarefaRepository.save(tarefaFinalizada);
		
		
		List<Tarefa> tarefas = tarefaService.procurarTarefasFiltradas(null, List.of(StatusDaTarefa.FINALIZADA.name()), "status");
		
		assertEquals(1, tarefas.size());
	}
	
	@Test
	public void shouldFindTarefasSucessfully_WhenTituloIsBlank() {
		Tarefa tarefaFinalizada = Tarefa.builder()
				.titulo("titulo abc")
				.descricao("descrição")
				.status(StatusDaTarefa.FINALIZADA)
				.build();
		Tarefa tarefaEmAndamento = Tarefa.builder()
				.titulo("titulo xyz")
				.descricao("descrição")
				.status(StatusDaTarefa.EM_ANDAMENTO)
				.build();

		tarefaService.criarTarefa(request);
		tarefaRepository.save(tarefaEmAndamento);
		tarefaRepository.save(tarefaFinalizada);
		
		
		List<Tarefa> tarefas = tarefaService.procurarTarefasFiltradas("             ", List.of(StatusDaTarefa.FINALIZADA.name()), "status");
		
		assertEquals(1, tarefas.size());
	}
	
	@Test
	public void shouldFindTarefasSucessfully_WhenStatusIsNull() {
		Tarefa tarefaFinalizada = Tarefa.builder()
				.titulo("titulo abc")
				.descricao("descrição")
				.status(StatusDaTarefa.FINALIZADA)
				.build();
		Tarefa tarefaEmAndamento = Tarefa.builder()
				.titulo("titulo xyz")
				.descricao("descrição")
				.status(StatusDaTarefa.EM_ANDAMENTO)
				.build();

		tarefaService.criarTarefa(request);
		tarefaRepository.save(tarefaEmAndamento);
		tarefaRepository.save(tarefaFinalizada);
		
		
		List<Tarefa> tarefas = tarefaService.procurarTarefasFiltradas("titulo", null, "status");
		
		assertEquals(3, tarefas.size());
	}
}
