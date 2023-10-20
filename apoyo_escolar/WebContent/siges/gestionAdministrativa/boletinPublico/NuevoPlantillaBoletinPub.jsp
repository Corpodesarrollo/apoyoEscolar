<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="plantillaBoletionPubVO" class="siges.gestionAdministrativa.boletinPublico.vo.PlantillaBoletionPubVO" scope="session"/>
<jsp:useBean  id="paramsVO" class="siges.gestionAdministrativa.boletinPublico.vo.ParamsVO" scope="page"/>
<html>
<head> 
<c:import url="/parametros.jsp"/>
  
<script languaje="text/javascript">
<!--

 	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno","-99");
	}
	
    var imagen1;
    imagen1=new Image
    imagen1.src='<c:url value="/etc/img/spinner.gif"/>';
    var ScrollSpeed = 100;  // milliseconds between scrolls
	var ScrollChars = 1;    // chars scrolled per time period
	var stop = 0;
	var flag = 0;
	var flagSpinner = 0;
	var msg2 = "";
	var decimas = 0; 
	var segundos = 0;  
	var minutos = 0; 
	var ValorCrono = ""  ;
    
    
   
			
			
			

	 function SetupTicker() { flag = 0; msg = "... .";RunTicker();}
	 function RunTicker() {
	  if(stop == 0){ 
	  
	   CronoID = window.setTimeout('RunTicker()',ScrollSpeed);
	   document.getElementById("txtmsg").innerHTML = "GENERANDO";
	   
	   document.getElementById("barraCargar").innerHTML = msg2;
	   
	   flag++;
	   if(flag == 6) flag=0;
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
		 ValorCrono += ":" + decimas;   
		 CronoEjecutandose = true;  
		 return true;  
	 }
	}
	function init(){stop = 1;flagSpinner = 0;}
	function Run(){   stop = 0;flagSpinner=0; SetupTicker();}
	function end(){stop = 1;DetenerCrono(); flagSpinner=0;} 
	function DetenerCrono (){if(CronoEjecutandose)clearTimeout(CronoID);CronoEjecutandose = false;} 
	 
    
    
    
    
	
	
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


	function download(name){
		document.frmConsultaExterna.target ="";
		document.frmConsultaExterna.action='<c:url value="/siges/gestionAdministrativa/padreFliaPublico/Nuevo.do"/>'
		document.frmConsultaExterna.submit();
		document.frmConsultaExterna.btnDescargar.style.display = 'none';
	}
	  
  
  
  
  function nuevo(){
	 document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
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
    //  document.getElementById('miTabla').style.display='none';		
 
	 if(  validarForma(document.frmNuevo2)  ) { 
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
      document.frmAjaxNuevo.ajax[1].value = document.frmNuevo.plaboltipodoc.value;
      document.frmAjaxNuevo.ajax[0].value = document.frmNuevo.plabolnumdoc.value;
      document.frmAjaxNuevo.ajax[2].value = document.frmNuevo.plabolcodigoest.value;
      document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_EST.value;
	  document.frmAjaxNuevo.action='<c:url value="/siges/gestionAdministrativa/padreFliaPublico/Ajax.do"/>'
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
	
	
      function generarBol(){
	   if(validarForma(document.frmNuevo)){
	     document.frmNuevo.plabolsedeNomb.value = document.frmNuevo.plabolsede.options[document.frmNuevo.plabolsede.options.selectedIndex].text;
	     document.frmNuevo.plaboljorndNomb.value = document.frmNuevo.plaboljornd.options[document.frmNuevo.plaboljornd.options.selectedIndex].text;
	     document.frmNuevo.plabolgradoNomb.value = document.frmNuevo.plabolgrado.options[document.frmNuevo.plabolgrado.options.selectedIndex].text;
	     document.frmNuevo.plabolgrupoNomb.value = document.frmNuevo.plabolgrupo.options[document.frmNuevo.plabolgrupo.options.selectedIndex].text;
	     document.frmNuevo.plabolperidoNomb.value = document.frmNuevo.plabolperido.options[document.frmNuevo.plabolperido.length -1].text;
	     document.frmNuevo.plabolperidoNum.value = document.frmNuevo.plabolperido.length - 2;

	     document.frmNuevo.cmd.value = document.frmNuevo.GUARDAR.value;
	     document.frmNuevo.target ="";
         document.frmNuevo.action='<c:url value="/siges/gestionAdministrativa/boletinPublico/Save.jsp"/>'
	     Run();   
	     
	     document.frmNuevo.submit();
	   }
      }
      
      function generarConsultaExterna(){
   	   
   	     document.frmConsultaExterna.cmd.value = document.frmNuevo.GUARDAR.value;
   	     document.frmConsultaExterna.target ="";
         document.frmConsultaExterna.action='<c:url value="/siges/gestionAdministrativa/boletinPublico/Save.jsp"/>'
   	     Run();
   	     document.frmConsultaExterna.submit();
	}

        
        function descargar(){
       //  alert("descargar");
	     document.frmNuevo.action = '<c:url value="/siges/gestionAdministrativa/padreFliaPublico/Nuevo.do"/>';
	     document.frmNuevo.cmd.value = document.frmNuevo.CMD_GENERAR.value;
	     document.frmNuevo.submit();
	  }
	  

	             
      function limpiar(){ 
      borrar_combo2(document.frmNuevo.plabolsede);
      borrar_combo2(document.frmNuevo.plaboljornd);
      borrar_combo2(document.frmNuevo.plabolmetodo);
      borrar_combo2(document.frmNuevo.plabolgrado);
      borrar_combo2(document.frmNuevo.plabolgrupo);
      borrar_combo2(document.frmNuevo.plabolperido);
      document.frmNuevo.plabolnomb.value = '';
      document.frmNuevo.plabolinstBNomb.value = '';
      
    //  document.getElementById('miTabla').style.display='none';				
	 
			
  }
   
	 -->
	 
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
 	   <form method="post" name="frmNuevo2" >
	</form>
	<form  name='listado' method='post' target='_blank'>
        <input type='hidden' name='aut' value='1'>
        <input type='hidden' name='dir'>
        <input type='hidden' name='tipo'>
    </form>
	
 	   <form method="post" name="frmAjaxNuevo" action='<c:url value="/siges/gestionAdministrativa/padreFliaPublico/Ajax.do"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${ paramsVO.FICHA_PLANTILLA_BOLETIN }"/>'>
		<input type="hidden" name="cmd"  value='-1'>
		<input type="hidden" name="CMD_AJAX_JORD"  id="CMD_AJAX_JORD"  value='<c:out value="${paramsVO.CMD_AJAX_JORD}"/>'>
		<input type="hidden" name="CMD_AJAX_METD"  id="CMD_AJAX_METD"  value='<c:out value="${paramsVO.CMD_AJAX_METD}"/>'>
		<input type="hidden" name="CMD_AJAX_GRUP"   id="CMD_AJAX_GRUP"   value='<c:out value="${paramsVO.CMD_AJAX_GRUP}"/>'>
		<input type="hidden" name="CMD_AJAX_GRAD"  id="CMD_AJAX_GRAD"  value='<c:out value="${paramsVO.CMD_AJAX_GRAD}"/>'>
		<input type="hidden" name="CMD_AJAX_EST"  id="CMD_AJAX_EST"  value='<c:out value="${paramsVO.CMD_AJAX_EST}"/>'>
		
		
		
		<c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach>
	  </form>
 
 
		
   <!-- FORMUALARIO DE EDICION CREACION--> 		  
 

       <form method="post" name="frmNuevo"  action='<c:url value="/siges/gestionAdministrativa/boletinPublico/Save.jsp"/>' >
        <input type="hidden" name="tipo"    value='<c:out value="${ paramsVO.FICHA_PLANTILLA_BOLETIN }"/>'>
        <input type="hidden" name="cmd"     value='3'>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="NUEVO"   value='<c:out value="${paramsVO.CMD_NUEVO}"/>'> 
		<input type="hidden" name="CMD_GENERAR"   value='<c:out value="${paramsVO.CMD_GENERAR}"/>'> 
        <input type="hidden" name="CMD_GUARDAR"      id="CMD_GUARDAR"  value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
        <input type="hidden" name="plabolsedeNomb"   id="plabolsedeNomb"  >
        <input type="hidden" name="plaboljorndNomb"  id="plaboljorndNomb" value='<c:out value="${sessioScope.plantillaBoletionPubVO.plaboljorndNomb }"/>' >
        <input type="hidden" name="plabolmetodoNomb" id="plabolmetodoNomb"  >
        <input type="hidden" name="plabolgradoNomb"  id="plabolgradoNomb"  >
        <input type="hidden" name="plabolgrupoNomb"  id="plabolgrupoNomb"  >
        <input type="hidden" name="plabolperidoNomb" id="plabolperidoNomb"  >
        <input type="hidden" name="plabolperidoNum"  id="plabolperidoNum"  >
        <input type="hidden" name="plabolinst" id="plabolinst" value='<c:out value="${sessionScope.plantillaBoletionPubVO.plabolinst  }"/>'  >
        
        <input type="hidden" name="plabolniveleval"  id="plabolniveleval"  >


		<table border="0" align="center" width="100%" cellpadding="2"
			cellspacing="2">
			<caption>CONSULTA DE BOLETINES</caption>

			<tr>
				<td><span class="style2">*</span>Tipo de documento</td>
				<td><select name="plaboltipodoc" id="plaboltipodoc"
					onchange="limpiar();">
						<option value="-99">-- Seleccione uno--</option>
						<c:forEach begin="0" items="${requestScope.filtroTiposDoc}"
							var="vig">
							<option value="<c:out value="${vig.codigo}"/>"
								<c:if test="${sessionScope.plantillaBoletionPubVO.plaboltipodoc ==vig.codigo}">SELECTED</c:if>>
								<c:out value="${vig.nombre}" />
							</option>
						</c:forEach>
				</select></td>
				<td><span class="style2">*</span>Número de documento</td>
				<td><input type="text" name="plabolnumdoc" id="plabolnumdoc"
					size="15" maxlength="15"
					value='<c:out value="${sessionScope.plantillaBoletionPubVO.plabolnumdoc  }"/>'
					onkeypress="limpiar();" onkeyup="limpiar();"></td>
			</tr>
			<tr>
				<td><span class="style2">*</span>Código del estudiante</td>
				<td><input type="text" name="plabolcodigoest"
					id="plabolcodigoest" size="15" maxlength="15"
					value='<c:out value="${sessionScope.plantillaBoletionPubVO.plabolcodigoest  }"/>'
					onkeypress="limpiar();" onkeyup="limpiar();"></td>
			</tr>
			<tr>
				<td colspan="4" align="right"><input type="button"
					value="Buscar" class="boton" onclick="ajaxEst();"></td>
			</tr>
		</table>

		<table border="0" align="center" width="100%" cellpadding="2" cellspacing="2">
			<caption>DATOS DEL ESTUDIANTE</caption>
		 		 
		 	<tr><td  >Nombre</td>
		 	     <td colspan="3" >
		 	       <input type="text" name="plabolnomb" id="plabolnomb" value='<c:out value="${sessionScope.plantillaBoletionPubVO.plabolnomb  }"/>'  readonly="readonly" style="width: 350px; "></td>
		    </tr>
		 	<tr><td  >Colegio</td>
		 	     <td colspan="3"><input type="text" name="plabolinstBNomb" id="plabolinstBNomb" value='<c:out value="${sessionScope.plantillaBoletionPubVO.plabolinstBNomb  }"/>'  readonly="readonly" style="width: 350px; "> </td>
		 	</tr>
		    
		    <tr><td style="width: 15%"><span class="style2" >*</span>Sede</td>
		 	    <td style="width: 35%"><select  name="plabolsede" id="plabolsede" style="width: 150px;" onchange="JavaScript: ajaxJornada();">
		 	        <option value="-99">-- Seleccione uno--</option>
		 	         <c:forEach begin="0" items="${sessionScope.listaSedes}" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.plantillaBoletionPubVO.plabolsede ==vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	      </select>
		 	    </td> 
		 	    

		 	    <td style="width: 15%"><span class="style2" >*</span>Jornada</td>
		 	    <td style="width: 35%"><select name="plaboljornd" id="plaboljornd" style="width: 15opx;" onchange="ajaxMetodologia();"  >
		 	        <option value="-99">-- Seleccione uno--</option>
		 	          <c:forEach begin="0" items="${ sessionScope.listaJornada }" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.plantillaBoletionPubVO.plaboljornd ==vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	 </tr>   
		 	 <tr><td><span class="style2" >*</span>Metodología</td>
		 	    <td><select name="plabolmetodo" id="plabolmetodo" onchange="ajaxGrado();">
		 	        <option value="-99">-- Seleccione uno--</option>
		 	            <c:forEach begin="0" items="${sessionScope.listaMetodo }" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.plantillaBoletionPubVO.plabolmetodo  ==vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	    <td><span class="style2" >*</span>Grado</td>
		 	    <td><select name="plabolgrado" id="plabolgrado" onchange="ajaxGrupo();">
		 	        <option value="-99">-- Seleccione uno--</option>
		 	              <c:forEach begin="0" items="${sessionScope.listaGrado }" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.plantillaBoletionPubVO.plabolgrado == vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	 </tr>   
		 	  <tr><td><span class="style2" >*</span>Grupo</td>
		 	    <td><select  name="plabolgrupo" id="plabolgrupo" >
		 	        <option value="-99">-- Seleccione uno--</option>
		 	         <c:forEach begin="0" items="${sessionScope.listaGrupo }" var="vig">
				      <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.plantillaBoletionPubVO.plabolgrupo == vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	    <td><span class="style2" >*</span>Periodo</td>
		 	    <td><select  name="plabolperido" id="plabolperido" > 
		 	          <option value="-99">-- Seleccione uno--</option>
		 	              <c:forEach begin="0" items="${sessionScope.filtroPeriodo }" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.plantillaBoletionPubVO.plabolperido == vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	 </tr>
		 	 <tr><td colspan="4"  align="right"><input type="button" value="Generar" class="boton"  onclick="generarBol();"></td></tr>  <tr>
		 	 <tr><td colspan="2" align="center"></td><td colspan="2" align="left"> <span align = "center"  id="txtmsg"   name="txtmsg" style="font-weight:bold;font-size:10pt;color: #FF6666"> </span><span align = "center"  id="barraCargar" name="barraCargar" style="font-weight:bold;font-size:9pt;color: #FF6666"> </span></td></tr>
		 	<tr style="display:none"><td><iframe name="frameAjaxNuevo" id="frameAjaxNuevo"></iframe></td></tr>
		</table>
	</form>
	<form method="post" name="frmConsultaExterna"  action='<c:url value="/siges/gestionAdministrativa/boletinPublico/Save.jsp"/>' >
        <input type="hidden" name="tipo"    value='<c:out value="${ paramsVO.FICHA_PLANTILLA_BOLETIN }"/>'>
        <input type="hidden" name="cmd"     value='<c:out value="${paramsVO.CMD_ENVIAR_CONSTANCIA}"/>'>
		
   	<table border="0" align="center" width="100%" cellpadding="2" cellspacing="2">
			<caption>Consultas Externas</caption>
		 	 <tr>
				<td>
					<span class="style2">*</span>Pin del Documento</td>
				<td>
					<input type="text" name="Pin_Documento"	id="Pin_Documento" size="15" maxlength="15">
				</td>
				<td colspan="4"  align="right">
					<input type="button" value="Consultar" class="boton"  onclick="generarConsultaExterna();">
				</td>
			</tr>
			<c:if test="${sessionScope.consultaExterna ne null}">
			<tr>
				<td colspan="2">
					<input name="btnDescargar" type="button" value="Descargar" onClick='download("<c:out value="${requestScope.rutaArchivoConsultasExternas}"/>")' class="boton">
				</td>
			</tr>
			</c:if>
		</table>     
   </form>
	
</body>
<script> 
		 	  <c:if test="${sessionScope.resultadoConsulta.generado==true}">
			          //alert("Boletín generado satisfactoriamente.")
			          descargar();
		       </c:if>
		       
		       
		   document.frmNuevo.plabolsede.disabled = true;
		   document.frmNuevo.plaboljornd.disabled = true;
		   document.frmNuevo.plabolmetodo.disabled = true;
		   document.frmNuevo.plabolgrado.disabled = true;
		   document.frmNuevo.plabolgrupo.disabled = true;
		   
		     
</script>
</html>