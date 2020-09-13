package br.gov.pe.sefaz.desafiosefaz.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	/**
	 * Classe responsável para retorna a conexão com o banco de dados
	 * @param sem parâmetros
	 * @return Connection objeto com a conexão do banco de dados
	 */
	public static Connection getConnection(){
		
		Connection con = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/desafiosefaz","postgres","masterkey1_");
			System.out.println("Conexão com o banco de dados... OK");
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("Conexão com o banco de dados... Falha: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
			System.out.println("Driver não encontrado. Detalhe: " + e.getMessage());
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("getConnection Falhou. Motivo: " + e.getMessage());
		}
				
		return con;
		
	}
	
}
