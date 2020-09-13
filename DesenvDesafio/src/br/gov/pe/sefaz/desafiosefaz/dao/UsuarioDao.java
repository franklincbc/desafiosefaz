package br.gov.pe.sefaz.desafiosefaz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.gov.pe.sefaz.desafiosefaz.entidades.Telefone;
import br.gov.pe.sefaz.desafiosefaz.entidades.Usuario;
import br.gov.pe.sefaz.desafiosefaz.jdbc.Conexao;

public class UsuarioDao {

	Connection con = Conexao.getConnection();
	
	
	/**
	 * Cria um novo usuário no banco de dados
	 * @param usuario Objeto com os dados que serão armazenadas
	 * @return não possui retorno
	 */
	public void inserir(Usuario usuario){
		
		//Monta SQL
		String sql = "INSERT INTO USUARIO ( " +
					 "		NOME, EMAIL, SENHA " +
					 " ) VALUES (" +
					 "		?,?,MD5(?)" +
					 " )";
		
		try {
			if(con==null){
				con = Conexao.getConnection();
			}
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, usuario.getNome() );
			statement.setString(2, usuario.getEmail() );
			statement.setString(3, usuario.getSenha() );
			
			statement.execute() ;
			statement.close();
			
			int id = existeUsuario(usuario);
			
			if(usuario.getTelefones()!= null && usuario.getTelefones().size() > 0 ){
				TelefoneDao telefoneDao = new TelefoneDao();
		        for (Telefone telefone : usuario.getTelefones()) {
		        	telefone.setUsuario_id(id);
					telefoneDao.salvar(telefone);
				}
			}
			
			System.out.println("Usuário cadastrado com sucesso");			
			
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("Erro ao carregar o PreparedStatement");
		} finally {
			
		}
		
				
	}
	
	

	/**
	 * Altera os dados do usuário no banco de dados
	 * @param usuario Objeto com os dados que serão atualizados
	 * @return não possui retorno
	 */
	public void alterar(Usuario usuario){
		// Monta SQL
		String sql = " UPDATE USUARIO " +
					 " SET nome = ?, email = ?, senha = MD5(?) "+
					 " WHERE ID = ? ";
		
		//Controi preparedStatement com o SQL
		try {
			if(con==null){
				con = Conexao.getConnection();
			}
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, usuario.getNome() );
			statement.setString(2, usuario.getEmail() );
			statement.setString(3, usuario.getSenha() );
			statement.setInt(4, usuario.getId() );
			
			statement.execute();
			statement.close();
			
			//Apagar tudo Telefone
			TelefoneDao telefoneDao = new TelefoneDao();
			telefoneDao.deleteTelefonesUsuario(usuario.getId());
			
			if(usuario.getTelefones()!= null && usuario.getTelefones().size() > 0 ){
				
				//salvar tudo de novo
		        for (Telefone telefone : usuario.getTelefones()) {
		        	telefone.setUsuario_id(usuario.getId());
					telefoneDao.salvar(telefone);
				}
			}
			
			System.out.println("usuário atualizado com sucesso");
			
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("Erro ao carregar o preparedStatement");
		}
		
		
	}
	
	/**
	 * Método que checa se o usuário já possui Id, para poder chamar o método que irá inserir() ou alterar() 
	 * @param usuario Objeto que será repassado para a procedure
	 * @return não possui retorno
	 */
	public void salvar(Usuario usuario){
		if ( usuario.getId() > 0 ){
			alterar(usuario);
		}
		else
		{
			inserir(usuario);
		}
	}
	
	/**
	 * Método de exclusão do usuário 
	 * @param usuario Objeto com id do usuário que irá ser excluído
	 * @return não possui retorno
	 */
	public void excluir(Usuario usuario){
		// Monta SQL
		String sql = " DELETE FROM USUARIO WHERE ID = ? ";
		
		//Controi preparedStatement com o SQL
		try {
			if(con==null){
				con = Conexao.getConnection();
			}
			PreparedStatement statement = con.prepareStatement(sql);			
			statement.setInt(1, usuario.getId() );
			
			statement.execute();
			statement.close();
			System.out.println("Usuário excluído com sucesso");
			
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("Erro ao carregar o preparedStatement");
		}
		
		
	}
	
	
	public List<Usuario> buscarTodos(){
		
		List<Usuario> lista = new ArrayList<Usuario>();
		ResultSet resultSet = null;
		
		// Monta SQL
		String sql = " SELECT * FROM USUARIO ORDER BY NOME ASC ";
		
		//Controi preparedStatement com o SQL
		try {
			if(con==null){
				con = Conexao.getConnection();
			}
			PreparedStatement statement = con.prepareStatement(sql);			
			resultSet = statement.executeQuery();			
			statement.close();
			
			while ( resultSet.next() ){
				
				Usuario usu = new Usuario();
				
				usu.setId( resultSet.getInt("id") );
				usu.setNome( resultSet.getString("nome") );
				usu.setEmail( resultSet.getString("email") );
				usu.setSenha( resultSet.getString("senha") );
				
				//Carrega telefones
				TelefoneDao telefoneDao = new TelefoneDao();
				List<Telefone> telefones = telefoneDao.getTelefonesByUsuario(usu.getId());
				usu.setTelefones(telefones);
				
				lista.add(usu);
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao carregar o preparedStatement");
		}
		
		return lista;
		
	}
	
	
	public Usuario buscarById(int id){
		
		Usuario usu = null;
		ResultSet resultSet = null;
		
		// Monta SQL
		String sql = " SELECT * FROM USUARIO WHERE ID = ? ";
		
		//Controi preparedStatement com o SQL
		try {
			if(con==null){
				con = Conexao.getConnection();
			}
			
			PreparedStatement statement = con.prepareStatement(sql);	
			statement.setInt(1, id);
			
			resultSet = statement.executeQuery();			
			statement.close();
			
			if( resultSet.next() ) {
				usu = new Usuario();			
				usu.setId( resultSet.getInt("id") );
				usu.setNome( resultSet.getString("nome") );
				usu.setEmail( resultSet.getString("email") );
				usu.setSenha( resultSet.getString("senha") );
				
				//Carrega telefones
				TelefoneDao telefoneDao = new TelefoneDao();
				List<Telefone> telefones = telefoneDao.getTelefonesByUsuario(usu.getId());
				usu.setTelefones(telefones);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao carregar o preparedStatement");
		}
		
		return usu;
		
	}
	

	public List<Usuario> buscarByNome(String nome){
		
		List<Usuario> lista = new ArrayList<Usuario>();		
		ResultSet resultSet = null;
		
		// Monta SQL
		String sql = " SELECT * FROM USUARIO WHERE upper(nome) like upper(?) ";
		
		//Controi preparedStatement com o SQL
		try {
			if(con==null){
				con = Conexao.getConnection();
			}
			PreparedStatement statement = con.prepareStatement(sql);	
			statement.setString(1, "%"+ nome + "%");
			
			resultSet = statement.executeQuery();			
			statement.close();
			
			while( resultSet.next() ) {
				Usuario usu = new Usuario();			
				usu.setId( resultSet.getInt("id") );
				usu.setNome( resultSet.getString("nome") );
				usu.setEmail( resultSet.getString("email") );
				usu.setSenha( resultSet.getString("senha") );
				
				//Carrega telefones
				TelefoneDao telefoneDao = new TelefoneDao();
				List<Telefone> telefones = telefoneDao.getTelefonesByUsuario(usu.getId());
				usu.setTelefones(telefones);
				
				lista.add(usu);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao carregar o preparedStatement");
		}
		
		return lista;
		
	}
	
	/**
	 * Checa se o usuário existe no banco (identico ao autenticar)
	 * @param usuario Objeto com email e senha a ser consultado no banco
	 * @return Boolean true se existir o usuário, false se não existir
	 */	
	public int existeUsuario(Usuario usuario){
		
		ResultSet resultSet = null;
		
		// Monta SQL
		String sql = "SELECT * FROM USUARIO WHERE email=? and senha=MD5(?)";
		
		//Controi preparedStatement com o SQL
		try {
			if(con==null){
				con = Conexao.getConnection();
			}
			PreparedStatement statement = con.prepareStatement(sql);	
			statement.setString(1, usuario.getEmail() );
			statement.setString(2, usuario.getSenha() );
			resultSet = statement.executeQuery();
			if (resultSet.next()){
				return  resultSet.getInt("id");
			}

			statement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao carregar o preparedStatement");
		}
		
		return 0;
		
	}
	
	/**
	 * Busca por email e senha de usuário
	 * @param usuario Objeto com email e senha a ser consultado no banco
	 * @return Null quando não encontrar no banco ou um ponteiro a um objeto completo quando encontra
	 */	
	public Usuario autenticar(Usuario usuario){
		
		Usuario usu = null;
		ResultSet resultSet = null;
		
		// Monta SQL
		String sql = "SELECT * FROM USUARIO WHERE email=? and senha=MD5(?)";
		
		//Controi preparedStatement com o SQL
		try {
			if(con==null){
				con = Conexao.getConnection();
			}
			PreparedStatement statement = con.prepareStatement(sql);	
			statement.setString(1, usuario.getEmail() );
			statement.setString(2, usuario.getSenha() );
			
			resultSet = statement.executeQuery();			
			statement.close();
			
			if( resultSet.next() ) {				
				usu = new Usuario();			
				usu.setId( resultSet.getInt("id") );
				usu.setNome( resultSet.getString("nome") );
				usu.setEmail( resultSet.getString("email") );
				usu.setSenha( resultSet.getString("senha") );
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao carregar o preparedStatement");
		}
		
		return usu;
		
	}
	
	
}
