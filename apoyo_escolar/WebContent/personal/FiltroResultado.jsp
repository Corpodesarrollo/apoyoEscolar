<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<jsp:useBean id="filtroPersonal" class="siges.personal.beans.FiltroPersonal" scope="session"/>
<%@include file="../parametros.jsp"%>
		<script language="JavaScript">
			<!--
			function hacerValidaciones_frmResultado(forma){
			}
			
			function buscar(){
				document.frmResultado.cmd.value='';
				document.frmResultado.ext2.value='';
				document.frmResultado.action='<c:url value="/personal/ControllerFiltroEdit.do"/>';
				document.frmResultado.submit();
			}
			function cancelarFiltro(){
				location.href='<c:url value="/bienvenida.jsp"/>';
			}
			function editarFiltro(){		
				if(document.frmResultado.id){
					if(validarForma(document.frmResultado)){
						document.frmResultado.cmd.value='Editar';
						document.frmResultado.ext2.value='/personal/FiltroResultado.jsp'; 
						document.frmResultado.action='<c:url value="/personal/ControllerFiltroSave.do"/>';
						document.frmResultado.submit();						
					}
				}
			}
			function eliminarFiltro(){
				if(confirm('¿Confirma la eliminación de los accesos del usuario?')){
					if(document.frmResultado.id){
						if(validarForma(document.frmResultado)){
							document.frmResultado.cmd.value='Eliminar';
							//document.frmResultado.ext2.value='/personal/FiltroResultado.jsp';
							document.frmResultado.action='<c:url value="/personal/ControllerFiltroSave.do"/>';
							document.frmResultado.submit();						
						}
					}
				}
			}
			
			function editarPersona(id,id2){
				document.frmResultado.id.value=id;
				document.frmResultado.id2.value=id2;
				editarFiltro();
			}
			function eliminarPersona(id,id2){
				document.frmResultado.id.value=id;
				document.frmResultado.id2.value=id2;
				//alert('PERSONA '+document.frmResultado.vigenciaparametro.value);
				eliminarFiltro();
			}
			//-->
		</script>
  <%@include file="../mensaje.jsp"%>
	<font size="1">
	<FORM ACTION='<c:url value="/personal/ControllerFiltroEdit.do"/>' METHOD="POST" name='frmResultado' onSubmit="return validarForma(frmResultado)">
			<input type="hidden" name="cmd" value="">
			<input type="hidden" name="id2" value="">
			<INPUT TYPE="hidden" NAME="ext2"  VALUE=''>
			<INPUT TYPE="hidden" NAME="height" VALUE='180'>
			<INPUT type='hidden' name='id'>
				<table border="0" align="center" width="98%" cellpadding="1" cellspacing="0">
				<caption>Resultados de la busqueda</caption>
					<tr>
						<td colspan="6">
						<input class='boton' id="cmd1" name="cmd1" type="button" value="Buscar" onClick="buscar()">
						</td>
					</tr>
				</table>
				<table border="1" align="center" width="98%" cellpadding="0" cellspacing="0" bordercolor="Silver">
					<tr>
						<th class="EncabezadoColumna" width="2%">N°</th>
						<th class="EncabezadoColumna" width="8%">&nbsp;</th>
						<th class="EncabezadoColumna">Identificación</th><th class="EncabezadoColumna">Apellidos</th><th class="EncabezadoColumna">Nombres</th>
					</tr>
					<c:if test="${empty sessionScope.filtroListaPersonal}">
					<tr><tD class="Fila1" align='center' colspan='6'>NO HAY REGISTROS DE ESTA BUSQUEDA</td></tr></c:if>
					<c:if test="${!empty sessionScope.filtroListaPersonal}">
					<c:forEach begin="0" items="${sessionScope.filtroListaPersonal}" var="fila" varStatus="st">
					<tr>
					    <td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${st.count}"/></td>
						<td align="center" class='Fila<c:out value="${st.count%2}"/>'><a href='javaScript:editarPersona(<c:out value="${fila[0]}"/>,<c:out value="${fila[3]}"/>);'><img border='0' src="../etc/img/editar.png" width='15' height='15'></a>
						<c:if test="${sessionScope.NivelPermiso!=1}"><a href='javaScript:eliminarPersona(<c:out value="${fila[0]}"/>,<c:out value="${fila[3]}"/>);'><img border='0' src="../etc/img/eliminar.png" width='15' height='15'></a></c:if>
						</td>
						<td class='Fila<c:out value="${st.count%2}"/>'><center><c:out value="${fila[0]}"/></center></td>
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[2]}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[1]}"/></td>
					</tr></c:forEach></c:if>
				</table>
</form>