<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<html><head><title>LISTA CAPACIDAD ESPACIOS FISICOS</title><%@include file="../parametros.jsp"%>
<script languaje='javaScript'>
<!--
	var nav4=window.Event ? true : false;
	
	function hacerValidaciones_frmReceso(forma){
	}

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}

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
					<INPUT TYPE="button" NAME="cmd2" VALUE="Cerrar" onClick="cancelar()">
				</td>
			</tr>
		</table>
		<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" >
		<caption>LISTA DE GRADOS/ASIGNATURAS CON NUMERO DE ESTUDIANTES MAYOR A LA CAPACIDAD DEL ESPACIO FISICO</caption>
			<tr>
				<th class="EncabezadoColumna">Grado</th>
				<th class="EncabezadoColumna">Asignatura</th>
				<th class="EncabezadoColumna">Espacio Fisico</th>
				<th class="EncabezadoColumna">Capacidad Espacio Fisico</th>
				<th class="EncabezadoColumna">Grupo</th>
				<th class="EncabezadoColumna">Nº de estudiantes del grupo</th>
			</tr>
			<c:forEach begin="0" items="${sessionScope.capacidadEspFis}" var="fila" varStatus="st">
				<tr>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[0]}"/></td>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[1]}"/></td>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[2]}"/></td>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[3]}"/></td>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[4]}"/></td>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[5]}"/></td>
				</tr>
			</c:forEach>
		</table>
	</FORM>
</body>
</html>

<script>
</script>