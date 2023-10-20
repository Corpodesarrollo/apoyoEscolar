<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<jsp:useBean id="filtro" class="siges.estudiante.beans.FiltroBean" scope="session"/>
<%@include file="../parametros.jsp"%>
		<script language="JavaScript">
			<!--
			function hacerValidaciones_frm(forma){
			}
			
			function nuevo(forma){
				forma.tipo.value='';
				forma.action='<c:url value="/estudiante/ControllerNuevoEdit.do"/>';
				forma.submit();
			}
			
			function buscar(){
				document.frm.cmd.value='';
				document.frm.ext2.value='';
				document.frm.action='<c:url value="/estudiante/ControllerFiltroEdit.do"/>';
				document.frm.submit();
			}
			
			function cancelarFiltro(){
				location.href='<c:url value="/bienvenida.jsp"/>';
			}
			
			function editarFiltro(){
				if(document.frm.id){
					if(validarForma(document.frm)){
						document.frm.cmd.value='Editar';
						document.frm.ext2.value='/estudiante/FiltroResultado.jsp';
						document.frm.action='<c:url value="/estudiante/ControllerFiltroSave.do"/>';
						document.frm.submit();
					}
				}
			}
			function editarPersona(id){
				document.frm.id.value=id;
				<c:if test="${!empty sessionScope.ModoConsulta}">
				document.frm.modcon.value='1';
				</c:if>
				editarFiltro();
			}
			
			function descargar(){
				if(document.frm.id){
					if(validarForma(document.frm)){
						document.frm.cmd.value='Descargar';
					    //document.frm.ext2.value='/estudiante/FiltroResultado.jsp';
						document.frm.action='<c:url value="/estudiante/ControllerFiltroSave.do?modulo=57"/>';
						document.frm.submit();
					}
			
				}
			}
			
			function agregar(){
				/* remote = window.open("","1","width=650,height=450,resizable=yes,scrollbars=yes,toolbar=no,directories=no,menubar=no,status=no");
				document.frm.action='<c:url value="/estudiante/Mosaico.jsp"/>';
				remote.moveTo(250,350);
				document.frm.target='1';
				document.frm.submit();
				if(remote.opener == null) 
					remote.opener = window;
				remote.opener.name = "frm";
				remote.focus(); */
				
				document.frm.action='<c:url value="/estudiante/Mosaico.jsp"/>';
				document.frm.target ="ModuloReportes";
				window.open("","ModuloReportes","width=800,height=500,menubar=yes,scrollbars=1").focus();
				document.frm.submit();
			}
		</script>
  <%@include file="../mensaje.jsp"%>
	<font size="1">
	<FORM ACTION='<c:url value="/estudiante/ControllerFiltroEdit.do?cmd2=buscar"/>' METHOD="POST" name='frm' onSubmit="return validarForma(frm)">
			<input type="hidden" name="cmd" value="">
		    <INPUT TYPE="hidden" NAME="cmd2"  VALUE="buscar">
			<input type="hidden" name="tipo" value="">
			<INPUT TYPE="hidden" NAME="ext2"  VALUE=''>
			<INPUT TYPE="hidden" NAME="height" VALUE='130'>
			<INPUT type='hidden' name='id'>
			<INPUT type='hidden' name='modcons' value='<c:out value="${requestScope.ModoConsulta}"/>'>
			<INPUT type='hidden' name='modcon' >
				<table border="0" align="center" width="98%">
				<caption>Resultados de la busqueda</caption>
					<tr>
						<td>
						<c:if test="${empty sessionScope.ModoConsulta}"><input class='boton' id="cmd1" name="cmd1" type="button" value="Buscar" onClick="buscar()"></c:if>&nbsp;&nbsp;<input class='boton' id="cmd1" name="cmd1" type="button" value="Reporte" onClick="descargar()">
						<input type="button" value="Anuario" onClick="agregar()" class="boton">
						</td>						
					</tr>
				</table>
				<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="Silver">
					<tr>
						<th class="EncabezadoColumna">N°</th>
						<th class="EncabezadoColumna" >&nbsp;</th>
						<th class="EncabezadoColumna">Grupo</th>
						<th class="EncabezadoColumna">Identificación</th>
						<th class="EncabezadoColumna">Nombres</th>
						<th class="EncabezadoColumna">Apellidos</th>
					</tr>
					<c:if test="${empty sessionScope.filtroEstudiantes}"><tr><th colspan='6'>NO HAY REGISTROS DE ESTA BUSQUEDA</th></tr></c:if>
					<c:if test="${!empty sessionScope.filtroEstudiantes}">
					<c:forEach begin="0" items="${sessionScope.filtroEstudiantes}" var="fila" varStatus="st">
					<tr>
						
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${st.count}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>'><a href='javaScript:editarPersona(<c:out value="${fila[0]}"/>);'><img border='0' src="../etc/img/editar.png" width='15' height='15'></a></td>
						<td class='Fila<c:out value="${st.count%2}"/>'>&nbsp;&nbsp;<c:out value="${fila[4]}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[1]}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[2]}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[3]}"/></td>
					</tr>
					</c:forEach>
					</c:if>
				</table>
</form>