<%@ page language="java" errorPage=""%>
<%@page import="siges.util.Recursos"%>
<%pageContext.setAttribute("tipoReceso",Recursos.recurso[Recursos.TIPORECESO]);%>
<%@include file="../parametros.jsp"%>
<jsp:useBean id="rotacion" class="siges.rotacion.beans.Rotacion" scope="session"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor='#ffffff'>
	<tr><td width="100%" valign='top'>
		<div style="width:100%;height:250px;overflow:auto;vertical-align:top;background-color:#ffffff;"><c:import url="rotacion/ControllerFiltro.do?tipo=10"/></div>
	</td></tr>
</table>
<script languaje="javaScript">
<!--
	var locked=<c:out value="${sessionScope.isLockedRotacion}"/>;
	function ver(){
		var el = document.getElementById("out");
		el.innerHTML = "";
		for(var i=0;i<document.frmNuevo.rotrec_tiprec.length;i++){
			el.innerHTML += "<br>para "+i+": "+document.frmNuevo.rotrec_tiprec[i].value+" - "+document.frmNuevo.rotrecdescripcion[i].value+" - "+document.frmNuevo.rotrechoraini[i].value+" - "+document.frmNuevo.rotrechorafin[i].value+" - "+document.frmNuevo.totreceso[i].value+"<br>";
		}
	}
	
	function partir(campo,campo2,campo3){
		var aux=campo.value;
		campo2.value=aux.substring(0,aux.indexOf(":"));
		campo3.value=aux.substring(aux.indexOf(":")+1,aux.length);
	}
	
	function unir(campo,campo2,campo3){
		campo.value=campo2.value+":"+campo3.value;
	}
	
	function quitarReceso(form){
		if(form.rec){
			if(validarSeleccion(form.rec, "- Debe seleccionar un item")){
				if(form.rec.length){
					for(var i=0;i<form.rec.length;i++){
						if(form.rec[i].checked)	break;
					}
					var num=form.rec[i].value;
				}
				else if(form.rec.checked){
					var num=form.rec.value;
				}
				else	return;
				if(confirm('¿DESEA QUITAR ESTE ELEMENTO?')){
					document.frmNuevo.rotrec_tiprec[num].value="-1";
					document.frmNuevo.rotrecdescripcion[num].value="-1";
					document.frmNuevo.rotrechoraini[num].value="-1";
					document.frmNuevo.rotrechorafin[num].value="-1";
					document.frmNuevo.nombre[num].value="-1";
					document.frmNuevo.totreceso[num].value="-1";
					document.frmNuevo.valor[num].value="-1";
					crearTabla();
				}
			}
		}
	}
	
	function crearTabla(){
		var x;
		if(document.frmNuevo.rec){
			if(document.frmNuevo.rec.length){
				x=document.frmNuevo.rec.length;
				//alert("+ de uno: "+x);
			}
			else{
				x=1;
				//alert("solo uno: "+x);
			}
		}
		else{
			x=0;
			//alert("vacio: "+x);
		}
		for(var j=0;j<x;j++){
			//alert("borrando: "+j+" de "+x);
			document.getElementById("tabla1").deleteRow(0);
		}
		for(var i=0;i<document.frmNuevo.valor.length;i++){
			if(document.frmNuevo.valor[i].value!="-1"){
				var newFila=document.getElementById("tabla1").insertRow(-1);
				var newCelda=newFila.insertCell(0);
				newCelda.innerHTML="<input type='radio' name='rec' value='"+document.frmNuevo.valor[i].value+"'>";
				var newCelda=newFila.insertCell(1);
				newCelda.innerHTML=document.frmNuevo.nombre[i].value;
				var newCelda=newFila.insertCell(2);
				newCelda.innerHTML=document.frmNuevo.rotrecdescripcion[i].value;
				var newCelda=newFila.insertCell(3);
				newCelda.innerHTML=document.frmNuevo.rotrechoraini[i].value;
				var newCelda=newFila.insertCell(4);
				newCelda.innerHTML=document.frmNuevo.rotrechorafin[i].value;
				//var newCelda=newFila.insertCell(5);
				//newCelda.innerHTML=document.frmNuevo.valor[i].value;
			}
		}
	}
	
	function editarReceso(form){
		if(form.rec){
			if(validarSeleccion(form.rec, "- Debe seleccionar un item")){
				if(form.rec.length){
					for(var i=0;i<form.rec.length;i++){
						if(form.rec[i].checked)	break;
					}
					var num=form.rec[i].value;
				}
				else if(form.rec.checked){
					var num=form.rec.value;
				}
				else	return;
				//alert("num: "+num);
				document.frmNuevo.cont.value=num;
				document.fr.rotrec_tiprec.value=document.frmNuevo.rotrec_tiprec[num].value;
				document.fr.rotrecdescripcion.value=document.frmNuevo.rotrecdescripcion[num].value;
				document.fr.rotrechoraini.value=document.frmNuevo.rotrechoraini[num].value;
				document.fr.rotrechorafin.value=document.frmNuevo.rotrechorafin[num].value;
				document.fr.totreceso.value=document.frmNuevo.totreceso[num].value;
				document.fr.editar.value=1;
				remoto()
			}
		}
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
	
	function nuevoReceso(){
		document.fr.editar.value=0;
		document.fr.rotrec_tiprec.value="";
		document.fr.rotrecdescripcion.value="";
		document.fr.rotrechoraini.value="";
		document.fr.rotrechorafin.value="";
		document.fr.totreceso.value="";
		remoto();
	}
	
	function remoto(){
		remote = window.open("","3","width=450,height=150,resizable=no,toolbar=no,directories=no,menubar=no,status=yes");
		document.fr.target='3';
		remote.moveTo(200,200);
		document.fr.submit();
		if (remote.opener == null) remote.opener = window;
		remote.opener.name = "centro";
		remote.focus();
	}

	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-- Seleccione uno --","-1");
	}
	
	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
	}

	var nav4=window.Event ? true : false;

	function cancelar(){
		location.href='<c:url value="/bienvenida.jsp"/>';
	}

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}

	function nuevo(){
		document.frmNuevo.tipo.value=16;
		document.frmNuevo.cmd.value="Nuevo";
		document.frmNuevo.submit();
	}

	function guardar(){
		unir(document.frmNuevo.rotstrhoraini,document.frmNuevo.jorinihor,document.frmNuevo.jorinimin);
		unir(document.frmNuevo.rotstrhorafin,document.frmNuevo.jorfinhor,document.frmNuevo.jorfinmin);
		//mirar();
		if(validarForma(document.frmNuevo)){
			if(validarRango(document.frmNuevo.jorinihor,document.frmNuevo.jorinimin,document.frmNuevo.jorfinhor,document.frmNuevo.jorfinmin)){
				if(validarCongruencia(document.frmNuevo.jorinihor,document.frmNuevo.jorinimin,document.frmNuevo.jorfinhor,document.frmNuevo.jorfinmin,document.frmNuevo.rotstrdurblq,document.frmNuevo.rotstrnumblq,document.frmNuevo.rotstrdurhor)){
					document.frmNuevo.tipo.value=11;
					document.frmNuevo.cmd.value="Guardar";
					document.frmNuevo.submit();
				}
			}
		}	
	}
	
	function validarCongruencia(inih,inim,finh,finm,durb,numb,durh){
		var totrec=0;
		for(var i=0;i<document.frmNuevo.totreceso.length;i++){
			if(document.frmNuevo.totreceso[i].value!=-1)	totrec+=parseInt(document.frmNuevo.totreceso[i].value);
		}
		var ih=parseInt(inih.value);
		var im=parseInt(inim.value);
		var fh=parseInt(finh.value);
		var fm=parseInt(finm.value);
		var db=parseInt(durb.value);
		var nb=parseInt(numb.value);
		var dh=parseInt(durh.value);
		var totminfin=parseInt((fh*60)+fm);
		var totminini=parseInt((ih*60)+im);
		var tothoras=parseFloat((totminfin-totminini-totrec)/dh);
		var tothoras2=parseFloat(db*nb);
		if(tothoras==tothoras2){
			document.frmNuevo.rotstrihtotal.value=tothoras;
			return true;
		}
		else{
			alert("El número de horas entre la hora de inicio y la hora de fin de jornada fijado no corresponde con el número horas respecto a la cantidad de bloques por jornada")
			return false;
		}
	}
	
	function mirar(){
		for(var i=0;i<20;i++){
			if(document.frmNuevo.rotrecdescripcion[i].value!="-1"){
				//alert(i+" _valor: "+document.frmNuevo.rotrecdescripcion[i].value)
			}
		}
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

	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.rotstrsede, "- Seleccione una sede",1)
		validarLista(forma.rotstrjornada, "- Seleccione una jornada",1)
		validarLista(forma.rtesgrgrado, "- Debe seleccionar al menos un grado",0)
		validarCampo(forma.rotstrnombre, "- Nombre de la estructura", 1, 40)
		validarCampo(forma.rotstrhoraini, "- Hora inicio de jornada", 1, 5)
		validarCampo(forma.rotstrhorafin, "- Hora fin de jornada", 1, 5)
		validarLista(forma.rotstrdurblq, "- Duración del bloque", 1)
		validarLista(forma.rotstrdurhor, "- Duración de la hora", 1)
		validarLista(forma.rotstrnumblq, "- Bloques por jornada", 1)
		validarLista(forma.rotstrsemcic, "- Semanas por ciclo", 1)
	}

	function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
		this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
	}

	function filtro(combo_padre,combo_hijo,combo_hijo2,combo_hijo3){
		borrar_combo(combo_hijo); borrar_combo2(combo_hijo2); 
		<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">
			var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">
				id_Hijos=new Array();
				Hijos= new Array();
				Sel_Hijos= new Array();
				id_Padre= new Array();
				var k=0;
				<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2">
					<c:if test="${fila[0]==fila2[2]}">
						Sel_Hijos[k] = '<c:if test="${sessionScope.rotacion.rotstrjornada == fila2[0]}">SELECTED</c:if>';
						id_Hijos[k] = '<c:out value="${fila2[0]}"/>';
						Hijos[k] = '<c:out value="${fila2[1]}"/>';
						id_Padre[k] = '<c:out value="${fila2[2]}"/>';
						k++;
					</c:if>
				</c:forEach>
				Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
			</c:forEach>
			var niv=combo_padre.options[combo_padre.selectedIndex].value;
			var val_padre = -1;
			for(var k=0;k<Padres.length;k++){
				if(Padres[k].id_Padre[0]==niv) val_padre=k;							
			}
			if(val_padre!=-1){
				var no_hijos = Padres[val_padre].Hijos.length;
				for(i=0; i < no_hijos; i++){
					if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
						combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED");
						combo_hijo.selectedIndex = i+1;
					}else	combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
				}
			}
		</c:if>
	}

	function filtro2(combo_padre,combo_padre2,combo_padre3,combo_hijo){
		var id=0;
		var vec=new Array();
		var au=0;
		borrar_combo2(combo_hijo);
		<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2}">
			var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2" varStatus="st">
				<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila4" varStatus="st2">
					id_Hijos=new Array();
					Hijos= new Array();
					Sel_Hijos= new Array();
					id_Padre= new Array();
					var k=0;
					<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3">
						<c:if test="${fila2[2]==fila3[3] && fila2[0]==fila3[4] && fila3[5]==fila4[0]}">
							Sel_Hijos[k] = '<c:forEach begin="0" items="${sessionScope.rotacion.rtesgrgrado}" var="filax"><c:if test="${filax == fila3[0]}">SELECTED</c:if></c:forEach>';
							id_Hijos[k] = '<c:out value="${fila3[0]}"/>';
							Hijos[k] = '<c:out value="${fila3[1]}"/>';
							id_Padre[k] = '<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>';
							k++;
						</c:if>
					</c:forEach>
					Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
				</c:forEach>
			</c:forEach>
			var niv=combo_padre.options[combo_padre.selectedIndex].value+'|'+combo_padre2.options[combo_padre2.selectedIndex].value+'|'+combo_padre3.value;
			var val_padre = -1;
			for(var k=0;k<Padres.length;k++){
				if(Padres[k].id_Padre[0]==niv) val_padre=k;
			}
			if(val_padre!=-1){ var no_hijos = Padres[val_padre].Hijos.length;
				for(i=0; i < no_hijos; i++){
					if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
						vec[au]=i;au+=1;
						combo_hijo.options[i] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); 
						combo_hijo.selectedIndex = i;
					}else	combo_hijo.options[i] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
				}
			}
			if(combo_hijo.length && vec.length){
				for(var j=0;j<combo_hijo.length;j++){
					for(var k=0;k<vec.length;k++){
						if(j==vec[k]){
							combo_hijo.options[j].selected=true;
						}
					}
				}
			}
		</c:if>
	}
	
	function filtro3(combo_padre,combo_padre2,combo_padre3,combo_hijo){
		var id=0;
		var vec=new Array();
		var au=0;
		var sede_=combo_padre.value;
		var jornada_=combo_padre2.value;
		borrar_combo2(combo_hijo);
		<c:if test="${!empty requestScope.filtroGradoF2}">
			var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila4" varStatus="st2">
				id_Hijos=new Array();
				Hijos= new Array();
				Sel_Hijos= new Array();
				id_Padre= new Array();
				var k=0;
				var metod_='<c:out value="${fila4[0]}"/>';
				<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3">
					var _sede='<c:out value="${fila3[3]}"/>';
					var _jornada='<c:out value="${fila3[4]}"/>';
					var _metod='<c:out value="${fila3[5]}"/>';
					if(sede_==_sede && jornada_==_jornada && metod_==_metod){
						Sel_Hijos[k] = '<c:forEach begin="0" items="${sessionScope.rotacion.rtesgrgrado}" var="filax"><c:if test="${filax == fila3[0]}">SELECTED</c:if></c:forEach>';
						id_Hijos[k] = '<c:out value="${fila3[0]}"/>';
						Hijos[k] = '<c:out value="${fila3[1]}"/>';
						id_Padre[k] = '<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>';
						k++;
					}
				</c:forEach>
				Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
			</c:forEach>
			var niv=combo_padre.value+'|'+combo_padre2.value+'|'+combo_padre3.value;
			var val_padre = -1;
			for(var k=0;k<Padres.length;k++){
				if(Padres[k].id_Padre[0]==niv) val_padre=k;
			}
			if(val_padre!=-1){ var no_hijos = Padres[val_padre].Hijos.length;
				for(i=0; i < no_hijos; i++){
					if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
						vec[au]=i;au+=1;
						combo_hijo.options[i] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); 
						combo_hijo.selectedIndex = i;
					}else	combo_hijo.options[i] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
				}
			}
			if(combo_hijo.length && vec.length){
				for(var j=0;j<combo_hijo.length;j++){
					for(var k=0;k<vec.length;k++){
						if(j==vec[k]){
							combo_hijo.options[j].selected=true;
						}
					}
				}
			}
		</c:if>
	}

