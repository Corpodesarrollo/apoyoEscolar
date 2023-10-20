<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="siges.gestionAdministrativa.agenda.vo.ParamsVO" scope="page"/>
<jsp:useBean id="circularesAct" class="siges.gestionAdministrativa.agenda.vo.CircularVO" scope="session"/>
<html>
<head>
<style type="text/css">@import url('<c:url value="/etc/css/calendar-win2k-1.css"/>');</style>
<script type="text/javascript" src="<c:url value="/etc/js/calendar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/lang/calendar-es.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/calendar-setup.js"/>"></script>
<script languaje="javaScript">

	function lanzar(tipo){
		document.frmNuevoCircular.tipo.value=tipo;
		document.frmNuevoCircular.cmd.value=document.frmNuevoCircular.NUEVO.value;
		document.frmFiltro.target="";
		document.frmNuevoCircular.submit();
	}

	function hacerValidaciones_frmNuevoCircular(forma){
		validarLista(forma.dato, "- Datos", 1);
		validarCampo(forma.asunto, "- Asunto", 1);
		validarCampo(forma.fechaRegistro, "- Fecha", 1); 
		validarCampo(forma.responsable, "- Responsable", 1);
		
		var currentdate = new Date(); 
		
		var s = forma.fechaRegistro.value.split("/");
		var d1 = new Date(s[0], s[1], s[2]);
		var d2 = new Date(currentdate.getDate(), currentdate.getMonth()+1,  currentdate.getFullYear());

		if (d2 > d1) {
		    alert("La fecha debe ser mayor a la fecha actual");
		}
	}

	function guardar(){
		
		if(validarForma(document.frmNuevoCircular)){
			document.frmNuevoCircular.cmd.value=document.frmNuevoCircular.GUARDAR.value;
			document.frmNuevoCircular.submit();
		}
	}
	
	function nuevo(){
			document.frmNuevoCircular.cmd.value=document.frmNuevoCircular.NUEVO.value;
			document.frmNuevoCircular.submit();
	}
	
	//Cambio nivel	
	function filtroNivel(){
		document.frmAjax1.ajax[0].value=document.frmNuevoCircular.nivel.value;
		if(parseInt(document.frmAjax1.ajax[0].value)>0){
			document.frmAjax1.cmd.value=document.frmNuevoCircular.AREA.value;
			document.frmAjax1.tipo.value=document.frmNuevoCircular.NIVEL.value;
	 		document.frmAjax1.target="frameAjax1";
			document.frmAjax1.submit();
		}
	}
	
	function uploadFile(){
		  if(validarCampo(document.frmUpload.archivo, "- Seleccionar archivo", 5)){
		  document.frmUpload.encoding="multipart/form-data";
		  document.frmUpload.submit();
		 } else{
			  alert("Debe seleccionar el archivo a cargar");
		 }
	}
		
