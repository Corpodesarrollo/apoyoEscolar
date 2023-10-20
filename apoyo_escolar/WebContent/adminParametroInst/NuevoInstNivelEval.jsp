<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!--	VERSION		FECHA			AUTOR			DESCRIPCION
			1.0		16/04/2020		JORGE CAMACHO	Se ajustó la tabulación de los controles
			2.0		18/04/2020		JORGE CAMACHO	Se actualizaron los valores para el dato Periodo de la siguiente manera:
													Promedio Aritmético: 2
													Criterio Docente: 1
													y se ajustaron las validaciones en elos métodos javascript 


-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean  id="instNivelEvaluacionVO" class="siges.adminParamsInst.vo.InstNivelEvaluacionVO" scope="session"/>
<jsp:setProperty name="instNivelEvaluacionVO" property="*"/>

<jsp:useBean  id="tipoEvaluacionInstAsigVO" class="siges.adminParamsInst.vo.TipoEvaluacionInstAsigVO" scope="session"/>
<jsp:setProperty name="tipoEvaluacionInstAsigVO" property="*"/>

<jsp:useBean  id="filtroNivelEvalVO" class="siges.adminParamsInst.vo.FiltroNivelEvalVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.adminParamsInst.vo.ParamsVO" scope="page"/>

