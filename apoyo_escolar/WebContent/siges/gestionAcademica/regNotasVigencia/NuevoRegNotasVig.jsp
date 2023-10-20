<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="estudianteRegNotasVO" class="siges.gestionAcademica.regNotasVigencia.vo.EstudianteRegNotasVO" scope="session"/>
<jsp:useBean id="filtroRegNotasVO" class="siges.gestionAcademica.regNotasVigencia.vo.FiltroRegNotasVO" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.gestionAcademica.regNotasVigencia.vo.ParamsVO" scope="page"/>

<html>
<head>
<script languaje="javaScript">
	var bandera = 0;
	var cantidadError = 0;
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;

		return  (key == 46  || key<=13 || (key>=48 && key<=57));
	}
		
	function hacerValidaciones_frm(forma){
	   validarLista(forma.filsede ,  '- Sede', 1);
	   validarLista(forma.filjornd , '- Jornada', 1);
	   validarLista(forma.filmetod ,'- Metodologia', 1);
	   validarLista(forma.filgrado , '- Grado', 1);
	   validarLista(forma.filgrupo , '- Grupo', 1);
	   validarLista(forma.filvigencia , '- Vigencia', 1);
	   validarLista(forma.filtipo , '- Tipo de promoción', 1); 
	   
  if(bandera == 0){
	cantidadError = 0;
	
   <c:if test="${sessionScope.tipoEval != paramsVO.ESCALA_CONCEPTUAL }">
	
	 <c:forEach begin="0" items="${sessionScope.listaArea }" var="areaVO">
      <c:forEach begin="0" items="${areaVO.listaEvalAreNota }"    varStatus="var" >
       var objects =  document.getElementById('notaEst_<c:out value="${areaVO.arecodJerar}" />_<c:out value="${var.count}" />');
       var b=false;
       if(trim(objects.value)==""){
    	   objects.value=forma.notaMin.value;
    	   b=true;
       }
       if( !( !isNaN(parseFloat(objects.value))  ||    !isNaN(parseInt(objects.value)) ) || parseFloat(objects.value) < parseFloat(forma.notaMin.value) || parseFloat(objects.value) > parseFloat(forma.notaMax.value)  ){
          cantidadError++;
          _campoError = objects;
        } else if(b){
        	objects.value="";
        	b=false
        }
       </c:forEach> 
        var objects =  document.getElementById('notaEst_<c:out value="${areaVO.arecodJerar}" />_<c:out value="${7}" />');
        if(trim(objects.value)==""){
     	   objects.value=forma.notaMin.value;
     	   b=true;
        }
        if(!  ( !isNaN(parseFloat(objects.value))  ||    !isNaN(parseInt(objects.value)) ) ||   parseFloat(objects.value) < parseFloat(forma.notaMin.value) || parseFloat(objects.value) > parseFloat(forma.notaMax.value)  ){
          cantidadError++;
          _campoError = objects;
        }else if(b){
        	objects.value="";
        	b=false
        }
          
         <c:forEach begin="0" items="${areaVO.listaAsig}" var="asigVO">
          <c:forEach begin="0" items="${asigVO.listaEvalAsiNota }"   varStatus="varAsig">
            var objects_ =  document.getElementById('notaEst_<c:out value="${asigVO.asicodjerar}" />_<c:out value="${varAsig.count}" />');
            if(trim(objects_.value)==""){
          	   objects_.value=forma.notaMin.value;
          	   b=true;
             }
            if(Number(objects_.value) < Number(forma.notaMin.value) || Number(objects_.value) > Number(forma.notaMax.value)  ){
             cantidadError++;
             _campoError = objects_;
            }else if(b){
            	objects_.value="";
            	b=false
            } 
           </c:forEach> 
            var objects_ =  document.getElementById('notaEst_<c:out value="${asigVO.asicodjerar}" />_<c:out value="${7}" />');
            if(trim(objects_.value)==""){
           	   objects_.value=forma.notaMin.value;
           	   b=true;
              }
            if( Number(objects_.value) < Number(forma.notaMin.value) || Number(objects_.value) > Number(forma.notaMax.value)  ){
             cantidadError++;
             _campoError = objects_;
            }else if(b){
            	objects_.value="";
            	b=false
            }
       </c:forEach> 
      </c:forEach> 
     
      if(cantidadError == 1){
          appendErrorMessage(cantidadError + " registro estan fuera del rango del valor mínimo y máximo de la nota")
      }
      if(cantidadError > 1){
          appendErrorMessage(cantidadError + " registros estan fuera del rango del valor mínimo y máximo de la nota")
      }
    </c:if>
    
    
    
    /*
    <c:if test="${sessionScope.tipoEval == paramsVO.ESCALA_CONCEPTUAL }">
	
	 <c:forEach begin="0" items="${sessionScope.listaArea }" var="areaVO">
      <c:forEach begin="0" items="${areaVO.listaEvalAreNota }"    varStatus="var" >
       var objects =  document.getElementById('notaEst_<c:out value="${areaVO.arecodJerar}" />_<c:out value="${var.count}" />');
        if( objects.value == '' ||  objects.selectedIndex < 1 ){
          cantidadError++;
          _campoError = objects;
        }
       </c:forEach> 
        var objects =  document.getElementById('notaEst_<c:out value="${areaVO.arecodJerar}" />_<c:out value="${7}" />');
        if( objects.value == '' ||  objects.selectedIndex < 1 ){
          cantidadError++;
          _campoError = objects;
        }
          
         <c:forEach begin="0" items="${areaVO.listaAsig}" var="asigVO">
          <c:forEach begin="0" items="${asigVO.listaEvalAsiNota }"   varStatus="varAsig">
            var objects_ =  document.getElementById('notaEst_<c:out value="${asigVO.asicodjerar}" />_<c:out value="${varAsig.count}" />');
            if( objects_.value == '' ||  objects_.selectedIndex < 1){
             cantidadError++;
             _campoError = objects_;
            } 
           </c:forEach> 
            var objects_ =  document.getElementById('notaEst_<c:out value="${asigVO.asicodjerar}" />_<c:out value="${7}" />');
            if( objects_.value == '' ||  objects_.selectedIndex < 1 ){
             cantidadError++;
             _campoError = objects_;
            }
       </c:forEach> 
      </c:forEach> 
   
     if(cantidadError == 1){
          appendErrorMessage(cantidadError + " registro aún no ha sido ingresado.")
      }
      if(cantidadError > 1){
          appendErrorMessage(cantidadError + " registros registros aún no han sido ingresados.")
      }
    </c:if>*/
    
    
   }// fin de if(bandera == 0){
    
  }
		
	function nuevo(){
		document.frm.cmd.value=document.frm.NUEVO.value;
		document.frm.action='<c:url value="/planDeEstudios/Nuevo.do"/>';
		document.frm.submit();
	}
	
	
	function buscar(){
	bandera = 1;

		if(validarForma(document.frm)){
			document.frm.cmd.value=document.frm.BUSCAR.value;
			bandera = 0;
			document.frm.action='<c:url value="/siges/gestionAcademica/regNotasVigencia/Save.jsp"/>';
			document.frm.target ="_self";
			document.frm.submit();
		}
	}
	
	 
 
 
  
	function guardar(){
	   
		if(validarForma(document.frm)){
			
			bandera = 0;
			document.frm.action='<c:url value="/siges/gestionAcademica/regNotasVigencia/Save.jsp"/>';
			document.frm.target ="_self";
			document.frm.cmd.value=document.frm.GUARDAR.value;
			document.frm.submit();
		}
	}
	
	function generarHistorico(){
		   
		if(validarForma(document.frm)){
			document.frm.cmd.value=document.frm.GENERAR.value;
			document.frm.action= "../../../Inicio.dos";
			document.frm.target ="_blank";
			document.frm.submit();
		}
	}
	
	function ajaxSede(){ 
	  borrar_combo2(document.frm.filjornd);
	  borrar_combo2(document.frm.filmetod);
  	  borrar_combo2(document.frm.filgrado);
 	  borrar_combo2(document.frm.filgrupo); 
     if(document.frm.filinst.value > 0){
   	   document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	   document.frmAjaxNuevo.ajax[0].value =  document.frm.filinst.value;
	   document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_SED.value;
      document.frmAjaxNuevo.submit();
	  }
	}
	
	function ajaxJornada(){ 
	  borrar_combo2(document.frm.filjornd);
	  borrar_combo2(document.frm.filmetod);
  	  borrar_combo2(document.frm.filgrado);
 	  borrar_combo2(document.frm.filgrupo);	  
    if(document.frm.filsede.value > 0){
   	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value =  document.frm.filinst.value;
	  document.frmAjaxNuevo.ajax[1].value =  document.frm.filsede.value;
	  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_JORD.value;
	  document.frmAjaxNuevo.submit();
	  }
	}
		
	function ajaxMetodologia(){ 
	  borrar_combo2(document.frm.filmetod);
  	  borrar_combo2(document.frm.filgrado);
 	  borrar_combo2(document.frm.filgrupo); 
 	  if(document.frm.filjornd.value > 0  ){
	   document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	   document.frmAjaxNuevo.ajax[0].value =  document.frm.filinst.value;
	   document.frmAjaxNuevo.ajax[1].value = document.frm.filsede.value;
	   document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_METD.value;
	   document.frmAjaxNuevo.submit();
	  }
	}
		 
	function ajaxGrado(){
 	  borrar_combo2(document.frm.filgrado);
 	  borrar_combo2(document.frm.filgrupo);
 	  if(document.frm.filmetod.value > 0){
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value =  document.frm.filinst.value;
	  document.frmAjaxNuevo.ajax[1].value = document.frm.filsede.value;
      document.frmAjaxNuevo.ajax[2].value = document.frm.filjornd.value;
      document.frmAjaxNuevo.ajax[3].value = document.frm.filmetod.value;
      document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_GRAD.value;
      document.frmAjaxNuevo.submit();
     }
	}
	
	
   function ajaxGrupo(){
	  borrar_combo2(document.frm.filgrupo);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
      document.frmAjaxNuevo.ajax[0].value =  document.frm.filinst.value;
      document.frmAjaxNuevo.ajax[1].value = document.frm.filsede.value;
      document.frmAjaxNuevo.ajax[2].value = document.frm.filjornd.value;
      document.frmAjaxNuevo.ajax[3].value = document.frm.filmetod.value;
      document.frmAjaxNuevo.ajax[4].value = document.frm.filgrado.value;
      document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_GRUP.value;
	  document.frmAjaxNuevo.submit();
   }
   
	
	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno","-9");
	}
	 
	 
	 
	function setVisible(){
	 
	  if(document.getElementById('t2')){
	   document.getElementById('t2').style.display='none';
	   document.getElementById('t2').style.display='none';
	   bandera = 1;
	  }
	}
		
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
 	  <form method="post" name="frmAjaxNuevo" action='<c:url value="/siges/gestionAcademica/RegNotasVigencia/RegNotasAjax.do"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${ paramsVO.FICHA_FILTRO_REG_NOTAS}"/>'>
		<input type="hidden" name="cmd"  value='-99'>
		<input type="hidden" name="CMD_AJAX_SED"  id="CMD_AJAX_SED"  value='<c:out value="${paramsVO.CMD_AJAX_SED }"/>'>
		<input type="hidden" name="CMD_AJAX_JORD"  id="CMD_AJAX_JORD"  value='<c:out value="${paramsVO.CMD_AJAX_JORD}"/>'>
		<input type="hidden" name="CMD_AJAX_METD"  id="CMD_AJAX_METD"  value='<c:out value="${paramsVO.CMD_AJAX_METD}"/>'>
		<input type="hidden" name="CMD_AJAX_GRAD"  id="CMD_AJAX_GRAD"  value='<c:out value="${paramsVO.CMD_AJAX_GRAD}"/>'>
		<input type="hidden" name="CMD_AJAX_GRUP"  id="CMD_AJAX_GRUP"  value='<c:out value="${paramsVO.CMD_AJAX_GRUP}"/>'>
		<input type="hidden" name="CMD_BUSCAR"      id="CMD_BUSCAR"  value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>

		<c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach>
	  </form>
	  
	  
	  
 		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					 <img src='<c:url value="/etc/img/tabs/acad_regNotasVig_1.gif"/>' alt="Registro de Notas Vigencias Anteriores" height="26" border="0">
				</td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		  <caption>REGISTRO DE NOTAS</caption>	
			<tr height="1">
				<td width="10">&nbsp;</td>
			</tr>
				 <tr><td align="center" colspan="4"><span style="color: RED;font-weight: bold;font-size: medium;"><b>
		    <c:out value="${sessionScope.estudianteRegNotasVO.estapellidos}"/>&nbsp;&nbsp;<c:out value="${sessionScope.estudianteRegNotasVO.estnombres}"/><br>
		    <c:out value="${sessionScope.estudianteRegNotasVO.esttipodocNom}"/>&nbsp;&nbsp;<c:out value="${sessionScope.estudianteRegNotasVO.estnumdoc}"/>
		    </b>
		                         
		 </span></td></tr>
		</table>
		
		 
	<form method="post" name="frm" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/siges/gestionAcademica/regNotasVigencia/Save.jsp"/>'>
		
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_NUEVO_REG_NOTAS }"/>'>
		<input type="hidden" name="cmd" value=''>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="BUSCAR"      id="CMD_BUSCAR"  value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="GENERAR"      id="CMD_GENERAR_HISTORICO"  value='<c:out value="${paramsVO.CMD_GENERAR_HISTORICO}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="FICHA_NUEVO_REG_NOTAS" value='<c:out value="${ paramsVO.FICHA_NUEVO_REG_NOTAS }"/>'>
		<input type="hidden" name="filinst" id="filinst" value='<c:out value="${login.instId}"/>'>		
		<input type="hidden" name="notaMin" id="notaMin" value='<c:out value="${sessionScope.tipoEvalMin}"/>'>
	    <input type="hidden" name="notaMax" id="notaMax" value='<c:out value="${sessionScope.tipoEvalMax}"/>'>		
		<input type="hidden" name="key" value="8">
		<input type="hidden" name="estnumdoc" value='<c:out value="${sessionScope.estudianteRegNotasVO.estnumdoc}"/>'>
		<input type="hidden" name="estnombre" value='<c:out value="${sessionScope.estudianteRegNotasVO.estapellidos}"/>&nbsp;<c:out value="${sessionScope.estudianteRegNotasVO.estnombres}"/>'>
		<table width="100%">
		 <tr><td colspan="4" class="Fila0" align="center">Filtro de búsqueda</td></tr> 
		 <tr><td colspan="1" ><input type="button" class="boton" value="Buscar" onclick="JavaScript:buscar();" >  </td>
		 <td colspan="3" ><input type="button" class="boton" value="Reporte Ajustes" onclick="JavaScript:generarHistorico();" >  </td>
		 </tr> 
		 <tr>
		    <td style="width: 15%"><span class="style2" >*</span>Sede</td>
		 	    <td style="width: 35%"><select  name="filsede" id="filsede" style="width: 150px;" onchange="JavaScript:setVisible();ajaxJornada();">
		 	        <option value="-99">-- Seleccione uno--</option>
		 	         <c:forEach begin="0" items="${sessionScope.listaSede }" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${filtroRegNotasVO.filsede == vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	      </select>
		 	    </td> 
                 <td style="width: 15%"><span class="style2" >*</span>Jornada</td>
		 	    <td style="width: 35%"><select name="filjornd" id="filjornd" style="width: 150px;" onchange="JavaScript:setVisible();ajaxMetodologia();"  >
		 	        <option value="-99">-- Seleccione uno--</option>
		 	          <c:forEach begin="0" items="${ sessionScope.listaJornada }" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${filtroRegNotasVO.filjornd ==vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	 </tr>   
		 	 <tr><td><span class="style2" >*</span>Metodología</td>
		 	    <td><select name="filmetod" id="filmetod" style="width: 150px;" onchange="JavaScript:setVisible();ajaxGrado();">
		 	        <option value="-99">-- Seleccione uno--</option>
		 	            <c:forEach begin="0" items="${sessionScope.listaMetodo }" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${filtroRegNotasVO.filmetod  ==vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	    <td><span class="style2" >*</span>Grado</td>
		 	    <td><select name="filgrado" id="filgrado" style="width: 150px;" onchange="JavaScript:setVisible();ajaxGrupo();">
		 	        <option value="-99">-- Seleccione uno--</option>
		 	              <c:forEach begin="0" items="${sessionScope.listaGrado }" var="vig">
				     <option value="<c:out value="${vig.codigo}"/>" <c:if test="${filtroRegNotasVO.filgrado == vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	 </tr>   
		 	  <tr><td><span class="style2" >*</span>Grupo</td>
		 	    <td><select  name="filgrupo" id="filgrupo" style="width: 150px;" >
		 	        <option value="-99">-- Seleccione uno--</option>
		 	         <c:forEach begin="0" items="${sessionScope.listaGrupo }" var="vig">
				      <option value="<c:out value="${vig.codigo}"/>" <c:if test="${filtroRegNotasVO.filgrupo == vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	 
		 	 
		 	    <td><span class="style2" >*</span>Vigencia</td>
		 	    <td><select  name="filvigencia" id="filvigencia" style="width: 150px;" onchange="JavaScript:setVisible();" >
		 	        <option value="-99">-- Seleccione uno--</option>
		 	         <c:forEach begin="0" items="${sessionScope.listaVigencia }" var="vig">
				      <option value="<c:out value="${vig.codigo}"/>" <c:if test="${filtroRegNotasVO.filvigencia == vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
                     </c:forEach>
		 	        </select>
		 	    </td>
		 	  </tr>
		 	  <tr>
		 	  <td><span class="style2" >*</span>Tipo Promoción</td>
		 	    <td><select  name="filtipo" id="filtipo" style="width: 150px;" >
		 	        <option value="-99">-- Seleccione uno--</option>
				    <option value="1" <c:if test="${filtroRegNotasVO.filtipo =='1'}">SELECTED</c:if>>Semestre 1</option>
				    <option value="2" <c:if test="${filtroRegNotasVO.filtipo =='2'}">SELECTED</c:if>>Semestre 2</option>
				    <option value="3" <c:if test="${filtroRegNotasVO.filtipo =='3'}">SELECTED</c:if>>Anual</option>
		 		</select>
		 	    </td>
		 	  </tr>
		 	  
		 	  <tr style="display:none"><td><iframe name="frameAjaxNuevo" id="frameAjaxNuevo"></iframe></td></tr>
		</table>
       <table width="100%"   > 
       <caption>LISTADO DE ÁREA / ASIGNATURA</caption>
         <tr><td>
          <table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
         <c:if test="${empty sessionScope.listaArea}">
          <tr><td colspan="4" align="center">No existe información con este filtro</td></tr>
         </c:if>
       </table>
       </td>
       </tr>
       <table width="100%" style="display:" id='t2' name='t2'   >  
         
         
        <c:if test="${!empty sessionScope.listaArea}">
          <tr><td colspan="4" ><input type="button" class="boton" value="Guardar" onclick="JavaScript:guardar();" > 
            <c:if test="${sessionScope.tipoEval != paramsVO.ESCALA_CONCEPTUAL }">
             <span class="style3"><b>Valor mínimo nota: <c:out value="${sessionScope.tipoEvalMin}"/> | Valor máximo nota: <c:out value="${sessionScope.tipoEvalMax}"/></b></span>							
            </c:if>
          
           </td></tr>
           <table width="100%" style="display:" id='t2' name='t2'   >
           <tr>
           	<td style="width: 17%"> Promoción</td>
           	<td style="width: 34%">
				<select name="filprom" id="filprom" style="width: 150px;"  >
		 	        <option value="-99">-- No existen datos--</option>
				    <option value="1" <c:if test="${requestScope.prom =='1'}">SELECTED</c:if>>Promovido</option>
				    <option value="0" <c:if test="${requestScope.prom =='0'}">SELECTED</c:if>>No Promovido</option>
				    <option value="3" <c:if test="${requestScope.prom =='3'}">SELECTED</c:if>>Retirado</option>
		 		</select>
           	</td>
           	<td style="width: 15%"> Tipo de promoción</td> 
				<td style="width: 34%">
				<select name="filtipoprom" id="filtipoprom" style="width: 150px;" >
		 	        <option value="-99">-- No existen datos--</option>
				    <option value="1" <c:if test="${requestScope.tipoProm =='1'}">SELECTED</c:if>>Semestre 1</option>
				    <option value="2" <c:if test="${requestScope.tipoProm =='2'}">SELECTED</c:if>>Semestre 2</option>
				    <option value="3" <c:if test="${requestScope.tipoProm =='3'}">SELECTED</c:if>>Anual</option>
		 		</select> 
           		</td>
           	</tr>
           	<tr>
           	<td colspan="1" style="width: 17%"> Observaciones</td>
           	<td colspan="3" style="width: 34%">
				<textarea name="filobs" id="filobs" rows="2" cols="50"><c:out value="${requestScope.obs}"/></textarea>
           	</td>
           	</tr>
           </table>
         <tr><td>
        <table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC">
        <tr>	
		  <th class="EncabezadoColumna" style="color: white;font-weight: bold;font-size: 12px;"  colspan="1" align="center">ÁREAS / ASIGNATURAS</th>
		   <c:forEach begin="0" items="${sessionScope.listaPeriodos.listaPerd}" var="itemVO">
		   <th class="EncabezadoColumna" style="color: white;font-weight: bold;font-size: 12px;"  colspan="1" align="center"><c:out value="${itemVO.nombre}"/></th>
		  </c:forEach>  
           <th class="EncabezadoColumna" style="color: white;font-weight: bold;font-size: 12px;"  colspan="1" align="center"><c:out value="${sessionScope.listaPeriodos.insparnomperdef}"/></th>
		</tr>
        <!-- Iteracion área -->
        <c:forEach begin="0" items="${sessionScope.listaArea }" var="areaVO">
		<tr>
           <td colspan="1" width="250px" > 
             <span style="color: black;font-size: 12px;font-weight: bold;" >
              <c:out value="${areaVO.arenombre}"/>
             </span>
           </td>
           <!-- Periodos área -->
           <c:forEach begin="0" items="${areaVO.listaEvalAreNota }" var="notas"  varStatus="var" >
		   <td width="80px" align="center">
               <c:if test="${sessionScope.tipoEval == paramsVO.ESCALA_CONCEPTUAL }">
		          <select   name='notaEst_<c:out value="${areaVO.arecodJerar}" />_<c:out value="${var.count}" />' id='notaEst_<c:out value="${areaVO.arecodJerar}" />_<c:out value="${var.count}" />'   style='BACKGROUND:#ffffff;COLOR:#000000;'  >
		            <option value='-99'>&nbsp;</option>
			          <c:forEach begin="0" items="${sessionScope.listaEscala}" var="escalaVO">
			            <option value="<c:out value="${escalaVO.codigo}"/>" <c:if test="${escalaVO.codigo  == notas }">SELECTED</c:if>><c:out value="${escalaVO.nombre}"/></option>
			          </c:forEach>  		            
		           </select>
		           </c:if>
		            <c:if test="${sessionScope.tipoEval != paramsVO.ESCALA_CONCEPTUAL }">
		             <input type="text" onKeyPress='return acepteNumeros(event)'  name='notaEst_<c:out value="${areaVO.arecodJerar}" />_<c:out value="${var.count}" />' id='notaEst_<c:out value="${areaVO.arecodJerar}" />_<c:out value="${var.count}" />'    style="width: 48px;" maxlength='4' value='<c:out value="${notas}" />'  >
		             
		           </c:if>
		   </td>
		   </c:forEach>  
		   <!-- /Periodos  área -->
           <!-- Periodo Final área -->
		   <td width="80px" align="center" >
               <c:if test="${sessionScope.tipoEval == paramsVO.ESCALA_CONCEPTUAL }">
		          <select  name='notaEst_<c:out value="${areaVO.arecodJerar}" />_<c:out value="${7}" />' id='notaEst_<c:out value="${areaVO.arecodJerar}" />_<c:out value="${7}" />'   style='BACKGROUND:#ffffff;COLOR:#000000;'  >
		            <option value='-99'>&nbsp;</option>
			          <c:forEach begin="0" items="${sessionScope.listaEscala}" var="escalaVO">
			            <option value="<c:out value="${escalaVO.codigo}"/>" <c:if test="${escalaVO.codigo == areaVO.finalEvalAreNota}">SELECTED</c:if>><c:out value="${escalaVO.nombre}"/></option>
			          </c:forEach>  		            
		           </select>
		           </c:if>
		           <c:if test="${sessionScope.tipoEval != paramsVO.ESCALA_CONCEPTUAL }">
		             <input type="text"  onKeyPress='return acepteNumeros(event)'  name='notaEst_<c:out value="${areaVO.arecodJerar}" />_<c:out value="${7}" />' id='notaEst_<c:out value="${areaVO.arecodJerar}" />_<c:out value="${7}" />'   style="width: 48px;" maxlength='4'  value='<c:out value="${areaVO.finalEvalAreNota }" />'    >
		           </c:if>
		   </td>
           <!-- /Periodo Final área -->
        </tr>
        <tr> 
           <td>
           <!-- Iteracion asignatura -->
	        <c:forEach begin="0" items="${areaVO.listaAsig}" var="asigVO">
			   <tr>
	            <td>&nbsp;&nbsp;&nbsp;<c:out value="${asigVO.asinombre}"/></td>
	            <!-- Periodos asignatura -->
	            <c:forEach begin="0" items="${asigVO.listaEvalAsiNota }" var="itemVOAsig" varStatus="varAsig">
		        <td width="80px" align="center" >
		           <c:if test="${sessionScope.tipoEval == paramsVO.ESCALA_CONCEPTUAL }">
		          <select  name='notaEst_<c:out value="${asigVO.asicodjerar}" />_<c:out value="${varAsig.count}" />' id='notaEst_<c:out value="${asigVO.asicodjerar}" />_<c:out value="${varAsig.count}" />'  style='BACKGROUND:#ffffff;COLOR:#000000;'  >
		            <option value='-99'>&nbsp;</option>
			          <c:forEach begin="0" items="${sessionScope.listaEscala}" var="escalaVO">
			            <option value="<c:out value="${escalaVO.codigo}"/>" <c:if test="${escalaVO.codigo == itemVOAsig}">SELECTED</c:if>><c:out value="${escalaVO.nombre}"/></option>
			          </c:forEach>  		            
		           </select>
		           </c:if>
		           <c:if test="${sessionScope.tipoEval != paramsVO.ESCALA_CONCEPTUAL }">
		             <input type="text"  style="width: 48px;" onKeyPress='return acepteNumeros(event)'  name='notaEst_<c:out value="${asigVO.asicodjerar}" />_<c:out value="${varAsig.count}" />' id='notaEst_<c:out value="${asigVO.asicodjerar}" />_<c:out value="${varAsig.count}" />'  maxlength='4'  value='<c:out value="${itemVOAsig}"/>'   >
		           </c:if>
		         </td>
		        </c:forEach>  
		        <!-- /Periodos  asignatura-->
		        <!-- Periodo Final asignatura -->
		        <td width="80px" align="center" >
		          <c:if test="${sessionScope.tipoEval == paramsVO.ESCALA_CONCEPTUAL }">
		          <select  name='notaEst_<c:out value="${asigVO.asicodjerar}" />_<c:out value="${7}" />' id='notaEst_<c:out value="${asigVO.asicodjerar}" />_<c:out value="${7}" />'  style='BACKGROUND:#ffffff;COLOR:#000000;'  >
		            <option value='-99'>&nbsp;</option>
			          <c:forEach begin="0" items="${sessionScope.listaEscala}" var="escalaVO">
			            <option value="<c:out value="${escalaVO.codigo}"/>" <c:if test="${escalaVO.codigo  == asigVO.finalEvalAsiNota }">SELECTED</c:if>><c:out value="${escalaVO.nombre}"/></option>
			          </c:forEach>  		            
		           </select>
		           </c:if>
		           <c:if test="${sessionScope.tipoEval != paramsVO.ESCALA_CONCEPTUAL }">
		             <input type="text"  style="width: 48px;"  onKeyPress='return acepteNumeros(event)'  name='notaEst_<c:out value="${asigVO.asicodjerar}" />_<c:out value="${7}" />'  id='notaEst_<c:out value="${asigVO.asicodjerar}" />_<c:out value="${7}" />' maxlength='4'   value='<c:out value="${asigVO.finalEvalAsiNota }" />'    >
		           </c:if>
		         </td>
                <!-- /Periodo Final asignatura -->
	           </tr>
	        </c:forEach>
	       <!-- /Iteracion asignatura asignatura -->
            </td>
           </tr>
          </c:forEach>
          </table> 
         </td>
         </tr>
         </c:if>
        </table>  
	</form>
</body>
<script type="text/javascript" >
 <c:if test="${  filtroRegNotasVO.filsede == 0 }"> 
   ajaxSede();
 </c:if>
 
  <c:if test="${  filtroRegNotasVO.filgrupo > 0 and filtroRegNotasVO.filvigencia > 0  and empty sessionScope.listaArea}"> 
    alert("No se encontró información de áreas / asignaturas: Posiblemente no existen datos de 'plan de estudios' para la vigencia " + document.frm.filvigencia.value+"." );
 </c:if>
 
 /*
 for(var i=0;i< 25;i++){
	alert(document.frm.elements[i].type+" : "+document.frm.elements[i].name);
	var nn = document.frm.elements[i].value;
	alert(nn);
	//document.getElementById(nn).disbled = true;
 }*/
 
 
</script>
</html>