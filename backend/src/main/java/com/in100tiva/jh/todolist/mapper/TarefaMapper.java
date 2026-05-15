package com.in100tiva.jh.todolist.mapper;

import java.util.List;

import com.in100tiva.jh.todolist.dto.TarefaRequest;
import com.in100tiva.jh.todolist.dto.TarefaResponse;
import com.in100tiva.jh.todolist.entity.Tarefa;

public class TarefaMapper {
	
	public static Tarefa RequestToEntity(TarefaRequest tarefaRequest) {
		return Tarefa.builder()
						.titulo(tarefaRequest.titulo())
						.descricao(tarefaRequest.descricao())
						.status(tarefaRequest.status())
						.build();
	}
	
	public static Tarefa editarTarefa(TarefaRequest tarefaRequest, Tarefa tarefa) {
		tarefa.setTitulo(tarefaRequest.titulo());
		tarefa.setDescricao(tarefaRequest.descricao());
		tarefa.setStatus(tarefaRequest.status());
		
		return tarefa;
	}
	
	public static TarefaResponse entityToResponse(Tarefa tarefa) {
		return new TarefaResponse(tarefa.getId(), tarefa.getTitulo(), tarefa.getDescricao(), tarefa.getStatus().getLegenda());
	}
	
	public static List<TarefaResponse> listEntityToListResponse(List<Tarefa> tarefas) {
		return tarefas.stream()
				.map(tarefa -> entityToResponse(tarefa))
				.toList();
	}
}
