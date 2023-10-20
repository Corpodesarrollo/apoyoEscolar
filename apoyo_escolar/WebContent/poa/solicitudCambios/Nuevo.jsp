<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="cambioVO" class="poa.solicitudCambios.vo.CambioVO" scope="session"/>
<jsp:useBean id="paramsVO" class="poa.solicitudCambios.vo.ParamsVO" scope="page"/>

<html>

	<head>
	
		<style type='text/css'>
			.manual:hover{COLOR: red; FONT-WEIGHT: bold; FONT-FAMILY: Arial, Helvetica, sans-serif; FONT-SIZE: 10pt;}
			.manual:link{COLOR: red; FONT-WEIGHT: bold; FONT-FAMILY: Arial, Helvetica, sans-serif; FONT-SIZE: 10pt;}
			.manual:active{COLOR: red; FONT-WEIGHT: bold; FONT-FAMILY: Arial, Helvetica, sans-serif; FONT-SIZE: 10pt;}
			.manual:visited{COLOR: red; FONT-WEIGHT: bold; FONT-FAMILY: Arial, Helvetica, sans-serif; FONT-SIZE: 10pt;}
		</style>
		
		<style type="text/css">@import url('<c:url value="/etc/css/calendar-win2k-1.css"/>');</style>
		<script type="text/javascript" src="<c:url value="/etc/js/calendar.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/etc/js/lang/calendar-es.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/etc/js/calendar-setup.js"/>"></script>
		<script language="javaScript">
			var nav4=window.Event ? true : false;

			function acepteNumeros(eve) {
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
				
			function borrar_combo(combo) {
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("-Seleccione una-","-99");
			}
		
			function nuevo() {
				document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
				document.frmNuevo.action='<c:url value="/poa/solicitudCambios/Nuevo.do"/>';
				document.frmNuevo.submit();
			}
		
			function guardar() {
				if(validarForma(document.frmNuevo)){			
					document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
					document.frmNuevo.submit();
				}
			}	
			
			function hacerValidaciones_frmNuevo(forma) {
				validarLista(forma.vigencia, "- Vigencia", 1);		
				validarCampo(forma.asunto, "- Asunto", 1, 180);
				validarCampo(forma.solicitud, "- Solicitud", 1, 1800);
			}
		</script>
		
	</head>
	
	<c:import url="/mensaje.jsp"/>
	
	<body onLoad="mensaje(document.getElementById('msg'));">
	
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%" valign='top'>
					<div style="width:100%;height:250px;overflow:auto;vertical-align:top;background-color:#ffffff;">
						<c:import url="/poa/solicitudCambios/Filtro.do"/>
					</div>
				</td>
			</tr>
		</table>

		<form method="post" name="frmAjax" action='<c:url value="/poa/solicitudCambios/Ajax.do"/>'>
			<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_CAMBIO}"/>'>
			<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
		</form>
		
		<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/poa/solicitudCambios/Save.jsp"/>'>
			
			<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_CAMBIO}"/>'>
			<input type="hidden" name="cmd" value=''>
			<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
			<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
				
			<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
				<caption>Registro / Edición de Solicitud</caption>
				<tr>
					<td width="45%">
						<c:if test="${sessionScope.NivelPermiso==2}">
							<c:if test="${sessionScope.filtroCambioVO.filEstatoPOA==1}">
	    						<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
								<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
							</c:if>
	    				</c:if>							
			  		</td>
			 	</tr>	
		 	</table>
		 	
		  	<table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
				<tr>
					<td width="15%"><span class="style2">*</span> Vigencia:</td>
					<td width="70%" colspan="1">
						<select name="vigencia" style="width:120px;" <c:out value="${sessionScope.cambioVO.disabled}"/>>
							<option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
								<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.cambioVO.vigencia}">selected</c:if>><c:out value="${vig.nombre}"/></option>
							</c:forEach>
						</select>
					</td>
					<c:if test="${sessionScope.cambioVO.fechaSol!=null}">
						<td width="15%">
							<b>Fecha Solicitud:</b>
							<c:out value="${sessionScope.cambioVO.fechaSol}"/>
						</td>
					</c:if>
		 		</tr>	
				<tr>
					<td><span class="style2">*</span> Asunto:</td>
					<td colspan="3" align="left">
						<input type="text" name="asunto" maxlength="180" size="70" <c:out value="${sessionScope.cambioVO.disabled}"/> value='<c:out value="${sessionScope.cambioVO.asunto}"/>'></input>
					</td>	
		 		</tr>	
				<tr>
					<td><span class="style2">*</span> Solicitud:</td>
					<td colspan="3" align="left">
						<textarea name="solicitud" cols="72" rows="3" <c:out value="${sessionScope.cambioVO.solicitud}"/> onKeyDown="textCounter(this,1800,1800);" onKeyUp="textCounter(this,320,320);" ><c:out value="${sessionScope.cambioVO.solicitud}"/></textarea>
					</td>
		 		</tr>
		 		<tr>
					<td><span class="style2">*</span> Estado:</td>
					<td colspan="3" align="left">
						<b><c:out value="${sessionScope.cambioVO.nombreEstado}"/></b>
					</td>
				</tr>
				<tr>
					<c:if test="${sessionScope.cambioVO.observacion!=null}">
						<c:if test="${sessionScope.cambioVO.nombreEstado == 'Rechazado'}">
							<td>
								<span class="style2"><b>Observación por el rechazo del cambio:</b>
									<c:out value="${sessionScope.cambioVO.observacion}"/>
								</span>
							</td>
						</c:if>
					</c:if>
		 		</tr>
			</table>	
		
		</form>
		
	</body>
	
	<script type="text/javascript">
  	//  Calendar.setup({
  	//      inputField     :    "plaFecha",
   	//     ifFormat       :    "%d/%m/%Y",
   	//     button         :    "imgfecha",
   	//     align          :    "Br"
  	//  });
	</script>

</html>
