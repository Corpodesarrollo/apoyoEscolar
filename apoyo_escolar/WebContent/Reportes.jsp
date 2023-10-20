<%@page import="java.lang.reflect.Array"%>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<%@include file="parametros.jsp"%>
<jsp:useBean id="reporteVO" class="siges.util.beans.ReporteVO" scope="session"/>
	<script>
		var espera=0;
		var estados=new Array();
		var i=0;
			estados[i++]='En cola';
			estados[i++]='Generando';
			estados[i++]='Listo';
			estados[i++]='No generado';
	
	function guardar(forma){
		if (forma.repTipo.selectedIndex==0){
			alert("-Seleccione un tipo de Reporte")
		}else{
			forma.target='';
			forma.action='<c:url value="/ReportesGuardar.jsp"/>';
			forma.submit();
		}
	}
			  
 	function cancelarlistado(){
 			document.listado.repTipo.selectedIndex=0;
			//location.href='<c:url value="/bienvenida.jsp"/>';
	}
	
	function hacerValidaciones_listado(forma){
			validarSeleccion(forma.chk,"- debe seleccionar por lo menos un reporte a eliminar")
	}
		
	function borrarlistado(){
		if(validarForma(document.listado)){
			if(confirm("Confirma la eliminación de los reportes indicados ?")){
				document.listado.accion.value='1';
				document.listado.action='<c:url value="/Reportes.do"/>';
				document.listado.tipo.value='html';
				document.listado.target='';
				document.listado.submit();
			}
		}else
			document.listado.accion.value='0';
	}
<c:forEach begin="0" items="${requestScope.lista}" var="fila" varStatus="st">
	function r_<c:out value="${st.count}"/>(){
		document.listado.dir.value='<c:out value="${fila[1]}"/>';
		document.listado.tipo.value='<c:out value="${fila[2]}"/>';
		var urlPath = "<c:url value="/${fila[1]}"/>";
		if(urlPath.indexOf("http") > -1){
			urlPath = '<c:out value="${fila[1]}"/>';
		}
        document.listado.action= urlPath;
		document.listado.submit();
	}
</c:forEach>

<c:forEach begin="0" items="${requestScope.lista}" var="fila" varStatus="st">
	function r_<c:out value="${st.count}"/>_2(){
		document.listado.dir.value='<c:out value="${fila[1]}"/>';
		document.listado.tipo.value='<c:out value="${fila[2]}"/>';
		var urlPath = "<c:url value="/${fila[1]}"/>";
		if(urlPath.indexOf("http") > -1){
			urlPath = '<c:out value="${fila[1]}"/>';
		}
		var blade="http://bladenodo4.redp.edu.co:7779";
         document.listado.action= blade + urlPath;

        //document.listado.action= urlPath;
		document.listado.submit();
	}
