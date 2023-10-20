<%@ page language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<jsp:useBean id="rotacion" class="siges.rotacion.beans.Rotacion" scope="session"/>
<SCRIPT language=javascript src='<c:url value="/etc/js/rotacion.js"/>'></SCRIPT>
<script languaje="javaScript">
<!--
var locked=<c:out value="${sessionScope.isLockedRotacion}"/>;
	function cambiar_radio(forma){
		if(forma.priorizar_.value==""){
			forma.primero[0].checked=true;
			forma.segundo[1].checked=true;
			forma.priorizar_.value="1";
		}else{
			if(forma.priorizar_.value=="1"){
				forma.primero[0].checked=true;
				forma.segundo[1].checked=true;
			}
			if(forma.priorizar_.value=="2"){
				forma.primero[1].checked=true;
				forma.segundo[0].checked=true;
			}
		}
	}
	
	function cambiar_radio2(forma,num){
		if(num==1){
			forma.primero[0].checked=true;
			forma.segundo[1].checked=true;
			forma.priorizar_.value="1";
		}
		if(num==2){
			forma.primero[1].checked=true;
			forma.segundo[0].checked=true;
			forma.priorizar_.value="2";
		}
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
		document.frmNuevo.tipo.value=86;
		document.frmNuevo.cmd.value="Nuevo";
		document.frmNuevo.submit();
	}

	function guardar(){
		//if(validar(document.frmNuevo)){
			document.frmNuevo.tipo.value=81;
			document.frmNuevo.cmd.value="Guardar";
			document.frmNuevo.submit();
		//}
	}

	function validar_combos(forma){
		var cont=0;
		for(var x=0;x<forma.length;x++){
			if(forma[x].value=="0|0")	cont=parseInt(cont)+1
		}
		if(cont==forma.length){
			return false;
		}
		else	return true;
	}
	
	function validar_array(array){
		var cont=0;
		for(var x=0;x<array.length;x++){
			if(document.getElementById("grado_"+grados[x]).value=="0")	cont=parseInt(cont)+1
		}
		if(cont==array.length){
			return false;
		}
		else	return true;
	}
	
	function validar(forma){
		var aux1,aux2,aux11,aux22,sum=0,x,y;
			if(validarLista(forma.estructura_, "",1)==false){
				alert("Seleccione una estructura")
			}
			return true;
	}

	function hacerValidaciones_frmNuevo(forma){
	}

	function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
		this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
	}

	function esconder(tabla1,tabla2,radio1,radio2){
		borrar_tabla(document.getElementById("grd2"));
		radio1.checked=true;
		radio2.checked=false;
		tabla1.style.display="";
		tabla2.style.display="none";
	}
	
	function setheight(tipo){
		switch(tipo){
			case 0:	document.frmNuevo.height.value=Htipo_receso;		break;
			case 10:	document.frmNuevo.height.value=Hestructura;		break;
			case 20:	document.frmNuevo.height.value=Hfijar_espacio;		break;
			case 60:	document.frmNuevo.height.value=Hfijar_asignatura;		break;
			case 70:	document.frmNuevo.height.value=Hfijar_espacio_docente;		break;
			case 40:	document.frmNuevo.height.value=Hinhabilitar_espacio;		break;
			case 50:	document.frmNuevo.height.value=Hinhabilitar_docentes;		break;
			case 80:	document.frmNuevo.height.value=Hpriorizar;		break;
			case 100:	document.frmNuevo.height.value=Hfijar_horario;		break;
			case 110:	document.frmNuevo.height.value=Hborrar_horario;		break;
			case 120:	document.frmNuevo.height.value=Hinhabilitar_hora;		break;
		}
	}
	
	function lanzar(tipo){
		setheight(tipo);
		document.frmNuevo.tipo.value=tipo;
		document.frmNuevo.action='<c:url value="/rotacion/ControllerEditar.do"/>';
		document.frmNuevo.target="";
		document.frmNuevo.submit();
	}
	
	function lanzar2(tipo){
		setheight(tipo);
		document.frmNuevo.tipo.value=tipo;
		document.frmNuevo.action='<c:url value="/rotacion/ControllerEditar.do"/>';
		document.frmNuevo.target="";
		document.frmNuevo.submit();
	}
	
	function borrar_tabla(tabla){
		if(tabla){
			while(tabla.rows.length){
				tabla.deleteRow()
			}
		}
	}
	
	function filtro(combo_padre){
		borrar_tabla(document.getElementById("grd"+combo_padre));
		var id=0;
		<c:if test="${!empty requestScope.listaEstructura && !empty requestScope.listaEstructuraGrado && !empty requestScope.filtroGradoF2}">
			var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.listaEstructura}" var="fila1" varStatus="st">
				id_Hijos=new Array();
				Hijos= new Array();
				Sel_Hijos= new Array();
				id_Padre= new Array();
				var k=0;
				<c:forEach begin="0" items="${requestScope.listaEstructuraGrado}" var="fila2" varStatus="st2">
					<c:set var="tempgrd"><c:out value="${fila2.estPrioridad}"/><c:out value="|"/><c:out value="${fila2.estGrado}"/><c:out value="|"/><c:out value="${fila2.estCodigo}"/></c:set>
					<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3">
						<c:if test="${fila1.estCodigo==fila2.estCodigo && fila1.estSede==fila3[3] && fila1.estJornada==fila3[4] && fila2.estGrado==fila3[0]}">
							Sel_Hijos[k] = '<c:forEach begin="0" items="${sessionScope.rotacion.grado_}" var="filax"><c:if test="${filax == tempgrd}"><c:out value="${tempgrd}"/></c:if></c:forEach>';
							id_Hijos[k] = '<c:out value="${fila3[0]}"/>';
							Hijos[k] = '<c:out value="${fila3[1]}"/>';
							id_Padre[k] = '<c:out value="${fila1.estCodigo}"/>';
							k++;
						</c:if></c:forEach></c:forEach>Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
			if(combo_padre.options){
				var niv=combo_padre.options[combo_padre.selectedIndex].value;
			}else{
					var niv=combo_padre;
			}
			var val_padre = -1;
			for(var k=0;k<Padres.length;k++){
				if(Padres[k].id_Padre[0]==niv) val_padre=k;
			}
			if(val_padre!=-1){
				var no_hijos = Padres[val_padre].Hijos.length;
				var j=0;
				var cantidad=-1;
				var newFila;
				for(i=no_hijos-1; i >=0 ; i--){
					cantidad++;
					if(cantidad==0){
						newFila=document.getElementById("grd"+combo_padre).insertRow(0);
					}else{
						if(cantidad%7==0){
							newFila=document.getElementById("grd"+combo_padre).insertRow(0);
						}	
					}
					var newCelda=newFila.insertCell(0);
					newCelda.innerHTML="<font style='COLOR:#006699;'>"+Padres[val_padre].Hijos[i]+"</font>";
					var newCelda=newFila.insertCell(1);
					var html="";
					html+="<select name='grado_' onChange=''>";
					for(var z=1;z<=no_hijos;z++){
						html+="<option value='"+z+"|"+Padres[val_padre].id_Hijos[i]+"|"+Padres[val_padre].id_Padre[i]+"' ";
						if(z+"|"+Padres[val_padre].id_Hijos[i]+"|"+Padres[val_padre].id_Padre[i]==Padres[val_padre].Sel_Hijos[i])	html+="SELECTED";
						//ielse if(i==(z-1))	html+="SELECTED";
						html+=">";
						if(z==0)	html+="--";
						else	html+=z;
						html+="</option>";
					}
					html+="</select>";
					newCelda.innerHTML=html;
				}
			}
		</c:if>
	}

	function buscar(){
		document.frmNuevo.tipo.value='80';
		document.frmNuevo.cmd.value='Buscar';
		document.frmNuevo.action='<c:url value="/rotacion/FiltroSave.jsp"/>';
		document.frmNuevo.submit();
	}

