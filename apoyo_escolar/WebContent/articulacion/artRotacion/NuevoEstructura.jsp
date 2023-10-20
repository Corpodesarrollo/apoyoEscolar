<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="artRotEstructuraVO" class="articulacion.artRotacion.vo.EstructuraVO" scope="session"/>
<jsp:useBean id="paramsVO" class="articulacion.artRotacion.vo.ParamsVO" scope="page"/>
<html>
<head>
<c:import url="/parametros.jsp"/>
<script languaje="javaScript">
<!--
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}

	function copiarChecks(forma){
		if(forma.parSabado_){
				if(forma.parSabado_.checked==true){
					forma.parSabado.value=forma.parSabado_.value;
				}
		}
		if(forma.parDomingo_){
				if(forma.parDomingo_.checked==true){
					forma.parDomingo.value=forma.parDomingo_.value;
				}
		}
	}

	function nuevo(){
		document.frmNuevo.cmd.value=document.frmNuevo.NUEVO.value;
		document.frmNuevo.action='<c:url value="/articulacion/artRotacion/Nuevo.do"/>';
		document.frmNuevo.submit();
	}

	function guardar(){
		if(validarForma(document.frmNuevo)){
			copiarChecks(document.frmNuevo);
			document.frmNuevo.cmd.value=document.frmNuevo.GUARDAR.value;
			document.frmNuevo.submit();
		}
	}
	
	function hacerValidaciones_frmNuevo(forma){
		validarCampo(forma.parNombre, "- Nombre", 1,60)
		validarLista(forma.parSede, "- Sede", 1)
		validarLista(forma.parJornada, "- Jornada", 1)
		validarLista(forma.parAnhoVigencia, "- Año de vigencia", 1)
		validarLista(forma.parPerVigencia, "- Periodo de vigencia", 1)
		//validarEntero(forma.parPerVigencia, "- Periodo de vigencia", 1,9)
		validarSeleccion(forma.parComponente,"- Componente")
		validarLista(forma.parHoraIni, "- Hora de inicio", 1)
		validarLista(forma.parMinIni, "- Hora de inicio", 1)
		validarLista(forma.parDuracion, "- Duración de una clase", 1)
		validarLista(forma.parHorasBloque, "- Número de clases por bloque", 1)
		validarLista(forma.parBloques, "- Número de bloques por día", 1)
	}
	
	
	/*FUNCION QUE ALMACENA LOS ARRAYS DE OBJETOS DE UN MISMO PADRE*/
		function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
			this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
		}

		/*FUNCION QUE LIMPIA UN COMBO Y LE PONE UNA OPCION POR DEFECTO*/
			function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("-Seleccione una-","-99");
			}
			
			/*FUNCION QUE LLENA EL CAMBO HIJO BASADO ENLO QU HAYAN ESCOJIDO EN EL COMBO PADRE*/
			function filtro(combo_padre,combo_hijo){
					borrar_combo(combo_hijo); 
					<c:if test="${!empty requestScope.lSedeVO && !empty requestScope.lJornadaVO}">
						var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.lSedeVO}" var="sede" varStatus="st">
							id_Hijos=new Array(); 
							Hijos= new Array(); 
							Sel_Hijos= new Array(); 
							id_Padre= new Array(); 
							var k=0;
							<c:forEach begin="0" items="${requestScope.lJornadaVO}" var="jornada">
								<c:if test="${sede.codigo==jornada.padre}">
									Sel_Hijos[k] = '<c:if test="${sessionScope.artRotEstructuraVO.parJornada==jornada.codigo}">SELECTED</c:if>';
									id_Hijos[k] = '<c:out value="${jornada.codigo}"/>'; 
									Hijos[k] = '<c:out value="${jornada.nombre}"/>'; 
									id_Padre[k] = '<c:out value="${jornada.padre}"/>'; 
									k++;
								</c:if>
							</c:forEach>
							Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
						</c:forEach>
						var niv=combo_padre.options[combo_padre.selectedIndex].value;
						var val_padre = -99;
						for(var k=0;k<Padres.length;k++){
							if(Padres[k].id_Padre[0]==niv) val_padre=k;							
						}
						if(val_padre!=-99){ var no_hijos = Padres[val_padre].Hijos.length;
							for(i=0; i < no_hijos; i++){
								if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
								}else
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
							}
						}
				</c:if>}
			
