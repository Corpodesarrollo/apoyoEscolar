<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroConsultaPOA" class="poa.consulta.vo.FiltroConsultaVO" scope="session"/>
<jsp:useBean id="paramsVO" class="poa.consulta.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	function hacerValidaciones_frmFiltro(forma){
		//validarLista(forma.filLocalidad, "- Localidad");
		//validarLista(forma.filInstitucion, "- Colegio");
		validarLista(forma.filVigencia, "- Vigencia");
	}
	
	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.filLocalidad.disabled=false;
			document.frmFiltro.cmd.value = document.frmFiltro.BUSCAR.value;
			document.frmFiltro.submit();
		}
	}
	
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-Seleccione una-","-99");
	}
		
	function ajaxColegio(){
		borrar_combo(document.frmFiltro.filInstitucion); 
		document.frmAjax0.ajax[0].value=document.frmFiltro.filLocalidad.value;
		document.frmAjax0.cmd.value='<c:out value="${paramsVO.CMD_AJAX_COLEGIO}"/>';
 		document.frmAjax0.target="frameAjax0";
		document.frmAjax0.submit();
	}
	
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmAjax0" action='<c:url value="/poa/consulta/Ajax.do"/>'><INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_CONSULTA}"/>'><input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'><input type="hidden" name="ajax"><input type="hidden" name="ajax"></form>
	<form action='<c:url value="/poa/consulta/Save.jsp"/>' method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_CONSULTA}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="id" value=""><input type="hidden" name="id2" value=""><input type="hidden" name="id3" value="">
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<img src='<c:url value="/etc/img/tabs/filtroPOA_1.gif"/>' alt="Consulta" height="26" border="0">
				</td>
			</tr>
		</table>
		<table border="0" align="center" width="95%">
		 	<caption>Filtro de búsqueda</caption>
			<tr>
				<td>
					<c:if test="${sessionScope.NivelPermiso==2}">
   						<input name="cmd1" type="button" value="Generar" onClick="buscar()" class="boton">
   				</c:if>
		  		</td>
		 	</tr>	
		</table>
		<table border="0" align="center" width="95%">
			<tr>
				<td> Localidad:</td>
				<td colspan="3">
					<select name="filLocalidad" style="width:150px" onchange="javaScript:ajaxColegio();" <c:if test="${sessionScope.filtroConsultaPOA.filTieneLocalidad}">disabled="disabled"</c:if>>
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaLocalidad}" var="log">
							<option value="<c:out value="${log.codigo}"/>" <c:if test="${log.codigo==sessionScope.filtroConsultaPOA.filLocalidad}">selected</c:if>><c:out value="${log.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>	
			<tr>
				<td> Colegio:</td>
				<td colspan="3">
					<select name="filInstitucion" style="width:300px" >
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaColegio}" var="inst">
							<option value="<c:out value="${inst.codigo}"/>" <c:if test="${inst.codigo==sessionScope.filtroConsultaPOA.filInstitucion}">selected</c:if>><c:out value="${inst.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Vigencia:</td>
				<td>
					<select name="filVigencia" style="width:100px" >
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
							<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.filtroConsultaPOA.filVigencia}">selected</c:if>><c:out value="${vig.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr style="display:none"><td><iframe name="frameAjax0" id="frameAjax0"></iframe></td></tr>	
		</table>
	</form>
</body>
</html>