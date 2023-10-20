<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="filtroReportesVO" class="siges.gestionAdministrativa.RepPuestoEstudiante.vo.FiltroReportesVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.gestionAdministrativa.RepPuestoEstudiante.vo.ParamsVO" scope="page"/>
<html>
<head> 

<script languaje="text/javascript">
<!--

 	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Todos--","-9");
	}
	
	
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
		validarLista(forma.loc,'- Localidad', 1);
		validarLista(forma.inst,'- Institución', 1);
		validarLista(forma.sede,'- Sede', 1);
		validarLista(forma.jornd,'- Jornada', 1);
	
		 
	}// function hacerValidaciones_frmNuevo(forma){
  
  
  
  
  function nuevo(){
	 document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
	 document.frmNuevo.submit();
  }
	
	
	   function lanzar(tipo){				
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.cmd.value= 6;
				document.frmNuevo.action="<c:url value="/siges/gestionAdministrativa/repPuestoEstudiante.do"/>";
				document.frmNuevo.target = ""; 
			 	document.frmNuevo.submit();
			}
		
		
		function lanzar_(tipo){
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.cmd.value='Cancelar';
				document.frmNuevo.action="<c:url value="/siges/gestionAdministrativa/repPuestoEstudiante.do"/>";
				document.frmNuevo.target="";
				document.frmNuevo.submit();
			}	
 
			
	
	 
	 
	 

	function guardar(){
		if(validarForma(document.frmNuevo)){
			setNombres();
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();			
		}	
	}
	function cancelar(){
		if (confirm('¿Desea cancelar la generación de reportes?')){
				document.frmNuevo.cmd.value=document.frmNuevo.CANCELAR.value;
				parent.location.href='<c:url value="/siges/gestionAdministrativa/repPuestoEstudiante.do"/>';
		}
	}


	function setNombres(){
		document.frmNuevo.loc_nombre.value=document.frmNuevo.loc.options[document.frmNuevo.loc.selectedIndex].text
		document.frmNuevo.inst_nombre.value=document.frmNuevo.inst.options[document.frmNuevo.inst.selectedIndex].text
		document.frmNuevo.sede_nombre.value=document.frmNuevo.sede.options[document.frmNuevo.sede.selectedIndex].text
		document.frmNuevo.jornd_nombre.value=document.frmNuevo.jornd.options[document.frmNuevo.jornd.selectedIndex].text
		document.frmNuevo.metodo_nombre.value=document.frmNuevo.metodo.options[document.frmNuevo.metodo.selectedIndex].text
		document.frmNuevo.grado_nombre.value=document.frmNuevo.grado.options[document.frmNuevo.grado.selectedIndex].text
		document.frmNuevo.grupo_nombre.value=document.frmNuevo.grupo.options[document.frmNuevo.grupo.selectedIndex].text
		/*document.frmNuevo.per_nombre.value=document.frmNuevo.periodo.options[document.frmNuevo.periodo.selectedIndex].text
		document.frmNuevo.asigCodigos.value=document.frmNuevo.asig.value
		document.frmNuevo.asigNombres.value=document.frmNuevo.asig.options[document.frmNuevo.asig.selectedIndex].text
		if(document.frmNuevo.escala.disabled==false){
			document.frmNuevo.escala_nombre.value=document.frmNuevo.escala.options[document.frmNuevo.escala.selectedIndex].text
		}*/

		/*if(document.frmNuevo.conAreAsi_[0].checked){
			document.frmNuevo.conAreAsi.value=1;
		}else if(document.frmNuevo.conAreAsi_[1].checked){
			document.frmNuevo.conAreAsi.value=2;
		}

		if(document.frmNuevo.tipoReporte.value==2){	
			if(document.frmNuevo.conGraGru_[0].checked){
				document.frmNuevo.conGraGru.value=1;
			}else if(document.frmNuevo.conGraGru_[1].checked){
				document.frmNuevo.conGraGru.value=2;
			}
		}*/
		
	}




	function ajaxColegios(){ 
		  borrar_combo2(document.frmNuevo.inst);
		  borrarCombosColegio();
		  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
		  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.loc.value;
		  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_INST.value;
		  document.frmAjaxNuevo.submit();
		 
		}

	function ajaxSedes(){ 
		document.frmNuevo.inst.title = document.frmNuevo.inst.options[document.frmNuevo.inst.selectedIndex].text;
		  borrar_combo2(document.frmNuevo.sede);
		  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
		  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.inst.value;
		  document.frmAjaxNuevo.ajax[1].value = document.frmNuevo.vig.value;
		  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_SEDE.value;
		  document.frmAjaxNuevo.submit();
		 
		}
	
	 
 	function ajaxJornada(){ 
	   
	  borrar_combo2(document.frmNuevo.jornd);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value =  document.frmNuevo.inst.value;
	  document.frmAjaxNuevo.ajax[1].value =  document.frmNuevo.sede.value;
	  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_JORD.value;
	  document.frmAjaxNuevo.submit();

	}
	
	
	function ajaxMetodologia(){ 
	  borrar_combo2(document.frmNuevo.metodo);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.inst.value;
	  document.frmAjaxNuevo.ajax[1].value = document.frmNuevo.sede.value;
	  document.frmAjaxNuevo.ajax[2].value = document.frmNuevo.jornd.value;
	  document.frmAjaxNuevo.ajax[3].value = document.frmNuevo.vig.value;
	  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_METD.value;
	  document.frmAjaxNuevo.submit();
	 
	}
	 
	 
 
	 
	 
	  function ajaxGrado(){
	  borrar_combo2(document.frmNuevo.grado);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.inst.value;
	  document.frmAjaxNuevo.ajax[1].value = document.frmNuevo.sede.value;
      document.frmAjaxNuevo.ajax[2].value = document.frmNuevo.jornd.value;
      document.frmAjaxNuevo.ajax[3].value = document.frmNuevo.metodo.value;
      document.frmAjaxNuevo.ajax[4].value = document.frmNuevo.vig.value;
      document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_GRAD.value;
	  document.frmAjaxNuevo.submit();
	  
	 
	 
	}
	
	
   function ajaxGrupo(){
	 
	  borrar_combo2(document.frmNuevo.grupo);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.inst.value;
      document.frmAjaxNuevo.ajax[1].value = document.frmNuevo.sede.value;
      document.frmAjaxNuevo.ajax[2].value = document.frmNuevo.jornd.value;
      document.frmAjaxNuevo.ajax[3].value = document.frmNuevo.metodo.value;
      document.frmAjaxNuevo.ajax[4].value = document.frmNuevo.grado.value;
      document.frmAjaxNuevo.ajax[5].value = document.frmNuevo.vig.value;
      document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_GRUP.value;
	  document.frmAjaxNuevo.submit();
 
	 
   }


   function ajaxAsig(){
	  // alert(<c:out value="${sessionScope.filtroReportesVO.numAsig}"/>);
	  if(document.frmNuevo.metodo.value>0){
		  var cont=1;
		  if(document.frmNuevo.tipoReporte.value==5 && document.frmNuevo.docente.value=='-9'){
			  cont=0;
		  }
		  
		   if(cont==1){
			   if(document.frmNuevo.conAreAsi_[0].checked || document.frmNuevo.conAreAsi_[1].checked){
				var inst_=document.frmNuevo.inst.value;
				var metod_=document.frmNuevo.metodo.value;
				var grado_=document.frmNuevo.grado.value;
				var codsAsig_=document.frmNuevo.asigCodigos.value;
				var aresig_=0;
				if(document.frmNuevo.conAreAsi_[0].checked){
					aresig_=1;
				}else if(document.frmNuevo.conAreAsi_[1].checked){
					aresig_=2;
				}
				
				var tipoRep_ = document.frmNuevo.tipoReporte.value;
				var vig_ = document.frmNuevo.vig.value;
				var docente_ = document.frmNuevo.docente.value;
				
			    var aux=document.frmNuevo.actionPopup.value+'?cmd='+<c:out value="${paramsVO.CMD_AJAX_ASIG}"/>+'&tipo='+1+'&inst='+inst_+'&metod='+metod_+'&grado='+grado_+'&codsAsig='+codsAsig_+'&conAreAsig='+aresig_+'&tipoRep='+tipoRep_+'&vig='+vig_+'&docente='+docente_;
		 	
				remote = window.open(aux,'3','width=500,height=400,resizable=no,toolbar=no,directories=no,menubar=no,status=yes')
				document.frmNuevo.target = '3';
				remote.moveTo(200, 200);
				if (remote == null) remote.opener = window;
				
				remote.opener.name = "centro";
				remote.focus();
				document.frmNuevo.target = '_self'; 
			   }else{
					alert("Seleccione si desea el reporte por Áreas o Asignaturas");
				}
	
			}else{
				alert("Seleccione Docente");
			}
	  }else{
			alert("Seleccione Metodología");
		}
		
	   }


   function ajaxRango(){

		  borrar_combo2(document.frmNuevo.grupo);
		  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
		  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.inst.value;
	      document.frmAjaxNuevo.ajax[1].value = document.frmNuevo.sede.value;
	      document.frmAjaxNuevo.ajax[2].value = document.frmNuevo.jornd.value;
	      document.frmAjaxNuevo.ajax[3].value = document.frmNuevo.metodo.value;
	      document.frmAjaxNuevo.ajax[4].value = document.frmNuevo.grado.value;
	      document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_RANGO.value;
		  document.frmAjaxNuevo.submit();
		  
		/*var inst_=document.frmNuevo.inst.value;
		var sede_=document.frmNuevo.sede.value;
		var jornada_=document.frmNuevo.jornd.value;
		var metod_=document.frmNuevo.metodo.value;
		var grado_=document.frmNuevo.grado.value;
		var codsAsig_=document.frmNuevo.asigCodigos.value;
		var aresig_ = document.frmNuevo.conAreAsi.value;
	    var aux=document.frmNuevo.actionPopup.value+'?cmd='+<c:out value="${paramsVO.CMD_AJAX_RANGO}"/>+'&tipo='+1+'&inst='+inst_+'&metod='+metod_+'&grado='+grado_+'&codSede='+sede_+'&codJornada='+jornada_;
	
		remote = window.open(aux,'3','width=500,height=250,resizable=yes,toolbar=no,directories=no,menubar=no,status=yes')
		document.frmNuevo.target = '3';
		remote.moveTo(200, 200);
		if (remote == null) remote.opener = window;
		
		remote.opener.name = "centro";
		remote.focus();
		document.frmNuevo.target = '_self'; */
		
	   }

   function borrarCombosColegio(){
	   borrar_combo2(document.frmNuevo.sede);
	   borrar_combo2(document.frmNuevo.jornd);
	   borrar_combo2(document.frmNuevo.metodo);
	   borrar_combo2(document.frmNuevo.grado);
	   borrar_combo2(document.frmNuevo.grupo);
	   document.frmNuevo.asigNombres.value='';		
   }
   
   var OCULTO="none";
	var	VISIBLE="block";

 	function cambioAsignaturas(){
 	}

 	function conAreAsig(){
		//alert("entro cambio con asig areas")
 	 }
   	  
	 -->
	 
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
 	   	<form method="post" name="frmAjaxNuevo" action='<c:url value="/siges/gestionAdministrativa/repPuestoEstudianteAjax.do"/>'>
			<input type="hidden" name="tipo" value='<c:out value="${ paramsVO.FICHA_REPORTES}"/>'>
			<input type="hidden" name="cmd"  value='-1'>
			<input type="hidden" name="CMD_AJAX_MUN"  id="CMD_AJAX_MUN"  value='<c:out value="${paramsVO.CMD_AJAX_MUN}"/>'>
			<input type="hidden" name="CMD_AJAX_INST"  id="CMD_AJAX_INST"  value='<c:out value="${paramsVO.CMD_AJAX_INST}"/>'>
			<input type="hidden" name="CMD_AJAX_SEDE"  id="CMD_AJAX_SEDE"  value='<c:out value="${paramsVO.CMD_AJAX_SEDE}"/>'>
			<input type="hidden" name="CMD_AJAX_JORD"  id="CMD_AJAX_JORD"  value='<c:out value="${paramsVO.CMD_AJAX_JORD}"/>'>
			<input type="hidden" name="CMD_AJAX_METD"  id="CMD_AJAX_METD"  value='<c:out value="${paramsVO.CMD_AJAX_METD}"/>'>
			<input type="hidden" name="CMD_AJAX_GRUP"   id="CMD_AJAX_GRUP"   value='<c:out value="${paramsVO.CMD_AJAX_GRUP}"/>'>
			<input type="hidden" name="CMD_AJAX_GRAD"  id="CMD_AJAX_GRAD"  value='<c:out value="${paramsVO.CMD_AJAX_GRAD}"/>'>
			<input type="hidden" name="CMD_AJAX_RANGO"  id="CMD_AJAX_RANGO"  value='<c:out value="${paramsVO.CMD_AJAX_RANGO}"/>'>
			<c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach>
		</form>
	
		
   <!-- FORMUALARIO DE EDICION CREACION--> 		  
	<form method="post" name="frmNuevo"  action='<c:url value="/siges/gestionAdministrativa/RepPuestoEstudiante/Save.jsp"/>'>
        <input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_REPORTES}"/>'>
        <input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'> 
		<input type="hidden" name="CANCELAR" value='<c:out value="${paramsVO.CMD_CANCELAR}"/>'>
		<input type="hidden" name="actionPopup" value='<c:url value="/siges/gestionAdministrativa/reportesComparativosAjax.do"/>'>
		
		<input type="hidden" name="tipoReporte" value='6'>
		<input type="hidden" name="prov_nombre" value=''>
		<input type="hidden" name="loc_nombre" value=''>
		<input type="hidden" name="zona_nombre" value=''>
		<input type="hidden" name="inst_nombre" value=''>
		<input type="hidden" name="sede_nombre" value=''>
		<input type="hidden" name="jornd_nombre" value=''>
		<input type="hidden" name="metodo_nombre" value=''>
		<input type="hidden" name="grado_nombre" value=''>
		<input type="hidden" name="grupo_nombre" value=''>
		<input type="hidden" name="per_nombre" value=''>		
		<input type="hidden" name="conAreAsi" value=''>
		<input type="hidden" name="conGraGru" value='1'>
		<input type="hidden" name="escala_nombre" value=''>
		
		<input type="hidden" name="asigCodigos" value=''>
		<input type="hidden" name="asigNombres" value=''>
		
		
		<input type="hidden" name="dacompromediar" value=''>
		
		<input type="hidden" name="fechaProm" value='<c:out value="${sessionScope.filtroReportesVO.fechaProm}"/>'>
		<input type="hidden" name="fechaPromValida" value='<c:out value="${sessionScope.filtroReportesVO.fechaPromValida}"/>'>
		
		<input type="hidden" name="valorIni_" value='0'>
		<input type="hidden" name="valorFin_" value='100'>
		
		 <table cellpadding="0" cellspacing="1" border="0" ALIGN="CENTER" width="100%" >
		  <caption>Gestión Administrativa >> Reporte Liatado Inasistencia</caption>	         
		    <tr>						
			 <td rowspan="2" bgcolor="#FFFFFF">			      
				   <a href="javaScript:lanzar(1)"><img src='<c:url value="/etc/img/tabs/reportes/puestoEstudiante_0.gif"/>' border="0"  height="26" alt='Reporte puesto estudiante'></a>
				   <a href="javaScript:lanzar(2)"><img src='<c:url value="/etc/img/tabs/reportes/puestoAsig_0.gif"/>' border="0"  height="26" alt='Reporte puesto asignatura'></a>
				   <a href="javaScript:lanzar(3)"><img src='<c:url value="/etc/img/tabs/reportes/puestoArea_0.gif"/>' border="0"  height="26" alt='Reporte puesto área'></a>
				   <a href="javaScript:lanzar(4)"><img src='<c:url value="/etc/img/tabs/reportes/puestoMortalidad_0.gif"/>' border="0"  height="26" alt='Reporte mortalidad colegios'></a>
				   <a href="javaScript:lanzar(5)"><img src='<c:url value="/etc/img/tabs/reportes/puestoIndice_0.gif"/>' border="0"  height="26" alt='Reporte índice pérdida'></a>
				   <img src='<c:url value="/etc/img/tabs/reportes/ListaInas_1.gif"/>' border="0"  height="26" alt='Reporte listado Inasistencia'>		
				   <a href="javaScript:lanzar(7)"><img src='<c:url value="/etc/img/tabs/reportes/ResumenInas_0.gif"/>' border="0"  height="26" alt='Reporte resumen Inasistencia'></a>
		        </td>
            </tr>
          </table>
          
		 <table cellpadding="0" cellspacing="1" border="0" ALIGN="CENTER" width="100%" >		 	         
		    <tr>						
			 <td rowspan="2" bgcolor="#FFFFFF">			      
				<input class="boton" name="cmd1" type="button" value="Generar" onClick="guardar()">
        		<input class="boton" name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
		     </td>
            </tr>
          </table>
     
		<!-- REPORTE UNO -->
	<div id="rep1" style="display:">
		  <table border="0" align="center" width="100%" cellpadding="1" cellspacing="1">	
		  <caption class="Fila0" colspan="4" align="center"><b>Filtro Reporte</b></caption>
		    <tr><td style="width: 15%"><span class="style2" >*</span>Vigencia</td>
		 	    <td style="width: 35%"><select  name="vig" id="vig" style="width: 150px;" onchange="JavaScript:cambioAsignaturas();">
		 	        <option value="-9">-- Seleccione una--</option>
		 	         <c:forEach begin="0" items="${requestScope.listaVigencia}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroReportesVO.vig ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	      </select>
		 	    </td>
		 	   
		 	 </tr> 			
			<tr>
		 	   <td style="width: 15%"><span class="style2" >*</span>Localidad</td>
		 	    <td style="width: 35%"><select  name="loc" id="loc" style="width: 150px;" onchange="JavaScript: ajaxColegios();" <c:if test="${sessionScope.filtroReportesVO.hab_loc ==0}">DISABLED</c:if>>
		 	        <option value="-9">--Todas--</option>
		 	         <c:forEach begin="0" items="${requestScope.listaLocalidad}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroReportesVO.loc ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	      </select>
		 	    </td>
		 	 </tr> 
		 	  	
		 	<tr>
		 	    <td style="width: 15%"><span class="style2" >*</span>Institución</td>
		 	    <td style="width: 35%" ><select name="inst" id="inst" style="width: 290px;" onchange="ajaxSedes();" <c:if test="${sessionScope.filtroReportesVO.hab_inst ==0}">DISABLED</c:if> >
		 	        <option value="-9">-- Seleccione uno--</option>
		 	          <c:forEach begin="0" items="${requestScope.listaColegio}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" title="<c:out value="${item.nombre}"/>" <c:if test="${sessionScope.filtroReportesVO.inst ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	     
		 	 </tr> 
		 	 
		 	<tr><td style="width: 15%"><span class="style2" >*</span>Sede</td>
		 	    <td style="width: 35%"><select  name="sede" id="sede" style="width: 290px;" onchange="JavaScript: ajaxJornada();">
		 	        <option value="-9">-- Todos--</option>
		 	         <c:forEach begin="0" items="${requestScope.listaSede}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroReportesVO.sede ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	      </select>
		 	    </td>
		 	    <td style="width: 15%"><span class="style2" >*</span>Jornada</td>
		 	    <td style="width: 35%">
		 	    	<select name="jornd" id="jornd" style="width: 100px;" onchange="ajaxMetodologia();"  >
			 	        <option value="-9">-- Todos--</option>
			 	          <c:forEach begin="0" items="${requestScope.listaJord}" var="item">
					     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroReportesVO.jornd ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
	                     </c:forEach>
		 	        </select>
		 	    </td>
		 	 </tr>   
		 	 <tr><td><span class="style2" ></span>Metodología</td>
		 	    <td><select name="metodo" id="metodo" onchange="ajaxGrado();" style="width: 200px;">
		 	        <option value="-9">-- Todos--</option>
		 	            <c:forEach begin="0" items="${requestScope.listaMetodo}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroReportesVO.metodo  ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	    <td>Grado</td>
		 	    <td><select name="grado" id="grado" onchange="ajaxGrupo();" style="width: 180px;">
		 	        <option value="-9">-- Todos--</option>
		 	              <c:forEach begin="0" items="${requestScope.listaGrado}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroReportesVO.grado == item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	 </tr>   
		 	  <tr><td>Grupo</td>
		 	    <td><select  name="grupo" id="grupo" style="width: 100px;">
		 	        <option value="-9">-- Todos--</option>
		 	         <c:forEach begin="0" items="${requestScope.listaGrupo}" var="item">
				      <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroReportesVO.grupo == item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	    
		 	    </tr>   
		 	  
		 	 
		 	<tr style="display:none"><td><iframe name="frameAjaxNuevo" id="frameAjaxNuevo"></iframe></td></tr>
		</table>
		</div>	
		
	
		
		
		
	</form>
</body>
<script> 
   
</script>
</html>
