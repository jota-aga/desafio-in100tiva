package com.in100tiva.jh.todolist.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.in100tiva.jh.todolist.dto.TarefaDTO;
import com.in100tiva.jh.todolist.entity.Tarefa;
import com.in100tiva.jh.todolist.enums.StatusDaTarefa;
import com.in100tiva.jh.todolist.repository.TarefaRepository;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class TarefaControllerTest {
	
	private static String URI = "/api/tarefa";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	private TarefaDTO tarefaRequest;
	
	@BeforeEach
	public void setUp() {
		tarefaRequest = new TarefaDTO("titulo", "descrição", StatusDaTarefa.FINALIZADA);
		tarefaRepository.deleteAll();
	}
	
	@Test
	public void shouldCreateSucessfully() throws JacksonException, Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(URI)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(tarefaRequest)))
		.andExpect(MockMvcResultMatchers.status().isCreated());
		
		List<Tarefa> tarefas = tarefaRepository.findAll();
		
		assertEquals(tarefas.size(), 1);
	}
	
	@Test
	public void createTarefa_WhenTituloIsEmpty() throws JacksonException, Exception {
		tarefaRequest = new TarefaDTO("   ", "descrição", StatusDaTarefa.FINALIZADA);

		mockMvc.perform(MockMvcRequestBuilders.post(URI)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(tarefaRequest)))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		List<Tarefa> tarefas = tarefaRepository.findAll();
		
		assertEquals(tarefas.size(), 0);
	}
	
	@Test
	public void shouldUpdateSucessfully() throws JacksonException, Exception {
		Tarefa tarefa = Tarefa.builder()
				.titulo("titulo antes")
				.descricao("descrição antes")
				.status(StatusDaTarefa.PENDENTE)
				.build();
		tarefa = tarefaRepository.save(tarefa);
		
		mockMvc.perform(MockMvcRequestBuilders.put(URI+"/"+tarefa.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(tarefaRequest)))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
		tarefa = tarefaRepository.findById(tarefa.getId()).get();
		
		assertEquals(tarefa.getTitulo(), tarefaRequest.titulo());
		assertEquals(tarefa.getDescricao(), tarefaRequest.descricao());
		assertEquals(tarefa.getStatus(), tarefaRequest.status());

	}
	
	@Test
	public void shouldDeleteSucessfully() throws JacksonException, Exception {
		Tarefa tarefa = Tarefa.builder()
				.titulo("titulo")
				.descricao("descrição")
				.status(StatusDaTarefa.PENDENTE)
				.build();
		tarefa = tarefaRepository.save(tarefa);
		
		mockMvc.perform(MockMvcRequestBuilders.delete(URI+"/"+tarefa.getId()))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
		Optional<Tarefa> optionalTarefa = tarefaRepository.findById(tarefa.getId());
		
		assertTrue(optionalTarefa.isEmpty());
	}
	
	@Test
	public void shouldFindByStatusSucessfully() throws JacksonException, Exception {
		Tarefa tarefa = Tarefa.builder()
				.titulo("titulo")
				.descricao("descrição")
				.status(StatusDaTarefa.PENDENTE)
				.build();
		Tarefa tarefa2 = Tarefa.builder()
				.titulo("titulo")
				.descricao("descrição")
				.status(StatusDaTarefa.PENDENTE)
				.build();
		
		tarefaRepository.saveAll(List.of(tarefa, tarefa2));
		
		mockMvc.perform(MockMvcRequestBuilders.get(URI+"/status")
				.param("status", StatusDaTarefa.PENDENTE.toString()))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
		List<Tarefa> tarefas = tarefaRepository.findAllByStatus(StatusDaTarefa.PENDENTE);
		
		assertEquals(2, tarefas.size());
	}
	
	@Test
	public void shouldFindByTituloSucessfully() throws JacksonException, Exception {
		Tarefa tarefa = Tarefa.builder()
				.titulo("titulozinho")
				.descricao("descrição")
				.status(StatusDaTarefa.PENDENTE)
				.build();
		Tarefa tarefa2 = Tarefa.builder()
				.titulo("tit ulo")
				.descricao("descrição")
				.status(StatusDaTarefa.PENDENTE)
				.build();
		
		tarefaRepository.saveAll(List.of(tarefa, tarefa2));
		
		mockMvc.perform(MockMvcRequestBuilders.get(URI+"/titulo")
				.param("titulo", "tit"))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
		List<Tarefa> tarefas = tarefaRepository.findAllByStatus(StatusDaTarefa.PENDENTE);
		
		assertEquals(2, tarefas.size());
	}
	
	@Test
	public void shouldFindByTituloAndStatusSucessfully() throws JacksonException, Exception {
		Tarefa tarefa = Tarefa.builder()
				.titulo("titulozinho")
				.descricao("descrição")
				.status(StatusDaTarefa.PENDENTE)
				.build();
		Tarefa tarefa2 = Tarefa.builder()
				.titulo("titulo")
				.descricao("descrição")
				.status(StatusDaTarefa.FINALIZADA)
				.build();
		
		tarefaRepository.saveAll(List.of(tarefa, tarefa2));
		
		mockMvc.perform(MockMvcRequestBuilders.get(URI+"/filter")
				.param("titulo", "tit")
				.param("status", StatusDaTarefa.FINALIZADA.toString()))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
		List<Tarefa> tarefas = tarefaRepository.findAllByTituloContainsAndStatus("titu", StatusDaTarefa.PENDENTE);
		
		assertEquals(1, tarefas.size());
	}
}
