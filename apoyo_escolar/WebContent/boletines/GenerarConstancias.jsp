<%@ page contentType="text/html; charset=iso-8859-9" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %>
<jsp:useBean id="filtrobcc" class="siges.boletines.beans.FiltroBeanConstancias" scope="session"/><jsp:setProperty name="filtrobcc" property="*"/>
<%@include file="../parametros.jsp"%>
		<script languaje='JavaScript'>
			
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			
  			function hacerValidaciones_frm(forma){
					if (trim(forma.id.value).length == 0){
						validarLista(forma.metodologia, "- Metodologia", 1);
					  validarLista(forma.grado, "- Grado", 1);
					  validarLista(forma.grupo, "- Grupo", 1);
					  validarLista(forma.estudiante, "- Estudiante", 1);
					}else{
						if (trim(forma.id.value).length > 0)
							validarCampo(forma.id, "- Identificación",1,12);
					}					 				
			    validarLista(forma.ano, "- Año", 1);
			    var te;
			        for(var i=0;i<document.frm.tipocons.length;i++){
			            if(document.frm.tipocons[i].checked) 
			            	te=document.frm.tipocons[i].value;
			        }
			        if(te!="2")
					validarCampo(forma.motivo, "- Motivo",1,50);
				}


			function lista(forma){
				forma.tipo.value=3;
				forma.accion.value=1;
				forma.action='<c:url value="/boletines/FiltroEditConstancias.jsp"/>';
				forma.target="";
				forma.submit();
			}
			
			function dismotivo(){
				var mytr2 = document.getElementById('trmotivo');
				mytr2.style.display='none';
			}
			function habmotivo(){
				var mytr2 = document.getElementById('trmotivo');
				mytr2.style.display='block';
			}
			
			
			function seleccionHijo(comboHijos){
				if(comboHijos.value != "-9"){
					var tipoDocumento = comboHijos.value.split("-")[0];
					var numeroDocumento = comboHijos.value.split("-")[1];
					document.frm.id.value=numeroDocumento;
					document.frm.txtNoDocumento.value=numeroDocumento;
				}else{
					document.frm.filTipoDoc.value=-9;
					document.frm.id.value="";
					document.frm.txtNoDocumento.value="";
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
					document.frm.tipo.value=3;
					document.frm.cmd.value="Buscar";
					var te2;
			        for(var i=0;i<document.frm.tipocons.length;i++){
			            if(document.frm.tipocons[i].checked) 
			            	te2=document.frm.tipocons[i].value;
			        }
			        document.frm.tiporep.value=te2;
					document.frm.target="ModuloReportes";
					document.frm.action='<c:url value="/boletines/FiltroGuardarConstancias.jsp"/>';
					window.open("","ModuloReportes","width=800,height=500,menubar=yes,scrollbars=1").focus();
					document.frm.submit();
				}
			}

			
			function cancelar(){
				document.frm.id.value="";
				document.frm.metodologia.selectedIndex=0;
				document.frm.grado.selectedIndex=0;
				document.frm.grupo.selectedIndex=0;
				document.frm.estudiante.selectedIndex=0;
				document.frm.motivo.value="";
				document.frm.ano.selectedIndex=0;
				document.frm.tipo.value=3;
				document.frm.action="<c:url value="/boletines/ControllerBoletinFiltroEdit.do"/>";
				document.frm.target="";
				document.frm.submit();
				/*if (confirm('¿Desea cancelar la generación de constancias?')){
 					document.frm.cmd.value="Cancelar";
					parent.location.href='<c:url value="/bienvenida.jsp"/>';
				}*/
			}
			
			function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
				this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
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
				combo.options[0] = new Option("-- Seleccione una --","-9");
			}
			function filtro(combo_padre,combo_hijo,combo_hijo2,combo_hijo3,combo_hijo4){
				//combo_hijo4.selectedIndex=0;
				if(combo_padre.selectedIndex==0){
					borrar_combo1(combo_hijo); borrar_combo(combo_hijo2); borrar_combo(combo_hijo3); 
				}else{
					borrar_combo(combo_hijo); borrar_combo(combo_hijo2); borrar_combo(combo_hijo3);
					<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">
						var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">
						<c:set var="z" value="${fila[0]}" scope="request"/>
							id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
							<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2">
							<c:if test="${fila[0]==fila2[2]}">
									Sel_Hijos[k] = '<c:if test="${sessionScope.filtrobcc.jornada== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;
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
				if(combo_padre3.selectedIndex==0){
					borrar_combo(combo_hijo);
				}else{
					borrar_combo(combo_hijo);
					<c:if test="${!empty requestScope.filtroGradoF2 && !empty requestScope.filtroMetodologiaF}">;var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"  varStatus="st">
						<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}" var="fila4">
						id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
						<c:forEach begin="0" items="${requestScope.filtroGradoF2}" var="fila3">
							<c:if test="${fila2[2]==fila3[3] && fila2[0]==fila3[4] && fila3[5]==fila4[0]}">
							Sel_Hijos[k] = '<c:if test="${sessionScope.filtrobcc.grado== fila3[0]}">SELECTED</c:if>';
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
									Sel_Hijos[k] = '<c:if test="${sessionScope.filtrobcc.grupo== fila3[0]}">SELECTED</c:if>';
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
		<style type="text/css">
TD.norepeat{
background-image:url(../etc/img/tabs/pestana_1.gif); 
background-repeat:no-repeat;
}

TD.norepeat2{
background-image:url(../etc/img/tabs/pestana_0.gif); 
background-repeat:no-repeat;
cursor:pointer;
}

.sombra {
color:white;
text-shadow:#666666 3px 3px 3px;
filter: progid:DXImageTransform.Microsoft.Shadow(color='#666666', Direction=135, Strength=4);
font-size: 85%;
font-stretch: ultra-expanded;
line-height: 95%;
font-weight: bolder;
}
.sombran {
text-shadow:#666666 3px 3px 3px;
filter: progid:DXImageTransform.Microsoft.Shadow(color='#666666', Direction=135, Strength=4);
font-size: 85%;
font-stretch: ultra-expanded;
line-height: 95%;
font-weight: bolder;
}
</style>
<%@include file="../mensaje.jsp"%>
	<form method="post" name="frm" onSubmit="return validarForma(frm)" action='<c:url value="/boletines/FiltroGuardarConstancias.jsp"/>'>
	<table border="0" align="center" width="100%">
	<caption>Generar Constancias</caption>
		<tr>
			<td width="45%">
      	<input class="boton" name="cmd1" type="button" value="Generar" onClick="guardar(document.frm.tipo.value)">
      	<input class="boton" name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
			</td>
		</tr>
	</table>
<!--//////////////////-->	
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${pageScope.tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<input type="hidden" name="tiporep">
	<input type="hidden" name="accion" value="0">
	<input type="hidden" name="sede" value='<c:out value="${sessionScope.login.sedeId}"/>'>
	<input type="hidden" name="jornada" value='<c:out value="${sessionScope.login.jornadaId}"/>'>
	<INPUT TYPE="hidden" NAME="ext2"  VALUE=''><INPUT TYPE="hidden" NAME="height" VALUE='180'>
	
	<c:if test="${sessionScope.login.perfil eq '510'}">
		<input type="hidden" name="filTipoDoc" value="-9">
		<input type="hidden" name="id" value="">
	</c:if>
	
	
	
	<table border="0" align="center" width="100%">
		<tr height="1">
			<td width="14%" height="26" class="norepeat"><p align="center" class="sombra">Constancias/Paz Y Salvo</p></td>
			<td width="86%" height="26"></td>
		</tr>
  </table>
	<TABLE width="100%" cellpadding="1" cellSpacing="0">
		
		<tr>
			<td colspan='4' class='Fila0' align="center">Tipo de Constancia</td>
		</tr>
		<tr>
			<td><input type="radio" name="tipocons" value="1"
				onClick="habmotivo()" checked> Costancia <input type="radio"
				name="tipocons" value="2" onClick="dismotivo()"> Paz y Salvo
			</td>
		</tr>
		
		<tr>
			<td colspan='4' class='Fila0' align="center">Búsqueda por
				estudiante</td>
		</tr>

		<c:if test="${sessionScope.login.perfil ne '510'}">
			<tr>
				<td colspan='4'><span class="style2"><b>Nota:<br>Si
							necesita generar la constancia de un solo estudiante, por favor
							ingrese el número de identificación del estudiante en el
							siguiente campo; de lo contrario, no lo diligencie.
					</b></span></td>
			</tr>
			<tr>
				<td>Número de Documento:</td>
				<td><input type='text' name='id' maxlength="15"
					value='<c:out value="${sessionScope.filtrobcc.id}"/>'></td>
			</tr>
		</c:if>
		<c:if test="${sessionScope.login.perfil eq '510'}">
			<tr>
				<td><span class="style2">*</span>Hijos/Estudiantes Registrados a su cargo</td>
				<td>
				<select style='width: 90%;' onchange="seleccionHijo(this);">
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
				</select>
				</td>
			</tr>
			<tr>
				<td>Número de documento:</td>
				<td colspan="1"><input type='text'
					<c:if test="${sessionScope.login.perfil ne '510'}">name='id'</c:if>
					<c:if test="${sessionScope.login.perfil eq '510'}">name='txtNoDocumento'</c:if>
					maxlength="15" value='<c:out value="${sessionScope.filtrobcc.id}"/>'
					<c:if test="${sessionScope.login.perfil eq '510'}">readonly</c:if>>
				</td>
			</tr>
		</c:if>

		<c:if test="${sessionScope.login.perfil ne '510'}">
			<tr>
				<td colspan='4' class='Fila0' align="center">Búsqueda por
					ubicación</td>
			</tr>
			<tr>
				<td><span class="style2">*</span>Metodolog&iacute;a:</td>
				<td><select name="metodologia" style='width: 120px;'
					onChange='filtro2(document.frm.sede,document.frm.jornada,document.frm.metodologia,document.frm.grado,document.frm.grupo)'>
						<option value='-9'>-- Seleccione una --</option>
						<c:forEach begin="0" items="${requestScope.filtroMetodologiaF}"
							var="fila">
							<option value="<c:out value="${fila[0]}"/>"
								<c:if test="${sessionScope.filtrobcc.metodologia==fila[0]}">SELECTED</c:if>>
								<c:out value="${fila[1]}" />
							</option>
						</c:forEach>
				</select></td>
				<td><span class="style2">*</span>Grado:</td>
				<td><select name="grado"
					onChange='filtro3(document.frm.sede, document.frm.jornada,document.frm.grado,document.frm.metodologia,document.frm.grupo)'
					style='width: 120px;'>
						<option value='-9'>-- Todos --</option>
						<c:forEach begin="0" items="${requestScope.filtroGradoF2}"
							var="fila">
							<option value="<c:out value="${fila[0]}"/>"
								<c:if test="${sessionScope.filtrobcc.grado== fila[0]}">SELECTED</c:if>>
								<c:out value="${fila[1]}" />
							</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td><span class="style2">*</span>Grupo:</td>
				<td><select name="grupo" style='width: 120px;'
					onChange="lista(document.frm)">
						<option value='-9'>-- Seleccione uno --</option>
				</select></td>
			</tr>
			<tr>
				<td><span class="style2">*</span>Estudiante:</td>
				<td colspan="3"><select name="estudiante" style='width: 300px;'>
						<option value='-9'>-- Seleccione uno --</option>
						<c:forEach begin="0" items="${requestScope.estudiantes}"
							var="fila">
							<option value='<c:out value="${fila[0]}"/>'>
								<c:out value="${fila[1]}" />
								<c:out value="${fila[2]}" />
								<c:out value="${fila[3]}" />
							</option>
						</c:forEach>
				</select></td>
			</tr>
		</c:if>
		<tr id="trmotivo">
			<td><span class="style2">*</span> Motivo:</td>
			<td><input name='motivo' type='text'
				value='<c:out value="${sessionScope.filtrobcc.motivo}"/>' maxlength="50">
			</td>
		</tr>
		<tr>
			<td><span class="style2">*</span>A&ntilde;o:</td>
			<td><select name="ano" style='width: 70px;'>
					<option value='-9'>-- Seleccione una --</option>
					<c:forEach begin="2005" end="${requestScope.AnioActual}" var="fila">
						<option value="<c:out value="${fila}"/>"
							<c:if test="${sessionScope.filtrobcc.ano==fila}">SELECTED</c:if>>
							<c:out value="${fila}" />
					</c:forEach>
			</select></td>
		</tr>
	</TABLE>
	<!--//////////////////////////////-->
</form>
<script>
document.frm.metodologia.onchange();
document.frm.grado.onchange();
<c:if test="${!empty requestScope.mensaje}">
document.frm.grupo.onchange();
</c:if>
</script>