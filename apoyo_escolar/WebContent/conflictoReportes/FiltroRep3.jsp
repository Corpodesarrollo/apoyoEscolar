<%@ page language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<%@page import="siges.util.Recursos"%>
<%pageContext.setAttribute("filtroLoc",Recursos.recurso[Recursos.LOCALIDAD]);%>
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
		combo.options[0] = new Option("--TODOS--","-1");
	}

	function cancelar(){
		location.href='<c:url value="/bienvenida.jsp"/>';
	}
	function metodo(){
		if(document.getElementById("input")) document.getElementById("input").style.display='';
	}
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}

	function buscar(){
		if(validarForma(document.frmFiltro)){
			<c:if test="${sessionScope.login.nivel == 2 or sessionScope.login.nivel == 4 or sessionScope.login.nivel == 6}">
				document.frmFiltro.nomlocal3.value='<c:out value="${sessionScope.login.loc}"/>'
				document.frmFiltro.nomcolegio3.value='<c:out value="${sessionScope.login.inst}"/>';
			</c:if>
			<c:if test="${sessionScope.login.nivel != 2 and sessionScope.login.nivel != 4 and sessionScope.login.nivel != 6}">
				document.frmFiltro.nomlocal3.value=document.frmFiltro.local3.options[document.frmFiltro.local3.selectedIndex].text;
				document.frmFiltro.nomcolegio3.value=document.frmFiltro.colegio3.options[document.frmFiltro.colegio3.selectedIndex].text;
			</c:if>
			
			document.frmFiltro.nomsede3.value=document.frmFiltro.sede3.options[document.frmFiltro.sede3.selectedIndex].text;
			document.frmFiltro.nomjorn3.value=document.frmFiltro.jorn3.options[document.frmFiltro.jorn3.selectedIndex].text;
			document.frmFiltro.nomperiodo3.value=document.frmFiltro.periodo3.options[document.frmFiltro.periodo3.selectedIndex].text;
			document.frmFiltro.tipo.value=5;
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
		<c:if test="${sessionScope.login.nivel != 2 and sessionScope.login.nivel != 4 and sessionScope.login.nivel != 6}">
			validarLista(forma.local3, "- Seleccione una localidad",1)
			validarLista(forma.colegio3, "- Seleccione un colegio",1)
		</c:if>
		validarLista(forma.sede3, "- Seleccione una sede",1)
		validarLista(forma.jorn3, "- Seleccione una jornada",1)
		validarLista(forma.periodo3, "- Seleccione un periodo",1)
	}
	
	function cargaColegio(){
		borrar_combo(document.frmFiltro.colegio3);
		borrar_combo(document.frmFiltro.sede3);
		//borrar_combo(document.form1.forcodjorn);
		document.getElementById("txtcol").innerHTML="CARGANDO COLEGIOS...";
		document.frmAux.loc.value=document.frmFiltro.local3.options[document.frmFiltro.local3.selectedIndex].value;
		document.frmAux.tipo.value="3";
		document.frmAux.target="frame";
		document.frmAux.submit();
	}
	
	function cargaSede(){
		borrar_combo(document.frmFiltro.sede3);
		//borrar_combo(document.form1.forcodjorn);
		document.getElementById("txtsed").innerHTML="CARGANDO SEDES...";
		document.frmAux.col.value=document.frmFiltro.colegio3.options[document.frmFiltro.colegio3.selectedIndex].value;
		document.frmAux.tipo.value="4";
		document.frmAux.target="frame";
		document.frmAux.submit();
	}

//-->
</script>
<%@include file="../mensaje.jsp" %>
	<font size="1">
		<form method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)" action="<c:url value="/conflictoReportes/NuevoGuardar.jsp"/>">
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="height" VALUE="180">
			<INPUT TYPE="hidden" NAME="tipo" VALUE="">
			<input type="hidden" name="ext" value="">
			<input type="hidden" name="nomlocal3" value="">
			<input type="hidden" name="nomcolegio3" value="">
			<input type="hidden" name="nomsede3" value="">
			<input type="hidden" name="nomjorn3" value="">
			<input type="hidden" name="nomperiodo3" value="">
			<!--<input type="hidden" name="periodo3" value="0">-->
			<input type="hidden" name="metodologia" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
			
			<c:if test="${sessionScope.login.nivel == 2 or sessionScope.login.nivel == 4 or sessionScope.login.nivel == 6}">
				<input type="hidden" name="local3" value="<c:out value="${sessionScope.login.locId}"/>">
				<input type="hidden" name="colegio3" value="<c:out value="${sessionScope.login.instId}"/>">
			</c:if>
						
			<table>
				<tr>
				  <td width="45%" align="left">
					  <input name="cmd2" class="boton" type="button" value="Generar Reporte" onClick="buscar()">
				  </td>
				</tr>
				<tr>
					<td rowspan="2" width="780">
						<img src='<c:url value="/etc/img/tabs/reporte3_1.gif"/>' alt='Reporte general de Conflicto Escolar' height="26" border="0">
						<a href="javaScript:lanzar(6)"><img src='<c:url value="/etc/img/tabs/reporte1_0.gif"/>' alt='Colegios que han cerrado Conflicto Escolar' height="26" border="0"></a>
					</td>
				</tr>
			</table>

  	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
				<caption>Filtro para reporte</caption>
			<tr>
				<c:if test="${sessionScope.login.nivel != 2 and sessionScope.login.nivel != 4 and sessionScope.login.nivel != 6}">			
				<td><span class="style2">*</span>&nbsp;Localidad</td>
				<td colspan="3">
						<select name="local3" class="entrada1" id="local3" onChange="cargaColegio()" style='width: 133px;'
							<c:if test="${sessionScope.filtroItemsVO.filTieneLocalidad==1}">disabled</c:if>>
							<option value="-1" selected>Seleccione uno</option>
							<c:forEach begin="0" items="${filtroLoc}" var="fila">
								<option value="<c:out value="${fila[0]}"/>"
									<c:if test="${sessionScope.login.locId eq fila[0]}">SELECTED</c:if>>
									<c:out value="${fila[1]}" />
								</option>
							</c:forEach>
						</select>
				</td>
				</c:if>
				<c:if test="${sessionScope.login.nivel != 4 and sessionScope.login.nivel != 6}">
				<td width="20%"><span class="style2">*</span>&nbsp;Colegio</td>
				<td colspan="3" width="80%">
					<select name="colegio3" class="entrada1" id="colegio3" onChange="cargaSede()" style='width:200px; <c:if test="${sessionScope.filtroItemsVO.filTieneColegio==1}">disabled</c:if>'>
							<option value="-1">Seleccione uno</option>
					</select><span class="style2" id="txtcol" name="txtcol" style="font-weight: bold">
				</td>
				</c:if>
			</tr>
			<tr>
				<td>
				<span class="style2">*</span>&nbsp;Sede</td>
				
				<td colspan="3">
				
				<c:if test="${sessionScope.login.nivel != 4 and sessionScope.login.nivel != 6}">
					<select name="sede3" class="entrada1" id="sede3" onChange="" style='width: 200px;'>
						<option value="-1">Seleccione uno</option>
						<option value="<c:out value='${sede3[0]}'/>">Seleccione uno</option>
					</select>
				</c:if>
				
				<c:if test="${sessionScope.login.nivel == 4 or sessionScope.login.nivel == 6}">
					<select name="sede3" class="entrada1" id="sede3" onChange="" style='width: 200px;'>
						<option value="<c:out value='${sede3[0]}'/>">Seleccione uno</option>
						<c:forEach begin="0" items="${requestScope.CargaSedes}" var="cargaSedes">
							<option value="<c:out value='${cargaSedes[0]}'/>"><c:out value='${cargaSedes[1]}'/></option>
						</c:forEach>
					</select>
				</c:if>
				
				<span class="style2" id="txtsed" name="txtsed" style="font-weight: bold"></span>
				</td>
				<td><span class="style2">*</span>&nbsp;Jornada</td>
				<td colspan="3">
				
				<c:if test="${sessionScope.login.nivel == 4 or sessionScope.login.nivel == 6}">				
					<select name="jorn3" class="entrada1" id="jorn3" style='width: 100px;'>
						<option value="-1">Seleccione uno</option>
						<c:forEach begin="0" items="${sessionScope.filtroJornadaF}" var="fila2">
							<option value="<c:out value="${fila2[0]}"/>"><c:out value="${fila2[1]}"/></option>
						</c:forEach>
					</select>
					
				</c:if>
				<c:if test="${sessionScope.login.nivel != 4 and sessionScope.login.nivel != 6}">
					<select name="jorn3" class="entrada1" id="jorn3" style='width: 100px;'>
							<option value="-1">Seleccione uno</option>
							<option value="1" <c:if test="${sessionScope.nuevoConvocatoria.forcodjorn== 1}">SELECTED</c:if>>Completa</option>
							<option value="2" <c:if test="${sessionScope.nuevoConvocatoria.forcodjorn== 2}">SELECTED</c:if>>Mañana</option>
							<option value="4" <c:if test="${sessionScope.nuevoConvocatoria.forcodjorn== 4}">SELECTED</c:if>>Tarde</option>
							<option value="8" <c:if test="${sessionScope.nuevoConvocatoria.forcodjorn== 8}">SELECTED</c:if>>Nocturna</option>
							<option value="16" <c:if test="${sessionScope.nuevoConvocatoria.forcodjorn== 16}">SELECTED</c:if>>Fin de Semana</option>
							<option value="32" <c:if test="${sessionScope.nuevoConvocatoria.forcodjorn== 32}">SELECTED</c:if>>Acelerada Mañana</option>
							<option value="64" <c:if test="${sessionScope.nuevoConvocatoria.forcodjorn== 64}">SELECTED</c:if>>Acelerada Tarde</option>
							<option value="0" <c:if test="${sessionScope.nuevoConvocatoria.forcodjorn== 0}">SELECTED</c:if>>Sin Jornada</option>
					</select>
				</c:if>
				</td>
			</tr>
			<tr>
				<td><span class="style2">*</span>&nbsp;Periodo:</td>
				<td><select name="periodo3">
						<option value="-1">--seleccione uno--</option>
						<c:if
							test="${sessionScope.login.vigencia_actual != null and sessionScope.login.vigencia_actual < 2013}">
							<script>alert("al menos no es nulo normalito");</script>
							<c:forEach begin="0" items="${filtroPeriodo}" var="fila">
								<option value="<c:out value="${fila[0]}"/>"
									<c:if test="${sessionScope.nuevoConflictoFiltro.periodo== fila[0]}">SELECTED</c:if>>
									<c:out value="${fila[1]}" />
							</c:forEach>
						</c:if>
						<c:if
							test="${sessionScope.login.vigencia_actual != null and sessionScope.login.vigencia_actual >= 2013}">

							<c:forEach begin="0" items="${sessionScope.listaPeriodos}"
								var="fila">
								<option value="<c:out value="${fila[0]}"/>"
									<c:if test="${sessionScope.nuevoConflictoFiltro.periodo==fila[0]}">SELECTED</c:if>>
									<c:out value="${fila[1]}" />
							</c:forEach>
						</c:if>
				</select> </td>
			</tr>
			<tr>
				<td style="display: none"><iframe name="frame" id="frame"></iframe></td>
			</tr>
			</form>
				<c:if test="${!empty requestScope.reporte3 && requestScope.reporte3!='--' && requestScope.reporte3!='0'}">
					<tr>
						<td colspan="6" align='center' valign="top">
							<form name='frmGenerar' action='<c:url value="/${requestScope.reporte3}"/>' method='post' target='_blank'>
								<input type='hidden' name='dir' value='<c:out value="${requestScope.reporte3}"/>'>
								<input type='hidden' name='tipo' value='zip'>
								<input type='hidden' name='accion' value='0'>
								<input type='hidden' name='aut' value='1'>El reporte fue generado satisfactoriamente.<br>Pulse en el Icono para descargar el archivo.<br>
							</form>
							>><a href='javaScript:document.frmGenerar.submit();'><img border=0 src='<c:url value="/etc/img/zip.gif"/>'></A><<
						</td>
					</tr>
				</c:if>
				<c:if test="${requestScope.reporte3=='--'}">
					<tr>
						<td colspan="6" valign="top" align='center'>No se pudo generar el reporte. <c:out value="${requestScope.error}"/><br>
						<a href='javaScript:metodo();'>&nbsp;&nbsp;&nbsp;<font color="white">__</font></a>
						<table style="display:none" id='input' name='input' width='100%' border='0'><tr><td><textarea rows="2" cols="90" name="input2"><c:out value="${requestScope.errormsj}"/></textarea></td></tr></table>
						</td>
					</tr>
				</c:if>
  	  </table>
 	  
 	  <form method="post" name="frmAux" action="<c:url value="/conflictoReportes/ControllerFiltro.do"/>">
			<input type="hidden" name="tipo" value="">
			<input type="hidden" name="loc" value="">
			<input type="hidden" name="col" value="">
			<input type="hidden" name="sed" value="">
			<input type="hidden" name="ext" value="1">
			<input type="hidden" name="forma" value="frmFiltro">
		</form>
  </font>
  <script>
  </script>