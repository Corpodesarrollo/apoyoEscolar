<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="horizontePEIVO" class="pei.registro.vo.HorizonteVO" scope="session"/>
<jsp:setProperty name="horizontePEIVO" property="*"/>
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
<SCRIPT language=javascript src='<c:url value="/pei/registro/NuevoHorizonte.js"/>'></SCRIPT>
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
				<img src='<c:url value="/etc/img/tabs/pei/horizonte_1.gif"/>' alt="Horizonte" height="26" border="0">
				<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_DESARROLLO_CURRICULAR}"/>)"><img src='<c:url value="/etc/img/tabs/pei/desarrollo_curricular_0.gif"/>' alt="Desarrollo curricular" height="26" border="0"></a>
				<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_DESARROLLO_ADMINISTRATIVO}"/>)"><img src='<c:url value="/etc/img/tabs/pei/desarrollo_administrativo_0.gif"/>' alt="Desarrollo administrativo" height="26" border="0"></a>
				<a href="javaScript:lanzar(<c:out value="${paramsVO.FICHA_DOCUMENTO}"/>)"><img src='<c:url value="/etc/img/tabs/pei/documento_0.gif"/>' alt="Documento" height="26" border="0"></a>
			</td>
		</tr>
	</table>
	<!-- /TABS -->
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='SaveHorizonte.jsp'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_HORIZONTE}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="horInstitucion" value='<c:out value="${sessionScope.horizontePEIVO.horInstitucion}"/>'>
		<input type="hidden" name="horEstado" value='<c:out value="${sessionScope.horizontePEIVO.horEstado}"/>'>
		<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
			<caption>Estado PEI: <span class='estadoPEI'><c:out value="${sessionScope.horizontePEIVO.horEstadoNombre}"/></span></caption>
				<tr>
					<td align="left" width="70%">
						<c:if test="${sessionScope.NivelPermiso==2 && sessionScope.horizontePEIVO.horDisabled==false}">
    					<input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
	    				</c:if>
	    				<c:if test="${sessionScope.horizontePEIVO.horDisabled==true}"><span class="style2">No se puede actualizar la información</span></c:if>
			  		</td>
			 	</tr>	
		 </table>
		  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
			
			<tr>
				<td colspan="4" class="Fila0">Acuerdos Institucionales</td>
			</tr>
			<tr>
				<td colspan="4">Indicar el proceso de diagnóstico institucional realizado</td>
			</tr>
			<tr>
				<td colspan="4">
					<table width="100%">
						<tr>
							<c:forEach begin="0" items="${requestScope.listaDiagnostico}" var="diagnostico" varStatus="st">
								<td align="center">
									<input type="radio" name="horDiagnostico" <c:out value="${sessionScope.horizontePEIVO.horDisabled_}"/> value='<c:out value="${diagnostico.codigo}"/>' <c:if test="${diagnostico.codigo==sessionScope.horizontePEIVO.horDiagnostico}">checked</c:if>><c:out value="${diagnostico.nombre}"/></input>
								</td>
							</c:forEach>
						</tr>
					</table>
				</td>
			</tr>
			
			
			<!-- 	
			<tr>
				<td colspan="4" class="Fila0">Respecto a los fines y objetivos de la educación, ¿Qué procesos han adelantado en el colegio con ellos?</td>
			</tr>
			<tr>
				<td colspan="4">
				<table width="80%" align="center"><tr>
					<c:forEach begin="0" items="${requestScope.listaProceso}" var="proceso" varStatus="st">
						<td>
						<c:out value="${proceso.nombre}"/> <input type="radio" name="horProceso" <c:out value="${sessionScope.horizontePEIVO.horDisabled_}"/> value='<c:out value="${proceso.codigo}"/>' <c:if test="${proceso.codigo==sessionScope.horizontePEIVO.horProceso}">checked</c:if>></input> 
						</td>
						<c:if test="${st.count%4==0}"></tr><tr></c:if>
					</c:forEach>
				</tr></table>
				</td>
			</tr> -->
			<tr>
				<td colspan="4" class="Fila0">Señalar la etapa en la que se encuentra el desarrollo para cada elemento</td>
			</tr>
			<tr>
				<td colspan="4">
					<table width="80%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="gray">
					<tr>
						<th align="center">ELEMENTO</th>
						<c:forEach begin="0" items="${requestScope.listaEtapa}" var="etapa" varStatus="st">
						<th align="center"><c:out value="${etapa.nombre}"/></th>
						</c:forEach>
					</tr>
					<tr>
						<td align="center">Misión</td>
						<c:forEach begin="0" items="${requestScope.listaEtapa}" var="etapa" varStatus="st">
						<td align="center"><input type="radio" name="horMision" <c:out value="${sessionScope.horizontePEIVO.horDisabled_}"/> value='<c:out value="${etapa.codigo}"/>' <c:if test="${etapa.codigo==sessionScope.horizontePEIVO.horMision}">checked</c:if>></input></td>
						</c:forEach>
					</tr>
					<tr>
						<td align="center">Visión</td>
						<c:forEach begin="0" items="${requestScope.listaEtapa}" var="etapa" varStatus="st">
						<td align="center"><input type="radio" name="horVision" <c:out value="${sessionScope.horizontePEIVO.horDisabled_}"/> value='<c:out value="${etapa.codigo}"/>' <c:if test="${etapa.codigo==sessionScope.horizontePEIVO.horVision}">checked</c:if>></input></td>
						</c:forEach>
					</tr>
					<tr>
						<td align="center">Perfiles</td>
						<c:forEach begin="0" items="${requestScope.listaEtapa}" var="etapa" varStatus="st">
						<td align="center"><input type="radio" name="horPerfil" <c:out value="${sessionScope.horizontePEIVO.horDisabled_}"/> value='<c:out value="${etapa.codigo}"/>' <c:if test="${etapa.codigo==sessionScope.horizontePEIVO.horPerfil}">checked</c:if>></input></td>
						</c:forEach>
					</tr>
					<tr>
						<td align="center">Principios y Fundamentos</td>
						<c:forEach begin="0" items="${requestScope.listaEtapa}" var="etapa" varStatus="st">
						<td align="center"><input type="radio" name=horEtapaPrincipios <c:out value="${sessionScope.horizontePEIVO.horDisabled_}"/> value='<c:out value="${etapa.codigo}"/>' <c:if test="${etapa.codigo==sessionScope.horizontePEIVO.horEtapaPrincipios}">checked</c:if>></input></td>
						</c:forEach>
					</tr>
					<tr>
						<td align="center">Objetivos Institucionales</td>
						<c:forEach begin="0" items="${requestScope.listaEtapa}" var="etapa" varStatus="st">
						<td align="center"><input type="radio" name="horObjetivo" <c:out value="${sessionScope.horizontePEIVO.horDisabled_}"/> value='<c:out value="${etapa.codigo}"/>' <c:if test="${etapa.codigo==sessionScope.horizontePEIVO.horObjetivo}">checked</c:if>></input></td>
						</c:forEach>
					</tr>
					</table>
				</td>
		 	</tr>
		 	<tr>
				<td colspan="4" class="Fila0">¿Se han establecido improntas o propósitos por ciclo y nivel?</td>
			</tr>
			<tr>
				<td align="center">
					<input type="radio" name="horImprontas" <c:out value="${sessionScope.horizontePEIVO.horDisabled_}"/> value='<c:out value="1"/>' <c:if test="${sessionScope.horizontePEIVO.horImprontas=='1'}">checked</c:if>>Si
				</td>
				<td align="center">
					<input type="radio" name="horImprontas" <c:out value="${sessionScope.horizontePEIVO.horDisabled_}"/> value='<c:out value="0"/>' <c:if test="${sessionScope.horizontePEIVO.horImprontas=='0'}">checked</c:if>>No
				</td>
			</tr>
				
			<tr>
				<td colspan="4" class="Fila0">Dificultades</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<textarea name="horDificultad" rows="3" cols="72" <c:out value="${sessionScope.horizontePEIVO.horDisabled_}"/> onKeyDown="textCounter(this,600,600);" onKeyUp="textCounter(this,600,600);"><c:out value="${sessionScope.horizontePEIVO.horDificultad}"/></textarea>
				</td>
		 	</tr>	
		</table>	
	</form>
	</c:if>
</body>
</html>