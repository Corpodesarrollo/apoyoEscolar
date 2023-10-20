<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroAreaPlanVO" class="siges.gestionAcademica.planDeEstudios.vo.FiltroAreaVO" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.gestionAcademica.planDeEstudios.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	function lanzar(tipo){
  	    document.frmFiltro.tipo.value=tipo;
  	    document.frmFiltro.cmd.value=-1;
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function hacerValidaciones_frmFiltro(forma){
	    validarLista(forma.filVigencia, "- Vigencia");
		validarLista(forma.filMetodologia, "- Metodologia");
	}
	
	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;
			document.frmFiltro.submit();
		}
	}
	
	function editar(m,n){
		if(document.frmFiltro.id){
				document.frmFiltro.id[0].value=document.frmFiltro.filInstitucion.value;
				document.frmFiltro.id[1].value=m;//met
				document.frmFiltro.id[2].value=document.frmFiltro.filVigencia.value;
				document.frmFiltro.id[3].value=n;//cod
				document.frmFiltro.cmd.value=document.frmFiltro.EDITAR.value;
				document.frmFiltro.submit();
		}
	}

	function eliminar(m,n){
		if(confirm('¿Desea eliminar el área?')){
			if(document.frmFiltro.id){
				document.frmFiltro.id[0].value=document.frmFiltro.filInstitucion.value;
				document.frmFiltro.id[1].value=m;//met
				document.frmFiltro.id[2].value=document.frmFiltro.filVigencia.value;
				document.frmFiltro.id[3].value=n;//cod
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
	<form action='<c:url value="/siges/gestionAcademica/planDeEstudios/SaveArea.jsp"/>' method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_AREA}"/>'>
		<input type="hidden" name="cmd" value=''>
		<c:forEach begin="0" end="4" var="i"><input type="hidden" name="id"></c:forEach>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="filInstitucion" value='<c:out value="${sessionScope.filtroAreaPlanVO.filInstitucion}"/>'>
		<!-- <input type="hidden" name="filVigencia" value='<c:out value="${sessionScope.filtroAreaPlanVO.filVigencia}"/>'> -->

		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_PLAN}"/>)"><img src='<c:url value="/etc/img/tabs/plan_de_estudios_0.gif"/>' alt="Plan" height="26" border="0"></a>
					<img src='<c:url value="/etc/img/tabs/areas_1.gif"/>' alt="Area" height="26" border="0">
					<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_ASIGNATURA}"/>)"><img src='<c:url value="/etc/img/tabs/asignatura_0.gif"/>' alt="Asignatura" height="26" border="0"></a>
				</td>
			</tr>
		</table>

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
				<td><span class="style2">*</span> Vigencia:</td>
				<td>
					<select name=filVigencia style="width:120px">
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaVigencia}" var="metod">
							 <option value="<c:out value="${metod.codigo}"/>"     <c:if test="${ ( sessionScope.filtroAreaPlanVO.filVigencia > 0 and metod.codigo == sessionScope.filtroAreaPlanVO.filVigencia ) or (sessionScope.filtroAreaPlanVO.filVigencia == 0 and  metod.codigo == sessionScope.areaPlanVO.areVigencia) }">selected</c:if>   ><c:out value="${metod.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2">*</span> Metodologia:</td>
				<td>
					<select name="filMetodologia" style="width:120px">
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaMetodologia}" var="metod">
							<option value="<c:out value="${metod.codigo}"/>" <c:if test="${metod.codigo==sessionScope.filtroAreaPlanVO.filMetodologia}">selected</c:if>><c:out value="${metod.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table><br> 	
		<!-- TABLA DE LOS RESULTADOS DE LA BUSQUEDA -->
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Listado de áreas</caption>
		 	<c:if test="${empty requestScope.listaArea}"><tr><th class="Fila1" colspan='6'>No hay áreas</th></tr></c:if>
			<c:if test="${!empty requestScope.listaArea}">
				<tr>
					<th width='5%' class="EncabezadoColumna">&nbsp;</th>
					<th width='8%' class="EncabezadoColumna">&nbsp;</th>
					<td width='50%' class="EncabezadoColumna" align="center">Nombre</td>
					<td width='30%' class="EncabezadoColumna" align="center">Abreviatura</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaArea}" var="lista" varStatus="st">
					<tr>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${st.count}"/></td>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${lista.areMetodologia}"/>,<c:out value="${lista.areCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
							<c:if test="${sessionScope.NivelPermiso==2}"><a href='javaScript:eliminar(<c:out value="${lista.areMetodologia}"/>,<c:out value="${lista.areCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.areNombre}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.areAbreviatura}"/></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>