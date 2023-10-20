<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="parametroFuenteFinanciacion" class="poa.parametro.vo.FuenteFinanciacionVO" scope="session"/>
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
		validarEntero(forma.fueCodigo, "- Código", 1, 999)
		validarCampo(forma.fueNombre, "- Nombre", 1, 60)
		validarCampoOpcional(forma.fueDescripcion, "- Descripción", 1, 250)
		validarEnteroOpcional(forma.fueOrden, "- Orden", 1, 999)
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
		if(trim(forma.fueOrden.value)=='0') forma.fueOrden.value='';
		if(trim(forma.fueCodigo.value)=='0') forma.fueCodigo.value='';
	}
	
	function cambioCheck(obj,obj2){
		if(obj.checked==true){
			obj2.value='true';
		}else{
			obj2.value='false';
		}
	}
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:200px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/poa/parametro/FiltroFuenteFinanciacion.jsp"/>
			</div>
		</td></tr>
	</table>
	<form method="post" name="frmNuevo" action='<c:url value="/poa/parametro/SaveFuenteFinanciacion.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_FUENTE_FINANCIACION}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="CANCELAR" value='<c:out value="${paramsVO.CMD_CANCELAR}"/>'>
	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		  <caption>Ingreso / edición de fuente de financiación</caption>
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
				<input type="text" name="fueCodigo" maxlength="3" size="5" value='<c:out value="${sessionScope.parametroFuenteFinanciacion.fueCodigo}"/>' <c:out value="${sessionScope.parametroFuenteFinanciacion.formaDisabled}"/> onKeyPress='return acepteNumeros(event)'></input>
				</td>
				<td><span class="style2">*</span> Nombre:</td>
				<td>
				<input type="text" name="fueNombre" maxlength="60" size="30" value='<c:out value="${sessionScope.parametroFuenteFinanciacion.fueNombre}"/>'></input>
				</td>
			</tr>	
			<tr>			
				<td><span class="style2">*</span> Descripción:</td>
				<td colspan="3">
					<textarea name="fueDescripcion" cols="40" rows="2" onKeyDown="textCounter(this,250,250);" onKeyUp="textCounter(this,250,250);"><c:out value="${sessionScope.parametroFuenteFinanciacion.fueDescripcion}"/></textarea>
				</td>
		 	</tr>	
		 	<tr>
				<td><span class="style2">*</span> Orden:</td>
				<td>
				<input type="text" name="fueOrden" maxlength="3" size="5" value='<c:out value="${sessionScope.parametroFuenteFinanciacion.fueOrden}"/>' onKeyPress='return acepteNumeros(event)'></input>
				</td>
		 	</tr>
		 	<tr>
				<td><span class="style2">*</span> Pide presupuesto:</td>
				<td>
				<input type="hidden" name="fuePresupuesto" value='<c:out value="${sessionScope.parametroFuenteFinanciacion.fuePresupuesto}"/>'>
				<input type="checkbox" name="fuePresupuesto_" value='<c:out value="${sessionScope.parametroFuenteFinanciacion.fuePresupuesto}"/>' <c:if test="${sessionScope.parametroFuenteFinanciacion.fuePresupuesto}">checked</c:if> onclick="cambioCheck(this,document.frmNuevo.fuePresupuesto)"></input>
				</td>
				<td><span class="style2">*</span> Oculta en el formulario de 'necesidades del colegio':</td>
				<td>
				<input type="hidden" name="fueOculta" value='<c:out value="${sessionScope.parametroFuenteFinanciacion.fueOculta}"/>'>
				<input type="checkbox" name="fueOculta_" value='<c:out value="${sessionScope.parametroFuenteFinanciacion.fueOculta}"/>' <c:if test="${sessionScope.parametroFuenteFinanciacion.fueOculta}">checked</c:if> onclick="cambioCheck(this,document.frmNuevo.fueOculta)"></input>
				</td>
		 	</tr>
		</table>	
	</form>
</body>
<script type="text/javascript">
	init(document.frmNuevo);
</script>
</html>