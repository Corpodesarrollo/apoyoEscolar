<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroItemsVO" class="siges.gestionAdministrativa.agenda.vo.FiltroTareasVO" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.gestionAdministrativa.agenda.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<script language="javaScript">

	function lanzar(tipo){
  	    document.frmFiltro.tipo.value=tipo;
  	    document.frmFiltro.cmd.value=-1;
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function hacerValidaciones_frmFiltro(forma){
		validarLista(forma.metodologia, "- Metodologia", 1);
		validarLista(forma.grado, "- Grado", 1);
		validarLista(forma.grupo, "- Grupo", 1);
	}

	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;
			document.frmFiltro.submit();
		}
	}
	
	function editar(m){
		document.frmFiltro.cmd.value=document.frmFiltro.EDITAR.value;
		document.frmFiltro.tipo.value=m;
		document.frmFiltro.submit();
	}

	
	//Cambio metodologia	
	function filtroMetodologia1(){
		document.frmAjax1.ajax[0].value=document.frmFiltro.metodologia.value;
		alert(document.frmFiltro.metodologia.value);
		if(parseInt(document.frmAjax1.ajax[0].value)>0){
			document.frmAjax1.cmd.value=document.frmFiltro.GRADO.value;
			document.frmAjax1.tipo.value=document.frmFiltro.AJAX_INSTANCIA0.value;
	 		document.frmAjax1.target="frameAjax1";
			document.frmAjax1.submit();
		}
	}
	
	//Cambio grado	
	function filtroGrado1(){
		document.frmAjax1.ajax[0].value=document.frmFiltro.metodologia.value;
		document.frmAjax1.ajax[1].value=document.frmFiltro.grado.value;
		if(parseInt(document.frmAjax1.ajax[0].value)>0){
			document.frmAjax1.cmd.value=document.frmFiltro.GRUPO.value;
	 		document.frmAjax1.target="frameAjax1";
			document.frmAjax1.submit();
		}
	}
	
	//Cambio Grupo	
	function filtroGrupo1(){
		document.frmAjax1.ajax[0].value=document.frmFiltro.metodologia.value;
		if(parseInt(document.frmAjax1.ajax[0].value)>0){
			document.frmAjax1.cmd.value=document.frmFiltro.ASIGNATURA.value;
	 		document.frmAjax1.target="frameAjax1";
			document.frmAjax1.submit();
		}
	}
			
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form method="post" name="frmAjax1" action='<c:url value="/siges/gestionAdministrativa/Ajax.do"/>'>
		<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">
	</form>
	<form action='<c:url value="/siges/gestionAdministrativa/agenda/tareas/Save.jsp"/>' method="post" name="frmFiltro" >
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="cmd"  value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="AJAX_INSTANCIA0" value='<c:out value="${paramsVO.CMD_AJAX_INSTANCIA0}"/>'> 
		<input type="hidden" name="GRADO" value='<c:out value="${paramsVO.CMD_AJAX_GRAD}"/>'> 
		<input type="hidden" name="GRUPO" value='<c:out value="${paramsVO.CMD_AJAX_GRUP}"/>'> 
		<input type="hidden" name="ASIGNATURA" value='<c:out value="${paramsVO.CMD_AJAX_AREA}"/>'> 
		
		<input type="hidden" name="tipo" value='0'>

		<table border="0" align="center" width="95%">
		 	<caption>Filtro de búsqueda</caption>
			<tr>
				<td>
 						<input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
		  		</td>
		 	</tr>	
		 	<tr style="display:none"><td><iframe name="frameAjax1" id="frameAjax1"></iframe></td></tr>
		</table>
	

	<TABLE width="90%" cellpadding="2" cellSpacing="2" align="center">
		<tr>	
		<td><span class="style2" >*</span>Metodolog&iacute;a:</td>
		<td>
      	<select name="metodologia" style='width:180px;' onChange='filtroMetodologia()'>
	        <option value='-9'>-- Seleccione una --</option>
	        <c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="niv">
					<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.filtroItemsVO.metodologia}">selected</c:if>><c:out value="${niv.nombre}"/></option>
	        </c:forEach>
        </select>
      </td>
     
			<td><span class="style2" >*</span>Grado:</td>
			<td>
      	<select name="grado"  style='width:120px;' onChange='filtroGrado()'>
				<option value='-9'>-- Seleccione una --</option>
				<c:forEach begin="0" items="${requestScope.listaGrado}" var="niv">
				<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.filtroItemsVO.grado}">SELECTED</c:if>><c:out value="${niv.nombre}"/></option>
				</c:forEach>
        </select>
      </td>
		 </tr>
      <tr>
			<td>Grupo:</td>
			<td>
	        	<select name="grupo"  style='width:180px;' onChange='filtroGrupo()'>
					<option value='-9'>-- Seleccione uno --</option>
					<c:forEach begin="0" items="${requestScope.listaGrupo}" var="niv">
						<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.filtroItemsVO.grupo}">SELECTED</c:if>><c:out value="${niv.nombre}"/></option>
					</c:forEach>
        		</select>
	      </td>

		<td>Asignatura:</td>
		<td>
        	<select name="asignaturas"  style='width:120px;'>
				<option value='-9'>-- Seleccione uno --</option>
				<c:forEach begin="0" items="${requestScope.listaAsignaturas}" var="niv">
					<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.filtroItemsVO.asignaturas}">SELECTED</c:if>><c:out value="${niv.nombre}"/></option>
				</c:forEach>
       		</select>
        </td>
	 </tr>
	 
	 <tr>
		<td>Estado:</td>
		<td>
	       	<select name="estado"  style='width:180px;'>
				<option value='0'>Todas --</option>
				<option value='1'>Pendientes --</option>
				<option value='2'>Entregadas --</option>
	      		</select>
	       </td>
	 </tr>
	 </TABLE>
	 
		
		
		<br> 	
		<!-- TABLA DE LOS RESULTADOS DE LA BUSQUEDA -->
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Listado de tareas</caption>
		 	<c:if test="${empty requestScope.listaTareas}"><tr><th class="Fila1" colspan='6'>No hay tareas</th></tr></c:if>
			<c:if test="${!empty requestScope.listaTareas}">
				<tr>
					<td width='5%' class="EncabezadoColumna" align="center">Acción</td>
					<td width='10%' class="EncabezadoColumna" align="center">Asignatura</td>
					<td width='30%' class="EncabezadoColumna" align="center">Tarea</td>
					<td width='30%' class="EncabezadoColumna" align="center">Fecha de entrega</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaTareas}" var="listaTareas" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${listaTareas.codigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
						</th>
						<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;<c:out value="${listaTareas.asignatura}"/>&nbsp;</th>
						<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;<c:out value="${listaTareas.tarea}"/>&nbsp;</th>
						<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;<c:out value="${listaTareas.fecha}"/>&nbsp;</th>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
</html>
<script>
	document.frmFiltro.metodologia.onchange();
	document.frmFiltro.grado.onchange();
</script>