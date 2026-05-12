package com.in100tiva.jh.todolist.mapper;

import com.in100tiva.jh.todolist.dto.TarefaDTO;
import com.in100tiva.jh.todolist.entity.Tarefa;

public class TarefaMapper {
	
	public static Tarefa RequestToEntity(TarefaDTO tarefaRequest) {
		return Tarefa.builder()
						.titulo(tarefaRequest.titulo())
						.descricao(tarefaRequest.descricao())
						.status(tarefaRequest.status())
						.build();
	}
	
	public static Tarefa editarTarefa(TarefaDTO tarefaRequest, Tarefa tarefa) {
		tarefa.setTitulo(tarefaRequest.titulo());
		tarefa.setDescricao(tarefaRequest.descricao());
		tarefa.setStatus(tarefaRequest.status());
		
		return tarefa;
	}
}
