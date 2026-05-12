package com.in100tiva.jh.todolist.dto;

import com.in100tiva.jh.todolist.enums.StatusDaTarefa;

import jakarta.validation.constraints.NotBlank;

public record TarefaDTO(
		@NotBlank(message="Título é obrigatório")
		String titulo,
		String descricao,
		StatusDaTarefa status) 
{}
