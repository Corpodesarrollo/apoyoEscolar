<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroItemsVO" class="siges.gestionAdministrativa.agenda.vo.FiltroTareasVO" scope="session"/>
<jsp:useBean id="tareasAct" class="siges.gestionAdministrativa.agenda.vo.TareasVO" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.gestionAdministrativa.agenda.vo.ParamsVO" scope="page"/>

<html>
<c:import url="/parametros.jsp"/>
<head>
<style type="text/css">@import url('<c:url value="/etc/css/calendar-win2k-1.css"/>');</style>
<script type="text/javascript" src="<c:url value="/etc/js/calendar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/lang/calendar-es.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/calendar-setup.js"/>"></script>
<script language="javaScript">

	function lanzar(tipo){
  	    document.frmNuevo.tipo.value=tipo;
  	    document.frmNuevo.cmd.value=4;
		document.frmNuevo.target="";
		document.frmNuevo.submit();
	}
	
	function hacerValidaciones_frmFiltro(forma){
		validarLista(forma.metodologia, "- Metodologia", 1);
		validarLista(forma.grado, "- Grado", 1);
		validarLista(forma.grupo, "- Grupo", 1);
	}

	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;
			document.frmFiltro.tipo.value=document.frmFiltro.FICHA_TAREAS.value; 
			document.frmFiltro.ajax[0].value=document.frmFiltro.metodologia.value;
			document.frmFiltro.ajax[1].value=document.frmFiltro.grado.value;	
			document.frmFiltro.ajax[2].value=document.frmFiltro.grupo.value;
			document.frmFiltro.submit();
		}
	}
	
	function hacerValidaciones_frmNuevo(forma){
		validarCampo(forma.nombre, "- Nombre", 1);
		validarCampo(forma.fechaEntrega, "- Fecha de entrega", 1);
		validarLista(forma.asignaturas, "- Asignaturas", 1);
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.ajax[0].value=document.frmNuevo.metodologia.value;
			document.frmNuevo.ajax[1].value=document.frmNuevo.grado.value;	
			document.frmNuevo.ajax[2].value=document.frmNuevo.grupo.value;
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}
	
	function editar(m){
		document.frmFiltro.cmd.value=document.frmFiltro.EDITAR.value;
		document.frmFiltro.ajax[0].value=m;
		document.frmFiltro.submit();
	}
	
	//Cambio metodologia Nuevo	
	function filtroMetodologiaNuevo(){
		document.frmAjax1.ajax[0].value=document.frmNuevo.metodologia.value;
		if(parseInt(document.frmAjax1.ajax[0].value)>0){
			document.frmAjax1.cmd.value=document.frmFiltro.GRADO.value;
			document.frmAjax1.tipo.value=document.frmFiltro.FICHA_TAREAS.value;
	 		document.frmAjax1.target="frameAjax2";
			document.frmAjax1.submit();
		}
	}
	
	//Cambio grado	
	function filtroGradoNuevo(){
		document.frmAjax1.ajax[0].value=document.frmNuevo.metodologia.value;
		document.frmAjax1.ajax[1].value=document.frmNuevo.grado.value;
		if(parseInt(document.frmAjax1.ajax[1].value)>0){
			document.frmAjax1.cmd.value=document.frmFiltro.GRUPO.value;
	 		document.frmAjax1.target="frameAjax2";
			document.frmAjax1.submit();
		}
	}
	
	//Cambio Grupo	
	function filtroGrupoNuevo(){
		document.frmAjax1.ajax[2].value=document.frmNuevo.grupo.value;
		if(parseInt(document.frmAjax1.ajax[2].value)>0){
			document.frmAjax1.cmd.value=document.frmFiltro.ASIGNATURA.value;
	 		document.frmAjax1.target="frameAjax2";
			document.frmAjax1.submit();
		}
	}

	
	//Cambio metodologia	
	function filtroMetodologia(){
		document.frmAjax1.ajax[0].value=document.frmFiltro.metodologia.value;
		if(parseInt(document.frmAjax1.ajax[0].value)>0){
			document.frmAjax1.cmd.value=document.frmFiltro.GRADO.value;
			document.frmAjax1.tipo.value=document.frmFiltro.AJAX_INSTANCIA0.value;
	 		document.frmAjax1.target="frameAjax1";
			document.frmAjax1.submit();
		}
	}
	
	//Cambio grado	
	function filtroGrado(){
		document.frmAjax1.ajax[0].value=document.frmFiltro.metodologia.value;
		document.frmAjax1.ajax[1].value=document.frmFiltro.grado.value;
		if(parseInt(document.frmAjax1.ajax[0].value)>0){
			document.frmAjax1.cmd.value=document.frmFiltro.GRUPO.value;
	 		document.frmAjax1.target="frameAjax1";
			document.frmAjax1.submit();
		}
	}
	
	//Cambio Grupo	
	function filtroGrupo(){
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

	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
	 	 <tr height="1">
			<td width="10">&nbsp;</td>
			<td rowspan="2" width="469">
				<img src='<c:url value="/etc/img/tabs/tareas_0.gif"/>' alt="Actividades" height="26" border="0">
				<a href="javaScript:lanzar(6)">
					<img src='<c:url value="/etc/img/tabs/circulares_1.gif"/>' alt="Circulares" height="26" border="0">
				</a>
				<a href="javaScript:lanzar(7)">
					<img src='<c:url value="/etc/img/tabs/permisoss_0.gif"/>' alt="Circulares" height="26" border="0">
				</a>
			</td>
			</td>
		 </tr>
	</table>
	
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
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_TAREAS}"/>'>
		<input type="hidden" name="cmd"  value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="AJAX_INSTANCIA0" value='<c:out value="${paramsVO.CMD_AJAX_INSTANCIA0}"/>'>
		<input type="hidden" name="FICHA_TAREAS" value='<c:out value="${paramsVO.FICHA_TAREAS}"/>'> 
		<input type="hidden" name="GRADO" value='<c:out value="${paramsVO.CMD_AJAX_GRAD}"/>'> 
		<input type="hidden" name="GRUPO" value='<c:out value="${paramsVO.CMD_AJAX_GRUP}"/>'> 
		<input type="hidden" name="ASIGNATURA" value='<c:out value="${paramsVO.CMD_AJAX_AREA}"/>'> 
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">
		
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
						<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;<c:out value="${listaTareas.asignaturas}"/>&nbsp;</th>
						<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;<c:out value="${listaTareas.nombre}"/>&nbsp;</th>
						<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;<c:out value="${listaTareas.fechaEntrega}"/>&nbsp;</th>
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
	
	<form method="post" name="frmNuevo" action='<c:url value="/siges/gestionAdministrativa/agenda/tareas/Save.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_TAREAS}"/>'>
		<input type="hidden" name="cmd"  value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="NUEVO"  value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="FICHA" value='<c:out value="${paramsVO.FICHA_TAREAS}"/>'>
		<input type="hidden" name="FICHA_TAREAS" value='<c:out value="${paramsVO.FICHA_TAREAS}"/>'> 
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">
		
		<table align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Crear/Editar Tarea</caption>
		 			<tr style="display:none"><td><iframe name="frameAjax2" id="frameAjax2"></iframe></td></tr>
		 			<tr>
						<td>
	 						<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
			  			</td>
			  			<td>
	 						<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
			  			</td>
			 		</tr>
				 	<tr>	
						<td align="right"><span class="style2" >*</span>Metodolog&iacute;a:</td>
						<td>
					      	<select name="metodologia" style='width:180px;' onChange='filtroMetodologiaNuevo()'>
						        <option value='-9'>-- Seleccione una --</option>
						        <c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="niv">
										<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.tareasAct.metodologia}">selected</c:if>><c:out value="${niv.nombre}"/></option>
						        </c:forEach>
					        </select>
				      	</td>
				     
							<td align="right"><span class="style2" >*</span>Grado:</td>
							<td>
				      	<select name="grado"  style='width:180px;' onChange='filtroGradoNuevo()'>
								<option value='-9'>-- Seleccione una --</option>
								<c:forEach begin="0" items="${requestScope.listaGrado1}" var="niv">
								<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.tareasAct.grado}">SELECTED</c:if>><c:out value="${niv.nombre}"/></option>
								</c:forEach>
				        </select>
				      </td>
				 </tr>
				 <tr>
					<td align="right"><span class="style2" >*</span>Grupo:</td>
					<td>
			        	<select name="grupo"  style='width:180px;' onChange='filtroGrupoNuevo()'>
							<option value='-9'>-- Seleccione uno --</option>
							<c:forEach begin="0" items="${requestScope.listaGrupo1}" var="niv">
								<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.tareasAct.grupo}">SELECTED</c:if>><c:out value="${niv.nombre}"/></option>
							</c:forEach>
		        		</select>
			      </td>
		
				<td align="right"><span class="style2" >*</span>Asignatura:</td>
				<td>
		        	<select name="asignaturas"  style='width:180px;'>
						<option value='-9'>-- Seleccione uno --</option>
						<c:forEach begin="0" items="${requestScope.listaAsignaturas1}" var="niv">
							<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.tareasAct.asignaturas}">SELECTED</c:if>><c:out value="${niv.nombre}"/></option>
						</c:forEach>
		       		</select>
		        </td>
			 </tr>
				 <tr>	
					<td align="right"><span class="style2" >*</span>Código : </td>
					<td>
						<input readonly maxlength="100" style='width:180px;' name="codigo" id="codigo" value="<c:out value="${sessionScope.tareasAct.codigo}"/>">
					</td>	
					<td width='20%' align="right"><span class="style2" >*</span>Nombre : </td>
					<td>
						<input maxlength="100" style='width:180px;' name="nombre" id="nombre" value="<c:out value="${sessionScope.tareasAct.nombre}"/>"/>
					</td>
				</tr>
				<tr>	
					<td width='20%' align="right">Descripción : </td>
					<td colspan="3">
						<textarea maxlength="500"	cols="86" rows="3" name="descripcion" id="descripcion" ><c:out value="${sessionScope.tareasAct.descripcion}"/></textarea>
					</td>
				</tr>
				<tr>	
					<td width='20%' align="right"><span class="style2" >*</span>Fecha de Publicación: </td>
					<td>
						<input type="text" readonly name="fechaPublicacion" id="fechaPublicacion" value="<c:out value="${sessionScope.tareasAct.fechaPublicacion}"/>"/>
					</td>	
					<td width='20%' align="right"><span class="style2" >*</span>Fecha de Entrega: </td>
					<td>
						<input type="text" name="fechaEntrega" id="fechaEntrega" readonly value='<c:out value="${sessionScope.tareasAct.fechaEntrega}"/>'>
						<img 	
							src='<c:url value="/etc/img/calendario.gif"/>' 
							alt="Seleccione fecha" id="imgFechaEnt"
							style="cursor: pointer"
							title="Date selector"
							onmouseover="this.style.background='red';"
							onmouseout="this.style.background=''" 
						/>
					</td>
				</tr>
				<tr>	
					<td width='20%' align="right">Observaciones : </td>
					<td colspan="3"	>
						<textarea maxlength="500" cols="86" rows="3" name="observaciones" id="observaciones" ><c:out value="${sessionScope.tareasAct.observaciones}"/></textarea>
					</td>
				</tr>					
				<tr>	
					<td width='20%' align="right">Estado : </td>
					<td>
						<select name="estado" id="estado" style="width:120px">
							<option value="1" <c:if test="${sessionScope.tareasAct.estado == 2}">SELECTED</c:if>>Pendiente</option>
							<option value="2" <c:if test="${sessionScope.tareasAct.estado == 3}">SELECTED</c:if>>Entregada</option>
					`	</select>
					</td>
				</tr>
		</table>
	</form>
</body>
<script type="text/javascript">
	Calendar.setup({inputField:"fechaEntrega",ifFormat:"%d/%m/%Y",button:"imgFechaEnt",align:"Br"});
</script>
</html>
