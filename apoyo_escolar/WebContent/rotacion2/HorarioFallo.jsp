<jsp:useBean id="horario" class="siges.rotacion.beans.Horario" scope="session"/><jsp:setProperty name="horario" property="*"/>
<%@include file="../parametros.jsp"%><c:set var="tip" value="43" scope="page"/>
<%@include file="../mensaje.jsp"%>

<c:if test="${empty requestScope.horarioFallo}">
<TABLE width="98%" align='center' cellpadding="0" cellSpacing="0" border='1'>
<caption>NO HAY ASIGNATURAS</caption>
<TR><TD>&nbsp;</TD></TR>
</TABLE>
</c:if>
<c:forEach begin="0" items="${requestScope.horarioFallo}" var="lista" varStatus="st">
	<center>CONVENCIONES:<br><font color="red">- Asignatura -</font>&nbsp;&nbsp;<font color="blue">- Docente -</font>&nbsp; <font color="green">- Espacio Físico -</font></center>
		<TABLE width="98%" align='center' cellpadding="0" cellSpacing="0" border='1'>
									<caption>Intento: <c:out value="${lista.nombreIteracion}"/>. Asignatura que falló: <c:out value="${lista.nombreAsignatura}"/></caption>
									<tr>
										<th width="5%">Clase</th><th width="16%">Lunes</th><th width="18%">Martes</th><th width="18%">Miercoles</th><th width="18%">Jueves</th><th width="18%">Viernes</th>
									</tr>
									<c:forEach begin="0" items="${lista.listaClase}" var="clase" varStatus="st2">
									<tr>
										<td align="center"><c:out value="${clase.nombreClase}"/></td>
										<c:forEach begin="0" items="${clase.listaDia}" var="dia" varStatus="st3">
										<td align="center">&nbsp;
												<font color="red"><c:out value="${dia.nombreAsignatura}"/></font><br>
												<font color="blue"><c:out value="${dia.nombreDocente}"/></font><br>
												<font color="green"><c:out value="${dia.nombreEspacio}"/></font><br>
										</td>
										</c:forEach>
									</tr>
								</c:forEach>
		</TABLE>
		<br>
</c:forEach>
