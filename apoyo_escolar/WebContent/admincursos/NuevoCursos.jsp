<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<jsp:useBean id="admincursos" class="siges.admincursos.beans.AdminCursos" scope="session"/>
<jsp:setProperty name="admincursos" property="*"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor='#ffffff'>
	<tr><td width="100%" valign='top'>
		<div style="width:100%;height:250px;overflow:auto;vertical-align:top;background-color:#ffffff;"><c:import url="admincursos/ControllerFiltro.do"/></div>
	</td></tr>
</table>
<script languaje="javaScript">
<!--
	var nav4=window.Event ? true : false;

	function cancelar(){
		location.href='<c:url value="/bienvenida.jsp"/>';
	}
	
	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}

	function nuevo(){
		document.frmNuevo.tipo.value=0;
		document.frmNuevo.cmd.value="Nuevo";
		document.frmNuevo.submit();
	}
	
	function guardar(){
		if(validarForma(document.frmNuevo)){
			validarJornada(document.frmNuevo)
			document.frmNuevo.tipo.value=1;
			document.frmNuevo.cmd.value="Guardar";
			document.frmNuevo.submit();
		}	
	}
	
	function validarJornada(forma){
		var cont=0;
		var txt="";
		var niv = new Array();
		niv[0]='Mañana';
		niv[1]='Tarde';
		niv[2]='Sabado';
		for(var i=0;i<forma.nivel_.length;i++){
			if(forma.nivel_[i].checked==true){
				if(cont==0)	txt+=niv[i];
				else	txt+=", "+niv[i];
				cont++;
			}
		}
		forma.jornada.value=txt;
		if(cont==1)
			forma.nivel.value="1";
		else if(cont==2)
			forma.nivel.value="2";
		else if(cont==3)
			forma.nivel.value="3";
		else	alert("- Debe seleccionar al menos una jornada")
	}
	
	function cargarJornada(forma){
		var nivel='<c:out value="${sessionScope.admincursos.nivel}"/>';
		if(nivel==1){
			forma.nivel_[0].checked=true;
		}else if(nivel==2){
			forma.nivel_[0].checked=true;
			forma.nivel_[1].checked=true;
		}else if(nivel==3){
			forma.nivel_[0].checked=true;
			forma.nivel_[1].checked=true;
			forma.nivel_[2].checked=true;
		}
	}
	
	function hacerValidaciones_frmNuevo(forma){
		validarCampo(forma.nombre, "- Nombre del curso", 1, 250)
		validarCampo(forma.cupototal, "- Cupo total", 1, 3)
		validarSeleccion(forma.nivel_, "- Debe seleccionar al menos una jornada");
	}
//-->
</script>
<%@include file="../mensaje.jsp" %>
	<font size="1">
		<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action="<c:url value="/admincursos/NuevoGuardar.jsp"/>">
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="height" VALUE="210">
			<INPUT TYPE="hidden" NAME="tipo" VALUE="">
			<input type="hidden" name="ext" value="">
			<input type="hidden" name="nivel" value="<c:out value="${sessionScope.admincursos.nivel}"/>">
			<input type="hidden" name="jornada" value="<c:out value="${sessionScope.admincursos.jornada}"/>">
			<table align="center" width="100%">
				<tr>
				  <td width="45%" align="left">
						<input name="cmd1" class="boton" type="button" value="Nuevo" onClick="nuevo()">
					  <input name="cmd2" class="boton" type="button" value="Guardar" onClick="guardar()">
					</td>
				</tr>
  	  </table>

  	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
				<caption>Nuevo Curso</caption>
				<tr>
					<td><span class="style2">*</span> Nombre:</td>
					<td><textarea rows="3" cols="70" name="nombre"><c:if test="${sessionScope.admincursos.estado==1}"><c:out value="${sessionScope.admincursos.nombre}"/></c:if></textarea></td>
				</tr>
				<tr>
					<td><span class="style2"></span> Cupos por grupo:</td>
					<td><input type="text" name="cupos" size="40" maxlength="60" <c:if test="${sessionScope.admincursos.estado==1}">value='<c:out value="${sessionScope.admincursos.cupos}"/>'</c:if>></td>
				</tr>
				<tr>
					<td><span class="style2"></span> Número de Grupos:</td>
					<td><input type="text" name="grupos" onKeyPress='return acepteNumeros(event)' size="3" maxlength="3" <c:if test="${sessionScope.admincursos.estado==1}">value='<c:out value="${sessionScope.admincursos.grupos}"/>'</c:if>></td>
				</tr>
				<tr>
					<td><span class="style2">*</span> Cupo total:</td>
					<td><input type="text" name="cupototal" onKeyPress='return acepteNumeros(event)' size="3" maxlength="3" <c:if test="${sessionScope.admincursos.estado==1}">value='<c:out value="${sessionScope.admincursos.cupototal}"/>'</c:if>></td>
				</tr>
				<tr>
					<td><span class="style2">*</span> Jornada(s) disponible(s):</td>
					<td>
						<input type="checkbox" name="nivel_" value="0">Mañana <input type="checkbox" name="nivel_" value="0">Tarde <input type="checkbox" name="nivel_" value="0">Sabado
					</td>
				</tr>
				<tr>
					<td><span class="style2">*</span> Activo:</td>
					<td>
						<select name="visible">
							<option value='1' <c:if test="${sessionScope.admincursos.visible == 1}">SELECTED</c:if>>Si</option>
							<option value='2' <c:if test="${sessionScope.admincursos.visible == 2}">SELECTED</c:if>>No</option>
						</select>
					</td>
				</tr>
				<tr>
					<td><span class="style2"></span> Responsable del curso:</td>
					<td><input type="text" name="responsable" size="40" maxlength="80" <c:if test="${sessionScope.admincursos.estado==1}">value='<c:out value="${sessionScope.admincursos.responsable}"/>'</c:if>></td>
				</tr>
				<tr>
					<td><span class="style2"></span> Correo responsable:</td>
					<td><input type="text" name="correoresp" size="50" maxlength="80" <c:if test="${sessionScope.admincursos.estado==1}">value='<c:out value="${sessionScope.admincursos.correoresp}"/>'</c:if>></td>
				</tr>
  	  </table>
 	  </form>
  </font>
  <c:if test="${sessionScope.admincursos.estado==1}">
	  <script>
  		cargarJornada(document.frmNuevo)
	  </script>
  </c:if>