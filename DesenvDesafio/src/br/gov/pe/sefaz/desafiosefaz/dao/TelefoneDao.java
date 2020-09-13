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

public class TelefoneDao {
	
	Connection conn = Conexao.getConnection();
	
	public void salvar(Telefone telefone){
		
		//Monta SQL
		String sql = "INSERT INTO TELEFONE ( " +
					 "		USUARIO_ID, DDD, NUMERO, TIPO " +
					 " ) VALUES (" +
					 "		?,?,?,?" +
					 " )";
		
		try {
		
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, telefone.getUsuario_id() );
			statement.setString(2, telefone.getDdd() );
			statement.setString(3, telefone.getNumero() );
			statement.setString(4, telefone.getTipo() );			
			
			statement.execute() ;
			statement.close();
			
			System.out.println("Telefone cadastrado com sucesso");			
			
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("Erro ao carregar o PreparedStatement");
		} finally {
			
		}
		
	}
	
	public List<Telefone> getTelefonesByUsuario(int usuario_id){
		List<Telefone> lista = new ArrayList<>();
		
		ResultSet resultSet = null;
		
		// Monta SQL
		String sql = " SELECT * FROM telefone WHERE usuario_id = ? ";
		
		//Controi preparedStatement com o SQL
		try {
			PreparedStatement statement = conn.prepareStatement(sql);	
			statement.setInt(1, usuario_id);
			
			resultSet = statement.executeQuery();			
			statement.close();
			
			while( resultSet.next() ) {
				Telefone telefone = new Telefone();			
				telefone.setUsuario_id( resultSet.getInt("usuario_id") );
				telefone.setDdd( resultSet.getString("ddd") );
				telefone.setNumero( resultSet.getString("numero") );
				telefone.setTipo( resultSet.getString("tipo") );
				
				lista.add(telefone);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao carregar o preparedStatement");
		}
		
		return lista;
	}
	
	
	
	public void deleteTelefonesUsuario(int usuario_id){
		// Monta SQL
		String sql = "DELETE FROM telefone WHERE usuario_id = ? ";
		
		//Controi preparedStatement com o SQL
		try {
			PreparedStatement statement = conn.prepareStatement(sql);	
			statement.setInt(1, usuario_id);		
			statement.execute();			
			statement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao carregar o preparedStatement");
		}
	}

}
