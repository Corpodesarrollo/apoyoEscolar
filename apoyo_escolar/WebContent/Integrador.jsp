<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<html><head><%@include file="parametros.jsp"%></head><!-- integrador -->
<body bgcolor="#ffffff" onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor='#ffffff'>
		<c:if test="${requestScope.serv3==null}">
			<tr>
				<td width="100%" valign='top'><c:import url="${requestScope.serv}"/></td>
			</tr>
		</c:if>
		<c:if test="${requestScope.serv3!=null}">
		<tr>
			<td width="100%" valign='top'><div style="width:100%;height:<c:out value="${requestScope.height}" default="250"/>px;overflow:auto;vertical-align:top;"><c:import url="${requestScope.serv}"/></div></td>
		</tr>
		<tr><td width="100%" bgcolor='#ffffff'><div style="width:100%;height:2px;overflow:hidden;vertical-align:top;background-color:#ffffff;"><hr></div></td></tr>
		<tr>
			<td width="100%" valign='top'><c:import url="${requestScope.serv3}"/></td>
		</tr>
		</c:if>		
	</table>
</body>
</html>