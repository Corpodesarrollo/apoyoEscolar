<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>

function borrar_combo(combo){
	for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
	combo.options[0] = new Option("--Seleccione uno--","-99");
}


<c:choose>
    <c:when test="${1==1}">
     borrar_combo(parent.document.frmFiltro.filAsignatura);
		  	<c:forEach begin="0" items="${requestScope.listaAsignaturas}" var="linea" varStatus="st">
					  parent.document.frmFiltro.filAsignatura.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${linea.nombre}"/>','<c:out value="${linea.codigo}"/>');
			  </c:forEach>
		  </c:when>
</c:choose>
	
	
</script>