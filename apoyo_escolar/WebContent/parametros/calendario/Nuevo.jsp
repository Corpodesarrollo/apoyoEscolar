<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="calendarioVO" class="parametros.calendario.vo.CalendarioVO" scope="session"/>
<jsp:useBean id="paramsVO" class="parametros.calendario.vo.ParamsVO" scope="page"/>
<html>
<head>
<style type="text/css">@import url('<c:url value="/etc/css/calendar-win2k-1.css"/>');</style>
<script type="text/javascript" src='<c:url value="/etc/js/calendar.js"/>'></script>
<script type="text/javascript" src='<c:url value="/etc/js/lang/calendar-es.js"/>'></script>
<script type="text/javascript" src='<c:url value="/etc/js/calendar-setup.js"/>'></script>
<script languaje="javaScript">
	

	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.cmd.value = document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}
 
	function hacerValidaciones_frmNuevo(forma){
		validarFechaUnCampo(forma.fecha, "- Fecha");
		validarCampo(forma.motivo, "- Motivo", 5, 250);
	} 

	function actualizar(){
		document.frmNuevo.cmd.value = document.frmNuevo.ACTUALIZAR.value;
		document.frmNuevo.submit();
	}
		
</script>
</head>

<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:250px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/parametros/calendario/Filtro.do"/>
			</div>
		</td></tr>
	</table>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/parametros/calendario/Save.jsp"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="ACTUALIZAR" value='<c:out value="${paramsVO.CMD_ACTUALIZAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>	
		
		<table border="0" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="white">
		 	<caption>Crear/Editar Festivos Calendario.</caption>
				<tr>
					<td width="120" align="right">Fecha : </td>
					<td>
						<c:if test="${sessionScope.calendarioVO.estado == 1}">
							<input type="text" name="fecha" id="fecha" size="10" maxlength="10" value='<c:out value="${sessionScope.calendarioVO.fecha}"/>' readonly="true">					
							<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Fecha" id="imgfechaFin" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''"/>
							<script  type="text/javascript">
							  Calendar.setup({inputField:"fecha",ifFormat:"%d/%m/%Y",button:"imgfechaFin",align:"Br"});
							</script>
						</c:if>
						<c:if test="${sessionScope.calendarioVO.estado == 2}">
							<c:out value="${sessionScope.calendarioVO.fecha}"/>
						</c:if>
					</td>
				</tr>
				<tr>	
					<td width="120" align="right">Motivo : </td><td><textarea rows="4" cols="80" name="motivo" id="motivo" type="text" ><c:out value="${sessionScope.calendarioVO.motivo}"/></textarea></td>
				</tr>
				<tr>	
					<td colspan="2" align="right">
						<c:if test="${sessionScope.calendarioVO.estado == 1}">
							<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
						</c:if>
						<c:if test="${sessionScope.calendarioVO.estado == 2}">
							<input name="cmd1" type="button" value="Actualizar" onClick="actualizar()" class="boton">
						</c:if>
					</td>
				</tr>
		</table>
	</form>

</body>
</html>