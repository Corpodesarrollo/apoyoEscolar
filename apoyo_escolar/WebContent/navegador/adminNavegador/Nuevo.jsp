<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="seccionNavegadorVO" class="navegador.adminNavegador.vo.SeccionNavegadorVO" scope="session"/>
<jsp:useBean id="paramsVO" class="navegador.adminNavegador.vo.ParamsVO" scope="page"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script language="JavaScript" type="text/javascript" src='<c:url value="/navegador/etc/tiny_mce/tiny_mce.js"/>'></script> 
<script language="javascript" type="text/javascript">
tinyMCE.init({
	theme : "advanced",
	mode : "textareas",
	theme_advanced_toolbar_location : "top",
	theme_advanced_toolbar_align : "left",
	language : "es"
});
</script>
</head>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;
	
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
	
	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
		document.frmNuevo.action='<c:url value="/navegador/adminNavegador/Nuevo.do"/>';
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.encoding="multipart/form-data";
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}

function textCounter(field, countfield, maxlimit) {
	if (field.value.length > maxlimit)
		field.value = field.value.substring(0, maxlimit);
	else 
		countfield.value = maxlimit - field.value.length;
}

function hacerValidaciones_frmNuevo(forma){
	validarCampo(forma.nombre, "- Nombre", 1, 60)
	if (trim(forma.nombreArchivo.value).length > 0)
		validarCampo(forma.nombreArchivo, "- Nombre del Archivo", 1, 100)
	if (trim(forma.nombreArchivoFondo.value).length > 0)
		validarCampo(forma.nombreArchivoFondo, "- Nombre del Archivo Fondo", 1, 100)
}
</script>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:150px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/navegador/adminNavegador/Lista.do"/>
			</div>
		</td></tr>
	</table>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/navegador/adminNavegador/Save.jsp"/>'>
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_SECCION}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>

		<table border="0" align="center" bordercolor="#FFFFFF" width="98%">
			<caption>Edición / Ingreso de Sección</caption>
				<tr>
					<td width="45%">
						<c:if test="${sessionScope.NivelPermiso==2}">
							<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
   						<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
    				</c:if>
			  	</td>
			 	</tr>	
		  </table>
		  <table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
		  <tr><td>
			  <table width="100%" border="0" cellspacing="3" cellpadding="0">
					<tr>
						<td><span class="style2">*</span><b>Nombre:</b></td>
						<td>
							<input type="text" name="nombre" maxlength="100" size="50"  value='<c:out value="${sessionScope.seccionNavegadorVO.nombre}"/>'></input>
						</td>
						<td><span class="style2">*</span><b>Orden:</b></td>
						<td>
							<input type="text" name="orden" size="3"  maxlength="3" value='<c:out value="${sessionScope.seccionNavegadorVO.orden}"/>'></input>
						</td>
					</tr>
					<tr>
					<td><span class="style2"></span><b>Descripción:</b></td>
						<td colspan="3">
							<textarea name="descripcion" cols="80" rows="5"><c:out value="${sessionScope.seccionNavegadorVO.descripcion}"/></textarea>
						</td>
					</tr>
				</table>
			</td>
			</tr>
			<tr>				
			<td>
				<fieldset><legend>Medio</legend>
				  <table width="100%" border="0" cellspacing="3" cellpadding="0">
						<tr>
							<td>
								<span class="style2" colspan="2">*</span><b>Nombre Archivo:</b>
							</td>
							<td>
								<input type="text" name="nombreArchivo" maxlength="100" size="74"  value='<c:out value="${sessionScope.seccionNavegadorVO.nombreArchivo}"/>'></input>
							</td>
						</tr>
						<tr>
							<td>
								<span class="style2" colspan="2">*</span><b>Archivo Asociado:</b>
							</td>
							<td>
								<input type='file' name='archivo' style='width:420px;'></td>
							</td>
						</tr>
				</table>
			</fieldset>	  
		</td>
		</tr>
		<tr>	
		<td>
			<fieldset><legend>Fondo</legend>
			  <table width="100%" border="0" cellspacing="3" cellpadding="0">
					<tr>
						<td >
							<span class="style2" colspan="2">*</span><b>Nombre Archivo:</b>
						</td>
						<td>
							<input type="text" name="nombreArchivoFondo" maxlength="100" size="74"  value='<c:out value="${sessionScope.seccionNavegadorVO.nombreArchivoFondo}"/>'></input>
						</td>
					</tr>
					<tr>
						<td>
							<span class="style2" colspan="2">*</span><b>Archivo Asociado:</b>
						</td>
						<td>
							<input type='file' name='archivoFondo' style='width:420px;'></td>
						</td>
					</tr>
			</table>
		</fieldset>	  
		</td>
		</tr>
	</table>
	</form>
</body>
</html>