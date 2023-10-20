<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="siges.gestionAdministrativa.actualizarMatricula.vo.ParamsVO" scope="page"/>
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
	var decimas = 0;  
	var segundos = 0;  
	var minutos = 0;
	var ValorCrono = ""  ;
    
	 function SetupTicker() { flag = 0; msg = "... ."; /* msg += "..2 ";  msg += "... 3";msg += "....4 "; */RunTicker();}
	 function RunTicker() {
	  if(stop == 0){ 
	   CronoID = window.setTimeout('RunTicker()',ScrollSpeed);
	   document.getElementById("txtmsg").innerHTML = "ATUALIZANDO";
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
		 decimas++ ; 
		 if ( decimas > 9 ) {  
			 decimas = 0;  
			 segundos++;
			 if ( segundos > 59 ) {  
				 segundos = 0;  
				 minutos++;  
				 if ( minutos > 99 ) {  
					 alert('Fin de la cuenta');  
					 DetenerCrono(); 
					 return true;  
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
    imagen1=new Image;
    imagen1.src='<c:url value="/etc/img/spinner.gif"/>';
  
   }
   
  function actulizar_vista(){
    document.frmNuevo.submit();
    }
    
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));" a>
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
 	 <tr height="1">
		<td width="10">&nbsp;</td>
		<td rowspan="2" width="469"><img src='<c:url value="/etc/img/tabs/actualizarMatr_1.gif"/>' alt="Acta" height="26" border="0"></td>
	 </tr>
	</table>
   
   <form method="post" name="frmNuevo" action='<c:url value="/siges/gestionAdministrativa/actualizacionMatricula/ActualizacionMatricula.do"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_ACTA}"/>'>
		<input type="hidden" name="cmd" value=''>
				<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
	   <caption>Actualizar Matricula</caption>
	    <tr><td>&nbsp;</td></tr>
	    <tr>
	      <td>Vigencia&nbsp;&nbsp;&nbsp;<select name="perVigencia" style="width: 50px"> 
	       <option value="-99" >-- // --</option>
	        <c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
	             <option value='<c:out value="${vig.codigo}"/>' > <c:out value="${vig.codigo}"/></option>
	           </c:forEach>
	          </select>
	        </td>
	    </tr>
	    <tr>
	      <td colspan="4">Para realizar la actualización de matricula haga click <a href="javaScript:actualizar()" style="font-size:1.3em" style="font-weight:bold">Aquí</a>.</td>
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
	    <tr>
	      <td></td>
	    </tr>
	    <tr>
	      <td colspan="4" align="center"> <a href="javaScript:actulizar_vista()" style="font-size:1.1em" style="font-weight:bold">>> ACTUALIZAR ESTADO DE SOLICITUDES <<</a>.
	      </td> 
	    </tr>
	    <tr>
	    <td colspan="4">
	      <table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
					<caption>Solicitudes</caption>
						<c:if test="${empty requestScope.listaTablaHilo}">
							 <tr><th class="Fila1">No existen Solicitudes</th></tr>
						</c:if>
               <c:if test="${!empty requestScope.listaTablaHilo}">
							<tr>
								<th class="EncabezadoColumna">Vigencia</th>
								<th class="EncabezadoColumna">Estado</th> 
								<th class="EncabezadoColumna" width="150px">Fecha</th>
								<th class="EncabezadoColumna">Mensaje</th>
							</tr>
						  <c:forEach begin="0" items="${requestScope.listaTablaHilo}" var="lista" varStatus="st">
							 <tr>
								<td class='Fila<c:out value="${st.count%2}"/>' align="center">&nbsp;&nbsp;<c:out value="${lista.vigencia}"/>&nbsp;&nbsp;</td>
								<td class='Fila<c:out value="${st.count%2}"/>' align="center">&nbsp;&nbsp;<c:out value="${lista.estado}"/>&nbsp;&nbsp;</td>
								 <td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.fechaSistema}"/></td>
								 <td class='Fila<c:out value="${st.count%2}"/>' align="left"><c:out value="${lista.mensaje}"/>&nbsp;</td>
							 </tr>
						  </c:forEach>
						</c:if>
					 </table>
           </td>
	    </tr> 
      <tr>
	    <td colspan="4">
	      <table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
					<caption>Resultado de la ultima Actualización</caption>
						<c:if test="${empty requestScope.listaResultadoVO}">
							<tr><th class="Fila1">No existen datos de resultado</th></tr>
						</c:if>
            <c:if test="${!empty requestScope.listaResultadoVO}">
							<tr>
								<th class="EncabezadoColumna">Tipo</th>
								<th class="EncabezadoColumna">Descripción</th>  
								<th class="EncabezadoColumna">Resultados</th>
								<th class="EncabezadoColumna">Fecha</th>  
							</tr>
						  <c:forEach begin="0" items="${requestScope.listaResultadoVO}" var="lista" varStatus="st">
							 <tr>
								<td class='Fila<c:out value="${st.count%2}"/>' align="center">&nbsp;&nbsp;<c:out value="${lista.tipo}"/>&nbsp;&nbsp;</td>
								<td class='Fila<c:out value="${st.count%2}"/>' align="left" style="width:300px;">&nbsp;&nbsp;<c:out value="${lista.descripcion}"/>&nbsp;&nbsp;</td>
								<td class='Fila<c:out value="${st.count%2}"/>' align="right" >&nbsp;&nbsp;<c:out value="${lista.resultado}"/>&nbsp;&nbsp;</td>
								<td class='Fila<c:out value="${st.count%2}"/>' align="center">&nbsp;&nbsp;<c:out value="${lista.fecha}"/>&nbsp;&nbsp;</td>
								
							 </tr>
						  </c:forEach>
						</c:if>
					 </table>
	             </td>
	         </tr> 
	     <tr><td align="center" colspan="2">
			  	   <span align = "center"  id="spinerss" name="spinerss" style="font-size:8pt;color: #FF6666"> </span>
		      </td>
		 </tr>
	  </table>	
	</form>	
	<script type="text/javascript">
	 /*   Calendar.setup({inputField:"actFecha",ifFormat:"%d/%m/%Y",button:"imgfechaIni",align:"Br"});
	    Calendar.setup({inputField:"actFechaProxima",ifFormat:"%d/%m/%Y",button:"imgfechaFin",align:"Br"});
	*/  
	</script>
</body>
</html>