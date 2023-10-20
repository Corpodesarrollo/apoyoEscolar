<jsp:useBean id="adminVO" class="siges.grupoPeriodo.beans.AdminVO" scope="session"/><jsp:setProperty name="adminVO" property="*"/>
<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%><c:set var="tip" value="3" scope="page"/>
<style type="text/css">@import url(<c:url value="/etc/css/calendar-win2k-1.css"/>);</style>
<script type="text/javascript" src="<c:url value="/etc/js/calendar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/lang/calendar-es.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/calendar-setup.js"/>"></script>
<script languaje='javaScript'>
		<!--
			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frmNuevo(forma){
				validarLista(forma.adminUsuarioBitacora, "- Usuario",1)
				validarCampo(forma.adminFechaBitacora, "- Fecha inicial",1,10)
				validarCampo(forma.adminFechaBitacoraFinal, "- Fecha final",1,10)
			} 
			
			function lanzar(tipo){				
				document.frmNuevo.tipo.value=tipo;
				document.frmNuevo.action="<c:url value="/grupoPeriodo/ControllerAdminEdit.do"/>";
				document.frmNuevo.target="";
				document.frmNuevo.submit();
			}
			
			function guardar(forma,tipo){
				if(validarForma(forma)){
					forma.tipo.value=tipo;
					forma.submit();
				}
			}
			
			function cancelar(){
				location.href='<c:url value="/bienvenida.jsp"/>';
			}
			
			//-->
	</script>
<%@include file="../mensaje.jsp"%>
	<font size="1">	
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/grupoPeriodo/adminBitacoraGuardar.jsp"/>'>
	<br>
	<table border="0" align="center" bordercolor="#FFFFFF" width="95%">
	<caption>Gestión Administrativa >> Bitácora de acciones de usuario</caption>
		<tr>
		  <td width="45%" bgcolor="#FFFFFF">
				<input class='boton' name="cmd1" type="button" value="Aceptar" onClick="guardar(document.frmNuevo,4)">
				<input class='boton' name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
		  </td>
		</tr>
	</table>
<!--//////////////////-->		
	<!-- FICHAS A MOSTRAR AL USUARIO -->
	<input type="hidden" name="tipo" value='<c:out value="${tip}"/>'>
	<input type="hidden" name="cmd" value="Cancelar">
	<input type="hidden" name="send" value="">
	<input type="hidden" name="adminInst" value='<c:out value="${sessionScope.login.instId}"/>'>
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr height="1">
			<td width="10" bgcolor="#FFFFFF">&nbsp;</td>
			<td rowspan="2" bgcolor="#FFFFFF"><img src="../etc/img/tabs/consulta_bitacora_1.gif" border="0"  height="26" alt='--'></td>
		</tr>
  </table><!-- FIN DE FICHAS A MOSTRAR AL USUARIO -->
	<TABLE width="100%" cellpadding="1" cellSpacing="0" border=0>
		<tr>
			<td colspan="2">&nbsp;</td>
		</TR>	
			<tr>
				<td><span class="style2">*</span>Usuario:</td>	
				<td>
						<select name="adminUsuarioBitacora" style='width:300px;'>
							<option value='-1'>-- Seleccione uno --</option>
								<c:forEach begin="0" items="${requestScope.listaUsuarios}" var="fila">
								<option value="<c:out value="${fila[0]}"/>" <c:if test="${sessionScope.adminVO.adminUsuarioBitacora==fila[0]}">SELECTED</c:if>><c:out value="${fila[1]}"/></option>
								</c:forEach>
						</select>				
				</td>	
			</tr>	
			<tr>
				<td><span class="style2">*</span>Fecha Inicial:</td>
				<td>
				<input type="text" name="adminFechaBitacora"  maxlength="10" size="11" value='<c:out value="${sessionScope.adminVO.adminFechaBitacora}"/>' readonly="true"></td>
			</tr>
			<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Click para modificar --></td>
				<td>&nbsp;<div style="float: left; margin-rigth: 1em; margin-bottom: 1em;" id="calendar-container"></div></td>
			<tr>
			   <tr>
    <td><span class="style2">*</span>Fecha final:</td>
    <td>
    <input type="text" name="adminFechaBitacoraFinal"  maxlength="10" size="11" value='<c:out value="${sessionScope.adminVO.adminFechaBitacoraFinal}"/>' readonly="true"></td>
   </tr>
   <tr>
    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Click para modificar --></td>
    <td>&nbsp;<div style="float: left; margin-rigth: 1em; margin-bottom: 1em;" id="calendar-container2"></div></td>
   <tr>
	</TABLE>
	</font>
</form>
<script type="text/javascript">
  function dateChanged1(calendar) {
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
      document.frmNuevo.adminFechaBitacora.value=d_+"/"+m_+"/"+y_;
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
	      document.frmNuevo.adminFechaBitacoraFinal.value=d_+"/"+m_+"/"+y_;
	    }
	  };

  Calendar.setup(
    {
      flat         : "calendar-container", // ID of the parent element
      flatCallback : dateChanged1           // our callback function
    }
  );
  Calendar.setup(
		    {
		      flat         : "calendar-container2", // ID of the parent element
		      flatCallback : dateChanged2           // our callback function
		    }
		  );
</script>