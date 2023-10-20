<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroGestionActividadesVO" class="siges.gestionAdministrativa.gestionActividades.vo.FiltroGestionVO" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.gestionAdministrativa.gestionActividades.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
	function lanzar(tipo){
  	    document.frmFiltro.tipo.value=tipo;
  	    document.frmFiltro.cmd.value=-1;
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}

	function buscar(){
		document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;
		document.frmFiltro.submit();
	}
	
	function editar(m){
		document.frmFiltro.cmd.value=document.frmFiltro.EDITAR.value;
		document.frmFiltro.tipo.value=m;
		document.frmFiltro.submit();
	}
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/siges/gestionAdministrativa/gestionActividades/Save.jsp"/>' method="post" name="frmFiltro" >
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="cmd"  value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="tipo" value='0'>

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
				<td><span class="style2">*</span> Mes:</td>
				<td>
					<select name="mes" style="width:120px">
						<option value="1" <c:if test="${1 == sessionScope.filtroGestionActividadesVO.MES}">selected</c:if>>-- ENERO --</option>
						<option value="2" <c:if test="${2 == sessionScope.filtroGestionActividadesVO.MES}">selected</c:if>>-- FEBRER0 --</option>
						<option value="3" <c:if test="${3 == sessionScope.filtroGestionActividadesVO.MES}">selected</c:if>>-- MARZO --</option>
						<option value="4" <c:if test="${4 == sessionScope.filtroGestionActividadesVO.MES}">selected</c:if>>-- ABRIL --</option>
						<option value="5" <c:if test="${5 == sessionScope.filtroGestionActividadesVO.MES}">selected</c:if>>-- MAYO --</option>
						<option value="6" <c:if test="${6 == sessionScope.filtroGestionActividadesVO.MES}">selected</c:if>>-- JUNIO --</option>
						<option value="7" <c:if test="${7 == sessionScope.filtroGestionActividadesVO.MES}">selected</c:if>>-- JULIO --</option>
						<option value="8" <c:if test="${8 == sessionScope.filtroGestionActividadesVO.MES}">selected</c:if>>-- AGOSTO --</option>
						<option value="9" <c:if test="${9 == sessionScope.filtroGestionActividadesVO.MES}">selected</c:if>>-- SEPTIEMBRE --</option>
						<option value="10" <c:if test="${10 == sessionScope.filtroGestionActividadesVO.MES}">selected</c:if>>-- OCTUBRE --</option>
						<option value="11" <c:if test="${11 == sessionScope.filtroGestionActividadesVO.MES}">selected</c:if>>-- NOVIEMBRE --</option>
						<option value="12" <c:if test="${12 == sessionScope.filtroGestionActividadesVO.MES}">selected</c:if>>-- DICIEMBRE --</option>
					</select>
				</td>
				<td><span class="style2">*</span> Nivel:</td>
				<td>
					<select name="nivel" style="width:120px">
						<option value="1" <c:if test="${1 == sessionScope.filtroGestionActividadesVO.nivel}">selected</c:if>>-- Colegio --</option>
						<option value="2" <c:if test="${2 == sessionScope.filtroGestionActividadesVO.nivel}">selected</c:if>>-- Sede --</option>
						<option value="3" <c:if test="${3 == sessionScope.filtroGestionActividadesVO.nivel}">selected</c:if>>-- Jornada --</option>
						<option value="4" <c:if test="${4 == sessionScope.filtroGestionActividadesVO.nivel}">selected</c:if>>-- Sede-Jornada --</option>
						<option value="5" <c:if test="${5 == sessionScope.filtroGestionActividadesVO.nivel}">selected</c:if>>-- Metodología-Grado --</option>
						<option value="6" <c:if test="${6 == sessionScope.filtroGestionActividadesVO.nivel}">selected</c:if>>-- Grupo --</option>
					</select>
				</td>
				<td><span class="style2">*</span> Estado:</td>
				<td>
					<select name="estado" style="width:120px">
						<option value="1" <c:if test="${1 == sessionScope.filtroGestionActividadesVO.estado}">selected</c:if>>-- Todos --</option>
						<option value="2" <c:if test="${2 == sessionScope.filtroGestionActividadesVO.estado}">selected</c:if>>-- Activos --</option>
						<option value="3" <c:if test="${3 == sessionScope.filtroGestionActividadesVO.estado}">selected</c:if>>-- Inactivos --</option>
					</select>
				</td>
			</tr>
		</table><br> 	
		<!-- TABLA DE LOS RESULTADOS DE LA BUSQUEDA -->
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Listado de actividades</caption>
		 	<c:if test="${empty requestScope.listaActividades}"><tr><th class="Fila1" colspan='6'>No hay actividades</th></tr></c:if>
			<c:if test="${!empty requestScope.listaActividades}">
				<tr>
					<td width='5%' class="EncabezadoColumna" align="center">Acción</td>
					<td width='10%' class="EncabezadoColumna" align="center">Nivel</td>
					<td width='30%' class="EncabezadoColumna" align="center">Nombre</td>
					<td width='30%' class="EncabezadoColumna" align="center">Fecha</td>
					<td width='30%' class="EncabezadoColumna" align="center">Estado</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaActividades}" var="asignacion" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${asignacion.codigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
						</th>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							&nbsp;
								<c:if test="${1 == sessionScope.filtroGestionActividadesVO.nivel}">Colegio</c:if>
								<c:if test="${2 == sessionScope.filtroGestionActividadesVO.nivel}">Sede</c:if>
								<c:if test="${3 == sessionScope.filtroGestionActividadesVO.nivel}">Jornada</c:if>
								<c:if test="${4 == sessionScope.filtroGestionActividadesVO.nivel}">Sede-Jornada</c:if>
								<c:if test="${5 == sessionScope.filtroGestionActividadesVO.nivel}">Metodología-Grado</c:if>
								<c:if test="${6 == sessionScope.filtroGestionActividadesVO.nivel}">Grupo</c:if>
							&nbsp;
						</th>
						<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;<c:out value="${asignacion.nombre}"/>&nbsp;</th>
						<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;<c:out value="${asignacion.fecha}"/>&nbsp;</th>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							&nbsp;
								<c:if test="${1 == asignacion.estado}">Activo</c:if>
								<c:if test="${2 == asignacion.estado}">Inactivo</c:if>
							&nbsp;
						</th>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>