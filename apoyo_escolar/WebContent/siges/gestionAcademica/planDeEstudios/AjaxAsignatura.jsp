<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="siges.gestionAcademica.planDeEstudios.vo.ParamsVO" scope="page"/>
<script>
<!--
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Seleccione uno--","-99");
	}
	function borrar_check(combo){
		for(var i = 0; i<combo.length; i++) { 
			combo[i].checked=false;
			combo[i].disabled=true;
		}
	}
	function borrar_texto(combo){
		for(var i = 0; i<combo.length; i++) { 
			combo[i].value='';
			combo[i].disabled=true;
		}
	}
	
	<c:choose>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_AREA0}">
		borrar_combo(parent.document.frmFiltro.filArea);
		<c:forEach begin="0" items="${requestScope.listaAreaBase0}" var="asig" varStatus="st">
			parent.document.frmFiltro.filArea.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${asig.nombre}"/>','<c:out value="${asig.codigo}"/>');
		</c:forEach>
	  </c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_AREA}">
		borrar_combo(parent.document.frmNuevo.asiArea);
		<c:forEach begin="0" items="${requestScope.listaAreaBase}" var="asig" varStatus="st">
			parent.document.frmNuevo.asiArea.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${asig.nombre}"/>','<c:out value="${asig.codigo}"/>');
		</c:forEach>
	  </c:when>
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_ASIGNATURA}">
		borrar_combo(parent.document.frmNuevo.asiCodigo);
		<c:forEach begin="0" items="${requestScope.listaAsignaturaBase}" var="asig" varStatus="st">
			parent.document.frmNuevo.asiCodigo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${asig.nombre}"/>','<c:out value="${asig.codigo}"/>');
		</c:forEach>
	  </c:when>
	  
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_ASIGNATURA_BASE}">
	  		parent.document.frmNuevo.asiNombre.value='<c:out value="${requestScope.listaAsignaturaBase.nombre}"/>';
	  		//parent.document.frmNuevo.asiAbreviatura.value='<c:out value="${requestScope.listaAsignaturaBase.nombre}"/>';
	  </c:when>
	  
	  <c:when test="${requestScope.ajaxParam==paramsVO.CMD_AJAX_GRADO}">
				borrar_combo(parent.document.frmNuevo.asiCodigo);
				<c:forEach begin="0" items="${requestScope.listaAsignaturaBase}" var="asig" varStatus="st">
					parent.document.frmNuevo.asiCodigo.options[<c:out value="${st.count}"/>] = new Option('<c:out value="${asig.nombre}"/>','<c:out value="${asig.codigo}"/>');
				</c:forEach>
	  		
	  		borrar_check(parent.document.frmNuevo.asiGrado_);
	  		borrar_texto(parent.document.frmNuevo.asiGrado2_);
	  		for(var i = 0; i<parent.document.frmNuevo.asiGrado.length; i++) { 
			  	<c:forEach begin="0" items="${requestScope.listaGrado}" var="asig" varStatus="st">
						if(parent.document.frmNuevo.asiGrado[i].value==<c:out value="${asig.codigo}"/>){
							parent.document.frmNuevo.asiGrado_[i].disabled=false;
							parent.document.frmNuevo.asiGrado2_[i].disabled=false;
						}
					</c:forEach>
				}	
		</c:when>
	</c:choose>
//-->
</script>