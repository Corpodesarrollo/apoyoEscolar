<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="siges.gestionAdministrativa.integracionNomina.vo.ParamsVO" scope="page"/>
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
	 validarLista(forma.perVigencia,      "- Vigencia",1);
	}
	function actualizar(){
	  if(validarForma(document.frmNuevo) ){
	  if(confirm("¿Esta seguro de actualizar esta información?.") 
	    && confirm("Usted esta a punto de realizar la actualización de matriculas.\n¿Esta seguro de realizar esta operación?")){
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
   
  function integracionNomina(){
   if(confirm("¿Está seguro de realizar este proceso?")){
     document.frmNuevo.cmd.value =  document.frmNuevo.CMD_CALL_NOMINA.value;
     document.frmNuevo.submit();
   }
  }
    
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));" a>
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
 	 <tr height="1">
		<td width="10">&nbsp;</td>
		<td rowspan="2" width="469"><img src='<c:url value="/etc/img/tabs/adm_integNomina_1.gif"/>' alt="Integración Nómina" height="26" border="0"></td>
	 </tr>
	</table>
   
   <form method="post" name="frmNuevo" action='<c:url value="/siges/gestionAdministrativa/integracionNomina.do"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_INTEGRACION_NOMINA }"/>'>
		<input type="hidden" name="cmd" value=''>
				<input type="hidden" name="CMD_CALL_NOMINA" value='<c:out value="${paramsVO.CMD_CALL_NOMINA}"/>'>
	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
	   <caption>Integración Nómina</caption>
	    <tr><td>&nbsp;</td></tr>
	    <tr><td>Para realizar la integración de nómina por favor haga click <a href="javaScript:integracionNomina()" style="font-size:1.3em" style="font-weight:bold">Aquí</a>.</td></tr>
	  </table>	
	</form>	
	<script type="text/javascript">
	 /*   Calendar.setup({inputField:"actFecha",ifFormat:"%d/%m/%Y",button:"imgfechaIni",align:"Br"});
	    Calendar.setup({inputField:"actFechaProxima",ifFormat:"%d/%m/%Y",button:"imgfechaFin",align:"Br"});
	*/  
	</script>
</body>
</html>