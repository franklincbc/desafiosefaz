package br.gov.pe.sefaz.desafiosefaz.entidades;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

	private int id;
	private String nome;
	private String email;
	private String senha;
	private List<Telefone> telefones;
	
	public Usuario() {
		super();
		
		this.id = 0;
		this.nome = "";
		this.email = "";
		this.senha = "";
		this.telefones = new ArrayList<>();
		
	}
		
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}


	public List<Telefone> getTelefones() {
		return telefones;
	}


	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
	

	
	
	
}
