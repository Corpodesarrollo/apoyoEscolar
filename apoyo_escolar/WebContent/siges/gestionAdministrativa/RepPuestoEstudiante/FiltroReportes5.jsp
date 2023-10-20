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
				document.frmNuevo.action="<c:url value="/boletines/ControllerBoletinFiltroEdit.do"/>";
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
				parent.location.href='<c:url value="/siges/gestionAdministrativa/reportesComparativos.do"/>';
		}
	}


function setNombres(){
		
		document.frmNuevo.loc_nombre.value=document.frmNuevo.loc.options[document.frmNuevo.loc.selectedIndex].text
		
		
		document.frmNuevo.jornd_nombre.value=document.frmNuevo.jornd.options[document.frmNuevo.jornd.selectedIndex].text
		document.frmNuevo.metodo_nombre.value=document.frmNuevo.metodo.options[document.frmNuevo.metodo.selectedIndex].text
		
		document.frmNuevo.per_nombre.value=document.frmNuevo.periodo.options[document.frmNuevo.periodo.selectedIndex].text
			
		
	}

	function ajaxProv(){ 
		  borrar_combo2(document.frmNuevo.loc);
		  borrar_combo2(document.frmNuevo.inst);
		  borrarCombosColegio()
		  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
		  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.prov.value;
		  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_MUN.value;
		  document.frmAjaxNuevo.submit();
		  if(document.frmNuevo.tipoReporte.value==2){		  	
	  			document.frmNuevo.conGraGru_[0].disabled=true;
	  			document.frmNuevo.conGraGru_[1].disabled=true;
	  			document.frmNuevo.conGraGru.value=1;
	  			document.frmNuevo.conGraGru_[0].checked=true;
	  			document.frmNuevo.conGraGru_[1].checked=false;
	      }
		 
		}

	function ajaxMunicipios(){ 
		  borrar_combo2(document.frmNuevo.loc);
		  borrar_combo2(document.frmNuevo.inst);
		  borrarCombosColegio();
		  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
		  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.prov.value;
		  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_MUN.value;
		  document.frmAjaxNuevo.submit();

		  if(document.frmNuevo.tipoReporte.value==2){		  	
	  			document.frmNuevo.conGraGru_[0].disabled=true;
	  			document.frmNuevo.conGraGru_[1].disabled=true;
	  			document.frmNuevo.conGraGru.value=1;
	  			document.frmNuevo.conGraGru_[0].checked=true;
	  			document.frmNuevo.conGraGru_[1].checked=false;
	      }
		 
		}

	function ajaxColegios(){ 
		  borrar_combo2(document.frmNuevo.inst);
		  borrarCombosColegio();
		  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
		  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.loc.value;
		  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_INST.value;
		  document.frmAjaxNuevo.submit();
		  if(document.frmNuevo.tipoReporte.value==2){		  	
	  			document.frmNuevo.conGraGru_[0].disabled=true;
	  			document.frmNuevo.conGraGru_[1].disabled=true;
	  			document.frmNuevo.conGraGru.value=1;
	  			document.frmNuevo.conGraGru_[0].checked=true;
	  			document.frmNuevo.conGraGru_[1].checked=false;
	  		}
		 
		}

	function ajaxSedes(){ 
		document.frmNuevo.inst.title = document.frmNuevo.inst.options[document.frmNuevo.inst.selectedIndex].text;
		  borrar_combo2(document.frmNuevo.sede);
		  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
		  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.inst.value;
		  document.frmAjaxNuevo.ajax[1].value = document.frmNuevo.zona.value;
		  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_SEDE.value;
		  document.frmAjaxNuevo.submit();
		  //alert("<c:out value="${sessionScope.filtroReportesVO.fechaProm}"/>");
		  if(document.frmNuevo.tipoReporte.value==2){		  	
	  			document.frmNuevo.conGraGru_[0].disabled=true;
	  			document.frmNuevo.conGraGru_[1].disabled=true;
	  			document.frmNuevo.conGraGru.value=1;
	  			document.frmNuevo.conGraGru_[0].checked=true;
	  			document.frmNuevo.conGraGru_[1].checked=false;
	  	  }
		}
	
	 
 	function ajaxJornada(){ 
	   
	  borrar_combo2(document.frmNuevo.jornd);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value =  document.frmNuevo.inst.value;
	  document.frmAjaxNuevo.ajax[1].value =  document.frmNuevo.sede.value;
	  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_JORD.value;
	  document.frmAjaxNuevo.submit();

	  if(document.frmNuevo.tipoReporte.value==2){		  	
	  			document.frmNuevo.conGraGru_[0].disabled=true;
	  			document.frmNuevo.conGraGru_[1].disabled=true;
	  			document.frmNuevo.conGraGru.value=1;
	  			document.frmNuevo.conGraGru_[0].checked=true;
	  			document.frmNuevo.conGraGru_[1].checked=false;
	  }
	 
	}
	
	
	function ajaxMetodologia(){ 
	  /*borrar_combo2(document.frmNuevo.metodo);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.inst.value;
	  document.frmAjaxNuevo.ajax[1].value = document.frmNuevo.sede.value;
	  document.frmAjaxNuevo.ajax[2].value = document.frmNuevo.jornd.value;
	  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_METD.value;
	  document.frmAjaxNuevo.submit();

	  	if(document.frmNuevo.tipoReporte.value==2){
		  	if(document.frmNuevo.inst.value>0 && document.frmNuevo.sede.value>0 && document.frmNuevo.jornd.value>0){
	  			document.frmNuevo.conGraGru_[0].disabled=false;
	  			document.frmNuevo.conGraGru_[1].disabled=false;
	  			document.frmNuevo.conGraGru.value=1;
	  			document.frmNuevo.conGraGru_[0].checked=true;
	  			document.frmNuevo.conGraGru_[1].checked=false;
		  	}else{
		  		document.frmNuevo.conGraGru_[0].disabled=true;
	  			document.frmNuevo.conGraGru_[1].disabled=true;
	  			document.frmNuevo.conGraGru.value=1;
	  			document.frmNuevo.conGraGru_[0].checked=true;
	  			document.frmNuevo.conGraGru_[1].checked=false;
			}
		}*/
	  
	 
	}
	 
	 
 
	 
	 
	  function ajaxGrado(){
		  /*borrar_combo2(document.frmNuevo.grado);
		  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
		  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.inst.value;
		  document.frmAjaxNuevo.ajax[1].value = document.frmNuevo.sede.value;
	      document.frmAjaxNuevo.ajax[2].value = document.frmNuevo.jornd.value;
	      document.frmAjaxNuevo.ajax[3].value = document.frmNuevo.metodo.value;
	      document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_GRAD.value;
		  document.frmAjaxNuevo.submit();*/
		}
	
	
   function ajaxGrupo(){
	 
	  borrar_combo2(document.frmNuevo.grupo);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.inst.value;
      document.frmAjaxNuevo.ajax[1].value = document.frmNuevo.sede.value;
      document.frmAjaxNuevo.ajax[2].value = document.frmNuevo.jornd.value;
      document.frmAjaxNuevo.ajax[3].value = document.frmNuevo.metodo.value;
      document.frmAjaxNuevo.ajax[4].value = document.frmNuevo.grado.value;
      document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_GRUP.value;
	  document.frmAjaxNuevo.submit();
 
	  if(document.frmNuevo.tipoReporte.value==2){
		  	if(document.frmNuevo.inst.value>0 && document.frmNuevo.sede.value>0 && document.frmNuevo.jornd.value>0){
		  		if(document.frmNuevo.grado.value>0){
		  			document.frmNuevo.conGraGru_[0].disabled=true;
		  			document.frmNuevo.conGraGru_[1].disabled=true;
		  			document.frmNuevo.conGraGru.value=2;
		  			document.frmNuevo.conGraGru_[0].checked=false;
		  			document.frmNuevo.conGraGru_[1].checked=true;
		  		}else
		  		if(document.frmNuevo.grado.value<0){
		  			document.frmNuevo.conGraGru_[0].disabled=false;
		  			document.frmNuevo.conGraGru_[1].disabled=false;
		  			document.frmNuevo.conGraGru.value=2;
		  			document.frmNuevo.conGraGru_[0].checked=false;
		  			document.frmNuevo.conGraGru_[1].checked=true;
		  		}
		  	}else{
		  		document.frmNuevo.conGraGru_[0].disabled=true;
	  			document.frmNuevo.conGraGru_[1].disabled=true;
	  			document.frmNuevo.conGraGru.value=1;
	  			document.frmNuevo.conGraGru_[0].checked=true;
	  			document.frmNuevo.conGraGru_[1].checked=false;
			 }
		}
   }


   function ajaxAsig(){
	  // alert(<c:out value="${sessionScope.filtroReportesVO.numAsig}"/>);
	  if(document.frmNuevo.loc.value>0){
		  var cont=1;
			  
				var inst_=document.frmNuevo.loc.value;				
				var codsAsig_=document.frmNuevo.asigCodigos.value;
				var aresig_=0;				
				var tipoRep_ = document.frmNuevo.tipoReporte.value;
				var vig_ = document.frmNuevo.vig.value;			
				
			    var aux=document.frmNuevo.actionPopup.value+'?cmd='+<c:out value="${paramsVO.CMD_AJAX_COLS}"/>+'&tipo='+4+'&loc='+inst_+'&codsAsig='+codsAsig_+'&conAreAsig='+aresig_+'&tipoRep='+tipoRep_+'&vig='+vig_;
		 	
				remote = window.open(aux,'3','width=500,height=400,resizable=no,toolbar=no,directories=no,menubar=no,status=yes')
				document.frmNuevo.target = '3';
				//remote.moveTo(200, 200);
				if (remote == null) remote.opener = window;
				
				remote.opener.name = "centro";
				remote.focus();
				document.frmNuevo.target = '_self'; 
			  
	  }else{
			alert("Seleccione Localidad");
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
 	   	<form method="post" name="frmAjaxNuevo" value='<c:url value="/siges/gestionAdministrativa/repPuestoEstudianteAjax.do"/>'>
			<input type="hidden" name="tipo" value='<c:out value="${ paramsVO.FICHA_REPORTES5}"/>'>
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
        <input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_REPORTES5}"/>'>
        <input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'> 
		<input type="hidden" name="CANCELAR" value='<c:out value="${paramsVO.CMD_CANCELAR}"/>'>
		<input type="hidden" name="actionPopup" value='<c:url value="/siges/gestionAdministrativa/repPuestoEstudianteAjax.do"/>'>
		
		<input type="hidden" name="tipoReporte" value='5'>
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
		
		<input type="hidden" name="instCodigos" value=''>
		<input type="hidden" name="gradosCodigos" value=''>
		
		<input type="hidden" name="dacompromediar" value=''>
		
		<input type="hidden" name="fechaProm" value='<c:out value="${sessionScope.filtroReportesVO.fechaProm}"/>'>
		<input type="hidden" name="fechaPromValida" value='<c:out value="${sessionScope.filtroReportesVO.fechaPromValida}"/>'>
		
		<input type="hidden" name="valorIni_" value='0'>
		<input type="hidden" name="valorFin_" value='100'>
		
		<input type="hidden" name="inst" value='-99'>
		
		 <table cellpadding="0" cellspacing="1" border="0" ALIGN="CENTER" width="100%" >
		  <caption>Gestión Administrativa >> Reporte Índice Pérdida</caption>	         
		    <tr>						
			 <td rowspan="2" bgcolor="#FFFFFF">
			 	   <a href="javaScript:lanzar(1)"><img src='<c:url value="/etc/img/tabs/reportes/puestoEstudiante_0.gif"/>' border="0"  height="26" alt='Reporte puesto estudiante'></a>			      
				   <a href="javaScript:lanzar(2)"><img src='<c:url value="/etc/img/tabs/reportes/puestoAsig_0.gif"/>' border="0"  height="26" alt='Reporte puesto asignatura'></a>
				   <a href="javaScript:lanzar(3)"><img src='<c:url value="/etc/img/tabs/reportes/puestoArea_0.gif"/>' border="0"  height="26" alt='Reporte puesto área'></a>			   
				   <a href="javaScript:lanzar(4)"><img src='<c:url value="/etc/img/tabs/reportes/puestoMortalidad_0.gif"/>' border="0"  height="26" alt='Reporte mortalidad colegios'></a>
				   <img src='<c:url value="/etc/img/tabs/reportes/puestoIndice_1.gif"/>' border="0"  height="26" alt='Reporte índice pérdida'>
				   <a href="javaScript:lanzar(6)"><img src='<c:url value="/etc/img/tabs/reportes/ListaInas_0.gif"/>' border="0"  height="26" alt='Reporte listado Inasistencia'></a>
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
		 	    <td style="width: 15%">Jornada</td>
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
		 	   
		 	   <td><span class="style2" >*</span>Periodo</td>
		 	    <td><select name="periodo" id="periodo" style="width: 100px;">
		 	        <option value="-9">--Todos--</option>
		 	        <c:forEach begin="0" items="${requestScope.listaPeriodo}" var="item">
				      <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroReportesVO.periodo == item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	 
		 	 </tr> 
		 	 
		 	 <tr>
		 	 <td>
		 	 	 Reporte generado por:
					 </td>
		 	 	<td>
		 	 		<input type="radio" name="conAreAsi_"  value="1" <c:if test="${sessionScope.filtroReportesVO.tipoReporte==2}">checked</c:if> onclick="conAreAsig();"> Áreas&nbsp;&nbsp;
		 	 		<input type="radio" name="conAreAsi_"  value="2" <c:if test="${sessionScope.filtroReportesVO.tipoReporte==2}">checked</c:if> checked onclick="conAreAsig();"> Asignaturas 
		 	 	</td>
		 	 	 <td><span class="style2" >*</span>Criterio Eval.</td>
		 	    <td><select name="criterioEval" id="criterioEval" style="width: 100px;">
		 	        <option value="-9">--Todos--</option>
		 	         <option value="1">Básico</option>
		 	          <option value="2">Bajo</option>
		 	        
		 	        </select>
		 	    </td>
		 	 
		 	 	
		 	 </tr>  
		 	 
		 	  <tr>
		 	    <td>Colegios</td>
		 	    <td><a href='javaScript:ajaxAsig();'>
								<img border='0' src='<c:url value="/etc/img/todas.gif"/>' width='18' height='18' alt="Seleccionar Colegios">
					 </a>
		 	    </td>
		 	 </tr>   
		 	 <tr><!-- <td colspan="1" >Áreas(s) / Asignatura(s)</td> -->
		 	    <td colspan="4">
		 	    <textarea name="instNombres" id="instNombres"  cols="80" row="2" value='<c:out value="${sessionScope.filtroReportesVO.instNombres}"/>' readonly="true"></textarea>	
		 	    	<!-- <input type='text' name='asigNombres' id='asigNombres' size="80" maxlength="500" readonly="true" value='<c:out value="${sessionScope.filtrob.id}"/>'> -->
		 	    	
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
