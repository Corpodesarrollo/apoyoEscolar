<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
var items=new Array();
var i=0;
<c:forEach begin="0" items="${sessionScope.fichaVO}" var="fila">
items[i++]=<c:out value="${fila.fichaId}"/>;
</c:forEach>

function showTab(item){
	if(items.length){
		for(var i=0;i<items.length;i++){
			if(items[i]==item){
				if(document.getElementById('td_'+item+'_1')){ 
					document.getElementById('td_'+item+'_1').style.display=''; 
					document.getElementById('td_'+item+'_0').style.display='none';
				}
			}else{
				if(document.getElementById('td_'+items[i]+'_1')){ 
					document.getElementById('td_'+items[i]+'_1').style.display='none'; 
					document.getElementById('td_'+items[i]+'_0').style.display='';
				}
			}
			}
		}
	}

</script>
<table><tr><c:forEach begin="0" items="${sessionScope.fichaVO}" var="fila"><td id='td_<c:out value="${fila.fichaId}"/>_0' style="display:none"><a href="javaScript:lanzar(<c:out value="${fila.fichaId}"/>)"><img src='<c:url value="/${fila.fichaUrl}"/>' alt='<c:out value="${fila.fichaNombre}"/>'  height="26" width="84" border="0"></a></td><td id='td_<c:out value="${fila.fichaId}"/>_1' style="display:none"><img src='<c:url value="/${fila.fichaUrl2}"/>' alt='<c:out value="${fila.fichaNombre}"/>'  height="26" width="84" border="0"></td></c:forEach></tr></table>
