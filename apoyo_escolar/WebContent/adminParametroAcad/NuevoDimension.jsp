<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="paramsVO"   class="siges.adminParamsAcad.vo.ParamsVO" scope="page"/>
<jsp:useBean id="dimensionVO" class="siges.adminParamsAcad.vo.DimensionVO" scope="session"/>
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
				validarCampo(forma.codigo, "- Código Dimension", 1, 3);
				validarCampo(forma.nombre, "- Nombre Dimension", 1, 100);       
	       
	        }
	
	
			function guardar(){
		    if(validarForma(document.frmNuevo)){
			     document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;			    
			     document.frmNuevo.submit();
		      }
	         }

			function nuevo(){		    
				     document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
				     document.frmNuevo.action='<c:url value="/adminParamsAcad/Nuevo.do"/>';				    
				     document.frmNuevo.submit();			    
		         }


			function editar(id_){
				//if(document.frmNuevo.id){
					    document.frmNuevo.id.value=id_;	
						document.frmNuevo.cmd.value=document.frmNuevo.EDITAR.value;						
						document.frmNuevo.action='<c:url value="/adminParamsAcad/Nuevo.do"/>';
						document.frmNuevo.submit();
					
				//}
			}

			function eliminar(id_){
				//if(document.frmNuevo.id){
					document.frmNuevo.id.value=id_;				
						document.frmNuevo.cmd.value=document.frmNuevo.ELIMINAR.value;						
						document.frmNuevo.action='<c:url value="/adminParamsAcad/Nuevo.do"/>';
						document.frmNuevo.submit();
					
				//}
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
 <font size="1">
   <form method="post" name="frmNuevo"   action='<c:url value="/adminParametroAcad/Save.jsp"/>'>
	   <input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
	   <input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
	   <input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
	   <input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		
	<input type="hidden" name="dato" value='<c:out value="${pageScope.dat}"/>'>
	<input type="hidden" name="tipo" value="2">
	<input type="hidden" name="cmd" value="6">
	<input type="hidden" name="ext" value="1">
	<input type="hidden" name="id" value="0">
	<table cellpadding="1" cellspacing="1" border="0" width="100%">
	<caption>PARÁMETROS ACADÉMICOS - DIMENSIONES</caption>
	  <tr height="1">				
		<td  width="650px" rowspan="2" bgcolor="#FFFFFF"><a href="javaScript:lanzar(22)"><img src="../etc/img/tabs/areas_0.gif" alt="&Aacute;reas"  height="26" border="0"></a><a href="javaScript:lanzar(29)"><img src="../etc/img/tabs/asignatura_0.gif" alt="Asignaturas"  height="26" border="0"></a><a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/jornada_0.gif" alt="Jornadas"  height="26" border="0"></a><a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/nivel_0.gif" alt="Niveles"  height="26" border="0"></a><a href="javaScript:lanzar(23)"><img src="../etc/img/tabs/grados_0.gif" alt="Grados"  height="26" border="0"></a><a href="javaScript:lanzar(24)"><img src="../etc/img/tabs/calendario_0.gif" alt="Calendarios"  height="26" border="0"></a><a href="javaScript:lanzar(9)"><img src="../etc/img/tabs/metodologia_0.gif" alt="Metodolog&iacute;as"  height="26" border="0"></a><a href="javaScript:lanzar(46)"><img src="../etc/img/tabs/idiomas_0.gif" alt="Idiomas"  height="26" border="0"></a><a href="javaScript:lanzar(42)"><img src="../etc/img/tabs/escalas_valorativas_0.gif" alt="Escalas Valorativas"  height="26" border="0"></a><a href="javaScript:lanzar(12)"><img src="../etc/img/tabs/especialidad_0.gif" alt="Especialidades"  height="26" border="0"></a><a href="javaScript:lanzar_(1)"><img src='<c:url value="/etc/img/tabs/adm_nivelEval_0.gif"/>' alt="Nivel Evaluación"  height="26" border="0"></a><img src='<c:url value="/etc/img/tabs/dimensiones_1.gif"/>'  alt="Dimensiones"  height="26" border="0">
		</td>
	  </tr>
     </table>
     <br>
     <table cellpadding="0" cellspacing="0" border="0" width="75%" align="center">
	  <tr height="1">
		<td wbgcolor="#FFFFFF">
		
			<div style="width:100%;height:180px;overflow:auto;vertical-align:top;background-color:#ffffff;">
			 <table align="center" cellpadding="2" cellspacing="2" border="0" width="100%">
			 <caption>Lista Dimensiones</caption>
				 	<c:if test="${empty requestScope.listaDimensiones}"><tr><th class="Fila1" colspan='6'>No hay datos de Dimensiones</th></tr></c:if>
					<c:if test="${!empty requestScope.listaDimensiones}">
						<tr> 
						 	<th class="EncabezadoColumna" align="center" width="40px">
						 	<img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'>
						 	<img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'>
						 	</th>
							<th class="EncabezadoColumna" align="center">Código</th>
							<th class="EncabezadoColumna" align="center">Nombre</th>						
						</tr>
						<c:forEach begin="0" items="${requestScope.listaDimensiones}" var="lista" varStatus="st">
							<tr>
								 <th class='Fila<c:out value="${st.count%2}"/>' width="40px;">
									<a href='javaScript:editar(<c:out value="${lista.codigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
									<c:if test="${sessionScope.NivelPermiso==2 }">
									<a href='javaScript:eliminar(<c:out value="${lista.codigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
								 </th>								
							    <td class='Fila<c:out value="${st.count%2}"  />' align="center" width="50px" >&nbsp;<c:out value="${lista.codigo}"/></td> 
								<td class='Fila<c:out value="${st.count%2}"/>'  >&nbsp;<c:out value="${lista.nombre}"/></td>						
							</tr>
						</c:forEach>
					</c:if> 
			  </table>					
			</div>
		</td>			
		
	  </tr>
	 
     </table>
	
	 <!-- FORMULARIO NUEVO/EDICION -->
	  <table cellpadding="2" cellspacing="2" border="0" width="100%">
	  <caption>Dimension Nuevo/Edición</caption>
	  
	    <tr>
		  <td   bgcolor="#FFFFFF" colspan="4">
			<input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar()">
			<input class='boton' name="cmd12" type="button" value="Nuevo" onClick="nuevo()">
		 </td>
	   </tr>
	    <tr style="height: 3px;"><td height="3px"></td>
	   <tr>
		<td colspan="4">
		<table width="100%" height="10px">
			   <tr>
					<td >
						<span class="style2">*</span> Código:
					</td>
					<td>
							<input type="text" name="codigo" id="codigo" size="5" onKeyPress='return acepteNumeros(event)' maxlength='2' value='<c:out value="${sessionScope.dimensionVO.codigo}"/>' <c:if test="${sessionScope.dimensionVO.insertar==0}">readonly=true</c:if> >
						</td>
				</tr>
				<tr >
					<td >
					<span class="style2">*</span>Dimensión:
						</td>
						<td>
							<input type="text" name="nombre" id="nombre" size="40" value='<c:out value="${sessionScope.dimensionVO.nombre}"/>' maxlength='100' >
						</td>			
					</tr>	
						
	     </table>
		 </td>
		</tr>		
	 </table>	
	</font>		 
  </form>
 </body>
</html>