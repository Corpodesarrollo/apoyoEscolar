<%@ page contentType="text/html; charset=iso-8859-9" language="java" errorPage="" %>
<%@ page import="siges.util.Recursos" %>

<!--	VERSION		FECHA			AUTOR			DESCRIPCION
			1.0		06/07/2021		JORGE CAMACHO	Código spaguetti
													En el método hacerValidaciones_frm() se modificó la longitud
													de la validación para el campo Número de documento

-->

<jsp:useBean id="filtrob" class="siges.boletines.beans.FiltroBeanReports" scope="session"/>

<jsp:setProperty name="filtrob" property="*"/>

<%@include file="../parametros.jsp"%>

<script languaje='JavaScript'>

	var nav4=window.Event ? true : false;
	
	function acepteNumeros(eve){
		var key = nav4 ? eve.which : eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
			
	function hacerValidaciones_frm(forma){
		validarLista(forma.periodo, "- Periodo", 1);
		validarLista(forma.filVigencia, "- Vigencia", 1);
		<c:if test="${sessionScope.login.perfil ne '510'}">
		if (trim(forma.id.value).length == 0){
			validarLista(forma.metodologia, "- Metodología", 1);	
			validarLista(forma.grado, "- Grado", 1);			
		}else{
			if (trim(forma.id.value).length > 0){
				validarLista(forma.filTipoDoc, "- Tipo Documento", 1);	
			}
			validarCampo(forma.id, "- Identificación", 1, 20);
		}
		</c:if>
	}
		
	function seleccionHijo(comboHijos){
		if(comboHijos.value != "-9"){
			var tipoDocumento = comboHijos.value.split("-")[0];
			var numeroDocumento = comboHijos.value.split("-")[1];
			document.frm.filTipoDoc.value = tipoDocumento;
			document.frm.id.value = numeroDocumento;
			document.frm.txtNoDocumento.value = numeroDocumento;
			document.frm.comboTipoDocumento.value = tipoDocumento;
		}else{
			document.frm.filTipoDoc.value = -9;
			document.frm.id.value = "";
			document.frm.txtNoDocumento.value = "";
			document.frm.comboTipoDocumento.value = -9;
		}
	}

	function lanzar(tipo){
		document.frm.tipo.value = tipo;
		document.frm.action = "<c:url value="/boletines/ControllerBoletinFiltroEdit.do"/>";
		document.frm.target = "";
		document.frm.submit();
	}
	
	function lanzar2(tipo){
		document.frm.tipo.value = '8';
		document.frm.cmd.value = '1';
		document.frm.action = "<c:url value="/siges/gestionAdministrativa/plantillaBoletin/PlantillaBoletin.do"/>";
		document.frm.target = "";
		document.frm.submit();
	}
			
	function guardar(tipo){
	
		if(validarForma(document.frm)){
		
			if(document.frm.mostrarAreas_.checked)
				document.frm.mostrarAreas.value='1';
			else
				document.frm.mostrarAreas.value='-9';
				
			if(document.frm.mostrarAsignaturas_.checked)
				document.frm.mostrarAsignaturas.value='1';
			else
				document.frm.mostrarAsignaturas.value='-9';
				
			if(document.frm.mostrarDescriptores_.checked)
				document.frm.mostrarDescriptores.value='1';
			else
				document.frm.mostrarDescriptores.value='-9';
				
			if(document.frm.mostrarLogros_.checked)
				document.frm.mostrarLogros.value='1';
			else
				document.frm.mostrarLogros.value='-9';
				
			if(document.frm.mostrarFR_.checked)
				document.frm.mostrarFirmaRector.value='1';
			else
				document.frm.mostrarFirmaRector.value='-9';

			if(document.frm.mostrarFD_.checked)
				document.frm.mostrarFirmaDirector.value='1';
			else
				document.frm.mostrarFirmaDirector.value='-9';

			if(document.frm.conLogrosP.checked)
				document.frm.mostrarLogrosP.value='1';
			else
				document.frm.mostrarLogrosP.value='-9';
			
			document.frm.periodonom.value = document.frm.periodo.options[document.frm.periodo.selectedIndex].text;
			document.frm.tipo.value = tipo;
			document.frm.cmd.value = "Buscar";
			document.frm.target = "ModuloReportes";
			
			if(document.frm.id.value == ''){
			    document.frm.id.value = ' ';
			}
			
			window.open("","ModuloReportes","width=800,height=500,menubar=yes,scrollbars=1").focus();
			document.frm.submit();
		}	
	}
			
	function cancelar(){
		document.frm.periodo.selectedIndex = 0;
		document.frm.metodologia.selectedIndex = 0;
		document.frm.grado.selectedIndex = 0;
		document.frm.grupo.selectedIndex = 0;
	}
			
	function cambiarAusenciasT(obj){
		if(obj.checked==true){
			obj.value=1;
		}else{
			obj.value=0;
		}
	}
			
	function Padre(id_Hijos, Hijos, Sel_Hijos, id_Padre){
		this.id_Hijos = id_Hijos;
		this.Hijos = Hijos;
		this.Sel_Hijos = Sel_Hijos;
		this.id_Padre = id_Padre;
	}

	function Padre2(id_Hijos, Hijos, Sel_Hijos, id_Padre, id_Padre2){
		this.id_Hijos = id_Hijos;
		this.Hijos = Hijos;
		this.Sel_Hijos = Sel_Hijos;
		this.id_Padre = id_Padre;
		this.id_Padre2 = id_Padre2;
	}
	
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("--Todos--","-9");
	}
			
	function borrar_combo1(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape")
				combo.options[i] = null;
			else
				combo.remove(i);
		}
		combo.options[0] = new Option("--Seleccione una--","-9");
	}
			
	function filtro(combo_padre, combo_hijo, combo_hijo2, combo_hijo3, combo_hijo4){
	
		combo_hijo4.selectedIndex = 0;
		
		if(combo_padre.selectedIndex==0){
			borrar_combo1(combo_hijo);
			borrar_combo1(combo_hijo2);
			borrar_combo1(combo_hijo3); 
		}else{
			borrar_combo(combo_hijo);
			borrar_combo(combo_hijo2);
			borrar_combo(combo_hijo3);
			<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">
				var Padres = new Array();
				<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">
				<c:set var="z" value="${fila[0]}" scope="session"/>
					id_Hijos = new Array();
					Hijos = new Array();
					Sel_Hijos = new Array();
					id_Padre = new Array();
					var k = 0;
					<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2">
					<c:if test="${fila[0]==fila2[2]}">
							Sel_Hijos[k] = '<c:if test="${sessionScope.filtrob.jornada== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;
					</c:if></c:forEach>
					Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos, id_Padre);
				</c:forEach>						
				var niv=combo_padre.options[combo_padre.selectedIndex].value;
				var val_padre = -9;
				for(var k=0;k<Padres.length;k++){
					if(Padres[k].id_Padre[0]==niv) val_padre=k;							
				}
				if(val_padre!=-9){ var no_hijos = Padres[val_padre].Hijos.length;
					for(i=0; i < no_hijos; i++){
						if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
							combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
						}else
							combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
					}
				}
			</c:if>
		}	
	}
	
	function filtro2(combo_padre, combo_padre2, combo_padre3, combo_hijo, combo_hijo2){
	
		var id=0;
		
		if(combo_padre3.selectedIndex==0){
			borrar_combo(combo_hijo);
    	}else{
			borrar_combo(combo_hijo);
			<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroMetodologiaF}">;var Padres = new Array();
				<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"  varStatus="st">
				<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila4">
				id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
				<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3">
					<c:if test="${fila2[2]==fila3[3] && fila2[0]==fila3[4] && fila3[5]==fila4[0]}">
					Sel_Hijos[k] = '<c:if test="${sessionScope.filtrob.grado== fila3[0]}">SELECTED</c:if>';
					id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; Hijos[k] = '<c:out value="${fila3[1]}"/>'; 
					id_Padre[k] = '<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>|<c:out value="${fila3[5]}"/>'; 
					k++;
					</c:if>
				</c:forEach>
				Padres[id++] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach></c:forEach>
				var niv=combo_padre.value+'|'+combo_padre2.value+'|'+combo_padre3.options[combo_padre3.selectedIndex].value;
				var val_padre = -9;
				for(var k=0;k<Padres.length;k++){
					if(Padres[k].id_Padre[0]==niv) val_padre=k;
				}
				if(val_padre!=-9){
					var no_hijos = Padres[val_padre].Hijos.length;
					for(i=0; i < no_hijos; i++){
						if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
							combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
						}else
							combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
					}
				}
			</c:if>
		}
	}
	
	function filtro3(combo_padre, combo_padre2, combo_padre3, combo_padre4, combo_hijo){
	
		if(combo_padre3.selectedIndex==0){
			borrar_combo(combo_hijo); 
		}else{
			borrar_combo(combo_hijo);
			<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroGrupoF}">
				var Padres = new Array();
				<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila2"  varStatus="st">
					id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); id_Padre2= new Array(); var k=0;
					<c:forEach begin="0" items="${requestScope.filtroGrupoF}" var="fila3">
						<c:if test="${fila2[0]==fila3[4] && fila2[3]==fila3[2] && fila2[4]==fila3[3] && fila2[5]==fila3[5]}">
							Sel_Hijos[k] = '<c:if test="${sessionScope.filtrob.grupo== fila3[0]}">SELECTED</c:if>';
							id_Hijos[k] = '<c:out value="${fila3[0]}"/>'; 
							Hijos[k] = '<c:out value="${fila3[1]}"/>'; 
							id_Padre[k] = '<c:out value="${fila3[2]}"/>|<c:out value="${fila3[3]}"/>|<c:out value="${fila3[4]}"/>'; 
							id_Padre2[k] = '<c:out value="${fila3[5]}"/>'; k++;
						</c:if>
					</c:forEach>
				Padres[<c:out value="${st.index}"/>] = new Padre2(id_Hijos, Hijos, Sel_Hijos,id_Padre,id_Padre2);
				</c:forEach>
				var niv=combo_padre.value+'|'+combo_padre2.value+'|'+combo_padre3.options[combo_padre3.selectedIndex].value;
				var niv2=combo_padre4.options[combo_padre4.selectedIndex].value;
				var val_padre = -9;
				for(var k=0;k<Padres.length;k++){
					if(Padres[k].id_Padre[0]==niv && Padres[k].id_Padre2[0]==niv2) val_padre=k;
				}
				if(val_padre!=-9){ var no_hijos = Padres[val_padre].Hijos.length;
					for(i=0; i < no_hijos; i++){
						if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
							combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
						}else
							combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
					}
				}
			</c:if>
		}	
	}
			
