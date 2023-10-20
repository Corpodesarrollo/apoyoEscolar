<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@page import="siges.util.Recursos"%>
<%pageContext.setAttribute("filtroLoc",Recursos.recurso[Recursos.LOCALIDAD]);%>
<%@include file="../parametros.jsp"%>

<script language="javascript" type="text/JavaScript">
<!--
  var nav4=window.Event ? true : false;
  
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("Seleccione uno","-1");
	}
	
	function llenarJorn(forma){
		borrar_combo(forma.forcodjorcurso)
		var jor,k=0;
		var niv = new Array();
		niv[1]='Mañana';
		niv[2]='Tarde';
		niv[3]='Sabado';
		var sel=forma.forcodcurso.selectedIndex
		if(sel>0){
			if(forma.nivel_){
				if(forma.nivel_.length){
					if(forma.nivel_[sel-1].value){
						for(var i=1;i<=forma.nivel_[sel-1].value;i++){
							jor='<c:out value="${sessionScope.nuevoConvocatoria.forcodjorcurso}"/>';
							if(jor==i){
								forma.forcodjorcurso.options[k+1] = new Option(niv[i],i,"SELECTED");
								forma.forcodjorcurso.selectedIndex = k+1;
							}else{
								forma.forcodjorcurso.options[k+1] = new Option(niv[i],i);
							}
							k++;
						}
					}
				}else{
					if(forma.nivel_.value){
						for(var i=1;i<=forma.nivel_.value;i++){
							jor='<c:out value="${sessionScope.nuevoConvocatoria.forcodjorcurso}"/>';
							if(jor==i){
								forma.forcodjorcurso.options[k+1] = new Option(niv[i],i,"SELECTED");
								forma.forcodjorcurso.selectedIndex = k+1;
							}else{
								forma.forcodjorcurso.options[k+1] = new Option(niv[i],i);
							}
							k++;
						}
					}
				}
			}
		}
	}
	
	/*function inhabilitarFormulario(){
		for(var i=0;i<document.form1.elements.length;i++){
			if(document.form1.elements[i].type != "hidden" && document.form1.elements[i].type != "button" && document.form1.elements[i].type != "submit"){
				document.form1.elements[i].disabled=true;
			}
		}
	}*/
	
	function inhabilitarFormulario(){
		document.form1.fortipodoc.disabled=true;
		document.form1.fornumdoc.disabled=true;
		document.form1.forcodlocal.disabled=true;
		/*document.form1.forcoddane.disabled=true;
		document.form1.forcodsede.disabled=true;
		document.form1.forcodjorn.disabled=true;*/
		document.form1.forservicio.disabled=true;
		document.form1.forescalafon.disabled=true;
	}

	function cargaColegio(){
		borrar_combo(document.form1.forcoddane);
		borrar_combo(document.form1.forcodsede);
		//borrar_combo(document.form1.forcodjorn);
		document.getElementById("txtcol").innerHTML="CARGANDO COLEGIOS...";
		document.frmAux.loc.value=document.form1.forcodlocal.options[document.form1.forcodlocal.selectedIndex].value;
		document.frmAux.combo.value="3";
		document.frmAux.target="frame";
		document.frmAux.submit();
	}
	
	function cargaSede(){
		borrar_combo(document.form1.forcodsede);
		//borrar_combo(document.form1.forcodjorn);
		document.getElementById("txtsed").innerHTML="CARGANDO SEDES...";
		document.frmAux.col.value=document.form1.forcoddane.options[document.form1.forcoddane.selectedIndex].value;
		document.frmAux.combo.value="4";
		document.frmAux.target="frame";
		document.frmAux.submit();
	}
	
	function cargaJornada(){
		borrar_combo(document.form1.forcodjorn);
		document.frmAux.col.value=document.form1.forcoddane.options[document.form1.forcoddane.selectedIndex].value;
		document.frmAux.sed.value=document.form1.forcodsede.options[document.form1.forcodsede.selectedIndex].value;
		document.frmAux.combo.value="5";
		document.frmAux.target="frame";
		document.frmAux.submit();
	}
	
	function hacerValidaciones_form1(forma){
		//validarLista(forma.forcodcurso,"- Curso",1)
		//validarLista(forma.forcodjorcurso,"- Jornada del Curso",1)
		/*basicos*/
		validarCampo(forma.fornombre1, "- Primer Nombre", 1,20)
		validarCampo(forma.forapellido1, "- Primer Apellido", 1, 20)
		validarLista(forma.fortipodoc,"- Tipo de documento",1)
    validarCampo(forma.fornumdoc, "- Cédula", 1, 11)
	  validarCampo(forma.foredad, "- Edad", 1, 2)
		//if (trim(forma.correoper.value).length > 0)
		validarcorreo(forma.forcorreoper,"- Correo Personal")
		//if (trim(forma.correoins.value).length > 0)
		validarcorreo(forma.forcorreoins,"- Correo Institucional")
		if(document.form1.tipo.value==2){
			validarLista(forma.forcodcurso,"- Curso",1)
			/*laboral*/
			validarLista(forma.forcodlocal,"- Localidad",1)
			validarLista(forma.forcoddane,"- Colegio",1)
			validarLista(forma.forcodsede,"- Sede",1)
			validarLista(forma.forcodjorn,"- Jornada",1)
			validarLista(forma.forservicio,"- Tiempo de Servicio en el Colegio",1)
			validarLista(forma.forescalafon,"- Categoría en el Escalafón Nacional Docente",1)
		}else if(document.form1.tipo.value==5){
			validarLista(forma.forcodcurso,"- Curso",1)
		}
	}

	function guardar(n){
		document.form1.tipo.value=n;
		if(validarForma(document.form1)){
			if(document.form1.tipo.value==2)document.form1.fornomins.value=document.form1.forcoddane.options[document.form1.forcoddane.selectedIndex].text;
			if(document.form1.tipo.value==2)document.form1.fornomsede.value=document.form1.forcodsede.options[document.form1.forcodsede.selectedIndex].text;
			if(document.form1.tipo.value==2)document.form1.fornomjorn.value=document.form1.forcodjorn.options[document.form1.forcodjorn.selectedIndex].text;
			document.form1.submit();
		}
	}

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}

	function habilitartitulotecnico(){
		document.form1.fortittecnologo.disabled= false;
	}            
     
	function inhabilitartitulotecnico(){    
		document.form1.fortittecnologo.value="";
	  document.form1.fortittecnologo.disabled= true;
	}
	
	function habilitarlicenciatura(){
		document.form1.fortitlicenc.disabled= false;
	}            
     
	function inhabilitarlicenciatura(){    
		document.form1.fortitlicenc.disabled= true;
	}	

	function habilitarotralicenciatura(){
		document.form1.fortitotralicenc.disabled= false;
	}            
     
	function inhabilitarotralicenciatura(){    
		document.form1.fortitotralicenc.value="";
	  document.form1.fortitotralicenc.disabled= true;
	}	
	
	function eliminar(n){
		if(document.form1.id){
			if(confirm('¿DESEA ELIMINAR LA INSCRIPCIÓN A ESTE CURSO?')){
				document.form1.id.value=n;
				document.form1.tipo.value='3';
				document.form1.submit();
			}
		}
	}
	

		//-->
