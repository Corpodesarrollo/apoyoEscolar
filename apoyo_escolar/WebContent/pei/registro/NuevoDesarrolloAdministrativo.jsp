<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="desarrolloAdministrativoPEIVO" class="pei.registro.vo.DesarrolloAdministrativoVO" scope="session"/>
<jsp:setProperty name="desarrolloAdministrativoPEIVO" property="*"/>
<jsp:useBean id="paramsVO" class="pei.registro.vo.ParamsVO" scope="page"/>
<html>
<head>
	<STYLE type='text/css'>
		.estadoPEI{
			COLOR: red;
			FONT-WEIGHT: bold;
			FONT-FAMILY: Arial, Helvetica, sans-serif;
			FONT-SIZE: 10pt;
		}
	</STYLE>
<SCRIPT language=javascript src='<c:url value="/pei/registro/NuevoDesarrolloAdministrativo.js"/>'></SCRIPT>
<script language="javaScript">var nav4=window.Event ? true : false;</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<!-- FORMULARIO DE FILTRO -->
	<c:if test="${sessionScope.filtroRegistroPEIVO.filDisabled==true}">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr><td width="100%" valign='top'>
				<div style="width:100%;overflow:auto;vertical-align:top;background-color:#ffffff;">
					<c:import url="/pei/registro/FiltroRegistro.jsp"/>
				</div>
			</td></tr>
		</table>
	</c:if>
	<!-- /FORMULARIO DE FILTRO -->
	<c:if test="${sessionScope.filtroRegistroPEIVO.filInstitucion>0}">
	<!-- TABS -->
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10">&nbsp;</td>
			<td rowspan="2" width="469">
				<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_IDENTIFICACION}"/>)"><img src='<c:url value="/etc/img/tabs/pei/identificacion_0.gif"/>' alt="Identificacion" height="26" border="0"></a>
				<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_HORIZONTE}"/>)"><img src='<c:url value="/etc/img/tabs/pei/horizonte_0.gif"/>' alt="Horizonte" height="26" border="0"></a>
				<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_DESARROLLO_CURRICULAR}"/>)"><img src='<c:url value="/etc/img/tabs/pei/desarrollo_curricular_0.gif"/>' alt="Desarrollo curricular" height="26" border="0"></a>
				<img src='<c:url value="/etc/img/tabs/pei/desarrollo_administrativo_1.gif"/>' alt="Administrativo" height="26" border="0">
				<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_DOCUMENTO}"/>)"><img src='<c:url value="/etc/img/tabs/pei/documento_0.gif"/>' alt="Documento" height="26" border="0"></a>
			</td>
		</tr>
	</table>
	<!-- /TABS -->
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='SaveDesarrolloAdministrativo.jsp'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_DESARROLLO_ADMINISTRATIVO}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="desInstitucion" value='<c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desInstitucion}"/>'>
		<input type="hidden" name="desEstado" value='<c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desEstado}"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
			<caption>Estado PEI: <span class='estadoPEI'><c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desEstadoNombre}"/></span></caption>
				<tr>
					<td align="left" width="70%">
						<c:if test="${sessionScope.NivelPermiso==2 && sessionScope.desarrolloAdministrativoPEIVO.desDisabled==false}">
    					<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
	    				</c:if>
	    				<c:if test="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled==true}"><span class="style2">No se puede editar la información</span></c:if>
			  		</td>
			 	</tr>	
		 </table>
		  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">

			<!-- Ajustes Junio 2013 -->
			
			<tr>
				<td colspan="4" class="Fila0">Comunidad Educativa</td>
			</tr>
			 
			<tr>
				<td colspan="4">El proceso del Manual de Convivencia se encuentra en la etapa de:</td>
			</tr>
			<tr>
				<c:forEach begin="0" items="${requestScope.listaManualConvivencia}" var="manualConvivencia" varStatus="st">
					<td>
					<input type="radio" name="desManualConvivencia" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='<c:out value="${manualConvivencia.codigo}"/>' <c:if test="${manualConvivencia.codigo==sessionScope.desarrolloAdministrativoPEIVO.desManualConvivencia}">checked</c:if>><c:out value="${manualConvivencia.nombre}"/></input> 
					</td>
					<c:if test="${st.count%4==0}"></tr><tr></c:if>
				</c:forEach>
			</tr>
			<tr>
				<td colspan="4">El proceso de Reglamento de Docentes se encuentra en la etapa de: </td>
			</tr>
			<tr>
				<c:forEach begin="0" items="${requestScope.listaReglamentoDocentes}" var="reglamentoDocentes" varStatus="st">
					<td>
					<input type="radio" name="desReglamentoDocentes" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='<c:out value="${reglamentoDocentes.codigo}"/>' <c:if test="${reglamentoDocentes.codigo==sessionScope.desarrolloAdministrativoPEIVO.desReglamentoDocentes}">checked</c:if>><c:out value="${reglamentoDocentes.nombre}"/></input> 
					</td>
					<c:if test="${st.count%4==0}"></tr><tr></c:if>
				</c:forEach>
			</tr>
			<tr>
				<td colspan="4">Existen evidencias del papel jugado por el gobierno escolar en la institución, en acciones de: </td>
			</tr>
			<tr>
				<c:forEach begin="0" items="${requestScope.listaGobiernoEscolar}" var="gobiernoEscolar" varStatus="st">
					<td>
					<input type="radio" name="desGobiernoEscolar" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='<c:out value="${gobiernoEscolar.codigo}"/>' <c:if test="${gobiernoEscolar.codigo==sessionScope.desarrolloAdministrativoPEIVO.desGobiernoEscolar}">checked</c:if>><c:out value="${gobiernoEscolar.nombre}"/></input> 
					</td>
					<c:if test="${st.count%4==0}"></tr><tr></c:if>
				</c:forEach>
			</tr>
			
			
			<!-- ****************** -->


			<tr>
				<td colspan="4" class="Fila0">Estrategias de Gestión</td>
			</tr>
			<tr>
				<td colspan="3">Con relación al Plan Operativo Anual, señalar:</td>
			</tr>
			<!-- <tr>
				<td>&nbsp;</td>
				<td colspan="3">¿En su construcción participan los órganos del Gobierno Escolar?</td>
			</tr>
			<tr>
				<td colspan="4">
				<table width="80%" align="center"><tr>
					<c:forEach begin="0" items="${requestScope.listaFrecuencia}" var="frecuencia" varStatus="st">
						<td>
						<c:out value="${frecuencia.nombre}"/> <input type="radio" name="desPoaConstruccion" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='<c:out value="${frecuencia.codigo}"/>' <c:if test="${frecuencia.codigo==sessionScope.desarrolloAdministrativoPEIVO.desPoaConstruccion}">checked</c:if>></input> 
						</td>
						<c:if test="${st.count%4==0}"></tr><tr></c:if>
					</c:forEach>
				</tr></table>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td colspan="3">¿En la ejecución del Plan Operativo Anual participan representantes de la comunidad escolar?</td>
			</tr>
			<tr>
				<td colspan="4">
				<table width="80%" align="center"><tr>
					<c:forEach begin="0" items="${requestScope.listaFrecuencia}" var="frecuencia" varStatus="st">
						<td>
						<c:out value="${frecuencia.nombre}"/> <input type="radio" name="desPoaComunidad" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='<c:out value="${frecuencia.codigo}"/>' <c:if test="${frecuencia.codigo==sessionScope.desarrolloAdministrativoPEIVO.desPoaComunidad}">checked</c:if>></input> 
						</td>
						<c:if test="${st.count%4==0}"></tr><tr></c:if>
					</c:forEach>
				</tr></table>
				</td>
			</tr> -->
			<tr>
				<td colspan="3">¿El Plan Operativo Anual es base para la toma de decisiones?</td>
			</tr>
			<tr>
				<td colspan="4">
				<table width="80%" align="center"><tr>
					<c:forEach begin="0" items="${requestScope.listaFrecuencia}" var="frecuencia" varStatus="st">
						<td>
						<c:out value="${frecuencia.nombre}"/> <input type="radio" name="desPoaDecisiones" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='<c:out value="${frecuencia.codigo}"/>' <c:if test="${frecuencia.codigo==sessionScope.desarrolloAdministrativoPEIVO.desPoaDecisiones}">checked</c:if>></input> 
						</td>
						<c:if test="${st.count%4==0}"></tr><tr></c:if>
					</c:forEach>
				</tr></table>
				</td>
			</tr>
			<tr>
				<td colspan="3">Con relación a la evaluación institucional, señalar:</td>
			</tr>
			<tr>
				<td colspan="3">¿En su desarrollo participan los órganos del Gobierno Escolar?</td>
			</tr>
			<tr>
				<td colspan="4">
				<table width="80%" align="center"><tr>
					<c:forEach begin="0" items="${requestScope.listaFrecuencia}" var="frecuencia" varStatus="st">
						<td>
						<c:out value="${frecuencia.nombre}"/> <input type="radio" name="desEiConstruccion" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='<c:out value="${frecuencia.codigo}"/>' <c:if test="${frecuencia.codigo==sessionScope.desarrolloAdministrativoPEIVO.desEiConstruccion}">checked</c:if>></input> 
						</td>
						<c:if test="${st.count%4==0}"></tr><tr></c:if>
					</c:forEach>
				</tr></table>
				</td>
			</tr>
			<tr>
				<td colspan="3">¿La evaluación institucional es la base para la toma de decisiones?</td>
			</tr>
			<tr>
				<td colspan="4">
				<table width="80%" align="center"><tr>
					<c:forEach begin="0" items="${requestScope.listaFrecuencia}" var="frecuencia" varStatus="st">
						<td>
						<c:out value="${frecuencia.nombre}"/> <input type="radio" name="desEiDecisiones" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='<c:out value="${frecuencia.codigo}"/>' <c:if test="${frecuencia.codigo==sessionScope.desarrolloAdministrativoPEIVO.desEiDecisiones}">checked</c:if>></input> 
						</td>
						<c:if test="${st.count%4==0}"></tr><tr></c:if>
					</c:forEach>
				</tr></table>
				</td>
			</tr>
			<tr>
				<td colspan="3">¿Con qué frecuencia se realizan evaluaciones institucionales?</td>
			</tr>
			<tr>
				<td colspan="4">
				<table width="80%" align="center"><tr>
					<c:forEach begin="0" items="${requestScope.listaFrecuenciaEvaluacion}" var="frecuencia" varStatus="st">
						<td>
						<c:out value="${frecuencia.nombre}"/> <input type="radio" name="desEiFrecuencia" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='<c:out value="${frecuencia.codigo}"/>' <c:if test="${frecuencia.codigo==sessionScope.desarrolloAdministrativoPEIVO.desEiFrecuencia}">checked</c:if>></input> 
						</td>
						<c:if test="${st.count%4==0}"></tr><tr></c:if>
					</c:forEach>
				</tr></table>
				</td>
			</tr>
			<tr>
				<td colspan="3">¿Qué instrumentos o herramientas utilizan para la evaluación institucional?</td>
			</tr>
			<tr>
				<td colspan="4">
				<table width="80%" align="center">
					<tr>
						<td>
						Orientación de la SED <input type="checkbox" name="desEiOrientacion_" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='1' <c:if test="${1==sessionScope.desarrolloAdministrativoPEIVO.desEiOrientacion}">checked</c:if> onclick="javaScript:cambioValor(this,document.frmNuevo.desEiOrientacion);"></input> 
						<input type="hidden" name="desEiOrientacion" value='<c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desEiOrientacion}"/>'></input>
						</td>
					</tr>
					<tr>
						<td>
						Otros Documentos <input type="checkbox" name="desEiDocumento_" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='1' <c:if test="${1==sessionScope.desarrolloAdministrativoPEIVO.desEiDocumento}">checked</c:if> onclick="javaScript:cambioCheck(this,document.frmNuevo.desEiDocumentoCual);cambioValor(this,document.frmNuevo.desEiDocumento);"></input> 
						<input type="hidden" name="desEiDocumento" value='<c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desEiDocumento}"/>'></input>
						</td>
					</tr>
					<tr>
						<td>
						¿Cuáles? <input type="text" name="desEiDocumentoCual" size="50" maxlength="100" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='<c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desEiDocumentoCual}"/>'></input>						
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td colspan="4" class="Fila0">Procedimientos Administrativos</td>
			</tr>
			<tr>
				<td>Ultima fecha de actualizacion por el usuario : </td>
				<td>
					<input type="text" value='<c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desfechaUltimaActualizacion}"/>' readonly>
				</td>
			</tr>
			
			
			<!-- Ajustes Junio 2013 -->
			
			
			<tr>
				<td colspan="4">¿Existe en el colegio un documento que describa el sistema de matriculas y pensiones?</td>
			</tr>
			<tr>
				<td>
					<input type="radio" name="desDocumentoMatricula" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='1' <c:if test="${sessionScope.desarrolloAdministrativoPEIVO.desDocumentoMatricula==1}">checked</c:if>>Si </input>
				</td>
				<td>
					<input type="radio" name="desDocumentoMatricula" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='0' <c:if test="${sessionScope.desarrolloAdministrativoPEIVO.desDocumentoMatricula==0}">checked</c:if>>No </input>
				</td>
			</tr>
			
			<tr>
				<td colspan="4">¿Existen procedimientos para relacionarse con otras organizaciones sociales del entorno?</td>
			</tr>
			<tr>
				<td>
					<input type="radio" name="desRelacionOrganizaciones" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='1' <c:if test="${sessionScope.desarrolloAdministrativoPEIVO.desRelacionOrganizaciones==1}">checked</c:if>>Si </input>
				</td>
				<td>
					<input type="radio" name="desRelacionOrganizaciones" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='0' <c:if test="${sessionScope.desarrolloAdministrativoPEIVO.desRelacionOrganizaciones==0}">checked</c:if>>No </input>
				</td>
			</tr>
			<tr>
				<td colspan="4">¿Cuales?</td>
			</tr>
			<tr>
				<td>
					<input type="text" name="desOtrasOrganizaciones" size="50" maxlength="100" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='<c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desOtrasOrganizaciones}"/>'></input>
				</td>
			</tr>
			
			<tr>
				<td colspan="4">¿En qué medida se han desarrollado los mecanismos para la evaluación de los recursos humanos, físicos, económicos y tecnológicos disponibles y previstos para el futuro, con el fin de realizar el proyecto?. Estimar en un porcentaje </td>
			</tr>
			<tr>
				<td>
					<input type="text" name="desPorcentajeMecanismos" size="3" maxlength="3" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='<c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desPorcentajeMecanismos}"/>' onKeyPress='return acepteNumeros(event)' onblur="valorMaximoPorcentaje(event);">%</input>
				</td>
			</tr>
			
			
			<tr>
				<td colspan="4">¿En qué medida se han establecido estrategias para articular la institución educativa con las expresiones culturales locales y regionales? Estimar en un porcentaje </td>
			</tr>
			<tr>
				<td>
					<input type="text" name="desPorcentajeExpresiones" size="3" maxlength="3" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='<c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desPorcentajeExpresiones}"/>' onKeyPress='return acepteNumeros(event)' onblur="valorMaximoPorcentaje(event);">%</input>
				</td>
			</tr>
			<tr>
				<td colspan="4">¿Cuales?</td>
			</tr>
			<tr>
				<td>
					<input type="text" name="desExpresiones" size="50" maxlength="100" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='<c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desExpresiones}"/>'></input>
				</td>
			</tr>
			
			<tr>
				<td colspan="4">¿En qué medida se han establecido criterios de organización administrativa y de evaluación de la gestión? Estimar en un porcentaje </td>
			</tr>
			<tr>
				<td>
					<input type="text" name="desPorcentajeCriteriosOrgAdmin" size="3" maxlength="3" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='<c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desPorcentajeCriteriosOrgAdmin}"/>' onKeyPress='return acepteNumeros(event)' onblur="valorMaximoPorcentaje(event);">%</input>
				</td>
			</tr>
		 	
			<!-- <tr>
				<td>&nbsp;</td>
				<td colspan="3">De los siguientes ítems señalar la etapa de desarrollo en que se encuentra:</td>
			</tr>
			<tr>
				<td colspan="4">
					<table width="80%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="gray">
					<tr>
						<th align="center">NOMBRE</th>
						<c:forEach begin="0" items="${requestScope.listaEtapa}" var="etapa" varStatus="st">
						<th align="center"><c:out value="${etapa.nombre}"/></th>
						</c:forEach>
						<th align="center">DIFICULTADES</th>
					</tr>
					<tr>
						<td align="center">Procedimientos Administrativos</td>
						<c:forEach begin="0" items="${requestScope.listaEtapa}" var="etapa" varStatus="st">
						<td align="center"><input type="radio" name="desEtapaAdmin" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='<c:out value="${etapa.codigo}"/>' <c:if test="${etapa.codigo==sessionScope.desarrolloAdministrativoPEIVO.desEtapaAdmin}">checked</c:if> onclick="javaScript:cambioCheck(this,document.frmNuevo.desDificultadAdmin);"></input></td>
						</c:forEach>
						<td><textarea name="desDificultadAdmin" rows="3" cols="30" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> onKeyDown="textCounter(this,600,600);" onKeyUp="textCounter(this,600,600);"><c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDificultadAdmin}"/></textarea></td>
					</tr>
					<tr>
						<td align="center">Manual de funciones</td>
						<c:forEach begin="0" items="${requestScope.listaEtapa}" var="etapa" varStatus="st">
						<td align="center"><input type="radio" name="desEtapaManual" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='<c:out value="${etapa.codigo}"/>' <c:if test="${etapa.codigo==sessionScope.desarrolloAdministrativoPEIVO.desEtapaManual}">checked</c:if> onclick="javaScript:cambioCheck(this,document.frmNuevo.desDificultadManual);"></input></td>
						</c:forEach>
						<td><textarea name="desDificultadManual" rows="3" cols="30" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> onKeyDown="textCounter(this,600,600);" onKeyUp="textCounter(this,600,600);"><c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDificultadManual}"/></textarea></td>
					</tr>
					<tr>
						<td align="center">Procedimiento de manejo de inventarios</td>
						<c:forEach begin="0" items="${requestScope.listaEtapa}" var="etapa" varStatus="st">
						<td align="center"><input type="radio" name="desEtapaInventario" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='<c:out value="${etapa.codigo}"/>' <c:if test="${etapa.codigo==sessionScope.desarrolloAdministrativoPEIVO.desEtapaInventario}">checked</c:if> onclick="javaScript:cambioCheck(this,document.frmNuevo.desDificultadInventario);"></input></td>
						</c:forEach>
						<td><textarea name="desDificultadInventario" rows="3" cols="30" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> onKeyDown="textCounter(this,600,600);" onKeyUp="textCounter(this,600,600);"><c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDificultadInventario}"/></textarea></td>
					</tr>
					<tr>
						<td align="center">Sistema de Información Escolar</td>
						<c:forEach begin="0" items="${requestScope.listaEtapa}" var="etapa" varStatus="st">
						<td align="center"><input type="radio" name="desEtapaEscolar" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='<c:out value="${etapa.codigo}"/>' <c:if test="${etapa.codigo==sessionScope.desarrolloAdministrativoPEIVO.desEtapaEscolar}">checked</c:if> onclick="javaScript:cambioCheck(this,document.frmNuevo.desDificultadEscolar);"></input></td>
						</c:forEach>
						<td><textarea name="desDificultadEscolar" rows="3" cols="30" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> onKeyDown="textCounter(this,600,600);" onKeyUp="textCounter(this,600,600);"><c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDificultadEscolar}"/></textarea></td>
					</tr>
					<tr>
						<td align="center">Estrategia de comunicaciones en la institución</td>
						<c:forEach begin="0" items="${requestScope.listaEtapa}" var="etapa" varStatus="st">
						<td align="center"><input type="radio" name="desEtapaComunicacion" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> value='<c:out value="${etapa.codigo}"/>' <c:if test="${etapa.codigo==sessionScope.desarrolloAdministrativoPEIVO.desEtapaComunicacion}">checked</c:if> onclick="javaScript:cambioCheck(this,document.frmNuevo.desDificultadComunicacion);"></input></td>
						</c:forEach>
						<td><textarea name="desDificultadComunicacion" rows="3" cols="30" <c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDisabled_}"/> onKeyDown="textCounter(this,600,600);" onKeyUp="textCounter(this,600,600);"><c:out value="${sessionScope.desarrolloAdministrativoPEIVO.desDificultadComunicacion}"/></textarea></td>
					</tr>
					</table>
				</td>
		 	</tr> -->
		 	
		</table>	
	</form>
	<script type="text/javascript">
		<c:if test="${!sessionScope.desarrolloAdministrativoPEIVO.desDisabled}">
		cambioCheck(document.frmNuevo.desEiDocumento_,document.frmNuevo.desEiDocumentoCual); 
		cambioCheck(document.frmNuevo.desEtapaAdmin,document.frmNuevo.desDificultadAdmin); 
		cambioCheck(document.frmNuevo.desEtapaManual,document.frmNuevo.desDificultadManual); 
		cambioCheck(document.frmNuevo.desEtapaInventario,document.frmNuevo.desDificultadInventario); 
		cambioCheck(document.frmNuevo.desEtapaEscolar,document.frmNuevo.desDificultadEscolar); 
		cambioCheck(document.frmNuevo.desEtapaComunicacion,document.frmNuevo.desDificultadComunicacion); 
		</c:if>
	</script>
	</c:if>
</body>
</html>