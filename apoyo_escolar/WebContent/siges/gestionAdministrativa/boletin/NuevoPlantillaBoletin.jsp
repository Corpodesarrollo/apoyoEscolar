<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="plantillaBoletionVO" class="siges.gestionAdministrativa.boletin.vo.PlantillaBoletionVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.gestionAdministrativa.boletin.vo.ParamsVO" scope="page"/>
<html>
<head>
  
  
<script languaje="text/javascript">
<!--

 	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno","-99");
	}
	
	
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

function validarNumeros2(e) { 
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true; 
    patron = /^\d/;
    te = String.fromCharCode(tecla); 
    return patron.test(te); 
} 



  
  
  
  
  function nuevo(){
	 document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
	 document.frmNuevo.submit();
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
   
   
   	  
    
    
	function hacerValidaciones_frmNuevo2(forma){
     validarLista(document.frmNuevo.plaboltipodoc , '- Tipo de documento', 1);
	   validarCampo(document.frmNuevo.plabolnumdoc , '- Número de documento');
	} 
	
	
   function ajaxEst(){
  // limpiar();     
        document.getElementById('miTabla').style.display='none';		
 
	 if(  validarForma(document.frmNuevo2)  ) { 
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
      document.frmAjaxNuevo.ajax[1].value = document.frmNuevo.plaboltipodoc.value;
      document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.plabolnumdoc.value;
      document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_EST.value;
	  document.frmAjaxNuevo.submit();
    }
   }
   
   
   
   
	function hacerValidaciones_frmNuevo(forma){
 
	   validarLista(forma.plaboltipodoc , '- Tipo de documento', 1);
	   validarCampo(forma.plabolnumdoc , '- Número de documento');
	   validarCampo(forma.plabolnomb , '- Nombre del estudiante');
	   validarLista(forma.plabolsede , '- Sede', 1);
	   validarLista(forma.plaboljornd , '- Jornada', 1);
	   validarLista(forma.plabolmetodo , '- Metodologia', 1);
	   validarLista(forma.plabolgrado , '- Grado', 1);
	   validarLista(forma.plabolgrupo , '- Grupo', 1);
	   validarLista(forma.plabolperido , '- Periodo', 1);
	   
	   
	 
	  
	  
	} 
	
	
      function generar(){
	
	   if(validarForma(document.frmNuevo)){
	 	 
	   document.frmNuevo.plabolsedeNomb.value = document.frmNuevo.plabolsede.options[document.frmNuevo.plabolsede.options.selectedIndex].text;
	   document.frmNuevo.plaboljorndNomb.value = document.frmNuevo.plaboljornd.options[document.frmNuevo.plaboljornd.options.selectedIndex].text;
	   document.frmNuevo.plabolgradoNomb.value = document.frmNuevo.plabolgrado.options[document.frmNuevo.plabolgrado.options.selectedIndex].text;
	   document.frmNuevo.plabolgrupoNomb.value = document.frmNuevo.plabolgrupo.options[document.frmNuevo.plabolgrupo.options.selectedIndex].text;
	   document.frmNuevo.plabolperidoNomb.value = document.frmNuevo.plabolperido.options[document.frmNuevo.plabolperido.length -1].text;
	   document.frmNuevo.plabolperidoNum.value = document.frmNuevo.plabolperido.length - 2;
	   document.frmNuevo.plabolnumdoc2.value = document.frmNuevo.plaboltipodoc.options[document.frmNuevo.plaboltipodoc.options.selectedIndex].text +' '+ document.frmNuevo.plabolnumdoc.value ;
	   document.frmNuevo.cmd.value = document.frmNuevo.CMD_GUARDAR.value;
	   document.frmNuevo.action='<c:url value="/siges/gestionAdministrativa/boletin/Save.jsp"/>';
	   document.frmNuevo.submit();
 
	 }
   }
   
           
      function descargar(){
	    document.frmNuevo.action = '<c:url value="/siges/gestionAdministrativa/plantillaBoletin/PlantillaBoletin.do"/>';
	    document.frmNuevo.cmd.value = document.frmNuevo.CMD_GENERAR.value;
	    document.frmNuevo.target="_blank"
	    document.frmNuevo.submit();
	  }
	  
	  
	             
      function limpiar(){ 
      borrar_combo2(document.frmNuevo.plaboljornd);
      borrar_combo2(document.frmNuevo.plabolmetodo);
      borrar_combo2(document.frmNuevo.plabolgrado);
      borrar_combo2(document.frmNuevo.plabolgrupo);
      document.frmNuevo.plabolnomb.value = '';
      
      document.getElementById('miTabla').style.display='none';				
	 
			
  }
   
	  
	 -->
	 
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
 	   <form method="post" name="frmNuevo2" >
	</form>
	   
 	   <form method="post" name="frmAjaxNuevo" action='<c:url value="/siges/gestionAdministrativa/plantillaBoletin/PlantillaBoletinAjax.do"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${ paramsVO.FICHA_PLANTILLA_BOLETIN }"/>'>
		<input type="hidden" name="cmd"  value='-1'>
		<input type="hidden" name="CMD_AJAX_JORD"  id="CMD_AJAX_JORD"  value='<c:out value="${paramsVO.CMD_AJAX_JORD}"/>'>
		<input type="hidden" name="CMD_AJAX_METD"  id="CMD_AJAX_METD"  value='<c:out value="${paramsVO.CMD_AJAX_METD}"/>'>
		<input type="hidden" name="CMD_AJAX_GRUP"   id="CMD_AJAX_GRUP"   value='<c:out value="${paramsVO.CMD_AJAX_GRUP}"/>'>
		<input type="hidden" name="CMD_AJAX_GRAD"  id="CMD_AJAX_GRAD"  value='<c:out value="${paramsVO.CMD_AJAX_GRAD}"/>'>
		<input type="hidden" name="CMD_AJAX_EST"  id="CMD_AJAX_EST"  value='<c:out value="${paramsVO.CMD_AJAX_EST}"/>'>
		
		
		
		<c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach>
	  </form>
	
		 <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		  <caption>Gestión Administrativa >> Generar Boletin</caption>
	         <tr style="height: 20px"><td>&nbsp;</td></tr>
		    <tr height="1">
						 <td width="10" bgcolor="#FFFFFF">&nbsp;</td>							
			 <td rowspan="2" bgcolor="#FFFFFF">
			       <a href="javaScript:lanzar_(1)"><img src='<c:url value="/etc/img/tabs/Boletines_0.gif"/>' alt="Parametros" height="26" border="0"></a>
			       <a href="javaScript:lanzar_(4)"><img src='<c:url value="/etc/img/tabs/logros_pendientes_0.gif"/>' alt="Parametros" height="26" border="0"></a>
				   <img src='<c:url value="/etc/img/tabs/boletin_plantilla_1.gif"/>' border="0"  height="26" alt='Escala Conceptual'>
		        </td>
            </tr>
          </table>
 
		
   <!-- FORMUALARIO DE EDICION CREACION--> 		  
	<form method="post" name="frmNuevo"  action='<c:url value="/siges/gestionAdministrativa/boletin/Save.jsp"/>'>
        <input type="hidden" name="tipo"    value='<c:out value="${paramsVO.FICHA_PLANTILLA_BOLETIN}"/>'>
        <input type="hidden" name="cmd"     value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO"   value='<c:out value="${paramsVO.CMD_NUEVO}"/>'> 
        <input type="hidden" name="CMD_GUARDAR"      id="CMD_GUARDAR"  value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
        <input type="hidden" name="CMD_GENERAR"   value='<c:out value="${paramsVO.CMD_GENERAR}"/>'> 
        <input type="hidden" name="plabolsedeNomb"   id="plabolsedeNomb"  >
        <input type="hidden" name="plaboljorndNomb"   id="plaboljorndNomb"  >
        <input type="hidden" name="plabolmetodoNomb" id="plabolmetodoNomb"  >
        <input type="hidden" name="plabolgradoNomb"  id="plabolgradoNomb"  >
        <input type="hidden" name="plabolgrupoNomb"  id="plabolgrupoNomb"  >
        <input type="hidden" name="plabolperidoNomb" id="plabolperidoNomb"  >
        <input type="hidden" name="plabolperidoNum"  id="plabolperidoNum"  >
         <input  type="hidden"  name="plabolnumdoc2" id="plabolnumdoc2" size="15"  maxlength="15"  value='<c:out value="${sessionScope.plantillaBoletionVO.plabolnumdoc  }"/>' >


       <table border="0" align="center" width="100%" cellpadding="2" cellspacing="2">
			<caption>Filtro de búsqueda estudiante en el sistema de matricula</caption>
		 	<tr><td colspan="4"><input type="button" value="buscar" class="boton"  onclick="ajaxEst();"></td></tr>
		 	<tr><td><span class="style2" >*</span>Tipo de documento</td>
		 	    <td><select name="plaboltipodoc"  id="plaboltipodoc"  onchange="limpiar();" >
		 	        <option value="-99">-- Seleccione uno--</option>
                     <c:forEach begin="0" items="${requestScope.filtroTiposDoc}" var="vig">
				       <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.plantillaBoletionVO.plaboltipodoc ==vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	    <td><span class="style2" >*</span>Número de documento</td>
		 	    <td><input  type="text"  name="plabolnumdoc" id="plabolnumdoc" size="15"  maxlength="15"  value='<c:out value="${sessionScope.plantillaBoletionVO.plabolnumdoc  }"/>'    onkeypress="limpiar();"  onkeyup="limpiar();">
		 	    </td>
		 	</tr>
		</table>	
		
		  <table border="0" align="center" width="100%" cellpadding="2" cellspacing="2">
			<caption>DATOS DEL ESTUDIANTE</caption>
		 		 	<tr><td colspan="4"><input type="button" value="Generar" class="boton"  onclick="generar();"></td></tr>
		 	<tr><td  >Nombre</td>
		 	   <td colspan="3"><input type="text" name="plabolnomb" id="plabolnomb" value='<c:out value="${sessionScope.plantillaBoletionVO.plabolnomb  }"/>'  readonly="readonly" style="width: 350px; "></td>
		 	</tr>
		 	<tr><td style="width: 15%"><span class="style2" >*</span>Sede</td>
		 	    <td style="width: 35%"><select  name="plabolsede" id="plabolsede" style="width: 150px;" onchange="JavaScript: ajaxJornada();">
		 	        <option value="-99">-- Seleccione uno--</option>
		 	         <c:forEach begin="0" items="${sessionScope.listaSede}" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.plantillaBoletionVO.plabolsede ==vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	      </select>
		 	    </td>
		 	    <td style="width: 15%"><span class="style2" >*</span>Jornada</td>
		 	    <td style="width: 35%"><select name="plaboljornd" id="plaboljornd" style="width: 15opx;" onchange="ajaxMetodologia();"  >
		 	        <option value="-99">-- Seleccione uno--</option>
		 	          <c:forEach begin="0" items="${ sessionScope.listaJornada }" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.plantillaBoletionVO.plaboljornd ==vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	 </tr>   
		 	 <tr><td><span class="style2" >*</span>Metodología</td>
		 	    <td><select name="plabolmetodo" id="plabolmetodo" onchange="ajaxGrado();">
		 	        <option value="-99">-- Seleccione uno--</option>
		 	            <c:forEach begin="0" items="${sessionScope.listaMetodo }" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.plantillaBoletionVO.plabolmetodo  ==vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	    <td><span class="style2" >*</span>Grado</td>
		 	    <td><select name="plabolgrado" id="plabolgrado" onchange="ajaxGrupo();">
		 	        <option value="-99">-- Seleccione uno--</option>
		 	              <c:forEach begin="0" items="${sessionScope.listaGrado }" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.plantillaBoletionVO.plabolgrado == vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	 </tr>   
		 	  <tr><td><span class="style2" >*</span>Grupo</td>
		 	    <td><select  name="plabolgrupo" id="plabolgrupo" >
		 	        <option value="-99">-- Seleccione uno--</option>
		 	         <c:forEach begin="0" items="${sessionScope.listaGrupo }" var="vig">
				      <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.plantillaBoletionVO.plabolgrupo == vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	    <td><span class="style2" >*</span>Periodo</td>
		 	    <td><select  name="plabolperido" id="plabolperido" > 
		 	          <option value="-99">-- Seleccione uno--</option>
		 	              <c:forEach begin="0" items="${sessionScope.filtroPeriodo }" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.plantillaBoletionVO.plabolperido == vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	 </tr>  
		 	 <tr>
		 	 <td colspan="4">
		 	<table border="0"   id="miTabla"  align="center" width="95%" cellpadding="0" cellspacing="0">
		 	 	<c:if test="${sessionScope.resultadoConsulta.generado==true}">
			
			 	<caption>Resultado</caption>
					<tr>
						<th><a target="_blank" href='<c:url value="/${resultadoConsulta.ruta}"><c:param name="tipo" value="${resultadoConsulta.tipo}"/></c:url>'><img src='<c:url value="/etc/img/zip.gif"/>' border="0"></a></th>
					</tr>
					<tr>
						<th>Pulse en el ícono para descargar el reporte</th>
					</tr>
					 
			
		</c:if>
		</table>
		 	  </td>
		 	 </tr>
		 	<tr style="display:none"><td><iframe name="frameAjaxNuevo" id="frameAjaxNuevo"></iframe></td></tr>
		</table>	
	</form>
</body>
<script>  
		 	  <c:if test="${sessionScope.resultadoConsulta.generado == true}">
		//	         descargar();
		            //document.getElementById('miTabla').style.display='';	
		       </c:if>
</script>

</html>