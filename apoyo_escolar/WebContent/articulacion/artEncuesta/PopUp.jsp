<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="articulacion.artEncuesta.vo.ParamsVO" scope="page"/>
<jsp:useBean id="rencuestaVO" class="articulacion.artEncuesta.vo.MostrarEncuestaVO" scope="session"/>
<html>
	<head>
		<c:import url="/parametros.jsp"/>
		<script>
			function cancelar(){
				parent.close();
			}
		</script>
	</head>
	<body >
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<form action='<c:url value="/articulacion/encuesta/Save.jsp"/>' method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)">
						<input type="hidden" name="id" value="">
					<table width="98%" border="0" cellspacing="0" cellpadding="2" align="center">
						<tr><td colspan="2">&nbsp;</td></tr>
						<tr>
							<td colspan="2" class="caption">1.	De las siguientes asignaturas, ordene de mayor a menor importancia las asignaturas que más le gustan, siendo 1 la más importante y 9 la de menor importancia:</td>
						</tr>
						<tr><td colspan="2">
						<table width="50%" border="1" cellspacing="0" cellpadding="0" align="center" bordercolor="silver">
							<tr><th class="TD">Asignatura</th><th class="TD">Importancia</th></tr>
							<c:forEach begin="0" items="${requestScope.listaAsignatura}" var="asig" varStatus="st">
								<tr>
									<td class="TD4"><c:out value="${asig.nombre}"/></td>
									<td align="center" class="TD4">
											<c:forEach begin="0" items="${requestScope.listaImportancia}" var="tipo">
												<c:set var="item" scope="page"><c:out value="${asig.codigo}"/><c:out value="|"/><c:out value="${tipo.codigo}"/></c:set>
												<c:forEach begin="0" items="${sessionScope.rencuestaVO.pregunta1}" var="p1"><c:if test="${p1==item}"><c:out value="${tipo.nombre}"/></c:if></c:forEach>
											</c:forEach>
									</td>
								</tr>
							</c:forEach>
						</table>
						</td></tr>
						<tr><td colspan="2">&nbsp;</td></tr>
						<tr>
							<td colspan="2" class="caption">2.	De las dos asignaturas con mayor preferencia, señale qué es lo que más le gusta de ellas (máximo 100 caracteres).</td>
						</tr>
						<tr>
							<td class="TD" width="15%">Asignatura 1:</td>
							<td class="TD4"><c:out value="${sessionScope.rencuestaVO.pregunta2a}"/></td>
						</tr>
						<tr>
							<td class="TD" width="15%">Asignatura 2:</td>
							<td class="TD4"><c:out value="${sessionScope.rencuestaVO.pregunta2b}"/></td>
						</tr>
						<tr><td colspan="2">&nbsp;</td></tr>
						<tr>
							<td colspan="2" class="caption">3.	Para continuar estudios superiores marque el ciclo de su preferencia:</td>
						</tr>
						<tr>
							<td colspan="2"  align="center">
							<table width="50%" border="0" cellspacing="0" cellpadding="0" align="center">
								<c:forEach begin="0" items="${requestScope.listaCiclo}" var="ciclo" varStatus="st">
									<tr><td class="TD4" width="10%"><c:if test="${ciclo.codigo==sessionScope.rencuestaVO.pregunta3}"><img src='<c:url value="/etc/img/a.png"/>' border="0" width="16" height="16"></c:if></td>
									<td  class="TD4"><c:out value="${ciclo.nombre}"/><td>
									<tr>
								</c:forEach> 
							</table>
							</td>
						</tr>
						<tr><td colspan="2">&nbsp;</td></tr>
						<tr>
							<td colspan="2" class="caption">4.	Al terminar la educación media, estaría interesado en:</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
							<table width="50%" border="0" cellspacing="0" cellpadding="0" align="center">
							<c:forEach begin="0" items="${requestScope.listaInteres}" var="interes" varStatus="st">
								<tr><td class="TD4" width="10%"><c:if test="${interes.codigo==sessionScope.rencuestaVO.pregunta4}"><img src='<c:url value="/etc/img/a.png"/>' border="0" width="16" height="16"></c:if></td>
								<td class="TD4"><c:out value="${interes.nombre}"/></td></tr>
							</c:forEach>
							</table>
							</td>
						</tr>
						<tr><td colspan="2">&nbsp;</td></tr>
						<tr>
							<td colspan="2" class="caption">5.	De las siguientes carreras cuál elegiría para continuar su educación superior</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
							<table width="50%" border="0" cellspacing="0" cellpadding="0" align="center">
							<c:forEach begin="0" items="${requestScope.listaCarrera}" var="carrera" varStatus="st">
								<tr>
								<td class="TD4" width="10%"><c:if test="${carrera.codigo==sessionScope.rencuestaVO.pregunta5a}"><img src='<c:url value="/etc/img/a.png"/>' border="0" width="16" height="16"></c:if> </td>
								<td class="TD4">
								<c:out value="${carrera.nombre}"/>
								<c:if test="${carrera.padre==1}"><input type="hidden" name="hab" value='<c:out value="${carrera.codigo}"/>'>
								<select name="pregunta5b" style="width:250px;" disabled>
									<option value='0'>--Seleccione uno--</option>
									<c:forEach begin="0" items="${requestScope.listaIngenieria}" var="ing">
										<option value='<c:out value="${ing.codigo}"/>' <c:if test="${ing.codigo==sessionScope.rencuestaVO.pregunta5b}">SELECTED</c:if>><c:out value="${ing.nombre}"/></option>
									</c:forEach>
								</select>
								</c:if>								
								<c:if test="${carrera.padre==2}"><input type="hidden" name="hab" value='<c:out value="${carrera.codigo}"/>'>
								<input type="text" name="pregunta5c" class="INPUT1" value='<c:out value="${sessionScope.rencuestaVO.pregunta5c}"/>' size="30" maxlength="50" readOnly></c:if>
								</td></tr>
							</c:forEach>
							</table>
							</td>
						</tr>
						<tr><td colspan="2">&nbsp;</td></tr>
						<tr>
							<td colspan="2" class="caption">6.	De las carreras de educación tecnológica que se ofrecen en los colegios en articulación, ¿cuál le gustaría continuar? Seleccione dos opciones.</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
							<table width="90%" border="1" cellspacing="0" cellpadding="0" align="center" bordercolor="silver">
							<tr>
							<th>Instituciones de Educación Superior</th>
							<th>Colegios Sede</th>
							<th>Localidad</th>
							<th width="150">Programas Técnicos <br>Profesionales y/o Tecnológicos</th>
							<th width="100">Ciclo Profesional</th>
							<th width="100">Opc. 1&nbsp;Opc. 2</th>							
							</tr>
							<tr><td colspan="3" class="TD4">Ninguna opción</td>
							<td class="TD4" colspan="3">
								<table width="350" border="1" align="left" cellpadding="0" cellspacing="0" bordercolor="silver">
									<tr>
										<td width="250" class="TD4">&nbsp;</td>
										<c:set var="item" scope="page"><c:out value="0|0"/></c:set>
										<td width="50" class="TD4" align="center"><c:if test="${item==sessionScope.rencuestaVO.pregunta6}"><img src='<c:url value="/etc/img/a.png"/>' border="0" width="16" height="16"></c:if>&nbsp;</td>
										<td width="50" class="TD4" align="center"><c:if test="${item==sessionScope.rencuestaVO.pregunta7}"><img src='<c:url value="/etc/img/a.png"/>' border="0" width="16" height="16"></c:if>&nbsp;</td>
								</table>
							</td>
							</tr>
							<c:forEach begin="0" items="${requestScope.listaEspecialidad}" var="univ" varStatus="st">
								<tr><td rowspan='<c:out value="${univ.size}"/>' class="TD4"><c:out value="${univ.nombre}"/></td></tr>
								<c:forEach begin="0" items="${univ.listaColegio}" var="col" varStatus="st2">
									<tr>
										<td class="TD4"><c:out value="${col.nombre}"/></td>
										<td class="TD4"><c:out value="${col.localidad}"/></td>
										<td class="TD4" colspan="3">
											<table width="350" border="1" align="left" cellpadding="0" cellspacing="0" bordercolor="silver">
												<c:forEach begin="0" items="${col.listaEspecialidad}" var="esp" varStatus="st3">
													<tr>
													<td width="150" class="TD4"><c:out value="${esp.nombre}"/></td>
													<td width="100" class="TD4"><c:out value="${esp.ciclo}"/>&nbsp;</td>
													<c:set var="item" scope="page"><c:out value="${col.codigo}"/><c:out value="|"/><c:out value="${esp.codigo}"/></c:set>
													<td width="50" class="TD4" align="center"><c:if test="${item==sessionScope.rencuestaVO.pregunta6}"><img src='<c:url value="/etc/img/a.png"/>' border="0" width="16" height="16"></c:if>&nbsp;</td>
													<td width="50" class="TD4" align="center"><c:if test="${item==sessionScope.rencuestaVO.pregunta7}"><img src='<c:url value="/etc/img/a.png"/>' border="0" width="16" height="16"></c:if>&nbsp;</td>
													</tr>
												</c:forEach>
											</table>
										</td>
									</tr>
								</c:forEach>
							</c:forEach>
							</table>
							</td>
						</tr>
						<tr><td colspan="2">&nbsp;</td></tr>
						<tr>
						<td colspan="2" align="center"><input name="Continuar" type="button" border="1" class="BOTON" value="Cerrar" onClick="javaScript:cancelar()"></td>
						</tr>
					</table>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>