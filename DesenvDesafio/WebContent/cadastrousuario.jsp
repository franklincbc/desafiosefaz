<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="br.gov.pe.sefaz.desafiosefaz.entidades.Usuario"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<% 		
	Usuario usuario = (Usuario) request.getAttribute("usuario");		
	String origem = request.getParameter("origem");
	
	String actionForm = "usuariocontroller.do?origem=lista";
	String menuVisibilidade = "visibility";
	if(origem!=null && origem.equals("index")){
		actionForm = "usuariocontroller.do?origem="+origem;
		menuVisibilidade = "none";
	}
	
	String tipoCadastro = "";		
	if(usuario.getId() > 0) {
		tipoCadastro = "Alteração";
	} else {
		tipoCadastro = "Inclusão";
	}
	String telefones = "[]";	
	
%>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Usuário</title>
		
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
			
		<% 
			telefones = (String) request.getAttribute("telefonesUsuario"); 
			if (telefones==null||telefones.equals("")){
				telefones="[]";
			}
		%>
	
		//vai ser armazenado local
		var telefones = <%=telefones%>			
		
			$(document).ready(					
				function(){
					// DOM	
					
					$(window).on("resize", function () {
					    var $grid = $("#gridUsuarioTelefone"),
					        newWidth = $grid.closest(".ui-jqgrid").parent().width();
					    $grid.jqGrid("setGridWidth", newWidth, true);
					});
										
								
					$("#gridUsuarioTelefone").jqGrid({
						data: telefones,
			        	datatype: 'local',
			        	colNames:['Tipo', 'DDD', 'Numero'],
			            colModel: [
			                { name: "tipo", label: 'Tipo', editable: true },
			                { name: "ddd", label: 'DDD', editable: true  },
			                { name: "numero", label: 'Número', editable: true  } 
			            ],
			            pager: "#pager",
				        rowNum: 5,
				        rowList: [10,20,30,50,100],
				        sortname: "nome",
				        sortorder: "desc",
				        viewrecords: true,
				        gridview: true,
				        autoencode: true,
				        caption: "Telefones",
				        //autowidth : true,
				        //shrinkToFit: true,
				        ondblClickRow: function(rowId) {
				            var rowData = jQuery(this).getRowData(rowId) ;
				            var id = rowData['id'];
				            var qryStr = "id="+id;
				            //location.href = "usuariocontroller.do?acao=alt&" + qryStr;				            
				        }				        
			        });	
					
					
					jQuery("#gridUsuarioTelefone")
					.navGrid("#pager", 
							{ edit: true, add: true, del: true, search: false, refresh: false, view: false, position: "left", cloneToTop: false },
							
							// options for the Edit Dialog
			                {	
			                    editCaption: "Alterar registro",
			                    recreateForm: true,
								//checkOnUpdate : true,
								//checkOnSubmit : true,
								beforeSubmit : function( postdata, form , oper) {
									if( confirm('Você deseja atualizar esse registro?') ) {
										// do something
										return [true,''];
									} else {
										return [false, 'Você não pode salvar!'];
									}
								},
			                    closeAfterEdit: true,
			                    errorTextFormat: function (data) {
			                        return 'Error: ' + data.responseText
			                    }
			                },
			                // options for the Add Dialog
			                {	
			                    closeAfterAdd: true,
			                    recreateForm: true,
			                    errorTextFormat: function (data) {
			                        return 'Error: ' + data.responseText
			                    }
			                },
			                // options for the Delete Dailog
			                {
			                    errorTextFormat: function (data) {
			                        return 'Error: ' + data.responseText
			                    }
			                });
					
			});	
			
		
			function onSubmitForm(){

				var telefoneArray = new Array();
				
				var grid = $("#gridUsuarioTelefone");
				var ids = grid.jqGrid('getDataIDs');				

				var i = 0;
 				while (i <= (ids.length -1) ){
 					var fone = grid.getRowData( ids[i] );
 					console.log(fone);
 					telefoneArray.push(fone);
 					i= i +1;
 				}
 				
 				var jsonTelefones = JSON.stringify(telefoneArray);
				
 				$('<input>').attr({
 				    type: 'hidden',
 				    id: 'telefones',
 				    name: 'telefones',
 				    value: jsonTelefones
 				}).appendTo('form');
				
				
			}
		
			
		</script>
	
		
</head>
<body>
	
	<div style="display: <%= menuVisibilidade%>">
		<c:import url="fragments/menu.jsp"></c:import>	
	</div>
	
	<div class="divForm">
	
	
		<h2>Cadastro de usuário (<%=tipoCadastro%>)</h2>
	
		<form action=<%= actionForm %> method="post" name="formUsuario" id="formUsuario">
		
			<input type="submit" name="submit" class="btn btn-default" value="Gravar"  onclick="return onSubmitForm();">
			
			</br> 
			<div class="form-group">
				<label>Código:</label>
				<input type="text" class="form-control" readonly="readonly" name="txtid" size="10" value="${requestScope.usuario.id}" /> 
			</div>
			
			<div class="form-group">
				<label>Nome:</label>
				<input type="text" class="form-control" name="txtnome" size="30" value="${requestScope.usuario.nome}" />
			</div> 
			
			<div class="form-group">
				<label for="email">Email:</label>
				<input type="email" class="form-control" name="txtemail" size="30" value="${requestScope.usuario.email}" />
			</div> 
			
			<div class="form-group">
				<label for="pwd">Senha:</label>
				<input type="password" class="form-control" name="txtsenha" maxlength="6" size="30" value="${requestScope.usuario.senha}" /> 
			</div> 
					
		</form>
		</br>
		
		<h1>Meus telefones </h1>
		<div>			
			<table id=gridUsuarioTelefone></table>
			<div id="pager"></div>
		</div> 
		
		
	</div>

</body>
</html>