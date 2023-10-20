<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroRegNotasVO" class="siges.gestionAcademica.regNotasVigencia.vo.FiltroRegNotasVO" scope="session"/><jsp:setProperty name="filtroRegNotasVO" property="*"/>
<jsp:useBean id="paramsVO" class="siges.gestionAcademica.regNotasVigencia.vo.ParamsVO" scope="page"/>
<html>
<head>
<script languaje="javaScript">
	
    var imagen1;
    //imagen1=new Image
   // imagen1.src='<c:url value="/etc/img/spinner.gif"/>';
    var ScrollSpeed = 100;  // milliseconds between scrolls
	var ScrollChars = 1;    // chars scrolled per time period
	var stop = 1;
	var flag = 0;
	var flagSpinner = 0;
	var msg2 = "";
	var msg = "";
	var decimas = 0  
	var segundos = 0  
	var minutos = 0  
	var ValorCrono = ""  ;
	CronoEjecutandose = true;  
	CronoID= null;
    function init(){stop = 1;flagSpinner = 0;}
	function Run(){ stop = 0;   flagSpinner=0;    SetupTicker();}
	function end(){stop = 1; DetenerCrono(); flagSpinner=0;} 
	function DetenerCrono (){if(CronoEjecutandose)
	                              clearTimeout(CronoID);
	                              CronoEjecutandose = false;
	                             } 
	                             
	                             
	                             
	function RunTicker() {
	 
	  if(stop == 0){ 
	 
	   CronoID = window.setTimeout('RunTicker()',ScrollSpeed);
	  
	   document.getElementById("txtmsg").innerHTML = "BUSCANDO";
	   
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
		 ValorCrono += ":" + decimas   
		 CronoEjecutandose = true;  
		 return true  
	 }
	}

	 
   
		
		
		    
    function SetupTicker() { 
      flag = 0;  
      msg = "... .";
      RunTicker();
    }
		
	
	
	
	
	
	
	
	
	
	
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
		
	function hacerValidaciones_frmNuevo(forma){
	  if(forma.filNumDoc.value == ''){
	  forma.filNumDoc.value = ' ';
	  }
	  
	  if(forma.filApell1.value == ''){
	  forma.filApell1.value = ' ';
	  }
	  
	  if(forma.filApell2.value == ''){
	  forma.filApell2.value = ' ';
	  }
	  
	  if(forma.filNom1.value == ''){
	  forma.filNom1.value = ' ';
	  }
	  
	  if(forma.filNom2.value == ''){
	  forma.filNom2.value = ' ';
	  }
	/*    validarLista(forma.filTipoDoc, "- Tipo de Documento", 1)
		validarLista(forma.areMetodologia, "- filNumDoc", 1)
		validarLista(forma.areCodigo, "- Área", 1)
		validarCampo(forma.areNombre, "- Nombre", 1, 60)
		validarCampo(forma.areAbreviatura, "- Abreviatura", 1, 10)
		validarEntero(forma.areOrden, "- Orden", 1, 999)
		validarSeleccion(forma.areGrado_, "- Grado (al menos debe seleccionar uno)")*/
	}
		
	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
		document.frmNuevo.action='<c:url value="/planDeEstudios/Nuevo.do"/>';
		document.frmNuevo.submit();
	}

	function buscar(){
		if(validarForma(document.frmNuevo)){
			validarDatos(document.frmNuevo);
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.tipo.value = '<c:out value="${paramsVO.FICHA_FILTRO_REG_NOTAS}" />';
			document.frmNuevo.numPag.value = Number(1);
			Run();
			document.frmNuevo.submit();
		}
	}
	
	function validarDatos(forma){
		if(forma.areGrado){
			if(forma.areGrado.length){
				for(i=0;i<forma.areGrado.length;i++){
					if(forma.areGrado_[i].checked==false){
						forma.areGrado[i].value='-1';
					}
				}
			}
		}
	}
	
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-Seleccione una-","-99");
	}
		
	function ajaxGrado(){
		document.frmAjax.ajax[0].value=document.frmNuevo.areInstitucion.value;
		document.frmAjax.ajax[1].value=document.frmNuevo.areMetodologia.value;
		document.frmAjax.ajax[2].value=document.frmNuevo.areVigencia.value;
		if(parseInt(document.frmAjax.ajax[1].value)!=-99){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_GRADO.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}	
	}
	
	function ajaxAreaBase(){
		document.frmAjax.ajax[0].value=document.frmNuevo.areCodigo.value;
		if(parseInt(document.frmAjax.ajax[0].value)!=-99){
			document.frmAjax.cmd.value=document.frmNuevo.AJAX_AREA_BASE.value;
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}
	}
 
 
 
	//Ocultar/Mostrar Div's
	OCULTO="none";
	VISIBLE="block";
	  function mostrar1(blo) {
	  document.getElementById('busquedaAvanzada' ).value = 1;
	    document.getElementById(blo).style.display=VISIBLE;
	    document.getElementById('ver_off1').style.display=VISIBLE;
	    document.getElementById('ver_on1').style.display=OCULTO;
	    }
	  function ocultar1(blo) {
	  document.getElementById('busquedaAvanzada' ).value = 0;
	    document.getElementById(blo).style.display=OCULTO;
	    document.getElementById('ver_off1').style.display=OCULTO;
	    document.getElementById('ver_on1').style.display=VISIBLE;
	    }
	  
	   function siguiente( ) {
	     document.frmNuevo.numPag.value = Number(document.frmNuevo.numPag.value) +Number(1) ;
	     document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
	     Run();
	     document.frmNuevo.submit();
	    }
	    
	    function primero( ) {
	     document.frmNuevo.numPag.value = Number(1) ;
	     document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
	     Run();
	     document.frmNuevo.submit();
	    }
	    
	    function atras( ) {
	     document.frmNuevo.numPag.value = Number(document.frmNuevo.numPag.value) - Number(1) ;
	     if(document.frmNuevo.numPag.value < 1)
	     document.frmNuevo.numPag.value = 1;
	     document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
	    Run();
	     document.frmNuevo.submit();
	    }
	    
	    function ultimo( ) {
	     document.frmNuevo.numPag.value =  Number('<c:out value="${sessionScope.filtroRegNotasVO.totalPag}"/>' ) ;
	     if(document.frmNuevo.numPag.value == 0)
	     document.frmNuevo.numPag.value = 1;
	     document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
	    Run();
	     document.frmNuevo.submit();
	    }
	    
	    function cambioBusqueda() {
	      document.frmNuevo.numPag.value = Number(1); 
	    }
	    
	    
	    
	    function editarEst(codEst) {
	         document.frmNuevo.codigoEst.value = codEst;
	        document.frmNuevo.tipo.value=document.frmNuevo.FICHA_NUEVO_REG_NOTAS.value;
	      	document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
			document.frmNuevo.submit(); 
	    }
	    
	  
	</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
 		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					 <img src='<c:url value="/etc/img/tabs/acad_regNotasVig_1.gif"/>' alt="Registro de Notas Vigencias Anteriores" height="26" border="0">
				</td>
			</tr>
		</table>
	<form method="post" name="frmAjax" action='<c:url value="/planDeEstudios/Ajax.do"/>'><INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_FILTRO_REG_NOTAS}"/>'><input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'><c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach></form>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/siges/gestionAcademica/regNotasVigencia/Save.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_FILTRO_REG_NOTAS}"/>'>
		<input type="hidden" name="FICHA_NUEVO_REG_NOTAS" value='<c:out value="${paramsVO.FICHA_NUEVO_REG_NOTAS}"/>'>		
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="busquedaAvanzada" id="busquedaAvanzada" value='<c:out value="${sessionScope.filtroRegNotasVO.busquedaAvanzada}"/>'>
		<input type="hidden" name="numPag" id="numPag" value='<c:out value="${sessionScope.filtroRegNotasVO.numPag }"/>'>
		<input type="hidden" name="codigoEst" id="codigoEst" value='0'>
		
	 	<!-- <input type="hidden" name="areVigencia" value='<c:out value="${sessionScope.areaPlanVO.areVigencia}"/>'> -->
	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
		  <caption>REGISTRO DE NOTAS</caption>
				<tr>
					<td colspan="1">
						<input name="cmd1" type="button" value="Buscar" onClick="buscar()" class="boton">
					</td>
					<td colspan="3" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span align = "center"  id="txtmsg"   name="txtmsg" style="font-weight:bold;font-size:10pt;color: #FF6666"> </span><span align = "center"  id="barraCargar" name="barraCargar" style="font-weight:bold;font-size:9pt;color: #FF6666"> </span>
			        </td>
			 	</tr>	
			<tr style="display:none"><td><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>	
			<tr><td colspan="4" align="center">&nbsp;</td>
			</tr>
		
			<tr><td colspan="4" align="center"><span class="style2">BÚSQUEDA POR DOCUMENTO</span> </td>
			</tr>
			<tr>
			<td width="18%">Tipo de Documento</td>
			<td width="35%">
				<select name="filTipoDoc" style="width:120px;" onchange="cambioBusqueda();" >
					<option value="-99" selected>--Seleccione uno--</option>
					<c:forEach begin="0" items="${requestScope.filtroTiposDoc}" var="metod">
						<option value="<c:out value="${metod.codigo}"/>" <c:if test="${metod.codigo==sessionScope.filtroRegNotasVO.filTipoDoc}">selected</c:if>><c:out value="${metod.nombre}"/></option>
					</c:forEach>
				</select>
			</td>
			<td width="18%">Número de documento</td>
			<td width="35%"><input type="text" name="filNumDoc" value='<c:out value="${sessionScope.filtroRegNotasVO.filNumDoc }"/>' maxlength="25"  onkeydown="cambioBusqueda();" onkeyup="cambioBusqueda();" >
			</td>
			</tr>
			<tr><td colspan="4" align="center">&nbsp;</td>
			</tr>
			</table>
			<tr><td colspan="4">
			
			  <div id="ver_on1"><h1 id="titu" style="margin: 0px; padding: 0px 0px 8px 0px;"><a href="#" onclick="mostrar1('bloque1')" title="Expandir info" style="TEXT-HEIGHT: bolder;FONT-FAMILY: Trebuchet MS,Arial, Helvetica, sans-serif;FONT-SIZE: 12px; margin: 0px; padding: 0px 0px 8px 0px;">&nbsp;&nbsp;Búsqueda Avanzada</a></h1>
           	            <p style="TEXT-HEIGHT: bolder;FONT-FAMILY: Trebuchet MS,Arial, Helvetica, sans-serif;COLOR: #003366;FONT-SIZE: 9px; margin: 0px; padding: 0px 0px 8px 0px;">&nbsp;&nbsp;Haga click en el vinculo para mostrar filtro de búsqueda avanzada</p>
           	            						
						</div>
						<div id="ver_off1" style="display: none"><h1 style="margin: 0px; padding: 0px 0px 8px 0px;"><a href="#" onclick="ocultar1('bloque1')" title="Cerrar info" style="TEXT-HEIGHT: bolder;FONT-FAMILY: Trebuchet MS,Arial, Helvetica, sans-serif;FONT-SIZE: 10px;margin: 0px; padding: 0px 0px 8px 0px;">&nbsp;&nbsp;Ocultar búsqueda Avanzada</a></h1>
						</div>	
						
					 </td>
			</tr>	
			<table id="bloque1" style="display:none"> 
			<tr><td colspan="4" align="center"><span class="style2">BÚSQUEDA POR NOMBRE</span> </td>
			</tr>
			<tr>
		
			<td width="18%">Primer Apellido</td>
			<td width="35%"><input type="text" size="15" value='<c:out value="${sessionScope.filtroRegNotasVO.filApell1 }"/>' name="filApell1"   onkeydown="cambioBusqueda();" onkeyup="cambioBusqueda();" ></td>
		 	<td width="18%">Segundo Apellido</td>
			<td width="35%"><input type="text" size="15" value='<c:out value="${sessionScope.filtroRegNotasVO.filApell2 }"/>' name="filApell2"    onkeydown="cambioBusqueda();" onkeyup="cambioBusqueda();" ></td>
			</tr>	
			<tr>
			<td width="18%" >Primer Nombre</td>
			<td width="35%" ><input type="text" size="15" value='<c:out value="${sessionScope.filtroRegNotasVO.filNom1 }"/>' name="filNom1"    onkeydown="cambioBusqueda();" onkeyup="cambioBusqueda();" ></td>
		 	<td width="18%">Segundo Nombre</td>
			<td width="35%"><input type="text" size="15"  value='<c:out value="${sessionScope.filtroRegNotasVO.filNom2 }"/>' name="filNom2"    onkeydown="cambioBusqueda();" onkeyup="cambioBusqueda();"  ></td>
			</tr> 
		 
			</table>
		 <table border="0" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray" class="table01" style="text-align: left;" >
		 <caption>Listado de Estudiantes</caption>
		   <tr><td colspan="4">&nbsp;</td></tr>
		  
		   <tr><td width="18%">Cantidad de registros por página</td>
		       <td  width="35%"><select name="cantPags"  onchange="cambioBusqueda();">
		            <option value="5" '<c:if test="${sessionScope.filtroRegNotasVO.cantPags == 1 }">' selected '</c:if>'>5</option>
		            <option value="10" '<c:if test="${sessionScope.filtroRegNotasVO.cantPags == 10 }">' selected '</c:if>'>10</option>
		            <option value="20" '<c:if test="${sessionScope.filtroRegNotasVO.cantPags == 20 }">' selected '</c:if>'>20</option>
		            <option value="30" '<c:if test="${sessionScope.filtroRegNotasVO.cantPags == 30 }">' selected '</c:if>'>30</option>
		          </select>
		       </td>
		      
		   </tr>
		  
		   <tr><td colspan="4">&nbsp;</td></tr>
		   </table>
		  
		  <table width="100%" align="center">
		   	<tr>
		   	 <td colspan="6" align="center" valign="middle" >
		       
		       <c:if test="${sessionScope.filtroRegNotasVO.numPag  > 1}"> 
		       <a href="#" onclick="primero()" title="Atras" style="TEXT-HEIGHT: bolder;FONT-FAMILY: Trebuchet MS,Arial, Helvetica, sans-serif;FONT-SIZE: 16px; margin: 0px; padding: 0px 0px 8px 0px;">&nbsp;&nbsp;<< &nbsp;&nbsp;  </a>
		       <a href="#" onclick="atras()" title="Atras" style="TEXT-HEIGHT: bolder;FONT-FAMILY: Trebuchet MS,Arial, Helvetica, sans-serif;FONT-SIZE: 16px; margin: 0px; padding: 0px 0px 8px 0px;">&nbsp;&nbsp;<&nbsp;&nbsp;  </a>
		       </c:if> 
		       
		       <c:if test="${   sessionScope.filtroRegNotasVO.totalPag >0 }"> 
		        Página <c:out value="${sessionScope.filtroRegNotasVO.numPag }"/> de <c:out value="${sessionScope.filtroRegNotasVO.totalPag }"/> 
		       </c:if>
		       <c:if test="${   sessionScope.filtroRegNotasVO.totalPag > sessionScope.filtroRegNotasVO.numPag }"> 
		       <a href="#" onclick="siguiente()" title="Siguiente" style="TEXT-HEIGHT: bolder;FONT-FAMILY: Trebuchet MS,Arial, Helvetica, sans-serif;FONT-SIZE: 16px; margin: 0px; padding: 0px 0px 8px 0px;">&nbsp;&nbsp;></a>
		       <a href="#" onclick="ultimo()" title="Ultimo" style="TEXT-HEIGHT: bolder;FONT-FAMILY: Trebuchet MS,Arial, Helvetica, sans-serif;FONT-SIZE: 16px; margin: 0px; padding: 0px 0px 8px 0px;">&nbsp;&nbsp;>>&nbsp;&nbsp;</a>
		       </c:if>
		    
		     </td>
		    </tr>
		  </table>
		 
		   <table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray" class="table01" style="text-align: left;" >
             <c:if test="${!empty requestScope.listaEst }">
		         <tr>	
					    <th class="EncabezadoColumna" colspan="1" align="center">N°</th>
					    <th class="EncabezadoColumna" colspan="1" align="center">&nbsp;</th>
					    <th class="EncabezadoColumna" colspan="1" align="center">Tipo Doc.</th>
						<th class="EncabezadoColumna" align="center">Número de documento</th>
						<th class="EncabezadoColumna" align="center">Apellidos</th>
						<th class="EncabezadoColumna" align="center">Nombres</th>
						
					</tr>
					 </c:if>
					<c:if test="${empty requestScope.listaEst }">
					 <tr><td colspan="6" align="center">No se encontrarón estudiantes con estos datos</td></tr>
					</c:if>
					<c:if test="${!empty requestScope.listaEst }">
					<c:forEach begin="0" items="${requestScope.listaEst  }" var="lista" varStatus="st">
						<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'><c:out value="${lista.numFila}"/></th>
						    <td class='Fila<c:out value="${st.count%2}"  />' > <a href='javaScript:editarEst(<c:out value="${  lista.estcodigo  }"/>);'><img border='0' src='<c:url value="/etc/img/editar.png" />' width='15' height='15'></a> </td>
						    <td class='Fila<c:out value="${st.count%2}"  />' > <c:forEach begin="0" items="${requestScope.filtroTiposDoc}" var="metod"><c:if test="${metod.codigo==lista.esttipodoc}"> <c:out value="${metod.nombre}"/></c:if></c:forEach></td>
							<td class='Fila<c:out value="${st.count%2}"  />'  >&nbsp;<c:out value="${lista.estnumdoc}"/></td> 
							<td class='Fila<c:out value="${st.count%2}"  />'  >&nbsp;<c:out value="${lista.estapellidos}"/></td> 
							<td class='Fila<c:out value="${st.count%2}"  />'  >&nbsp;<c:out value="${lista.estnombres}"/></td> 
					 	</tr>
					</c:forEach>
					</c:if>
		</table>
	</form>
</body>
<script type="text/javascript">
   <c:if test="${sessionScope.filtroRegNotasVO.busquedaAvanzada != 1}">
    ocultar1('bloque1');
  </c:if>
  
  <c:if test="${sessionScope.filtroRegNotasVO.busquedaAvanzada == 1 or !empty sessionScope.filtroRegNotasVO.filApell1  or !empty sessionScope.filtroRegNotasVO.filApell2  }">
    mostrar1('bloque1');
  </c:if>
 
</script>
</html>