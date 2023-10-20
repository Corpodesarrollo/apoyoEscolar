<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %><%@ page import="siges.util.Recursos" %> 
  <jsp:useBean id="nuevoInstitucion" class="siges.institucion.beans.Institucion" scope="session"/><jsp:setProperty name="nuevoInstitucion" property="*"/>
  <jsp:useBean id="nuevoJornada" class="siges.institucion.beans.Jornada" scope="session"/><jsp:setProperty name="nuevoJornada" property="*"/>
  <jsp:useBean id="nuevoSede" class="siges.institucion.beans.Sede" scope="session"/><jsp:setProperty name="nuevoSede" property="*"/>
  <jsp:useBean id="nuevoEspacio" class="siges.institucion.beans.Espacio" scope="session"/><jsp:setProperty name="nuevoEspacio" property="*"/>
  <jsp:useBean id="nuevoNivel" class="siges.institucion.beans.Nivel" scope="session"/><jsp:setProperty name="nuevoNivel" property="*"/>  
	<jsp:useBean id="nuevoTransporte" class="siges.institucion.beans.Transporte" scope="session"/><jsp:setProperty name="nuevoTransporte" property="*"/>
  <jsp:useBean id="nuevoCafeteria" class="siges.institucion.beans.Cafeteria" scope="session"/><jsp:setProperty name="nuevoCafeteria" property="*"/>
  <jsp:useBean id="nuevoConflicto" class="siges.institucion.beans.ConflictoEscolar" scope="session"/>

<%@include file="../parametros.jsp"%>
		<c:if test="${sessionScope.NivelPermiso==1}"><c:set var="permisoBoton" value="none"/></c:if>
		<script languaje='javaScript'>			
			<!--
			function  mostrarOtro(){
				if(document.getElementById("otro")){
					document.getElementById("otro").style.display='';
					document.getElementById("otrotxt").focus();
			  }
			}
			
			var fichaBasico=1;
			var fichaSede=1;
			var fichaEspacio=1;
			var fichaTransporte=0;
			var fichaCafeteria=0;
			var fichaConflicto=0;
		  var nav4=window.Event ? true : false;
		  function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
		  }
			
			function cancelar(){
				//if (confirm('¿DESEA CANCELAR LA INSERCIÓN DE UN NUEVO REGISTRO?')){
					document.frmNuevo.cmd.value="Cancelar";
					document.frmNuevo.submit();
				//}
			}
			
			function hacerValidaciones_frmNuevo(forma){				
				validarLista(forma.restiposerv, "- Tipo de servicio",1)
				validarCampo(forma.resdescripcion, "- Descripción",1,200)
				validarCampo(forma.reshorario, "- Horario", 1, 20)
				validarCampo(forma.rescosto, "- Costo", 1, 5)
			}
			
			function inhabilitarFormulario(){
				for(var i=0;i<document.frmNuevo.elements.length;i++){
					if(document.frmNuevo.elements[i].type != "hidden" && document.frmNuevo.elements[i].type != "button" && document.frmNuevo.elements[i].type != "submit")
					document.frmNuevo.elements[i].disabled=true;
				}	
			}
			
			function lanzar(tipo){				
				document.frmNuevo.tipo2.value=tipo;
				document.frmNuevo.action="<c:url value="/institucion/ControllerNuevoEdit.do"/>";
				document.frmNuevo.submit();
			}
			
			function guardar(tipo){
				alert("La información fue guardada satisfactoriamente")
				/*if(validarForma(document.frmNuevo)){
					document.frmNuevo.tipo2.value=tipo;
					document.frmNuevo.cmd.value="Guardar";
					document.frmNuevo.submit();
				}*/
			}
			
			function editar(tipo,nombre){	
				document.frm.tipo2.value=tipo;
				document.frm.cmd.value=nombre;
				if(document.frm.cmd.value=='Eliminar'){
					if (isChecked(document.frm.r)){
						if(!confirm("Realmente desea eliminarlo?"))
							return false;
					}
				}
				return true;	
			}
			
			function hacerValidaciones_frm(forma){
				if(forma.cmd.value!='Nuevo')
					validarSeleccion(forma.r, "- Debe seleccionar un item")
			}
			-->
