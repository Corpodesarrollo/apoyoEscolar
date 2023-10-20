<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="siges.gestionAdministrativa.gestionActividades.vo.ParamsVO" scope="page"/>
<jsp:useBean id="gestionAct" class="siges.gestionAdministrativa.gestionActividades.vo.GestionActVO" scope="session"/>
<html>
<head>
<style type="text/css">@import url('<c:url value="/etc/css/calendar-win2k-1.css"/>');</style>
<script type="text/javascript" src="<c:url value="/etc/js/calendar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/lang/calendar-es.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/calendar-setup.js"/>"></script>
<script languaje="javaScript">

	function hacerValidaciones_frmNuevo(forma){
		validarCampo(forma.nombre, "- Nombre", 1);
		validarCampo(forma.descripcion, "- Descripcion", 1);
		validarCampo(forma.fecha, "- Fecha", 1);
		validarCampo(forma.horaInicial, "- Hora Inicial", 1);
		validarCampo(forma.horaFinal, "- Hora Final", 1);
		validarCampo(forma.lugar, "- Lugar", 1); 
		validarCampo(forma.participantes, "- Participantes", 1);
		validarCampo(forma.estado, "- Estado", 1); 
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}
	
	function nuevo(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
			document.frmNuevo.submit();
		}
	}
		
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">


	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:200px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/siges/gestionAdministrativa/gestionActividades/Filtro.jsp"/>
			</div>
		</td></tr>
	</table>
	<form method="post" name="frmAjax" action='<c:url value="/siges/gestionAdministrativa/gestionActividades/Ajax.do"/>'>
	</form>
	<form method="post" name="frmNuevo" action='<c:url value="/siges/gestionAdministrativa/gestionActividades/Save.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_GESTION_ACTIVIDADES}"/>'>
		<input type="hidden" name="cmd"  value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="NUEVO"  value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		
		<table align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Crear actividad</caption>
		 		<tr>
					<td>
 						<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
		  			</td>
		  			<td>
 						<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
		  			</td>
		 		</tr>
				<tr>
					<td width='20%' align="right">Nivel : </td>
					<td>
						<select name="nivel" style="width:120px">
						<option value="1" <c:if test="${sessionScope.gestionAct.nivel == 1}">SELECTED</c:if>>Colegio --</option>
						<option value="2" <c:if test="${sessionScope.gestionAct.nivel == 2}">SELECTED</c:if>>Sede --</option>
						<option value="3" <c:if test="${sessionScope.gestionAct.nivel == 3}">SELECTED</c:if>>Jornada --</option>
						<option value="4" <c:if test="${sessionScope.gestionAct.nivel == 4}">SELECTED</c:if>>Sede-Jornada --</option>
						<option value="5" <c:if test="${sessionScope.gestionAct.nivel == 5}">SELECTED</c:if>>Metodología-Grado --</option>
						<option value="6" <c:if test="${sessionScope.gestionAct.nivel == 6}">SELECTED</c:if>>Grupo --</option>
					</select>
					</td>
				</tr>
				<tr>	
					<td width='20%' align="right">Nombre : </td>
					<td>
						<input type="text" name="nombre" id="nombre" style="width:500px" maxlength="100" value='<c:out value="${sessionScope.gestionAct.nombre}"/>'>
					</td>
				</tr>
				<tr>	
					<td width='20%' align="right">Descripción : </td>
					<td>
						<textarea maxlength="500"	cols="80" rows="3" name="descripcion" id="descripcion" ><c:out value="${sessionScope.gestionAct.descripcion}"/></textarea>
					</td>
				</tr>
				<tr>	
					<td width='20%' align="right">Fecha : </td>
					<td>
						<input type="text" name="fecha" id="fecha" readonly value='<c:out value="${sessionScope.gestionAct.fecha}"/>'>
						<img 	
							src='<c:url value="/etc/img/calendario.gif"/>' 
							alt="Seleccione fecha" id="imgFechapla"
							style="cursor: pointer"
							title="Date selector"
							onmouseover="this.style.background='red';"
							onmouseout="this.style.background=''" 
						/>
					</td>
				</tr>
				<tr>	
					<td width='20%' align="right">Hora Inicial : </td>
					<td>
						<input maxlength="10" type="text" name="horaInicial" id="horaInicial" value='<c:out value="${sessionScope.gestionAct.horaInicial}"/>'>
					</td>
				</tr>
				<tr>	
					<td width='20%' align="right">Hora Final : </td>
					<td>
						<input maxlength="10" type="text" name="horaFinal" id="horaFinal" value='<c:out value="${sessionScope.gestionAct.horaFinal}"/>'>
					</td>
				</tr>
				<tr>	
					<td width='20%' align="right">Lugar : </td>
					<td>
						<input type="text" name="lugar" id="lugar" maxlength="50" style="width:500px" value='<c:out value="${sessionScope.gestionAct.lugar}"/>'>
					</td>
				</tr>
				<tr>	
					<td width='20%' align="right">Participantes : </td>
					<td>
						<input type="text" name="participantes" id="participantes" maxlength="100" style="width:500px"  value='<c:out value="${sessionScope.gestionAct.participantes}"/>'>
					</td>
				</tr>
				<tr>	
					<td width='20%' align="right">Estado : </td>
					<td>
						<select name="estado" id="estado" style="width:120px">
						<option value="2" <c:if test="${sessionScope.gestionAct.estado == 2}">SELECTED</c:if>>Activo</option>
						<option value="3" <c:if test="${sessionScope.gestionAct.estado == 3}">SELECTED</c:if>>Inactivo</option>
					</select>
					</td>
				</tr>
		</table>
	</form>
</body>
<script type="text/javascript">
	Calendar.setup({inputField:"fecha",ifFormat:"%d/%m/%Y",button:"imgFechapla",align:"Br"});
</script>
</html>