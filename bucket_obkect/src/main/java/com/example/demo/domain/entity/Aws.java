package com.example.demo.domain.entity;

public class Aws {
	
	
	public String idDaChave;
	public String chaveDeAcesso;
	
	public Aws() {}
	
	public Aws(String idDaChave, String chaveDeAcesso) {
		super();
		this.idDaChave = idDaChave;
		this.chaveDeAcesso = chaveDeAcesso;
	}
	public String getIdDaChave() {
		return idDaChave;
	}
	public void setIdDaChave(String idDaChave) {
		this.idDaChave = idDaChave;
	}
	public String getChaveDeAcesso() {
		return chaveDeAcesso;
	}
	public void setChaveDeAcesso(String chaveDeAcesso) {
		this.chaveDeAcesso = chaveDeAcesso;
	}
	
	

}
