<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<%@page import="siges.util.Recursos"%>
<%pageContext.setAttribute("filtroTipoDocumento",Recursos.recurso[Recursos.TIPODOCUMENTO]);%>
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
	
	function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
		this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
	}

	function filtro(combo_padre,combo_hijo){
		borrar_combo(combo_hijo);
		<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">var Padres = new Array();
		<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
		<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"><c:if test="${fila[0]==fila2[2]}">Sel_Hijos[k] = '<c:if test="${sessionScope.login.jornadaId== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;</c:if></c:forEach>
		Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
		var niv=combo_padre.options[combo_padre.selectedIndex].value;
		var val_padre = -1;
		for(var k=0;k<Padres.length;k++){
			if(Padres[k].id_Padre[0]==niv) val_padre=k;							
		}
		if(val_padre!=-1){ var no_hijos = Padres[val_padre].Hijos.length;
			for(i=0; i < no_hijos; i++){
				if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
					combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
				}else
					combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
				}
			}
		</c:if>
	}
	
	function hacerValidaciones_form1(forma){
		//validarCampo(forma.nombres, "- Nombres", 1,100)
		//validarCampo(forma.apellidos, "- Apellidos", 1, 30)
		//validarCampo(forma.cedula, "- Cédula (Númerico(1-12))", 1, 12)
		//validarCampo(forma.edad, "- Edad (Númerico(1-2))", 1, 2)
		//if (trim(forma.correoper.value).length > 0) 								
		validarcorreo(forma.correoper,"- Email Personal")
		//if (trim(forma.correoins.value).length > 0) 								
		validarcorreo(forma.correoins,"- Email Institucional")
		//if (trim(forma.telefono.value).length > 0)
		//validarCampo(forma.telefono, "- Teléfono", 1,30)
		//validarLista(forma.localidad,"- Localidad",1)
		//validarLista(forma.colegio,"- Colegio",1)
		//validarLista(forma.sede,"- Sede",1)
	}

	function guardar(){
		if(validarForma(document.form1)){
			alert("Información guardada satisfactorimente");
			/*document.form1.colnombre.value=document.form1.colegio.options[document.form1.colegio.selectedIndex].text;
			document.form1.colegio_.value=document.form1.colegio.options[document.form1.colegio.selectedIndex].value;
			document.form1.sednombre.value=document.form1.sede.options[document.form1.sede.selectedIndex].text;
			document.form1.sede_.value=document.form1.sede.options[document.form1.sede.selectedIndex].value;
			document.form1.submit();*/
		}
	}

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}

	function habilitartitulotecnico(){
	   document.form1.titulotecnico.enable= true;
	}            

	function inhabilitartitulotecnico(){    
		document.form1.titulotecnico.value="";
	   document.form1.titulotecnico.disabled= true;
	}	

	function habilitarotralicenciatura(){
	   document.form1.otralicenciatura.enable= true;
	}            

	function inhabilitarotralicenciatura(){    
		document.form1.otralicenciatura.value="";
	   document.form1.otralicenciatura.disabled= true;
	}
	
	function habilitar(){}
	
	function inhabilitar(){}

	//-->
</script>
<%@include file="../mensaje.jsp"%>
<form name="form1" method="post" action="" onSubmit="return hacerValidaciones(form1)" >
<input type="hidden" name="colnombre" value="">
<input type="hidden" name="sednombre" value="">
<input type="hidden" name="colegio_" value="">
<input type="hidden" name="sede_" value="">
<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
	<tr>
		<td width="45%" bgcolor="#FFFFFF">
      <input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar()">
		</td>
	</tr>
</table>

<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
	<tr height="1">
		<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
		<td rowspan="2" width="600" bgcolor="#FFFFFF">
			<img src="../etc/img/tabs/docente_1.gif" alt="Información Docente" border="0"  height="26">
		</td>
	</tr>
</table>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<caption>Información Personal</caption>
	<tr>
		<td width="18%"><span>Nombre</span></td>
		<td colspan="3"><input name="nombres" type="text" id="nombres" size="80" value="<c:out value="${sessionScope.login.usuario}"/>" readonly></td>
	</tr>
  <tr>
  	<td><span>Número de Documento:</span></td>
  	<td colspan="3"><input name="cedula" type="text" id="cedula" size="12" onKeyPress='return acepteNumeros(event)' value="<c:out value="${sessionScope.login.usuarioId}"/>" readonly></td>
  </tr>
	<tr>
		<td><span>Edad</span></td>
		<td colspan="3"><input name="edad" type="text" id="edad"  size="2" onKeyPress='return acepteNumeros(event)'>&nbsp&nbsp Años</td>
	</tr>
	<tr>
		<td><span>Genero</span></td>
		<td width="66%"><input name="genero" type="radio" value="1">Masculino<input name="genero" type="radio" value="2">Femenino</td>
	</tr>
	<tr>
	  <td><span>Teléfono Casa </span></td>
	  <td colspan="3"><input name="telefono" type="text" id="telefono" size='40'></td>
	</tr>
  <tr>
  	<td><span>Dirección Casa </span></td>
    <td colspan="3"><input name="direccion" type="text" id="direccion" size='50'></td>
  </tr>
  <tr>
  	<td><span>Email Personal</span></td>
  	<td colspan="3"><input name="correoper" type="text" id="correoper" size='50'></td>
  </tr>
	<tr>
		<td><span>Email Institucional</span></td>
		<td width="30%"><input name="correoins" type="text" id="correoins" size='50'></td>
	</tr>
