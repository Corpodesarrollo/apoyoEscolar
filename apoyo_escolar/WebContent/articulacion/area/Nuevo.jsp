<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="areaVO" class="articulacion.area.vo.AreaVO" scope="session"/>
<jsp:useBean id="paramsVO" class="articulacion.area.vo.ParamsVO" scope="page"/>
<html>
<head>
<c:import url="/parametros.jsp"/>
<script languaje="javaScript">
<!--
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}

	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
		document.frmNuevo.action='<c:url value="/articulacion/area/Nuevo.do"/>';
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}
	
	function hacerValidaciones_frmNuevo(forma){
		//validarLista(forma.areCodigo, "- Área", 1)
  		validarEntero(forma.areIdentificacion, "- Código", 1, 99999)
		validarCampo(forma.areNombre, "- Nombre", 1, 60)
		validarCampo(forma.areAbreviatura, "- Abreviatra", 1, 10)
	}
	
	function cargaParams(){
		document.frmAjax.id.value=document.frmNuevo.areCodigo.options[document.frmNuevo.areCodigo.selectedIndex].value;
  		document.frmAjax.target="frameAjax";
		document.frmAjax.submit();
	}

//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:300px;overflow:auto;vertical-align:top;background-color:#ffffff;"><c:import url="/articulacion/area/Lista.do"/></div>
		</td></tr>
	</table>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/articulacion/area/Save.jsp"/>'>
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DEFAULT}"/>'>
			<input type="hidden" name="cmd" value=''>
			<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
			<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
			<input type="hidden" name="areAnhoVigencia" value='<c:out value="${sessionScope.filAreVO.filAnho}"/>'>
			<input type="hidden" name="arePerVigencia" value='<c:out value="${sessionScope.filAreVO.filPer}"/>'>
			
			<input type="hidden" name="areCodigo" value='2'>
			
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Área</caption>
				<tr><td width="45%">
					<c:if test="${sessionScope.NivelPermiso==2}">
					<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
		    		<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
	    		</c:if>
			  </td></tr>	
	  </table>
		
		<table width="98%" border="0" cellspacing="2" cellpadding="0">
			<!-- <tr>
				<td><span class="style2">*</span><b>&nbsp;Área:</b></td>
				<td>
					<select name="areCodigo" style='width:250px;' onChange='cargaParams()' <c:if test="${sessionScope.areaVO.formaEstado==1}">disabled</c:if>>
						<c:forEach begin="0" items="${requestScope.listaGArea}" var="gArea">
							<option value="<c:out value="${gArea.codigo}"/>" <c:if test="${gArea.codigo==sessionScope.areaVO.areCodigo}">selected</c:if>><c:out value="${gArea.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr> -->
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Código:</b></td>
				<td><input type="text" name="areIdentificacion" maxlength="5" size="5" onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.areaVO.areIdentificacion}"/>'></input></td>
			</tr>
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Nombre:</b></td>
				<td><input type="text" name="areNombre" maxlength="60" size="60" value='<c:out value="${sessionScope.areaVO.areNombre}"/>'></input></td>
			</tr>
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Abreviatura:</b></td>
				<td><input type="text" name="areAbreviatura" maxlength="10" size="10" value='<c:out value="${sessionScope.areaVO.areAbreviatura}"/>'></input></td>
			</tr>
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Orden:</b></td>
				<td><input type="text" name="areOrden" maxlength="3" size="5" onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.areaVO.areOrden}"/>'></input></td>
			</tr>
			<tr><td style="display:none"><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>
		</table>
	</form>
		<form method="post" name="frmAjax" action="<c:url value="/articulacion/area/Nuevo.do"/>">
			<input type="hidden" name="id" value="">
			<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DEFAULT}"/>'>
			<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
		</form>
</body>
</html>