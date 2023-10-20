<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!--	VERSION		FECHA		AUTOR			DESCRIPCION -->
<!-- 		1.0		28/11/2019	JORGE CAMACHO	Version Inicial

-->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="cambioActividadesVO" class="poa.aprobacionActividades.vo.PlaneacionVO" scope="session"/>
<jsp:useBean id="paramsVO" class="poa.aprobacionActividades.vo.ParamsVO" scope="page"/>
<jsp:useBean id="params2VO" class="poa.aprobarCambios.vo.ParamsVO" scope="page"/>

<html>

	<head>
	
		<link rel="stylesheet" type="text/css" media="all" href='<c:url value="/etc/css/calendar-win2k-1.css"/>' title="win2k-cold-1" />
		
		<script type="text/javascript" src='<c:url value="/etc/js/calendar.js"/>'></script>
		<script type="text/javascript" src='<c:url value="/etc/js/lang/calendar-es.js"/>'></script>
		<script type="text/javascript" src='<c:url value="/etc/js/calendar-setup.js"/>'></script>
		<script languaje="javaScript">
			var nav4=window.Event ? true : false;

			function acepteNumeros(eve) {
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			
				
			function borrar_combo(combo) {
			
				for(var i = combo.length - 1; i >= 0; i--) {
				
					if(navigator.appName == "Netscape")
						combo.options[i] = null;
					else
						combo.remove(i);
				}
				
				combo.options[0] = new Option("-Seleccione una-","-99");
				
			}
			
			
			function cambioTipo(obj) {
		
				document.frmNuevo.plaCronograma1.value = "";
				document.frmNuevo.plaCronograma2.value = "";
				document.frmNuevo.plaCronograma3.value = "";
				document.frmNuevo.plaCronograma4.value = "";
				document.frmNuevo.plaMetaAnualCantidad.value = "";
				
				document.frmNuevo.plaCronograma1.disabled = false;
				document.frmNuevo.plaCronograma2.disabled = false;
				document.frmNuevo.plaCronograma3.disabled = false;
				document.frmNuevo.plaCronograma4.disabled = false;
				
				if(obj.options[obj.selectedIndex].text == 'Sumatoria') {
				
					document.frmNuevo.plaMetaAnualCantidad.disabled = true;
					
				} else if(obj.options[obj.selectedIndex].text == 'Constante') {
				
					document.frmNuevo.plaMetaAnualCantidad.disabled = false;
					
					document.frmNuevo.plaCronograma1.disabled = true;
					document.frmNuevo.plaCronograma2.disabled = true;
					document.frmNuevo.plaCronograma3.disabled = true;
					document.frmNuevo.plaCronograma4.disabled = true;
					
				} else if(obj.options[obj.selectedIndex].text == 'Demanda') {
				
					document.frmNuevo.plaMetaAnualCantidad.disabled = true;
					
					document.frmNuevo.plaCronograma1.disabled = true;
					document.frmNuevo.plaCronograma2.disabled = true;
					document.frmNuevo.plaCronograma3.disabled = true;
					document.frmNuevo.plaCronograma4.disabled = true;
					
					document.frmNuevo.plaMetaAnualCantidad.value = 1;
					document.frmNuevo.plaCronograma1.value = 1;
					document.frmNuevo.plaCronograma2.value = 1;
					document.frmNuevo.plaCronograma3.value = 1;
					document.frmNuevo.plaCronograma4.value = 1;
					
				}
				
			}
			
			
			function calcularCantidad() {
			
				if(limpiarCaracteres()) {
				
					if(document.frmNuevo.plaTipoMeta.options[document.frmNuevo.plaTipoMeta.selectedIndex].text == 'Sumatoria') {
					
						document.frmNuevo.plaMetaAnualCantidad.value = parseInt(esNulo(document.frmNuevo.plaCronograma1.value)) +
																	parseInt(esNulo(document.frmNuevo.plaCronograma2.value)) +
																	parseInt(esNulo(document.frmNuevo.plaCronograma3.value)) +
																	parseInt(esNulo(document.frmNuevo.plaCronograma4.value));
						
						document.frmNuevo.plaMetaAnualCantidad.value = document.frmNuevo.plaMetaAnualCantidad.value;
					}
				}
				
			}
			
	
			function calcularCronograma() {
				
				if(document.frmNuevo.plaTipoMeta.options[document.frmNuevo.plaTipoMeta.selectedIndex].text == 'Constante') {
				
					document.frmNuevo.plaCronograma1.value = document.frmNuevo.plaMetaAnualCantidad.value;
					document.frmNuevo.plaCronograma2.value = document.frmNuevo.plaMetaAnualCantidad.value;
					document.frmNuevo.plaCronograma3.value = document.frmNuevo.plaMetaAnualCantidad.value;
					document.frmNuevo.plaCronograma4.value = document.frmNuevo.plaMetaAnualCantidad.value;
					
				}
				
			}
			
			
			function esNulo(objValor) {
			
				var numero = new Number(objValor);
			
    			if(isNaN(numero)) {
         			return 0;
    			} else {
        			return numero;
    			}
    			
			}
			
		
			function ajaxLinea() {
			
				borrar_combo(document.frmNuevo.plaLineaAccion); 
				document.frmAjax.ajax[0].value=document.frmNuevo.plaAreaGestion.value;
				document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_LINEA}"/>';
		 		document.frmAjax.target="frameAjax";
				document.frmAjax.submit();
				
			}
			
		
			function ajaxTipoActividad() {
			
				if(document.frmNuevo.tipoActividad.value == "PIMA - PIGA") {
					document.getElementById('tablaPimaPiga').style.display = 'table';
				} else {
					document.getElementById('tablaPimaPiga').style.display = 'none';
				} 
				
			}
			

			function ajaxAccionMejoramiento() {
			
				if (document.frmNuevo.accionMejoramiento.value == "otras") {
					document.getElementById('filaAccionMejoramientoOtras').style.display = 'table-row';
				} else {
					document.getElementById('filaAccionMejoramientoOtras').style.display = 'none';
				}
				
			}
		
		
			function ajaxTipoGasto() {
			
				//document.frmNuevo.plaRubroGasto.innerHTML = "";
				if(document.frmNuevo.tipoGasto.value != "-99"){ 
					
					//if(document.frmNuevo.plaTipoGasto.value == "Inversion"){
						//document.getElementById('labelProyecto').innerHTML = 'Nombre del Proyecto de Inversion';
						
					//}else if (document.frmNuevo.plaTipoGasto.value == "Funcionamiento"){
						//document.getElementById('labelProyecto').innerHTML = 'Subnivel de Gastos Generales ó Servicios Personales';
						
					//}
		
					document.frmAjax.ajax[3].value = document.frmNuevo.tipoGasto.value;
					document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_TIPOGASTO}"/>';
			 		document.frmAjax.target="frameAjax";
					document.frmAjax.submit();
				}//else{
					//document.frmNuevo.rubroGasto.innerHTML = "";
					//document.frmNuevo.rubroGasto.options[0] = new Option('-Seleccione Uno-','-99');
				//}
			}
			
			
			function ajaxFuenteFinanciacion() {
			
				if (document.frmNuevo.fuenteFinanciacion.value == "otros recursos"){
					document.getElementById('filaFuenteFinanciacionOtro').style.display = 'table-row';
				} else {
					document.getElementById('filaFuenteFinanciacionOtro').style.display = 'none';
				}
				
			}
			
				
			function setUnidad() {
			
				var forma = document.frmNuevo;
				
				if(parseInt(forma.plaUnidades[forma.plaMetaAnualUnidad.selectedIndex].value)==1) {
					forma.plaMetaAnualCual.disabled=false;
				} else {
					forma.plaMetaAnualCual.value="";
					forma.plaMetaAnualCual.disabled=true;
				}
				
			}
			

			function lanzar(tipo) {
			
		  		document.frmNuevo.tipo.value=tipo;
		  		document.frmNuevo.cmd.value=document.frmNuevo.BUSCAR.value;
				document.frmNuevo.target="";
				document.frmNuevo.submit();
				
			}	
				
		
			function editar(n,m,o) {
			
				if(document.frmNuevo.id) {
				
					document.frmNuevo.id.value=n;
					document.frmNuevo.id2.value=m;
					document.frmNuevo.id3.value=o;
					document.frmNuevo.cmd.value=document.frmNuevo.EDITAR.value;
					document.frmNuevo.submit();
					
				}
				
			}
			

			function hacerValidaciones_frmNuevo(forma) {
			
				validarLista(forma.plaVigencia, "- Vigencia", 1)
				//if(  document.getElementById('placcodobjetivo').style.display == 'none' ){
				// validarCampo(forma.plaObjetivo, "- Objetivo Estratégico", 1, 320)
				//}else{
				//validarLista(forma.placcodobjetivo, "- Objetivo Estratégico", 1)
				//}
				validarCampo(forma.plaActividad, "- Actividad / Tarea", 1, 320)
				validarLista(forma.plaAreaGestion, "- Área de Gestión", 1)
				//validacion de ponderador
				//validarFloat(forma.plaPonderador, "- Ponderador (máximo "+forma.plaPonderadores.options[forma.plaAreaGestion.selectedIndex].value+" %)", 0.01, forma.plaPonderadores.options[forma.plaAreaGestion.selectedIndex].value)
				validarFloat(forma.plaPonderador, "- La suma del ponderador del área '"+forma.plaAreaGestion.options[forma.plaAreaGestion.selectedIndex].text+"' no puede superar el "+forma.plaPonderadores.options[forma.plaAreaGestion.selectedIndex].text+"% (queda el "+forma.plaPonderadores.options[forma.plaAreaGestion.selectedIndex].value+" %)", 0.01, forma.plaPonderadores.options[forma.plaAreaGestion.selectedIndex].value)
				//fin de validacion de ponderador
				validarLista(forma.plaLineaAccion, "- Línea de Acción / Proceso / Proyecto", 1)
				validarLista(forma.plaTipoMeta, "- Tipo de Meta", 1)
				validarEntero(forma.plaMetaAnualCantidad, "- Cantidad", 1, 9999999)
				validarLista(forma.plaMetaAnualUnidad, "- Unidad de medida", 1)
				
				//validacion del cual
				if(parseInt(forma.plaUnidades[forma.plaMetaAnualUnidad.selectedIndex].value)==1){
					validarCampo(forma.plaMetaAnualCual, "- ¿Cuál?", 1, 100)
				}
				
				validarCampo(forma.plaResponsable, "- Nombre del responsable", 1, 250)
				
				validarCampo(forma.plaCronograma1, "- Cronograma 1er Trimestre", 1, 7)
				validarCampo(forma.plaCronograma2, "- Cronograma 2do Trimestre", 1, 7)
				validarCampo(forma.plaCronograma3, "- Cronograma 3er Trimestre", 1, 7)
				validarCampo(forma.plaCronograma4, "- Cronograma 4to Trimestre", 1, 7)
			
				if (forma.tipoActividad.value == "PIMA - PIGA"){
					
					if (document.frmNuevo.accionMejoramiento.value == "otras"){
						validarCampo(forma.CUAL, "- ¿Cual Accion de Mejoramiento / Programa Ambiental?", 1, 100);
					}else{
						validarLista(forma.accionMejoramiento, "- Accion de Mejoramiento / Programa Ambiental", 1);
					}
					
					validarLista(forma.tipoGasto, "- Tipo de Gasto", 1);
					validarLista(forma.rubroGasto, "- Rubro de Gasto", 1);
					
					if(forma.tipoGasto.value == "Inversion"){
						validarCampo(forma.subnivelGasto, "- Proyecto de Inversion", 1, 100);		
					}else if (forma.tipoGasto.value == "Funcionamiento"){
						validarCampo(forma.subnivelGasto, "- Gastos generales o Servicios Personales", 1, 100);
					}
					
					if (document.frmNuevo.fuenteFinanciacion.value == "otros recursos"){
						validarCampo(forma.fuenteFinanciacionOtros, "-Otra Fuente Financiacion", 1, 100);
					}else{
						validarLista(forma.fuenteFinanciacion, "- Fuente Financiacion", 1);
					}
					validarCampo(forma.montoAnual, "- Monto Anual", 0, 100);
					if(forma.montoAnual.value == ""){
						appendErrorMessage("- Monto Anual");
						return false;
					}
					
				}else if(!(forma.tipoActividad.value == "POA")){
					validarLista(forma.tipoActividad, "- Tipo de Actividad", 1);
				}
				
				//validarCampo(forma.SEGFECHACUMPLIMT,'- Fecha Prevista de Entrega');
			}
			

			function guardar() {
		
				//alert(document.frmNuevo.SEGFECHACUMPLIMT.value);
				var vigencia = document.frmNuevo.plaVigencia.value;
				var fechacumpl = document.frmNuevo.SEGFECHACUMPLIMT.value.split("/");
				
				if(vigencia != fechacumpl[2])
				{
					alert("La fecha de entrega debe pertenecer al año de vigencia");
					return false;
				}
				
				if(validarForma(document.frmNuevo)) {
				
					validarCampos(document.frmNuevo);
					 
					document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
					document.frmNuevo.submit();
				}
				
			}
			
		
			function validarCampos(forma) {
			
				// Campo Cual
				forma.plaMetaAnualCual.disabled = false;
				
				if(trim(forma.plaMetaAnualCual.value) == '') {
					forma.plaMetaAnualCual.value=' ';
				}
				
			}
			
			
			function limpiarCaracteres() {
			
				var retorno = true;
				var crono1 = document.getElementsByName("plaCronograma1")[0].value;
				var crono2 = document.getElementsByName("plaCronograma2")[0].value;
				var crono3 = document.getElementsByName("plaCronograma3")[0].value;
				var crono4 = document.getElementsByName("plaCronograma4")[0].value;
				
				if(!Number.isInteger(parseInt(crono1)) || parseInt(crono1) < 0) {
				
					if(crono1 != "") {
					
						alert("Debe registrar números enteros positivos en el primer trimestre");
						document.getElementsByName("plaCronograma1")[0].value = "";
						retorno = false;
					}
					
				} else {
				
					document.getElementsByName("plaCronograma1")[0].value = parseInt(crono1);
					
				}
				
				if(!Number.isInteger(parseInt(crono2)) || parseInt(crono2) < 0) {
				
					if(crono2 != "") {
					
						alert("Debe registrar números enteros positivos en el segundo trimestre");
						document.getElementsByName("plaCronograma2")[0].value = "";
						retorno = false;
						
					}
					
				} else {
				
					document.getElementsByName("plaCronograma2")[0].value = parseInt(crono2);
					
				}
				
				if(!Number.isInteger(parseInt(crono3)) || parseInt(crono3) < 0) {
				
					if(crono3 != "") {
					
						alert("Debe registrar números enteros positivos en el tercer trimestre");
						document.getElementsByName("plaCronograma3")[0].value = "";
						retorno = false;
						
					}
					
				} else {
				
					document.getElementsByName("plaCronograma3")[0].value = parseInt(crono3);
					
				}
				
				if(!Number.isInteger(parseInt(crono4)) || parseInt(crono4) < 0) {
				
					if(crono4 != "") {
					
						alert("Debe registrar números enteros positivos en el cuarto trimestre");
						document.getElementsByName("plaCronograma4")[0].value = "";
						retorno = false;
						
					}
					
				} else {
				
					document.getElementsByName("plaCronograma4")[0].value = parseInt(crono4);
					
				}
				
				return retorno;
					
			}
			
			
			function bloquearTipoMeta() {
				
				if(document.frmNuevo.plaTipoMeta.options[document.frmNuevo.plaTipoMeta.selectedIndex].text == 'Sumatoria') {
				
					document.frmNuevo.plaMetaAnualCantidad.disabled = true;
					
				} else if(document.frmNuevo.plaTipoMeta.options[document.frmNuevo.plaTipoMeta.selectedIndex].text == 'Demanda') {
				
					document.frmNuevo.plaMetaAnualCantidad.disabled = true;
					
					document.frmNuevo.plaCronograma1.disabled = true;
					document.frmNuevo.plaCronograma2.disabled = true;
					document.frmNuevo.plaCronograma3.disabled = true;
					document.frmNuevo.plaCronograma4.disabled = true;
					
				} else if(document.frmNuevo.plaTipoMeta.options[document.frmNuevo.plaTipoMeta.selectedIndex].text == 'Constante') {
				
					document.frmNuevo.plaMetaAnualCantidad.disabled = false;
					
					document.frmNuevo.plaCronograma1.disabled = true;
					document.frmNuevo.plaCronograma2.disabled = true;
					document.frmNuevo.plaCronograma3.disabled = true;
					document.frmNuevo.plaCronograma4.disabled = true; 
				
				}
			
			}
		</script>
		
	</head>
	
	<c:import url="/mensaje.jsp"/>
	
	<body onLoad="mensaje(document.getElementById('msg'));">
		
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="269">
				<a href="javaScript:lanzar(<c:out value="${params2VO.FICHA_CAMBIO}"/>)"><img src='<c:url value="/etc/img/tabs/aprobarCambios_0.gif"/>' alt="Aprobar Cambios POA" height="26" border="0"></a>										
					<img src='<c:url value="/etc/img/tabs/realizarCambio_1.gif"/>' alt="Realizar Cambio POA" height="26" border="0">
				</td>
			</tr>
		</table>

		<table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
			<tr>
				<td width="5%"><span class="style2"></span> Vigencia:</td>
				<td width="25%" colspan="1">
					<c:out value="${sessionScope.cambio2VO.vigencia}"/>				
				</td>
				<c:if test="${sessionScope.cambio2VO.fechaSol!=null}">
					<td><b>Fecha Solicitud:</b>
						<c:out value="${sessionScope.cambio2VO.fechaSol}"/>
					</td>
				</c:if>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Asunto:</td>
				<td colspan="2" align="left">
					<input type="text" name="asunto" maxlength="180" size="77" <c:out value="${sessionScope.cambio2VO.disabled}"/> value='<c:out value="${sessionScope.cambio2VO.asunto}"/>'></input>
				</td>
			</tr>	
			<tr>
				<td><span class="style2">*</span> Solicitud:</td>
				<td colspan="2" align="left">
					<textarea name="solicitud" cols="77" rows="3"  <c:out value="${sessionScope.cambio2VO.disabled}"/> <c:out value="${sessionScope.cambio2VO.solicitud}"/> onKeyDown="textCounter(this,1800,1800);" onKeyUp="textCounter(this,320,320);" ><c:out value="${sessionScope.cambio2VO.solicitud}"/></textarea>
				</td>
			</tr>	
			<tr>
				<td width="20%"><span class="style2">*</span> Estado:</td>
				<td width="25%" colspan="1">
					<b><c:out value="${sessionScope.cambio2VO.nombreEstado}"/></b>
				</td>
				<c:if test="${sessionScope.cambio2VO.fechaEstado!=null}">
					<td><b>Fecha Estado:</b> <c:out	value="${sessionScope.cambio2VO.fechaEstado}" /></td>
				</c:if>
			</tr>
		</table>	
			
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<img src='<c:url value="/etc/img/tabs/actividades_1.gif"/>' alt="Actividades" height="26" border="0">
					<a href="javaScript:lanzar(<c:out value="${params2VO.FICHA_ACTIVIDAD_SIN}"/>)"><img src='<c:url value="/etc/img/tabs/problemas_0.gif"/>' alt="Actividades sin recursos" height="26" border="0"></a>
				</td>
			</tr>
		</table>
		
		<div style="width:100%;height:200px;overflow:scroll;vertical-align:top;background-color:#ffffff;" id="tabla">
			<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
			 	<caption>POA. <c:out value="${sessionScope.filtroCambioVO.filNombreColegio}"/></caption>
			 	<c:if test="${empty requestScope.listaActividades}">
					<tr>
						<th class="Fila1" colspan='6'>No hay actividades</th>
					</tr>
				</c:if>
				<c:if test="${!empty requestScope.listaActividades}">
					<tr>
						<th width='20' class="EncabezadoColumna" colspan="2">&nbsp;</th>
						<td class="EncabezadoColumna" align="center">Objetivo Estratégico</td>
						<td class="EncabezadoColumna" align="center">Actividad / Tarea</td>
						<td class="EncabezadoColumna" align="center">Área de Gestión</td>
						<td class="EncabezadoColumna" align="center">Línea de Acción</td>
						<td class="EncabezadoColumna" align="center">Ponderador</td>
					</tr>
					<c:forEach begin="0" items="${requestScope.listaActividades}" var="lista" varStatus="st"> 
						<tr>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaConsecutivo}"/></td>
							<th class='Fila<c:out value="${st.count%2}"/>'>
								<a href='javaScript:editar(<c:out value="${lista.plaVigencia}"/>,<c:out value="${lista.plaInstitucion}"/>,<c:out value="${lista.plaCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
							</th>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.placcodobjetivoText}"/></td> 
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaActividad}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaAreaGestionNombre}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaLineaAccionNombre}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaPonderador}"/></td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
		</div>
		
		<form method="post" name="frmAjax" action='<c:url value="/poa/aprobacionActividades/Ajax.do"/>'>
			<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ACTIVIDAD}"/>'>
			<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
		</form>
		
		<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/poa/aprobarCambios/SaveAct.jsp"/>'>
		
			<input type="hidden" name="tipo" value='<c:out value="${params2VO.FICHA_ACTIVIDAD}"/>'>
			<input type="hidden" name="cmd" value=''>
			<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
			<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
			<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
			<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
			<input type="hidden" name="plaInstitucion" value='<c:out value="${sessionScope.filtroCambioVO.filEntidad}"/>'>
			
			<select name="plaPonderadores" style="display:none;">
				<option value="0">-</option>
				<c:forEach begin="0" items="${requestScope.listaAreaGestion}" var="area">
					<option value='<c:out value="${area.padre}"/>'><c:out value="${area.padre2}"/></option>
				</c:forEach>
			</select>
			<input type="hidden" name="id" value=""><input type="hidden" name="id2" value=""><input type="hidden" name="id3" value="">
		
			<!--<c:forEach begin="0" items="${requestScope.listaAreaGestion}" var="area">
			<input type="hidden" name="plaPonderadores" value='<c:out value="${area.padre}"/>'>
			</c:forEach>-->
			<input type="hidden" name="plaUnidades" value='0'>
			<c:forEach begin="0" items="${requestScope.listaUnidadMedida}" var="unidad">
				<input type="hidden" name="plaUnidades" value='<c:out value="${unidad.padre}"/>'>
			</c:forEach>
			<c:forEach begin="0" items="${requestScope.listaFuenteFinanciera}" var="fuente">
				<input type="hidden" name="plaFuentes" value='<c:out value="${fuente.padre}"/>'>
			</c:forEach>
			<c:if test="${sessionScope.cambioActividadesVO.formaEstado==1}">
				<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
					<caption>Detalle de actividad</caption>
					<tr>
						<td width="45%">
							<c:if test="${sessionScope.NivelPermiso==2}">
    							<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">								
	    					</c:if>					
			  			</td>
			 		</tr>	
		 		</table>
		 		
	  			<table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
					<tr>
						<td width="15%" title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblVigencia}"/>'>
							<span class="style2">*</span> Vigencia:
						</td>
						<td width="85%" colspan="3">
							<select name="plaVigencia" style="width:220px;" <c:out value="${sessionScope.cambioActividadesVO.plaDisabled}"/> >
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
									<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.cambioActividadesVO.plaVigencia}">selected</c:if>><c:out value="${vig.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
		 			</tr>
					<tr>
						<td width="15%" title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblObjetivo}"/>'>
							<span class="style2">*</span> Objetivo Estratégico:
						</td>
						<td width="85%" colspan="3">
				 			<select style="width:700px" name="placcodobjetivo" id="placcodobjetivo" >
								<c:forEach begin="0" items="${sessionScope.listaObjetivos}" var="vig">
									<option  title='<c:out value="${vig.nombre}"/>' value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo == sessionScope.cambioActividadesVO.placcodobjetivo  }">selected</c:if>><c:out value="${vig.nombre}"/></option>
								</c:forEach>											
							</select>
							<!-- textarea name="plaObjetivo" cols="70" rows="3" <c:out value="${sessionScope.cambioActividadesVO.plaDisabled}"/> onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);" ><c:out value="${sessionScope.cambioActividadesVO.plaObjetivo}"/></textarea-->
						</td>
		 			</tr>
		 			<!-- BEGIN Tipo de Actividad -->
				 	<tr>
						<td width="15%" title='<c:out value="${sessionScope.seguimientoVO.lblAreaGestion}"/>'>
							<span class="style2">*</span> Tipo de Actividad:
						</td>
						<td width="85%" colspan="3">
							<select name="tipoActividad" style="width:220px;" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> onchange="ajaxTipoActividad()">
								<option value="-99" selected>-Seleccione uno-</option>
								<option value="POA" <c:if test="${sessionScope.cambioActividadesVO.tipoActividad != null and 'POA' eq sessionScope.cambioActividadesVO.tipoActividad}">selected</c:if>>POA</option>
								<option value="PIMA - PIGA" <c:if test="${sessionScope.cambioActividadesVO.tipoActividad != null and 'PIMA - PIGA' eq sessionScope.cambioActividadesVO.tipoActividad}">selected</c:if>>PIMA - PIGA</option>
							</select>
						</td>
					</tr>
					<c:if test="${sessionScope.cambioActividadesVO.tipoActividad != null and 'PIMA - PIGA' eq sessionScope.cambioActividadesVO.tipoActividad}">
						<tr>
				 			<td>
				 				<span class="style2">*</span> Acción de mejoramiento / Programa Ambiental:
				 			</td>
							<td align="left">
								<select name="accionMejoramiento" style="width:220px;" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> onchange="ajaxAccionMejoramiento()">
									<option value="-99" selected>-Seleccione uno-</option>
									<c:forEach begin="0" items="${requestScope.listaAccionMejoramiento}" var="vig">
										<option value="<c:out value="${vig[1]}"/>" <c:if test="${vig[1]==sessionScope.cambioActividadesVO.accionMejoramiento}">selected</c:if>><c:out value="${vig[0]}"/></option>
									</c:forEach>
								</select>
							</td>
						</tr>
					</c:if>
					<!-- INICIO acción de mejoramiento 'otras' -->
					<tr id="filaAccionMejoramientoOtras" style="display: <c:if test="${sessionScope.cambioActividadesVO.accionMejoramiento=='otras'}">table-row;</c:if><c:if test="${sessionScope.cambioActividadesVO.accionMejoramiento!='otras'}">none;</c:if>">
						<td width="15%">
							<span class="style2">*</span>¿ Cual Acción de Mejoramiento / Programa Ambiental ?
						</td>
						<td width="85%" colspan="3">
							<textarea name="CUAL" <c:out value="${sessionScope.cambioActividadesVO.plaDisabled}"/> maxlength="100" cols="110" rows="3" onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);"><c:out value="${sessionScope.cambioActividadesVO.CUAL}"/></textarea>
						</td>
					</tr>
					<!-- END Tipo de actividad -->
					<tr>
						<td width="15%" title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblActividad}"/>'>
							<span class="style2">*</span> Actividad / Tarea:
						</td>
						<td width="85%" colspan="3">
							<textarea name="plaActividad" cols="110" rows="3" <c:out value="${sessionScope.cambioActividadesVO.plaDisabled}"/> onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);" ><c:out value="${sessionScope.cambioActividadesVO.plaActividad}"/></textarea>
						</td>
				 	</tr>
				 	<tr>
						<td width="15%" title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblAreaGestion}"/>'>
							<span class="style2">*</span> Área de Gestión:
						</td>
						<td width="30%">
							<select name="plaAreaGestion" style="width:220px;" <c:out value="${sessionScope.cambioActividadesVO.plaDisabled}"/> >
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaAreaGestion}" var="area">
									<option value="<c:out value="${area.codigo}"/>" <c:if test="${area.codigo==sessionScope.cambioActividadesVO.plaAreaGestion}">selected</c:if>><c:out value="${area.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
						<td width="20%" align="right" title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblLineaAccion}"/>'>
							<span class="style2">*</span> Línea de Acción / Proceso / Proyecto:
						</td>
						<td width="35%" align="left">
							<select name="plaLineaAccion" style="width:300px;">
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaLineaAccion}" var="linea">
									<option value="<c:out value="${linea.codigo}"/>" <c:if test="${linea.codigo==sessionScope.cambioActividadesVO.plaLineaAccion}">selected</c:if>><c:out value="${linea.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
				 	</tr>
				 	<tr>
						<td width="15%" title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblPonderador}"/>'>
							<span class="style2">*</span> Ponderador:
						</td>
						<td width="30%">
							<input type="text" name="plaPonderador" maxlength="5" size="3" <c:out value="${sessionScope.cambioActividadesVO.plaDisabled}"/> value='<c:out value="${sessionScope.cambioActividadesVO.plaPonderador}"/>'></input>%
						</td>
						<td width="20%" align="right" title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblTipoMeta}"/>'>
							<span class="style2">*</span> Tipo de Meta:
						</td>
						<td width="35%" align="left">
							<select name="plaTipoMeta" style="width:300px;" <c:out value="${sessionScope.cambioActividadesVO.plaDisabled}"/> onchange="javaScript:cambioTipo(this)">
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaTipoMeta}" var="tipoMeta">
									<option value="<c:out value="${tipoMeta.codigo}"/>" <c:if test="${tipoMeta.codigo==sessionScope.cambioActividadesVO.plaTipoMeta}">selected</c:if>><c:out value="${tipoMeta.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
			 		</tr>
			 	</table>
			 	<br>
				
				<table align="center" width="95%" border="1" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<table align="center" width="100%" border="0" cellpadding="1" cellspacing="0">
								<tr>
									<th colspan="6" class="Fila0">Meta Anual / Producto Final</th>
								</tr>
								<tr>
									<td width="15%" title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblMetaAnualUnidad}"/>'>
										<span class="style2">*</span> Unidad de Medida:
									</td>
									<td width="30%">
										<select name="plaMetaAnualUnidad" style="width:220px;" <c:out value="${sessionScope.cambioActividadesVO.plaDisabled}"/> onchange="javaScript:setUnidad()">
											<option value="-99" selected>-Seleccione uno-</option>
											<c:forEach begin="0" items="${requestScope.listaUnidadMedida}" var="unidad">
												<option value="<c:out value="${unidad.codigo}"/>" <c:if test="${unidad.codigo==sessionScope.cambioActividadesVO.plaMetaAnualUnidad}">selected</c:if>><c:out value="${unidad.nombre}"/></option>
											</c:forEach>
										</select>
									</td>
									<td width="5%" title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblMetaAnualCual}"/>'>¿Cuál?:
									</td>
									<td width="30%">
										<input type="text" name="plaMetaAnualCual" maxlength="100" size="40" <c:out value="${sessionScope.cambioActividadesVO.plaDisabled}"/> value='<c:out value="${sessionScope.cambioActividadesVO.CUAL}"/>'></input>
									</td>
									<td width="10%" title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblMetaAnualCantidad}"/>'>
										<span class="style2">*</span> Cantidad:
									</td>
									<td width="10%">
										<input type="text" name="plaMetaAnualCantidad" maxlength="7" size="4" <c:out value="${sessionScope.cambioActividadesVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' onblur='calcularCronograma()' value='<c:out value="${sessionScope.cambioActividadesVO.plaMetaAnualCantidad}"/>'></input>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				
				<!-- 
				<table align="center" width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblFuenteFinanciera}"/>'><span class="style2">*</span> Fuente financiera:</td>
						<td>
							<c:forEach begin="0" items="${requestScope.listaFuenteFinanciera}" var="fuente">
								<input type="checkbox" name="plaFuenteFinanciera" <c:out value="${sessionScope.cambioActividadesVO.plaDisabled}"/> onclick="setFuente()" value='<c:out value="${fuente.codigo}"/>' <c:forEach begin="0" items="${sessionScope.cambioActividadesVO.plaFuenteFinanciera}" var="fuenteF"><c:if test="${fuente.codigo==fuenteF}">checked</c:if></c:forEach>><c:out value="${fuente.nombre}"/><br>
							</c:forEach>
						</td>
						<td  title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblPresupuesto}"/>'>Presupuesto:</td>
						<td>
						$<input type="text" name="plaPresupuesto" maxlength="10" size="5" <c:out value="${sessionScope.cambioActividadesVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.cambioActividadesVO.plaPresupuesto}"/>'></input>
						</td>
					</tr>						 	
				</table> -->
				
				<table align="center" width="95%" border="1" cellpadding="1" cellspacing="0">
					<tr>
						<td>
							<table align="center" width="100%" border="0" cellpadding="1" cellspacing="0">
								<tr>
									<th colspan="4" class="Fila0">Cronograma</th>
								</tr>
								<tr>
									<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblCronograma1}"/>' align="center"><span class="style2">*</span> 1er Trimestre:</td>
									<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblCronograma2}"/>' align="center"><span class="style2">*</span> 2do Trimestre:</td>
									<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblCronograma3}"/>' align="center"><span class="style2">*</span> 3er Trimestre:</td>
									<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblCronograma4}"/>' align="center"><span class="style2">*</span> 4to Trimestre:</td>
								</tr>
								<tr>
									<td align="center"><input type="text" name="plaCronograma1" maxlength="7" size="4" <c:out value="${sessionScope.cambioActividadesVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' onblur='calcularCantidad()' value='<c:out value="${sessionScope.cambioActividadesVO.plaCronograma1}"/>'></input></td>
									<td align="center"><input type="text" name="plaCronograma2" maxlength="7" size="4" <c:out value="${sessionScope.cambioActividadesVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' onblur='calcularCantidad()' value='<c:out value="${sessionScope.cambioActividadesVO.plaCronograma2}"/>'></input></td>
									<td align="center"><input type="text" name="plaCronograma3" maxlength="7" size="4" <c:out value="${sessionScope.cambioActividadesVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' onblur='calcularCantidad()' value='<c:out value="${sessionScope.cambioActividadesVO.plaCronograma3}"/>'></input></td>
									<td align="center"><input type="text" name="plaCronograma4" maxlength="7" size="4" <c:out value="${sessionScope.cambioActividadesVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' onblur='calcularCantidad()' value='<c:out value="${sessionScope.cambioActividadesVO.plaCronograma4}"/>'></input></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
		
				<table align="center" width="95%" border="1" cellpadding="1" cellspacing="0">
					<tr>
						<td>
							<table align="center" width="100%" border="0" cellpadding="1" cellspacing="0">
								<tr>
									<th colspan="2" class="Fila0">Fechas de Seguimiento</th>
								</tr>
								<tr>
									<td title='<c:out value="${sessionScope.filtroSeguimientoVO.lblSeguimiento1}"/>' align="right">Fecha Prevista de Entrega:
									</td>
									<!-- td title='<c:out value="${sessionScope.filtroSeguimientoVO.lblSeguimiento2}"/>' align="center">Fecha de entrega real</td-->
									<td>
		                    			<input name="SEGFECHACUMPLIMT" id="SEGFECHACUMPLIMT" type="text" size="10" maxlength="10" value='<c:out value="${sessionScope.cambioActividadesVO.SEGFECHACUMPLIMT}"/>' readOnly>
										<img 	src='<c:url value="/etc/img/calendario.gif"/>' 
												alt="Seleccione fecha" id="imgFechapla"
												style="cursor: pointer;"
												title="Date selector"
												onmouseover="this.style.background='red';"
												onmouseout="this.style.background=''" />                    	
									</td>
									<!--td>
		                    			<input name="SEGFECHAREALCUMPLIM" id="SEGFECHAREALCUMPLIM" type="text" size="10" maxlength="10" value='<c:out value="${sessionScope.cambioActividadesVO.SEGFECHAREALCUMPLIM}"/>' readOnly>                   	
									</td-->
								</tr>
							</table>
						</td>
					</tr>
				</table>
				
				<!--  table align="center" width="95%" border="1" cellpadding="1" cellspacing="0">
					<tr><td>
					<table align="center" width="100%" border="0"  cellpadding="1" cellspacing="0">
						<tr><th colspan="4" class="Fila0">Presupuesto</th></tr>
						<tr>
							<td title='<c:out value="${sessionScope.filtroSeguimientoVO.lblSeguimiento1}"/>' align="center">Presupuesto ejecutado:</td>
							<td><input type="text" name="PRESUPUESTOEJECUTADO" maxlength="115" size="8" <c:out value="${sessionScope.cambioActividadesVO.PRESUPUESTOEJECUTADO}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.cambioActividadesVO.PRESUPUESTOEJECUTADO}"/>'></input></td>
						</tr>
					</table>
					</td>
					</tr>
				</table-->
				<table align="center" width="95%" border="0" cellpadding="1" cellspacing="0">	
					<tr>
						<!-- <td width="20%" title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblFecha}"/>'><span class="style2">*</span> Fecha prevista para terminar actividad:</td>
						<td>
						<input type="text" name="plaFecha" maxlength="10" size="10" <c:out value="${sessionScope.cambioActividadesVO.plaDisabled}"/> value='<c:out value="${sessionScope.cambioActividadesVO.plaFecha}"/>'></input>
						<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="imgfecha" style='cursor:pointer;<c:if test="${sessionScope.cambioActividadesVO.plaDesHabilitado}">display:none;</c:if>' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
						</td> -->
						<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblResponsable}"/>'>
							<span class="style2">*</span> Nombre del Responsable:</td>
						<td>
							<input type="text" name="plaResponsable" maxlength="250" size="30" <c:out value="${sessionScope.cambioActividadesVO.plaDisabled}"/> value='<c:out value="${sessionScope.cambioActividadesVO.plaResponsable}"/>'></input>
						</td>
					</tr>
					<tr style="display:none">
						<td>
							<iframe name="frameAjax" id="frameAjax"></iframe>
						</td>
					</tr>	
				</table>
		
				<table align="center" id="tablaPimaPiga" border="0" width="95%" cellpadding="1" cellspacing="0" style="display: 
					<c:if test="${sessionScope.cambioActividadesVO.tipoActividad != null and sessionScope.cambioActividadesVO.tipoActividad eq 'PIMA - PIGA'}">table;</c:if>
					<c:if test="${sessionScope.cambioActividadesVO.tipoActividad != null and sessionScope.cambioActividadesVO.tipoActividad ne 'PIMA - PIGA'}">none;</c:if>
					<c:if test="${sessionScope.cambioActividadesVO.tipoActividad == null}">none;</c:if>">
				 	<caption>PIMA - PIGA</caption>
				 	
					<!-- FIN acción de mejoramiento 'otras' -->	
				 	<tr>
						<td width="30%"><span class="style2">*</span> Tipo de Gasto:
						</td>
						<td width="25%" >
							<select name="tipoGasto" style="width: 180px;" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> onchange="ajaxTipoGasto()">
								<option value="-99" selected>-Seleccione uno-</option>
								<option value="Inversion" <c:if test="${sessionScope.cambioActividadesVO.tipoGasto != null and 'Inversion' eq sessionScope.cambioActividadesVO.tipoGasto}">selected</c:if>>Inversion</option>
								<option value="Funcionamiento" <c:if test="${sessionScope.cambioActividadesVO.tipoGasto != null and 'Funcionamiento' eq sessionScope.cambioActividadesVO.tipoGasto}">selected</c:if>>Funcionamiento</option>
							</select>
						</td>
					</tr>
					<tr>	
						<td>
							<span class="style2">*</span> Rubro de Gasto:
						</td>
						<td width="25%" >
							<select name="rubroGasto" style="width: 180px;" <c:out value="${sessionScope.cambioActividadesVO.plaDisabled}"/>><option value="-99" selected>-Seleccione uno-</option>
								<c:forEach items="${requestScope.listaRubroGasto}" var="vig">
									<option value="<c:out value="${vig[1]}"/>" <c:if test="${sessionScope.cambioActividadesVO.rubroGasto eq vig[1]}">selected</c:if>><c:out value="${vig[0]}"/></option>
								</c:forEach>
							</select>
						</td>
					</tr>
				 	<tr>
						<td width="30%">
							<span class="style2">*</span> Subnivel del gasto: </td>
						<td width="25%">
							<input name="subnivelGasto" type="text"  value="<c:out value="${sessionScope.cambioActividadesVO.subnivelGasto}"/>" />
						</td>
					</tr>
				 	<!-- FILA FUENTE FINANCIACION -->
				 	<tr>	
						<td><span class="style2">*</span> Fuente de Financiacion: </td>
						<td width="25%" >
							<select id="fuenteFinanciacion" name="fuenteFinanciacion" style="width: 180px;" <c:out value="${sessionScope.cambioActividadesVO.plaDisabled}"/> onchange="ajaxFuenteFinanciacion()"> <option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaFuenteFinanciacion}" var="fuenteFinanciacionLista">
									<option value="<c:out value="${fuenteFinanciacionLista[1]}"/>" <c:if test="${fuenteFinanciacionLista[1]==sessionScope.cambioActividadesVO.fuenteFinanciacion}">selected</c:if>><c:out value="${fuenteFinanciacionLista[0]}"/></option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr id="filaFuenteFinanciacionOtro" style="display: <c:if test="${sessionScope.cambioActividadesVO.fuenteFinanciacion=='otros recursos'}">table-row;</c:if><c:if test="${sessionScope.cambioActividadesVO.fuenteFinanciacion!='otros recursos'}">none;</c:if>">
						<td><span class="style2">*</span>¿ Que otro recurso ?</td>
						<td colspan="3">
							<textarea name="fuenteFinanciacionOtros" maxlength="100" cols="70" rows="3" <c:out value="${sessionScope.cambioActividadesVO.plaDisabled}"/> onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);"><c:out  value="${sessionScope.cambioActividadesVO.fuenteFinanciacionOtros}"/></textarea>
						</td>
					</tr>
					<!-- FIN FILA FUENTE FINANCIACION -->
					
						
				 	<tr>
						<td width="30%"><span class="style2">*</span> Monto Anual: </td>
						<td width="25%">
							<input name="montoAnual" type="text"  value="<c:out value="${sessionScope.cambioActividadesVO.montoAnual}"/>" />
						</td>
					</tr>
				 	<tr>
						<td title='<c:out value="${sessionScope.seguimientoVO.lblAreaGestion}"/>' width="30%"><span class="style2">*</span> Presupuesto de participación: </td>
						<td width="25%">
							<input name="presupuestoParticipativo" type="text"  value="<c:out value="${sessionScope.cambioActividadesVO.presupuestoParticipativo}"/>" />
						</td>
					</tr>	
				</table>
		
				<script type="text/javascript">	
					Calendar.setup({ inputField : "SEGFECHACUMPLIMT",ifFormat : "%d/%m/%Y",button : "imgFechapla",align : "Br"}); 
					//Calendar.setup({ inputField : "SEGFECHAREALCUMPLIM",ifFormat : "%d/%m/%Y",button : "imgFechareal",align : "Br"});
				</script>
		
			</c:if>
			
		</form>
		
	</body>
	
	<c:if test="${sessionScope.cambioActividadesVO.formaEstado==1}">
		<script type="text/javascript">
	 	//   Calendar.setup({
	    //    inputField     :    "plaFecha",
	   	//     ifFormat       :    "%d/%m/%Y",
	  	//      button         :    "imgfecha",
	  	//      align          :    "Br"
	 	//   });
		</script>
		<script type="text/javascript">
			<c:if test="${!sessionScope.cambioActividadesVO.plaDesHabilitado}">
				setUnidad();
			</c:if>
			
			bloquearTipoMeta();
		</script>
	</c:if>

</html>