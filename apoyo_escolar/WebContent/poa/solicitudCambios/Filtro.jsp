<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="filtroCambioVO" class="poa.solicitudCambios.vo.FiltroCambiosVO" scope="session"/>
<jsp:useBean id="paramsVO" class="poa.solicitudCambios.vo.ParamsVO" scope="page"/>

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
				validarLista(forma.filVigencia, "- Vigencia");
			}
	
			function buscar() {
				if(validarForma(document.frmFiltro)) {
					document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;
					document.frmFiltro.submit();
				}
			}
			
			function aprobar() {
				if(validarForma(document.frmFiltro)) {
					if(confirm("¿Confirma el envio del POA? \n Nota: Una vez enviado no se podrá agregar o editar actividades")) {
						document.frmFiltro.cmd.value=document.frmFiltro.APROBAR.value;
						document.frmFiltro.submit();
					}	
				}
			}
	
			function editar(n,m,o,p) {
				if(document.frmFiltro.id) {
					document.frmFiltro.id.value=n;
					document.frmFiltro.id2.value=m;
					document.frmFiltro.id3.value=o;
					document.frmFiltro.id4.value=p;
					document.frmFiltro.cmd.value=document.frmFiltro.EDITAR.value;				
					document.frmFiltro.submit();
				}
			}
		
			function eliminar(n,m,o,p) {
				if(confirm('¿desea eliminar el cambio?')) {
					if(document.frmFiltro.id) {
						document.frmFiltro.id.value=n;
						document.frmFiltro.id2.value=m;
						document.frmFiltro.id3.value=o;
						document.frmFiltro.id4.value=p;
						document.frmFiltro.cmd.value=document.frmFiltro.ELIMINAR.value;
						document.frmFiltro.submit();
					}
				}
			}
	
			function inhabilitar() {
				if(document.getElementById("cmdPOA")){
					document.getElementById("cmdPOA").style.display='none';
				}
			}
		//-->
		</script>
		
	</head>
	
	<c:import url="/mensaje.jsp"/>
	
	<body onLoad="mensaje(document.getElementById('msg'));">
	
		<form action='<c:url value="/poa/solicitudCambios/Save.jsp"/>' method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		
			<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_CAMBIO}"/>'>
			<input type="hidden" name="cmd" value=''>
			<input type="hidden" name="id" value="">
			<input type="hidden" name="id2" value="">
			<input type="hidden" name="id3" value="">
			<input type="hidden" name="id4" value="">
			<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
			<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
			<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
			<input type="hidden" name="APROBAR" value='<c:out value="${paramsVO.CMD_APROBAR_COLEGIO}"/>'>
			<input type="hidden" name="guia" value="<c:out value="${requestScope.guia}"/>">
			<input type="hidden" name="filInstitucion" value='<c:out value="${sessionScope.login.instId}"/>'>
			
			<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
				<tr height="1">
					<td width="10">&nbsp;</td>
					<td rowspan="2" width="469">
						<img src='<c:url value="/etc/img/tabs/cambio_1.gif"/>' alt="Solicitud Cambios POA" height="26" border="0">					
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
			  		<th><span class="estadoPOA">Estado del POA: <c:out value="${sessionScope.filtroCambioVO.filEstado}"/></span></th>
			 	</tr>	
			</table>
			
			<table border="0" align="center" width="95%">
				<tr>
					<td><span class="style2">*</span> Vigencia:</td>
					<td>
						<select name="filVigencia" style="width:120px" onchange="javaScript:inhabilitar()">
							<option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
								<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.filtroCambioVO.filVigencia}">selected</c:if>><c:out value="${vig.nombre}"/></option>
							</c:forEach>
						</select>
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
						<td class="EncabezadoColumna" align="center">Fecha Estado</td>					
					</tr>
					<c:forEach begin="0" items="${requestScope.listaCambios}" var="lista" varStatus="st">
						<tr>						
							<th class='Fila<c:out value="${st.count%2}"/>'>
								<a href='javaScript:editar(<c:out value="${lista.vigencia}"/>,<c:out value="${lista.nivel}"/>,<c:out value="${lista.entidad}"/>,"<c:out value="${lista.fechaSol_}"/>");'>
									<img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'>
								</a>
								<c:if test="${sessionScope.NivelPermiso==2}">
									<a href='javaScript:eliminar(<c:out value="${lista.vigencia}"/>,<c:out value="${lista.nivel}"/>,<c:out value="${lista.entidad}"/>,"<c:out value="${lista.fechaSol_}"/>");'>
										<img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'>
									</a>
								</c:if>
							</th>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.fechaSol}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.asunto}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.nombreEstado}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.fechaEstado}"/></td>						
						</tr>
					</c:forEach>
				</c:if>
			</table>
			
		</form>
	
	</body>

</html>
