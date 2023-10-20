<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
		<script language="JavaScript">
			<!--
			function hacerValidaciones_frmUsuFiltroResultado(forma){
					validarSeleccion(forma.idusu, "- Debe seleccionar un item");			
			}
			
			function cancelarFiltro(){
				location.href='<c:url value="/bienvenida.jsp"/>';
			}
					
			function nuevo(){
				document.frmUsuFiltroResultado.tipo.value='1';
				document.frmUsuFiltroResultado.ext2.value='';
				document.frmUsuFiltroResultado.cmd.value='NuevoUsuario';
				document.frmUsuFiltroResultado.action='<c:url value="/usuario/ControllerUsuarioNuevoEdit.do"/>';
				document.frmUsuFiltroResultado.submit();
			}
			
			function editar(){
				if(document.frmUsuFiltroResultado.idusu){
					if(validarForma(document.frmUsuFiltroResultado)){
						document.frmUsuFiltroResultado.idaux.value=document.frmUsuFiltroResultado.idusu.value;
						document.frmUsuFiltroResultado.tipo.value='4';
						document.frmUsuFiltroResultado.ext2.value='';
						document.frmUsuFiltroResultado.cmd.value='EditarUsuario';
						document.frmUsuFiltroResultado.action='<c:url value="/usuario/ControllerUsuarioNuevoEdit.do"/>';
						document.frmUsuFiltroResultado.submit();
					}
				}
			}
			
			function eliminar(){
				if(document.frmUsuFiltroResultado.idusu){
					if(validarForma(document.frmUsuFiltroResultado)){
						if (confirm('¿DESEA ELIMINAR ESTE REGISTRO?')){
							document.frmUsuFiltroResultado.tipo.value='3';
							document.frmUsuFiltroResultado.ext2.value='';
							document.frmUsuFiltroResultado.cmd.value='EliminarUsuario';
							document.frmUsuFiltroResultado.action='<c:url value="/usuario/ControllerUsuarioNuevoEdit.do"/>';
							document.frmUsuFiltroResultado.submit();						
						}
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
			<input type="hidden" name="idaux" value="<c:out value='${requestScope.idusu}'/>"/>
				<table border="0" align="center" width="95%">
				<caption>RESULTADO DE LA BUSQUEDA</caption>
					<tr>
						<td colspan="6">
						<input id="cmd1" name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
						<input class='boton' id="cmd1" name="cmd1" type="button" value="Editar Acceso" onClick="editar()" id="cmd1">
						<input class='boton' id="cmd1" name="cmd1" type="button" value="Agregar Acceso" onClick="nuevo()" id="cmd1">
						<input class='boton' id="cmd1" name="cmd1" type="button" value="Eliminar Acceso" onClick="eliminar()" id="cmd1">
						</td>
					</tr>
				</table>
				<input type="hidden" name="idx" value="<c:out value='${requestScope.id}'/>|<c:out value='-1'/>"/>
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
						<c:if test="${!empty requestScope.buscaUsuario2}">
							<th>Ubicaci&oacute;n:&nbsp;<c:out value="${requestScope.buscaUsuario2[0][2]}"/><c:out value="${requestScope.buscaUsuario2[0][3]}"/><c:out value="${requestScope.buscaUsuario2[0][4]}"/><c:out value="${requestScope.buscaUsuario2[0][5]}"/><c:out value="${requestScope.buscaUsuario2[0][6]}"/><c:out value="${requestScope.buscaUsuario2[0][7]}"/></th>
						</c:if>
						<c:if test="${!empty requestScope.buscaUsuario}">
							<th>USUARIO IDENTIFICADO CON C.C.: <c:out value="${requestScope.buscaUsuario13[0][3]}"/></th>
						</c:if>
					</tr>
					<c:if test="${empty requestScope.buscaUsuario2 && empty requestScope.buscaUsuario}">
						<tr><th colspan='6'>LA BUSQUEDA NO ARROJO RESULTADOS</th></tr>
					</c:if>
					<c:if test="${!empty requestScope.buscaUsuario2}"><!--BuscaUsuario2-->
						<c:forEach begin="0" items="${requestScope.buscaUsuario2}" var="fila" varStatus="a">
							<tr>
								<c:forEach begin="0" items="${requestScope.buscaUsuario13}" var="fila13" varStatus="b">
									<c:if test="${a.index==b.index}">
										<td width='10'><INPUT type='radio' name='idusu' value="<c:out value="${fila13[1]}"/>|<c:out value="${fila13[3]}"/>|<c:out value="${fila13[2]}"/>*<c:out value="${fila13[0]}"/>"></td>
										<td><c:out value="${requestScope.buscaUsuario13[b.index][3]}"/>: <c:out value="${requestScope.buscaUsuario13[b.index][5]}"/></td>
									</c:if>
								</c:forEach>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${!empty requestScope.buscaUsuario}"><!--BuscaUsuario o BuscaUsuario13-->
						<c:forEach begin="0" items="${requestScope.buscaUsuario}" var="fila" varStatus="a">
							<tr>
							
								<c:forEach begin="0" items="${requestScope.buscaUsuario13}" var="fila13" varStatus="b">
								
									<c:if test="${fila[0]==fila13[1]}">
									<td width='10'><INPUT type='radio' name='idusu' value="<c:out value="${fila13[1]}"/>|<c:out value="${fila13[3]}"/>|<c:out value="${fila13[2]}"/>*<c:out value="${fila13[0]}"/>"></td>
									<td><c:out value="${fila[2]}"/><c:out value="${fila[3]}"/><c:out value="${fila[4]}"/><c:out value="${fila[5]}"/><c:out value="${fila[6]}"/><c:out value="${fila[7]}"/></td>
									</c:if>
								</c:forEach>
							</tr>
						</c:forEach>
					</c:if>
				</table>
			</form>
		</font>