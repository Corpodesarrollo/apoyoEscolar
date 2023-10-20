<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
		<script language="JavaScript">
			<!--
			function hacerValidaciones_frmUsuFiltroResultado(forma){
					validarSeleccion(forma.idper, "- Debe seleccionar un item");			
			}
			
			function cancelarFiltro(){
				location.href='<c:url value="/bienvenida.jsp"/>';
			}
					
			function nuevo(){
				document.frmUsuFiltroResultado.tipo.value='5';
				document.frmUsuFiltroResultado.ext2.value='';
				document.frmUsuFiltroResultado.cmd.value='NuevoPersonal';
				document.frmUsuFiltroResultado.action='<c:url value="/usuario/ControllerUsuarioNuevoEdit.do"/>';
				document.frmUsuFiltroResultado.submit();
			}
			
			function usuarios(){
				if(document.frmUsuFiltroResultado.idper){
					if(validarForma(document.frmUsuFiltroResultado)){
						document.frmUsuFiltroResultado.tipo.value='2';
						document.frmUsuFiltroResultado.ext2.value='';
						document.frmUsuFiltroResultado.cmd.value='BuscarUsuarios';
						document.frmUsuFiltroResultado.action='<c:url value="/usuario/ControllerUsuarioNuevoEdit.do"/>';
						document.frmUsuFiltroResultado.submit();
					}
				}
			}
			
			function editar(){
				if(document.frmUsuFiltroResultado.idper){
					if(validarForma(document.frmUsuFiltroResultado)){
						document.frmUsuFiltroResultado.tipo.value='7';
						document.frmUsuFiltroResultado.ext2.value='';
						document.frmUsuFiltroResultado.cmd.value='EditarPersonal';
						document.frmUsuFiltroResultado.action='<c:url value="/usuario/ControllerUsuarioNuevoEdit.do"/>';
						document.frmUsuFiltroResultado.submit();
					}
				}
			}
			
			function buscar(){
				document.frmUsuFiltroResultado.tipo.value='0';
				document.frmUsuFiltroResultado.ext2.value='';
				document.frmUsuFiltroResultado.cmd.value='buscar';
				document.frmUsuFiltroResultado.action='<c:url value="/usuario/ControllerUsuarioNuevoEdit.do"/>';
				document.frmUsuFiltroResultado.submit();
			}
			
			//-->
		</script>
  <%@include file="../mensaje.jsp"%>
	<font size="1">
	<FORM ACTION='<c:url value="/usuario/ControllerUsuarioNuevoEdit.do"/>' METHOD="POST" name='frmUsuFiltroResultado' onSubmit="return validarForma(frmUsuFiltroResultado)">
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="ext2"  VALUE=''>
			<INPUT TYPE="hidden" NAME="height" VALUE='180'>
			<INPUT TYPE="hidden" NAME="tipo" VALUE='1'>
				<table border="0" align="center" width="95%">
				<caption>RESULTADO DE LA BUSQUEDA DE PERSONAL</caption>
					<tr>
						<td colspan="6">
						<input id="cmd1" name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
						<input id="cmd1" name="cmd1" type="button" value="Editar Persona" onClick="editar()" class="boton">
						<input id="cmd1" name="cmd1" type="button" value="Ver Accesos" onClick="usuarios()" class="boton">
						<input id="cmd1" name="cmd1" type="button" value="Agregar Persona" onClick="nuevo()" class="boton">
						</td>
					</tr>
				</table>
				
			<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
				<tr height="1">
						<td width="10">&nbsp;</td>
					<td rowspan="2" width="469"><img src="../etc/img/tabs/usuarios_1.gif" alt="Usuarios"  height="26" border="0"></td>
					</tr>
			</table>
			<br>
				<table border="1" align="center" width="80%" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
					<tr>
						<th width="1%">&nbsp;</th>
						<th>Nombre</th><th>N&uacute;mero de Documento</th>
					</tr>
					<c:if test="${empty requestScope.filtroDocumento}">
						<tr><th colspan='6'>LA BUSQUEDA NO ARROJO RESULTADOS</th></tr>
					</c:if>
					<c:if test="${!empty requestScope.filtroDocumento}">
						<c:forEach begin="0" items="${requestScope.filtroDocumento}" var="fila">
							<tr>
								<td width='10'><INPUT type='radio' name='idper' value="<c:out value="${fila[0]}"/>"></td>
								<td><c:out value="${fila[1]}"/></td>
								<td><c:out value="${fila[0]}"/></td>
							</tr>
						</c:forEach>
					</c:if>
				</table>
			</form>
		</font>