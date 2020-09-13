package br.gov.pe.sefaz.desafiosefaz.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.gov.pe.sefaz.desafiosefaz.dao.UsuarioDao;
import br.gov.pe.sefaz.desafiosefaz.entidades.Usuario;

@WebServlet("/autenticadorcontroller.do")
public class AutenticadorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AutenticadorController() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Chamado no menu, opção "sair"
		
		HttpSession sessao = request.getSession(false);
		if(sessao != null){
			sessao.invalidate();			
		}
		response.sendRedirect("index.html");
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email =  request.getParameter("email");
		String senha =  request.getParameter("senha");
		System.out.println("Tentativa de login. Email: " + email + " | Senha: " + senha);
		
		//Cria o usuário
		Usuario usuario = new Usuario();
		usuario.setEmail(email);
		usuario.setSenha(senha);
		
		UsuarioDao usuarioDao = new UsuarioDao();
		if(usuarioDao!=null ){
			System.out.println("UsuarioDao criado com sucesso");
		}
		Usuario usuarioRetorno = usuarioDao.autenticar(usuario );
		
		if(usuarioRetorno != null && usuarioRetorno.getId() > 0){
			//usuário existe
			System.out.println("Autenticado com sucesso");
			//Cria uma sessão
			HttpSession sessao = request.getSession();
			
			//Adiciona o objeto do usuário na sessão
			sessao.setAttribute("usuarioLogado", usuarioRetorno);
			
			//Encaminha para pagina principal
			request.getRequestDispatcher("principal.jsp").forward(request, response);	
			
		} 
		else 
		{	
			System.out.println("Falha na autenticação");
			response.sendRedirect("index.html");
			
		}

		
	}

}
