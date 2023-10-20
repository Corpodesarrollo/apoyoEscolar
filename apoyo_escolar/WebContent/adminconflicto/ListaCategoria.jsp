<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<script language="javaScript">
<!--
	function hacerValidaciones_frmLista(forma){
		//validarSeleccion(forma.id, "- Debe seleccionar un item");			
	}
	
	function editar(n){
		if(document.frmLista.id){
			if(validarForma(document.frmLista)){
				document.frmLista.tipo.value='2';
				document.frmLista.id.value=n;
				document.frmLista.cmd.value='Editar';
				document.frmLista.action='<c:url value="/adminconflicto/ControllerEditar.do"/>';
				document.frmLista.submit();
			}
		}
	}
	
	function eliminar(n){
		if(document.frmLista.id){
			if(validarForma(document.frmLista)){
				if(confirm('¿DESEA ELIMINAR ESTE REGISTRO?')){
					document.frmLista.tipo.value='3';
					document.frmLista.id.value=n;
					document.frmLista.cmd.value='Eliminar';
					document.frmLista.action='<c:url value="/adminconflicto/ControllerEditar.do"/>';
					document.frmLista.submit();
				}
			}
		}
	}

	function lanzar(tipo){
		document.frmLista.tipo.value=tipo;
		document.frmLista.action='<c:url value="/adminconflicto/ControllerEditar.do"/>';
		document.frmLista.target="";
		document.frmLista.submit();
	}

	function lanzar2(tipo){
		document.frmLista.tipo.value=tipo;
		document.frmLista.action='<c:url value="/adminconflicto/ControllerEditar.do"/>';
		document.frmLista.target="";
		document.frmLista.submit();
	}
//-->
</script>

<%@include file="../mensaje.jsp" %>

	<font size="1">
		<form action="" method="post" name="frmLista" onSubmit="return validarForma(frmLista)">

			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="height" VALUE="210">
			<INPUT TYPE="hidden" NAME="tipo" VALUE="">
			<input type="hidden" name="ext" value="">
			<input type="hidden" name="id" value="">
			<input type="hidden" name="guia" value="<c:out value="${requestScope.guia}"/>">

			<table>
				<tr>
					<td rowspan="2" width="600">
						<img src='<c:url value="/etc/img/tabs/confcategoria_1.gif"/>' alt='Categorias de Conflicto' height="26" border="0">
						<a href="javaScript:lanzar(10)"><img src='<c:url value="/etc/img/tabs/confclase_0.gif"/>' alt='Clases de Conflicto' height="26" border="0"></a>
						<a href="javaScript:lanzar(20)"><img src='<c:url value="/etc/img/tabs/conftipo_0.gif"/>' alt='Tipos de Conflicto' height="26" border="0"></a>
					</td>
				</tr>
			</table>

		  <table border="0" align="center" width="95%" cellpadding="0" cellspacing="0">
		  	<caption>Resultado de la Busqueda.</caption>
			  <c:if test="${empty requestScope.tcategoria}">
					<tr>
						<th class="Fila1" colspan='6'>LA BUSQUEDA NO ARROJO RESULTADOS</th>
					</tr>
				</c:if>
		  	<c:if test="${!empty requestScope.tcategoria}">
			  	<tr>
						<th class="EncabezadoColumna">&nbsp;</th>
						<th class="EncabezadoColumna">Código</th>
						<th class="EncabezadoColumna">Nombre</th>
						<th class="EncabezadoColumna">Orden</th>
					</tr>
					<c:forEach begin="0" items="${requestScope.tcategoria}" var="fila" varStatus="st">
						<tr>
							<c:set var="guiab"><c:out value="${fila[0]}"/></c:set>
							<td class='Fila<c:out value="${st.count%2}"/>'>
								<a href='javaScript:editar("<c:out value="${guiab}"/>");' title="Editar Registro"><img border='0' src='../etc/img/<c:if test="${requestScope.guia==guiab}">X</c:if>editar.png' width='15' height='15'></a>
								<a href='javaScript:eliminar("<c:out value="${guiab}"/>");' title="Eliminar Registro"><img border='0' src='../etc/img/eliminar.png' width='15' height='15'></a>
							</td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila[0]}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila[1]}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila[2]}"/></td>
						</tr>
					</c:forEach>
				</c:if>
		  </table>
 	  </form>
  </font>