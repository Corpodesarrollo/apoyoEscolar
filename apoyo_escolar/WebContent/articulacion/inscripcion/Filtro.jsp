<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="articulacion.inscripcion.vo.ParamsVO" scope="page"/>
<jsp:useBean id="filtroInscripcionVO" class="articulacion.inscripcion.vo.DatosVO" scope="session"/>
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
	
	
	var nav4=window.Event ? true : false;
	
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
	
	function filtrarInscripcion(){
		if (validarForma(document.frmFiltro)){
			document.frmFiltro.cmd.value=document.frmFiltro.FILTRO.value;
			document.frmFiltro.action='<c:url value="/articulacion/inscripcion/Save0.jsp"/>';
			document.frmFiltro.submit();
		}
	}
	
	function cambioSelect(objeto){
		var variable=objeto.options[objeto.selectedIndex].value
		if(variable=='0'){
			  
		       document.getElementById ('per').style.display='none';
		       document.getElementById ('per2').style.display='none';
		}
		else if(variable=='2'){
		      
		       document.getElementById ('per').style.display='';
		       document.getElementById ('per2').style.display='';
		}else if(variable=='1'){
			   
		       document.getElementById ('per').style.display='';
		       document.getElementById ('per2').style.display='';
		}
		else{
		      //document.frmNuevo.combo2.selectedIndex=0;
		      document.getElementById('esp').style.display='none';
		}
	}

	function hacerValidaciones_frmFiltro(forma){
		validarLista(forma.jornada, "- Seleccione una Jornada", 1)
	}
	
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("--","-99");
	}
</script>
</head>

<c:import url="/mensaje.jsp"/>
<body>
 <form method="post" name="frmFiltro" onSubmit="return validarForma(frmNuevo)">
	 <input type="hidden" name="cmd" value=''>
	 <input type="hidden" name="FILTRO" value='<c:out value="${2}"/>'>
	 <input type="hidden" name="especialidad" value='<c:out value="${sessionScope.login.artEspId}"/>'>
		  <table cellpadding="0" cellspacing="0" border="0"   width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<img src='<c:url value="/etc/img/tabs/art_inscripcion_1.gif"/>' alt="Perfiles" height="26" border="0">
					<a href="javaScript:lanzarServicio(2)"><img src='<c:url value="/etc/img/tabs/Horario_Estudiante_0.gif"/>' alt="" height="26" border="0"></a>
				</td>
			</tr>
		</table>
		
		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Filtro de Búsqueda</caption>
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
								<option value="<c:out value="${jornada.codigo}"/>" <c:if test="${jornada.codigo==sessionScope.filtroInscripcionVO.jornada}">selected</c:if>><c:out value="${jornada.nombre}"/></option>
							</c:forEach>
						</c:if>
					</select>
					
					
		  		</td>
		  		
					<td style='width:30px;'>
						<span class="style2">*</span><b>Componente</b>
					</td>
					
					<td>
						<select id="comp" name="componente" onChange='javaScript:cambioSelect(this);'>
							<option value="1" <c:if test="${1==sessionScope.filtroInscripcionVO.componente}">selected</c:if>>Académico</option>
							<option value="2" <c:if test="${2==sessionScope.filtroInscripcionVO.componente}">selected</c:if>>Técnico</option>
						</select>
			  		</td>
			  		
			 
			
			  		<td id='per'>
						<span class="style2">*</span><b>Semestre</b>
					</td>
			  		<td id='per2'>
						<select name="semestre">
							<option value="1" <c:if test="${1==sessionScope.filtroInscripcionVO.semestre}">selected</c:if>>1</option>
							<option value="2" <c:if test="${2==sessionScope.filtroInscripcionVO.semestre}">selected</c:if>>2</option>
							<option value="3" <c:if test="${3==sessionScope.filtroInscripcionVO.semestre}">selected</c:if>>3</option>
							<option value="4" <c:if test="${4==sessionScope.filtroInscripcionVO.semestre}">selected</c:if>>4</option>
						</select>
			  		</td>
			 	</tr>
		  </table>
	</form>
</body>
<script>
	cambioSelect(document.frmFiltro.componente);
	//setPeriodo(document.frmFiltro.especialidad,document.frmFiltro.periodo);
</script>
</html>