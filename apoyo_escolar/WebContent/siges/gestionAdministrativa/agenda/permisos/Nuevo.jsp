<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="siges.gestionAdministrativa.agenda.vo.ParamsVO" scope="page"/>
<jsp:useBean id="permisosAct" class="siges.gestionAdministrativa.agenda.vo.PermisosVO" scope="session"/>
<html>
<head>
<style type="text/css">@import url('<c:url value="/etc/css/calendar-win2k-1.css"/>');</style>
<script type="text/javascript" src="<c:url value="/etc/js/calendar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/lang/calendar-es.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/calendar-setup.js"/>"></script>
<script languaje="javaScript">

	function lanzar(tipo){
		document.frmNuevo.tipo.value=tipo;
		document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
		document.frmFiltro.target="";
		document.frmNuevo.submit();
	}

	function hacerValidaciones_frmNuevo(forma){
		validarCampo(forma.motivo, "- Motivo", 1);
		validarCampo(forma.fecha, "- Fecha", 1);
		validarEntero(forma.horaInicio, "- Hora Salida debe estar entre 6 y 22", 6,22); 
		validarEntero(forma.horaFin, "- Hora llegada debe estar entre 6 y 22", 6,22);
		validarEntero(forma.minIni, "- Minuto salida debe estar entre 5 y 55", 5,55);
		validarEntero(forma.minFin, "- Minuto llegada debe estar entre 5 y 55", 5,55);
		
		var currentdate = new Date(); 
		
		var s = forma.fecha.value.split("/");
		var d1 = new Date(s[0], s[1], s[2]);
		var d2 = new Date(currentdate.getDate(), currentdate.getMonth()+1,  currentdate.getFullYear());

		if (d2 > d1) {
		    alert("La fecha debe ser mayor a la fecha actual");
		} 
		
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}
	
	function nuevo(){
			document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
			document.frmNuevo.submit();
	}
	
	
	//Cambio nivel	
	function filtroNivel(){
		document.frmAjax1.ajax[0].value=document.frmNuevo.nivel.value;
		if(parseInt(document.frmAjax1.ajax[0].value)>0){
			document.frmAjax1.cmd.value=document.frmNuevo.AREA.value;
			document.frmAjax1.tipo.value=document.frmNuevo.NIVEL.value;
	 		document.frmAjax1.target="frameAjax1";
			document.frmAjax1.submit();
		}
	}
		
