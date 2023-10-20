<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="filtroRepCarnesVO" class="siges.gestionAdministrativa.repCarnes.vo.FiltroRepCarnesVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.gestionAdministrativa.repCarnes.vo.ParamsVO" scope="page"/>
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
function init(){stop = 1;flagSpinner = 0;}
function Run(){ stop = 0;   flagSpinner=0;    SetupTicker();}
function end(){stop = 1; DetenerCrono(); flagSpinner=0;} 
function DetenerCrono (){if(CronoEjecutandose)
                              clearTimeout(CronoID);
                              CronoEjecutandose = false;
                             } 
                             
                             
                             
function RunTicker() {
 
  if(stop == 0){ 
 
   CronoID = window.setTimeout('RunTicker()',ScrollSpeed);
  
   document.getElementById("txtmsg").innerHTML = "GENERANDO";
   
   document.getElementById("barraCargar").innerHTML = msg2;
   
   flag++;
   
   if(flag == 6) flag=0;
   
   msg2 = msg.substring(0,flag);
   decimas++;  
	 if ( decimas > 9 ){decimas = 0;segundos++;  
		 if ( segundos > 59 ){segundos = 0;minutos++;  
			 if ( minutos > 99 ){alert('Fin de la cuenta');DetenerCrono();return true;  
			 }  
		 }  
	 }  
	 ValorCrono = (minutos < 10) ? "0" + minutos : minutos  
	 ValorCrono += (segundos < 10) ? ":0" + segundos : ":" + segundos  
	 ValorCrono += ":" + decimas   
	 CronoEjecutandose = true;  
	 return true  
 }
}

 

	
	
	    
function SetupTicker() { 
  flag = 0;  
  msg = "... .";
  RunTicker();
}
	

 	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Todos","-99");
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
		validarSeleccion(forma.tipoReporte, "- Tipo Reporte");	
		validarLista(forma.loc, "- Localidad",1);	
	  
	  
	}// function hacerValidaciones_frmNuevo(forma){
  
  
  
  
  function nuevo(){
	 document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
	 document.frmNuevo.submit();
  }
	
	
	  	 
	 

	function guardar(){
		if(document.getElementById('rep1')){
			document.getElementById('rep1').style.display="none";
		}
		if(validarForma(document.frmNuevo)){	
			//setNombres();		
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			ScrollChars = 1;    // chars scrolled per time period
			stop = 0;
			flag = 0;
			msg2 = "";
			decimas = 0  
			segundos = 0  
			minutos = 0  
			ValorCrono = ""  ;
			Run();
			document.frmNuevo.submit();
		}	
	}
	function cancelar(){
		if (confirm('¿Desea cancelar la generación de reportes?')){
			document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
			document.frmNuevo.loc.value='-99';
			document.frmNuevo.inst.value='-99';
			document.frmNuevo.sede.value='-99';
			document.frmNuevo.jornd.value='-99';
			document.frmNuevo.grado.value='-99';
			document.frmNuevo.grupo.value='-99';
			//document.frmNuevo.tipoReporte[0].checked=false;
			//document.frmNuevo.tipoReporte[1].checked=false;
			//document.frmNuevo.tipoReporte[2].checked=false;
			//document.frmNuevo.tipoReporte.value='-99';
			document.frmNuevo.submit();
		}
	}


	function setNombres(){
		
		document.frmNuevo.loc_nombre.value=document.frmNuevo.loc.options[document.frmNuevo.loc.selectedIndex].text
		document.frmNuevo.zona_nombre.value=document.frmNuevo.zona.options[document.frmNuevo.zona.selectedIndex].text
		document.frmNuevo.inst_nombre.value=document.frmNuevo.inst.options[document.frmNuevo.inst.selectedIndex].text
		document.frmNuevo.sede_nombre.value=document.frmNuevo.sede.options[document.frmNuevo.sede.selectedIndex].text
		document.frmNuevo.jornd_nombre.value=document.frmNuevo.jornd.options[document.frmNuevo.jornd.selectedIndex].text
		document.frmNuevo.metodo_nombre.value=document.frmNuevo.metodo.options[document.frmNuevo.metodo.selectedIndex].text
		document.frmNuevo.grado_nombre.value=document.frmNuevo.grado.options[document.frmNuevo.grado.selectedIndex].text
		document.frmNuevo.grupo_nombre.value=document.frmNuevo.grupo.options[document.frmNuevo.grupo.selectedIndex].text
		//document.frmNuevo.escala_nombre.value=document.frmNuevo.escala_nombre.value
		
	}

	function ajaxColegios(){ 
		  borrar_combo2(document.frmNuevo.inst);
		  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
		  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.loc.value;
		  document.frmAjaxNuevo.ajax[1].value = document.frmNuevo.tipoCol.value;
		  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_INST.value;
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




  
   	  
	 -->
	 
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
 	   	<form method="post" name="frmAjaxNuevo" action='<c:url value="/siges/gestionAdministrativa/repCarnesAjax.do"/>'>
			<input type="hidden" name="tipo" value='<c:out value="${ paramsVO.FICHA_REPORTES}"/>'>
			<input type="hidden" name="cmd"  value='-1'>
			<input type="hidden" name="CMD_AJAX_INST"  id="CMD_AJAX_INST"  value='<c:out value="${paramsVO.CMD_AJAX_INST}"/>'>
			<input type="hidden" name="CMD_AJAX_SEDE"  id="CMD_AJAX_SEDE"  value='<c:out value="${paramsVO.CMD_AJAX_SEDE}"/>'>
			<input type="hidden" name="CMD_AJAX_JORD"  id="CMD_AJAX_JORD"  value='<c:out value="${paramsVO.CMD_AJAX_JORD}"/>'>
			<input type="hidden" name="CMD_AJAX_METD"  id="CMD_AJAX_METD"  value='<c:out value="${paramsVO.CMD_AJAX_METD}"/>'>
			<input type="hidden" name="CMD_AJAX_GRUP"   id="CMD_AJAX_GRUP"   value='<c:out value="${paramsVO.CMD_AJAX_GRUP}"/>'>
			<input type="hidden" name="CMD_AJAX_GRAD"  id="CMD_AJAX_GRAD"  value='<c:out value="${paramsVO.CMD_AJAX_GRAD}"/>'>
			<c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach>
		</form>
	
		 
   <!-- FORMUALARIO DE EDICION CREACION--> 		  
	<form method="post" name="frmNuevo"  action='<c:url value="/siges/gestionAdministrativa/repCarnes/Save.jsp"/>'>
        <input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_REPORTES}"/>'>
        <input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'> 
		<input type="hidden" name="CANCELAR" value='<c:out value="${paramsVO.CMD_CANCELAR}"/>'>
		<input type="hidden" name="actionPopup" value='<c:url value="/siges/gestionAdministrativa/repCarnesAjax.do"/>'>
		
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
		
		<table cellpadding="0" cellspacing="1" border="0" ALIGN="CENTER" width="100%" >
		  <caption>Gestión Administrativa >> Reportes Carnés</caption>	         
		    <tr>						
			 <td rowspan="1" bgcolor="#FFFFFF">			      
				   <img src='<c:url value="/etc/img/tabs/rep_carnes_1.gif"/>' border="0"  height="26" alt='Reportes Carnés'>
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
		  	
			<tr>
		 	   <td style="width: 15%"><span class="style2" >*</span>Localidad</td>
		 	    <td style="width: 35%"><select  name="loc" id="loc" style="width: 190px;" onchange="JavaScript: ajaxColegios();" <c:if test="${sessionScope.filtroRepCarnesVO.hab_loc ==0}">DISABLED</c:if>>
		 	        <option value="-99">-- Seleccione Uno--</option>
		 	         <c:forEach begin="0" items="${requestScope.listaLocalidad}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroRepCarnesVO.loc ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	      </select>
		 	    </td>
		 	    <td style="width: 15%">Tipo Colegio</td>
		 	    <td style="width: 35%"><select  name="tipoCol" id="tipoCol" style="width: 100px;" onchange="JavaScript: ajaxColegios();" <c:if test="${sessionScope.filtroRepCarnesVO.hab_loc ==0}">DISABLED</c:if>>
		 	        <option value="-99">-- Todos --</option>
		 	         <c:forEach begin="0" items="${requestScope.listaTiposCol}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroRepCarnesVO.tipoCol ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	      </select>
		 	    </td>
		 	 </tr> 
		 	  	
		 	<tr>
		 	    <td style="width: 15%">Colegio</td>
		 	    <td style="width: 35%" colspan="2"><select name="inst" id="inst" style="width: 350px;" onchange="ajaxSedes();" <c:if test="${sessionScope.filtroRepCarnesVO.hab_inst ==0}">DISABLED</c:if> >
		 	        <option value="-99">-- Todos--</option>
		 	          <c:forEach begin="0" items="${requestScope.listaColegio}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroRepCarnesVO.inst ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	     <!-- <td style="width: 15%">Zona</td>
		 	    <td style="width: 35%"><select name="zona" id="zona" style="width: 15opx;" onchange="ajaxMetodologia();"  >
		 	        <option value="-99">-- Todos--</option>
		 	          <c:forEach begin="0" items="${requestScope.listaZonas}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroRepCarnesVO.jornd ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td> -->
		 	 </tr> 
		 	 
		 	<tr><td style="width: 15%">Sede</td>
		 	    <td style="width: 35%"><select  name="sede" id="sede" style="width: 250px;" onchange="JavaScript: ajaxJornada();">
		 	        <option value="-99">-- Todos--</option>
		 	         <c:forEach begin="0" items="${requestScope.listaSede}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroRepCarnesVO.sede ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	      </select>
		 	    </td>
		 	    <td style="width: 15%">Jornada</td>
		 	    <td style="width: 35%">
		 	    	<select name="jornd" id="jornd" style="width: 100px;" onchange="ajaxGrado();"  >
			 	        <option value="-99">-- Todos--</option>
			 	          <c:forEach begin="0" items="${requestScope.listaJord}" var="item">
					     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroRepCarnesVO.jornd ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
	                     </c:forEach>
		 	        </select>
		 	    </td>
		 	 </tr>   
		 	 <tr>
		 	    <td>Grado</td>
		 	    <td><select name="grado" id="grado" onchange="ajaxGrupo();" style="width: 150px;">
		 	        <option value="-99">-- Todos--</option>
		 	              <c:forEach begin="0" items="${requestScope.listaGrado}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroRepCarnesVO.grado == item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	    <td>Grupo</td>
		 	    <td><select  name="grupo" id="grupo" style="width: 100px;">
		 	        <option value="-99">-- Todos--</option>
		 	         <c:forEach begin="0" items="${requestScope.listaGrupo}" var="item">
				      <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroRepCarnesVO.grupo == item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	 </tr>   
		 	  <tr>
		 	  
		 	 </tr>  
		 	 
		 	
		 	 
		 	<tr style="display:none"><td><iframe name="frameAjaxNuevo" id="frameAjaxNuevo"></iframe></td></tr>
		</table>
	
	 <table border="0" align="center" width="80%" cellpadding="1" cellspacing="1">
	 	<tr>
			<td colspan="2" align="center">
						<fieldset>
							<legend><b><span class="style2" >*</span>Seleccione el reporte que desea generar </b></legend>
							<table border="0" align="center" width="90%" cellpadding="1" cellspacing="0">
								 <tr>												
									<td colspan="0" align="left" width="60%">
										Consolidado Estudiantes SIN Foto
									</td>
									<td colspan="0" align="left" width="40%">
										<input type="radio" name="tipoReporte" value="1" <c:if test="${sessionScope.filtroRepCarnesVO.tipoReporte==1}">checked</c:if>>
									</td>
									</tr>
									<tr>
									<td colspan="0" align="left" width="60%">	
										Consolidado Estudiantes CON Foto&nbsp;&nbsp;
									</td>
									<td colspan="0" align="left" width="40%">	
										<input type="radio" name="tipoReporte"  value="2" <c:if test="${sessionScope.filtroRepCarnesVO.tipoReporte==2}">checked</c:if>>
									</td>
									</tr>
									<tr>
									<td colspan="0" align="left" width="50%">	
										Listado Estudiantes SIN Foto
									</td>
									<td colspan="0" align="left" width="50%">	
										<input type="radio" name="tipoReporte"  value="3" <c:if test="${sessionScope.filtroRepCarnesVO.tipoReporte==3}">checked</c:if>>
									</td>
								 </tr>
								 <tr>
									<td colspan="0" align="left" width="50%">	
										Listado Estudiantes CON Foto
									</td>
									<td colspan="0" align="left" width="50%">	
										<input type="radio" name="tipoReporte"  value="4" <c:if test="${sessionScope.filtroRepCarnesVO.tipoReporte==4}">checked</c:if>>
									</td>
								 </tr>
							</table>
						</fieldset>					
					</td>
		</tr>
		</table>
		
		
		
		<table border="0" align="center" bordercolor="#000000" width="100%" cellpadding="2" cellspacing="0">
			 <tr style="height:30px;"><td align="right" width="54%" colspan="3">
		      <span align = "center"  id="txtmsg" name="txtmsg" style="font-weight:bold;font-size:10pt;color: blue"> </span>
		      </td>
		       <td align="left" width="46%">
			       <span align = "center"  id="barraCargar" name="barraCargar" style="font-weight:bold;font-size:10pt;color: blue"> </span>
			   	   <!-- <span align = "center"  id="barraCargar" name="barraCargar" > </span> -->
		       </td></tr>
		 </table>
		<br>
		</br>
		<c:if test="${requestScope.resultadoReportes.generado==true}">
		<div id="rep1" style="display:block ;">
			<table border="0" align="center" width="95%" cellpadding="0" cellspacing="0" id="tabla" name="tabla" style="display:;">
			 	<caption>Resultado</caption>
					<tr>
						<th><a target="_blank" href='<c:url value="/${resultadoReportes.ruta}"><c:param name="tipo" value="${resultadoReportes.tipo}"/></c:url>'><img src='<c:url value="/etc/img/zip.gif"/>' border="0"></a></th>
					</tr>
					<tr>
						<th>Pulse en el ícono para descargar el reporte</th>
					</tr>
			</table>
			</div>
		</c:if>
	</form>
</body>
<script> 
   
</script>
</html>