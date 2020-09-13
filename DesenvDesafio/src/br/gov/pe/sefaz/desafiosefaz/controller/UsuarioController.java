package br.gov.pe.sefaz.desafiosefaz.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.gov.pe.sefaz.desafiosefaz.dao.UsuarioDao;
import br.gov.pe.sefaz.desafiosefaz.entidades.Telefone;
import br.gov.pe.sefaz.desafiosefaz.entidades.Usuario;


@WebServlet("/usuariocontroller.do")
public class UsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UsuarioController() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Chamando Metodo GET");				
		
		//Pega a ação da requisição
		String acao = request.getParameter("acao");
		
		UsuarioDao usuarioDao = new UsuarioDao();
		
		if(acao != null && acao.equals("exc") ){	
			System.out.println("Exclusão de usuário");	
			
			//Pega o Id do usuário de exclusão
			String id = request.getParameter("id");
			
			Usuario usuario = new Usuario();
			usuario.setId( Integer.parseInt( id ) ); 
			usuarioDao.excluir(usuario);
			
			//Chama a ação de carregar a lista pelo redirect (O cliente fará uma nova requisição)
			response.sendRedirect("usuariocontroller.do?acao=lis");
		}
		
		if(acao != null && acao.equals("alt") ){
			System.out.println("alteração de usuário");
			
			//Pega o ID do usuário de alteração
			String id = request.getParameter("id");
			
			Usuario usuario = usuarioDao.buscarById( Integer.parseInt(id) );
			
			//Incluir o objeto usuário no request
			request.setAttribute("usuario", usuario);
			
			Gson gson = new Gson();
			String listaJsonString = gson.toJson( usuario.getTelefones() ).toString();
			request.setAttribute("telefonesUsuario", listaJsonString );
			
			// Encaminha para tela de cadastro
			RequestDispatcher saida = request.getRequestDispatcher("cadastrousuario.jsp");
			saida.forward(request, response);	
			
		}
			
		if(acao != null && acao.equals("lis") ){
			String nomePesquisa = request.getParameter("nomepesquisa");
			String tipo = request.getParameter("tipo");

			// Carrega a lista de usuário
			List<Usuario> lista = null;	
			if (nomePesquisa == null || nomePesquisa.equals("") ) {
				lista = usuarioDao.buscarTodos();	
			} else {
				lista = usuarioDao.buscarByNome(nomePesquisa);
			}
			
			// Incluir a lista no request
			request.setAttribute("lista", lista );			
			
			Gson gson = new Gson();
			String listaJsonString = gson.toJson(lista).toString();
			request.setAttribute("listaJsonString", listaJsonString );
									
			// Encaminhas para a pagina JSP (listausuarios)
			RequestDispatcher saida = request.getRequestDispatcher("listausuarios.jsp");
			saida.forward(request, response);			
					
		}
		
		if(acao != null && acao.equals("cad") ){

			Usuario usuario = new Usuario();
			
			//Incluir no request o usuario (em branco)
			request.setAttribute("usuario", usuario);		
			
			// encaminha objeto usuario para tela
			if( request.getParameter("origem")!=null && request.getParameter("origem").equals("index.html") ) {
				System.out.println("index.html chamou cadastro usuario");
				RequestDispatcher saida = request.getRequestDispatcher("cadastrousuario.jsp?origem=index");
				saida.forward(request, response);
			} else {				
				RequestDispatcher saida = request.getRequestDispatcher("cadastrousuario.jsp");
				saida.forward(request, response);
			}
			
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Chamando Metodo POST");
		
		//Recebe dados da tela
		String id = request.getParameter("txtid");
		String nome = request.getParameter("txtnome");
		String email = request.getParameter("txtemail");
		String senha = request.getParameter("txtsenha");
		String origem = request.getParameter("origem");
		
		//Cria e seta os dados do usuario
		Usuario usuario = new Usuario();
		
		if (id != null && id != "" && !(id.equals("0")) ){
			usuario.setId( Integer.parseInt(id) );
		}
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setSenha(senha);
		
		//telefones
		String telefones = request.getParameter("telefones");
		//JsonObject json = new JsonObject().getAsJsonObject(telefones);
		JsonArray jsonArr = new JsonParser().parse(telefones).getAsJsonArray();
		
		List<Telefone> lista =  new ArrayList<Telefone>();
		for (int i = 0; i < jsonArr.size(); i++) {
			JsonObject jsonObj = jsonArr.get(i).getAsJsonObject();
			
			Telefone telefone = new Telefone();
			
			telefone.setTipo( jsonObj.get("tipo").getAsString() );
			telefone.setDdd( jsonObj.get("ddd").getAsString() );
			telefone.setNumero( jsonObj.get("numero").getAsString() );
			lista.add(telefone);
			
			
		}
		usuario.setTelefones(lista);
		
		//PErsiste no banco
		UsuarioDao usuarioDAO = new UsuarioDao();
		usuarioDAO.salvar(usuario);
		
		//Saída ao Browser
		//PrintWriter saida = response.getWriter();
		//saida.println("Salvo com sucesso!");
		
		if (origem!=null && origem.equals("index")){
			response.sendRedirect("index.html");
		}else {
			//Redirecionará para a lista de usuário
			response.sendRedirect("usuariocontroller.do?acao=lis");
		}
	}

}
