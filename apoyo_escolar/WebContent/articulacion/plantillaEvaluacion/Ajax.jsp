<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.plantillaEvaluacion.vo.ParamsVO" scope="page"/>
<script>
<!--
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--","-99");
	}
	function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno","-99");
	}
	<c:choose>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_GRUPO}">
	  		borrar_combo(parent.document.frmFiltro.grupo);
		  	<c:forEach begin="0" items="${sessionScope.ajaxGrupo}" var="grupo" varStatus="st">
			  	<c:if test="${grupo.codigo==sessionScope.FilEvaluacionPVO.grupo}">
					parent.document.frmFiltro.grupo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.consecutivo}"/>','<c:out value="${grupo.codigo}"/>','selected');
				</c:if>
				<c:if test="${grupo.codigo!=sessionScope.FilEvaluacionPVO.grupo}">
					parent.document.frmFiltro.grupo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${grupo.consecutivo}"/>','<c:out value="${grupo.codigo}"/>');
				</c:if>
			</c:forEach>
		</c:when>
		<c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_ASIGNATURA}">
            <c:forEach begin="0" items="${sessionScope.ajaxAsignatura}" var="asignatura" varStatus="st">
                  <c:if test="${asignatura.codigo==sessionScope.FilEvaluacionPVO.asignatura }">
                    parent.document.frmFiltro.asignatura.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${asignatura.nombre}"/>','<c:out value="${ asignatura.codigo}"/>','selected');
                </c:if>
                <c:if test="${asignatura.codigo!=sessionScope.FilEvaluacionPVO.asignatura}">
                    parent.document.frmFiltro.asignatura.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${asignatura.nombre}"/>','<c:out value="${asignatura.codigo}"/>');
                </c:if>
            </c:forEach>
        </c:when>
        
        <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_PRUEBA}">
	  		borrar_combo2(parent.document.frmFiltro.prueba);
		  	<c:forEach begin="0" items="${sessionScope.ajaxPrueba}" var="prueba" varStatus="st">
			  	<c:if test="${prueba.codigo==sessionScope.FilEvaluacionPVO.prueba}">
					parent.document.frmFiltro.prueba.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${prueba.nombre}"/>','<c:out value="${prueba.codigo}"/>','selected');
				</c:if>
				<c:if test="${prueba.codigo!=sessionScope.FilEvaluacionPVO.prueba}">
					parent.document.frmFiltro.prueba.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${prueba.nombre}"/>','<c:out value="${prueba.codigo}"/>');
				</c:if>
			</c:forEach>
		</c:when>
	</c:choose>
//-->
</script>