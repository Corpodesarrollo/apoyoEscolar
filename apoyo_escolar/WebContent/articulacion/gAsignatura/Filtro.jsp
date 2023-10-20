<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<script languaje="javaScript">
	function lanzarServicio(n){
		if(n==1){//universidad
			document.frmFiltro.action='<c:url value="/articulacion/universidad/Nuevo.do"/>';  	
  	}
		if(n==2){//tipo de programa
			document.frmFiltro.action='<c:url value="/articulacion/universidad/NuevoTipoPrograma.do"/>';  	
  	}
		if(n==3){//especialidad base
			document.frmFiltro.action='<c:url value="/articulacion/especialidadBase/Nuevo.do"/>';  	
  	}
		if(n==4){//area base
			document.frmFiltro.action='<c:url value="/articulacion/gArea/Nuevo.do"/>';  	
  	}
		if(n==5){//asignatura base
  		document.frmFiltro.action='<c:url value="/articulacion/gAsignatura/Nuevo.do"/>';  	
  	}
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function filtrar(){
		document.frmFiltro.cmd.value=document.frmFiltro.FILTRO.value;
		document.frmFiltro.action='<c:url value="/articulacion/gAsignatura/Save0.jsp"/>';
		document.frmFiltro.submit();
	}
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body>
 <form method="post" name="frmFiltro" onSubmit="return validarForma(frmNuevo)">
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10">&nbsp;</td>
			<td rowspan="2" width="469">
				<a href="javaScript:lanzarServicio(1)"><img src='<c:url value="/etc/img/tabs/universidad_0.gif"/>' alt="Universidad" height="26" border="0"></a>
				<a href="javaScript:lanzarServicio(3)"><img src='<c:url value="/etc/img/tabs/esp_base_0.gif"/>' alt="Especialidad" height="26" border="0"></a>
				<a href="javaScript:lanzarServicio(4)"><img src='<c:url value="/etc/img/tabs/g_art_area_0.gif"/>' alt="Area" height="26" border="0"></a>
				<img src='<c:url value="/etc/img/tabs/asignatura_1.gif"/>' alt="Asignaturas" height="26" border="0">
			</td>
		</tr>
	</table>
	 <input type="hidden" name="cmd" value=''>
	 <input type="hidden" name="FILTRO" value='<c:out value="${2}"/>'>
	<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Filtro de Búsqueda de Asignatura</caption>
		 	<tr>
		 		<td>
		 			<input name="cmd" type="button" value="Buscar" onClick="filtrar()" class="boton">
		 		</td>
	 		</tr>
			<tr>
				<td>
					<span class="style2">*</span><b>Area</b>
				</td>
				<td>
					<select name="codigo">
						<c:forEach begin="0" items="${requestScope.listaAreaVO}" var="area">
							<option value="<c:out value="${area.codigo}"/>" <c:if test="${area.codigo==sessionScope.FareaVO.codigo}">selected</c:if>><c:out value="${area.nombre}"/></option>
						</c:forEach>
					</select>
		  		</td>
		  	</tr>
		 </table>
	</form>
</body>
</html>