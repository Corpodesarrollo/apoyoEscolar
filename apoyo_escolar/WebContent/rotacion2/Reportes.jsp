<%@include file="../parametros.jsp"%><c:set var="tip" value="13" scope="page"/>
<%@include file="../mensaje.jsp"%>
	<script>
		var espera=0;

		function solicitar(){
			document.listado.action='<c:url value="/rotacion2/ControllerEditar.do?tipo=0"/>';
			document.listado.submit();
		}
		
		function eliminar(id){
			if(confirm('¿Realmente desea eliminar la solicitud?')){
				var id2 = id.split('|');
				var cod=id2[5];
				document.listado.tipo.value='15';
				document.listado.dir.value=cod;
				document.listado.submit();
			}	
		}
		
		function cancelarlistado(){
			location.href='<c:url value="/bienvenida.jsp"/>';
		}
		
		<c:forEach begin="0" items="${requestScope.lista}" var="fila" varStatus="st">function r_<c:out value="${st.count}"/>(){document.listado.dir.value='<c:out value="${fila[0]}"/>';document.listado.submit();}</c:forEach>
		<c:forEach begin="0" items="${requestScope.lista}" var="fila" varStatus="st">function msg_<c:out value="${st.count}"/>(){alert("<c:out value="${fila[9]}"/>");}</c:forEach>
	</script>
<form onSubmit="return validarForma(listado)" name='listado' method='post' action='<c:url value="/rotacion2/NuevoGuardar.jsp"/>'>
	<input type='hidden' name='aut' value='1'>
	<input type='hidden' name='dir'>
	<input type='hidden' name='tipo' VALUE='<c:out value="${tip}"/>'>
<br>
		<table width='98%' border='0' align='center'cellpadding="0" cellspacing="0" bordercolor="#000000">
		<tr><td colspan='4'>
				<input class='boton' type='button' name='cmdb' value='Aceptar' onClick='cancelarlistado()'>
				<input class='boton' type='button' name='cmdb' value='Solicitar' onClick='solicitar()'>
				</td>
		</tr>
		</table><br>
	<table width='98%' border='1' align='center' cellpadding="1" cellspacing="0" bordercolor="silver">
	<caption>Lista de solicitudes</caption>
	<c:if test="${empty requestScope.lista}"><tr><th class="EncabezadoColumna"> No hay solicitudes</th></tr></c:if>
	<c:if test="${!empty requestScope.lista}">
		<tr>
				<th width='1%' class="EncabezadoColumna">&nbsp;</th>
				<th class="EncabezadoColumna">Sede - Jornada - Grado - Grupo</th>
				<th class="EncabezadoColumna">Estado</th>
				<th class="EncabezadoColumna">Fecha de solicitud</th>
				<th class="EncabezadoColumna">Fecha de generación</th>
				<th class="EncabezadoColumna">Responsable</th>
				<th class="EncabezadoColumna">&nbsp;</th>
		</tr>
		<c:forEach begin="0" items="${requestScope.lista}" var="fila"  varStatus="st">
		<tr><c:if test="${fila[7]<=0}"><script>espera=1;</script><%request.setAttribute("mensaje","VERIFIQUE LA SIGUIENTE INFORMACION: \n\n Hay solicitudes que se estan procesando en este momento. \nSi esta esperando la generación de alguna de estas solicitudes \nhaga click en el vinculo 'ACTUALIZAR ESTADO DE SOLICITUDES' \nal final de esta página");%></c:if>
		<td class='Fila<c:out value="${st.count%2}"/>'><c:if test="${fila[7]>1}"><a href='javaScript:eliminar("<c:out value="${fila[0]}"/>");'><img border='0' src="../etc/img/eliminar.png" width='15' height='15'></a></c:if></td>
		<td class='Fila<c:out value="${st.count%2}"/>'><script>document.write("<c:out value="${fila[1]}"/> - <c:out value="${fila[2]}"/> ".substring(0,150)); </script> <c:if test="${fila[4]!=-9}">- <c:out value="${fila[4]}"/></c:if> <c:if test="${fila[5]!=-9}">- <c:out value="${fila[5]}"/></c:if></td>
		<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[8]}"/></td>
		<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[6]}"/></td>
		<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[10]}"/></td>
		<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[11]}"/></td>
		<th class='Fila<c:out value="${st.count%2}"/>'>
		<c:if test="${fila[7]==1}">
			<input class='boton' type='button' onClick='javaScript:document.listado.tipo.value=13;r_<c:out value="${st.count}"/>()' value='Ver Horario' style='width:80px;'><br>
			<!--<input class='boton' type='button' onClick='javaScript:document.listado.tipo.value=22;document.listado.aut.value=1;r_<c:out value="${st.count}"/>()' value='  Aceptar  ' style='width:80px;'><br>-->
			<input class='boton' type='button' onClick='javaScript:document.listado.tipo.value=22;document.listado.aut.value=2;r_<c:out value="${st.count}"/>()' value='  Rechazar ' style='width:80px;'>
		</c:if>
		<c:if test="${fila[7]==2}"><input class='boton' type='button' onClick='javaScript:msg_<c:out value="${st.count}"/>()' value='Ver Motivo'></c:if>
		</th>
		</tr></c:forEach>
	</c:if>
		</table><br>
<br>
</form><span class="style2">
<script>if(espera==1){document.write("<font size='2'><CENTER>Hay solicitudes que se están procesando en este momento.<br> Haga click <a style='COLOR:blue;' href='<c:url value="/rotacion2/ControllerEditar.do?tipo=15"/>'>aquí</a> para actualizar el estado de las solicitudes.<p>");}</script>
</span>
<%@include file="../mensaje.jsp"%>
<c:if test="${requestScope.refrescar==1}">
<form method="post" name="frmR" action="<c:url value="/rotacion2/ControllerEditar.do"/>">
<INPUT TYPE="hidden" NAME="ext2" VALUE='/rotacion2/ControllerFiltro.do'>
<INPUT TYPE="hidden" NAME="height" VALUE="180">
<INPUT TYPE="hidden" NAME="tipo" VALUE="19">
<INPUT TYPE="hidden" NAME="mensaje" VALUE="<c:out value="${requestScope.mensaje}"/>">
</form>
<script>document.frmR.submit();</script>
</c:if>