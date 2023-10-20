<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<link href='<c:url value="/etc/css/login.css"/>' rel="stylesheet" type="text/css">
<SCRIPT language=javascript src='<c:url value="/etc/js/validar.js"/>'></SCRIPT>
<script languaje='javaScript'>
function ReportePDF(vigencia){
	document.frmLogin.vigencia.value=vigencia.name;
	document.frmLogin.key.value=7;
	document.frmLogin.submit();
}
</script>
</head>
<body>
<FORM ACTION='<c:url value="/Inicio.dos"/>' METHOD="POST" name='frmLogin' target="_blank">
<input type="hidden" name="inst" value='<c:out value="${requestScope.inst_}"/>' >
<input type="hidden" name="sede" value='<c:out value="${requestScope.sede_}"/>' >
<input type="hidden" name="jornada" value='<c:out value="${requestScope.jornada_}"/>' >
<input type="hidden" name="grado" value='<c:out value="${requestScope.grado_}"/>' >
<input type="hidden" name="grupo" value='<c:out value="${requestScope.grupo_}"/>' >
<input type="hidden" name="vigencia" value='' >
<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_CIERRE_VIG}"/>'>
<input type="hidden" name="key" value="5">
<table border="1" align="center" width="50%" cellpadding="0" cellspacing="0">
					<caption>Detalle de Promocion</caption>
						<c:if test="${empty requestScope.listaTablaHilo2}">
							 <tr><th class="Fila1">No existen Solicitudes</th></tr>
						</c:if>
               <c:if test="${!empty requestScope.listaTablaHilo2}">
							<tr>
								<th class="EncabezadoColumna">Vigencia</th>
								<th class="EncabezadoColumna">Reporte</th>
							</tr>
						  <c:forEach begin="0" items="${requestScope.listaTablaHilo2}" var="lista" varStatus="st">
							 <tr>
								<td class='Fila<c:out value="${st.count%2}"/>' align="center">&nbsp;&nbsp;<c:out value="${lista.vigencia}"/>&nbsp;&nbsp;</td>
								 <td class='Fila<c:out value="${st.count%2}"/>' align="center" ><input type="button" class='boton' name="<c:out value="${lista.vigencia}"/>" value="PDF" onClick="ReportePDF(this)"></td>
							 </tr>
						  </c:forEach>
						</c:if>
					 </table>
					 </FORM>
</body>
</html>