</table>

<br>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<caption>Información Laboral</caption>
  <tr>
  	<td>Sede</td>
    <td>
	 		<select name="sede" id="sede" onChange='filtro(document.form1.sede, document.form1.jornada)'>
			  <option value="-1">Seleccione uno</option>
			  <c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
					<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.login.sedeId== fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
				</c:forEach>
      </select>
		</td>
	</tr>
  <tr>
  	<td>Jornada</td>
    <td>
    	<select name="jornada" id="jornada">
      	<option value="-1">Seleccione uno</option>
      </select>
    </td>
  </tr>
  <tr>
  	<td>Tiempo de Servicio en el Colegio</td>
    <td>
    	<select name="servicio" id="servicio">
    		<option value="1" checked>1 año o menos</option>
    		<option value="2"> años</option>
    		<option value="3"> años</option>
    		<option value="4"> años</option>
    		<option value="5"> años</option>
    		<option value="6"> años</option>
    		<option value="7"> años</option>
    		<option value="8"> años</option>
    		<option value="9"> años</option>
    		<option value="10"> años</option>
    		<option value="11"> años</option>
    		<option value="12"> años</option>
    		<option value="13"> años</option>
    		<option value="14"> años</option>
    		<option value="15"> años</option>
    		<option value="16"> años</option>
    		<option value="17"> años</option>
    		<option value="18"> años</option>
    		<option value="19"> años</option>
	    	<option value="20"> años</option>
	    	<option value="21">Más de 20 años</option>
			</select>
		</td>
	</tr>
	<tr>
	  <td>Categoría en el Escalafón Nacional Docente</td>
    <td>
    	<select name="escalafon" id="escalafon">
      	<option value="A">A</option>
        <option value="B">B</option>
        <option value="C">C</option>
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
        <option value="4">4</option>
        <option value="5">5</option>
        <option value="6">6</option>
        <option value="7">7</option>
        <option value="8">8</option>
        <option value="9">9</option>
        <option value="10">10</option>
        <option value="11">11</option>
        <option value="12">12</option>
        <option value="13">13</option>
        <option value="14">14</option>
			</select>
		</td>
	</tr>
	<c:if test="${!empty requestScope.filtroGradoF2}">
  <tr align="left" valign="baseline">
	  <td valign="top">Grados que imparte:</td>
    <td>
    	<table width="100%" border="0" bgcolor="#FFFFFF">
     		<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila">
      		<tr>
      			<td width="3%"><c:out value="${fila[1]}"/></td>
	          <td width="5%"><input type="checkbox" name="g<c:out value="${fila[1]}"/>" value="<c:out value="${fila[0]}"/>"></td>
          </tr>
				</c:forEach>
			</table>
		</td>
	</tr>		
	</c:if>
</table>

<br>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<caption>Información Académica</caption>
  <tr>
  	<td width="34%">Tiene título como Normalista Superior?</td>
	  <td width="66%"><input name="normalista" id="normalista" type="radio" value="1">Si<input name="normalista" id="normalista" type="radio" value="0">No </td>
	</tr>
  <tr>
  	<td>Tiene título como Bachiller Pedagógico?</td>
    <td><input name="bachiller" id="bachiller" type="radio" value="1">Si<input name="bachiller" id="bachiller" type="radio" value="0">No
	</tr>
  <tr>
  	<td>Tiene título como Técnico o Tecnólogo?</td>
    <td><input name="tecnico" id="tecnico" type="radio" value="1" onClick="habilitartitulotecnico()">Si<input name="tecnico" id="tecnico" type="radio" value="0" onClick="inhabilitartitulotecnico()">No 	  &nbsp&nbsp&nbsp Denominación del título:
	  <input name="titulotecnico" id="titulotecnico" type="text" size="40"></td>
	</tr>
</table>

