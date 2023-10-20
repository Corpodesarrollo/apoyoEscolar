<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="desarrolloCurricularPEIVO" class="pei.registro.vo.DesarrolloCurricularVO" scope="session"/>
<jsp:setProperty name="desarrolloCurricularPEIVO" property="*"/>
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
<SCRIPT language=javascript src='<c:url value="/pei/registro/NuevoDesarrolloCurricular.js"/>'></SCRIPT>
<script language="javaScript">var nav4=window.Event ? true : false;</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="reSize();mensaje(document.getElementById('msg'));">
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
				<img src='<c:url value="/etc/img/tabs/pei/desarrollo_curricular_1.gif"/>' alt="Desarrollo Curricular" height="26" border="0">
				<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_DESARROLLO_ADMINISTRATIVO}"/>)"><img src='<c:url value="/etc/img/tabs/pei/desarrollo_administrativo_0.gif"/>' alt="Desarrollo administrativo" height="26" border="0"></a>
				<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_DOCUMENTO}"/>)"><img src='<c:url value="/etc/img/tabs/pei/documento_0.gif"/>' alt="Documento" height="26" border="0"></a>
			</td>
		</tr>
	</table>
	<!-- /TABS -->
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='SaveDesarrolloCurricular.jsp'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_DESARROLLO_CURRICULAR}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="desInstitucion" value='<c:out value="${sessionScope.desarrolloCurricularPEIVO.desInstitucion}"/>'>
		<input type="hidden" name="desEstado" value='<c:out value="${sessionScope.desarrolloCurricularPEIVO.desEstado}"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
			<caption>Estado PEI: <span class='estadoPEI'><c:out value="${sessionScope.desarrolloCurricularPEIVO.desEstadoNombre}"/></span></caption>
				<tr>
					<td align="left" width="70%">
						<c:if test="${sessionScope.NivelPermiso==2 && sessionScope.desarrolloCurricularPEIVO.desDisabled==false}">
    					<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
	    				</c:if>
	    				<c:if test="${sessionScope.desarrolloCurricularPEIVO.desDisabled==true}"><span class="style2">No se puede editar la información</span></c:if>
			  		</td>
			 	</tr>	
		 </table>
		  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">

			<!-- Ajustes PEI Junio 2013 -->
			<tr>
				<td colspan="4" class="Fila0">Estado en el proceso de Desarrollo Curricular</td>
			</tr>
			<tr>
				<td colspan="4">El desarrollo de la Estructura Curricular se encuentra en la etapa de: </td>
			</tr>
			<tr>
				<td colspan="4">
					<table width="100%">
						<tr>
							<c:forEach begin="0" items="${requestScope.listaEstructuraCurricular}" var="estructuraCurricular" varStatus="st">
								<td align="center">
									<input type="radio" name="desEstructuraCurricular" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='<c:out value="${estructuraCurricular.codigo}"/>' <c:if test="${estructuraCurricular.codigo==sessionScope.desarrolloCurricularPEIVO.desEstructuraCurricular}">checked</c:if>><c:out value="${estructuraCurricular.nombre}"/></input>
								</td>
							</c:forEach>
						</tr>
					</table>
				</td>
			</tr>
			
			<tr>
				<td colspan="4">¿En qué medida la malla curricular está unificada y articulada en todas las sedes y todas las jornadas?. Indique un porcentaje </td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<input type="text" name="desMallaCurricular" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='<c:out value="${sessionScope.desarrolloCurricularPEIVO.desMallaCurricular}"/>' size="3" maxlength="3" onKeyPress='return acepteNumeros(event)' onblur="valorMaximoPorcentaje(event);"/>%
				</td>
			</tr>
			
			<tr>
				<td colspan="4">Establecer una síntesis del estado del proceso de desarrollo curricular en términos de alcances y dificultades:</td>
			</tr>
			<tr>
				<td colspan="4">
					<iframe id="frameCurriculo" name="frameCurriculo" marginheight="0" marginwidth="0"  frameborder="0" width="100%" height="300px" src='<c:url value="Nuevo.do"><c:param name="tipo" value="${paramsVO.FICHA_CURRICULO}"/>
						<c:param name="cmd" value="${paramsVO.CMD_BUSCAR}"/></c:url>' onload='reSize()'>
					</iframe>
				</td>
			</tr>
			
			<tr>
				<td colspan="4" class="Fila0">Malla Curricularr</td>
			</tr>
			<tr>
				<td colspan="4">El Plan de estudios y la prácticas pedagógicas reflejan lo formulado en el PEI  </td>
			</tr>
			<tr>
				<td colspan="4">
					<table width="100%">
						<tr>
							<c:forEach begin="0" items="${requestScope.listaPlanEstudios}" var="planEstudios" varStatus="st">
								<td align="center">
									<input type="radio" name="desPlanEstudios" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='<c:out value="${planEstudios.codigo}"/>' <c:if test="${planEstudios.codigo==sessionScope.desarrolloCurricularPEIVO.desPlanEstudios}">checked</c:if>><c:out value="${planEstudios.nombre}"/></input>
								</td>
							</c:forEach>
						</tr>
					</table>
				</td>
			</tr>
			
			<tr>
				<td colspan="4">¿El colegio desarrolla la propuesta de organizacion curricular por ciclos? </td>
			</tr>
			<tr>
				<td colspan="4">
					<table width="100%">
						<tr>
							<td align="center">
								<input type="radio" name="desCiclos" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='1' <c:if test="${sessionScope.desarrolloCurricularPEIVO.desCiclos==1}">checked</c:if>>Si
							</td>
							<td align="center">
								<input type="radio" name="desCiclos" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='0' <c:if test="${sessionScope.desarrolloCurricularPEIVO.desCiclos==0}">checked</c:if>>No
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			<tr>
				<td colspan="4">Si es otro rediseño currcular indique cual: </td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<textarea name="desOtroDiseno" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> onKeyDown="textCounter(this,100,100);" onKeyUp="textCounter(this,100,100);"><c:out value="${sessionScope.desarrolloCurricularPEIVO.desOtroDiseno}"/></textarea>
				</td>
			</tr>
			
			
			<tr>
				<td colspan="4">¿Que ciclos se han implementado en la institucion? </td>
			</tr>
			<tr>
				<td colspan="4">
					<table width="100%">
						<tr>
							<td align="center">
								<input type="checkbox" name="desCiclo1" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='1' <c:if test="${sessionScope.desarrolloCurricularPEIVO.desCiclo1==1}">checked</c:if>>Primero
							</td>
							<td align="center">
								<input type="checkbox" name="desCiclo2" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='1' <c:if test="${sessionScope.desarrolloCurricularPEIVO.desCiclo2==1}">checked</c:if>>Segundo
							</td>
							<td align="center">
								<input type="checkbox" name="desCiclo3" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='1' <c:if test="${sessionScope.desarrolloCurricularPEIVO.desCiclo3==1}">checked</c:if>>Tercero
							</td>
							<td align="center">
								<input type="checkbox" name="desCiclo4" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='1' <c:if test="${sessionScope.desarrolloCurricularPEIVO.desCiclo4==1}">checked</c:if>>Cuarto
							</td>
							<td align="center">
								<input type="checkbox" name="desCiclo5" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='1' <c:if test="${sessionScope.desarrolloCurricularPEIVO.desCiclo5==1}">checked</c:if>>Quinto
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			
		<!--<tr>
				<td colspan="4" class="Fila0" title='<c:out value="${sessionScope.filtroRegistroPEIVO.lblFase}"/>'>¿El colegio en qué fase se halla de la reorganización del currículo por Ciclos?</td>
			</tr>
			<tr>
				<td colspan="4">
				<table width="80%" align="center" cellpadding="0" cellspacing="0">
					<c:forEach begin="0" items="${requestScope.listaFase}" var="fase" varStatus="st">
						<tr>
						<td title='<c:out value="${fase.descripcion}"/>'>
						<c:out value="${fase.nombre}"/> 
						</td>
						<td align="left"  title='<c:out value="${fase.descripcion}"/>'>
						<input type="radio" name="desFaseCurriculo" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='<c:out value="${fase.codigo}"/>' <c:if test="${fase.codigo==sessionScope.desarrolloCurricularPEIVO.desFaseCurriculo}">checked</c:if>></input> 
						</td>
						</tr>
					</c:forEach>
				</table>
				</td>
			</tr>
			
			<tr>
				<td colspan="4" class="Fila0">Respecto a los siguientes elementos de la reorganización por Ciclos, ¿Qué procesos se han adelantado en el colegio con ellos?</td>
			</tr>
			<tr>
				<td colspan="4">
					<table width="90%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="gray">
					<tr>
						<th align="center">ELEMENTO</th>
						<c:forEach begin="0" items="${requestScope.listaElemento}" var="etapa" varStatus="st">
						<th align="center"><c:out value="${etapa.nombre}"/></th>
						</c:forEach>
					</tr>
					<tr>
						<td align="center">Herramientas para la vida</td>
						<c:forEach begin="0" items="${requestScope.listaElemento}" var="etapa" varStatus="st">
						<td align="center"><input type="radio" name="desHerramientaVida" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='<c:out value="${etapa.codigo}"/>' <c:if test="${etapa.codigo==sessionScope.desarrolloCurricularPEIVO.desHerramientaVida}">checked</c:if>></input></td>
						</c:forEach>
					</tr>
					<tr>
						<td align="center">Base de conocimientos esenciales</td>
						<c:forEach begin="0" items="${requestScope.listaElemento}" var="etapa" varStatus="st">
						<td align="center"><input type="radio" name="desBaseConocimiento" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='<c:out value="${etapa.codigo}"/>' <c:if test="${etapa.codigo==sessionScope.desarrolloCurricularPEIVO.desBaseConocimiento}">checked</c:if>></input></td>
						</c:forEach>
					</tr>
					</table>
				</td>
		 	</tr> -->
		 	
			<tr>
				<td colspan="4" class="Fila0">Describir brevemente los aspectos claves del proceso por Ciclos:</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<table width="90%" align="center" border="0" cellpadding="0" cellspacing="0">
						<tr><th align="left">PRIMER CICLO</th></tr>
						<tr><td>Alcances</td></tr>
						<tr><td><textarea name="desAlcance1" rows="3" cols="80" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> onKeyDown="textCounter(this,600,600);" onKeyUp="textCounter(this,600,600);"><c:out value="${sessionScope.desarrolloCurricularPEIVO.desAlcance1}"/></textarea></td></tr>
						<tr><td>Dificultades</td></tr>
						<tr><td><textarea name="desDificultad1" rows="3" cols="80" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> onKeyDown="textCounter(this,600,600);" onKeyUp="textCounter(this,600,600);"><c:out value="${sessionScope.desarrolloCurricularPEIVO.desDificultad1}"/></textarea></td></tr>
						<tr><th align="left">SEGUNDO CICLO</th></tr>
						<tr><td>Alcances</td></tr>
						<tr><td><textarea name="desAlcance2" rows="3" cols="80" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> onKeyDown="textCounter(this,600,600);" onKeyUp="textCounter(this,600,600);"><c:out value="${sessionScope.desarrolloCurricularPEIVO.desAlcance2}"/></textarea></td></tr>
						<tr><td>Dificultades</td></tr>
						<tr><td><textarea name="desDificultad2" rows="3" cols="80" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> onKeyDown="textCounter(this,600,600);" onKeyUp="textCounter(this,600,600);"><c:out value="${sessionScope.desarrolloCurricularPEIVO.desDificultad2}"/></textarea></td></tr>
						<tr><th align="left">TERCER CICLO</th></tr>
						<tr><td>Alcances</td></tr>
						<tr><td><textarea name="desAlcance3" rows="3" cols="80" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> onKeyDown="textCounter(this,600,600);" onKeyUp="textCounter(this,600,600);"><c:out value="${sessionScope.desarrolloCurricularPEIVO.desAlcance3}"/></textarea></td></tr>
						<tr><td>Dificultades</td></tr>
						<tr><td><textarea name="desDificultad3" rows="3" cols="80" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> onKeyDown="textCounter(this,600,600);" onKeyUp="textCounter(this,600,600);"><c:out value="${sessionScope.desarrolloCurricularPEIVO.desDificultad3}"/></textarea></td></tr>
						<tr><th align="left">CUARTO CICLO</th></tr>
						<tr><td>Alcances</td></tr>
						<tr><td><textarea name="desAlcance4" rows="3" cols="80" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> onKeyDown="textCounter(this,600,600);" onKeyUp="textCounter(this,600,600);"><c:out value="${sessionScope.desarrolloCurricularPEIVO.desAlcance4}"/></textarea></td></tr>
						<tr><td>Dificultades</td></tr>
						<tr><td><textarea name="desDificultad4" rows="3" cols="80" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> onKeyDown="textCounter(this,600,600);" onKeyUp="textCounter(this,600,600);"><c:out value="${sessionScope.desarrolloCurricularPEIVO.desDificultad4}"/></textarea></td></tr>
						<tr><th align="left">QUINTO CICLO</th></tr>
						<tr><td>Alcances</td></tr>
						<tr><td><textarea name="desAlcance5" rows="3" cols="80" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> onKeyDown="textCounter(this,600,600);" onKeyUp="textCounter(this,600,600);"><c:out value="${sessionScope.desarrolloCurricularPEIVO.desAlcance5}"/></textarea></td></tr>
						<tr><td>Dificultades</td></tr>
						<tr><td><textarea name="desDificultad5" rows="3" cols="80" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> onKeyDown="textCounter(this,600,600);" onKeyUp="textCounter(this,600,600);"><c:out value="${sessionScope.desarrolloCurricularPEIVO.desDificultad5}"/></textarea></td></tr>
					</table>
				</td>
		 	</tr>
		 	<!-- Ajustes Junio 2013 -->
		 	
		 	<tr>
				<td colspan="4" class="Fila0">Sistema de Evaluación</td>
			</tr>
			<tr>
				<td colspan="4">Sistema Institucional de Evaluacion se encuentra en la etapa de:  </td>
			</tr>
			<tr>
				<td colspan="4">
					<table width="100%">
						<tr>
							<c:forEach begin="0" items="${requestScope.listaSistemaEvaluacion}" var="sistemaEvaluacion" varStatus="st">
								<td align="center">
									<input type="radio" name="desSistemaEvaluacion" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='<c:out value="${sistemaEvaluacion.codigo}"/>' <c:if test="${sistemaEvaluacion.codigo==sessionScope.desarrolloCurricularPEIVO.desSistemaEvaluacion}">checked</c:if>><c:out value="${sistemaEvaluacion.nombre}"/></input>
								</td>
							</c:forEach>
						</tr>
					</table>
				</td>
			</tr>
			
			<tr>
				<td colspan="4">¿En qué medida se han definido los criterios para la evaluacion del rendimiento del educando?. Indique un porcentaje </td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<input type="text" name="desCriterioEvaluacion" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='<c:out value="${sessionScope.desarrolloCurricularPEIVO.desCriterioEvaluacion}"/>' size="3" maxlength="3" onKeyPress='return acepteNumeros(event)' onblur="valorMaximoPorcentaje(event);"/>%
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<iframe id="frameCriterioEvaluacion" name="frameCriterioEvaluacion" marginheight="0" marginwidth="0"  frameborder="0" width="100%" height="300px" src='<c:url value="Nuevo.do"><c:param name="tipo" value="${paramsVO.FICHA_CRITERIO_EVALUACION}"/>
						<c:param name="cmd" value="${paramsVO.CMD_BUSCAR}"/></c:url>' onload='reSize()'>
					</iframe>
				</td>
			</tr>
			
		 	<!-- Fin Ajustes Junio 2013 -->
		 	
		 	<tr>
				<td colspan="4" class="Fila0">Proyectos de Política Educativa</td>
			</tr>
			<tr>
				<td colspan="4">¿La institucion desarrolla el proyecto de ciudadanía? </td>
			</tr>
			<tr>
				<td colspan="4">
					<table width="100%">
						<tr>
							<td align="center">
								<input type="radio" name="desCiudadania" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='1' <c:if test="${sessionScope.desarrolloCurricularPEIVO.desCiudadania==1}">checked</c:if>>Si
							</td>
							<td align="center">
								<input type="radio" name="desCiudadania" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='0' <c:if test="${sessionScope.desarrolloCurricularPEIVO.desCiudadania==0}">checked</c:if>>No
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="4">Indique el porcentaje de avance del proyecto de Ciudadania</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<input type="text" name="desAvanceCiudadania" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='<c:out value="${sessionScope.desarrolloCurricularPEIVO.desAvanceCiudadania}"/>' size="3" maxlength="3" onKeyPress='return acepteNumeros(event)' onblur="valorMaximoPorcentaje(event);"/>%
				</td>
			</tr>
			<tr>
				<td colspan="4">La fase en la que se encuentra el proyecto es </td>
			</tr>
			<tr>
				<td colspan="4">
					<table width="100%">
						<tr>
							<c:forEach begin="0" items="${requestScope.listaProyectoCiudadania}" var="proyectoCiudadania" varStatus="st">
								<td align="center">
									<input type="radio" name="desFaseCiudadania" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='<c:out value="${proyectoCiudadania.codigo}"/>' <c:if test="${proyectoCiudadania.codigo==sessionScope.desarrolloCurricularPEIVO.desFaseCiudadania}">checked</c:if>><c:out value="${proyectoCiudadania.nombre}"/></input>
								</td>
							</c:forEach>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<iframe id="frameCiudadania" name="frameCiudadania" marginheight="0" marginwidth="0"  frameborder="0" width="100%" height="300px" src='<c:url value="Nuevo.do"><c:param name="tipo" value="${paramsVO.FICHA_CIUDADANIA}"/>
						<c:param name="cmd" value="${paramsVO.CMD_BUSCAR}"/></c:url>' onload='reSize()'>
					</iframe>
				</td>
			</tr>
			<tr>
				<td colspan="4">¿La institucion tiene cursos de primera infancia? </td>
			</tr>
			<tr>
				<td colspan="4">
					<table width="100%">
						<tr>
							<td align="center">
								<input type="radio" name="desPrimeraInfancia" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='1' <c:if test="${sessionScope.desarrolloCurricularPEIVO.desPrimeraInfancia==1}">checked</c:if>>Si
							</td>
							<td align="center">
								<input type="radio" name="desPrimeraInfancia" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='0' <c:if test="${sessionScope.desarrolloCurricularPEIVO.desPrimeraInfancia==0}">checked</c:if>>No
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<iframe id="framePrimeraInfancia" name="framePrimeraInfancia" marginheight="0" marginwidth="0"  frameborder="0" width="100%" height="300px" src='<c:url value="Nuevo.do"><c:param name="tipo" value="${paramsVO.FICHA_PRIMERA_INFANCIA}"/>
						<c:param name="cmd" value="${paramsVO.CMD_BUSCAR}"/></c:url>' onload='reSize()'>
					</iframe>
				</td>
			</tr>
				<!-- PROYECTO 40 HORAS -->
			
			<tr>
				<td colspan="4">¿La institucion desarrolla el proyecto de 40 horas? </td>
			</tr>
			<tr>
				<td colspan="4">
					<table width="100%">
						<tr>
							<td align="center">
								<input type="radio" name="desProyecto40Horas" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='1' <c:if test="${sessionScope.desarrolloCurricularPEIVO.desProyecto40Horas==1}">checked</c:if>>Si
							</td>
							<td align="center">
								<input type="radio" name="desProyecto40Horas" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='0' <c:if test="${sessionScope.desarrolloCurricularPEIVO.desProyecto40Horas==0}">checked</c:if>>No
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<iframe id="frameProyecto40Horas" name="frameProyecto40Horas" marginheight="0" marginwidth="0"  frameborder="0" width="100%" height="300px" src='<c:url value="Nuevo.do"><c:param name="tipo" value="${paramsVO.FICHA_PROYECTO_40_HORAS}"/>
						<c:param name="cmd" value="${paramsVO.CMD_BUSCAR}"/></c:url>' onload='reSize()'>
					</iframe>
				</td>
			</tr>
			<tr>
				<td colspan="4" class="Fila0">Inclusion de Poblaciones</td>
			</tr>
			<td colspan="4">
				<iframe id="framePoblaciones" name="framePoblaciones" marginheight="0" marginwidth="0"  frameborder="0" width="100%" height="300px" src='<c:url value="Nuevo.do"><c:param name="tipo" value="${paramsVO.FICHA_POBLACIONES}"/>
					<c:param name="cmd" value="${paramsVO.CMD_BUSCAR}"/></c:url>' onload='reSize()'>
				</iframe>
			</td>
			
			<tr>
				<td colspan="4">¿La institucion tiene atención a Necesidades Educativas Especiales? </td>
			</tr>
			<tr>
				<td colspan="4">
					<table width="100%">
						<tr>
							<td align="center">
								<input type="radio" name="desNecesidades" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='1' <c:if test="${sessionScope.desarrolloCurricularPEIVO.desNecesidades==1}">checked</c:if>>Si
							</td>
							<td align="center">
								<input type="radio" name="desNecesidades" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='0' <c:if test="${sessionScope.desarrolloCurricularPEIVO.desNecesidades==0}">checked</c:if>>No
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="4">Cantidad de estudiantes con NEE (Necesidades Educativas Especiales) </td>
			</tr>
			<tr>
				<td colspan="4">
					<table width="100%">
						<tr>
							<td colspan="4" align="center">
								<input type="text" name="desCantidadNecesidades" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='<c:out value="${sessionScope.desarrolloCurricularPEIVO.desCantidadNecesidades}"/>' size="3" maxlength="3" onKeyPress='return acepteNumeros(event)' onblur="valorMaximoPorcentaje(event);"/>
								
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			
			
			
			
			
			
			
			
		 	<!-- 
			<tr>
				<td colspan="4" class="Fila0">Registre su Criterio de Alcance</td>
			</tr>
			<tr>
			<td colspan="4" >
				<table width="90%" align="center" border="1" bordercolor="gray" cellpadding="0" cellspacing="0">
					<tr><td align="center" colspan="2" bgcolor="silver" title='<c:out value="${sessionScope.filtroRegistroPEIVO.lblPreparacion}"/>'>PREPARACIÓN</td>
					<td align="center" bgcolor="silver">Max.</td></tr>
					<c:forEach begin="0" items="${sessionScope.desarrolloCurricularPEIVO.desListaCriterioPreparacion}" var="criterio" varStatus="st">
						<tr>
						<td title='<c:out value="${criterio.critDescripcion}"/>'><c:out value="${criterio.critNombre}"/></td>
						<td title='<c:out value="${criterio.critDescripcion}"/>'>
							<input type="text"  size="3" maxlength="3" name="desCriterioPreparacion_" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='<c:out value="${criterio.critValor}"/>' onKeyPress='return acepteNumeros(event)'></input> 
							<input type="hidden" name="desCriterioPreparacion" value='<c:out value="${criterio.critCodigo}"/>:'></input> 
							<input type="hidden" name="desCriterioPreparacion2" value='<c:out value="${criterio.critValorMaximo}"/>'></input> 
						</td>
						<td bgcolor="silver"><c:out value="${criterio.critValorMaximo}"/></td>
						</tr>
					</c:forEach>
				</table>
				<table width="90%" align="center" border="1" bordercolor="gray" cellpadding="0" cellspacing="0">
					<tr><td align="center" colspan="2" bgcolor="silver" title='<c:out value="${sessionScope.filtroRegistroPEIVO.lblFormulacion}"/>'>FORMULACIÓN</td>
					<td align="center" bgcolor="silver">Max.</td></tr>
					<c:forEach begin="0" items="${sessionScope.desarrolloCurricularPEIVO.desListaCriterioFormulacion}" var="criterio" varStatus="st">
						<tr>
						<td title='<c:out value="${criterio.critDescripcion}"/>'><c:out value="${criterio.critNombre}"/></td>
						<td title='<c:out value="${criterio.critDescripcion}"/>'>
							<input type="text"  size="3" maxlength="3" name="desCriterioFormulacion_" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='<c:out value="${criterio.critValor}"/>'></input> 
							<input type="hidden" name="desCriterioFormulacion" value='<c:out value="${criterio.critCodigo}"/>:'></input> 
							<input type="hidden" name="desCriterioFormulacion2" value='<c:out value="${criterio.critValorMaximo}"/>'></input> 
						</td>
						<td bgcolor="silver"><c:out value="${criterio.critValorMaximo}"/></td>
						</tr>
					</c:forEach>
				</table>
				<table width="90%" align="center" border="1" bordercolor="gray" cellpadding="0" cellspacing="0">
					<tr><td align="center" colspan="2" bgcolor="silver" title='<c:out value="${sessionScope.filtroRegistroPEIVO.lblEjecucion}"/>'>EJECUCIÓN</td>
					<td align="center" bgcolor="silver">Max.</td></tr>
					<c:forEach begin="0" items="${sessionScope.desarrolloCurricularPEIVO.desListaCriterioEjecucion}" var="criterio" varStatus="st">
						<tr>
						<td title='<c:out value="${criterio.critDescripcion}"/>'><c:out value="${criterio.critNombre}"/></td>
						<td title='<c:out value="${criterio.critDescripcion}"/>'>
							<input type="text" size="3" maxlength="3" name="desCriterioEjecucion_" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='<c:out value="${criterio.critValor}"/>'></input> 
							<input type="hidden" name="desCriterioEjecucion" value='<c:out value="${criterio.critCodigo}"/>:'></input> 
							<input type="hidden" name="desCriterioEjecucion2" value='<c:out value="${criterio.critValorMaximo}"/>'></input> 
						</td>
						<td bgcolor="silver"><c:out value="${criterio.critValorMaximo}"/></td>
						</tr>
					</c:forEach>
				</table>
				<table width="90%" align="center" border="1" bordercolor="gray" cellpadding="0" cellspacing="0">
					<tr><td align="center" colspan="2" bgcolor="silver" title='<c:out value="${sessionScope.filtroRegistroPEIVO.lblSeguimiento}"/>'>SEGUIMIENTO, EVALUACIÓN Y SOSTENIBILIDAD</td>
					<td align="center" bgcolor="silver">Max.</td></tr>
					<c:forEach begin="0" items="${sessionScope.desarrolloCurricularPEIVO.desListaCriterioSeguimiento}" var="criterio" varStatus="st">
						<tr>
						<td title='<c:out value="${criterio.critDescripcion}"/>'><c:out value="${criterio.critNombre}"/></td>
						<td title='<c:out value="${criterio.critDescripcion}"/>'>
							<input type="text" size="3" maxlength="3" name="desCriterioSeguimiento_" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='<c:out value="${criterio.critValor}"/>'></input> 
							<input type="hidden" name="desCriterioSeguimiento" value='<c:out value="${criterio.critCodigo}"/>:'></input> 
							<input type="hidden" name="desCriterioSeguimiento2" value='<c:out value="${criterio.critValorMaximo}"/>'></input> 
						</td>
						<td bgcolor="silver"><c:out value="${criterio.critValorMaximo}"/></td>
						</tr>
					</c:forEach>
				</table>
			</td>
			</tr>
			-->
			<tr>
				<td colspan="4" class="Fila0">Media Especializada</td>
			</tr>
			
			
			<tr>
			<td>¿El colegio está articulado con la eduación superior?&nbsp;&nbsp;&nbsp;&nbsp;
				<c:forEach begin="0" items="${requestScope.listaSiNo}" var="resp" varStatus="st">
				<c:out value="${resp.nombre}"/> <input type="radio" name="desArticulado" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='<c:out value="${resp.codigo}"/>' <c:if test="${resp.codigo==sessionScope.desarrolloCurricularPEIVO.desArticulado}">checked</c:if> onclick="javaScript:cambioArticulado(document.frmNuevo.desArticulado)"></input>&nbsp;&nbsp;&nbsp;				
				</c:forEach>			
			</td>
			</tr>
			<tr>
			<td colspan="4" >Indicar la estrategia utilizada:</td>
			</tr>				
			<tr>
			<td colspan="4" >
				<table width="50%" align="left" border="0" cellpadding="0" cellspacing="0">
				<!--
				<tr>
				 <td>Media especializada</td>
				<td>
					<input type="checkbox" name="desMedia_" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='1' <c:if test="${1==sessionScope.desarrolloCurricularPEIVO.desMedia}">checked</c:if>></input>
					<input type="hidden" name="desMedia" value='<c:out value="${sessionScope.desarrolloCurricularPEIVO.desMedia}"/>'></input>
				</td>
				</tr> -->
				<tr>
				<td>Articulación con Instituciones de Educación Superior</td>
				<td>
					<input type="checkbox" name="desArticUniversidad_" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='1' <c:if test="${1==sessionScope.desarrolloCurricularPEIVO.desArticUniversidad}">checked</c:if>></input>
					<input type="hidden" name="desArticUniversidad" value='<c:out value="${sessionScope.desarrolloCurricularPEIVO.desArticUniversidad}"/>'></input>
				</td>
				</tr>
				<tr>
				<td>Articulados a Nivel superior con el SENA</td>
				<td>
					<input type="checkbox" name="desArticSena_" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='1' <c:if test="${1==sessionScope.desarrolloCurricularPEIVO.desArticSena}">checked</c:if>></input>
					<input type="hidden" name="desArticSena" value='<c:out value="${sessionScope.desarrolloCurricularPEIVO.desArticSena}"/>'></input>
				</td>
				</tr>
				<!--
				<tr>
				<td>Integrados con el SENA</td>
				<td>
					<input type="checkbox" name="desIntegradoSena_" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='1' <c:if test="${1==sessionScope.desarrolloCurricularPEIVO.desIntegradoSena}">checked</c:if>></input>
					<input type="hidden" name="desIntegradoSena" value='<c:out value="${sessionScope.desarrolloCurricularPEIVO.desArticSena}"/>'></input>
				</td>
				</tr>-->
				</table>
			</td>
			</tr>
			<tr>
				<td colspan="4" class="Fila0" title='<c:out value="${sessionScope.filtroRegistroPEIVO.lblPrograma}"/>'>Articulación en programas con el SENA</td>
			</tr>
			<tr>
				<td colspan="4">
				<iframe id="frameProgramaSENA" name="frameProgramaSENA" marginheight="0" marginwidth="0"  frameborder="0" width="100%" height="50px" src='<c:url value="Nuevo.do"><c:param name="tipo" value="${paramsVO.FICHA_PROGRAMA_SENA}"/><c:param name="cmd" value="${paramsVO.CMD_BUSCAR}"/></c:url>' onload='reSize()'></iframe>
				</td>
			</tr>
			<tr>
				<td colspan="4" class="Fila0" title='<c:out value="${sessionScope.filtroRegistroPEIVO.lblUniversidad}"/>'>Articulación con Instituciones Universitarias</td>
			</tr>
			<tr>
				<td colspan="4">
				<iframe id="frameProgramaUniversidad" name="frameProgramaUniversidad" marginheight="0" marginwidth="0"  frameborder="0" width="100%" height="300px" src='<c:url value="Nuevo.do"><c:param name="tipo" value="${paramsVO.FICHA_PROGRAMA_UNIVERSIDAD}"/><c:param name="cmd" value="${paramsVO.CMD_BUSCAR}"/></c:url>' onload='reSize()'></iframe>
				</td>
			</tr>
			<!-- 
			<tr>
				<td colspan="4">
				<table width="50%" align="left" border="0" cellpadding="0" cellspacing="0">
				<tr>
				<td>¿Realiza el seguimiento a egresados?</td>
				<td>
					<c:forEach begin="0" items="${requestScope.listaSiNo}" var="resp" varStatus="st">
					<c:out value="${resp.nombre}"/> <input type="radio" name="desEgresado" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='<c:out value="${resp.codigo}"/>' <c:if test="${resp.codigo==sessionScope.desarrolloCurricularPEIVO.desEgresado}">checked</c:if>></input>&nbsp;&nbsp;&nbsp;				
					</c:forEach>			
				</td>
				</tr>
				<tr>
				<td>¿Realiza práctica laboral o pasantías?</td>
				<td>
					<c:forEach begin="0" items="${requestScope.listaSiNo}" var="resp" varStatus="st">
					<c:out value="${resp.nombre}"/> <input type="radio" name="desPasantia" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='<c:out value="${resp.codigo}"/>' <c:if test="${resp.codigo==sessionScope.desarrolloCurricularPEIVO.desPasantia}">checked</c:if>></input>&nbsp;&nbsp;&nbsp;				
					</c:forEach>			
				</td>
				</tr>
				</table>
				</td>
			</tr>
			 -->
			<tr>
				<td colspan="4" class="Fila0"  title='<c:out value="${sessionScope.filtroRegistroPEIVO.lblProyecto}"/>'>Proyectos según la Ley General de Proyectos</td>
			</tr>
			<tr>
				<td colspan="4" class="Fila0" title='<c:out value="${sessionScope.filtroRegistroPEIVO.lblProyecto}"/>'>Señale en qué etapa se encuentran los siguientes proyectos:</td>
			</tr>
			<tr>
				<td colspan="4">
				<table width="90%" align="center" border="1" bordercolor="silver" cellpadding="0" cellspacing="0">
					<tr>
					<th bgcolor="silver">Nombre</th>
					<c:forEach begin="0" items="${requestScope.listaEtapa}" var="etapa" varStatus="st">
					<th bgcolor="silver"><c:out value="${etapa.nombre}"/></th>
					</c:forEach>						
					<th bgcolor="silver">Alcances</th>
					<th bgcolor="silver">Dificultades</th>
					</tr>
					<c:forEach begin="0" items="${sessionScope.desarrolloCurricularPEIVO.desListaProyecto}" var="proyecto" varStatus="st">
						<tr>
						<td bgcolor="silver"  title='<c:out value="${proyecto.proyDescripcion}"/>'>
							<c:out value="${proyecto.proyNombre}"/>
							<input type="hidden" name="desProyecto" value='<c:out value="${proyecto.proyCodigo}"/>|'></input>
						</td>
						<c:forEach begin="0" items="${requestScope.listaEtapa}" var="etapa" varStatus="st2">
						<td align="center"  title='<c:out value="${proyecto.proyDescripcion}"/>'>
							<input type="radio" name='desProyecto<c:out value="${st.index}"/>' id='desProyecto<c:out value="${st.index}"/>' <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> value='<c:out value="${etapa.codigo}"/>' <c:if test="${etapa.codigo==proyecto.proyEtapa}">checked</c:if> onclick='javaScript:cambioProyecto(document.frmNuevo.desProyecto<c:out value="${st.index}"/>,<c:out value="${st.index}"/>);'></input>
						</td>
						</c:forEach>						
						<td title='<c:out value="${proyecto.proyDescripcion}"/>'>
							<textarea name="desProyecto_Alcances" rows="2" cols="25" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> onKeyDown="textCounter(this,200,200);" onKeyUp="textCounter(this,200,200);"><c:out value="${proyecto.proyAlcance}"/></textarea>
						</td>
						<td title='<c:out value="${proyecto.proyDescripcion}"/>'>
							<textarea name="desProyecto_" rows="2" cols="25" <c:out value="${sessionScope.desarrolloCurricularPEIVO.desDisabled_}"/> onKeyDown="textCounter(this,600,600);" onKeyUp="textCounter(this,600,600);"><c:out value="${proyecto.proyDificultad}"/></textarea>
						</td>
						</tr>
					</c:forEach>
				</table>
				</td>
			</tr>
			<tr>
				<td colspan="4" class="Fila0">Otros proyectos a iniciativa del colegio o del Nivel Central de la Secretaría de Educación
				</td>
			</tr>
			<tr>
				<td colspan="4" class="Fila0">Tales como Lectura, Escritura y Oralidad, Derechos humanos
				</td>
			</tr>
			<tr>
				<td colspan="4">
				<iframe id="frameOtroProyecto" name="frameOtroProyecto" marginheight="0" marginwidth="0"  frameborder="0" width="100%" height="300px" src='<c:url value="Nuevo.do"><c:param name="tipo" value="${paramsVO.FICHA_OTRO_PROYECTO}"/><c:param name="cmd" value="${paramsVO.CMD_BUSCAR}"/></c:url>' onload='reSize()'></iframe>
				</td>
			</tr>
			<tr>
				<td colspan="4" class="Fila0">Capacitación a Docentes</td>
			</tr>
			<tr>
				<td colspan="4">
				<iframe id="frameCapacitacion" name="frameCapacitacion" marginheight="0" marginwidth="0"  frameborder="0" width="100%" height="300px" src='<c:url value="Nuevo.do"><c:param name="tipo" value="${paramsVO.FICHA_CAPACITACION}"/><c:param name="cmd" value="${paramsVO.CMD_BUSCAR}"/></c:url>' onload='reSize()'></iframe>
				</td>
			</tr>
		</table>	
	</form>
	<script type="text/javascript">
		<c:if test="${!sessionScope.desarrolloCurricularPEIVO.desDisabled}">setProyecto(document.frmNuevo); setArticulado(document.frmNuevo);</c:if>
	</script>
	</c:if>
</body>
</html>