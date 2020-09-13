<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<style>
	/* CSS exemplo do site w3school - Url: https://www.w3schools.com/howto/howto_js_topnav.asp */

	/* Add a black background color to the top navigation */
	.topnav {
	  background-color: #333;
	  overflow: hidden;
	}
	
	/* Style the links inside the navigation bar */
	.topnav a {
	  float: left;
	  color: #f2f2f2;
	  text-align: center;
	  padding: 14px 16px;
	  text-decoration: none;
	  font-size: 17px;
	}
	
	/* Change the color of links on hover */
	.topnav a:hover {
	  background-color: #ddd;
	  color: black;
	}
	
	/* Add a color to the active/current link */
	.topnav a.active {
	  background-color: #4CAF50;
	  color: white;
	}
</style>

<div class="topnav">
	<a class="active" href="principal.jsp">Home</a>
    <a href="usuariocontroller.do?acao=lis">Lista de usuários</a>
    <a href="autenticadorcontroller.do">Sair</a>
</div>
    
    