</script>
	<%@include file="../mensaje.jsp"%>
	<font size="1">
		<form method="post" name="frmNuevo" onSubmit=" return validarForma(frmNuevo)"  action='<c:url value="/institucion/NuevoGuardar.jsp"/>'>
		<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
			<tr>		
				<td width="45%" bgcolor="#FFFFFF">
        <input class='boton' name="cmd1" type="button" value="Guardar" onClick="guardar(7)"  style='display:<c:out value="${permisoBoton}"/>'>
				</td> 
			</tr>		
		</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo2" value="7">
	<input type="hidden" name="cmd" value="Cancelar"><INPUT TYPE="hidden" NAME="height" VALUE='150'>
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe2}"/>'>
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10" bgcolor="#FFFFFF">&nbsp;</td>			
				<td rowspan="2" width="600" bgcolor="#FFFFFF"><script>if(fichaBasico==1)document.write('<a href="javaScript:lanzar(1)"><img src="../etc/img/tabs/datos_basicos_0.gif" alt="Inf B&aacute;sica"  height="26" border="0"></a>');if(fichaSede==1)document.write('<a href="javaScript:lanzar(2)"><img src="../etc/img/tabs/sedes_0.gif" alt="Sedes"  height="26" border="0"></a>');if(fichaEspacio==1)document.write('<a href="javaScript:lanzar(3)"><img src="../etc/img/tabs/espacios_fisicos_0.gif" alt="Espacios Fisicos"  height="26" border="0"></a>');if(fichaTransporte==1)document.write('<a href="javaScript:lanzar(6)"><img src="../etc/img/tabs/transporte_0.gif" alt="transporte"  height="26" border="0"></a>');if(fichaCafeteria==1)document.write('<img src="../etc/img/tabs/cafeteria_1.gif" alt="Cafeteria"  height="26" border="0">');if(fichaConflicto==1)document.write('<img src="../etc/img/tabs/conflicto_escolar_1.gif" alt="Conflicto escolar" border="0"  height="26">');</script></td>
			</tr>
		</table>
		<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
		<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td width="1" bgcolor="#000000"><img src="../etc/img/pixel.gif" width="1" height="1"></td>
				<td>
					<TABLE width="100%" cellpadding="0" cellSpacing="10">
					<caption>SEDE: <c:out value="${sessionScope.nuevoConflicto.cesednom}"/> -  PERIODO: <c:out value="${sessionScope.nuevoConflicto.cepernom}"/></caption>
						<tr>
							<td><font style="font-size:13px;color:#003366;">1. Tipos de Conflicto:</font></td>
						</tr>
						<tr>	
							<td>
								<TABLE width="90%" cellpadding="3" cellSpacing="0" align="right">
									<tr><td>
										<table border="0">
											<tr>
												<th rowspan="2">Marque los que se presentan</th>
												<th>Tipos de Conflicto</th>
												<th colspan="5" align="center">Solución Alcanzada</th>
											</tr>
											<tr>
												<th>Dinámica Urbana y Conflicto Armado</th>
												<th>Ninguno</th>
												<th>Poco</th>
												<th>Regular</th>
												<th>Bueno</th>
												<th>Excelente</th>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>1.	Porte y tráfico de armas</td>
												<td align="center"><input type="radio" name="11"/></td>
												<td align="center"><input type="radio" name="11"/></td>
												<td align="center"><input type="radio" name="11"/></td>
												<td align="center"><input type="radio" name="11"/></td>
												<td align="center"><input type="radio" name="11"/></td>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>2.	Porte y tráfico de drogas</td>
												<td align="center"><input type="radio" name="12"/></td>
												<td align="center"><input type="radio" name="12"/></td>
												<td align="center"><input type="radio" name="12"/></td>
												<td align="center"><input type="radio" name="12"/></td>
												<td align="center"><input type="radio" name="12"/></td>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>3.	Maltrato y violencia intrafamiliar</td>
												<td align="center"><input type="radio" name="13"/></td>
												<td align="center"><input type="radio" name="13"/></td>
												<td align="center"><input type="radio" name="13"/></td>
												<td align="center"><input type="radio" name="13"/></td>
												<td align="center"><input type="radio" name="13"/></td>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>4.	Pandillas, galladas, parches, combos, bandolas, bandas</td>
												<td align="center"><input type="radio" name="14"/></td>
												<td align="center"><input type="radio" name="14"/></td>
												<td align="center"><input type="radio" name="14"/></td>
												<td align="center"><input type="radio" name="14"/></td>
												<td align="center"><input type="radio" name="14"/></td>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>5.	Abuso y explotación sexual</td>
												<td align="center"><input type="radio" name="15"/></td>
												<td align="center"><input type="radio" name="15"/></td>
												<td align="center"><input type="radio" name="15"/></td>
												<td align="center"><input type="radio" name="15"/></td>
												<td align="center"><input type="radio" name="15"/></td>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>6.	Tráfico de personas</td>
												<td align="center"><input type="radio" name="16"/></td>
												<td align="center"><input type="radio" name="16"/></td>
												<td align="center"><input type="radio" name="16"/></td>
												<td align="center"><input type="radio" name="16"/></td>
												<td align="center"><input type="radio" name="16"/></td>
											</tr>
											<tr>
												<th rowspan="2">Marque los que se presentan</th>
												<th>Tipos de Conflicto</th>
												<th colspan="5" align="center">Solución Alcanzada</th>
											</tr>
											<tr>
												<th>Entre pares de estudiantes</th>
												<th>Ninguno</th>
												<th>Poco</th>
												<th>Regular</th>
												<th>Bueno</th>
												<th>Excelente</th>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>1.	Agresiones f&iacute;sicas con armas</td>
												<td align="center"><input type="radio" name="21"/></td>
												<td align="center"><input type="radio" name="21"/></td>
												<td align="center"><input type="radio" name="21"/></td>
												<td align="center"><input type="radio" name="21"/></td>
												<td align="center"><input type="radio" name="21"/></td>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>2.	Encuentros de grupos a la salida del colegio y en parques</td>
												<td align="center"><input type="radio" name="22"/></td>
												<td align="center"><input type="radio" name="22"/></td>
												<td align="center"><input type="radio" name="22"/></td>
												<td align="center"><input type="radio" name="22"/></td>
												<td align="center"><input type="radio" name="22"/></td>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>3.	Robos</td>
												<td align="center"><input type="radio" name="23"/></td>
												<td align="center"><input type="radio" name="23"/></td>
												<td align="center"><input type="radio" name="23"/></td>
												<td align="center"><input type="radio" name="23"/></td>
												<td align="center"><input type="radio" name="23"/></td>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>4.	Amenazas</td>
												<td align="center"><input type="radio" name="24"/></td>
												<td align="center"><input type="radio" name="24"/></td>
												<td align="center"><input type="radio" name="24"/></td>
												<td align="center"><input type="radio" name="24"/></td>
												<td align="center"><input type="radio" name="24"/></td>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>5.	Intimidaci&oacute;n</td>
												<td align="center"><input type="radio" name="25"/></td>
												<td align="center"><input type="radio" name="25"/></td>
												<td align="center"><input type="radio" name="25"/></td>
												<td align="center"><input type="radio" name="25"/></td>
												<td align="center"><input type="radio" name="25"/></td>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>6.	Golpes a los m&aacute;s peque&ntilde;os</td>
												<td align="center"><input type="radio" name="26"/></td>
												<td align="center"><input type="radio" name="26"/></td>
												<td align="center"><input type="radio" name="26"/></td>
												<td align="center"><input type="radio" name="26"/></td>
												<td align="center"><input type="radio" name="26"/></td>
											</tr>
											<tr>
												<th rowspan="2">Marque los que se presentan</th>
												<th>Tipos de Conflicto</th>
												<th colspan="5" align="center">Solución Alcanzada</th>
											</tr>
											<tr>
												<th>Entre autoridad y estudiantes</th>
												<th>Ninguno</th>
												<th>Poco</th>
												<th>Regular</th>
												<th>Bueno</th>
												<th>Excelente</th>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>1.	P&eacute;rdida del a&ntilde;o o de asignaturas</td>
												<td align="center"><input type="radio" name="31"/></td>
												<td align="center"><input type="radio" name="31"/></td>
												<td align="center"><input type="radio" name="31"/></td>
												<td align="center"><input type="radio" name="31"/></td>
												<td align="center"><input type="radio" name="31"/></td>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>2.	Castigo f&iacute;sico o verbal</td>
												<td align="center"><input type="radio" name="32"/></td>
												<td align="center"><input type="radio" name="32"/></td>
												<td align="center"><input type="radio" name="32"/></td>
												<td align="center"><input type="radio" name="32"/></td>
												<td align="center"><input type="radio" name="32"/></td>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>3.	Indisciplina</td>
												<td align="center"><input type="radio" name="33"/></td>
												<td align="center"><input type="radio" name="33"/></td>
												<td align="center"><input type="radio" name="33"/></td>
												<td align="center"><input type="radio" name="33"/></td>
												<td align="center"><input type="radio" name="33"/></td>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>4.	Abuso de autoridad</td>
												<td align="center"><input type="radio" name="34"/></td>
												<td align="center"><input type="radio" name="34"/></td>
												<td align="center"><input type="radio" name="34"/></td>
												<td align="center"><input type="radio" name="34"/></td>
												<td align="center"><input type="radio" name="34"/></td>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>5.	Irrespeto a la autoridad y a las normas</td>
												<td align="center"><input type="radio" name="35"/></td>
												<td align="center"><input type="radio" name="35"/></td>
												<td align="center"><input type="radio" name="35"/></td>
												<td align="center"><input type="radio" name="35"/></td>
												<td align="center"><input type="radio" name="35"/></td>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>6.	Desescolarizaci&oacute;n</td>
												<td align="center"><input type="radio" name="36"/></td>
												<td align="center"><input type="radio" name="36"/></td>
												<td align="center"><input type="radio" name="36"/></td>
												<td align="center"><input type="radio" name="36"/></td>
												<td align="center"><input type="radio" name="36"/></td>
											</tr>
											<tr>
												<th rowspan="2">Marque los que se presentan</th>
												<th>Tipos de Conflicto</th>
												<th colspan="5" align="center">Solución Alcanzada</th>
											</tr>
											<tr>
												<th>Entre adultos</th>
												<th>Ninguno</th>
												<th>Poco</th>
												<th>Regular</th>
												<th>Bueno</th>
												<th>Excelente</th>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>1.	Entre rector y docentes</td>
												<td align="center"><input type="radio" name="41"/></td>
												<td align="center"><input type="radio" name="41"/></td>
												<td align="center"><input type="radio" name="41"/></td>
												<td align="center"><input type="radio" name="41"/></td>
												<td align="center"><input type="radio" name="41"/></td>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>2.	Entre docentes y padres y madres</td>
												<td align="center"><input type="radio" name="42"/></td>
												<td align="center"><input type="radio" name="42"/></td>
												<td align="center"><input type="radio" name="42"/></td>
												<td align="center"><input type="radio" name="42"/></td>
												<td align="center"><input type="radio" name="42"/></td>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>3.	Entre rector y padres y madres</td>
												<td align="center"><input type="radio" name="43"/></td>
												<td align="center"><input type="radio" name="43"/></td>
												<td align="center"><input type="radio" name="43"/></td>
												<td align="center"><input type="radio" name="43"/></td>
												<td align="center"><input type="radio" name="43"/></td>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>4.	Entre docentes</td>
												<td align="center"><input type="radio" name="44"/></td>
												<td align="center"><input type="radio" name="44"/></td>
												<td align="center"><input type="radio" name="44"/></td>
												<td align="center"><input type="radio" name="44"/></td>
												<td align="center"><input type="radio" name="44"/></td>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>5.	Entre docentes y coordinadores</td>
												<td align="center"><input type="radio" name="45"/></td>
												<td align="center"><input type="radio" name="45"/></td>
												<td align="center"><input type="radio" name="45"/></td>
												<td align="center"><input type="radio" name="45"/></td>
												<td align="center"><input type="radio" name="45"/></td>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>6.	Entre rector y orientados</td>
												<td align="center"><input type="radio" name="46"/></td>
												<td align="center"><input type="radio" name="46"/></td>
												<td align="center"><input type="radio" name="46"/></td>
												<td align="center"><input type="radio" name="46"/></td>
												<td align="center"><input type="radio" name="46"/></td>
											</tr>
											<tr>
												<td><input type="checkbox"/></td>
												<td>7.	Entre coordinador y orientador</td>
												<td align="center"><input type="radio" name="47"/></td>
												<td align="center"><input type="radio" name="47"/></td>
												<td align="center"><input type="radio" name="47"/></td>
												<td align="center"><input type="radio" name="47"/></td>
												<td align="center"><input type="radio" name="47"/></td>
											</tr>
										</table>
									</td></tr>
							  </TABLE>
							</td>
						</tr>
						<tr><td><hr/></td></tr>
						<tr><td><font style="font-size:13px;color:#003366;">2. Formas comunes de resolver conflictos: </font></td></tr>
						<tr><td></td></tr>
						<tr>
						<td>
							<table border="0">
								<tr><th rowspan="2">Formas comunes de resolver conflictos</th><th colspan="4">Clase de Conflicto</th></tr>
								<tr><th>Dinámica Urbana</th><th>Entre pares de estudiantes</th><th>Entre autoridad y estudiantes</th><th>Entre adultos</th></tr>
								<tr><td></td></tr>
								<tr><td> a.	Represión: </td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td></tr>
								<tr><td> b.	Conciliación: </td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td></tr>
								<tr><td> c.	Diálogo: </td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td></tr>
								<tr><td> d.	Sanción: </td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td></tr>
								<tr><td> e.	Traslado: </td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td></tr>
								<tr><td> f.	Pactos: </td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td></tr>
								<tr><td> g.	Compromisos firmados: </td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td></tr>
								<tr><td> h.	Mediación: </td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td></tr>
								<tr><td> i.	Utilización pedagógico: </td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td></tr>
								<tr><td> j.	Reconciliación: </td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td></tr>
								<tr><td> k.	Denuncio: </td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td><td align="center"><input type="checkbox" name="resconf"/></td></tr>
								<tr><td> l.	Otro: </td><td align="center"><input type="checkbox" name="resconf" onclick="mostrarOtro()"/></td><td align="center"><input type="checkbox" name="resconf" onclick="mostrarOtro()"/></td><td align="center"><input type="checkbox" name="resconf" onclick="mostrarOtro()"/></td><td align="center"><input type="checkbox" name="resconf" onclick="mostrarOtro()"/></td></tr>
							</table>
							<table border="0" id="otro" style="display:none" align="right">
								<tr><td align="right">   Cuál? </td><td colspan="4"><input type="text" name="otrotxt" size="80"/></td></tr>
							</table>
						</td></tr>
						<tr><td><hr></td></tr>
						<tr>
							<td><font style="font-size:13px;color:#003366;">3. Medidas que está tomando la autoridad: </font></td>
						</tr>
						<tr>
							<td>
								<TABLE width="90%" cellpadding="3" cellSpacing="0" align="right">
									<tr><th>Medidas</th><th>N&uacute;mero de veces aplicada durante este período</th></tr>
									<tr><td>a.	Buscar apoyo / mediación de terceros: Organizaciones no gubernamentales, organismos de control, grupos mediadores.</td><td align="center"><input type="text" size="5" onKeyPress='return acepteNumeros(event)'/></td></tr>
									<tr><td>b.	Talleres de aprendizaje de tratamiento del conflicto</td><td align="center"><input type="text" size="5"/></td></tr>
									<tr><td>c.	Constitución de forma participativa des estaciones, mecanismos, instancias y procedimientos para la resolución de conflictos</td><td align="center"><input type="text" size="5" onKeyPress='return acepteNumeros(event)'/></td></tr>
									<tr><td>d.	Establecimiento de procesos participativos interinstitucionales para la solución</td><td align="center"><input type="text" size="5" onKeyPress='return acepteNumeros(event)'/></td></tr>
									<tr><td>e.	Solicitud de participación de fuerza pública y policiales</td><td align="center"><input type="text" size="5" onKeyPress='return acepteNumeros(event)'/></td></tr>
									<tr><td>f.	Actividades artísticas, lúdicas y culturales.</td><td align="center"><input type="text" size="5" onKeyPress='return acepteNumeros(event)'/></td></tr>
									<tr><td>g.	Cátedra de Derechos Humanos</td><td align="center"><input type="text" size="5" onKeyPress='return acepteNumeros(event)'/></td></tr>
									<tr><td>h.	Caminos Seguros a la Escuela</td><td align="center"><input type="text" size="5" onKeyPress='return acepteNumeros(event)'/></td></tr>
									<tr><td>i.	Procesos de perdón y reconciliación</td><td align="center"><input type="text" size="5" onKeyPress='return acepteNumeros(event)'/></td></tr>
								</table>
							</td>
						</tr>
						<tr><td><hr></td></tr>
						<tr><td><font style="font-size:13px;color:#003366;">4. Cómo han influido los conflictos en el desempeño académico? </font></td></tr>
						<tr><td><textarea rows="3" cols="133"></textarea></td></tr>
						<tr><td></td></tr>
						<tr><td>
						<table align="center">
						<tr><th>Grado</th><th>1</th><th>2</th><th>3</th><th>4</th><th>5</th></tr>
						<tr><td>1</td><td><input type="radio" name="g1"/></td><td><input type="radio" name="g1"/></td><td><input type="radio" name="g1"/></td><td><input type="radio" name="g1"/></td><td><input type="radio" name="g1"/></td></tr>
						<tr><td>2</td><td><input type="radio" name="g2"/></td><td><input type="radio" name="g2"/></td><td><input type="radio" name="g2"/></td><td><input type="radio" name="g2"/></td><td><input type="radio" name="g2"/></td></tr>
						<tr><td>3</td><td><input type="radio" name="g3"/></td><td><input type="radio" name="g3"/></td><td><input type="radio" name="g3"/></td><td><input type="radio" name="g3"/></td><td><input type="radio" name="g3"/></td></tr>
						<tr><td>4</td><td><input type="radio" name="g4"/></td><td><input type="radio" name="g4"/></td><td><input type="radio" name="g4"/></td><td><input type="radio" name="g4"/></td><td><input type="radio" name="g4"/></td></tr>
						<tr><td>5</td><td><input type="radio" name="g5"/></td><td><input type="radio" name="g5"/></td><td><input type="radio" name="g5"/></td><td><input type="radio" name="g5"/></td><td><input type="radio" name="g5"/></td></tr>
						<tr><td>6</td><td><input type="radio" name="g6"/></td><td><input type="radio" name="g6"/></td><td><input type="radio" name="g6"/></td><td><input type="radio" name="g6"/></td><td><input type="radio" name="g6"/></td></tr>
						<tr><td>7</td><td><input type="radio" name="g7"/></td><td><input type="radio" name="g7"/></td><td><input type="radio" name="g7"/></td><td><input type="radio" name="g7"/></td><td><input type="radio" name="g7"/></td></tr>
						<tr><td>8</td><td><input type="radio" name="g8"/></td><td><input type="radio" name="g8"/></td><td><input type="radio" name="g8"/></td><td><input type="radio" name="g8"/></td><td><input type="radio" name="g8"/></td></tr>
						<tr><td>9</td><td><input type="radio" name="g9"/></td><td><input type="radio" name="g9"/></td><td><input type="radio" name="g9"/></td><td><input type="radio" name="g9"/></td><td><input type="radio" name="g9"/></td></tr>
						<tr><td>10</td><td><input type="radio" name="g10"/></td><td><input type="radio" name="g10"/></td><td><input type="radio" name="g10"/></td><td><input type="radio" name="g10"/></td><td><input type="radio" name="g10"/></td></tr>
						<tr><td>11</td><td><input type="radio" name="g11"/></td><td><input type="radio" name="g11"/></td><td><input type="radio" name="g11"/></td><td><input type="radio" name="g11"/></td><td><input type="radio" name="g11"/></td></tr>
						</table>
						</td></tr>
					</table>
				</td>								
				<td width="1" bgcolor="#000000"><img src="../etc/img/pixel.gif" width="1" height="1"></td>			
			<tr bgcolor="#000000" height="1">
			<td colspan="3"><img src="../etc/img/pixel.gif" width="1" height="1"></td>
			</tr>
	  </table>	
<!--//////////////////////////////-->
</form>
