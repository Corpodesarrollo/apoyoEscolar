<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="parametroProgramacionFechas" class="poa.parametro.vo.ProgramacionFechasVO" scope="session"/>
<jsp:useBean id="paramsVO" class="poa.parametro.vo.ParamsVO" scope="page"/>
<html>
<head>
<style type="text/css">@import url('<c:url value="/etc/css/calendar-win2k-1.css"/>');</style>
<script type="text/javascript" src="<c:url value="/etc/js/calendar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/lang/calendar-es.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/calendar-setup.js"/>"></script>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key==46 || key<=13 || (key>=48 && key<=57));
	}
		
	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.progVigencia, "- Vigencia", 1)
		validarFechaUnCampo(forma.progPlaneacionIni, "- Fecha inicial de planeación")
		validarFechaUnCampo(forma.progPlaneacionFin, "- Fecha final de planeación")
		validarFechaUnCampo(forma.progSeguimiento1Ini, "- Fecha inicial de seguimiento primer trimestre")
		validarFechaUnCampo(forma.progSeguimiento1Fin, "- Fecha final de seguimiento primer trimestre")
		validarFechaUnCampo(forma.progSeguimiento2Ini, "- Fecha inicial de seguimiento segundo trimestre")
		validarFechaUnCampo(forma.progSeguimiento2Fin, "- Fecha final de seguimiento segundo trimestre")
		validarFechaUnCampo(forma.progSeguimiento3Ini, "- Fecha inicial de seguimiento tercer trimestre")
		validarFechaUnCampo(forma.progSeguimiento3Fin, "- Fecha final de seguimiento tercer trimestre")
		validarFechaUnCampo(forma.progSeguimiento4Ini, "- Fecha inicial de seguimiento cuarto trimestre")
		validarFechaUnCampo(forma.progSeguimiento4Fin, "- Fecha final de seguimiento cuarto trimestre")
		
		validarRangoFechas(forma.progPlaneacionIni,forma.progPlaneacionFin,'La fecha inicial de planeación debe ser menor que la fecha final');
		validarRangoFechas(forma.progSeguimiento1Ini,forma.progSeguimiento1Fin,'La fecha inicial de seguimiento primer trimestre debe ser menor que la fecha final');
		validarRangoFechas(forma.progSeguimiento2Ini,forma.progSeguimiento2Fin,'La fecha inicial de seguimiento segundo trimestre debe ser menor que la fecha final');
		validarRangoFechas(forma.progSeguimiento3Ini,forma.progSeguimiento3Fin,'La fecha inicial de seguimiento tercer trimestre debe ser menor que la fecha final');
		validarRangoFechas(forma.progSeguimiento4Ini,forma.progSeguimiento4Fin,'La fecha inicial de seguimiento cuarto trimestre debe ser menor que la fecha final');
		
	}
		
	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
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
				<c:import url="/poa/parametro/FiltroProgramacionFechas.jsp"/>
			</div>
		</td></tr>
	</table>
	<form method="post" name="frmNuevo" action='<c:url value="/poa/parametro/SaveProgramacionFechas.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_PROGRAMACION_FECHAS}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="CANCELAR" value='<c:out value="${paramsVO.CMD_CANCELAR}"/>'>
	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		  <caption>Ingreso / edición de programación de fechas</caption>
				<tr>
					<td colspan="4">
						<c:if test="${sessionScope.NivelPermiso==2}">
    					<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
							<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
	    				</c:if>
			  		</td>
			 	</tr>	
			<tr>
			<tr>
				<td><span class="style2">*</span> Vigencia:</td>
				<td>
					<select name="progVigencia" style="width:150px" <c:out value="${sessionScope.parametroProgramacionFechas.formaDisabled}"/>>
						<option value="-99" selected>-Seleccione uno-</option>
						<c:forEach begin="0" items="${requestScope.listaVigencia_POA}" var="vigencia">
							<option value='<c:out value="${vigencia.codigo}"/>' <c:if test="${vigencia.codigo==sessionScope.parametroProgramacionFechas.progVigencia}">selected</c:if>><c:out value="${vigencia.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Fecha de planeación:</td>
				<td>Desde: 
				<input type="text" name="progPlaneacionIni" maxlength="10" size="10" value='<c:out value="${sessionScope.parametroProgramacionFechas.progPlaneacionIni}"/>'></input>
				<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="imgProgPlaneacionIni" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
				</td>
				<td>Hasta:
				<input type="text" name="progPlaneacionFin" maxlength="10" size="10" value='<c:out value="${sessionScope.parametroProgramacionFechas.progPlaneacionFin}"/>'></input>
				<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="imgProgPlaneacionFin" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
				</td>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Fecha de seguimiento primer trimestre:</td>
				<td>Desde: 
				<input type="text" name="progSeguimiento1Ini" maxlength="10" size="10" value='<c:out value="${sessionScope.parametroProgramacionFechas.progSeguimiento1Ini}"/>'></input>
				<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="imgProgSeguimiento1Ini" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
				</td>
				<td>Hasta:
				<input type="text" name="progSeguimiento1Fin" maxlength="10" size="10" value='<c:out value="${sessionScope.parametroProgramacionFechas.progSeguimiento1Fin}"/>'></input>
				<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="imgProgSeguimiento1Fin" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span> Fecha de seguimiento segundo trimestre:</td>
				<td>Desde: 
				<input type="text" name="progSeguimiento2Ini" maxlength="10" size="10" value='<c:out value="${sessionScope.parametroProgramacionFechas.progSeguimiento2Ini}"/>'></input>
				<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="imgProgSeguimiento2Ini" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
				</td>
				<td>Hasta:
				<input type="text" name="progSeguimiento2Fin" maxlength="10" size="10" value='<c:out value="${sessionScope.parametroProgramacionFechas.progSeguimiento2Fin}"/>'></input>
				<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="imgProgSeguimiento2Fin" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span> Fecha de seguimiento tercer trimestre:</td>
				<td>Desde: 
				<input type="text" name="progSeguimiento3Ini" maxlength="10" size="10" value='<c:out value="${sessionScope.parametroProgramacionFechas.progSeguimiento3Ini}"/>'></input>
				<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="imgProgSeguimiento3Ini" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
				</td>
				<td>Hasta:
				<input type="text" name="progSeguimiento3Fin" maxlength="10" size="10" value='<c:out value="${sessionScope.parametroProgramacionFechas.progSeguimiento3Fin}"/>'></input>
				<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="imgProgSeguimiento3Fin" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span> Fecha de seguimiento cuarto trimestre:</td>
				<td>Desde: 
				<input type="text" name="progSeguimiento4Ini" maxlength="10" size="10" value='<c:out value="${sessionScope.parametroProgramacionFechas.progSeguimiento4Ini}"/>'></input>
				<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="imgProgSeguimiento4Ini" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
				</td>
				<td>Hasta:
				<input type="text" name="progSeguimiento4Fin" maxlength="10" size="10" value='<c:out value="${sessionScope.parametroProgramacionFechas.progSeguimiento4Fin}"/>'></input>
				<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="imgProgSeguimiento4Fin" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
				</td>
			</tr>
		</table>	
	</form>
