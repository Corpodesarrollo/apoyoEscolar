<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroParametroPEIVO" class="pei.parametro.vo.FiltroParametroVO" scope="session"/>
<jsp:useBean id="paramsVO" class="pei.parametro.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<SCRIPT language=javascript src='<c:url value="/pei/parametro/ListaParametro.js"/>'></SCRIPT>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/pei/parametro/SaveParametro.jsp"/>' method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_PARAMETRO}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="filCodigo" value='<c:out value="${sessionScope.filtroParametroPEIVO.filCodigo}"/>'>
		<table border="0" align="center" width="95%">
			<caption>Filtro de búsqueda</caption>
			<tr>
				<td>
					<input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
		  		</td>
		 	</tr>
		</table>
		<table border="0" align="center" width="95%">
			<tr>
				<td><span class="style2">*</span> Tipo de parámetro:</td>
				<td>
					<select name="filTipo" style="width:180px;">
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaTipo}" var="tipo">
							<option value="<c:out value="${tipo.codigo}"/>" <c:if test="${tipo.codigo==sessionScope.filtroParametroPEIVO.filTipo}">selected</c:if>><c:out value="${tipo.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2">*</span> Estado:</td>
				<td>
					<select name="filEstado" style="width:80px;">
						<option value="-99" selected>Todos</option>
						<c:forEach begin="0" items="${requestScope.listaEstado}" var="estado">
							<option value="<c:out value="${estado.codigo}"/>" <c:if test="${estado.codigo==sessionScope.filtroParametroPEIVO.filEstado}">selected</c:if>><c:out value="${estado.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>		
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
				<caption>Listado de parámetros</caption>
				<tr>
					<th width='40' class="EncabezadoColumna">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Nombre</td>
					<td class="EncabezadoColumna" align="center">Estado</td>
					<td class="EncabezadoColumna" align="center">Orden</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaParametro}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${lista.parTipo}"/>,<c:out value="${lista.parCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
							<c:if test="${sessionScope.NivelPermiso==2}"><a href='javaScript:eliminar(<c:out value="${lista.parTipo}"/>,<c:out value="${lista.parCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.parNombre}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.parNombreEstado}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.parOrden}"/></td>
					</tr>
				</c:forEach>
		</table>
	</form>
</body>
</html>