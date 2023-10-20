<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@page import="siges.util.Recursos"%>
<jsp:useBean id="nuevoPerfil" class="siges.perfil.beans.Perfil" scope="session" /><jsp:setProperty name="nuevoPerfil" property="*"/>
<%pageContext.setAttribute("filtroPerfil0",Recursos.recurso[Recursos.PERFIL]);%>
<%@include file="../parametros.jsp"%>
		<script language="JavaScript">
			<!--
			function hacerValidaciones_perFiltroResultado(forma){
				if(forma.idper)
					validarSeleccion(forma.idper, "- Debe seleccionar un item");
				else{
					return false;
				}	
			}
			
			function nuevo(){
				document.perFiltroResultado.tipo.value='1';
				document.perFiltroResultado.ext2.value='';
				document.perFiltroResultado.cmd.value='Nuevo';
				document.perFiltroResultado.action='<c:url value="/perfil/ControllerPerfilNuevoEdit.do"/>';
				document.perFiltroResultado.submit();
			}
			
			function editar(){		
				if(document.perFiltroResultado.idper){
					if(validarForma(document.perFiltroResultado)){
						document.perFiltroResultado.tipo.value='4';
						document.perFiltroResultado.ext2.value='';
						document.perFiltroResultado.cmd.value='Editar';
						document.perFiltroResultado.action='<c:url value="/perfil/ControllerPerfilNuevoEdit.do"/>';
						document.perFiltroResultado.submit();
					}
				}
			}
			
			function eliminar(){
				if(document.perFiltroResultado.idper){
					if(validarForma(document.perFiltroResultado)){
						if (confirm('¿DESEA ELIMINAR ESTE REGISTRO?')){
							document.perFiltroResultado.tipo.value='3';
							document.perFiltroResultado.ext2.value='';
							document.perFiltroResultado.cmd.value='Nuevo';
							document.perFiltroResultado.action='<c:url value="/perfil/ControllerPerfilNuevoEdit.do"/>';
							document.perFiltroResultado.submit();						
						}
					}
				 }
			}
			
			function cancelarFiltro(){
				location.href='<c:url value="/bienvenida.jsp"/>';
			}
			//-->
		</script>
  <%@include file="../mensaje.jsp"%>
	<font size="1">
	<FORM ACTION='<c:url value="/perfil/ControllerPerfilNuevoEdit.do"/>' METHOD="POST" name='perFiltroResultado' onSubmit="return validarForma(perFiltroResultado)">
			<table border="0" align="center" width="95%">
				<caption>Perfiles de Usuario</caption>
				<tr>
					<td colspan="6">
					<input id="cmd1" name="cmd1" type="button" value="Editar" onClick="editar()" class="boton">
 					<input id="cmd1" name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
					<input id="cmd1" name="cmd1" type="button" value="Eliminar" onClick="eliminar()" class="boton">
					</td>
				</tr>
			</table>
						
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="ext2"  VALUE=''>
			<INPUT TYPE="hidden" NAME="height" VALUE='180'>
			<INPUT TYPE="hidden" NAME="tipo" VALUE='1'>
			
			<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
				<tr height="1">
						<td width="10">&nbsp;</td>
					<td rowspan="2" width="469"><img src="../etc/img/tabs/perfiles_1.gif" alt="Perfiles" height="26" border="0"></td>
					</tr>
			</table>
			<br>
			<table border="1" align="center" width="80%" cellpadding="0" cellspacing="0" bordercolor="Silver">
				<tr>
					<th class="EncabezadoColumna" width="1%">&nbsp;</th>
					<th class="EncabezadoColumna">C&oacute;digo</th><th class="EncabezadoColumna">Nombre</th><th class="EncabezadoColumna">Descripci&oacute;n</th>
				</tr>
				<c:if test="${empty filtroPerfil0}">
				<tr><th class='Fila0' colspan='6'>NO HAY REGISTROS DE ESTA BUSQUEDA</th></tr></c:if>
				<c:if test="${!empty filtroPerfil0}">
				<c:forEach begin="0" items="${filtroPerfil0}" var="fila" varStatus="st">
				<tr>
					<td class='Fila<c:out value="${st.count%2}"/>' width='10'><INPUT class='Fila<c:out value="${st.count%2}"/>' type='radio' name='idper' value='<c:out value="${fila[0]}"/>'></td>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[0]}"/></td>
					<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[1]}"/></td>
					<td class='Fila<c:out value="${st.count%2}"/>'><textarea class='Fila<c:out value="${st.count%2}"/>'  style="height: 18px;width: 100%" rows="1" title='<c:out value="${fila[2]}"/>' > <c:out value="${fila[2]}"/> </textarea></td>
				</tr>
				</c:forEach>
				</c:if>
			</table>
	</form>
<c:remove var="filtroPerfil0"/>