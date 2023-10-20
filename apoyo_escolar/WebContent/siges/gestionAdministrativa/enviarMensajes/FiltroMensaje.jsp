<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="filtroMensajeVO" class="siges.gestionAdministrativa.enviarMensajes.vo.FiltroEnviarVO" scope="session"/>
<jsp:useBean  id="mensajesVO" class="siges.gestionAdministrativa.enviarMensajes.vo.MensajesVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.gestionAdministrativa.enviarMensajes.vo.ParamsVO" scope="page"/>
<html>
<head> 
<style type="text/css">@import url('<c:url value="/etc/css/calendar-win2k-1.css"/>');</style>
<script type="text/javascript" src="<c:url value="/etc/js/calendar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/lang/calendar-es.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/calendar-setup.js"/>"></script>

<script languaje="text/javascript">
<!--
var listPerfiles = new Array();
var listLocal = new Array();
var listInst = new Array();
var listSede = new Array();
var listJord = new Array();

var tipo_perfil = 1;
var tipo_local = 2;
var tipo_inst = 3;
var tipo_sede = 4;
var tipo_jord = 5;  
var indexPerfil = 0;
var num = 1;


 <c:forEach begin="0" items="${requestScope.listaPerfiles}" var="item" varStatus="st">
	listPerfiles[<c:out value="${st.count - 1 }"/> ]  = <c:out value="${item.codigo }"/>+"|"+ '<c:out value="${item.nombre }"/>'; 
 </c:forEach>
 
  <c:forEach begin="0" items="${requestScope.listaLocal}" var="item" varStatus="st">
	listLocal[<c:out value="${st.count - 1 }"/> ]  = <c:out value="${item.codigo }"/>+"|"+ '<c:out value="${item.nombre }"/>'; 
 </c:forEach>
 
  
    
  <c:forEach begin="0" items="${requestScope.listaInst}" var="item" varStatus="st">
	listInst[<c:out value="${st.count - 1 }"/> ]  = '<c:out value="${item.codigo }"/>' + '|' + '<c:out value="${item.nombre }"/>'; 
 </c:forEach>
 
  <c:forEach begin="0" items="${requestScope.listaSedess}" var="item" varStatus="st">
	listSede[<c:out value="${st.count - 1 }"/> ]  = '<c:out value="${item.codigo }"/>' + '|' + '<c:out value="${item.nombre }"/>'; 
</c:forEach>

 <c:forEach begin="0" items="${requestScope.listaJorddd}" var="item" varStatus="st">
	listJord[<c:out value="${st.count - 1 }"/> ]  = '<c:out value="${item.codigo }"/>' + '|' + '<c:out value="${item.nombre }"/>'; 
