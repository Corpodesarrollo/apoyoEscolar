<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.horarioArticulacion.vo.ParamsVO" scope="page"/>
<html>
<head>
<c:import url="/parametros.jsp"/>
<STYLE type='text/css'>
		.c1{COLOR:red;}
		.c2{COLOR:blue;}
		.c3{COLOR:green;}
		.c7{COLOR:brown;}
</STYLE>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:175px;overflow:auto;vertical-align:top;background-color:#ffffff;"><c:import url="/articulacion/horarioArticulacion/Lista.do"/></div>
		</td></tr>
	</table>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/articulacion/horarioArticulacion/SaveHorario.jsp"/>'>
			<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_HORARIO_GRUPO}"/>'>
			<input type="hidden" name="cmd" value=''>
		<c:if test="${sessionScope.horArtVO.formaEstado==1}">
			<table border="0" align="center" width="100%"><caption>HORARIO</caption><tr><td></td></tr></table>
			<table border="1" align="center" bordercolor="black" width="100%" cellpadding="0" cellspacing="0">
				<tr><th width="5%">&nbsp;</th>
					<c:forEach begin="0" items="${sessionScope.horArtVO.horEncabezado}" var="encabezado">
					<th width="20%">&nbsp;<c:out value="${encabezado.nombre}"/>&nbsp;</th>
					</c:forEach>
				</tr>
					<c:forEach begin="0" items="${sessionScope.horArtVO.horClase}" var="clase">
					<tr>
						<th>&nbsp;<c:out value="${clase.hora}"/>&nbsp;</th>
						<c:forEach begin="0" items="${clase.dia}" var="dia">
							<td align="center" class='<c:out value="${dia.style}"/>'>
								<span class="c1"><c:out value="${dia.asignatura}"/></span><br>
								<span class="c2"><c:out value="${dia.docente}"/></span><br>
								<span class="c3"><c:out value="${dia.espacio}"/></span><br>
							</td>
						</c:forEach>
					<tr>
					</c:forEach>
		  </table>
			<table border="1" align="center" bordercolor="black" width="30%" cellpadding="0" cellspacing="0">
			<tr><th colspan="1" align="center"><b>CONVENCIONES</b></th></tr>
			<tr><td align="center"><span class="c1"> --Asignatura-- </span></td></tr>
			<tr><td align="center"><span class="c2"> --Docente-- </span></td></tr>
			<tr><td align="center"><span class="c3"> --Espacio físico-- </span></td></tr>
			</table>		  
		</c:if>
	</form>
</body>
</html>