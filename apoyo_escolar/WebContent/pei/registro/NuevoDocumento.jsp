<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="documentoPEIVO" class="pei.registro.vo.DocumentoVO" scope="session"/>
<jsp:useBean id="paramsVO" class="pei.registro.vo.ParamsVO" scope="page"/>
<html>
<head>
	<STYLE type='text/css'>
		.estadoPEI{
			COLOR: red;
			FONT-WEIGHT: bold;
			FONT-FAMILY: Arial, Helvetica, sans-serif;
			FONT-SIZE: 10pt;
		}
	</STYLE>
<SCRIPT language=javascript src='<c:url value="/pei/registro/NuevoDocumento.js"/>'></SCRIPT>
<script language="javaScript">var nav4=window.Event ? true : false;
    var extensiones = new Array();
    extensiones[0]=".pdf";
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<!-- FORMULARIO DE FILTRO -->
	<c:if test="${sessionScope.filtroRegistroPEIVO.filDisabled==true}">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr><td width="100%" valign='top'>
				<div style="width:100%;overflow:auto;vertical-align:top;background-color:#ffffff;">
					<c:import url="/pei/registro/FiltroRegistro.jsp"/>
				</div>
			</td></tr>
		</table>
	</c:if>
	<!-- /FORMULARIO DE FILTRO -->
	<c:if test="${sessionScope.filtroRegistroPEIVO.filInstitucion>0}">
	<!-- TABS -->
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10">&nbsp;</td>
			<td rowspan="2" width="469">
				<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_IDENTIFICACION}"/>)"><img src='<c:url value="/etc/img/tabs/pei/identificacion_0.gif"/>' alt="Identificacion" height="26" border="0"></a>
				<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_HORIZONTE}"/>)"><img src='<c:url value="/etc/img/tabs/pei/horizonte_0.gif"/>' alt="Horizonte" height="26" border="0"></a>
				<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_DESARROLLO_CURRICULAR}"/>)"><img src='<c:url value="/etc/img/tabs/pei/desarrollo_curricular_0.gif"/>' alt="Desarrollo curricular" height="26" border="0"></a>
				<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_DESARROLLO_ADMINISTRATIVO}"/>)"><img src='<c:url value="/etc/img/tabs/pei/desarrollo_administrativo_0.gif"/>' alt="Desarrollo administrativo" height="26" border="0"></a>
				<img src='<c:url value="/etc/img/tabs/pei/documento_1.gif"/>' alt="Documento" height="26" border="0">
			</td>
		</tr>
	</table>
	<!-- /TABS -->
	<!-- LISTA -->
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:200px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/pei/registro/ListaDocumento.jsp"/>
			</div>
		</td></tr>
	</table>
	<!-- /LISTA -->
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='SaveDocumento.jsp'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_DOCUMENTO}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="docInstitucion" value='<c:out value="${sessionScope.documentoPEIVO.docInstitucion}"/>'>
		<input type="hidden" name="docUsuario" value='<c:out value="${sessionScope.documentoPEIVO.docUsuario}"/>'>
		<input type="hidden" name="idenEstado" value='<c:out value="${sessionScope.identificacionPEIVO.idenEstado}"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
			<caption>IMPORTACIÓN Y EDICIÓN DE DOCUMENTO</caption>
				<tr>
					<td align="left" width="70%">
						<c:if test="${sessionScope.NivelPermiso==2 && (sessionScope.filtroRegistroPEIVO.filDisabled==false && sessionScope.identificacionPEIVO.idenDisabled==false)}">
    					<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
						<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
	    				</c:if>
	    				<c:if test="${sessionScope.filtroRegistroPEIVO.filDisabled==true || sessionScope.identificacionPEIVO.idenDisabled==true}"><span class="style2">No se puede actualizar la información</span></c:if>
			  		</td>
			 	</tr>	
		 </table>
		  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
			<c:if test="${sessionScope.documentoPEIVO.edicion==false}">			
				<tr>
					<td><span class="style2">*</span> Documento (PDF):</td>
					<td colspan="3">
						<input type="file" name="docArchivo" size="40" accept="application/pdf" <c:out value="${sessionScope.filtroRegistroPEIVO.filDisabled_}"/>></input>
					</td>
				</tr>
			</c:if>
			<tr>
				<td><span class="style2">*</span> Nombre:</td>
				<td>
					<input type="text" name="docNombre" maxlength="200" size="40" value='<c:out value="${sessionScope.documentoPEIVO.docNombre}"/>' <c:out value="${sessionScope.filtroRegistroPEIVO.filDisabled_}"/>></input>
				</td>
				<td><span class="style2">*</span> Fecha:</td>
				<td>
					<input type="text" name="docFecha" maxlength="10" size="10" value='<c:out value="${sessionScope.documentoPEIVO.docFecha}"/>' readonly="readonly"></input>
				</td>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Usuario:</td>
				<td colspan="3">
					<input type="text" name="docUsuarioNombre" maxlength="120" size="40" value='<c:out value="${sessionScope.documentoPEIVO.docUsuarioNombre}"/>' readonly="readonly"></input>
				</td>
			</tr>	
			<tr>
				<td colspan="4"> Descripción:</td>
			</tr>	
			<tr>
				<td colspan="4">
				<textarea name="docDescripcion" rows="2" cols="70" onKeyDown="textCounter(this,600,600);" onKeyUp="textCounter(this,600,600);" <c:out value="${sessionScope.filtroRegistroPEIVO.filDisabled_}"/>><c:out value="${sessionScope.documentoPEIVO.docDescripcion}"/></textarea>				
				</td>
			</tr>	
		</table>
	</form>
	</c:if>
</body>
</html>