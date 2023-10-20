<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<jsp:useBean id="nuevoPersonal" class="siges.personal.beans.Personal" scope="session"/><jsp:setProperty name="nuevoPersonal" property="*"/>
<jsp:useBean id="nuevoConvivencia" class="siges.estudiante.beans.Convivencia" scope="session"/><jsp:setProperty name="nuevoConvivencia" property="*"/>
<jsp:useBean id="carga" class="siges.personal.beans.Carga" scope="session"/>
<jsp:useBean id="nuevoSalud" class="siges.estudiante.beans.Salud" scope="session"/><jsp:setProperty name="nuevoSalud" property="*"/>
<jsp:useBean id="laboralVO" class="siges.personal.beans.LaboralVO" scope="session"/><jsp:setProperty name="laboralVO" property="*"/>
<jsp:useBean id="asistenciaVO" class="siges.estudiante.beans.AsistenciaVO" scope="session"/><jsp:setProperty name="asistenciaVO" property="*"/>
<jsp:useBean id="formacionVO" class="siges.personal.beans.FormacionVO" scope="session"/><jsp:setProperty name="formacionVO" property="*"/>
<jsp:useBean id="paramsVO" class="siges.personal.beans.ParamsVO" scope="page"/>
<%@include file="../parametros.jsp"%><c:set var="tip" value="8" scope="page"/>
<script language="javaScript">
<!--
			var fichaPersonal=1;
			var fichaSede=1;
			var fichaConvivencia=1;
			var fichaSalud=1;
			var fichaLaboral=1;
			var fichaAcademica=1;
			var fichaAsistencia=1;
			var fichaCarga=1;
			var fichaFoto=1;
			var nav4=window.Event ? true : false;

	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-- Seleccione uno --","-1");
	}
	
	function limpiar_combo(combo){
		combo.selectedIndex = 0;
	}

	function borrar_combo3(combo){
		if(combo.length){
			for(var i=0;i<combo.length;i++) {
				combo[i].checked=false;
				combo[i].disabled=true;
				if(document.getElementById('td'+combo[i].value))document.getElementById('td'+combo[i].value).style.display='none';
			}
		 }else{
			combo.checked=false;
			combo.disabled=true;
			if(document.getElementById('td'+combo.value))
				document.getElementById('td'+combo.value).style.display='none';
		 }
	}
	
	function eliminar(sede,jornada,asignatura,met){
		if(!confirm('¿Realmente desea eliminar asignatura de la carga del docente?')) return;
		//sede
		if(document.frmNuevo.rotdagsede.length){
			for(var i=0;i<document.frmNuevo.rotdagsede.length;i++){
				if(document.frmNuevo.rotdagsede[i].value==sede){
					document.frmNuevo.rotdagsede.selectedIndex=i;
				}
			}
		}		
		filtro(document.frmNuevo.rotdagsede, document.frmNuevo.rotdagjornada,document.frmNuevo.rotdaggrados_,document.frmNuevo.rotdagmetodologia,document.frmNuevo.rotdagasignatura);
		//jornada
		if(document.frmNuevo.rotdagjornada.length){
			for(var i=0;i<document.frmNuevo.rotdagjornada.length;i++){
				if(document.frmNuevo.rotdagjornada[i].value==jornada){
					document.frmNuevo.rotdagjornada.selectedIndex=i;
				}
			}
		}
		filtro22(document.frmNuevo.rotdagasignatura,document.frmNuevo.rotdaggrados_,document.frmNuevo.rotdagmetodologia);
		//met
		if(document.frmNuevo.rotdagmetodologia.length){
			for(var i=0;i<document.frmNuevo.rotdagmetodologia.length;i++){
				if(document.frmNuevo.rotdagmetodologia[i].value==met){
					document.frmNuevo.rotdagmetodologia.selectedIndex=i;
				}
			}
		}
		filtroAsignatura(document.frmNuevo.rotdagmetodologia,document.frmNuevo.rotdagasignatura,document.frmNuevo.rotdaggrados_);
		//asignatura
		if(document.frmNuevo.rotdagasignatura.length){
			for(var i=0;i<document.frmNuevo.rotdagasignatura.length;i++){
				if(document.frmNuevo.rotdagasignatura[i].value==asignatura){
					document.frmNuevo.rotdagasignatura.selectedIndex=i;
				}
			}
		}
		filtroGrados(document.frmNuevo.rotdagsede, document.frmNuevo.rotdagjornada,document.frmNuevo.rotdagmetodologia,document.frmNuevo.rotdaggrados_,document.frmNuevo.rotdagasignatura,document.frmNuevo.rotdagIHgrados_);
		if(document.frmNuevo.rotdaggrados.length){
			for(var i=0;i<document.frmNuevo.rotdaggrados.length;i++){
					document.frmNuevo.rotdaggrados[i].disabled=true;document.frmNuevo.rotdaggrados[i].value='';
			}
		}else{
			document.frmNuevo.rotdaggrados.disabled=true;document.frmNuevo.rotdaggrados.value='';
		}
		guardar(document.frmNuevo.tipo.value);
	}
	
	function editar(sede,jornada,asignatura,met){
		//sede
		if(document.frmNuevo.rotdagsede.length){
			for(var i=0;i<document.frmNuevo.rotdagsede.length;i++){
				if(document.frmNuevo.rotdagsede[i].value==sede){
					document.frmNuevo.rotdagsede.selectedIndex=i;
				}
			}
		}		
		filtro(document.frmNuevo.rotdagsede, document.frmNuevo.rotdagjornada,document.frmNuevo.rotdaggrados_,document.frmNuevo.rotdagmetodologia,document.frmNuevo.rotdagasignatura);
		//jornada
		if(document.frmNuevo.rotdagjornada.length){
			for(var i=0;i<document.frmNuevo.rotdagjornada.length;i++){
				if(document.frmNuevo.rotdagjornada[i].value==jornada){
					document.frmNuevo.rotdagjornada.selectedIndex=i;
				}
			}
		}
		filtro22(document.frmNuevo.rotdagasignatura,document.frmNuevo.rotdaggrados_,document.frmNuevo.rotdagmetodologia);
		//met
		if(document.frmNuevo.rotdagmetodologia.length){
			for(var i=0;i<document.frmNuevo.rotdagmetodologia.length;i++){
				if(document.frmNuevo.rotdagmetodologia[i].value==met){
					document.frmNuevo.rotdagmetodologia.selectedIndex=i;
				}
			}
		}
		filtroAsignatura(document.frmNuevo.rotdagmetodologia,document.frmNuevo.rotdagasignatura,document.frmNuevo.rotdaggrados_);
		//asignatura
		if(document.frmNuevo.rotdagasignatura.length){
			for(var i=0;i<document.frmNuevo.rotdagasignatura.length;i++){
				if(document.frmNuevo.rotdagasignatura[i].value==asignatura){
					document.frmNuevo.rotdagasignatura.selectedIndex=i;
				}
			}
		}
		filtroGrados(document.frmNuevo.rotdagsede, document.frmNuevo.rotdagjornada,document.frmNuevo.rotdagmetodologia,document.frmNuevo.rotdaggrados_,document.frmNuevo.rotdagasignatura,document.frmNuevo.rotdagIHgrados_);
	}
	
	function cancelar(){
		location.href='<c:url value="/bienvenida.jsp"/>';
	}

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}

	function getSubTotal(obj,obj2){
			var subTotal=0;
			if(obj.length){
				for(var i=0;i<obj.length;i++){
					if(obj[i].disabled==false && obj[i].checked==true){
						subTotal=parseInt(subTotal)+parseInt(obj2[i].value);
					}
				}
			}else{
					if(obj.disabled==false && obj.checked==true){
						subTotal=parseInt(subTotal)+parseInt(obj2.value);
					}
			}			
			return subTotal;
	}
	
	function filtro(combo_padre,combo_hijo,combo_hijo2,combo_hijo3,combo_hijo4){
		borrar_combo(combo_hijo);
		<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">
			var Padres = new Array();<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">id_Hijos=new Array();Hijos= new Array();Sel_Hijos= new Array();id_Padre= new Array();var k=0;
				<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"><c:if test="${fila[0]==fila2[2]}">Sel_Hijos[k] = '<c:if test="${sessionScope.carga.rotdagjornada == fila2[0] || requestScope.jornadaref==fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>';Hijos[k] = '<c:out value="${fila2[1]}"/>';id_Padre[k] = '<c:out value="${fila2[2]}"/>';k++;</c:if>
				</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
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
	function filtroAsignatura(combo_padre,combo_hijo,combo_hijo2){
		borrar_combo(combo_hijo); 
		borrar_combo3(combo_hijo2);
		<c:if test="${!empty requestScope.filtroMetodologiaF && !empty requestScope.filtroAsignatura}">
			var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila" varStatus="st">id_Hijos=new Array();Hijos= new Array();Sel_Hijos= new Array();id_Padre= new Array();var k=0;
				<c:forEach begin="0" items="${requestScope.filtroAsignatura}" var="fila2"><c:if test="${fila[0]==fila2[2]}">Sel_Hijos[k] = '<c:if test="${sessionScope.carga.rotdagjornada == fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>';Hijos[k] = '<c:out value="${fila2[1]}"/>';id_Padre[k] = '<c:out value="${fila2[2]}"/>';k++;</c:if>
				</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
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
	
	
	function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
		this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
	}
	
	function guardar(tipo){
		if(validarForma(document.frmNuevo)){
			var subTotalUsuario=getSubTotal(document.frmNuevo.rotdaggrados_,document.frmNuevo.rotdagIHgrados_);//subtotoal de la asigantura del usuario
			var totalServidor=parseInt(document.frmNuevo.ihTotal2.value);//la carga total del servidor
			var subTotalServidor=parseInt(document.frmNuevo.ihTotal3.value);//subtotoal de la asigantura del servidor
			var totalUsuario=parseInt(document.frmNuevo.rotdaghoras.value);///lo que el usuario dice como total semanal
			var validacion=parseInt(totalServidor)-parseInt(subTotalServidor)+parseInt(subTotalUsuario)-parseInt(totalUsuario);
			var sobrante=parseInt(totalUsuario)-parseInt(totalServidor)-parseInt(subTotalServidor);
			/*
			alert('subTotalUsuario:'+subTotalUsuario);
			alert('totalServidor:'+totalServidor);
			alert('subTotalServidor:'+subTotalServidor);
			alert('totalUsuario:'+totalUsuario);
			alert('validacion:'+validacion);
			alert('sobrante:'+sobrante);
			*/
			if(parseInt(validacion)>0){
				alert('La carga académica excede el total de la semana. Solo dispone de '+sobrante+' para asignar');
				return false;
			}
			if(document.frmNuevo.rotdaggrados.length){
				for(var i=0;i<document.frmNuevo.rotdaggrados.length;i++){
					if(document.frmNuevo.rotdaggrados_[i].disabled==true || document.frmNuevo.rotdaggrados_[i].checked==false){
					
						document.frmNuevo.rotdaggrados[i].disabled=true;
						document.frmNuevo.rotdaggrados[i].value='';
						
						document.frmNuevo.rotdagIHgrados_[i].disabled=true;
						document.frmNuevo.rotdagIHgrados_[i].value='';
						
					}
				}
			}else{
			
					if(confirm('¿Desea GUARDAR LOS GRUPOS SELECCIONADOS para este docente?')){
					if(document.frmNuevo.rotdaggrados_.disabled==true || document.frmNuevo.rotdaggrados_.checked==false){
						document.frmNuevo.rotdaggrados.disabled=true;document.frmNuevo.rotdaggrados.value='';
						document.frmNuevo.rotdagIHgrados_.disabled=true;document.frmNuevo.rotdagIHgrados_.value='';
						}
					}
			}
			document.frmNuevo.tipo.value=tipo;
			document.frmNuevo.cmd.value="Guardar";
			document.frmNuevo.submit();
		}	
	}

	function hacerValidaciones_frmNuevo(forma){
		validarLista(forma.rotdagsede, "- Seleccione una sede",1)
		validarLista(forma.rotdagjornada, "- Seleccione una jornada",1)
		/*validarLista(forma.rotdagasignatura, "- Seleccione una asignatura",1)
		validarEntero(forma.rotdaghoras, "- Total horas semanales", 1, 99)
		if(forma.rotdaggrados.length){
			for(var i=0;i<forma.rotdaggrados.length;i++){
				if(forma.rotdaggrados_[i].disabled==false && forma.rotdaggrados_[i].checked==true){
					validarEntero(forma.rotdagIHgrados_[i], "- Total horas semanales de grados seleccionados", 1, 99)
				}
			}
		}else{
				if(forma.rotdaggrados_.disabled==false && forma.rotdaggrados_.checked==true){
					validarEntero(forma.rotdagIHgrados_, "- Total horas semanales de grados seleccionados", 1, 99)
				}
		}*/
	}

	function guardarCarga(){
		var listaCarga = document.getElementById('frmListaCarga').contentWindow.document;
		listaCarga.frmCarga.tipo.value=8;
		listaCarga.frmCarga.ihTotal2=document.frmNuevo.rotdaghoras.value;
		listaCarga.frmCarga.target="_parent";
		listaCarga.frmCarga.action ='<c:url value="/personal/NuevoGuardar.jsp"/>';
		listaCarga.frmCarga.cmd.value="Guardar";
		listaCarga.frmCarga.rotdagsede.value=document.frmNuevo.rotdagsede.value;
		listaCarga.frmCarga.rotdagjornada.value=document.frmNuevo.rotdagjornada.value;
		listaCarga.frmCarga.rotdagmetodologia.value=document.frmNuevo.rotdagmetodologia.value;
		listaCarga.frmCarga.rotdaghoras.value=document.frmNuevo.rotdaghoras.value;
		
		var elements = listaCarga.getElementsByClassName("txtHorasAsignatura");
		var sumaCarga=0;
		for(var i=0; i<elements.length; i++) {
		    if (elements[i].value != ""){
		    	sumaCarga+=parseInt(elements[i].value);
		    }
		}
		if(sumaCarga > document.frmNuevo.rotdaghoras.value){
			alert("el total de horas asignadas excede en "+(sumaCarga-document.frmNuevo.rotdaghoras.value)+" el numero de horas semanales disponibles del docente");
		}else{
				//if(confirm('Se han guardado exitosamente el total de horas asignadas')){
				if(confirm('Alerta: Por favor presione Aceptar y espere mientras se actualiza la informacion.')){
			listaCarga.frmCarga.submit();	
			}
		}
		//}
		return false;
		
	}
	
	function lanzar(tipo){
		document.frmNuevo.tipo.value=tipo;
		document.frmNuevo.action='<c:url value="/personal/ControllerNuevoEdit.do"/>';
		document.frmNuevo.target="";
		
		document.frmNuevo.submit();
	}
	
	function buscar(){
		document.frmNuevo.tipo.value='8';
		document.frmNuevo.action='<c:url value="/personal/FiltroSave.jsp"/>';
		document.frmNuevo.submit();
	}

	
	
	function resizeIframe(iframe) {
		iframe.height = iframe.contentWindow.document.body.scrollHeight + "px";
	}
  
	
	function ajaxListaCarga(){
		document.frmAjaxListaCarga.sedeSeleccionada.value = document.frmNuevo.rotdagsede.value;
		document.frmAjaxListaCarga.jornadaSeleccionada.value = document.frmNuevo.rotdagjornada.value;
		document.frmAjaxListaCarga.metodologiaSeleccionada.value = document.frmNuevo.rotdagmetodologia.value;
		document.frmAjaxListaCarga.tipo.value=11;
		document.frmAjaxListaCarga.target='frameAjax';
		document.frmAjaxListaCarga.submit();
		//document.frmCarga.submit();
		//document.getElementById('frmListaCarga').contentDocument.location.reload(true);
	}
	
//-->
</script>
<%@include file="../mensaje.jsp" %>
<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/personal/NuevoGuardar.jsp"/>'>
			<input type="hidden" name="cmd" value="">
 


		  	<c:if test="${ sessionScope.login.perfil == paramsVO.PERFIL_RECTOR or sessionScope.login.perfil == paramsVO.PERFIL_ADMIN_ACADEMICO}">
			     
			 <input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframePer}"/>'>
	  </c:if>
	  
		    <input type="hidden" name="ext" value="">
		 
		 
		
			
			<INPUT TYPE="hidden" NAME="height" VALUE="180">
			<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${pageScope.tip}"/>'>
			
			<input type="hidden" name="rotdagdocente" value='<c:out value="${sessionScope.nuevoPersonal.pernumdocum}"/>'>
			<input type="hidden" name="rotdagVigencia" value='<c:out value="${sessionScope.nuevoPersonal2.perVigencia}"/>'>
            <input type="hidden" name="ihTotal2" value='<c:out value="${requestScope.ihTotal2}"/>'>
			<input type="hidden" name="ihTotal3" value='0'>
			
			<input type="hidden" name="sedeSeleccionada" value='0'>
			<input type="hidden" name="jornadaSeleccionada" value='0'>
			<input type="hidden" name="metodologiaSeleccionada" value='0'>
			
			
		<table>
			<tr>
				<td rowspan="2" width="510">
					<script>
						if(fichaPersonal==1)document.write('<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Datos Básicos"  height="26" border="0"></a>');
						if(fichaSalud==1)document.write('<a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/inf_salud_0.gif" alt="Salud"  height="26" border="0"></a>');
						if(fichaConvivencia==1)document.write('<a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/inf_convivencia_0.gif" alt="Convivencia"  height="26" border="0"></a>');
						if(fichaSede==1)document.write('<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/Doc_Jor0.gif" alt=""  height="26" border="0"></a>');
						if(fichaLaboral==1)document.write('<a href="javaScript:lanzar(5)"><img src="../etc/img/tabs/inf_laboral_0.gif" alt="Laboral"  height="26" border="0"></a>');
						if(fichaAcademica==1)document.write('<a href="javaScript:lanzar(6)"><img src="../etc/img/tabs/for_academica_0.gif" alt="Academica"  height="26" border="0"></a><br>');
						if(fichaAsistencia==1)document.write('<a href="javaScript:lanzar(7)"><img src="../etc/img/tabs/asistencia_0.gif" alt="Asistencia" height="26" border="0"></a>');
						if(fichaCarga==1)document.write('<img src="../etc/img/tabs/carga_academica_1.gif" alt=""  height="26" border="0">');
						if(fichaFoto==1)document.write('<a href="javaScript:lanzar(9)"><img src="../etc/img/tabs/fotografia_0.gif" alt=""  height="26" border="0"></a>');
					</script>
				</td>
				<td align="right"><c:out value="${sessionScope.nuevoPersonal.pernombre1}"/> <c:out value="${sessionScope.nuevoPersonal.perapellido1}"/></td>
			</tr>
		</table>
		<table border="0" align="center" width="95%" cellpadding="0" cellspacing="0">
			  <caption>Filtro de Búsqueda</caption>
			  <tr><td colspan="2" width="50%"><input name="cmd2" class="boton" type="button" value="Buscar" onClick="buscar()"></td></tr>
			  <tr>
						<td align="right" width="50%"><span class="style2">*</span> Vigencia: &nbsp;&nbsp;&nbsp;</td>
						<td>
						<select name="perVigencia" style='width:50px;'>
							<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
								<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.nuevoPersonal2.perVigencia}">selected</c:if>><c:out value="${vig.nombre}"/></option>
							</c:forEach>
						</select>
						<td>
			  </tr>
		  </table>
		  <c:if test="${!empty requestScope.cargaDocente}">
			  <table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
				  <tr>
				  	<td class="EncabezadoColumna">Sede</td>
				  	<td class="EncabezadoColumna">Jornada</td>
				  	<td class="EncabezadoColumna">Asignatura</td>
				  </tr>
					<c:forEach begin="0" items="${requestScope.cargaDocente}" var="fila"  varStatus="st">
						<tr>
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[3]}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[4]}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[5]}"/></td>
						</tr>
					</c:forEach>
			  </table>
		  </c:if>
			<font size="1"><br>
		<table>
			<tr>
				<td width="45%" align="left">
					  <input name="cmd2" class="boton" type="button" value="Guardar" onClick="guardarCarga()">
				</td>
			</tr>
		</table>	
			
  	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
				<tr>
					<td><span class="style2" >*</span> Total Horas Semanales:</td>
				  <td colspan="2">
	          <input type="text" size="3" maxlength="2" name="rotdaghoras" onKeyPress='return acepteNumeros(event)' value='<c:out value="${requestScope.ihTotal}"/>'/>
					</td>
				</tr>			
				<tr><td><hr></td><td colspan="2"><B>ASIGNATURAS Y GRADOS QUE DICTA</B><BR></td></tr>
						<tr>
							<td><span class="style2" >*</span>Vigencia:</td>
							<td><c:out value="${sessionScope.nuevoPersonal2.perVigencia}"/>
							</td>
						</tr>
						<tr><td><span class="style2" >*</span>Sede:</td>
				    <td colspan="3">
						<select name="rotdagsede" onChange='ajaxListaCarga(); filtro(document.frmNuevo.rotdagsede, document.frmNuevo.rotdagjornada,document.frmNuevo.rotdaggrados_,document.frmNuevo.rotdagmetodologia,document.frmNuevo.rotdagasignatura);' style='width:300px;'>
							<option value='-1'>-- Seleccione uno --</option>
							<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
								<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.carga.rotdagsede == fila[0] || requestScope.sederef==fila[0]}">SELECTED</c:if>>
									<c:out value="${fila[1]}"/>
								</option>
							</c:forEach>
						</select>
				   </td>
					 </tr>
					 <tr><td><span class="style2" >*</span>Jornada:</td>
					  <td colspan="2">
						<select name="rotdagjornada" style='width:150px;' onChange='ajaxListaCarga(); filtro22(document.frmNuevo.rotdagasignatura,document.frmNuevo.rotdaggrados_,document.frmNuevo.rotdagmetodologia);'>
							<option value='-1'>-- Seleccione uno --</option>
						</select>
					  </td>
						</tr>
						<tr>
							<td><span class="style2">*</span> Metodología:</td>
							<td>
			            <select name="rotdagmetodologia" style='width:120px;' onChange='ajaxListaCarga(); filtroAsignatura(document.frmNuevo.rotdagmetodologia,document.frmNuevo.rotdagasignatura,document.frmNuevo.rotdaggrados_);'>
			            	<option value='-9'>-- Seleccione uno --</option>
			            	<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila">
				            	<option value='<c:out value="${fila[0]}"/>' <c:if test="${sessionScope.carga.rotdagmetodologia==fila[0] || requestScope.metodologiaref==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
				            </c:forEach>
			            </select>
							</td>	
						</tr>
					<!-- <tr>	
						<td><span class="style2" >*</span>Asignatura:</td>
				    <td colspan="2">
							<select name="rotdagasignatura" onChange='filtroGrados(document.frmNuevo.rotdagsede, document.frmNuevo.rotdagjornada,document.frmNuevo.rotdagmetodologia,document.frmNuevo.rotdaggrados_,document.frmNuevo.rotdagasignatura,document.frmNuevo.rotdagIHgrados_);' style='width:300px;'>
								<option value='-1'>-- Seleccione uno --</option>
							</select>
				    </td>
				</tr>
				<tr valign='top'>
					<td><span class="style2">*</span>Grado:</td>
					<td>
					<table border="0" align="center" width="100%" cellpadding="1" cellspacing="0">
					<c:forEach begin="0" items="${sessionScope.filtroGrados}" var="fila" varStatus="st0">
						<tr name='td<c:out value="${fila[0]}"/>' id='td<c:out value="${fila[0]}"/>' style='display:none'>
							<input type='hidden' name='rotdaggrados' value='<c:out value="${fila[0]}"/>'>
							<td>
								<input type='checkbox' name='rotdaggrados_' value='<c:out value="${fila[0]}"/>' disabled>
								<c:out value="${fila[1]}"/>
							</td>
							<td>Total Horas Semanales: <input type='text' size='2' maxlength='2' id='rotdagIHgrados_' name='rotdagIHgrados_' onKeyPress='return acepteNumeros(event)' disabled></td>
						</tr>
					</c:forEach>
					</table>
					</td>
				</tr> -->
				<tr style="display:none;"><td><iframe name="frameAjax" id="frameAjax" height="180"></iframe></td></tr>
				<tr>
				<td colspan="4">
					<iframe id="frmListaCarga" name="frmListaCarga" onload="resizeIframe(this)" marginheight="0" marginwidth="0"  frameborder="0" width="100%" height="500"
						src='
						<c:url value="/personal/ControllerNuevoEdit.do">
							<c:param name="tipo" value="10"/>
						</c:url>' onload='reSize()'>
					</iframe>
				</td>
			</tr>			
  	  </table>
	</form>
	
	<form method="post" name="frmAjaxListaCarga" action='<c:url value="/personal/ControllerNuevoEdit.do"/>'>
			
			<input type="hidden" name="cmd" value="">
			<input type="hidden" name="tipo" value="">
			
			<input type="hidden" name="rotdagdocente" value='<c:out value="${sessionScope.nuevoPersonal.pernumdocum}"/>'>
			<input type="hidden" name="rotdagVigencia" value='<c:out value="${sessionScope.nuevoPersonal2.perVigencia}"/>'>
            <input type="hidden" name="ihTotal2" value='<c:out value="${requestScope.ihTotal2}"/>'>
			<input type="hidden" name="ihTotal3" value='0'>
			
			<input type="hidden" name="sedeSeleccionada" value='0'>
			<input type="hidden" name="jornadaSeleccionada" value='0'>
			<input type="hidden" name="metodologiaSeleccionada" value='0'>
	</form>
	<!-- <table border="0" align="center" width="95%" cellpadding="1"
			cellspacing="0">
			<tr>
				<td><span class="style2">*</span> Total Horas Semanales:</td>
				<td colspan="2"><input type="text" size="3" maxlength="2"
					name="rotdaghoras" onKeyPress='return acepteNumeros(event)'
					value='<c:out value="${requestScope.ihTotal}"/>' /></td>
			</tr>
			<tr>
				<td><hr></td>
				<td colspan="2"><B>ASIGNATURAS Y GRADOS QUE DICTA</B><BR></td>
			</tr>
			<tr>
				<td><span class="style2">*</span>Vigencia:</td>
				<td><c:out value="${sessionScope.nuevoPersonal2.perVigencia}" />
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span>Sede:</td>
				<td colspan="3"><select name="rotdagsede"
					onChange='filtro(document.frmNuevo.rotdagsede, document.frmNuevo.rotdagjornada,document.frmNuevo.rotdaggrados_,document.frmNuevo.rotdagmetodologia,document.frmNuevo.rotdagasignatura)'
					style='width: 300px;'>
						<option value='-1'>-- Seleccione uno --</option>
						<c:forEach begin="0" items="${requestScope.filtroSedeF}"
							var="fila">
							<option value="<c:out value="${fila[0]}"/>"
								<c:if test="${sessionScope.carga.rotdagsede == fila[0] || requestScope.sederef==fila[0]}">SELECTED</c:if>>
								<c:out value="${fila[1]}" />
							</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td><span class="style2">*</span>Jornada:</td>
				<td colspan="2"><select name="rotdagjornada" style='width: 150px;' onChange='filtro22(document.frmNuevo.rotdagasignatura,document.frmNuevo.rotdaggrados_,document.frmNuevo.rotdagmetodologia)'>
						<option value='-1'>-- Seleccione uno --</option>
				</select></td>
			</tr>
			<tr>
				<td><span class="style2">*</span> Metodología:</td>
				<td><select name="rotdagmetodologia" style='width: 120px;' onChange='filtroAsignatura(document.frmNuevo.rotdagmetodologia,document.frmNuevo.rotdagasignatura,document.frmNuevo.rotdaggrados_)'>
						<option value='-9'>-- Seleccione uno --</option>
						<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}"	var="fila">
							<option value='<c:out value="${fila[0]}"/>'
								<c:if test="${sessionScope.carga.rotdagmetodologia==fila[0] || requestScope.metodologiaref==fila[0]}">SELECTED</c:if>>
								<c:out value="${fila[1]}" />
							</option>
						</c:forEach>
				</select>
				</td>
			</tr>
			</table>
			
	
			<tr>
				<td><span class="style2" >*</span>Asignatura:</td>
		    <td colspan="2">
					<select name="rotdagasignatura" onChange='filtroGrados(document.frmNuevo.rotdagsede, document.frmNuevo.rotdagjornada,document.frmNuevo.rotdagmetodologia,document.frmNuevo.rotdaggrados_,document.frmNuevo.rotdagasignatura,document.frmNuevo.rotdagIHgrados_);' style='width:300px;'>
						<option value='-1'>-- Seleccione uno --</option>
					</select>
		    </td>
			</tr>
			<tr valign='top'>
				<td><span class="style2">*</span>Grado:</td>
				<td>
				<table border="0" align="center" width="100%" cellpadding="1" cellspacing="0">
				<c:forEach begin="0" items="${requestScope.filtroGrados}" var="fila" varStatus="st0">
					<tr name='td<c:out value="${fila[0]}"/>' id='td<c:out value="${fila[0]}"/>' style='display:none'>
						<input type='hidden' name='rotdaggrados' value='<c:out value="${fila[0]}"/>'>
						<td><input type='checkbox' name='rotdaggrados_' value='<c:out value="${fila[0]}"/>' disabled><c:out value="${fila[1]}"/></td>
						<td>Total Horas Semanales: <input type='text' size='2' maxlength='2' id='rotdagIHgrados_' name='rotdagIHgrados_' onKeyPress='return acepteNumeros(event)' disabled></td>
					</tr>
				</c:forEach>
				</table>
				</td>
			</tr> -->		
			
<script>
	filtro(document.frmNuevo.rotdagsede, document.frmNuevo.rotdagjornada,document.frmNuevo.rotdaggrados_,document.frmNuevo.rotdagmetodologia,document.frmNuevo.rotdagasignatura);
	filtroAsignatura(document.frmNuevo.rotdagmetodologia,document.frmNuevo.rotdagasignatura,document.frmNuevo.rotdaggrados_);
		<c:if test="${sessionScope.carga.estado==1}">
			filtro(document.frmNuevo.rotdagsede, document.frmNuevo.rotdagjornada,document.frmNuevo.rotdaggrados_,document.frmNuevo.rotdagmetodologia,document.frmNuevo.rotdagasignatura);
			filtroGrados(document.frmNuevo.rotdagsede, document.frmNuevo.rotdagjornada,document.frmNuevo.rotdagmetodologia,document.frmNuevo.rotdaggrados_,document.frmNuevo.rotdagasignatura,document.frmNuevo.rotdagIHgrados_);
		</c:if>
</script>
