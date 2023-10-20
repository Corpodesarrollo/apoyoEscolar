<%@ page isErrorPage="true" session="false"%>
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
<body onLoad="cargar()"><%@include file="mensaje.jsp"%>
<hr><font color="gainsboro"><CENTER><H1><c:out value="${requestScope.mensaje}"/><hr></H1><P>
<a href="<c:url value="/"/>"> >> VOLVER A PAGINA DE INICIO << </a>

	<%if(exception!=null){%>
	<p>Usted ha encontrado un error que desconociamos:<br/>
	<b><%=exception.getMessage()%></b>
	<textarea rows="10" cols="100"><%java.io.PrintWriter pw= new java.io.PrintWriter(out);exception.printStackTrace(pw);%></textarea>
	</p>
		<hr>
			<%@include file="mensaje.jsp"%>
		<hr>
		<%}else{%>ERROR SQL: <%@include file="mensaje.jsp"%><%}%>

</CENTER></font></body>
</html>