</body>
<script type="text/javascript">
Calendar.setup({inputField:"progPlaneacionIni",ifFormat:"%d/%m/%Y",button:"imgProgPlaneacionIni",align:"Br"});
Calendar.setup({inputField:"progPlaneacionFin",ifFormat:"%d/%m/%Y",button:"imgProgPlaneacionFin",align:"Br"});
Calendar.setup({inputField:"progSeguimiento1Ini",ifFormat:"%d/%m/%Y",button:"imgProgSeguimiento1Ini",align:"Br"});
Calendar.setup({inputField:"progSeguimiento1Fin",ifFormat:"%d/%m/%Y",button:"imgProgSeguimiento1Fin",align:"Br"});
Calendar.setup({inputField:"progSeguimiento2Ini",ifFormat:"%d/%m/%Y",button:"imgProgSeguimiento2Ini",align:"Br"});
Calendar.setup({inputField:"progSeguimiento2Fin",ifFormat:"%d/%m/%Y",button:"imgProgSeguimiento2Fin",align:"Br"});
Calendar.setup({inputField:"progSeguimiento3Ini",ifFormat:"%d/%m/%Y",button:"imgProgSeguimiento3Ini",align:"Br"});
Calendar.setup({inputField:"progSeguimiento3Fin",ifFormat:"%d/%m/%Y",button:"imgProgSeguimiento3Fin",align:"Br"});
Calendar.setup({inputField:"progSeguimiento4Ini",ifFormat:"%d/%m/%Y",button:"imgProgSeguimiento4Ini",align:"Br"});
Calendar.setup({inputField:"progSeguimiento4Fin",ifFormat:"%d/%m/%Y",button:"imgProgSeguimiento4Fin",align:"Br"});
</script>
</html>