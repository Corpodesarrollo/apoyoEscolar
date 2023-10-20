<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="gAreaVO" class="articulacion.gArea.vo.GAreaVO" scope="session"/>
<jsp:useBean id="paramsVO" class="articulacion.gArea.vo.ParamsVO" scope="page"/>
<html>
<head>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;
	
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
	
	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
		document.frmNuevo.action='<c:url value="/articulacion/gArea/Nuevo.do"/>';
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}

	function hacerValidaciones_frmNuevo(forma){
		validarEntero(forma.geArtAreaCodigo, "- Código del Area", 1, 99)
		validarCampo(forma.geArtAreaNombre , "- Nombre del Area", 1, 150)
	}
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:150px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/articulacion/gArea/Lista.do"/>
			</div>
		</td></tr>
	</table>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/articulacion/gArea/Save.jsp"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>

		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Información del Área</caption>
				<tr>
					<td width="45%">
						<c:if test="${sessionScope.NivelPermiso==2}">
							<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
		        			<c:if test="${sessionScope.gAreaVO.formaEstado==1}">
		        				<input name="cmd1" type="button" value="Actualizar" onClick="guardar()" class="boton">
		        			</c:if>
	    					<c:if test="${sessionScope.gAreaVO.formaEstado!=1}">
	    						<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
	    					</c:if>
	    				</c:if>
			  		</td>
			 	</tr>	
		  </table>
		  <table width="100%" border="0" cellspacing="2" cellpadding="2">
				<tr>
					<td>
						<span class="style2">*</span><b>Código</b>
					</td>
					<td>
						<input type="text" name="geArtAreaCodigo" maxlength="2" size="2" onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.gAreaVO.geArtAreaCodigo}"/>'></input>
					</td>
				</tr>
				<tr>
					<td>
						<span class="style2">*</span><b>Nombre del Área</b>
					</td>
					<td>
						<input type="text" name="geArtAreaNombre" maxlength="60" size="60"  value='<c:out value="${sessionScope.gAreaVO.geArtAreaNombre}"/>'></input>
					</td>
				</tr>
				<tr>
					<td>
						<span class="style2">*</span><b>Componente</b>
					</td>
					<td>
						<select name="geArtAreaTipo">
								<option value="1" <c:if test="${1==sessionScope.gAreaVO.geArtAreaTipo}">selected</c:if>>Académico</option>
								<option value="2" <c:if test="${2==sessionScope.gAreaVO.geArtAreaTipo}">selected</c:if>>Técnico</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
</body>
</html>