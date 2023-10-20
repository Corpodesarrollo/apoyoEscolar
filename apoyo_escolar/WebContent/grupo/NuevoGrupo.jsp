<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="grupoVO" class="siges.adminGrupo.vo.GrupoVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.adminGrupo.vo.ParamsVO" scope="page"/>
<html>
<head>
   
   

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
	    //validarLista( forma.docDirector ,'- Director de Grupo', 1);	    
	    validarCampo( forma.nombre,   '- Nombre ',1,249);     
	    //validarCampo( forma.orden,    '- Orden ');
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
				 location.href='<c:url value="/grupo/ControllerGrupoFiltroEdit.do"/>';
				}		
			}	


	 

	  function cargaEspacios(){
			document.frmAux.cod_inst.value=document.frmNuevo.codInst.value;
			document.frmAux.cod_sede.value=document.frmNuevo.codSede.options[document.frmNuevo.codSede.selectedIndex].value;
			document.frmAux.cod_tipo_esp.value=document.frmNuevo.codTipoEspacio.options[document.frmNuevo.codTipoEspacio.selectedIndex].value;			
			document.frmAux.cmd.value=document.frmAux.cmd3.value;		
			document.frmAux.target="frame";
			document.frmAux.submit();
	}
	 
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
 	  	<table border="0"  bordercolor="#FFFFFF" width="100%">
			
      </table>
      
  <!-- FICHAS DE NAVEGACION -->	  
 <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		  
	         <tr style="height: 20px"><td>&nbsp;</td></tr>
		    <tr height="1">
			 <td width="10" bgcolor="#FFFFFF">&nbsp;</td>							
			 <td rowspan="2" bgcolor="#FFFFFF">				 
				 <img src='<c:url value="/etc/img/tabs/grupos_1.gif"/>' alt="Grupos"  height="26" border="0">
			  </td>
            </tr>
    </table>
	 
  <!-- LISTA DE RESULTADOS -->
   <table   align="center"  width="100%">
   <caption>Grupos - Filtro de Busqueda</caption>
     <tr>
      <td>
        <c:import url="/grupo/ControllerGrupoFiltroSave.do?"><c:param value="${paramsVO.FICHA_GRUPOS}" name="tipo" /></c:import>
	  </td>
	 </tr>
   </table>	
		 
		 	
   <!-- FORMUALARIO DE EDICION CREACION--> 		  
	<form method="post" name="frmNuevo"  action='<c:url value="/grupo/FiltroGuardar.jsp"/>' onsubmit="return false;">
        <input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_GRUPOS}"/>'>
        <input type="hidden" name="dato" value='<c:out value="${pageScope.dat}"/>'>
        <input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="codInst"   id="codInst"    value='<c:out value="${sessionScope.grupoVO.codInst}"/>'>
		
		
	  <table cellpadding="2" cellspacing="2"  width="100%">
	   <caption>Edición Grupos</caption>
	      <tr>
	      <td colspan="4"> 
	        <c:if test="${sessionScope.grupoVO.insertar==0}">
       			<input class='boton' name="cmd1" type="button" value="Actualizar" onClick="guardar()">
             </c:if>
             <c:if test="${sessionScope.grupoVO.insertar==1}">
      			<input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar()">
             </c:if>
			<!-- <input class='boton' name="cmd13" type="button" value="Nuevo" onClick="nuevo()"> -->
			<input class='boton' name="cmd13" type="button" value="Cancelar" onClick="cancelar()">
			
		 </td>
		</tr>			
		<tr><td width="80px"><font color="red">*</font>Sede</td>
		      <td width="120px">
			      <select name="codSede" id="codSede" style="width: 220px;" '<c:if test="${sessionScope.grupoVO.insertar==0}">' DISABLED '</c:if>'>
			      <option value='-99'>-- Seleccione uno --</option>
					<c:forEach begin="0" items="${requestScope.listaSedesEditar}" var="item">
						<option value="<c:out value="${item.codigo}"/>" <c:if test="${item.codigo==sessionScope.grupoVO.codSede}">selected</c:if>><c:out value="${item.nombre}"/></option>
					</c:forEach>
			      </select>
			  </td>
			  <td width="80px"><font color="red">*</font>Jornada</td>
			    <td width="120px">
				    <select name="codJorn" id="codJorn" style="width: 150px;"  '<c:if test="${sessionScope.grupoVO.insertar==0}">' DISABLED  '</c:if>'>
				    
				    <option value='-99'>-- Seleccione uno --</option>
					<c:forEach begin="0" items="${requestScope.listaJornadasEditar}" var="item">
						<option value="<c:out value="${item.codigo}"/>" <c:if test="${item.codigo==sessionScope.grupoVO.codJorn}">selected</c:if>><c:out value="${item.nombre}"/></option>
					</c:forEach>
					
				    </select>
			  </td>
		   </tr>
		   <tr><td width="80px"><font color="red">*</font>Metodología</td>
		      <td width="120px">
			      <select name="codMetodo" id="codMetodo" style="width: 150px;" '<c:if test="${sessionScope.grupoVO.insertar==0}">' DISABLED '</c:if>'>
			      <option value='-99'>-- Seleccione uno --</option>
					<c:forEach begin="0" items="${requestScope.listaMetodologiasEditar}" var="item">
						<option value="<c:out value="${item.codigo}"/>" <c:if test="${item.codigo==sessionScope.grupoVO.codMetodo}">selected</c:if>><c:out value="${item.nombre}"/></option>
					</c:forEach>
			      </select>
			  </td>
			  <td width="80px"><font color="red">*</font>Grado</td>
			    <td width="120px">
				    <select name="codGrado" id="codGrado" style="width: 160px;"  '<c:if test="${sessionScope.grupoVO.insertar==0}">' DISABLED  '</c:if>'>
				    
				    <option value='-99'>-- Todos --</option>
					<c:forEach begin="0" items="${requestScope.listaGradosEditar}" var="item">
						<option value="<c:out value="${item.codigo}"/>" <c:if test="${item.codigo==sessionScope.grupoVO.codGrado}">selected</c:if>><c:out value="${item.nombre}"/></option>
					</c:forEach>
					
				    </select>
			  </td>
		   </tr>	
		   
		   <tr>
			  <td width="100px"><font color="red">*</font>Código</td>
			    <td width="120px"><input type="text" name="codigo" id="codigo"  size="10" maxlength="25" value='<c:out value="${sessionScope.grupoVO.codigo}"/>'  readonly="true">
			  </td>
		   </tr>
		    <tr><td width="80px">Tipo de Espacio Físico</td>
		      <td width="120px">
			      <select name="codTipoEspacio" id="codTipoEspacio" style="width: 160px;" onchange="cargaEspacios()" >
			      <option value='-99'>-- Seleccione uno --</option>
			      <c:forEach begin="0" items="${requestScope.listaTiposEsp}" var="item">
						<option value="<c:out value="${item.codigo}"/>" <c:if test="${item.codigo==sessionScope.grupoVO.codTipoEspacio}">selected</c:if>><c:out value="${item.nombre}"/></option>
					</c:forEach>
					
			      </select>
			  </td>
			  <td width="80px">Espacio Físico</td>
			    <td width="120px">
				    <select name="codEspacio" id="codEspacio" style="width: 200px;"  >
				    <option value='-99'>-- Seleccione uno --</option>
				     <c:forEach begin="0" items="${requestScope.listaEspacios}" var="item">
						<option value="<c:out value="${item.codigo}"/>" <c:if test="${item.codigo==sessionScope.grupoVO.codEspacio}">selected</c:if>><c:out value="${item.nombre}"/></option>
					</c:forEach>
				    </select>
			  </td>
		   </tr>
		   
		   <tr><td width="80px" colspan="1">Director de Grupo</td>
		      <td width="120px">
			      <select name="docDirector" id="docDirector" style="width: 250px;">
			       <option value='-99'>-- Seleccione uno --</option>
				     <c:forEach begin="0" items="${requestScope.listaDirectores}" var="item">
						<option value="<c:out value="${item.codigo2}"/>" <c:if test="${item.codigo2==sessionScope.grupoVO.docDirector}">selected</c:if>><c:out value="${item.nombre}"/></option>
					</c:forEach>
			      </select>
			  </td>			  
		   </tr>
		   	
		   		   
		   <tr><td><font color="red">*</font>Nombre</td>
		       <td width="120px"><input type="text" name="nombre" id="nombre"  size="10" maxlength="25" value='<c:out value="${sessionScope.grupoVO.nombre}"/>' ></td>
		       <td>Cupo</td>
		       <td width="120px"><input type="text" name="cupo" id="cupo"  size="2" maxlength="2" value='<c:out value="${sessionScope.grupoVO.cupo}"/>' onkeypress="return validarNumeros(event)" ></td>
		   </tr>
		   <tr>
		   <td>Orden</td>
		       <td width="120px"><input type="text" name="orden" id="orden"  size="2" maxlength="2" value='<c:out value="${sessionScope.grupoVO.orden}"/>' onkeypress="return validarNumeros(event)" ></td> 
		   </tr>
		  
	  </table>	
    </form>
  </body>
  <script>
 
  </script> 
</html>