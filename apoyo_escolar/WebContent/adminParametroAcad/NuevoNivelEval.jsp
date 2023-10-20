<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="paramsVO"   class="siges.adminParamsAcad.vo.ParamsVO" scope="page"/>
<c:import url="/parametros.jsp"/>
<c:set var="dat" value="1" scope="page"/>
	<html>
	<head>
		<title>Datos Maestros - Parámetros Académicos</title>

		<script languaje='javaScript'>
		<!--
			/*                                                                                                             
			function ayuda(){
 			 remote = window.open("");
			 remote.location.href="/sae/tutor/nuevo_registro_orientadora/index.html";
			}
			*/
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}

			function lanzar(dato){
				document.frmNuevo.dato.value=dato;	
				document.frmNuevo.action="<c:url value="/datoMaestro/DatoMaestroSeleccionEdit.do"/>";
				document.frmNuevo.submit();
			}
			
			function lanzar_(tipo){				
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.cmd.value='-1';
				document.frmNuevo.action = '<c:url value="/adminParamsAcad/Nuevo.do"/>';
				document.frmNuevo.target = ""; 
				document.frmNuevo.submit();
			}
			
			function cancelar(){
				if (confirm('¿Desea cancelar la configuración de los datos maestros?')){
				 location.href='<c:url value="/bienvenida.jsp"/>';
				}		
			}			

			function cargar(forma){
				//<c:if test="${sessionScope.nuevoBasica.estado== '1'}">inhabilitarFormulario();</c:if>
				<c:if test="${requestScope.ok=='ok' && sessionScope.editar=='1'}">
						document.f.submit();
				</c:if>
				mensaje(document.getElementById("msg"));
			}
			
			
			function hacerValidaciones_frmNuevo(forma){
	       
	         <c:forEach begin="0" items="${requestScope.listanivelEvaluacionVO  }" var="lista" varStatus="st">
	            validarLista(forma.estados_<c:out value="${ lista.g_nivevacodigo}"/>,'- Estado del nivel  <c:out value="${ lista.g_nivevanombre}"/>' ,1);
	          </c:forEach>
	       
	        }
	
	
			function guardar_(){
		    if(validarForma(document.frmNuevo)){
			     document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			     <c:forEach begin="0" items="${requestScope.listanivelEvaluacionVO  }" var="lista" varStatus="st">
	                if( ('<c:out value="${st.count}"/>' - 1 ) <20){
	                document.frmNuevo.listaEstados_['<c:out value="${st.count}"/>' - 1].value =  document.frmNuevo.estados_<c:out value="${ lista.g_nivevacodigo}"/>.value;
	                }
                  </c:forEach>
			     document.frmNuevo.submit();
		      }
	         }
			//-->
		</script>
	    <style type="text/css">
			<!--
			.style2 {color: #FF6666}
			-->
        </style>
	</head>
<c:import url="/mensaje.jsp"/>
<body onLoad='cargar(frmNuevo)'>
 
   <form method="post" name="frmNuevo"   action='<c:url value="/adminParamsAcad/Nuevo.do"/>'>
	 <input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
	   <input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		  
	<input type="hidden" name="dato" value='<c:out value="${pageScope.dat}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<input type="hidden" name="tipo" value="2">
	
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
	  <tr><td  width="750px" rowspan="2" bgcolor="#FFFFFF"> <a href="javaScript:lanzar(22)"><img src="../etc/img/tabs/areas_0.gif" alt="&Aacute;reas"  height="26" border="0"></a>  <a href="javaScript:lanzar(29)"><img src="../etc/img/tabs/asignatura_0.gif" alt="Asignaturas"  height="26" border="0"></a><a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/jornada_0.gif" alt="Jornadas"  height="26" border="0"></a><a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/nivel_0.gif" alt="Niveles"  height="26" border="0"></a><a href="javaScript:lanzar(23)"><img src="../etc/img/tabs/grados_0.gif" alt="Grados"  height="26" border="0"></a><a href="javaScript:lanzar(24)"><img src="../etc/img/tabs/calendario_0.gif" alt="Calendarios"  height="26" border="0"></a><a href="javaScript:lanzar(9)"><img src="../etc/img/tabs/metodologia_0.gif" alt="Metodolog&iacute;as"  height="26" border="0"></a><br><a href="javaScript:lanzar(46)"><img src="../etc/img/tabs/idiomas_0.gif" alt="Idiomas"  height="26" border="0"></a><a href="javaScript:lanzar(42)"><img src="../etc/img/tabs/escalas_valorativas_0.gif" alt="Escalas Valorativas"  height="26" border="0"></a><a href="javaScript:lanzar(12)"><img src="../etc/img/tabs/especialidad_0.gif" alt="Especialidades"  height="26" border="0"></a><a href="javaScript:lanzar(53)"><img src="../etc/img/tabs/tipos_enfasis_0.gif" alt="Tipos de Énfasis"  height="26" border="0"></a><a href="javaScript:lanzar(52)"><img src="../etc/img/tabs/tipos_modalidad_0.gif" alt="Tipos de Modalidad"  height="26" border="0"></a><a href="javaScript:lanzar(51)"><img src="../etc/img/tabs/tipos_especialidad_0.gif" alt="Tipos de Especialidad"  height="26" border="0"></a><a href="javaScript:lanzar_(1)"><img src='<c:url value="/etc/img/tabs/adm_nivelEval_1.gif"/>'  alt="Nivel Evaluación"  height="26" border="0"> 
	  <a href="javaScript:lanzar_(2)"><img src="../etc/img/tabs/dimensiones_0.gif" alt="Dimensiones"  height="26" border="0"></a>
	  </td></tr>
	
     </table>
	
	 <!-- FORMULARIO NUEVO/EDICION -->
	  <table cellpadding="0" cellspacing="0" border="0" width="100%">
	   <tr><td height="25px">&nbsp;</td>
		</tr>
	    <tr>
		  <td   bgcolor="#FFFFFF" colspan="4">
			<input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar_()">
			<input class='boton' name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
		 </td>
	   </tr>
	    <tr style="height: 3px;"><td height="3px"></td>
	   <tr>
		<td colspan="4">
		 <table align="center" cellpadding="1" cellspacing="0" border="1" width="75%">
		
		 <caption>Lista Nivel Evaluación</caption>
			 	<c:if test="${empty requestScope.listanivelEvaluacionVO}"><tr><th class="Fila1" colspan='6'>No hay datos de nivel evaluación</th></tr></c:if>
				<c:if test="${!empty requestScope.listanivelEvaluacionVO}">
					<tr> 
						 <th class="EncabezadoColumna" align="center">Código</th>
						<th class="EncabezadoColumna" align="center">Nombre</th>
						<th class="EncabezadoColumna" align="center">Estado</th>
					</tr>
					<c:forEach begin="0" items="${requestScope.listanivelEvaluacionVO  }" var="lista" varStatus="st">
						<tr>
						  <td class='Fila<c:out value="${st.count%2}"  />'   >&nbsp;<c:out value="${lista.g_nivevacodigo}"/></td> 
							<td class='Fila<c:out value="${st.count%2}"/>'  >&nbsp;<c:out value="${lista.g_nivevanombre}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"  >
							    <input type="hidden" name="listaEstados_" id="listaEstados_" > 
							     <select name='estados_<c:out value="${ lista.g_nivevacodigo}"/>' style="width: 100px;" >
							       <option>--Seleccione uno-</option>
							        <c:forEach begin="0" items="${requestScope.listaEstados}" var="lista2" varStatus="st">
							         <option value='<c:out value="${lista2.codigo}"/>|<c:out value="${lista.g_nivevacodigo}"/>'  '<c:if test="${lista.g_nivevaestado == lista2.codigo}">' SELECTED '</c:if>'  >  <c:out value="${lista2.nombre}"/> </option>
							        </c:forEach>
							     </select> 
							 </td>
						</tr>
					</c:forEach>
					  
				</c:if> 
		  </table>
		  
		 </td>
		</tr>	
		<input type="hidden" name="listaEstados_" id="listaEstados_" > 	
	 </table>	 
  </form>
 </body>
</html>