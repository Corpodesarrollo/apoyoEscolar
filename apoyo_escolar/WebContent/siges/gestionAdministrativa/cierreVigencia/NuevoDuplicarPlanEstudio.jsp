<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="siges.gestionAdministrativa.cierreVigencia.vo.ParamsVO" scope="page"/>
<html>
<head>
<style type="text/css">@import url('<c:url value="/etc/css/calendar-win2k-1.css"/>');</style>
<script type="text/javascript" src='<c:url value="/etc/js/calendar.js"/>'></script>
<script type="text/javascript" src='<c:url value="/etc/js/validar.js"/>'></script>
<script type="text/javascript" src='<c:url value="/etc/js/lang/calendar-es.js"/>'></script>
<script type="text/javascript" src='<c:url value="/etc/js/calendar-setup.js"/>'></script>
<script languaje="javaScript">
	var nav4=window.Event ? true : false;
   
    var imagen1;  
    imagen1=new Image
    imagen1.src='<c:url value="/etc/img/spinner.gif"/>';
    var ScrollSpeed = 100;  // milliseconds between scrolls
	var ScrollChars = 1;    // chars scrolled per time period
	var stop = 0;
	var flag = 0;
	var flagSpinner = 0;
	var msg2 = "";
	var decimas = 0  
	var segundos = 0  
	var minutos = 0  
	var ValorCrono = ""  ;
    
    
   
			
			
			

	 function SetupTicker() { flag = 0; msg = "... ."; /* msg += "..2 ";  msg += "... 3";msg += "....4 "; */RunTicker();}
	 function RunTicker() {
	  if(stop == 0){ 
	   CronoID = window.setTimeout('RunTicker()',ScrollSpeed);
	   document.getElementById("txtmsg").innerHTML = "ATUALIZANDO";
	  // document.getElementById("barraCargar").innerHTML = msg2;
	  // document.getElementById("timemsg").innerHTML = ValorCrono;
	  // alert(flagSpinner);
	   if(flagSpinner < 1){
	    flagSpinner = 1;
	    document.getElementById("timemsg").innerHTML  = "<img name=spinerss border=0  height=8 src="+'<c:url value="/etc/img/_spinxer.gif"/>'+"  >";
	  }
	   flag++;
	   if(flag == 6)
	     flag=0;
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
	function init(){stop = 1;flagSpinner = 0;}
	function Run(){stop = 0;flagSpinner=0; SetupTicker();MostrarCrono();}
	function end(){stop = 1;DetenerCrono(); flagSpinner=0;} 


	function DetenerCrono (){if(CronoEjecutandose)clearTimeout(CronoID);CronoEjecutandose = false;} 
	function MostrarCrono () {  
				  
		 //incrementa el crono  
		 decimas++  
		 if ( decimas > 9 ) {  
			 decimas = 0  
			 segundos++  
			 if ( segundos > 59 ) {  
				 segundos = 0  
				 minutos++  
				 if ( minutos > 99 ) {  
					 alert('Fin de la cuenta')  
					 DetenerCrono()  
					 return true  
				 }  
			 }  
		 }  
		 ValorCrono = (minutos < 10) ? "0" + minutos : minutos;  
		 ValorCrono += (segundos < 10) ? ":0" + segundos : ":" + segundos;  
		 ValorCrono += ":" + decimas;   
		 CronoEjecutandose = true ; 
		 return true;  
	 } 
    
    
    
    
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
		
 

	
	function hacerValidaciones_frmNuevo(forma){
	 validarLista(forma.vigenciaOrigen,      "- Vigencia origen ",1);
	 validarLista(forma.vigenciaDestino,      "- Vigencia destino",1);
	  if(forma.vigenciaOrigen.value > 0 && forma.vigenciaDestino.value >0 && forma.vigenciaOrigen.value <= forma.vigenciaDestino.value ){
	    appendErrorMessage("La vigencia a la cual se va duplicar no puede ser mayor o igual a la vigencia desde la cual se va copiar la información.")
        
        if (_campoError == null) {
            _campoError = forma.vigenciaOrigen;
        }
	  }
	}
	
	
	function duplicar(){
	  if(validarForma(document.frmNuevo) ){
	  if(confirm("¿Esta seguro de actualizar esta información?")){
	    document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
         Run();

       document.frmNuevo.submit();
	  }
	  }
    }
    function loadImagen(){
    imagen1=new Image
    imagen1.src='<c:url value="/etc/img/spinner.gif"/>';
  
   }
   
  function actulizar_vista(){
    document.frmNuevo.submit();
    }
     
	function lanzar_(tipo){				
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.cmd.value=-1;
				document.frmNuevo.action = '<c:url value="/siges/gestionAdministrativa/cierreVigencia/CierreVigencia.do"/>';
				document.frmNuevo.target = ""; 
				document.frmNuevo.submit();
	}   
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));" a>
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
 	 <tr height="1">
		<td width="10">&nbsp;</td>
		<td rowspan="2" width="469">
		<a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_CIERRE_VIG }" />')"><img src='<c:url value="/etc/img/tabs/adm_cierreVig_0.gif"/>' alt="Cierre de Vigencia " height="26" border="0"></a>
		<img src='<c:url value="/etc/img/tabs/adm_duplPlanEstd_1.gif"/>' alt="Duplicar Plan de Estudios" height="26" border="0"
		
		></td>
	 </tr>
	</table>
   
   <form method="post" name="frmNuevo" action='<c:url value="/siges/gestionAdministrativa/cierreVigencia/CierreVigencia.do"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_DUPLI_PLAN_ESTUD}"/>'>
		<input type="hidden" name="cmd" value=''>
	    <input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
	    <input type="hidden" name="advertencia" id="advertencia"  value='<c:out value="${requestScope.advertencia }"/>' >
	    
	   <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
	   <caption>Duplicar Plan Estudio Vigencias Anteriores</caption>
	    <tr><td>&nbsp;</td></tr>
	    <tr>
	      <td width="100px">Vigencia desde la cual se quiere copiar el plan de estudios&nbsp;&nbsp;&nbsp;</td>
	      <td>
	      <select name="vigenciaOrigen" id="vigenciaOrigen" style="width: 60px"> 
	       <option value="-99" >-- // --</option>
	        <c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
	             <option value='<c:out value="${vig.codigo}"/>'   '<c:if test="${requestScope.vigenciaOrigen == vig.codigo }">' SELECTED '</c:if>'  > <c:out value="${vig.codigo}"/></option>
	           </c:forEach>
	          </select>
	        </td>
	       <td width="100px">Vigencia en la cual se quiere duplicar&nbsp;&nbsp;&nbsp;</td>
	      <td>
	       <select name="vigenciaDestino" id="vigenciaDestino" style="width: 60px"> 
	       <option value="-99" >-- // --</option>
	        <c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
	             <option value='<c:out value="${vig.codigo}"/>'   '<c:if test="${requestScope.vigenciaDestino == vig.codigo }">' SELECTED '</c:if>'  > <c:out value="${vig.codigo}"/></option>
	           </c:forEach>
	          </select>
	        </td>
	    </tr>
	    <tr>
	      <td colspan="4">Para duplicar la información haga click <a href="javaScript:duplicar()" style="font-size:1.3em" style="font-weight:bold">Aquí</a>.</td>
	    </tr>
	       <tr>
	      <td>&nbsp;</td>
	    </tr>
	     <tr><td align="center" width="54%" colspan="4">
                <span align = "center"  id="txtmsg" name="txtmsg" style="font-weight:bold;font-size:10pt;color: #FF6666"> </span>
		     </td>
          </tr> 
		  <tr>
			<td style="display:none"><iframe name="frame" id="frame"></iframe></td>
		  </tr>
		  <tr>
			  <td align="center" colspan="4" >
			  	   <span align = "center"  id="timemsg" name="timemsg" style="font-size:8pt;color: #FF6666"> </span>
		      </td>
		 </tr>
        <tr><td align="center" colspan="2">
			  	   <span align = "center"  id="spinerss" name="spinerss" style="font-size:8pt;color: #FF6666"> </span>
		      </td>
		 </tr>
	  </table>	
	</form>	
	<script type="text/javascript">
 
	</script>
</body>
 <script>
 document.frmNuevo.advertencia.value = -1;
  <c:if test="${requestScope.advertencia == 1 &&  !empty requestScope.msgAdvertencia }">
    if(confirm('<c:out value="${requestScope.msgAdvertencia }"/>' )){
      document.frmNuevo.advertencia.value = 3;
      duplicar();
    }else{
     document.frmNuevo.advertencia.value = -1;
    }
  </c:if>
  
 </script>
</html>