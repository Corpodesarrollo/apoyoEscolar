<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "java.util.ResourceBundle" %>
<% ResourceBundle resource = ResourceBundle.getBundle("path");
   String rutaArchivo=resource.getString("ruta.archivos.seguimiento");
   %>

<jsp:useBean id="seguimientoSEDVO" class="poa.seguimientoSED.vo.SeguimientoVO" scope="session"/>
<jsp:useBean id="paramsVO" class="poa.seguimientoSED.vo.ParamsVO" scope="page"/>

<html>

	<head>

		<script languaje="javaScript">
			var nav4=window.Event ? true : false;

			function acepteNumeros(eve) {
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57) );
			}
				
			function borrar_combo(combo) {
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("-Seleccione una-","-99");
			}
				
			function ajaxLinea() {
				borrar_combo(document.frmNuevo.plaLineaAccion); 
				document.frmAjax.ajax[0].value=document.frmNuevo.plaAreaGestion.value;
				document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_LINEA}"/>';
		 		document.frmAjax.target="frameAjax";
				document.frmAjax.submit();
			}
			
			function verObsEvaluacion(textoObservacion, trimestre) {
			
				alert("Observación " + trimestre + ": " + textoObservacion );
			}
			
		</script>
		
	</head>
	
	<c:import url="/mensaje.jsp"/>
	
	<body onLoad="mensaje(document.getElementById('msg'));">
	
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%" valign='top'>
					<div style="width:100%;height:250px;overflow:auto;vertical-align:top;background-color:#ffffff;">
						<c:import url="/poa/seguimientoSED/Filtro.do"/>
					</div>
				</td>
			</tr>
		</table>
		<br>
		
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<img src='<c:url value="/etc/img/tabs/actividades_1.gif"/>' alt="Actividades" height="26" border="0">
				</td>
			</tr>
		</table>
		
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
		 	
		 	<caption>POA. <c:out value="${sessionScope.filtroSeguimientoSEDVO.filNombreColegio}"/> 
		 		<c:if test="${!empty sessionScope.seguimientoSEDVO.califTotalActividad}">- CALIFICACIÓN DEPENDENCIA:
					<c:out value="${sessionScope.seguimientoSEDVO.califTotalActividad}"/>
				</c:if>
				</caption>
		 	<c:if test="${empty requestScope.listaActividades}">
				<tr>
					<th class="Fila1" colspan='6'>No hay actividades</th>
				</tr>
			</c:if>
			<c:if test="${!empty requestScope.listaActividades}">
				<tr>
					<th width='20' class="EncabezadoColumna" rowspan="2" colspan="2">&nbsp;</th>
					<td class="EncabezadoColumna" align="center" rowspan="2" width="51%">Objetivo Estratégico</td>
					<td class="EncabezadoColumna" align="center" rowspan="2" width="20%">Actividad / Tarea</td>
					<td class="EncabezadoColumna" align="center" colspan="4" width="24%">Seguimiento</td>
					<td class="EncabezadoColumna" align="center" rowspan="2">Calificación</td>
				</tr>
				<tr>
					<td class="EncabezadoColumna" align="center">1er Periodo</td>
					<td class="EncabezadoColumna" align="center">2do Periodo</td>
					<td class="EncabezadoColumna" align="center">3er Periodo</td>
					<td class="EncabezadoColumna" align="center">4to Periodo</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaActividades}" var="lista" varStatus="st">
					<tr>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaConsecutivo}"/></td>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${lista.plaVigencia}"/>,<c:out value="${lista.plaInstitucion}"/>,<c:out value="${lista.plaCodigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.placcodobjetivoText}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaActividad}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaSeguimiento1}"/>&nbsp;</td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaSeguimiento2}"/>&nbsp;</td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaSeguimiento3}"/>&nbsp;</td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.plaSeguimiento4}"/>&nbsp;</td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.califActividad}"/></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
		
		<form method="post" name="frmAjax" action='<c:url value="/poa/seguimientoSED/Ajax.do"/>'>
			<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_ACTIVIDAD}"/>'>
			<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
		</form>
		
		<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/poa/seguimientoSED/Save.jsp"/>'>
			
			<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_ACTIVIDAD}"/>'>
			<input type="hidden" name="cmd" value=''>
			<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
			<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
			<input type="hidden" name="plaInstitucion" value='<c:out value="${sessionScope.login.instId}"/>'>
			
			<c:if test="${sessionScope.seguimientoSEDVO.formaEstado==1}">
			
	  			<table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		  			<caption>Detalle de seguimiento</caption>
					<tr>
						<td width="20%" title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblVigencia}"/>'>Vigencia:</td>
						<td width="80%" colspan="3">
							<select name="plaVigencia" style="width:220px;" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/>>
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
									<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.seguimientoSEDVO.plaVigencia}">selected</c:if>><c:out value="${vig.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
		 			</tr>
					<tr>
						<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblObjetivo}"/>'>Objetivo Estratégico:</td>
						<td colspan="3">
							<textarea name="plaObjetivo" cols="110" rows="3" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/> onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);" ><c:out value="${sessionScope.seguimientoSEDVO.placcodobjetivoText}"/></textarea>
						</td>
		 			</tr>
		 			<tr>
						<td><span class="style2">*</span> Tipo de Actividad: </td>
						<td colspan="3">
							<select name="plaTipoActividad" style="width:220px;" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/> onchange="ajaxTipoActividad()">
								<option value="-99" selected>-Seleccione uno-</option>
								<option value="POA" <c:if test="${sessionScope.seguimientoSEDVO.tipoActividad != null and 'POA' eq sessionScope.seguimientoSEDVO.tipoActividad}">selected</c:if>>POA</option>
								<option value="PIMA - PIGA" <c:if test="${sessionScope.seguimientoSEDVO.tipoActividad != null and 'PIMA - PIGA' eq sessionScope.seguimientoSEDVO.tipoActividad}">selected</c:if>>PIMA - PIGA</option>
							</select>
						</td>
					</tr>
					<tr id="filaAccionMejoramiento" style="display: 
						<c:if test="${sessionScope.seguimientoSEDVO.tipoActividad  != null and 'PIMA - PIGA' eq sessionScope.seguimientoSEDVO.tipoActividad}">table-row;</c:if>
						<c:if test="${sessionScope.seguimientoSEDVO.tipoActividad  != null and 'PIMA - PIGA' ne sessionScope.seguimientoSEDVO.tipoActividad}">none;</c:if>
						<c:if test="${sessionScope.seguimientoSEDVO.tipoActividad  == null }">none;</c:if>">
						<td><span class="style2">*</span> Acción de Mejoramiento / Programa Ambiental:</td>
						<td colspan="3">
							<select name="plaAccionMejoramiento" style="width: 180px;" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/> onchange="ajaxAccionMejoramiento()">
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaAccionMejoramiento}" var="accionMejoramiento">
									<option value="<c:out value="${accionMejoramiento[1]}"/>" <c:if test="${accionMejoramiento[1]==sessionScope.seguimientoSEDVO.accionMejoramiento}">selected</c:if>><c:out value="${accionMejoramiento[0]}"/></option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr id="filaAccionMejoramientoOtras" style="display: <c:if test="${sessionScope.seguimientoSEDVO.accionMejoramiento=='otras'}">table-row;</c:if><c:if test="${sessionScope.seguimientoSEDVO.accionMejoramiento!='otras'}">none;</c:if>">
						<td><span class="style2">*</span>¿ Cual Acción de Mejoramiento / Programa Ambiental ?</td>
						<td colspan="3">
							<textarea name="plaAccionMejoramientoTxt" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/> maxlength="100" cols="110" rows="3" onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);"><c:out value="${sessionScope.seguimientoSEDVO.accionMejoramientoOtras}"/></textarea>
						</td>
					</tr>
					<tr>
						<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblActividad}"/>'>Actividad / Tarea:</td>
						<td colspan="3">
							<textarea name="plaActividad" cols="110" rows="3" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/> onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);" ><c:out value="${sessionScope.seguimientoSEDVO.plaActividad}"/></textarea>
						</td>
				 	</tr>	
				 	<tr>
						<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblAreaGestion}"/>'>Área de Gestión:</td>
						<td>
							<select name="plaAreaGestion" style="width:220px;" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/> onchange="ajaxLinea()">
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaAreaGestion}" var="area">
									<option value="<c:out value="${area.codigo}"/>" <c:if test="${area.codigo==sessionScope.seguimientoSEDVO.plaAreaGestion}">selected</c:if>><c:out value="${area.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
						<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblLineaAccion}"/>' align="right">Línea de Acción / Proceso / Proyecto:</td>
						<td>
							<select name="plaLineaAccion" style="width:280px;" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/>>
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaLineaAccion}" var="linea">
									<option value="<c:out value="${linea.codigo}"/>" <c:if test="${linea.codigo==sessionScope.seguimientoSEDVO.plaLineaAccion}">selected</c:if>><c:out value="${linea.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
				 	</tr>
				 	<tr>
						<td	title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblTipoMeta}"/>'>
							<span class="style2">*</span> Ponderador:
						</td>
						<td align="left">
							<input type="text" name="plaPonderador" maxlength="5" size="3" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/> readonly value='<c:out value="${sessionScope.seguimientoSEDVO.plaPonderador}"/>'></input>%
						</td>
						<td align="right" title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblTipoMeta}"/>'><span class="style2">*</span> Tipo de Meta:</td>
						<td>
							<select name="plaTipoMeta" style="width:280px;" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/>>
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaTipoMeta}" var="linea">
									<option value="<c:out value="${linea.codigo}"/>" <c:if test="${linea.codigo==sessionScope.seguimientoSEDVO.plaTipoMeta}">selected</c:if>><c:out value="${linea.nombre}"/></option>
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
									<th colspan="6" class="Fila0">Meta Anual / Producto Final</th>
								</tr>
								<tr>
									<td width="20%" align="left"><span class="style2">*</span> Unidad de Medida:</td>
									<td width="20%" align="left">
										<select name="plaUnidadMedida" style="width:220px;" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/>>
											<option value="-99" selected>-Seleccione uno-</option>
											<c:forEach begin="0" items="${requestScope.listaUnidadMedida}" var="unidad">
												<option value="<c:out value="${unidad.codigo}"/>" <c:if test="${unidad.codigo==sessionScope.seguimientoSEDVO.plaUnidadMedida}">selected</c:if>><c:out value="${unidad.nombre}"/></option>
											</c:forEach>
										</select>
									</td>
									<td width="10%" align="right">¿Cuál?:</td>
									<td width="30%" align="left">
										<input type="text" name="placOtroCual" maxlength="100" size="40" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/> value='<c:out value="${sessionScope.seguimientoSEDVO.PLACOTROCUAL}"/>'></input>
									</td>
									<td width="10%"><span class="style2">*</span> Cantidad:</td>
									<td width="10%">
										<input type="text" name="plaCantidad" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaCantidad}"/>'></input>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>

				<table align="center" width="95%" border="0" cellpadding="1" cellspacing="0">	
					<tr>
						<td width="20%" title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblFecha}"/>'>Fecha Prevista de Entrega:</td>
						<td>
						<input type="text" name="plaFecha" maxlength="10" size="10" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/> value='<c:out value="${sessionScope.seguimientoSEDVO.plaFecha}"/>'></input>
						</td>
						<td width="20%" title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblFechaReal}"/>'>Fecha de Entrega Real:</td>
						<td>
						<input type="text" name="plaFechaReal" maxlength="10" size="10" <c:if test="${!sessionScope.filtroSeguimientoSEDVO.filFechaHabil}">disabled</c:if> value='<c:out value="${sessionScope.seguimientoSEDVO.plaFechaReal}"/>'></input>
						</td>
					</tr>
					<tr style="display:none"><td><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>	
				</table>
		
				<!-- INICIO PIMA - PIGA -->
				<table align="center" id="tablaPimaPiga" border="0" width="95%" cellpadding="1" cellspacing="0" style="display: 
					<c:if test="${sessionScope.seguimientoSEDVO.tipoActividad != null and sessionScope.seguimientoSEDVO.tipoActividad eq 'PIMA - PIGA'}">table;</c:if>
					<c:if test="${sessionScope.seguimientoSEDVO.tipoActividad != null and sessionScope.seguimientoSEDVO.tipoActividad ne 'PIMA - PIGA'}">none;</c:if>
					<c:if test="${sessionScope.seguimientoSEDVO.tipoActividad == null}">none;</c:if>">
						<caption>PIMA - PIGA</caption>
					 	<tr>
							<td width="30%"><span class="style2">*</span> Tipo de gasto: </td>
							<td width="25%" >
								<select name="tipoGasto" style="width: 180px;" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/> onchange="ajaxTipoGasto()">
									<option value="-99" selected>-Seleccione uno-</option>
									<option value="Inversion" <c:if test="${sessionScope.seguimientoSEDVO.TIPOGASTO != null and 'Inversion' eq sessionScope.seguimientoSEDVO.TIPOGASTO}">selected</c:if>>Inversion</option>
									<option value="Funcionamiento" <c:if test="${sessionScope.seguimientoSEDVO.TIPOGASTO != null and 'Funcionamiento' eq sessionScope.seguimientoSEDVO.TIPOGASTO}">selected</c:if>>Funcionamiento</option>
								</select>
							</td>
						</tr>
						<tr>	
							<td><span class="style2">*</span> Rubro de gasto: </td>
							<td width="25%" >
								<select name="rubroGasto" style="width: 180px;" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/>><option value="-99" selected>-Seleccione uno-</option>
									<c:forEach items="${requestScope.listaRubroGasto}" var="vig">
										<option value="<c:out value="${vig[1]}"/>" <c:if test="${sessionScope.seguimientoSEDVO.RUBROGASTO eq vig[1]}">selected</c:if>><c:out value="${vig[0]}"/></option>
									</c:forEach>
								</select>
							</td>
						</tr>
		
					 	<tr><c:if test="${'Inversion' eq sessionScope.seguimientoSEDVO.TIPOGASTO}">
							<td width="30%"><span class="style2">*</span> Nombre del Proyecto de Inversion: </td>
							</c:if>
							<c:if test="${'Funcionamiento' eq sessionScope.seguimientoSEDVO.TIPOGASTO}">
							<td width="30%"><span class="style2">*</span> Nombre del Subnivel de Gastos Generales ó Servicios Personales: </td>
							</c:if>
							<td width="25%">
								<input name="subnivelGasto" type="text" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/> value="<c:if test="${'Funcionamiento' eq sessionScope.seguimientoSEDVO.TIPOGASTO}"><c:out value="${sessionScope.seguimientoSEDVO.SUBNIVELGASTO}"/></c:if><c:if test="${'Inversion' eq sessionScope.seguimientoSEDVO.TIPOGASTO}"><c:out value="${sessionScope.seguimientoSEDVO.PROYECTOINVERSION}"/></c:if>"/>
							</td>
						</tr>
					 	<!-- FUENTE FINANCIACION -->
					 	<tr>	
							<td><span class="style2">*</span> Fuente de Financiacion: </td>
							<td width="25%" >
								<select id="fuenteFinanciacion" name="fuenteFinanciacion" style="width: 180px;" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/>> <option value="-99" selected>-Seleccione uno-</option>
									<c:forEach begin="0" items="${requestScope.listaFuenteFinanciacion}" var="fuenteFinanciacionLista">
										<option value="<c:out value="${fuenteFinanciacionLista[1]}"/>" <c:if test="${fuenteFinanciacionLista[1]==sessionScope.seguimientoSEDVO.FUENTEFINANCIACION}">selected</c:if>><c:out value="${fuenteFinanciacionLista[0]}"/></option>
									</c:forEach>
								</select>
							</td>
						</tr>
					 	
					 	<tr style="display: <c:if test="${sessionScope.seguimientoSEDVO.FUENTEFINANCIACION=='otros recursos'}">table-row;</c:if><c:if test="${sessionScope.seguimientoSEDVO.FUENTEFINANCIACION!='otros recursos'}">none;</c:if>">
							<td><span class="style2">*</span>¿ Que otro recurso ?</td>
							<td colspan="3">
								<textarea name="fuenteFinanciacionOtros" maxlength="100" cols="70" rows="3" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/> onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);"><c:out  value="${sessionScope.seguimientoSEDVO.FUENTEFINANCIACIONOTROS}"/></textarea>
							</td>
						</tr>
						<!-- FIN FILA FUENTE FINANCIACION -->
					 	<tr>
							<td width="30%"><span class="style2">*</span> Monto Anual: </td>
							<td width="25%">
								<input name="montoAnual" type="text" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/> value="<c:out value="${sessionScope.seguimientoSEDVO.MONTOANUAL}"/>" />
							</td>
						</tr>
					 	<tr>
							<td width="30%"><span class="style2">*</span> Presupuesto de participación: </td>
							<td width="25%">
								<input name="presupuestoParticipativo" type="text" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/> value="<c:out value="${sessionScope.seguimientoSEDVO.PPTOPARTICIPATIVO}"/>" />
							</td>
						</tr>	
				</table>
				<!-- FIN PIMA - PIGA -->		
		
				<table align="center" width="95%" border="1" cellpadding="1" cellspacing="0">
					<tr>
						<td>
							<table align="center" width="100%" border="0"  cellpadding="1" cellspacing="0">
								<tr><th colspan="4" class="Fila0">Cronograma</th></tr>
								<tr>
									<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblCronograma1}"/>' align="center">1er Periodo:</td>
									<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblCronograma2}"/>' align="center">2do Periodo:</td>
									<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblCronograma3}"/>' align="center">3er Periodo:</td>
									<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblCronograma4}"/>' align="center">4to Periodo:</td>
								</tr>
								<tr>
									<td align="center"><input type="text" name="plaCronograma1" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaCronograma1}"/>'></input></td>
									<td align="center"><input type="text" name="plaCronograma2" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaCronograma2}"/>'></input></td>
									<td align="center"><input type="text" name="plaCronograma3" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaCronograma3}"/>'></input></td>
									<td align="center"><input type="text" name="plaCronograma4" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaCronograma4}"/>'></input></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>

				<table align="center" width="95%" border="1" cellpadding="1" cellspacing="0">
					<tr>
						<td>
							<table align="center" width="100%" border="0"  cellpadding="1" cellspacing="0">
								<tr><th colspan="4" class="Fila0">Seguimiento</th></tr>
								<tr>
									<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblSeguimiento1}"/>' align="center">1er Periodo:</td>
									<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblSeguimiento2}"/>' align="center">2do Periodo:</td>
									<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblSeguimiento3}"/>' align="center">3er Periodo:</td>
									<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblSeguimiento4}"/>' align="center">4to Periodo:</td>
								</tr>
								<tr>
