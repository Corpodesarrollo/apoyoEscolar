<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<jsp:useBean id="filtroComision" class="siges.comision.beans.FiltroBeanCom" scope="session"/>
<%@include file="../parametros.jsp"%>
		<script language="JavaScript">
			<!--
			function hacerValidaciones_frm(forma){
				if(forma.id)
					validarSeleccion(forma.id, "- Debe seleccionar un item");
				else
					return false;
			}
			
			function buscar(){
				document.frm.cmd.value='';
				document.frm.ext2.value='';
				document.frm.action='<c:url value="/comision/ControllerFiltroEdit.do"/>';
				document.frm.submit();
			}
			
			function cancelarFiltro(){
				location.href='<c:url value="/bienvenida.jsp"/>';
			}
			
			function editarFiltro(){
				if(document.frm.id){
					if(validarForma(document.frm)){
						document.frm.cmd.value='Editar';
						document.frm.ext2.value='/comision/FiltroResultado.jsp';
						document.frm.action='<c:url value="/comision/ControllerFiltroSave.do"/>';
						document.frm.submit();
					}
				}
			}
			//-->
		</script>
  <%@include file="../mensaje.jsp"%>
	<font size="1">
	<FORM ACTION='<c:url value="/comision/ControllerFiltroEdit.do"/>' METHOD="POST" name='frm' onSubmit="return validarForma(frm)">
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="ext2"  VALUE=''><INPUT TYPE="hidden" NAME="height" VALUE='130'>
				<table border="0" align="center" width="95%">
				<caption>Comisi&oacute;n de Evaluaci&oacute;n, Resultados de busqueda -</caption>
					<tr>
						<td colspan="6">
							<input id="cmd1" name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
							<input id="cmd1" name="cmd1" type="button" value="Editar" onClick="editarFiltro()" class="boton">
						</td>
					</tr>
				</table>
				<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
					<tr>
						<th >Código</th>
						<th >Identificación</th>
						<th >Nombres</th>
						<th >Apellidos</th>
					</tr>
					<c:if test="${empty sessionScope.filtroEstudiantes}">
					<tr><th colspan='6'>NO HAY REGISTROS DE ESTA BUSQUEDA</th></tr></c:if>
					<c:if test="${!empty sessionScope.filtroEstudiantes}">
					<c:forEach begin="0" items="${sessionScope.filtroEstudiantes}" var="fila">
					<tr>
						<td><INPUT type='radio' name='id' value='<c:out value="${fila[0]}"/>'>
						<c:out value="${fila[0]}"/></td>
						<td><c:out value="${fila[1]}"/></td>
						<td><c:out value="${fila[2]}"/></td>
						<td><c:out value="${fila[3]}"/></td>
					</tr>
					</c:forEach>
					</c:if>
				</table>
</form>