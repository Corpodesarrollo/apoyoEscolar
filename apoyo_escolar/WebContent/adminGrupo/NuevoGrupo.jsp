<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="grupoVO" class="siges.adminGrupo.vo.GrupoVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.adminGrupo.vo.ParamsVO" scope="page"/>
<html>
<head>
   <script type='text/javascript'  src='<c:url value="/dwr/interface/factory_competencia.js"/>' ></script>
   <script type='text/javascript'  src='<c:url value="/dwr/engine.js"/>' ></script> 
   <script type='text/javascript'  src='<c:url value="/dwr/util.js"/>' ></script>
   <script type='text/javascript'  src='<c:url value="/planEstudios/AjaxCompetencia.js"/>' ></script>
   

<script languaje="text/javascript">


function validarLetras(e) { 
    tecla = (document.all) ? e.keyCode : e.which; 
    if (tecla==8) return true; 
    patron =/[A-Za-z\s]/; 
    te = String.fromCharCode(tecla); 
    return patron.test(te); 
} 

function validarNumeros(e) {
    tecla = (document.all) ? e.keyCode : e.which;
 
    if (tecla==8 || tecla==46) return true; 
    patron = /\d/;
    te = String.fromCharCode(tecla);  
    return patron.test(te); 
} 

function validarNumeros2(e) { 
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true; 
    patron = /^\d/;
    te = String.fromCharCode(tecla); 
    return patron.test(te); 
} 



	function hacerValidaciones_frmNuevo(forma){
	    validarLista( forma.vigencia ,'- Vigencia', 1);
	    validarLista( forma.codmetod ,'- Metodologia', 1);	    
	    validarCampo( forma.nombre,   '- Nombre ',1,249);     
	    validarCampo( forma.orden,    '- Orden ');
	 }
		
	function guardar(){
		if(validarForma(document.frmNuevo)){
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			 document.frmNuevo.submit();
		}
	}
	
  function nuevo(){
		 
			document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
			 document.frmNuevo.submit();
	 
	}
	
	
	function lanzar(tipo){				
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.cmd.value= -1;
				document.frmNuevo.action = '<c:url value="/grupoPeriodo/ControllerAdminEdit.do"/>';
				document.frmNuevo.target = ""; 
			 	document.frmNuevo.submit();
			}
			
	function lanzar_(tipo){				
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.cmd.value = -1;
				document.frmNuevo.action = '<c:url value="/adminParamsInst/Nuevo.do"/>';
				document.frmNuevo.target = ""; 
				document.frmNuevo.submit();
			}
			
	function lanzar(dato){
				document.frmNuevo.dato.value=dato;	
				document.frmNuevo.action="<c:url value="/datoMaestro/DatoMaestroSeleccionEdit.do"/>";
				document.frmNuevo.submit();
			}
			
	function lanzarServicio(){
				document.frmNuevo.cmd.value='';	
				document.frmNuevo.action='<c:url value="/cargaAcademica/Nuevo.do"/>';
				document.frmNuevo.submit();
			}
			
 
	 
	function cancelar(){
				if (confirm('¿Esta seguro de cancelar?')){
				 location.href='<c:url value="/bienvenida.jsp"/>';
				}		
			}	

	function cargarNombre(){
		var nombreDim =  document.frmNuevo.coddim[document.frmNuevo.coddim.selectedIndex].text;
		document.frmNuevo.nombre.value=nombreDim;
		if(nombreDim.length>10){
			document.frmNuevo.abrev.value=nombreDim.substring(0,9);
		}else{
			document.frmNuevo.abrev.value=nombreDim;
		}
	}		
	 
	 
	 
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
 	  	<table border="0"  bordercolor="#FFFFFF" width="100%">
			<tr>		
			  <td width="45%" bgcolor="#FFFFFF">                
				  <input  name="cmd12" class="boton" type="button" value="Cancelar" onClick="cancelar()">
			  </td>   	
			</tr>	
      </table>
      
  <!-- FICHAS DE NAVEGACION -->	  
 <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		  
	         <tr style="height: 20px"><td>&nbsp;</td></tr>
		    <tr height="1">
			 <td width="10" bgcolor="#FFFFFF">&nbsp;</td>							
			 <td rowspan="2" bgcolor="#FFFFFF">
				 <a href="javaScript:lanzar(31)"><img src="../etc/img/tabs/plan_de_estudios_0.gif" alt="Plan de Estudios"  height="26" border="0"></a>
				 <a href="javaScript:lanzar(28)"><img src="../etc/img/tabs/areas_0.gif" alt="&Aacute;reas"  height="26" border="0"></a>
				 <a href="javaScript:lanzarServicio_2(1)"><img src="../etc/img/tabs/acad_competencias_0.gif" alt="Competencias"  height="26" border="0"></a>				 
				 <a href="javaScript:lanzar(30)"><img src="../etc/img/tabs/asignatura_0.gif" alt="Asignaturas"  height="26" border="0"></a>
				 <a href="javaScript:lanzarServicio()"><img src="../etc/img/tabs/asignacion_academica_0.gif" alt="Asignación"  height="26" border="0"></a>
				 <img src='<c:url value="/etc/img/tabs/dimensiones_1.gif"/>' alt="Dimensiones"  height="26" border="0">
			  </td>
            </tr>
    </table>
	 
  <!-- LISTA DE RESULTADOS -->
   <table   align="center"  width="100%">
   <caption>Dimensiones - Filtro de Busqueda</caption>
     <tr>
      <td>
        <c:import url="/grupo/ControllerGrupoFiltroSave.do?"><c:param value="${paramsVO.FICHA_GRUPO}" name="tipo" /></c:import>
	  </td>
	 </tr>
   </table>	
		 
		 
		 <form method="post" name="frmAjax0" action='<c:url value="/grupo/Ajax.do"/>'>
			<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_GRUPOS}"/>'>
			<input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'>
			<c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach>
		</form>	
   <!-- FORMUALARIO DE EDICION CREACION--> 		  
	<form method="post" name="frmNuevo"  action='<c:url value="/grupo/FiltroGuardar.jsp"/>'>
        <input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_DIMENSIONES}"/>'>
        <input type="hidden" name="dato" value='<c:out value="${pageScope.dat}"/>'>
        <input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="codinst"   id="codinst"    value='<c:out value="${sessionScope.dimensionVO.codinst}"/>'>
		<input type="hidden" name="orden_"    id="orden_"     value='<c:out value="${sessionScope.dimensionVO.orden}"/>'>
		<input type="hidden" name="codmetod_" id="codmetod_"  value='<c:out value="${sessionScope.dimensionVO.codmetod}"/>'>
		<input type="hidden" name="vigencia_" id="vigencia_"  value='<c:out value="${sessionScope.dimensionVO.vigencia}"/>'>
		<input type="hidden" name="coddim_" id="coddim_"  value='<c:out value="${sessionScope.dimensionVO.coddim}"/>'>
		
	  <table cellpadding="2" cellspacing="2"  width="100%">
	   <caption>Ingreso/Edición Dimensiones   <c:out value="${sessionScope.dimensionVO.vigencia}"/></caption>
	      <tr>
	      <td colspan="4"> 
	        <c:if test="${sessionScope.dimensionVO.edicion}">
       			<input class='boton' name="cmd1" type="button" value="Actualizar" onClick="guardar()">
             </c:if>
             <c:if test="${!sessionScope.dimensionVO.edicion}">
      			<input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar()">
             </c:if>
			<input class='boton' name="cmd13" type="button" value="Nuevo" onClick="nuevo()">
			<input class='boton' name="cmd13" type="button" value="Cancelar" onClick="cancelar()">
			
		 </td>
		</tr>			
		<tr><td width="80px"><font color="red">*</font>Vigencia</td>
		      <td width="120px"><select name="vigencia" id="vigencia" style="width: 60px;" '<c:if test="${sessionScope.dimensionVO.edicion}">' DISABLED '</c:if>'></select>
			  </td>
			  <td width="80px"><font color="red">*</font>Metodologia</td>
			    <td width="120px"><select name="codmetod" id="codmetod" style="width: 160px;"  '<c:if test="${sessionScope.dimensionVO.edicion}">' DISABLED  '</c:if>'></select>
			  </td>
		   </tr>	
		   <tr>
			  <td width="100px"><font color="red">*</font>Dimensión</td>
			    <td width="120px"><select name="coddim" id="coddim" style="width:160px;" onchange="cargarNombre();" '<c:if test="${sessionScope.dimensionVO.edicion}">' DISABLED  '</c:if>'></select>
			  </td>
		   </tr>		   
		   <tr><td>Abreviatura</td>
		       <td width="120px"><input type="text" name="abrev" id="abrev"  size="10" maxlength="25" value='<c:out value="${sessionScope.dimensionVO.abrev}"/>' ></td>
		       <td>Orden</td>
		       <td width="120px"><input type="text" name="orden" id="orden"  size="2" maxlength="2" value='<c:out value="${sessionScope.dimensionVO.orden}"/>' onkeypress="return validarNumeros(event)" ></td>
		   </tr>
		   <tr><td><font color="red">*</font>Nombre</td>
		       <td colspan="3"><input type="text" name="nombre" id="nombre" size="60" value='<c:out value="${sessionScope.dimensionVO.nombre}"/>'></input>  </td> 
		   </tr>
		  
	  </table>	
    </form>
  </body>
  <script>
 ajaxVigenciaF();
 //ajaxMetodologia();
 ajaxVigenciaDim();
 ajaxMetodologiaDim();
 ajaxDimensiones();
 //ajaxMetodologia();
  </script> 
</html>