</script>
</head>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
	 	 <tr height="1">
			<td width="10">&nbsp;</td>
			<td rowspan="2" width="469">
				<a href="javaScript:lanzar(5)">
					<img src='<c:url value="/etc/img/tabs/tareas_1.gif"/>' alt="Tareas height="26" border="0">
				</a>
				<a href="javaScript:lanzar(6)">
					<img src='<c:url value="/etc/img/tabs/circulares_1.gif"/>' alt="Circulares" height="26" border="0">
				</a>
				<img src='<c:url value="/etc/img/tabs/permisoss_1.gif"/>' alt="Circulares" height="26" border="0">
			</td>
		 </tr>
	</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" valign='top'>
				<div style="width:100%;height:200px;overflow:auto;vertical-align:top;background-color:#ffffff;">
					<c:import url="/siges/gestionAdministrativa/agenda/permisos/Filtro.jsp"/>
				</div>
			</td>
		</tr>
	</table>
	<form method="post" name="frmNuevo" action='<c:url value="/siges/gestionAdministrativa/agenda/permisos/Save.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_PERMISOS}"/>'>
		<input type="hidden" name="cmd"  value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="NUEVO"  value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NIVEL" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>  
		<input type="hidden" name="AREA" value='<c:out value="${paramsVO.CMD_AJAX_AREA}"/>'>
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">
		
		<table align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Crear/Editar Permiso</caption>
		 	<tr>
				<td>
					<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
	  			</td>
	  			<td>
					<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
	  			</td>
	  			<td>
	  			</td>
	  			<td>
	  			</td>
	 		</tr>
	 		<tr>
	 			<td align="right"><span class="style2" >*</span>Motivo:</td>
				<td colspan="3" >
					<select name="motivo" style='width:180px;'>
			        <c:forEach begin="0" items="${requestScope.tipoPermiso}" var="niv">
								<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.permisosAct.motivo}">selected</c:if>><c:out value="${niv.nombre}"/></option>
				        </c:forEach>
				        </select>
				        		      	</td>
	 		</tr>
	 		<tr>	
				<td align="right"><span class="style2" >*</span>Fecha : </td>
				<td colspan="3" >
					<input  type="text" name="fecha" id="fecha" readonly value='<c:out value="${sessionScope.permisosAct.fecha}"/>'>
					<img 	
						src='<c:url value="/etc/img/calendario.gif"/>' 
						alt="Seleccione fecha" id="imgFecha1"
						style="cursor: pointer"
						title="Date selector"
						onmouseover="this.style.background='red';"
						onmouseout="this.style.background=''" 
					/>
				</td>	
			</tr>
			<tr>
				<td>
					<span class="style2" >*</span>Hora Salida : 
				</td>
				<td>
					<input maxlength="2" type="text" name="horaInicio" id="horaInicio" value='<c:out value="${sessionScope.permisosAct.horaInicio}"/>'>
		      	</td>
		      	<td>
					<span class="style2" >*</span>Minuto Salida : 
				</td>
				<td>
					<input maxlength="2" type="text" name="minIni" id="minIni" value='<c:out value="${sessionScope.permisosAct.minIni}"/>'>
		      	</td>
			</tr>
			<tr>
				<td>
					<span class="style2" >*</span>Hora llegada : 
				</td>
				<td>
					<input maxlength="2" type="text" name="horaFin" id="horaFin" value='<c:out value="${sessionScope.permisosAct.horaFin}"/>'>
		      	</td>
		      	<td>
					<span class="style2" >*</span>Minuto llegada : 
				</td>
				<td>
					<input maxlength="2" type="text" name="minFin" id="minFin" value='<c:out value="${sessionScope.permisosAct.minFin}"/>'>
		      	</td>
			</tr>
			<tr>
				<td>
					<span class="style2" >*</span>Observaciones : 
				</td>
				<td  colspan="3"  >
					<input maxlength="100" style='width:540px;'  type="text" name="observaciones" id="observaciones" value='<c:out value="${sessionScope.permisosAct.observaciones}"/>'>
		      	</td>
			</tr>
			<c:if test="${sessionScope.permisosAct.nivel == 510}"> <!-- No es un padre de familia -->
				<tr>
					<td>
						<span class="style2" >*</span>Observaciones Responsable: 
					</td>
					<td  colspan="3"  >
						<input maxlength="100" style='width:540px;' readonly  type="text" name="observacionesResponsable" id="observacionesResponsable" value='<c:out value="${sessionScope.permisosAct.observacionesResponsable}"/>'>
			      	</td>
				</tr>
			</c:if>
			<c:if test="${sessionScope.permisosAct.nivel != 510}"> <!-- No es un padre de familia -->
				<tr>
					<td>
						<span class="style2" >*</span>Observaciones Responsable: 
					</td>
					<td  colspan="3"  >
						<input maxlength="100" style='width:540px;'  type="text" name="observacionesResponsable" id="observacionesResponsable" value='<c:out value="${sessionScope.permisosAct.observacionesResponsable}"/>'>
			      	</td>
				</tr>
				<tr>
					<td>Estado:</td>
					<td>
				       	<select name="estado"  style='width:180px;'>
							<option value='0' <c:if test="${0==sessionScope.permisosAct.estado}">selected</c:if>>Pendientes --</option>
							<option value='1' <c:if test="${1==sessionScope.permisosAct.estado}">selected</c:if>>Aprobadas --</option>
							<option value='2' <c:if test="${2==sessionScope.permisosAct.estado}">selected</c:if>>Rechazadas --</option>
			      		</select>
				   </td>
				</tr>
			</c:if>
		</table>
		
	</form>
	
</body>
<script type="text/javascript">
	Calendar.setup({inputField:"fecha",ifFormat:"%d/%m/%Y",button:"imgFecha1",align:"Br"});
	Calendar.setup({inputField:"fechaInicio",ifFormat:"%d/%m/%Y",button:"imgFecha",align:"Br"});
	Calendar.setup({inputField:"fechaFin",ifFormat:"%d/%m/%Y",button:"imgFechaFin",align:"Br"});
</script>
</html>