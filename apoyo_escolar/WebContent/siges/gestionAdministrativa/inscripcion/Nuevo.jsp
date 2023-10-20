<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="articulacion.inscripcion.vo.ParamsVO" scope="page"/>
<jsp:useBean id="filtroInscripcionVO" class="articulacion.inscripcion.vo.FiltroInscripcionVO" scope="session"/>
<html>
<head>
<style type="text/css">@import url('<c:url value="/etc/css/calendar-win2k-1.css"/>');</style>
<script type="text/javascript" src="<c:url value="/etc/js/calendar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/lang/calendar-es.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/calendar-setup.js"/>"></script>
<script languaje="javaScript">

function hacerValidaciones_frmFiltro(forma){
	  validarLista(forma.filEspecialidad, "- Especialidad", 1);
	  validarLista(forma.filSemestre, "- Semestre", 1);
	  validarLista(forma.filAsignatura, "- Asignatura", 1);
	 }

	 function buscar(){
	  if(validarForma(document.frmFiltro)){
	   document.frmFiltro.submit();
	  }
	 }
	 
	 function guardar(){
		    document.frmEstudiantes.submit();
		  }
	
	function lanzarServicio(n){
		  if(n==1){//plantilla
		   document.frmTabs.action='<c:url value="/articulacion/plantillaArticulacion/Nuevo.do"/>';   
		   }
		  if(n==2){//importacion
		   document.frmTabs.action='<c:url value="/articulacion/importarArticulacion/Nuevo.jsp"/>';   
		   } if(n==3){//Aprobación inscripciones articulación
		   document.frmTabs.action='<c:url value="/siges/gestionAdministrativa/inscripcion/Nuevo.do"/>';   
		   }
		  document.frmTabs.target="";
		  document.frmTabs.submit();
		 }
	
	 //Cambio grado 
	 function filtroEspecialidad(){
	  document.frmAjax1.cmd.value=document.frmFiltro.filEspecialidad.value;
	  if(parseInt(document.frmAjax1.cmd.value)>0){
	    document.frmAjax1.target="frameAjax1";
	    document.frmAjax1.submit();
	  }
	 }

	