</script>
<%@include file="../mensaje.jsp" %>
<form name="form1" method="post" action="<c:url value="/convocatoria/NuevoGuardar.jsp"/>" onSubmit="return hacerValidaciones(form1)" >
	<input type="hidden" name="fornomins" value="">
	<input type="hidden" name="fornomsede" value="">
	<input type="hidden" name="fornomjorn" value="">
	<input type="hidden" name="tipo" value="2">
	<input type="hidden" name="id" value="">

<c:if test="${sessionScope.nuevoConvocatoria.estado==1}">
<p style="FONT-FAMILY: Tahoma;FONT-SIZE: 12px;color:red;text-align: center;font-weight: bold;">Consulta de datos de inscripción</p>
</c:if>
<c:if test="${sessionScope.nuevoConvocatoria.estado!=1}">
<p style="FONT-FAMILY: Tahoma;FONT-SIZE: 12px;color:red;text-align: center;font-weight: bold;">Usted no ha realizado ninguna inscripción, por favor diligencie el siguiente formulario</p>
</c:if>

	<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0">
	  	<tr>
		    <td colspan=4 bgcolor="#999999"><font size="2"><strong>Cursos Inscritos con Estado: 'Activo'</strong></font></td>
			</tr>
		  <c:if test="${empty requestScope.inscritos}">
				<tr>
					<th class="Fila1" colspan='6'>No se ha inscrito a ningún curso</th>
				</tr>
			</c:if>
	  	<c:if test="${!empty requestScope.inscritos}">
		  	<tr>
					<th width='30' class="EncabezadoColumna">&nbsp;</th>
					<th class="EncabezadoColumna">Nombre</th>
					<th class="EncabezadoColumna">Grupo</th>
					<th class="EncabezadoColumna">Fecha Inscripción</th>
				</tr>
				<c:forEach begin="0" items="${requestScope.inscritos}" var="fila" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:eliminar(<c:out value="${fila[0]}"/>);'><img border='0' src='../etc/img/eliminar.png' width='15' height='15'></a>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila[1]}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila[2]}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila[3]}"/></td>
					</tr>
				</c:forEach>
			</c:if>
	  </table>
	<br>
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
	    <td colspan=4 bgcolor="#999999"><font size="2"><strong>Cursos Disponibles</strong></font></td>
		</tr>
		<tr><td>&nbsp;</tr></tr>
		<tr>
			<td colspan="2"><span class="style2">*</span>&nbsp;Curso &nbsp
				<select name="forcodcurso" class="entrada1" id="forcodcurso" onchange="llenarJorn(document.form1)" style='width:310px;'>
					<option value="-1" selected>Seleccione uno</option>
					<c:forEach items="${requestScope.cursos}" var="fila"><c:if test="${fila[0]==1}"><option value="<c:out value="${fila[6]}"/>" <c:if test="${sessionScope.nuevoConvocatoria.forcodcurso == fila[6]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option></c:if></c:forEach>
	      </select>
	      <c:forEach items="${requestScope.cursos}" var="fila"><c:if test="${fila[0]==1}"><input type="hidden" name="nivel_" value="<c:out value="${fila[7]}"/>"/></c:if></c:forEach>
	    </td>
	    <td width="38%">Jornada
				<select name="forcodjorcurso" class="entrada1" id="forcodjorcurso">
					<option value="-1" selected>Seleccione uno</option>
	      </select>
      </td>
		</tr>
		<tr><td>&nbsp;</tr></tr>
		<tr>
	    <td colspan= "5">
				<table border = 1>
					<tr><th width="340">Curso</th><th width="230">Cupos</th><th width="70">Grupos</th><th width="160">Jornadas</th><th width="70">Cupo Total</th><th>Responsable</th><th>E-Mail Responsable</th></tr>
					<c:forEach items="${requestScope.cursos}" var="fila"><c:if test="${fila[0]==1}"><tr><td width="40%"><c:out value="${fila[1]}"/></td><td width="20%"><center><c:out value="${fila[2]}"/></center></td><td width="10%"><center><c:out value="${fila[3]}"/></center></td><td width="20%"><center><c:out value="${fila[4]}"/></center></td><td width="10%"><center><c:out value="${fila[5]}"/></center><td><center><c:out value="${fila[8]}"/></center></td><td><a class="A" href="mailto:<c:out value="${fila[9]}"/>"><c:out value="${fila[9]}"/></a></td></tr></c:if></c:forEach>
				</table>
			</td>
		</tr>
		<tr><td>&nbsp</td></tr>
	</table>
	
	<br>

	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="4">
		<tr>
			<td colspan="4" bgcolor="#999999"><font size="2"><strong>Información Personal</strong></font></td>
		</tr>
		<tr>
			<td><span class="style2">*</span><span>&nbsp;1.Primer Nombre</span></td>
			<td><input name="fornombre1" type="text" id="fornombre1" size="20" value="<c:out value="${sessionScope.nuevoConvocatoria.fornombre1}"/>"></td>
			<td><span>2.Segundo Nombre</span></td>
			<td><input name="fornombre2" type="text" id="fornombre2" size="20" value="<c:out value="${sessionScope.nuevoConvocatoria.fornombre2}"/>"></td>
		</tr>
		<tr>
			<td><span class="style2">*</span><span>&nbsp;3.Primer Apellido</span></td>
			<td><input name="forapellido1" type="text" id="forapellido1" size="20" value="<c:out value="${sessionScope.nuevoConvocatoria.forapellido1}"/>"></td>
			<td><span>4.Segundo Apellido</span></td>
			<td><input name="forapellido2" type="text" id="forapellido2" size="20" value="<c:out value="${sessionScope.nuevoConvocatoria.forapellido2}"/>"></td>
		</tr>
    <tr>
    	<td colspan="4"><span class="style2">*</span><span>&nbsp;5.Tipo Documento</span>
	    	<select name="fortipodoc" class="entrada1" id="fortipodoc">
	    		<option value="-1" selected>Seleccione uno</option>
	      	<option value="1" <c:if test="${sessionScope.nuevoConvocatoria.fortipodoc== 1}">SELECTED</c:if>>Cédula de Ciudadanía</option>
	        <option value="2" <c:if test="${sessionScope.nuevoConvocatoria.fortipodoc== 2}">SELECTED</c:if>>Cédula de Extranjería</option>
	      </select>
				<span class="style2">*</span><span>&nbsp;6.Número Documento</span>
				<input name="fornumdoc" type="text" id="fornumdoc" size="12" onKeyPress='return acepteNumeros(event)' value="<c:out value="${sessionScope.nuevoConvocatoria.fornumdoc}"/>">
			<span class="style2">*</span><span>&nbsp;7.Edad&nbsp;</span><input name="foredad" type="text" id="foredad"  size="2" onKeyPress='return acepteNumeros(event)' value="<c:out value="${sessionScope.nuevoConvocatoria.foredad}"/>">&nbsp Años</td>
		</tr>
		<tr>
			<td><span>8.Teléfono</span></td>
			<td><input name="fortelefono" type="text" id="fortelefono" size='40' value="<c:out value="${sessionScope.nuevoConvocatoria.fortelefono}"/>"></td>
	    <td><span>9.Dirección</span></td>
	    <td><input name="fordireccion" type="text" id="fordireccion" size='40' value="<c:out value="${sessionScope.nuevoConvocatoria.fordireccion}"/>"></td>
	  </tr>
    <tr>
    	<td><span class="style2">*</span><span>&nbsp;10.Correo Personal</span></td>
			<td><input name="forcorreoper" type="text" id="forcorreoper" size='40' value="<c:out value="${sessionScope.nuevoConvocatoria.forcorreoper}"/>"></td>
			<td><span class="style2">*</span><span>&nbsp;12.Correo Institucional &nbsp</span></td>
			<td><input name="forcorreoins" type="text" id="forcorreoins" size='40' value="<c:out value="${sessionScope.nuevoConvocatoria.forcorreoins}"/>"></td>
		</tr>
	</table>

