<%@include file="../parametros.jsp"%>
<head>
<title>ESTADO DE LOS GRUPOS EN LA GENERACION DE ROTACIÓN</title>
<script>
	function cancelar(){
  	parent.close();
	}
</script>
</head>
<style type="text/css">
<!--
.estado-1 {
BACKGROUND: silver;
}
.estado0 {
BACKGROUND: white;
}
.estado1 {
BACKGROUND: #FFFF99;
}
.estado2 {
BACKGROUND: #000000;
}
.estado3 {
BACKGROUND: #00FF00;
}
.estado4 {
BACKGROUND: #FF6F6F;
}
-->
</style>
	<form method="post" name="frm" onSubmit="return validarForma(frm)" action='<c:url value="/rotacion2/NuevoGuardar.jsp"/>'>
		<table border="0" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
			<tr>
				<td>
					<INPUT class='boton' TYPE="button" NAME="cmd2" VALUE="Cerrar" onClick="cancelar()">
				</td>
			</tr>
		</table>
				<TABLE width="100%" align='center' cellpadding="0" cellSpacing="0" border='1'>
				<caption>ESTADO DE GRUPOS</caption>
								<c:if test="${requestScope.listaEstadoGrupos!=null}">
										<tr>
											<th class="EncabezadoColumna">Estado</th>
											<th class="EncabezadoColumna">Grado</th>
											<th class="EncabezadoColumna">Grupo</th>
											<th class="EncabezadoColumna">Responsable</th>
											<th class="EncabezadoColumna">Fecha</th>
										</tr>
										<c:forEach begin="0" items="${requestScope.listaEstadoGrupos}" var="fila" varStatus="st"><tr>
										<th class='estado<c:out value="${fila[2]}"/>'><c:out value="${fila[5]}"/>&nbsp;</th>
										<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[0]}"/>&nbsp;</td>
										<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[1]}"/>&nbsp;</td>
										<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[3]}"/>&nbsp;</td>
										<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[4]}"/>&nbsp;</td>
										</tr></c:forEach>
								</c:if>
								</TABLE>
		<center>
		CONVENCIONES:<br>
		<TABLE width="30%" align='center' cellpadding="0" cellSpacing="0" border='0'>
			<tr><tH class='estado1'>GRUPO LISTO PROPUESTO</tH></tr>
			<tr><tH class='estado4'>GRUPO RECHAZADO</tH></tr>
			<tr><tH class='estado3'>GRUPO OFICIALIZADO</tH></tr>
		</table>	
</form>