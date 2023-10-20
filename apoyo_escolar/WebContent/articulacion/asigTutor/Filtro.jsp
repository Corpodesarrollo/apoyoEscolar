<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="paramsVO" class="articulacion.asigTutor.vo.ParamsVO" scope="page"/>
<jsp:useBean id="FilAsigTutorVO" class="articulacion.asigTutor.vo.DatosVO" scope="session"/>
<html>
<head>
<script languaje="javaScript">
	function filtrar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.cmd.value=document.frmFiltro.FILTRO.value;
			document.frmFiltro.action='<c:url value="/articulacion/asigTutor/Save0.jsp"/>';
			document.frmFiltro.submit();
		}
	}
	
	
	function hacerValidaciones_frmFiltro(forma){
		validarLista(forma.docente, "- Seleccione un Docente", 1)
	}
	
	function borrar_combo(combo){
		for(var i = combo.length - 1; i >= 0; i--) {
			if(navigator.appName == "Netscape") combo.options[i] = null;
			else combo.remove(i);
		}
		combo.options[0] = new Option("--","-99");
	}
	
	function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
			this.id_Hijos= id_Hijos; 
			this.Hijos= Hijos; 
			this.Sel_Hijos= Sel_Hijos; 
			this.id_Padre=id_Padre;
		}
	
	function setJornadas(combo_padre,combo_hijo){
		borrar_combo(combo_hijo); 
		<c:if test="${!empty requestScope.listaSedeVO && !empty requestScope.listaJornadaVO}">
			var Padres = new Array();
			<c:forEach begin="0" items="${requestScope.listaSedeVO}" var="sede" varStatus="st">
				id_Hijos=new Array();  Hijos= new Array();  Sel_Hijos= new Array();  id_Padre= new Array();  var k=0;
				<c:forEach begin="0" items="${requestScope.listaJornadaVO}" var="jornada">
					<c:if test="${sede.codigo==jornada.sede}"> 
						Sel_Hijos[k] = '<c:if test="${jornada.codigo==sessionScope.FilAsigTutorVO.jornada}">SELECTED</c:if>'; 
						id_Hijos[k] = '<c:out value="${jornada.codigo}"/>';  
						Hijos[k] = '<c:out value="${jornada.nombre}"/>'; 
						id_Padre[k] = '<c:out value="${jornada.sede}"/>'; k++;
					</c:if>
				</c:forEach>
				Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);
			</c:forEach>
			var niv=combo_padre.options[combo_padre.selectedIndex].value;
			var val_padre = -99;
			for(var k=0;k<Padres.length;k++){
				if(Padres[k].id_Padre[0]==niv) val_padre=k;							
			}
			if(val_padre!=-99){ 
				var no_hijos = Padres[val_padre].Hijos.length;
				for(i=0; i < no_hijos; i++){
					if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
						combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); 
						combo_hijo.selectedIndex = i+1;
					}else
						combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
				}
			}
		</c:if>
	}
	function ajaxDocente(obj){
		borrar_combo(document.frmFiltro.docente); 
		var val=obj.options[obj.selectedIndex].value;
	//	alert(val);
		if(val!=-99){//
			document.frmAjax.ajax[1].value=document.frmFiltro.sede.value;
			document.frmAjax.ajax[2].value=val;
			document.frmAjax.cmd.value='<c:out value="${paramsVO.CMD_AJAX_DOCENTE}"/>';
	 		document.frmAjax.target="frameAjax";
			document.frmAjax.submit();
		}
	}
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body>
 <form method="post" name="frmFiltro" onSubmit="return validarForma(frmNuevo)">
	<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
		<tr>
			<td width="10">&nbsp;</td>
			<td width="469">
				<img src='<c:url value="/etc/img/tabs/asignar_tutor_articulacion_1.gif"/>' alt="Asignar Tutor" height="26" border="0">
			</td>
		</tr>
	</table>
	 <input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ASIG_TUTOR}"/>'>
	 <input type="hidden" name="FILTRO" value='1'>
	 <input type="hidden" name="cmd" value=''>
	 <input type="hidden" name="institucion" value='<c:out value="${sessionScope.login.instId}"/>'>
	 <input type="hidden" name="metodologia" value='<c:out value="${sessionScope.login.metodologiaId}"/>'>
		<table border="0" align="center" width="100%">
			<caption>Filtro de Búsqueda</caption>
				<tr>
			 		<td style='width:30px;'>
			 			<input name="cmd" type="button" value="Buscar" onClick="filtrar()" class="boton">
			 		</td>
		 		</tr>
				<tr>
				<tr>
					<td width="25%"><span class="style2">*</span><b>Sede:</b></td>
			 		<td  width="25%">
						<select style="width:300px;" name="sede" onchange="setJornadas(this,document.frmFiltro.jornada)">
							<c:forEach begin="0" items="${requestScope.listaSedeVO}" var="sede">
								<option value="<c:out value="${sede.codigo}"/>" <c:if test="${sede.codigo==sessionScope.FilAsigTutorVO.sede}">selected</c:if>><c:out value="${sede.nombre}"/></option>
							</c:forEach>
						</select>
		  			</td>
		  			<td  width="25%"><span class="style2">*</span><b>Jornada:</b></td>
			  		<td width="25%">
						<select name="jornada" onchange="ajaxDocente(this)">
							<option value="-99">----</option>
						</select>
		  			</td>
		  		</tr>
		  		<tr>
					<td>
						<span class="style2">*</span><b>Docente:</b>
					</td>
					<td colspan="3">
						<select style="width:300px;" name="docente">
							<option value="0">--Seleccione uno--</option>
							<c:forEach begin="0" items="${sessionScope.ajaxDocente}" var="docente" varStatus="st">
								<option value='<c:out value="${docente.codigo}"/>' <c:if test="${docente.codigo==sessionScope.FilAsigTutorVO.docente}">selected</c:if>><c:out value="${docente.nombre}"/></option>
							</c:forEach>
						</select>
			  		</td>
			  	</tr>
			  	<tr>
			  		<td><b>Especialidad:</b></td>
			  		<td>
						<select name="especialidad">
							<option value="0">--Seleccione una--</option>
							<c:forEach begin="0" items="${requestScope.listaEspecialidadVO}" var="especialidad">
								<option value="<c:out value="${especialidad.codigo}"/>"  <c:if test="${especialidad.codigo==sessionScope.FilAsigTutorVO.especialidad}">selected</c:if> ><c:out value="${especialidad.nombre}"/></option>
							</c:forEach>
						</select>
			  		</td>
			  		<td><b>Semestre:</b></td>
			  		<td>
						<select name="semestre">
							<option value="0">--</option>
							<option value="1" <c:if test="${1==sessionScope.FilAsigTutorVO.semestre}">selected</c:if>>1</option>
							<option value="2" <c:if test="${2==sessionScope.FilAsigTutorVO.semestre}">selected</c:if>>2</option>
							<option value="3" <c:if test="${3==sessionScope.FilAsigTutorVO.semestre}">selected</c:if>>3</option>
							<option value="4" <c:if test="${4==sessionScope.FilAsigTutorVO.semestre}">selected</c:if>>4</option>
						</select>
		  			</td>
				<tr>
				<td style="display:none"><iframe name="frameAjax" id="frameAjax"></iframe></td>
				</tr>
		 </table>
	</form>
	<form method="post" name="frmAjax" action="<c:url value="/articulacion/asigTutor/Ajax.do"/>">
		<input type="hidden" name="ajax" value='<c:out value="${sessionScope.login.instId}"/>'>
		<input type="hidden" name="ajax">
		<input type="hidden" name="ajax">
		<INPUT TYPE="hidden" NAME="tipo" VALUE=''>
		<input type="hidden" name="cmd">
	</form>
</body>
<script>
	setJornadas(document.frmFiltro.sede,document.frmFiltro.jornada);
	//ajaxDocente(document.frmFiltro.jornada);
</script>
</html>