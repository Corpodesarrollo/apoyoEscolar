<%@ page language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<jsp:useBean id="filtroconflicto" class="siges.adminconflicto.beans.FiltroConflicto" scope="session"/>
<jsp:setProperty name="filtroconflicto" property="*"/>
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
			if(document.frmFiltro.fcategoria.selectedIndex>0){
				document.frmFiltro.catnombre.value=document.frmFiltro.fcategoria[document.frmFiltro.fcategoria.selectedIndex].text;
			}
			if(document.frmFiltro.fclase.selectedIndex>0){
				document.frmFiltro.clanombre.value=document.frmFiltro.fclase[document.frmFiltro.fclase.selectedIndex].text;
			}
			document.frmFiltro.tipo.value=20;
			document.frmFiltro.cmd.value="Buscar";
			document.frmFiltro.action='<c:url value="/adminconflicto/NuevoGuardar.jsp"/>';
			document.frmFiltro.submit();
		}	
	}
	
	function lanzar(tipo){
		document.frmFiltro.tipo.value=tipo;
		document.frmFiltro.action='<c:url value="/adminconflicto/ControllerEditar.do"/>';
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}
	
	function lanzar2(tipo){
		document.frmFiltro.tipo.value=tipo;
		document.frmFiltro.action='<c:url value="/adminconflicto/ControllerEditar.do"/>';
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}

	function hacerValidaciones_frmFiltro(forma){
		//validarLista(forma.tcategoria, "- Seleccione una categoria",1)
		//validarLista(forma.tclase, "- Seleccione una clase",1)
	}
//-->
</script>
<%@include file="../mensaje.jsp" %>
	<font size="1">
		<form method="post" name="frmFiltro" onSubmit="return validarForma(frmFiltro)" action="">
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="height" VALUE="210">
			<INPUT TYPE="hidden" NAME="tipo" VALUE="">
			<input type="hidden" name="ext" value="">
			<input type="hidden" name="catnombre" value="">
			<input type="hidden" name="clanombre" value="">
			<input type="hidden" name="estadof" value="1">

			<table>
				<tr>
				  <td width="45%" align="left">
					  <input name="cmd2" class="boton" type="button" value="Buscar" onClick="buscar()">
				  </td>
				</tr>
				<tr>
					<td rowspan="2" width="780">
						<a href="javaScript:lanzar(0)"><img src='<c:url value="/etc/img/tabs/confcategoria_0.gif"/>' alt='Categorias de Conflicto' height="26" border="0"></a>
						<a href="javaScript:lanzar(10)"><img src='<c:url value="/etc/img/tabs/confclase_0.gif"/>' alt='Clases de Conflicto' height="26" border="0"></a>
						<img src='<c:url value="/etc/img/tabs/conftipo_1.gif"/>' alt='Tipos de Conflicto' height="26" border="0">
					</td>
				</tr>
			</table>

  	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
				<caption>B&uacute;squeda de Tipos de Conflicto Escolar</caption>
				<tr>
					<td><span class="style2" ></span>Categoria:</td>
			    <td colspan="2">
						<select name="fcategoria">
							<option value='-1'>-- TODOS --</option>
							<c:forEach begin="0" items="${requestScope.tcategoria}" var="fila">
								<option value="<c:out value="${fila[0]}"/>"><c:out value="${fila[1]}"/></option>
							</c:forEach>
						</select>
			    </td>
			   </tr>
			   <tr>
					<td><span class="style2" ></span>Clase:</td>
			    <td colspan="2">
						<select name="fclase">
							<option value='-1'>-- TODOS --</option>
							<c:forEach begin="0" items="${requestScope.tclase}" var="fila">
								<option value="<c:out value="${fila[0]}"/>"><c:out value="${fila[1]}"/></option>
							</c:forEach>
						</select>
			    </td>
				</tr>
  	  </table>
 	  </form>
  </font>
  <script>
  </script>