</script>

<%@include file="../mensaje.jsp"%>

<form method="post" name="frm" onSubmit="return validarForma(frm)" action='<c:url value="/boletines/FiltroGuardar.jsp"/>'>

	<input type="hidden" name="repTipo" value="3">
	<input type="hidden" name="repOrden" value="2"> 
	
	<table border="0" align="center" width="100%">
		<caption>Generar Boletines</caption>
		<tr>
			<td width="45%" bgcolor="#FFFFFF">
      			<input class="boton" name="cmd1" type="button" value="Generar" onClick="guardar(document.frm.tipo.value)">
        		<input class="boton" name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
		 	</td>
		</tr>
	</table>

	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>	
	<input type="hidden" name="cmd" value="Cancelar">
	<INPUT TYPE="hidden" NAME="ext2"  VALUE=''><INPUT TYPE="hidden" NAME="height" VALUE='180'>
	<input type="hidden" name="periodonom" value='<c:out value="${sessionScope.filtrob.periodonom}"/>'>
	<input type="hidden" name="sede" value='<c:out value="${sessionScope.login.sedeId}"/>'>
	<input type="hidden" name="jornada" value='<c:out value="${sessionScope.login.jornadaId}"/>'>

	<input type="hidden" name="mostrarAreas" value="-9">
	<input type="hidden" name="mostrarAsignaturas" value="-9">
	<input type="hidden" name="mostrarDescriptores" value="-9">
	<input type="hidden" name="mostrarLogros" value="-9">
	<input type="hidden" name="mostrarLogrosP" value="-9">
	
	<input type="hidden" name="mostrarFirmaRector" value="-9">
	<input type="hidden" name="mostrarFirmaDirector" value="-9">
	
	<c:if test="${sessionScope.login.perfil eq '510'}">
		<input type="hidden" name="filTipoDoc" value="-9">
		<input type="hidden" name="id" value="">
	</c:if>

	<table border="0" align="center" width="100%">
		<tr height="1">
			<td rowspan="2" width="420" >
				<img src="../etc/img/tabs/Boletines_1.gif" width="84" height="26" border="0">
				<c:if test="${sessionScope.login.perfil ne '510'}">
					<a href="javaScript:lanzar(4)"><img src="../etc/img/tabs/logros_pendientes_0.gif" width="84" height="26" border="0"></a>
					<a href="javaScript:lanzar2(8)"><img src="../etc/img/tabs/boletin_plantilla_0.gif" width="84" height="26" border="0"></a>
				</c:if>
			</td>
		</tr>
	</table>

	<table border="0" align="center" width="98%" cellpadding="2" cellspacing="2">
		<caption class="Fila0" colspan="4" align="center"><b>Filtro Generación Boletín</b></caption>
	</table>
	
	<table border="0" align="center" width="90%" cellpadding="2" cellspacing="2">		
		<tr>
			<td><span class="style2" >*</span>Vigencia:</td>
			<td>
     				<select name="filVigencia" style='width:120px;'>
       				<option value='-9'>-- Seleccione una --</option>
			        <c:forEach begin="0" items="${requestScope.filtroVigencia}" var="vig">
						<option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.filtrob.filVigencia==vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
			        </c:forEach>
       			</select>
    			<td><span class="style2" >*</span>Periodo:</td>
			<td>
		      	<select name="periodo" style='width:120px;'>
		        	<option value='-9'>-- Seleccione uno --</option>
		        	<c:forEach begin="0" items="${requestScope.filtroPeriodo}" var="per">
		        		<option value="<c:out value="${per.codigo}"/>" <c:if test="${sessionScope.filtrob.periodo== per.codigo}">SELECTED</c:if>> <c:out value="${per.nombre}"/> </option>
		        	</c:forEach>
		        </select>
     			</td>			
		</tr>
	</table>

	<c:if test="${sessionScope.login.perfil ne '510'}">
		<!-- Perfil Padre de familia -->
		<table width="90%" cellpadding="2" cellSpacing="2" align="center">
			<tr>
				<td class="Fila0" colspan="4" align="left">Por ubicación</td>
			</tr>
			<tr>
				<td><span class="style2">*</span>Metodolog&iacute;a:</td>
				<td>
					<select name="metodologia" style='width: 180px;' onChange='filtro2(document.frm.sede,document.frm.jornada,document.frm.metodologia,document.frm.grado,document.frm.grupo)'>
						<option value='-9'>-- Seleccione una --</option>
						<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila">
							<option value="<c:out value="${fila[0]}"/>"
								<c:if test="${sessionScope.filtrob.metodologia==fila[0]}">SELECTED</c:if>>
								<c:out value="${fila[1]}"/>
							</option>
						</c:forEach>
					</select>
				</td>
				<td><span class="style2">*</span>Grado:</td>
				<td>
					<select name="grado" onChange='filtro3(document.frm.sede, document.frm.jornada,document.frm.grado,document.frm.metodologia,document.frm.grupo)' style='width: 120px;'>
						<option value='-9'>-- Seleccione una --</option>
						<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila">
							<option value="<c:out value="${fila[0]}"/>"
								<c:if test="${sessionScope.filtrob.grado== fila[0]}">SELECTED</c:if>>
								<c:out value="${fila[1]}"/>
							</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>Grupo:</td>
				<td>
					<select name="grupo" style='width: 120px;'>
						<option value='-9'>-- Todos --</option>
					</select>
				</td>
			</tr>
		</table>
	</c:if>
	<!-- Perfil Padre de familia -->
	
	<!-- ESTUDIANTE -->
	<table border="0" align="center" width="90%" cellpadding="2" cellspacing="2">
		<tr>
			<td class="Fila0" colspan="4" align="left">Por estudiante</td>
		</tr>
		
		<c:if test="${sessionScope.login.perfil ne '510'}">
			<tr>
				<td colspan='4'>
					<span class="style2"><b>Nota:<br>Si necesita generar el boletín de un solo estudiante, por favor ingrese el número de identificación del estudiante en el siguiente campo; de lo contrario, no lo diligencie.</b></span>
				</td>
				<td>
					<input type='text' name='escondido'  maxlength="2" style="display:none">
				</td>
			</tr>
		</c:if>
		
		<c:if test="${sessionScope.login.perfil eq '510'}">
			<tr>
				<td>
					<span class="style2" >*</span>Hijos / Estudiantes Registrados a su cargo
				</td>
				<td>
			      	<select style='width:90%;' onchange="seleccionHijo(this);">
			        	<option value='-9'>-- --</option>
				        <c:forEach begin="0" items="${requestScope.listaHijos}" var="hijo">
							<option value="<c:out value="${hijo.estTipoDocumento}"/>-<c:out value="${hijo.estNoDocumento}"/>"><c:out value="${hijo.estNombre1}"/> <c:out value="${hijo.estNombre2}"/> <c:out value="${hijo.estApellido1}"/> <c:out value="${hijo.estApellido2}"/></option>
				        </c:forEach>
			        </select>
	      		</td>
			</tr>
		</c:if>
		<tr>
			<td>
				<span class="style2" >*</span>Tipo Documento:
			</td>
			<td>
		      	<select <c:if test="${sessionScope.login.perfil ne '510'}">name="filTipoDoc"</c:if><c:if test="${sessionScope.login.perfil eq '510'}">name="comboTipoDocumento"</c:if> style='width:60px;' <c:if test="${sessionScope.login.perfil eq '510'}">disabled</c:if>>
			        <option value='-9'>-- --</option>
			        <c:forEach begin="0" items="${requestScope.filtroTiposDoc}" var="vig">
						<option value="<c:out value="${vig.codigo}"/>" <c:if test="${sessionScope.filtrob.filTipoDoc==vig.codigo}">SELECTED</c:if>><c:out value="${vig.nombre}"/></option>
			        </c:forEach>
		        </select>
	      	</td>
			<td>Número de documento:</td>
			<td colspan="1">
				<input type='text' <c:if test="${sessionScope.login.perfil ne '510'}">name='id'</c:if><c:if test="${sessionScope.login.perfil eq '510'}">name='txtNoDocumento'</c:if>  maxlength="15" value='<c:out value="${sessionScope.filtrob.id}"/>' <c:if test="${sessionScope.login.perfil eq '510'}">readonly</c:if>>
			</td>
		</tr>
	</table>
	
	<table border="0" align="center" width="90%" cellpadding="2" cellspacing="0" align="center">
		<tr>
			<td class="Fila0" colspan="4">Datos del Boletín</td>
		</tr>			 
		<tr>
			<td>Mostrar Áreas:</td>
			<td>
				<input type="checkbox" name="mostrarAreas_" id="mostrarAreas_" value='true' CHECKED>
			</td>
			<td>Mostrar Asignaturas:</td>
			<td>
				<input type="checkbox" name="mostrarAsignaturas_" id="mostrarAsignaturas_" value='true' CHECKED>
			</td>
	    </tr>
		<tr>
			<td>Mostrar Descriptores:</td>
			<td>
				<input type="checkbox" name="mostrarDescriptores_" id="mostrarDescriptores_"  CHECKED >
			</td>
			<td>Mostrar Logros:</td>
			<td>
				<input type="checkbox" name="mostrarLogros_"  id="mostrarLogros_" value='true' checked>
			</td> 
		</tr>
		<tr>
			<td>Mostrar Logros Pendientes:</td>
			<td>
				<input type="checkbox" name="conLogrosP" id="conLogrosP" value="1" >
			</td>
		</tr> 
		<tr>
			<td>Mostrar Firma Rector:</td>
			<td>
				<input type="checkbox" name="mostrarFR_" id="mostrarFR_"  CHECKED >
			</td>
			<td>Mostrar Firma Director Grupo:</td>
			<td>
				<input type="checkbox" name="mostrarFD_"  id="mostrarFD_" value='true' checked>
			</td> 
		</tr>
	</table>
	<!--
	<table border="0" align="center" width="98%" cellpadding="2" cellspacing="0">
		<tr>
			<td class="Fila0" colspan="4" align="center">Formato alternativo</td>
		</tr>
		<tr>
			<td>Formato Horizontal:</td>
		  	<td>
		  		<input type="checkbox" name="formatoDos" id="formatoDos" value='true' CHECKED>
		  	</td>
    	</tr>
	</table>
	-->
</form>
	
<script>
	document.frm.metodologia.onchange();
	document.frm.grado.onchange();
</script>
