<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="parametros.calendario.vo.ParamsVO" scope="page"/>
<jsp:useBean id="filtroCalendarioVO" class="parametros.calendario.vo.FiltroCalendarioVO" scope="session"/>
<html>
<head>
<script language="javaScript">
	function lanzar(dato){
		document.frmCambiar.dato.value=dato;	
		document.frmCambiar.action="<c:url value="/datoMaestro/DatoMaestroSeleccionEdit.do"/>";
		document.frmCambiar.submit();
	}	
	function buscarCol(){
		document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;
		document.frmFiltro.submit();
	}	
	function editar(n){	
		document.frmFiltro.fechaEditar.value=n;
		document.frmFiltro.cmd.value=document.frmFiltro.EDITAR.value;
		document.frmFiltro.submit();
	}
</script>
</head>
<body>
	<form action='<c:url value="/parametros/calendario/CalendarioFiltro.do"/>' method="post" name="frmReporte" target="_blank">
			<input type="hidden" name="cmd" value=''><input type="hidden" name="ext" value='1'>
			<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
			<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
	</form>
	
	<form method="post" name="frmCambiar" action="<c:url value="/datoMaestro/DatoMaestroSeleccionEdit.do"/>">
	<input type="hidden" name="dato" value='65'>
		<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr height="1">
				<td width="8"  wbgcolor="#FFFFFF">&nbsp;</td>			
				<td  width="650px" rowspan="2" bgcolor="#FFFFFF"><a href="javaScript:lanzar(2)"><img src="/apoyo_escolar/etc/img/tabs/tipos_documento_0.gif" alt="Tipos de documento"  height="26" border="0"></a><a href="javaScript:lanzar(25)"><img src="/apoyo_escolar/etc/img/tabs/tipos_propiedad_0.gif" alt="Tipos de Propiedad"  height="26" border="0"></a><a href="javaScript:lanzar(6)"><img src="/apoyo_escolar/etc/img/tabs/tipos_espacio_fisico_0.gif" alt="Tipos de Espacio F&iacute;sico"  height="26" border="0"></a><a href="javaScript:lanzar(11)"><img src="/apoyo_escolar/etc/img/tabs/tipos_ocupantes_0.gif" alt="Tipos de Ocupante"  height="26" border="0" usemap="#ipoi"></a><a href="javaScript:lanzar(19)"><img src="/apoyo_escolar/etc/img/tabs/tipos_programa_0.gif" alt="Tipos de Programas"  height="26" border="0"></a><a href="javaScript:lanzar(8)"><img src="/apoyo_escolar/etc/img/tabs/tipos_reconocimiento_0.gif" alt="Tipos de Reconocimiento"  height="26" border="0"></a><a href="javaScript:lanzar(14)"><img src="/apoyo_escolar/etc/img/tabs/tipos_convivencia_0.gif" alt="Tipos de Convivencia"  height="26" border="0"></a><a href="javaScript:lanzar(18)"><img src="/apoyo_escolar/etc/img/tabs/motivos_ausencia_0.gif" alt="Motivos de Ausencia"  height="26" border="0"></a><a href="javaScript:lanzar(17)"><img src="/apoyo_escolar/etc/img/tabs/etnias_0.gif" alt="Etnias"  height="26" border="0"></a><a href="javaScript:lanzar(13)"><img src="/apoyo_escolar/etc/img/tabs/condiciones_0.gif" alt="Condici&oacute;nes"  height="26" border="0"></a><a href="javaScript:lanzar(44)"><img src="/apoyo_escolar/etc/img/tabs/rango_tarifa_0.gif" alt="Rangos de Tarifa"  height="26" border="0"></a><a href="javaScript:lanzar(47)"><img src="/apoyo_escolar/etc/img/tabs/cargo_gobierno_escolar_0.gif" alt="Cargos de Gobierno Escolar"  height="26" border="0"></a><img src="/apoyo_escolar/etc/img/tabs/tipo_escala_valorativa_0.gif" alt="Tipos de Escala Valorativa" width="84"  height="26" border="0"><a href="javaScript:lanzar(50)"><img src="/apoyo_escolar/etc/img/tabs/tipos_receso_0.gif" alt="Tipos de Receso"  height="26" border="0"></a><a href="javaScript:lanzar(49)"><img src="/apoyo_escolar/etc/img/tabs/tipos_atencion_especial_0.gif" alt="Tipos de Atención Especial"  height="26" border="0"></a><a href="javaScript:lanzar(65)"><img src="/apoyo_escolar/etc/img/tabs/calendario_1.gif" alt="Calendario"  height="26" border="0"></a></td>
			</tr>
  		</table>
	</form>
	
	<form action='<c:url value="/parametros/calendario/Save.jsp"/>' method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="fechaEditar" value='ninguna'>
		
		
		
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
				<td><span class="style2">*</span> Mes:</td>
				<td>
					<select name="mes" style="width:100px">
						<c:forEach begin="0" items="${requestScope.listaMeses}" var="vig">
							<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.filtroCalendarioVO.mes}">selected</c:if>><c:out value="${vig.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Listado de calendarios</caption>
		 	<c:if test="${empty requestScope.listaCalendario}">
				<tr>
					<th class="Fila1" colspan='6'>No hay calendarios</th>
				</tr>
			</c:if>
			<c:if test="${!empty requestScope.listaCalendario}">
				<tr>
					<td class="EncabezadoColumna" align="center">Fecha</td>
					<td class="EncabezadoColumna" align="center">Motivo</td>
					<td class="EncabezadoColumna" align="center">Acción</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaCalendario}" var="lista" varStatus="st">
					<tr>
						<td>
							<c:out value="${lista.fecha}"/>
						</td>
						<td>
							<c:out value="${lista.motivo}"/>
						</td>
						<td align="center">
							<input name="cmdEditar" type="button" value="Editar" onClick="editar('<c:out value="${lista.fecha}"/>')" class="boton">
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>