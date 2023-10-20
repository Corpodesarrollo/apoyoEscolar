<%@ page language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<jsp:useBean id="rotacion" class="siges.rotacion.beans.Rotacion" scope="session"/>
<jsp:setProperty name="rotacion" property="*"/>
<script languaje="javaScript">
<!--
	var nav4=window.Event ? true : false;

	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("--TODOS--","-1");
	}

	function cancelar(){
		location.href='<c:url value="/bienvenida.jsp"/>';
	}

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}

	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.tipo.value=47;
			document.frmFiltro.cmd.value="Guardar";
			document.frmFiltro.submit();
		}	
	}
	
	function lanzar(tipo){
		document.frmFiltro.tipo.value=tipo;
		document.frmFiltro.action='<c:url value="/rotacion/ControllerEditar.do"/>';
		document.frmFiltro.ext2.value='/rotacion/ControllerFiltro.do';
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function lanzar2(tipo){
		document.frmFiltro.tipo.value=tipo;
		document.frmFiltro.action='<c:url value="/rotacion/ControllerEditar.do"/>';
		document.frmFiltro.ext2.value="";
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}

	function hacerValidaciones_frmFiltro(forma){
		//validarLista(forma.fsede, "- Seleccione una sede",1)
		//validarLista(forma.fjornada, "- Seleccione una jornada",1)
		//validarLista(forma.fespacio, "- Seleccione un espacio físico",1)
	}

	function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
		this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
	}

	function filtro1(combo_padre,combo_hijo){
		<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroEspFis}">
			var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">
				id_Hijos=new Array();
				Hijos= new Array();
				Sel_Hijos= new Array();
				id_Padre= new Array();
				var k=0;
				<c:forEach begin="0" items="${requestScope.filtroEspFis}" var="fila2">
					<c:if test="${fila[0]==fila2[0]}">
						Sel_Hijos[k] = '<c:if test="${sessionScope.rotacion.fespacio == fila2[0]}">SELECTED</c:if>';
						id_Hijos[k] = '<c:out value="${fila2[1]}"/>';
						Hijos[k] = '<c:out value="${fila2[2]}"/>';
						id_Padre[k] = '<c:out value="${fila2[0]}"/>';
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

//-->
</script>
<%@include file="../mensaje.jsp" %>
	<font size="1">
		<form method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)" action="<c:url value="/rotacion/NuevoGuardar.jsp"/>">
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="ext2" VALUE='/rotacion/ControllerFiltro.do'>
			<INPUT TYPE="hidden" NAME="height" VALUE="180">
			<INPUT TYPE="hidden" NAME="tipo" VALUE="">
			<input type="hidden" name="ext" value="">
			<input type="hidden" name="metodologia" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
			
			<table>
				<tr>
				  <td width="45%" align="left">
					  <input name="cmd2" class="boton" type="button" value="Buscar" onClick="buscar()">
				  </td>
				</tr>
				<tr>
					<td rowspan="2" width="780">
						<a href="javaScript:lanzar(0)"><img src="../etc/img/tabs/tipo_receso_0.gif" alt="Tipo de Receso" height="26" border="0"></a>
						<a href="javaScript:lanzar(10)"><img src='<c:url value="/etc/img/tabs/estructura_0.gif"/>' alt='Estructura' height="26" border="0"></a>
						<a href="javaScript:lanzar(20)"><img src="../etc/img/tabs/espacios_0.gif" alt="Fija Espacios Físicos" height="26" border="0"></a>
						<a href="javaScript:lanzar(60)"><img src='<c:url value="/etc/img/tabs/fijar_asignatura_0.gif"/>' alt='Fijar Asignatura' height="26" border="0"></a>
						<a href="javaScript:lanzar(70)"><img src='<c:url value="/etc/img/tabs/espacio_docente_0.gif"/>' alt='Fijar Espacio por Docente' height="26" border="0"></a>
						<img src="../etc/img/tabs/espfis_jornada_1.gif" alt="Inhabilitar Espacios Físicos" height="26" border="0">
						<a href="javaScript:lanzar(50)"><img src='<c:url value="/etc/img/tabs/inhabilitar_docentes_0.gif"/>' alt='Inhabilitar Docentes' height="26" border="0"></a>
						<a href="javaScript:lanzar2(80)"><img src='<c:url value="/etc/img/tabs/priorizar_0.gif"/>' alt='Priorizar' height="26" border="0"></a>
					</td>
				</tr>
			</table>

  	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
				<caption>B&uacute;squeda de Espacios F&iacute;sicos Inhabilitados</caption>
				<tr>
					<td><span class="style2" ></span>Sede:</td>
			    <td colspan="2">
						<select name="fsede" onChange='filtro1(document.frmFiltro.fsede,document.frmFiltro.fespacio)' style='width:300px;'>
							<option value='-1'>-- TODOS --</option>
							<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
								<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.rotacion.fsede == fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
							</c:forEach>
						</select>
			   </td>
					<!--<td><span class="style2" ></span>Jornada:</td>
				  <td>
						<select name="fjornada" style='width:150px;' onChange=''>
							<option value='-1'>-- TODOS --</option>
						</select>
				  </td>-->
				</tr>
				<tr>
					<td><span class="style2" ></span>Espacio F&iacute;sico:</td>
			    <td colspan="2">
						<select name="fespacio" onChange='' style='width:300px;'>
							<option value='-1'>-- TODOS --</option>
						</select>
			    </td>
				</tr>
  	  </table>
 	  </form>
  </font>
  <script>
		filtro1(document.frmFiltro.fsede,document.frmFiltro.fespacio);
  </script>