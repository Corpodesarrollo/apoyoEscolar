<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!--	VERSION		FECHA			AUTOR				DESCRIPCION
			1.0		04/12/2019		JORGE CAMACHO		CODIGO ESPAGUETI
-->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="paramsVO" class="poa.reportes.vo.ParamsVO" scope="page"/>

<html>

	<head>

		<script languaje="javaScript">
			var nav4=window.Event ? true : false;

			function acepteNumeros(eve) {
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
	
			function mostrarReporte() {
	  
	  			<c:if test="${sessionScope.resultadoReportes.generado}">
	   				document.getElementById('tableRep').style.display = '';
	  			</c:if> 
	    		<c:if test="${empty sessionScope.resultadoReportes or   sessionScope.resultadoReportes.generado == false}">
	   				document.getElementById('tableRep').style.display = 'none';
	  			</c:if>	   
			}
				
			function ocultarReporte() {
	   			document.getElementById('tableRep').style.display = 'none';
			}
		</script>
		
	</head>
	
	<c:import url="/mensaje.jsp"/>
	
	<body onLoad="mensaje(document.getElementById('msg'));">
	
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%" valign='top'>
					<div style="width:100%;height:350px;overflow:auto;vertical-align:top;background-color:#ffffff;">
						<c:import url="/poa/reportes/Filtro.do"/>
					</div>
				</td>
			</tr>
		</table>
		
		<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/poa/reportes/Save.jsp"/>'>
			
			<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_REPORTES}"/>'>
			<input type="hidden" name="cmd" value=''>
			<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
			<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		
     	  	<table id="tableRep" style="display: none" name="tableRep" border="0" align="center" width="95%" cellpadding="0" cellspacing="0">
			 	<caption>Resultado</caption>
					<tr><th><a target="_blank" onclick="ocultarReporte();" href='<c:url value="/${resultadoReportes.ruta}"><c:param name="tipo" value="${resultadoReportes.tipo}"/><c:param name="cmd" value="DeleteFile"/></c:url>'><img src='<c:url value="/etc/img/zip.gif"/>' border="0"></a></th></tr>
					<tr><th>Pulse en el ícono para descargar el reporte</th></tr>
			</table>
	 
		</form>

	</body>
	
	<script type="text/javascript">
		mostrarReporte();
	</script>

</html>