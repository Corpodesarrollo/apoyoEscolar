<jsp:useBean id="filtroEspXGradoVO" class="siges.rotacion.beans.FiltroEspXGradoVO" scope="session"/>
<jsp:useBean id="paramsVO" class="siges.rotacion.beans.ParamsVO" scope="page"/>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@include file="../parametros.jsp"%>
<script language="javaScript">
<!--
		function Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre){
			this.id_Hijos= id_Hijos; this.Hijos= Hijos; this.Sel_Hijos= Sel_Hijos; this.id_Padre=id_Padre;
		}

			function borrar_combo(combo){
				for(var i = combo.length - 1; i >= 0; i--) {
					if(navigator.appName == "Netscape") combo.options[i] = null;
					else combo.remove(i);
				}
				combo.options[0] = new Option("--Seleccione una--","-99");
			}
			
			function filtroJornada0(combo_padre,combo_hijo){
					borrar_combo(combo_hijo); 
					<c:if test="${!empty requestScope.lSedeVO && !empty requestScope.lJornadaVO}">
						var Padres = new Array();
						<c:forEach begin="0" items="${requestScope.lSedeVO}" var="sede" varStatus="st">
							id_Hijos=new Array();  Hijos= new Array();  Sel_Hijos= new Array();  id_Padre= new Array();  var k=0;
							<c:forEach begin="0" items="${requestScope.lJornadaVO}" var="jornada">
								<c:if test="${sede.codigo==jornada.sede}"> Sel_Hijos[k] = '<c:if test="${sessionScope.filtroEspXGradoVO.filJornada==jornada.codigo}">SELECTED</c:if>'; id_Hijos[k] = '<c:out value="${jornada.codigo}"/>';  Hijos[k] = '<c:out value="${jornada.nombre}"/>'; id_Padre[k] = '<c:out value="${jornada.sede}"/>'; k++;</c:if>
							</c:forEach>Padres[<c:out value="${st.index}"/>] = new Padre(id_Hijos, Hijos, Sel_Hijos,id_Padre);</c:forEach>
						var niv=combo_padre.options[combo_padre.selectedIndex].value;
						var val_padre = -99;
						for(var k=0;k<Padres.length;k++){
							if(Padres[k].id_Padre[0]==niv) val_padre=k;							
						}
						if(val_padre!=-99){ var no_hijos = Padres[val_padre].Hijos.length;
							for(i=0; i < no_hijos; i++){
								if(Padres[val_padre].Sel_Hijos[i] == "SELECTED"){
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i], Padres[val_padre].id_Hijos[i],"SELECTED"); combo_hijo.selectedIndex = i+1;
								}else
									combo_hijo.options[i+1] = new Option(Padres[val_padre].Hijos[i],Padres[val_padre].id_Hijos[i]);
							}
						}
				</c:if>}

	function buscar(){
		if(validarForma(document.frmLista)){
			document.frmLista.cmd.value='<c:out value="${paramsVO.CMD_BUSCAR}"/>';
			document.frmLista.submit();
		}
	}
	
	function hacerValidaciones_frmLista(forma){
		validarLista(forma.filSede, "- Sede", 1)
		validarLista(forma.filJornada, "- Jornada", 1)
	}
		
	function lanzar(tipo){
		document.frmLista.tipo.value=tipo;
		document.frmLista.action='<c:url value="/rotacion/ControllerEditar.do"/>';
		document.frmLista.target="";
		document.frmLista.submit();
	}

	function lanzar2(tipo){
		document.frmLista.tipo.value=tipo;
		document.frmLista.action='<c:url value="/rotacion/ControllerEditar.do"/>';
		document.frmLista.target="";
		document.frmLista.submit();
	}

	function editar(n,m,o,p,q,r){
		if(document.frmLista.param){
				document.frmLista.param[0].value=n;
				document.frmLista.param[1].value=m;
				document.frmLista.param[2].value=o;
				document.frmLista.param[3].value=p;
				document.frmLista.param[4].value=q;
				document.frmLista.param[5].value=r;				
				document.frmLista.cmd.value='<c:out value="${paramsVO.CMD_EDITAR}"/>'
				document.frmLista.submit();
		}
	}
	
	function eliminar(n,m,o,p,q,r){
		<c:if test="${sessionScope.isLockedRotacion!=0}">alert('Registro solo para consulta'); return; </c:if>
		if(document.frmLista.param){
			if(confirm('�Desea eliminar la asignaci�n?')){
				document.frmLista.param[0].value=n;
				document.frmLista.param[1].value=m;
				document.frmLista.param[2].value=o;
				document.frmLista.param[3].value=p;
				document.frmLista.param[4].value=q;
				document.frmLista.param[5].value=r;				
				document.frmLista.cmd.value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'
				document.frmLista.submit();
			}	
		}
	}

