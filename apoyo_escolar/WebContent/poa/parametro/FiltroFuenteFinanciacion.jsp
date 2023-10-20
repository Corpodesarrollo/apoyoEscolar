<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="poa.parametro.vo.ParamsVO" scope="page"/>
<html>
<head>
<c:import url="/parametros.jsp"/>
<script language="javaScript">
	<!--
	function lanzar(tipo){
  	document.frmFiltro.tipo.value=tipo;
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function hacerValidaciones_frmFiltro(forma){
		//validarLista(forma.filVigencia, "- Vigencia");
	}
	
	/*
	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;
			document.frmFiltro.submit();
		}
	}
	*/
	
	function editar(n){
		if(document.frmFiltro.id){
				document.frmFiltro.id[0].value=n;
				document.frmFiltro.cmd.value=document.frmFiltro.EDITAR.value;
				document.frmFiltro.submit();
		}
	}

	function eliminar(n){
		if(confirm('¿Desea eliminar la información?')){
			if(document.frmFiltro.id){
					document.frmFiltro.id[0].value=n;
					document.frmFiltro.cmd.value=document.frmFiltro.ELIMINAR.value;
					document.frmFiltro.submit();
			}
		}
	}
	
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form action='<c:url value="/poa/parametro/SaveFuenteFinanciacion.jsp"/>' method="post" name="frmFiltro">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_FUENTE_FINANCIACION}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="id" value=""><input type="hidden" name="id" value="">
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="guia" value='<c:out value="${requestScope.guia}"/>'>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_AREA_GESTION}"/>)"><img src='<c:url value="/etc/img/tabs/area_gestion_0.gif"/>' alt="Area de gestion" height="26" border="0"></a>
					<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_LINEA_ACCION}"/>)"><img src='<c:url value="/etc/img/tabs/linea_accion_0.gif"/>' alt="Linea de accion" height="26" border="0"></a>
					<img src='<c:url value="/etc/img/tabs/fuente_financiacion_1.gif"/>' alt="Fuente de financiacion" height="26" border="0">
					<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_TIPO_META}"/>)"><img src='<c:url value="/etc/img/tabs/tipo_meta_0.gif"/>' alt="Tipo de meta" height="26" border="0"></a>
					<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_UNIDAD_MEDIDA}"/>)"><img src='<c:url value="/etc/img/tabs/unidad_medida_0.gif"/>' alt="Unidad de medida" height="26" border="0"></a>
					<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_PROGRAMACION_FECHAS}"/>)"><img src='<c:url value="/etc/img/tabs/programacion_fechas_0.gif"/>' alt="Programación de fechas" height="26" border="0"></a>
				   <a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_VIG}"/>)"><img src='<c:url value="/etc/img/tabs/poa_vigencia_0.gif"/>' alt="Vigencia" height="26" border="0"></a>
				</td>
			</tr>
		</table>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Lista de resultados</caption>
		 	<c:if test="${empty requestScope.listaFuenteFinanciacion}">
				<tr>
					<th class="Fila1" colspan='6'>No hay resultados</th>
				</tr>
			</c:if>
			<c:if test="${!empty requestScope.listaFuenteFinanciacion}">
				<tr>
					<th width='20' class="EncabezadoColumna">&nbsp;</th>
					<td class="EncabezadoColumna" align="center">Código</td>
					<td class="EncabezadoColumna" align="center">Nombre</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaFuenteFinanciacion}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${lista.fueCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
							<c:if test="${sessionScope.NivelPermiso==2}"><a href='javaScript:eliminar(<c:out value="${lista.fueCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.fueCodigo}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.fueNombre}"/></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>