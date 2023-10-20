<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!--	VERSION		FECHA		AUTOR			DESCRIPCION -->
<!-- 		1.0		27/11/2019	JORGE CAMACHO	Se modificó el nombre del campo que se muestra en: Fecha prevista para terminar actividad -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="aprobacionActividadesVO" class="poa.aprobacionActividades.vo.PlaneacionVO" scope="session"/>
<jsp:useBean id="paramsVO" class="poa.aprobacionActividades.vo.ParamsVO" scope="page"/>

<html>

	<head>

		<style type="text/css">@import url(<c:url value="/etc/css/calendar-win2k-1.css"/>);</style>
		
		<script type="text/javascript" src="<c:url value="/etc/js/calendar.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/etc/js/lang/calendar-es.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/etc/js/calendar-setup.js"/>"></script>
		<script languaje="javaScript">
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
				
			function ajaxLinea() {
			
				borrar_combo(document.frmNuevo.plaLineaAccion); 
				document.frmAjax.ajax[0].value=document.frmNuevo.plaAreaGestion.value;
				document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_LINEA}"/>';
		 		document.frmAjax.target="frameAjax";
				document.frmAjax.submit();
				
			}
				
			function setUnidad() {
			
				var forma = document.frmNuevo;
				
				if(parseInt(forma.plaUnidades[forma.plaMetaAnualUnidad.selectedIndex].value) == 1) {
				
					forma.plaMetaAnualCual.disabled = false;
					
				} else {
				
					forma.plaMetaAnualCual.value="";
					forma.plaMetaAnualCual.disabled=true;
					
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
		</script>
		
	</head>
	
	<c:import url="/mensaje.jsp"/>
	
	<body onLoad="mensaje(document.getElementById('msg'));">
	
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%" valign='top'>
					<div style="width:100%;height:300px;overflow:auto;vertical-align:top;background-color:#ffffff;">
						<c:import url="/poa/aprobacionActividades/Filtro.do"/>
					</div>
				</td>
			</tr>
		</table>
		
		<table cellpadding="0" cellspacing="0" border="0" align="CENTER" width="100%" >
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
		
		<div style="width:100%;height:200px;overflow:auto;vertical-align:top;background-color:#ffffff;">
			<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
			 	<caption>POA. <c:out value="${sessionScope.filtroAprobacionActividadesVO.filNombreColegio}"/></caption>
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
			
			<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_ACTIVIDAD}"/>'>
			<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
			<input type="hidden" name="ajax">
			<input type="hidden" name="ajax">
			
		</form>
		
		<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/poa/aprobacionActividades/Save.jsp"/>'>
			
			<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_ACTIVIDAD}"/>'>
			<input type="hidden" name="cmd" value=''>
			<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
			<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
			<input type="hidden" name="plaInstitucion" value='<c:out value="${sessionScope.login.instId}"/>'>
			<input type="hidden" name="plaPonderadores" value='0'>
			
			<c:forEach begin="0" items="${requestScope.listaAreaGestion}" var="area">
				<input type="hidden" name="plaPonderadores" value='<c:out value="${area.padre}"/>'>
			</c:forEach>
			
			<input type="hidden" name="plaUnidades" value='0'>
			
			<c:forEach begin="0" items="${requestScope.listaUnidadMedida}" var="unidad">
				<input type="hidden" name="plaUnidades" value='<c:out value="${unidad.padre}"/>'>
			</c:forEach>
			
			<c:forEach begin="0" items="${requestScope.listaFuenteFinanciera}" var="fuente">
				<input type="hidden" name="plaFuentes" value='<c:out value="${fuente.padre}"/>'>
			</c:forEach>
			
			<c:if test="${sessionScope.aprobacionActividadesVO.formaEstado==1}">
			
				<table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
					<caption>Detalle de actividad</caption>
					<tr>
						<td width="15%" title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblVigencia}"/>'><span class="style2">*</span> Vigencia:</td>
						<td width="85%" colspan="3">
							<select name="plaVigencia" style="width:220px;" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/>>
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
									<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.aprobacionActividadesVO.plaVigencia}">selected</c:if>><c:out value="${vig.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
				 	</tr>
				 	<tr>
						<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblObjetivo}"/>'><span class="style2">*</span> Objetivo Estratégico:</td>
						<td colspan="3">
							<textarea name="plaObjetivo" cols="110" rows="3" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);" ><c:out value="${sessionScope.aprobacionActividadesVO.placcodobjetivoText}"/></textarea>
						</td>
				 	</tr>
				 	<tr>
						<td><span class="style2">*</span> Tipo de Actividad: </td>
						<td>
							<select name="plaTipoActividad" style="width:220px;" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> onchange="ajaxTipoActividad()">
								<option value="-99" selected>-Seleccione uno-</option>
								<option value="POA" <c:if test="${sessionScope.aprobacionActividadesVO.tipoActividad != null and 'POA' eq sessionScope.aprobacionActividadesVO.tipoActividad}">selected</c:if>>POA</option>
								<option value="PIMA - PIGA" <c:if test="${sessionScope.aprobacionActividadesVO.tipoActividad != null and 'PIMA - PIGA' eq sessionScope.aprobacionActividadesVO.tipoActividad}">selected</c:if>>PIMA - PIGA</option>
							</select>
						</td>
					</tr>
					<tr id="filaAccionMejoramiento"	style="display: 
						<c:if test="${sessionScope.aprobacionActividadesVO.tipoActividad  != null and 'PIMA - PIGA' eq sessionScope.aprobacionActividadesVO.tipoActividad}">table-row;</c:if>
						<c:if test="${sessionScope.aprobacionActividadesVO.tipoActividad  != null and 'PIMA - PIGA' ne sessionScope.aprobacionActividadesVO.tipoActividad}">none;</c:if>
						<c:if test="${sessionScope.aprobacionActividadesVO.tipoActividad  == null }">none;</c:if>">
							<td><span class="style2">*</span> Acción de Mejoramiento / Programa Ambiental:</td>
							<td>
								<select name="plaAccionMejoramiento" style="width:220px;" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> onchange="ajaxAccionMejoramiento()">
									<option value="-99" selected>-Seleccione uno-</option>
									<c:forEach begin="0" items="${requestScope.listaAccionMejoramiento}"	var="accionMejoramiento">
										<option value="<c:out value="${accionMejoramiento[1]}"/>" <c:if test="${accionMejoramiento[1]==sessionScope.aprobacionActividadesVO.accionMejoramiento}">selected</c:if>>
											<c:out value="${accionMejoramiento[0]}" />
										</option>
									</c:forEach>
								</select>
							</td>
					</tr>
					<tr id="filaAccionMejoramientoOtras" style="display: <c:if test="${sessionScope.aprobacionActividadesVO.accionMejoramiento=='otras'}">table-row;</c:if><c:if test="${sessionScope.aprobacionActividadesVO.accionMejoramiento!='otras'}">none;</c:if>">
						<td><span class="style2">*</span>¿ Cual Acción de Mejoramiento / Programa Ambiental ?</td>
						<td colspan="3">
							<textarea name="plaAccionMejoramientoTxt" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> maxlength="100" cols="110" rows="3" onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);"><c:out value="${sessionScope.aprobacionActividadesVO.accionMejoramiento}" /></textarea>
						</td>
					</tr>
					<tr>
						<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblActividad}"/>'><span class="style2">*</span> Actividad / Tarea:</td>
						<td colspan="3">
							<textarea name="plaActividad" cols="110" rows="3" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);" ><c:out value="${sessionScope.aprobacionActividadesVO.plaActividad}"/></textarea>
						</td>
				 	</tr>	
				 	<tr>
						<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblAreaGestion}"/>'><span class="style2">*</span> Área de Gestión:</td>
						<td>
							<select name="plaAreaGestion" style="width:220px;" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> onchange="ajaxLinea()">
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaAreaGestion}" var="area">
									<option value="<c:out value="${area.codigo}"/>" <c:if test="${area.codigo==sessionScope.aprobacionActividadesVO.plaAreaGestion}">selected</c:if>><c:out value="${area.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
						<td align="right" title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblLineaAccion}"/>'><span class="style2">*</span> Línea de Acción / Proceso / Proyecto:</td>
						<td>
							<select name="plaLineaAccion" style="width:280px;" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/>>
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaLineaAccion}" var="linea">
									<option value="<c:out value="${linea.codigo}"/>" <c:if test="${linea.codigo==sessionScope.aprobacionActividadesVO.plaLineaAccion}">selected</c:if>><c:out value="${linea.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
				 	</tr>
					<tr>
						<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblPonderador}"/>'><span class="style2">*</span> Ponderador:</td>
						<td>
							<input type="text" name="plaPonderador" maxlength="5" size="3" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.aprobacionActividadesVO.plaPonderador}"/>'></input>%
						</td>
						<td align="right" title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblTipoMeta}"/>'><span class="style2">*</span> Tipo de Meta:</td>
						<td>
							<select name="plaTipoMeta" style="width:280px;" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/>>
								<option value="-99" selected>-Seleccione uno-</option>
								<c:forEach begin="0" items="${requestScope.listaTipoMeta}" var="tipoMeta">
									<option value="<c:out value="${tipoMeta.codigo}"/>" <c:if test="${tipoMeta.codigo==sessionScope.aprobacionActividadesVO.plaTipoMeta}">selected</c:if>><c:out value="${tipoMeta.nombre}"/></option>
								</c:forEach>
							</select>
						</td>
				 	</tr>	
				</table>
					
				<table align="center" width="95%" border="1" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<table align="center" width="100%" border="0" cellpadding="1" cellspacing="0">
								<tr><th colspan="6" class="Fila0">Meta Anual/Producto Final</th></tr>
								<tr>
									<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblMetaAnualUnidad}"/>'><span class="style2">*</span> Unidad de Medida:</td>
									<td>
										<select name="plaMetaAnualUnidad" style="width:220px;" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> onchange="javaScript:setUnidad()">
											<option value="-99" selected>-Seleccione uno-</option>
											<c:forEach begin="0" items="${requestScope.listaUnidadMedida}" var="unidad">
												<option value="<c:out value="${unidad.codigo}"/>" <c:if test="${unidad.codigo==sessionScope.aprobacionActividadesVO.plaMetaAnualUnidad}">selected</c:if>><c:out value="${unidad.nombre}"/></option>
											</c:forEach>
										</select>
									</td>
									<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblMetaAnualCual}"/>'>¿Cuál?:</td>
									<td>
										<input type="text" name="plaMetaAnualCual" maxlength="100" size="20" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> value='<c:out value="${sessionScope.aprobacionActividadesVO.plaMetaAnualCual}"/>'></input>
									</td>
									<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblMetaAnualCantidad}"/>'><span class="style2">*</span> Cantidad:</td>
									<td>
										<input type="text" name="plaMetaAnualCantidad" maxlength="5" size="3" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.aprobacionActividadesVO.plaMetaAnualCantidad}"/>'></input>
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
								<input type="checkbox" name="plaFuenteFinanciera" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> onclick="setFuente()" value='<c:out value="${fuente.codigo}"/>' <c:forEach begin="0" items="${sessionScope.aprobacionActividadesVO.plaFuenteFinanciera}" var="fuenteF"><c:if test="${fuente.codigo==fuenteF}">checked</c:if></c:forEach>><c:out value="${fuente.nombre}"/><br>
							</c:forEach>
						</td>
						<td  title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblPresupuesto}"/>'>Presupuesto:</td>
						<td>
						$<input type="text" name="plaPresupuesto" maxlength="10" size="5" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.aprobacionActividadesVO.plaPresupuesto}"/>'></input>
						</td>
					</tr>						 	
				</table>
				-->
					
				<table align="center" width="95%" border="1" cellpadding="1" cellspacing="0">
					<tr>
						<td>
							<table align="center" width="100%" border="0"  cellpadding="1" cellspacing="0">
								<tr><th colspan="4" class="Fila0">Cronograma</th></tr>
								<tr>
									<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblCronograma1}"/>' align="center"><span class="style2">*</span> 1er Trimestre:</td>
									<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblCronograma2}"/>' align="center"><span class="style2">*</span> 2do Trimestre:</td>
									<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblCronograma3}"/>' align="center"><span class="style2">*</span> 3er Trimestre:</td>
									<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblCronograma4}"/>' align="center"><span class="style2">*</span> 4to Trimestre:</td>
								</tr>
								<tr>
									<td align="center"><input type="text" name="plaCronograma1" maxlength="5" size="3" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.aprobacionActividadesVO.plaCronograma1}"/>'></input></td>
									<td align="center"><input type="text" name="plaCronograma2" maxlength="5" size="3" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.aprobacionActividadesVO.plaCronograma2}"/>'></input></td>
									<td align="center"><input type="text" name="plaCronograma3" maxlength="5" size="3" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.aprobacionActividadesVO.plaCronograma3}"/>'></input></td>
									<td align="center"><input type="text" name="plaCronograma4" maxlength="5" size="3" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> onKeyPress='return acepteNumeros(event)' value='<c:out value="${sessionScope.aprobacionActividadesVO.plaCronograma4}"/>'></input></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				
				<table align="center" width="95%" border="0" cellpadding="1" cellspacing="0">	
					<tr>
						<td width="20%" title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblFecha}"/>'><span class="style2">*</span> Fecha prevista para terminar actividad:</td>
						<td>
							<input type="text" name="plaFecha" maxlength="10" size="10" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> value='<c:out value="${sessionScope.aprobacionActividadesVO.SEGFECHACUMPLIMT}"/>'></input>
							<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="imgfecha" style='cursor:pointer;<c:if test="${sessionScope.aprobacionActividadesVO.plaDesHabilitado}">display:none;</c:if>' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
						</td>
						<td title='<c:out value="${sessionScope.filtroAprobacionActividadesVO.lblResponsable}"/>'><span class="style2">*</span> Nombre del Responsable:</td>
						<td>
							<input type="text" name="plaResponsable" maxlength="250" size="30" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> value='<c:out value="${sessionScope.aprobacionActividadesVO.plaResponsable}"/>'></input>
						</td>
					</tr>
					<tr style="display:none"><td><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>	
				</table>
				
				<!-- INICIO PIMA - PIGA -->
				<table align="center" id="tablaPimaPiga" border="0" width="95%" cellpadding="1" cellspacing="0" style="display: 
					<c:if test="${sessionScope.aprobacionActividadesVO.tipoActividad != null and sessionScope.aprobacionActividadesVO.tipoActividad eq 'PIMA - PIGA'}">table;</c:if>
					<c:if test="${sessionScope.aprobacionActividadesVO.tipoActividad != null and sessionScope.aprobacionActividadesVO.tipoActividad ne 'PIMA - PIGA'}">none;</c:if>
					<c:if test="${sessionScope.aprobacionActividadesVO.tipoActividad == null}">none;</c:if>">
						<caption>PIMA - PIGA</caption>
					 	<tr>
							<td width="30%"><span class="style2">*</span> Tipo de Gasto: </td>
							<td width="25%" >
								<select name="tipoGasto" style="width: 180px;" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> onchange="ajaxTipoGasto()">
									<option value="-99" selected>-Seleccione uno-</option>
									<option value="Inversion" <c:if test="${sessionScope.aprobacionActividadesVO.tipoGasto != null and 'Inversion' eq sessionScope.aprobacionActividadesVO.tipoGasto}">selected</c:if>>Inversion</option>
									<option value="Funcionamiento" <c:if test="${sessionScope.aprobacionActividadesVO.tipoGasto != null and 'Funcionamiento' eq sessionScope.aprobacionActividadesVO.tipoGasto}">selected</c:if>>Funcionamiento</option>
								</select>
							</td>
						</tr>
						<tr>	
							<td><span class="style2">*</span> Rubro de Gasto: </td>
							<td width="25%" >
								<select name="rubroGasto" style="width: 180px;" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/>><option value="-99" selected>-Seleccione uno-</option>
									<c:forEach items="${requestScope.listaRubroGasto}" var="vig">
										<option value="<c:out value="${vig[1]}"/>" <c:if test="${sessionScope.aprobacionActividadesVO.rubroGasto eq vig[1]}">selected</c:if>><c:out value="${vig[0]}"/></option>
									</c:forEach>
								</select>
							</td>
						</tr>		
					 	<tr>
					 		<c:if test="${'Inversion' eq sessionScope.aprobacionActividadesVO.tipoGasto}">
								<td width="30%"><span class="style2">*</span> Nombre del Proyecto de Inversion: </td>
							</c:if>
							<c:if test="${'Funcionamiento' eq sessionScope.aprobacionActividadesVO.tipoGasto}">
								<td width="30%"><span class="style2">*</span> Nombre del Subnivel de Gastos Generales ó Servicios Personales: </td>
							</c:if>
							<td width="25%">
								<input name="subnivelGasto" type="text" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> value="<c:if test="${'Funcionamiento' eq sessionScope.aprobacionActividadesVO.tipoGasto}"><c:out value="${sessionScope.aprobacionActividadesVO.subnivelGasto}"/></c:if><c:if test="${'Inversion' eq sessionScope.aprobacionActividadesVO.tipoGasto}"><c:out value="${sessionScope.aprobacionActividadesVO.proyectoInversion}"/></c:if>"/>
							</td>
						</tr>
					 	<!-- FUENTE FINANCIACION -->
					 	<tr>	
							<td><span class="style2">*</span> Fuente de Financiacion: </td>
							<td width="25%" >
								<select id="fuenteFinanciacion" name="fuenteFinanciacion" style="width: 180px;" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/>> <option value="-99" selected>-Seleccione uno-</option>
									<c:forEach begin="0" items="${requestScope.listaFuenteFinanciacion}" var="fuenteFinanciacionLista">
										<option value="<c:out value="${fuenteFinanciacionLista[1]}"/>" <c:if test="${fuenteFinanciacionLista[1]==sessionScope.aprobacionActividadesVO.fuenteFinanciacion}">selected</c:if>><c:out value="${fuenteFinanciacionLista[0]}"/></option>
									</c:forEach>
								</select>
							</td>
						</tr>					 	
					 	<tr style="display: <c:if test="${sessionScope.aprobacionActividadesVO.fuenteFinanciacion=='otros recursos'}">table-row;</c:if><c:if test="${sessionScope.aprobacionActividadesVO.fuenteFinanciacion!='otros recursos'}">none;</c:if>">
							<td><span class="style2">*</span>¿ Que otro recurso ?</td>
							<td colspan="3">
								<textarea name="fuenteFinanciacionOtros" maxlength="100" cols="70" rows="3" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> onKeyDown="textCounter(this,320,320);" onKeyUp="textCounter(this,320,320);"><c:out  value="${sessionScope.aprobacionActividadesVO.fuenteFinanciacionOtros}"/></textarea>
							</td>
						</tr>
						<!-- FIN FILA FUENTE FINANCIACION -->
					 	<tr>
							<td width="30%"><span class="style2">*</span> Monto Anual: </td>
							<td width="25%">
								<input name="montoAnual" type="text" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> value="<c:out value="${sessionScope.aprobacionActividadesVO.montoAnual}"/>" />
							</td>
						</tr>
					 	<tr>
							<td width="30%"><span class="style2">*</span> Presupuesto de participación: </td>
							<td width="25%">
								<input name="presupuestoParticipativo" type="text" <c:out value="${sessionScope.aprobacionActividadesVO.plaDisabled}"/> value="<c:out value="${sessionScope.aprobacionActividadesVO.presupuestoParticipativo}"/>" />
							</td>
						</tr>	
				</table>
				<!-- FIN PIMA - PIGA -->
				
			</c:if>
			
		</form>
		
	</body>
	
	<c:if test="${sessionScope.aprobacionActividadesVO.formaEstado==1}">
		<script type="text/javascript">
	 		//   Calendar.setup({
	    	//    inputField     :    "plaFecha",
	   		//     ifFormat       :    "%d/%m/%Y",
	  		//      button         :    "imgfecha",
	  		//      align          :    "Br"
	 		//   });
		</script>
		<script type="text/javascript">
			<c:if test="${!sessionScope.aprobacionActividadesVO.plaDesHabilitado}">setUnidad();</c:if>
		</script>
	</c:if>
	
</html>
