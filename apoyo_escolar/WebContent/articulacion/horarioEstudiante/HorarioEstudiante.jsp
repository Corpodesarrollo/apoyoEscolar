<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.horarioArticulacion.vo.ParamsVO" scope="page"/>
<html>
<head>
<c:import url="/parametros.jsp"/>
<script languaje="javaScript">
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
<table cellpadding="0" cellspacing="0" border="0"   width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<img src='<c:url value="/etc/img/tabs/art_inscripcion_1.gif"/>' alt="" height="26" border="0">
				</td>
			</tr>
		</table>
		
<table width="100%" border="0" cellspacing="2" cellpadding="0" align="center">
<caption>INSCRIPCION</caption>
</table>
			<table border="1" align="center" bordercolor="black" width="100%" cellpadding="0" cellspacing="0">
				<tr><th width="5%">&nbsp;</th>
					<c:forEach begin="0" items="${sessionScope.horArtVO.horEncabezado}" var="encabezado">
					<th width="20%">&nbsp;<c:out value="${encabezado.nombre}"/>&nbsp;</th>
					</c:forEach>
				</tr>
					<c:set var="i" scope="page" value="0"/>
					<c:forEach begin="0" items="${sessionScope.horArtVO.horClase}" var="clase">
					<tr>
						<th>&nbsp;<c:out value="${clase.hora}"/>&nbsp;</th>
						<c:forEach begin="0" items="${clase.dia}" var="dia">
							<td align="center" class='<c:out value="${dia.style}"/>'>
								<input type="checkbox" name="check" value='<c:out value="${clase.clase}"/>|<c:out value="${dia.dia}"/>' <c:out value="${dia.disabled}"/> <c:out value="${dia.checked}"/>>
								<input type="hidden" name="checkHor" value='<c:out value="${clase.clase}"/>|<c:out value="${dia.dia}"/>'>
								<input type="hidden" name="checkEsp" value='<c:out value="${clase.clase}"/>|<c:out value="${dia.dia}"/>|<c:out value="${dia.esp}"/>'>
								<a id='td<c:out value="${clase.clase}"/>_<c:out value="${dia.dia}"/>' style="display:" href='javaScript:remoto(<c:out value="${i}"/>,<c:out value="${clase.clase}"/>,<c:out value="${dia.dia}"/>,<c:out value="${dia.esp}"/>);'><img src='<c:url value="/etc/img/z.gif"/>' border='0'></a>
								<c:set var="i" scope="page" value="${i+1}"/>
								<br>
								<span class="c1"><c:out value="${dia.asignatura}"/></span><br>
								<span class="c2"><c:out value="${dia.docente}"/></span><br>
								<span class="c3"><c:out value="${dia.espacio}"/></span><br>
								<span class="c7"><c:out value="${dia.grupo}"/></span><br>
							</td>
						</c:forEach>
					<tr>
					</c:forEach>
		  </table>
</body>
</html>