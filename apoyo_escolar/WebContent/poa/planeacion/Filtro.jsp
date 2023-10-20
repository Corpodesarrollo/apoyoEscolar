<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="filtroPlaneacionVO" class="poa.planeacion.vo.FiltroPlaneacionVO" scope="session"/>
<jsp:useBean id="paramsVO" class="poa.planeacion.vo.ParamsVO" scope="page"/>

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
			
		  		document.frmFiltro.tipo.value = tipo;
				document.frmFiltro.target = "";
				document.frmFiltro.submit();
				
			}
			
	
			function hacerValidaciones_frmFiltro(forma) {
			
				validarLista(forma.filVigencia, "- Vigencia");
				
			}
			
	
			function buscar() {
			
				if(validarForma(document.frmFiltro)) {
					document.frmFiltro.cmd.value = document.frmFiltro.BUSCAR.value;
					document.frmFiltro.submit();
				}
				
			}
			
	
			function aprobar() {
			
				if(validarForma(document.frmFiltro)) {
					if(confirm("¿Confirma el envío del POA? \n Nota: Una vez enviado no se podrá agregar o editar actividades")) {
						document.frmFiltro.cmd.value = document.frmFiltro.APROBAR.value;
						document.frmFiltro.submit();
					}	
				}
				
			}
			
	
			function editar(n, m, o) {
			
				if(document.frmFiltro.id) {
				
					document.frmFiltro.id.value = n;
					document.frmFiltro.id2.value = m;
					document.frmFiltro.id3.value = o;
					document.frmFiltro.cmd.value = document.frmFiltro.EDITAR.value;
					document.frmFiltro.submit();
				}
				
			}
			

			function eliminar(n, m, o) {
			
				if(confirm('¿Desea eliminar la actividad?')) {
				
					if(document.frmFiltro.id) {
						document.frmFiltro.id.value = n;
						document.frmFiltro.id2.value = m;
						document.frmFiltro.id3.value = o;
						document.frmFiltro.cmd.value = document.frmFiltro.ELIMINAR.value;
						document.frmFiltro.submit();
					}
					
				}
				
			}
			
	
			function inhabilitar() {
			
				if(document.getElementById("cmdPOA")) {
					document.getElementById("cmdPOA").style.display='none';
				}
			
			}
		//-->
		</script>
	</head>
	
	<c:import url="/mensaje.jsp"/>
	
	<body onLoad="mensaje(document.getElementById('msg'));">
		
		<form action='<c:url value="/poa/planeacion/Save.jsp"/>' method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
			
			<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_ACTIVIDAD}"/>'>
			<input type="hidden" name="cmd" value=''>
			<input type="hidden" name="id" value="">
			<input type="hidden" name="id2" value="">
			<input type="hidden" name="id3" value="">
			<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
			<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
			<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
			<input type="hidden" name="APROBAR" value='<c:out value="${paramsVO.CMD_APROBAR_COLEGIO}"/>'>
			<input type="hidden" name="guia" value="<c:out value="${requestScope.guia}"/>">
			<input type="hidden" name="filInstitucion" value='<c:out value="${sessionScope.login.instId}"/>'>
			
			<!-- 
			<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
				<tr height="1">
					<td width="10">&nbsp;</td>
					<td rowspan="2" width="469">
						<img src='<c:url value="/etc/img/tabs/actividades_1.gif"/>' alt="Actividades" height="26" border="0">
						<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_ACTIVIDAD_SIN}"/>)"><img src='<c:url value="/etc/img/tabs/problemas_0.gif"/>' alt="Actividades sin recursos" height="26" border="0"></a>
					</td>
				</tr>
			</table>
			-->

			<table border="0" align="center" width="95%">
				<tr>
					<c:if test="${sessionScope.filtroPlaneacionVO.filObservacion!=null}">
						<td>
							<span class="style2"><b>Observación por el rechazo del POA:</b>
								<c:out value="${sessionScope.filtroPlaneacionVO.filObservacion}"/>
							</span>
						</td>
					</c:if>
		 			<th>
		 				<span class="style2">Fecha para poder registrar: <c:out value="${sessionScope.filtroPlaneacionVO.filRangoFechas}"/></span>
		 			</th>
				</tr>
			</table>
		
			<table border="0" align="center" width="95%">
			 	<caption>Filtro de búsqueda</caption>
				<tr>
					<td>
						<c:if test="${sessionScope.NivelPermiso==2}">
	   						<input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
	   						<c:if test="${sessionScope.filtroPlaneacionVO.filHabilitado==1}">
	   							<input name="cmdPOA" id='cmdPOA' type="button" value="Enviar POA" onClick="aprobar()" class="boton">
	   						</c:if>
	   					</c:if>
			  		</td>
			  		<th>
			  			<span class="estadoPOA">Estado del POA: <c:out value="${sessionScope.filtroPlaneacionVO.filEstado}"/></span>
			  		</th>
			 	</tr>	
			</table>
			
			<table border="0" align="center" width="95%">
				<tr>
					<td><span class="style2">*</span> Vigencia:</td>
					<td>
						<select name="filVigencia" style="width:120px" onchange="javaScript:inhabilitar()">
							<option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
								<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.filtroPlaneacionVO.filVigencia}">selected</c:if>><c:out value="${vig.nombre}"/></option>
							</c:forEach>
						</select>
					</td>
					<td><span class="style2">*</span> Área de Gestión:</td>
					<td>
						<select name="filAreaGestion" style="width:220px">
							<option value="-99" selected>-Todos-</option>
							<c:forEach begin="0" items="${requestScope.listaAreaGestionFiltro}" var="area">
								<option value="<c:out value="${area.codigo}"/>" <c:if test="${area.codigo==sessionScope.filtroPlaneacionVO.filAreaGestion}">selected</c:if>><c:out value="${area.nombre}"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
			<br>
		
			<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
			 	<caption>Listado de actividades</caption>
			 	<c:if test="${empty requestScope.listaActividades}">
					<tr>
						<th class="Fila1" colspan='6'>No hay actividades</th>
					</tr>
				</c:if>
				<c:if test="${!empty requestScope.listaActividades}">
					<tr>
						<th width='20' class="EncabezadoColumna" colspan="2">&nbsp;</th>
						<td class="EncabezadoColumna" align="center">Objetivo Estratégico</td>
						<td class="EncabezadoColumna" align="center">Actividad / Tarea</td>
						<td class="EncabezadoColumna" align="center">Área de Gestión</td>
						<td class="EncabezadoColumna" align="center">Línea de Acción</td>
						<td class="EncabezadoColumna" align="center">Ponderador</td>
					</tr>
					<c:forEach begin="0" items="${requestScope.listaActividades}" var="lista" varStatus="st">
						<tr>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaConsecutivo}"/></td>
							<th class='Fila<c:out value="${st.count%2}"/>'>
								<a href='javaScript:editar(<c:out value="${lista.plaVigencia}"/>,<c:out value="${lista.plaInstitucion}"/>,<c:out value="${lista.plaCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
								<c:if test="${sessionScope.NivelPermiso==2}">
									<c:if test="${requestScope.eliminar == 'true'}">
										<a href='javaScript:eliminar(<c:out value="${lista.plaVigencia}"/>,<c:out value="${lista.plaInstitucion}"/>,<c:out value="${lista.plaCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a>
									</c:if>	
								</c:if>
							</th>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center">
								<c:if test="${!empty lista.plaObjetivo}">
									<c:out value="${lista.plaObjetivo}"/>
								</c:if>
								<c:if test="${empty lista.plaObjetivo}">
									<textarea class='Fila<c:out value="${st.count%2}"/>' style="font-size: 12px;width:90%" readonly="readonly" cols="20" rows="3" title='<c:out value="${lista.placcodobjetivoText}"/>'><c:out value="${lista.placcodobjetivoText}"/></textarea>
								</c:if>
							</td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaActividad}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaAreaGestionNombre}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaLineaAccionNombre}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaPonderador}"/></td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
			
		</form>
		
	</body>
	
</html>
