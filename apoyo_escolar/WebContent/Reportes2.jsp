<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<script>
		var espera=0;
		function cancelarlistado(){
			location.href='<c:url value="/bienvenida.jsp"/>';
		}
		function validarChecks(forma){
			validarSeleccion(forma.sedjorcodjor,"- Jornada (debe seleccionar por lo menos uno)")
		}
		function hacerValidaciones_listado(forma){
			validarSeleccion(forma.chk,"- debe seleccionar por lo menos un reporte a eliminar")
		}
		function borrarlistado(){
			if(validarForma(document.listado)){
				if(confirm("Confirma la eliminación de los reportes indicados ?")){
					document.listado.accion.value='1';
					document.listado.action='<c:url value="/private/"/>';
					document.listado.tipo.value='html';
					document.listado.target='';
					document.listado.submit();
				}
			}else
				document.listado.accion.value='0';
		}
		function llenarlistado(){
				if(confirm("Confirma la eliminación de todos los reportes ?")){
					if(document.listado.chk.length){
						for(var i=0;i<document.listado.chk.length;i++){
							document.listado.chk[i].checked=true;
						}
					}else{
						document.listado.chk.checked=true;
					}
					document.listado.accion.value='1';
					document.listado.action='<c:url value="/private/"/>';
					document.listado.tipo.value='html';
					document.listado.target='';
					document.listado.submit();
				}
		}
		function descargarlistado(){
			<c:forEach begin="0" items="${requestScope.lista}" var="fila" varStatus="st">
				document.listado.accion.value=0; document.listado.target='_f<c:out value="${st.count}"/>'; r_<c:out value="${st.count}"/>();
			</c:forEach>
		}
		<c:forEach begin="0" items="${requestScope.lista}" var="fila" varStatus="st">function r_<c:out value="${st.count}"/>(){document.listado.dir.value='<c:out value="${fila[1]}"/>';document.listado.tipo.value='zip';document.listado.action='<c:url value="/${fila[1]}"/>';document.listado.submit();}</c:forEach>
	</script>
<form onSubmit="return validarForma(listado)" name='listado' method='post' target='_blank'><input type='hidden' name='aut' value='1'><input type='hidden' name='dir'><input type='hidden' name='tipo'><input type='hidden' name='accion' value='0'><br>
	<table width='95%' border='1' align='center' cellpadding="1" cellspacing="0" bordercolor="silver">
	<caption>-- Lista de libros 2005 --</caption>
	<c:if test="${empty requestScope.lista}"><tr><th class="EncabezadoColumna"> No hay reportes solicitados</th></tr></c:if>
	<c:if test="${!empty requestScope.lista}">
		<tr><th class="EncabezadoColumna">REPORTE</th></tr>
		<c:forEach begin="0" items="${requestScope.lista}" var="fila"  varStatus="st"><tr>
		<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[0]}"/></td>
		<td class='Fila<c:out value="${st.count%2}"/>'><a href='javaScript:document.listado.accion.value=0;r_<c:out value="${st.count}"/>()'>Descargar</a></td>
		</tr></c:forEach>
			<tr><td colspan=2><span class="style2">Nota:<br>Esta lista de reportes indica los reportes de Libro de notas que fueron generados bajo esta sesión para el año 2005.<br> Algunos de ellos pueden arrojar error al tratar de ser descargados, esto significa que no existen.</span></th></tr>
		</table>
		<br>
	</c:if>
	</table>
<script>
</script></span><%@include file="mensaje.jsp"%>

