<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="filtroReportesVO" class="siges.gestionAdministrativa.comparativos.vo.FiltroReportesVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.gestionAdministrativa.comparativos.vo.ParamsVO" scope="page"/>
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
		validarLista(forma.tipoReporte,'- Tipo de reporte', 1);
		//validarLista(forma.ordenReporte,'- Orden de reporte', 1);
		if(forma.tipoReporte.value==1){
			validarLista(forma.periodo,'- Período', 1);
			validarLista(forma.metodo,'- Metodologia', 1);
		}else if(forma.tipoReporte.value==2){
			validarLista(forma.metodo,'- Metodologia', 1);
			validarLista(forma.periodo,'- Período', 1);
		}else if(forma.tipoReporte.value==3){
			validarLista(forma.metodo,'- Metodologia', 1);
		}else if(forma.tipoReporte.value==4){
			validarLista(forma.metodo,'- Metodologia', 1);
			validarLista(forma.periodo,'- Período', 1);
			//validarFloat(forma.valorIni, '- Valor Inicial','<c:out value="${sessionScope.filtroReportesVO.valorIni}"/>' ,'<c:out value="${sessionScope.filtroReportesVO.valorFin}"/>');
			//validarFloat(forma.valorFin, '- Valor Final','<c:out value="${sessionScope.filtroReportesVO.valorIni}"/>' ,'<c:out value="${sessionScope.filtroReportesVO.valorFin}"/>');
			if((trim(forma.valorIni.value).length > 0) && (trim(forma.valorFin.value).length > 0)){
				validarFloat(forma.valorIni, '- Valor Inicial',forma.valorIni_.value ,forma.valorFin_.value);
				validarFloat(forma.valorFin, '- Valor Final',forma.valorIni_.value ,forma.valorFin_.value);
			}else{
				validarCampo(forma.valorIni, "- Valor Inicial", 1, 5)
				validarCampo(forma.valorFin, "- Valor Final", 1, 5)
			}
			//validarCampo(forma.asigNombres, "- Áreas/Asignaturas", 1, 999)
			//alert("INI: "+<c:out value="${sessionScope.filtroReportesVO.valorIni}"/> +"FIN: "+<c:out value="${sessionScope.filtroReportesVO.valorFin}"/>);
			//alert("INI: "+forma.valorIni_.value +"FIN: "+forma.valorFin.value);
			//forma.valorFin.value='5.0';
			//alert("INI2: "+forma.valorIni_.value +"FIN2: "+forma.valorFin.value);
			//if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'>=2){
			if(forma.escala.disabled==false){
				validarLista(forma.escala,'- Escala', 1);
			}
		}	  
	}// function hacerValidaciones_frmNuevo(forma){
  
  
  
  
  function nuevo(){
	 document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
	 document.frmNuevo.submit();
  }
	
	
	   function lanzar(tipo){				
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.cmd.value= -1;
				document.frmNuevo.action="<c:url value="/boletines/ControllerBoletinFiltroEdit.do"/>";
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
			if(trim(document.frmNuevo.asigCodigos.value)!=''){
			if(document.frmNuevo.fechaPromValida.value==0){
				document.frmNuevo.dacompromediar.value=1;
			}else {
			//alert(<c:out value="${sessionScope.filtroReportesVO.fechaPromValida}"/>);		
			var msjFechaProm="";		
			
				msjFechaProm="La última actualización de promedio por grupos se realizó el <c:out value="${sessionScope.filtroReportesVO.fechaProm}"/>. ¿Desea realizar actualización para este reporte?";
				//if(document.frmNuevo.prov.value>=0){
				//	var entidad=document.frmNuevo.prov.options[document.frmNuevo.prov.selectedIndex].text;
					//msjFechaProm="La última actualización de promedio por grupos para la provincia "+entidad+" se realizó el "+document.frmNuevo.fechaProm.value+". ¿Desea realizar actualización para este reporte?";
					if(document.frmNuevo.loc.value>=0){
						entidad=document.frmNuevo.loc.options[document.frmNuevo.loc.selectedIndex].text;
						msjFechaProm="La última actualización de promedio por grupos para la localidad "+entidad+" se realizó el "+document.frmNuevo.fechaProm.value+". ¿Desea realizar actualización para este reporte?";
						if(document.frmNuevo.inst.value>=0){
							entidad=document.frmNuevo.inst.options[document.frmNuevo.inst.selectedIndex].text;
							msjFechaProm="La última actualización de promedio por grupos para el colegio "+entidad+" se realizó el "+document.frmNuevo.fechaProm.value+". ¿Desea realizar actualización para este reporte?";
						}
					}
				//}		
			}
				
				setNombres();
				//alert("promediar:"+document.frmNuevo.dacompromediar.value);
				if(document.frmNuevo.dacompromediar.value!=1){
					if (confirm(msjFechaProm)){
						document.frmNuevo.dacompromediar.value=1;
					}else{
						document.frmNuevo.dacompromediar.value=0;
					}
				}		
				document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
				document.frmNuevo.submit();
			}else{
				//if(document.frmNuevo.conAreAsi_[0].checked){
					//alert("Seleccione por lo menos una Área, verifique esta información por favor.");
				//}else{
					alert("Seleccione por lo menos una Asignatura, verifique esta información por favor.");
				//}
			}
		}	
	}
	function cancelar(){
		if (confirm('¿Desea cancelar la generación de reportes?')){
				document.frmNuevo.cmd.value=document.frmNuevo.CANCELAR.value;
				parent.location.href='<c:url value="/siges/gestionAdministrativa/reportesComparativos.do"/>';
		}
	}


	function setNombres(){
		//document.frmNuevo.prov_nombre.value=document.frmNuevo.prov.options[document.frmNuevo.prov.selectedIndex].text
		document.frmNuevo.loc_nombre.value=document.frmNuevo.loc.options[document.frmNuevo.loc.selectedIndex].text
		document.frmNuevo.zona_nombre.value=document.frmNuevo.zona.options[document.frmNuevo.zona.selectedIndex].text
		document.frmNuevo.inst_nombre.value=document.frmNuevo.inst.options[document.frmNuevo.inst.selectedIndex].text
		document.frmNuevo.sede_nombre.value=document.frmNuevo.sede.options[document.frmNuevo.sede.selectedIndex].text
		document.frmNuevo.jornd_nombre.value=document.frmNuevo.jornd.options[document.frmNuevo.jornd.selectedIndex].text
		document.frmNuevo.metodo_nombre.value=document.frmNuevo.metodo.options[document.frmNuevo.metodo.selectedIndex].text
		document.frmNuevo.grado_nombre.value=document.frmNuevo.grado.options[document.frmNuevo.grado.selectedIndex].text
		document.frmNuevo.grupo_nombre.value=document.frmNuevo.grupo.options[document.frmNuevo.grupo.selectedIndex].text
		document.frmNuevo.per_nombre.value=document.frmNuevo.periodo.options[document.frmNuevo.periodo.selectedIndex].text
		if(document.frmNuevo.escala.disabled==false){
			document.frmNuevo.escala_nombre.value=document.frmNuevo.escala.options[document.frmNuevo.escala.selectedIndex].text
			//alert("ESCALA "+document.frmNuevo.escala_nombre.value);
		}

		//if(document.frmNuevo.conAreAsi_[0].checked){
			//document.frmNuevo.conAreAsi.value=1;
		//}else if(document.frmNuevo.conAreAsi_[1].checked){
			document.frmNuevo.conAreAsi.value=2;
		//}

			if(document.frmNuevo.tipoReporte.value==2){	
				if(document.frmNuevo.conGraGru_[0].checked){
					document.frmNuevo.conGraGru.value=1;
				}else if(document.frmNuevo.conGraGru_[1].checked){
					document.frmNuevo.conGraGru.value=2;
				}
			}
		
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
	  borrar_combo2(document.frmNuevo.metodo);
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
		}
	  
	 
	}
	 
	 

	 
	 
	  function ajaxGrado(){
	  borrar_combo2(document.frmNuevo.grado);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.inst.value;
	  document.frmAjaxNuevo.ajax[1].value = document.frmNuevo.sede.value;
    document.frmAjaxNuevo.ajax[2].value = document.frmNuevo.jornd.value;
    document.frmAjaxNuevo.ajax[3].value = document.frmNuevo.metodo.value;
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
	  if(document.frmNuevo.metodo.value>0){
	   //if(document.frmNuevo.conAreAsi_[0].checked || document.frmNuevo.conAreAsi_[1].checked){
		var inst_=document.frmNuevo.inst.value;
		var metod_=document.frmNuevo.metodo.value;
		var grado_=document.frmNuevo.grado.value;
		var codsAsig_=document.frmNuevo.asigCodigos.value;
		var aresig_=2;
		/*if(document.frmNuevo.conAreAsi_[0].checked){
			aresig_=1;
		}else if(document.frmNuevo.conAreAsi_[1].checked){
			aresig_=2;
		}*/
		
		var tipoRep_ = document.frmNuevo.tipoReporte.value;
		var vig_ = document.frmNuevo.vig.value;
		
	    var aux=document.frmNuevo.actionPopup.value+'?cmd='+<c:out value="${paramsVO.CMD_AJAX_ASIG}"/>+'&tipo='+1+'&inst='+inst_+'&metod='+metod_+'&grado='+grado_+'&codsAsig='+codsAsig_+'&conAreAsig='+aresig_+'&tipoRep='+tipoRep_+'&vig='+vig_;
 	
		remote = window.open(aux,'3','width=500,height=400,resizable=no,toolbar=no,directories=no,menubar=no,status=yes')
		document.frmNuevo.target = '3';
		remote.moveTo(200, 200);
		if (remote == null) remote.opener = window;
		
		remote.opener.name = "centro";
		remote.focus();
		document.frmNuevo.target = '_self'; 
	   //}else{
		///	alert("Seleccione si desea el reporte por Áreas o Asignaturas");
		//}
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
   	function ajaxReporte(){		 
		//alert("validar tipo de reporte: "+document.frmNuevo.tipoReporte.value);
		 if(document.frmNuevo.tipoReporte.value>0){
			  document.getElementById('rep1').style.display=VISIBLE;
			  if(document.frmNuevo.tipoReporte.value==1){
				  document.frmNuevo.grupo.disabled=false; 
				  document.frmNuevo.periodo.disabled=false;
				  document.frmNuevo.asigNombres.rows='2';
				  document.getElementById('rep4').style.display=OCULTO;
				  document.getElementById('rep2').style.display=OCULTO;
				  document.frmNuevo.asigCodigos.value='';
				  document.frmNuevo.asigNombres.value='';
			  }else if(document.frmNuevo.tipoReporte.value==2){
				  document.frmNuevo.grupo.disabled=true; 
				  document.frmNuevo.asigNombres.rows='1';
				  document.getElementById('rep4').style.display=OCULTO;
				  document.getElementById('rep2').style.display=VISIBLE;
				  document.frmNuevo.conGraGru_[0].disabled=true;
				  document.frmNuevo.conGraGru_[1].disabled=true;
				  document.frmNuevo.asigCodigos.value='';
				  document.frmNuevo.asigNombres.value='';

				  //
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
					//
			  }else if(document.frmNuevo.tipoReporte.value==3){
				  document.frmNuevo.periodo.disabled=true;
				  document.frmNuevo.grupo.disabled=false;
				  document.frmNuevo.asigNombres.rows='1'; 
				  document.getElementById('rep4').style.display=OCULTO;
				  document.getElementById('rep2').style.display=OCULTO;
				  document.frmNuevo.asigCodigos.value='';
				  document.frmNuevo.asigNombres.value='';
			  }else if(document.frmNuevo.tipoReporte.value==4){
				  document.getElementById('rep4').style.display=VISIBLE;
				  document.frmNuevo.grupo.disabled=true; 
				  document.frmNuevo.periodo.disabled=false;
				  document.frmNuevo.asigNombres.rows='1';
				  document.getElementById('rep2').style.display=OCULTO;
				  var msj="";
				  if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==1){
					  msj="Escala: Numérica; Valor mínimo: "+<c:out value='${sessionScope.filtroReportesVO.valorFin}'/>+"; Valor máximo: "+<c:out value='${sessionScope.filtroReportesVO.valorFin}'/>;
					  document.frmNuevo.escala.disabled=true;
				  } else
					  if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==2)
						  msj="Escala: Conceptual - Rango entre 0% y 100%";
					  else
					  if('<c:out value="${sessionScope.filtroReportesVO.tipoEscala}"/>'==3)
						  msj="Escala: MEN - Rango entre 0% y 100%";
				  document.getElementById("txtmsg").innerHTML = msj;
				  document.frmNuevo.asigCodigos.value='';
				  document.frmNuevo.asigNombres.value='';
				  
			  }
			  //document.getElementById('rep2').style.display=OCULTO;	  
			  //document.getElementById('rep3').style.display=OCULTO;
			  //document.getElementById('rep4').style.display=OCULTO;
		  }else{	  
		  if(document.frmNuevo.tipoReporte.value==-9){
			  //document.getElementById('rep4').style.display=OCULTO;
			  document.getElementById('rep1').style.display=OCULTO;	 
			  document.getElementById('rep4').style.display=OCULTO;
			  document.getElementById('rep2').style.display=OCULTO; 
			  //document.getElementById('rep2').style.display=OCULTO;
			 // document.getElementById('rep3').style.display=OCULTO;
		  }	 
		  }  
 	}

 	function cambioAsignaturas(){
 	}

 	function conAreAsig(){
		alert("entro cambio con asig areas")
 	 }
   	  
	 -->
	 
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
 	   	<form method="post" name="frmAjaxNuevo" action='<c:url value="/siges/gestionAdministrativa/reportesComparativosAjax.do"/>'>
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
	<form method="post" name="frmNuevo"  action='<c:url value="/siges/gestionAdministrativa/comparativos/Save.jsp"/>'>
        <input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_REPORTES}"/>'>
        <input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'> 
		<input type="hidden" name="CANCELAR" value='<c:out value="${paramsVO.CMD_CANCELAR}"/>'>
		<input type="hidden" name="actionPopup" value='<c:url value="/siges/gestionAdministrativa/reportesComparativosAjax.do"/>'>
		
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
		
		<input type="hidden" name="dacompromediar" value=''>
		
		<input type="hidden" name="fechaProm" value='<c:out value="${sessionScope.filtroReportesVO.fechaProm}"/>'>
		<input type="hidden" name="fechaPromValida" value='<c:out value="${sessionScope.filtroReportesVO.fechaPromValida}"/>'>
		
		<input type="hidden" name="valorIni_" value='0'>
		<input type="hidden" name="valorFin_" value='100'>
		
		 <table cellpadding="0" cellspacing="1" border="0" ALIGN="CENTER" width="100%" >
		  <caption>Gestión Administrativa >> Reportes Comparativos</caption>	         
		    <tr>						
			 <td rowspan="2" bgcolor="#FFFFFF">			      
				   <img src='<c:url value="/etc/img/tabs/comparativos_1.gif"/>' border="0"  height="26" alt='Reportes Comparativos'>
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
     
		<table border="0" align="center" width="98%" cellpadding="2" cellspacing="2">
			<caption class="Fila0" colspan="4" align="center"><b>Tipo Reporte</b></caption>
			<tr>
			 <td style="width: 15%"><span class="style2" >*</span>Tipo de Reporte</td>
		 	    <td style="width: 35%"><select name="tipoReporte" id="tipoReporte" style="width: 300px;" onchange="ajaxReporte();"  >
		 	        <option value="-9">-- Seleccione uno --</option>
		 	          <c:forEach begin="0" items="${requestScope.listaReportes}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroReportesVO.tipoReporte ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>	
		 	    <!--  <td style="width: 15%"><span class="style2" >*</span>Ordenado por</td>
		 	    <td style="width: 35%"><select name="ordenReporte" id="ordenReporte" style="width: 100px;" onchange="ajaxReporte();"  >
		 	        <option value="-99">-- Seleccione uno --</option>
		 	          <c:forEach begin="0" items="${requestScope.listaOrden}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroReportesVO.tipoReporte ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>	 -->		
			</tr>
		</table>
		<!-- REPORTE UNO -->
	<div id="rep1" style="display: none">
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
		 	   <td style="width: 15%">Localidad</td>
		 	    <td style="width: 35%"><select  name="loc" id="loc" style="width: 150px;" onchange="JavaScript: ajaxColegios();" <c:if test="${sessionScope.filtroReportesVO.hab_loc ==0}">DISABLED</c:if>>
		 	        <option value="-9">--Todas--</option>
		 	         <c:forEach begin="0" items="${requestScope.listaLocalidad}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroReportesVO.loc ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	      </select>
		 	    </td>
		 	 </tr> 
		 	  	
		 	<tr>
		 	    <td style="width: 15%">Institución</td>
		 	    <td style="width: 35%" ><select name="inst" id="inst" style="width: 290px;" onchange="ajaxSedes();" <c:if test="${sessionScope.filtroReportesVO.hab_inst ==0}">DISABLED</c:if> >
		 	        <option value="-9">-- Todos--</option>
		 	          <c:forEach begin="0" items="${requestScope.listaColegio}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" title="<c:out value="${item.nombre}"/>" <c:if test="${sessionScope.filtroReportesVO.inst ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	    <td style="width: 15%">Zona</td>
		 	    <td style="width: 35%"><select name="zona" id="zona" style="width: 15opx;" onchange="ajaxSedes();">
		 	        <option value="-9">-- Todos--</option>
		 	          <c:forEach begin="0" items="${requestScope.listaZonas}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroReportesVO.zona ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td> 
		 	 </tr> 
		 	 
		 	<tr><td style="width: 15%">Sede</td>
		 	    <td style="width: 35%"><select  name="sede" id="sede" style="width: 290px;" onchange="JavaScript: ajaxJornada();">
		 	        <option value="-9">-- Todos--</option>
		 	         <c:forEach begin="0" items="${requestScope.listaSede}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroReportesVO.sede ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	      </select>
		 	    </td>
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
		 	 <tr><td><span class="style2" >*</span>Metodología</td>
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
		 	    <td><span class="style2" >*</span>Periodo</td>
		 	    <td><select name="periodo" id="periodo" style="width: 100px;">
		 	        <option value="-9">-- Seleccione uno--</option>
		 	        <c:forEach begin="0" items="${requestScope.listaPeriodo}" var="item">
				      <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroReportesVO.periodo == item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	 </tr>  
		 	 <tr>
		 	 	<!-- <td>
		 	 		<input type="radio" name="conAreAsi_"  value="1" <c:if test="${sessionScope.filtroReportesVO.tipoReporte==2}">checked</c:if> onchange="conAreAsig();"> Áreas
		 	 	</td> -->
		 	 	<td colspan="2">
		 	 		<!-- <input type="radio" name="conAreAsi_"  value="2" <c:if test="${sessionScope.filtroReportesVO.tipoReporte==2}">checked</c:if> checked onchange="conAreAsig();"> Asignaturas &nbsp;&nbsp; -->
		 	 		Asignaturas &nbsp;&nbsp; 
		 	 		<a href='javaScript:ajaxAsig();'>
								<img border='0' src='<c:url value="/etc/img/todas.gif"/>' width='18' height='18' alt="Adicionar Asignaturas">
					 </a>
		 	 	</td>
		 	 	<td>
		 	 	 
					 </td>
		 	 </tr>
		 	 
		 	  <tr><!-- <td colspan="1" >Áreas(s) / Asignatura(s)</td> -->
		 	    <td colspan="4">
		 	    <textarea name="asigNombres" id="asigNombres"  cols="78" row="2" value='<c:out value="${sessionScope.filtroReportesVO.asigNombres}"/>'></textarea>	
		 	    	<!-- <input type='text' name='asigNombres' id='asigNombres' size="80" maxlength="500" readonly="true" value='<c:out value="${sessionScope.filtrob.id}"/>'> -->
		 	    	
		 	    </td>
		 	   
		 	 </tr>  
		 	 
		 	<tr style="display:none"><td><iframe name="frameAjaxNuevo" id="frameAjaxNuevo"></iframe></td></tr>
		</table>
		</div>	
		
		<div id="rep2" style="display: none">
		<table border="0" align="left" width="30%" cellpadding="1" cellspacing="1">	
			<tr>
		 	 	<td>
		 	 		<input type="radio" name="conGraGru_"  value="1" checked> Grados
		 	 	</td>
		 	 	<td>
		 	 		<input type="radio" name="conGraGru_"  value="2"> Grupos &nbsp;&nbsp;		 	 		
		 	 	</td>
		 	 	<td>
		 	 	 
					 </td>
		 	 </tr>
		</table>
		</div>
		
		<div id="rep4" style="display: none">
		<table border="0" align="left" width="80%" cellpadding="1" cellspacing="1">	
		<tr >
			<td colspan="6">
				<span align = "left"  id="txtmsg"   name="txtmsg" style="font-weight:bold;font-size:10pt;color: #FF6666"> </span><span align = "left"  id="barraCargar" name="barraCargar" style="font-weight:bold;font-size:9pt;color: #FF6666"> </span>
			</td>
		</tr>
			<tr><td colspan="1" ><span class="style2" >*</span>Valor Inicial</td> 
			    <td colspan="1">
		 	      <input type="text" name="valorIni" id="valorIni"  size="4" value='<c:out value="${sessionScope.filtroReportesVO.valorIni}"/>'></textarea>	 	    	
		 	    </td>
		 	    <td colspan="1" ><span class="style2" >*</span>Valor Final</td>
		 	    <td colspan="1">
		 	    <input type="text" name="valorFin" id="valorFin"  size="4" value='<c:out value="${sessionScope.filtroReportesVO.valorFin}"/>'></textarea>	 	    	
		 	    </td>		 	 
		 	    <td colspan="1"><span class="style2" >*</span>Escala</td>
		 	    <td colspan="1">
		 	    <select name="escala" id="escala"  style="width: 150px;">
		 	        <option value="-9">-- Seleccione uno--</option>
		 	         <c:forEach begin="0" items="${requestScope.tipoEval.escala}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${requestScope.escala  ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    	 
		 	    </td>
		 	   
		 	 </tr>  
		</table>
		</div>
		
	</form>
</body>
<script> 
   
</script>
</html>
