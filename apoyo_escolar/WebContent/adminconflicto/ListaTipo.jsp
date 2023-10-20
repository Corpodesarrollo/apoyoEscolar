<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<jsp:useBean id="filtroconflicto" class="siges.adminconflicto.beans.FiltroConflicto" scope="session"/>
<jsp:setProperty name="filtroconflicto" property="*"/>
<script language="javaScript">
<!--
	function hacerValidaciones_frmLista(forma){
		//validarSeleccion(forma.id, "- Debe seleccionar un item");			
	}
	
	function cancelar(){
		location.href='<c:url value="/bienvenida.jsp"/>';
	}
	
	function editar(n){
		if(document.frmLista.id){
			if(validarForma(document.frmLista)){
				document.frmLista.tipo.value='22';
				document.frmLista.id.value=n;
				document.frmLista.cmd.value='Editar';
				document.frmLista.action='<c:url value="/adminconflicto/NuevoGuardar.jsp"/>';
				document.frmLista.submit();
			}
		}
	}
	
	function eliminar(n){
		if(document.frmLista.id){
			if(validarForma(document.frmLista)){
				if(confirm('¿DESEA ELIMINAR ESTE REGISTRO?')){
					document.frmLista.tipo.value='23';
					document.frmLista.id.value=n;
					//document.frmLista.estadof.value="-1";
					document.frmLista.cmd.value='Eliminar';
					document.frmLista.action='<c:url value="/adminconflicto/NuevoGuardar.jsp"/>';
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
	
	function buscar(){
			document.frmLista.tipo.value=20;
			document.frmLista.cmd.value="Buscar";
			document.frmLista.estadof.value="-1";
			document.frmLista.action='<c:url value="/adminconflicto/ControllerEditar.do"/>';
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
			<input type="hidden" name="estadof" value="1">
			<input type="hidden" name="catnombre" value="<c:out value="${sessionScope.filtroconflicto.catnombre}"/>">
			<input type="hidden" name="clanombre" value="<c:out value="${sessionScope.filtroconflicto.clanombre}"/>">
			<input type="hidden" name="fcategoria" value="<c:out value="${sessionScope.filtroconflicto.fcategoria}"/>">
			<input type="hidden" name="fclase" value="<c:out value="${sessionScope.filtroconflicto.fclase}"/>">

			<table>
				<tr>
				  <td width="45%" align="left">
					  <input name="cmd2" class="boton" type="button" value="Volver a Buscar" onClick="buscar()">
				  </td>
				</tr>
				<tr>
					<td rowspan="2" width="600">
						<a href="javaScript:lanzar(0)"><img src='<c:url value="/etc/img/tabs/confcategoria_0.gif"/>' alt='Categorias de Conflicto' height="26" border="0"></a>
						<a href="javaScript:lanzar(10)"><img src='<c:url value="/etc/img/tabs/confclase_0.gif"/>' alt='Clases de Conflicto' height="26" border="0"></a>
						<img src='<c:url value="/etc/img/tabs/conftipo_1.gif"/>' alt='Tipos de Conflicto' height="26" border="0">
					</td>
				</tr>
			</table>

		  <table border="0" align="center" width="95%" cellpadding="0" cellspacing="0">
		  	<caption>Resultado de la Busqueda.<br/><c:if test="${!empty requestScope.catnombre}"> Categoria: <c:out value="${requestScope.catnombre}"/></c:if><c:if test="${!empty requestScope.clanombre}">; Clase: <c:out value="${requestScope.clanombre}"/></c:if></caption>
			  <c:if test="${empty requestScope.ttipo}">
					<tr>
						<th class="Fila1" colspan='6'>LA BUSQUEDA NO ARROJO RESULTADOS</th>
					</tr>
				</c:if>
				<c:if test="${!empty requestScope.ttipo}">
					<c:if test="${!empty requestScope.catnombre}">
				  	<c:if test="${empty requestScope.clanombre}">
				  		<tr>
								<th class="EncabezadoColumna">&nbsp;</th>
								<th class="EncabezadoColumna">Nombre</th>
								<th class="EncabezadoColumna">Clase</th>
								<th class="EncabezadoColumna">Orden</th>
							</tr>
							<c:forEach begin="0" items="${requestScope.ttipo}" var="fila" varStatus="st">
								<tr>
									<c:set var="guiab"><c:out value="${fila[0]}"/></c:set>
									<td class='Fila<c:out value="${st.count%2}"/>'>
										<a href='javaScript:editar("<c:out value="${guiab}"/>");' title="Editar Registro"><img border='0' src='../etc/img/<c:if test="${requestScope.guia==guiab}">X</c:if>editar.png' width='15' height='15'></a>
										<a href='javaScript:eliminar("<c:out value="${guiab}"/>");' title="Eliminar Registro"><img border='0' src='../etc/img/eliminar.png' width='15' height='15'></a>
									</td>
									<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila[1]}"/></td>
									<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila[3]}"/></td>
									<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila[6]}"/></td>
								</tr>
							</c:forEach>
						</c:if>
					</c:if>
					<c:if test="${empty requestScope.catnombre}">
				  	<c:if test="${!empty requestScope.clanombre}">
				  		<tr>
								<th class="EncabezadoColumna">&nbsp;</th>
								<th class="EncabezadoColumna">Nombre</th>
								<th class="EncabezadoColumna">Categoria</th>
								<th class="EncabezadoColumna">Orden</th>
							</tr>
							<c:forEach begin="0" items="${requestScope.ttipo}" var="fila" varStatus="st">
								<tr>
									<c:set var="guiab"><c:out value="${fila[0]}"/></c:set>
									<td class='Fila<c:out value="${st.count%2}"/>'>
										<a href='javaScript:editar("<c:out value="${guiab}"/>");' title="Editar Registro"><img border='0' src='../etc/img/<c:if test="${requestScope.guia==guiab}">X</c:if>editar.png' width='15' height='15'></a>
										<a href='javaScript:eliminar("<c:out value="${guiab}"/>");' title="Eliminar Registro"><img border='0' src='../etc/img/eliminar.png' width='15' height='15'></a>
									</td>
									<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila[1]}"/></td>
									<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila[2]}"/></td>
									<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila[6]}"/></td>
								</tr>
							</c:forEach>
						</c:if>
					</c:if>
					<c:if test="${!empty requestScope.catnombre}">
				  	<c:if test="${!empty requestScope.clanombre}">
				  		<tr>
								<th class="EncabezadoColumna">&nbsp;</th>
								<th class="EncabezadoColumna">Nombre</th>
								<th class="EncabezadoColumna">Orden</th>
							</tr>
							<c:forEach begin="0" items="${requestScope.ttipo}" var="fila" varStatus="st">
								<tr>
									<c:set var="guiab"><c:out value="${fila[0]}"/></c:set>
									<td class='Fila<c:out value="${st.count%2}"/>'>
										<a href='javaScript:editar("<c:out value="${guiab}"/>");' title="Editar Registro"><img border='0' src='../etc/img/<c:if test="${requestScope.guia==guiab}">X</c:if>editar.png' width='15' height='15'></a>
										<a href='javaScript:eliminar("<c:out value="${guiab}"/>");' title="Eliminar Registro"><img border='0' src='../etc/img/eliminar.png' width='15' height='15'></a>
									</td>
									<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila[1]}"/></td>
									<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila[6]}"/></td>
								</tr>
							</c:forEach>
						</c:if>
					</c:if>
					<c:if test="${empty requestScope.catnombre}">
				  	<c:if test="${empty requestScope.clanombre}">
				  		<tr>
								<th class="EncabezadoColumna">&nbsp;</th>
								<th class="EncabezadoColumna">Nombre</th>
								<th class="EncabezadoColumna">Categoria</th>
								<th class="EncabezadoColumna">Clase</th>
								<th class="EncabezadoColumna">Orden</th>
							</tr>
							<c:forEach begin="0" items="${requestScope.ttipo}" var="fila" varStatus="st">
								<tr>
									<c:set var="guiab"><c:out value="${fila[0]}"/></c:set>
									<td class='Fila<c:out value="${st.count%2}"/>' width="40px">
										<a href='javaScript:editar("<c:out value="${guiab}"/>");' title="Editar Registro"><img border='0' src='../etc/img/<c:if test="${requestScope.guia==guiab}">X</c:if>editar.png' width='15' height='15'></a>
										<a href='javaScript:eliminar("<c:out value="${guiab}"/>");' title="Eliminar Registro"><img border='0' src='../etc/img/eliminar.png' width='15' height='15'></a>
									</td>
									<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila[1]}"/></td>
									<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila[2]}"/></td>
									<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila[3]}"/></td>
									<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${fila[6]}"/></td>
								</tr>
							</c:forEach>
						</c:if>
					</c:if>
				</c:if>
		  </table>
 	  </form>
  </font>