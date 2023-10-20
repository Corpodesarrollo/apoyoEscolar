<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="periodoPrgfechasVO" class="siges.adminParamsInst.vo.PeriodoPrgfechasVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.adminParamsInst.vo.ParamsVO" scope="page"/>
<html>
<head>
<style type="text/css">@import url('<c:url value="/etc/css/calendar-win2k-1.css"/>');</style>
<script type="text/javascript" src="<c:url value="/etc/js/calendar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/lang/calendar-es.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/calendar-setup.js"/>"></script>
<script languaje="javaScript">
	function hacerValidaciones_frmNuevo(forma){
	  
// periodo 1
	 if(forma.prf_cierrep1_){
	  if(forma.prf_cierrep1_.checked == true){
	 	 forma.prf_cierrep1.value = 1;
	 	 validarCampo(forma.prf_ini_per1,'- Fecha Inicial');
	 	 validarCampo(forma.prf_fin_per1,'- Fecha Final');
	 	 validarRangoFechasPeriodo(forma.prf_ini_per1,forma.prf_fin_per1,'La fecha final no puede ser inferior o igual a la fecha inicial para el periodo 1.' );
		    if(forma.prf_alertap1_.checked == true){
		 	 forma.prf_alertap1.value = 1;
		 	 validarEntero(forma.prf_diasp1,'- Dias aviso',1,99);
		   }else{
		    forma.prf_alertap1.value = 0;
		   }
	   }else{
	    forma.prf_cierrep1.value = 0;
	    forma.prf_alertap1.value = 0;
	   }
	  } 
	 
	 
	 // periodo 2
	 if(forma.prf_cierrep2_){
	  if(forma.prf_cierrep2_.checked == true){
	 	 forma.prf_cierrep2.value = 1;
	 	 validarCampo(forma.prf_ini_per2,'- Fecha Inicial');
	 	 validarCampo(forma.prf_fin_per2,'- Fecha Final');
	 	 validarRangoFechasPeriodo(forma.prf_fin_per1,forma.prf_ini_per2,'- La fecha incial del periodo 2 no puede ser inferior o igual a la fecha final del periodo 1.' );
	 	 validarRangoFechasPeriodo(forma.prf_ini_per2,forma.prf_fin_per2,'La fecha final no puede ser inferior o igual a la fecha inicial para el periodo 2.' );
		    if(forma.prf_alertap2_.checked == true){
		 	 forma.prf_alertap2.value = 1;
		 	 validarEntero(forma.prf_diasp2,'- Dias aviso (1 - 99 dias)',1,99);
		   }else{
		    forma.prf_alertap2.value = 0;
		   }
	   }else{
	    forma.prf_cierrep2.value = 0;
	    forma.prf_alertap2.value = 0;
	   }
	   }
	   	 // periodo 3
	  if(forma.prf_cierrep3_){ 	 
	  if(forma.prf_cierrep3_.checked == true){
	 	 forma.prf_cierrep3.value = 1;
	 	 validarCampo(forma.prf_ini_per3,'- Fecha Inicial');
	 	 validarCampo(forma.prf_fin_per3,'- Fecha Final');
	 	  validarRangoFechasPeriodo(forma.prf_fin_per2,forma.prf_ini_per3,'- La fecha inicial del periodo 3 no puede ser inferior o igual a la fecha final del periodo 2.' );
	 	  validarRangoFechasPeriodo(forma.prf_ini_per3,forma.prf_fin_per3,'- La fecha final no puede ser inferior o igual a la fecha inicial para el periodo 3.' );
		    if(forma.prf_alertap3_.checked == true){
		 	 forma.prf_alertap3.value = 1;
		 	 validarEntero(forma.prf_diasp3,'- Dias aviso (1 - 99 dias)',1,99);
		   }else{
		    forma.prf_alertap3.value = 0;
		   }
	   }else{
	    forma.prf_cierrep3.value = 0;
	    forma.prf_alertap3.value = 0;
	   }
	  }
	    
	    	   	 // periodo 4
	  if(forma.prf_cierrep4_){
	  if(forma.prf_cierrep4_.checked == true){
	 	 forma.prf_cierrep4.value = 1;
	 	 validarCampo(forma.prf_ini_per4,'- Fecha Inicial');
	 	 validarCampo(forma.prf_fin_per4,'- Fecha Final');
	 	 validarRangoFechasPeriodo(forma.prf_fin_per3,forma.prf_ini_per4,'- La fecha inicial del periodo 4 no puede ser inferior o igual a la fecha final del periodo 3' );
	 	 validarRangoFechasPeriodo(forma.prf_ini_per4,forma.prf_fin_per4,'- La fecha final no puede ser inferior o igual a la fecha inicial para el periodo 4.' );
		    if(forma.prf_alertap4_.checked == true){
		 	 forma.prf_alertap4.value = 1;
		 	 validarEntero(forma.prf_diasp4,'- Dias aviso (1 - 99 dias)',1,99);
		   }else{
		    forma.prf_alertap4.value = 0;
		   }
	    }else{
	     forma.prf_cierrep4.value = 0;
	     forma.prf_alertap4.value = 0;
	    }
	   }
	   	   	 // periodo 5
	  if(forma.prf_cierrep5_){	   	 
	  if(forma.prf_cierrep5_.checked == true){
	 	 forma.prf_cierrep5.value = 1;
	 	 validarCampo(forma.prf_ini_per5,'- Fecha Inicial');
	 	 validarCampo(forma.prf_fin_per5,'- Fecha Final');
	 	validarRangoFechasPeriodo(forma.prf_fin_per4, forma.prf_ini_per5,'- La fecha inicial del periodo 5 no puede ser inferior o igual a la fecha final del periodo 4.' );
	 	 validarRangoFechasPeriodo(forma.prf_ini_per5,forma.prf_fin_per5,'- La fecha final no puede ser inferior o igual a la fecha inicial para el periodo 5.' );
		    if(forma.prf_alertap5_.checked == true){
		 	 forma.prf_alertap5.value = 1;
		 	 validarEntero(forma.prf_diasp5,'- Dias aviso (1 - 99 dias)',1,99);
		   }else{
		    forma.prf_alertap5.value = 0;
		   }
	    }else{
	     forma.prf_cierrep5.value = 0;
	     forma.prf_alertap5.value = 0;
	    }
	   }
	   	   	 // periodo 6
	  if(forma.prf_cierrep6_){
	  if(forma.prf_cierrep6_.checked == true){
	 	 forma.prf_cierrep6.value = 1;
	 	 validarCampo(forma.prf_ini_per6,'- Fecha Inicial');
	 	 validarCampo(forma.prf_fin_per6,'- Fecha Final');
	 	 validarRangoFechasPeriodo(forma.prf_fin_per5, forma.prf_ini_per6,'- La fecha inicial del periodo 6 no puede ser inferior o igual a la fecha final del periodo 5.' );
	 	 validarRangoFechasPeriodo(forma.prf_ini_per6,forma.prf_fin_per6,'- La fecha final no puede ser inferior o igual a la fecha inicial para el periodo 6.' );
		    if(forma.prf_alertap6_.checked == true){
		 	 forma.prf_alertap6.value = 1;
		 	 validarEntero(forma.prf_diasp6,'- Dias aviso (1 - 99 dias)',1,99);
		   }else{
		    forma.prf_alertap6.value = 0;
		   }
	   }else{
	    forma.prf_cierrep6.value = 0;
	    forma.prf_alertap6.value = 0;
	   }
	   }
	   	   	   	 // periodo 7
	  if(forma.prf_cierrep7_){
	  if(forma.prf_cierrep7_.checked == true){
	 	 forma.prf_cierrep7.value = 1;
	 	 validarCampo(forma.prf_ini_per7,'- Fecha Inicial');
	 	 validarCampo(forma.prf_fin_per7,'- Fecha Final');
	 	 if(forma.prf_fin_per6){
	 	 validarRangoFechasPeriodo(forma.prf_fin_per6,forma.prf_ini_per7,'- La fecha inicial del periodo 7 no pueder ser inferior o igual a la fecha final del periodo 6.' );
	 	 }else if(forma.prf_fin_per5){
	 	  validarRangoFechasPeriodo(forma.prf_fin_per5,forma.prf_ini_per7,'- La fecha inicial del periodo 7 no pueder ser inferior o igual a la fecha final del periodo 5.' );
	 	 }else if(forma.prf_fin_per4){
	 	  validarRangoFechasPeriodo(forma.prf_fin_per4,forma.prf_ini_per7,'- La fecha inicial del periodo 7 no pueder ser inferior o igual a la fecha final del periodo 4.' );
	 	 }else if(forma.prf_fin_per3){
	 	  validarRangoFechasPeriodo(forma.prf_fin_per3,forma.prf_ini_per7,'- La fecha inicial del periodo 7 no pueder ser inferior o igual a la fecha final del periodo 3.' );
	 	 }else if(forma.prf_fin_per2){
	 	  validarRangoFechasPeriodo(forma.prf_fin_per2,forma.prf_ini_per7,'- La fecha inicial del periodo 7 no pueder ser inferior o igual a la fecha final del periodo 2.' );
	 	 }else if(forma.prf_fin_per1){
	 	  validarRangoFechasPeriodo(forma.prf_fin_per1,forma.prf_ini_per7,'- La fecha inicial del periodo 7 no pueder ser inferior o igual a la fecha final del periodo 1.' );
	 	 }
	 	 
	 	 validarRangoFechasPeriodo(forma.prf_ini_per7,forma.prf_fin_per7,'- La fecha final  no puede ser inferior o igual a la fecha inicial para el periodo 7.' );
		    if(forma.prf_alertap7_.checked == true){
		 	 forma.prf_alertap7.value = 1;
		 	 validarEntero(forma.prf_diasp7,'- Dias aviso (1 - 99 dias)',1,99);
		   }else{
		    forma.prf_alertap7.value = 0;
		   }
	    }else{
	     forma.prf_cierrep7.value = 0;
	     forma.prf_alertap7.value = 0;
	    }
	   }
	   
	   
	   
	}
		
	function guardar(){
		if(validarForma(document.frmNuevo)){
			  document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			  document.frmNuevo.submit();
		  
		}//if(validarForma(document.frmNuevo)){
	}
	
	
	function lanzar(tipo){				
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.cmd.value=6;
				document.frmNuevo.action = '<c:url value="/grupoPeriodo/ControllerAdminEdit.do"/>';
				document.frmNuevo.target = ""; 
				document.frmNuevo.submit();
			}
			
	function lanzar_(tipo){				
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.cmd.value='-1';
				document.frmNuevo.action = '<c:url value="/adminParamsInst/Nuevo.do"/>';
				document.frmNuevo.target = ""; 
				document.frmNuevo.submit();
			}
			
	function cancelar(){
				location.href='<c:url value="/bienvenida.jsp"/>';
	} 
	
	function habilitarCkdDia(perf){
     if(document.getElementById('prf_cierrep'+ perf +'_') != null ){
	   if(document.getElementById('prf_cierrep'+ perf +'_').checked == true){
	    document.getElementById('prf_alertap'+ perf +'_').disabled = false;
	    document.getElementById('prf_ini_per'+ perf +'').disabled =false;
	    document.getElementById('prf_ini_per'+ perf +'_').style.display = '';
	    document.getElementById('prf_fin_per'+ perf +'').disabled =false;
   	    document.getElementById('prf_fin_per'+ perf +'_').style.display = '';
	   }else{
	    document.getElementById('prf_alertap'+ perf +'_').disabled = true; 
	    //document.getElementById('prf_ini_per'+ perf +'').disabled =true;
	    //document.getElementById('prf_ini_per'+ perf +'_').style.display = 'none';
	    //document.getElementById('prf_fin_per'+ perf +'').disabled =true;
	    //document.getElementById('prf_fin_per'+ perf +'_').style.display = 'none';
	    document.getElementById('prf_alertap'+ perf +'_').checked = false;
	   }
	  }else{
 
	  }
	  habilitarTxtdDia(perf);
	}
	
	
	function habilitarTxtdDia(perf){
	 if(document.getElementById('prf_alertap' + perf + '_') != null ){
	   if(document.getElementById('prf_alertap' + perf + '_').checked == true){
	     document.getElementById('prf_diasp' + perf + '').disabled = false;
	   }else{
	    document.getElementById('prf_diasp' + perf + '').value = "0";
	    document.getElementById('prf_diasp' + perf + '').disabled = true;
	   }
	 }
	}
	 
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
 
	<form method="post" name="frmNuevo"  action='<c:url value="/adminParametroInst/Save.jsp"/>'>
		
		 
		 <table border="0" align="center" bordercolor="#FFFFFF" width="95%">
		<caption>Gestión Administrativa >> Parámetros >> Programar Periodos</caption>
			<tr>
			  <td   bgcolor="#FFFFFF">
					<input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar()">
					<input class='boton' name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
			  </td>
			</tr>
		</table>
	
	     <input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_PER_PROG_FECHAS}"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="msgValidarNivelEval"  id="msgValidarNivelEval" value='<c:out value="${instParVO.msgValidarNivelEval}"/>'>
		<input type="hidden" name="insparnivevalAntes"  id="insparnivevalAntes" value='<c:out value="${instParVO.insparniveval}"/>'>
		<input type="hidden" name="eliminarCascada"  id="eliminarCascada" value='<c:out value="${instParVO.eliminarCascada}"/>'>
		
		
		 
		 <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		    <tr height="1">
			
			 <td width="10" bgcolor="#FFFFFF">&nbsp;</td>							
			 <td rowspan="2" bgcolor="#FFFFFF">
			       <a href="javaScript:lanzar(1)"><img src='<c:url value="/etc/img/tabs/validacion_horarios_0.gif"/>' alt="Parametros" height="26" border="0"></a>
				   <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_PERIODO }" />')"><img src='<c:url value="/etc/img/tabs/adm_periodos_0.gif"/>' alt="Parametros " height="26" border="0"></a>
			       <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_INST_NIVEL_EVAL }" />')"><img src='<c:url  value="/etc/img/tabs/adm_nivelEval_0.gif"/>' alt="Nivel Evaluación " height="26" border="0"></a>
			       <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_ESCL_CONCEPT }" />')"><img src='<c:url value="/etc/img/tabs/adm_escalaConpt_0.gif"/>' alt="Escala Conceptual" height="26" border="0"></a>
			       <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_ESCL_NUM }" />')"><img src='<c:url value="/etc/img/tabs/adm_escalaNum_0.gif"/>' alt="Escala Numérical" height="26" border="0"></a>
			       <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_PER_PROG_FECHAS }" />')"><img src='<c:url value="/etc/img/tabs/adm_perProgFechas_1.gif"/>' alt="Programar Periodos" height="26" border="0"></a>
		           <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_PONDERACION_POR_PERIODO }" />')"><img src='<c:url value="/etc/img/tabs/adm_ponporperiodo_0.png"/>' alt="Ponderación por periodos" height="26" border="0"></a>
			 
		        </td>
            </tr>
          </table>
		  <table border="0" align="center" width="95%" cellpadding="2" cellspacing="2">
	       <tr><td align="center">Vigencia &nbsp;<c:out value="${periodoPrgfechasVO.prfvigencia}"/></td></tr>
		  </table>	
			<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
			 	<caption>Programar periodos</caption>
			 		<c:if test="${sessionScope.login.logNumPer  > 1}">
			 		<tr> 
						<th class="EncabezadoColumna" align="center">Periodo</th>
						<th class="EncabezadoColumna" align="center">Fecha Inicial</th>
						<th class="EncabezadoColumna" align="center">Fecha Final</th>
						<th class="EncabezadoColumna" align="center">Cierre <br>Automático</th>
						<th class="EncabezadoColumna" align="center">Generar Alerta</th>
						<th class="EncabezadoColumna" align="center">Días Aviso</th>
					</tr>
					</c:if>
					<c:if test="${sessionScope.login.logNumPer >= 1}">
					<tr>
						<td class='Fila1' align="center">1</td> 
						<td class='Fila1' align="center">
						<input type="text" name="prf_ini_per1" id="prf_ini_per1" readonly="readonly" maxlength="10" size="10" value='<c:out value="${periodoPrgfechasVO.prf_ini_per1 }"/>'>
						<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="prf_ini_per1_" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
						</td> 
						<td class='Fila1' align="center"><input type="text" name="prf_fin_per1" id="prf_fin_per1" readonly="readonly" maxlength="10" size="10" value='<c:out value="${periodoPrgfechasVO.prf_fin_per1 }"/>'><img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="prf_fin_per1_" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" /></td> 
						<td class='Fila1' align="center"><input type="checkbox"  name="prf_cierrep1_" id="prf_cierrep1_" onclick="habilitarCkdDia(1);" <c:if test="${periodoPrgfechasVO.prf_cierrep1 == 1}">checked="checked"</c:if> ><input type="hidden"  name="prf_cierrep1" id="prf_cierrep1" ></td>
					    <td class='Fila1' align="center"><input type="checkbox"  name="prf_alertap1_" id="prf_alertap1_" onclick="habilitarTxtdDia(1);"  <c:if test="${periodoPrgfechasVO.prf_alertap1 == 1}">checked="checked"</c:if> > <input  type="hidden"  name="prf_alertap1" id="prf_alertap1" ></td>
						<td class='Fila1' align="center"><input type="text" maxlength="2" size="2" name="prf_diasp1" id="prf_diasp1" value='<c:out value="${periodoPrgfechasVO.prf_diasp1 }"/>' ></td> 
                    </tr>
					</c:if>
					<c:if test="${sessionScope.login.logNumPer  >= 2}">

                    <tr>
						<td class='Fila0' align="center">2</td> 
						<td class='Fila0' align="center"><input type="text"  name="prf_ini_per2" id="prf_ini_per2" readonly="readonly" maxlength="10" size="10" value='<c:out value="${periodoPrgfechasVO.prf_ini_per2 }"/>'><img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="prf_ini_per2_" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" /></td> 
						<td class='Fila0' align="center"><input type="text"  name="prf_fin_per2" id="prf_fin_per2" readonly="readonly" maxlength="10" size="10" value='<c:out value="${periodoPrgfechasVO.prf_fin_per2 }"/>'><img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="prf_fin_per2_" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" /></td> 
						<td class='Fila0' align="center"><input type="checkbox"  name="prf_cierrep2_" id="prf_cierrep2_"  onclick="habilitarCkdDia(2);" <c:if test="${periodoPrgfechasVO.prf_cierrep2 == 1}">checked="checked"</c:if> ><input type="hidden" name="prf_cierrep2" id="prf_cierrep2" > </td>
					    <td class='Fila0' align="center"><input type="checkbox"  name="prf_alertap2_" id="prf_alertap2_" onclick="habilitarTxtdDia(2);"  <c:if test="${periodoPrgfechasVO.prf_alertap2 == 1}">checked="checked"</c:if> ><input type="hidden" name="prf_alertap2" id="prf_alertap2" > </td>
						<td class='Fila0' align="center"><input type="text" maxlength="2" size="2" name="prf_diasp2" id="prf_diasp2" value='<c:out value="${periodoPrgfechasVO.prf_diasp2 }"/>' ></td> 
                    </tr>
					</c:if>
					<c:if test="${sessionScope.login.logNumPer >= 3 }">

                    <tr>
						<td class='Fila1' align="center">3</td> 
						<td class='Fila1' align="center"><input type="text"  name="prf_ini_per3" id="prf_ini_per3" readonly="readonly" maxlength="10" size="10" value='<c:out value="${periodoPrgfechasVO.prf_ini_per3 }"/>'><img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="prf_ini_per3_" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" /></td> 
						<td class='Fila1' align="center"><input type="text"  name="prf_fin_per3" id="prf_fin_per3" readonly="readonly" maxlength="10" size="10" value='<c:out value="${periodoPrgfechasVO.prf_fin_per3 }"/>'><img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="prf_fin_per3_" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" /></td> 
						<td class='Fila1' align="center"><input type="checkbox"   name="prf_cierrep3_" id="prf_cierrep3_" onclick="habilitarCkdDia(3);" <c:if test="${periodoPrgfechasVO.prf_cierrep3 == 1}">checked="checked"</c:if> ><input type="hidden"  name="prf_cierrep3" id="prf_cierrep3"></td>
					    <td class='Fila1' align="center"><input type="checkbox"  name="prf_alertap3_" id="prf_alertap3_" onclick="habilitarTxtdDia(3);" <c:if test="${periodoPrgfechasVO.prf_alertap3 == 1}">checked="checked"</c:if> ><input type="hidden"  name="prf_alertap3" id="prf_alertap3" ></td>
						<td class='Fila1' align="center"><input type="text" maxlength="2" size="2" name="prf_diasp3" id="prf_diasp3" value='<c:out value="${periodoPrgfechasVO.prf_diasp3 }"/>' ></td> 
                    </tr>
					</c:if>
					<c:if test="${sessionScope.login.logNumPer  >= 4}">

                    <tr>
						<td class='Fila0' align="center">4</td> 
						<td class='Fila0' align="center"><input type="text"  name="prf_ini_per4" id="prf_ini_per4" readonly="readonly" maxlength="10" size="10" value='<c:out value="${periodoPrgfechasVO.prf_ini_per4 }"/>'><img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="prf_ini_per4_" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" /></td> 
						<td class='Fila0' align="center"><input type="text"  name="prf_fin_per4" id="prf_fin_per4" readonly="readonly" maxlength="10" size="10" value='<c:out value="${periodoPrgfechasVO.prf_fin_per4 }"/>'><img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="prf_fin_per4_" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" /></td> 
						<td class='Fila0' align="center"><input type="checkbox"  name="prf_cierrep4_" id="prf_cierrep4_" onclick="habilitarCkdDia(4);" <c:if test="${periodoPrgfechasVO.prf_cierrep4 == 1}">checked="checked"</c:if> ><input type="hidden"  name="prf_cierrep4" id="prf_cierrep4"  ></td>
					    <td class='Fila0' align="center"><input type="checkbox"  name="prf_alertap4_" id="prf_alertap4_" onclick="habilitarTxtdDia(4);" <c:if test="${periodoPrgfechasVO.prf_alertap4 == 1}">checked="checked"</c:if> ><input type="hidden"  name="prf_alertap4" id="prf_alertap4"  ></td>
						<td class='Fila0' align="center"><input type="text" maxlength="2" size="2" name="prf_diasp4" id="prf_diasp4" value='<c:out value="${periodoPrgfechasVO.prf_diasp4 }"/>' ></td> 
                    </tr>
					</c:if>
					<c:if test="${sessionScope.login.logNumPer  >= 5}">

                    <tr>
						<td class='Fila1' align="center">5</td> 
						<td class='Fila1' align="center"><input type="text"  name="prf_ini_per5" id="prf_ini_per5" readonly="readonly" maxlength="10" size="10" value='<c:out value="${periodoPrgfechasVO.prf_ini_per5 }"/>'><img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="prf_ini_per5_" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" /></td> 
						<td class='Fila1' align="center"><input type="text"  name="prf_fin_per5" id="prf_fin_per5" readonly="readonly" maxlength="10" size="10" value='<c:out value="${periodoPrgfechasVO.prf_fin_per5 }"/>'><img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="prf_fin_per5_" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" /></td> 
						<td class='Fila1' align="center"><input type="checkbox"  name="prf_cierrep5_" id="prf_cierrep5_" onclick="habilitarCkdDia(5);"  <c:if test="${periodoPrgfechasVO.prf_cierrep5 == 1}">checked="checked"</c:if> ><input type="hidden"  name="prf_cierrep5" id="prf_cierrep5" ></td>
					    <td class='Fila1' align="center"><input type="checkbox"  name="prf_alertap5_" id="prf_alertap5_" onclick="habilitarTxtdDia(5);" <c:if test="${periodoPrgfechasVO.prf_alertap5 == 1}">checked="checked"</c:if> ><input type="hidden"  name="prf_alertap5" id="prf_alertap5" ></td>
						<td class='Fila1' align="center"><input type="text" maxlength="2" size="2" name="prf_diasp5" id="prf_diasp5" value='<c:out value="${periodoPrgfechasVO.prf_diasp5 }"/>' ></td> 
                    </tr>
					</c:if>
					<c:if test="${sessionScope.login.logNumPer  >= 6}">

                    <tr>
						<td class='Fila0' align="center">6</td> 
						<td class='Fila0' align="center"><input type="text"  name="prf_ini_per6" id="prf_ini_per6" readonly="readonly" maxlength="10" size="10" value='<c:out value="${periodoPrgfechasVO.prf_ini_per6 }"/>'><img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="prf_ini_per6_" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" /></td> 
						<td class='Fila0' align="center"><input type="text"  name="prf_fin_per6" id="prf_fin_per6" readonly="readonly" maxlength="10" size="10" value='<c:out value="${periodoPrgfechasVO.prf_fin_per6 }"/>'><img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="prf_fin_per6_" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" /></td> 
						<td class='Fila0' align="center"><input type="checkbox"  name="prf_cierrep6_" id="prf_cierrep6_" onclick="habilitarCkdDia(6);"  <c:if test="${periodoPrgfechasVO.prf_cierrep6 == 1}">checked="checked"</c:if> ><input type="hidden"  name="prf_cierrep6" id="prf_cierrep6" ></td>
					    <td class='Fila0' align="center"><input type="checkbox"  name="prf_alertap6_" id="prf_alertap6_" onclick="habilitarTxtdDia(6);" <c:if test="${periodoPrgfechasVO.prf_alertap6 == 1}">checked="checked"</c:if> ><input type="hidden"  name="prf_alertap6" id="prf_alertap6" ></td>
						<td class='Fila0' align="center"><input type="text" maxlength="2" size="2" name="prf_diasp6" id="prf_diasp6" value='<c:out value="${periodoPrgfechasVO.prf_diasp6 }"/>' ></td> 
                    </tr>
					</c:if>

                    <tr>
						<td class='Fila1' align="center">Final</td> 
						<td class='Fila1' align="center"><input type="text"  name="prf_ini_per7" id="prf_ini_per7" readonly="readonly" maxlength="10" size="10" value='<c:out value="${periodoPrgfechasVO.prf_ini_per7 }"/>'><img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="prf_ini_per7_" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" /></td> 
						<td class='Fila1' align="center"><input type="text"  name="prf_fin_per7" id="prf_fin_per7" readonly="readonly" maxlength="10" size="10" value='<c:out value="${periodoPrgfechasVO.prf_fin_per7 }"/>'><img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="prf_fin_per7_" style='cursor:pointer;' title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" /></td> 
						<td class='Fila1' align="center"><input type="checkbox"  name="prf_cierrep7_" id="prf_cierrep7_" onclick="habilitarCkdDia(7);" <c:if test="${periodoPrgfechasVO.prf_cierrep7 == 1}">checked="checked"</c:if> ><input type="hidden"  name="prf_cierrep7" id="prf_cierrep7"  ></td>
					    <td class='Fila1' align="center"><input type="checkbox"  name="prf_alertap7_" id="prf_alertap7_" onclick="habilitarTxtdDia(7);" <c:if test="${periodoPrgfechasVO.prf_alertap7 == 1}">checked="checked"</c:if> ><input type="hidden"  name="prf_alertap7" id="prf_alertap7"  ></td>
						<td class='Fila1' align="center"><input type="text" maxlength="2" size="2" name="prf_diasp7" id="prf_diasp7" value='<c:out value="${periodoPrgfechasVO.prf_diasp7 }"/>' ></td> 
                    </tr>
				 
                                                                               
			</table>
	 
	</form>
