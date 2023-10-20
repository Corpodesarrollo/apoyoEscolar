<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="filtroRepEstadisticoVO" class="siges.gestionAcademica.repEstadisticos.vo.FiltroRepEstadisticoVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.gestionAcademica.repEstadisticos.vo.ParamsVO" scope="page"/>
<html>
<head>
 <script languaje="text/javascript">
 <!--
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
	
 	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno","-99");
	}
	
	function borrar_comboTodos(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("-- Todos --","-99");
	}
	
    function lanzar(tipo){				
				document.frmFiltro.tipo.value=tipo;
				document.frmFiltro.cmd.value= -1;
				document.frmFiltro.action="<c:url value="/boletines/ControllerBoletinFiltroEdit.do"/>";
				document.frmFiltro.target = ""; 
			 	document.frmFiltro.submit();
	}
	
    function cancelar(){
				location.href='<c:url value="/bienvenida.jsp"/>';
	}
	
	function ajaxSede(){ 
	  borrar_combo2(document.frmFiltro.filjornd);
	  borrar_combo2(document.frmFiltro.filmetod);
  	  borrar_combo2(document.frmFiltro.filgrado);
 	  borrar_combo2(document.frmFiltro.filgrupo); 
   
     if(document.frmFiltro.filinst.value > 0){
   	   document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	   document.frmAjaxNuevo.ajax[0].value =  document.frmFiltro.filinst.value;
	   document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_SED.value;
	   document.frmAjaxNuevo.submit();
	  }
	}
	
	function ajaxJornada(){ 
	  borrar_combo2(document.frmFiltro.filjornd);
	  borrar_combo2(document.frmFiltro.filmetod);
  	  borrar_combo2(document.frmFiltro.filgrado);
 	  borrar_combo2(document.frmFiltro.filgrupo);	  
    if(document.frmFiltro.filsede.value > 0){
   	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value =  document.frmFiltro.filinst.value;
	  document.frmAjaxNuevo.ajax[1].value =  document.frmFiltro.filsede.value;
	  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_JORD.value;
	  document.frmAjaxNuevo.submit();
	  }
	}
		
	function ajaxMetodologia(){ 
	  borrar_combo2(document.frmFiltro.filmetod);
  	  borrar_combo2(document.frmFiltro.filgrado);
 	  borrar_combo2(document.frmFiltro.filgrupo); 
 	  if(document.frmFiltro.filjornd.value > 0  ){
	   document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	   document.frmAjaxNuevo.ajax[0].value =  document.frmFiltro.filinst.value;
	   document.frmAjaxNuevo.ajax[1].value = document.frmFiltro.filsede.value;
	   document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_METD.value;
	   document.frmAjaxNuevo.submit();
	  }
	}
		 
	function ajaxGrado(){
 	  borrar_combo2(document.frmFiltro.filgrado);
 	  borrar_combo2(document.frmFiltro.filgrupo);
 	  if(document.frmFiltro.filmetod.value > 0){
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value =  document.frmFiltro.filinst.value;
	  document.frmAjaxNuevo.ajax[1].value = document.frmFiltro.filsede.value;
      document.frmAjaxNuevo.ajax[2].value = document.frmFiltro.filjornd.value;
      document.frmAjaxNuevo.ajax[3].value = document.frmFiltro.filmetod.value;
      document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_GRAD.value;
      document.frmAjaxNuevo.submit();
     }
	}
	
	
   function ajaxGrupo(){
	  borrar_combo2(document.frmFiltro.filgrupo);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
      document.frmAjaxNuevo.ajax[0].value =  document.frmFiltro.filinst.value;
      document.frmAjaxNuevo.ajax[1].value = document.frmFiltro.filsede.value;
      document.frmAjaxNuevo.ajax[2].value = document.frmFiltro.filjornd.value;
      document.frmAjaxNuevo.ajax[3].value = document.frmFiltro.filmetod.value;
      document.frmAjaxNuevo.ajax[4].value = document.frmFiltro.filgrado.value;
      document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_GRUP.value;
	  document.frmAjaxNuevo.submit();
   }
   
   
    function ajaxEstd(){
	  borrar_comboTodos(document.frmFiltro.filest);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
      document.frmAjaxNuevo.ajax[0].value =  document.frmFiltro.filinst.value;
      document.frmAjaxNuevo.ajax[1].value = document.frmFiltro.filsede.value;
      document.frmAjaxNuevo.ajax[2].value = document.frmFiltro.filjornd.value;
      document.frmAjaxNuevo.ajax[3].value = document.frmFiltro.filmetod.value;
      document.frmAjaxNuevo.ajax[4].value = document.frmFiltro.filgrado.value;
      document.frmAjaxNuevo.ajax[5].value = document.frmFiltro.filgrupo.value;
      document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_EST .value;
	  document.frmAjaxNuevo.submit();
   }
   
	function hacerValidaciones_frmFiltro(forma){
	validarLista(forma.filperd ,  '- Periodo', 1);
	  if(forma.filtipodoc.selectedIndex > 0 || forma.filnumdoc.value != '' ){
	    validarLista(forma.filtipodoc ,  '- Tipo de documento', 1);
	    validarCampo(forma.filnumdoc ,  '- Número de documento', 1);
	  }else{
	   
	   validarLista(forma.filsede ,  '- Sede', 1);
	   validarLista(forma.filjornd , '- Jornada', 1);
	   validarLista(forma.filmetod ,'- Metodologia', 1);
	   validarLista(forma.filgrado , '- Grado', 1);
	   validarLista(forma.filgrupo , '- Grupo', 1);;
	  } 
	} 
	
  function generar(){
	   if(validarForma(document.frmFiltro)){
	 	 document.frmFiltro.cmd.value = document.frmFiltro.CMD_GUARDAR.value;
	     document.frmFiltro.filsedeNomb.value = document.frmFiltro.filsede.options[document.frmFiltro.filsede.options.selectedIndex].text;
	     document.frmFiltro.filjorndNomb.value = document.frmFiltro.filjornd.options[document.frmFiltro.filjornd.options.selectedIndex].text;
	     document.frmFiltro.filgradoNomb.value = document.frmFiltro.filgrado.options[document.frmFiltro.filgrado.options.selectedIndex].text;
	     document.frmFiltro.filgrupoNomb.value = document.frmFiltro.filgrupo.options[document.frmFiltro.filgrupo.options.selectedIndex].text; 
	     document.frmFiltro.filperdNomb.value = document.frmFiltro.filperd.options[document.frmFiltro.filperd.options.selectedIndex].text; 
	     document.frmFiltro.target ="ModuloReportes";
	     window.open("","ModuloReportes","width=800,height=500,menubar=yes,scrollbars=1").focus();
	     document.frmFiltro.submit();
	 }
   }
  
   function lanzar_(tipo){
		document.frmFiltro.tipo.value=tipo;
		document.frmFiltro.cmd.value='6';
		document.frmFiltro.action="<c:url value="/gestionAcademica/RepEstadistico/Nuevo.do"/>";
	    document.frmFiltro.target="";
		document.frmFiltro.submit();
	}	
	
	
	 function limpiarCombos(){
	    document.frmFiltro.filsede.selectedIndex = 0;
	    //borrar_combo2(document.frmFiltro.filsede);
	    borrar_combo2(document.frmFiltro.filjornd);
	    borrar_combo2(document.frmFiltro.filmetod);
	    borrar_combo2(document.frmFiltro.filgrado);
	    borrar_combo2(document.frmFiltro.filgrupo);
	  //   ajaxSede();
	  }
 
 
 	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno","-9");
	}
	   function right(e){    
		    if (navigator.appName == 'Netscape' && (e.which == 3 || e.which == 2 || e.which == 17)){
		                    limpiarCombos();
		      return false;
		    }else if (navigator.appName == 'Microsoft Internet Explorer' && (event.button == 2 || event.button == 3 || event.button == 17 )) {
		           limpiarCombos();
		        return false;
		    }
		  return true;
		}

		
		function checkKey(evt) {
		  if (evt.ctrlKey){
		   limpiarCombos();
		   return false;
		  }
		} 
		 
 
	 //--> 
	 
	 
	 
	 function limpiarCampos(){
	    document.frmFiltro.filtipodoc.selectedIndex = 0;
	    document.frmFiltro.filnumdoc.value = '';
	  }
 
