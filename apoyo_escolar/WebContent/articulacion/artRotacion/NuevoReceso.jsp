<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="artRotRecesoVO" class="articulacion.artRotacion.vo.RecesoVO" scope="session"/>
<jsp:useBean id="paramsVO" class="articulacion.artRotacion.vo.ParamsVO" scope="page"/>
<html>
<head>
<c:import url="/parametros.jsp"/>
<script languaje="javaScript">
<!--
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}

	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
		document.frmNuevo.action='<c:url value="/articulacion/artRotacion/Nuevo.do"/>';
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}
	
	function hacerValidaciones_frmNuevo(forma){
	}
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:200px;overflow:auto;vertical-align:top;background-color:#ffffff;"><c:import url="/articulacion/artRotacion/Filtro.do"/></div>
		</td></tr>
	</table>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/articulacion/artRotacion/SaveReceso.jsp"/>'>
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_RECESO}"/>'>
			<input type="hidden" name="cmd" value=''>
			<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
			<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
			<input type="hidden" name="resEstructura" value='<c:out value="${sessionScope.artRotFiltroRecesoVO.filEstructura}"/>'>
		<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>REGISTRO / EDICIÓN DE RECESO</caption>
				<tr><td width="45%">
					<c:if test="${sessionScope.NivelPermiso==2}">
						<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
		        <input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
	    		</c:if>
			  </td></tr>	
	  </table>
		<table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Nombre:</b></td>
				<td colspan="3"><input type="text" name="resNombre" value='<c:out value="${sessionScope.artRotRecesoVO.resNombre}"/>' size="40" maxlength="60">
				</td>
			</tr>	
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Tipo:</b></td>
				<td>
					<select name="resTipo" style='width:100px;'>
						<option value="-99">--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.lTipoReceso}" var="receso">
							<option value="<c:out value="${receso.codigo}"/>" <c:if test="${receso.codigo==sessionScope.artRotRecesoVO.resTipo}">selected</c:if>><c:out value="${receso.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Clase inicial:</b></td>
				<td>
					<select name="resClase" style='width:100px;'>
						<option value="-99">--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.lClase}" var="clase">
							<option value="<c:out value="${clase.codigo}"/>" <c:if test="${clase.codigo==sessionScope.artRotRecesoVO.resClase}">selected</c:if>><c:out value="${clase.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Duración (Horas - Minutos):</b></td>
				<td>
					<select name="resDuracionHor" style='width:35px;'><option value="-99">--</option>
					<c:forEach begin="0" end="23" step="1" var="hora">
						<option value="<c:out value="${hora}"/>" <c:if test="${hora==sessionScope.artRotRecesoVO.resDuracionHor}">selected</c:if>><c:out value="${hora}"/></option>
					</c:forEach>
					</select>
					-
					<select name="resDuracionMin" style='width:35px;'><option value="-99">--</option>
					<c:forEach begin="0" end="59" step="5" var="min">
						<option value="<c:out value="${min}"/>" <c:if test="${min==sessionScope.artRotRecesoVO.resDuracionMin}">selected</c:if>><c:out value="${min}"/></option>
					</c:forEach>
					</select>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>