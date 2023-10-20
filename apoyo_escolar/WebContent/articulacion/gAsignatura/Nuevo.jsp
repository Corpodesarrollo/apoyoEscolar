<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="gAsignaturaVO" class="articulacion.gAsignatura.vo.GAsignaturaVO" scope="session"/>
<jsp:useBean id="paramsVO" class="articulacion.gAsignatura.vo.ParamsVO" scope="page"/>
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
		document.frmNuevo.action='<c:url value="/articulacion/gAsignatura/Nuevo.do"/>';
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}

	function hacerValidaciones_frmNuevo(forma){
		validarEntero(forma.geArtAsigCodigo, "- Código de la Asignatura", 1, 99)
		validarCampo(forma.geArtAsigNombre, "- Nombre de la Asignatura", 1, 150)
		validarLista(forma.geArtAsigCodArea, "- Nombre del Area", 1)
	}
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:100px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/articulacion/gAsignatura/Filtro.do"/>
			</div>
			<div style="width:100%;height:120px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/articulacion/gAsignatura/Lista.do"/>
			</div>
			
		</td></tr>
	</table>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/articulacion/gAsignatura/Save.jsp"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>

		<input type="hidden" name="geArtAsigCodArea" value='<c:out value="${sessionScope.FareaVO.codigo}"/>'/>
		
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Nuevo Registro de Asignatura</caption>
				<tr>
					<td width="45%">
						<c:if test="${sessionScope.NivelPermiso==2}">
							<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
		        			<c:if test="${sessionScope.gAsignaturaVO.formaEstado==1}">
		        				<input name="cmd1" type="button" value="Actualizar" onClick="guardar()" class="boton">
		        			</c:if>
	    					<c:if test="${sessionScope.gAsignaturaVO.formaEstado!=1}">
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
					<input type="text" name="geArtAsigCodigo" maxlength="2" size="2" onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.gAsignaturaVO.geArtAsigCodigo}"/>'></input>
				</td>
			</tr>
			<tr>
				<td>
					<span class="style2">*</span><b>Nombre de Asignatura</b>
				</td>
				<td>
					<input type="text" name="geArtAsigNombre" maxlength="60" size="60"  value='<c:out value="${sessionScope.gAsignaturaVO.geArtAsigNombre}"/>'></input>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>