</body>
<script type="text/javascript"> 
<c:if test="${sessionScope.login.logNumPer  >= 1}">
 Calendar.setup({inputField:"prf_ini_per1",ifFormat:"%d/%m/%Y",button:"prf_ini_per1_",align:"Br"});
 Calendar.setup({inputField:"prf_fin_per1",ifFormat:"%d/%m/%Y",button:"prf_fin_per1_",align:"Br"});
</c:if>

<c:if test="${sessionScope.login.logNumPer  >= 2}">
 Calendar.setup({inputField:"prf_ini_per2",ifFormat:"%d/%m/%Y",button:"prf_ini_per2_",align:"Br"});
 Calendar.setup({inputField:"prf_fin_per2",ifFormat:"%d/%m/%Y",button:"prf_fin_per2_",align:"Br"});
</c:if>

<c:if test="${sessionScope.login.logNumPer  >= 3}">
 Calendar.setup({inputField:"prf_ini_per3",ifFormat:"%d/%m/%Y",button:"prf_ini_per3_",align:"Br"});
 Calendar.setup({inputField:"prf_fin_per3",ifFormat:"%d/%m/%Y",button:"prf_fin_per3_",align:"Br"});
</c:if>

<c:if test="${sessionScope.login.logNumPer  >= 4}">
 Calendar.setup({inputField:"prf_ini_per4",ifFormat:"%d/%m/%Y",button:"prf_ini_per4_",align:"Br"});
 Calendar.setup({inputField:"prf_fin_per4",ifFormat:"%d/%m/%Y",button:"prf_fin_per4_",align:"Br"});