//-->
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:200px;overflow:auto;vertical-align:top;background-color:#ffffff;"><c:import url="/articulacion/artRotacion/Filtro.do"/></div>
		</td></tr>
	</table>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/articulacion/artRotacion/SaveEstructura.jsp"/>'>
		<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_DEFAULT}"/>'>
			<input type="hidden" name="cmd" value=''>
			<input type="hidden" name="anhoVigencia" value='<c:out value="${requestScope.anhoVigencia}"/>'>
			<input type="hidden" name="perVigencia" value='<c:out value="${requestScope.perVigencia}"/>'>
			<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
			<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>REGISTRO / EDICIÓN DE ESTRUCTURA</caption>
				<tr><td width="45%">
					<c:if test="${sessionScope.NivelPermiso==2}">
						<input name="cmd1" type="button" value="Nuevo" onClick="nuevo()" class="boton">
		        <input name="cmd1" type="button" value="Guardar" onClick="guardar()" class="boton">
	    		</c:if>
			  </td></tr>	
	  </table>
		<table width="100%" border="0" cellspacing="2" cellpadding="0">
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Nombre:</b></td>
				<td colspan="3"><input type="text" name="parNombre" value='<c:out value="${sessionScope.artRotEstructuraVO.parNombre}"/>' size="40" maxlength="60">
				</td>
			</tr>	
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Sede:</b></td>
				<td colspan="3">
					<select name="parSede" style='width:350px;' onChange='filtro(document.frmNuevo.parSede,document.frmNuevo.parJornada)' <c:if test="${sessionScope.artRotEstructuraVO.formaEstado==1}">disabled</c:if>>
						<option value="-99">-Seleccione una-</option>
						<c:forEach begin="0" items="${requestScope.lSedeVO}" var="sede">
							<option value="<c:out value="${sede.codigo}"/>" <c:if test="${sede.codigo==sessionScope.artRotEstructuraVO.parSede}">selected</c:if>><c:out value="${sede.nombre}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Jornada:</b></td>
				<td>
					<select name="parJornada" style='width:100px;' <c:if test="${sessionScope.artRotEstructuraVO.formaEstado==1}">disabled</c:if>>
						<option value="-99">-Seleccione una-</option>
					</select>
				</td>
				<td><span class="style2">*</span><b>&nbsp;Vigencia:</b></td>
				<td>
					<select name="parAnhoVigencia" style='width:50px;'>
						<option value="-99">--</option>
						<c:forEach begin="0" items="${requestScope.listaVigencia}" var="item">
							<option value='<c:out value="${item.codigo}"/>' <c:if test="${item.codigo==sessionScope.artRotEstructuraVO.parAnhoVigencia}">selected</c:if>><c:out value="${item.nombre}"/></option>
						</c:forEach>
					</select>
					<select name="parPerVigencia" style='width:30px;'>
						<option value="-99">--</option>
						<c:forEach begin="0" items="${requestScope.lPeriodoVO}" var="vig">
							<option value="<c:out value="${vig}"/>" <c:if test="${vig==sessionScope.artRotEstructuraVO.parPerVigencia}">selected</c:if>><c:out value="${vig}"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Componente:</b></td>
				<td>
					Académico:<input type="radio" name="parComponente" value="1" <c:if test="${sessionScope.artRotEstructuraVO.parComponente==1}">CHECKED</c:if> <c:if test="${sessionScope.artRotEstructuraVO.formaEstado==1}">disabled</c:if>>&nbsp;&nbsp;
					Técnico:<input type="radio" name="parComponente" value="2" <c:if test="${sessionScope.artRotEstructuraVO.parComponente==2}">CHECKED</c:if> <c:if test="${sessionScope.artRotEstructuraVO.formaEstado==1}">disabled</c:if>>&nbsp;&nbsp;
				</td>
				<td><span class="style2">*</span><b>&nbsp;Clase:</b></td>
				<td>
				<input type="hidden" name="parSabado" value="0"><input type="hidden" name="parDomingo" value="0">
				Sabado: <input type='checkbox' name='parSabado_' value='1' <c:if test="${sessionScope.artRotEstructuraVO.parSabado=='1'}">CHECKED</c:if>>&nbsp;&nbsp;
				Domingo: <input type='checkbox' name='parDomingo_' value='1' <c:if test="${sessionScope.artRotEstructuraVO.parDomingo=='1'}">CHECKED</c:if>>&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Hora inicio:</b></td>
				<td>
					<select name="parHoraIni" style='width:35px;'><option value="-99">--</option>
					<c:forEach begin="0" end="23" step="1" var="hora">
						<option value="<c:out value="${hora}"/>" <c:if test="${hora==sessionScope.artRotEstructuraVO.parHoraIni}">selected</c:if>><c:out value="${hora}"/></option>
					</c:forEach>
					</select>
					-
					<select name="parMinIni" style='width:35px;'><option value="-99">--</option>
					<c:forEach begin="0" end="55" step="5" var="min">
						<option value="<c:out value="${min}"/>" <c:if test="${min==sessionScope.artRotEstructuraVO.parMinIni}">selected</c:if>><c:out value="${min}"/></option>
					</c:forEach>
					</select>
				</td>
				<td><span class="style2">*</span><b>&nbsp;Duración (minutos):</b></td>
				<td>
				<select name="parDuracion" style='width:35px;'><option value="-99">--</option>
					<c:forEach begin="10" end="60" step="5" var="min">
						<option value="<c:out value="${min}"/>" <c:if test="${min==sessionScope.artRotEstructuraVO.parDuracion}">selected</c:if>><c:out value="${min}"/></option>
					</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span><b>&nbsp;Clases por bloque:</b></td>
				<td>
				<select name="parHorasBloque" style='width:35px;'><option value="-99">--</option>
					<c:forEach begin="1" end="4" step="1" var="clase">
						<option value="<c:out value="${clase}"/>" <c:if test="${clase==sessionScope.artRotEstructuraVO.parHorasBloque}">selected</c:if>><c:out value="${clase}"/></option>
					</c:forEach>
				</select>
				</td>
				<td><span class="style2">*</span><b>&nbsp;Bloques por día:</b></td>
				<td>
				<select name="parBloques" style='width:35px;'><option value="-99">--</option>
					<c:forEach begin="1" end="8" step="1" var="bloque">
						<option value="<c:out value="${bloque}"/>" <c:if test="${bloque==sessionScope.artRotEstructuraVO.parBloques}">selected</c:if>><c:out value="${bloque}"/></option>
					</c:forEach>
				</select>
				</td>
			</tr>
		</table>
	</form>
</body>
<script>
filtro(document.frmNuevo.parSede,document.frmNuevo.parJornada);
</script>
</html>