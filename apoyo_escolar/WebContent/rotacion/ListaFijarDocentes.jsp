<jsp:useBean id="rotacion" class="siges.rotacion.beans.Rotacion" scope="session"/>
<jsp:setProperty name="rotacion" property="*"/>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>

<script language="javaScript">
<!--
var locked=<c:out value="${sessionScope.isLockedRotacion}"/>;

	function hacerValidaciones_frmLista(forma){
		validarSeleccion(forma.id, "- Debe seleccionar un item");			
	}
	
	function cancelar(){
		location.href='<c:url value="/bienvenida.jsp"/>';
	}
	
	function editar(){
		if(document.frmLista.id){
			if(validarForma(document.frmLista)){
				document.frmLista.tipo.value='32';
				document.frmLista.cmd.value='Editar';
				document.frmLista.action='<c:url value="/rotacion/ControllerEditar.do"/>';
				document.frmLista.ext2.value='/rotacion/ControllerFiltro.do';
				document.frmLista.submit();
			}
		}
	}
	
	function eliminar(){
		if(document.frmLista.id){
			if(locked!=0){
				alert('Registro solo para consulta');
				return;
			}
			if(validarForma(document.frmLista)){
				if(confirm('¿DESEA ELIMINAR ESTE REGISTRO?')){
					document.frmLista.tipo.value='33';
					document.frmLista.cmd.value='Eliminar';
					document.frmLista.action='<c:url value="/rotacion/ControllerEditar.do"/>';
					document.frmLista.ext2.value='/rotacion/ControllerFiltro.do';
					document.frmLista.submit();
				}
			}
		}
	}

	function lanzar(tipo){
		document.frmLista.tipo.value=tipo;
		document.frmLista.action='<c:url value="/rotacion/ControllerEditar.do"/>';
		document.frmLista.ext2.value='/rotacion/ControllerFiltro.do';
		document.frmLista.target="";
		document.frmLista.submit();
	}

	function lanzar2(tipo){
		document.frmLista.tipo.value=tipo;
		document.frmLista.action='<c:url value="/rotacion/ControllerEditar.do"/>';
		document.frmLista.ext2.value='';
		document.frmLista.target="";
		document.frmLista.submit();
	}
//-->
</script>

<%@include file="../mensaje.jsp" %>

	<font size="1">
		<form action="" method="post" name="frmLista" onSubmit="return validarForma(frmLista)">

			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="ext2" VALUE="">
			<INPUT TYPE="hidden" NAME="height" VALUE="180">
			<INPUT TYPE="hidden" NAME="tipo" VALUE="">
			<input type="hidden" name="ext" value="">
			<input type="hidden" name="guia" value="<c:out value="${requestScope.guia}"/>">

			<table>
				<tr>
					<td rowspan="2" width="780">
						<a href="javaScript:lanzar(10)"><img src='<c:url value="/etc/img/tabs/estructura_0.gif"/>' alt='Estructura' height="26" border="0"></a>
						<a href="javaScript:lanzar(20)"><img src="../etc/img/tabs/espacios_0.gif" alt="Fijar Espacios Físicos" height="26" border="0"></a>
						<!--<img src='<c:url value="/etc/img/tabs/rel_doc_asig_grado_1.gif"/>' alt='Fijar Docentes' height="26" border="0">-->
						<a href="javaScript:lanzar(60)"><img src='<c:url value="/etc/img/tabs/fijar_asignatura_0.gif"/>' alt='Fijar Asignatura' height="26" border="0"></a>
						<a href="javaScript:lanzar(70)"><img src='<c:url value="/etc/img/tabs/espacio_docente_0.gif"/>' alt='Fijar Espacio por Docente' height="26" border="0"></a>
						<a href="javaScript:lanzar(40)"><img src="../etc/img/tabs/espfis_jornada_0.gif" alt="Inhabilitar Espacios Físicos" height="26" border="0"></a>
						<a href="javaScript:lanzar(50)"><img src='<c:url value="/etc/img/tabs/inhabilitar_docentes_0.gif"/>' alt='Inhabilitar Docentes' height="26" border="0"></a>
						<a href="javaScript:lanzar2(80)"><img src='<c:url value="/etc/img/tabs/priorizar_0.gif"/>' alt='Priorizar' height="26" border="0"></a>
						<a href="javaScript:lanzar2(100)"><img src='<c:url value="/etc/img/tabs/fijar_horario_0.gif"/>' alt='Fijar horario' height="26" border="0"></a>
						<!--<a href="javaScript:lanzar2(90)"><img src='<c:url value="/etc/img/tabs/generar_horario_0.gif"/>' alt='Generar Horario' height="26" border="0"></a>-->
						<a href="javaScript:lanzar(130)"><img src='<c:url value="/etc/img/tabs/docente_grupo_0.gif"/>' alt='Docente por grupo' height="26" border="0"></a>
						<a href="javaScript:lanzar(140)"><img src='<c:url value="/etc/img/tabs/espacio_grado_0.gif"/>' alt='Espacio por grado' height="26" border="0"></a>
					</td>
				</tr>
			</table>

		  <table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
		  	<caption>Resultado de la Busqueda.</caption>
			  <c:if test="${empty requestScope.docente}">
					<tr>
						<th class="Fila1" colspan='6'>LA BUSQUEDA NO ARROJO RESULTADOS</th>
					</tr>
				</c:if>
		  	<c:if test="${!empty requestScope.docente}">
			  	<tr>
						<th class="EncabezadoColumna">&nbsp;</th><th class="EncabezadoColumna">Sede</th><th class="EncabezadoColumna">Jornada</th><th class="EncabezadoColumna">Docente</th><th class="EncabezadoColumna">Asignatura</th>
					</tr>
					<c:forEach begin="0" items="${requestScope.docente}" var="fila" varStatus="st">
						<tr>
							<c:set var="guiab"><c:out value="${fila[0]}"/><c:out value="|"/><c:out value="${fila[1]}"/><c:out value="|"/><c:out value="${fila[2]}"/></c:set>
							<td class='Fila<c:out value="${st.count%2}"/>' width='10'><INPUT class='Fila<c:out value="${st.count%2}"/>' type='radio' name='id' value="<c:out value="${fila[0]}"/>|<c:out value="${fila[1]}"/>|<c:out value="${fila[2]}"/>" <c:if test="${requestScope.guia==guiab}">checked</c:if>></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila[6]}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila[7]}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila[9]}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila[8]}"/></td>
						</tr>
					</c:forEach>
				</c:if>
		  </table>
 	  </form>
  </font>