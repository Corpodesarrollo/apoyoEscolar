<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.inscripcion.vo.ParamsVO" scope="page"/>
<jsp:useBean id="componenteVO" class="articulacion.horarioEstudiante.vo.ComponenteVO" scope="session"/>
<html>
<head>
<script languaje="javaScript">
	
	function lanzarServicio(n){
		if(n==1){//
			document.frmFiltro.action='<c:url value="/articulacion/inscripcion/Nuevo.do"/>';  	
  	}
		if(n==2){//
			document.frmFiltro.action='<c:url value="/articulacion/horarioEstudiante/Lista.do"/>';  	
  	}
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
function hacerValidaciones_frmFiltro(forma)
	{
		validarLista(forma.jornada, "- Seleccione una Jornada", 1)
	}
	
	
	function filtrarInscripcion(){
	
	if (validarForma(document.frmFiltro)){
		document.frmFiltro.cmd.value=document.frmFiltro.FILTRO.value;
		document.frmFiltro.action='<c:url value="/articulacion/horarioEstudiante/Save.jsp"/>';
		document.frmFiltro.submit();
		}
	}
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("--","-99");
	}
	
	
</script>
<STYLE type='text/css'>
		.c1{COLOR:red;}
		.c2{COLOR:blue;}
		.c3{COLOR:green;}
		.c4{BACKGROUND:orange;}
		.c5{BACKGROUND:yellow;}
		.c6{BACKGROUND:cyan;}
		.c7{COLOR:brown;}
	</STYLE>
</head>
<c:import url="/mensaje.jsp"/>
<body>
 <form method="post" name="frmFiltro" onSubmit="return validarForma(frmNuevo)">
	 <input type="hidden" name="cmd" value=''>
	 <input type="hidden" name="FILTRO" value='<c:out value="${2}"/>'>
	
		  <table cellpadding="0" cellspacing="0" border="0"   width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<a href="javaScript:lanzarServicio(1)"><img src='<c:url value="/etc/img/tabs/art_inscripcion_0.gif"/>' alt="" height="26" border="0"></a>
					<img src='<c:url value="/etc/img/tabs/Horario_Estudiante_1.gif"/>' alt="Perfiles" height="26" border="0">
				</td>
			</tr>
		</table>
	
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Componente</caption>
					<tr>
				 		<td style='width:30px;'>
				 			<input name="cmd" type="button" value="Buscar" onClick="filtrarInscripcion()" class="boton">
				 		</td>
			 		</tr>
					<tr>
					
					<td style='width:30px;'>
						<span class="style2">*</span><b>Jornada</b>
					</td>
					
					<td>
					<select style='width:150px;' name="jornada">
						<option value="-99">--Seleccione uno--</option>
						<c:if test="${!empty(sessionScope.listaJornadaVO)}">

							<c:forEach begin="0" items="${sessionScope.listaJornadaVO}" var="jornada">
								<option value="<c:out value="${jornada.codigo}"/>" <c:if test="${jornada.codigo==sessionScope.componenteVO.jornada}">selected</c:if>><c:out value="${jornada.nombre}"/></option>
							</c:forEach>
						</c:if>
					</select>
					
					
		  		</td>
		  		
					<td align="center">
						<span class="style2">*</span><b>Componente &nbsp;&nbsp;&nbsp;</b>
					    <select id="comp" name="componente" onChange='javaScript:cambioSelect(this);'>
							<option value="1" <c:if test="${1==sessionScope.componenteVO.componente}">selected</c:if>>Académico</option>
							<option value="2" <c:if test="${2==sessionScope.componenteVO.componente}">selected</c:if>>Técnico</option>
						</select>
			  		</td>
			 	</tr>
		  </table>
	</form>

		  
		  <table border="1" align="center"  style='width:100%;' cellpadding=0 cellspacing=0 bordercolor='gray'>
		  <caption>HORARIO ESTUDIANTE</caption>
		  
			
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
								
								<c:set var="i" scope="page" value="${i+1}"/>
								
								<!--  <span class="c1"><c:out value="${dia.asignatura}"/></span><br>-->
								<span class="c7"><c:out value="${dia.grupo}"/></span><br>
								<span class="c1"><c:out value="${dia.nombreAsignatura}"/></span><br>
								<span class="c2"><c:out value="${dia.docente}"/></span><br>
								<span class="c3"><c:out value="${dia.espacio}"/></span><br>
								
							</td>
						</c:forEach>
					<tr>
					</c:forEach>
					 
		  </table>
		  <br>
		  <table align="right">
		  <tr>
		  <td>
		  VB Tutor __________________________
		  </td>
		  </tr>
		  </table>
		 
			  
</body>

</html>