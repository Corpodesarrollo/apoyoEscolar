<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<!--	VERSION		FECHA		AUTOR				DESCRIPCION
			1.0		28/11/2019	JORGE CAMACHO		Version Inicial
			2.0		03/12/2019	JORGE CAMACHO		Se modificó el método javascript: validarSeguimientoVsDemanda() para cuando los valores son vacíos
			3.0		06/04/2021	JORGE CAMACHO		Se modificaron los métodos javascript para Cargar y Eliminar documentos
								GERSON CESPEDES
			4.0		27/06/2021	JORGE CAMACHO		Se modificó el método javascript: peticion() para validar las extensiones permitidas,
								GERSON CEPEDES		que el nombre del archivo tenga dígitos, caracteres sin acentos, espacios, guiones y guión al piso.
													También se modificó el mensaje del método mensajeUnidadMedida()
													También se agregó el objeto Cargando... cuando se manda a cargar un archivo de evidencia


 -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import = "java.util.ResourceBundle" %>
<% ResourceBundle resource = ResourceBundle.getBundle("path");
  String rutaArchivo=resource.getString("ruta.archivos.seguimiento");
   %>

<jsp:useBean id="seguimientoVO" class="poa.seguimiento.vo.SeguimientoVO" scope="session" />
<jsp:useBean id="paramsVO" class="poa.seguimiento.vo.ParamsVO" scope="page" />

