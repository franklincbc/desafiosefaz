package br.gov.pe.sefaz.desafiosefaz.entidades;


public class Telefone {

	private int usuario_id;
	private String ddd;
	private String numero;
	private String tipo;

	
	public Telefone() {
		super();
		this.usuario_id = 0;
		this.ddd = "";
		this.numero = "";
		this.tipo = "";
	}
	
	public int getUsuario_id() {
		return usuario_id;
	}
	public void setUsuario_id(int usuario_id) {
		this.usuario_id = usuario_id;
	}
	public String getDdd() {
		return ddd;
	}
	public void setDdd(String ddd) {
		this.ddd = ddd;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	
	
}
