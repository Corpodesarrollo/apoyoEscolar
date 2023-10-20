<jsp:useBean id="adminVO" class="siges.grupoPeriodo.beans.AdminVO" scope="session"/><jsp:setProperty name="adminVO" property="*"/>
<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%><c:set var="tip" value="3" scope="page"/>
<script languaje='javaScript'>
		<!--
			function cancelar(){
				location.href='<c:url value="/bienvenida.jsp"/>';
			}
			function buscar(forma,tipo){
				forma.tipo.value=tipo;
				forma.submit();
			}
			//-->
	</script>
<%@include file="../mensaje.jsp"%>
	<font size="1">	
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/grupoPeriodo/ControllerAdminEdit.do"/>'>
	<br>
	<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
	<caption>Gestión Administrativa >> Bitácora de acciones de usuario</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
				<input class='boton' name="cmd1" type="button" value="Volver a buscar" onClick="buscar(document.frmNuevo,3)">
				<input class='boton' name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
		  </td>
		</tr>
	</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<input type="hidden" name="send" value="">
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
			<td rowspan="2" bgcolor="#FFFFFF"><img src="../etc/img/tabs/consulta_bitacora_1.gif" border="0"  height="26" alt='--'></td>
		</tr>
  </table><!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
	<TABLE width="100%" cellpadding="1" cellSpacing="0" border=1 borderColor='silver'>
		<c:if test="${empty requestScope.listaBitacora}">
		<tr><th>NO HAY REGISTROS</th></TR>
		</c:if>
		<c:if test="${!empty requestScope.listaBitacora}">
		<tr><th>Fecha</th><th>Acción</th></TR>	
		<c:forEach begin="0" items="${requestScope.listaBitacora}" var="fila">
		<tr><td><c:out value="${fila[0]}"/></td><td><c:out value="${fila[1]}"/></td></TR>	
		</c:forEach>
		</c:if>
	</TABLE>
	</font>
</form>