//-->
</script>
<%@include file="../mensaje.jsp" %>
	<font size="1">
		<FORM ACTION='<c:url value="/rotacion/ListaReceso.jsp"/>?ext=1' METHOD="POST" name='fr'>
			<input type="hidden" name="rotrec_tiprec" value="-1">
			<input type="hidden" name="rotrecdescripcion" value="-1">
			<input type="hidden" name="rotrechoraini" value="-1">
			<input type="hidden" name="rotrechorafin" value="-1">
			<input type="hidden" name="totreceso" value="-1">
			<input type="hidden" name="editar" value="-1">
		</form>
		<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action="<c:url value="/rotacion/NuevoGuardar.jsp"/>">
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="height" VALUE="180">
			<INPUT TYPE="hidden" NAME="tipo" VALUE="">
			<input type="hidden" name="ext" value="">
			<input type="hidden" name="cont" value="0">
			<input type="hidden" name="lon" value="0">
			<input type="hidden" name="rotstrmetodologia" value='<c:out value="${sessionScope.filtroRotacion.filMetodologia}"/>'>
			<input type="hidden" name="rotstrcodigo" value="<c:out value="${sessionScope.rotacion.rotstrcodigo}"/>">
			<input type="hidden" name="rotstrhoraini" value="<c:out value="${sessionScope.rotacion.rotstrhoraini}"/>">
			<input type="hidden" name="rotstrhorafin" value="<c:out value="${sessionScope.rotacion.rotstrhorafin}"/>">
			<input type="hidden" name="rotstrihtotal" value="">
			<input type="hidden" name="auxjorinihor" value="">
			<input type="hidden" name="auxjorinimin" value="">
			<input type="hidden" name="auxjorfinhor" value="">
			<input type="hidden" name="auxjorfinmin" value="">
			<input type="hidden" name="aux1" value="">
			<input type="hidden" name="aux2" value="">
			<c:forEach begin="0" end="20" step="1" varStatus="st">
				<input type="hidden" name="rotrec_tiprec" value="-1">
				<input type="hidden" name="rotrecdescripcion" value="-1">
				<input type="hidden" name="rotrechoraini" value="-1">
				<input type="hidden" name="rotrechorafin" value="-1">
				<input type="hidden" name="totreceso" value="-1">
				<input type="hidden" name="nombre" value="-1">
				<input type="hidden" name="valor" value="-1">
			</c:forEach>
			<c:if test="${sessionScope.login.sedeId!='' && sessionScope.login.jornadaId!=''}">
				<input type="hidden" name="rotstrsede" value="<c:out value="${sessionScope.login.sedeId}"/>">
				<input type="hidden" name="rotstrjornada" value="<c:out value="${sessionScope.login.jornadaId}"/>">
			</c:if>

			<table align="center" width="100%">
				<tr>
				  <td width="45%" align="left">
