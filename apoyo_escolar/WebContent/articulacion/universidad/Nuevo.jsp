<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="universidadVO" class="articulacion.universidad.vo.UniversidadVO" scope="session"/>
<jsp:useBean id="paramsVO" class="articulacion.universidad.vo.ParamsVO" scope="page"/>
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
		document.frmNuevo.action='<c:url value="/articulacion/universidad/Nuevo.do"/>';
		document.frmNuevo.submit();
	}

	function guardar(){
	
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			if(document.frmNuevo.guniDireccionsp.value=="")
				document.frmNuevo.guniDireccionsp.value=" "
			if(document.frmNuevo.guniTelefono.value=="")
				document.frmNuevo.guniTelefono.value=" "
			document.frmNuevo.submit();
		}
	}

	function hacerValidaciones_frmNuevo(forma){
		validarEntero(forma.guniCodigo, "- Código de Universidad", 1, 999)
		validarCampo(forma.guniNombre , "- Nombre de Universidad", 1, 150)
		validarCampoOpcional(forma.guniDireccionsp , "- Dirección de la universidad", 1, 150)
	}
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:150px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/articulacion/universidad/Lista.do"/>
			</div>
		</td></tr>
	</table>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/articulacion/universidad/Save.jsp"/>'>
		<input type="hidden" name="cmd" value=''>
		
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>

		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Información de la Universidad</caption>
				<tr>
					<td width="45%">
						<c:if test="${sessionScope.NivelPermiso==2}">
							<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
		        			<c:if test="${sessionScope.universidadVO.formaEstado==1}">
		        				<input name="cmd1" type="button" value="Actualizar" onClick="guardar()" class="boton">
		        			</c:if>
	    					<c:if test="${sessionScope.universidadVO.formaEstado!=1}">
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
						<input type="text" name="guniCodigo" maxlength="3" size="3" onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.universidadVO.guniCodigo}"/>'></input>
					</td>
				</tr>
				<tr>
					<td>
						<span class="style2">*</span><b>Nombre de Universidad</b>
					</td>
					<td>>
						<input type="text" name="guniNombre" maxlength="60" size="50"  value='<c:out value="${sessionScope.universidadVO.guniNombre}"/>'></input>
					</td>
				</tr>
				<tr>
					<td>
						<span class="style2"></span><b>Dirección</b>
					</td>
					<td>
						<input type="text" name="guniDireccionsp" maxlength="100" size="50"  value='<c:out value="${sessionScope.universidadVO.guniDireccionsp}"/>'></input>
					</td>
				</tr>
				<tr>
					<td>
						<span class="style2"></span><b>Teléfono</b>
					</td>
					<td>
						<input type="text" name="guniTelefono" maxlength="60" size="50"  value='<c:out value="${sessionScope.universidadVO.guniTelefono}"/>' onKeyPress='return acepteNumeros(event)'></input>
					</td>
				</tr>
				<tr>
					<td>
						<span class="style2"></span><b>Contacto</b>
					</td>
					<td>
						<input type="text" name="guniContacto" maxlength="50" size="50"  value='<c:out value="${sessionScope.universidadVO.guniContacto}"/>'></input>
					</td>
				</tr>
				<tr>
					<td>
						<span class="style2"></span><b>Teléfono del Contacto</b>
					</td>
					<td>
						<input type="text" name="guniTelcontacto" maxlength="30" size="30"  value='<c:out value="${sessionScope.universidadVO.guniTelcontacto}"/>' onKeyPress='return acepteNumeros(event)'></input>
					</td>
				</tr>
				<tr>
					<td>
						<span class="style2"></span><b>Correo del Contacto</b>
					</td>
					<td>
						<input type="text" name="guniCorreo" maxlength="50" size="50"  value='<c:out value="${sessionScope.universidadVO.guniCorreo}"/>'></input>
					</td>
				</tr>
				<tr>
					<td>
						<span class="style2"></span><b>Estado</b>
					</td>
					<td>
						<input type="radio" name="guniEstado" value="1" <c:if test="${sessionScope.universidadVO.guniEstado eq '1'}">checked</c:if>> Activo
						<input type="radio" name="guniEstado" value="2" <c:if test="${sessionScope.universidadVO.guniEstado eq '2'}">checked</c:if>> Inactivo
					</td>
				</tr>
			</table>
		</form>
</body>
</html>