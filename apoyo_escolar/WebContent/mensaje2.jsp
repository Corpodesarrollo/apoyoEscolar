<!--	VERSION		FECHA			AUTOR				DESCRIPCION
			1.0		09/05/2018		JORGE CAMACHO		SE CREO LA PAGINA WEB
			
-->

<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>

<%@page session="false"%>

<html>
	<head>		
		<%@include file="parametros.jsp"%>
		<script languaje='javaScript'>
			<!--
				function cargar(){
					mensaje(document.getElementById("msg"));
				}				
			//-->
		</script>
	</head>

	<body onLoad="cargar()">
		<%@include file="mensaje.jsp"%>
		<hr>
		<font color="gainsboro">
			<center>
				<h1>
					<c:out value="${requestScope.mensaje}"/>
				</h1>
				<hr>
				<p>
					<a href="<c:url value="/"/>"> >> VOLVER A PAGINA DE INICIO << </a>
				</p>
			</center>
		</font>
	</body>
	
</html>