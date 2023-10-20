<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<html><head><title>LISTA ESPACIOS FIJADOS INSUFICIENTES</title>
<%@include file="../parametros.jsp"%>
<script languaje='javaScript'>
<!--
	function cancelar(){
  	parent.close();
	}
//-->
</script>
</head>
<body onLoad=''>
	<FORM ACTION="" METHOD="POST" name="frmReceso">
		<table border="0" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
			<tr>
				<td>
					<INPUT class='boton' TYPE="button" NAME="cmd2" VALUE="Cerrar" onClick="cancelar()">
				</td>
			</tr>
		</table>
		<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" >
		<caption>LISTA DE ESPACIOS FISICOS FIJADOS QUE SON INSUFICIENTES PARA LA CANTIDAD DE HORAS NECESARIAS</caption>
			<tr>
				<th  class="EncabezadoColumna">Espacio físico</th>
				<th  class="EncabezadoColumna">Asignatura</th>
				<th  class="EncabezadoColumna">Clases que faltan</th>
			</tr>
			<c:forEach begin="0" items="${sessionScope.validacionEspacios}" var="fila" varStatus="st">
				<tr>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[0]}"/></td>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[1]}"/></td>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[2]}"/></td>
				</tr>
			</c:forEach>
		</table>
	</FORM>
</body>
</html>