<html>

	<head>

		<style type='text/css'>
			.manual:hover {
				COLOR: red;
				FONT-WEIGHT: bold;
				FONT-FAMILY: Arial, Helvetica, sans-serif;
				FONT-SIZE: 10pt;
			}

			.manual:link {
				COLOR: red;
				FONT-WEIGHT: bold;
				FONT-FAMILY: Arial, Helvetica, sans-serif;
				FONT-SIZE: 10pt;
			}

			.manual:active {
				COLOR: red;
				FONT-WEIGHT: bold;
				FONT-FAMILY: Arial, Helvetica, sans-serif;
				FONT-SIZE: 10pt;
			}
			
			.manual:visited {
				COLOR: red;
				FONT-WEIGHT: bold;
				FONT-FAMILY: Arial, Helvetica, sans-serif;
				FONT-SIZE: 10pt;
			}
		</style>
		
		<link rel="stylesheet" type="text/css" media="all" href='<c:url value="/etc/css/calendar-win2k-1.css"/>' title="win2k-cold-1" />
		
		<script type="text/javascript" src='<c:url value="/etc/js/calendar.js"/>'></script>
		<script type="text/javascript" src='<c:url value="/etc/js/lang/calendar-es.js"/>'></script>
		<script type="text/javascript" src='<c:url value="/etc/js/calendar-setup.js"/>'></script>
		<script languaje="javaScript">
			var nav4=window.Event ? true : false;

			function acepteNumeros(eve) {
				var key=nav4?eve.which :eve.keyCode;
				return(key==46 || key<=13 || (key>=48 && key<=57));
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
			
		
			function ajaxArea() {
				borrar_combo(document.frmNuevo.plaAreaGestion); 
				document.frmAjax.ajax[0].value=document.frmNuevo.plaVigencia.value;
				document.frmAjax.ajax[1].value=document.frmNuevo.plaInstitucion.value;
				document.frmAjax.ajax[2].value=document.frmNuevo.plaCodigo.value;
				document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_AREA}"/>';
		 		document.frmAjax.target="frameAjax";
				document.frmAjax.submit();
			}
			
			
			function ajaxLinea() {
				borrar_combo(document.frmNuevo.plaLineaAccion); 
				document.frmAjax.ajax[0].value=document.frmNuevo.plaAreaGestion.value;
				document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_LINEA}"/>';
		 		document.frmAjax.target="frameAjax";
				document.frmAjax.submit();
			}
			
		
			function guardar() {
			
				if(validarForma(document.frmNuevo)) {
					
					var vigencia = document.frmNuevo.plaVigencia.value;
					
					var fechacumpl = document.frmNuevo.SEGFECHAREALCUMPLIM.value.split("/");
					
					if(vigencia != fechacumpl[2]) {
						alert("La Fecha de Entrega Real debe pertenecer al año de vigencia");
						return false;
					}
					
					document.frmNuevo.cmd.value = document.frmNuevo.GUARDAR.value;
					document.frmNuevo.submit();
					
				}
				
			}
			
	
			function hacerValidaciones_frmNuevo(forma) {
			
				// Pregunta si en el formulario hay un campo de Demanda habilitado para hacer las validaciones correspondientes
				if(forma.plaPorcentaje1) {
					validarSeguimientoVsDemanda(); 
				}
				
				// Seguimiento
				if(forma.plaSeguimiento1.disabled==false) validarEntero(forma.plaSeguimiento1, "- Seguimiento 1er Trimestre", 0, 999999999999)
				if(forma.plaSeguimiento2.disabled==false) validarEntero(forma.plaSeguimiento2, "- Seguimiento 2do Trimestre", 0, 999999999999)
				if(forma.plaSeguimiento3.disabled==false) validarEntero(forma.plaSeguimiento3, "- Seguimiento 3er Trimestre", 0, 999999999999)
				if(forma.plaSeguimiento4.disabled==false) validarEntero(forma.plaSeguimiento4, "- Seguimiento 4to Trimestre", 0, 999999999999)
				
				// Demanda
				if(forma.plaPorcentaje1 && forma.plaPorcentaje1.disabled==false){if(trim(forma.plaPorcentaje1.value).length==0)forma.plaPorcentaje1.value=0;validarFloat(forma.plaPorcentaje1, "- Demanda 1er Periodo", 0.0, 999999999999)}
				if(forma.plaPorcentaje2 && forma.plaPorcentaje2.disabled==false){if(trim(forma.plaPorcentaje2.value).length==0)forma.plaPorcentaje2.value=0;validarFloat(forma.plaPorcentaje2, "- Demanda 2do Periodo", 0.0, 999999999999)}
				if(forma.plaPorcentaje3 && forma.plaPorcentaje3.disabled==false){if(trim(forma.plaPorcentaje3.value).length==0)forma.plaPorcentaje3.value=0;validarFloat(forma.plaPorcentaje3, "- Demanda 3er Periodo", 0.0, 999999999999)}
				if(forma.plaPorcentaje4 && forma.plaPorcentaje4.disabled==false){if(trim(forma.plaPorcentaje4.value).length==0)forma.plaPorcentaje4.value=0;validarFloat(forma.plaPorcentaje4, "- Demanda 4to Periodo", 0.0, 999999999999)}
				
				var tipoActividad = '<c:out value="${sessionScope.seguimientoVO.TIPOACTIVIDAD}"/>';
				
				if(tipoActividad == "PIMA - PIGA") {
					validarEntero(forma.PRESUPUESTOEJECUTADO1, "- Falta indicar el presupuesto ejecutado", 0, 1000000000000);
				}
				
				return false;
				
			}
			
			
			function limpiarCaracteresSeguimiento() {
			
				var retorno = true;
				var crono1 = document.getElementsByName("plaSeguimiento1")[0].value;
				var crono2 = document.getElementsByName("plaSeguimiento2")[0].value;
				var crono3 = document.getElementsByName("plaSeguimiento3")[0].value;
				var crono4 = document.getElementsByName("plaSeguimiento4")[0].value;
				
				if(!Number.isInteger(parseInt(crono1)) || parseInt(crono1) < 0) {
					if(crono1 != "") {
						alert("Debe registrar números enteros positivos en el campo seguimiento del primer trimestre");
						document.getElementsByName("plaSeguimiento1")[0].value = "";
						retorno = false;
					}
				} else {
					document.getElementsByName("plaSeguimiento1")[0].value = parseInt(crono1);
				}
				
				if(!Number.isInteger(parseInt(crono2)) || parseInt(crono2) < 0) {
					if(crono2 != "") {
						alert("Debe registrar números enteros positivos en el campo seguimiento del segundo trimestre");
						document.getElementsByName("plaSeguimiento2")[0].value = "";
						retorno = false;
					}
				} else {
					document.getElementsByName("plaSeguimiento2")[0].value = parseInt(crono2);
				}
				
				if(!Number.isInteger(parseInt(crono3)) || parseInt(crono3) < 0) {
					if(crono3 != "") {
						alert("Debe registrar números enteros positivos en el campo seguimiento del tercer trimestre");
						document.getElementsByName("plaSeguimiento3")[0].value = "";
						retorno = false;
					}
				} else {
					document.getElementsByName("plaSeguimiento3")[0].value = parseInt(crono3);
				}
				
				if(!Number.isInteger(parseInt(crono4)) || parseInt(crono4) < 0) {
					if(crono4 != "") {
						alert("Debe registrar números enteros positivos en el campo seguimiento del cuarto trimestre");
						document.getElementsByName("plaSeguimiento4")[0].value = "";
						retorno = false;
					}
				} else {
					document.getElementsByName("plaSeguimiento4")[0].value = parseInt(crono4);
				}
				
				return retorno;
				
			}
			
			
			function setVisible() {
			
				if(document.frmNuevo.plaVigencia.value > 2010) {
			   		document.getElementById('plaObjetivo').style.display='none';
			   		document.getElementById('placcodobjetivo').style.display='';
			  	} else {
			   		document.getElementById('plaObjetivo').style.display='';
			   		document.getElementById('placcodobjetivo').style.display='none';
			  	}
			}
			
	
			function limpiarCaracteresDemanda() {
			
				var retorno = true;
				var crono1 = document.getElementsByName("plaPorcentaje1")[0].value;
				var crono2 = document.getElementsByName("plaPorcentaje2")[0].value;
				var crono3 = document.getElementsByName("plaPorcentaje3")[0].value;
				var crono4 = document.getElementsByName("plaPorcentaje4")[0].value;
				
				if(!Number.isInteger(parseInt(crono1)) || parseInt(crono1) < 0) {
					if(crono1 != "") {
						alert("Debe registrar números enteros positivos en el campo demanda del primer trimestre");
						document.getElementsByName("plaPorcentaje1")[0].value = "";
						retorno = false;
					}
				} else {
					document.getElementsByName("plaPorcentaje1")[0].value = parseInt(crono1);
				}
				
				if(!Number.isInteger(parseInt(crono2)) || parseInt(crono2) < 0) {
					if(crono2 != "") {
						alert("Debe registrar números enteros positivos en el campo demanda del segundo trimestre");
						document.getElementsByName("plaPorcentaje2")[0].value = "";
						retorno = false;
					}
				} else {
					document.getElementsByName("plaPorcentaje2")[0].value = parseInt(crono2);
				}
				
				if(!Number.isInteger(parseInt(crono3)) || parseInt(crono3) < 0) {
					if(crono3 != "") {
						alert("Debe registrar números enteros positivos en el campo demanda del tercer trimestre");
						document.getElementsByName("plaPorcentaje3")[0].value = "";
						retorno = false;
					}
				} else {
					document.getElementsByName("plaPorcentaje3")[0].value = parseInt(crono3);
				}
				
				if(!Number.isInteger(parseInt(crono4)) || parseInt(crono4) < 0) {
					if(crono4 != "") {
						alert("Debe registrar números enteros positivos en el campo demanda del cuarto trimestre");
						document.getElementsByName("plaPorcentaje4")[0].value = "";
						retorno = false;
					}
				} else {
					document.getElementsByName("plaPorcentaje4")[0].value = parseInt(crono4);
				}
				
				return retorno;
				
			}
			
	
			function validarSeguimientoVsDemanda() {
           
           		// Pregunta si los valores de Seguimiento y Demanda son válidos
                if (limpiarCaracteresDemanda() && limpiarCaracteresSeguimiento()) {
               
                    var segui1 = document.getElementsByName("plaSeguimiento1")[0].value;
                    var segui2 = document.getElementsByName("plaSeguimiento2")[0].value;
                    var segui3 = document.getElementsByName("plaSeguimiento3")[0].value;
                    var segui4 = document.getElementsByName("plaSeguimiento4")[0].value;
                   
                    var demanda1 = document.getElementsByName("plaPorcentaje1")[0].value;
                    var demanda2 = document.getElementsByName("plaPorcentaje2")[0].value;
                    var demanda3 = document.getElementsByName("plaPorcentaje3")[0].value;
                    var demanda4 = document.getElementsByName("plaPorcentaje4")[0].value;
                   
                    if(isNaN(parseInt(demanda1))){demanda1=0;}
                    if(isNaN(parseInt(demanda2))){demanda2=0;}
                    if(isNaN(parseInt(demanda3))){demanda3=0;}
                    if(isNaN(parseInt(demanda4))){demanda4=0;}
                   
                    if(isNaN(parseInt(segui1))){segui1=0;}
                    if(isNaN(parseInt(segui2))){segui2=0;}
                    if(isNaN(parseInt(segui3))){segui3=0;}
                    if(isNaN(parseInt(segui4))){segui4=0;}
                   
                    if(parseInt(segui1) > parseInt(demanda1)) {
                        alert("El valor de seguimiento es mayor al valor de demanda en el primer trimestre");
                        document.getElementsByName("plaPorcentaje11")[0].value = 0;
                    }
                   
                    if(parseInt(segui2) > parseInt(demanda2)) {
                        alert("El valor de seguimiento es mayor al valor de demanda en el segundo trimestre");
                        document.getElementsByName("plaPorcentaje22")[0].value = 0;
                    }
                   
                    if(parseInt(segui3) > parseInt(demanda3)) {
                        alert("El valor de seguimiento es mayor al valor de demanda en el tercer trimestre");
                        document.getElementsByName("plaPorcentaje33")[0].value = 0;
                    }
                   
                    if(parseInt(segui4) > parseInt(demanda4)) {
                        alert("El valor de seguimiento es mayor al valor de demanda en el cuarto trimestre");
                        document.getElementsByName("plaPorcentaje44")[0].value = 0;
                    }
                   
                }
               
            }
            			
			
			function peticion(trimestre, vigencia, codNivel, placCodigo, placDependencia, idUsuario, usuario, disabled) {
				
				var rutaBase = "<%=rutaArchivo %>"; 
				if(disabled != "") {
				 
					alert("No se puede cargar el archivo. El trimestre no está habilitado" );
				 
				} else {
				
					/*
					//--- Extensiones permitidas ---
						Texto sin formato (TXT).
					Documentos (DOC, DOCX, RTF). 					
					Hojas de cálculo (XLS, XLSX, CSV).
					Archivos de impresión (PDF). 
					Presentaciones (PPT, PPTX). 
					Archivos comprimidos (ZIP, RAR). 
					*/
					var fileInput = document.getElementById('boton' + trimestre);
	    			var filePath = fileInput.value;
	    			var fileNameWithExtension = document.getElementById('boton' + trimestre).files[0].name;
	    			var fileNameWithoutExtension = (fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf("."))).toLowerCase();
	    			
					var allowedExtensions = /(.txt|.doc|.docx|.rtf|.xls|.xlsx|.csv|.pdf|.ppt|.pptx|.zip|.rar)$/i;
				 				 
				    if(!allowedExtensions.exec(filePath)){
				        alert('No se permite el almacenamiento de archivos con este tipo de formato. ' +
				        'Por favor consultar los lineamientos para la Evaluación de la Gestión por Dependencias.');
				        fileInput.value = '';
				        return false;
				    }					
      				
				    if (fileNameWithoutExtension.length > 80) {
				    	alert('El nombre del archivo debe contener máximo 80 caracteres');
				        fileInput.value = '';
				        return false;
				    }
				    
				    var allowedName = /^[0-9a-zA-Z\_\-\s]+$/;

				    if (!allowedName.exec(fileNameWithoutExtension)) {
				    	alert('La plataforma no permite almacenar nombres de archivo que contengan palabras con acento o con caracteres especiales');
				        fileInput.value = '';
				        return false;
				    }
				    
				    
					var tamanioArchivo = document.getElementById('boton' + trimestre).files[0].size;					
					tamanioArchivo =  tamanioArchivo / 1048576;
					console.log("inicia peticion");
					//alert("tamaño archivo en megaBytes: " + tamanioArchivo);
					
					if(tamanioArchivo > 30.05) {
					
						alert("No se puede cargar el archivo. El Archivo Excede el tamaño máximo de 30 MB" );
						
					} else {
					
						var formData = new FormData();
					
						//formData.append("accountnum", 123456); // number 123456 is immediately converted to string "123456"

						formData.append("file", document.getElementById('boton' + trimestre).files[0]);
						
						formData.append("vigencia", vigencia);
						formData.append("codNivel", codNivel);
						formData.append("placCodigo", placCodigo);
						formData.append("placDependencia", placDependencia);
						formData.append("trimestre", trimestre);
						formData.append("idUsuario", idUsuario);
						formData.append("usuario", usuario);
						
						document.frmNuevo.cmd.value = 5;  
						document.frmNuevo.id.value = vigencia;
						document.frmNuevo.id2.value = placDependencia;
						document.frmNuevo.id3.value = placCodigo;
						
						var request = new XMLHttpRequest();
						
						//request.open("POST", "http://172.16.70.11:7804/ApoyoEscolarBE/api/gestionPoa/cargarArchivo");	//desarrollo
						//request.open("POST", "http://172.16.70.20:1934/ApoyoEscolarBE/api/gestionPoa/cargarArchivo");	//pruebas
						//request.open("POST", "https://reportesapoyo.educacionbogota.edu.co/ApoyoEscolarBE/api/gestionPoa/cargarArchivo");	//produccion
						request.open("POST", rutaBase + "ApoyoEscolarBE/api/gestionPoa/cargarArchivo");	
						
						console.log("antes de enviar");						//agregar
						var element = document.getElementById("loading");	//agregar
						element.classList.add("active");					//agregar
						
						// 06-04-2021
						request.onreadystatechange = function() {
	
	                        if (request.readyState == XMLHttpRequest.DONE) {

	                            var serverResponse = JSON.parse(request.responseText);
	                            
	                            if (serverResponse.status == "OK") {
	                            	document.frmNuevo.submit();
	                            	alert('La evidencia fue cargada de manera exitosa');
	                            } else {
	                            	document.frmNuevo.submit();
	                            	alert(serverResponse.msg);
	                            }
	                        }
	                    }
					
						request.send(formData);
					
						/*
						document.frmNuevo.cmd.value = 5;  
						document.frmNuevo.id.value=vigencia;
						document.frmNuevo.id2.value=placDependencia;
						document.frmNuevo.id3.value=placCodigo;
						  
		        		document.frmNuevo.submit();
		        		alert("Archivo subido.");
		        		*/
					
					}
				}
			}
			
			
			function mensajeUnidadMedida() {
			
				var e = document.getElementById("unidadMedida");		
				var nombreUnidadMedida = e.options[e.selectedIndex].text;
				
				if (nombreUnidadMedida === 'Otro') {
					
					var e1 = document.getElementById("placOtroCual");
					alert("Recuerde que el documento a cargar debe corresponder a un(a) " + e1.value);
					
				} else {
				
					alert("Recuerde que el documento a cargar debe corresponder a un(a) "+ nombreUnidadMedida);
					
				}
				
			}
	
			
			function eliminarArchivo(idSeguimiento, idUsuario, vigencia, codNivel, placCodigo, placDependencia, disabled) {
				
				//alert("ids: "+idSeguimiento+", "+ vigencia + ", " + codNivel +", "+ placDependencia+ ", "+ placCodigo);
	
				var rutaBase = "<%=rutaArchivo %>";
				if(disabled != "") {
					 
					alert("No se puede Eliminar el archivo. El trimestre no está habilitado" );
					
		 		} else {
		 		
					var formData = new FormData();	
					var request = new XMLHttpRequest();
					
					document.frmNuevo.cmd.value = 5;  
					document.frmNuevo.id.value = vigencia;
					document.frmNuevo.id2.value = placDependencia;
					document.frmNuevo.id3.value = placCodigo;
					
					//request.open("GET", "http://172.16.70.11:7804/ApoyoEscolarBE/api/gestionPoa/eliminarArchivo?idEvidenciaSeguimiento="+idSeguimiento+"&idUsuario="+idUsuario);	//desarrollo
					//request.open("GET", "http://172.16.70.20:1934/ApoyoEscolarBE/api/gestionPoa/eliminarArchivo?idEvidenciaSeguimiento="+idSeguimiento+"&idUsuario="+idUsuario);	//pruebas
					//request.open("GET", "https://reportesapoyo.educacionbogota.edu.co/ApoyoEscolarBE/api/gestionPoa/eliminarArchivo?idEvidenciaSeguimiento="+idSeguimiento+"&idUsuario="+idUsuario);	//produccion
					request.open("GET", rutaBase + "ApoyoEscolarBE/api/gestionPoa/eliminarArchivo?idEvidenciaSeguimiento="+idSeguimiento+"&idUsuario="+idUsuario+"&vigencia="+vigencia);
					
					console.log("antes de enviar");
					
					// 06-04-2021
					request.onreadystatechange = function() {
			
		                if (request.readyState == XMLHttpRequest.DONE) {
		                    
		                    var serverResponse = JSON.parse(request.responseText);
		                    
		                    if (serverResponse.status == "OK") {
		                    	document.frmNuevo.submit();
		                    	alert("El archivo fue eliminado correctamente");
		                    } else {
                            	document.frmNuevo.submit();
	                            alert(serverResponse.msg);
                            }
		                }
		            }
					
					request.send(formData);
		
					/*
					document.frmNuevo.cmd.value = 5;  
					document.frmNuevo.id.value=vigencia;
					document.frmNuevo.id2.value=placDependencia;
					document.frmNuevo.id3.value=placCodigo;
			      	document.frmNuevo.submit();
			      	alert("Archivo eliminado.");
			      	*/
      			}
			}
			
			function verObsEvaluacion(textoObservacion, trimestre) {
			
				alert("Observación " + trimestre + ": " + textoObservacion );
			}
			
		</script>
		
	</head>

	<c:import url="/mensaje.jsp" />
	
	<body onLoad="mensaje(document.getElementById('msg'));">
	
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%" valign='top'>
					<div style="width:100%;height:380px;overflow:auto;vertical-align:top;background-color:#ffffff;">
						<c:import url="/poa/seguimiento/Filtro.do" />
					</div>
				</td>
			</tr>
		</table>
		
		<c:if test="${requestScope.buscaSeguimiento==1}">
		
			<form method="post" name="frmAjax" action='<c:url value="/poa/seguimiento/Ajax.do"/>'>
				<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_ACTIVIDAD}"/>'>
				<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
				<input type="hidden" name="ajax">
				<input type="hidden" name="ajax">
				<input type="hidden" name="ajax">
			</form>
			
			<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/poa/seguimiento/Save.jsp"/>'>
				<input type="hidden" name="id" value=""><input type="hidden" name="id2" value=""><input type="hidden" name="id3" value=""><input type="hidden" name="id4" value="">
				<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_ACTIVIDAD}"/>'>
				<input type="hidden" name="cmd" value=''>
				<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
				<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
				<input type="hidden" name="plaCodigo" value='<c:out value="${sessionScope.seguimientoVO.plaCodigo}"/>'>
				<input type="hidden" name="plaInstitucion" value='<c:out value="${sessionScope.login.instId}"/>'>
				
				<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
					<caption>Registro / edición de seguimiento</caption>
					<tr>
						<td width="45%">
							<c:if test="${sessionScope.NivelPermiso==2 && sessionScope.filtroSeguimientoVO.filFechaHabil==true && sessionScope.seguimientoVO.plaVigencia eq sessionScope.filtroSeguimientoVO.filVigenciaPoa}">
								<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
							</c:if>
							<!-- <a class="manual" href='<c:url value="/private/manual/Manual_POA_Seguimiento.pdf?tipo=pdf"/>' target="_blank">&nbsp;&nbsp;&nbsp;Descargue el manual aquí</a> -->
						</td>
					</tr>
				</table>
				
				<table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
					<tr>
						<td width="20%"	title='<c:out value="${sessionScope.filtroSeguimientoVO.lblVigencia}"/>'>
							<span class="style2">*</span> Vigencia:
						</td>
						<td width="80%" align="left">
							<select name="plaVigencia" onchange="ajaxArea()" style="width:220px;" <c:out value="${sessionScope.seguimientoVO.plaDisabled}"/>>
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
									<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.seguimientoVO.plaVigencia}">selected</c:if>>
										<c:out value="${vig.nombre}" />
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td title='<c:out value="${sessionScope.filtroSeguimientoVO.lblObjetivo}"/>'>
							<span class="style2">*</span> Objetivo Estratégico:
						</td>
						<td align="left">
							<c:if test="${sessionScope.seguimientoVO.plaDisabled == 'disabled'}">
								<select title='<c:out value="${sessionScope.seguimientoVO.placcodobjetivoText}"/>' style="width:700px; display:none" name="placcodobjetivo" id="placcodobjetivo">
									<c:forEach begin="0" items="${sessionScope.listaObjetivos}" var="vig">
										<c:if test="${vig.codigo == sessionScope.seguimientoVO.placcodobjetivo}">
											<option title='<c:out value="${vig.nombre}"/>' value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo == sessionScope.seguimientoVO.placcodobjetivo  }">selected</c:if>>
												<c:out value="${vig.nombre}" />
											</option>
										</c:if>
									</c:forEach>
								</select>
							</c:if>
							<c:if test="${sessionScope.seguimientoVO.plaDisabled != 'disabled'}">
								<select style="width:700px; display:none" name="placcodobjetivo" id="placcodobjetivo" <c:out value="${sessionScope.seguimientoVO.plaDisabled}"/>>
									<option value="-99" selected>-- Seleccione uno --</option>
									<c:forEach begin="0" items="${sessionScope.listaObjetivos}" var="vig">
										<option title='<c:out value="${vig.nombre}"/>' value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo == sessionScope.seguimientoVO.placcodobjetivo  }">selected</c:if>>
											<c:out value="${vig.nombre}" />
										</option>
									</c:forEach>
								</select>
							</c:if>
							<textarea name="plaObjetivo" id="plaObjetivo" cols="110" rows="3" <c:out value="${sessionScope.seguimientoVO.plaDisabled}"/> onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);"><c:out value="${sessionScope.seguimientoVO.placcodobjetivoText}" /></textarea>
						</td>
					</tr>
					<tr>
						<td	title='<c:out value="${sessionScope.seguimientoVO.lblAreaGestion}"/>'>
							<span class="style2">*</span> Tipo de Actividad:
						</td>
						<td align="left">
							<input name="TIPOACTIVIDAD" style="width: 220px;" type="text" value="<c:out value="${sessionScope.seguimientoVO.TIPOACTIVIDAD}"/>" readonly />
						</td>
					</tr>
					<c:if test="${sessionScope.seguimientoVO.TIPOACTIVIDAD == 'PIMA - PIGA'}">
						<tr>
							<td	title='<c:out value="${sessionScope.seguimientoVO.lblAreaGestion}"/>'>
								<span class="style2">*</span> Acción de mejoramiento:
							</td>
							<td align="left">
								<textarea name="ACCIONMEJORAMIENTO" type="text" cols="110" rows="3" readonly><c:out value="${sessionScope.seguimientoVO.ACCIONMEJORAMIENTO}" /></textarea>
							</td>
						</tr>
						<!-- INICIO acción de mejoramiento 'otras' -->
						<c:if test="${sessionScope.seguimientoVO.ACCIONMEJORAMIENTO == 'otras'}">
							<tr>
								<td	title='<c:out value="${sessionScope.seguimientoVO.lblAreaGestion}"/>'>
									<span class="style2">*</span> Cuál?:
								</td>
								<td align="left">
									<input name="CUAL" type="text" value="<c:out value="${sessionScope.seguimientoVO.CUAL}"/>" readonly />
								</td>
							</tr>
						</c:if>
						<!-- FIN acción de mejoramiento 'otras' -->
					</c:if>
					<tr>
						<td title='<c:out value="${sessionScope.filtroSeguimientoVO.lblActividad}"/>'>
							<span class="style2">*</span> Actividad / Tarea:
						</td>
						<td align="left">
							<textarea name="plaActividad" cols="110" rows="3" <c:out value="${sessionScope.seguimientoVO.plaDisabled}"/> onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);"><c:out value="${sessionScope.seguimientoVO.plaActividad}" /></textarea>
						</td>
					</tr>
					<tr>
						<td title='<c:out value="${sessionScope.filtroSeguimientoVO.lblAreaGestion}"/>'>
							<span class="style2">*</span> Área de Gestión:
						</td>
						<td align="left">
							<select name="plaAreaGestion" style="width:700px;" <c:out value="${sessionScope.seguimientoVO.plaDisabled}"/> onchange="ajaxLinea()">
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaAreaGestion}" var="area">
									<option value="<c:out value="${area.codigo}"/>" <c:if test="${area.codigo==sessionScope.seguimientoVO.plaAreaGestion}">selected</c:if>>
										<c:out value="${area.nombre}" />
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td title='<c:out value="${sessionScope.filtroSeguimientoVO.lblLineaAccion}"/>'>
							<span class="style2">*</span> Línea de Acción /	Proceso / Proyecto:
						</td>
						<td align="left">
							<select name="plaLineaAccion" style="width:700px;" <c:out value="${sessionScope.seguimientoVO.plaDisabled}"/>>
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaLineaAccion}" var="linea">
									<option value="<c:out value="${linea.codigo}"/>" <c:if test="${linea.codigo==sessionScope.seguimientoVO.plaLineaAccion}">selected</c:if>>
										<c:out value="${linea.nombre}" />
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td	title='<c:out value="${sessionScope.filtroSeguimientoVO.lblTipoMeta}"/>'>
							<span class="style2">*</span> Ponderador:
						</td>
						<td align="left">
							<input type="text" name="plaPonderador" maxlength="5" size="3" <c:out value="${sessionScope.seguimientoVO.plaDisabled}"/> readonly value='<c:out value="${sessionScope.seguimientoVO.plaPonderador}"/>'></input>%
						</td>
					</tr>
					<tr>
						<td	title='<c:out value="${sessionScope.filtroSeguimientoVO.lblTipoMeta}"/>'>
							<span class="style2">*</span> Tipo de Meta:
						</td>
						<td align="left">
							<select name="plaTipoMeta" style="width:700px;" <c:out value="${sessionScope.seguimientoVO.plaDisabled}"/>>
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaTipoMeta}" var="linea">
									<option value="<c:out value="${linea.codigo}"/>" <c:if test="${linea.codigo==sessionScope.seguimientoVO.plaTipoMeta}">selected</c:if>>
										<c:out value="${linea.nombre}" />
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
				
				<table align="center" width="95%" border="1" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<table align="center" width="100%" border="0" cellpadding="1" cellspacing="0">
								<tr>
									<th colspan="4" class="Fila0">Meta Anual / Producto Final</th>
								</tr>
								<tr>
									<td width="20%">
										<span class="style2">*</span> Unidad de Medida:
									</td>
									<td width="20%">
										<select id="unidadMedida" name="plaUnidadMedida" style="width:270px;" <c:out value="${sessionScope.seguimientoVO.plaDisabled}"/>>
											<option value="-99" selected>-Seleccione uno-</option>
											<c:forEach begin="0" items="${requestScope.listaUnidadMedida}" var="unidad">
												<option value="<c:out value="${unidad.codigo}"/>"
													<c:if test="${unidad.codigo==sessionScope.seguimientoVO.plaUnidadMedida}">selected</c:if>>
													<c:out value="${unidad.nombre}" />
												</option>
											</c:forEach>
										</select>
									</td>
									<td width="10%" align="right">¿Cuál?:</td>
									<td width="30%" align="left">
										<input type="text" name="placOtroCual" id="placOtroCual" maxlength="100" size="40" <c:out value="${sessionScope.seguimientoVO.plaDisabled}"/> value='<c:out value="${sessionScope.seguimientoVO.PLACOTROCUAL}"/>'></input>
									</td>
									<td width="10%" align="left">
										<span class="style2">*</span> Cantidad:
									</td>
									<td width="10%">
										<input type="text" name="plaCantidad" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoVO.plaCantidad}"/>'></input>
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
									<td title='<c:out value="${sessionScope.filtroSeguimientoVO.lblCronograma1}"/>' align="center">
										<span class="style2">*</span> 1er Trimestre:
									</td>
									<td title='<c:out value="${sessionScope.filtroSeguimientoVO.lblCronograma2}"/>' align="center">
										<span class="style2">*</span> 2do Trimestre:
									</td>
									<td	title='<c:out value="${sessionScope.filtroSeguimientoVO.lblCronograma3}"/>' align="center">
										<span class="style2">*</span> 3er Trimestre:
									</td>
									<td	title='<c:out value="${sessionScope.filtroSeguimientoVO.lblCronograma4}"/>'	align="center">
										<span class="style2">*</span> 4to Trimestre:
									</td>
								</tr>
								<tr>
									<td align="center">
										<input type="text" name="plaCronograma1" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoVO.plaCronograma1}"/>'></input>
									</td>
									<td align="center">
										<input type="text" name="plaCronograma2" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoVO.plaCronograma2}"/>'></input>
									</td>
									<td align="center">
										<input type="text" name="plaCronograma3" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoVO.plaCronograma3}"/>'></input>
									</td>
									<td align="center">
										<input type="text" name="plaCronograma4" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoVO.plaCronograma4}"/>'></input>
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
									<th colspan="4" class="Fila0">Seguimiento</th>
								</tr>
								<tr>
									<td	title='<c:out value="${sessionScope.filtroSeguimientoVO.lblSeguimiento1}"/>' align="center">1er Trimestre:
									</td>
									<td title='<c:out value="${sessionScope.filtroSeguimientoVO.lblSeguimiento2}"/>' align="center">2do Trimestre:
									</td>
									<td	title='<c:out value="${sessionScope.filtroSeguimientoVO.lblSeguimiento3}"/>' align="center">3er Trimestre:
									</td>
									<td title='<c:out value="${sessionScope.filtroSeguimientoVO.lblSeguimiento4}"/>' align="center">4to Trimestre:
									</td>
								</tr>
								<tr>