</c:if>

<c:if test="${sessionScope.login.logNumPer  >= 5}">
 Calendar.setup({inputField:"prf_ini_per5",ifFormat:"%d/%m/%Y",button:"prf_ini_per5_",align:"Br"});
 Calendar.setup({inputField:"prf_fin_per5",ifFormat:"%d/%m/%Y",button:"prf_fin_per5_",align:"Br"});
</c:if>

<c:if test="${sessionScope.login.logNumPer  >= 6}">
 Calendar.setup({inputField:"prf_ini_per6",ifFormat:"%d/%m/%Y",button:"prf_ini_per6_",align:"Br"});
 Calendar.setup({inputField:"prf_fin_per6",ifFormat:"%d/%m/%Y",button:"prf_fin_per6_",align:"Br"});
</c:if>
Calendar.setup({inputField:"prf_ini_per7",ifFormat:"%d/%m/%Y",button:"prf_ini_per7_",align:"Br"});
Calendar.setup({inputField:"prf_fin_per7",ifFormat:"%d/%m/%Y",button:"prf_fin_per7_",align:"Br"});



habilitarCkdDia(1);
habilitarTxtdDia(1);

habilitarCkdDia(2);
habilitarTxtdDia(2);

habilitarCkdDia(3);
habilitarTxtdDia(3);

