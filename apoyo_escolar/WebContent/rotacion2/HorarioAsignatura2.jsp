<jsp:useBean id="horario" class="siges.rotacion.beans.Horario" scope="session"/><jsp:setProperty name="horario" property="*"/>
<%@include file="../parametros.jsp"%><c:set var="tip" value="43" scope="page"/>
<%@include file="../mensaje.jsp"%>
<c:forEach begin="0" items="${requestScope.horarioAsignatura}" var="lista" varStatus="st">
	<center>CONVENCIONES:<br><font color="red">- Grupo -</font>&nbsp;&nbsp;<font color="blue">- Docente -</font>&nbsp; <font color="green">- Espacio Físico -</font></center>
		<TABLE width="98%" align='center' cellpadding="0" cellSpacing="0" border='1'>
									<caption><c:out value="${lista.nombreAsignatura}"/></caption>
									<tr>
										<th width="5%">Clase</th><th width="16%">Lunes</th><th width="18%">Martes</th><th width="18%">Miercoles</th><th width="18%">Jueves</th><th width="18%">Viernes</th>
									</tr>
									<c:forEach begin="0" items="${lista.listaClase}" var="clase" varStatus="st2">
									<tr>
										<td align="center"><c:out value="${clase.nombreClase}"/></td>
										<c:forEach begin="0" items="${clase.listaDia}" var="dia" varStatus="st3">
										<td align="center">&nbsp;
											<c:if test="${dia.estado>=1}">  
												<font color="red"><c:out value="${dia.nombreGrupo}"/></font><br>
												<font color="blue"><c:out value="${dia.nombreDocente}"/></font><br>
												<font color="green"><c:out value="${dia.nombreEspacio}"/></font><br>
											</c:if>
											<c:if test="${dia.estado>=2}">  
												<font color="red"><c:out value="${dia.nombreGrupo2}"/></font><br>
												<font color="blue"><c:out value="${dia.nombreDocente2}"/></font><br>
												<font color="green"><c:out value="${dia.nombreEspacio2}"/></font><br>
											</c:if>
											<c:if test="${dia.estado>=3}">
												<font color="red"><c:out value="${dia.nombreGrupo3}"/></font><br>
												<font color="blue"><c:out value="${dia.nombreDocente3}"/></font><br>
												<font color="green"><c:out value="${dia.nombreEspacio3}"/></font><br>
											</c:if>
										</td>
										</c:forEach>
									</tr>
								</c:forEach>
		</TABLE>
		<br>
</c:forEach>
