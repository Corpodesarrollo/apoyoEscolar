<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="filtroBoletinVO" class="siges.gestionAdministrativa.padreFlia.vo.FiltroBoletinVO" scope="session"/>
<jsp:useBean  id="estudinateMarcarVO" class="siges.gestionAdministrativa.padreFlia.vo.EstudianteMarcarVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.gestionAdministrativa.padreFlia.vo.ParamsVO" scope="page"/>
<html>
<head>
  
  
<script languaje="text/javascript">
 <!--

 	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno","-99");
	}
	
	 
	
	
	   function lanzar(tipo){				
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.cmd.value= -1;
				document.frmNuevo.action="<c:url value="/boletines/ControllerBoletinFiltroEdit.do"/>";
				document.frmNuevo.target = ""; 
			 	document.frmNuevo.submit();
			}
		
		
		function lanzar_(tipo){
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.cmd.value='Cancelar';
				document.frmNuevo.action="<c:url value="/boletines/ControllerBoletinFiltroEdit.do"/>";
				document.frmNuevo.target="";
				document.frmNuevo.submit();
			}	
 
			
	function cancelar(){
				location.href='<c:url value="/bienvenida.jsp"/>';
	}
	 
	 
	 
	 
	 
 	function ajaxJornada(){ 
	   
	  borrar_combo2(document.frmNuevo.plaboljornd);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value =  document.frmNuevo.plabolsede.value;
	  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_JORD.value;
	  document.frmAjaxNuevo.submit();
	 
	}
	
	
	function ajaxMetodologia(){ 
	  borrar_combo2(document.frmNuevo.plabolmetodo);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.plabolsede.value;
	  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_METD.value;
	  document.frmAjaxNuevo.submit();
	 
	}
	 
	 
 
	 
	 
	 function ajaxGrado(){
 
	  borrar_combo2(document.frmNuevo.plabolgrado);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.plabolsede.value;
      document.frmAjaxNuevo.ajax[1].value = document.frmNuevo.plaboljornd.value;
      document.frmAjaxNuevo.ajax[2].value = document.frmNuevo.plabolmetodo.value;
      document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_GRAD.value;
      document.frmAjaxNuevo.submit();
	 
	}
	
	
   function ajaxGrupo(){
	 
	  borrar_combo2(document.frmNuevo.plabolgrupo);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
      document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.plabolsede.value;
      document.frmAjaxNuevo.ajax[1].value = document.frmNuevo.plaboljornd.value;
      document.frmAjaxNuevo.ajax[2].value = document.frmNuevo.plabolmetodo.value;
      document.frmAjaxNuevo.ajax[3].value = document.frmNuevo.plabolgrado.value;
      document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_GRUP.value;
	  document.frmAjaxNuevo.submit();
 
	
   }
   
   
   	  
    
 
   
   
   
   
	function hacerValidaciones_frmNuevo(forma){
 

	   validarLista(forma.plabolsede , '- Sede', 1);
	   validarLista(forma.plaboljornd , '- Jornada', 1);
	   validarLista(forma.plabolmetodo , '- Metodologia', 1);
	   validarLista(forma.plabolgrado , '- Grado', 1);
	   validarLista(forma.plabolgrupo , '- Grupo', 1);
	   validarLista(forma.plabolperido , '- Periodo', 1);
	   
	 
	  
	  
	} 
	
	
 
   
    
 
 
 
 
   
     function buscar(){
	
	   if(validarForma(document.frmNuevo)){
	 	 document.frmNuevo.cmd.value = document.frmNuevo.CMD_BUSCAR.value;
	    document.frmNuevo.submit();
 
	 }
   }
 
   
   
   
   
     
 
 
  /*  function guardar(){
	     for(var i=0; i< document.frmNuevoEst.listaEstados_.length;i++){
          
           
            document.frmNuevoEst.listaEstados_[i].value = document.getElementById( "estConsulta|1" ).value;
            if(i< 5){
            alert(document.frmNuevoEst.listaEstados_[i].value);
         
            }
        }
         document.frmNuevoEst.cmd.value = document.frmNuevo.CMD_GUARDAR.value;
	    document.frmNuevoEst.submit();
     }*/
 
 

                  
  function guardar(){

	  <c:forEach begin="0" items="${  requestScope.listaEst   }" var="lista" varStatus="st">
	       if(document.frmNuevoEst.estConsulta_<c:out value="${st.count}"/>_[0].checked == true){
	        document.frmNuevoEst.listaEstados_['<c:out value="${st.count}"/>' - 1].value =  document.frmNuevoEst.estConsulta_<c:out value="${st.count}"/>_[0].value;
	      }else{
	        document.frmNuevoEst.listaEstados_['<c:out value="${st.count}"/>' - 1].value =  document.frmNuevoEst.estConsulta_<c:out value="${st.count}"/>_[1].value;
	      }
	  </c:forEach>
       
        document.frmNuevoEst.cmd.value = document.frmNuevo.CMD_GUARDAR.value;
	    document.frmNuevoEst.submit();
 }
 
 
   function todo( a){
     <c:forEach begin="0" items="${  requestScope.listaEst   }" var="lista" varStatus="st">
	       document.frmNuevoEst.estConsulta_<c:out value="${st.count}"/>_[a].checked = true;
	  </c:forEach>
    }

   function generar(){		
			document.frmReporte.cmd.value =  document.frmNuevo.GENERAR.value;			
			document.frmReporte.sede.value=document.frmNuevo.plabolsede.value;
			document.frmReporte.jornada.value=document.frmNuevo.plaboljornd.value;
			document.frmReporte.metod.value=document.frmNuevo.plabolmetodo.value;
			document.frmReporte.grado.value=document.frmNuevo.plabolgrado.value;
			document.frmReporte.grupo.value=document.frmNuevo.plabolgrupo.value;			
			document.frmReporte.periodo.value=document.frmNuevo.plabolperido.value;
		    document.frmReporte.submit();		
	}  
 
	 //--> 

	 
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
 	   <form method="post" name="frmNuevo2" >
	</form>
	
 	   <form method="post" name="frmAjaxNuevo" action='<c:url value="/siges/gestionAdministrativa/padreFlia/Ajax.do"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${ paramsVO.FICHA_PADRE_FLIA}"/>'>
		<input type="hidden" name="cmd"  value='-1'>
		<input type="hidden" name="CMD_AJAX_JORD"  id="CMD_AJAX_JORD"  value='<c:out value="${paramsVO.CMD_AJAX_JORD}"/>'>
		<input type="hidden" name="CMD_AJAX_METD"  id="CMD_AJAX_METD"  value='<c:out value="${paramsVO.CMD_AJAX_METD}"/>'>
		<input type="hidden" name="CMD_AJAX_GRUP"   id="CMD_AJAX_GRUP"   value='<c:out value="${paramsVO.CMD_AJAX_GRUP}"/>'>
		<input type="hidden" name="CMD_AJAX_GRAD"  id="CMD_AJAX_GRAD"  value='<c:out value="${paramsVO.CMD_AJAX_GRAD}"/>'>
		<input type="hidden" name="CMD_AJAX_EST"  id="CMD_AJAX_EST"  value='<c:out value="${paramsVO.CMD_AJAX_EST}"/>'>
		 <input type="hidden" name="CMD_BUSCAR"      id="CMD_BUSCAR"  value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		
		
		<c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach>
	  </form>
	
		 <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		  <caption>Gestión Administrativa >> Padre de Flia</caption>
	         <tr style="height: 20px"><td>&nbsp;</td></tr>
		    <tr height="1">
						 <td width="10" bgcolor="#FFFFFF">&nbsp;</td>							
			 <td rowspan="2" bgcolor="#FFFFFF">
			       <img src='<c:url value="/etc/img/tabs/padre_flia_1.gif"/>' border="0"  height="26" alt='Escala Conceptual'>
		        </td>
            </tr>
          </table>
 
 <form action='<c:url value="/siges/gestionAdministrativa/padreFlia/Nuevo.do"/>' method="post" name="frmReporte" target="">			
			<input type="hidden" name="inst" value='0'>
			<input type="hidden" name="sede" value='0'>
			<input TYPE="hidden" NAME="jornada" VALUE='1'>
			<input TYPE="hidden" NAME="metod" VALUE='1'>
			<input TYPE="hidden" NAME="grado" VALUE='1'>
			<input TYPE="hidden" NAME="grupo" VALUE='1'>
			<input TYPE="hidden" NAME="periodo" VALUE='1'>
			<input type="hidden" name="cmd" value=''>
			<input type="hidden" name="ext" value='1'>			
	</form>
		
   <!-- FORMUALARIO DE EDICION CREACION--> 		  
	<form method="post" name="frmNuevo"  action='<c:url value="/siges/gestionAdministrativa/padreFlia/Save.jsp"/>'>
        <input type="hidden" name="tipo"    value='<c:out value="${paramsVO.FICHA_PADRE_FLIA}"/>'>
        <input type="hidden" name="cmd"     value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="GENERAR" value='<c:out value="${paramsVO.CMD_GENERAR}"/>'>
		<input type="hidden" name="NUEVO"   value='<c:out value="${paramsVO.CMD_NUEVO}"/>'> 
        <input type="hidden" name="CMD_GUARDAR"      id="CMD_GUARDAR"  value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
        <input type="hidden" name="CMD_BUSCAR"      id="CMD_BUSCAR"  value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
       


 
		
		  <table border="0" align="center" width="100%" cellpadding="2" cellspacing="2">
			<caption>ADMINISTRACIÓN CONSULTA BOLETINES PADRES DE FAMILIA</caption>
		 		 	<tr><td colspan="4"><input type="button" value="Buscar" class="boton"  onclick="JavaScript:buscar();">
		 		 	
		 		 	</td></tr>
		  
		 	<tr><td style="width: 15%"><span class="style2" >*</span>Sede</td>
		 	    <td style="width: 35%"><select  name="plabolsede" id="plabolsede" style="width: 150px;" onchange="JavaScript:borrar_combo2(document.frmNuevo.plabolmetodo); ajaxJornada();">
		 	        <option value="-99">-- Seleccione uno--</option>
		 	         <c:forEach begin="0" items="${sessionScope.listaSede}" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.filtroBoletinVO.plabolsede ==vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	      </select>
		 	    </td>
		 	    <td style="width: 15%"><span class="style2" >*</span>Jornada</td>
		 	    <td style="width: 35%"><select name="plaboljornd" id="plaboljornd" style="width: 15opx;" onchange="ajaxMetodologia();"  >
		 	        <option value="-99">-- Seleccione uno--</option>
		 	          <c:forEach begin="0" items="${ sessionScope.listaJornada }" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.filtroBoletinVO.plaboljornd ==vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	 </tr>   
		 	 <tr><td><span class="style2" >*</span>Metodología</td>
		 	    <td><select name="plabolmetodo" id="plabolmetodo" onchange="ajaxGrado();">
		 	        <option value="-99">-- Seleccione uno--</option>
		 	            <c:forEach begin="0" items="${sessionScope.listaMetodo }" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.filtroBoletinVO.plabolmetodo  ==vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	    <td><span class="style2" >*</span>Grado</td>
		 	    <td><select name="plabolgrado" id="plabolgrado" onchange="ajaxGrupo();">
		 	        <option value="-99">-- Seleccione uno--</option>
		 	              <c:forEach begin="0" items="${sessionScope.listaGrado }" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.filtroBoletinVO.plabolgrado == vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	 </tr>   
		 	  <tr><td><span class="style2" >*</span>Grupo</td>
		 	    <td><select  name="plabolgrupo" id="plabolgrupo" >
		 	        <option value="-99">-- Seleccione uno--</option>
		 	         <c:forEach begin="0" items="${sessionScope.listaGrupo }" var="vig">
				      <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.filtroBoletinVO.plabolgrupo == vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	    <td><span class="style2" >*</span>Periodo</td>
		 	    <td><select  name="plabolperido" id="plabolperido" > 
		 	          <option value="-99">-- Seleccione uno--</option>
		 	              <c:forEach begin="0" items="${sessionScope.filtroPeriodo }" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.filtroBoletinVO.plabolperido == vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	 </tr>  
		 	 <tr>
		 	 <td colspan="4">
		 	  <c:if test="${sessionScope.resultadoReportes.generado==true}">
			   <table border="0" align="center" width="95%" cellpadding="0" cellspacing="0">
			 	<caption>Resultado</caption>
					<tr>
						<th><a target="_blank" href='<c:url value="/${resultadoReportes.ruta}"><c:param name="tipo" value="${resultadoReportes.tipo}"/></c:url>'><img src='<c:url value="/etc/img/zip.gif"/>' border="0"></a></th>
					</tr>
					<tr>
						<th>Pulse en el ícono para descargar el reporte</th>
					</tr>
			    </table>
		       </c:if>
		 	  </td>
		 	 </tr>
		 	<tr style="display:none"><td><iframe name="frameAjaxNuevo" id="frameAjaxNuevo"></iframe></td></tr>
		</table>	
	</form>
	
	  <!-- FORMUALARIO DE EDICION CREACION--> 		  
	<form method="post" name="frmNuevoEst"  action='<c:url value="/siges/gestionAdministrativa/padreFlia/Nuevo.do"/>'>
        <input type="hidden" name="tipo"    value='<c:out value="${paramsVO.FICHA_PADRE_FLIA}"/>'>
        <input type="hidden" name="cmd"     value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO"   value='<c:out value="${paramsVO.CMD_NUEVO}"/>'> 
        <input type="hidden" name="CMD_GUARDAR"      id="CMD_GUARDAR"  value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
       <table border="0" align="center" width="100%" cellpadding="2" cellspacing="2">
		  <caption>LISTADO DE ESTUDIANTES</caption>
			<c:if test="${!empty requestScope.listaEst }">
			<tr><td colspan="4"><input type="button" value="Guardar" class="boton"  onclick="guardar();">
			</td></tr>
			</c:if>
		   <tr>
		     <td> 
		       <table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray" class="table01" style="text-align: left;" >
		         <c:if test="${empty requestScope.listaEst }"><tr><th class="Fila1" colspan='6'>No hay datos</th></tr></c:if>
				 <c:if test="${!empty requestScope.listaEst }">
				
		         <tr> 
						<th class="EncabezadoColumna"  rowspan="2" colspan="2" align="center">No.</th> 
                        <th class="EncabezadoColumna" colspan="2" align="center"	>DATOS DEL ESTUDIANTE</th>
                        <th class="EncabezadoColumna" colspan="1" align="center"	>PERMITIR CONSULTA</th>
					</tr>
					<tr>	
					
					    <th class="EncabezadoColumna" align="center">Apellidos</th>
						<th class="EncabezadoColumna" align="center">Nombres</th>
						<th class="EncabezadoColumna2" align="center">Si<input type="radio" name="Todos"  onclick="todo(0)" id="Todos">&nbsp; &nbsp; No<input type="radio" name="Todos"  onclick="todo(1)" id="Todos"></th>
					</tr>
					<c:forEach begin="0" items="${requestScope.listaEst   }" var="lista" varStatus="st">
						<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${st.count}"/></th>
						  
						    <td class='Fila<c:out value="${st.count}"  />' >  <input type="hidden" name="listaEstados_" id="listaEstados_"  value="0">  </td>
							<td class='Fila<c:out value="${st.count%2}"  />'  >&nbsp;<c:out value="${lista.estapellido}"/>  <input type="hidden"  name="estcodigo" id="estcodigo" value='<c:out value="${lista.estcodigo}"/>' ></td> 
							<td class='Fila<c:out value="${st.count%2}"/>'  >&nbsp;   <c:out value="${lista.estnombre}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>'  ><div align="center" >&nbsp; 
							  Si<input type="radio"    <c:if test="${ lista.estconsulta == 1}"> checked="checked" </c:if> value='<c:out value="${lista.estcodigo}"/>_1' name='estConsulta|<c:out value="${st.count}"/>_'  id='estConsulta_<c:out value="${st.count}"/>_'   >&nbsp;&nbsp;&nbsp;
							  No<input type="radio"    <c:if test="${ lista.estconsulta == 0}"> checked="checked" </c:if> value='<c:out value="${lista.estcodigo}"/>_0' name='estConsulta|<c:out value="${st.count}"/>_'   id='estConsulta_<c:out value="${st.count}"/>_'   ></div>    </td>
						</tr>
					</c:forEach>
					</c:if>
				 </table>
		     </td>
		   </tr>
		</table> 		 	
      </form> 
</body>
<script> 
   
</script>
</html>