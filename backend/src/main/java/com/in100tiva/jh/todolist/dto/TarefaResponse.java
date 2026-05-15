package com.in100tiva.jh.todolist.dto;

public record TarefaResponse(
		Long id,
		String titulo,
		String descricao,
		String status
		) {

}
