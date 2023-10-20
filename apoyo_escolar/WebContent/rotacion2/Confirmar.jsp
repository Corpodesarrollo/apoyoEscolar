<jsp:useBean id="horario" class="siges.rotacion.beans.Horario" scope="session"/><jsp:setProperty name="horario" property="*"/>
<%@include file="../parametros.jsp"%><c:set var="tip" value="23" scope="page"/>
<%@include file="../mensaje.jsp"%>
<script>
function accion(a){
	document.frm.cmd.value=a;
	document.frm.submit();
}
</script>
	<font size="2">
	<form method="post" name="frm" onSubmit="return validarForma(frm)" action='<c:url value="/rotacion2/NuevoGuardar.jsp"/>' >
				<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'>
				<input type="hidden" name="cmd" value="1">
				<TABLE width="80%" align='center' cellpadding="0" cellSpacing="0" border='0'>
					<tr><td><font size=2><span class="style2">Nota:</span><br>
					<c:if test="${sessionScope.horario.accion==1}">
						Va a aceptar lo propuesto por el sistema. ¿esta seguro?</font>
						</td></tr>
						<tr><th>&nbsp;</th></tr>
						<tr><th>Aceptar</th></tr>
					</c:if>
					<c:if test="${sessionScope.horario.accion==2}">
						Va a rechazar lo propuesto por el sistema. ¿esta seguro?									
						</td></tr>
						<tr><th>&nbsp;</th></tr>
						<tr><th>Rechazar</th></tr>
					</c:if>
				</TABLE><p>
			<center>
			<input class='boton' type='button' style='width:50px;' name='a' value='SI' onClick='accion(1)'>
			<input class='boton' type='button' style='width:50px;' name='a' value='NO' onClick='accion(2)'>
			</center>
	</font>
</form>
