<%@page import="com.google.gson.JsonArray"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% 
	JsonArray list = new JsonArray();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Usuários</title>
		
		<meta name="viewport" content="width=device-width,initial-scale=1">
		
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.11.4/themes/redmond/jquery-ui.min.css">
	    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.15.4/css/ui.jqgrid.min.css">
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.15.4/jquery.jqgrid.min.js"></script>	    
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.13.6/js/i18n/grid.locale-pt-br.min.js"></script>
	    
	    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap-theme.min.css" integrity="sha384-6pzBo3FDv/PJ8r2KRkGHifhEocL+1X2rVCTTkUfGk7/0pbek5mMa1upzvWbrUbOZ" crossorigin="anonymous">
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
	
		<style>
			.divForm{
				margin: 20px;
			}
		</style>
		
		<script type="text/javascript">
			
			<% String listaString = (String) request.getAttribute("listaJsonString"); %>
			var mydata = <%= listaString %>
		
			$(document).ready(					
				function(){
					// DOM	
					
					$(window).on("resize", function () {
					    var $grid = $("#grid"),
					        newWidth = $grid.closest(".ui-jqgrid").parent().width();
					    $grid.jqGrid("setGridWidth", newWidth, true);
					});

								
					$("#grid").jqGrid({
						data: mydata,
			        	datatype: 'local',
			        	colNames:['Código', 'nome', 'email'],
			            colModel: [
			                { name: "id" },
			                { name: "nome" },
			                { name: "email" } 
			            ],
			            pager: "#pager",
				        rowNum: 10,
				        rowList: [10,20,30,50,100],
				        sortname: "nome",
				        sortorder: "desc",
				        viewrecords: true,
				        gridview: true,
				        autoencode: true,
				        caption: "Usuários",
				        autowidth : true,
				        shrinkToFit: true,
				        ondblClickRow: function(rowId) {
				            var rowData = jQuery(this).getRowData(rowId);
				            var id = rowData['id'];
				            var qryStr = "id="+id;
				            location.href = "usuariocontroller.do?acao=alt&" + qryStr;				            
				        }				        
			        });	
		
					jQuery("#grid")
					.navGrid("#pager",{edit: false, add:false, del:false, search:false})
					
					.navButtonAdd("#pager", {
						caption:"Novo",
						buttonicon:"ui-icon-add",
						onClickButton: function(){
							location.href = "usuariocontroller.do?acao=cad";	
						}
					, position: "last"
					})
					
					.navButtonAdd("#pager", {
						caption:"Alterar",
						buttonicon:"ui-icon-add",
						onClickButton: function(rowId){
														
							var grid = $("#grid");
				            var rowKey = grid.jqGrid('getGridParam',"selrow"); //Retorna Chave (PK)				            
				            //var rowIndex = grid.jqGrid('getInd', rowKey);
							var rowData = grid.getRowData(rowKey);
							//console.log(rowData);
				            
							var id = rowData['id'];
							if (id > 0){
					            var qryStr = "id="+id;
					            location.href = "usuariocontroller.do?acao=alt&" + qryStr;	
							} 
							else 
							{ 
								alert('Nenhum registro selecionado') 
							}
							
						}
					, position: "last"
					})
					
					.navButtonAdd("#pager", {
						caption:"Apagar",
						buttonicon:"ui-icon-add",
						onClickButton: function(){
							
							var grid = $("#grid");
				            var rowKey = grid.jqGrid('getGridParam',"selrow"); //Retorna Chave (PK)				            
				            //var rowIndex = grid.jqGrid('getInd', rowKey);
							var rowData = grid.getRowData(rowKey);
							//console.log(rowData);				            
							var id = rowData['id'];
							var nome = rowData['nome'];
				            
				            //Confirma antes de apagar
				            if(window.confirm("Tem certeza que deseja excluir o usuário (" + nome+")?" )){
								//faz a requisição
								location.href = "usuariocontroller.do?acao=exc&id=" + id;
							}

						}
					, position: "last"
					});
			});	
			

		</script>
	

		
	</head>
	
	<body class='default'>		
		
		<c:import url="fragments/menu.jsp"></c:import>
	
		<div class="divForm">
			<h2>Lista de usuários</h2>
			
			<div>			
				<table id="grid"></table>
				<div id="pager"></div>
			</div> 
		</div>
		
	</body>
</html>