</script>
</head>
<body onLoad="mensaje(document.getElementById('msg'));">
 
 <form method="post" name="frmAjax1" action='<c:url value="/siges/gestionAdministrativa/inscripcion/Ajax.do"/>'>
  <input type="hidden" name="cmd">
  <input type="hidden" name="ajax">
  <input type="hidden" name="ajax">
  <input type="hidden" name="ajax">
 </form>
	 
	 <!-- BEGIN Encabezados para cambiar de TABS -->
	 <form method="post" name="frmTabs" onSubmit="return validarForma(frmNuevo)">
	 	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			 <tr>
			  <td width="10">&nbsp;</td>
			   <td rowspan="2" width="469">
			   <a href="javaScript:lanzarServicio(1)"><img src='<c:url value="/etc/img/tabs/art_generar_plantillas_0.gif"/>' alt="" height="26" border="0"></a>
			   <a href="javaScript:lanzarServicio(2)"><img src='<c:url value="/etc/img/tabs/art_importar_plantillas_0.gif"/>' alt="" height="26" border="0"></a>
			   <img src='<c:url value="/etc/img/tabs/aprob_inscr_1.gif"/>' alt="Aprobación Inscripciones" height="26" border="0">
			  </td>
			 </tr>
			</table> 
		</form>
		<!-- END Encabezados para cambiar de TABS -->
		
		<!-- BEGIN Filtro -->
		<form method="post" name="frmFiltro" action='<c:url value="/siges/gestionAdministrativa/inscripcion/Save.jsp"/>'>
		  <input type="hidden" name="cmd" value='2'>
		  <input type="hidden" name="FILTRO" value='<c:out value="${2}"/>'>
		  
		  <tr style="display:none"><td><iframe name="frameAjax1" id="frameAjax1"></iframe></td></tr>
		  
		  <table border="0" align="center" bordercolor="#FFFFFF" width="100%">
		   <caption>Filtro de Búsqueda</caption>
		     <tr>
		       <td style='width:30px;'>
		        <input name="cmd" type="button" value="Buscar" onClick="buscar()" class="boton">
		       </td>
		      </tr>
		     <tr>	       
		      
		      <!-- BEGIN Especialidad --> 
		      <td style='width:30px;'>
		       <span class="style2">*</span><b>Especialidad</b>
		      </td>
		     <td>
		      <select style='width:150px;' name="filEspecialidad" onChange='filtroEspecialidad()'>
		      <option value="-99">--Seleccione uno--</option>
		      <c:if test="${!empty(sessionScope.listaEspecialidad)}">
		       <c:forEach begin="0" items="${sessionScope.listaEspecialidad}" var="esp">
		        <option value="<c:out value="${esp.codigo}"/>" <c:if test="${esp.codigo==sessionScope.filtroInscripcionVO.filEspecialidad}">selected</c:if>><c:out value="${esp.nombre}"/></option>
		       </c:forEach>
		      </c:if>
		     </select>
		     </td>
		     <!-- END Especialidad -->
		    
		     <!-- BEGIN Semestre --> 
		     <td id='per'>
		      <span class="style2">*</span><b>Semestre</b>
		     </td>
		       <td id='per2'>
				      <select name="filSemestre">
				      <option value="0" >--Seleccione uno--</option>
				       <option value="1" <c:if test="${1==sessionScope.filtroInscripcionVO.filSemestre}">selected</c:if>>1</option>
				       <option value="2" <c:if test="${2==sessionScope.filtroInscripcionVO.filSemestre}">selected</c:if>>2</option>
				       <option value="3" <c:if test="${3==sessionScope.filtroInscripcionVO.filSemestre}">selected</c:if>>3</option>
				       <option value="4" <c:if test="${4==sessionScope.filtroInscripcionVO.filSemestre}">selected</c:if>>4</option>
				      </select>
		       </td>
		      <!-- END Semestre --> 
		      
		      <!-- BEGIN Asignatura --> 
       <td id='per'>
        <span class="style2">*</span><b>Asignatura</b>
       </td>
         <td id='per3'>
          <select style='width:150px;' name="filAsignatura">
			        <option value="-99">--Seleccione uno--</option>
			        <c:if test="${!empty(sessionScope.listaAsignaturas)}">
			         <c:forEach begin="0" items="${sessionScope.listaAsignaturas}" var="asign">
			          <option value="<c:out value="${asign.codigo}"/>" <c:if test="${asign.codigo==sessionScope.filtroInscripcionVO.filAsignatura}">selected</c:if>><c:out value="${asign.nombre}"/></option>
			         </c:forEach>
			        </c:if>
			       </select>
         </td>
        <!-- END Asignatura --> 
		       
		     </tr>
		    </table>
		 </form>
		<!-- END Filtro -->
		
		<!-- BEGIN Estudiante lista -->
  <form method="post" name="frmEstudiantes" action='<c:url value="/siges/gestionAdministrativa/inscripcion/Save.jsp"/>'>
   <input type="hidden" name="cmd" value='3'>
    <table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
    <c:if test="${empty requestScope.inscripciones}">
    <tr><th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE INSCRIPCIONES</th></tr>
   </c:if>
    <c:if test="${!empty requestScope.inscripciones}">
     <caption>Lista inscripciones</caption>
      <tr>
	      <th class="EncabezadoColumna" align="center">
	       Aprobar
	      </th>
       <th class="EncabezadoColumna" align="center">
        Nombre
       </th>
       <th class="EncabezadoColumna" align="center">
        Grupo
       </th>
      </tr>
      <c:forEach begin="0" items="${requestScope.inscripciones}" var="inscr" varStatus="st">
       <tr>
        <th align="center">
         <input type="checkbox" name='x' value='<c:out value="${inscr.artinsCodest}"/>:<c:out value="${inscr.artinsGrupo}"/>:<c:out value="${sessionScope.filtroInscripcionVO.filAsignatura}"/>' >
        </th>
        <td>
         &nbsp;<c:out value="${inscr.nombreEstudiante}"/>
        </td>
        <td align="center">
         &nbsp;<c:out value="${inscr.artinsGrupo}"/>
        </td>
       </tr>
      </c:forEach>
     </c:if> 
    </table>
   <table border="0" align="center" bordercolor="#FFFFFF" width="100%">
    <tr>
     <td align="center" width="100%"><c:if test="${!empty requestScope.inscripciones}"><input name="cmd1" type="button" value="Aprobar" onClick="guardar()" class="boton"></c:if></td>
    </tr> 
   </table>
  </form>
		<!-- END Estudiante lista -->
		
		
</body>

</html>