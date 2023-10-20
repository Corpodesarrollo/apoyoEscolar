<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="filtroSeguimientoVO" class="poa.seguimiento.vo.FiltroSeguimientoVO" scope="session"/>
<jsp:useBean id="paramsVO" class="poa.seguimiento.vo.ParamsVO" scope="page"/>

<html>

	<c:import url="/parametros.jsp"/>

	<head>

		<script language="javaScript">
			<!-- 
				function hacerValidaciones_frmFiltro(forma) {
				
					validarLista(forma.filVigencia, "- Vigencia");
					
				}
	
				function buscar() {
					
					if(validarForma(document.frmFiltro)) {
						document.frmFiltro.cmd.value = document.frmFiltro.BUSCAR.value;
						document.frmFiltro.submit();
					}
					
				}
	
				function editar(n,m,o) {
				
					if(document.frmFiltro.id) {
					
							document.frmFiltro.id.value = n;
							document.frmFiltro.id2.value = m;
							document.frmFiltro.id3.value = o;
							document.frmFiltro.cmd.value = document.frmFiltro.EDITAR.value;
							document.frmFiltro.submit();
							
					}
					
				}
			//-->
		</script>
		
	</head>
	
	<c:import url="/mensaje.jsp"/>
	
	<body onLoad="mensaje(document.getElementById('msg'));">
	
		<form action='<c:url value="/poa/seguimiento/Save.jsp"/>' method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		
			<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_ACTIVIDAD}"/>'>
			<input type="hidden" name="cmd" value=''>
			<input type="hidden" name="id" value=""><input type="hidden" name="id2" value=""><input type="hidden" name="id3" value="">
			<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
			<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
			<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
			<input type="hidden" name="APROBAR" value='<c:out value="${paramsVO.CMD_APROBAR_COLEGIO}"/>'>
			<input type="hidden" name="guia" value="<c:out value="${requestScope.guia}"/>">
			<input type="hidden" name="filInstitucion" value='<c:out value="${sessionScope.login.instId}"/>'>
			
			<table cellpadding="0" cellspacing="0" border="0" align="center" width="100%" >
				<tr height="1">
					<td width="10">&nbsp;</td>
					<td rowspan="2" width="469">
						<img src='<c:url value="/etc/img/tabs/actividades_1.gif"/>' alt="Actividades" height="26" border="0">
					</td>
				</tr>
			</table>
			
			<table border="0" align="center" width="95%">
			 	<caption>Filtro de búsqueda</caption>
				<tr>
					<td>
						<c:if test="${sessionScope.NivelPermiso==2}">
							<input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
						</c:if>
					</td>
					<th>
						<span class="style2">Trimestre a registrar: <c:out value="${sessionScope.filtroSeguimientoVO.filRangoFechas}"/></span>
					</th>
			 	</tr>	
			</table>
		
			<table border="0" align="center" width="95%">
				<tr>
					<td>
						<span class="style2">*</span> Vigencia:
					</td>
					<td>
						<select name="filVigencia" style="width:120px" >
							<option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
								<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.filtroSeguimientoVO.filVigencia}">selected</c:if>><c:out value="${vig.nombre}"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
			
			<br>
			
			<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
			 	
			 	<caption>Listado de actividades  <c:if test="${!empty sessionScope.seguimientoVO.califTotalActividad}">- CALIFICACIÓN DEPENDENCIA:
					<c:out value="${sessionScope.seguimientoVO.califTotalActividad}"/>
				</c:if>
				</caption>
			 	<c:if test="${empty requestScope.listaActividades}">
					<tr>
						<th class="Fila1" colspan='6'>No hay actividades</th>
					</tr>
				</c:if>
				<c:if test="${!empty requestScope.listaActividades}">
					<tr>
						<th width='20' class="EncabezadoColumna" colspan="2" rowspan="2">&nbsp;</th>
						<td class="EncabezadoColumna" align="center" rowspan="2">Objetivo Estratégico</td>
						<td class="EncabezadoColumna" align="center" rowspan="2">Actividad / Tarea</td>
						<td class="EncabezadoColumna" align="center" colspan="4">Seguimiento</td>
						<td class="EncabezadoColumna" align="center" rowspan="2">Calificación</td>
					</tr>
					<tr>
						<td class="EncabezadoColumna" align="center">1er Trimestre</td>
						<td class="EncabezadoColumna" align="center">2do Trimestre</td>
						<td class="EncabezadoColumna" align="center">3er Trimestre</td>
						<td class="EncabezadoColumna" align="center">4to Trimestre</td>
					</tr>
					<c:forEach begin="0" items="${requestScope.listaActividades}" var="lista" varStatus="st">
						<tr>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center">
								<c:out value="${lista.plaConsecutivo}"/>
							</td>
							<th class='Fila<c:out value="${st.count%2}"/>'>
								<a href='javaScript:editar(<c:out value="${lista.plaVigencia}"/>,<c:out value="${lista.plaInstitucion}"/>,<c:out value="${lista.plaCodigo}"/>);'>
									<img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'>
								</a>
							</th>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center">
								<c:if test="${!empty lista.plaObjetivo}">
									<c:out value="${lista.plaObjetivo}"/>
								</c:if>
								<c:if test="${empty lista.plaObjetivo}">
									<textarea class='Fila<c:out value="${st.count%2}"/>' style="font-size:12px;width:90%" readonly="readonly" cols="20" rows="3" title='<c:out value="${lista.placcodobjetivoText}"/>'>
										<c:out value="${lista.placcodobjetivoText}"/>
									</textarea>
								</c:if>
							</td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaActividad}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaSeguimiento1}"/>&nbsp;</td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaSeguimiento2}"/>&nbsp;</td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaSeguimiento3}"/>&nbsp;</td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaSeguimiento4}"/>&nbsp;</td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.califActividad}"/></td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
			
		</form>
		
	</body>
	
</html>