<!-- 									<td align="center"> -->
<%-- 										<input type="text" name="plaSeguimiento1" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoVO.plaDisabled1}"/> onKeyPress='return acepteNumeros(event)' onblur='limpiarCaracteresSeguimiento()' value='<c:out value="${sessionScope.seguimientoVO.plaSeguimiento1}"/>'></input> --%>
<!-- 									</td> -->
<!-- 									<td align="center"> -->
<%-- 										<input type="text" name="plaSeguimiento2" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoVO.plaDisabled2}"/> onKeyPress='return acepteNumeros(event)' onblur='limpiarCaracteresSeguimiento()' value='<c:out value="${sessionScope.seguimientoVO.plaSeguimiento2}"/>'></input> --%>
<!-- 									</td> -->
<!-- 									<td align="center"> -->
<%-- 										<input type="text" name="plaSeguimiento3" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoVO.plaDisabled3}"/> onKeyPress='return acepteNumeros(event)' onblur='limpiarCaracteresSeguimiento()' value='<c:out value="${sessionScope.seguimientoVO.plaSeguimiento3}"/>'></input> --%>
<!-- 									</td> -->
<!-- 									<td align="center"> -->
<%-- 										<input type="text" name="plaSeguimiento4" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoVO.plaDisabled4}"/> onKeyPress='return acepteNumeros(event)' onblur='limpiarCaracteresSeguimiento()' value='<c:out value="${sessionScope.seguimientoVO.plaSeguimiento4}"/>'></input> --%>
<!-- 									</td> -->
									
								<c:choose>
								  <c:when test="${sessionScope.seguimientoVO.rojoNaranjaSeguimiento1 == 1}">
								    <td align="center">
										<input class="styleRedSeguimiento" type="text" name="plaSeguimiento1" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoVO.plaDisabled1}"/> onKeyPress='return acepteNumeros(event)' onblur='limpiarCaracteresSeguimiento()' value='<c:out value="${sessionScope.seguimientoVO.plaSeguimiento1}"/>'></input>
									</td>
								  </c:when>	
								  <c:when test="${sessionScope.seguimientoVO.rojoNaranjaSeguimiento1 == 2}">
								    <td align="center">
										<input class="styleOrangeSeguimiento" type="text" name="plaSeguimiento1" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoVO.plaDisabled1}"/> onKeyPress='return acepteNumeros(event)' onblur='limpiarCaracteresSeguimiento()' value='<c:out value="${sessionScope.seguimientoVO.plaSeguimiento1}"/>'></input>
									</td>
								  </c:when>							 
								  <c:otherwise>
								    <td align="center">
										<input type="text" name="plaSeguimiento1" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoVO.plaDisabled1}"/> onKeyPress='return acepteNumeros(event)' onblur='limpiarCaracteresSeguimiento()' value='<c:out value="${sessionScope.seguimientoVO.plaSeguimiento1}"/>'></input>
									</td>								   
								  </c:otherwise>
								</c:choose>
								<c:choose>
								  <c:when test="${sessionScope.seguimientoVO.rojoNaranjaSeguimiento2 == 1}">
								    <td align="center">
										<input class="styleRedSeguimiento" type="text" name="plaSeguimiento2" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoVO.plaDisabled2}"/> onKeyPress='return acepteNumeros(event)' onblur='limpiarCaracteresSeguimiento()' value='<c:out value="${sessionScope.seguimientoVO.plaSeguimiento2}"/>'></input>
									</td>
								  </c:when>	
								  <c:when test="${sessionScope.seguimientoVO.rojoNaranjaSeguimiento2 == 2}">
								    <td align="center">
										<input class="styleOrangeSeguimiento" type="text" name="plaSeguimiento2" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoVO.plaDisabled2}"/> onKeyPress='return acepteNumeros(event)' onblur='limpiarCaracteresSeguimiento()' value='<c:out value="${sessionScope.seguimientoVO.plaSeguimiento2}"/>'></input>
									</td>
								  </c:when>								 
								  <c:otherwise>
								   <td align="center">
										<input type="text" name="plaSeguimiento2" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoVO.plaDisabled2}"/> onKeyPress='return acepteNumeros(event)' onblur='limpiarCaracteresSeguimiento()' value='<c:out value="${sessionScope.seguimientoVO.plaSeguimiento2}"/>'></input>
									</td>
								  </c:otherwise>
								</c:choose>
								<c:choose>
								  <c:when test="${sessionScope.seguimientoVO.rojoNaranjaSeguimiento3 == 1}">
								    <td align="center">
										<input class="styleRedSeguimiento" type="text" name="plaSeguimiento3" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoVO.plaDisabled3}"/> onKeyPress='return acepteNumeros(event)' onblur='limpiarCaracteresSeguimiento()' value='<c:out value="${sessionScope.seguimientoVO.plaSeguimiento3}"/>'></input>
									</td>
								  </c:when>	
								  <c:when test="${sessionScope.seguimientoVO.rojoNaranjaSeguimiento3 == 2}">
								    <td align="center">
										<input class="styleOrangeSeguimiento" type="text" name="plaSeguimiento3" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoVO.plaDisabled3}"/> onKeyPress='return acepteNumeros(event)' onblur='limpiarCaracteresSeguimiento()' value='<c:out value="${sessionScope.seguimientoVO.plaSeguimiento3}"/>'></input>
									</td>
								  </c:when>							 
								  <c:otherwise>
								    <td align="center">
										<input type="text" name="plaSeguimiento3" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoVO.plaDisabled3}"/> onKeyPress='return acepteNumeros(event)' onblur='limpiarCaracteresSeguimiento()' value='<c:out value="${sessionScope.seguimientoVO.plaSeguimiento3}"/>'></input>
									</td>
								  </c:otherwise>
								</c:choose>
								<c:choose>
								  <c:when test="${sessionScope.seguimientoVO.rojoNaranjaSeguimiento4 == 1}">
								    <td align="center">
										<input class="styleRedSeguimiento" type="text" name="plaSeguimiento4" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoVO.plaDisabled4}"/> onKeyPress='return acepteNumeros(event)' onblur='limpiarCaracteresSeguimiento()' value='<c:out value="${sessionScope.seguimientoVO.plaSeguimiento4}"/>'></input>
									</td>
								  </c:when>	
								  <c:when test="${sessionScope.seguimientoVO.rojoNaranjaSeguimiento4 == 2}">
								    <td align="center">
										<input class="styleOrangeSeguimiento" type="text" name="plaSeguimiento4" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoVO.plaDisabled4}"/> onKeyPress='return acepteNumeros(event)' onblur='limpiarCaracteresSeguimiento()' value='<c:out value="${sessionScope.seguimientoVO.plaSeguimiento4}"/>'></input>
									</td>
								  </c:when>								 
								  <c:otherwise>
								   <td align="center">
										<input type="text" name="plaSeguimiento4" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoVO.plaDisabled4}"/> onKeyPress='return acepteNumeros(event)' onblur='limpiarCaracteresSeguimiento()' value='<c:out value="${sessionScope.seguimientoVO.plaSeguimiento4}"/>'></input>
									</td>
								  </c:otherwise>
								</c:choose>
									
								
								</tr>
							</table>
						</td>
					</tr>
				</table>
				
				<c:if test="${sessionScope.seguimientoVO.plaMostrarPorcentaje==true}">
					<table align="center" width="95%" border="1" cellpadding="1" cellspacing="0">
						<tr>
							<td>
								<table align="center" width="100%" border="0" cellpadding="1" cellspacing="0">
									<tr>
										<th colspan="4" class="Fila0">Demanda</th>
									</tr>
									<tr>
										<td	title='<c:out value="${sessionScope.filtroSeguimientoVO.lblPorcentaje1}"/>' align="center">1er Trimestre:
										</td>
										<td	title='<c:out value="${sessionScope.filtroSeguimientoVO.lblPorcentaje2}"/>'	align="center">2do Trimestre:
										</td>
										<td title='<c:out value="${sessionScope.filtroSeguimientoVO.lblPorcentaje3}"/>' align="center">3er Trimestre:
										</td>
										<td title='<c:out value="${sessionScope.filtroSeguimientoVO.lblPorcentaje4}"/>' align="center">4to Trimestre:
										</td>
									</tr>
									<tr>
										<td align="center">
											<input type="text" name="plaPorcentaje1" maxlength="7" size="3" <c:out value="${sessionScope.seguimientoVO.plaDisabled1}"/> onKeyPress='return acepteNumeros(event)' onblur='limpiarCaracteresDemanda()' value='<c:out value="${sessionScope.seguimientoVO.plaPorcentaje1}"/>'></input>
										</td>
										<td align="center">
											<input type="text" name="plaPorcentaje2" maxlength="7" size="3" <c:out value="${sessionScope.seguimientoVO.plaDisabled2}"/> onKeyPress='return acepteNumeros(event)' onblur='limpiarCaracteresDemanda()' value='<c:out value="${sessionScope.seguimientoVO.plaPorcentaje2}"/>'></input>
										</td>
										<td align="center">
											<input type="text" name="plaPorcentaje3" maxlength="7" size="3" <c:out value="${sessionScope.seguimientoVO.plaDisabled3}"/>	onKeyPress='return acepteNumeros(event)' onblur='limpiarCaracteresDemanda()' value='<c:out value="${sessionScope.seguimientoVO.plaPorcentaje3}"/>'></input>
										</td>
										<td align="center">
											<input type="text" name="plaPorcentaje4" maxlength="7" size="3" <c:out value="${sessionScope.seguimientoVO.plaDisabled4}"/> onKeyPress='return acepteNumeros(event)' onblur='limpiarCaracteresDemanda()' value='<c:out value="${sessionScope.seguimientoVO.plaPorcentaje4}"/>'></input>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</c:if>
				
				<table align="center" width="95%" border="1" cellpadding="1" cellspacing="0">
					<tr>
						<td>
							<table align="center" width="100%" border="0"  cellpadding="1" cellspacing="0">
								<tr>
									<th colspan="4" class="Fila0">Resultados de la Evaluación</th>
								</tr>
								<tr>
									<td title='<c:out value="${sessionScope.filtroSeguimientoVO.lblSeguimiento1}"/>' align="center">1er Trimestre:</td>
									<td title='<c:out value="${sessionScope.filtroSeguimientoVO.lblSeguimiento2}"/>' align="center">2do Trimestre:</td>
									<td title='<c:out value="${sessionScope.filtroSeguimientoVO.lblSeguimiento3}"/>' align="center">3er Trimestre:</td>
									<td title='<c:out value="${sessionScope.filtroSeguimientoVO.lblSeguimiento4}"/>' align="center">4to Trimestre:</td>
								</tr>
								<tr>								
									<td align="center"><input type="text" name="califEvalSeg1" maxlength="7" onClick="verObsEvaluacion('<c:out value="${sessionScope.seguimientoVO.obserEvalSeg1}"/>', 1)"
									title="${sessionScope.seguimientoVO.evalTexto1}" 
									size="4" <c:out value="readonly"/> value='<c:out value="${sessionScope.seguimientoVO.califEvalSeg1}"/>'></input></td>
									<td align="center"><input type="text" name="califEvalSeg2" maxlength="7" onClick="verObsEvaluacion('<c:out value="${sessionScope.seguimientoVO.obserEvalSeg2}"/>', 2)" 
									title="${sessionScope.seguimientoVO.evalTexto2}"
									size="4" <c:out value="readonly"/> value='<c:out value="${sessionScope.seguimientoVO.califEvalSeg2}"/>'></input></td>
									<td align="center"><input type="text" name="califEvalSeg3" maxlength="7" onClick="verObsEvaluacion('<c:out value="${sessionScope.seguimientoVO.obserEvalSeg3}"/>', 3)"
									title="${sessionScope.seguimientoVO.evalTexto3}"
									size="4" <c:out value="readonly"/> value='<c:out value="${sessionScope.seguimientoVO.califEvalSeg3}"/>'></input></td>
									<td align="center"><input type="text" name="califEvalSeg4" maxlength="7" onClick="verObsEvaluacion('<c:out value="${sessionScope.seguimientoVO.obserEvalSeg4}"/>', 4)"
									title="${sessionScope.seguimientoVO.evalTexto4}"
									size="4" <c:out value="readonly"/> value='<c:out value="${sessionScope.seguimientoVO.califEvalSeg4}"/>'></input></td>
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
									<td title='<c:out value="${sessionScope.filtroSeguimientoVO.lblSeguimiento1}"/>' align="center">Fecha Prevista de Entrega:
									</td>
								</tr>
								<tr>
									<td style="text-align: center;">
										<input name="SEGFECHACUMPLIMT" id="SEGFECHACUMPLIMT" type="text" size="10" maxlength="10" value='<c:out value="${sessionScope.seguimientoVO.SEGFECHACUMPLIMT}"/>' readonly="readonly">
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
									<td	title='<c:out value="${sessionScope.filtroSeguimientoVO.lblSeguimiento1}"/>' align="center">Fecha de Entrega Real:
									</td>
								</tr>
								<tr>
									<td style="text-align: center;">
										<input <c:if test="${sessionScope.seguimientoVO.plaVigencia eq sessionScope.filtroSeguimientoVO.filVigenciaPoa}"> name="SEGFECHAREALCUMPLIM" id="SEGFECHAREALCUMPLIM"</c:if> type="text" size="10" maxlength="10" value='<c:out value="${sessionScope.seguimientoVO.SEGFECHAREALCUMPLIM}"/>' <c:if test="${sessionScope.seguimientoVO.plaVigencia ne sessionScope.filtroSeguimientoVO.filVigenciaPoa}"> readonly="readonly" </c:if>>
										<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione fecha" id="imgFechareal" style="cursor: pointer;" title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
	
				<table align="center" width="95%" border="1" cellpadding="1" cellspacing="0">
					<tr>
						<td align="center">Responsable:
							<input type="text" name="RESPONSABLE" maxlength="40" readonly size="80" <c:out value="${sessionScope.seguimientoVO.RESPONSABLE}"/> value='<c:out value="${sessionScope.seguimientoVO.RESPONSABLE}"/>'></input>
						</td>
					</tr>
				</table>
	
				<table align="center" width="95%" border="1" cellpadding="1" cellspacing="0" style="display:
					<c:if test="${sessionScope.seguimientoVO.TIPOACTIVIDAD  != null and 'PIMA - PIGA' eq sessionScope.seguimientoVO.TIPOACTIVIDAD}">table;</c:if>
					<c:if test="${sessionScope.seguimientoVO.TIPOACTIVIDAD  != null and 'PIMA - PIGA' ne sessionScope.seguimientoVO.TIPOACTIVIDAD}">none;</c:if>
					<c:if test="${sessionScope.seguimientoVO.TIPOACTIVIDAD  == null }">none;</c:if>">
					<tr>
						<td>
							<table align="center" border="0" cellpadding="1" cellspacing="0" width="100%">
								<tr>
									<td align="center">Tipo de Gasto:
									</td>
									<td>
										<input type="text" size="85" value='<c:out value="${sessionScope.seguimientoVO.TIPOGASTO}"/>' readonly="readonly">
									</td>
								</tr>
								<tr>
									<td align="center">Rubro de Gasto:
									</td>
									<td>
										<input type="text" size="85" value='<c:out value="${sessionScope.seguimientoVO.RUBROGASTO}"/>' readonly="readonly">
									</td>
								</tr>
								<tr>
									<td align="center">
										<c:if test="${sessionScope.seguimientoVO.TIPOGASTO eq 'Funcionamiento'}">Subnivel de Gastos Generales o Servicios Personales</c:if>
										<c:if test="${sessionScope.seguimientoVO.TIPOGASTO eq 'Inversion'}">Nombre del Proyecto de Inversion</c:if>
										<c:if test="${sessionScope.seguimientoVO.TIPOGASTO == null or sessionScope.seguimientoVO.TIPOGASTO eq ''}">Proyecto de Inversion</c:if>:
									</td>
									<td>
										<input type="text" size="85" value="<c:if test="${sessionScope.seguimientoVO.TIPOGASTO eq 'Funcionamiento'}"><c:out value="${sessionScope.seguimientoVO.SUBNIVELGASTO}"/></c:if><c:if test="${sessionScope.seguimientoVO.TIPOGASTO eq 'Inversion'}"><c:out value="${sessionScope.seguimientoVO.PROYECTOINVERSION}"/></c:if>" readonly="readonly">
									</td>
								</tr>
								<tr>
									<td align="center">Fuente de Financiacion:
									</td>
									<td>
										<input type="text" size="85" value='<c:out value="${sessionScope.seguimientoVO.FUENTEFINANCIACION}"/>' readonly="readonly">
									</td>
								</tr>
								<tr>
									<td align="center">Monto Anual:
									</td>
									<td>
										<input type="text" size="85" maxlength="10" value='<c:out value="${sessionScope.seguimientoVO.MONTOANUAL}"/>' readonly="readonly">
									</td>
								</tr>
								<tr>
									<td align="center">Presupuesto Participativo:
									</td>
									<td>
										<input type="text" size="85" maxlength="10"	value='<c:out value="${sessionScope.seguimientoVO.PPTOPARTICIPATIVO}"/>' readonly="readonly">
									</td>
								</tr>
							</table>
	
							<table align="center" width="100%" border="0" cellpadding="1" cellspacing="0">
								<tr>
									<th colspan="4" class="Fila0">Presupuesto Ejecutado</th>
								</tr>
								<tr>
									<td align="center">1er Trimestre</td>
									<td align="center">2do Trimestre</td>
									<td align="center">3er Trimestre</td>
									<td align="center">4to Trimestre</td>
								</tr>
								<tr>
									<td align="center">
										<input type="text" name="PRESUPUESTOEJECUTADO1" size="10" maxlength="10" <c:out value="${sessionScope.seguimientoVO.plaDisabled1}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoVO.PRESUPUESTOEJECUTADO1}"/>'></input>
									</td>
									<td align="center">
										<input type="text" name="PRESUPUESTOEJECUTADO2" size="10" maxlength="10" <c:out value="${sessionScope.seguimientoVO.plaDisabled2}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoVO.PRESUPUESTOEJECUTADO2}"/>'></input>
									</td>
									<td align="center">
										<input type="text" name="PRESUPUESTOEJECUTADO3" size="10" maxlength="10" <c:out value="${sessionScope.seguimientoVO.plaDisabled3}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoVO.PRESUPUESTOEJECUTADO3}"/>'></input>
									</td>
									<td align="center">
										<input type="text" name="PRESUPUESTOEJECUTADO4" size="10" maxlength="10" <c:out value="${sessionScope.seguimientoVO.plaDisabled4}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoVO.PRESUPUESTOEJECUTADO4}"/>'></input>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
	
				<table align="center" width="95%" border="1" cellpadding="1" cellspacing="0">
					<tr>
						<th class="Fila0">Análisis de la actividad</th>
					</tr>
					<tr>
						<td>
							<table align="center" width="100%" border="0" cellpadding="1" cellspacing="0">
								<tr>
									<th colspan="2" class="Fila0">Primer Trimestre</th>
								</tr>
								<tr>
									<td	title='<c:out value="${sessionScope.filtroSeguimientoVO.lblVerificacion1}"/>'>Evidencias de logro:
									</td>
									<td>
										<textarea name="plaVerificacion1" cols="140" rows="5" <c:out value="${sessionScope.seguimientoVO.plaDisabled1}"/> onKeyDown="textCounter(this,500,500);"onKeyUp="textCounter(this,500,500);"><c:out value="${sessionScope.seguimientoVO.plaVerificacion1}" /></textarea>
									</td>
								</tr>
								<tr>
									<td	title='<c:out value="${sessionScope.filtroSeguimientoVO.lblLogros1}"/>'>Logros / Dificultades y Medidas Correctivas:
									</td>
									<td>
										<textarea name="plaLogros1" cols="140" rows="5" <c:out value="${sessionScope.seguimientoVO.plaDisabled1}"/> onKeyDown="textCounter(this,500,500);" onKeyUp="textCounter(this,500,500);"><c:out value="${sessionScope.seguimientoVO.plaLogros1}" /></textarea>
									</td>
								</tr>
							</table>
						
							<c:if test="${sessionScope.NivelPermiso==2}">
								<input type="file" name="file" id="boton1" onclick="mensajeUnidadMedida();"
								 		onchange="peticion(1, <c:out value="${sessionScope.seguimientoVO.plaVigencia}"/>, 
								 		3, <c:out value="${sessionScope.seguimientoVO.plaCodigo}"/>,
								 		<c:out value="${sessionScope.login.instId}"/>, <c:out value="${sessionScope.login.usuarioId}"/>,
								 		'<c:out value="${sessionScope.login.nomPerfil}"/>', '<c:out value="${sessionScope.seguimientoVO.plaDisabled1}"/>');"
										style="width: 0.1px;
										  height: 0.1px;
										  opacity: 0;"/>
								<label class="selectCSV"  for="boton1" 
								  style="color: white;
								   margin-left: 41%;
								  padding: .5em 1em;
								  background-color: 5CB85C;
								  display: block;
								  width: 9em;
								  text-align: center;
								  margin-top: 1%;" >Cargar</label>
								  <br>
							</c:if>		  
							<div style="overflow:scroll; height:200px;">
								<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 							<caption>Listado de Archivos</caption>
		 							<c:if test="${empty requestScope.listaArchivos1}">
										<tr>
											<th class="Fila1" colspan='6'>No hay Archivos Cargados</th>
										</tr>
									</c:if>
									<c:if test="${!empty requestScope.listaArchivos1}">
										<tr>
											<td class="EncabezadoColumna" align="center" >Evidencias</td>
											<td class="EncabezadoColumna" align="center" >Responsable</td>
											<td class="EncabezadoColumna" align="center" style="width: 20%;" >Fecha</td>
											<td class="EncabezadoColumna" align="center" style="width: 20%;" >Accion</td>
										</tr>
										
										<c:forEach begin="0" items="${requestScope.listaArchivos1}" var="lista" varStatus="st">
											<tr>
												<td class='Fila' align="center"><c:out value="${lista.nombreArchivo}"/></td>
												<td class='Fila' align="center"><c:out value="${lista.usuarioResponsable}"/></td>
												<td class='Fila' align="center"><c:out value="${lista.fechaCreacion}"/></td>
												<td class='Fila' align="center">
												<!-- <input name="descargar" type="button" value="Descargar" onClick="peticion2(1)" class="boton" style="color: white;
													  padding: .5em 1em;
													  background-color: 5CB85C;
													  display: block;
													  width: 9em;
													  text-align: center;" > -->
												          
												<!--  <a target="_blank"  href='http://172.16.70.11:7804/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&idUsuario=<c:out value="${sessionScope.login.usuarioId}"/>' > -->
												<!--  <a target="_blank"  href='http://172.16.70.11:7804/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst' >  -->
												<!-- <a target="_blank"  href='http://172.16.70.20:1934/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst' > -->   
												<!-- <a target="_blank"  href='https://reportesapoyo.educacionbogota.edu.co/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst' >  -->
													 <a target="_blank"  href='<%=rutaArchivo %>ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst&vigencia=<c:out value="${sessionScope.filtroSeguimientoVO.filVigencia}"/>' >
														<input name="descargar" type="button" value="Descargar" onClick="subioArchivo()" class="boton" 
														style="color: white;
														padding: .5em 1em;
														background-color: 5CB85C;
														display: block;
														width: 9em;
														text-align: center;" >
													</a>
													<c:if test="${sessionScope.NivelPermiso==2}">
													<!-- <a   href='http://172.16.70.11:7804/ApoyoEscolarBE/api/gestionPoa/eliminarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&idUsuario=<c:out value="${sessionScope.login.usuarioId}"/>' >  -->
														<input name="eliminar" type="button" value="Eliminar" 
															  onClick="eliminarArchivo( <c:url value="${lista.idEvidenciaSeguimiento}"></c:url> , <c:out value="${sessionScope.login.usuarioId}"/>,
															  <c:out value="${sessionScope.seguimientoVO.plaVigencia}"/>, 
														 		3, <c:out value="${sessionScope.seguimientoVO.plaCodigo}"/>,
														 		<c:out value="${sessionScope.login.instId}"/>,
														 		'<c:out value="${sessionScope.seguimientoVO.plaDisabled1}"/>' )" class="boton" style="color: white;
															  padding: .5em 1em;
															  background-color: D9534F;
															  display: block;
															  width: 9em;
															  text-align: center;
															  margin-top: 2%;
							    							  margin-bottom: 1%;">
							    				   <!-- </a> -->
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</c:if>
								</table>
							</div>		  
											
							
							
							<table align="center" width="100%" border="0" cellpadding="1" cellspacing="0">
								<tr>
									<th colspan="2" class="Fila0">Segundo Trimestre</th>
								</tr>
								<tr>
									<td	title='<c:out value="${sessionScope.filtroSeguimientoVO.lblVerificacion2}"/>'>Evidencias de logro:
									</td>
									<td>
										<textarea name="plaVerificacion2" cols="140" rows="5" <c:out value="${sessionScope.seguimientoVO.plaDisabled2}"/> onKeyDown="textCounter(this,500,500);" onKeyUp="textCounter(this,500,500);"><c:out value="${sessionScope.seguimientoVO.plaVerificacion2}" /></textarea>
									</td>
								</tr>
								<tr>
									<td	title='<c:out value="${sessionScope.filtroSeguimientoVO.lblLogros2}"/>'>Logros / Dificultades y Medidas Correctivas:
									</td>
									<td>
										<textarea name="plaLogros2" cols="140" rows="5" <c:out value="${sessionScope.seguimientoVO.plaDisabled2}"/> onKeyDown="textCounter(this,500,500);" onKeyUp="textCounter(this,500,500);"><c:out value="${sessionScope.seguimientoVO.plaLogros2}" /></textarea>
									</td>
								</tr>
							</table>
							
				<c:if test="${sessionScope.NivelPermiso==2}">
				<input type="file" name="file" id="boton2" onclick="mensajeUnidadMedida();" 
				 		onchange="peticion(2, <c:out value="${sessionScope.seguimientoVO.plaVigencia}"/>, 
				 		3, <c:out value="${sessionScope.seguimientoVO.plaCodigo}"/>,
				 		<c:out value="${sessionScope.login.instId}"/>, <c:out value="${sessionScope.login.usuarioId}"/>,
				 		'<c:out value="${sessionScope.login.nomPerfil}"/>', '<c:out value="${sessionScope.seguimientoVO.plaDisabled2}"/>');"
						style="width: 0.1px;
						  height: 0.1px;
						  opacity: 0;"/>
				<label class="selectCSV"  for="boton2" 
				  style="color: white;
				   margin-left: 41%;
				  padding: .5em 1em;
				  background-color: 5CB85C;
				  display: block;
				  width: 9em;
				  text-align: center;
				  margin-top: 1%;" >Cargar</label>
				  <br>
			</c:if>		  
	<div style="overflow:scroll;
    			height:200px;">
			<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Listado de Archivos</caption>
		 	<c:if test="${empty requestScope.listaArchivos2}">
				<tr>
					<th class="Fila1" colspan='6'>No hay Archivos Cargados</th>
				</tr>
			</c:if>
			<c:if test="${!empty requestScope.listaArchivos2}">
				<tr>
						
					<td class="EncabezadoColumna" align="center" >Evidencias</td>
					<td class="EncabezadoColumna" align="center" >Responsable</td>
					<td class="EncabezadoColumna" align="center" style="width: 20%;" >Fecha</td>
					<td class="EncabezadoColumna" align="center" style="width: 20%;" >Accion</td>
				</tr>
				
				<c:forEach begin="0" items="${requestScope.listaArchivos2}" var="lista" varStatus="st">
					<tr>
										
						<td class='Fila' align="center"><c:out value="${lista.nombreArchivo}"/></td>
						<td class='Fila' align="center"><c:out value="${lista.usuarioResponsable}"/></td>
						<td class='Fila' align="center"><c:out value="${lista.fechaCreacion}"/></td>
						<td class='Fila' align="center">
						<!-- <input name="descargar" type="button" value="Descargar" onClick="peticion2(1)" class="boton" style="color: white;
							  padding: .5em 1em;
							  background-color: 5CB85C;
							  display: block;
							  width: 9em;
							  text-align: center;" > -->
						          
						<!--  <a target="_blank"  href='http://172.16.70.11:7804/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&idUsuario=<c:out value="${sessionScope.login.usuarioId}"/>' >  -->
						<!--  <a target="_blank"  href='http://172.16.70.11:7804/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst' >   -->
						<!--  <a target="_blank"  href='http://172.16.70.20:1934/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst' > -->   
						<!--  <a target="_blank"  href='https://reportesapoyo.educacionbogota.edu.co/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst' >  -->
						   <a target="_blank"  href='<%=rutaArchivo %>ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst&vigencia=<c:out value="${sessionScope.filtroSeguimientoVO.filVigencia}"/>' >
							  <input name="descargar" type="button" value="Descargar"  class="boton" 
							  style="color: white;
							  padding: .5em 1em;
							  background-color: 5CB85C;
							  display: block;
							  width: 9em;
							  text-align: center;" >
						</a>
						<c:if test="${sessionScope.NivelPermiso==2}">
						<!-- <a   href='http://172.16.70.11:7804/ApoyoEscolarBE/api/gestionPoa/eliminarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&idUsuario=<c:out value="${sessionScope.login.usuarioId}"/>' >  -->
							<input name="eliminar" type="button" value="Eliminar" 
								  onClick="eliminarArchivo( <c:url value="${lista.idEvidenciaSeguimiento}"></c:url> , <c:out value="${sessionScope.login.usuarioId}"/>,
								  <c:out value="${sessionScope.seguimientoVO.plaVigencia}"/>, 
							 		3, <c:out value="${sessionScope.seguimientoVO.plaCodigo}"/>,
							 		<c:out value="${sessionScope.login.instId}"/>,
							 		'<c:out value="${sessionScope.seguimientoVO.plaDisabled2}"/>' )" class="boton" style="color: white;
								  padding: .5em 1em;
								  background-color: D9534F;
								  display: block;
								  width: 9em;
								  text-align: center;
								  margin-top: 2%;
    							  margin-bottom: 1%;">
    				   <!-- </a> -->
						</c:if>
						</td>
						
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</div>		  
							
							<table align="center" width="100%" border="0" cellpadding="1" cellspacing="0">
								<tr>
									<th colspan="2" class="Fila0">Tercer Trimestre</th>
								</tr>
								<tr>
									<td title='<c:out value="${sessionScope.filtroSeguimientoVO.lblVerificacion3}"/>'>Evidencias de logro:
									</td>
									<td>
										<textarea name="plaVerificacion3" cols="140" rows="5" <c:out value="${sessionScope.seguimientoVO.plaDisabled3}"/> onKeyDown="textCounter(this,500,500);"onKeyUp="textCounter(this,500,500);"><c:out value="${sessionScope.seguimientoVO.plaVerificacion3}" /></textarea>
									</td>
								</tr>
								<tr>
									<td	title='<c:out value="${sessionScope.filtroSeguimientoVO.lblLogros3}"/>'>Logros / Dificultades y Medidas Correctivas:
									</td>
									<td>
										<textarea name="plaLogros3" cols="140" rows="5" <c:out value="${sessionScope.seguimientoVO.plaDisabled3}"/> onKeyDown="textCounter(this,500,500);"onKeyUp="textCounter(this,500,500);"><c:out value="${sessionScope.seguimientoVO.plaLogros3}" /></textarea>
									</td>
								</tr>
							</table>
							
													
				<c:if test="${sessionScope.NivelPermiso==2}">
				<input type="file" name="file" id="boton3" onclick="mensajeUnidadMedida();" 
				 		onchange="peticion(3, <c:out value="${sessionScope.seguimientoVO.plaVigencia}"/>, 
				 		3, <c:out value="${sessionScope.seguimientoVO.plaCodigo}"/>,
				 		<c:out value="${sessionScope.login.instId}"/>, <c:out value="${sessionScope.login.usuarioId}"/>,
				 		'<c:out value="${sessionScope.login.nomPerfil}"/>', '<c:out value="${sessionScope.seguimientoVO.plaDisabled3}"/>');"
						style="width: 0.1px;
						  height: 0.1px;
						  opacity: 0;"/>
				<label class="selectCSV"  for="boton3" 
				  style="color: white;
				   margin-left: 41%;
				  padding: .5em 1em;
				  background-color: 5CB85C;
				  display: block;
				  width: 9em;
				  text-align: center;
				  margin-top: 1%;" >Cargar</label>
				  <br>
			</c:if>		  
	<div style="overflow:scroll;
    			height:200px;">
			<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Listado de Archivos</caption>
		 	<c:if test="${empty requestScope.listaArchivos3}">
				<tr>
					<th class="Fila1" colspan='6'>No hay Archivos Cargados</th>
				</tr>
			</c:if>
			<c:if test="${!empty requestScope.listaArchivos3}">
				<tr>
						
					<td class="EncabezadoColumna" align="center" >Evidencias</td>
					<td class="EncabezadoColumna" align="center" >Responsable</td>
					<td class="EncabezadoColumna" align="center" style="width: 20%;" >Fecha</td>
					<td class="EncabezadoColumna" align="center" style="width: 20%;" >Accion</td>
				</tr>
				
				<c:forEach begin="0" items="${requestScope.listaArchivos3}" var="lista" varStatus="st">
					<tr>
										
						<td class='Fila' align="center"><c:out value="${lista.nombreArchivo}"/></td>
						<td class='Fila' align="center"><c:out value="${lista.usuarioResponsable}"/></td>
						<td class='Fila' align="center"><c:out value="${lista.fechaCreacion}"/></td>
						<td class='Fila' align="center">
						<!-- <input name="descargar" type="button" value="Descargar" onClick="peticion2(1)" class="boton" style="color: white;
							  padding: .5em 1em;
							  background-color: 5CB85C;
							  display: block;
							  width: 9em;
							  text-align: center;" > -->
						          
						<!--  <a target="_blank"  href='http://172.16.70.11:7804/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&idUsuario=<c:out value="${sessionScope.login.usuarioId}"/>' >  -->
						<!--  <a target="_blank"  href='http://172.16.70.11:7804/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst' >   -->
						<!--  <a target="_blank"  href='http://172.16.70.20:1934/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst' > -->   
						<!--  <a target="_blank"  href='https://reportesapoyo.educacionbogota.edu.co/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst'>  -->
						   <a target="_blank"  href='<%=rutaArchivo %>ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst&vigencia=<c:out value="${sessionScope.filtroSeguimientoVO.filVigencia}"/>'>
							  <input name="descargar" type="button" value="Descargar"  class="boton" 
							  style="color: white;
							  padding: .5em 1em;
							  background-color: 5CB85C;
							  display: block;
							  width: 9em;
							  text-align: center;" >
						</a>
						<c:if test="${sessionScope.NivelPermiso==2}">
						<!-- <a   href='http://172.16.70.11:7804/ApoyoEscolarBE/api/gestionPoa/eliminarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&idUsuario=<c:out value="${sessionScope.login.usuarioId}"/>' >  -->
							<input name="eliminar" type="button" value="Eliminar" 
								  onClick="eliminarArchivo( <c:url value="${lista.idEvidenciaSeguimiento}"></c:url> , <c:out value="${sessionScope.login.usuarioId}"/>,
								  <c:out value="${sessionScope.seguimientoVO.plaVigencia}"/>, 
							 		3, <c:out value="${sessionScope.seguimientoVO.plaCodigo}"/>,
							 		<c:out value="${sessionScope.login.instId}"/>,
							 		'<c:out value="${sessionScope.seguimientoVO.plaDisabled3}"/>' )" class="boton" style="color: white;
								  padding: .5em 1em;
								  background-color: D9534F;
								  display: block;
								  width: 9em;
								  text-align: center;
								  margin-top: 2%;
    							  margin-bottom: 1%;">
    				   <!-- </a> -->
						</c:if>
						</td>
						
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</div>	
							
							
							<table align="center" width="100%" border="0" cellpadding="1" cellspacing="0">
								<tr>
									<th colspan="2" class="Fila0">Cuarto Trimestre</th>
								</tr>
								<tr>
									<td	title='<c:out value="${sessionScope.filtroSeguimientoVO.lblVerificacion4}"/>'>Evidencias de logro:
									</td>
									<td>
										<textarea name="plaVerificacion4" cols="140" rows="5" <c:out value="${sessionScope.seguimientoVO.plaDisabled4}"/> onKeyDown="textCounter(this,500,500);"onKeyUp="textCounter(this,500,500);"><c:out value="${sessionScope.seguimientoVO.plaVerificacion4}" /></textarea>
									</td>
								</tr>
								<tr>
									<td	title='<c:out value="${sessionScope.filtroSeguimientoVO.lblLogros4}"/>'>Logros / Dificultades y Medidas Correctivas:
									</td>
									<td>
										<textarea name="plaLogros4" cols="140" rows="5" <c:out value="${sessionScope.seguimientoVO.plaDisabled4}"/> onKeyDown="textCounter(this,500,500);" onKeyUp="textCounter(this,500,500);"><c:out value="${sessionScope.seguimientoVO.plaLogros4}" /></textarea>
									</td>
								</tr>
							</table>
							
																			
				<c:if test="${sessionScope.NivelPermiso==2}">
				<input type="file" name="file" id="boton4" onclick="mensajeUnidadMedida();" 
				 		onchange="peticion(4, <c:out value="${sessionScope.seguimientoVO.plaVigencia}"/>, 
				 		3, <c:out value="${sessionScope.seguimientoVO.plaCodigo}"/>,
				 		<c:out value="${sessionScope.login.instId}"/>, <c:out value="${sessionScope.login.usuarioId}"/>,
				 		'<c:out value="${sessionScope.login.nomPerfil}"/>', '<c:out value="${sessionScope.seguimientoVO.plaDisabled4}"/>');"
						style="width: 0.1px;
						  height: 0.1px;
						  opacity: 0;"/>
				<label class="selectCSV"  for="boton4" 
				  style="color: white;
				   margin-left: 41%;
				  padding: .5em 1em;
				  background-color: 5CB85C;
				  display: block;
				  width: 9em;
				  text-align: center;
				  margin-top: 1%;" >Cargar</label>
				  <br>
			</c:if>		  
	<div style="overflow:scroll;
    			height:200px;">
			<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	<caption>Listado de Archivos</caption>
		 	<c:if test="${empty requestScope.listaArchivos4}">
				<tr>
					<th class="Fila1" colspan='6'>No hay Archivos Cargados</th>
				</tr>
			</c:if>
			<c:if test="${!empty requestScope.listaArchivos4}">
				<tr>
						
					<td class="EncabezadoColumna" align="center" >Evidencias</td>
					<td class="EncabezadoColumna" align="center" >Responsable</td>
					<td class="EncabezadoColumna" align="center" style="width: 20%;" >Fecha</td>
					<td class="EncabezadoColumna" align="center" style="width: 20%;" >Accion</td>
				</tr>
				
				<c:forEach begin="0" items="${requestScope.listaArchivos4}" var="lista" varStatus="st">
					<tr>
										
						<td class='Fila' align="center"><c:out value="${lista.nombreArchivo}"/></td>
						<td class='Fila' align="center"><c:out value="${lista.usuarioResponsable}"/></td>
						<td class='Fila' align="center"><c:out value="${lista.fechaCreacion}"/></td>
						<td class='Fila' align="center">
						<!-- <input name="descargar" type="button" value="Descargar" onClick="peticion2(1)" class="boton" style="color: white;
							  padding: .5em 1em;
							  background-color: 5CB85C;
							  display: block;
							  width: 9em;
							  text-align: center;" > -->
						          
						<!--  <a target="_blank"  href='http://172.16.70.11:7804/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&idUsuario=<c:out value="${sessionScope.login.usuarioId}"/>' >  -->
						<!--  <a target="_blank"  href='http://172.16.70.11:7804/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst' >   -->
						<!--  <a target="_blank"  href='http://172.16.70.20:1934/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst' > -->   
						<!--  <a target="_blank"  href='https://reportesapoyo.educacionbogota.edu.co/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst'>  -->
						   <a target="_blank"  href='<%=rutaArchivo %>ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst&vigencia=<c:out value="${sessionScope.filtroSeguimientoVO.filVigencia}"/>'>
							  <input name="descargar" type="button" value="Descargar"  class="boton" 
							  style="color: white;
							  padding: .5em 1em;
							  background-color: 5CB85C;
							  display: block;
							  width: 9em;
							  text-align: center;" >
						</a>
						<c:if test="${sessionScope.NivelPermiso==2}">
						<!-- <a   href='http://172.16.70.11:7804/ApoyoEscolarBE/api/gestionPoa/eliminarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&idUsuario=<c:out value="${sessionScope.login.usuarioId}"/>' >  -->
							<input name="eliminar" type="button" value="Eliminar" 
								  onClick="eliminarArchivo( <c:url value="${lista.idEvidenciaSeguimiento}"></c:url> , <c:out value="${sessionScope.login.usuarioId}"/>,
								  <c:out value="${sessionScope.seguimientoVO.plaVigencia}"/>, 
							 		3, <c:out value="${sessionScope.seguimientoVO.plaCodigo}"/>,
							 		<c:out value="${sessionScope.login.instId}"/>,
							 		'<c:out value="${sessionScope.seguimientoVO.plaDisabled4}"/>' )" class="boton" style="color: white;
								  padding: .5em 1em;
								  background-color: D9534F;
								  display: block;
								  width: 9em;
								  text-align: center;
								  margin-top: 2%;
    							  margin-bottom: 1%;">
    				   <!-- </a> -->
						</c:if>
						</td>
						
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</div>	
							
							<c:if test="${sessionScope.NivelPermiso==2 && sessionScope.filtroSeguimientoVO.filFechaHabil==true && sessionScope.seguimientoVO.plaVigencia eq sessionScope.filtroSeguimientoVO.filVigenciaPoa}">
								<input name="cmd2" type="button" value="Guardar" onClick="guardar()" class="boton">
							</c:if>
						</td>
					</tr>
				</table>
				
			</form>
			
			<script type="text/javascript">
				setVisible();
		   		Calendar.setup({ inputField : "SEGFECHAREALCUMPLIM",ifFormat : "%d/%m/%Y",button : "imgFechareal",align : "Br"});
			</script>
			
		</c:if>
		
		<div class="loading" id="loading">
			<div class="loading--inside">
		    	<span></span>
		    	<p>Cargando...</p>
		  	</div>
		</div>
		
		<style>
			.loading {width: 100%;height: 100vh;display: none;position: fixed;top: 0;left: 0;background: rgba(0, 0, 0, 0.7);}
			.loading.active {display: block;z-index: 10000;}
			.loading--inside {position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%);}
			.loading--inside span {width: 30px;height: 30px;display: block;margin: 0 auto;border: 3px solid white;border-bottom: 3px solid transparent;border-radius: 100%;animation: loading 1s linear infinite;}
			.loading--inside p {color: white;text-align: center;}
			@keyframes loading {from {transform: rotate(0deg);}to {transform: rotate(360deg);}}
		</style>
		
	</body>
	
</html>
