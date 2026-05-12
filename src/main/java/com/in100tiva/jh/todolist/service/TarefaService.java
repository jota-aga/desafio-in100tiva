package com.in100tiva.jh.todolist.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in100tiva.jh.todolist.dto.TarefaRequest;
import com.in100tiva.jh.todolist.entity.Tarefa;
import com.in100tiva.jh.todolist.enums.StatusDaTarefa;
import com.in100tiva.jh.todolist.exception.NotFoundException;
import com.in100tiva.jh.todolist.mapper.TarefaMapper;
import com.in100tiva.jh.todolist.repository.TarefaRepository;

import jakarta.transaction.Transactional;

@Service
public class TarefaService {
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	@Transactional
	public void criarTarefa(TarefaRequest tarefaRequest) {
		Tarefa tarefa = TarefaMapper.RequestToEntity(tarefaRequest);
		tarefa.setStatus(StatusDaTarefa.EM_ANDAMENTO);
		
		tarefaRepository.save(tarefa);
	}
	
	@Transactional
	public void atualizarTarefa(TarefaRequest tarefaRequest, Long id) {
		Tarefa tarefa = procurarTarefaPorId(id);
		
		tarefa = TarefaMapper.editarTarefa(tarefaRequest, tarefa);
		
		tarefaRepository.save(tarefa);
	}
	
	@Transactional
	public void deletarTarefa(Long id) {
		tarefaRepository.deleteById(id);
	}
	
	public List<Tarefa> listarTarefas(){
		return tarefaRepository.findAll();
	}
	
	public Tarefa procurarTarefaPorId(Long id) {
		return tarefaRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Tarefa by id"));
	}
}