</c:forEach>
	
	<c:forEach begin="0" items="${requestScope.lista}" var="fila" varStatus="st">function msg_<c:out value="${st.count}"/>(){alert("<c:out value="${fila[6]}"/>");}</c:forEach>
	function posicion_(a,b){
		alert(a+" de "+b);
	}
	
	// LIBRO DE NOTAS
	<c:forEach begin="0" items="${requestScope.lista}" var="fila" varStatus="st">function message1_<c:out value="${st.count}"/>(a,b){alert("<c:out value="${fila[8]}"/>"+" de "+b);}</c:forEach>
	<c:forEach begin="0" items="${requestScope.lista}" var="fila" varStatus="st">function message11_<c:out value="${st.count}"/>(a,b){alert("<c:out value="${fila[7]}"/>"+" de "+b);}</c:forEach>
	// LOGRO X GRUPO
	<c:forEach begin="0" items="${requestScope.lista}" var="fila" varStatus="st">function message2_<c:out value="${st.count}"/>(a,b){alert("<c:out value="${fila[9]}"/>"+" de "+b);}</c:forEach>
	<c:forEach begin="0" items="${requestScope.lista}" var="fila" varStatus="st">function message21_<c:out value="${st.count}"/>(a,b){alert("<c:out value="${fila[7]}"/>"+" de "+b);}</c:forEach>
	// ASIGNATURA X GRUPO
	<c:forEach begin="0" items="${requestScope.lista}" var="fila" varStatus="st">function message3_<c:out value="${st.count}"/>(a,b){alert("<c:out value="${fila[10]}"/>"+" de "+b);}</c:forEach>
	<c:forEach begin="0" items="${requestScope.lista}" var="fila" varStatus="st">function message31_<c:out value="${st.count}"/>(a,b){alert("<c:out value="${fila[7]}"/>"+" de "+b);}</c:forEach>
	// CERTIFICADOS
	<c:forEach begin="0" items="${requestScope.lista}" var="fila" varStatus="st">function message4_<c:out value="${st.count}"/>(a,b){alert("<c:out value="${fila[11]}"/>"+" de "+b);}</c:forEach>
	<c:forEach begin="0" items="${requestScope.lista}" var="fila" varStatus="st">function message41_<c:out value="${st.count}"/>(a,b){alert("<c:out value="${fila[7]}"/>"+" de "+b);}</c:forEach>
	// CARNES
	<c:forEach begin="0" items="${requestScope.lista}" var="fila" varStatus="st">function message5_<c:out value="${st.count}"/>(a,b){alert("<c:out value="${fila[12]}"/>"+" de "+b);}</c:forEach>
	<c:forEach begin="0" items="${requestScope.lista}" var="fila" varStatus="st">function message51_<c:out value="${st.count}"/>(a,b){alert("<c:out value="${fila[7]}"/>"+" de "+b);}</c:forEach>
</script>