</script>
<style type="text/css">
TD.norepeat{
background-image:url(../../etc/img/tabs/pestana_1.gif); 
background-repeat:no-repeat;
}

TD.norepeat2{
background-image:url(../../etc/img/tabs/pestana_0.gif); 
background-repeat:no-repeat;
cursor:pointer;
}

.sombra {
color:white;
text-shadow:#666666 3px 3px 3px;
filter: progid:DXImageTransform.Microsoft.Shadow(color='#666666', Direction=135, Strength=4);
font-size: 85%;
font-stretch: ultra-expanded;
line-height: 95%;
font-weight: bolder;
}
.sombran {
text-shadow:#666666 3px 3px 3px;
filter: progid:DXImageTransform.Microsoft.Shadow(color='#666666', Direction=135, Strength=4);
font-size: 85%;
font-stretch: ultra-expanded;
line-height: 95%;
font-weight: bolder;
}
</style>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
 	   <form method="post" name="frmFiltro2" >
	</form>
	
 	   <form method="post" name="frmAjaxNuevo" action='<c:url value="/gestionAcademica/RepEstadistico/Ajax.do"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${ paramsVO.FICHA_REP_ASIGNATURAS}"/>'>
		<input type="hidden" name="cmd"  value='-1'>
		<input type="hidden" name="CMD_AJAX_SED"   id="CMD_AJAX_SED"   value='<c:out value="${paramsVO.CMD_AJAX_SED  }"/>'>
		<input type="hidden" name="CMD_AJAX_JORD"  id="CMD_AJAX_JORD"  value='<c:out value="${paramsVO.CMD_AJAX_JORD}"/>'>
		<input type="hidden" name="CMD_AJAX_METD"  id="CMD_AJAX_METD"  value='<c:out value="${paramsVO.CMD_AJAX_METD}"/>'>
		<input type="hidden" name="CMD_AJAX_GRUP"  id="CMD_AJAX_GRUP"  value='<c:out value="${paramsVO.CMD_AJAX_GRUP}"/>'>
		<input type="hidden" name="CMD_AJAX_GRAD"  id="CMD_AJAX_GRAD"  value='<c:out value="${paramsVO.CMD_AJAX_GRAD}"/>'>
		<input type="hidden" name="CMD_AJAX_EST"   id="CMD_AJAX_EST"  value='<c:out value="${paramsVO.CMD_AJAX_EST}"/>'>		
		<input type="hidden" name="CMD_BUSCAR"     id="CMD_BUSCAR"     value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		
		
		<c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach>
	  </form>
	
		 <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		  <caption>Gestión Académica >> Reportes Estadísticos </caption>
	        <tr height="1">
						 <td width="10" bgcolor="#FFFFFF">&nbsp;</td>							
			       <td width="14%" height="26"><a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_REP_ASISTENCIA }"/>')"> <img src='<c:url value="/etc/img/tabs/asistencia_entrega_boletines_0.gif"/>' border="0"  height="26" alt='Asistencia'></a></td>
			       <td width="14%" height="26"><img src='<c:url value="/etc/img/tabs/evaluacion_asig_grupo_1.gif"/>' width="84" height="26" border="0"></td>
			       <td width="14%" height="26"><a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_REP_AREAS }"/>')"><img src='<c:url value="/etc/img/tabs/evaluacion_area_0.gif"/>' width="84"  height="26" border="0"></a></td>
			       <c:forEach begin="0" items="${sessionScope.logroanddesc}" var="logdes">
				<td width="14%" height="26" class="norepeat2" onclick="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_REP_LOGROS }"/>')">
					<p align="center" class="sombran">Evaluación de <c:out value="${logdes[0]}"/></p>
				</td>
				<td width="14%" height="26" class="norepeat2" onclick="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_REP_DESCRIPTOR }"/>')">
				<p align="center" class="sombran">Evaluación Tipo <c:out value="${logdes[1]}"/></p>
				</td>
			</c:forEach>
			       <td width="14%" height="26"><a href="javaScript:lanzar_('<c:out value="${paramsVO.FICHA_REP_LOGROS_GRADO }"/>'  )"><img src='<c:url value="/etc/img/tabs/Evaluacion_LogroGrado_0.gif"/>' width="84" height="26" border="0"></a></td>
			       <td width="14%" height="26"></td>
            </tr>
          </table>
 
 
   <!-- FORMUALARIO DE EDICION CREACION--> 		  
	<form method="post" name="frmFiltro"  onKeyDown="checkKey(event)"   action='<c:url value="/siges/gestionAcademica/repEstadisticos/Save.jsp"/>'>
        <input type="hidden" name="tipo"    value='<c:out value="${paramsVO.FICHA_REP_ASIGNATURAS}"/>'>
        <input type="hidden" name="cmd"     value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="GENERAR" value='<c:out value="${paramsVO.CMD_GENERAR}"/>'>
		<input type="hidden" name="NUEVO"   value='<c:out value="${paramsVO.CMD_NUEVO}"/>'> 
        <input type="hidden" name="CMD_GUARDAR"      id="CMD_GUARDAR"  value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
        <input type="hidden" name="CMD_BUSCAR"      id="CMD_BUSCAR"  value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
        <input type="hidden" name="filinst"      id="filinst"  value='<c:out value="${login.instId}"/>'>
        <input type="hidden" name="filsedeNomb"   id="filsedeNomb"  >
        <input type="hidden" name="filjorndNomb"  id="filjorndNomb" value='<c:out value="${sessioScope.plantillaBoletionPubVO.filjorndNomb }"/>' >
        <input type="hidden" name="filmetodoNomb" id="filmetodoNomb"  >
        <input type="hidden" name="filgradoNomb"  id="filgradoNomb"  >
        <input type="hidden" name="filgrupoNomb"  id="filgrupoNomb"  >
        <input type="hidden" name="filperdNomb" id="filperdNomb"  >
        <input type="hidden" name="filperdNum"  id="filperidoNum"  >
       
        <table border="0" align="center" width="100%" cellpadding="2" cellspacing="2">
		  <caption align="center">REPORTE EVALUACIÓN DE ASIGNATURA</caption>
		  <tr><td colspan="4"><input type="button" value="Generar" class="boton"  onclick="JavaScript:generar();"></td></tr>
		</table> 
		 
		<table border="0" align="center" width="100%" cellpadding="2" cellspacing="2">
		<tr><td colspan="4" class="Fila0" align="center">Filtro Generar reporte</td></tr>
		  <tr><td><span class="style2" >*</span> Periodo</td>
		      <td><select   name="filperd" id="filperd" >
		           <option value="-99">-- Seleccione uno--</option>
		 	         <c:forEach begin="0" items="${sessionScope.listaPeriodos}" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.filtroRepEstadisticoVO.filsede ==vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		          </select>
		      </td>
		      <td width="50%">&nbsp;</td> 
		  </tr>
		</table> 	
     	  <table border="0" align="center" width="100%" cellpadding="2" cellspacing="2">
			<tr><td colspan="4" class="Fila0" align="center">Generación por ubicación</td></tr>
		 	 <tr><td style="width: 15%;display:none;"><span class="style2" >*</span>Sede</td>
		 	    <td style="width: 35%;display:none;">
		 	     <select  name="filsede" id="filsede" style="width: 150px;display:none;" onchange="JavaScript:borrar_combo2(document.frmFiltro.filmetod); limpiarCampos(); ajaxJornada();">
		 	        <option value="-99">-- Seleccione uno--</option>
		 	         <c:forEach begin="0" items="${sessionScope.listaSede}" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.filtroRepEstadisticoVO.filsede ==vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	      </select>
		 	    </td>
		 	    <td style="width: 15%;display:none;"><span class="style2" >*</span>Jornada</td>
		 	    <td style="width: 35%;display:none;"><select name="filjornd" id="filjornd" style="width: 15opx;display:none;" onchange="limpiarCampos();ajaxMetodologia();"  >
		 	        <option value="-99">-- Seleccione uno--</option>
		 	          <c:forEach begin="0" items="${ sessionScope.listaJornada }" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.filtroRepEstadisticoVO.filjornd ==vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	 </tr>   
		 	 <tr><td><span class="style2" >*</span>Metodología</td>
		 	    <td><select name="filmetod" id="filmetod" onchange="limpiarCampos();ajaxGrado();">
		 	        <option value="-99">-- Seleccione uno--</option>
		 	            <c:forEach begin="0" items="${sessionScope.listaMetodo }" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.filtroRepEstadisticoVO.filmetod  ==vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	    <td><span class="style2" >*</span>Grado</td>
		 	    <td><select name="filgrado" id="filgrado" onchange="limpiarCampos();ajaxGrupo();">
		 	        <option value="-99">-- Seleccione uno--</option>
		 	              <c:forEach begin="0" items="${sessionScope.listaGrado }" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.filtroRepEstadisticoVO.filgrado == vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	 </tr>   
		 	  <tr><td><span class="style2" >*</span>Grupo</td>
		 	    <td><select  name="filgrupo" id="filgrupo" onchange="limpiarCampos();ajaxEstd();" >
		 	        <option value="-99">-- Seleccione uno--</option>
		 	         <c:forEach begin="0" items="${sessionScope.listaGrupo }" var="vig">
				      <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.filtroRepEstadisticoVO.filgrupo == vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	    </tr>
		 	    <tr>
		 	    <td>Estudiante</td>
		 	    <td colspan="3" >
		 	       <select  style="width: 250px;" name="filest" id="filest" onchange="limpiarCampos();" >
		 	        <option value="-99">-- Todos --</option>
		 	         <c:forEach begin="0" items="${sessionScope.listaGrupo }" var="vig">
				      <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.filtroRepEstadisticoVO.filest == vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	    
		 	 </tr>  
		    <tr style="display:none"><td><iframe name="frameAjaxNuevo" id="frameAjaxNuevo"></iframe></td></tr>
		</table>	
		
		<table border="0" align="center" width="100%" cellpadding="2" cellspacing="2">
		 <tr><td colspan="4" class="Fila0" align="center">Generación por estudiante</td></tr>
		 <tr><td colspan="4">
              <span class="style2"><b>Nota:<br>Si necesita generar el reporte de un solo estudiante, por favor solo seleccione el periodo y diligencie los siguientes campos:</span>
		    </td> 
		 </tr>
		  <tr><td><span class="style2" >*</span> Tipo de Documento</td>
		      <td><select name="filtipodoc" id="filtipodoc"  onchange="JavaScript:limpiarCombos();">
		           <option value="-99">-- Seleccione uno--</option>
		 	         <c:forEach begin="0" items="${sessionScope.listaTiposDoc}" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.filtroRepEstadisticoVO.filtipodoc ==vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		          </select>
		      </td>
		      <td><span class="style2" >*</span> Número de documento</td>
		      <td><input type="text"  name="filnumdoc" id="filnumdoc"   onmouseover="right(event)"  onmousedown="right(event)"    onclick="right(event);"  onkeydown="JavaScript:limpiarCombos();"   onkeypress="JavaScript:limpiarCombos();"   maxlength="25" size="25" value='<c:out value="${sessionScope.filtroRepEstadisticoVO.filnumdoc }"/>'   >
		      </td>
		  </tr>
		</table> 	
	</form>
	
 
</body>
<script  type="text/javascript"> 
	limpiarCombos();
 ajaxSede();
 
</script>
</html>