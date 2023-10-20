<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<script>
<!--
		function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("--Seleccione uno--","-1");
		}
		
	<c:choose>
		<c:when test="${requestScope.var==3}">
	  	borrar_combo(parent.document.<c:out value="${requestScope.forma}"/>.colegio3);
	  	<c:forEach begin="0" items="${requestScope.col1}" var="fila" varStatus="st">
				parent.document.<c:out value="${requestScope.forma}"/>.colegio3.options[<c:out value="${st.count}"/>] = new Option("<c:out value="${fila[1]}"/>","<c:out value="${fila[0]}"/>");
			</c:forEach>
			parent.document.getElementById("txtcol").innerHTML="";
		</c:when>

		<c:when test="${requestScope.var==4}">
	  	borrar_combo(parent.document.<c:out value="${requestScope.forma}"/>.sede3);
	  	//borrar_combo(parent.document.<c:out value="${requestScope.forma}"/>.jorn3);
	  	<c:forEach begin="0" items="${requestScope.col1}" var="fila" varStatus="st">
				parent.document.<c:out value="${requestScope.forma}"/>.sede3.options[<c:out value="${st.count}"/>] = new Option("<c:out value="${fila[1]}"/>","<c:out value="${fila[0]}"/>");
			</c:forEach>
			parent.document.getElementById("txtsed").innerHTML="";
		</c:when>

		<c:when test="${requestScope.var==5}">
	  	borrar_combo(parent.document.<c:out value="${requestScope.forma}"/>.jorn3);
	  	<c:forEach begin="0" items="${requestScope.col1}" var="fila" varStatus="st">
				parent.document.<c:out value="${requestScope.forma}"/>.jorn3.options[<c:out value="${st.count}"/>] = new Option("<c:out value="${fila[1]}"/>","<c:out value="${fila[0]}"/>");
			</c:forEach>
		</c:when>

	</c:choose>
-->
</script>