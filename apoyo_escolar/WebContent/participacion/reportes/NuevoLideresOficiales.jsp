<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="lideresVO" class="participacion.lideres.vo.LideresVO" scope="session"/>
<jsp:useBean id="filtroItemsVO" class="participacion.reportes.vo.FiltroItemsVO" scope="session"/>
<jsp:useBean id="paramsVO" class="participacion.reportes.vo.ParamsVO" scope="page"/>
<jsp:useBean id="paramsVO2" class="participacion.common.vo.ParamParticipacion" scope="page"/>
<html>
<head>
<style type="text/css">@import url('<c:url value="/etc/css/calendar-win2k-1.css"/>');</style>
<script type="text/javascript" src='<c:url value="/etc/js/calendar.js"/>'></script>
<script type="text/javascript" src='<c:url value="/etc/js/lang/calendar-es.js"/>'></script>
<script type="text/javascript" src='<c:url value="/etc/js/calendar-setup.js"/>'></script>
<script languaje="javaScript">
  var extensiones = new Array();
  extensiones[0]=".zip";
  extensiones[1]=".doc";
  extensiones[2]=".xls";
  extensiones[3]=".pdf";
  extensiones[4]=".ppt";
   
	var nav4=window.Event ? true : false;

	function acepteNumeros(eve){
		var key=nav4?eve.which :eve.keyCode;
		return(key<=13 || (key>=48 && key<=57));
	}
		
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("-Seleccione una-","-99");
	}
		
	

	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr><td width="100%" valign='top'>
			<div style="width:100%;height:1220px;overflow:auto;vertical-align:top;background-color:#ffffff;">
				<c:import url="/participacion/reportes/FiltroItemsCaracterizacion.jsp"/>
			</div>
		</td></tr>
		<tr style="display:none"><td><iframe name="frameAjax" id="frameAjax"></iframe></td></tr>
	</table>

	 <form method="post" name="frmAjax" action='<c:url value="/participacion/lideres/Ajax.do"/>'><INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_LIDERES}"/>'><input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_AJAX}"/>'><input type="hidden" name="ajax"><input type="hidden" name="ajax"><input type="hidden" name="ajax"></form>
	<!--<form method="post" name="frmNuevo" action='<c:url value="/participacion/lideres/SaveLideres.jsp"/>'>
		 <input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_LIDERES}"/>'>
		 <input type="hidden" name="cmd" value=''>
		  <input type="hidden" name="lidTieneLocalidad" value='<c:out value="${sessionScope.lideresVO.lidTieneLocalidad}"/>'>
		 <input type="hidden" name="lidTieneColegio" value='<c:out value="${sessionScope.lideresVO.lidTieneColegio}"/>'>
		 <input type="hidden" name="formaEstado" value='<c:out value="${sessionScope.lideresVO.formaEstado}"/>'>
		 <input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		 <input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		 <input type="hidden" name="CANCELAR" value='<c:out value="${paramsVO.CMD_CANCELAR}"/>'>
		 <input type="hidden" name="AJAX_INSTANCIA" value='<c:out value="${paramsVO.CMD_AJAX_INSTANCIA}"/>'>
		 <input type="hidden" name="AJAX_RANGO" value='<c:out value="${paramsVO.CMD_AJAX_RANGO}"/>'>
		 <input type="hidden" name="AJAX_COLEGIO" value='<c:out value="${paramsVO.CMD_AJAX_COLEGIO}"/>'>
		 <input type="hidden" name="NIVEL_DISTRITAL" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_DISTRITAL}"/>'>
		 <input type="hidden" name="NIVEL_CENTRAL" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_CENTRAL}"/>'>
		 <input type="hidden" name="NIVEL_LOCAL" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_LOCAL}"/>'>
		 <input type="hidden" name="NIVEL_COLEGIO" value='<c:out value="${paramsVO2.NIVEL_INSTANCIA_COLEGIO}"/>'>
		 <input type="hidden" name="lidNivel" value='<c:out value="${sessionScope.filtroItemsVO.filNivel}"/>'>
		 <input type="hidden" name="lidInstancia" value='<c:out value="${sessionScope.filtroItemsVO.filInstancia}"/>'>
	 	
	</form>	-->
</body>
</html>