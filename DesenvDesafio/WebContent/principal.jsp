<%@page import="br.gov.pe.sefaz.desafiosefaz.entidades.Usuario"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Principal</title>
	</head>
	
	
	<body>	
	
		<c:import url="fragments/menu.jsp"></c:import>		
		<% 
			Usuario usuario = (Usuario) session.getAttribute("usuarioLogado"); 		
		%>
		<div>	
			<a> Usuário logado: <%= usuario.getNome() %> </a> 
		</div>
		</br>			
		
		<h2> Desenvolvedor (pleno): </h2>
		<h3> Franklin Cardoso </h2>
		<h4> Contato: (81) 9 8294.1997 </h2>
		<h5> Recife-PE </h2>
	
	
	</body>

</html>