<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@include file="../../parametros.jsp"%>
<script>
	<!--
		function borrar_combo(combo){
			for(var i = combo.length - 1; i >= 0; i--) {
				if(navigator.appName == "Netscape") combo.options[i] = null;
				else combo.remove(i);
			}
			combo.options[0] = new Option("--Seleccione uno--","-9");
		}
		function borrar_combo2(combo){
			for(var i = combo.length - 1; i >= 0; i--) {
				if(navigator.appName == "Netscape") combo.options[i] = null;
				else combo.remove(i);
			}
			combo.options[0] = new Option("--Todos--","-9");
		}
	<c:choose>
	  <c:when test="${requestScope.var==1}">
	  	borrar_combo(parent.document.<c:out value="${requestScope.forma}"/>.lidCodEstud);
		  <c:forEach begin="0" items="${requestScope.col1}" var="fila" varStatus="st">
				parent.document.<c:out value="${requestScope.forma}"/>.lidCodEstud.options[<c:out value="${st.count}"/>] = new Option("<c:out value="${fila[1]}"/>","<c:out value="${fila[0]}"/>");
			</c:forEach>
		</c:when>
	  <c:when test="${requestScope.var==2}">
	  	borrar_combo(parent.document.<c:out value="${requestScope.forma}"/>.lidCodEstud);
		  <c:forEach begin="0" items="${requestScope.col1}" var="fila" varStatus="st">
				parent.document.<c:out value="${requestScope.forma}"/>.lidCodEstud.options[<c:out value="${st.count}"/>] = new Option("<c:out value="${fila[1]}"/>","<c:out value="${fila[0]}"/>");
			</c:forEach>
		</c:when>
	</c:choose>
	parent.setEstudiante(parent.document.<c:out value="${requestScope.forma}"/>,parent.document.<c:out value="${requestScope.forma}"/>.lidCodEstud);
	//-->
</script>