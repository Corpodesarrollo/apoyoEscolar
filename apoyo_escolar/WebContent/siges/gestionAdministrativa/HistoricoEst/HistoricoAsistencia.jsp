<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
        <head>
                <title>Historico Estudiante Asistencia </title>
        </head>
        <body>
        <table border="1" align="center" bordercolor="#FFFFFF" width="50%">
        <tr>
        <td>
        <a href="javascript:print()"><input  class='boton' name="impr1" type="button" value="Imprimir" style="margin-top:2px;margin-bottom:2px;" onClick="javascript:window.print(); return false;" /></a>
        </td>
        </tr>
        </table>
        <table border="1" align="center" bordercolor="#FFFFFF" width="50%">
			<caption>Historico Estudiante Asistencia</caption>
			<tr > 
							<td align="left" bgcolor="#009BB2" width="10%">
							Estudiante :
							</td>
							<td align="center" width="40%">
							<c:out value="${requestScope.NombreCompleto}"/>
							</td>
			</tr>
			<tr>
							<td align="left" bgcolor="#009BB2" width="10%">
							Colegio :
							</td>
							<td align="center" width="40%">
							<c:out value="${requestScope.Col}"/>
							</td>
			</tr>
			<table border="1" align="center" bordercolor="#FFFFFF" width="50%">
			<tr>
							<td align="left" bgcolor="#009BB2" width="5%">
							Sede :
							</td>
							<td align="center" width="20%">
							<c:out value="${requestScope.Sede}"/>
							</td>
							<td align="left" bgcolor="#009BB2" width="10%">
							Metodologia :
							</td>
							<td align="center" width="15%">
							<c:out value="${requestScope.Metodologia}"/>
							</td>
			</tr>
			<tr>
							<td align="left" bgcolor="#009BB2" width="5%">
							Grado :
							</td>
							<td align="center" width="20%">
							<c:out value="${requestScope.Grado}"/>
							</td>
							<td align="left" bgcolor="#009BB2" width="10%">
							Grupo :
							</td>
							<td align="center" width="15%">
							<c:out value="${requestScope.Grupo}"/>
							</td>
			</tr>
			</table>
			<table border="1" align="center" bordercolor="#FFFFFF" width="25%">
			<caption>Detalle Asistencia</caption>
			<c:if test="${!empty requestScope.DetalleInasistencia}">
									<c:forEach begin="0" items="${requestScope.DetalleInasistencia}" var="fila">
										<tr>
										<td align="right" width="40%">
										<c:out value="${fila[1]}"/>
										</td>
										<td align="center" width="10%">
										<c:out value="${fila[2]}"/>
										</td>
										</tr>
									</c:forEach>
							</c:if>
			<tr>
							<td align="left" bgcolor="#009BB2" width="40%">
							Total Inasistencia:
							</td>
							<td align="center" width="10%">
							<c:out value="${requestScope.TotalInasistencia}"/>
							</td>
							</tr>
							<tr>
							<td align="left" bgcolor="#009BB2" width="40%">
							Total Retardos:
							</td>
							<td align="center" width="10%">
							<c:out value="${requestScope.TotalRetardo}"/>
							</td>
							</tr>
							<tr>
							<td align="left" bgcolor="#009BB2" width="40%">
							Total Salidas Tarde:
							</td>
							<td align="center" width="10%">
							<c:out value="${requestScope.TotalSalidas}"/>
							</td>
							</tr>
							</table>
		</table>
		</body>
</html>