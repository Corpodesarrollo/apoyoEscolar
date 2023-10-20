<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!--	VERSION		FECHA		AUTOR			DESCRIPCION -->
<!-- 		1.0		28/11/2019	JORGE CAMACHO	Version Inicial -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="planeacionVO" class="poa.planeacion.vo.PlaneacionVO" scope="session"/>
<jsp:useBean id="paramsVO" class="poa.planeacion.vo.ParamsVO" scope="page"/>

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
			
			
			function ajaxArea() {
			
				borrar_combo(document.frmNuevo.plaAreaGestion); 
				document.frmAjax.ajax[0].value = document.frmNuevo.plaVigencia.value;
				document.frmAjax.ajax[1].value = document.frmNuevo.plaInstitucion.value;
				document.frmAjax.ajax[2].value = document.frmNuevo.plaCodigo.value;
				document.frmAjax.cmd.value = '<c:out value="${paramsVO.CMD_AJAX_AREA}"/>';
		 		document.frmAjax.target = "frameAjax";
				document.frmAjax.submit();
				
			}
			
	
			function ajaxLinea() {
			
				borrar_combo(document.frmNuevo.plaLineaAccion);
				document.frmAjax.ajax[0].value = document.frmNuevo.plaAreaGestion.value;
				document.frmAjax.cmd.value = '<c:out value="${paramsVO.CMD_AJAX_LINEA}"/>';
		 		document.frmAjax.target = "frameAjax";
				document.frmAjax.submit();
				
			}
			
			
			function ajaxTipoActividad() {
			
				if(document.frmNuevo.plaTipoActividad.value == "PIMA - PIGA") {
					document.getElementById('tablaPimaPiga').style.display = 'table';
					document.getElementById('tablaPresupuestoActividad').style.display = 'table';
					document.getElementById('filaAccionMejoramiento').style.display = 'table-row';
				} else {
					document.getElementById('tablaPimaPiga').style.display = 'none';
					document.getElementById('tablaPresupuestoActividad').style.display = 'none';
					document.getElementById('filaAccionMejoramiento').style.display = 'none';
					document.getElementById('filaAccionMejoramientoOtras').style.display = 'none';
				}
				
			}
			
	
			function ajaxTipoGasto() {
			
				if(document.frmNuevo.plaTipoGasto.value != "-99") {
					if(document.frmNuevo.plaTipoGasto.value == "Inversion") {
						document.getElementById('labelProyecto').innerHTML = 'Nombre del Proyecto de Inversión';
						
					} else if (document.frmNuevo.plaTipoGasto.value == "Funcionamiento") {
						document.getElementById('labelProyecto').innerHTML = 'Subnivel de Gastos Generales o Servicios Personales';
					}
					
					document.frmAjax.ajax[3].value=document.frmNuevo.plaTipoGasto.value;
					document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_TIPOGASTO}"/>';
			 		document.frmAjax.target="frameAjax";
					document.frmAjax.submit();
					
				} else {
					document.frmNuevo.plaRubroGasto.innerHTML = "";
					document.frmNuevo.plaRubroGasto.options[0] = new Option('-Seleccione uno-','-99');
				}
				
			}
			
	
			function ajaxAccionMejoramiento() {
				
				if (document.frmNuevo.plaAccionMejoramiento.value == "otras") {
					document.getElementById('filaAccionMejoramientoOtras').style.display = 'table-row';
				} else {
					document.getElementById('filaAccionMejoramientoOtras').style.display = 'none';
				}
				
			}
			
	
			function ajaxFuenteFinanciacion() {
			
				if (document.frmNuevo.plaFuenteFinanciacion.value == "otros recursos") {
					document.getElementById('filaFuenteFinanciacionOtro').style.display = 'table-row';
				} else {
					document.getElementById('filaFuenteFinanciacionOtro').style.display = 'none';
				}
				
			}
			
	
			function nuevo() {
			
				document.frmNuevo.cmd.value = document.frmNuevo.NUEVO.value;
				document.frmNuevo.action = '<c:url value="/poa/planeacion/Nuevo.do"/>';
				document.frmNuevo.submit();
				
			}
			

			function guardar() {
				
				if(validarForma(document.frmNuevo)) {
				
					document.getElementById("guardar1").disabled = true;
					
					validarCampos(document.frmNuevo);
					
					var vigencia = document.frmNuevo.plaVigencia.value;
					var fechacumpl = document.frmNuevo.SEGFECHACUMPLIMT.value.split("/");
					
					if(vigencia != fechacumpl[2]) {
						alert("La fecha de entrega debe pertenecer al año de vigencia");
						return false;
					}
					
					document.frmNuevo.cmd.value = document.frmNuevo.GUARDAR.value;
					document.frmNuevo.submit();
					
				}
				
			}
			

			function validarCampos(forma) {
			
				// Campo Cual
				forma.plaMetaAnualCual.disabled = false;
				
				if(trim(forma.plaMetaAnualCual.value) == '') {
					forma.plaMetaAnualCual.value = ' ';
				}
				
			}
			
	
			function hacerValidaciones_frmNuevo(forma) {
			
				validarLista(forma.plaVigencia, "- Vigencia", 1)
				
				if(document.getElementById('placcodobjetivo').style.display == 'none') {
				 	validarCampo(forma.plaObjetivo, "- Objetivo Estratégico", 1, 320)
				} else {
					validarLista(forma.placcodobjetivo, "- Objetivo Estratégico", 1)
				}
				
				validarLista(forma.plaTipoActividad, "- Tipo de Actividad", 1);
				validarCampo(forma.plaActividad, "- Actividad / Tarea", 1, 320)
				validarLista(forma.plaAreaGestion, "- Área de Gestión", 1)
				
				// Inicio validación de ponderador
				//validarFloat(forma.plaPonderador, "- Ponderador (máximo "+forma.plaPonderadores.options[forma.plaAreaGestion.selectedIndex].value+" %)", 0.01, forma.plaPonderadores.options[forma.plaAreaGestion.selectedIndex].value)
				validarFloat(forma.plaPonderador, "- La suma del ponderador del área '"+forma.plaAreaGestion.options[forma.plaAreaGestion.selectedIndex].text+"' no puede superar el "+forma.plaPonderadores.options[forma.plaAreaGestion.selectedIndex].text+"% (queda el "+forma.plaPonderadores.options[forma.plaAreaGestion.selectedIndex].value+" %)", 0.01, forma.plaPonderadores.options[forma.plaAreaGestion.selectedIndex].value)
				// Fin validación de ponderador
				
				validarLista(forma.plaLineaAccion, "- Línea de Acción / Proceso / Proyecto", 1)
				validarLista(forma.plaTipoMeta, "- Tipo de Meta", 1)
				validarEntero(forma.plaMetaAnualCantidad, "- Cantidad", 1, 9999999)
				validarLista(forma.plaMetaAnualUnidad, "- Unidad de Medida", 1)
				
				// Validación del cual
				if(parseInt(forma.plaUnidades[forma.plaMetaAnualUnidad.selectedIndex].value) == 1) {
					validarCampo(forma.plaMetaAnualCual, "- ¿Cuál?", 1, 100)
				}
				
				validarCampo(forma.plaResponsable, "- Nombre del Responsable", 1, 250)
		
				validarCampo(forma.plaCronograma1, "- Cronograma 1er Trimestre", 1, 7)
				validarCampo(forma.plaCronograma2, "- Cronograma 2do Trimestre", 1, 7)
				validarCampo(forma.plaCronograma3, "- Cronograma 3er Trimestre", 1, 7)
				validarCampo(forma.plaCronograma4, "- Cronograma 4to Trimestre", 1, 7)
				
				validarCampo(forma.SEGFECHACUMPLIMT,'- Fecha Prevista de Entrega');				
				
				if (forma.plaTipoActividad.value == "PIMA - PIGA") {
					
					if (document.frmNuevo.plaAccionMejoramiento.value == "otras") {
						validarCampo(forma.plaAccionMejoramientoTxt, "- ¿Cual Accion de Mejoramiento / Programa Ambiental?", 1, 100);
					} else {
						validarLista(forma.plaAccionMejoramiento, "- Acción de Mejoramiento / Programa Ambiental", 1);
					}
					
					validarLista(forma.plaTipoGasto, "- Tipo de Gasto", 1);
					validarLista(forma.plaRubroGasto, "- Rubro de Gasto", 1);
					
					if(forma.plaTipoGasto.value == "Inversion") {
						validarCampo(forma.txtProyecto, "- Proyecto de Inversión", 1, 100);		
					} else if (forma.plaTipoGasto.value == "Funcionamiento") {
						validarCampo(forma.txtProyecto, "- Gastos Generales o Servicios Personales", 1, 100);
					}
					
					if (document.frmNuevo.plaFuenteFinanciacion.value == "otros recursos") {
						validarCampo(forma.fuenteFinanciacionOtros, "- Otra Fuente Financiación", 1, 100);
					} else {
						validarLista(forma.plaFuenteFinanciacion, "- Fuente Financiación", 1);
					}
					
					validarEntero(forma.txtMontoAnual, "- Monto Anual debe ser numérico y mayor que cero", 0, 999999999999);
					
				}		
				
			}
			
	
			function setCeros() {
			
				var forma = document.frmNuevo;
				
				if(trim(forma.plaPonderador.value).length > 0) {
					if(parseFloat(trim(forma.plaPonderador.value)) == 0.0) {
						forma.plaPonderador.value = "";
					}
				}
				
				if(trim(forma.plaMetaAnualCantidad.value).length > 0) {
					if(parseFloat(trim(forma.plaMetaAnualCantidad.value)) == 0.0) {
						forma.plaMetaAnualCantidad.value = "";
					}
				}
				
			}
			
	
			function setUnidad() {
			
				var forma = document.frmNuevo;
				
				if(parseInt(forma.plaUnidades[forma.plaMetaAnualUnidad.selectedIndex].value) == 1) {
					forma.plaMetaAnualCual.disabled = false;
				} else {
					forma.plaMetaAnualCual.value = "";
					forma.plaMetaAnualCual.disabled = true;
				}
				
			}
			
		
			function setFuente() {
			
				var forma = document.frmNuevo;
				var band = false;
				
				if(forma.plaFuenteFinanciera.length) {
				
					for(var i = 0; i < forma.plaFuenteFinanciera.length; i++) {
					
						if(forma.plaFuenteFinanciera[i].checked == true) {
						
							if(parseInt(forma.plaFuentes[i].value) == 1) {
								band = true;
								break;
							}
								
						}
						
					}
					
				} else {
				
					if(forma.plaFuenteFinanciera.checked == true) {
					
						if(parseInt(forma.plaFuentes.value) == 1) {
							band = true;
						}
						
					}
					
				}
				
				if(band == true) {
					forma.plaPresupuesto.disabled = false;
				} else {
					forma.plaPresupuesto.value = '';
					forma.plaPresupuesto.disabled = true;
				}
				
			}
			
			
			function inhabilitarTodo() {
			
				document.frmNuevo.plaVigencia.disabled = true;
				document.frmNuevo.plaObjetivo.disabled = true;
				document.frmNuevo.plaLineaAccion.disabled = true;
				document.frmNuevo.plaCategoria.disabled = true;
				document.frmNuevo.plaActividad.disabled = true;
				document.frmNuevo.plaPonderador.disabled = true;
				document.frmNuevo.plaTipoMeta.disabled = true;
				document.frmNuevo.plaMetaAnualUnidad.disabled = true;
				document.frmNuevo.plaMetaAnualCual.disabled = true;
				document.frmNuevo.plaMetaAnualCantidad.disabled = true;
				document.frmNuevo.plaCronograma1.disabled = true;
				document.frmNuevo.plaCronograma2.disabled = true;
				document.frmNuevo.plaCronograma3.disabled = true;
				document.frmNuevo.plaCronograma4.disabled = true;
				document.frmNuevo.plaResponsable.disabled = true;
				document.frmNuevo.plaFuncion.disabled = true;
				
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
			
	
			function setVisible() {
				
				if (document.frmNuevo.plaVigencia.value > 2010) {
			  
					document.getElementById('plaObjetivo').style.display = 'none';
			   		document.getElementById('placcodobjetivo').style.display = '';
			   		
			  } else {
			   		
			   		document.getElementById('plaObjetivo').style.display = '';
			   		document.getElementById('placcodobjetivo').style.display = 'none';
			   		
			  }
			  
			}			
		</script>
		
	</head>
	
	<c:import url="/mensaje.jsp"/>

	<body onLoad="mensaje(document.getElementById('msg'));">
	
		<form method="post" name="frmAjax" action='<c:url value="/poa/planeacion/Ajax.do"/>'>
			<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_ACTIVIDAD}"/>'>
			<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
		</form>
		
		<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/poa/planeacion/Save.jsp"/>'>
		
			<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_ACTIVIDAD}"/>'>
			<input type="hidden" name="cmd" value=''>
			<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
			<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
			<input type="hidden" name="plaCodigo" value='<c:out value="${sessionScope.planeacionVO.plaCodigo}"/>'>
			<input type="hidden" name="plaInstitucion" value='<c:out value="${sessionScope.login.instId}"/>'>
			<input type="hidden" name="plaTipoActividadValue" value=''>
			
			<select name="plaPonderadores" style="display:none;">
				<option value="0">-</option>
				<c:forEach begin="0" items="${requestScope.listaAreaGestion}" var="area">
					<option value='<c:out value="${area.padre}"/>'>
						<c:out value="${area.padre2}"/>
					</option>
				</c:forEach>
			</select>
			
			<input type="hidden" name="plaUnidades" value='0'>
			<c:forEach begin="0" items="${requestScope.listaUnidadMedida}" var="unidad">
				<input type="hidden" name="plaUnidades" value='<c:out value="${unidad.padre}"/>'>
			</c:forEach>
			
			<c:forEach begin="0" items="${requestScope.listaFuenteFinanciera}" var="fuente">
				<input type="hidden" name="plaFuentes" value='<c:out value="${fuente.padre}"/>'>
			</c:forEach>
			
			<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
				<tr height="1">
					<td width="10">&nbsp;</td>
					<td rowspan="2" width="469">
						<img src='<c:url value="/etc/img/tabs/actividades_1.gif"/>' alt="Actividades" height="26" border="0">
						<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_ACTIVIDAD_SIN}"/>)">
							<img src='<c:url value="/etc/img/tabs/problemas_0.gif"/>' alt="Actividades sin recursos" height="26" border="0">
						</a>
					</td>
				</tr>
			</table>
			
			<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
				<caption>Registro / edición de actividad [Total ponderado= <c:out value="${sessionScope.filtroPlaneacionVO.filPonderado}"/>%]</caption>
				<tr>
					<td width="45%">
						<c:if test="${sessionScope.NivelPermiso==2 && sessionScope.filtroPlaneacionVO.filFechaHabil==true}">
	  						<input id="guardar1" name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
							<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
	   					</c:if>
	   					<c:if test="${sessionScope.filtroPlaneacionVO.filFechaHabil==false}">
	   						<span class="style2">No se puede registrar actividades porque está por fuera de la fecha establecida</span>
	   					</c:if>
						<%-- <a class="manual" href='<c:url value="/private/manual/Manual_POA.pdf?tipo=pdf"/>' target="_blank">&nbsp;&nbsp;&nbsp;Descargue el manual aquí</a> --%>
			  		</td>
			 	</tr>	
			 </table>
			 
		  	<table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
				<tr>
					<td width="15%" title='<c:out value="${sessionScope.filtroPlaneacionVO.lblVigencia}"/>'>
						<span class="style2">*</span> Vigencia:
					</td>
					<td width="85%" colspan="3">
						<select name="plaVigencia" onchange="setVisible();ajaxArea()" style="width:220px;" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/>>
							<option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
								<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.planeacionVO.plaVigencia}">selected</c:if>><c:out value="${vig.nombre}"/></option>
							</c:forEach>
						</select>
					</td>
		 		</tr>
				<tr>
					<td width="15%" title='<c:out value="${requestScope.filtroPlaneacionVO.lblObjetivo}"/>'>
						<span class="style2">*</span> Objetivo Estratégico:
					</td>
					<td width="85%" colspan="3">
				 		<select style="width:700px;display:none" name="placcodobjetivo" id="placcodobjetivo" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/>>
							<option value="-99" selected>-- Seleccione uno --</option>
							<c:forEach begin="0" items="${sessionScope.listaObjetivos}" var="vig">
								<option title='<c:out value="${vig.nombre}"/>' value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo == sessionScope.planeacionVO.placcodobjetivo  }">selected</c:if>><c:out value="${vig.nombre}"/></option>
							</c:forEach>					
						</select>
				   		<textarea name="plaObjetivo" id="plaObjetivo" cols="110" rows="3" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);" ><c:out value="${sessionScope.planeacionVO.plaObjetivo}"/></textarea>
					</td>					
		 		</tr>
				<tr>
					<td width="15%">
						<span class="style2">*</span> Tipo de Actividad:
					</td>
					<td width="85%" colspan="3">
						<select name="plaTipoActividad" style="width:220px;" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> onchange="ajaxTipoActividad()">
							<option value="-99" selected>-Seleccione uno-</option>
							<option value="POA" <c:if test="${sessionScope.planeacionVO.tipoActividad != null and 'POA' eq sessionScope.planeacionVO.tipoActividad}">selected</c:if>>POA</option>
							<!-- <option value="PIMA - PIGA" <c:if test="${sessionScope.planeacionVO.tipoActividad != null and 'PIMA - PIGA' eq sessionScope.planeacionVO.tipoActividad}">selected</c:if>>PIMA - PIGA</option>  --> 
						</select>
					</td>
				</tr>
				<tr id="filaAccionMejoramiento" style="display: 
					<c:if test="${sessionScope.planeacionVO.tipoActividad  != null and 'PIMA - PIGA' eq sessionScope.planeacionVO.tipoActividad}">table-row;</c:if>
					<c:if test="${sessionScope.planeacionVO.tipoActividad  != null and 'PIMA - PIGA' ne sessionScope.planeacionVO.tipoActividad}">none;</c:if>
					<c:if test="${sessionScope.planeacionVO.tipoActividad  == null }">none;</c:if>">
					<td width="15%">
						<span class="style2">*</span> Acción de Mejoramiento / Programa Ambiental:
					</td>
					<td width="85%" colspan="3">
						<select name="plaAccionMejoramiento" style="width:220px;" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> onchange="ajaxAccionMejoramiento()">
							<option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaAccionMejoramiento}" var="accionMejoramiento">
								<option value="<c:out value="${accionMejoramiento[1]}"/>" <c:if test="${accionMejoramiento[1]==sessionScope.planeacionVO.accionMejoramiento}">selected</c:if>><c:out value="${accionMejoramiento[0]}"/></option>
							</c:forEach>
						</select>
					</td>				
				</tr>
				<tr id="filaAccionMejoramientoOtras" style="display: <c:if test="${sessionScope.planeacionVO.accionMejoramiento=='otras'}">table-row;</c:if><c:if test="${sessionScope.planeacionVO.accionMejoramiento!='otras'}">none;</c:if>">
					<td width="15%">
						<span class="style2">*</span>¿ Cual Acción de Mejoramiento / Programa Ambiental ?
					</td>
					<td width="85%" colspan="3">
						<textarea name="plaAccionMejoramientoTxt" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> maxlength="100" cols="110" rows="3" onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);"><c:out value="${sessionScope.planeacionVO.accionMejoramientoOtras}"/></textarea>
					</td>
				</tr>
				<tr>
					<td width="15%" title='<c:out value="${sessionScope.filtroPlaneacionVO.lblActividad}"/>'>
						<span class="style2">*</span> Actividad / Tarea:
					</td>
					<td width="85%" colspan="3">
						<textarea name="plaActividad" cols="110" rows="3" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);" ><c:out value="${sessionScope.planeacionVO.plaActividad}"/></textarea>
					</td>
		 		</tr>
		 		<tr>
					<td width="15%" title='<c:out value="${sessionScope.filtroPlaneacionVO.lblAreaGestion}"/>'>
						<span class="style2">*</span> Área de Gestión:
					</td>
					<td width="30%">
						<select name="plaAreaGestion" style="width:220px;" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> onchange="ajaxLinea()">
							<option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaAreaGestion}" var="area">
								<option value="<c:out value="${area.codigo}"/>" <c:if test="${area.codigo==sessionScope.planeacionVO.plaAreaGestion}">selected</c:if>><c:out value="${area.nombre}"/></option>
							</c:forEach>
						</select>
					</td>
					<td width="20%" align="right" title='<c:out value="${sessionScope.filtroPlaneacionVO.lblLineaAccion}"/>'>
						<span class="style2">*</span> Línea de Acción / Proceso / Proyecto:
					</td>
					<td width="35%" align="left">
						<select name="plaLineaAccion" style="width:300px;" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/>>
							<option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaLineaAccion}" var="linea">
								<option value="<c:out value="${linea.codigo}"/>" <c:if test="${linea.codigo==sessionScope.planeacionVO.plaLineaAccion}">selected</c:if>><c:out value="${linea.nombre}"/></option>
							</c:forEach>
						</select>
					</td>
		 		</tr>
				<tr>
					<td width="15%" title='<c:out value="${sessionScope.filtroPlaneacionVO.lblPonderador}"/>'>
						<span class="style2">*</span> Ponderador:
					</td>
					<td width="30%">
						<input type="text" name="plaPonderador" maxlength="5" size="3" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.planeacionVO.plaPonderador}"/>'></input>%
					</td>
					<td width="20%" align="right" title='<c:out value="${sessionScope.filtroPlaneacionVO.lblTipoMeta}"/>'>
						<span class="style2">*</span> Tipo de Meta:
					</td>
					<td width="35%" align="left">
						<select name="plaTipoMeta" style="width:300px;" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> onchange="javaScript:cambioTipo(this)">
							<option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaTipoMeta}" var="tipoMeta">
								<option value="<c:out value="${tipoMeta.codigo}"/>" <c:if test="${tipoMeta.codigo==sessionScope.planeacionVO.plaTipoMeta}">selected</c:if>><c:out value="${tipoMeta.nombre}"/></option>
							</c:forEach>
						</select>
					</td>
		 		</tr>
			</table>
			<br>

			<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<table border="0" align="center" width="100%" cellpadding="1" cellspacing="0">
							<tr>
								<th colspan="6" class="Fila0">Meta Anual / Producto Final</th>
							</tr>
							<tr>
								<td width="15%" title='<c:out value="${sessionScope.filtroPlaneacionVO.lblMetaAnualUnidad}"/>'>
									<span class="style2">*</span> Unidad de Medida:
								</td>
								<td width="30%">
									<select name="plaMetaAnualUnidad" style="width:220px;" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> onchange="javaScript:setUnidad()">
										<option value="-99" selected>-Seleccione uno-</option>
										<c:forEach begin="0" items="${requestScope.listaUnidadMedida}" var="unidad">
											<option value="<c:out value="${unidad.codigo}"/>" <c:if test="${unidad.codigo==sessionScope.planeacionVO.plaMetaAnualUnidad}">selected</c:if>><c:out value="${unidad.nombre}"/></option>
										</c:forEach>
									</select>
								</td>
								<td width="5%" title='<c:out value="${sessionScope.filtroPlaneacionVO.lblMetaAnualCual}"/>'>¿Cuál?:
								</td>
								<td width="30%">
									<input type="text" name="plaMetaAnualCual" maxlength="100" size="40" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> value='<c:out value="${sessionScope.planeacionVO.plaMetaAnualCual}"/>'></input>
								</td>
								<td width="10%" title='<c:out value="${sessionScope.filtroPlaneacionVO.lblMetaAnualCantidad}"/>'>
									<span class="style2">*</span> Cantidad:
								</td>
								<td width="10%">
									<input type="text" name="plaMetaAnualCantidad" maxlength="7" size="4" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' onblur='calcularCronograma()' value='<c:out value="${sessionScope.planeacionVO.plaMetaAnualCantidad}"/>'></input>
								</td>
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
								<th colspan="4" class="Fila0">Cronograma</th>
							</tr>
							<tr>
								<td title='<c:out value="${sessionScope.filtroPlaneacionVO.lblCronograma1}"/>' align="center">
									<span class="style2">*</span> 1er Trimestre:
								</td>
								<td title='<c:out value="${sessionScope.filtroPlaneacionVO.lblCronograma2}"/>' align="center">
									<span class="style2">*</span> 2do Trimestre:
								</td>
								<td title='<c:out value="${sessionScope.filtroPlaneacionVO.lblCronograma3}"/>' align="center">
									<span class="style2">*</span> 3er Trimestre:
								</td>
								<td title='<c:out value="${sessionScope.filtroPlaneacionVO.lblCronograma4}"/>' align="center">
									<span class="style2">*</span> 4to Trimestre:
								</td>
							</tr>
							<tr>
								<td align="center">
									<input type="text" name="plaCronograma1" maxlength="7" size="4" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)'  onblur='calcularCantidad()' value='<c:out value="${sessionScope.planeacionVO.plaCronograma1}"/>'></input>
								</td>
								<td align="center">
									<input type="text" name="plaCronograma2" maxlength="7" size="4" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)'  onblur='calcularCantidad()' value='<c:out value="${sessionScope.planeacionVO.plaCronograma2}"/>'></input>
								</td>
								<td align="center">
									<input type="text" name="plaCronograma3" maxlength="7" size="4" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)'  onblur='calcularCantidad()' value='<c:out value="${sessionScope.planeacionVO.plaCronograma3}"/>'></input>
								</td>
								<td align="center">
									<input type="text" name="plaCronograma4" maxlength="7" size="4" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)'  onblur='calcularCantidad()' value='<c:out value="${sessionScope.planeacionVO.plaCronograma4}"/>'></input>
								</td>
							</tr>
							<tr>
								<th colspan="4" class="Fila0">Fecha Prevista de Entrega</th>
							</tr>
							<tr>
								<td colspan="4" style="text-align: center;">
				                   	<input name="SEGFECHACUMPLIMT" id="SEGFECHACUMPLIMT" type="text" size="10" maxlength="10" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> value='<c:out value="${sessionScope.planeacionVO.SEGFECHACUMPLIMT}"/>'>
									<img 	src='<c:url value="/etc/img/calendario.gif"/>' 
											alt="Seleccione fecha" id="imgFechapla"
											style="cursor: pointer; display: <c:if test="${sessionScope.planeacionVO.plaDisabled eq 'disabled'}">none;</c:if>"
											title="Date selector"
											onmouseover="this.style.background='red';"
											onmouseout="this.style.background=''" 
											/>                    	
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			
			<table align="center" width="95%" border="0" cellpadding="1" cellspacing="0">	
				<tr>
					<td title='<c:out value="${sessionScope.filtroPlaneacionVO.lblResponsable}"/>'>
						<span class="style2">*</span> Nombre del Responsable:</td>
					<td>
						<input type="text" name="plaResponsable" maxlength="250" size="30" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> value='<c:out value="${sessionScope.planeacionVO.plaResponsable}"/>'></input>
					</td>
				</tr>
				<tr style="display:none">
					<td>
						<iframe name="frameAjax" id="frameAjax"></iframe>
					</td>
				</tr>	
			</table>
		
			<table id="tablaPimaPiga" align="center" width="95%" border="0" cellpadding="1" cellspacing="0" style="display: 
				<c:if test="${sessionScope.planeacionVO.tipoActividad != null and sessionScope.planeacionVO.tipoActividad eq 'PIMA - PIGA'}">table;</c:if>
				<c:if test="${sessionScope.planeacionVO.tipoActividad != null and sessionScope.planeacionVO.tipoActividad ne 'PIMA - PIGA'}">none;</c:if>
				<c:if test="${sessionScope.planeacionVO.tipoActividad == null}">none;</c:if>">
				<tr>		
					<td>
						<span class="style2">*</span> Tipo de Gasto:
					</td>
					<td width="25%" >
						<select name="plaTipoGasto" style="width: 180px;" onchange="ajaxTipoGasto()" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/>>
							<option value="-99" selected>-Seleccione uno-</option>
							<option value="Inversion" <c:if test="${sessionScope.planeacionVO.tipoGasto eq 'Inversion'}">selected</c:if>>Inversion</option>
							<option value="Funcionamiento" <c:if test="${sessionScope.planeacionVO.tipoGasto eq'Funcionamiento'}">selected</c:if>>Funcionamiento</option>
						</select>
					</td>
				</tr>
				<tr>	
					<td>
						<span class="style2">*</span> Rubro de Gasto:
					</td>
					<td width="25%" >
						<select id="plaRubroGasto" name="plaRubroGasto" style="width: 180px;" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/>><option value="-99" selected>-Seleccione uno-</option>
							<c:forEach items="${requestScope.listaRubroGasto}" var="rubroGasto">
								<option value="<c:out value="${rubroGasto[1]}"/>" <c:if test="${sessionScope.planeacionVO.rubroGasto eq rubroGasto[1]}">selected</c:if>><c:out value="${rubroGasto[0]}"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>			
				<tr>		
					<td>
						<span class="style2">*</span><label id="labelProyecto"> Proyecto de Inversión: </label>
					</td>
					<td colspan="3" >
						<textarea name="txtProyecto" maxlength="50"	cols="70" rows="3" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);"><c:if test="${sessionScope.planeacionVO.tipoGasto eq 'Funcionamiento'}"><c:out value="${sessionScope.planeacionVO.subnivelGasto}"/></c:if><c:if test="${sessionScope.planeacionVO.tipoGasto eq 'Inversion'}"><c:out value="${sessionScope.planeacionVO.proyectoInversion}"/></c:if></textarea>
					</td>
				</tr>
			</table>
		
			<table id="tablaPresupuestoActividad" style="display: 
				<c:if test="${sessionScope.planeacionVO.tipoActividad != null and sessionScope.planeacionVO.tipoActividad eq 'PIMA - PIGA'}">table;</c:if>
				<c:if test="${sessionScope.planeacionVO.tipoActividad != null and sessionScope.planeacionVO.tipoActividad ne 'PIMA - PIGA'}">none;</c:if>
				<c:if test="${sessionScope.planeacionVO.tipoActividad == null}">none;</c:if>">
				<caption>PRESUPUESTO DE ACTIVIDAD</caption>
				<tr>	
					<td>
						<span class="style2">*</span> Fuente de Financiación:
					</td>
					<td width="25%" >
						<select id="plaFuenteFinanciacion" name="plaFuenteFinanciacion" style="width: 180px;" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> onchange="ajaxFuenteFinanciacion()"> <option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaFuenteFinanciacion}" var="fuenteFinanciacion">
								<option value="<c:out value="${fuenteFinanciacion[1]}"/>" <c:if test="${fuenteFinanciacion[1]==sessionScope.planeacionVO.fuenteFinanciacion}">selected</c:if>><c:out value="${fuenteFinanciacion[0]}"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr id="filaFuenteFinanciacionOtro" style="display: <c:if test="${sessionScope.planeacionVO.fuenteFinanciacion=='otros recursos'}">table-row;</c:if><c:if test="${sessionScope.planeacionVO.fuenteFinanciacion!='otros recursos'}">none;</c:if>">
					<td>
						<span class="style2">*</span>¿ Qué otro recurso ?
					</td>
					<td colspan="3">
						<textarea name="fuenteFinanciacionOtros" maxlength="100" cols="70" rows="3" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);"><c:out  value="${sessionScope.planeacionVO.fuenteFinanciacionOtros}"/></textarea>
					</td>
				</tr>
				<tr>		
					<td>
						<span class="style2">*</span><label> Monto Anual: </label>
					</td>
					<td colspan="3" >
						<input type="text" maxlength="12" name="txtMontoAnual" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value="<c:out value="${sessionScope.planeacionVO.montoAnual}"/>"/>
					</td>
				</tr>
				<tr>	
					<td>
						<label> Presupuesto Participativo: </label>
					</td>
					<td colspan="3" >
						<input type="text" maxlength="12" name="txtPresupuestoParticipativo" <c:out value="${sessionScope.planeacionVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value="<c:out value="${sessionScope.planeacionVO.presupuestoParticipativo}"/>"/>
					</td>
				</tr>	
			</table>
			
		</form>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%" valign='top'>
					<div style="width:100%;height:350px;overflow:auto;vertical-align:top;background-color:#ffffff;">
						<c:import url="/poa/planeacion/Filtro.do"/>
					</div>
				</td>
			</tr>
		</table>
	
	</body>
	
	<script type="text/javascript">
		Calendar.setup({inputField:"SEGFECHACUMPLIMT",ifFormat:"%d/%m/%Y",button:"imgFechapla",align:"Br"});
		Calendar.setup({inputField:"SEGFECHACUMPLIMT",ifFormat:"%d/%m/%Y",button:"imgFechapla",align:"Br"});
		//  Calendar.setup({
  		//      inputField     :    "plaFecha",
   		//     ifFormat       :    "%d/%m/%Y",
   		//     button         :    "imgfecha",
   		//     align          :    "Br"
  		//  });
	</script>
	
	<script type="text/javascript">
		<c:if test="${!sessionScope.planeacionVO.plaDesHabilitado}">
			setUnidad();
		</c:if>
		<c:if test="${sessionScope.planeacionVO.plaDesHabilitado==false}">
			bloquearTipoMeta();	
		</c:if>
		
		setCeros();
		setVisible();
		
		<c:if test="${sessionScope.filtroPlaneacionVO.filFechaHabil==false}">
			<c:if test="${sessionScope.planeacionVO.formaEstado==0}">
				inhabilitarTodo();
			</c:if>
			<c:if test="${sessionScope.planeacionVO.formaEstado==1}">
				inhabilitarTodo();
			</c:if>
		</c:if>
	</script>
	
</html>
