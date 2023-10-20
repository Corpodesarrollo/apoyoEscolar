<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	errorPage=""%>
<jsp:useBean id="filtroEst" class="siges.estudiante.beans.FiltroBean"
	scope="session" />
<jsp:setProperty name="filtroEst" property="*" />
<jsp:useBean id="paramsVO" class="siges.common.vo.Params"
	scope="request" />

<%@include file="../parametros.jsp"%>
<script languaje='javaScript'>			var nav4=window.Event ? true : false;
			function acepteNumeros(eve){
				var key=nav4?eve.which :eve.keyCode;
				return(key<=13 || (key>=48 && key<=57));
			}
			function hacerValidaciones_frm(forma){
				forma.id.value=trim(forma.id.value);
				
				validarLista(forma.tipoDocumento,"- Tipo Documento",1);
				validarCampo(forma.id, "- Numero Documento", 1,30);
			}
			
			function cancelar(){
				parent.location.href='<c:url value="/bienvenida.jsp"/>';
			}
			function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
				this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
			}
			function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("--Todos--","-9");
			}
			
			function borrar_combo2(combo){
				combo.selectedIndex=0;
			}
			function filtroEst4(combo_hijo4){
					borrar_combo2(combo_hijo4);
			}		
			
			 
	function ajaxSede(){  
	  //borrar_combo2(document.frm.jornada);
	  borrar_combo2(document.frm.metodologia);
  	  borrar_combo2(document.frm.grado);
 	  borrar_combo2(document.frm.filgrupo);  
    if(document.frm.filinst.value > 0){
   	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value =  document.frm.filinst.value;
	  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_SED.value;
	  document.frmAjaxNuevo.submit();
	  }
	}
	
	
	 
	function ajaxJornada(){ 
	  //borrar_combo(document.frm.jornada);
	  borrar_combo2(document.frm.metodologia);
  	  borrar_combo2(document.frm.grado);
 	  borrar_combo2(document.frm.filgrupo);	 
 	  
    if(document.frm.sede.value > 0){
   	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value =  document.frm.filinst.value;
	  document.frmAjaxNuevo.ajax[1].value =  document.frm.sede.value;
	  document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_JORD.value;
	  document.frmAjaxNuevo.submit();
		}
	  }//else{
	  //alert("no entro");
	    //borrar_combo(document.frm.jornada);
	  //}
	
	
	function ajaxMetodologia(){ 
	  borrar_combo2(document.frm.metodologia);
  	  borrar_combo2(document.frm.grado);
 	  borrar_combo2(document.frm.filgrupo); 
 	  if(document.frm.jornada.value > 0  ){
	   document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	   document.frmAjaxNuevo.ajax[0].value =  document.frm.filinst.value;
	    document.frmAjaxNuevo.ajax[1].value = document.frm.sede.value;
	    document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_METD.value;
	   document.frmAjaxNuevo.submit();
	  }
	}
	 
	 
 
	 
	 
	 function ajaxGrado(){
 	  borrar_combo2(document.frm.grado);
 	  borrar_combo2(document.frm.filgrupo);
 	  if(document.frm.metodologia.value > 0){
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
	  document.frmAjaxNuevo.ajax[0].value =  document.frm.filinst.value;
	  document.frmAjaxNuevo.ajax[1].value = document.frm.sede.value;
      document.frmAjaxNuevo.ajax[2].value = document.frm.jornada.value;
      document.frmAjaxNuevo.ajax[3].value = document.frm.metodologia.value;
      document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_GRAD.value;
      document.frmAjaxNuevo.submit();
     }
	}
	
	
   function ajaxGrupo(){
	  borrar_combo2(document.frm.filgrupo);
	  document.frmAjaxNuevo.target = 'frameAjaxNuevo';
      document.frmAjaxNuevo.ajax[0].value = document.frm.filinst.value;
      document.frmAjaxNuevo.ajax[1].value = document.frm.sede.value;
      document.frmAjaxNuevo.ajax[2].value = document.frm.jornada.value;
      document.frmAjaxNuevo.ajax[3].value = document.frm.metodologia.value;
      document.frmAjaxNuevo.ajax[4].value = document.frm.grado.value;
      document.frmAjaxNuevo.cmd.value = document.frmAjaxNuevo.CMD_AJAX_GRUP.value;
	  document.frmAjaxNuevo.submit();
   }
			 
			 
			</script>
