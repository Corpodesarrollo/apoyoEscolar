<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage=""%>
<jsp:useBean id="organizacionParams" class="siges.institucion.organizacion.beans.Params" scope="page" />
<jsp:useBean id="asociacionVO" class="siges.institucion.organizacion.beans.AsociacionVO" scope="session" />
<%@include file="../../parametros.jsp"%>
<style type="text/css">@import url(<c:url value="/etc/css/calendar-win2k-1.css"/>);</style>
<script type="text/javascript"
	src="<c:url value="/etc/js/calendar.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/etc/js/lang/calendar-es.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/etc/js/calendar-setup.js"/>"></script>
<c:if test="${sessionScope.NivelPermiso==1}">
	<c:set var="permisoBoton" value="none" />
</c:if>
<script languaje='javaScript'>			
			<!--
			var fichaLideres=1;
			var fichaGobierno=1;
			var fichaAsociacion=1;
			
		  var nav4=window.Event ? true : false;
		  
		  function acepteNumeros(eve){
			var key=nav4?eve.which :eve.keyCode;
			return(key<=13 || (key>=48 && key<=57));
		  }
			
			function cancelar(){
					document.frmNuevo.cmd.value="Cancelar";
					document.frmNuevo.submit();
			}
			
  		function hacerValidaciones_frmNuevo(forma){				
		    validarCampo(forma.asoNombre, "- Nombre", 1, 250)
		    validarCampo(forma.asoFechaConstitucion, "- Fecha de constitución", 1, 12)
		    validarCampo(forma.asoVigencia, "- Vigencia", 1, 4)
			}
			
			function inhabilitarFormulario(){
				for(var i=0;i<document.frmNuevo.elements.length;i++){
					if(document.frmNuevo.elements[i].type != "hidden" && document.frmNuevo.elements[i].type != "button" && document.frmNuevo.elements[i].type != "submit")
					document.frmNuevo.elements[i].disabled=true;
				}
			}
			
			function lanzar(tipo){
				document.frmNuevo.tipo2.value=tipo;
				document.frmNuevo.target='centro';
				document.frmNuevo.action="<c:url value="/institucion/organizacion/ControllerOrganizacionEdit.do"/>";
				document.frmNuevo.submit();
			}
			
			function guardar(tipo){
				if(validarForma(document.frmNuevo)){
					document.frmNuevo.tipo2.value=tipo;
					document.frmNuevo.cmd.value="Guardar";
					document.frmNuevo.submit();
				}
			}
			
			function editar(tipo,nombre,tipo2){	
				document.frm.target='centro';
				document.frm.ext.value='';
				document.frm.tipo2.value=tipo;
				document.frm.cmd.value=nombre;
				if(document.frm.cmd.value=='Eliminar'){
					if (isChecked(document.frm.r)){
						if(!confirm("Realmente desea eliminarlo?"))
							return false;
					}
				}
				if(!validarForma(document.frm)) return false;
				if(document.frm.cmd.value=='Nuevo' || document.frm.cmd.value=='Editar'){
					remote = window.open("","3","width=700,height=200,resizable=no,toolbar=no,directories=no,menubar=no,status=yes");
					document.frm.target='3';
					document.frm.ext.value='1';
					document.frm.tipo2.value=tipo2;
					remote.moveTo(200,200);
					if (remote.opener == null) remote.opener = window;
					remote.opener.name = "centro";
					remote.focus();
				}
				return true;	
			}
			
			function hacerValidaciones_frm(forma){
				if(forma.cmd.value!='Nuevo')
					validarSeleccion(forma.r, "- Debe seleccionar un item")
			}
			
			function ocultar(t1,t2,t3){
				document.getElementById(t1).style.display='';
				document.getElementById(t2).style.display='none';
				document.getElementById(t3).style.display='none';
			}
			
		//-->
	</script>
<%@include file="../../mensaje.jsp"%>
<form method="post" name="frmNuevo"	onSubmit=" return validarForma(frmNuevo)"	action='<c:url value="/institucion/organizacion/OrganizacionGuardar.jsp"/>'>
	<input type="hidden" name="tipo2"	value='<c:out value="${pageScope.organizacionParams.FICHA_ASO}"/>'> 
	<input type="hidden" name="cmd" value="Cancelar">
	<INPUT TYPE="hidden" NAME="height" VALUE='150'> 
	<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe2}"/>'>