<form onSubmit="return validarForma(listado)" name='listado' method='post' target='_blank'>
	<input type='hidden' name='aut' value='1'>
	<input type='hidden' name='dir'>
	<input type='hidden' name='tipo'>
	<input type='hidden'	name='accion' value='0'>
	<input type="hidden" name="repFecha" maxlength='10' value='<c:out value="${hoy}"/>'>

	<table width='95%' border='1' align='center' cellpadding="0" cellspacing="0">
	<caption>Filtro de búsqueda</caption>
	<tr><td>
		<table width='100%' border='0' align='center' cellpadding="0" cellspacing="0">
		<TR>
			<TD colspan='4'><input class="boton" name="cmd1" type="button" value="Buscar" onClick="guardar(document.listado)">
				<span class="style2" >SELECCIONE EL TIPO DE REPORTE A MOSTRAR</span>
			</TD>
		</tr>
		<TR>
			<TD>Tipo de reporte:</TD>
			<TD>
				 
				 <select name="repTipo" style='width:300px;'>
				 <option value="-1" />--Seleccione uno--</option>
				   	   <c:forEach begin="0"  items="${sessionScope.listaRep}" var="item" >
				     <option value='<c:out value="${item.codigo}"/>'  <c:if test="${sessionScope.reporteVO.repTipo == item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
				   </c:forEach>
				 </select>
			</TD>
			<TD>Orden:</TD>
			<TD>
				<select name="repOrden" style='width:150px;'>
					<option value='2' <c:if test="${sessionScope.reporteVO.repOrden==2}">SELECTED</c:if>>Fecha descendente</option>
					<option value='1' <c:if test="${sessionScope.reporteVO.repOrden==1}">SELECTED</c:if>>Fecha ascendente</option>
				</select>
			</TD>
		</TR>
		</TABLE>
	</td></tr>
	</table>
	<hr>
	<table width='95%' border='1' align='center' cellpadding="1" cellspacing="0" bordercolor="silver">
	<caption>Lista de reportes generados</caption>
	<c:if test="${empty requestScope.lista}"><tr><th class="EncabezadoColumna"> .No hay reportes solicitados</th></tr></c:if>
	
	<c:if test="${!empty requestScope.lista}">
		<tr>
			<th class="EncabezadoColumna">&nbsp;</th>
			<th class="EncabezadoColumna">REPORTE <span class="style2"><sub>1</sub></span> </th>
			<th class="EncabezadoColumna">ESTADO</th>
			<th width='10%' class="EncabezadoColumna">FECHA</th>
			<th class="EncabezadoColumna" width='10%'>&nbsp;</th>
		</tr>
		<c:forEach begin="0" items="${requestScope.lista}" var="fila"  varStatus="st"><tr>
		<c:if test="${fila[4]<=0}"><script>espera=1;</script>
			<c:if test="${requestScope.total_boletines==null}">
				<%request.setAttribute("mensaje","VERIFIQUE LA SIGUIENTE INFORMACIÓN:\n\nHay reportes que se están generando en este momento.\nSi está esperando la generación de alguno de estos reportes\nhaga click en el vínculo 'ACTUALIZAR ESTADO DE LOS REPORTES'\nal final de esta página");%>
			</c:if>
			<c:if test="${requestScope.total_boletines!=null}">
				<c:set var="mensaje" value="VERIFIQUE LA SIGUIENTE INFORMACIÓN: En este momento hay ${requestScope.total_boletines} boletines en cola" scope="request"/>
			</c:if>
		</c:if>
		<td class='Fila<c:out value="${st.count%2}"/>'><input class='Fila<c:out value="${st.count%2}"/>' type='checkbox' name='chk' value='<c:out value="${fila[1]}"/>'></td>
		<td class='Fila<c:out value="${st.count%2}"/>'><img border="0" src="<c:url value="/etc/img/${fila[2]}"/>.gif" alt="<c:out value="${fila[0]}"/>" align="middle" width="20" height="18"/><script>document.write("<c:out value="${fila[0]}"/>".substring(0,50)+"...");</script></td>
		<td class='Fila<c:out value="${st.count%2}"/>'><script>document.write(estados[<c:out value="${fila[4]}"/>+1]);</script></td>
		<td class='Fila<c:out value="${st.count%2}"/>'><c:out value="${fila[3]}"/></td>
		<td class='Fila<c:out value="${st.count%2}"/>'>
		<c:if test="${fila[4]==1}"><a href='javaScript:document.listado.accion.value=0;r_<c:out value="${st.count}"/>()'>Descargar</a></c:if>
		<c:if test="${fila[4]==1}"><a href='javaScript:document.listado.accion.value=0;r_<c:out value="${st.count}"/>_2()'>Descargar 2</a></c:if>

		<c:if test="${fila[4]==2}"><a href='javaScript:msg_<c:out value="${st.count}"/>()'>Ver motivo</a></c:if>
	
		<c:if test="${fila[4]<0}">
			<c:choose>
				<c:when test="${ fila[7]!=null && fila[7]!=-999}">
					<a href="javaScript:posicion_('<c:out value="${fila[7]}"/>','<c:out value="${requestScope.total_reporte}"/>');">Ver posición</a>
				</c:when>
         </c:choose>
		</c:if>
		</td></tr></c:forEach>
		</table><br>
		<table width='80%' border='0' align='center'cellpadding="0" cellspacing="0" bordercolor="#000000">
		<tr>
			<th colspan='4'>
				<input type='button' name='cmda' value='Borrar Seleccionado(s)' onClick='borrarlistado()'> 
				<input type='button' name='cmdb' value='Cancelar' onClick='cancelarlistado()'>
			</th>	
		</tr>
	</c:if>
	</table><br>
	<span class="style2">1: </span> Para ver el nombre completo del reporte ponga el puntero del mouse sobre la imagen del reporte
</form><span class="style2">
<script>
<c:if test="${!empty requestScope.mensaje2}">
alert('<c:out value="${requestScope.mensaje2}"/>');
</c:if>
if(espera==1){
document.write("<font size='2'><CENTER>Hay reportes que se están generando en este momento,<br> si esta esperando la generación de alguno de estos reportes haga click aqui<p>");
document.write("<a href='<c:url value="/Reportes.do"/>'> >> ACTUALIZAR ESTADO DE LOS REPORTES << </a></CENTER></font>");
}
</script></span><%@include file="mensaje.jsp"%>