<%@include file="../mensaje.jsp"%>

<form method="post" name="frmAjaxNuevo"
	action='<c:url value="/AjaxCommon.do"/>'>
	<input type="hidden" name="tipo" value='1'> <input
		type="hidden" name="cmd" value='-1'> <input type="hidden"
		name="CMD_AJAX_SED" id="CMD_AJAX_SED"
		value='<c:out value="${paramsVO.CMD_AJAX_SED  }"/>'> <input
		type="hidden" name="CMD_AJAX_JORD" id="CMD_AJAX_JORD"
		value='<c:out value="${paramsVO.CMD_AJAX_JORD}"/>'> <input
		type="hidden" name="CMD_AJAX_METD" id="CMD_AJAX_METD"
		value='<c:out value="${paramsVO.CMD_AJAX_METD}"/>'> <input
		type="hidden" name="CMD_AJAX_GRUP" id="CMD_AJAX_GRUP"
		value='<c:out value="${paramsVO.CMD_AJAX_GRUP}"/>'> <input
		type="hidden" name="CMD_AJAX_GRAD" id="CMD_AJAX_GRAD"
		value='<c:out value="${paramsVO.CMD_AJAX_GRAD}"/>'> <input
		type="hidden" name="CMD_BUSCAR" id="CMD_BUSCAR"
		value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
	<c:forEach begin="0" end="5" var="i">
		<input type="hidden" name="ajax">
	</c:forEach>
</form>



<FORM METHOD="POST" name='frm' onSubmit=" return validarForma(frm)"
	action='<c:url value="/estudiante/FiltroGuardar.jsp?val=2"/>'>
	<INPUT TYPE="hidden" NAME="nivel" VALUE="1"> <INPUT
		TYPE="hidden" NAME="tipo" VALUE="1"> <INPUT TYPE="hidden"
		NAME="cmd2" VALUE="buscar"> <input type="hidden"
		name="filinst" id="filinst" value='<c:out value="${login.instId}"/>'>
	<table border="0" align="center" bordercolor="#FFFFFF" width="90%">
		<caption>Inactivar Estudiante</caption>
		<tr>
			<td><INPUT class='boton' TYPE="submit" NAME="cmd1"
				VALUE="Inactivar"></td>
		</tr>
	</table>

	<table border="0" align="center" bordercolor="#FFFFFF" width="90%"
		cellpadding="2" cellSpacing="0">
		<tr>
			<th colspan='4' class='Fila0'>Datos del alumno</th>
		</tr>
		<tr>
			<td><span class="style2">*</span>Tipo Documento:</td>
			<td><select name="tipoDocumento" style='width: 60px;'>
					<option value='-9'>-- --</option>
					<c:forEach begin="0" items="${requestScope.filtroTiposDoc}"
						var="vig">
						<option value="<c:out value="${vig.codigo}"/>"
							<c:if test="${sessionScope.filtrob.filTipoDoc==vig.codigo}">SELECTED</c:if>>
							<c:out value="${vig.nombre}" />
						</option>
					</c:forEach>
			</select>
			<td><span class="style2">*</span>Nº de identificación:</td>
			<td><input type='text' name='id' maxlength="15"
				value='<c:out value="${sessionScope.filtroEst.id}"/>'></td>
		</tr>
	</table>
	<table border="0" align="center" bordercolor="#FFFFFF" width="90%"
		cellpadding="2" cellSpacing="0">
		<tr style="display: none">
			<td><iframe name="frameAjaxNuevo" id="frameAjaxNuevo"></iframe>
			</td>
		</tr>
	</table>
</FORM>
<script languaje='javaScript'>
ajaxMetodologia();
</script>