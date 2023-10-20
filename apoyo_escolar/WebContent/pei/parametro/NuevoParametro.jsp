<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="parametroPEIVO" class="pei.parametro.vo.ParametroVO" scope="session"/>
<jsp:useBean id="paramsVO" class="pei.parametro.vo.ParamsVO" scope="page"/>
<html>
<head>
	<STYLE type='text/css'>
		.manual:hover{COLOR: red; FONT-WEIGHT: bold; FONT-FAMILY: Arial, Helvetica, sans-serif; FONT-SIZE: 10pt;}
		.manual:link{COLOR: red; FONT-WEIGHT: bold; FONT-FAMILY: Arial, Helvetica, sans-serif; FONT-SIZE: 10pt;}
		.manual:active{COLOR: red; FONT-WEIGHT: bold; FONT-FAMILY: Arial, Helvetica, sans-serif; FONT-SIZE: 10pt;}
		.manual:visited{COLOR: red; FONT-WEIGHT: bold; FONT-FAMILY: Arial, Helvetica, sans-serif; FONT-SIZE: 10pt;}
	</STYLE>
<SCRIPT language=javascript src='<c:url value="/pei/parametro/NuevoParametro.js"/>'></SCRIPT>
<script language="javaScript">var nav4=window.Event ? true : false;
var tipoEspecial=new Array();var i=0;
tipoEspecial[i++]='5';
tipoEspecial[i++]='6';
tipoEspecial[i++]='7';
tipoEspecial[i++]='8';
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<!-- TABS -->
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10">&nbsp;</td>
			<td rowspan="2" width="469">
				<img src='<c:url value="/etc/img/tabs/pei/parametro_1.gif"/>' alt="Parametro" height="26" border="0">
			</td>
		</tr>
	</table>
	<!-- /TABS -->
	<!-- LISTA -->
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:200px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/pei/parametro/ListaParametro.jsp"/>
			</div>
		</td></tr>
	</table>
	<!-- /LISTA -->
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/pei/parametro/SaveParametro.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_PARAMETRO}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="parCodigo" value='<c:out value="${sessionScope.parametroPEIVO.parCodigo}"/>'>
		<input type="hidden" name="parEdicion" value='<c:out value="${sessionScope.parametroPEIVO.parEdicion}"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
			<caption>Nuevo / Edición de parámetro</caption>
				<tr>
					<td align="left" width="70%">
						<c:if test="${sessionScope.NivelPermiso==2}">
    					<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
    					<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
	    				</c:if>
			  		</td>
			  		<td align="right">
						<a class="manual" href='<c:url value="/pei/resources/MANUAL_PEI.pdf"/>' target="_blank">&nbsp;Descargue el manual</a>			  		
			  		</td>
			 	</tr>	
		 </table>
		 <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
			<tr>
				<td><span class="style2">*</span> Tipo de parámetro:</td>
				<td>
					<select name="parTipo" style="width:180px;" <c:out value="${sessionScope.parametroPEIVO.parDisabled}"/> onchange="javaScript:cambioTipo(document.frmNuevo);">
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaTipo}" var="tipo">
							<option value="<c:out value="${tipo.codigo}"/>" <c:if test="${tipo.codigo==sessionScope.parametroPEIVO.parTipo}">selected</c:if>><c:out value="${tipo.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2">*</span> Orden:</td>
				<td>
					<input type="text" name="parOrden" maxlength="3" size="3" onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.parametroPEIVO.parOrden}"/>'></input>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span> Nombre:</td>
				<td colspan="3">
					<input type="text" name="parNombre" maxlength="120" size="50" value='<c:out value="${sessionScope.parametroPEIVO.parNombre}"/>'></input>				
				</td>
			</tr>
			<tr>
				<td>Abreviatura:</td>
				<td>
					<input type="text" name="parAbreviatura" maxlength="10" size="10" value='<c:out value="${sessionScope.parametroPEIVO.parAbreviatura}"/>'></input>				
				</td>
				<td><span class="style2">*</span> Estado:</td>
				<td>
					<select name="parEstado" style="width:80px;">
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaEstado}" var="estado">
							<option value="<c:out value="${estado.codigo}"/>" <c:if test="${estado.codigo==sessionScope.parametroPEIVO.parEstado}">selected</c:if>><c:out value="${estado.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr id='filaValor' name='filaValor' style="display:block;">
				<td><span class="style2">*</span> Máximo Valor:&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td colspan="3">
					<input type="text" name="parValor" maxlength="3" size="3" onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.parametroPEIVO.parValor}"/>'></input>
				</td>
			</tr>
			<tr>
				<td>Descripción:</td>
				<td colspan="3">
					<textarea name="parDescripcion" rows="3" cols="60" onKeyDown="textCounter(this,600,600);" onKeyUp="textCounter(this,600,600);"><c:out value="${sessionScope.parametroPEIVO.parDescripcion}"/></textarea>
				</td>
		 	</tr>	
		</table>	
	</form>
</body>
<script type="text/javascript">
	cambioTipo(document.frmNuevo);
</script>
</html>