</c:forEach> 
 




 function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Todos","-99");
 }
 
 function borrar_combo33(combo){  
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

 
function validarRangoFechasPeriodo2(fecha1,fecha2,msgError,funcion) {
  var firstDateArray, secondDateArray;
	firstDateArray = fecha1.value.split("/");
	secondDateArray = fecha2.value.split("/");
	
	//año
	if(firstDateArray[2] > secondDateArray[2]){
	   if (_campoError == null) {
            _campoError = fecha2;
        }
        appendErrorMessage(msgError)
        return false;
	}else if(firstDateArray[2] != secondDateArray[2]) 	return true;
	
	
	
	//mes
	if(firstDateArray[1] > secondDateArray[1]){
	   if (_campoError == null) {
            _campoError = fecha2;
        }
        appendErrorMessage(msgError)
        return false;
	}else if(firstDateArray[1] != secondDateArray[1]) 	return true;
	
	
	// dia
	if(firstDateArray[0]  >= secondDateArray[0]){
	   if (_campoError == null) {
            _campoError = fecha2;
        }
        appendErrorMessage(msgError)
        return false;
	} else	return true;
   
}
 

function validarNumeros2(e) { 
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true; 
    patron = /^\d/;
    te = String.fromCharCode(tecla); 
    return patron.test(te); 
} 



	function hacerValidaciones_frmNuevo(forma){ 
	 
	 document.frmNuevo.perfiles.value = getStringLista(listPerfiles , tipo_perfil);
	 document.frmNuevo.localidades.value = getStringLista(listLocal, tipo_local);
	 document.frmNuevo.colegios.value = getStringLista(listInst, tipo_inst);
	 document.frmNuevo.sedes.value = getStringLista(listSede, tipo_sede);
	 document.frmNuevo.jornadas.value = getStringLista(listJord, tipo_jord);
	 
	   validarCampo(forma.msjfechaini , '- Fecha inicial');
       validarCampo(forma.msjfechafin , '- Fecha final');
       validarRangoFechasPeriodo2(forma.msjfechaini, forma.msjfechafin  ,"- La fecha inicial no puede ser mayor a la final");
       validarCampo(forma.msjasunto , '- Asunto',1,300);
       validarCampo(forma.msjcontenido , '- Contenido',1,3000);
	   
	   if(! (listPerfiles.length > 0)){
		  appendErrorMessage("- Seleccione al menos un perfil");
	 	  _campoError = forma.perfil;
       } 
}
  
  
  
  
   function nuevo(){
	 document.frmNuevo.tipo.value=document.frmNuevo.FICHA_MSJ.value;
	 document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
	 document.frmNuevo.submit();
   }
   
   function buscar(){
     document.frmNuevo.tipo.value=document.frmNuevo.FICHA_LISTA.value;
	 document.frmNuevo.cmd.value=document.frmNuevo.BUSCAR.value;
	 document.frmNuevo.submit();
   }
   
   function guardar(){
		if(validarForma(document.frmNuevo)){
		    document.frmNuevo.tipo.value=document.frmNuevo.FICHA_MSJ.value;					
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;			
			document.frmNuevo.submit();
		}	
	}
 

 
 
 
 
  // TABLAS AJAX
	function ajaxColegios(){ 
		  borrar_combo2(document.frmNuevo.inst);
		  borrar_combo2(document.frmNuevo.sede); 
		  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
		  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.local_inst.value;
		  document.frmAjaxNuevo.ajax[1].value = -99;
		  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_INST.value;
		  document.frmAjaxNuevo.submit();
		 
		}

	function ajaxSede(){
	
		  borrar_combo2(document.frmNuevo.sede);
		  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
		  var flaTexto = document.frmNuevo.inst_sede.value;
	 	  document.frmAjaxNuevo.ajax[0].value = flaTexto;
		  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_SEDE.value;
		  document.frmAjaxNuevo.submit();
		 
		}
	 
 	function ajaxJornada(){ 
	   
	  borrar_combo2(document.frmNuevo.jornada);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value = getStringLista(listInst, tipo_inst);
	  document.frmAjaxNuevo.ajax[1].value = getStringLista(listSede, tipo_sede);
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


 
  
  
   


 //::::::::::::::::::  TABLAS DINAMICAS
 
 
 // funciones para eliminar
 
 
  /**
 *  Función : Borra los filas la tabla
 *            segun el codigo que recibe como parametro
 *           
 */
function eliminarRow(tipo, cod, cod2) {

  if(tipo == tipo_perfil){ 
   deleteElemento(listPerfiles, cod, tipo);
   pintarTabla(listPerfiles, tipo);
  } else
  if(tipo == tipo_local){
   <c:if test="${!empty sessionScope.login.munId and    sessionScope.login.munId  > 0  }">
     alert("No es posible eliminar la localidad.");
   </c:if>
   <c:if test="${empty sessionScope.login.munId }">
    deleteElemento(listLocal, cod, tipo);
    pintarTabla(listLocal, tipo);
    addCombo(listLocal, tipo);
   </c:if>
  } else
  if(tipo == tipo_inst){
    <c:if test="${!empty sessionScope.login.instId and    sessionScope.login.instId  > 0  }">
     alert("No es posible eliminar el colegio.");
    </c:if>
    <c:if test="${empty sessionScope.login.instId }">
     deleteElemento(listInst, cod+"|"+cod2 , tipo);
     pintarTabla(listInst, tipo);
    </c:if>
  } else
  if(tipo == tipo_sede){
   deleteElemento(listSede, cod+"|"+cod2 , tipo);
   pintarTabla(listSede, tipo);
  } else
  if(tipo == tipo_jord){
   deleteElemento(listJord, cod , tipo);
   pintarTabla(listJord, tipo);
  }
}



 /**
 *  Función : Borra los elementos hijos que la tabla
 *           
 *           
 */
function deleteElemento(lista, valor, tipo){ 
try{

 var flag = false;
 var list2 = new Array();
 var ii = 0; 
  if(tipo != tipo_inst && tipo != tipo_sede ){
   for(var i=0;i<lista.length;i++){ 
     var fragmentoTexto = lista[i].split('|');  
     if(fragmentoTexto[0] != valor){
       list2[ii++] =lista[i];
     }else{
        if(tipo == tipo_local){
          deleteElementoHijo(listInst, fragmentoTexto[0], tipo_inst);
         }
     }
   }
 }else{   
    for(var i=0;i<lista.length;i++){
     var fragmentoTexto = lista[i].split('|'); 
     var fragmentoTexto2 = fragmentoTexto[0].split(':'); 
     if( trim(fragmentoTexto2[0] + "|" + fragmentoTexto2[2]) != trim(valor)){
        list2[ii++] = lista[i];
     }else{
        if(tipo == tipo_inst){
          deleteElementoHijo(listSede, fragmentoTexto2[2], tipo_sede);
         }
     }
   }
 }
 
 if(tipo == tipo_perfil){
  listPerfiles = null; listPerfiles = list2;
 } else
 if(tipo == tipo_local){
  listLocal = null;listLocal = list2;
 } else
 if(tipo == tipo_inst){
  listInst = null;listInst = list2;
  ajaxJornada();
 } else
  if(tipo == tipo_sede){
  listSede = null; listSede = list2;
  ajaxJornada();
  
 } else
 if(tipo == tipo_jord){
  listJord = null;listJord  = list2;
 } 
 ajaxJornada();
 }catch(error){
   alert("Error deleteElemento "+ error);
 }

}


 /**
 *  Función : Borra los elementos hijos que la lista
 *           
 *           
 */
function deleteElementoHijo(lista, valor, tipo){
 try{
 var flag = false;
 var list2 = new Array();
 var ii = 0; 
 
  for(var i=0;i<lista.length;i++){ 
     var fragmentoTexto = lista[i].split('|');
     var fragmentoTexto2 = fragmentoTexto[0].split(':'); 
     if(fragmentoTexto2[0] != valor){
       list2[ii++] =lista[i];
     }else{
       if(tipo == tipo_inst){ 
          deleteElementoHijo(listSede, fragmentoTexto2[2], tipo_sede);
        }
     }
   }
  
  if(tipo == tipo_inst){
    listInst = null; listInst = list2;
    pintarTabla(listInst, tipo);
    addCombo(listInst, tipo);
    borrar_combo33(document.frmNuevo.inst);  
  }
  
  if(tipo == tipo_sede){
    listSede = null; listSede = list2;
    pintarTabla(listSede, tipo);
    addCombo(listSede, tipo);
    borrar_combo33(document.getElementById('sede') ); 
  }
  
  
 
  }catch(error){
    alert("Error en deleteElementoHijo " + error)
  }
}


 

 /**
 *  Función : Adiciona un nueva fila a la tabla 
 *           que corresponte al tipo 
 *           
 */
function agrega_row(tipo, combo, combo2){
try{ 
 

 
    if(tipo == tipo_perfil && addElemento(listPerfiles, combo.value+"|"+ combo.options[combo.options.selectedIndex].text, tipo_perfil, combo)){
      alert("Ya se agrego el perfil '" + combo.options[combo.options.selectedIndex].text+"' ");
    } else
    if(tipo == tipo_local && addElemento(listLocal, combo.value+"|"+ combo.options[combo.options.selectedIndex].text,  tipo_local, combo, combo2)){
      alert("Ya se agrego la localidad '" + combo.options[combo.options.selectedIndex].text+"' ");
    } else
    if(tipo == tipo_inst && addElemento(listInst, document.getElementById('local_inst').value + ":" + document.getElementById('local_inst').options[document.getElementById('local_inst').options.selectedIndex].text + ":" + combo.value+"|"+ combo.options[combo.options.selectedIndex].text, tipo_inst, combo, combo2)){
      alert("Ya se agrego la institución '" + combo.options[combo.options.selectedIndex].text+"' ");
    }
    if(tipo == tipo_sede && addElemento(listSede, document.getElementById('inst_sede').value + ":" + document.getElementById('inst_sede').options[document.getElementById('inst_sede').options.selectedIndex].text + ":" + combo.value+"|"+ combo.options[combo.options.selectedIndex].text, tipo_sede, combo, combo2 )){
      alert("Ya se agrego la sede '" + combo.options[combo.options.selectedIndex].text+"' ");
    }
    if(tipo == tipo_jord && addElemento(listJord, combo.value+"|"+ combo.options[combo.options.selectedIndex].text, tipo_jord, combo, combo2)){
      alert("Ya se agrego la jornada '" + combo.options[combo.options.selectedIndex].text+"' ");
    }
     
     
     // pintar 
    if(tipo == tipo_perfil){
      pintarTabla(listPerfiles, tipo);
    }
    if(tipo ==  tipo_local){
      pintarTabla(listLocal, tipo);
      addCombo(listLocal, tipo);
    }
    
    if(tipo ==  tipo_inst){
     
      pintarTabla(listInst, tipo);
      addCombo(listInst, tipo);
    }
    
    if(tipo ==  tipo_sede){
      pintarTabla(listSede, tipo);
    }
    if(tipo ==  tipo_jord){
      pintarTabla(listJord, tipo);
    }
     
 
   ajaxJornada();
  }catch(error){
    alert("agrega_celda  " + error);
  }
}




/**
*  Función : Adiciona un o más  elemento a la lista que
            que corresponte al tipo 
*           
*/
function addElemento(lista, valor, tipo, combo, combo2){
 var flag = false; 
 
try{

 if(combo.options.selectedIndex == 0){
	// insertar en la table todos lo valores del combo
	  var  lista_ = new Array();
	  var k= 0; 
	  for(var i=1;i<combo.length;i++){
	     if(tipo == tipo_inst ){
	          var valor_ = combo2.options[combo2.selectedIndex].value + ':' + combo2.options[combo2.selectedIndex].text + ':' + combo.options[i].value +'|'+ combo.options[i].text;
		      if(! isRepetido(listInst,valor_)){
		         listInst[ listInst.length ] = valor_;
		      } 
		  }else
		  if( tipo == tipo_sede){
	          var valor_ = combo2.options[combo2.selectedIndex].value + ':' + combo2.options[combo2.selectedIndex].text + ':' + combo.options[i].value +'|'+ combo.options[i].text;
		      if(! isRepetido(listSede,valor_)){
		         listSede[ listSede.length ] = valor_;
		      } 
		  }else{ 
	       lista_[i-1] = combo.options[i].value +'|'+ combo.options[i].text;
	     } 
	   }
	   
	   if(tipo == tipo_perfil){ listPerfiles = null; listPerfiles  = lista_;} 
	   if(tipo == tipo_local){ listLocal = null; listLocal  = lista_;} 
	   if(tipo == tipo_jord){  listJord = null; listJord  = lista_;}
	   
     return false;
 }else{
	// insertar en la table un valor  
	 if(!isRepetido(lista, valor)){
	   var ind = lista.length;
	   lista[ind] = valor;
	  }else{
	    return true;
	  } 
	  
	  return flag;
  
  }
 
 }catch(error){
   alert("Error addElemento " + error);
 }
}




/**
*  Funcion : Valida si el valor que existe en la lista
*           
*/
function isRepetido(lista, valor){
 var flag = false;
    for(var i=0;i<lista.length;i++){
	   if(lista[i] == valor){
	     flag = true;
	     break;
	   }
	}
 return flag;	
}



/**
*  Funcion : Limpia las tabla, es decir borrar las filas
*           que tenga la tabla
*/
function clearTable(tbody) {
    while (tbody.rows.length > 0) {
        tbody.deleteRow(0);
    } 
}


/**
*  Funcion : Pintala las filas de la tabla segun el array
*           list que recibe como parametro
*/
function pintarTabla(listado, tipo){
  
try{ 
 if(tipo == tipo_perfil){
    var tbody = document.getElementById('perfil_tabla').getElementsByTagName("TBODY")[0];
  } 
  
  if(tipo == tipo_local){
    var tbody = document.getElementById('local_tabla').getElementsByTagName("TBODY")[0];
  } 
  
    if(tipo == tipo_inst){
    var tbody = document.getElementById('inst_tabla').getElementsByTagName("TBODY")[0];
  } 
  
    if(tipo == tipo_sede){
    var tbody = document.getElementById('sede_tabla').getElementsByTagName("TBODY")[0];
    
  } 
  
    if(tipo == tipo_jord){
     var tbody = document.getElementById('jord_tabla').getElementsByTagName("TBODY")[0];
    } 
      
    clearTable(tbody);
    for(var i=0;i<listado.length;i++){
		var row = document.createElement("TR") 
		var td1 = document.createElement("TD") 
        td1.setAttribute("class","Fila"+ ((i+1)%2) ) 
        var fragmentoTexto = listado[i].split('|'); 
   
    // columna 1
        var label;
        label = document.createElement('label');  
		if(tipo != tipo_inst && tipo != tipo_sede){
		 label.innerHTML = "<a onclick='eliminarRow("+ tipo +"," + fragmentoTexto[0] + ")'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a>";
	    }else{
	       var fragmentoTexto2 =  fragmentoTexto[0].split(':');
	      label.innerHTML = "<a onclick='eliminarRow("+ tipo +"," + fragmentoTexto2[0]+","+fragmentoTexto2[2] + ")'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a>";
	    } 
	    td1.appendChild (label); 

 
    // columna 2
        var td1_1;
		if(tipo == tipo_inst || tipo == tipo_sede){
		 var fragmentoTexto2 =  fragmentoTexto[0].split(':');
		 td1_1 = document.createElement("TD");
		 td1_1.setAttribute("class","Fila"+ ((i+1)%2) ); 
		 td1_1.appendChild (document.createTextNode(" " +  fragmentoTexto2[1] ));
		}
				
    // columna 3				
		var td2 = document.createElement("TD")
		td2.setAttribute("class","Fila"+ ((i+1)%2) );
		td2.appendChild (document.createTextNode(" " +  fragmentoTexto[1]))
          
        row.appendChild(td1);
		if(tipo == tipo_inst || tipo == tipo_sede ) row.appendChild(td1_1);
		row.appendChild(td2);
		tbody.appendChild(row);  
	}
  }catch(error){
    alert("Error pintarTabla "+ error);
  }	
}


/**
*  Funcion : Retorna un string con los codigos correspondientes
*           al array lista que recibe como parametros
*/
function getStringLista(lista, tipo){
 try{
   var valores = "";
  for(var i=0;i<lista.length;i++){
    if(tipo == tipo_perfil){
      var fragmentoTexto = lista[i].split('|');
      valores +=  fragmentoTexto[0] + ",";
    } else
    if(tipo == tipo_local){
      var fragmentoTexto = lista[i].split('|');
      valores +=  fragmentoTexto[0] + ",";
    } else
    if(tipo == tipo_inst){
      var fragmentoTexto = lista[i].split('|');
      var fragmentoTexto2 = fragmentoTexto[0].split(':');
      valores +=  fragmentoTexto2[0] + "|" + fragmentoTexto2[2] + ",";
    } else
    if(tipo == tipo_sede){
      var fragmentoTexto = lista[i].split('|');
      var fragmentoTexto2 = fragmentoTexto[0].split(':');
      valores += fragmentoTexto2[0] + "|" + fragmentoTexto2[2]+",";
    } else
    if(tipo == tipo_jord){
      var fragmentoTexto = lista[i].split('|');
      var fragmentoTexto2 = fragmentoTexto[0].split(':');
      valores += fragmentoTexto2[0] + ",";
    } 
  }
  return valores.substring(0, valores.length -1);
  }catch(error){
   alert("getStringLista error " + error );
  }
  return null;
}


/**
*  Funcion : Borrar las opciones del combo
*           
*/
 function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno","-99");
 }
	

/**
*  Funcion : Adicona el listado de la tabla
*           al combo correspondiente
*/	
function addCombo(lista, tipo){
 try{
 
	 if(tipo == tipo_local){
	    borrar_combo2(document.getElementById('local_inst') ); 
	    for(var i=0;i<lista.length;i++){
	        var fragmentoTexto = lista[i].split('|');
		    document.getElementById('local_inst').options[i+1] = new Option(fragmentoTexto[1],fragmentoTexto[0] );
		     <c:if test="${!empty sessionScope.login.munId and    sessionScope.login.munId  > 0 }"> 
    		   document.getElementById('local_inst').selectedIndex  = i+1;
    		   document.getElementById('local_inst').disabled = false;
             </c:if>
		    
		}  
	 
	  }else 
	  if(tipo == tipo_inst){
	    borrar_combo2(document.getElementById('inst_sede') ); 
	    for(var i=0;i<lista.length;i++){
	        var fragmentoTexto = lista[i].split('|');
	        var fragmentoTexto2 = fragmentoTexto[0].split(':');
		    document.getElementById('inst_sede').options[i+1] = new Option(fragmentoTexto[1], fragmentoTexto2[2]);
		     
		}  
	  }
	  
    
  }catch(error){
    alert("function addCombo " + error);
  }
}
-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));" width="100%">
 	   	<form method="post" name="frmAjaxNuevo" action='<c:url value="/siges/gestionAdministrativa/enviarMensajesAjax.do"/>'>
			<input type="hidden" name="tipo" value='<c:out value="${ paramsVO.FICHA_MSJ}"/>'>
			<input type="hidden" name="cmd"  value='-1'>
			<input type="hidden" name="tipoAdd"  value='0'>
			<input type="hidden" name="CMD_AJAX_INST"  id="CMD_AJAX_INST"  value='<c:out value="${paramsVO.CMD_AJAX_INST}"/>'>
			<input type="hidden" name="CMD_AJAX_SEDE"  id="CMD_AJAX_SEDE"  value='<c:out value="${paramsVO.CMD_AJAX_SED}"/>'>
			<input type="hidden" name="CMD_AJAX_JORD"  id="CMD_AJAX_JORD"  value='<c:out value="${paramsVO.CMD_AJAX_JORD}"/>'>
			<input type="hidden" name="CMD_AJAX_METD"  id="CMD_AJAX_METD"  value='<c:out value="${paramsVO.CMD_AJAX_METD}"/>'>
			<input type="hidden" name="CMD_AJAX_GRUP"   id="CMD_AJAX_GRUP"   value='<c:out value="${paramsVO.CMD_AJAX_GRUP}"/>'>
			<input type="hidden" name="CMD_AJAX_GRAD"  id="CMD_AJAX_GRAD"  value='<c:out value="${paramsVO.CMD_AJAX_GRAD}"/>'>
			<input type="hidden" name="ADD_PER"  id="ADD_PER"  value='<c:out value="${paramsVO.ADD_PER}"/>'>			
			<input type="hidden" name="nom"  id="nom" >			
			<input type="hidden" name="cod"  id="cod" >			
			 <c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach>
		</form>
	 <!-- FORMUALARIO DE EDICION CREACION--> 		  
	<form method="post" name="frmNuevo"  action='<c:url value="/siges/gestionAdministrativa/enviarMensajes/Save.jsp"/>' width="100%" >
        <input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_MSJ}"/>'>
        <input type="hidden" name="FICHA_MSJ" value='<c:out value="${paramsVO.FICHA_MSJ}"/>'>
        <input type="hidden" name="FICHA_LISTA" value='<c:out value="${paramsVO.FICHA_LISTA}"/>'>
        <input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'> 
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'> 
		<input type="hidden" name="CANCELAR" value='<c:out value="${paramsVO.CMD_CANCELAR}"/>'>
		<input type="hidden" name="formaEstado" value='<c:out value="${mensajesVO.formaEstado}"/>'>
		<input type="hidden" name="perfiles" value=''>
		<input type="hidden" name="localidades" value=''>
		<input type="hidden" name="colegios" value=''>
		<input type="hidden" name="sedes" value=''>
		<input type="hidden" name="jornadas" value=''>
		<!--  INPUT PARA EL FILTRO -->

		<input type="hidden" name="filFechaDesde" value='<c:out value="${sessionScope.filtroMensajeVO.filFechaDesde}"/>'>
		<input type="hidden" name="filFechaHasta" value='<c:out value="${sessionScope.filtroMensajeVO.filFechaHasta}"/>'>
 
	
			
		<table cellpadding="0" cellspacing="1" border="0" ALIGN="CENTER" width="100%" >
		  <caption width="100%" >Gestión Administrativa >> Enviar Mensajes</caption>	         
		    <tr>						
			 <td rowspan="1" bgcolor="#FFFFFF">			      
				   <img src='<c:url value="/etc/img/tabs/enviarMensajes_1.gif"/>' border="0"  height="26" alt='Envíar Mensajes'>
		        </td>
            </tr>
          </table>
          
		 <table cellpadding="1" cellspacing="1" border="0" ALIGN="CENTER"  width="100%" >	
		 <caption colspan="4" align="center"><b>Mensaje</b></caption>			 	         
		    <tr>						
			 <td rowspan="2" bgcolor="#FFFFFF">			      
				<c:if test="${mensajesVO.formaEstado == 1}">
				  <input class="boton" name="cmd1" type="button" value="Actualizar" onClick="guardar()">
				</c:if>
				<c:if test="${mensajesVO.formaEstado != 1}">
				  <input class="boton" name="cmd1" type="button" value="Guardar" onClick="guardar()">
				</c:if>				
        		<input class="boton" name="cmd12" type="button" value="Nuevo" onClick="nuevo()">
        		&nbsp;&nbsp;&nbsp;
        		<input class="boton" name="cmd12" type="button" value="Buscar" onClick="buscar()">
		     </td>
            </tr>
          </table>
        <table   >
			<tr>
				<td width="15%"><span class="style2" >*</span>Fecha Inicial</td>
				<td  width="35%" align="left">
				<input type="text" id="msjfechaini" name="msjfechaini" maxlength="10" size="10" value='<c:out value="${sessionScope.mensajesVO.msjfechaini}"/>'></input>
				<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="imgfecha"  title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
				</td>
				
				<td  width="15%"><span class="style2" >*</span>Fecha Final</td>
				<td  width="35%" align="left">
				<input type="text" id="msjfechafin" name="msjfechafin" maxlength="10" size="10" value='<c:out value="${sessionScope.mensajesVO.msjfechafin}"/>'></input>
				<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="imgfecha2"  title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
				</td>
			</tr>
			
		</table>
		<table border="0" align="center" width="98%" cellpadding="1" cellspacing="1" width="400px">	
			<tr>
				<td><span class="style2">*</span>Asunto:</td>				
		 	</tr>
		 	<tr>				
				<td colspan="3"><textarea name="msjasunto" id="msjasunto" cols="60" rows="1" onKeyDown="textCounter(this,300,300);" onKeyUp="textCounter(this,300,300);"><c:out value="${sessionScope.mensajesVO.msjasunto}"/></textarea></td>
		 	</tr>
		 	<tr>
				<td><span class="style2">*</span>Mensaje:</td>				
		 	</tr>
		 	<tr>				
				<td colspan="3"><textarea name="msjcontenido" id="msjcontenido" cols="60" rows="4" onKeyDown="textCounter(this,3000,3000);" onKeyUp="textCounter(this,3000,3000);"><c:out value="${sessionScope.mensajesVO.msjcontenido}"/></textarea></td>
		 	</tr>
		 	<tr style="display:none"><td><iframe name="frameAjaxNuevo" id="frameAjaxNuevo"></iframe></td></tr>
		</table>
		
		  <table border="0" align="center" width="98%" cellpadding="1" cellspacing="1" width="400px" >	
		  	<tr><td class="Fila0" colspan="4" align="center">Enviar a:</td></tr>
		  	
		  	<tr><td>
		  	<fieldset>
							<legend><b><span class="style2" >*</span>Seleccione Perfiles</b></legend>
		  	<table border="0" align="center" width="100%" cellpadding="1" cellspacing="1">
			<tr>
		 	   <td style="width: 15%"><span class="style2" >*</span>Perfil</td>
		 	    <td style="width: 35%"><select  name="perfil" id="perfil" style="width: 190px;" onchange="JavaScript:ajaxColegios();" <c:if test="${sessionScope.filtroRepCarnesVO.hab_loc ==0}">DISABLED</c:if>>
		 	        <option value="-99">-- Todos --</option>
		 	         <c:forEach begin="0" items="${requestScope.listaPerfil_}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroRepCarnesVO.loc ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	      </select>
		 	    </td>	
		 	    <td><a  href="javascript:agrega_row(1, document.frmNuevo.perfil)"><img border='0' src='<c:url value="/etc/img/add.gif"/>' width='15' height='15'> Agregar</a></td>	 	    
		 	 </tr> 
	 
		 </table>
		<table id="perfil_tabla"  border="1" align="center" width="50%" cellpadding="1" cellspacing="1" width="400px">
		<caption>Lista de Perfiles agregados</caption>
		    <c:forEach begin="0" items="${requestScope.listaPerfiles}" var="item" varStatus="st">
				 <tr><td class='Fila<c:out value="${st.count%2}"  />'  > <a onclick="eliminarRow(1, '<c:out value="${item.codigo }"/>')"><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></td><td class='Fila<c:out value="${st.count%2}"  />'  ><c:out value="${item.nombre }"/></td></tr> 
              </c:forEach>
		<TBODY>
		     
		</TBODY>
	   </table> 
		      	
		</fieldset>
		
		  	<fieldset>
				<legend><b>Seleccione Localidades</b></legend>
		  	<table border="0" align="center" width="100%" cellpadding="1" cellspacing="1">
			<tr>
		 	   <td style="width: 15%">Localidad</td>
		 	    <td style="width: 35%">
		 	      <select  name="localidad" id="localidad" style="width: 190px;"  <c:if test="${!empty sessionScope.login.munId and    sessionScope.login.munId  > 0 }"> disabled="disabled" </c:if> >
		 	        <option value="-99">--Todos--</option>
		 	         <c:forEach begin="0" items="${requestScope.listaLocalidad}" var="item">
				     <option value="<c:out value="${item.codigo}"/>"  <c:if test="${!empty sessionScope.login.munId and    sessionScope.login.munId  == item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	      </select>
		 	    </td>	
		 	    <td><a href='javascript:agrega_row(2, document.frmNuevo.localidad)'><img border='0' src='<c:url value="/etc/img/add.gif"/>' width='15' height='15'> Agregar</a></td>	 	    
		 	 </tr> 
		 	 </table>
		 	  <table id="local_tabla"  border="1" align="center" width="50%" cellpadding="1" cellspacing="1">
		        <caption>Lista de Localidades agregados</caption> 
		        <TBODY>
		        </TBODY>
	         </table>
		 	 </fieldset>
		 	
	         <fieldset>
			<legend><b>Seleccione Colegios</b></legend>
		     <table  id="inst_tabla_combox" border="0" align="center" width="100%" cellpadding="1" cellspacing="1">
		 	 <tr>
		 	   <TBODY>
		 	    <td style="width: 15%">Localidad</td>
		 	    <td style="width: 35%" colspan="2">
		 	     <select name="local_inst" id="local_inst" style="width: 350px;" onchange="JavaScript: ajaxColegios();"  <c:if test="${sessionScope.filtroRepCarnesVO.hab_inst ==0}">DISABLED</c:if> >
		 	        <option value="-99">-- Todos--</option>
		 	          <c:forEach begin="0" items="${requestScope.listaColegio}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroRepCarnesVO.inst ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	    <td>&nbsp;&nbsp;</td>
		 	  </tr> 
		 	  </TBODY>
		 	 </table>
		 	 <table  id="inst_tabla_combox" border="0" align="center" width="100%" cellpadding="1" cellspacing="1">
		 	 <tr>
		 	   <TBODY>
		 	    <td style="width: 15%">Colegio</td>
		 	    <td style="width: 35%" colspan="2"><select name="inst" id="inst" style="width: 350px;"   >
		 	        <option value="-99">-- Todos--</option>
		 	          <c:forEach begin="0" items="${requestScope.listaColegio}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroRepCarnesVO.inst ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	    <td><a href='javascript:agrega_row(3, document.frmNuevo.inst, document.frmNuevo.local_inst)'><img border='0' src='<c:url value="/etc/img/add.gif"/>' width='15' height='15'> Agregar</a></td>
		 	  </tr> 
		 	  </TBODY>
		 	 </table>
		 	  <table id="inst_tabla"  border="1" align="center" width="90%" cellpadding="1" cellspacing="1">
		        <TBODY><caption>Lista de instituciones agregadas</caption>
		       </TBODY>
	         </table>
		 	 </fieldset>
		 	 
		 	
		 	 
		 	 <fieldset>
							<legend><b>Seleccione Sedes </b></legend>
		 	 <table border="0" align="center" width="100%" cellpadding="1" cellspacing="1">
		 	 <tr><td style="width: 15%">Colegio</td>
		 	    <td style="width: 35%"><select  name="inst_sede" id="inst_sede" style="width: 250px;" onchange="JavaScript:ajaxSede();">
		 	        <option value="-99">-- Todos--</option>
		 	         <c:forEach begin="0" items="${requestScope.listaSede}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroRepCarnesVO.sede ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	      </select>
		 	    </td>
		 	    <td>&nbsp;</td>
		 	  </tr>
		 	 
		 	 <tr><td style="width: 15%">Sede</td>
		 	    <td style="width: 35%"><select  name="sede" id="sede" style="width: 250px;" onchange="JavaScript:ajaxJornada();">
		 	        <option value="-99">-- Todos--</option>
		 	         <c:forEach begin="0" items="${requestScope.listaSede}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroRepCarnesVO.sede ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	      </select>
		 	    </td>
		 	    <td><a href='javascript:agrega_row(4, document.frmNuevo.sede, document.frmNuevo.inst_sede )'><img border='0' src='<c:url value="/etc/img/add.gif"/>' width='15' height='15'> Agregar</a></td>
		 	  </tr>
		 	  </table>
		 	  <table id="sede_tabla"  border="1" align="center" width="100%" cellpadding="1" cellspacing="1">
		        <TBODY><caption>Lista de sedes agregadas</caption>
		          <c:forEach begin="0" items="${requestScope.listaSede}" var="item" varStatus="st">
				     <tr><td class='Fila<c:out value="${st.count%2}"  />'  > <a onclick="eliminarRow(4, '<c:out value="${item.codigo }"/>')"><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></td><td class='Fila<c:out value="${st.count%2}"  />'  ><c:out value="${item.nombre }"/></td></tr> 
                  </c:forEach>
                </TBODY>
	         </table>
		 	 </fieldset>
		 	  <fieldset>
			  <legend><b>Seleccione Jornadas</b></legend>
		 	  <table border="0" align="center" width="100%" cellpadding="1" cellspacing="1">
		 	    <td style="width: 15%">Jornada</td>
		 	    <td style="width: 35%">
		 	    	<select name="jornada" id="jornada"  style="width: 100px;" onchange="ajaxGrado();"  >
			 	        <option value="-99">-- Todos--</option>
			 	          <c:forEach begin="0" items="${requestScope.listaJord}" var="item">
					     <option value="<c:out value="${item.codigo}"/>" <c:if test="${sessionScope.filtroRepCarnesVO.jornd ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
	                     </c:forEach>
		 	        </select>
		 	    </td>
		 	    <td><a href='javascript:agrega_row(5, document.frmNuevo.jornada )'><img border='0' src='<c:url value="/etc/img/add.gif"/>' width='15' height='15'> Agregar</a></td>
		 	 </tr>   
		 	 </table>
		 	 	</fieldset> 
		 	 	 <table id="jord_tabla"  border="1" align="center" width="50%" cellpadding="1" cellspacing="1">
		        <TBODY><caption>Lista de jornadas agregadas</caption>
		          <c:forEach begin="0" items="${requestScope.listaJord}" var="item" varStatus="st">
				     <tr><td class='Fila<c:out value="${st.count%2}"  />'  > <a onclick="eliminarRow(5, '<c:out value="${item.codigo }"/>')"><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></td><td class='Fila<c:out value="${st.count%2}"  />'  ><c:out value="${item.nombre }"/></td></tr> 
                  </c:forEach>
		        </TBODY>
	         </table>
		 	</td></tr>
		 	 </table>
		 <table cellpadding="1" cellspacing="1" border="0" ALIGN="CENTER"  width="100%" >	
		    <tr><td>&nbsp;&nbsp;&nbsp;</td></tr>
		    <tr><td>&nbsp;&nbsp;&nbsp;</td></tr>
		    <tr>						
			 <td align="center" rowspan="2" bgcolor="#FFFFFF">			      
				<c:if test="${mensajesVO.formaEstado == 1}">
				  <input class="boton" name="cmd1" type="button" value="Actualizar" onClick="guardar()">
				</c:if>
				<c:if test="${mensajesVO.formaEstado != 1}">
				  <input class="boton" name="cmd1" type="button" value="Guardar" onClick="guardar()">
				</c:if>				
        		<input class="boton" name="cmd12" type="button" value="Nuevo" onClick="nuevo()">
		     </td>
            </tr>
          </table>
	 </form>
