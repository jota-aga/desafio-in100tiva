package com.in100tiva.jh.todolist.enums;

public enum StatusDaTarefa {
	PENDENTE("Pendente"),
	EM_ANDAMENTO("Em andamento"),
	FINALIZADA("Finalizada");
	
	private String legenda;

	private StatusDaTarefa(String legenda) {
		this.legenda = legenda;
	}

	public String getLegenda() {
		return legenda;
	}
}	
