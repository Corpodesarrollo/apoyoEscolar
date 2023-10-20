<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="filtroSeguimientoSEDVO" class="poa.seguimientoSED.vo.FiltroSeguimientoVO" scope="session"/>
<jsp:useBean id="paramsVO" class="poa.seguimientoSED.vo.ParamsVO" scope="page"/>
<jsp:useBean id="params2VO" class="poa.consulta.vo.ParamsVO" scope="page"/>

<html>

	<c:import url="/parametros.jsp"/>

	<head>
	
		<style type='text/css'>
			.estadoPOA{
				COLOR: red;
				FONT-WEIGHT: bold;
				FONT-FAMILY: Arial, Helvetica, sans-serif;
				FONT-SIZE: 10pt;
			}
		</style>
		
		<script language="javaScript">
		<!--
			function lanzar(tipo) {
  				document.frmFiltro.tipo.value=tipo;
				document.frmFiltro.target="";
				document.frmFiltro.submit();
			}
	
			function hacerValidaciones_frmFiltro(forma) {
				validarLista(forma.filLocalidad, "- Localidad");
				validarLista(forma.filVigencia, "- Vigencia");
			}
			
			function buscarCol() {
				if(validarForma(document.frmFiltro)) {
					document.frmFiltro.filLocalidad.disabled=false;	
					document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR_COL.value;
					document.frmFiltro.submit();
				}
			}
			
			function imprimir(n,m,o) {
				document.frmReporte.filLocalidad.value=n;
				document.frmReporte.filVigencia.value=m;
				document.frmReporte.filInstitucion.value=o;
				document.frmReporte.cmd.value=document.frmReporte.BUSCAR2.value;
				document.frmReporte.submit();
			}
	
			function editarCol(n) {
				if(document.frmFiltro.filInstitucion) {
						document.frmFiltro.filInstitucion.value=n;
						document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;
						document.frmFiltro.submit();
				}
			}
		
			function editar(n,m,o) {
				if(document.frmFiltro.id) {
					document.frmFiltro.id.value=n;
					document.frmFiltro.id2.value=m;
					document.frmFiltro.id3.value=o;
					document.frmFiltro.cmd.value=document.frmFiltro.EDITAR.value;
					document.frmFiltro.submit();
				}
			}
		//-->
		</script>
		
	</head>
	
	<c:import url="/mensaje.jsp"/>
	
	<body onLoad="mensaje(document.getElementById('msg'));">
	
		<form action='<c:url value="/poa/consulta/NuevoImprimirSeguimiento.do"/>' method="post" name="frmReporte" target="_blank">
			<input type="hidden" name="filLocalidad">
			<input type="hidden" name="filVigencia">
			<input type="hidden" name="filInstitucion">
			<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${params2VO.FICHA_CONSULTA}"/>'>
			<input type="hidden" name="cmd" value=''><input type="hidden" name="ext" value='1'>
			<input type="hidden" name="BUSCAR2" value='<c:out value="${params2VO.CMD_BUSCAR_SEGUIMIENTO}"/>'>
		</form>

		<form action='<c:url value="/poa/seguimientoSED/Save.jsp"/>' method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		
			<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_ACTIVIDAD}"/>'>
			<input type="hidden" name="cmd" value=''>
			<input type="hidden" name="id" value="">
			<input type="hidden" name="id2" value="">
			<input type="hidden" name="id3" value="">
			<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
			<input type="hidden" name="EDITAR_COL" value='<c:out value="${paramsVO.CMD_EDITAR_COL}"/>'>
			<input type="hidden" name="BUSCAR_COL" value='<c:out value="${paramsVO.CMD_BUSCAR_COL}"/>'>
			<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
			<input type="hidden" name="guia" value="<c:out value="${requestScope.guia}"/>">
			<input type="hidden" name="filInstitucion" value="<c:out value="${sessionScope.filtroSeguimientoSEDVO.filInstitucion}"/>">
			
			<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
				<tr height="1">
					<td width="10">&nbsp;</td>
					<td rowspan="2" width="469">
						<img src='<c:url value="/etc/img/tabs/filtroPOA_1.gif"/>' alt="Filtro" height="26" border="0">
					</td>
				</tr>
			</table>
			
			<table border="0" align="center" width="95%">
			 	<caption>Filtro de búsqueda</caption>
				<tr>
					<td>
						<c:if test="${sessionScope.NivelPermiso==2}">
	   						<input name="cmd1" type="button" value="Buscar" onClick="buscarCol()" class="boton">
	   					</c:if>
			  		</td>
			 	</tr>	
			</table>
			
			<table border="0" align="center" width="95%">
				<tr>
					<td><span class="style2">*</span> Vigencia:</td>
					<td>
						<select name="filVigencia" style="width:120px">
							<option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
								<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.filtroSeguimientoSEDVO.filVigencia}">selected</c:if>><c:out value="${vig.nombre}"/></option>
							</c:forEach>
						</select>
					</td>
					<td><span class="style2">*</span> Localidad:</td>
					<td>
						<select name="filLocalidad" style="width:180px" <c:if test="${sessionScope.filtroSeguimientoSEDVO.filTieneLocalidad}">disabled="disabled"</c:if> >
							<option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaLocalidad}" var="log">
								<option value="<c:out value="${log.codigo}"/>" <c:if test="${log.codigo==sessionScope.filtroSeguimientoSEDVO.filLocalidad}">selected</c:if>><c:out value="${log.nombre}"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
			
			<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
			 	<caption>Listado de colegios</caption>
			 	<c:if test="${empty requestScope.listaColegio}">
					<tr>
						<th class="Fila1" colspan='6'>No hay colegios</th>
					</tr>
				</c:if>
				<c:if test="${!empty requestScope.listaColegio}">
					<tr>
						<th width='20' class="EncabezadoColumna" colspan="2">&nbsp;</th>
						<td class="EncabezadoColumna" align="center">Nombre</td>
						<td class="EncabezadoColumna" align="center">Imprimir</td>
					</tr>
					<c:forEach begin="0" items="${requestScope.listaColegio}" var="lista" varStatus="st">
						<tr>
							<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${lista.colConsecutivo}"/></th>
							<th class='Fila<c:out value="${st.count%2}"/>'><a href='javaScript:editarCol(<c:out value="${lista.colColegio}"/>,<c:out value="${lista.colVigencia}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a></th>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle"><c:out value="${lista.colNombre}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center" valign="middle">&nbsp;<input name="cmdPOA3" id='cmdPOA3' type="button" value="Imprimir" style="width:70px;" onClick='imprimir(<c:out value="${sessionScope.filtroSeguimientoSEDVO.filLocalidad}"/>,<c:out value="${lista.colVigencia}"/>,<c:out value="${lista.colColegio}"/>)' class="boton">&nbsp;</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
			
		</form>
		
	</body>
	
</html>