<br>

	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="3">
		<tr>
	    <td colspan=4 bgcolor="#999999"><font size="2"><strong>Información Laboral</strong></font></td>
		</tr>
    <tr>
      <td><span class="style2">*</span>&nbsp;13.Localidad</td>
      <td colspan="3">
      	<select name="forcodlocal" class="entrada1" id="forcodlocal" onChange="cargaColegio()">
	        <option value="-1" selected>Seleccione uno</option>
	        <c:forEach begin="0" items="${filtroLoc}" var="fila">
						<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.nuevoConvocatoria.forcodlocal == fila[0]}">SELECTED</c:if>>
							<c:out value="${fila[1]}"/></option>
					</c:forEach>
	      </select>
	     </td>
    </tr>
	<c:if test="${sessionScope.nuevoConvocatoria.estado!=1}">
    <tr>
      <td  width="20%"><span class="style2">*</span>&nbsp;14.Colegio</td>
			<td  colspan="3" width="80%">
		    <select name="forcoddane" class="entrada1" id="forcoddane" onChange="cargaSede()">
	  	    <option value="-1">Seleccione uno</option>
	      </select><span class="style2" id="txtcol" name="txtcol" style="font-weight:bold"></span>
			</td>
		</tr>
    <tr>
      <td><span class="style2">*</span>&nbsp;15.Sede</td>
      <td colspan="3">
      	<select name="forcodsede" class="entrada1" id="forcodsede" onChange="">
				  <option value="-1">Seleccione uno</option>
        </select><span class="style2" id="txtsed" name="txtsed" style="font-weight:bold"></span>
      </td>
   	</tr>
   	<tr>
			<td><span class="style2">*</span>&nbsp;16.Jornada</td>
	    <td colspan="3">
	    	<select name="forcodjorn" class="entrada1" id="forcodjorn">
		      <option value="-1">Seleccione uno</option>
		      <option value="1" <c:if test="${sessionScope.nuevoConvocatoria.forcodjorn== 1}">SELECTED</c:if>>Completa</option>
          <option value="2" <c:if test="${sessionScope.nuevoConvocatoria.forcodjorn== 2}">SELECTED</c:if>>Mañana</option>
          <option value="4" <c:if test="${sessionScope.nuevoConvocatoria.forcodjorn== 4}">SELECTED</c:if>>Tarde</option>
          <option value="8" <c:if test="${sessionScope.nuevoConvocatoria.forcodjorn== 8}">SELECTED</c:if>>Nocturna</option>
          <option value="16" <c:if test="${sessionScope.nuevoConvocatoria.forcodjorn== 16}">SELECTED</c:if>>Fin de Semana</option>
          <option value="32" <c:if test="${sessionScope.nuevoConvocatoria.forcodjorn== 32}">SELECTED</c:if>>Acelerada Mañana</option>
          <option value="64" <c:if test="${sessionScope.nuevoConvocatoria.forcodjorn== 64}">SELECTED</c:if>>Acelerada Tarde</option>
          <option value="0" <c:if test="${sessionScope.nuevoConvocatoria.forcodjorn== 0}">SELECTED</c:if>>Sin Jornada</option>
		    </select>
		  </td>
		</tr>
	  <!--<tr>
			<td><span class="style2">*</span>&nbsp;16.Jornada</td>
	    <td colspan="3">
	    	<select name="forcodjorn" class="entrada1" id="forcodjorn">
		      <option value="-1">Seleccione uno</option>
		    </select>
		  </td>
		</tr>-->
	</c:if>
	<c:if test="${sessionScope.nuevoConvocatoria.estado==1}">
		<tr>
	    <td  width="20%">14.Colegio</td>
	    <td><c:out value="${sessionScope.nuevoConvocatoria.fornomins}"/></td>
		</tr>
		<tr>
	    <td>15.Sede</td>
	    <td colspan="3"><c:out value="${sessionScope.nuevoConvocatoria.fornomsede}"/></td>
		</tr>
		<tr>
			<td>16.Jornada</td>
	    <td colspan="3"><c:out value="${sessionScope.nuevoConvocatoria.fornomjorn}"/></td>
	  </tr>
	</c:if>
    <tr>
      <td><span class="style2">*</span>&nbsp;17.Tiempo de Servicio en el Colegio</td>
      <td>
      	<select name="forservicio" class="entrada1" id="forservicio">
	      	<option value="-1" selected>Seleccione uno</option>
          <option value="1" <c:if test="${sessionScope.nuevoConvocatoria.forservicio == 1}">SELECTED</c:if>>Entre 1 y 3 años</option>
          <option value="2" <c:if test="${sessionScope.nuevoConvocatoria.forservicio == 2}">SELECTED</c:if>>Entre 4 y 7 años</option>
          <option value="3" <c:if test="${sessionScope.nuevoConvocatoria.forservicio == 3}">SELECTED</c:if>>Entre 8 y 15 años</option>
          <option value="4" <c:if test="${sessionScope.nuevoConvocatoria.forservicio == 4}">SELECTED</c:if>>Más de 15 años</option>
        </select>
      </td>
		  <td colspan="2"><span class="style2">*</span>&nbsp;18.Categoría en el Escalafón Nacional Docente &nbsp
        <select name="forescalafon" class="entrada1" id="forescalafon">
        	<option value="-99" selected>--</option>
          <option value="-1" <c:if test="${sessionScope.nuevoConvocatoria.forservicio == -1}">SELECTED</c:if>>A</option>
          <option value="-2" <c:if test="${sessionScope.nuevoConvocatoria.forservicio == -2}">SELECTED</c:if>>B</option>
          <option value="-3" <c:if test="${sessionScope.nuevoConvocatoria.forservicio == -3}">SELECTED</c:if>>C</option>
          <option value="1" <c:if test="${sessionScope.nuevoConvocatoria.forservicio == 1}">SELECTED</c:if>>1</option>
          <option value="2" <c:if test="${sessionScope.nuevoConvocatoria.forservicio == 2}">SELECTED</c:if>>2</option>
          <option value="3" <c:if test="${sessionScope.nuevoConvocatoria.forservicio == 3}">SELECTED</c:if>>3</option>
          <option value="4" <c:if test="${sessionScope.nuevoConvocatoria.forservicio == 4}">SELECTED</c:if>>4</option>
          <option value="5" <c:if test="${sessionScope.nuevoConvocatoria.forservicio == 5}">SELECTED</c:if>>5</option>
          <option value="6" <c:if test="${sessionScope.nuevoConvocatoria.forservicio == 6}">SELECTED</c:if>>6</option>
          <option value="7" <c:if test="${sessionScope.nuevoConvocatoria.forservicio == 7}">SELECTED</c:if>>7</option>
          <option value="8" <c:if test="${sessionScope.nuevoConvocatoria.forservicio == 8}">SELECTED</c:if>>8</option>
          <option value="9" <c:if test="${sessionScope.nuevoConvocatoria.forservicio == 9}">SELECTED</c:if>>9</option>
          <option value="10" <c:if test="${sessionScope.nuevoConvocatoria.forservicio == 10}">SELECTED</c:if>>10</option>
          <option value="11" <c:if test="${sessionScope.nuevoConvocatoria.forservicio == 11}">SELECTED</c:if>>11</option>
          <option value="12" <c:if test="${sessionScope.nuevoConvocatoria.forservicio == 12}">SELECTED</c:if>>12</option>
          <option value="13" <c:if test="${sessionScope.nuevoConvocatoria.forservicio == 13}">SELECTED</c:if>>13</option>
          <option value="14" <c:if test="${sessionScope.nuevoConvocatoria.forservicio == 14}">SELECTED</c:if>>14</option>
        </select>
      </td>
   	</tr>
	  <tr align="left" valign="baseline" bgcolor="#f4f4f4"> 
  		<td bgcolor="#FFFFFF">19.Grados que imparte</td>
      <td colspan="3" bgcolor="#FFFFFF" class="unnamed1"> 
        <table width="100%" border="0" bgcolor="#FFFFFF">
          <tr> 
            <td width="3%">0&ordm;</td>
            <td width="5%"><input type="checkbox" name="forgrado0" value="0" <c:if test="${sessionScope.nuevoConvocatoria.forgrado0 == 0}">checked</c:if>></td>
            <td width="3%">1&ordm;</td>
            <td width="5%"><input type="checkbox" name="forgrado1" value="1" <c:if test="${sessionScope.nuevoConvocatoria.forgrado1 == 1}">checked</c:if>></td>
            <td width="3%">2&ordm;</td>
            <td width="5%"><input type="checkbox" name="forgrado2" value="2" <c:if test="${sessionScope.nuevoConvocatoria.forgrado2 == 2}">checked</c:if>></td>
            <td width="3%">3&ordm;</td>
            <td width="5%"><input type="checkbox" name="forgrado3" value="3" <c:if test="${sessionScope.nuevoConvocatoria.forgrado3 == 3}">checked</c:if>></td>
            <td width="3%">4&ordm;</td>
            <td width="6%"><input type="checkbox" name="forgrado4" value="4" <c:if test="${sessionScope.nuevoConvocatoria.forgrado4 == 4}">checked</c:if>></td>
            <td width="3%">5&ordm;</td>
            <td width="6%"><input type="checkbox" name="forgrado5" value="5" <c:if test="${sessionScope.nuevoConvocatoria.forgrado5 == 5}">checked</c:if>></td>
            <td width="3%">6&ordm;</td>
            <td width="5%"><input type="checkbox" name="forgrado6" value="6" <c:if test="${sessionScope.nuevoConvocatoria.forgrado6 == 6}">checked</c:if>></td>
            <td width="3%">7&ordm;</td>
            <td width="7%"><input type="checkbox" name="forgrado7" value="7" <c:if test="${sessionScope.nuevoConvocatoria.forgrado7 == 7}">checked</c:if>></td>
            <td width="3%">8&ordm;</td>
            <td width="5%"><input type="checkbox" name="forgrado8" value="8" <c:if test="${sessionScope.nuevoConvocatoria.forgrado8 == 8}">checked</c:if>></td>
            <td width="3%">9&ordm;</td>
            <td width="5%"><input type="checkbox" name="forgrado9" value="9" <c:if test="${sessionScope.nuevoConvocatoria.forgrado9 == 9}">checked</c:if>></td>
            <td width="5%">10&ordm;</td>
            <td width="5%"><input type="checkbox" name="forgrado10" value="10" <c:if test="${sessionScope.nuevoConvocatoria.forgrado10 == 10}">checked</c:if>></td>
            <td width="5%">11&ordm;</td>
            <td width="5%"><input type="checkbox" name="forgrado11" value="11" <c:if test="${sessionScope.nuevoConvocatoria.forgrado11 == 11}">checked</c:if>></td>
          </tr>
        </table>
      </td>
    </tr>		
	</table>

