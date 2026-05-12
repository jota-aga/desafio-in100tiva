package com.in100tiva.jh.todolist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.in100tiva.jh.todolist.dto.TarefaRequest;
import com.in100tiva.jh.todolist.entity.Tarefa;
import com.in100tiva.jh.todolist.enums.StatusDaTarefa;
import com.in100tiva.jh.todolist.service.TarefaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tarefa")
public class TarefaController {
	
	@Autowired
	private TarefaService tarefaService;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public void criarTarefa(@Valid @RequestBody TarefaRequest request) {
		tarefaService.criarTarefa(request);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public void atualizarTarefa(@RequestBody TarefaRequest request, @PathVariable Long id) {
		tarefaService.atualizarTarefa(request, id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public void deletarTarefa(@PathVariable Long id) {
		tarefaService.deletarTarefa(id);
	}
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public void listarTarefas() {
		tarefaService.listarTarefas();
	}
	
	@GetMapping("/status")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Tarefa> procurarTarefaPorStatus(@RequestParam StatusDaTarefa status) {
		return tarefaService.procurarTarefasPorStatus(status);
	}
	
	@GetMapping("/titulo")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Tarefa> procurarTarefaPorTitulo(@RequestParam String titulo) {
		return tarefaService.procurarTarefasPorTitulo(titulo);
	}
}
