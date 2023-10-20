<jsp:useBean id="horario" class="siges.rotacion.beans.Horario" scope="session"/><jsp:setProperty name="horario" property="*"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr><td width="100%" valign='top'>
		<div style="width:100%;height:200px;overflow:auto;vertical-align:top;background-color:#ffffff;"><c:import url="/rotacion/ControllerEditar.do?tipo=110"/></div>
	</td></tr>
</table>

<%@include file="../parametros.jsp"%><c:set var="tip" value="111" scope="page"/>
<%@include file="../mensaje.jsp"%>
<script>
			function guardar2(forma){
				if(confirm('¿Realmente desea eliminar la parte del horario seleccionada?'))
					forma.submit();
			}
			function cancelar(){
				location.href='<c:url value="/bienvenida.jsp"/>';
			}
</script>
<font size="1">
	<form method="post" name="frmHorGru" onSubmit="return validarForma(frmHorGru)" action='<c:url value="/rotacion/NuevoGuardar.jsp"/>'>
				<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'>
				<input type="hidden" name="cmd" value="Cancelar">
				<input type="hidden" name="send" value="">
			<TABLE style="display:" id='th1' name='th1' width="100%" align='center' cellpadding="0" cellSpacing="0" border='0'><tr><td>
			  <c:if test="${sessionScope.isLockedRotacion==1}">
					  <span class="style2">Nota: Información de solo lectura. Proceso de rotación pendiente por aprobar o rechazar</span>
			  </c:if>
			  <c:if test="${sessionScope.isLockedRotacion!=1}">
					<input name="cmd1" class='boton' type="button" value="Borrar" onClick="guardar2(frmHorGru)">
			  </c:if>
			</td></tr></table>
								<c:if test="${requestScope.hora!=null}">
								<TABLE style="display:" id='th2' name='th2'  width="100%" align='center' cellpadding="0" cellSpacing="0" border='1'>
									<caption>HORARIO</caption>
									<tr><th>Clase /Dia</th><th>Lunes</th><th>Martes</th><th>Miercoles</th><th>Jueves</th><th>Viernes</th>
									</tr><c:set var="num" scope="page" value="-1"/>
									<c:forEach begin="1" end="${requestScope.hora[0]}" step="1" var="hora" varStatus="status">
									<tr><th><c:out value="${hora}"/></th>
									<c:forEach begin="1" end="5" step="1" var="dia" varStatus="status2"><td align="center" valign="middle">
									<c:set var="a_" value=""/><c:set var="d_" value=""/><c:set var="a" value=""/><c:set var="d" value=""/><c:set var="e_" value=""/><c:set var="e" value=""/>
										<c:forEach begin="0" items="${requestScope.hora2}" var="fila" varStatus="st">
											<c:choose><c:when test="${dia==1 && fila[1]==hora}"><c:set var="a_" value="${fila[2]}"/><c:set var="d_" value="${fila[3]}"/><c:set var="e_" value="${fila[4]}"/></c:when>
												<c:when test="${dia==2 && fila[1]==hora}"><c:set var="a_" value="${fila[5]}"/><c:set var="d_" value="${fila[6]}"/><c:set var="e_" value="${fila[7]}"/></c:when>
												<c:when test="${dia==3 && fila[1]==hora}"><c:set var="a_" value="${fila[8]}"/><c:set var="d_" value="${fila[9]}"/><c:set var="e_" value="${fila[10]}"/></c:when>
												<c:when test="${dia==4 && fila[1]==hora}"><c:set var="a_" value="${fila[11]}"/><c:set var="d_" value="${fila[12]}"/><c:set var="e_" value="${fila[13]}"/></c:when>
												<c:when test="${dia==5 && fila[1]==hora}"><c:set var="a_" value="${fila[14]}"/><c:set var="d_" value="${fila[15]}"/><c:set var="e_" value="${fila[16]}"/></c:when>
												<c:when test="${dia==6 && fila[1]==hora}"><c:set var="a_" value="${fila[17]}"/><c:set var="d_" value="${fila[18]}"/><c:set var="e_" value="${fila[19]}"/></c:when>
												<c:when test="${dia==7 && fila[1]==hora}"><c:set var="a_" value="${fila[20]}"/><c:set var="d_" value="${fila[21]}"/><c:set var="e_" value="${fila[22]}"/></c:when></c:choose>
										</c:forEach>
											<c:if test="${sessionScope.horario.grado<=0}"><c:choose><c:when test="${a_==1}"><c:set var="a" value="Corporal"/></c:when><c:when test="${a_==2}"><c:set var="a" value="Comunicativa"/></c:when><c:when test="${a_==3}"><c:set var="a" value="Cognitiva"/></c:when><c:when test="${a_==4}"><c:set var="a" value="Ética"/></c:when><c:when test="${a_==5}"><c:set var="a" value="Estética"/></c:when></c:choose></c:if>
											<c:if test="${sessionScope.horario.grado>0}">
											<c:forEach begin="0" items="${requestScope.filtroAsignaturaF}" var="fila2">
											<c:if test="${a_==fila2[0]}"><c:set var="a" value="${fila2[1]}"/></c:if>
											</c:forEach>
											</c:if>
											<c:forEach begin="0" items="${requestScope.filtroDocenteF}" var="fila2">
											<c:if test="${d_==fila2[0]}"><c:set var="d" value="${fila2[1]}"/></c:if>
											</c:forEach>
											<c:forEach begin="0" items="${requestScope.listaEsp}" var="fila2">
											<c:if test="${e_==fila2[0]}"><c:set var="e" value="${fila2[1]}"/></c:if>
											</c:forEach>											
										<script>document.write('-<font color="red"><c:out value="${a}"/></font>- <br> <font color="blue"><c:out value="${d}"/></font><br> <font color="green"><c:out value="${e}"/></font>');</script>
									</td></c:forEach></c:forEach></tr>
								</TABLE></c:if>
		<center>
		CONVENCIONES:<br>
		<font color="red">- Asignatura -</font>&nbsp;&nbsp;&nbsp;
		<font color="blue">- Docente -</font>&nbsp;&nbsp;&nbsp;
		<font color="green">- Espacio Físico -</font></center>
	</font>
</form>