<br>
<c:if test="${sessionScope.nuevoConvocatoria.estado!=1}">
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000">
		<tr>
			<td align="center"><input type="button" name="boton" onClick="guardar(2)" value="Inscribir" class="boton"></td>
    </tr>
		<tr>
			<td style="display:none"><iframe name="frame" id="frame"></iframe></td>
		</tr>
	</table>
</c:if>
<c:if test="${sessionScope.nuevoConvocatoria.estado==1}">
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#000000">
		<tr>
			<td align="center"><input type="button" name="boton" onClick="guardar(4)" value="Actualizar Datos" class="boton"></td>
			<c:if test="${empty requestScope.inscritos}">
			<td align="center"><input type="button" name="boton" onClick="guardar(5)" value="Inscribir Curso" class="boton"></td>
			</c:if>
    </tr>
		<tr>
			<td style="display:none"><iframe name="frame" id="frame"></iframe></td>
		</tr>
	</table>
</c:if>
</form>
<form method="post" name="frmAux" action="<c:url value="/convocatoria/ControllerFiltro.do"/>">
	<input type="hidden" name="combo" value="">
	<input type="hidden" name="loc" value="">
	<input type="hidden" name="col" value="">
	<input type="hidden" name="sed" value="">
	<input type="hidden" name="ext" value="1">
	<input type="hidden" name="forma" value="form1">
</form>

<c:if test="${sessionScope.nuevoConvocatoria.estado==1}">
	<script>inhabilitarFormulario();llenarJorn(document.form1);</script>
</c:if>