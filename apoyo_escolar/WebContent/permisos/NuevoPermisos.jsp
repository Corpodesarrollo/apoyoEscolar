<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@page import="siges.util.Recursos"%>
<%pageContext.setAttribute("filtroPerfil",Recursos.recurso[Recursos.PERFIL]);
pageContext.setAttribute("filtroServicioPerfil",Recursos.recurso[Recursos.SERVICIOPERFIL]);%>
<%@include file="../parametros.jsp"%>
		<script language="JavaScript">
			<!--
			function hacerValidaciones_frmNuevoPermisos(forma){
/*				if(forma.id2)
					validarSeleccion(forma.id2, "- Debe seleccionar un item");
				else{
					return false;
				}	*/
			}
			
			function check1(n){
				switch(n){
					<c:forEach begin="0" items="${requestScope.filtroGrupo}" var="fila" varStatus="st">
						case <c:out value="${st.index}"/>:
							if(document.frmNuevoPermisos.id<c:out value="${st.index}"/>[0].checked){
		  	        document.frmNuevoPermisos.id<c:out value="${st.index}"/>[1].checked=false;
							}else{
		  	        document.frmNuevoPermisos.id<c:out value="${st.index}"/>[1].checked=false;
							}
						break;
					</c:forEach>
				}
			}
			
			function check2(n){
				switch(n){
					<c:forEach begin="0" items="${requestScope.filtroGrupo}" var="fila" varStatus="st">
						case <c:out value="${st.index}"/>:
							if(document.frmNuevoPermisos.id<c:out value="${st.index}"/>[1].checked){
		  	        document.frmNuevoPermisos.id<c:out value="${st.index}"/>[0].checked=true;
							}
						break;
					</c:forEach>
				}
			}
			
			function buscar(){
				document.frmNuevoPermisos.cmd.value='';
				document.frmNuevoPermisos.ext2.value='';
				document.frmNuevoPermisos.action='<c:url value="/permisos/ControllerFiltroPermiso.do"/>';
				document.frmNuevoPermisos.submit();
			}
			function cancelarFiltro(){
				location.href='<c:url value="/bienvenida.jsp"/>';
			}
			function editarFiltro(){		
				if(document.frmNuevoPermisos.id2){
//					if(validarForma(document.frmNuevoPermisos)){
						document.frmNuevoPermisos.cmd.value='Guardar';
						document.frmNuevoPermisos.ext2.value='';
						document.frmNuevoPermisos.action='<c:url value="/permisos/ControllerFiltroPermiso.do"/>';
						document.frmNuevoPermisos.submit();						
//					}
				}
			}
			
			function cancelar(){
				if (confirm('¿Desea cancelar la edición de Privilegios?')){
 					location.href='<c:url value="/bienvenida.jsp"/>';
				}		
			}
			
			function validarPerfil(){
				document.frmNuevoPermisos.perf.value=document.frmNuevoPermisos.id.selectedIndex;
				document.frmNuevoPermisos.cmd.value="cambio";
				document.frmNuevoPermisos.submit();				
			}
			//-->
		</script>
  <%@include file="../mensaje.jsp"%>
	<font size="1">
	<FORM ACTION='<c:url value="/permisos/ControllerFiltroPermiso.do"/>' METHOD="POST" name='frmNuevoPermisos' onSubmit="return validarForma(frmNuevoPermisos)">
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="ext2"  VALUE=''>
			<INPUT TYPE="hidden" NAME="height" VALUE='180'>
			<INPUT TYPE="hidden" NAME="perf"  VALUE=''>
			
			<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
				<caption>Privilegios</caption>
					<tr>
						<td colspan="6">
						<input id="cmd1" name="cmd1" type="button" value="Guardar" onClick="editarFiltro()" class="boton">
						</td>
					</tr>					
			</table>
			<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
				<tr height="1">
					<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
					<td rowspan="2" width="469" bgcolor="#FFFFFF"><img src="../etc/img/tabs/permisos_1.gif" alt="Permisos"  height="26" border="0"></td>
				</tr>
			</table>		
			<br>
			<table border="0" align="center" width="95%">
					<tr><td><b>Seleccione un perfil:</b></td><td>
							<select name="id" onchange='validarPerfil()' style='width:300px;'>
								<option value="-1">--seleccione uno--</option>
								<c:forEach begin="0" items="${filtroPerfil}" var="fila">
									<option value="<c:out value="${fila[0]}"/>" <c:if test="${fila[0]==requestScope.id}">selected</c:if>>
									<c:out value="${fila[1]}"/></option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
					</tr>
				</table>
				<table border="1" align="center" width="80%" cellpadding="0" cellspacing="0" bordercolor="silver">				
					<tr>
						<th class="EncabezadoColumna" width="15%">Consulta</th><th class="EncabezadoColumna" width="15%">Administraci&oacute;n</th><th class="EncabezadoColumna" width="60%">Nombre</th>
					</tr>
					<c:if test="${empty requestScope.filtroGrupo}">
						<tr><th class='Fila0' colspan='6'>NO HAY REGISTROS DE ESTA BUSQUEDA</th></tr>
					</c:if>					
					<c:if test="${!empty requestScope.filtroGrupo}">
					<c:forEach begin="0" items="${requestScope.filtroGrupo}" var="fila" varStatus="st">
						<c:if test="${st.last}"><INPUT class='Fila<c:out value="${st.count%2}"/>' TYPE="hidden" NAME="total" VALUE='<c:out value="${st.index+1}"/>'></c:if>
					<tr>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><INPUT class='Fila<c:out value="${st.count%2}"/>' type='checkbox' onclick='javaScript:check1(<c:out value="${st.index}"/>);' name='id<c:out value="${st.index}"/>' value='<c:out value="${fila[0]}"/>'
								<c:if test="${!empty filtroServicioPerfil}"><c:forEach begin="0" items="${filtroServicioPerfil}" var="fila1">
										<c:if test="${fila[0]==fila1[1] && requestScope.id==fila1[0] && fila1[2]==1}">CHECKED</c:if></c:forEach></c:if>></td>
					 <td class='Fila<c:out value="${st.count%2}"/>' align="center"><INPUT class='Fila<c:out value="${st.count%2}"/>' type='checkbox' onclick='javaScript:check2(<c:out value="${st.index}"/>);' name='id<c:out value="${st.index}"/>' value='<c:out value="${fila[0]}"/>'
					 <c:if test="${!empty filtroServicioPerfil}">
									<c:forEach begin="0" items="${filtroServicioPerfil}" var="fila1"><c:if test="${fila[0]==fila1[1] && requestScope.id==fila1[0] && fila1[2]==2}">CHECKED</c:if></c:forEach></c:if>><script>check2(<c:out value="${st.index}"/>);</script></td>
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[1]}"/></td>
					</tr>
					</c:forEach>
					</c:if>
				</table>
</form>
<c:remove var="filtroServicioPerfil"/>
<c:remove var="filtroPerfil"/>