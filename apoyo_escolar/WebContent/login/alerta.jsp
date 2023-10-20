<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean  id="paramsVO"   class="siges.login.beans.ParamsVO" scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<script type="text/javascript" >
<!--
var num = 1;
function ventana(id){
 try{    
        num++;
  	    remote = window.open( '<c:url value="/bienvenida.do"/>' + "?tipos_=12&id="+id,"3","width=450,height=500,resizable=0,toolbar=no,directories=no,menubar=no,status=no,overflow:auto;vertical-align:top, replace:true;");
		remote.moveTo(250,250);
		//remote.document.body.innerHTML=".";
		//remote.document.write(_pagina);		
		remote.focus();
	}catch(error){
	 }	
}
--> 	
</script>
</head>
<body>
 <form name="frm">
     <table  border="0" width="100%"  > 
	 <c:if test= "${empty sessionScope.listaMensajeVO}">
	 <tr  style="height: 5px"><td  colspan="3" align="center" class="Fila1" ><b>No hay mensajes</b></td></tr>
	 </c:if>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
         <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
         <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	  
	   <c:forEach begin="0" items="${sessionScope.listaMensajeVO}" var="lista" varStatus="st">
	    <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	    <tr  style="height: 5px"><td  class="Fila0" align="center" style="height: 5px">N° <c:out value="${st.count}"/></td> <td class="Fila0" align="center"><font style="font: bold;">Asunto</font></td><td class="Fila0" ></td></tr>
	    <tr>
	      <td   class="Fila0" align="center" width="30%" valign="middle">
	        Enviado por:<br> <c:if test="${paramsVO.ENV_SISTEM == lista.msjenviadopor}"><c:out value="${paramsVO.ENV_SISTEM_TEXT}"/></c:if><c:if test="${paramsVO.ENV_SED == lista.msjenviadopor}"><c:out value="${paramsVO.ENV_SED_TEXT}"/></c:if><c:if test="${paramsVO.ENV_LOC == lista.msjenviadopor}"><c:out value="${paramsVO.ENV_LOC_TEXT}"/></c:if><c:if test="${paramsVO.ENV_INST == lista.msjenviadopor}"><c:out value="${paramsVO.ENV_INST_TEXT}"/></c:if>   
	         <br><br><br>[<c:out  value="${lista.msjfechaini}"/> - <c:out  value="${lista.msjfechafin}"/>]</b>
	      </td>
	      <td class="Fila1" width="30px" style="width: 50%" ><c:out  value="${lista.msjasunto}"/><b>
	      </td>
	      <td  width="30px" style="width: 25%"><a class="vinculo" href="javaScript:ventana('<c:out  value="${lista.msjcodigo}"/>')">(Click para ver mensaje)</a>
	      </td>
	     </tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	  </c:forEach>
         <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	  <c:if test= "${!empty sessionScope.listaMensajeVO}">
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
         <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	     <tr style="width: 10px;height: 10px"><td style="width: 10px;height: 10px"></td></tr>
	
	 </c:if>
	    
     </table>		
 </form>
</body>
</html>