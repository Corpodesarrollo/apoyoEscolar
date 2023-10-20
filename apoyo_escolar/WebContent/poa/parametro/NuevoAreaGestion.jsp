<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="parametroAreaGestion" class="poa.parametro.vo.AreaGestionVO" scope="session"/>
<jsp:useBean id="paramsVO" class="poa.parametro.vo.ParamsVO" scope="page"/>
<html>
<head>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key==46 || key<=13 || (key>=48 && key<=57));
	}
		
	function hacerValidaciones_frmNuevo(forma){
		validarEntero(forma.areCodigo, "- Código", 1, 999)
		validarCampo(forma.areNombre, "- Nombre", 1, 60)
		validarCampoOpcional(forma.areDescripcion, "- Descripción", 1, 250)
		validarFloat(forma.arePonderador, "- Ponderador", 0.1, 100)
		validarEnteroOpcional(forma.areOrden, "- Orden", 1, 999)
	}
		
	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}
	
	function init(forma){
		if(trim(forma.areOrden.value)=='0') forma.areOrden.value='';
		if(trim(forma.areCodigo.value)=='0') forma.areCodigo.value='';
		if(trim(forma.arePonderador.value)=='0.0') forma.arePonderador.value='';
	}
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:200px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/poa/parametro/FiltroAreaGestion.jsp"/>
			</div>
		</td></tr>
	</table>
	<form method="post" name="frmNuevo" action='<c:url value="/poa/parametro/SaveAreaGestion.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_AREA_GESTION}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="CANCELAR" value='<c:out value="${paramsVO.CMD_CANCELAR}"/>'>
	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		  <caption>Ingreso / edición de área de gestión</caption>
				<tr>
					<td colspan="4">
						<c:if test="${sessionScope.NivelPermiso==2}">
    					<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
							<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
	    				</c:if>
			  		</td>
			 	</tr>	
			<tr>
			<tr>
				<td><span class="style2">*</span> Código:</td>
				<td>
				<input type="text" name="areCodigo" maxlength="3" size="5" value='<c:out value="${sessionScope.parametroAreaGestion.areCodigo}"/>' <c:out value="${sessionScope.parametroAreaGestion.formaDisabled}"/> onKeyPress='return acepteNumeros(event)'></input>
				</td>
				<td><span class="style2">*</span> Nombre:</td>
				<td>
				<input type="text" name="areNombre" maxlength="60" size="30" value='<c:out value="${sessionScope.parametroAreaGestion.areNombre}"/>'></input>
				</td>
			</tr>	
			<tr>			
				<td><span class="style2">*</span> Descripción:</td>
				<td colspan="3">
					<textarea name="areDescripcion" cols="40" rows="2" onKeyDown="textCounter(this,250,250);" onKeyUp="textCounter(this,250,250);"><c:out value="${sessionScope.parametroAreaGestion.areDescripcion}"/></textarea>
				</td>
		 	</tr>	
		 	<tr>
				<td><span class="style2">*</span> Ponderador:</td>
				<td>
				<input type="text" name="arePonderador" maxlength="7" size="6" value='<c:out value="${sessionScope.parametroAreaGestion.arePonderador}"/>' onKeyPress='return acepteNumeros(event)'></input>
				</td>
				<td><span class="style2">*</span> Orden:</td>
				<td>
				<input type="text" name="areOrden" maxlength="3" size="5" value='<c:out value="${sessionScope.parametroAreaGestion.areOrden}"/>' onKeyPress='return acepteNumeros(event)'></input>
				</td>
		 	</tr>
		</table>	
	</form>
</body>
<script type="text/javascript">
init(document.frmNuevo);
</script>
</html>