</body>
<script type="text/javascript">
    Calendar.setup({
        inputField     :    "msjfechaini",
        ifFormat       :    "%d/%m/%Y",
        button         :    "imgfecha",
        align          :    "Br"
    });

    Calendar.setup({
        inputField     :    "msjfechafin",
        ifFormat       :    "%d/%m/%Y",
        button         :    "imgfecha2",
        align          :    "Br"
    });
    
    
    
  
    
    
    pintarTabla(listLocal, tipo_local);
    addCombo(listLocal, tipo_local);
    
    
     <c:if test="${!empty sessionScope.login.munId and    sessionScope.login.munId  > 0  }">
      document.frmNuevo.localidad.disabled = true;
       <c:if test="${  mensajesVO.formaEstado != 1 }">
	      agrega_row(2, document.frmNuevo.localidad);
      </c:if>
    </c:if>
    
    <c:if test="${!empty sessionScope.login.instId and    sessionScope.login.instId  > 0 }"> 
       document.frmNuevo.inst.disabled = true;
    </c:if>
     
    
    pintarTabla(listInst, tipo_inst);
    addCombo(listInst, tipo_inst);
    
    pintarTabla(listSede, tipo_sede);

    pintarTabla(listJord, tipo_jord);
    
    
     
    ajaxJornada();
    
     <c:if test="${!empty sessionScope.login.munId and    sessionScope.login.munId  > 0  }">
       document.frmNuevo.local_inst.disabled = true;
	    ajaxColegios();
    </c:if>
    
    		
</script>
</html>