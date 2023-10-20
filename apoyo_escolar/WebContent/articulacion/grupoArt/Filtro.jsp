<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="articulacion.grupoArt.vo.ParamsVO" scope="page"/>
<jsp:useBean id="params2VO" class="articulacion.horarioArticulacion.vo.ParamsVO" scope="page"/>
<jsp:useBean id="filtroGrupoVO" class="articulacion.grupoArt.vo.DatosVO" scope="session"/>
<html>
<head>
<script languaje="javaScript">
	function lanzarServicio(n){
		if(n==1){//grupo
			document.frmFiltro.action='<c:url value="/articulacion/grupoArt/Nuevo.do"/>';
  	}
		if(n==2){//para horario
			document.frmFiltro.action='<c:url value="/articulacion/horarioArticulacion/Nuevo.do"/>';  	
			document.frmFiltro.tipo.value='<c:out value="${params2VO.FICHA_PARAMETROS}"/>';
  	}
		if(n==3){//horario
			document.frmFiltro.action='<c:url value="/articulacion/horarioArticulacion/Nuevo.do"/>';  	
			document.frmFiltro.tipo.value='<c:out value="${params2VO.FICHA_HORARIO}"/>';
  	}
		if(n==4){//horario por grupo
			document.frmFiltro.action='<c:url value="/articulacion/horarioArticulacion/Nuevo.do"/>';  	
			document.frmFiltro.tipo.value='<c:out value="${params2VO.FICHA_HORARIO_GRUPO}"/>';
  	}
		if(n==5){//horario por docente
  		document.frmFiltro.action='<c:url value="/articulacion/horarioArticulacion/Nuevo.do"/>';  	
			document.frmFiltro.tipo.value='<c:out value="${params2VO.FICHA_HORARIO_DOCENTE}"/>';
  	}
		document.frmFiltro.target="";
		document.frmFiltro.submit();
	}

	
	function filtrar(){
		if(validarForma(document.frmFiltro)){
			document.frmFiltro.cmd.value=document.frmFiltro.FILTRO.value;
			document.frmFiltro.action='<c:url value="/articulacion/grupoArt/Save0.jsp"/>';
			document.frmFiltro.submit();
		}	
	}
	
	function cambioSelect(objeto){
		var variable=objeto.options[objeto.selectedIndex].value
		if(variable=='0'){
			   document.frmFiltro.especialidad.value=0;
			   document.getElementById ('esp').style.display='none';
		       document.getElementById ('esp1').style.display='none';
		}
		else if(variable=='2'){
		       document.getElementById ('esp').style.display='';
		       document.getElementById ('esp1').style.display='';
		}else if(variable=='1'){
			   document.frmFiltro.especialidad.value=0;
			   document.getElementById ('esp').style.display='none';
		       document.getElementById ('esp1').style.display='none';
		}
	}

	function hacerValidaciones_frmFiltro(forma){
		validarLista(forma.jornada, "- Jornada", 1)
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
						Sel_Hijos[k] = '<c:if test="${jornada.codigo==sessionScope.filtroGrupoVO.jornada}">SELECTED</c:if>'; 
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
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body>
 <form method="post" name="frmFiltro" onSubmit="return validarForma(frmNuevo)">
	 <input type="hidden" name="cmd" value=''>
	 <input type="hidden" name="tipo" value=''>
	 <input type="hidden" name="FILTRO" value='<c:out value="${2}"/>'>
	 <input type="hidden" name="institucion" value='<c:out value="${sessionScope.login.instId}"/>'>
	 
	 <input type="hidden" name="componente" value='2'>
	
		<table cellpadding="0" cellspacing="0" border="0" ALIGN="CENTER" width="100%" >
			<tr height="1">
				<td width="10">&nbsp;</td>
				<td rowspan="2" width="469">
					<img src='<c:url value="/etc/img/tabs/art_grupos_1.gif"/>' alt="Grupos" height="26" border="0">
					<a href="javaScript:lanzarServicio(4)"><img src='<c:url value="/etc/img/tabs/horario_grupo_0.gif"/>' alt="Horario"  height="26" border="0"></a>
					<a href="javaScript:lanzarServicio(5)"><img src='<c:url value="/etc/img/tabs/horario_docente_0.gif"/>' alt="Horario"  height="26" border="0"></a>
				</td>
			</tr>
		</table>

		<table border="0" align="center" bordercolor="#FFFFFF" width="100%">
			<caption>Filtro de Búsqueda</caption>
					<tr>
				 		<td style='width:30px;'>
				 			<input name="cmd" type="button" value="Buscar" onClick="filtrar()" class="boton">
				 		</td>
			 		</tr>
					<tr>
					<tr>
						<td>
							<span class="style2">*</span><b>Sede:</b>
						</td>
				 		<td colspan="6">
							<select name="sede" onchange="setJornadas(this,document.frmFiltro.jornada)">
								<c:forEach begin="0" items="${requestScope.listaSedeVO}" var="sede">
									<option value="<c:out value="${sede.codigo}"/>" <c:if test="${sede.codigo==sessionScope.filtroGrupoVO.sede}">selected</c:if>><c:out value="${sede.nombre}"/></option>
								</c:forEach>
							</select>
			  			</td>
			  		</tr>
			  		<tr>
				  		<td>
							<span class="style2">*</span><b>Jornada:</b>
						</td>
				  		<td>
							<select name="jornada">
							</select>
			  			</td>
						<td>
							<span class="style2">*</span><b>Vigencia:</b>
						</td>
						<td colspan="2">
							<select name="anVigencia" style='width:50px;'>
								<c:forEach begin="0" items="${requestScope.listaVigencia}" var="item">
									<option value='<c:out value="${item.codigo}"/>' <c:if test="${item.codigo==sessionScope.filtroGrupoVO.anVigencia}">selected</c:if>><c:out value="${item.nombre}"/></option>
								</c:forEach>
							</select>
							-
							<select name="perVigencia">
								<option value="1" <c:if test="${1==sessionScope.filtroGrupoVO.perVigencia}">selected</c:if>>1</option>
								<option value="2" <c:if test="${2==sessionScope.filtroGrupoVO.perVigencia}">selected</c:if>>2</option>
							</select>
				  		</td>
				  		<td>
							<span class="style2">*</span><b>Semestre:</b>
						</td>
				  		<td>
							<select name="periodo">
								<option value="1" <c:if test="${1==sessionScope.filtroGrupoVO.periodo}">selected</c:if>>1</option>
								<option value="2" <c:if test="${2==sessionScope.filtroGrupoVO.periodo}">selected</c:if>>2</option>
								<option value="3" <c:if test="${3==sessionScope.filtroGrupoVO.periodo}">selected</c:if>>3</option>
								<option value="4" <c:if test="${4==sessionScope.filtroGrupoVO.periodo}">selected</c:if>>4</option>
							</select>
			  			</td>
					</tr>
					<tr>
						<!-- <td style='width:30px;'>
							<span class="style2">*</span><b>Componente:</b>
						</td>
						
						<td>
							<select id="comp" name="componente" onChange='javaScript:cambioSelect(this);'>
								<option value="1" <c:if test="${1==sessionScope.filtroGrupoVO.componente}">selected</c:if>>Académico</option>
								<option value="2" <c:if test="${2==sessionScope.filtroGrupoVO.componente}">selected</c:if>>Técnico</option>
							</select>
				  		</td>  -->
				  		<td colspan="1" id='esp'>
							<span class="style2">*</span><b>Especialidad:</b>
						</td>
				  		<td id='esp1' colspan="3">
							<select name="especialidad">
							<option value="0">---</option>
								<c:forEach begin="0" items="${requestScope.listaEspecialidadVO}" var="especialidad">
									<option value="<c:out value="${especialidad.codigo}"/>"  <c:if test="${especialidad.codigo==sessionScope.filtroGrupoVO.especialidad}">selected</c:if>><c:out value="${especialidad.nombre}"/></option>
								</c:forEach>
							</select>
				  		</td>
					</tr>	
		  </table>
	</form>
</body>
<script>
	//cambioSelect(document.frmFiltro.componente);
	setJornadas(document.frmFiltro.sede,document.frmFiltro.jornada);
</script>
</html>