//-->
</script>
<%@include file="../mensaje.jsp" %>
		<form action='<c:url value="/rotacion/NuevoGuardar.jsp"/>' method="post" name="frmLista" onSubmit="return validarForma(frmLista)">
			<input type="hidden" name="cmd" value="">
			<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_ESPACIO_GRADO}"/>'>
			<input type="hidden" name="param"><input type="hidden" name="param"><input type="hidden" name="param">
			<input type="hidden" name="param"><input type="hidden" name="param"><input type="hidden" name="param">
			<input type="hidden" name="filInstitucion" value='<c:out value="${sessionScope.login.instId}"/>'>
			<input type="hidden" name="guia" value="<c:out value="${requestScope.guia}"/>">
			<table>
				<tr>
					<td rowspan="2" width="600">
						<a href="javaScript:lanzar(10)"><img src='<c:url value="/etc/img/tabs/estructura_0.gif"/>' alt='Estructura' height="26" border="0"></a>
						<a href="javaScript:lanzar(20)"><img src="../etc/img/tabs/espacios_0.gif" alt="Fijar Espacios F�sicos" height="26" border="0"></a>
						<a href="javaScript:lanzar(60)"><img src='<c:url value="/etc/img/tabs/fijar_asignatura_0.gif"/>' alt='Fijar Asignatura' height="26" border="0"></a>
						<a href="javaScript:lanzar(70)"><img src='<c:url value="/etc/img/tabs/espacio_docente_0.gif"/>' alt='Fijar Espacio por Docente' height="26" border="0"></a>
						<a href="javaScript:lanzar(40)"><img src="../etc/img/tabs/espfis_jornada_0.gif" alt="Inhabilitar Espacios F�sicos" height="26" border="0"></a>
						<a href="javaScript:lanzar(50)"><img src='<c:url value="/etc/img/tabs/inhabilitar_docentes_0.gif"/>' alt='Inhabilitar Docentes' height="26" border="0"></a><br>
						<a href="javaScript:lanzar2(80)"><img src='<c:url value="/etc/img/tabs/priorizar_0.gif"/>' alt='Priorizar' height="26" border="0"></a>
						<a href="javaScript:lanzar2(100)"><img src='<c:url value="/etc/img/tabs/fijar_horario_0.gif"/>' alt='Fijar horario' height="26" border="0"></a>
						<a href="javaScript:lanzar2(110)"><img src='<c:url value="/etc/img/tabs/borrar_horario_0.gif"/>' alt='Borrar horario' height="26" border="0"></a>
						<a href="javaScript:lanzar(120)"><img src='<c:url value="/etc/img/tabs/inhabilitar_horas_0.gif"/>' alt='Inhabilitar Horas' height="26" border="0"></a>
						<a href="javaScript:lanzar(130)"><img src='<c:url value="/etc/img/tabs/docente_grupo_0.gif"/>' alt='Docente por grupo' height="26" border="0"></a>
						<img src='<c:url value="/etc/img/tabs/espacio_grado_1.gif"/>' alt='Espacio por grado' height="26" border="0">
					</td>
				</tr>
			</table>
  	  <table border="0" align="center" width="95%" cellpadding="1" cellspacing="0">
			<caption>Filtro de b�squeda</caption>
			<tr><td width="45%" align="left" colspan="4"><input name="cmd2" class="boton" type="button" value="Buscar" onClick="buscar()"></td></tr>
					<tr>
						<td><span class="style2" >*</span>Sede:</td>
				    <td>
							<select name="filSede" onChange='filtroJornada0(document.frmLista.filSede, document.frmLista.filJornada)' style='width:220px;'>
								<option value='-99'>-- Seleccione uno --</option>
								<c:forEach begin="0" items="${requestScope.lSedeVO}" var="sede">
								<option value="<c:out value="${sede.codigo}"/>" <c:if test="${sessionScope.filtroEspXGradoVO.filSede == sede.codigo}">SELECTED</c:if>><c:out value="${sede.nombre}"/></option></c:forEach>
							</select>
				   </td>
						<td><span class="style2" >*</span>Jornada:</td>
					  <td>
							<select name="filJornada" style='width:100px;'>
								<option value='-99'>-- Seleccione uno --</option>
							</select>
					  </td>
				</tr>
				<tr>
						<td><span class="style2">*</span> Vigencia:</td>
						<td>
							<select name="filVigencia" style='width:50px;'>
							<c:forEach begin="0" items="${requestScope.listaVigencia}" var="vig">
								<option value="<c:out value="${vig}"/>" <c:if test="${vig==sessionScope.filtroEspXGradoVO.filVigencia}">selected</c:if>><c:out value="${vig}"/></option>
							</c:forEach>
							</select>
						</td>
						<td><span class="style2">*</span> Metodologia:</td>
						<td>
							<select name="filMetodologia" style='width:100px;'>
							<c:forEach begin="0" items="${requestScope.lMetodologiaVO}" var="vig">
								<option value="<c:out value="${vig.codigo}"/>" <c:if test="${vig.codigo==sessionScope.filtroEspXGradoVO.filMetodologia}">selected</c:if>><c:out value="${vig.nombre}"/></option>
							</c:forEach>
							</select>
						</td>
					</tr>
  	  </table>
 	  </form>
	  <table border="1" align="center" width="95%" cellpadding="0" cellspacing="0">
		  	<caption>Resultado de la B�squeda</caption>
			  <c:if test="${empty requestScope.lEspacioGrado}">
					<tr>
						<th class="Fila1" colspan='6'>LA B�SQUEDA NO ARROJO RESULTADOS</th>
					</tr>
				</c:if>
		  	<c:if test="${!empty requestScope.lEspacioGrado}">
			  	<tr>
						<th width='30' class="EncabezadoColumna">&nbsp;</th>
						<th width='90%' class="EncabezadoColumna">Espacio f�sico</th>
					</tr>
					<c:forEach begin="0" items="${requestScope.lEspacioGrado}" var="espGra" varStatus="st">
						<tr>
							<th class='Fila<c:out value="${st.count%2}"/>'>
							<a href='javaScript:editar(<c:out value="${espGra.espGraInstitucion}"/>,<c:out value="${espGra.espGraSede}"/>,<c:out value="${espGra.espGraJornada}"/>,<c:out value="${espGra.espGraMetodologia}"/>,<c:out value="${espGra.espGraEspacio}"/>,<c:out value="${sessionScope.filtroEspXGradoVO.filVigencia}"/>);'><img border='0' src='../etc/img/<c:if test="${requestScope.guia==espGra.espGraInstitucion && requestScope.guia2==espGra.espGraSede && requestScope.guia3==espGra.espGraJornada && requestScope.guia4==espGra.espGraMetodologia && requestScope.guia5==espGra.espGraEspacio}">X</c:if>editar.png' width='15' height='15'></a>
							<a href='javaScript:eliminar(<c:out value="${espGra.espGraInstitucion}"/>,<c:out value="${espGra.espGraSede}"/>,<c:out value="${espGra.espGraJornada}"/>,<c:out value="${espGra.espGraMetodologia}"/>,<c:out value="${espGra.espGraEspacio}"/>,<c:out value="${sessionScope.filtroEspXGradoVO.filVigencia}"/>);'><img border='0' src='../etc/img/eliminar.png' width='15' height='15'></a>
							</th>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"><c:out value="${espGra.espGraNombreEspacio}"/></td>
						</tr>
					</c:forEach>
				</c:if>
		  </table>
 	  </form>
<script> 
filtroJornada0(document.frmLista.filSede,document.frmLista.filJornada);
</script>