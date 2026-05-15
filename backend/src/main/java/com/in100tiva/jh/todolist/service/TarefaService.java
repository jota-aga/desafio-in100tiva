package com.in100tiva.jh.todolist.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.in100tiva.jh.todolist.dto.TarefaRequest;
import com.in100tiva.jh.todolist.entity.Tarefa;
import com.in100tiva.jh.todolist.enums.StatusDaTarefa;
import com.in100tiva.jh.todolist.exception.NotFoundException;
import com.in100tiva.jh.todolist.mapper.TarefaMapper;
import com.in100tiva.jh.todolist.repository.TarefaRepository;

import jakarta.persistence.OrderBy;
import jakarta.persistence.criteria.Predicate;
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
		Sort sort = Sort.by(Sort.Direction.ASC, "status");
		return tarefaRepository.findAll(sort);
	}
	
	public Tarefa procurarTarefaPorId(Long id) {
		return tarefaRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Tarefa by id"));
	}
	
	public List<Tarefa> procurarTarefasFiltradas(String titulo, List<String> status, String sortBy){
		Sort sort = Sort.by(
				Sort.Direction.DESC, 
				sortBy != null && !sortBy.isBlank()? sortBy : "status")
				;
		
		Specification<Tarefa> specification = (root, query, builder) ->{
			List<Predicate> predicates = new ArrayList<>();
			
			if(titulo != null && !titulo.isBlank()) {
				predicates.add(builder.like(builder.lower(root.get("titulo")), "%"+titulo.toLowerCase()+"%"));
			}
			
			if(status != null && !status.isEmpty()) {
				try {
					List<StatusDaTarefa> statusEnum = status.stream()
							.map(s -> StatusDaTarefa.valueOf(s))
							.toList();
					predicates.add(root.get("status").in(statusEnum));
				}
				catch (IllegalArgumentException  e) {
					System.out.println("Não foi possível tranformar o enum");
				}
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
		
		return tarefaRepository.findAll(specification, sort);
				
	}
}