//-->
</script>
<%@include file="../mensaje.jsp" %>
		<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action="<c:url value="/rotacion/NuevoGuardar.jsp"/>">
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="height" VALUE="180">
			<INPUT TYPE="hidden" NAME="tipo" VALUE="">
			<input type="hidden" name="ext" value="">
			<input type="hidden" name="priorizar_" value="<c:out value="${sessionScope.rotacion.priorizar_}"/>">
			<input type="hidden" name="rotstrmetodologia" value='<c:out value="${sessionScope.filtroRotacion.filMetodologia}"/>'>
			<table align="center" width="100%">
				<tr>
					<td rowspan="2" width="600">
						<a href="javaScript:lanzar(10)"><img src="../etc/img/tabs/estructura_0.gif" alt="Estructura" height="26" border="0"></a>
						<a href="javaScript:lanzar(20)"><img src="../etc/img/tabs/espacios_0.gif" alt="Fijar Espacios Físicos" height="26" border="0"></a>
						<a href="javaScript:lanzar(60)"><img src='<c:url value="/etc/img/tabs/fijar_asignatura_0.gif"/>' alt='Fijar Asignatura' height="26" border="0"></a>
						<a href="javaScript:lanzar(70)"><img src='<c:url value="/etc/img/tabs/espacio_docente_0.gif"/>' alt='Fijar Espacio por Docente' height="26" border="0"></a>
						<a href="javaScript:lanzar(40)"><img src="../etc/img/tabs/espfis_jornada_0.gif" alt="Inhabilitar Espacios Físicos" height="26" border="0"></a>
						<a href="javaScript:lanzar(50)"><img src='<c:url value="/etc/img/tabs/inhabilitar_docentes_0.gif"/>' alt='Inhabilitar Docentes' height="26" border="0"></a><br>
						<img src='<c:url value="/etc/img/tabs/priorizar_1.gif"/>' alt='Priorizar' height="26" border="0">
						<a href="javaScript:lanzar2(100)"><img src='<c:url value="/etc/img/tabs/fijar_horario_0.gif"/>' alt='Fijar horario' height="26" border="0"></a>
						<a href="javaScript:lanzar2(110)"><img src='<c:url value="/etc/img/tabs/borrar_horario_0.gif"/>' alt='Borrar horario' height="26" border="0"></a>
						<a href="javaScript:lanzar(120)"><img src='<c:url value="/etc/img/tabs/inhabilitar_horas_0.gif"/>' alt='Inhabilitar Horas' height="26" border="0"></a>
						<a href="javaScript:lanzar(130)"><img src='<c:url value="/etc/img/tabs/docente_grupo_0.gif"/>' alt='Docente por grupo' height="26" border="0"></a>
						<a href="javaScript:lanzar(140)"><img src='<c:url value="/etc/img/tabs/espacio_grado_0.gif"/>' alt='Espacio por grado' height="26" border="0"></a>
					</td>
				</tr>
  	  </table>
		  <table border="0" align="center" width="95%" cellpadding="0" cellspacing="0" >
			  <caption>Filtro de Búsqueda.</caption>
			  <tr>
						<td colspan="2" ><input name="cmd2" class="boton" type="button" value="Buscar" onClick="buscar()"></td>
				</tr>
			  <tr>
						<td align="right" ><span class="style2">*</span> Vigencia: &nbsp;&nbsp;&nbsp;</td>
						<td>
						<select name="filAnhoVigencia" style='width:50px;'>
							<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
								<option value="<c:out value="${vig}"/>" <c:if test="${vig==sessionScope.filtroRotacion.filAnhoVigencia}">selected</c:if>><c:out value="${vig}"/></option>
							</c:forEach>
						</select>
						</td>
						<td align="right"><span class="style2">*</span> Metodologia: &nbsp;&nbsp;&nbsp;</td>
						<td>
						<select name="filMetodologia" style='width:150px;'>
							<c:forEach begin="0" items="${requestScope.listaMetodologia}" var="vig">
								<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.filtroRotacion.filMetodologia}">selected</c:if>><c:out value="${vig.nombre}"/></option>
							</c:forEach>
						</select>
						</td>
			  </tr>
		  </table>
  	  <BR>
  	<table style="display:" border="0" width="98%" cellpadding="1" cellspacing="0" align="center">
		<caption>Criterio de priorización del Proceso de rotación</caption>
			<tr>
				  <td width="30%" align="left">
					<script>
							if(locked==0){
								document.write('<input name="cmd2" class="boton" type="button" value="Guardar" onClick="guardar()">');
							}
					</script>
					  <c:if test="${sessionScope.isLockedRotacion==1}">
						  <span class="style2">Nota: Información de solo lectura. Proceso de rotación pendiente por aprobar o rechazar</span>
					  </c:if>
					</td>
			</tr>
			<tr>
				<td>Vigencia: </td><td><c:out value="${sessionScope.filtroRotacion.filAnhoVigencia}"/>
				<input type="hidden" name="vigencia" value='<c:out value="${sessionScope.filtroRotacion.filAnhoVigencia}"/>'>
				</td>
			</tr>
			<tr>
				<td class="Fila0" colspan="2" align="center">PRIORIZAR PRIMERO POR:</td>
			</tr>
			<tr><td colspan=2>
  	  <table style="display:" border="0" width="100%" cellpadding="0" cellspacing="0" align="left">
			<tr>
				<td width="30%">&nbsp;</td>
				<td><font style='COLOR:#006699;'>Grado&nbsp;&nbsp;</FONT> <input type="radio" name="primero" onclick="cambiar_radio2(document.frmNuevo,1)"/></td>
	  	  <td><font style='COLOR:#006699;'>Asignatura&nbsp;&nbsp;</FONT> <input type="radio" name="primero" onclick="cambiar_radio2(document.frmNuevo,2)"/></td>
				<td width="30%">&nbsp;</td>
			</tr>
			<tr style="display:none;">
				<td width="30%">&nbsp;</td>
				<td><font style='COLOR:#006699;'>Grado </FONT><input type="radio" name="segundo" onclick="cambiar_radio2(document.frmNuevo,2)"/></td>
				<td><font style='COLOR:#006699;'>Asignatura </FONT><input type="radio" name="segundo" onclick="cambiar_radio2(document.frmNuevo,1)"/></td>
				<td width="30%">&nbsp;</td>
			</tr>			
			</table>
			</td></tr>
				<tr>
					<td  class="Fila0" colspan="2" align="center">ORDEN DE ESTRUCTURAS:</td>
				</tr>
			<tr><td colspan=2>
	  	  <table style="display:" border="0" width="100%" cellpadding="0" cellspacing="0" align="left">
					<c:forEach begin="0" items="${requestScope.listaEstructura}" var="fila" varStatus="st2">
						<tr>
						<td width="30%">&nbsp;</td>
							<td><span class="style2" ></span><font style='COLOR:#006699;'><c:out value="${fila.estNombre}"/></font></td>
							<td>
								<select name='estructura_'>
									<c:forEach begin="1" end="${requestScope.totalEst}" var="fila2" varStatus="st">
										<c:set var="tempstr"><c:out value="${st.index}"/>|<c:out value="${fila.estCodigo}"/></c:set>
										<option value='<c:out value="${tempstr}"/>' <c:forEach begin="0" items="${sessionScope.rotacion.estructura_}" var="fila3" varStatus="st3"><c:if test="${sessionScope.rotacion.estado==1}"><c:if test="${fila3 == tempstr}">SELECTED</c:if></c:if></c:forEach><c:if test="${sessionScope.rotacion.estado!=1}"><c:if test="${st.index==st2.count}">SELECTED</c:if></c:if>><c:if test="${st.index==0}"><c:out value="--"/></c:if><c:if test="${st.index>0}"><c:out value="${st.index}"/></c:if></option>
									</c:forEach>
								</select>
							</td>
						</tr>
				</c:forEach>
				</table>
			</td></tr>
				<tr>
					<td class="Fila0" colspan="2" align="center">ORDEN DE GRADOS:</td>
				</tr>
			<tr><td colspan=2>
	  	  <table style="display:" border="0" width="100%" cellpadding="0" cellspacing="0" align="left">
				<c:forEach begin="0" items="${requestScope.listaEstructura}" var="fila" varStatus="st2">
						<tr>
						<td class="Fila0"><font style='COLOR:#006699;text-transform:uppercase;'><c:out value="${fila.estNombre}"/>:</font></td>
						<td>
						<table id="grd<c:out value="${fila.estCodigo}"/>" width="100%" style="display:" border="0"></table>
						<script>filtro(<c:out value="${fila.estCodigo}"/>)</script>	
						</td>
						</tr>
						<tr><td>&nbsp;</td></tr>
				</c:forEach>
				</table>
			</td></tr>
				<tr>
					<td class="Fila0" colspan="2" align="center">ORDEN DE ASIGNATURAS:</td>
				</tr>
			<tr><td colspan=2>
	  	  <table style="display:" border="0" width="70%" cellpadding="1" cellspacing="0" align="center">
					<tr><td><font style='COLOR:#006699;'>Primera asignatura:</font></td>
					<td>
						<select name='prioAsig1'>
						<option value='-1'>-- Seleccione uno --</option>
						<c:forEach begin="0" items="${requestScope.filtroAsignatura}" var="fila" varStatus="st2"><option value='<c:out value="${fila[0]}"/>' <c:if test="${2==fila[2]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option></c:forEach>
						</select>
					</td></tr>
					<tr><td><font style='COLOR:#006699;'>Segunda asignatura:</font></td>
					<td>
						<select name='prioAsig2'>
						<option value='-1'>-- Seleccione uno --</option>
						<c:forEach begin="0" items="${requestScope.filtroAsignatura}" var="fila" varStatus="st2"><option value='<c:out value="${fila[0]}"/>'  <c:if test="${1==fila[2]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option></c:forEach>
						</select>
					</td></tr>
				</table>
			</td></tr>
			</table>
 	  </form>
<script>cambiar_radio(document.frmNuevo)</script>