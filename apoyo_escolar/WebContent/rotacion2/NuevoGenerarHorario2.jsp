<%@ page language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<jsp:useBean id="rotacion2" class="siges.rotacion2.beans.Rotacion2" scope="session"/>
<jsp:setProperty name="rotacion2" property="*"/>
<script languaje="javaScript">
<!--
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-- Seleccione uno --","-1");
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
		document.frmNuevo.tipo.value=96;
		document.frmNuevo.cmd.value="Nuevo";
		document.frmNuevo.submit();
	}

	function ver(){
			document.frmNuevo.tipo.value=15;
			document.frmNuevo.cmd.value="Ver";
			document.frmNuevo.submit();
	}

	function guardar(){
		var txt="Con los parámetros configurados se presentan los siguientes casos: \n\n ";
		<c:if test="${!empty sessionScope.docentesFaltantes}">
			txt+="- Existen grados - asignaturas que no tienen docentes. \n"
		</c:if>
		<c:if test="${!empty sessionScope.capacidadEspFis}">
			txt+="- Existen espacios físicos que no tienen la capacidad suficiente para algunos grupos \n"
		</c:if>
		<c:if test="${!empty sessionScope.docentesSinCarga}">
			txt+="- Existen docentes que no tienen carga académica. \n"
		</c:if>
		txt+="\n ¿Desea solicitar la generación del horario?"
		if(validarForma(document.frmNuevo)){
			if(confirm(txt)){
				document.frmNuevo.tipo.value=12;
				document.frmNuevo.cmd.value="Guardar";
				document.frmNuevo.submit();
			}
		}
	}
	
	function hacerValidaciones_frmNuevo(forma){
	}

	function lanzar(tipo){
		document.frmNuevo.tipo.value=tipo;
		document.frmNuevo.action='<c:url value="/rotacion2/ControllerEditar.do"/>';
		document.frmNuevo.ext2.value='/rotacion2/ControllerFiltro.do';
		document.frmNuevo.target="";
		document.frmNuevo.submit();
	}
	
	function lanzar2(tipo){
		document.frmLista.tipo.value=tipo;
		document.frmLista.action='<c:url value="/rotacion2/ControllerEditar.do"/>';
		document.frmLista.ext2.value='';
		document.frmLista.target="";
		document.frmLista.submit();
	}
	
	function remoto(n){
		if(n==1)
			remote = window.open('<c:url value="/rotacion2/ListaDocentesFaltantes.jsp?ext=1&servTarget=3"/>',"3","width=700,height=550,resizable=no,toolbar=no,directories=no,menubar=no,status=yes,scrollbars=yes");
		if(n==2)
			remote = window.open('<c:url value="/rotacion2/ListaCapacidad.jsp?ext=1&servTarget=3"/>',"3","width=450,height=550,resizable=no,toolbar=no,directories=no,menubar=no,status=yes,scrollbars=yes");
		if(n==3)
			remote = window.open('<c:url value="/rotacion2/ListaDocentesSinCarga.jsp?ext=1&servTarget=3"/>',"3","width=450,height=550,resizable=no,toolbar=no,directories=no,menubar=no,status=yes,scrollbars=yes");
		if(n==4)
			remote = window.open('<c:url value="/rotacion2/ListaValidacionEspacios.jsp?ext=1&servTarget=3"/>',"3","width=450,height=550,resizable=no,toolbar=no,directories=no,menubar=no,status=yes,scrollbars=yes");
			
		remote.moveTo(200,200);
		if (remote.opener == null) remote.opener = window;
		if(n==1)
			remote.opener.name = "centro";
		if(n==2)
			remote.opener.name = "centro";
		if(n==3)
			remote.opener.name = "centro";
		if(n==4)
			remote.opener.name = "centro";
		remote.focus();
	}
	
	function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
		this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
	}

	function filtro(combo_padre,combo_hijo,combo_hijo2,combo_hijo3){
		borrar_combo(combo_hijo); borrar_combo(combo_hijo2); 
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
						Sel_Hijos[k] = '';
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
		borrar_combo(combo_hijo);
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
							Sel_Hijos[k] = '';
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
						combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); 
						combo_hijo.selectedIndex = i+1;
					}else	combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
				}
			}
		</c:if>
	}

