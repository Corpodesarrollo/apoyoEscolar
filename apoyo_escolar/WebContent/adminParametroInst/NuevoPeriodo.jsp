<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="instParVO" class="siges.adminParamsInst.vo.InstParVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.adminParamsInst.vo.ParamsVO" scope="page"/>
<html>
<head>
<script languaje="javaScript">
	function hacerValidaciones_frmNuevo(forma){
	
	 	 validarLista(forma.inspartipper,'- Tipo de periodo', 1);
	     validarLista(forma.insparnumper,'- Número de periodos', 1);
	 	 validarLista(forma.insparniveval,'- Nivel de evaluación',1);
	 	 validarCampo(forma.insparnomperdef,'- Nombre ultimo periodo');
	 	// validarCampo(forma.porcentajeperdida,'- % de perdida de asignatura');
	 	 validarEnteroCeroaCien(forma.porcentajeperdida,'- Porcentaje debe estar entre 0% y 100%');
	 	// validarCampo(forma.insparsubtitbol,'- Subtitulo Boletin');
	 	validarCampo(forma.insparsubtitbol,'- Evaluación de logros');
	 	validarCampo(forma.insparevaldescriptores,'- Evaluación de descriptores');
	 	validarFloat(forma.porcentajeperdida, '- Porcentaje de perdida por inasistencia  ',0.0,100);

	}
		
	function guardar(){
		if(validarForma(document.frmNuevo)){
	
		   if(document.frmNuevo.insparniveval.value !=  document.frmNuevo.insparnivevalAntes.value &&
		      document.frmNuevo.msgValidarNivelEval.value.length > 0 ){
			  
			  if( confirm("¿Esta seguro de actualizar esta información?.") &&
			       confirm(document.frmNuevo.msgValidarNivelEval.value) ){
			       document.frmNuevo.eliminarCascada.value =1;
		  	   }else{
			    return false;  
		  	  }//if( !confirm("¿Esta seguro de actualizar esta información?.") ||
		   }//if(document.frmNuevo.insparniveval.value !=  document.frmNuevo.insparnivevalAntes.value &&	
	
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
	
	function validaFloat__(numero)
	{
	  if (!/^([0-9])*[.]?[0-9]*$/.test(numero)){
	   alert("El valor " + numero + " no es un número");
	   appendErrorMessage(msgError)
       if (_campoError == null) {
           _campoError = lista;
       }
       //return false;
	  }
	}
	
	function justNumbers(string) {
	
		if (!string) var string = window.event;
		if (string.keyCode) code = string.keyCode;
		else if (string.which) code = string.which;
		var character = String.fromCharCode(code);	
		
	
		var illegalChars = /^[1-9]+[\.]{0,1}\d*$/;
		var patron = /^[1-9]+[\.]$/;
		if (illegalChars.test(character) ){
		   return true;
		  }
		return false;
		}


	 
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
 
	<form method="post" name="frmNuevo"  action='<c:url value="/adminParametroInst/Save.jsp"/>'>
		
		 
		 <table border="0" align="center" bordercolor="#FFFFFF" width="95%">
		<caption>Gestión Administrativa >> Parámetros</caption>
			<tr>
			  <td   bgcolor="#FFFFFF">
					<input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar()">
					<input class='boton' name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
			  </td>
			</tr>
		</table>
	
	     <input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_PERIODO}"/>'>
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
				   <img src='<c:url value="/etc/img/tabs/adm_periodos_1.gif"/>' border="0"  height="26" alt='Parametros'>
			       <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_INST_NIVEL_EVAL }" />')"><img src='<c:url  value="/etc/img/tabs/adm_nivelEval_0.gif"/>' alt="Nivel Evaluación " height="26" border="0"></a>
			       <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_ESCL_CONCEPT }" />')"><img src='<c:url value="/etc/img/tabs/adm_escalaConpt_0.gif"/>' alt="Escala Conceptual" height="26" border="0"></a>
			       <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_ESCL_NUM }" />')"><img src='<c:url value="/etc/img/tabs/adm_escalaNum_0.gif"/>' alt="Escala Numérical" height="26" border="0"></a>
			       <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_PER_PROG_FECHAS }" />')"><img src='<c:url value="/etc/img/tabs/adm_perProgFechas_0.gif"/>' alt="Programar Periodos" height="26" border="0"></a>
		           <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_PONDERACION_POR_PERIODO }" />')"><img src='<c:url value="/etc/img/tabs/adm_ponporperiodo_0.png"/>' alt="Ponderación por periodos" height="26" border="0"></a>
			 
		        </td>
            </tr>
          </table>
		  <table border="0" align="center" width="95%" cellpadding="2" cellspacing="2">
			 
					<tr><td width="100px">Vigencia Actual</td>
					    <td><c:out value="${instParVO.insparvigencia}"/></td>
					 </tr>
					 <tr>   
					    <td width="90px"><font color="red">*</font>Número de Periodos</td>
					    <td><select name="insparnumper">
					           <option value="-99" style="width: 20px"> -//- </option>
					          <c:forEach  begin="0" items="${sessionScope.listaNumeroPeriodos}" var="lista" >
					            <option value='<c:out  value="${ lista.codigo}"/>' <c:if test="${lista.codigo  == instParVO.insparnumper }">SELECTED</c:if > ><c:out value="${lista.nombre}" /> </option>
					          </c:forEach>
					        </select>
					    </td>
				 <!-- 	<td><font color="red">*</font>Tipo de Periodo</td>
					    <td><select name="inspartipper">
					           <option value="-99"> --Seleccione uno -- </option>
					          <c:forEach  begin="0" items="${sessionScope.listaTipoPeriodos}" var="lista" >
					            <option value='<c:out  value="${ lista.codigo}"/>' <c:if test="${lista.codigo  == instParVO.inspartipper }">SELECTED</c:if > ><c:out value="${lista.nombre}" /> </option>
					          </c:forEach>
					        </select>
					    </td>
					   -->  					    <input type="hidden" name="inspartipper" value="6">
					    </tr>
				 	 
				 	   <tr>
					    <td><font color="red">*</font>Nivel de Evaluación</td>
					    <td><select name="insparniveval">
					           <option value="-99"> --Seleccione uno -- </option>
					          <c:forEach  begin="0" items="${sessionScope.listaTipoCombinacion}" var="lista" >
					            <option value='<c:out  value="${ lista.codigo}"/>' <c:if test="${lista.codigo  == instParVO.insparniveval }">SELECTED</c:if > ><c:out value="${lista.nombre}" /> </option>
					          </c:forEach>
					        </select>
					    </td>
				 	</tr>
				 	  <tr>
					    <td><font color="red">*</font>% p&eacute;rdida de la asignatura por inasistencia</td>
					    <td colspan="3">
					    <input type="text" name="porcentajeperdida" id="porcentajeperdida" value='<c:out value="${instParVO.porcentajeperdida}" />'  size="50" maxlength="50" />%
					    </td>
				 	</tr>
				 	  <!--  <input type="hidden" value="1" name="insparniveval"> -->
				 	<tr>
					    <td><font color="red">*</font>Nombre último Periodo</td>
					    <td colspan="3"><input type="text" name="insparnomperdef" id="insparnomperdef" value='<c:out value="${instParVO.insparnomperdef}" />'  size="50" maxlength="50"></td>
				 	</tr>	
				 	<tr>
					    <td><font color="red">*</font>Evaluaci&oacute;n Logros</td>
					    <td colspan="3"><input type="text" name="insparsubtitbol" id="insparsubtitbol" value='<c:out value="${instParVO.insparsubtitbol}" />'  size="50" maxlength="21"></td>
				 	</tr>
				 	<tr>
					    <td><font color="red">*</font>Evaluaci&oacute;n Descriptores</td>
					    <td colspan="3"><input type="text" name="insparevaldescriptores" id="insparevaldescriptores" value='<c:out value="${instParVO.insparevaldescriptores}" />'  size="50" maxlength="21"></td>
				 	</tr>
			</table>	
			<!-- TABLA DE LOS RESULTADOS DE LA BUSQUEDA -->
			<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray">
			 	<caption>Lista Vigencias/Periodos</caption>
			 	<c:if test="${empty sessionScope.listaAllPeriodoInst}"><tr><th class="Fila1" colspan='6'>No hay datos</th></tr></c:if>
				<c:if test="${!empty sessionScope.listaAllPeriodoInst}">
					<tr> 
						<th class="EncabezadoColumna" align="center">Vigencia</th>
						<th class="EncabezadoColumna" align="center">Nª de Periodos</th>
						<th class="EncabezadoColumna" align="center">Tipo de Periodo</th>
						<th class="EncabezadoColumna" align="center">Nombre último periodo</th>
						<th class="EncabezadoColumna" align="center">Nivel de Evaluación</th>
					</tr>
					<c:forEach begin="0" items="${sessionScope.listaAllPeriodoInst}" var="lista" varStatus="st">
						<tr>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.insparvigencia}"/></td> 
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.insparnumper}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.inspartipperNom}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.insparnomperdef}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.insparcodcombNom}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center">
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
	 
	</form>
</body>
</html>