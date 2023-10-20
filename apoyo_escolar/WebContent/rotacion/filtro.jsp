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
	  <c:when test="${requestScope.var==1}">
	  	borrar_combo(parent.document.frm.docente);
			<c:forEach begin="0" items="${requestScope.col1}" var="fila" varStatus="st">parent.document.frm.docente.options[<c:out value="${st.count}"/>] = new Option("<c:out value="${fila[1]}"/>","<c:out value="${fila[0]}"/>");</c:forEach>
		</c:when>
	  <c:when test="${requestScope.var==2}">
	  	borrar_combo(parent.document.frm.asignatura);
			<c:forEach begin="0" items="${requestScope.col1}" var="fila" varStatus="st">parent.document.frm.asignatura.options[<c:out value="${st.count}"/>] = new Option("<c:out value="${fila[1]}"/>","<c:out value="${fila[0]}"/>");</c:forEach>
		</c:when>
	  <c:when test="${requestScope.var==3}">
	  	borrar_combo(parent.document.frm.asignatura);
			<c:forEach begin="0" items="${requestScope.col1}" var="fila" varStatus="st">parent.document.frm.asignatura.options[<c:out value="${st.count}"/>] = new Option("<c:out value="${fila[1]}"/>","<c:out value="${fila[0]}"/>");</c:forEach>
		</c:when>
		<c:when test="${requestScope.var==4}">
		parent.document.frm.horasdisp.value=<c:if test="${!empty requestScope.col1}"><c:out value="${requestScope.col1}"/></c:if><c:if test="${empty requestScope.col1}">No posee registros</c:if>;
		</c:when>
		<c:when test="${requestScope.var==5}">
		//alert('ES'+<c:out value="${requestScope.col1}"/>);
		
		parent.document.getElementById("horadisp2").value=<c:if test="${!empty requestScope.col1}"><c:out value="${requestScope.col1}"/></c:if><c:if test="${empty requestScope.col1}">No posee registros</c:if>;
		
		</c:when>
	</c:choose>	
		
		
//-->
</script>