<!--//////////////////--> <!-- FICHAS A MOSTRAR AL USUARIO -->
<table cellpadding="0" cellspacing="0" border="1" ALIGN="CENTER" width="100%" bordercolor="#FFFFFF">
	<tr height="1">
		<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
		<td rowspan="2" width="600" bgcolor="#FFFFFF"><script>
				if(fichaLideres==1)document.write('<a href="javaScript:lanzar(<c:out value="${pageScope.organizacionParams.FICHA_LID}"/>)"><img src="<c:url value="/etc/img/tabs/lideres_0.gif"/>" alt=""  height="26" border="0"></a>');
				if(fichaGobierno==1)document.write('<a href="javaScript:lanzar(<c:out value="${pageScope.organizacionParams.FICHA_GOB}"/>)"><img src="<c:url value="/etc/img/tabs/gobierno_escolar_0.gif"/>" alt=""  height="26" border="0"></a>');
				if(fichaAsociacion==1)document.write('<img src="<c:url value="/etc/img/tabs/asociacion_padres_1.gif"/>" alt=""  height="26" border="0">');
				</script></td>
	</tr>
</table>
<!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
	<table border="1" align="center" bordercolor="#FFFFFF" width="100%" cellpadding="0" cellSpacing="0">
	<caption>ASOCIACIÓN</caption>
		<tr>
			<td width="45%">
			<input name="cmd1" type="button" class='boton' value="Guardar" onClick="guardar(document.frmNuevo.tipo2.value)" style='display:<c:out value="${permisoBoton}"/>'> 
			<input name="cmd1" type="button" class='boton' value="Cancelar" onClick="cancelar()">
			</td>
		</tr>
	</table>
	<TABLE width="100%" cellpadding="0" cellSpacing="0">
	<tr>
		<td><span class="style2">*</span> Nombre:</td>
		<td colspan="3"><input type="text" name="asoNombre" maxlength="100"
			size="70"
			value='<c:out value="${sessionScope.asociacionVO.asoNombre}"/>'></td>
	</tr>
	<tr>
		<td>Personería jurídica:</td>
		<td><input type="text" name="asoPersoneria" maxlength="20" size="25"
			value='<c:out value="${sessionScope.asociacionVO.asoPersoneria}"/>'></td>
		<td><span class="style2">*</span> Vigencia:</td>
		<td><input type="text" name="asoVigencia"
			onKeyPress='return acepteNumeros(event)' maxlength="4" size="5"
			value='<c:out value="${sessionScope.asociacionVO.asoVigencia}"/>'></td>
	</tr>
	<tr>
		<td><span class="style2">*</span> Fecha de constitución:<br>
		<input type="text" name="asoFechaConstitucion" maxlength="10"
			size="12"
			value='<c:out value="${sessionScope.asociacionVO.asoFechaConstitucion}"/>'
			readonly><a href="javaScript:ocultar('td1','td2','td3')"><img
			border="0" src='<c:url value="/etc/img/calendario.gif"/>'></a></td>
		<td>Fecha de inicio:<br>
		<input type="text" name="asoFechaInicio" maxlength="10" size="12"
			value='<c:out value="${sessionScope.asociacionVO.asoFechaInicio}"/>'
			readonly><a href="javaScript:ocultar('td2','td1','td3')"><img
			border="0" src='<c:url value="/etc/img/calendario.gif"/>'></a></td>
		<td>Fecha de finalización:<br>
		<input type="text" name="asoFechaFin" maxlength="10" size="12"
			value='<c:out value="${sessionScope.asociacionVO.asoFechaFin}"/>'
			readonly><a href="javaScript:ocultar('td3','td2','td1')"><img
			border="0" src='<c:url value="/etc/img/calendario.gif"/>'></a></td>
	</tr>
</TABLE>
<TABLE width="100%" cellpadding="3" cellSpacing="0" align="center">
	<tr align="center" id='td1' style="display:none">
		<td width="33%">
		<div style="float: left; margin-rigth: 1em; margin-bottom: 1em;"
			id="calendar-container"></div>
		</td>
		<td width="33%">&nbsp;</td>
		<td width="33%">&nbsp;</td>
	</tr>
	<tr align="center" id='td2' style="display:none">
		<td width="33%">&nbsp;</td>
		<td width="33%">
		<div style="float: left; margin-rigth: 1em; margin-bottom: 1em;"
			id="calendar-container2"></div>
		</td>
		<td width="33%">&nbsp;</td>
	</tr>
	<tr align="center" id='td3' style="display:none">
		<td width="33%">&nbsp;</td>
		<td width="33%">&nbsp;</td>
		<td width="33%">
		<div style="float: left; margin-rigth: 1em; margin-bottom: 1em;"
			id="calendar-container3"></div>
		</td>
	</tr>
