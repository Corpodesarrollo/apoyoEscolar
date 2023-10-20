<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!--	VERSION		FECHA			AUTOR				DESCRIPCION
			1.0		04/12/2019		JORGE CAMACHO		Versión Inicial
-->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="filtroReportesPOA" class="poa.reportes.vo.FiltroReportesVO" scope="session"/>
<jsp:useBean id="paramsVO" class="poa.reportes.vo.ParamsVO" scope="page"/>

<html>

	<c:import url="/parametros.jsp"/>

	<head>

		<script language="javaScript">
		<!--
			var imagen1;
    		var ScrollSpeed = 100;  // milliseconds between scrolls
			var ScrollChars = 1;    // chars scrolled per time period
			var stop = 1;
			var flag = 0;
			var flagSpinner = 0;
			var msg2 = "";
			var msg = "";
			var decimas = 0  
			var segundos = 0  
			var minutos = 0  
			var ValorCrono = "";
			CronoEjecutandose = true;  
			CronoID = null;
    		
    		function init() {
    			stop = 1;
    			flagSpinner = 0;
    		}
    		
			function Run() {
				stop = 0;
				flagSpinner = 0;
				SetupTicker();
			}
			
			function end() {
				stop = 1;
				DetenerCrono();
				flagSpinner = 0;
			}
			 
			function DetenerCrono() {
				if(CronoEjecutandose)
					clearTimeout(CronoID);
					
				CronoEjecutandose = false;
			} 
	                             
			function RunTicker() {
			
	  			if(stop == 0) {
	   				CronoID = window.setTimeout('RunTicker()', ScrollSpeed);
	   				document.getElementById("txtmsg").innerHTML = "GENERANDO";
	   				document.getElementById("barraCargar").innerHTML = msg2;
	   				flag++;
	   				
	   				if(flag == 6)
	   					flag = 0;
	   
	   				msg2 = msg.substring(0, flag);
	   				decimas++;
	   				
		 			if (decimas > 9) {
		 				decimas = 0;
		 				segundos++;  
			 
			 			if (segundos > 59) {
			 				segundos = 0;
			 				minutos++;  
				 		
				 			if (minutos > 99) {
				 				alert('Fin de la cuenta');
				 				DetenerCrono();
				 				return true;  
				 			}
			 			}
		 			}
		 			
		 			ValorCrono = (minutos < 10) ? "0" + minutos : minutos  
		 			ValorCrono += (segundos < 10) ? ":0" + segundos : ":" + segundos  
		 			ValorCrono += ":" + decimas   
		 			
		 			CronoEjecutandose = true;  
		 			return true;
	 			}
	 			
			}
		    
    		function SetupTicker() { 
      			flag = 0;  
      			msg = "... .";
      			RunTicker();
    		}    

			function hacerValidaciones_frmFiltro(forma) {
			
				validarLista(forma.filTipoReporte, "- Tipo Reporte", 1);
				validarLista(forma.filVigencia, "- Vigencia", 1);
				
				if(parseInt(forma.filTipoReporte.value) == 50) {									// Necesidades por Área de Gestión
					validarLista(forma.filLocalidad, "- Localidad", 0);						
				}
				
				if(parseInt(forma.filTipoReporte.value) == 51) {
					validarLista(forma.filLocalidad, "- Localidad", 0);						
				}
				
				if(parseInt(forma.filTipoReporte.value) == 52) {									// Reporte General Seguimiento
					validarLista(forma.filLocalidad, "- Localidad", 1);
					validarLista(forma.filInstitucion, "- Institución", 1);
					validarLista(forma.filPeriodo, "- Periodo", 1);					
				}
				
				if(parseInt(forma.filTipoReporte.value) == 53) {									// Consulta General de Actividades
					validarLista(forma.filInstitucion, "- Institución", 0);
					validarLista(forma.filAreaGestion, "- Área de Gestión", 0);						
					validarLista(forma.filLineaAccion, "- Línea de Acción", 0);
					//validarLista(forma.filFteFin, "- Fuente de Financiación", 0);
					
					if((trim(forma.filPorEjec1.value).length > 0) || (trim(forma.filPorEjec2.value).length > 0)) {
						validarEntero(forma.filPorEjec1, "- Porcentaje Inicial", 0, 100);
						validarEntero(forma.filPorEjec2, "- Porcentaje Final", 0, 100);
						validarEntero(forma.filPorEjec2, "- El Porcentaje Final debe ser mayor al Inicial", parseInt(forma.filPorEjec1.value), 100);
					}
						
				}
				
				if(parseInt(forma.filTipoReporte.value) == 55) {
					validarLista(forma.filLocalidad, "- Localidad", 1);						
					validarLista(forma.filInstitucion, "- Institución", 1);
				}
				
				if(parseInt(forma.filTipoReporte.value) == 56) {									// Avance del Ponderador por Áreas de Gestión 
					validarLista(forma.filLocalidad, "- Localidad", 1);
					validarLista(forma.filInstitucion, "- Institución", 1);						
				}
				
				if(parseInt(forma.filTipoReporte.value) == 57) {									// Avance del Ponderador por Líneas de Acción
					validarLista(forma.filLocalidad, "- Localidad", 1);
					validarLista(forma.filInstitucion, "- Institución", 1);						
				}
				
				if(parseInt(forma.filTipoReporte.value) == 58) {
					validarLista(forma.filLocalidad, "- Localidad", 1);						
					validarLista(forma.filInstitucion, "- Institución", 1);						
				}
				
				if(parseInt(forma.filTipoReporte.value) == 59) {									// Reporte POA
					validarLista(forma.filLocalidad, "- Localidad", 1);
					validarLista(forma.filInstitucion, "- Institución", 1);						
				}				
				
				if(parseInt(forma.filTipoReporte.value) == 60) {									// Reporte General Consolidado
					validarLista(forma.filLocalidad, "- Localidad", 1);
					validarLista(forma.filInstitucion, "- Institución", 1);
				}
				
			}
	
			function buscar() {
				if(validarForma(document.frmFiltro)) {
					document.frmFiltro.cmd.value = document.frmFiltro.BUSCAR.value;
					Run();
					document.frmFiltro.submit();
				}		
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
			
			function ajaxColegio() {		
				borrar_combo(document.frmFiltro.filInstitucion); 
				document.frmAjax0.ajax[0].value = document.frmFiltro.filLocalidad.value;
				document.frmAjax0.ajax[1].value = document.frmFiltro.filVigencia.value;
				document.frmAjax0.cmd.value = '<c:out value="${paramsVO.CMD_AJAX_COLEGIO}"/>';
		 		document.frmAjax0.target = "frameAjax0";
				document.frmAjax0.submit();		
			}
		
			function reinicioValores() {
			
				borrar_combo(document.frmFiltro.filInstitucion); 
				document.frmFiltro.filLocalidad.disabled = true;					
				document.frmFiltro.filInstitucion.disabled = true;
				document.frmFiltro.filAreaGestion.disabled = true;
				document.frmFiltro.filLineaAccion.disabled = true;					
				document.frmFiltro.filFteFin.disabled = true;
				document.frmFiltro.filPorEjec1.disabled = true;
				document.frmFiltro.filPorEjec2.disabled = true;
				
				document.frmFiltro.filTipoReporte.value = "-99";
				document.frmFiltro.filVigencia.value = "-99";
				document.frmFiltro.filLocalidad.value = "-99";					
				document.frmFiltro.filInstitucion.value = "-99";
				document.frmFiltro.filAreaGestion.value = "-99";
				document.frmFiltro.filLineaAccion.value = "-99";					
				document.frmFiltro.filFteFin.value = "-99";
				document.frmFiltro.filPorEjec1.value = "";
				document.frmFiltro.filPorEjec2.value="";
			}
	
			function ajaxLineas() {
				borrar_combo(document.frmFiltro.filLineaAccion); 
				document.frmAjax0.ajax[0].value = document.frmFiltro.filAreaGestion.value;
				document.frmAjax0.cmd.value = '<c:out value="${paramsVO.CMD_AJAX_LINEAS}"/>';
		 		document.frmAjax0.target = "frameAjax0";
				document.frmAjax0.submit();
			}
		
			function cambioReporte() {
			
				if(parseInt(document.frmFiltro.filTipoReporte.value) > 0) {
				
					if(parseInt(document.frmFiltro.filTipoReporte.value) == 50) {					// Necesidades por Área de Gestión
					
						document.frmFiltro.filLocalidad.disabled = false;					
						document.frmFiltro.filInstitucion.disabled = true;
						document.frmFiltro.filAreaGestion.disabled = true;
						document.frmFiltro.filLineaAccion.disabled = true;
						document.frmFiltro.filPeriodo.disabled=true;
						document.frmFiltro.filFteFin.disabled = true;
						document.frmFiltro.filPorEjec1.disabled = true;
						document.frmFiltro.filPorEjec2.disabled = true;
						
						document.frmFiltro.filLocalidad.value = "-99";	
						document.frmFiltro.filLocalidad.options[0].text = "-Todas";				
						document.frmFiltro.filInstitucion.value = "-99";
						document.frmFiltro.filAreaGestion.value = "-99";
						document.frmFiltro.filLineaAccion.value = "-99";
						document.frmFiltro.filPeriodo.value="-99";
						document.frmFiltro.filFteFin.value = "-99";
						document.frmFiltro.filPorEjec1.value = "";
						document.frmFiltro.filPorEjec2.value = "";
						
					} else if(parseInt(document.frmFiltro.filTipoReporte.value) == 51) {
					
						document.frmFiltro.filLocalidad.disabled = false;					
						document.frmFiltro.filInstitucion.disabled = true;
						document.frmFiltro.filAreaGestion.disabled = true;
						document.frmFiltro.filLineaAccion.disabled = true;
						document.frmFiltro.filPeriodo.disabled=true;
						document.frmFiltro.filFteFin.disabled = true;
						document.frmFiltro.filPorEjec1.disabled = true;
						document.frmFiltro.filPorEjec2.disabled = true;
						
						document.frmFiltro.filLocalidad.value = "-99";
						document.frmFiltro.filLocalidad.options[0].text = "-Todas";					
						document.frmFiltro.filInstitucion.value = "-99";
						document.frmFiltro.filAreaGestion.value = "-99";
						document.frmFiltro.filLineaAccion.value = "-99";
						document.frmFiltro.filPeriodo.value="-99";
						document.frmFiltro.filFteFin.value = "-99";
						document.frmFiltro.filPorEjec1.value = "";
						document.frmFiltro.filPorEjec2.value = "";
							
					} else if(parseInt(document.frmFiltro.filTipoReporte.value) == 52) {			// Reporte General Seguimiento
					
						document.frmFiltro.filLocalidad.disabled = false;					
						document.frmFiltro.filInstitucion.disabled = false;
						document.frmFiltro.filAreaGestion.disabled = true;
						document.frmFiltro.filLineaAccion.disabled = true;
						document.frmFiltro.filPeriodo.disabled=false;
						document.frmFiltro.filFteFin.disabled = true;
						document.frmFiltro.filPorEjec1.disabled = true;
						document.frmFiltro.filPorEjec2.disabled = true;
						
						document.frmFiltro.filLocalidad.value = "-99";
						document.frmFiltro.filLocalidad.options[0].text = "-Seleccione uno";					
						document.frmFiltro.filInstitucion.value = "-99";
						document.frmFiltro.filAreaGestion.value = "-99";
						document.frmFiltro.filLineaAccion.value = "-99";
						document.frmFiltro.filPeriodo.value="-99";
						document.frmFiltro.filFteFin.value = "-99";
						document.frmFiltro.filPorEjec1.value = "";
						document.frmFiltro.filPorEjec2.value = "";
						
					} else if(parseInt(document.frmFiltro.filTipoReporte.value) == 53) {			// Consulta General de Actividades
					
						document.frmFiltro.filLocalidad.disabled = false;					
						document.frmFiltro.filInstitucion.disabled = false;
						document.frmFiltro.filAreaGestion.disabled = false;
						document.frmFiltro.filLineaAccion.disabled = false;
						document.frmFiltro.filPeriodo.disabled=false;
						document.frmFiltro.filFteFin.disabled = false;
						document.frmFiltro.filPorEjec1.disabled = false;
						document.frmFiltro.filPorEjec2.disabled = false;	
						
						document.frmFiltro.filLocalidad.value = "-99";	
						document.frmFiltro.filLocalidad.options[0].text = "-Todas";				
						document.frmFiltro.filInstitucion.value = "-99";
						document.frmFiltro.filAreaGestion.value = "-99";
						document.frmFiltro.filLineaAccion.value = "-99";
						document.frmFiltro.filPeriodo.value="-99";
						document.frmFiltro.filFteFin.value = "-99";
						document.frmFiltro.filPorEjec1.value = "0";
						document.frmFiltro.filPorEjec2.value = "100";
											
					} else if(parseInt(document.frmFiltro.filTipoReporte.value) == 54) {
					
						document.frmFiltro.filLocalidad.disabled = false;					
						document.frmFiltro.filInstitucion.disabled = false;	
						document.frmFiltro.filAreaGestion.disabled = false;
						document.frmFiltro.filLineaAccion.disabled = false;
						document.frmFiltro.filPeriodo.disabled=false;
						document.frmFiltro.filFteFin.disabled = false;
						document.frmFiltro.filPorEjec1.disabled = true;
						document.frmFiltro.filPorEjec2.disabled = true;	
						
						document.frmFiltro.filLocalidad.value = "-99";
						document.frmFiltro.filLocalidad.options[0].text = "-Todas";					
						document.frmFiltro.filInstitucion.value = "-99";
						document.frmFiltro.filAreaGestion.value = "-99";
						document.frmFiltro.filLineaAccion.value = "-99";
						document.frmFiltro.filPeriodo.value="-99";
						document.frmFiltro.filFteFin.value = "-99";
						document.frmFiltro.filPorEjec1.value = "";
						document.frmFiltro.filPorEjec2.value = "";
						
					} else if(parseInt(document.frmFiltro.filTipoReporte.value) == 55) {
					
						document.frmFiltro.filLocalidad.disabled = false;					
						document.frmFiltro.filInstitucion.disabled = false;
						document.frmFiltro.filAreaGestion.disabled = true;
						document.frmFiltro.filLineaAccion.disabled = true;
						document.frmFiltro.filPeriodo.disabled=false;
						document.frmFiltro.filFteFin.disabled = true;
						document.frmFiltro.filPorEjec1.disabled = true;
						document.frmFiltro.filPorEjec2.disabled = true;
						
						document.frmFiltro.filLocalidad.value = "-99";
						document.frmFiltro.filLocalidad.options[0].text = "-Seleccione uno";					
						document.frmFiltro.filInstitucion.value = "-99";
						document.frmFiltro.filAreaGestion.value = "-99";
						document.frmFiltro.filLineaAccion.value = "-99";
						document.frmFiltro.filPeriodo.value="-99";
						document.frmFiltro.filFteFin.value = "-99";
						document.frmFiltro.filPorEjec1.value = "";
						document.frmFiltro.filPorEjec2.value = "";
												
					} else if(parseInt(document.frmFiltro.filTipoReporte.value) == 56) {			// Avance del Ponderador por Áreas de Gestión 
					
						document.frmFiltro.filLocalidad.disabled = false;					
						document.frmFiltro.filInstitucion.disabled = false;
						document.frmFiltro.filAreaGestion.disabled = true;
						document.frmFiltro.filLineaAccion.disabled = true;
						document.frmFiltro.filPeriodo.disabled=false;
						document.frmFiltro.filFteFin.disabled = true;
						document.frmFiltro.filPorEjec1.disabled = true;
						document.frmFiltro.filPorEjec2.disabled = true;
						
						document.frmFiltro.filLocalidad.value = "-99";
						document.frmFiltro.filLocalidad.options[0].text = "-Seleccione uno";					
						document.frmFiltro.filInstitucion.value = "-99";
						document.frmFiltro.filAreaGestion.value = "-99";
						document.frmFiltro.filLineaAccion.value = "-99";
						document.frmFiltro.filPeriodo.value="-99";
						document.frmFiltro.filFteFin.value = "-99";
						document.frmFiltro.filPorEjec1.value = "";
						document.frmFiltro.filPorEjec2.value = "";
												
					} else if(parseInt(document.frmFiltro.filTipoReporte.value) == 57) {			// Avance del Ponderador por Líneas de Acción
					
						document.frmFiltro.filLocalidad.disabled = false;					
						document.frmFiltro.filInstitucion.disabled = false;
						document.frmFiltro.filAreaGestion.disabled = true;
						document.frmFiltro.filLineaAccion.disabled = true;
						document.frmFiltro.filPeriodo.disabled=false;
						document.frmFiltro.filFteFin.disabled = true;
						document.frmFiltro.filPorEjec1.disabled = true;
						document.frmFiltro.filPorEjec2.disabled = true;
						
						document.frmFiltro.filLocalidad.value = "-99";	
						document.frmFiltro.filLocalidad.options[0].text = "-Seleccione uno";				
						document.frmFiltro.filInstitucion.value = "-99";
						document.frmFiltro.filAreaGestion.value = "-99";
						document.frmFiltro.filLineaAccion.value = "-99";
						document.frmFiltro.filPeriodo.value="-99";
						document.frmFiltro.filFteFin.value = "-99";
						document.frmFiltro.filPorEjec1.value = "";
						document.frmFiltro.filPorEjec2.value = "";
												
					} else if(parseInt(document.frmFiltro.filTipoReporte.value) == 58) {
					
						document.frmFiltro.filLocalidad.disabled = false;					
						document.frmFiltro.filInstitucion.disabled = false;
						document.frmFiltro.filAreaGestion.disabled = true;
						document.frmFiltro.filLineaAccion.disabled = true;
						document.frmFiltro.filPeriodo.disabled=false;
						document.frmFiltro.filFteFin.disabled = true;
						document.frmFiltro.filPorEjec1.disabled = true;
						document.frmFiltro.filPorEjec2.disabled = true;
						
						document.frmFiltro.filLocalidad.value = "-99";	
						document.frmFiltro.filLocalidad.options[0].text = "-Seleccione uno";				
						document.frmFiltro.filInstitucion.value = "-99";
						document.frmFiltro.filAreaGestion.value = "-99";
						document.frmFiltro.filLineaAccion.value = "-99";
						document.frmFiltro.filPeriodo.value="-99";
						document.frmFiltro.filFteFin.value = "-99";
						document.frmFiltro.filPorEjec1.value = "";
						document.frmFiltro.filPorEjec2.value = "";
																
					} else if(parseInt(document.frmFiltro.filTipoReporte.value) == 59) {			// Reporte POA
					
						document.frmFiltro.filLocalidad.disabled = false;					
						document.frmFiltro.filInstitucion.disabled = false;
						document.frmFiltro.filAreaGestion.disabled = true;
						document.frmFiltro.filLineaAccion.disabled = true;
						document.frmFiltro.filPeriodo.disabled=false;
						document.frmFiltro.filFteFin.disabled = true;
						document.frmFiltro.filPorEjec1.disabled = true;
						document.frmFiltro.filPorEjec2.disabled = true;
						
						document.frmFiltro.filLocalidad.value = "-99";	
						document.frmFiltro.filLocalidad.options[0].text = "-Seleccione uno";				
						document.frmFiltro.filInstitucion.value = "-99";
						document.frmFiltro.filAreaGestion.value = "-99";
						document.frmFiltro.filLineaAccion.value = "-99";
						document.frmFiltro.filPeriodo.value="-99";
						document.frmFiltro.filFteFin.value = "-99";
						document.frmFiltro.filPorEjec1.value = "";
						document.frmFiltro.filPorEjec2.value = "";
																
					} else if(parseInt(document.frmFiltro.filTipoReporte.value) == 60) {			// Reporte General Consolidado
					
						document.frmFiltro.filLocalidad.disabled = false;					
						document.frmFiltro.filInstitucion.disabled = false;
						document.frmFiltro.filAreaGestion.disabled = true;
						document.frmFiltro.filLineaAccion.disabled = true;
						document.frmFiltro.filPeriodo.disabled=true;
						document.frmFiltro.filObjetivo.disabled = true;
						document.frmFiltro.filFteFin.disabled = true;
						document.frmFiltro.filPorEjec1.disabled = true;
						document.frmFiltro.filPorEjec2.disabled = true;
						
						document.frmFiltro.filLocalidad.value = "-99";	
						document.frmFiltro.filLocalidad.options[0].text = "-Seleccione uno";				
						document.frmFiltro.filInstitucion.value = "-99";
						document.frmFiltro.filAreaGestion.value = "-99";
						document.frmFiltro.filLineaAccion.value = "-99";
						document.frmFiltro.filPeriodo.value="-99";
						document.frmFiltro.filFteFin.value = "-99";
						document.frmFiltro.filPorEjec1.value = "";
						document.frmFiltro.filPorEjec2.value = "";
																
					} else if(parseInt(document.frmFiltro.filTipoReporte.value) == -99) {
						reinicioValores();								
					}
		
				}
				
			}
		
			function cambioReporteGenerado() {
			
				if(parseInt(document.frmFiltro.filTipoReporte.value) > 0) {
					
					if(parseInt(document.frmFiltro.filTipoReporte.value) == 50) {					// Necesidades por Área de Gestión
					
						document.frmFiltro.filLocalidad.disabled = false;					
						document.frmFiltro.filInstitucion.disabled = true;
						document.frmFiltro.filAreaGestion.disabled = true;
						document.frmFiltro.filLineaAccion.disabled = true;
						document.frmFiltro.filPeriodo.disabled=true;
						document.frmFiltro.filFteFin.disabled = true;
						document.frmFiltro.filPorEjec1.disabled = true;
						document.frmFiltro.filPorEjec2.disabled = true;
						
						document.frmFiltro.filPorEjec1.value = "";
						document.frmFiltro.filPorEjec2.value = "";
						document.frmFiltro.filLocalidad.options[0].text = "-Todas";
						document.frmFiltro.filInstitucion.options[0].value = -99;
					
					} else if(parseInt(document.frmFiltro.filTipoReporte.value) == 51) {
					
						document.frmFiltro.filLocalidad.disabled = false;					
						document.frmFiltro.filInstitucion.disabled = true;
						document.frmFiltro.filAreaGestion.disabled = true;
						document.frmFiltro.filLineaAccion.disabled = true;
						document.frmFiltro.filPeriodo.disabled=true;
						document.frmFiltro.filFteFin.disabled = true;
						document.frmFiltro.filPorEjec1.disabled = true;
						document.frmFiltro.filPorEjec2.disabled = true;
						
						document.frmFiltro.filPorEjec1.value = "";
						document.frmFiltro.filPorEjec2.value = "";
						document.frmFiltro.filLocalidad.options[0].text = "-Todas";
						document.frmFiltro.filInstitucion.options[0].value = -99;
					
					} else if(parseInt(document.frmFiltro.filTipoReporte.value) == 52) {			// Reporte General Seguimiento
					
						document.frmFiltro.filLocalidad.disabled = false;					
						document.frmFiltro.filInstitucion.disabled = false;
						document.frmFiltro.filAreaGestion.disabled = true;
						document.frmFiltro.filLineaAccion.disabled = true;
						document.frmFiltro.filPeriodo.disabled=false;
						document.frmFiltro.filFteFin.disabled = true;
						document.frmFiltro.filPorEjec1.disabled = true;
						document.frmFiltro.filPorEjec2.disabled = true;
						
						document.frmFiltro.filPorEjec1.value = "";
						document.frmFiltro.filPorEjec2.value = "";
						document.frmFiltro.filLocalidad.options[0].text = "-Seleccione uno";
										
					} else if(parseInt(document.frmFiltro.filTipoReporte.value) == 53) {			// Consulta General de Actividades
					
						document.frmFiltro.filLocalidad.disabled = false;					
						document.frmFiltro.filInstitucion.disabled = false;
						document.frmFiltro.filAreaGestion.disabled = false;
						document.frmFiltro.filLineaAccion.disabled = false;
						document.frmFiltro.filPeriodo.disabled=false;
						document.frmFiltro.filFteFin.disabled = false;
						document.frmFiltro.filPorEjec1.disabled = false;
						document.frmFiltro.filPorEjec2.disabled = false;
						
						document.frmFiltro.filLocalidad.options[0].text = "-Todas";	
									
					} else if(parseInt(document.frmFiltro.filTipoReporte.value) == 54) {
					
						document.frmFiltro.filLocalidad.disabled = false;					
						document.frmFiltro.filInstitucion.disabled = false;	
						document.frmFiltro.filAreaGestion.disabled = false;
						document.frmFiltro.filLineaAccion.disabled = false;
						document.frmFiltro.filPeriodo.disabled=false;
						document.frmFiltro.filFteFin.disabled = false;
						document.frmFiltro.filPorEjec1.disabled = true;
						document.frmFiltro.filPorEjec2.disabled = true;	
						
						document.frmFiltro.filPorEjec1.value = "";
						document.frmFiltro.filPorEjec2.value = "";
						document.frmFiltro.filLocalidad.options[0].text = "-Todas";
							
					} else if(parseInt(document.frmFiltro.filTipoReporte.value) == 55) {
					
						document.frmFiltro.filLocalidad.disabled = false;					
						document.frmFiltro.filInstitucion.disabled = false;
						document.frmFiltro.filAreaGestion.disabled = true;
						document.frmFiltro.filLineaAccion.disabled = true;
						document.frmFiltro.filPeriodo.disabled=false;
						document.frmFiltro.filFteFin.disabled = true;
						document.frmFiltro.filPorEjec1.disabled = true;
						document.frmFiltro.filPorEjec2.disabled = true;
						
						document.frmFiltro.filPorEjec1.value = "";
						document.frmFiltro.filPorEjec2.value = "";
						
					} else if(parseInt(document.frmFiltro.filTipoReporte.value) == 56) {			// Avance del Ponderador por Áreas de Gestión 
					
						document.frmFiltro.filLocalidad.disabled = false;					
						document.frmFiltro.filInstitucion.disabled = false;
						document.frmFiltro.filAreaGestion.disabled = true;
						document.frmFiltro.filLineaAccion.disabled = true;
						document.frmFiltro.filPeriodo.disabled=false;
						document.frmFiltro.filFteFin.disabled = true;
						document.frmFiltro.filPorEjec1.disabled = true;
						document.frmFiltro.filPorEjec2.disabled = true;
						
						document.frmFiltro.filPorEjec1.value = "";
						document.frmFiltro.filPorEjec2.value = "";
						document.frmFiltro.filLocalidad.options[0].text = "-Seleccione uno";
										
					} else if(parseInt(document.frmFiltro.filTipoReporte.value) == 57) {			// Avance del Ponderador por Líneas de Acción
					
						document.frmFiltro.filLocalidad.disabled = false;					
						document.frmFiltro.filInstitucion.disabled = false;
						document.frmFiltro.filAreaGestion.disabled = true;
						document.frmFiltro.filLineaAccion.disabled = true;
						document.frmFiltro.filPeriodo.disabled=false;
						document.frmFiltro.filFteFin.disabled = true;
						document.frmFiltro.filPorEjec1.disabled = true;
						document.frmFiltro.filPorEjec2.disabled = true;
						
						document.frmFiltro.filPorEjec1.value = "";
						document.frmFiltro.filPorEjec2.value = "";
						document.frmFiltro.filLocalidad.options[0].text = "-Seleccione uno";
										
					} else if(parseInt(document.frmFiltro.filTipoReporte.value) == 58) {
					
						document.frmFiltro.filLocalidad.disabled = false;					
						document.frmFiltro.filInstitucion.disabled = false;
						document.frmFiltro.filAreaGestion.disabled = true;
						document.frmFiltro.filLineaAccion.disabled = true;
						document.frmFiltro.filPeriodo.disabled=false;
						document.frmFiltro.filFteFin.disabled = true;
						document.frmFiltro.filPorEjec1.disabled = true;
						document.frmFiltro.filPorEjec2.disabled = true;
						
						document.frmFiltro.filPorEjec1.value = "";
						document.frmFiltro.filPorEjec2.value = "";
						document.frmFiltro.filLocalidad.options[0].text = "-Seleccione uno";
														
					} else if(parseInt(document.frmFiltro.filTipoReporte.value) == 59) {			// Reporte POA
					
						document.frmFiltro.filLocalidad.disabled = false;					
						document.frmFiltro.filInstitucion.disabled = false;
						document.frmFiltro.filAreaGestion.disabled = true;
						document.frmFiltro.filLineaAccion.disabled = true;
						document.frmFiltro.filPeriodo.disabled=false;
						document.frmFiltro.filFteFin.disabled = true;
						document.frmFiltro.filPorEjec1.disabled = true;
						document.frmFiltro.filPorEjec2.disabled = true;
						
						document.frmFiltro.filPorEjec1.value = "";
						document.frmFiltro.filPorEjec2.value = "";
						document.frmFiltro.filLocalidad.options[0].text = "-Seleccione uno";
											
					} else if(parseInt(document.frmFiltro.filTipoReporte.value) == 60) {			// Reporte General Consolidado
					
						document.frmFiltro.filLocalidad.disabled = false;					
						document.frmFiltro.filInstitucion.disabled = false;
						document.frmFiltro.filAreaGestion.disabled = true;
						document.frmFiltro.filLineaAccion.disabled = true;
						document.frmFiltro.filPeriodo.disabled=true;
						document.frmFiltro.filObjetivo.disabled = true;
						document.frmFiltro.filFteFin.disabled = true;
						document.frmFiltro.filPorEjec1.disabled = true;
						document.frmFiltro.filPorEjec2.disabled = true;
						
						document.frmFiltro.filPorEjec1.value = "";
						document.frmFiltro.filPorEjec2.value = "";
						document.frmFiltro.filLocalidad.options[0].text = "-Seleccione uno";					
					}

				}
				
			}	
		//-->
		</script>
		
	</head>
	
	<c:import url="/mensaje.jsp"/>
	
	<body onLoad="mensaje(document.getElementById('msg'));">
	
		<form method="post" name="frmAjax0" action='<c:url value="/poa/reportes/Ajax.do"/>'>
			<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_REPORTES}"/>'>
			<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
		</form>
		
		<form action='<c:url value="/poa/reportes/Save.jsp"/>' method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)">
		
			<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_REPORTES}"/>'>
			<input type="hidden" name="cmd" value=''>
			<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		 	
			<table cellpadding="0" cellspacing="0" border="0" align="CENTER" width="100%" >
				<tr height="1">
					<td width="10">&nbsp;</td>
					<td rowspan="2" width="469">
						<img src='<c:url value="/etc/img/tabs/poa_reportes_1.gif"/>' alt="Reportes" height="26" border="0">
					</td>
				</tr>
			</table>
			
			<table border="0" align="center" width="95%">
			 	<caption>Filtro de búsqueda</caption>
				<tr>
					<td>
						<c:if test="${sessionScope.NivelPermiso==2}">
	   						<input name="cmd1" type="button" value="Generar" onClick="buscar()" class="boton">
	   					</c:if>
			  			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span align="left" id="txtmsg" name="txtmsg" style="font-weight:bold;font-size:10pt;color:#FF6666"></span><span align="left" id="barraCargar" name="barraCargar" style="font-weight:bold;font-size:9pt;color:#FF6666"></span>
			  		</td>
			 	</tr>	
			</table>
			
			<table border="0" align="center" width="95%">
				<tr>
					<td width="125px"><span class="style2">*</span> Reporte:</td>
					<td>
						<select name="filTipoReporte" onchange="cambioReporte();ocultarReporte();" style="width:280px">
							<option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaReportes}" var="rep">
								<option value="<c:out value="${rep.codigo}"/>" <c:if test="${rep.codigo==sessionScope.filtroReportesPOA.filTipoReporte}">selected</c:if>><c:out value="${rep.nombre}"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
			 	<tr>
					<td width="125px"><span class="style2">*</span> Vigencia:</td>
					<td>
						<select name="filVigencia" id="filVigencia" onchange="javaScript:ajaxColegio();ocultarReporte();" style="width:180px">
							<option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
								<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.filtroReportesPOA.filVigencia}">selected</c:if>><c:out value="${vig.nombre}"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td width="125px"> Objetivo:</td>
					<td>
						<select name="filObjetivo" id="filObjetivo" onchange="ocultarReporte();" style="width:500px">
							<option value="-99" selected>- Todos -</option>
							<c:forEach begin="0" items="${sessionScope.listaObjetivos}" var="vig">
								<option title="<c:out value="${vig.nombre}"/>" value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.filtroReportesPOA.filObjetivo}">selected</c:if>><c:out value="${vig.nombre}"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td width="125px"> Localidad:</td>
					<td>
						<select name="filLocalidad" style="width:280px" onchange="javaScript:ajaxColegio();ocultarReporte();" disabled="true">
							<option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaLocalidad}" var="log">
								<option value="<c:out value="${log.codigo}"/>" <c:if test="${log.codigo==sessionScope.filtroReportesPOA.filLocalidad}">selected</c:if>><c:out value="${log.nombre}"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td width="125px"> Colegio:</td>
					<td>
						<select name="filInstitucion" style="width:500px" onchange="ocultarReporte();" disabled="true" >
							<option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaColegio}" var="inst">
								<option value="<c:out value="${inst.codigo}"/>" <c:if test="${inst.codigo==sessionScope.filtroReportesPOA.filInstitucion}">selected</c:if>><c:out value="${inst.nombre}"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td width="125px"> Área de Gestión:</td>
					<td>
						<select name="filAreaGestion" style="width:500px" onchange="javaScript:ajaxLineas();ocultarReporte();" disabled="true">
							<option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaAreas}" var="are">
								<option value="<c:out value="${are.codigo}"/>" <c:if test="${are.codigo==sessionScope.filtroReportesPOA.filAreaGestion}">selected</c:if>><c:out value="${are.nombre}"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td width="125px"> Línea de Acción:</td>
					<td>
						<select name="filLineaAccion" style="width:500px" onchange="ocultarReporte();" disabled="true">
							<option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaLineasAccion}" var="lin">
								<option value="<c:out value="${lin.codigo}"/>" <c:if test="${lin.codigo==sessionScope.filtroReportesPOA.filLineaAccion}">selected</c:if>><c:out value="${lin.nombre}"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td width="125px"> Periodo:</td>
					<td>
						<select name="filPeriodo" style="width:180px">>
							<option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaPeriodos}" var="per">
								<option value="<c:out value="${per.codigo}"/>" <c:if test="${per.codigo==sessionScope.filtroReportesPOA.filPeriodo}">selected</c:if>><c:out value="${per.nombre}"/></option>
							</c:forEach>
						</select>
					</td>
                </tr>
				<input type="hidden" name="filFteFin" value="-99">	
				<!--
				<tr>	
					<td>Fuente Financiación:</td>
					<td>
						<select name="filFteFin" style="width:280px" disabled="true">
							<option value="-99" selected>-Seleccione uno-</option>
							<c:forEach begin="0" items="${requestScope.listaFuentesFin}" var="fte">
								<option value="<c:out value="${fte.codigo}"/>" <c:if test="${fte.codigo==sessionScope.filtroReportesPOA.filFteFin}">selected</c:if>><c:out value="${fte.nombre}"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				 -->
				<tr id="por_ejecucion">
					<td>% Ejecución:</td>
					<td>
						<input type="text" name="filPorEjec1" maxlength="3" size="1" onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.filtroReportesPOA.filPorEjec1}"/>' disabled="true"></input> - 
						<input type="text" name="filPorEjec2" maxlength="3" size="1" onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.filtroReportesPOA.filPorEjec2}"/>' disabled="true"></input>
					</td>				
				</tr>
				<tr style="display:none"><td><iframe name="frameAjax0" id="frameAjax0"></iframe></td></tr>	
			</table>
			
		</form>
		
		<script>
			cambioReporteGenerado();
		</script>
		
	</body>
	
</html>
