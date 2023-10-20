<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="vigenciaPoaVO" class="poa.parametro.vo.VigenciaPoaVO" scope="session"/>
<jsp:useBean id="paramsVO" class="poa.parametro.vo.ParamsVO" scope="page"/>
<html>
<head>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key==46 || key<=13 || (key>=48 && key<=57));
	}
		
		
		function lanzar(tipo){
  	document.frmNuevo.tipo.value=tipo;
		document.frmNuevo.target="";
		document.frmNuevo.submit();
	}
	function hacerValidaciones_frmNuevo(forma){
		 validarLista(forma.vigenciaPoa, "- Vigencia", 1);
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
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_AREA_GESTION}"/>)"><img src='<c:url value="/etc/img/tabs/area_gestion_0.gif"/>' alt="Area de gestion" height="26" border="0"></a>
					<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_LINEA_ACCION}"/>)"><img src='<c:url value="/etc/img/tabs/linea_accion_0.gif"/>' alt="Linea de accion" height="26" border="0"></a>
					<!--<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_FUENTE_FINANCIACION}"/>)"><img src='<c:url value="/etc/img/tabs/fuente_financiacion_0.gif"/>' alt="Fuente de financiacion" height="26" border="0"></a>-->
					<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_TIPO_META}"/>)"><img src='<c:url value="/etc/img/tabs/tipo_meta_0.gif"/>' alt="Tipo de meta" height="26" border="0"></a>
					<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_UNIDAD_MEDIDA}"/>)"><img src='<c:url value="/etc/img/tabs/unidad_medida_0.gif"/>' alt="Unidad de medida" height="26" border="0"></a>
					<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_PROGRAMACION_FECHAS}"/>)"><img src='<c:url value="/etc/img/tabs/programacion_fechas_0.gif"/>' alt="Programación de fechas" height="26" border="0"></a>
					<img src='<c:url value="/etc/img/tabs/poa_vigencia_1.gif"/>' alt="Vigencia POA" height="26" border="0">
				</td>
			</tr>
	</table>
	<form method="post" name="frmNuevo" action='<c:url value="/poa/parametro/SaveAreaGestion.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_VIG}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
	    <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		  <caption>Ingreso / edición vigencia POA</caption>
	         <tr><td>&nbsp;&nbsp;</td></tr>
	         <tr>
			<td width="30%" align="center" ><span class="style2">*</span> Vigencia:&nbsp;&nbsp;&nbsp;<select name="vigenciaPoa" id="vigenciaPoa" style="width:80px;" <c:out value="${sessionScope.aprobacionVO.plaDisabled}"/>>
					<option value="-99" selected>-Seleccione uno-</option>
					<c:forEach begin="0" items="${requestScope.listaVigencias}" var="vig">
						<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.vigenciaPoaVO.vigencia}">selected</c:if>><c:out value="${vig.nombre}"/></option>
					</c:forEach>
				</select>
			 </td>
		 	</tr>
		 <tr><td>&nbsp;&nbsp;</td></tr>
		 <tr><td  align="center" ><input type="button" value="Actualizar" onClick="guardar()" class="boton" >  </td>  </tr>
		</table>	
	</form>
</body>
<script type="text/javascript">
init(document.frmNuevo);
</script>
</html>