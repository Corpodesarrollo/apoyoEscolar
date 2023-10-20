<%@ page language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<%@page import="siges.util.Recursos"%>
<%pageContext.setAttribute("filtroPeriodo",Recursos.recursoEstatico[Recursos.PERIODO]);%>
<jsp:useBean id="filtroreportes" class="siges.conflictoReportes.beans.ConflictoFiltro" scope="session"/>
<jsp:setProperty name="filtroreportes" property="*"/>
<script languaje="javaScript">
<!--
	var nav4=window.Event ? true : false;

	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("--Seleccione uno--","-1");
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
			document.frmFiltro.nomsede2.value=document.frmFiltro.sede2.options[document.frmFiltro.sede2.selectedIndex].text;
			document.frmFiltro.nomjorn2.value=document.frmFiltro.jorn2.options[document.frmFiltro.jorn2.selectedIndex].text;
			document.frmFiltro.nomperiodo2.value=document.frmFiltro.periodo2.options[document.frmFiltro.periodo2.selectedIndex].text;
			document.frmFiltro.tipo.value=4;
			document.frmFiltro.cmd.value="Guardar";
			document.frmFiltro.submit();
		}	
	}
	
	function lanzar(tipo){
		document.frmFiltro.tipo.value=tipo;
		document.frmFiltro.action='<c:url value="/conflictoReportes/ControllerFiltro.do"/>';
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function hacerValidaciones_frmFiltro(forma){
		validarLista(forma.sede2, "- Sede",1)
		validarLista(forma.jorn2, "- Jornada",1)
		validarLista(forma.metodologia2, "- Metodologia",1)
		validarLista(forma.periodo2, "- Periodo",1)
	}
	
	function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
		this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
	}
		  
  function filtro(combo_padre,combo_hijo){
		borrar_combo(combo_hijo);
		<c:if test="${!empty requestScope.filtroSedeF && !empty requestScope.filtroJornadaF}">var Padres = new Array();
		<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila" varStatus="st">id_Hijos=new Array(); Hijos= new Array(); Sel_Hijos= new Array(); id_Padre= new Array(); var k=0;
		<c:forEach begin="0" items="${requestScope.filtroJornadaF}" var="fila2"><c:if test="${fila[0]==fila2[2]}">Sel_Hijos[k] = '<c:if test="${sessionScope.filtroreportes.jorn2== fila2[0]}">SELECTED</c:if>';id_Hijos[k] = '<c:out value="${fila2[0]}"/>'; Hijos[k] = '<c:out value="${fila2[1]}"/>'; id_Padre[k] = '<c:out value="${fila2[2]}"/>'; k++;</c:if></c:forEach>
		Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
		var niv=combo_padre.options[combo_padre.selectedIndex].value;
		var val_padre = -1;
		for(var k=0;k<Padres.length;k++){
			if(Padres[k].id_Padre[0]==niv) val_padre=k;							
		}
		if(val_padre!=-1){ var no_hijos = Padres[val_padre].Hijos.length;
			for(i=0; i < no_hijos; i++){
				if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
					combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
				}else
					combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
				}
			}
		</c:if>
	}

//-->
</script>
<%@include file="../mensaje.jsp" %>
		<form method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)" action="<c:url value="/conflictoReportes/NuevoGuardar.jsp"/>">
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="height" VALUE="180">
			<INPUT TYPE="hidden" NAME="tipo" VALUE="">
			<input type="hidden" name="ext" value="">
			<input type="hidden" name="nomsede2" value="">
			<input type="hidden" name="nomjorn2" value="">
			<input type="hidden" name="nomperiodo2" value="">
			<input type="hidden" name="nommetodologia2" value="">
			<table>
				<tr>
				  <td width="45%" align="left">
					  <input name="cmd2" class="boton" type="button" value="Generar Reporte" onClick="buscar()">
				  </td>
				</tr>
				<tr>
					<td rowspan="2" width="780">
						<!-- <a href="javaScript:lanzar(0)"><img src='<c:url value="/etc/img/tabs/reporte1_0.gif"/>' alt="Colegios que no han cerrado Conflicto Escolar" height="26" border="0"></a>
						<img src='<c:url value="/etc/img/tabs/reporte2_1.gif"/>' alt='Grupos que no han cerrado Conflicto Escolar' height="26" border="0"> -->
						<a href="javaScript:lanzar(2)"><img src='<c:url value="/etc/img/tabs/reporte3_0.gif"/>' alt='Reporte general de Conflicto Escolar' height="26" border="0"></a>
						<a href="javaScript:lanzar(6)"><img src='<c:url value="/etc/img/tabs/reporte1_0.gif"/>' alt='Colegios que han cerrado Conflicto Escolar' height="26" border="0"></a>
					</td>
				</tr>
			</table>

  	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
				<caption>Filtro</caption>
				<tr>
					<td><span class="style2" >*</span>Sede:</td>
			    <td>
						<select name="sede2" onChange='filtro(document.frmFiltro.sede2, document.frmFiltro.jorn2)' style='width:300px;'>
							<option value='-1'>-- Seleccione uno --</option>
							<c:forEach begin="0" items="${requestScope.filtroSedeF}" var="fila">
								<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtroreportes.sede2 == fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
							</c:forEach>
						</select>
					</td>
					<td><span class="style2" >*</span>Jornada:</td>
				  <td>
						<select name="jorn2" style='width:150px;' onChange=''>
							<option value='-1'>-- Seleccione uno --</option>
						</select>							
				  </td>
				 </tr>
				  <tr>
				 	<td><span class="style2">*</span> Metodologia:</td>
					<td>
						<select name="metodologia2">
	            <option value="-1">--seleccione uno--</option>
	            <c:forEach begin="0" items="${requestScope.filtroMetodologia}" var="fila">
	        	    <option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtroreportes.metodologia2==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
							</c:forEach>
	          </select>
	        </td>
				 	<td><span class="style2">*</span> Periodo:</td>
					<td>
						<select name="periodo2">
	            <option value="-1">--seleccione uno--</option>
	            <c:forEach begin="0" items="${filtroPeriodo}" var="fila">
	        	    <option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.filtroreportes.periodo2== fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
							</c:forEach>
	          </select>
	        </td>
				</tr> 
			</form>
				<c:if test="${!empty requestScope.reporte2 && requestScope.reporte2!='--' && requestScope.reporte2!='0'}">
					<tr>
						<td colspan="6" align='center' valign="top">
							<form name='frmGenerar' action='<c:url value="/${requestScope.reporte2}"/>' method='post' target='_blank'>
								<input type='hidden' name='dir' value='<c:out value="${requestScope.reporte2}"/>'>
								<input type='hidden' name='tipo' value='xls'>
								<input type='hidden' name='accion' value='0'>
								<input type='hidden' name='aut' value='1'>El reporte fue generado satisfactoriamente.<br>Pulse en el Icono para descargar el archivo.<br>
							</form>
							>><a href='javaScript:document.frmGenerar.submit();'><img border=0 src='<c:url value="/etc/img/xls.gif"/>'></A><<
						</td>
					</tr>
				</c:if>
				<c:if test="${requestScope.reporte1=='--'}">
					<tr>
						<td colspan="6" valign="top" align='center'>No se pudo generar el reporte.<br></td>
					</tr>
				</c:if>
  	  </table>
<script>
	filtro(document.frmFiltro.sede2, document.frmFiltro.jorn2)
</script>