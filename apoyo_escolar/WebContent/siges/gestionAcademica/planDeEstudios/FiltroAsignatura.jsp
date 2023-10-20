<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroAsignaturaPlanVO" class="siges.gestionAcademica.planDeEstudios.vo.FiltroAsignaturaVO" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.gestionAcademica.planDeEstudios.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">
<!--
	function lanzar(tipo){
  		document.frmFiltro.tipo.value=tipo;
		document.frmFiltro.cmd.value = -1;
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function hacerValidaciones_frmFiltro(forma){
	  
		validarLista(forma.filVigencia, "- Vigencia",1);
		validarLista(forma.filMetodologia, "- Metodologia");
		validarLista(forma.filArea, "- Area");
	}
	
	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.id[0].value=document.frmFiltro.filInstitucion.value;
			document.frmFiltro.id[1].value=document.frmFiltro.filMetodologia.value;//met
			document.frmFiltro.id[2].value=document.frmFiltro.filVigencia.value;
			document.frmFiltro.id[3].value=document.frmFiltro.filArea.value;//cod
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
		if(confirm('¿Desea eliminar la asignatura?')){
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
	
	function ajaxArea0(){
		borrar_combo(document.frmFiltro.filArea); 
		document.frmAjax0.ajax[0].value=document.frmFiltro.filInstitucion.value;
		document.frmAjax0.ajax[1].value=document.frmFiltro.filMetodologia.value;
		document.frmAjax0.ajax[2].value=document.frmFiltro.filVigencia.value;
		if(parseInt(document.frmAjax0.ajax[1].value)!=-99){
			document.frmAjax0.cmd.value=document.frmFiltro.AJAX_AREA0.value;
	 		document.frmAjax0.target="frameAjax0";
			document.frmAjax0.submit();
		}
	}
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmAjax0" action='<c:url value="/planDeEstudios/Ajax.do"/>'>
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ASIGNATURA}"/>'>
		<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
		<c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach>
	</form>
	<form action='<c:url value="/siges/gestionAcademica/planDeEstudios/SaveAsignatura.jsp"/>' method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		<input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ASIGNATURA}"/>'>
		<input type="hidden" name="cmd" value=''>
		<c:forEach begin="0" end="4" var="i"><input type="hidden" name="id"></c:forEach>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="AJAX_AREA0" value='<c:out value="${paramsVO.CMD_AJAX_AREA0}"/>'>
		<input type="hidden" name="filInstitucion" value='<c:out value="${sessionScope.filtroAsignaturaPlanVO.filInstitucion}"/>'>
		 

		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_PLAN}"/>)"><img src='<c:url value="/etc/img/tabs/plan_de_estudios_0.gif"/>' alt="Plan" height="26" border="0"></a>
					<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_AREA}"/>)"><img src='<c:url value="/etc/img/tabs/areas_0.gif"/>' alt="Area" height="26" border="0"></a>
					<img src='<c:url value="/etc/img/tabs/asignatura_1.gif"/>' alt="Asignatura" height="26" border="0">
				</td>
			</tr>
		</table>
		<table border="0" align="center" width="95%">
		 	<caption>Filtro de búsqueda</caption>
			<tr><td><input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton"></td></tr>
		</table>
		<table border="0" align="center" width="95%">
			<tr>
				<td width="20%"><span class="style2">*</span>Vigencia:</td>
				<td width="30%">
					<select name="filVigencia"  style="width:120px;" >
						<option value="-99" selected>--Seleccione uno--</option>
				   
				      <c:forEach begin="0" items="${requestScope.listaVigencia}" var="metod">
					     <option value="<c:out value="${metod.codigo}"/>" <c:if test="${   metod.codigo == sessionScope.filtroAsignaturaPlanVO.filVigencia  }">selected</c:if>     <c:if test="${ ( sessionScope.filtroAsignaturaPlanVO.filVigencia > 0 and metod.codigo == sessionScope.filtroAsignaturaPlanVO.filVigencia ) or (sessionScope.filtroAsignaturaPlanVO.filVigencia == 0 and  metod.codigo == sessionScope.asignaturaPlanVO.asiVigencia) }">selected</c:if>   ><c:out value="${metod.nombre}"/></option>
					  </c:forEach>
				    </select>
				</td>
				<td><span class="style2">*</span>Metodologia:</td>
				<td>
					<select name="filMetodologia" style="width:150px" onchange="javaScript:ajaxArea0()">
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaMetodologia}" var="metod">
							<option value="<c:out value="${metod.codigo}"/>" <c:if test="${metod.codigo==sessionScope.filtroAsignaturaPlanVO.filMetodologia}">selected</c:if>><c:out value="${metod.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
				</tr>
				<tr>
				<td><span class="style2">*</span> Área:</td>
				<td colspan="3">
					<select name="filArea" style="width:250px">
						<option value="-99" selected>--Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.listaAreaBase0}" var="are">
							<option value="<c:out value="${are.codigo}"/>" <c:if test="${are.codigo==sessionScope.filtroAsignaturaPlanVO.filArea}">selected</c:if>><c:out value="${are.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr style="display:none"><td><iframe name="frameAjax0" id="frameAjax0"></iframe></td></tr>
		</table><br> 	
		<!-- TABLA DE LOS RESULTADOS DE LA BUSQUEDA -->
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Listado de asignaturas</caption>
		 	<c:if test="${empty requestScope.listaAsignatura}"><tr><th class="Fila1" colspan='6'>No hay asignaturas</th></tr></c:if>
			<c:if test="${!empty requestScope.listaAsignatura}">
				<tr>
					<th width='5%' class="EncabezadoColumna">&nbsp;</th>
					<th width='8%' class="EncabezadoColumna">&nbsp;</th>
					<td width='50%' class="EncabezadoColumna" align="center">Nombre</td>
					<td width='30%' class="EncabezadoColumna" align="center">Abreviatura</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaAsignatura}" var="lista" varStatus="st">
					<tr>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${st.count}"/></td>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${lista.asiMetodologia}"/>,<c:out value="${lista.asiCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
							<c:if test="${sessionScope.NivelPermiso==2}"><a href='javaScript:eliminar(<c:out value="${lista.asiMetodologia}"/>,<c:out value="${lista.asiCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.asiNombre}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.asiAbreviatura}"/></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>