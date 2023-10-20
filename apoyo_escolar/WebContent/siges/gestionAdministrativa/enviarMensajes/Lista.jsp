<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="filtroMensajeVO" class="siges.gestionAdministrativa.enviarMensajes.vo.FiltroEnviarVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.gestionAdministrativa.enviarMensajes.vo.ParamsVO" scope="page"/>
<html>
<c:import url="/parametros.jsp"/>
<head>
<style type="text/css">@import url('<c:url value="/etc/css/calendar-win2k-1.css"/>');</style>
<script type="text/javascript" src="<c:url value="/etc/js/calendar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/lang/calendar-es.js"/>"></script>
<script type="text/javascript" src="<c:url value="/etc/js/calendar-setup.js"/>"></script>
<script languaje="javaScript">
<%
int lindx;
if (request.getHeader("Referer").lastIndexOf('?')==-1)
	lindx=request.getHeader("Referer").length();
else
	lindx=request.getHeader("Referer").lastIndexOf('?');%>
 function borrar_combo2(combo){
		for(var i = combo.length - 1; i >= 0; i--) { if(navigator.appName == "Netscape") combo.options[i] = null; else combo.remove(i); }
		combo.options[0] = new Option("--Todos","-99");
 }
 
function hacerValidaciones_frmFiltro(forma){
	validarFechaUnCampo(forma.filFechaDesde, "- Fecha Desde")
	validarFechaUnCampo(forma.filFechaHasta, "- Fecha Hasta")
  
  
}
	function Buscar(){				
		if(validarForma(document.frmFiltro)){			
			document.frmFiltro.cmd.value=document.frmFiltro.BUSCAR.value;	
			document.frmFiltro.fillocal.disabled = false;
			document.frmFiltro.filinst.disabled = false;
			document.frmFiltro.submit();
		}	
	}
		
	function Nuevo(){
		document.frmFiltro.cmd.value=document.frmFiltro.NUEVO_MSJ.value;
		document.frmFiltro.tipo.value=document.frmFiltro.MENSAJE.value;				
		document.frmFiltro.submit();
	}

	function editar(cod){
		if(document.frmFiltro.id){
				document.frmFiltro.id.value = cod;
				document.frmFiltro.cmd.value=document.frmFiltro.EDITAR.value;
				document.frmFiltro.submit();
		}
	}

	function eliminar(cod){
		if(confirm('¿Desea eliminar el mensaje?')){
			if(document.frmFiltro.id){
				document.frmFiltro.id.value=cod;				
				document.frmFiltro.cmd.value=document.frmFiltro.ELIMINAR.value;
				document.frmFiltro.submit();
			}
		}
	}
	
	function ajaxColegios(){ 
		  borrar_combo2(document.frmFiltro.filinst); 
		  document.frmAjaxFiltro.target = 'frameAjaxFiltro';
		  document.frmAjaxFiltro.ajax[0].value = document.frmFiltro.fillocal.value;
		  document.frmAjaxFiltro.ajax[1].value = -99;
		  document.frmAjaxFiltro.cmd.value = document.frmFiltro.CMD_AJAX_INST.value;
		  document.frmAjaxFiltro.submit();
		 
	}
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
 	   	<form method="post" name="frmAjaxFiltro" action='<c:url value="/siges/gestionAdministrativa/enviarMensajesAjax.do"/>'>
			<input type="hidden" name="tipo" value='<c:out value="${ paramsVO.FICHA_MSJ}"/>'>
			<input type="hidden" name="cmd"  value='-1'>
			<input type="hidden" name="tipoAdd"  value='0'>
			<input type="hidden" name="CMD_AJAX_INST"  id="CMD_AJAX_INST"  value='<c:out value="${paramsVO.CMD_AJAX_INST}"/>'>
			<input type="hidden" name="CMD_AJAX_SEDE"  id="CMD_AJAX_SEDE"  value='<c:out value="${paramsVO.CMD_AJAX_SED}"/>'>
			<input type="hidden" name="CMD_AJAX_JORD"  id="CMD_AJAX_JORD"  value='<c:out value="${paramsVO.CMD_AJAX_JORD}"/>'>
			<input type="hidden" name="CMD_AJAX_METD"  id="CMD_AJAX_METD"  value='<c:out value="${paramsVO.CMD_AJAX_METD}"/>'>
			<input type="hidden" name="CMD_AJAX_GRUP"   id="CMD_AJAX_GRUP"   value='<c:out value="${paramsVO.CMD_AJAX_GRUP}"/>'>
			<input type="hidden" name="CMD_AJAX_GRAD"  id="CMD_AJAX_GRAD"  value='<c:out value="${paramsVO.CMD_AJAX_GRAD}"/>'>
			<input type="hidden" name="ADD_PER"  id="ADD_PER"  value='<c:out value="${paramsVO.ADD_PER}"/>'>			
			<input type="hidden" name="nom"  id="nom" >			
			<input type="hidden" name="cod"  id="cod" >			
			 <c:forEach begin="0" end="5" var="i"><input type="hidden" name="ajax"></c:forEach>
		</form>
		
		
		
