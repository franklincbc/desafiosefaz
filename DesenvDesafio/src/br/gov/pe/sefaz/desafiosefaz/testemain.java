package br.gov.pe.sefaz.desafiosefaz;

import java.sql.Connection;
import java.util.List;

import br.gov.pe.sefaz.desafiosefaz.dao.UsuarioDao;
import br.gov.pe.sefaz.desafiosefaz.entidades.Usuario;
import br.gov.pe.sefaz.desafiosefaz.jdbc.Conexao;

public class testemain {

	public static void main(String[] args) {
		
		//Testa CRUD

		//novoUsuario("Franklin Cardoso brito","franklincbc@gmail.com","123");
		//novoUsuario("Bianca Araujo","bianca@gmail.com","1234");
		//novoUsuario("Carlos","carlos@gmail.com","ccc");
		//novoUsuario("Wendell Jackson","wendell@gmail.com","www");
		alterarUsuario(2, "Franklin","franklincbc@gmail.com","123");		
		//excluirUsuario(1);
		
		
		//Testa Busca
		//buscarTodos();
		
		
	}
	
	public static void novoUsuario(String nome, String email, String senha){
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setSenha(senha);
		
		UsuarioDao usuarioDao = new UsuarioDao();
		usuarioDao.salvar(usuario);
				
	}
	
	public static void alterarUsuario(int id, String nome, String email, String senha){
		Usuario usuario = new Usuario();
		usuario.setId(id);
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setSenha(senha);
		
		UsuarioDao usuarioDao = new UsuarioDao();
		usuarioDao.salvar(usuario);
				
	}
	
	public static void excluirUsuario(int id){
		Usuario usuario = new Usuario();
		usuario.setId(id);		
		
		UsuarioDao usuarioDao = new UsuarioDao();
		usuarioDao.excluir(usuario);
	}
	
	public static void buscarTodos(){
		
		UsuarioDao usuarioDao = new UsuarioDao();
		List<Usuario> lista = usuarioDao.buscarTodos();
		for (Usuario usu: lista) {
			System.out.println("[ " + usu.getNome() + " ]");
			
		}
		
		
	}
	
	

}