habilitarCkdDia(4);
habilitarTxtdDia(4);

habilitarCkdDia(5);
habilitarTxtdDia(5);

habilitarCkdDia(6);
habilitarTxtdDia(6);

habilitarCkdDia(7);
habilitarTxtdDia(7);

habilidarSoloAbiertos();
function habilidarSoloAbiertos(){
var perd = 0;

 <c:if test="${ periodoPrgfechasVO.perestado1 == 1}">
  perd = 1;
  if(document.getElementById('prf_cierrep' + perd + '_')){
    document.getElementById('prf_cierrep' + perd + '_').disabled = true;
    document.getElementById('prf_alertap' + perd + '_').disabled = true;
    document.getElementById('prf_diasp' + perd + '').disabled = true;
    document.getElementById('prf_ini_per'+ perd +'_').style.display = 'none';
    document.getElementById('prf_fin_per'+ perd +'_').style.display = 'none';
   }  
 </c:if>
 
 <c:if test="${ periodoPrgfechasVO.perestado2 == 1}">
  perd = 2;
  if(document.getElementById('prf_cierrep' + perd + '_')){
    document.getElementById('prf_cierrep' + perd + '_').disabled = true;
    document.getElementById('prf_alertap' + perd + '_').disabled = true;
    document.getElementById('prf_diasp' + perd + '').disabled = true;
    document.getElementById('prf_ini_per'+ perd +'_').style.display = 'none';
    document.getElementById('prf_fin_per'+ perd +'_').style.display = 'none';
 
   }  
 </c:if>
 
 
  <c:if test="${ periodoPrgfechasVO.perestado3 == 1}">
  perd = 3;
  if(document.getElementById('prf_cierrep' + perd + '_')){
    document.getElementById('prf_cierrep' + perd + '_').disabled = true;
    document.getElementById('prf_alertap' + perd + '_').disabled = true;
    document.getElementById('prf_diasp' + perd + '').disabled = true;
    document.getElementById('prf_ini_per'+ perd +'_').style.display = 'none';
    document.getElementById('prf_fin_per'+ perd +'_').style.display = 'none';
  }  
 </c:if>
 
 
  <c:if test="${ periodoPrgfechasVO.perestado4 == 1}">
  perd = 4;
  if(document.getElementById('prf_cierrep' + perd + '_')){
    document.getElementById('prf_cierrep' + perd + '_').disabled = true;
    document.getElementById('prf_alertap' + perd + '_').disabled = true;
    document.getElementById('prf_diasp' + perd + '').disabled = true;
    document.getElementById('prf_ini_per'+ perd +'_').style.display = 'none';
    document.getElementById('prf_fin_per'+ perd +'_').style.display = 'none';
   }  
 </c:if>
 
  <c:if test="${ periodoPrgfechasVO.perestado5 == 1}">
  perd = 5;
  if(document.getElementById('prf_cierrep' + perd + '_')){
    document.getElementById('prf_cierrep' + perd + '_').disabled = true;
    document.getElementById('prf_alertap' + perd + '_').disabled = true;
    document.getElementById('prf_diasp' + perd + '').disabled = true;
    document.getElementById('prf_ini_per'+ perd +'_').style.display = 'none';
    document.getElementById('prf_fin_per'+ perd +'_').style.display = 'none';
 }  
 </c:if>
 
  <c:if test="${ periodoPrgfechasVO.perestado6 == 1}">
  perd = 6;
  if(document.getElementById('prf_cierrep' + perd + '_')){
    document.getElementById('prf_cierrep' + perd + '_').disabled = true;
    document.getElementById('prf_alertap' + perd + '_').disabled = true;
    document.getElementById('prf_diasp' + perd + '').disabled = true;
    document.getElementById('prf_ini_per'+ perd +'_').style.display = 'none';
    document.getElementById('prf_fin_per'+ perd +'_').style.display = 'none';
 }  
 </c:if>
 
  <c:if test="${ periodoPrgfechasVO.perestado7 == 1}">
  perd = 7;
  if(document.getElementById('prf_cierrep' + perd + '_')){
    document.getElementById('prf_cierrep' + perd + '_').disabled = true;
    document.getElementById('prf_alertap' + perd + '_').disabled = true;
    document.getElementById('prf_diasp' + perd + '').disabled = true;
    document.getElementById('prf_ini_per'+ perd +'_').style.display = 'none';
    document.getElementById('prf_fin_per'+ perd +'_').style.display = 'none';
  }  
 </c:if>

}

</script>
</html>