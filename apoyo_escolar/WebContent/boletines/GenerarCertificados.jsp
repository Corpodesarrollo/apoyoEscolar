<%@ page contentType="text/html; charset=iso-8859-9" language="java" errorPage="" %>
<%@ page import="siges.util.Recursos" %>

<!--
	VERSION		FECHA			AUTOR			DESCRIPCION
		1.0		08/03/2021		JORGE CAMACHO	Se ajustó la validación de la longitud del número de identificación para que reciba hasta 20 dígitos
-->

<jsp:useBean id="filtroc" class="siges.boletines.beans.FiltroBeanReports" scope="session"/>
<jsp:setProperty name="filtroc" property="*"/>

<%@include file="../parametros.jsp"%>

	<script languaje='JavaScript'>
			
		var nav4=window.Event ? true : false;
		function acepteNumeros(eve){
			var key=nav4?eve.which :eve.keyCode;
			return(key<=13 || (key>=48 && key<=57));
		}
			
		function hacerValidaciones_frm(forma){
			<c:if test="${sessionScope.login.perfil ne '510'}">
			validarLista(forma.filVigencia, "- Vigencia", 1);
			if (trim(forma.id.value).length == 0){
				validarLista(forma.metodologia, "- Metodologia", 1);					
				validarLista(forma.grado, "- Grado", 1);
				validarLista(forma.tipoProm, "- Tipo Promoción", 1);
		  	} else {
				if (trim(forma.id.value).length > 0)
					validarCampo(forma.id, "- Identificación",1,20);
					
				validarLista(forma.filTipoDoc, "- Tipo Documento", 1);
				validarLista(forma.tipoProm, "- Tipo Promoción", 1);
		  	}
			</c:if>
		 
		}
			
		function seleccionHijo(comboHijos){
			if(comboHijos.value != "-9"){
				var tipoDocumento = comboHijos.value.split("-")[0];
				var numeroDocumento = comboHijos.value.split("-")[1];
				document.frm.filTipoDoc.value=tipoDocumento;
				document.frm.id.value=numeroDocumento;
				document.frm.txtNoDocumento.value=numeroDocumento;
				document.frm.comboTipoDocumento.value=tipoDocumento;
			}else{
				document.frm.filTipoDoc.value=-9;
				document.frm.id.value="";
				document.frm.txtNoDocumento.value="";
				document.frm.comboTipoDocumento.value=-9;
			}
		}
			
		function lanzar(tipo){	
			document.frm.tipo.value=tipo;					
			document.frm.action="<c:url value="/boletines/ControllerBoletinFiltroEdit.do"/>";
			document.frm.target="";
			document.frm.submit();
		}

		function guardar(tipo){
			if(validarForma(document.frm)){
				document.frm.tipo.value=2;
				document.frm.cmd.value="Buscar";
				if(document.frm.id.value == ''){
				    document.frm.id.value = ' ';
				  }
				window.open("","ModuloReportes","width=800,height=500,menubar=yes,scrollbars=1").focus();
				document.frm.submit();			
			}	
		}

		function cancelar(){
			document.frm.filVigencia.selectedIndex=0;
			document.frm.metodologia.selectedIndex=0;
			document.frm.grado.selectedIndex=0;
			document.frm.grupo.selectedIndex=0;
			document.frm.tipoProm.selectedIndex=0;
		}
		
		function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
			this.id_Hijos = id_Hijos;
			this.Hijos = Hijos;
			this.Sel_Hijos = Sel_Hijos;
			this.id_Padre =id_Padre;
		}
		
		function Padre2(id_Hijos, Hijos, Sel_Hijos,id_Padre,id_Padre2){
			this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre; this.id_Padre2=id_Padre2;
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
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("--Seleccione una --","-9");
			}
			function filtro(combo_padre,combo_hijo,combo_hijo2,combo_hijo3,combo_hijo4){
				combo_hijo4.selectedIndex=0;
				if(combo_padre.selectedIndex==0){
					borrar_combo1(combo_hijo); borrar_combo1(combo_hijo2); borrar_combo1(combo_hijo3); 
				}else{
					borrar_combo(combo_hijo); borrar_combo(combo_hijo2); borrar_combo(combo_hijo3);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">
						var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">
						<c:set var="z" value="${fila[0]}" scope="request"/>
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2">
							<c:if test="${fila[0]==fila2[2]}">
									Sel_Hijos[k] = '<c:if test="${sessionScope.filtrobc.jornada== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;
							</c:if></c:forEach>
							Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
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
			
			
							//sede, jornada,metodologia,grado,grupo	
			function filtro2(combo_padre,combo_padre2,combo_padre3,combo_hijo,combo_hijo2){
				var id=0;
				if(combo_padre3.selectedIndex==0)
					borrar_combo(combo_hijo);
			    else{
					borrar_combo(combo_hijo);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroMetodologiaF}">;var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"  varStatus="st">
						<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila4">
						id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
						<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3">
							<c:if test="${fila2[2]==fila3[3] && fila2[0]==fila3[4] && fila3[5]==fila4[0]}">
							Sel_Hijos[k] = '<c:if test="${sessionScope.filtrobc.grado== fila3[0]}">SELECTED</c:if>';
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
			
			function filtro3(combo_padre,combo_padre2,combo_padre3,combo_padre4,combo_hijo){
				if(combo_padre3.selectedIndex==0)
					borrar_combo(combo_hijo); else{
					borrar_combo(combo_hijo);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF && !empty requestScope.filtroGradoF2 && !empty requestScope.filtroGrupoF}">
						var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila2"  varStatus="st">
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); id_Padre2= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroGrupoF}" var="fila3">
								<c:if test="${fila2[0]==fila3[4] && fila2[3]==fila3[2] && fila2[4]==fila3[3] && fila2[5]==fila3[5]}">
									Sel_Hijos[k] = '<c:if test="${sessionScope.filtrobc.grupo== fila3[0]}">SELECTED</c:if>';
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
<form method="post" name="frm" onSubmit="return validarForma(frm)"
	action='<c:url value="/boletines/FiltroGuardarCertificados.jsp"/>'
	target="ModuloReportes">
	<table border="0" align="center" width="100%">
		<caption>Generar Certificados</caption>
		<tr>
			<td width="45%" bgcolor="#FFFFFF"><input class="boton"
				name="cmd1" type="button" value="Generar"
				onClick="guardar(document.frm.tipo.value)"> <input
				class="boton" name="cmd12" type="button" value="Cancelar"
				onClick="cancelar()"></td>
		</tr>
	</table>
	<!--//////////////////-->
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo"
		value='<c:out value="${pageScope.tip}"/>'> <input
		type="hidden" name="cmd" value="Cancelar"> <input
		type="hidden" name="sede"
		value='<c:out value="${sessionScope.login.sedeId}"/>'> <input
		type="hidden" name="jornada"
		value='<c:out value="${sessionScope.login.jornadaId}"/>'> <INPUT
		TYPE="hidden" NAME="ext2" VALUE=''><INPUT TYPE="hidden"
		NAME="height" VALUE='180'>

	<c:if test="${sessionScope.login.perfil eq '510'}">
		<input type="hidden" name="filTipoDoc" value="-9">
		<input type="hidden" name="id" value="">
		<input type="hidden" name="filVigencia" value='<c:out value = "${sessionScope.login.vigencia_actual}"/>'>
	</c:if>


	<table border="0" align="center" width="100%">
		<tr height="1">
			<td rowspan="2" width="420" bgcolor="#FFFFFF"><img
				src="../etc/img/tabs/Certificados_1.gif"
				alt="Generaci&oacute;n de Certificados" width="84" height="26"
				border="0"></td>
		</tr>
	</table>


	<table border="0" align="center" width="98%" cellpadding="2"
		cellspacing="2">
		<caption class="Fila0" colspan="4" align="center">
			<b>Filtro Generación Certificados</b>
		</caption>
	</table>
	<c:if test="${sessionScope.login.perfil ne '510'}">
	<table border="0" align="center" width="90%" cellpadding="2"
		cellspacing="2">
		<tr>
			<td><span class="style2">*</span>Vigencia:</td>
			<td><select name="filVigencia" style='width: 120px;'>
					<option value='-9'>-- Seleccione una --</option>
					<c:forEach begin="0" items="${requestScope.filtroVigencia}"
						var="vig">
						<option value="<c:out value="${vig.codigo}"/>"
							<c:if test="${sessionScope.filtroc.filVigencia==vig.codigo}">SELECTED</c:if>>
							<c:out value="${vig.nombre}" />
						</option>
					</c:forEach>
			</select></td>
		</tr>
	</table>

	
		<TABLE width="90%" cellpadding="2" cellSpacing="2" align="center">
			<tr>
				<td class="Fila0" colspan="4" align="left">Por ubicación</td>
			</tr>
			<tr>
				<td><span class="style2">*</span>Metodolog&iacute;a:</td>
				<td><select name="metodologia" style='width: 180px;'
					onChange='filtro2(document.frm.sede,document.frm.jornada,document.frm.metodologia,document.frm.grado,document.frm.grupo)'>
						<option value='-9'>-- Seleccione una --</option>
						<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}"
							var="fila">
							<option value="<c:out value="${fila[0]}"/>"
								<c:if test="${sessionScope.filtroc.metodologia==fila[0]}">SELECTED</c:if>>
								<c:out value="${fila[1]}" />
							</option>
						</c:forEach>
				</select></td>

				<td><span class="style2">*</span>Grado:</td>
				<td><select name="grado"
					onChange='filtro3(document.frm.sede, document.frm.jornada,document.frm.grado,document.frm.metodologia,document.frm.grupo)'
					style='width: 120px;'>
						<option value='-9'>-- Seleccione una --</option>
						<c:forEach begin="0" items="${requestScope.filtroGradoF2}"
							var="fila">
							<option value="<c:out value="${fila[0]}"/>"
								<c:if test="${sessionScope.filtroc.grado== fila[0]}">SELECTED</c:if>>
								<c:out value="${fila[1]}" />
							</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>

				<td>Grupo:</td>
				<td><select name="grupo" style='width: 120px;'>
						<option value='-9'>-- Todos --</option>
				</select></td>



				<td><span class="style2">*</span>Tipo de Promoción</td>
				<td><select name='tipoProm' id='tipoProm'>
						<option value='-3'>-- Seleccione uno --</option>
						<c:forEach begin="0" items="${requestScope.filtroTipoProm}"
							var="item">
							<option value="<c:out value="${item.codigo}"/>"
								<c:if test="${sessionScope.filtroc.tipoProm ==item.codigo}">SELECTED</c:if>>
								<c:out value="${item.nombre}" />
							</option>
						</c:forEach>
				</select></td>
			</tr>
		</TABLE>
	</c:if>
	<!-- ESTUDIANTE -->
	<table border="0" align="center" width="90%" cellpadding="2"
		cellspacing="2">

		<tr>
			<td class="Fila0" colspan="4" align="left">Por estudiante</td>
		</tr>
		<c:if test="${sessionScope.login.perfil ne '510'}">
			<tr>
				<td colspan='4'><span class="style2"><b>Nota:<br>Si
							necesita generar el certificado de un solo estudiante, por favor
							ingrese el número de identificación del estudiante en el
							siguiente campo; de lo contrario, no lo diligencie.
					</b></span></td>
				<td><input type='text' name='escondido' maxlength="2"
					style="display: none"></td>
			</tr>
		</c:if>
		<c:if test="${sessionScope.login.perfil eq '510'}">
			<tr>
				<td><span class="style2">*</span>Hijos/Estudiantes Registrados
					a su cargo</td>
				<td><select style='width: 90%;' onchange="seleccionHijo(this);">
						<option value='-9'>-- --</option>
						<c:forEach begin="0" items="${requestScope.listaHijos}" var="hijo">
							<option
								value="<c:out value="${hijo.estTipoDocumento}"/>-<c:out value="${hijo.estNoDocumento}"/>">
								<c:out value="${hijo.estNombre1}" />
								<c:out value="${hijo.estNombre2}" />
								<c:out value="${hijo.estApellido1}" />
								<c:out value="${hijo.estApellido2}" />
							</option>
						</c:forEach>
				</select></td>
			</tr>
		</c:if>
		<tr>
			<td><span class="style2">*</span>Tipo Documento:</td>
			<td>
			<select style='width: 60px;'
				<c:if test="${sessionScope.login.perfil ne '510'}">name="filTipoDoc"</c:if>
				<c:if test="${sessionScope.login.perfil eq '510'}">name="comboTipoDocumento"</c:if>
				<c:if test="${sessionScope.login.perfil eq '510'}">disabled</c:if>>
					<option value='-9'>-- --</option>
					<c:forEach begin="0" items="${requestScope.filtroTiposDoc}"	var="vig">
						<option value="<c:out value="${vig.codigo}"/>"
							<c:if test="${sessionScope.filtrob.filTipoDoc==vig.codigo}">SELECTED</c:if>>
							<c:out value="${vig.nombre}" />
						</option>
					</c:forEach>
			</select></td>
			<td>Número de documento:</td>
			<td colspan="1"><input type='text' 
				<c:if test="${sessionScope.login.perfil ne '510'}">name='id'</c:if>
				<c:if test="${sessionScope.login.perfil eq '510'}">name='txtNoDocumento'</c:if>
				value='<c:out value="${sessionScope.filtrob.id}"/>'
				<c:if test="${sessionScope.login.perfil eq '510'}">readonly</c:if>></td>

		</tr>
	</table>


</form>
<script>
document.frm.metodologia.onchange();
document.frm.grado.onchange();
</script>