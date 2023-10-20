<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="filtroCambioVO" class="poa.aprobarCambios.vo.FiltroCambiosVO" scope="session"/>
<jsp:useBean id="paramsVO" class="poa.aprobarCambios.vo.ParamsVO" scope="page"/>

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
				validarLista(forma.filEntidad, "- Colegio");
							
			}
			
			function buscar() {
			
				if(validarForma(document.frmFiltro)) {
				
					document.frmFiltro.cmd.value = document.frmFiltro.BUSCAR.value;
					document.frmFiltro.submit();
					
				}
				
			}
	
			function aprobar(v,n,e,f) {
			
				if(validarForma(document.frmFiltro)) {
				
					if(confirm("¿Confirma la aprobación del cambio? \n Nota: Una vez aprobado debe realizar el cambio respectivo en el POA.")) {
					
						document.frmFiltro.cmd.value = document.frmFiltro.APROBAR.value;
						document.frmFiltro.filVigencia.value = v;
						document.frmFiltro.filNivel.value = n;
						document.frmFiltro.filEntidad.value = e;
						document.frmFiltro.filFechaSol.value = f;
						document.frmFiltro.submit();
						
					}	
					
				}
				
			}

			function cambio(v,n,e,f) {
			
				if(validarForma(document.frmFiltro)) {

					document.frmFiltro.cmd.value = document.frmFiltro.REALIZAR.value;				
					document.frmFiltro.filVigencia.value = v;
					document.frmFiltro.filNivel.value = n;
					document.frmFiltro.filEntidad.value = e;
					document.frmFiltro.filFechaSol.value = f;
					document.frmFiltro.submit();

				}
				
			}

			function rechazar(v,n,e,f) {
			
				if(validarForma(document.frmFiltro)) {
				
					if(confirm("¿Confirma el rechazo del cambio?")) {
					
						if(validarObservacion(document.frmFiltro)) {
											
							document.frmFiltro.cmd.value = document.frmFiltro.RECHAZAR.value;					
							document.frmFiltro.filVigencia.value = v;
							document.frmFiltro.filNivel.value = n;
							document.frmFiltro.filEntidad.value = e;
							document.frmFiltro.filFechaSol.value = f;
							document.frmFiltro.submit();
							
						}
						
					}
					
				}
				
			}

			function validarObservacion(forma) {
			
				if(forma.filObservacion) {
				
					if(trim(forma.filObservacion.value).length==0 || trim(forma.filObservacion.value).length>500) {
						alert('- Observación por rechazo (hasta 500 caracteres)');
						return false;
					}
					
				}
				
				return true;
				
			}
	
			function editar(n,m,o,p) {
			
				if(document.frmFiltro.id) {
				
					document.frmFiltro.id.value = n;
					document.frmFiltro.id2.value = m;
					document.frmFiltro.id3.value = o;
					document.frmFiltro.id4.value = p;
					document.frmFiltro.cmd.value = document.frmFiltro.EDITAR.value;				
					document.frmFiltro.submit();
					
				}
				
			}

			function eliminar(n,m,o,p) {
			
				if(confirm('¿Desea eliminar el cambio?')) {
				
					if(document.frmFiltro.id) {
					
						document.frmFiltro.id.value = n;
						document.frmFiltro.id2.value = m;
						document.frmFiltro.id3.value = o;
						document.frmFiltro.id4.value = p;
						document.frmFiltro.cmd.value = document.frmFiltro.ELIMINAR.value;
						document.frmFiltro.submit();
						
					}
					
				}
				
			}
			
			function inhabilitar() {
			
				if(document.getElementById("cmdPOA")) {
				
					document.getElementById("cmdPOA").style.display = 'none';
					
				}
			}

			function ajaxColegio() {
			
				borrar_combo(document.frmFiltro.filEntidad);
				document.frmAjax0.ajax[0].value = document.frmFiltro.filVigencia.value;
				document.frmAjax0.ajax[1].value = document.frmFiltro.filLoc.value;
				document.frmAjax0.cmd.value = '<c:out value="${paramsVO.CMD_AJAX_COLEGIO}"/>';
		 		document.frmAjax0.target = "frameAjax0";
				document.frmAjax0.submit();
				
			}

		//-->
		</script>
		
	</head>
	
	<c:import url="/mensaje.jsp"/>
	
	<body onLoad="mensaje(document.getElementById('msg'));">
	
		<form method="post" name="frmAjax0" action='<c:url value="/poa/aprobarCambios/Ajax.do"/>'>
		
			<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_CAMBIO}"/>'>
			<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
		
		</form>
		
		<form action='<c:url value="/poa/aprobarCambios/Save.jsp"/>' method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
			
			<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_CAMBIO}"/>'>
			<input type="hidden" name="cmd" value=''>
			<input type="hidden" name="id" value="">
			<input type="hidden" name="id2" value="">
			<input type="hidden" name="id3" value="">
			<input type="hidden" name="id4" value="">
			<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
			<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
			<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
			<input type="hidden" name="APROBAR" value='<c:out value="${paramsVO.CMD_APROBAR_CAMBIO}"/>'>
			<input type="hidden" name="RECHAZAR" value='<c:out value="${paramsVO.CMD_RECHAZAR_CAMBIO}"/>'>
			<input type="hidden" name="REALIZAR" value='<c:out value="${paramsVO.CMD_REALIZAR_CAMBIO}"/>'>
			<input type="hidden" name="guia" value="<c:out value="${requestScope.guia}"/>">
			<input type="hidden" name="filInstitucion" value='<c:out value="${sessionScope.login.instId}"/>'>
			<input type="hidden" name="filEstado" value=''>
			<input type="hidden" name="filNivel" value=''>
			<input type="hidden" name="filFechaSol" value=''>
			
			<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
				<tr height="1">
					<td width="10">&nbsp;</td>
					<td rowspan="2" width="469">
						<img src='<c:url value="/etc/img/tabs/aprobarCambios_1.gif"/>' alt="Aprobar Cambios POA" height="26" border="0">					
						<img src='<c:url value="/etc/img/tabs/realizarCambio_0.gif"/>' alt="Realizar Cambio POA" height="26" border="0">
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
			  		<!-- <th><span class="estadoPOA">Estado del POA: <c:out value="${sessionScope.filtroCambioVO.filEstado}"/></span></th> -->
			 	</tr>	
			</table>
			
			<table border="0" align="center" width="95%">
				<tr>
					<td width="15%"><span class="style2">*</span> Vigencia:</td>
					<td>
						<select name="filVigencia" style="width:180px" onchange="javaScript:inhabilitar()">
							<option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
								<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.filtroCambioVO.filVigencia}">selected</c:if>><c:out value="${vig.nombre}"/></option>
							</c:forEach>
						</select>
					</td>					
				</tr>
				<tr>
					<td><span class="style2">*</span> Localidad:</td>
					<td>
						<select name="filLoc" style="width:280px" onchange="javaScript:ajaxColegio()" <c:if test="${sessionScope.login.perfil==250}">disabled="disabled"</c:if>>
							<option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaLocalidades}" var="vig">
								<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.filtroCambioVO.filLoc}">selected</c:if>><c:out value="${vig.nombre}"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td><span class="style2">*</span> Colegio:</td>
					<td>
						<select name="filEntidad" style="width:500px">
							<option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaColegios}" var="vig">
								<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.filtroCambioVO.filEntidad}">selected</c:if>><c:out value="${vig.nombre}"/></option>
							</c:forEach>
						</select>
					</td>				
				</tr>
				<tr>
					<td>Observación por Rechazo:</td>
					<td colspan="3">
						<textarea name="filObservacion" cols="110" rows="2" onKeyDown="textCounter(this,500,500);" onKeyUp="textCounter(this,500,500);" ></textarea>
					</td>
				</tr>
				<tr style="display:none">
					<td>
						<iframe name="frameAjax0" id="frameAjax0"></iframe>
					</td>
				</tr>
			</table>
			<br>
		 	
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Listado de Cambios</caption>
		 	<c:if test="${empty requestScope.listaCambios}">
				<tr>
					<th class="Fila1" colspan='6'>No hay solicitudes de cambios</th>
				</tr>
			</c:if>
			<c:if test="${!empty requestScope.listaCambios}">
				<tr>
					<th width='20' class="EncabezadoColumna" colspan="1">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Fecha Solicitud</td>
					<td class="EncabezadoColumna" align="center">Asunto</td>
					<td class="EncabezadoColumna" align="center">Estado</td>
					<td class="EncabezadoColumna" align="center">Aprobar</td>		
					<td class="EncabezadoColumna" align="center">Rechazar</td>			
				</tr>
				<c:forEach begin="0" items="${requestScope.listaCambios}" var="lista" varStatus="st">
					<tr>						
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${lista.vigencia}"/>,<c:out value="${lista.nivel}"/>,<c:out value="${lista.entidad}"/>,"<c:out value="${lista.fechaSol_}"/>");'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>							
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.fechaSol}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.asunto}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.nombreEstado}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center">
						<c:if test="${lista.estado==0}">
							<input name="cmd1" type="button" value="Aprobar" onClick='aprobar(<c:out value="${lista.vigencia}"/>,<c:out value="${lista.nivel}"/>,<c:out value="${lista.entidad}"/>,"<c:out value="${lista.fechaSol_}"/>");' class="boton">
						</c:if>
						<c:if test="${lista.estado==1}">
							<input name="cmd1" type="button" value="Realizar" onClick='cambio(<c:out value="${lista.vigencia}"/>,<c:out value="${lista.nivel}"/>,<c:out value="${lista.entidad}"/>,"<c:out value="${lista.fechaSol_}"/>");' class="boton">
						</c:if>
						<c:if test="${lista.estado==2}">
							&nbsp;
						</c:if>
						</td>												
						<td class='Fila<c:out value="${st.count%2}"/>' align="center">
						<c:if test="${lista.estado==0}">
							<input name="cmd2" type="button" value="Rechazar" onClick='rechazar(<c:out value="${lista.vigencia}"/>,<c:out value="${lista.nivel}"/>,<c:out value="${lista.entidad}"/>,"<c:out value="${lista.fechaSol_}"/>");' class="boton">
						</c:if>
						<c:if test="${lista.estado==1}">
							&nbsp;
						</c:if>
						<c:if test="${lista.estado==2}">
							&nbsp;
						</c:if>
						</td>
						
						
					</tr>
				</c:forEach>
			</c:if>
		</table>
		
	</form>
</body>
</html>
