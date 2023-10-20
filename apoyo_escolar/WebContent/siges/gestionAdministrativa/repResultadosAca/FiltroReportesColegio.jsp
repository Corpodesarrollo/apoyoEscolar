<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="filtroRepResultadosVO" class="siges.gestionAdministrativa.repResultadosAca.vo.FiltroRepResultadosVO" scope="session"/>
		          
<jsp:useBean  id="paramsVO"   class="siges.gestionAdministrativa.repResultadosAca.vo.ParamsVO" scope="page"/>
<html>
<head> 

<script languaje="text/javascript">
<!--

var imagen1;
//imagen1=new Image
// imagen1.src='<c:url value="/etc/img/spinner.gif"/>';
var ScrollSpeed = 100;  // milliseconds between scrolls
var ScrollChars = 1;    // chars scrolled per time period
var stop = 1;
var flag = 0;
var flagSpinner = 0;
var msg2 = "";
var msg = "";
var decimas = 0  
var segundos = 0  
var minutos = 0  
var ValorCrono = ""  ;
CronoEjecutandose = true;  
CronoID= null;

	

 	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno","-99");
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
		//validarSeleccion(forma.tipoReporte, "- Tipo Reporte");	
        //validarLista(forma.filVigencia, "- Vigencia",1);	
		validarLista(forma.periodo, "- Período",1);	  
	  
	 }// 
  
  
  
  
  function nuevo(){
	 document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
	 document.frmNuevo.submit();
  }
	
	
	  	 
	 

	function guardar(){
		if(validarForma(document.frmNuevo)){	
			setNombres();					
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			
			document.frmNuevo.target="ModuloCompVigAnt";
			document.frmNuevo.action='<c:url value="/siges/gestionAdministrativa/repResultadosAca/Save2.jsp"/>';
			window.open("","ModuloCompVigAnt","width=800,height=500,menubar=yes,scrollbars=1");
			document.frmNuevo.submit();
		}	
	}
	function cancelar(){
		if (confirm('¿Desea cancelar la generación de reportes?')){
			document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
			document.frmNuevo.filVigencia.value='-99';
			document.frmNuevo.periodo.value='-99';
			
			document.frmNuevo.submit();
		}
	}


	function setNombres(){
		
		//document.frmNuevo.loc_nombre.value=document.frmNuevo.loc.options[document.frmNuevo.loc.selectedIndex].text
		//document.frmNuevo.inst_nombre.value=document.frmNuevo.inst.options[document.frmNuevo.inst.selectedIndex].text
		
		
	}

	function ajaxColegios(){ 
		  borrar_combo2(document.frmNuevo.inst);
		  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
		  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.loc.value;
		  document.frmAjaxNuevo.ajax[1].value = document.frmNuevo.tipoCol.value;
		  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_INST.value;
		  document.frmAjaxNuevo.submit();
		 
		}

	function ajaxPeriodo(){ 
		  borrar_combo2(document.frmNuevo.periodo);
		  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
		  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.inst.value;
		  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_PER.value;
		  document.frmAjaxNuevo.submit();
		 
		}

	function ajaxSedes(){ 
		  borrar_combo2(document.frmNuevo.sede);
		  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
		  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.inst.value;
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
	  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.sede.value;
	  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_METD.value;
	  document.frmAjaxNuevo.submit();
	 
	}
	 
	 
 
	 
	 
	  function ajaxGrado(){
	  borrar_combo2(document.frmNuevo.grado);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.inst.value;
      document.frmAjaxNuevo.ajax[1].value = document.frmNuevo.sede.value;
      document.frmAjaxNuevo.ajax[2].value = document.frmNuevo.jornd.value;
      document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_GRAD.value;
	  document.frmAjaxNuevo.submit();
	 
	}
	
	
   function ajaxGrupo(){
	 
	  borrar_combo2(document.frmNuevo.grupo);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.inst.value;
      document.frmAjaxNuevo.ajax[1].value = document.frmNuevo.sede.value;
      document.frmAjaxNuevo.ajax[2].value = document.frmNuevo.jornd.value;      
      document.frmAjaxNuevo.ajax[3].value = document.frmNuevo.grado.value;
      document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_GRUP.value;
	  document.frmAjaxNuevo.submit();
 
	
   }

   function lanzar(tipo){
		document.frmAjaxNuevo.tipo.value=tipo;
		document.frmAjaxNuevo.action="<c:url value="/siges/gestionAdministrativa/repResultadosAca.do"/>";
		document.frmAjaxNuevo.target="";
		document.frmAjaxNuevo.submit();
	}

  	  
	 -->
	 
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
 	   	<form method="post" name="frmAjaxNuevo" action='<c:url value="/siges/gestionAdministrativa/repResultadosAcaAjax.do"/>'>
			<input type="hidden" name="tipo" value='<c:out value="${ paramsVO.FICHA_REPORTES_COLEGIO}"/>'>
			<input type="hidden" name="cmd"  value='-1'>
			<input type="hidden" name="CMD_AJAX_INST"  id="CMD_AJAX_INST"  value='<c:out value="${paramsVO.CMD_AJAX_INST}"/>'>
			<input type="hidden" name="CMD_AJAX_SEDE"  id="CMD_AJAX_PER"  value='<c:out value="${paramsVO.CMD_AJAX_PER}"/>'>
			<input type="hidden" name="CMD_AJAX_SEDE"  id="CMD_AJAX_SEDE"  value='<c:out value="${paramsVO.CMD_AJAX_SEDE}"/>'>
			<input type="hidden" name="CMD_AJAX_JORD"  id="CMD_AJAX_JORD"  value='<c:out value="${paramsVO.CMD_AJAX_JORD}"/>'>
			<input type="hidden" name="CMD_AJAX_METD"  id="CMD_AJAX_METD"  value='<c:out value="${paramsVO.CMD_AJAX_METD}"/>'>
			<input type="hidden" name="CMD_AJAX_GRUP"   id="CMD_AJAX_GRUP"   value='<c:out value="${paramsVO.CMD_AJAX_GRUP}"/>'>
			<input type="hidden" name="CMD_AJAX_GRAD"  id="CMD_AJAX_GRAD"  value='<c:out value="${paramsVO.CMD_AJAX_GRAD}"/>'>
			<c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach>
		</form>
	
		 
   <!-- FORMULARIO DE EDICION CREACION--> 		  
	<form method="post" name="frmNuevo"  action='<c:url value="/siges/gestionAdministrativa/repResultadosAca/Save2.jsp"/>'>
        <input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_REPORTES_COLEGIO}"/>'>
        <input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'> 
		<input type="hidden" name="CANCELAR" value='<c:out value="${paramsVO.CMD_CANCELAR}"/>'>
		<input type="hidden" name="actionPopup" value='<c:url value="/siges/gestionAdministrativa/repResultadosAcaAjax.do"/>'>
		<input type="hidden" name="tipoCol" value='1'>
		<input type="hidden" name="loc_nombre" value=''>
		<input type="hidden" name="zona_nombre" value=''>
		<input type="hidden" name="inst_nombre" value=''>
		<input type="hidden" name="sede_nombre" value=''>
		<input type="hidden" name="jornd_nombre" value=''>
		<input type="hidden" name="metodo_nombre" value=''>
		<input type="hidden" name="grado_nombre" value=''>
		<input type="hidden" name="grupo_nombre" value=''>
		<input type="hidden" name="escala_nombre" value=''>
		
		<input type="hidden" name="asigCodigos" value=''>
		
		<input type="hidden" name="tipoReporte" value='1'>
		
		<table cellpadding="0" cellspacing="1" border="0" ALIGN="CENTER" width="100%" >
		  <caption>Gestión Administrativa >> Resultados academicos</caption>	         
		    <tr>						
		     <td rowspan="1" bgcolor="#FFFFFF">			      
				   <a href="javaScript:lanzar(1)"><img src='<c:url value="/etc/img/tabs/resultadosAca_2.gif"/>' border="0"  height="26" alt='Reportes Carnés'></a>
		        </td>
            </tr>
          </table>
          
		 <table cellpadding="1" cellspacing="1" border="0" ALIGN="CENTER" width="100%" >	
		 <caption colspan="4" align="center"><b>Filtro Reporte</b></caption>			 	         
		    <tr>						
			 <td rowspan="2" bgcolor="#FFFFFF">			      
				<input class="boton" name="cmd1" type="button" value="Generar" onClick="guardar()">
        		<input class="boton" name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
		     </td>
            </tr>
          </table>    
		
		<!-- REPORTE UNO -->
	
		  <table border="0" align="center" width="100%" cellpadding="1" cellspacing="1">		 	 
		 	<tr><td style="width: 10%"><span class="style2" >*</span>Período</td>
		 	    <td style="width: 35%">
		 	    <select  name="periodo" id="periodo" style="width: 120px;">
		 	        <option value="-99">-- Seleccione uno--</option>
		 	         <c:forEach begin="0" items="${requestScope.listaPeriodo}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroRepResultadosVO.periodo ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	      </select>
		 	    </td>
		 	   
		 	 </tr>   
		 	  
		 	  <tr>
		 	  
		 	 </tr>  
		 	 
		 	
		 	 
		 	<tr style="display:none"><td><iframe name="frameAjaxNuevo" id="frameAjaxNuevo"></iframe></td></tr>
		</table>
	</form>
</body>
<script> 
   
</script>
</html>