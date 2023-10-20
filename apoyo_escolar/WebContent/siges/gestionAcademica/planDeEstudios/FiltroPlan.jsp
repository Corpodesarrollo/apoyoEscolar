<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroPlanDeEstudiosVO" class="siges.gestionAcademica.planDeEstudios.vo.FiltroPlanDeEstudiosVO" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.gestionAcademica.planDeEstudios.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	function lanzar(tipo){
  	document.frmFiltro.tipo.value=tipo;
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function hacerValidaciones_frmFiltro(forma){
	}
	
	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;
			document.frmFiltro.submit();
		}
	}
	
	function editar(o){
		if(document.frmFiltro.id){
				document.frmFiltro.id[0].value=document.frmFiltro.filInstitucion.value;
				document.frmFiltro.id[1].value=o;
				document.frmFiltro.id[2].value=document.frmFiltro.filVigencia.value;
				document.frmFiltro.cmd.value=document.frmFiltro.EDITAR.value;
				document.frmFiltro.submit();
		}
	}

	function eliminar(o){
		if(confirm('¿Desea eliminar el plan de estudios?')){
			if(document.frmFiltro.id){
					document.frmFiltro.id[0].value=document.frmFiltro.filInstitucion.value;
					document.frmFiltro.id[1].value=o;
					document.frmFiltro.id[2].value=document.frmFiltro.filVigencia.value;
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
	<form action='<c:url value="/siges/gestionAcademica/planDeEstudios/SavePlan.jsp"/>' method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_PLAN}"/>'>
		<input type="hidden" name="cmd" value=''>
		<c:forEach begin="0" end="4" var="i"><input type="hidden" name="id"></c:forEach>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="filInstitucion" value='<c:out value="${sessionScope.filtroPlanDeEstudiosVO.filInstitucion}"/>'>
		<input type="hidden" name="filVigencia" value='<c:out value="${sessionScope.filtroPlanDeEstudiosVO.filVigencia}"/>'>

		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<img src='<c:url value="/etc/img/tabs/plan_de_estudios_1.gif"/>' alt="Plan" height="26" border="0">
					<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_AREA}"/>)"><img src='<c:url value="/etc/img/tabs/areas_0.gif"/>' alt="Area" height="26" border="0"></a>
					<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_ASIGNATURA}"/>)"><img src='<c:url value="/etc/img/tabs/asignatura_0.gif"/>' alt="Asignatura" height="26" border="0"></a>
				</td>
			</tr>
		</table>
		<!-- TABLA DE LOS RESULTADOS DE LA BUSQUEDA -->
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Listado de planes de estudio</caption>
		 	<c:if test="${empty requestScope.listaPlanDeEstudios}"><tr><th class="Fila1" colspan='6'>No hay planes de estudio</th></tr></c:if>
			<c:if test="${!empty requestScope.listaPlanDeEstudios}">
				<tr>
					<th width='20' class="EncabezadoColumna" >&nbsp;</th>
					<th width='30' class="EncabezadoColumna" >&nbsp;</th>
					<td width="25%" class="EncabezadoColumna" align="center">Metodologia</td>
					<td width="60%" class="EncabezadoColumna" align="center">Criterio</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaPlanDeEstudios}" var="lista" varStatus="st">
					<tr>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${st.count}"/></td>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${lista.plaMetodologia}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
							<c:if test="${sessionScope.NivelPermiso==2}"><a href='javaScript:eliminar(<c:out value="${lista.plaMetodologia}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaMetodologiaNombre}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaCriterio}"/></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>