</script>
</head>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
	 	 <tr height="1">
			<td width="10">&nbsp;</td>
			<td rowspan="2" width="469"><a href="javaScript:lanzar(5)"><img src='<c:url value="/etc/img/tabs/tareas_1.gif"/>' alt="Actividades" height="26" border="0"></a>
			<img src='<c:url value="/etc/img/tabs/circulares_0.gif"/>' alt="Actividades" height="26" border="0">
			<a href="javaScript:lanzar(7)">
					<img src='<c:url value="/etc/img/tabs/permisoss_0.gif"/>' alt="Circulares" height="26" border="0">
				</a>
			</td>
		 </tr>
	</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" valign='top'>
				<div style="width:100%;height:200px;overflow:auto;vertical-align:top;background-color:#ffffff;">
					<c:import url="/siges/gestionAdministrativa/agenda/circulares/Filtro.jsp"/>
				</div>
			</td>
		</tr>
	</table>
	
	<form method="post" name="frmAjax1" action='<c:url value="/siges/gestionAdministrativa/Ajax.do"/>' >
		<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">
	</form>
	
	<c:if test="${sessionScope.circularesAct.nivelUsuario != 510}"> <!-- No es un padre de familia -->
		<form method="post" name="frmNuevoCircular" action='<c:url value="/siges/gestionAdministrativa/agenda/circulares/Save.jsp"/>'>
			<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_CIRCULARES}"/>'>
			<input type="hidden" name="cmd"  value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
			<input type="hidden" name="NUEVO"  value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
			<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
			<input type="hidden" name="NIVEL" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>  
			<input type="hidden" name="AREA" value='<c:out value="${paramsVO.CMD_AJAX_AREA}"/>'>
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			
			<table align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
			 	<caption>Crear/Editar Circular</caption>
			 	<tr style="display:none"><td><iframe name="frameAjax1" id="frameAjax1"></iframe></td></tr>
			 	<tr>
							<td>
		 						<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
				  			</td>
				  			<td>
		 						<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
				  			</td>
				 		</tr>
			 	<tr>
			 		<td align="right"><span class="style2" >*</span>Nivel:</td>
					<td>
				      	<select name="nivel"  style='width:180px;' onChange='filtroNivel()'>
								<option value='0'>Seleccione Nivel</option>
								<option value='1' <c:if test="${1==sessionScope.circularesAct.nivel}">selected</c:if>>Colegio</option>
								<option value='2' <c:if test="${2==sessionScope.circularesAct.nivel}">selected</c:if>>Jornada</option>
								<option value='3' <c:if test="${3==sessionScope.circularesAct.nivel}">selected</c:if>>Sede</option>
								<option value='4' <c:if test="${4==sessionScope.circularesAct.nivel}">selected</c:if>>Sede-Jornada</option>
								<option value='5' <c:if test="${5==sessionScope.circularesAct.nivel}">selected</c:if>>Metodología-Grado</option>
								<option value='6' <c:if test="${6==sessionScope.circularesAct.nivel}">selected</c:if>>Grupo</option>
				        </select>
				    </td>
				    <td align="right"><span class="style2" >*</span>Datos:</td>
					<td>
				      	<select name="dato" style='width:180px;'>
					        <option value='-9'>-- Seleccione una --</option>
					        <c:forEach begin="0" items="${requestScope.listaDatos}" var="niv">
									<option value="<c:out value="${niv.codigo}"/>" <c:if test="${niv.codigo==sessionScope.circularesAct.dato}">selected</c:if>><c:out value="${niv.nombre}"/></option>
					        </c:forEach>
				        </select>
			      	</td>
			 	</tr>
			 	<tr>	
					<td align="right"><span class="style2" >*</span>Asunto : </td>
					<td colspan="3">
						<input maxlength="100" style='width:540px;' name="asunto" id="asunto" value="<c:out value="${sessionScope.circularesAct.asunto}"/>">
					</td>	
				</tr>
				<tr>	
					<td width='20%' align="right"><span class="style2" >*</span>Descripción : </td>
					<td colspan="3">
						<textarea maxlength="500" cols="86" rows="3" name="descripcion" id="descripcion" ><c:out value="${sessionScope.circularesAct.descripcion}"/></textarea>
					</td>
				</tr>
				<tr>	
					<td align="right"><span class="style2" >*</span>Fecha : </td>
					<td>
						<input type="text" name="fechaRegistro" id="fechaRegistro" readonly value='<c:out value="${sessionScope.circularesAct.fechaRegistro}"/>'>
						<img 	
							src='<c:url value="/etc/img/calendario.gif"/>' 
							alt="Seleccione fecha" id="imgFecha"
							style="cursor: pointer"
							title="Date selector"
							onmouseover="this.style.background='red';"
							onmouseout="this.style.background=''" 
						/>
					</td>	
					<td width='20%' align="right"><span class="style2" >*</span>Responsable : </td>
					<td>
						<input maxlength="100" style='width:180px;' name="responsable" id="responsable" value="<c:out value="${sessionScope.circularesAct.responsable}"/>"/>
					</td>
				</tr>
				<tr>	
					<td align="right"><span class="style2" >*</span>Estado : </td>
					<td>
						<select name="estado" id="estado" style="width:120px">
							<option value="1" <c:if test="${sessionScope.circularesAct.estado == 1}">SELECTED</c:if>>Activa</option>
							<option value="2" <c:if test="${sessionScope.circularesAct.estado == 2}">SELECTED</c:if>>Inactiva</option>
					`	</select>
					</td>	
				</tr>
			</table>
		</form>
		
		<c:if test="${sessionScope.circularesAct.codigo > 0}">
			<form method="post" name="frmUpload" action='<c:url value="/siges/gestionAdministrativa/LoadFiles.do"/>' >
				<table>
					<tr>	
						<td align="right"><span class="style2" >*</span>Documento : </td>
						<td colspan="2">
							<input type="file" maxlength="200" style='width:540px;' name="archivo" id="archivo" value="<c:out value="${sessionScope.circularesAct.archivo}"/>"/>
						</td>	
						<td>
							<input type="button" value="Cargar Archivo" onclick="uploadFile()"/>
						</td>
					</tr>
				</table>
			</form>
		</c:if>
		<script type="text/javascript">
			Calendar.setup({inputField:"fechaRegistro",ifFormat:"%d/%m/%Y",button:"imgFecha",align:"Br"});
		</script>
	</c:if>
	
				
	
</body>

</html>