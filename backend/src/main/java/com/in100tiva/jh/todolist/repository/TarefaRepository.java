package com.in100tiva.jh.todolist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.in100tiva.jh.todolist.entity.Tarefa;
import com.in100tiva.jh.todolist.enums.StatusDaTarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long>{
	List<Tarefa> findAllByStatus(StatusDaTarefa status);
	List<Tarefa> findAllByTituloContains(String titulo);
}