<html>

	<head>
    
		<script languaje="text/javascript">

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

			function limpiarvalores() {
			
				var grupoasig = document.getElementsByName("tipEvalTipoEval");
				for(var i = 0; i < grupoasig.length; i++) {
					grupoasig[i].checked = false;
				}
				
				var grupoind = document.getElementsByName("insnivmodoevallogro");
				for(var i = 0; i < grupoind.length; i++) {
					grupoind[i].checked = false;
				}
			}
			
			function validarValorClic() {
			
				var grupoprougramas = document.getElementsByName("insnivmodoeval");
				var grupoasig = document.getElementsByName("tipEvalTipoEval");
				
				for(var i = 0; i < grupoprougramas.length; i++) {
				   	
					grupoasig[0].disabled = false;
					grupoasig[1].disabled = false;
					grupoasig[2].disabled = false;
					grupoasig[3].disabled = true;
					
					if(grupoprougramas[i].checked) {
					
						if(grupoprougramas[i].value == "1") {
				    	 
				    	    grupoasig[0].disabled = true;
				    	    grupoasig[1].disabled = true;
				    	    grupoasig[2].disabled = true;
				    	    grupoasig[3].disabled = true;
				    	    break;
				    	    
				       	} else if(grupoprougramas[i].value == "3") {
				    	  
							grupoasig[0].disabled = false;
				    	 	grupoasig[1].disabled = false;
				    	 	grupoasig[2].disabled = false;
				    	 	grupoasig[3].disabled = true;
				    	    break;
						}
			      
					}
				     
				}
			
			}
			
			function hacerValidaciones_frmNuevo(forma) {
	
				if(forma.insnivcodsede) {
					validarLista(forma.insnivcodsede, '- Sede', 1);
				}
				
				if(forma.insnivcodjorn) {
					validarLista(forma.insnivcodjorn, '- Jornada', 1);
				}
				
				if(forma.insnivcodmetod) {
					validarLista(forma.insnivcodmetod, '- Metodologia', 1);
				}
				
				if(forma.insnivcodnivel) {
					validarLista(forma.insnivcodnivel, '- Nivel', 1);
				}
	  
	  			if(forma.insnivcodgrado) {
	   				validarLista(forma.insnivcodgrado, '- Grado', 1);
	  			}
	  
	  			validarLista(forma.insnivtipoevalasig, '- Tipo de evaluación asignatura', 1);
	  			validarLista(forma.insnivtipoevalprees, '- Tipo de evaluación preescolar', 1);
	  			
	  			var errorvalasig = true;
	  			var errorindicador = true;
	  			var errorvalperiodo = true;
	  			var estadovalidarasignatura = true;
	  			var grupoasig = document.getElementsByName("tipEvalTipoEval");
	  			var grupoind = document.getElementsByName("insnivmodoevallogro");
	  			var grupoprougramas = document.getElementsByName("insnivmodoeval");
	  
				for(var i = 0; i < grupoprougramas.length; i++) {
				  
					if(grupoprougramas[i].checked) {
				  
				  		if(grupoprougramas[i].value == '1') {
					  		estadovalidarasignatura = false;
				  		}
				  
				  		errorvalperiodo = false;
				  		break;
				 	}
				}
	  
			  	if(errorvalperiodo) {
					appendErrorMessage('- Modalidad de evaluación : Periodo.');  
			  	}
	  
	  			if(estadovalidarasignatura) {
	  			
		  			for(var i = 0; i < grupoasig.length; i++) {
		  			
			  			if(grupoasig[i].checked) {
				  			errorvalasig = false;
				  			break;
			  			} 
		  			}
		  			
		  			if(errorvalasig) {
			  			appendErrorMessage('- Modalidad de evaluación : Asignatura.');  			  
		  			}
		  			
	  			}
	  			
	  			
				for(var i = 0; i < grupoind.length; i++) {
				
					if(grupoind[i].checked) {
						errorindicador = false;
						break;
					}
					
				}
				
				if(errorindicador) {
		  			appendErrorMessage('- Modalidad de evaluación : Indicador.');
		  		}
	  
	  
	  			if(document.frmNuevo.insnivtipoevalasig.value == 1 || document.frmNuevo.insnivtipoevalasig.value == 3) {
	  			
	    			validarCampo(forma.insnivvalminnum,'- Valor númerico minimo');
	    			validarFloat(forma.insnivvalminnum,'- Valor númerico minimo (formato 00.00)', 0.00,999.99);
	    			validarFloat(forma.insnivvalmaxnum,'- Valor númerico maximo (formato 00.00)', 0.01,999.99);
	    			validarFloat(forma.insnivvalaprobnum,'- Valor de aprobación (formato 00.00)', 0.01,999.99);
	    
	    			if((forma.insnivvalminnum.value * 100) >= (forma.insnivvalmaxnum.value * 100)) {
	       				appendErrorMessage('- El rango numérico es incorrecto. El valor mínimo no puede ser mayor o igual al valor máximo.');
	        			if (_campoError == null) {
              				_campoError = forma.insnivvalminnum.value;
            			}
	    			}
	    
	    			if(!((forma.insnivvalminnum.value * 100) < (forma.insnivvalaprobnum.value * 100) && (forma.insnivvalmaxnum.value * 100) > (forma.insnivvalaprobnum.value * 100))) {
	       				appendErrorMessage('- El valor de aprobación de la escala no está dentro del rango.');
	        			if (_campoError == null) {
              				_campoError = forma.insnivvalaprobnum.value;
            			}
	    			}
	    
	  			} else if(document.frmNuevo.insnivtipoevalasig.value == 2) {
	  			
	       			forma.insnivmodoeval.disabled = false;
		   			forma.insnivmodoeval.value = 1;
		   			
	    		}
	    		
	 		}
		
			function guardar() {
				if(validarForma(document.frmNuevo)) {
					document.frmNuevo.cmd.value = document.frmNuevo.GUARDAR.value;
			 		document.frmNuevo.submit();
				}
			}
	
  			function nuevo() {
				document.frmNuevo.cmd.value = document.frmNuevo.NUEVO.value;
			 	document.frmNuevo.submit();
			}
	
			function lanzar(tipo) {
				document.frmNuevo.tipo.value = tipo;
				document.frmNuevo.cmd.value = -1;
				document.frmNuevo.action = '<c:url value="/grupoPeriodo/ControllerAdminEdit.do"/>';
				document.frmNuevo.target = "";
			 	document.frmNuevo.submit();
			}
			
			function lanzar_(tipo) {
				document.frmNuevo.tipo.value = tipo;
				document.frmNuevo.cmd.value = -1;
				document.frmNuevo.action = '<c:url value="/adminParamsInst/Nuevo.do"/>';
				document.frmNuevo.target = "";
				document.frmNuevo.submit();
			}
			
			function cancelar() {
				location.href='<c:url value="/bienvenida.jsp"/>';
			}	 
	    
 			function ajaxJornada_2() {
	 			if(document.frmNuevo.insnivcodjorn) {
					borrar_combo2(document.frmNuevo.insnivcodjorn);
					document.frmAjaxNuevo.target = 'frameAjaxNuevo';
					document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.insnivcodsede.value;
					document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_JORD.value;
					document.frmAjaxNuevo.submit();
	 			}
			}
			
			function ajaxMetodologia_2() {
	 			if(document.frmNuevo.insnivcodmetod) {
					borrar_combo2(document.frmNuevo.insnivcodmetod);
					document.frmAjaxNuevo.target = 'frameAjaxNuevo';
					document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.insnivcodsede.value;
					document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_METD.value;
					document.frmAjaxNuevo.submit();
	 			}
			}
			
			function ajaxNivel_2() {
				if(document.frmNuevo.insnivcodnivel) {
			  		borrar_combo2(document.frmNuevo.insnivcodnivel);
			  		document.frmAjaxNuevo.target = 'frameAjaxNuevo';
			  		if(document.frmNuevo.insnivcodmetod) {
			  			document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.insnivcodmetod.value;
			  		} else {
			  			document.frmAjaxNuevo.ajax[0].value = -99;
			  		}
			  		document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_NVL.value;
			  		document.frmAjaxNuevo.submit();
			 	}
			}
	 
	  		function ajaxGrado_2() {
	 			if(document.frmNuevo.insnivcodgrado) {
	  				borrar_combo2(document.frmNuevo.insnivcodgrado);
	  				document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	   
	   				// Verifique que existe la metodologia
	   				if(document.frmNuevo.insnivcodmetod) {
	    				document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.insnivcodmetod.value;
	   				} else {
	    				document.frmAjaxNuevo.ajax[0].value = -99;
	   				}

	   				// Verifique que existe el nivel	   
	   				if(document.frmNuevo.insnivcodnivel) {
	    				document.frmAjaxNuevo.ajax[1].value = document.frmNuevo.insnivcodnivel.value;
	   				} else {
	    				document.frmAjaxNuevo.ajax[1].value = -99;
	   				}
	   
	  				document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_GRAD.value;
	  				document.frmAjaxNuevo.submit();
	 			}
			}
		</script>
		
	</head>
	
	<c:import url="/mensaje.jsp"/>
	
	<body onLoad="mensaje(document.getElementById('msg'));">
 		
 		<form method="post" name="frmAjaxNuevo" action='<c:url value="/adminParametroInst/Ajax.do"/>'>
		
			<input type="hidden" name="tipo" value='<c:out value="${ paramsVO.FICHA_INST_NIVEL_EVAL }"/>'>
			<input type="hidden" name="cmd" value='-1'>
			<input type="hidden" name="formulario" id="formulario" value='2'>
			<input type="hidden" name="CMD_AJAX_JORD" id="CMD_AJAX_JORD" value='<c:out value="${paramsVO.CMD_AJAX_JORD}"/>'>
			<input type="hidden" name="CMD_AJAX_METD" id="CMD_AJAX_METD" value='<c:out value="${paramsVO.CMD_AJAX_METD}"/>'>
			<input type="hidden" name="CMD_AJAX_NVL" id="CMD_AJAX_NVL" value='<c:out value="${paramsVO.CMD_AJAX_NVL}"/>'>
			<input type="hidden" name="CMD_AJAX_GRAD" id="CMD_AJAX_GRAD" value='<c:out value="${paramsVO.CMD_AJAX_GRAD}"/>'>
		
			<c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach>
	  
	  	</form>
	
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<caption>Gestión Administrativa >> Nivel de Evaluación</caption>
			<tr style="height: 20px">
				<td>&nbsp;</td>
			</tr>
		    <tr height="1">
				<td width="10" bgcolor="#FFFFFF">&nbsp;</td>							
			 	<td rowspan="2" bgcolor="#FFFFFF">
			       <a href="javaScript:lanzar(1)"><img src='<c:url value="/etc/img/tabs/validacion_horarios_0.gif"/>' alt="Validación de Horarios" height="26" border="0"></a>
				   <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_PERIODO }" />')"><img src='<c:url value="/etc/img/tabs/adm_periodos_0.gif"/>' alt="Parametros " height="26" border="0"></a>
				   <img src='<c:url value="/etc/img/tabs/adm_nivelEval_1.gif"/>' border="0"  height="26" alt='Nivel de Evaluación'>
		           <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_ESCL_CONCEPT }" />')"><img src='<c:url value="/etc/img/tabs/adm_escalaConpt_0.gif"/>' alt="Escala Conceptual " height="26" border="0"></a>
		           <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_ESCL_NUM }" />')"><img src='<c:url value="/etc/img/tabs/adm_escalaNum_0.gif"/>' alt="Escala Numerica " height="26" border="0"></a>
		           <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_PER_PROG_FECHAS }" />')"><img src='<c:url value="/etc/img/tabs/adm_perProgFechas_0.gif"/>' alt="Programar Periodos" height="26" border="0"></a>
		           <a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_PONDERACION_POR_PERIODO }" />')"><img src='<c:url value="/etc/img/tabs/adm_ponporperiodo_0.png"/>' alt="Ponderación por periodos" height="26" border="0"></a>
		        </td>
            </tr>
		</table>
          
		<!-- LISTA DE RESULTADOS -->
        <table border="0" cellpadding="0" cellspacing="0" align="center" width="100%">
        	<caption >Filtro de Búsqueda</caption>
         	<tr>
          		<td>
            		<c:import url="/adminParametroInst/Lista.do"><c:param value="${paramsVO.FICHA_INST_NIVEL_EVAL}" name="tipo" /></c:import>
		   		</td>
		   	</tr>
		</table>	
		
   		<!-- FORMULARIO DE EDICION CREACION-->
		<form method="post" name="frmNuevo" action='<c:url value="/adminParametroInst/Save.jsp"/>'>

	        <input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_INST_NIVEL_EVAL }"/>'>
	        <input type="hidden" name="cmd" value=''>
			<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
			<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'> 
			<input type="hidden" name="insconvigencia" id="insconvigencia" value='<c:out value="${sessionScope.filtroNivelEvalVO.filVigencia}"/>'>
			<input type="hidden" name="insnivvigencia" id="insnivvigencia" value='<c:out value="${sessionScope.filtroNivelEvalVO.filVigencia}"/>'>
			<input type="hidden" name="insnivcodinst" id="insnivcodinst" value='<c:out value="${sessionScope.filtroNivelEvalVO.filInst}"/>'>
			<input type="hidden" name="insnivcodniveleval" id="insnivcodniveleval" value='<c:out value="${sessionScope.filtroNivelEvalVO.filniveval}"/>'>
			<input type="hidden" name="insnivcodsede_" id="insnivcodsede_" value='<c:out value="${sessionScope.instNivelEvaluacionVO.insnivcodsede}"/>'>
			<input type="hidden" name="insnivcodjorn_" id="insnivcodjorn_" value='<c:out value="${sessionScope.instNivelEvaluacionVO.insnivcodjorn}"/>'>
			<input type="hidden" name="insnivcodmetod_" id="insnivcodmetod_" value='<c:out value="${sessionScope.instNivelEvaluacionVO.insnivcodmetod}"/>'>
			<input type="hidden" name="insnivcodnivel_" id="insnivcodnivel_" value='<c:out value="${sessionScope.instNivelEvaluacionVO.insnivcodnivel}"/>'>
			<input type="hidden" name="insnivcodgrado_" id="insnivcodgrado_" value='<c:out value="${sessionScope.instNivelEvaluacionVO.insnivcodgrado}"/>'>
			<input type="hidden" name="yaFueUtilizado" id="yaFueUtilizado" value='<c:out value="${sessionScope.instNivelEvaluacionVO.yaFueUtilizado}"/>'>
			<input type="hidden" name="insnivvalminnumAntes" id="insnivvalminnumAntes" value='<c:out value="${sessionScope.instNivelEvaluacionVO.insnivvalminnumAntes}"/>'>
			<input type="hidden" name="insnivvalmaxnumAntes" id="insnivvalmaxnumAntes" value='<c:out value="${sessionScope.instNivelEvaluacionVO.insnivvalmaxnumAntes}"/>'>
			<input type="hidden" name="insnivtipoevalasigAntes" id="insnivtipoevalasigAntes" value='<c:out value="${sessionScope.instNivelEvaluacionVO.insnivtipoevalasigAntes}"/>'>
		
		 	<table border="0" align="center" width="100%" cellpadding="2" cellspacing="2">
				
				<caption>Nuevo / Edición de Nivel de Evaluación</caption>
				
				<tr>
			    	<td bgcolor="#FFFFFF" colspan="4">
                 		<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">
            				<input class='boton' name="cmd1" type="button" value="Actualizar" onClick="guardar()">
                 		</c:if>
                 		<c:if test="${!sessionScope.instNivelEvaluacionVO.edicion}">
            				<input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar()">
                 		</c:if>
						<input class='boton' name="cmd13" type="button" value="Nuevo" onClick="nuevo()">
						<input class='boton' name="cmd12" type="reset" value="Cancelar" >
			      	</td>
				</tr>
		 		<tr> 
    	    		<td colspan="4">
			    		<fieldset id="fieldset_">
			     			<legend><b>Nivel Evaluación</b></legend>
			  				<table border="0" width="100%">   
			     				<tr>
			     					<td width="70px"><font color="red">*</font>Vigencia</td>
		         					<td width="250px">
		         						<select name="filVigencia" style="width: 60px;" onmouseover="this.disabled=true;" onmouseout="this.disabled=false;">
					   						<option value="-99"> --  //  -- </option>
					     					<c:forEach begin="0" items="${sessionScope.listaVigencia}" var="lista">
					       						<option value='<c:out value="${lista.codigo}"/>' <c:if test="${lista.codigo == sessionScope.filtroNivelEvalVO.filVigencia }">SELECTED</c:if >><c:out value="${lista.nombre}" /></option>
					    					</c:forEach>
					   					</select>
				 					</td>				
									<c:choose> 
	       								<c:when test="${sessionScope.filtroNivelEvalVO.filniveval == paramsVO.NVLEVA_INST}">
		        							<td width="70px">&nbsp;</td>
		        							<td width="70px">&nbsp;</td>
		   								</c:when>		   
		   								<c:when test="${sessionScope.filtroNivelEvalVO.filniveval == paramsVO.NVLEVA_SED}">
	             							<td width="70px"><font color="red">*</font>Sede</td>
	             							<td width="250px">
	                							<select '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodsede" id="insnivcodsede" onchange="javaScript:ajaxJornada_();">
	                 								<option value="-99" >--Seleccione uno--</option>
					   								<c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodsede && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					  								</c:forEach>   
	                							</select>
	             							</td>
										</c:when>
           								<c:when test="${sessionScope.filtroNivelEvalVO.filniveval == paramsVO.NVLEVA_JORN}">
	               							<td width="70px"><font color="red">*</font>Jornada</td>
	               							<td width="250px">
	                 							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insnivcodjorn" id="insnivcodjorn" onchange="javaScript:ajaxMetodologia_();" >
	                   								<option value="-99" >--Seleccione uno--</option>
					   								<c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodjorn && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>   
	                 							</select>
	               							</td>
										</c:when>
									   	<c:when test="${sessionScope.filtroNivelEvalVO.filniveval == paramsVO.NVLEVA_METD}">
								        	<td width="70px"><font color="red">*</font>Metodología</td><td width="250px" >
								            	<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insnivcodmetod" id="insnivcodmetod">
								                	<option value="-99" >--Seleccione uno--</option>
												   	<c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
														<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodmetod && requestScope.nuevoNivelEvalref==1}">selected</c:if> ><c:out value="${sed.nombre}"/></option>
												   	</c:forEach>
								               	</select>
								            </td>
									   	</c:when>
										<c:when test="${sessionScope.filtroNivelEvalVO.filniveval == paramsVO.NVLEVA_NVL}">
											<td width="70px"><font color="red">*</font>Nivel</td><td>
										    	<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodnivel" id="insnivcodnivel" onchange="javaScript:ajaxGrado_2();">
													<option value="-99" >--Seleccione uno--</option>
													<c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
														<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.instNivelEvaluacionVO.insnivcodnivel && requestScope.nuevoNivelEvalref==1}">selected</c:if> ><c:out value="${sed.nombre}"/></option>
													</c:forEach>
										  		</select>
											</td>
										</c:when>
								        <c:when test="${sessionScope.filtroNivelEvalVO.filniveval == paramsVO.NVLEVA_GRD}">
											<td width="70px"><font color="red">*</font>Grado</td>
								         	<td width="250px">
									           	<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodgrado" id="insnivcodgrado">
									            	<option value="-99" >--Seleccione uno--</option>
													<c:forEach begin="0" items="${sessionScope.listaGrado  }" var="sed">
														<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodgrado && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
												  	</c:forEach> 
									           	</select>
								          	</td>
								       	</c:when>
	       								<c:when test="${sessionScope.filtroNivelEvalVO.filniveval == paramsVO.NVLEVA_SED_JORN}">
	           								<td width="70px"><font color="red">*</font>Sede</td>
	            							<td width="250px">
	            								<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodsede" id="insnivcodsede" onchange="javaScript:ajaxJornada_2();">
	                 								<option value="-9" >--Seleccione uno--</option>
					   								<c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodsede && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					    							</c:forEach>
	             								</select>
	            							</td>
											<td width="70px"><font color="red">*</font>Jornada</td>
											<td>
												<select '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insnivcodjorn" id="insnivcodjorn" onchange="javaScript:ajaxMetodologia_();">
						     						<option value="-99" >--Seleccione uno--</option>
						         					<c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
								 						<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodjorn && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
							    					</c:forEach>
						    					</select>
											</td>
		   								</c:when>
			  							<c:when test="${sessionScope.filtroNivelEvalVO.filniveval == paramsVO.NVLEVA_SED_METD }">
	          								<td width="70px"><font color="red">*</font>Sede</td>
	          								<td>
												<select '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodsede" id="insnivcodsede" onchange="javaScript:ajaxMetodologia_2();">
													<option value="-9" >--Seleccione uno--</option>
												   	<c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
														<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodsede && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
												  	</c:forEach> 
								                </select>
											</td>
										 	<td width="70px"><font color="red">*</font>Metodología</td>
										 	<td>
										    	<select '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodmetod" id="insnivcodmetod" onchange="javaScript:ajaxNivel_();">
										      		<option value="-99" >--Seleccione uno--</option> 
											   		<c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
												 		<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodmetod && requestScope.nuevoNivelEvalref==1}">selected</c:if> ><c:out value="${sed.nombre}"/></option>
											   		</c:forEach> 
										     	</select>
										   	</td>
								   		</c:when>
		     							<c:when test="${sessionScope.filtroNivelEvalVO.filniveval == paramsVO.NVLEVA_SED_NVL }">
	          								<td width="70px">Sede</td>
	          								<td width="250px">
	             								<select '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodsede" id="insnivcodsede" onchange="javaScript:ajaxNivel_2();">
	                  								<option value="-99" >--Seleccione uno--</option>
					   								<c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.instNivelEvaluacionVO.insnivcodsede && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach> 
	             								</select>
	             							</td>
				 							<td><font color="red">*</font>Nivel</td>
				 							<td>
				      							<select '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodnivel" id="insnivcodnivel">
				      								<option value="-9" >--Seleccione uno--</option>
				       								<c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo == sessionScope.instNivelEvaluacionVO.insnivcodnivel && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
				      							</select>
				      						</td>
		   								</c:when>
		    							<c:when test="${sessionScope.filtroNivelEvalVO.filniveval == paramsVO.NVLEVA_SED_GRD }">
	          								<td width="70px"><font color="red">*</font>Sede</td>
	          								<td width="250px">
	             								<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodsede" id="insnivcodsede" onchange="javaScript:ajaxGrado_2();">
	                 								<option value="-99" >--Seleccione uno--</option>
					   								<c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodsede && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach> 
	              								</select>
	             							</td>
			  								<td width="70px"><font color="red">*</font>Grado</td>
			  								<td width="250px">
												<select '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodgrado" id="insnivcodgrado">
													<option value="-99" >--Seleccione uno--</option>
													<c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
														<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodgrado && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
													</c:forEach> 
												</select>
			   								</td>
		   								</c:when>     		        
	       								<c:when test="${sessionScope.filtroNivelEvalVO.filniveval == paramsVO.NVLEVA_SED_JORN_METD }">
	             							<td width="70px"><font color="red">*</font>Sede</td>
	             							<td width="250px">
	                							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodsede" id="insnivcodsede" onchange="javaScript:ajaxJornada_2();">
	                  								<option value="-99" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodsede && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
	                							</select>
	               							</td>
				  							<td width="70px"><font color="red">*</font>Jornada</td>
				  							<td>
				  								<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insnivcodjorn" id="insnivcodjorn" onchange="javaScript:ajaxMetodologia_2();">
				     								<option value="-99" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodjorn && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
				     							</select>
				   							</td> 
				   							<td><font color="red">*</font>Metodología</td>
				   							<td width="250px">
				     							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodmetod" id="insnivcodmetod">
				     								<option value="-99" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodmetod && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
				     							</select>
				  							</td>
		     							</c:when>
		      							<c:when test="${sessionScope.filtroNivelEvalVO.filniveval == paramsVO.NVLEVA_SED_JORN_NVL }">
	             							<td width="70px"><font color="red">*</font>Sede</td>
	             							<td width="250px">
	                							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodsede" id="insnivcodsede" onchange="javaScript:ajaxJornada_2();">
	                 								<option value="-99" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodsede && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
	                							</select>
	               							</td>
				  							<td width="70px"><font color="red">*</font>Jornada</td>
				     						<td width="250px">
				       							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'   style="width: 120px;" name="insnivcodjorn" id="insnivcodjorn" onchange="javaScript:ajaxNivel_2();" >
				     								<option value="-99" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodjorn && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
				      							</select>
				    						</td>
				  							<td width="70px"><font color="red">*</font>Nivel</td>
				  							<td width="250px">
				    							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodnivel" id="insnivcodnivel">
				     								<option value="-99" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodnivel && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
				    							</select>
				  							</td>
		     							</c:when>
		     							<c:when test="${sessionScope.filtroNivelEvalVO.filniveval == paramsVO.NVLEVA_SED_JORN_GRD }">
	              							<td width="70px"><font color="red">*</font>Sede</td>
	              							<td width="250px">
	                							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodsede" id="insnivcodsede" onchange="javaScript:ajaxJornada_2();">
	                 								<option value="-9" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodsede && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
	                							</select>
	               							</td>
				 							<td width="70px"><font color="red">*</font>Jornada</td >
				  							<td width="250px">
				    							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insnivcodjorn"    id="insnivcodjorn"      onchange="javaScript:ajaxGrado_2();" >
				      								<option value="-99" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodjorn }">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
				    							</select>
				   							</td>
				 							<td width="70px"><font color="red">*</font>Grado</td>
				 							<td width="250px">
				   								<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodgrado" id="insnivcodgrado"   >
				   									<option value="-99" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodgrado && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
				   								</select>
				 							</td>
		     							</c:when>
	       								<c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_SED_METD_NVL }">
		             						<td width="70px"><font color="red">*</font>Sede</td>
		             						<td width="250px">
		               							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodsede"    id="insnivcodsede"      onchange="javaScript:ajaxMetodologia_2();" >
		                  							<option value="-9" >--Seleccione uno--</option>
		                   							<c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
							 							<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodsede && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
						   							</c:forEach>
		               							</select>
		             						</td>
						 					<td width="70px"><font color="red">*</font>Metodología</td> 
					 						<td width="250px">
					   							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodmetod"  id="insnivcodmetod"   onchange="javaScript:ajaxNivel_2();" >
					    							<option value="-9" >--Seleccione uno--</option>
		                   							<c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
							 							<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodmetod && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
						   							</c:forEach>
					   							</select>
					 						</td>
			         						<td width="70px"><font color="red">*</font>Nivel</td>
			         						<td width="250px">
			          							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodnivel"   id="insnivcodnivel"    onchange="javaScript:ajaxGrado_();">
			            							<option value="-9" >--Seleccione uno--</option>
		                   							<c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
							 							<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodnivel && requestScope.nuevoNivelEvalref==1}">selected</c:if> ><c:out value="${sed.nombre}"/></option>
						   							</c:forEach>
			          							</select>
			          						</td>
		     							</c:when>
										<c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_SED_METD_GRD }">
											<td width="70px"><font color="red">*</font>Sede</td>
											<td>
												<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodsede"    id="insnivcodsede"      onchange="javaScript:ajaxMetodologia_2();" >
													<option value="-9" >--Seleccione uno--</option>
										     		<c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
														<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodsede && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
													</c:forEach>
										         </select>
											</td>
										  	<td width="70px"><font color="red">*</font>Metodología</td> 
										  	<td width="250px">
										    	<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodmetod"  id="insnivcodmetod"  onchange="javaScript:ajaxGrado_2();" >
													<option value="-9" >--Seleccione uno--</option>
										         	<c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
														<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodmetod && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
													</c:forEach>
										  		</select>
											<td width="70px"><font color="red">*</font>Grado</td>
											<td width="250px">
										  		<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodgrado" id="insnivcodgrado"   >
													<option value="-9" >--Seleccione uno--</option>
										         	<c:forEach begin="0" items="${sessionScope.listGrado}" var="sed">
														<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodgrado && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
													</c:forEach>
										 		</select>
											</td>
										</c:when>
										<c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_SED_NVL_GRD  }">
											<td width="70px" ><font color="red">*</font>Sede</td>
										    <td>
										    	<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodsede"    id="insnivcodsede"      onchange="javaScript:ajaxNivel_2();" >
										        	<option value="-9" >--Seleccione uno--</option>
										          	<c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
														<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodsede && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
													</c:forEach>
												</select>
											</td>
											<td width="70px" ><font color="red">*</font>Nivel</td>
										    <td width="250px">
										    	<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodnivel"   id="insnivcodnivel"    onchange="javaScript:ajaxGrado_2();">
										 	  		<option value="-9" >--Seleccione uno--</option>
										            <c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
														<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodnivel && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
										 			</c:forEach>
										   		</select>
										   	</td>
											<td width="70px" ><font color="red">*</font>Grado</td>
											<td width="250px">
										  		<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodgrado" id="insnivcodgrado"   >
										   			<option value="-9" >--Seleccione uno--</option>
										            <c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
														<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodgrado && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
										 			</c:forEach>
										 		</select>
										 	</td>
										</c:when>
	       								<c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_SED_JORD_METD_NVL }">
	             							<td width="70px" ><font color="red">*</font>Sede</td>
	             							<td width="250px">
	               								<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodsede"    id="insnivcodsede"      onchange="javaScript: borrar_combo2(document.frmNuevo.insnivcodmetod  );borrar_combo2(document.frmNuevo.insnivcodnivel );  ajaxJornada_2();" >
	                 								<option value="-9" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodsede && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
	               								</select>
	              							</td>
				  							<td width="70px" ><font color="red">*</font>Jornada</td>
				  							<td width="250px">
				    							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insnivcodjorn"    id="insnivcodjorn"      onchange="javaScript:ajaxMetodologia_2();" >
				      								<option value="-9" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodjorn && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
				    							</select>
				  							</td> 
				  							<td width="70px" ><font color="red">*</font>Metodología</td> 
				  							<td width="250px" >
				    							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodmetod"  id="insnivcodmetod"   onchange="javaScript:ajaxNivel_2();" >
				      								<option value="-9" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodmetod && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
				    							</select>
				    						</td>
		         							<td width="70px" ><font color="red">*</font>Nivel</td>
		             						<td width="250px">
		                						<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" style="width: 140px;" name="insnivcodnivel"   id="insnivcodnivel"    onchange="javaScript:ajaxGrado3_();">
		                   							<option value="-9" >--Seleccione uno--</option>
	                       							<c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						  								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodnivel && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					     							</c:forEach>
				        						</select>
		               						</td>
		     							</c:when>
		       							<c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_SED_JORD_METD_GRD }">
	              							<td width="70px" ><font color="red">*</font>Sede</td>
	              							<td width="250px">
	                							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodsede"    id="insnivcodsede"      onchange="javaScript: borrar_combo2(document.frmNuevo.insnivcodmetod);borrar_combo2(document.frmNuevo.insnivcodgrado); ajaxJornada_2();" >
	                  								<option value="-9" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodsede && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
	                							</select>
	                						</td>
				  							<td width="70px" ><font color="red">*</font>Jornada</td>
				  							<td width="250px">
				    							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insnivcodjorn"    id="insnivcodjorn"      onchange="javaScript:ajaxMetodologia_2();" >
				    								<option value="-9" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodjorn && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
				    							</select>
				   							</td>
				  							<td width="70px" ><font color="red">*</font>Metodología</td> 
				  							<td width="250px">
				     							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodmetod"  id="insnivcodmetod"   onchange="javaScript:ajaxGrado_2();" >
				       								<option value="-9" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodmetod && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
				     							</select>
				     						</td>
		         							<td width="70px" >Grado</td>
		         							<td>
		         								<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodgrado" id="insnivcodgrado" >
		            								<option value="-9" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodgrado && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
		         								</select>
		         							</td>
		      							</c:when>
		       							<c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_SED_METD_NVL_GRD }">
	              							<td width="70px"><font color="red">*</font>Sede</td>
	              							<td width="250px">
	                 							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodsede"    id="insnivcodsede"      onchange="javaScript:borrar_combo2(document.frmNuevo.insnivcodnivel); borrar_combo2(document.frmNuevo.insnivcodgrado); ajaxMetodologia_2();" >
	                  								<option value="-9" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodsede && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
	                 							</select>
	                 						</td>
				  							<td width="70px"><font color="red">*</font>Metodología</td> 
				   							<td width="250px" >
				      							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodmetod"  id="insnivcodmetod"   onchange="javaScript:ajaxNivel_2();" >
				      								<option value="-9" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodmetod && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
				      							</select>
				   							</td>
                  							<td width="70px" ><font color="red">*</font>Nivel</td>
                  							<td width="250px">
                   								<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" style="width: 140px;" name="insnivcodnivel"   id="insnivcodnivel"    onchange="javaScript:ajaxGrado_2();">
                     									<option value="-9" >--Seleccione uno--</option>
                   									<c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
					 									<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodnivel && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
				   									</c:forEach>
			    								</select>
                  							</td>
		          							<td width="70px" ><font color="red">*</font>Grado</td>
		          							<td width="250px">
		           								<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodgrado" id="insnivcodgrado" >
                      								<option value="-9" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodgrado}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
		           								</select>
		           							</td>
		      							</c:when>
		        						<c:when test="${sessionScope.filtroNivelEvalVO.filniveval  == paramsVO.NVLEVA_SED_JORD_METD_NVL_GRD }">
	              							<td width="70px"><font color="red">*</font>Sede</td>
	              							<td width="250px">
	                							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'   style="width: 140px;" name="insnivcodsede"    id="insnivcodsede"      onchange="javaScript:ajaxJornada_2();" >
	                   								<option value="-9" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaSed}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodsede && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
	                							</select>
	                						</td>
				  							<td width="70px" ><font color="red">*</font>Jornada</td>
				  							<td width="250px">
				    							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 120px;" name="insnivcodjorn"    id="insnivcodjorn"      onchange="javaScript:ajaxMetodologia_2();" >
				     								<option value="-9" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaJornada}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodnivel && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
				   								</select>
				  							</td>
				  							<td width="70px"><font color="red">*</font>Metodología</td>
				  							<td width="250px">
				    							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodmetod"  id="insnivcodmetod"   onchange="javaScript:ajaxNivel_2();" >
				   	  								<option value="-9" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaMetodo}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodmetod && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
				    							</select>
				   							</td>
                  							<td width="70px"><font color="red">*</font>Nivel</td>
                  							<td width="250px">
                    							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" style="width: 140px;" name="insnivcodnivel"   id="insnivcodnivel"    onchange="javaScript:ajaxGrado_2();">
                      								<option value="-9" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaNivel}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodnivel}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
                    							</select>
                    						</td>
                  							<td width="70px"><font color="red">*</font>Grado</td>
                  							<td width="250px">
                    							<select  '<c:if test="${sessionScope.instNivelEvaluacionVO.edicion}">' DISABLED '</c:if>'  style="width: 140px;" name="insnivcodgrado" id="insnivcodgrado" >
                      								<option value="-9" >--Seleccione uno--</option>
	                   								<c:forEach begin="0" items="${sessionScope.listaGrado}" var="sed">
						 								<option value="<c:out value="${sed.codigo}"/>" <c:if test="${sed.codigo==sessionScope.instNivelEvaluacionVO.insnivcodgrado && requestScope.nuevoNivelEvalref==1}">selected</c:if>><c:out value="${sed.nombre}"/></option>
					   								</c:forEach>
                    							</select>
                    						</td>
		      							</c:when>
	       							</c:choose>
								</tr>
							</table>
						</fieldset>
					</td>
	   			</tr>
	   			<tr>
				  	<td colspan="4">
				   		<fieldset>
				    		<legend><b><font color="BLACK">Tipo de Evaluación</font></b></legend>
				   			<table border="0" width="100%"> 
				    			<tr>
				     				<td width="70px">
				     					<font color="red">*</font>Asignatura<c:out value="${sessionScope.instNivelEvaluacionVO.insnivtipoevalasig}"/>
				     				</td>
					 				<td width="250px"> 
					    				<select name="insnivtipoevalasig" id="insnivtipoevalasig" onchange="seleccion();">
					       					<option value="-99">--Seleccione uno--</option>
									       	<option value="1"  <c:if test="${sessionScope.instNivelEvaluacionVO.insnivtipoevalasig == 1 && requestScope.nuevoNivelEvalref==1}">SELECTED</c:if>>Numérico </option>
									       	<option value="2"  <c:if test="${sessionScope.instNivelEvaluacionVO.insnivtipoevalasig == 2 && requestScope.nuevoNivelEvalref==1}">SELECTED</c:if>>Conceptual</option>
									       	<option value="3"  <c:if test="${sessionScope.instNivelEvaluacionVO.insnivtipoevalasig == 3 && requestScope.nuevoNivelEvalref==1}">SELECTED</c:if>>Porcentual</option>
									   	</select> 
					 				</td>
					 				<td width="70px">
					 					<font color="red">*</font>Preescolar
					 				</td>
					 				<td width="250px"> 
					    				<select name="insnivtipoevalprees" id="insnivtipoevalprees">
					      					<option value="-99">--Seleccione uno--</option>
					      					<option value="1" <c:if test="${sessionScope.instNivelEvaluacionVO.insnivtipoevalprees  == 1 && requestScope.nuevoNivelEvalref==1}">SELECTED</c:if>>Cualitativa </option>
					      					<option value="2" <c:if test="${sessionScope.instNivelEvaluacionVO.insnivtipoevalprees == 2 && requestScope.nuevoNivelEvalref==1}">SELECTED</c:if>>Asignatura </option>
					   					</select>
					 				</td>
				    			</tr>
				   			</table>
				   		</fieldset>
				   	</td>
			   	</tr>
			   	<tr>
			    	<td colspan="4">
			    		<fieldset id="fieldset_">
			     			<legend><b>Escala Numérica/Porcentual</b></legend>
			      			<table border="0" width="100%">
			       				<tr>
			        				<td>
			        					<legend><b>Modo de Evaluación</b></legend>
								        <table width="100%">
								        	<tr>
								         		<td width="30%">
								         			<font color="red">*</font> Periodo
								         		</td>
								          		<td width="70%">
													<input type="radio" name="insnivmodoeval" value="2" onClick="limpiarvalores();validarValorClic()" <c:if test="${sessionScope.instNivelEvaluacionVO.insnivmodoeval == '2' && requestScope.nuevoNivelEvalref==1}">CHECKED</c:if> />Promedio Aritmético<br />
													<input type="radio" name="insnivmodoeval" value="1" onClick="limpiarvalores();validarValorClic()" <c:if test="${sessionScope.instNivelEvaluacionVO.insnivmodoeval == '1' && requestScope.nuevoNivelEvalref==1}">CHECKED</c:if> />Criterio Docente<br />
													<input type="radio" name="insnivmodoeval" value="3" onClick="limpiarvalores();validarValorClic()" <c:if test="${sessionScope.instNivelEvaluacionVO.insnivmodoeval == '3' && requestScope.nuevoNivelEvalref==1}">CHECKED</c:if> />Ponderación por periodo<br />
													<input type="radio" name="insnivmodoeval" value="4" onClick="limpiarvalores();validarValorClic()" <c:if test="${sessionScope.instNivelEvaluacionVO.insnivmodoeval == '4' && requestScope.nuevoNivelEvalref==1}">CHECKED</c:if> />Acumulativo (&Sigma;)<br />
								          		</td>
								        	</tr>
								        </table>
				        				<br/><br/>
								        <table width="100%">
								        	<tr>
								         		<td width="30%">
								         			<font color="red">*</font> Asignatura
								         		</td>
								          		<td width="70%">
								              		<input type="radio" name="tipEvalTipoEval" value="2" <c:if test="${sessionScope.tipoEvaluacionInstAsigVO.tipEvalTipoEval == '2' && requestScope.nuevoNivelEvalref==1}">CHECKED</c:if> />Promedio Aritmético<br />
					                          		<input type="radio" name="tipEvalTipoEval" value="3" <c:if test="${sessionScope.tipoEvaluacionInstAsigVO.tipEvalTipoEval == '3' && requestScope.nuevoNivelEvalref==1}">CHECKED</c:if> />Ponderación por intensidad horaria<br />
					                          		<input type="radio" name="tipEvalTipoEval" value="5" <c:if test="${sessionScope.tipoEvaluacionInstAsigVO.tipEvalTipoEval == '5' && requestScope.nuevoNivelEvalref==1}">CHECKED</c:if> />Ponderación por porcentaje<br />
					                          		<input type="radio" name="tipEvalTipoEval" value="" disabled="disabled" <c:if test="${sessionScope.tipoEvaluacionInstAsigVO.tipEvalTipoEval == 'ninguno' && requestScope.nuevoNivelEvalref==1}">CHECKED</c:if> />Ninguno<br />
								          		</td>
								        	</tr>
										</table>
										<br/><br/>
										<table width="100%">
								        	<tr>
								         		<td width="30%">
								         			<font color="red">*</font> Indicador
								         		</td>
								          		<td width="70%">
								          			<input type="radio" name="insnivmodoevallogro" value="1" <c:if test="${sessionScope.instNivelEvaluacionVO.insnivmodoevallogro == '1' && requestScope.nuevoNivelEvalref==1}">CHECKED</c:if> />Ponderación por porcentaje<br />
								          			<input type="radio" name="insnivmodoevallogro" value="0" <c:if test="${sessionScope.instNivelEvaluacionVO.insnivmodoevallogro == '0' && requestScope.nuevoNivelEvalref==1}">CHECKED</c:if> />Ninguno<br />
								          		</td>
								          	</tr>
										</table>
									</td>
			       				</tr>
			      				<tr>
			        				<td colspan="4">
			        					<table>
			        						<tr><td><br/><br/><br/></td></tr>
			         						<tr>
												<td colspan="1" width="70px">
													<font color="red">*</font>Valor Mínimo
												</td>
												<td colspan="1" width="250px">
													<input type="text" name="insnivvalminnum" id="insnivvalminnum" size="7" maxlength="6" onkeypress="return validarNumeros(event)" value='<c:if test="${requestScope.nuevoNivelEvalref==1}"><c:out value="${sessionScope.instNivelEvaluacionVO.insnivvalminnum}"/></c:if>' >
												</td>
												<td colspan="1" width="70px">
													<font color="red">*</font>Valor Máximo
												</td>
												<td colspan="1" width="250px">
													<input type="text" name="insnivvalmaxnum" id="insnivvalmaxnum" size="7" maxlength="6" onkeypress="return validarNumeros(event)" value='<c:if test="${requestScope.nuevoNivelEvalref==1}"><c:out value="${sessionScope.instNivelEvaluacionVO.insnivvalmaxnum }"/></c:if>' >
												</td>
												<td colspan="1" width="70px">
													<font color="red">*</font>Valor Aprobar
												</td>
												<td colspan="1" width="250px">
													<input type="text" size="7" maxlength="6" id="insnivvalaprobnum" name="insnivvalaprobnum" onkeypress="return validarNumeros(event)"  value='<c:if test="${requestScope.nuevoNivelEvalref==1}"><c:out value="${sessionScope.instNivelEvaluacionVO.insnivvalaprobnum }"/></c:if>'>
												</td>
				    						</tr>
				    					</table>
				   					</td>
			     				</tr>
							</table>
			   			</fieldset>
			  		</td>
				</tr>
				<tr style="display:none">
					<td>
						<iframe name="frameAjaxNuevo" id="frameAjaxNuevo"></iframe>
					</td>
				</tr>
		   	</table>
			<!-- TABLA DE LOS RESULTADOS DE LA BUSQUEDA -->
		</form>
		
	</body>
	
	<script>
  		seleccion();
  		validarValorClic(); 
	       
	       
		function seleccion() {
	  		try {
	  			if(document.frmNuevo.insnivtipoevalasig.value == 1 || document.frmNuevo.insnivtipoevalasig.value == 3) {
					// document.frmNuevo.insnivtipoevalprees.disabled = false;
	     			document.frmNuevo.insnivvalminnum.disabled = false;
	     			document.frmNuevo.insnivvalmaxnum.disabled = false;
	     			document.frmNuevo.insnivvalaprobnum.disabled =  false;
	     			document.frmNuevo.insnivmodoeval.disabled =  false;
	     			document.frmNuevo.fieldset_.disabled =  false;
	   			} else {
					document.frmNuevo.insnivvalminnum.disabled =  true;
					document.frmNuevo.insnivvalmaxnum.disabled =  true;
					document.frmNuevo.insnivvalaprobnum.disabled =  true;
					document.frmNuevo.insnivmodoeval.disabled =  true;
					document.frmNuevo.insnivmodoeval.value = -99;
					document.frmNuevo.fieldset_.disabled =  true;
					document.frmNuevo.insnivvalaprobnum.value = '';
					document.frmNuevo.insnivvalminnum.value = '';
					document.frmNuevo.insnivvalmaxnum.value = '';
	   			}
	   		} catch(e) {
	        	//alert("Error en esta pagina javaScript:" + e);
	   		}
		}
	</script>
	
</html>