</TABLE>
</form>
	<form method="post" align="center" name="frm" onSubmit="return validarForma(frm)" action='<c:url value="/institucion/organizacion/ControllerOrganizacionEdit.do"/>'>
		<input type="hidden" name="tipo2" value='<c:out value="${pageScope.organizacionParams.FICHA_ASO}"/>'> 
		<input type="hidden" name="forma1" value='<c:out value="${pageScope.organizacionParams.FICHA_ASO}"/>'> 
		<input type="hidden" name="forma2" value='<c:out value="${pageScope.organizacionParams.FICHA_ASO2}"/>'> 
		<input type="hidden" name="cmd" value=""> 
		<input type="hidden" name="ext" value=""> 
		<input type="hidden" NAME="height" VALUE='150'> 
		<input type="hidden" name="s" value=""> 
		<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe2}"/>'>
	<table border="1" align="center" width="100%" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF">
		<caption>INTEGRANTES DE LA ASOCIACIÓN</caption>
		<tr>
			<th colspan='4' align='left'>
			<c:if test="${!empty requestScope.filtroAsociaciones}">
				<input name="cmd1" type="submit" class='boton' value="Editar" onClick="return editar(document.frm.forma1.value,'Editar',document.frm.forma2.value)"> 
				<input name="cmd1" type="submit" class='boton' value="Eliminar" onClick="return editar(document.frm.forma1.value,'Eliminar',document.frm.forma2.value)" style='display:<c:out value="${permisoBoton}"/>'>
			</c:if>
				<input name="cmd1" type="submit" class='boton' value="Nuevo" onClick="return editar(document.frm.forma1.value,'Nuevo',document.frm.forma2.value)" style='display:<c:out value="${permisoBoton}"/>'> 
				</th>				
		</tr>
		<tr>
			<th width='10' class="EncabezadoColumna"></th>
			<th class="EncabezadoColumna" width="15%"><b>Identificación</b></th>
			<th class="EncabezadoColumna" width="40%"><b>Nombre</b></th>
			<th class="EncabezadoColumna" width="40%"><b>Cargo</b></th>
		</tr>
		<c:forEach begin="0" items="${requestScope.filtroAsociaciones}" var="fila" varStatus="st">
			<tr>
				<td class='Fila<c:out value="${st.count%2}"/>' width='1%'>
				<input type='radio' name='r' value='<c:out value="${fila[0]}"/>' onClick='document.frm.s.value="<c:out value="${fila[1]}"/>"'></td>
				<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[2]}" />: <c:out value="${fila[3]}" /></td>
				<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[4]}" /></td>
				<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[5]}" /></td>
			</tr>
		</c:forEach>
	</table>
	</form>
<script type="text/javascript"><c:if test="${sessionScope.NivelPermiso==1}">inhabilitarFormulario();</c:if></script>
<script type="text/javascript">
  function dateChanged(calendar) {
    if (calendar.dateClicked) {
      var y_ = calendar.date.getFullYear();
      var m = parseInt(calendar.date.getMonth())+1;     // integer, 0..11
      var d = calendar.date.getDate();      // integer, 1..31
      if(parseInt(m)<10){
      	var m_="0"+m;
     	}else{
     		var m_=m;
     	}
      if(parseInt(d)<10){
      	var d_="0"+d;
      }else{
      	var d_=d;
      }
      document.frmNuevo.asoFechaConstitucion.value=d_+"/"+m_+"/"+y_;
      document.getElementById('td1').style.display='none';
    }
  };

  function dateChanged2(calendar) {
    if (calendar.dateClicked) {
      var y_ = calendar.date.getFullYear();
      var m = parseInt(calendar.date.getMonth())+1;     // integer, 0..11
      var d = calendar.date.getDate();      // integer, 1..31
      if(parseInt(m)<10){
      	var m_="0"+m;
     	}else{
     		var m_=m;
     	}
      if(parseInt(d)<10){
      	var d_="0"+d;
      }else{
      	var d_=d;
      }
      document.frmNuevo.asoFechaInicio.value=d_+"/"+m_+"/"+y_;
      document.getElementById('td2').style.display='none';
    }
  };
	
  function dateChanged3(calendar) {
    if (calendar.dateClicked) {
      var y_ = calendar.date.getFullYear();
      var m = parseInt(calendar.date.getMonth())+1;     // integer, 0..11
      var d = calendar.date.getDate();      // integer, 1..31
      if(parseInt(m)<10){
      	var m_="0"+m;
     	}else{
     		var m_=m;
     	}
      if(parseInt(d)<10){
      	var d_="0"+d;
      }else{
      	var d_=d;
      }
      document.frmNuevo.asoFechaFin.value=d_+"/"+m_+"/"+y_;
      document.getElementById('td3').style.display='none';
    }
  };
  
  Calendar.setup(
    {
      flat         : "calendar-container", // ID of the parent element
      flatCallback : dateChanged           // our callback function
    }
  );
  Calendar.setup(
    {
      flat         : "calendar-container2", // ID of the parent element
      flatCallback : dateChanged2           // our callback function
    }
  );
  Calendar.setup(
    {
      flat         : "calendar-container3", // ID of the parent element
      flatCallback : dateChanged3           // our callback function
    }
  );
</script>