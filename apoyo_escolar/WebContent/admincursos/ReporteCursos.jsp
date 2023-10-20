<%@ page language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
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

	function buscar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.tipo.value=5;
			document.frmFiltro.cmd.value="Guardar";
			document.frmFiltro.submit();
		}	
	}
	
	function lanzar(tipo){
		document.frmFiltro.tipo.value=tipo;
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function hacerValidaciones_frmFiltro(forma){
		
	}

//-->
</script>
<%@include file="../mensaje.jsp" %>
	<font size="1">
		<form method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)" action="<c:url value="/admincursos/ControllerEditar.do"/>">
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="height" VALUE="180">
			<INPUT TYPE="hidden" NAME="tipo" VALUE="">
			<input type="hidden" name="ext" value="">

			<table>
				<tr>
					<td rowspan="2" width="780">
						<a href="javaScript:lanzar(0)"><img src='<c:url value="/etc/img/tabs/admincursos_0.gif"/>' alt='Administración de cursos' height="26" border="0"></a>
						<img src='<c:url value="/etc/img/tabs/reportecursos_1.gif"/>' alt='Reporte de cursos' height="26" border="0">
				</tr>
			</table>

  	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
				<caption>Reporte de cursos</caption>
				<tr>
		      <td align="center"><span class="ENUNCIADO">Para generar un reporte sobre los docentes que se han inscrito a los cursos existentes, de click en el boton 'Generar Reporte'</span><br></td>
				</tr>
				<tr>
				  <td align="center">
					  <input name="cmd2" class="boton" type="button" value="Generar Reporte" onClick="buscar()">
				  </td>
				</tr>
				<tr><td></td></tr>
		</form>
				<c:if test="${!empty requestScope.reporte && requestScope.reporte!='--' && requestScope.reporte!='0'}">
					<tr>
						<td colspan="6" align='center' valign="top">
							<form name='frmGenerar' action='<c:url value="/${requestScope.reporte}"/>' method='post' target='_blank'>
								<input type='hidden' name='dir' value='<c:out value="${requestScope.reporte}"/>'>
								<input type='hidden' name='tipo' value='xls'>
								<input type='hidden' name='accion' value='0'>
								<input type='hidden' name='aut' value='1'>El reporte fue generado satisfactoriamente.<br>Pulse en el Icono para descargar el archivo.<br>
							</form>
							>><a href='javaScript:document.frmGenerar.submit();'><img border=0 src='<c:url value="/etc/img/xls.gif"/>'></A><<
						</td>
					</tr>
				</c:if>
				<c:if test="${requestScope.reporte=='--'}">
					<tr>
						<td colspan="6" valign="top" align='center'>No se pudo generar el reporte.<br></td>
					</tr>
				</c:if>
  	  </table>
  </font>
  <script>
  </script>