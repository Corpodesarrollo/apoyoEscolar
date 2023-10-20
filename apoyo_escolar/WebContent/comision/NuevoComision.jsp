<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<style type="text/css">@import url(<c:url value="/etc/css/calendar-win2k-1.css"/>);</style>
<script type="text/javascript" src="<c:url value="/etc/js/calendar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/lang/calendar-es.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/calendar-setup.js"/>"></script>
<script>
		<!--
			var nav4=window.Event ? true : false;			
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frmNuevo(forma){
			  validarCampo(forma.numacta, "- Número de acta", 1, 10)
				validarCampo(forma.fecha, "- Fecha", 1, 15)
				validarCampo(forma.sitactual, "- Situación Actual", 1, 20)
				validarCampo(forma.recomendacion, "- Recomendaciones", 1,20)
			}
			function lanzar(tipo){				
				document.frmNuevo.tipo.value=tipo;					
				document.frmNuevo.action="<c:url value="/comision/ControllerNuevoEdit.do"/>";
				document.frmNuevo.submit();
			}
			function guardar(tipo){
				alert("La información fue ingresada satisfactoriamente");
/*				if(validarForma(document.frmNuevo)){
					document.frmNuevo.tipo.value=tipo;
					document.frmNuevo.cmd.value="Guardar";
					document.frmNuevo.action='<c:url value="/comision/ControllerNuevoSave.do"/>';
					document.frmNuevo.submit();
				}	*/
			}
			function cancelar(){
				if (confirm('¿Desea cancelar la edición de la comisi&oacute;n de evaluaci&oacute;n?')){
 					document.frmNuevo.cmd.value="Cancelar";
					document.frmNuevo.action='<c:url value="/bienvenida.jsp"/>';
          document.frmNuevo.submit(); 
				}		
			}
			function inhabilitarFormulario(){
				for(var i=0;i<document.frmNuevo.elements.length;i++){
					if(document.frmNuevo.elements[i].type != "hidden" && document.frmNuevo.elements[i].type != "button" && document.frmNuevo.elements[i].type != "submit")
					document.frmNuevo.elements[i].disabled=true;
				}	
			}			
			//-->
</script>
<%@include file="../mensaje.jsp"%>
<font size="1">
	<form name="f" target='1' action='<c:url value="/comision/ControllerFiltroSave.do"/>' ></form>
	<form method="post" name="frmNuevo" onSubmit="return validarForma(frmNuevo)" action='<c:url value="/comision/NuevoGuardar.jsp"/>'>

		<table border="1" align="center" bordercolor="#FFFFFF" width="100%">
			<tr>
			  <td width="45%">
					<input  name="cmd1" type="button" value="Guardar" onClick="guardar(1)" class="boton">
			  </td>
			</tr>
	  </table>
	
		<input type="hidden" name="tipo" value=''>
		<input type="hidden" name="cmd" value="Cancelar">
		<INPUT TYPE="hidden" NAME="height" VALUE='200'>
		<input type="hidden" name="ext2" value='<c:out value="${sessionScope.subframe}"/>'>
		
	  <table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
  	  <tr height="1">
				<td width="10">&nbsp;</td>
	      <td rowspan="2" width="469"><img src="../etc/img/tabs/Comision_Evaluacion_1.gif" alt="Comisi&oacute;n de Evaluaci&oacute;n"  height="26" border="0"></td>
			</tr>
	  </table>
	  
	  <table cellpadding="0" cellspacing="0" border="0" width="100%">
	  	<tr>
				<td>
					<span class="style2">*</span> Acta N&uacute;mero:
				</td>
				<td>
					<input type="text" name="numacta"  maxlength="10" size='10' onKeyPress='return acepteNumeros(event)'>
				</td>
	  	</tr>
	  	<tr>
	  		<td>
					<span class="style2">*</span> Fecha:
				</td>
				<td>
					<input type="text" name="fecha"  maxlength="15" size='15' value='' readOnly='true'>
					<img src="<c:url value="/etc/img/calendario.gif"/>" alt="Seleccione una fecha" id="imgfecha"
			     style="cursor: pointer;
			     title="Date selector"
			     onmouseover="this.style.background='red';"
			     onmouseout="this.style.background=''" />
				</td>
	  	</tr>
	  	<tr>
	  		<td>
					<span class="style2">*</span> Situaci&oacute;n actual:
				</td>
				<td>
					<textarea name="sitactual" rows='4' cols='70' wrap='virtual'></textarea>
				</td>
	  	</tr>	  	
	  	<tr>
	  		<td>
					<span class="style2">*</span> Recomendaciones:
				</td>
				<td>
					<textarea name="recomendacion" rows='4' cols='70' wrap='virtual'></textarea>
				</td>
	  	</tr>	  	
	  </table>
	
	</form>
</font>
<script type="text/javascript">
    Calendar.setup({
        inputField     :    "fecha",
        ifFormat       :    "%m/%d/%Y",
        button         :    "imgfecha",
        align          :    "Br"
    });
</script>