<form method="post" name="frmFiltro"  action='<c:url value="/siges/gestionAdministrativa/enviarMensajes/Save.jsp"/>'>
		<input type="hidden" name="tipo" value='<c:out value="${paramsVO.FICHA_LISTA}"/>'>
		<input type="hidden" name="MENSAJE" value='<c:out value="${paramsVO.FICHA_MSJ}"/>'>
        <input type="hidden" name="cmd" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="GUARDAR" value='<c:out value="${paramsVO.CMD_GUARDAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="NUEVO_MSJ" value='<c:out value="${paramsVO.CMD_NUEVO_MSJ}"/>'>
		<input type="hidden" name="CMD_AJAX_INST"  id="CMD_AJAX_INST"  value='<c:out value="${paramsVO.CMD_AJAX_INST}"/>'>
		<input type="hidden" name="id" value=''>
		
		<!--  <div style="width:100%;height:350px;overflow:auto;vertical-align:top;background-color:#ffffff;">-->
		<table cellpadding="0" cellspacing="1" border="0" ALIGN="CENTER" width="100%" >
		  <caption>Gestión Administrativa >> Envíar Mensajes</caption>	         
		    <tr>						
			 <td rowspan="1" bgcolor="#FFFFFF">			      
				   <img src='<c:url value="/etc/img/tabs/enviarMensajes_1.gif"/>' border="0"  height="26" alt='Envíar Mensajes'>
		        </td>
            </tr>
          </table>
		
		<table >
			<tr>
				<td colspan="1">
					<input name="cmd1" type="button" value="Buscar" onClick="Buscar()" class="boton">
					<input name="cmd2" type="button" value="Nuevo" onClick="Nuevo()" class="boton">
				</td>
			</tr>
		</table>
		
		<table>
			<tr>
			  <td>Perfil</td>
			  <td><select  name="filperfil" id="filperfil" style="width: 190px;"   <c:if test="${sessionScope.filtroRepCarnesVO.hab_loc ==0}">DISABLED</c:if>>
		 	        <option value="-99">-- Todos --</option>
		 	         <c:forEach begin="0" items="${requestScope.filListaPerfil}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${ filtroMensajeVO.filperfil ==item.codigo}"><% if (request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("enviarMensajes.do") || request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("Ajax.do") || request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("Save.jsp")){ %>' SELECTED '<% } %></c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	      </select>
			  </td>
			  <td>Localidad</td>
			  <td><select  name="fillocal" id="fillocal" style="width: 190px;" onchange="JavaScript:ajaxColegios();" <c:if test="${!empty sessionScope.login.munId and    sessionScope.login.munId  > 0 }"> disabled="disabled" </c:if>  >
		 	        <option value="-99">-- Todos --</option>
		 	         <c:forEach begin="0" items="${requestScope.filLlistaLocalidad}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${filtroMensajeVO.fillocal ==item.codigo or (!empty sessionScope.login.munId and    sessionScope.login.munId  == item.codigo )}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	      </select>
			  </td>
			</tr>		
			<tr>
			  <td>Colegio</td>
			  <td colspan="3"><select  name="filinst" id="filinst" style="width: 290px;"    <c:if test="${!empty sessionScope.login.instId and    sessionScope.login.instId  > 0 }"> disabled="disabled" </c:if> >
		 	        <option value="-9">-- Todos --</option>
		 	         <c:forEach begin="0" items="${requestScope.fiListaColegio}" var="item">
				     <option value="<c:out value="${item.codigo}"/>" <c:if test="${filtroMensajeVO.filinst ==item.codigo}">SELECTED</c:if>><c:out value="${item.nombre}"/></option>
                     </c:forEach>
		 	      </select>
			  </td>
			</tr>				
			<tr>
				<td width="15%"><span class="style2" >*</span>Fecha Desde</td>
				<td  width="45%" align="left">
				<input type="text" id="filFechaDesde" name="filFechaDesde" maxlength="10" size="10" value=<% if (request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("enviarMensajes.do") || request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("Ajax.do") || request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("Save.jsp")){ %>'<c:out value="${filtroMensajeVO.filFechaDesde}"/>'<% } %>></input>
				<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="imgfecha"  title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
				</td>
				
				<td  width="15%"><span class="style2" >*</span>Fecha Hasta</td>
				<td  width="35%" align="left">
				<input type="text" id="filFechaHasta" name="filFechaHasta" maxlength="10" size="10" value=<% if (request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("enviarMensajes.do") || request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("Ajax.do") || request.getHeader("Referer").substring(request.getHeader("Referer").lastIndexOf('/')+1,lindx).equals("Save.jsp")){ %>'<c:out value="${filtroMensajeVO.filFechaHasta}"/>'<% } %>></input>
				<img src='<c:url value="/etc/img/calendario.gif"/>' alt="Seleccione una fecha" id="imgfecha2"  title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
				</td>
			</tr>
			<tr style="display:none"><td><iframe name="frameAjaxFiltro" id="frameAjaxFiltro"></iframe></td></tr>
		</table>
		<table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
		 	<caption>Listado Mensajes </caption>
		 	<c:if test="${empty requestScope.listaMensajes}">
		 		<tr><th class="Fila1" colspan='6'>NO EXISTEN REGISTROS DE MENSAJES</th></tr>
		 	</c:if>			
			<c:if test="${!empty requestScope.listaMensajes}">
				<tr>
					<th width='30' class="EncabezadoColumna">&nbsp;</th>
					<th class="EncabezadoColumna">De</td>
					<th class="EncabezadoColumna">Asunto</td>
					<th class="EncabezadoColumna">Fecha Inicio</td>
					<th class="EncabezadoColumna">Fecha Fin</td>
				</tr>
				<c:forEach begin="0" items="${requestScope.listaMensajes}" var="lista" varStatus="st">
					<tr>
						<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${lista.msjcodigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
							<c:if test="${sessionScope.NivelPermiso==2}"><a href='javaScript:eliminar(<c:out value="${lista.msjcodigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						</th>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:if test="${lista.msjenviadopor == 1}">SISTEMA</c:if><c:if test="${lista.msjenviadopor == 2 }">SED</c:if><c:if test="${lista.msjenviadopor == 3 }">LOCALIDAD</c:if><c:if test="${lista.msjenviadopor == 4 }">COLEGIO</c:if></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="left" valign="top" ><c:out value="${lista.msjasunto}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.msjfechaini}"/></td>
						<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${lista.msjfechafin}"/></td>
					</tr>
				</c:forEach>				
			</c:if>
		</table>
		
		<!--  </div>-->
			</form>
</body>
<script type="text/javascript">
    Calendar.setup({
        inputField     :    "filFechaDesde",
        ifFormat       :    "%d/%m/%Y",
        button         :    "imgfecha",
        align          :    "Br"
    });

    Calendar.setup({
        inputField     :    "filFechaHasta",
        ifFormat       :    "%d/%m/%Y",
        button         :    "imgfecha2",
        align          :    "Br"
    });
    
    <c:if test="${!empty sessionScope.login.munId and    sessionScope.login.munId  > 0 }">
         document.frmFiltro.fillocal.disabled = true;
	
      ajaxColegios();
    </c:if>
    
    <c:if test="${!empty sessionScope.login.instId and    sessionScope.login.instId  > 0 }"> 
    		document.frmFiltro.filinst.disabled = true;
    </c:if>
    		
</script>
</html>