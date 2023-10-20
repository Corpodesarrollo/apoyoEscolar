<jsp:useBean id="horario" class="siges.rotacion.beans.Horario" scope="session"/><jsp:setProperty name="horario" property="*"/>
<%@include file="../parametros.jsp"%><c:set var="tip" value="24" scope="page"/>
<%@include file="../mensaje.jsp"%>
<script>	
	function remoto2(){
		remote = window.open('<c:url value="/rotacion2/ControllerEditar.do?tipo=43&ext=1&servTarget=3"/>',"3","width=700,height=550,resizable=no,toolbar=no,directories=no,menubar=no,status=no,scrollbars=yes");
		if (remote.opener == null) remote.opener = window;
		remote.opener.name = "centro";
		remote.focus();
	}
	
	function oficializar(forma){
		if(confirm('¿Realmente desea oficializar el horario del grupo?')){
			forma.cmd.value='1';
			forma.submit();
		}
	}

	function rechazar(forma){
		if(confirm('¿Realmente desea rechazar el horario del grupo?')){
			forma.cmd.value='2';
			forma.submit();
		}
	}
</script>
	<font size="1">
	<form method="post" name="frmHorGru" onSubmit="return validarForma(frmHorGru)" action='<c:url value="/rotacion2/NuevoGuardar.jsp"/>'>
				<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'>
				<input type="hidden" name="cmd" value="">
				<input type="hidden" name="send" value="">
				<input type="hidden" name="jer" value='<c:out value="${sessionScope.horario.jerGrupo}"/>'>
							<c:if test="${requestScope.hora!=null}">
							<c:choose>
								<c:when test="${sessionScope.horario.editable==1}"><c:set var="estado" scope="page" value="LISTO PROPUESTO"/></c:when>
								<c:when test="${sessionScope.horario.editable==2}"><c:set var="estado" scope="page" value="NO ES PARTE DE LA SOLICITUD"/></c:when>
								<c:when test="${sessionScope.horario.editable==3}"><c:set var="estado" scope="page" value="OFICIALIZADO"/></c:when>
								<c:when test="${sessionScope.horario.editable==4}"><c:set var="estado" scope="page" value="RECHAZADO"/></c:when>
							</c:choose>
							<c:if test="${sessionScope.horario.editable==1}">
								<TABLE style="display:" id='th1' name='th1' width="98%" align='center' cellpadding="0" cellSpacing="0" border='0'>
									<tr><td>
										<input name="cmd1" class='boton' type="button" value="Oficializar" onClick="oficializar(document.frmHorGru)">
										<input name="cmd1" class='boton' type="button" value="Rechazar" onClick="rechazar(document.frmHorGru)">
										<input name="cmd1" class='boton' type="button" value="Fallos" onClick="javaScript:remoto2()">
										</td>
										<th align='left'><b>GRUPO <c:out value="${estado}"/></b></th>
									</tr>
								</table><br>
							</c:if>
							<c:if test="${sessionScope.horario.editable!=1}">
								<TABLE style="display:" id='th1' name='th1' width="98%" align='center' cellpadding="0" cellSpacing="0" border='0'>
									<tr><th><b>GRUPO <c:out value="${estado}"/></b></th></tr>
								</table><br>
							</c:if>
								<TABLE width="98%" align='center' cellpadding="0" cellSpacing="0" border='1'>
									<caption>HORARIO PROPUESTO</caption>
									<tr><th>Clase</th><th>Lunes</th><th>Martes</th><th>Miercoles</th><th>Jueves</th><th>Viernes</th>
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
		<center>CONVENCIONES:<br>&nbsp;&nbsp; <font color="red">- Asignatura -</font>&nbsp;&nbsp;<font color="blue">- Docente -</font>&nbsp; <font color="green">- Espacio Físico -</font></center>
	</font>
</form>