<%-- 									<td align="center"><input type="text" name="plaSeguimiento1" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled1}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaSeguimiento1}"/>'></input></td> --%>
<%-- 									<td align="center"><input type="text" name="plaSeguimiento2" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled2}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaSeguimiento2}"/>'></input></td> --%>
<%-- 									<td align="center"><input type="text" name="plaSeguimiento3" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled3}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaSeguimiento3}"/>'></input></td> --%>
<%-- 									<td align="center"><input type="text" name="plaSeguimiento4" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled4}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaSeguimiento4}"/>'></input></td> --%>
								
								<c:choose>
								  <c:when test="${sessionScope.seguimientoSEDVO.rojoNaranjaSeguimiento1 == 1}">
								    <td align="center"><input class="styleRedSeguimiento" type="text" name="plaSeguimiento1" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled1}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaSeguimiento1}"/>'></input></td>
								  </c:when>	
								  <c:when test="${sessionScope.seguimientoSEDVO.rojoNaranjaSeguimiento1 == 2}">
								    <td align="center"><input class="styleOrangeSeguimiento" type="text" name="plaSeguimiento1" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled1}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaSeguimiento1}"/>'></input></td>
								  </c:when>								 
								  <c:otherwise>
								   <td align="center"><input type="text" name="plaSeguimiento1" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled1}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaSeguimiento1}"/>'></input></td>
								  </c:otherwise>
								</c:choose>
								<c:choose>
								  <c:when test="${sessionScope.seguimientoSEDVO.rojoNaranjaSeguimiento2 == 1}">
								    <td align="center"><input class="styleRedSeguimiento" type="text" name="plaSeguimiento2" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled2}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaSeguimiento2}"/>'></input></td>
								  </c:when>	
								   <c:when test="${sessionScope.seguimientoSEDVO.rojoNaranjaSeguimiento2 == 2}">
								    <td align="center"><input class="styleOrangeSeguimiento" type="text" name="plaSeguimiento2" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled2}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaSeguimiento2}"/>'></input></td>
								  </c:when>							 
								  <c:otherwise>
								   <td align="center"><input type="text" name="plaSeguimiento2" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled2}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaSeguimiento2}"/>'></input></td>
								  </c:otherwise>
								</c:choose>
								<c:choose>
								  <c:when test="${sessionScope.seguimientoSEDVO.rojoNaranjaSeguimiento3 == 1}">
								  	<td align="center"><input class="styleRedSeguimiento" type="text" name="plaSeguimiento3" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled3}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaSeguimiento3}"/>'></input></td>
								  </c:when>
								   <c:when test="${sessionScope.seguimientoSEDVO.rojoNaranjaSeguimiento3 == 2}">
								  	<td align="center"><input class="styleOrangeSeguimiento" type="text" name="plaSeguimiento3" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled3}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaSeguimiento3}"/>'></input></td>
								  </c:when>								 
								  <c:otherwise>
								   <td align="center"><input type="text" name="plaSeguimiento3" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled3}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaSeguimiento3}"/>'></input></td>
								  </c:otherwise>
								</c:choose>
								<c:choose>
								  <c:when test="${sessionScope.seguimientoSEDVO.rojoNaranjaSeguimiento4 == 1}">
								    <td align="center"><input class="styleRedSeguimiento" type="text" name="plaSeguimiento4" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled4}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaSeguimiento4}"/>'></input></td>
								  </c:when>	
								  <c:when test="${sessionScope.seguimientoSEDVO.rojoNaranjaSeguimiento4 == 2}">
								    <td align="center"><input class="styleOrangeSeguimiento" type="text" name="plaSeguimiento4" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled4}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaSeguimiento4}"/>'></input></td>
								  </c:when>								 
								  <c:otherwise>
								   <td align="center"><input type="text" name="plaSeguimiento4" maxlength="7" size="4" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled4}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaSeguimiento4}"/>'></input></td>
								  </c:otherwise>
								</c:choose>
								
								</tr>
							</table>
						</td>
					</tr>
				</table>
				
				<c:if test="${sessionScope.seguimientoSEDVO.plaMostrarPorcentaje==true}">
					<table align="center" width="95%" border="1" cellpadding="1" cellspacing="0">
						<tr>
							<td>
								<table align="center" width="100%" border="0" cellpadding="1" cellspacing="0">
									<tr><th colspan="4" class="Fila0">Demanda</th></tr>
									<tr>
										<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblPorcentaje1}"/>' align="center">1er Periodo:</td>
										<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblPorcentaje2}"/>' align="center">2do Periodo:</td>
										<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblPorcentaje3}"/>' align="center">3er Periodo:</td>
										<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblPorcentaje4}"/>' align="center">4to Periodo:</td>
									</tr>
									<tr>
										<td align="center"><input type="text" name="plaPorcentaje1" maxlength="5" size="3" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled1}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaPorcentaje1}"/>'></input></td>
										<td align="center"><input type="text" name="plaPorcentaje2" maxlength="5" size="3" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled2}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaPorcentaje2}"/>'></input></td>
										<td align="center"><input type="text" name="plaPorcentaje3" maxlength="5" size="3" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled3}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaPorcentaje3}"/>'></input></td>
										<td align="center"><input type="text" name="plaPorcentaje4" maxlength="5" size="3" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled4}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.seguimientoSEDVO.plaPorcentaje4}"/>'></input></td>
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
									<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblSeguimiento1}"/>' align="center">1er Trimestre:</td>
									<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblSeguimiento2}"/>' align="center">2do Trimestre:</td>
									<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblSeguimiento3}"/>' align="center">3er Trimestre:</td>
									<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblSeguimiento4}"/>' align="center">4to Trimestre:</td>
								</tr>
								<tr>								
									<td align="center"><input type="text" name="califEvalSeg1" maxlength="7" onClick="verObsEvaluacion('<c:out value="${sessionScope.seguimientoSEDVO.obserEvalSeg1}"/>', 1)"
									title="${sessionScope.seguimientoSEDVO.evalTexto1}" 
									size="4" <c:out value="readonly"/> value='<c:out value="${sessionScope.seguimientoSEDVO.califEvalSeg1}"/>'></input></td>
									<td align="center"><input type="text" name="califEvalSeg2" maxlength="7" onClick="verObsEvaluacion('<c:out value="${sessionScope.seguimientoSEDVO.obserEvalSeg2}"/>', 2)"
									title="${sessionScope.seguimientoSEDVO.evalTexto2}"
									size="4" <c:out value="readonly"/> value='<c:out value="${sessionScope.seguimientoSEDVO.califEvalSeg2}"/>'></input></td>
									<td align="center"><input type="text" name="califEvalSeg3" maxlength="7" onClick="verObsEvaluacion('<c:out value="${sessionScope.seguimientoSEDVO.obserEvalSeg3}"/>', 3)"
									title="${sessionScope.seguimientoSEDVO.evalTexto3}"
									size="4" <c:out value="readonly"/> value='<c:out value="${sessionScope.seguimientoSEDVO.califEvalSeg3}"/>'></input></td>
									<td align="center"><input type="text" name="califEvalSeg4" maxlength="7" onClick="verObsEvaluacion('<c:out value="${sessionScope.seguimientoSEDVO.obserEvalSeg4}"/>', 4)"
									title="${sessionScope.seguimientoSEDVO.evalTexto4}"
									size="4" <c:out value="readonly"/> value='<c:out value="${sessionScope.seguimientoSEDVO.califEvalSeg4}"/>'></input></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
						
				<table align="center" width="95%" border="1" cellpadding="1" cellspacing="0">
					<tr>
						<td align="center">Responsable : <input type="text" readonly name="RESPONSABLE" maxlength="40" size="30" <c:out value="${sessionScope.seguimientoSEDVO.RESPONSABLE}"/> value='<c:out value="${sessionScope.seguimientoSEDVO.RESPONSABLE}"/>'></input></td>
					</tr>
				</table>
				
		
				<table align="center" width="95%" border="1" cellpadding="1" cellspacing="0">
					<tr><th class="Fila0">Análisis de la Actividad</th></tr>
					<tr><td>
						<table align="center" width="100%" border="0"  cellpadding="1" cellspacing="0">
							<tr><th colspan="2" class="Fila0">Primer periodo</th></tr>
							<tr>
								<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblVerificacion1}"/>'>Fuente de Verificación:</td>
								<td><textarea name="plaVerificacion1" cols="110" rows="5" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled1}"/> onKeyDown="textCounter(this,500,500);" onKeyUp="textCounter(this,500,500);" ><c:out value="${sessionScope.seguimientoSEDVO.plaVerificacion1}"/></textarea></td>
							</tr>
							<tr>
								<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblLogros1}"/>'>Logros / Dificultades y Medidas Correctivas:</td>
								<td><textarea name="plaLogros1" cols="110" rows="5" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled1}"/> onKeyDown="textCounter(this,500,500);" onKeyUp="textCounter(this,500,500);" ><c:out value="${sessionScope.seguimientoSEDVO.plaLogros1}"/></textarea></td>
							</tr>
						</table>
						
		<br>				
		<div style="overflow:scroll;
    			height:200px;">
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
												          
						<!--  <a target="_blank"  href='http://172.16.70.11:7804/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&idUsuario=<c:out value="${sessionScope.login.usuarioId}"/>' >  -->
						<!--  <a target="_blank"  href='http://172.16.70.11:7804/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst' >   -->
						<!--  <a target="_blank"  href='http://172.16.70.20:1934/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst' > -->   
						<!--  <a target="_blank"  href='https://reportesapoyo.educacionbogota.edu.co/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst' >  -->
						   <a target="_blank"  href='<%=rutaArchivo %>ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst&vigencia=<c:out value="${sessionScope.filtroSeguimientoSEDVO.filVigencia}"/>' >
							  <input name="descargar" type="button" value="Descargar" onClick="subioArchivo()" class="boton" 
							  style="color: white;
							  padding: .5em 1em;
							  background-color: 5CB85C;
							  display: block;
							  width: 9em;
							  text-align: center;" >
						</a>
						
						</td>
						
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</div>				
						
						<table align="center" width="100%" border="0"  cellpadding="1" cellspacing="0">
							<tr><th colspan="2" class="Fila0">Segundo periodo</th></tr>
							<tr>
								<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblVerificacion2}"/>'>Fuente de Verificación:</td>
								<td><textarea name="plaVerificacion2" cols="110" rows="5" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled2}"/> onKeyDown="textCounter(this,500,500);" onKeyUp="textCounter(this,500,500);" ><c:out value="${sessionScope.seguimientoSEDVO.plaVerificacion2}"/></textarea></td>
							</tr>
							<tr>
								<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblLogros2}"/>'>Logros / Dificultades y Medidas Correctivas:</td>
								<td><textarea name="plaLogros2" cols="110" rows="5" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled2}"/> onKeyDown="textCounter(this,500,500);" onKeyUp="textCounter(this,500,500);" ><c:out value="${sessionScope.seguimientoSEDVO.plaLogros2}"/></textarea></td>
							</tr>
						</table>
		<br>			
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
						
						          
						<!--  <a target="_blank"  href='http://172.16.70.11:7804/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&idUsuario=<c:out value="${sessionScope.login.usuarioId}"/>' >   -->
						<!--  <a target="_blank"  href='http://172.16.70.11:7804/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst' >   -->
						<!--  <a target="_blank"  href='http://172.16.70.20:1934/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst' > -->   
						<!--  <a target="_blank"  href='https://reportesapoyo.educacionbogota.edu.co/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst' >  -->
						   <a target="_blank"  href='<%=rutaArchivo %>ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst&vigencia=<c:out value="${sessionScope.filtroSeguimientoSEDVO.filVigencia}"/>' >
							  <input name="descargar" type="button" value="Descargar"  class="boton" 
							  style="color: white;
							  padding: .5em 1em;
							  background-color: 5CB85C;
							  display: block;
							  width: 9em;
							  text-align: center;" >
						</a>
						
						</td>
						
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</div>		  
						
						
						<table align="center" width="100%" border="0"  cellpadding="1" cellspacing="0">
							<tr><th colspan="2" class="Fila0">Tercer periodo</th></tr>
							<tr>
								<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblVerificacion3}"/>'>Fuente de Verificación:</td>
								<td><textarea name="plaVerificacion3" cols="110" rows="5" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled3}"/> onKeyDown="textCounter(this,500,500);" onKeyUp="textCounter(this,500,500);" ><c:out value="${sessionScope.seguimientoSEDVO.plaVerificacion3}"/></textarea></td>
							</tr>
							<tr>
								<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblLogros3}"/>'>Logros / Dificultades y Medidas Correctivas:</td>
								<td><textarea name="plaLogros3" cols="110" rows="5" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled3}"/> onKeyDown="textCounter(this,500,500);" onKeyUp="textCounter(this,500,500);" ><c:out value="${sessionScope.seguimientoSEDVO.plaLogros3}"/></textarea></td>
							</tr>
						</table>
		
		<br>				
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
												          
						<!--  <a target="_blank"  href='http://172.16.70.11:7804/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&idUsuario=<c:out value="${sessionScope.login.usuarioId}"/>' >   -->
						<!--  <a target="_blank"  href='http://172.16.70.11:7804/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst' >   -->
						<!--  <a target="_blank"  href='http://172.16.70.20:1934/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst' > -->   
						<!--  <a target="_blank"  href='https://reportesapoyo.educacionbogota.edu.co/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst'>  -->
						   <a target="_blank"  href='<%=rutaArchivo %>ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst&vigencia=<c:out value="${sessionScope.filtroSeguimientoSEDVO.filVigencia}"/>'>
							  <input name="descargar" type="button" value="Descargar"  class="boton" 
							  style="color: white;
							  padding: .5em 1em;
							  background-color: 5CB85C;
							  display: block;
							  width: 9em;
							  text-align: center;" >
						</a>
						
						</td>
						
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</div>				
						
						<table align="center" width="100%" border="0"  cellpadding="1" cellspacing="0">
							<tr><th colspan="2" class="Fila0">Cuarto periodo</th></tr>
							<tr>
								<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblVerificacion4}"/>'>Fuente de Verificación:</td>
								<td><textarea name="plaVerificacion4" cols="110" rows="5" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled4}"/> onKeyDown="textCounter(this,500,500);" onKeyUp="textCounter(this,500,500);" ><c:out value="${sessionScope.seguimientoSEDVO.plaVerificacion4}"/></textarea></td>
							</tr>
							<tr>
								<td title='<c:out value="${sessionScope.filtroSeguimientoSEDVO.lblLogros4}"/>'>Logros / Dificultades y Medidas Correctivas:</td>
								<td><textarea name="plaLogros4" cols="110" rows="5" <c:out value="${sessionScope.seguimientoSEDVO.plaDisabled4}"/> onKeyDown="textCounter(this,500,500);" onKeyUp="textCounter(this,500,500);" ><c:out value="${sessionScope.seguimientoSEDVO.plaLogros4}"/></textarea></td>
							</tr>
						</table>
		
		<br>				
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
												          
						<!--  <a target="_blank"  href='http://172.16.70.11:7804/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&idUsuario=<c:out value="${sessionScope.login.usuarioId}"/>' >   -->
						<!--  <a target="_blank"  href='http://172.16.70.11:7804/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst' >   -->
						<!--  <a target="_blank"  href='http://172.16.70.20:1934/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst' > -->   
						<!--  <a target="_blank"  href='https://reportesapoyo.educacionbogota.edu.co/ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst' >  -->
						   <a target="_blank"  href='<%=rutaArchivo %>ApoyoEscolarBE/api/gestionPoa/descargarArchivo?idEvidenciaSeguimiento=<c:url value="${lista.idEvidenciaSeguimiento}"></c:url>&perfilCarp=niv_inst&vigencia=<c:out value="${sessionScope.filtroSeguimientoSEDVO.filVigencia}"/>' >
							  <input name="descargar" type="button" value="Descargar"  class="boton" 
							  style="color: white;
							  padding: .5em 1em;
							  background-color: 5CB85C;
							  display: block;
							  width: 9em;
							  text-align: center;" >
						</a>
						
						</td>
						
					</tr>
				</c:forEach>
			</c:if>
		</table>
	</div>	
	
						
					</td></tr>
				</table>

			</c:if>	
	
		</form>
		
	</body>
	
</html>
