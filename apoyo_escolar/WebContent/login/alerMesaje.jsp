<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean  id="paramsVO"   class="siges.login.beans.ParamsVO" scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<link href='etc/css/cssmain.css' rel='stylesheet' type='text/css'>
<title></title>
<body>
 <form name='form1'>
    <table width='420px' height='280px' border='0' cellpadding='3'  cellspacing='3'>
     <caption><b>Mensaje</b></caption>
     <tr width='420px' style='width: 400px;' >
       <td width='400px' align='center' height='10px' class='Fila1'  style='width: 400px;' ><span class='style2' style='font: bold;' > Rango de fecha&nbsp;&nbsp;[<c:out value="${sessionScope.mensajesVO.msjfechaini}"/>] - [<c:out value="${sessionScope.mensajesVO.msjfechafin}"/>]</span></td></tr>
     <tr>   <td width='400px'  height='10px' class='Fila0'  style='width: 400px;' ><b>- ENVIADO POR </b></td>
	 </tr>
	 <tr width='400px' style='width: 400px;' >
	    <td align='left'  valign='top'  width='400px' style='width: 400px;' >&nbsp;&nbsp;&nbsp;<c:if test="${paramsVO.ENV_SISTEM == sessionScope.mensajesVO.msjenviadopor}"><c:out value="${paramsVO.ENV_SISTEM_TEXT}"/></c:if><c:if test="${paramsVO.ENV_SED == sessionScope.mensajesVO.msjenviadopor}"><c:out value="${paramsVO.ENV_SED_TEXT}"/></c:if><c:if test="${paramsVO.ENV_LOC == sessionScope.mensajesVO.msjenviadopor}"><c:out value="${paramsVO.ENV_LOC_TEXT}"/></c:if><c:if test="${paramsVO.ENV_INST == sessionScope.mensajesVO.msjenviadopor}"><c:out value="${paramsVO.ENV_INST_TEXT}"/></c:if></td>
	 </tr>   
	 <tr><td width='400px'  height='10px' class='Fila0'  style='width: 400px;' ><b>- ASUNTO</b></td>
     </tr>
	 <tr width='400px' style='width: 400px;' >
	    <td align='left'  valign='top'  width='400px' style='width: 400px;' ><textarea cols='120'  style='width: 400px;height: 60' rows='3' class='Fila1' readonly='readonly'><c:out value="${sessionScope.mensajesVO.msjasunto}"/></textarea></td>
	 <tr>
	 <tr width='400px' style='width: 400px;font-weight: bold;' >
	  <td width='400px' height='10px' class='Fila0' style='width: 400px;font-weight: bold;' ><b> CONTENIDO </b></td>
	 <tr>		    		    
	 <tr width='400px' style='width: 400px;' >
	      <td align='left' valign='top' width='400px' style='width: 400px;' ><textarea cols='120'  style='width: 400px;height: 200' rows='3' class='Fila1' readonly='readonly'><c:out value="${sessionScope.mensajesVO.msjcontenido}"/></textarea></td>
	 </tr>
     <tr><td colspan='2' align='center'><input type='button' value='Cerrar' onclick='parent.close();'></td></tr>
	</table>
  </form>
 </body>
</html> 
		