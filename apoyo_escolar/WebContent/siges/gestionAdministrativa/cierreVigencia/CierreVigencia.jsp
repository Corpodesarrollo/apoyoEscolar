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
	 validarLista(forma.perVigencia,      "- Vigencia",1);
	}
	
	function c_c(){
		document.frmNuevo.tipo.value=3;
		document.frmNuevo.cmd.value=9;
		document.frmNuevo.submit();
	}
	function actualizar(){
		var yearCombo = document.getElementById('perVigencia').value;
		var msg='';
		var f = new Date();
		if(yearCombo >= f.getFullYear()){
			alert('El proceso de cierre de vigencia ya fue ejecutado.');
		}else{
			<c:if test="${!empty requestScope.Periodosa}">
			<c:forEach begin="0" items="${requestScope.Periodosa}" var="fila" varStatus="st">	
			<c:if test="${requestScope.nperiodos==1}">
					<c:if test="${fila[1]==0}">
					msg=msg+' -Verfique Sede:<c:out value="${fila[14]}"/> jornada:<c:out value="${fila[15]}"/> tiene periodos sin cerrar \n';
					</c:if>
				</c:if>
				<c:if test="${requestScope.nperiodos==2}">
					<c:if test="${fila[1]==0 || fila[3]==0}">
					
					msg=msg+' -Verfique Sede:<c:out value="${fila[14]}"/> jornada:<c:out value="${fila[15]}"/> tiene periodos sin cerrar \n';
					</c:if>
				</c:if>
				<c:if test="${requestScope.nperiodos==3}">
					<c:if test="${fila[1]==0 || fila[3]==0 || fila[5]==0}">
					msg=msg+' -Verfique Sede:<c:out value="${fila[14]}"/> jornada:<c:out value="${fila[15]}"/> tiene periodos sin cerrar \n';
					</c:if>
				</c:if>
				<c:if test="${requestScope.nperiodos==4}">
					<c:if test="${fila[1]==0 || fila[3]==0 || fila[5]==0 || fila[7]==0}">
					msg=msg+' -Verfique Sede:<c:out value="${fila[14]}"/> jornada:<c:out value="${fila[15]}"/> tiene periodos sin cerrar \n';
					</c:if>
				</c:if>
				<c:if test="${requestScope.nperiodos==5}">
					<c:if test="${fila[1]==0 || fila[3]==0 || fila[5]==0 || fila[7]==0 || fila[9]==0}">
					msg=msg+' -Verfique Sede:<c:out value="${fila[14]}"/> jornada:<c:out value="${fila[15]}"/> tiene periodos sin cerrar \n';
					</c:if>
				</c:if>
				<c:if test="${requestScope.nperiodos==6}">
					<c:if test="${fila[1]==0 || fila[3]==0 || fila[5]==0 || fila[7]==0 || fila[9]==0 || fila[11]==0}">
					msg=msg+' -Verfique Sede:<c:out value="${fila[14]}"/> jornada:<c:out value="${fila[15]}"/> tiene periodos sin cerrar \n';
					</c:if>
				</c:if>
				<c:if test="${requestScope.nperiodos==7}">
					<c:if test="${fila[1]==0 || fila[3]==0 || fila[5]==0 || fila[7]==0 || fila[9]==0 || fila[11]==0 || fila[13]==0}">
					msg=msg+' -Verfique Sede:<c:out value="${fila[14]}"/> jornada:<c:out value="${fila[15]}"/> tiene periodos sin cerrar \n';
					</c:if>
				</c:if>
			</c:forEach>
			</c:if>
			<c:if test="${empty requestScope.Estudiantesn && empty requestScope.Estudiantesp}">
				if (msg==''||msg==null){
				if(validarForma(document.frmNuevo) ){
				  if(confirm("¿Esta seguro de actualizar esta información?.") 
				    && confirm("Usted esta a punto de realizar el cierre de Vigencia.\n¿Esta seguro de realizar esta operación?")){
				    document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			         Run();
			       document.frmNuevo.submit();
				  }
				}}
			</c:if>
			<c:if test="${!empty requestScope.Estudiantesn}">
				<c:forEach begin="0" items="${requestScope.Estudiantesn}" var="fila" varStatus="st">
				 		msg=msg+' -Verfique Colegio:<c:out value="${fila[0]}"/> Sede:<c:out value="${fila[1]}"/> jornada:<c:out value="${fila[2]}"/> grado:<c:out value="${fila[3]}"/> grupo:<c:out value="${fila[4]}"/> tiene estudiantes sin promocion \n';
				</c:forEach>
			</c:if>
			<c:if test="${!empty requestScope.Estudiantesp}">
				<c:forEach begin="0" items="${requestScope.Estudiantesp}" var="fila" varStatus="st">
				 		msg=msg+'-Verfique Colegio:<c:out value="${fila[1]}"/> Sede:<c:out value="${fila[2]}"/> jornada:<c:out value="${fila[3]}"/> grado:<c:out value="${fila[4]}"/> grupo:<c:out value="${fila[5]}"/> tiene estudiantes pendientes \n';
				</c:forEach>
			</c:if>
			if(msg!='')alert(msg)
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
				document.frmNuevo.cmd.value=6;
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
		  <img src='<c:url value="/etc/img/tabs/adm_cierreVig_1.gif"/>' alt="Cierre Vigencia" height="26" border="0">
		  <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_DUPLI_PLAN_ESTUD}" />')"><img src='<c:url value="/etc/img/tabs/adm_duplPlanEstd_0.gif"/>' alt="Escala Conceptual " height="26" border="0"></a>
		</td>
	 </tr>
	</table>
   
   <form method="post" name="frmNuevo" action='<c:url value="/siges/gestionAdministrativa/cierreVigencia/CierreVigencia.do"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_CIERRE_VIG}"/>'>
		<input type="hidden" name="cmd" value=''>
	    <input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
	   <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
	   <caption>Cierre Vigencia</caption>
	    <tr><td>&nbsp;</td></tr>
	    <tr>
	      <td>Vigencia&nbsp;&nbsp;&nbsp;<select name="perVigencia" style="width: 60px" id="perVigencia"> 
	       <option value="-99" >-- // --</option>
	        <c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
	             <option value='<c:out value="${vig.codigo}"/>'   '<c:if test="${requestScope.perVigencia == vig.codigo }">' SELECTED '</c:if>'  > <c:out value="${vig.codigo}"/></option>
	           </c:forEach>
	          </select>
	        </td>
	    </tr>
	    <tr>
	      <td colspan="4">Para realizar el cierre de vigencia haga click <a href="javaScript:actualizar()" style="font-size:1.3em" style="font-weight:bold">Aquí</a>.</td>
	    </tr>
	    <tr>
	      <td colspan="4">Para consultar el detalle de cierre de vigencia haga click <a href='<c:url value="javaScript:c_c()"/>' style="font-size:1.3em" style="font-weight:bold">Aquí</a>.</td>
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
	      <td colspan="4" align="center"> <a href="javaScript:actulizar_vista();" style="font-size:1.1em" style="font-weight:bold">>> ACTUALIZAR ESTADO DE SOLICITUDES <<</a>.
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