//-->
</script>
<%@include file="../mensaje.jsp" %>
	<font size="1">
		<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action="<c:url value="/rotacion2/NuevoGuardar.jsp"/>">
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="ext2" VALUE=''>
			<INPUT TYPE="hidden" NAME="height" VALUE="180">
			<INPUT TYPE="hidden" NAME="tipo" VALUE="">
			<input type="hidden" name="ext" value="">
			<input type="hidden" name="metodologia" value='<c:out value="${sessionScope.metodologia}"/>'>
			<input type="hidden" name="sede" value='<c:out value="${requestScope.sede}"/>'>
			<input type="hidden" name="jornada" value='<c:out value="${requestScope.jornada}"/>'>
			<input type="hidden" name="grado" value='<c:out value="${requestScope.grado}"/>'>
			<table align="center" width="100%">
				<tr>
				  <td width="45%" align="left">
					  <input name="cmd12" class="boton" type="button" value="Cancelar" onClick="cancelar()">
					  <input name="cmd12" class="boton" type="button" value="Ver Solicitudes" onClick="ver()">
					</td>
				</tr>
				<tr>
					<td rowspan="2" width="780">
						<img src='<c:url value="/etc/img/tabs/generar_horario_1.gif"/>' alt='Generar Horario' height="26" border="0">
					</td>
				</tr>
  	  </table>
  	  <table align="center" width="100%" cellpadding="0" cellspacing="0" border="0" >
  	  <caption>VALIDACIONES</caption><tr><td>&nbsp;</td></tr>
  	  </table>
  	  <table align="center" width="100%" cellpadding="0" cellspacing="0" border="0" >
			  <c:if test="${empty sessionScope.docentesFaltantes}">
					<tr>
						<th colspan='6'>TODOS LOS GRADOS - ASIGNATURAS TIENEN ASIGNADO UN DOCENTE</th>
					</tr>
				</c:if>
				<c:if test="${!empty sessionScope.docentesFaltantes}">
					<tr>
						<th colspan='6' style="color:red">GRADOS - ASIGNATURAS SIN DOCENTE<br><a style='color:blue;' href="javaScript:remoto(1)" style="">De click aquí para ver la lista</a></th>
					</tr>
				</c:if>
			</table>
			<br><br>
			<table align="center" width="100%"  cellpadding="0" cellspacing="0" border="0" >
			  <c:if test="${empty sessionScope.capacidadEspFis}">
					<tr>
						<th colspan='6'>TODOS LOS GRADOS - ASIGNATURAS - GRUPOS CUMPLEN CON LA CAPACIDAD DEL ESPACIO FÍSICO</th>
					</tr>
				</c:if>
				<c:if test="${!empty sessionScope.capacidadEspFis}">
					<tr>
						<th colspan='6' style="color:red">CAPACIDAD DEL ESPACIO FÌSICO INSUFICIENTE<br><a style='color:blue;' href="javaScript:remoto(2)" style="">De click aquí para ver la lista</a></th>
					</tr>
				</c:if>
			</table>
			<br><br>
			<table align="center" width="100%"  cellpadding="0" cellspacing="0" border="0" >
			  <c:if test="${empty sessionScope.docentesSinCarga}">
					<tr>
						<th colspan='6'>TODOS LOS DOCENTES TIENEN ASIGNADA CARGA ACADÉMICA</th>
					</tr>
				</c:if>
				<c:if test="${!empty sessionScope.docentesSinCarga}">
					<tr>
						<th colspan='6' style="color:red">DOCENTES SIN CARGA ACADÉMICA<br><a style='color:blue;' href="javaScript:remoto(3)" style="">De click aquí para ver la lista</a></th>
					</tr>
				</c:if>
			</table>
  	  <br><br>
 	  </form>
  	  <table align="center" width="100%"  border='0' >
  	  	<tr>
				  <th width="45%" align="center">
							<c:if test="${requestScope.plantilla=='--'}">No se pudo generar la plantilla de validaciones</c:if>
							<c:if test="${requestScope.plantilla!='--'}">
							<form name='frmGenerar' action='<c:url value="/${requestScope.plantilla}"/>' method='post' target='_blank'><input type='hidden' name='dir' value='<c:out value="${requestScope.plantilla}"/>'><input type='hidden' name='tipo' value='<c:out value="${requestScope.tipoArchivo}"/>'><input type='hidden' name='accion' value='0'><input type='hidden' name='aut' value='1'>
								<a style='color:blue;' href='javaScript:document.frmGenerar.submit();'>Las validaciones de la solicitud se pueden descargar en formato Excel haciendo click <b>AQUI&nbsp;<img border=0 src='<c:url value="/etc/img/xls.gif"/>'></b></A><br>
							</form>
							</c:if>
					</th>
				</tr>
			</table>

  	  <table align="center" width="100%" cellpadding="0" cellspacing="0" border="0" >
  	  <caption>RESTRICCIONES</caption><tr><td>&nbsp;</td></tr>
  	  </table>
			<table align="center" width="100%"  cellpadding="0" cellspacing="0" border="0">
			  <c:if test="${empty sessionScope.validacionEspacios}">
					<tr>
						<th colspan='6'>TODOS LOS ESPACIOS FIJADOS SON SUFICIENTES PARA LAS HORAS DE LA ASIGNATURA</th>
					</tr>
				</c:if>
				<c:if test="${!empty sessionScope.validacionEspacios}">
					<tr>
						<th colspan='6' style="color:red">ESPACIOS FIJADOS INSUFICIENTES PARA LAS HORAS DE LA ASIGNATURA<br><a style='color:blue;' href="javaScript:remoto(4)" style="">De click aquí para ver la lista</a></th>
					</tr>
				</c:if>
			</table>
			<br><br>
			<c:if test="${requestScope.invalido==1}">
				<table align="center" width="100%" cellpadding="0" cellspacing="0" border="0">
	  	  	<tr>
					  <td width="45%" align="center">
						  Debido a restricciones con los espacios físicos no se podrá solicitar la rotación.
						  Debe solucionarlo para poder solicitar.
						</td>
					</tr>
				</table>
			</c:if>
				<table align="center" width="100%" cellpadding="0" cellspacing="0" border="0">
	  	  	<tr>
					  <td width="45%" align="center">
						  <input name="cmd2" class="boton" type="button" value="Solicitar Horario" onClick="guardar()">
						</td>
					</tr>
				</table>
  </font>
  <script>
  </script>