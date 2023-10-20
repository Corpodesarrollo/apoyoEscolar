<%@include file="parametros.jsp"%>
<table cellspacing="0" cellpadding="6" bgcolor='#f5f5f5' border="0" width="100%">
	<tr>
		<td align="center"><c:choose>
		<c:when test="${sessionScope.login.nivel==2}">Localidad: <c:out value="${sessionScope.login.mun}"/><br>&nbsp;</c:when>
		<c:when test="${sessionScope.login.nivel==4}"><font style='FONT-SIZE:12;FONT-FAMILY:Arial;COLOR: #006699;'><c:out value="${sessionScope.login.inst}"/></font><br>&nbsp;</c:when>
		<c:when test="${sessionScope.login.nivel==6 && sessionScope.login.perfil!=500}"><font style='FONT-SIZE:12;FONT-FAMILY:Arial;COLOR: #006699;'><c:out value="${sessionScope.login.inst}"/><br> <c:out value="${sessionScope.login.sede}"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Jornada <c:out value="${sessionScope.login.jornada}"/></font></c:when>
		<c:when test="${sessionScope.login.nivel==6 && sessionScope.login.perfil==500}"><font style='FONT-SIZE:12;FONT-FAMILY:Arial;COLOR: #006699;'><c:out value="${sessionScope.login.inst}"/><br> <c:out value="${sessionScope.login.sede}"/></font></c:when>
		</c:choose>
		</td>
	</tr>
</table>