<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="instanciaVO" class="participacion.instancia.vo.InstanciaVO" scope="session"/>
<jsp:useBean id="paramsVO" class="participacion.instancia.vo.ParamsVO" scope="page"/>
<html>
<head>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
		
	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}

	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.CANCELAR.value;
		document.frmNuevo.submit();
	}
	
	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.instNivel, "- Nivel", 1)
		validarCampo(forma.instNombre, "- Nombre", 1, 250)
		validarCampoOpcional(forma.instNorma, "- Norma", 1, 200)
		validarCampoOpcional(forma.instConforma, "- Conformación", 1, 250)
		validarCampoOpcional(forma.instIntegrantes, "- Número de integrantes", 1, 60)
	}
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:200px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/participacion/instancia/FiltroInstancia.jsp"/>
			</div>
		</td></tr>
	</table>
	<form method="post" name="frmNuevo" action='<c:url value="/participacion/instancia/SaveInstancia.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_INSTANCIA}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="CANCELAR" value='<c:out value="${paramsVO.CMD_CANCELAR}"/>'>
	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		  <caption>Ingreso / Edición de instancia</caption>
				<tr>
					<td width="45%"><c:if test="${sessionScope.NivelPermiso==2}"><input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">&nbsp;<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton"></c:if></td>
			 	</tr>	
		</table>	 	
		<table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
			<tr>
				<td><span class="style2">*</span> Nivel:</td>
				<td>
					<select name="instNivel" style="width:80px;">
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaNivelVO}" var="niv">
							<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.instanciaVO.instNivel}">selected</c:if>><c:out value="${niv.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2">*</span> Nombre:</td>
				<td>
					<input type="text" name="instNombre" size="40" maxlength="250" value='<c:out value="${sessionScope.instanciaVO.instNombre}"/>'>
				</td>
		 	</tr>
			<tr>
				<td>Número de integrantes:</td>
				<td>
					<input type="text" name="instIntegrantes" size="10" maxlength="60" value='<c:out value="${sessionScope.instanciaVO.instIntegrantes}"/>' onkeyPress="return acepteNumeros(event);">
				</td>
				<td>Norma:</td>
				<td>
					<input type="text" name="instNorma" size="30" maxlength="200" value='<c:out value="${sessionScope.instanciaVO.instNorma}"/>'>
				</td>
		 	</tr>
			<tr>
				<td>Conformación:</td>
				<td colspan="3">
					<textarea name="instConforma" cols="70" rows="3" onKeyDown="textCounter(this,250,250);" onKeyUp="textCounter(this,250,250);" ><c:out value="${sessionScope.instanciaVO.instConforma}"/></textarea>
				</td>
		 	</tr>	
		</table>
	</form>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:150px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/participacion/instancia/FiltroObjetivo.jsp"/>
			</div>
		</td></tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:150px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/participacion/instancia/FiltroRepresentante.jsp"/>
			</div>
		</td></tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:150px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/participacion/instancia/FiltroDocumento.jsp"/>
			</div>
		</td></tr>
	</table>
</body>
</html>