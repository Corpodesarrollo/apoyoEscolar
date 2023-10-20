<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@page import="siges.util.Recursos"%>
<%pageContext.setAttribute("tipoReceso",Recursos.recurso[Recursos.TIPORECESO]);%>
<html><head><title>NUEVO RECESO</title><%@include file="../parametros.jsp"%>
<script languaje='javaScript'>
<!--
var locked=<c:out value="${sessionScope.isLockedRotacion}"/>;
	var nav4=window.Event ? true : false;
	
	function ponerNombre(combo, campo){
		campo.value=combo.options[combo.selectedIndex].text;
	}

	function partir(campo,campo2,campo3){
		var aux=campo.value;
		campo2.value=aux.substring(0,aux.indexOf(":"));
		campo3.value=aux.substring(aux.indexOf(":")+1,aux.length);
	}
	
	function unir(campo,campo2,campo3){
		campo.value=campo2.value+":"+campo3.value;
	}
	
	function combohora(combo,campo2){
		var aux;
		<c:forEach begin="0" end="17" step="1" var="fila" varStatus="st">
			aux='<c:out value="${fila+6}"/>';
			if(parseInt(aux)==parseInt(campo2.value)){
				combo.options[<c:out value="${st.index}"/>] = new Option('<c:out value="${fila+6}"/>','<c:out value="${fila+6}"/>',"SELECTED");
				combo.selectedIndex = '<c:out value="${st.index}"/>';
			}
			else{
				combo.options[<c:out value="${st.index}"/>] = new Option('<c:out value="${fila+6}"/>','<c:out value="${fila+6}"/>');
			}
		</c:forEach>
	}
	
	function combomin(combo,campo2){
		var aux;
		<c:forEach begin="0" end="59" step="1" var="fila" varStatus="st">
			aux='<c:out value="${fila}"/>';
			if(parseInt(aux)==parseInt(campo2.value)){
				<c:if test="${fila<10}">
					combo.options[<c:out value="${st.index}"/>] = new Option('<c:out value="0"/><c:out value="${fila}"/>','<c:out value="0"/><c:out value="${fila}"/>',"SELECTED");
				</c:if>
				<c:if test="${fila>=10}">
					combo.options[<c:out value="${st.index}"/>] = new Option('<c:out value="${fila}"/>','<c:out value="${fila}"/>',"SELECTED");
				</c:if>
				combo.selectedIndex = '<c:out value="${st.index}"/>';
			}
			else{
				<c:if test="${fila<10}">
					combo.options[<c:out value="${st.index}"/>] = new Option('<c:out value="0"/><c:out value="${fila}"/>','<c:out value="0"/><c:out value="${fila}"/>');
				</c:if>
				<c:if test="${fila>=10}">
					combo.options[<c:out value="${st.index}"/>] = new Option('<c:out value="${fila}"/>','<c:out value="${fila}"/>');
				</c:if>
			}
		</c:forEach>
	}
	
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("--Seleccione uno--","-1");
	}
	
	function llenarCombo(combo){
		borrar_combo(combo)
		<c:forEach begin="0" items="${tipoReceso}" var="fila" varStatus="st">
			var tr='<c:out value="${fila[0]}"/>';
			if(tr==opener.document.fr.rotrec_tiprec.value){
				combo.options[<c:out value="${st.index+1}"/>] = new Option('<c:out value="${fila[1]}"/>', '<c:out value="${fila[0]}"/>',"SELECTED");
				combo.selectedIndex = <c:out value="${st.index+1}"/>;
			}else	combo.options[<c:out value="${st.index+1}"/>] = new Option('<c:out value="${fila[1]}"/>','<c:out value="${fila[0]}"/>');
		</c:forEach>
	}
	
	function cargarValores(){
		document.frmReceso.descripcion.value=opener.document.fr.rotrecdescripcion.value;
		document.frmReceso.hini.value=opener.document.fr.rotrechoraini.value;
		document.frmReceso.hfin.value=opener.document.fr.rotrechorafin.value;
		document.frmReceso.totreceso.value=opener.document.fr.totreceso.value;
	}
	
	function validarRango(inih,inim,finh,finm){
		var ih=parseInt(inih.value);
		var im=parseInt(inim.value);
		var fh=parseInt(finh.value);
		var fm=parseInt(finm.value);
		if(ih==fh){
			if(im<fm){
				return true;
			}
			else if(im>=fm){
				alert("La hora final es mayor a la hora inicial")
				return false;
			}
		}
		else if(ih<fh){
			return true;
		}
		else{
			alert("La hora final es mayor a la hora inicial")
			return false;
		}
	}

	function aceptar(){
		if(opener.document.frmNuevo.rec){
			if(opener.document.frmNuevo.rec.length){
				if(opener.document.frmNuevo.lon.value < opener.document.frmNuevo.rec.length){
					opener.document.frmNuevo.lon.value=opener.document.frmNuevo.rec.length;
				}
			}
			else{
				opener.document.frmNuevo.lon.value=1;
			}
		}
		else opener.document.frmNuevo.lon.value=0;
		unir(document.frmReceso.hini,document.frmReceso.jorinihor,document.frmReceso.jorinimin);
		unir(document.frmReceso.hfin,document.frmReceso.jorfinhor,document.frmReceso.jorfinmin);
		if(validarForma(document.frmReceso)){
			if(validarRango(document.frmReceso.jorinihor,document.frmReceso.jorinimin,document.frmReceso.jorfinhor,document.frmReceso.jorfinmin)){
				var aux=parseInt(opener.document.frmNuevo.lon.value);
				var aux2=parseInt(opener.document.frmNuevo.cont.value);
				if(opener.document.fr.editar.value==1){
					opener.document.frmNuevo.rotrec_tiprec[aux2].value=document.frmReceso.treceso.options[document.frmReceso.treceso.selectedIndex].value;
					opener.document.frmNuevo.rotrecdescripcion[aux2].value=document.frmReceso.descripcion.value;
					opener.document.frmNuevo.rotrechoraini[aux2].value=document.frmReceso.hini.value;
					opener.document.frmNuevo.rotrechorafin[aux2].value=document.frmReceso.hfin.value;
					opener.document.frmNuevo.nombre[aux2].value=document.frmReceso.treceso.options[document.frmReceso.treceso.selectedIndex].text;
					opener.document.frmNuevo.totreceso[aux2].value=(parseInt(document.frmReceso.jorfinmin.value)+(parseInt(document.frmReceso.jorfinhor.value)*60))-(parseInt(document.frmReceso.jorinimin.value)+(parseInt(document.frmReceso.jorinihor.value)*60));
					//opener.document.frmNuevo.valor[aux2].value=aux;
				}
				else{
					opener.document.frmNuevo.rotrec_tiprec[aux].value=document.frmReceso.treceso.options[document.frmReceso.treceso.selectedIndex].value;
					opener.document.frmNuevo.rotrecdescripcion[aux].value=document.frmReceso.descripcion.value;
					opener.document.frmNuevo.rotrechoraini[aux].value=document.frmReceso.hini.value;
					opener.document.frmNuevo.rotrechorafin[aux].value=document.frmReceso.hfin.value;
					opener.document.frmNuevo.nombre[aux].value=document.frmReceso.treceso.options[document.frmReceso.treceso.selectedIndex].text;
					opener.document.frmNuevo.totreceso[aux].value=(parseInt(document.frmReceso.jorfinmin.value)+(parseInt(document.frmReceso.jorfinhor.value)*60))-(parseInt(document.frmReceso.jorinimin.value)+(parseInt(document.frmReceso.jorinihor.value)*60));
					opener.document.frmNuevo.valor[aux].value=aux;
				}
				crearTabla();
				opener.document.fr.editar.value=0;
				parent.close();
			}
		}
	}
	
	function crearTabla(){
		var x;
		if(opener.document.frmNuevo.rec){
			if(opener.document.frmNuevo.rec.length){
				x=opener.document.frmNuevo.rec.length;
			}
			else{
				x=1;
			}
		}
		else{
			x=0;
		}
		
		for(var j=0;j<x;j++){
			opener.document.getElementById("tabla1").deleteRow(0);
		}
		for(var i=0;i<opener.document.frmNuevo.valor.length;i++){
			if(opener.document.frmNuevo.valor[i].value!="-1"){
				var newFila=opener.document.getElementById("tabla1").insertRow(-1);
				var newCelda=newFila.insertCell(0);
				newCelda.innerHTML="<input type='radio' name='rec' value='"+opener.document.frmNuevo.valor[i].value+"'>";
				var newCelda=newFila.insertCell(1);
				newCelda.innerHTML=opener.document.frmNuevo.nombre[i].value;
				var newCelda=newFila.insertCell(2);
				newCelda.innerHTML=opener.document.frmNuevo.rotrecdescripcion[i].value;
				var newCelda=newFila.insertCell(3);
				newCelda.innerHTML=opener.document.frmNuevo.rotrechoraini[i].value;
				var newCelda=newFila.insertCell(4);
				newCelda.innerHTML=opener.document.frmNuevo.rotrechorafin[i].value;
				//var newCelda=newFila.insertCell(5);
				//newCelda.innerHTML=opener.document.frmNuevo.totreceso[i].value;
				//var newCelda=newFila.insertCell(6);
				//newCelda.innerHTML=opener.document.frmNuevo.valor[i].value;
			}
		}
	}

	function hacerValidaciones_frmReceso(forma){
		validarLista(forma.treceso, "- Seleccione un tipo de receso",1)
		validarCampo(forma.descripcion, "- Nombre del receso", 1, 80)
		validarCampo(forma.hini, "- Hora inicio de jornada", 1, 5)
		validarCampo(forma.hfin, "- Hora fin de jornada", 1, 5)
	}

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}

	function cancelar(){
  	parent.close();
	}
