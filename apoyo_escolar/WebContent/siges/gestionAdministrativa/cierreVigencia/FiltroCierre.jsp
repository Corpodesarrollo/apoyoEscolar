<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<link href='<c:url value="/etc/css/login.css"/>' rel="stylesheet" type="text/css">
<SCRIPT language=javascript src='<c:url value="/etc/js/validar.js"/>'></SCRIPT>
<script languaje='javaScript'>
function ReportePDF(vigencia){
	document.frmFiltro.vigencia.value=vigencia.name;
	document.frmFiltro.key.value=5;
	document.frmFiltro.submit();
}
function ReportePDFdet(vigencia){
	document.frmFiltro.vigencia.value=vigencia.name;
	document.frmFiltro.key.value=7;
	document.frmFiltro.submit();
}
function ReporteXLS(vigencia){
	document.frmFiltro.vigencia.value=vigencia.name;
	document.frmFiltro.key.value=6;
	document.frmFiltro.submit();
}
</script>
</head>
<body>
<FORM ACTION='<c:url value="/Inicio.dos"/>' METHOD="POST" name='frmFiltro' target="_blank">
<input type="hidden" name="inst" value='<c:out value="${requestScope.inst_}"/>' >
<input type="hidden" name="sede" value='<c:out value="${requestScope.sede_}"/>' >
<input type="hidden" name="jornada" value='<c:out value="${requestScope.jornada_}"/>' >
<input type="hidden" name="grado" value='<c:out value="${requestScope.grado_}"/>' >
<input type="hidden" name="grupo" value='<c:out value="${requestScope.grupo_}"/>' >
<input type="hidden" name="vigencia" value='' >
<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_CIERRE_VIG}"/>'>
<input type="hidden" name="key" value="5">
<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
					<caption>Detalle de Cierre</caption>
						<c:if test="${empty requestScope.listaTablaHilo2}">
							 <tr><th class="Fila1">No existen Solicitudes</th></tr>
						</c:if>
               <c:if test="${!empty requestScope.listaTablaHilo2}">
							<tr>
								<th class="EncabezadoColumna">Vigencia</th>
								<th class="EncabezadoColumna">Estado</th> 
								<th class="EncabezadoColumna" width="150px">Fecha</th>
								<th class="EncabezadoColumna">Reporte</th>
							</tr>
						  <c:forEach begin="0" items="${requestScope.listaTablaHilo2}" var="lista" varStatus="st">
							 <tr>
								<td class='Fila<c:out value="${st.count%2}"/>' align="center">&nbsp;&nbsp;<c:out value="${lista.vigencia}"/>&nbsp;&nbsp;</td>
								<td class='Fila<c:out value="${st.count%2}"/>' align="center">&nbsp;&nbsp;<c:out value="${lista.estado}"/>&nbsp;&nbsp;</td>
								 <td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.fechaSistema}"/></td>
								 <td class='Fila<c:out value="${st.count%2}"/>' align="center" ><input type="button" class='boton' name="<c:out value="${lista.vigencia}"/>" value="Ver Detalle" onClick="ReportePDFdet(this)"><input type="button" class='boton' name="<c:out value="${lista.vigencia}"/>" value="PDF" onClick="ReportePDF(this)"><input type="button" class='boton' name="<c:out value="${lista.vigencia}"/>" value="EXCEL" onClick="ReporteXLS(this)"></td>
							 </tr>
						  </c:forEach>
						</c:if>
					 </table>
					 </FORM>
</body>
</html>