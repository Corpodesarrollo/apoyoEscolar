<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${requestScope.mensaje!= null}"><input type="hidden" id='msg' name="msg" value='<c:out value="${requestScope.mensaje}"/>'></c:if>