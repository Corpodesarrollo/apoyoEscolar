<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroRegistroPEIVO" class="pei.registro.vo.FiltroRegistroVO" scope="session"/>
<jsp:useBean id="paramsVO" class="pei.registro.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<SCRIPT language=javascript src='<c:url value="/pei/registro/FiltroRegistro.js"/>'></SCRIPT>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='SaveRegistro.jsp' method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_IDENTIFICACION}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<!-- TABS -->
		<table cellpadding="0" cellspacing="1" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<img src='<c:url value="/etc/img/tabs/pei/filtro_1.gif"/>' alt="Filtro" height="26" border="0">
				</td>
			</tr>
		</table>
		<!-- /TABS -->
		<table border="0" align="center" width="95%" cellspacing="1">
			<caption>Filtro de búsqueda</caption>
			<tr>
				<td>
					<input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
		  		</td>
		 	</tr>
		</table>
		<table border="0" align="center" width="95%">
			<tr>
				<td><span class="style2">*</span> Localidad:</td>
				<td>
					<select name="filLocalidad" style="width:100px;" onchange="javaScript:ajaxInstitucion();" <c:out value="${sessionScope.filtroRegistroPEIVO.filLocalidadBloqueada}"/>>
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaLocalidad}" var="tipo">
							<option value="<c:out value="${tipo.codigo}"/>" <c:if test="${tipo.codigo==sessionScope.filtroRegistroPEIVO.filLocalidad}">selected</c:if>><c:out value="${tipo.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2">*</span> Colegio:</td>
				<td>
					<select name="filInstitucion" style="width:250px;">
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaInstitucion}" var="estado">
							<option value="<c:out value="${estado.codigo}"/>" <c:if test="${estado.codigo==sessionScope.filtroRegistroPEIVO.filInstitucion}">selected</c:if>><c:out value="${estado.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
		<iframe name="frameAjax" id="frameAjax" width="0" height="0" style="display:none"></iframe>			
		<table border="0" align="center" width="95%">
			<caption>PEI del Colegio: <c:out value="${sessionScope.filtroRegistroPEIVO.filNombreInstitucion}"/></caption><tr><td></td></tr>
		</table>
	</form>
	<form method="post" name="frmAjax" action='Ajax.do'><INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_IDENTIFICACION}"/>'><input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="AJAX" value='<c:out value="${paramsVO.CMD_AJAX}"/>'></form>
</body>
</html>