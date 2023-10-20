<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="ponderacionPrgPeriodoVO" class="siges.adminParamsInst.vo.PonderacionPorPeriodoVO" scope="session"/>
<jsp:setProperty name="ponderacionPrgPeriodoVO" property="*"/>

<jsp:useBean id="paramsVO" class="siges.adminParamsInst.vo.ParamsVO" scope="page"/>

<%@include file="../parametros.jsp"%>

<c:set var="tip" value="1" scope="page"/>

<c:if test="${sessionScope.niveldeevaluacionactualparaponderizacion==1}">

	<script languaje='javaScript'>
		<!--
		
			var nav4 = window.Event ? true : false;
			
			function acepteNumeros(eve) {
				var key = nav4 ? eve.which : eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
				
			function cancelar() {
				location.href = '<c:url value="/bienvenida.jsp"/>';
			}
	
			function lanzar(tipo){				
				document.frmNuevo.tipo.value = tipo;
				document.frmNuevo.cmd.value = 6;
				document.frmNuevo.action = '<c:url value="/grupoPeriodo/ControllerAdminEdit.do"/>';
				document.frmNuevo.target = ""; 
				document.frmNuevo.submit();
			}
						
			function lanzar_(tipo) {	
				document.frmNuevo.tipo.value = tipo;
				document.frmNuevo.cmd.value = '-1';
				document.frmNuevo.action = '<c:url value="/adminParamsInst/Nuevo.do"/>';
				document.frmNuevo.target = ""; 
				document.frmNuevo.submit();
			}
			
			function guardar() {
			
				if(validarForma(document.frmNuevo)){
					document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
					document.frmNuevo.submit();
				  
				}
			}
				
			function hacerValidaciones_frmNuevo(forma) {
				
				per1 = validarEnteroCeroaCien(forma.prfperiodo1,'- Porcentaje 1er periodo debe estar entre 0% y 100%');
				per2 = validarEnteroCeroaCien(forma.prfperiodo2,'- Porcentaje 2da periodo debe estar entre 0% y 100%');
				per3 = validarEnteroCeroaCien(forma.prfperiodo3,'- Porcentaje 3ra periodo debe estar entre 0% y 100%');
				per4 = validarEnteroCeroaCien(forma.prfperiodo4,'- Porcentaje 4ra periodo debe estar entre 0% y 100%');
				per5 = validarEnteroCeroaCien(forma.prfperiodo5,'- Porcentaje 5ra periodo debe estar entre 0% y 100%');
				per6 = validarEnteroCeroaCien(forma.prfperiodo6,'- Porcentaje 6ra periodo debe estar entre 0% y 100%');
				
				if(per1 && per2 && per3 && per4 && per5 && per6) {
					 sumarPonderaciones(forma.prfperiodo1, forma.prfperiodo2, forma.prfperiodo3, forma.prfperiodo4, forma.prfperiodo5, forma.prfperiodo6, '- La sumatoria de las ponderaciones debe ser igual a 100% o poseer valores de 0%');
				}
	
			}
				
			function sumarPonderaciones(campo1, campo2, campo3, campo4, campo5, campo6, msgError) {
	
				var total = parseInt(trim(campo1.value)) + parseInt(trim(campo2.value)) + parseInt(trim(campo3.value)) + parseInt(trim(campo4.value)) + parseInt(trim(campo5.value)) + parseInt(trim(campo6.value));
				
				if(total == 100) {
					return true;
				}
				
				if(total == 0) {
					return true;
				}
				    
				appendErrorMessage(msgError);
			    
			    if (_campoError == null) {
					_campoError = campo1; 
			        return false;
			    }
				
				return false;
		
			}	
		//-->
	</script>

	<c:import url="/mensaje.jsp"/>

	<body onLoad="mensaje(document.getElementById('msg'));">
	
		<form method="post" name="frmNuevo" action='<c:url value="/adminParametroInst/Save.jsp"/>'>
			
			<br>
			<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
				<caption>Ponderación por periodos</caption>
				<tr>
				  <td width="45%" bgcolor="#FFFFFF">
					<input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar()">
					<input class='boton' name="cmd12_" type="reset" value="Cancelar" >
				  </td>
				</tr>
			</table>
		
			<!--//////////////////-->		
			<!-- FICHAS A MOSTRAR AL USUARIO -->
			<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_PONDERACION_POR_PERIODO}"/>'>
			<input type="hidden" name="cmd" value="">
			<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		    <input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
			<input type="hidden" name="send" value="">
			<input type="hidden" name="adminHorario" value='<c:out value="${sessionScope.adminVO.adminHorario}"/>'>
			<input type="hidden" name="adminInst" value='<c:out value="${sessionScope.login.instId}"/>'>
		
			<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
				<tr height="1">
					<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
					<td rowspan="2" bgcolor="#FFFFFF">
						<a href="javaScript:lanzar(1)"><img src='<c:url value="/etc/img/tabs/validacion_horarios_0.gif"/>' alt="Parametros" height="26" border="0"></a>
					   	<a href="javaScript:lanzar_(1)"><img src='<c:url value="/etc/img/tabs/adm_periodos_0.gif"/>' alt="Parametros" height="26" border="0"></a>
					   	<a href="javaScript:lanzar_(4)"><img src='<c:url value="/etc/img/tabs/adm_nivelEval_0.gif"/>' alt="Nivel Evaluacion" height="26" border="0"></a>
				       	<a href="javaScript:lanzar_(2)"><img src='<c:url value="/etc/img/tabs/adm_escalaConpt_0.gif"/>' alt="Escala Conceptual" height="26" border="0"></a>
		    		   	<a href="javaScript:lanzar_(3)"><img src='<c:url value="/etc/img/tabs/adm_escalaNum_0.gif"/>' alt="Escala Numérica" height="26" border="0"></a>
					   	<a href="javaScript:lanzar_(40)"><img src='<c:url value="/etc/img/tabs/adm_perProgFechas_0.gif"/>' alt="Programar Periodos" height="26" border="0"></a>
					  	<img src='<c:url value="/etc/img/tabs/adm_ponporperiodo_1.png"/>' alt="Ponderación por periodos" height="26" border="0">
					</td>
				</tr>
			</table>
			<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
	
			<table border="0" align="center" width="95%" cellpadding="2" cellspacing="2">
				<caption>PARAMETRIZACIÓN DE LA PONDERACIÓN POR PERIODOS</caption>
				<tr>
					<td align="center">Vigencia Actual:
						<c:out value="${instParVO.insparvigencia}"/>
						<input type="hidden" name="prfvigencia" id="prfvigencia" value='<c:out value="${instParVO.insparvigencia}"/>' >
						<input type="hidden" name="prfcodinst" id="prfcodinst" value='<c:out value="${ponderacionPrgPeriodoVO.prfcodinst}"/>' >
						<input type="hidden" name="prfTipoEvaluacion" id="prfTipoEvaluacion" value='<c:out value="${ponderacionPrgPeriodoVO.prfTipoEvaluacion}"/>' >
					</td>
				</tr>
				<tr>   
					<td colspan="2">
						<c:choose>
							<c:when test="${sessionScope.numeroperiodosparam>0}">			  
								<table border="1" align="center" width="50%" cellpadding="0" cellspacing="0" bordercolor="gray">
									<tr>
							        	<th class="EncabezadoColumna" align="center" width="50%">Periodo</th>
							        	<th class="EncabezadoColumna" align="center"><font color="red">*</font>Promedio periodo</th>
							      	</tr>
							      	
							      	<c:choose>
		                            	<c:when test="${sessionScope.numeroperiodosparam>0}">
		                                	<tr>
							              		<td align="center" class="Fila1">1</td>
							              		<td class="Fila1">
							              			<input type="text" maxlength="4" size="4" name="prfperiodo1" id="prfpromedio1" value='<c:out value="${ponderacionPrgPeriodoVO.prfperiodo1 }" />' onKeyPress='return acepteNumeros(event)' >
							             	</tr>
		                            	</c:when>
		                            	<c:when test="${sessionScope.numeroperiodosparam<=0}">
		                                	<tr style="display: none;">
							              		<td align="center" class="Fila1">1</td>
							              		<td class="Fila1">
							              			<input type="text" maxlength="4" size="4" name="prfperiodo1" id="prfpromedio1" value='<c:out value="0.0"/>' onKeyPress='return acepteNumeros(event)'>
							             	</tr>
		                            	</c:when>
		                          	</c:choose>
		                          
		                           	<c:choose>
		                            	<c:when test="${sessionScope.numeroperiodosparam>1}">
		                                	<tr >
							              		<td align="center" class="Fila0">2</td>
							              		<td class="Fila0">
							              			<input type="text" maxlength="4" size="4" name="prfperiodo2" id="prfpromedio2" value='<c:out value="${ponderacionPrgPeriodoVO.prfperiodo2 }"/>' onKeyPress='return acepteNumeros(event)' >
							             	</tr>
		                            	</c:when>
		                            	<c:when test="${sessionScope.numeroperiodosparam<=1}">
		                                	<tr style="display: none;">
							              		<td align="center" class="Fila0">2</td>
							              		<td class="Fila0">
							              			<input type="text" maxlength="4" size="4" name="prfperiodo2" id="prfpromedio2" value='<c:out value="0.0"/>' onKeyPress='return acepteNumeros(event)'>
							             	</tr>
		                            	</c:when>
		                          	</c:choose>
		                          
		                          	<c:choose>
		                            	<c:when test="${sessionScope.numeroperiodosparam>2}">
		                                	<tr >
							              		<td align="center" class="Fila1">3</td>
							              		<td class="Fila1">
							              			<input type="text" maxlength="4" size="4" name="prfperiodo3" id="prfpromedio3" value='<c:out value="${ponderacionPrgPeriodoVO.prfperiodo3 }"/>' onKeyPress='return acepteNumeros(event)' >
							             	</tr>
		                            	</c:when>
		                            	<c:when test="${sessionScope.numeroperiodosparam<=2}">
		                                	<tr style="display: none;">
							              		<td align="center" class="Fila1">3</td>
							              		<td class="Fila1">
							              			<input type="text" maxlength="4" size="4" name="prfperiodo3" id="prfpromedio3" value='<c:out value="0.0"/>' onKeyPress='return acepteNumeros(event)'>
							             	</tr>
		                            	</c:when>
		                          	</c:choose>
		                          
		                          	<c:choose>
		                            	<c:when test="${sessionScope.numeroperiodosparam>3}">
		                                	<tr >
							              		<td align="center" class="Fila0">4</td>
							              		<td class="Fila0">
							              			<input type="text" maxlength="4" size="4" name="prfperiodo4" id="prfpromedio4" value='<c:out value="${ponderacionPrgPeriodoVO.prfperiodo4 }"/>' onKeyPress='return acepteNumeros(event)'>
							             	</tr>
		                            	</c:when>
		                            	<c:when test="${sessionScope.numeroperiodosparam<=3}">
		                                	<tr style="display: none;">
							              		<td align="center" class="Fila0">4</td>
							              		<td class="Fila0">
							              			<input type="text" maxlength="4" size="4" name="prfperiodo4" id="prfpromedio4" value='<c:out value="0.0"/>' onKeyPress='return acepteNumeros(event)'>
							             	</tr>
		                            	</c:when>
		                          	</c:choose>
		                          
		                          	<c:choose>
		                            	<c:when test="${sessionScope.numeroperiodosparam>4}">
		                                	<tr >
							              		<td align="center" class="Fila1">5</td>
							              		<td class="Fila1">
							              			<input type="text" maxlength="4" size="4" name="prfperiodo5" id="prfpromedio5" value='<c:out value="${ponderacionPrgPeriodoVO.prfperiodo5 }"/>' onKeyPress='return acepteNumeros(event)'>
							             	</tr>
		                            	</c:when>
		                            	<c:when test="${sessionScope.numeroperiodosparam<=4}">
		                                	<tr style="display: none;">
							              		<td align="center" class="Fila1">5</td>
							              		<td class="Fila1">
							              			<input type="text" maxlength="4" size="4" name="prfperiodo5" id="prfpromedio5" value='<c:out value="0.0"/>' onKeyPress='return acepteNumeros(event)'>
							             	</tr>
		                            	</c:when>
		                          	</c:choose>
		                          
		                          	<c:choose>
		                            	<c:when test="${sessionScope.numeroperiodosparam>5}">
		                                	<tr >
							              		<td align="center" class="Fila0">6</td>
							              		<td class="Fila0">
							              			<input type="text" maxlength="4" size="4" name="prfperiodo6" id="prfpromedio6" value='<c:out value="${ponderacionPrgPeriodoVO.prfperiodo6 }"/>' onKeyPress='return acepteNumeros(event)'>
							             	</tr>
		                            	</c:when>
		                            	<c:when test="${sessionScope.numeroperiodosparam<=5}">
		                                	<tr style="display: none;">
							              		<td align="center" class="Fila0">6</td>
							              		<td class="Fila0">
							              			<input type="text" maxlength="4" size="4" name="prfperiodo6" id="prfpromedio6" value='<c:out value="0.0"/>' onKeyPress='return acepteNumeros(event)'>
							             	</tr>
		                            	</c:when>
		                          	</c:choose>
		
								</table>
							</c:when>
		                    <c:otherwise>
		                    	<p><font color="red">No se encuentra registro del número de períodos para la vigencia del <c:out value="${instParVO.insparvigencia}"/> en los parámetros del colegio.</font></p>
							</c:otherwise>
						</c:choose>
					
					</td>					   
				
				</tr>
					 	 
			</table>
		 
		</form>
		
	</body>

	</html>
	
</c:if>

<c:if test="${sessionScope.niveldeevaluacionactualparaponderizacion!=1}">
	<script type="text/javascript">
  		alert('El tipo de Nivel de Evaluacion no es: NUMERICO, para poder configurar la ponderación por periodo debe tener este tipo de nivel de evaluacion <c:out value="${sessionScope.niveldeevaluacionactualparaponderizacion}"/>');
  		history.back(1);
  	</script>
</c:if>