<br>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td width="25%" rowspan="2">Título de Pregrado</td>
    <td width="14%">Licenciatura</td>
		<td colspan="2" width="61%"><input name="licenciatura" type="radio" value="1" > Si<input name="licenciatura" type="radio" value="2" > No &nbsp&nbsp&nbsp Cu&aacute;l?<select name="titulolicenciatura" id="titulolicenciatura" style='width:222px;'>
      	<option value="1">Ciencias Naturales y Educación Ambiental</option>
        <option value="2">Ciencias Sociales</option>
        <option value="3">Educación Artística</option>
        <option value="4">Educación Ética y en Valores Humanos</option>
        <option value="5">Educación Física, Recreación y Deportes</option>
        <option value="6">Educación Religiosa</option>
        <option value="7">Humanidades, Lengua Castellana e Idiomas Extranjeros</option>
        <option value="8">Matemáticas</option>
        <option value="9">Tecnología e informática</option>
        <option value="10">Educación Preescolar</option>
        <option value="11">Educación Básica Primaria</option>
        <option value="12">Educación Especial</option>
        <option value="13">Psicopedagogía</option>
        <option value="14">Adminstración Educativa</option>
      </select>
    </td>
	</tr>
  <tr>
    <td>Otra Licenciatura</td>
    <td colspan="2"><input name="otralicenciatura" type="radio" value="1" onClick="habilitarotralicenciatura()"> Si<input name="otralicenciatura" type="radio" value="2" onClick="inhabilitarotralicenciatura()" > No &nbsp&nbsp&nbsp Cu&aacute;l?		<input name="titulootralicenciatura" type="text" id="titulootralicenciatura" size='55'></td>
  </tr>
  <tr>
		<td>&nbsp</td>
  </tr>
  <tr>
  	<td width="25%" rowspan="3">Formación Especializada</td>
		<td>Maestría</td>
		<td><input name="maestria" type="radio" value="1" onClick="habilitar()" >Si<input name="maestria" type="radio" value="2" onClick="inhabilitar()" >No &nbsp&nbsp&nbsp Cu&aacute;l?		<input name="titulootramaestria" type="text" id="titulootramaestria" size='55'></td>
	</tr>
  <tr>
  	<td>Especialización</td>
    <td><input name="especializacion" type="radio" value="1" onClick="habilitar()" >Si<input name="especializacion" type="radio" value="2" onClick="inhabilitar()" >No &nbsp&nbsp&nbsp Cu&aacute;l?		<input name="titulootraespecializacion" type="text" id="titulootraespecializacion" size='55'></td>
	</tr>
	<tr>
  	<td>Doctorado</td>
    <td><input name="doctorado" type="radio" value="1" onClick="habilitar()" >Si<input name="doctorado" type="radio" value="2" onClick="inhabilitar()" >No &nbsp&nbsp&nbsp Cu&aacute;l?		<input name="titulootradoctorado" type="text" id="titulootradoctorado" size='55'></td>
  </tr>
  <tr>
		<td>&nbsp</td>
  </tr>
	<tr>
	  <td>Nivel en el cual trabaja</td>
	  <td>
	  	<select name="nivel" id="select5">
        <option value="1">Preescolar</option>
        <option value="2">Básica Primaria</option>
        <option value="3">Básica Secundaria</option>
        <option value="4">Media</option>
      </select>
    </td>
		<td colspan=2>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
	  <td>Area de Desempeño Actual</td>
		<td colspan=2>
			<select name="area" id="area">
      	<option value="1">Ciencias Naturales y Educación Ambiental</option>
        <option value="2">Ciencias Sociales, Historia, Geografía, Constitución Política y Democracia</option>
        <option value="3">Educación Artística</option>
        <option value="4">Educación Ética y en Valores Humanos</option>
        <option value="5">Educación Física, Recreación y Deportes</option>
        <option value="6">Educación Religiosa</option>
        <option value="7">Humanidades, Lengua Castellana e Idiomas Extranjeros</option>
        <option value="8">Matemáticas</option>
        <option value="9">Tecnología e informática</option>
        <option value="10">Educación Preescolar</option>
        <option value="11">Educación Básica Primaria</option>
        <option value="12">Educación Especial</option>
        <option value="13">Psicopedagogía</option>
      </select>
    </td>
	</tr>
  <tr>
  	<td>&nbsp;</td>
  </tr>
	<tr>
	  <td rowspan="3">Proyectos Pedagógicos en Desarrollo</td>
	  <td colspan=2>1. <input name="proyecto1" type="text" id="proyecto1" size='95'></td>
	</tr>
	<tr><td colspan=2>2. <input name="proyecto2" type="text" id="proyecto22" size='95'></td>
	</tr>
	<tr><td colspan=2>3. <input name="proyecto3" type="text" id="proyecto3" size='95'></td>
	</tr>
</table>
<br>
</form>
<script>
filtro(document.form1.sede, document.form1.jornada)
document.form1.sede.disabled=true;
document.form1.jornada.disabled=true;
</script>