<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="permisosFiltro" class="siges.gestionAdministrativa.agenda.vo.FiltroPermisosVO" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.gestionAdministrativa.agenda.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>

<script language="javaScript">
	
	function hacerValidaciones_frmFiltro(forma){
		validarLista(forma.permiso, "- Permiso", 1);
		validarCampo(forma.fechaInicio, "- Fecha Inicio", 1);
		validarCampo(forma.fechaFin, "- Fecha Final", 1);
	}

	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;
			document.frmFiltro.submit();
		}
	}
	
	function editar(m){
		document.frmFiltro.cmd.value=document.frmFiltro.EDITAR.value;
		document.frmFiltro.ajax[0].value=m;
		document.frmFiltro.submit();
	}
	
	function eliminar(m){
		document.frmFiltro.cmd.value=document.frmFiltro.ELIMINAR.value;
		document.frmFiltro.ajax[0].value=m;
		document.frmFiltro.submit();
	}
	
	function descargar(m){
		document.frmFiltro.cmd.value=document.frmFiltro.DESCARGAR.value;
		document.frmFiltro.ajax[0].value=m;
		document.frmFiltro.submit();
	}
	
	function download(name){
		document.listado.dir.value='/apoyo_escolar/private/resultados/permisos/'+name;
		document.listado.tipo.value='pdf';
		document.listado.action='/apoyo_escolar/private/resultados/permisos/'+name;
		document.listado.submit();
	}
	
		
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<form  name='listado' method='post' target='_blank'>
		<input type='hidden' name='aut' value='1'>
		<input type='hidden' name='dir'>
		<input type='hidden' name='tipo'>
	</form>
	<form method="post" name="frmAjax0" action='<c:url value="/siges/gestionAdministrativa/Ajax.do"/>'>
		<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">
	</form>
	<form action='<c:url value="/siges/gestionAdministrativa/agenda/permisos/Save.jsp"/>' method="post" name="frmFiltro" >
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="cmd"  value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="AJAX_INSTANCIA0" value='<c:out value="${paramsVO.CMD_AJAX_INSTANCIA0}"/>'> 
		<input type="hidden" name="GRADO" value='<c:out value="${paramsVO.CMD_AJAX_GRAD}"/>'> 
		<input type="hidden" name="GRUPO" value='<c:out value="${paramsVO.CMD_AJAX_GRUP}"/>'> 
		<input type="hidden" name="ELIMINAR"  value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="ASIGNATURA" value='<c:out value="${paramsVO.CMD_AJAX_AREA}"/>'>
		<input type="hidden" name="DESCARGAR" value='<c:out value="${paramsVO.CMD_GENERAR_HISTORICO}"/>'>
		 
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_PERMISOS}"/>'> 
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">

		<table border="0" align="center" width="95%">
		 	<caption>Filtro de búsqueda</caption>
			<tr>
				<td>
 					<input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
		  		</td>
		 	</tr>	
		 	<tr style="display:none"><td><iframe name="frameAjax0" id="frameAjax0"></iframe></td></tr>
		</table>
	

	<TABLE width="90%" cellpadding="2" cellSpacing="2" align="center">
		<tr>	
			<td><span class="style2" >*</span>TIPO DE PERMISO:</td>
			<td>
		      	<select name="permiso" style='width:180px;'>
			        <option value='-9'>-- Seleccione uno --</option>
			        <c:forEach begin="0" items="${requestScope.tipoPermiso}" var="niv">
								<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.permisosFiltro.permiso}">selected</c:if>><c:out value="${niv.nombre}"/></option>
				        </c:forEach>
		        </select>
	        </td>
	    </tr>
	    <tr>	     
			<td>
				<span class="style2" >*</span>FECHA INICIO:
			</td>
			<td>
			    <input type="text" name="fechaInicio" id="fechaInicio" readonly value='<c:out value="${sessionScope.permisosFiltro.fechaInicio}"/>'>
					<img 	
						src='<c:url value="/etc/img/calendario.gif"/>' 
						alt="Seleccione fecha" id="imgFecha"
						style="cursor: pointer"
						title="Date selector"
						onmouseover="this.style.background='red';"
						onmouseout="this.style.background=''" 
					/>  	
		    </td>
		    <td>
				<span class="style2" >*</span>FECHA FINAL:
			</td>
			<td>
			    <input type="text" name="fechaFin" id="fechaFin" readonly value='<c:out value="${sessionScope.permisosFiltro.fechaFin}"/>'>
					<img 	
						src='<c:url value="/etc/img/calendario.gif"/>' 
						alt="Seleccione fecha" id="imgFechaFin"
						style="cursor: pointer"
						title="Date selector"
						onmouseover="this.style.background='red';"
						onmouseout="this.style.background=''" 
					/>   	
		    </td>
  		</tr>
	  
	 <tr>
		<td>Estado:</td>
		<td>
	       	<select name="estado"  style='width:180px;'>
				<option value='0' <c:if test="${0==sessionScope.permisosFiltro.estado}">selected</c:if>>Pendientes --</option>
				<option value='1' <c:if test="${1==sessionScope.permisosFiltro.estado}">selected</c:if>>Aprobadas --</option>
				<option value='2' <c:if test="${2==sessionScope.permisosFiltro.estado}">selected</c:if>>Rechazadas --</option>
      		</select>
	   </td>
	 </tr>
	 </TABLE>
	 
		
		
		<br> 	
		<!-- TABLA DE LOS RESULTADOS DE LA BUSQUEDA -->
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Listado de Permisos</caption>
 			<c:if test="${sessionScope.permisosFiltro.download == true}">
				<tr>
					<td>
						<td>
		 					<input name="cmd2" type="button" value="Descargar Formato" onClick='download("<c:out value="${sessionScope.permisosFiltro.file}"/>")' class="boton">
				  		</td>
					</td>
				</tr>
			</c:if>
		 	<c:if test="${empty requestScope.listaPermisos}"><tr><th class="Fila1" colspan='6'>No hay permisos</th></tr></c:if>
			<c:if test="${!empty requestScope.listaPermisos}">
				<tr>
					<td width='5%' class="EncabezadoColumna" align="center">Editar</td>
					<td width='5%' class="EncabezadoColumna" align="center">Eliminar</td>
					<td width='5%' class="EncabezadoColumna" align="center">Generar</td>
					<td width='20%' class="EncabezadoColumna" align="center">Motivo</td>
					<td width='30%' class="EncabezadoColumna" align="center">Fecha</td>
					<td width='20%' class="EncabezadoColumna" align="center">Hora Salida</td>
					<td width='20%' class="EncabezadoColumna" align="center">Hora llegada</td>
					<td width='30%' class="EncabezadoColumna" align="center">Estado</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaPermisos}" var="list" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${list.codigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
						</th>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:eliminar(<c:out value="${list.codigo}"/>);'><img border='0' src='<c:url value="/etc/img/a.png"/>' width='15' height='15'></a>
						</th>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:descargar(<c:out value="${list.codigo}"/>);'><img border='0' src='<c:url value="/etc/img/pdf.gif"/>' width='15' height='15'></a>
						</th>
						<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;<c:out value="${list.motivoString}"/>&nbsp;</th>
						<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;<c:out value="${list.fecha}"/>&nbsp;</th>
						<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;<c:out value="${list.horaInicio}"/>&nbsp;</th>
						<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;<c:out value="${list.horaFin}"/>&nbsp;</th>
						<th class='Fila<c:out value="${st.count%2}"/>'>&nbsp;<c:out value="${list.estadoString}"/>&nbsp;</th>
						
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</form>
</body>
<script type="text/javascript">
	Calendar.setup({inputField:"fechaInicio",ifFormat:"%d/%m/%Y",button:"imgFecha",align:"Br"});
	Calendar.setup({inputField:"fechaFin",ifFormat:"%d/%m/%Y",button:"imgFechaFin",align:"Br"});
</script>
</html>
