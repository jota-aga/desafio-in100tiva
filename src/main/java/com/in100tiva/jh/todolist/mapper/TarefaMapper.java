package com.in100tiva.jh.todolist.mapper;

import com.in100tiva.jh.todolist.dto.TarefaRequest;
import com.in100tiva.jh.todolist.entity.Tarefa;

public class TarefaMapper {
	
	public static Tarefa RequestToEntity(TarefaRequest tarefaRequest) {
		return Tarefa.builder()
						.titulo(tarefaRequest.titulo())
						.descrição(tarefaRequest.descricao())
						.status(tarefaRequest.status())
						.build();
	}
	
	public static Tarefa editarTarefa(TarefaRequest tarefaRequest, Tarefa tarefa) {
		tarefa.setTitulo(tarefaRequest.titulo());
		tarefa.setDescrição(tarefa.getDescrição());
		tarefa.setStatus(tarefa.getStatus());
		
		return tarefa;
	}
}