<script>
				if(locked==0){
					  document.write('<input name="cmd2" class="boton" type="button" value="Guardar" onClick="guardar()&nbsp;">');
					  document.write('<input name="cmd1" class="boton" type="button" value="Nuevo" onClick="nuevo()">');
				}
</script>
					  <c:if test="${sessionScope.isLockedRotacion==1}">
						  <span class="style2">Nota: Información de solo lectura. Proceso de rotación pendiente por aprobar o rechazar</span>
					  </c:if>
					</td>
				</tr>
  	  </table>

  	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
					<c:if test="${sessionScope.rotacion.estado!=1}"><caption>NUEVO REGISTRO DE ESTRUCTURA DE HORARIOS</caption></c:if>
					<c:if test="${sessionScope.rotacion.estado==1}"><caption>EDITAR REGISTRO DE ESTRUCTURA DE HORARIOS</caption></c:if>
				<c:if test="${sessionScope.login.sedeId!='' && sessionScope.login.jornadaId!=''}">
					<tr>
						<td colspan="1">Sede: </td><td colspan="1"><font style='COLOR:#006699;'><c:out value="${sessionScope.login.sede}"/></font></td>
						<td colspan="1">Jornada: </td><td colspan="1"><font style='COLOR:#006699;'><c:out value="${sessionScope.login.jornada}"/></font></td>
					</tr>
				</c:if>
				<c:if test="${sessionScope.login.sedeId=='' && sessionScope.login.jornadaId==''}">
					<tr>
						<td><span class="style2" >*</span>Sede:</td>
				    <td colspan="1">
							<select name="rotstrsede" onChange='filtro(document.frmNuevo.rotstrsede, document.frmNuevo.rotstrjornada,document.frmNuevo.rtesgrgrado,document.frmNuevo.rotstrmetodologia)' style='width:300px;'>
								<option value='-1'>-- Seleccione uno --</option>
								<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
									<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.rotacion.rotstrsede == fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option></c:forEach>
							</select>
				   </td>
						<td><span class="style2" >*</span>Jornada:</td>
					  <td>
							<select name="rotstrjornada" style='width:150px;' onChange='filtro2(document.frmNuevo.rotstrsede, document.frmNuevo.rotstrjornada,document.frmNuevo.rotstrmetodologia,document.frmNuevo.rtesgrgrado)'>
								<option value='-1'>-- Seleccione uno --</option>
							</select>
					  </td>
				</tr>
				</c:if>
				<tr>
						<td><span class="style2">*</span>Nombre Estructura:</td>
						<td colspan="2"><input type="text" size="30" name="rotstrnombre" maxlength="50" <c:if test="${sessionScope.rotacion.estado==1}">value='<c:out value="${sessionScope.rotacion.rotstrnombre}"/>'</c:if>></td>
						<td><span class="style2">*</span> Vigencia: &nbsp;&nbsp;&nbsp;<c:out value="${sessionScope.filtroRotacion.filAnhoVigencia}"/>
						<input type="hidden" name="vigencia" value='<c:out value="${sessionScope.filtroRotacion.filAnhoVigencia}"/>'>
						</td>
				</tr>
				<tr>
					<td rowspan='5'><span class="style2" >*</span>Grado:</td>
				  <td  rowspan='5'><select name="rtesgrgrado" style='width:150px;' multiple size='7'></select></td>
				</tr>
				<tr>
					<td colspan="1"><span class="style2">*</span>Nº de semanas por ciclo:</td>
					<td>
						<select name="rotstrsemcic" style='width:30px;'><option value='-1'>--</option>
							<c:forEach begin="1" end="4" var="fila">
								<option value="<c:out value="${fila}"/>" <c:if test="${sessionScope.rotacion.rotstrsemcic == fila}">SELECTED</c:if>><c:out value="${fila}"/></option></c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="1"><span class="style2">*</span>Duración de una hora:</td>
					<td><select name="rotstrdurhor" style='width:50px;'><option value='-1'>--</option>
							<c:forEach begin="15" end="60" var="fila">
								<option value="<c:out value="${fila}"/>" <c:if test="${sessionScope.rotacion.rotstrdurhor == fila}">SELECTED</c:if>><c:out value="${fila}"/></option>
							</c:forEach>
						</select>&nbsp;&nbsp;(en minutos)</td>
				</tr>
				<tr>
					<td><span class="style2">*</span>Duraci&oacute;n de un bloque:</td>
					<td>
						<select name="rotstrdurblq" style='width:50px;'><option value='-1'>--</option>
							<c:forEach begin="1" end="3" var="fila">
								<option value="<c:out value="${fila}"/>" <c:if test="${sessionScope.rotacion.rotstrdurblq == fila}">SELECTED</c:if>><c:out value="${fila}"/></option></c:forEach>
						</select>&nbsp;&nbsp;(en horas)
					</td>
				</tr>
				<tr>
					<td colspan="1"><span class="style2">*</span>Nº de bloques por Jornada:</td>
					<td>
						<select name="rotstrnumblq" style='width:50px;'><option value='-1'>--</option>
							<c:forEach begin="1" end="12" var="fila">
								<option value="<c:out value="${fila}"/>" <c:if test="${sessionScope.rotacion.rotstrnumblq == fila}">SELECTED</c:if>><c:out value="${fila}"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td><span class="style2">*</span>Hora Inicio de Jornada:</td>
					<td><select name="jorinihor"></select>&nbsp;:&nbsp;<select name="jorinimin"></select></td>
					<td  colspan="1"><span class="style2">*</span>Hora Final de Jornada:</td>
					<td><select name="jorfinhor"></select>&nbsp;:&nbsp;<select name="jorfinmin"></select></td>
				</tr>
  	  </table>
  	  <br>
  	  <br>
  	  <br>
  	  <table border="0" align="center" width="100%">
	  	  <tr>
					<td align="left" >
						<input name="cmd1" class="boton" type="button" value="Nuevo Receso" onClick="nuevoReceso()">
					  <input name="cmd2" class="boton" type="button" value="Editar Receso" onClick="editarReceso(document.frmNuevo)">
					  <input name="cmd12" class="boton" type="button" value="Quitar Receso" onClick="quitarReceso(document.frmNuevo)">
					</td>
				</tr>
			</table>
			<table border="0" align="center" width="95%" cellpadding="0" cellspacing="0">
			<caption>RECESOS PARA LA ESTRUCTURA ACTUAL</caption>
				<tr>
					<td align="center" colspan="3">
						<table border="0" width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<th class="EncabezadoColumna">&nbsp;</th>
								<th class="EncabezadoColumna">Tipo de Receso</th>
								<th class="EncabezadoColumna">Nombre</th>
								<th class="EncabezadoColumna">Hora Inicial</th>
								<th class="EncabezadoColumna">Hora Final</th>
							</tr>
						</table>
						<table id="tabla1" border="0" width="90%" cellpadding="0" cellspacing="0">
						</table>
					</td>
				</tr>
  	  </table>
 	  </form>
  </font>
	<div id="out"></div>
	<script>
		<c:if test="${sessionScope.rotacion.estado==1}">
			var nombreTR,ax1,ax2,ax3,ax4;
			partir(document.frmNuevo.rotstrhoraini,document.frmNuevo.auxjorinihor,document.frmNuevo.auxjorinimin);
			partir(document.frmNuevo.rotstrhorafin,document.frmNuevo.auxjorfinhor,document.frmNuevo.auxjorfinmin);
			<c:if test="${sessionScope.login.sedeId=='' && sessionScope.login.jornadaId==''}">
			  filtro(document.frmNuevo.rotstrsede, document.frmNuevo.rotstrjornada,document.frmNuevo.rtesgrgrado,document.frmNuevo.rotstrmetodologia);
			  filtro2(document.frmNuevo.rotstrsede, document.frmNuevo.rotstrjornada,document.frmNuevo.rotstrmetodologia,document.frmNuevo.rtesgrgrado);
		  </c:if>
		  <c:forEach items="${sessionScope.rotacion.rotrechoraini}" var="fila" varStatus="st">
		  	<c:forEach begin="0" items="${tipoReceso}" var="fila2">
		  		<c:if test="${sessionScope.rotacion.rotrec_tiprec[st.index]==fila2[0]}">
		  			nombreTR='<c:out value="${fila2[1]}"/>';
		  		</c:if>
		  	</c:forEach>
		  	var x='<c:out value="${st.index}"/>';
	  		var newFila=document.getElementById("tabla1").insertRow(-1);
				var newCelda=newFila.insertCell(0);
				newCelda.innerHTML="<input type='radio' name='rec' value='"+x+"'>";
				var newCelda=newFila.insertCell(1);
				newCelda.innerHTML=nombreTR;
				var newCelda=newFila.insertCell(2);
				newCelda.innerHTML='<c:out value="${sessionScope.rotacion.rotrecdescripcion[st.index]}"/>';
				var newCelda=newFila.insertCell(3);
				newCelda.innerHTML='<c:out value="${sessionScope.rotacion.rotrechoraini[st.index]}"/>';
				var newCelda=newFila.insertCell(4);
				newCelda.innerHTML='<c:out value="${sessionScope.rotacion.rotrechorafin[st.index]}"/>';
				//var newCelda=newFila.insertCell(5);
				//newCelda.innerHTML=x;
				document.frmNuevo.rotrec_tiprec[x].value='<c:out value="${sessionScope.rotacion.rotrec_tiprec[st.index]}"/>';
				document.frmNuevo.rotrecdescripcion[x].value='<c:out value="${sessionScope.rotacion.rotrecdescripcion[st.index]}"/>';
				document.frmNuevo.rotrechoraini[x].value='<c:out value="${sessionScope.rotacion.rotrechoraini[st.index]}"/>';
				document.frmNuevo.rotrechorafin[x].value='<c:out value="${sessionScope.rotacion.rotrechorafin[st.index]}"/>';
				partir(document.frmNuevo.rotrechoraini[x],document.frmNuevo.aux1,document.frmNuevo.aux2)
				ax1=parseInt(document.frmNuevo.aux1.value)*60;
				ax2=parseInt(document.frmNuevo.aux2.value);
				partir(document.frmNuevo.rotrechorafin[x],document.frmNuevo.aux1,document.frmNuevo.aux2)
				ax3=parseInt(document.frmNuevo.aux1.value)*60;
				ax4=parseInt(document.frmNuevo.aux2.value);
				document.frmNuevo.totreceso[x].value=(parseInt(ax3)+parseInt(ax4))-(parseInt(ax1)+parseInt(ax2))
				document.frmNuevo.nombre[x].value=nombreTR;
				document.frmNuevo.valor[x].value=x;
				nombreTR="";
			</c:forEach>
		</c:if>
		<c:if test="${sessionScope.login.sedeId!='' && sessionScope.login.jornadaId!=''}">
		  filtro3(document.frmNuevo.rotstrsede, document.frmNuevo.rotstrjornada,document.frmNuevo.rotstrmetodologia,document.frmNuevo.rtesgrgrado);
	  </c:if>
		combohora(document.frmNuevo.jorinihor,document.frmNuevo.auxjorinihor);
		combomin(document.frmNuevo.jorinimin,document.frmNuevo.auxjorinimin);
		combohora(document.frmNuevo.jorfinhor,document.frmNuevo.auxjorfinhor);
		combomin(document.frmNuevo.jorfinmin,document.frmNuevo.auxjorfinmin);
  </script>