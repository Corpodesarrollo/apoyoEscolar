<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="poa.consulta.vo.ParamsVO" scope="page"/>
<html>
<head>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:150px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/poa/consulta/Filtro.do"/>
			</div>
		</td></tr>
	</table>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/poa/consulta/Save.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_CONSULTA}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<c:if test="${requestScope.resultadoConsulta.generado==true}">
			<table border="0" align="center" width="95%" cellpadding="0" cellspacing="0">
			 	<caption>Resultado</caption>
					<tr>
						<th><a target="_blank" href='<c:url value="/${resultadoConsulta.ruta}"><c:param name="tipo" value="${resultadoConsulta.tipo}"/></c:url>'><img src='<c:url value="/etc/img/zip.gif"/>' border="0"></a></th>
					</tr>
					<tr>
						<th>Pulse en el ícono para descargar el reporte</th>
					</tr>
			</table>
		</c:if>
	</form>
</body>
</html>