//-->
</script>
</head>
<body onLoad=''>
	<FORM ACTION="" METHOD="POST" name="frmReceso">
	<input type="hidden" name="hini" value="">
	<input type="hidden" name="hfin" value="">
	<input type="hidden" name="auxjorinihor" value="">
	<input type="hidden" name="auxjorinimin" value="">
	<input type="hidden" name="auxjorfinhor" value="">
	<input type="hidden" name="auxjorfinmin" value="">
	<input type="hidden" name="totreceso" value="">
		<table border="0" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
			<caption>Nuevo Receso</caption>
			<tr>
				<td>
					<INPUT TYPE="button" NAME="cmd" class="boton" VALUE="Aceptar" onClick="return aceptar()">
					<INPUT TYPE="button" NAME="cmd2" class="boton" VALUE="Cancelar" onClick="cancelar()">
				</td>
			</tr>
			<tr><td><br></td></tr>
		</table>
		<table border="0" align="center" width="100%" cellpadding="1" cellspacing="0" >
			<tr>
				<td>Tipo Receso</td>
				<td colspan="3">
					<select name="treceso" onchange="ponerNombre(document.frmReceso.treceso, document.frmReceso.descripcion)">
						<option value='-1'>-- Seleccione uno --</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>Nombre :</td>
				<td colspan="3"><input type="text" size="40" name="descripcion" maxlength="80"></td>
			</tr>
			<tr>
				<td>Hora Inicial :</td>
				<td><select name="jorinihor"></select>&nbsp;:&nbsp;<select name="jorinimin"></select></td>
				<td>Hora Final :</td>
				<td><select name="jorfinhor"></select>&nbsp;:&nbsp;<select name="jorfinmin"></select></td>
			</tr>
		</table>
	</FORM>
</body>
</html>

<script>
partir(opener.document.fr.rotrechoraini,document.frmReceso.auxjorinihor,document.frmReceso.auxjorinimin);
partir(opener.document.fr.rotrechorafin,document.frmReceso.auxjorfinhor,document.frmReceso.auxjorfinmin);
llenarCombo(document.frmReceso.treceso)
combohora(document.frmReceso.jorinihor,document.frmReceso.auxjorinihor)
combomin(document.frmReceso.jorinimin,document.frmReceso.auxjorinimin)
combohora(document.frmReceso.jorfinhor,document.frmReceso.auxjorfinhor)
combomin(document.frmReceso.jorfinmin,document.frmReceso.auxjorfinmin)
if(opener.document.fr.editar.value==1)	cargarValores()
else document.frmReceso.descripcion.value="";
</script>