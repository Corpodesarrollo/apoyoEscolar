<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean  id="filtroGrupos" class="siges.adminGrupo.vo.FiltroGrupoVO" scope="session"/>
<jsp:useBean  id="paramsVO"   class="siges.adminGrupo.vo.ParamsVO" scope="page"/>
<html>
<head>
  
<script languaje="javaScript">

  
	 
  function editar(jerGrado,codCodigo){
	  //alert("params inst:"+inst+"  vig:"+vig+"  metod:"+metodo+"  codigo:"+codCodigo);
	 if(document.frmFiltro.param){
			 document.frmFiltro.param[0].value = jerGrado;
			 document.frmFiltro.param[1].value = codCodigo;			 
			 document.frmFiltro.cmd.value = document.frmFiltro.EDITAR.value;
			 document.frmFiltro.submit();
		}
	}
	
	
  function eliminar(inst, vig, metodo,codCodigo){
   if(document.frmFiltro.param  && confirm("¿Esta seguro de eliminar esta información?")){
			 document.frmFiltro.param[0].value = inst;
			 document.frmFiltro.param[1].value = vig;
			 document.frmFiltro.param[2].value = metodo;			  
			 document.frmFiltro.param[3].value =  codCodigo;
		document.frmFiltro.cmd.value = document.frmFiltro.ELIMINAR.value;
		document.frmFiltro.submit();
	}
  }
	
  function buscar(){
	if(validarForma(document.frmFiltro)){
	    document.frmFiltro.cmd.value = document.frmFiltro.BUSCAR.value;
		document.frmFiltro.submit();
	}
  }
	
  function hacerValidaciones_frmFiltro(forma){
   
      validarLista(forma.filCodSede,'- Sede',1);
      validarLista(forma.filCodJorn,'- Jornada',1);
      validarLista(forma.filCodMetodo,'- Metodologia',1);
      validarLista(forma.filCodGrado,'- Grado',0);
   
  }

  function cargaJornadas(){
		document.frmAux.cod_inst.value=document.frmFiltro.filCodInst.value;
		document.frmAux.cod_sede.value=document.frmFiltro.filCodSede.options[document.frmFiltro.filCodSede.selectedIndex].value;
		document.frmAux.cmd.value=document.frmAux.cmd1.value;
		document.frmAux.target="frame";
		document.frmAux.submit();
}

  function cargaGrados(){
		document.frmAux.cod_inst.value=document.frmFiltro.filCodInst.value;
		document.frmAux.cod_sede.value=document.frmFiltro.filCodSede.options[document.frmFiltro.filCodSede.selectedIndex].value;
		document.frmAux.cod_metod.value=document.frmFiltro.filCodMetodo.options[document.frmFiltro.filCodMetodo.selectedIndex].value;
		document.frmAux.cod_jornada.value=document.frmFiltro.filCodJorn.options[document.frmFiltro.filCodJorn.selectedIndex].value;
		document.frmAux.cmd.value=document.frmAux.cmd2.value;		
		document.frmAux.target="frame";
		document.frmAux.submit();
}
	  
	   
	
</script>
</head>
<c:import url="/mensaje.jsp"/>
<body onLoad="mensaje(document.getElementById('msg'));">
 
	<form method="post" name="frmFiltro"  action='<c:url value="/grupo/FiltroGuardar.jsp"/>'>
		
		 
	
	    <input TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_GRUPOS}"/>'>
		<input type="hidden" name="cmd" value=''>
		<c:forEach begin="0" end="12"><input type="hidden" name="param" value=''></c:forEach>
		<input type="hidden" name="EDITAR" value='<c:out value="${paramsVO.CMD_EDITAR}"/>'>
		<input type="hidden" name="ELIMINAR" value='<c:out value="${paramsVO.CMD_ELIMINAR}"/>'>
		<input type="hidden" name="BUSCAR" value='<c:out value="${paramsVO.CMD_BUSCAR}"/>'>
		<input type="hidden" name="NUEVO" value='<c:out value="${paramsVO.CMD_NUEVO}"/>'>
		<input type="hidden" name="filCodInst" id="filCodInst" value='<c:out value="${sessionScope.filtroGrupos.filCodInst}"/>'>		
		 
		 <table cellpadding="2" cellspacing="2"  width="100%">
		<tr> <td bgcolor="#FFFFFF"><input class='boton' name="cmd1" type="button" value="Buscar" onClick="buscar()">
		<input class='boton' name="cmd12" type="button" value="Cancelar" onClick="cancelar()">
		</td>
			 </tr>
		</table>	 
		<table cellpadding="2" cellspacing="2"  width="100%">
		
	      <tr><td width="80px"><font color="red">*</font>Sede</td>
		      <td>
		      <select name="filCodSede" id="filCodSede" style="width:220px;" onchange="cargaJornadas()">
		      		<option value='-99'>-- Seleccione uno --</option>
					<c:forEach begin="0" items="${requestScope.listaSedes}" var="item">
						<option value="<c:out value="${item.codigo}"/>" <c:if test="${item.codigo==sessionScope.filtroGrupos.filCodSede}">selected</c:if>><c:out value="${item.nombre}"/></option>
					</c:forEach>
		      </select>
			  </td>
			  <td width="80px"><font color="red">*</font>Jornada</td>
			    <td width="120px"><select name="filCodJorn" id="filCodJorn" style="width: 160px;" onchange="cargaGrados()">
			    	<option value='-99'>-- Seleccione uno --</option>
					<c:forEach begin="0" items="${requestScope.listaJornadas}" var="item">
						<option value="<c:out value="${item.codigo}"/>" <c:if test="${item.codigo==sessionScope.filtroGrupos.filCodJorn}">selected</c:if>><c:out value="${item.nombre}"/></option>
					</c:forEach>
			    </select>
			  </td>
		   </tr>
		    <tr><td width="80px"><font color="red">*</font>Metodología</td>
		      <td><select name="filCodMetodo"   id="filCodMetodo" style="width: 150px;" onchange="cargaGrados()">
		      		<option value='-99'>-- Seleccione uno --</option>
					<c:forEach begin="0" items="${requestScope.listaMetodologias}" var="item">
						<option value="<c:out value="${item.codigo}"/>" <c:if test="${item.codigo==sessionScope.filtroGrupos.filCodMetodo}">selected</c:if>><c:out value="${item.nombre}"/></option>
					</c:forEach>
		      </select>
			  </td>
			  <td width="80px">Grado</td>
			    <td width="120px"><select name="filCodGrado" id="filCodGrado" style="width: 160px;">
			    	<option value='-99'>-- Todos --</option>
					<c:forEach begin="0" items="${requestScope.listaGrados}" var="item">
						<option value="<c:out value="${item.codigo}"/>" <c:if test="${item.codigo==sessionScope.filtroGrupos.filCodGrado}">selected</c:if>><c:out value="${item.nombre}"/></option>
					</c:forEach>			    
			    </select>
			  </td>
		 			 <tr>
						<td colspan="4" style="display:none"><iframe name="frame" id="frame"></iframe></td>
					</tr>	
		   </tr>
		 </table>
		
		 <!-- LISTA DE RESULTADOS -->
         <div style="width:100%;height:100px;overflow:auto;vertical-align:top;background-color:#ffffff;">
		   <table border="1" align="center" width="95%" cellpadding="0" cellspacing="0" bordercolor="gray" class="table01" style="text-align: left;" >
			 	<caption>Lista Grupos</caption>
			 	<c:if test="${empty requestScope.listaGrupos}"><tr><th class="Fila1" colspan='6'>No hay datos</th></tr></c:if>
				<c:if test="${!empty requestScope.listaGrupos}">
					<tr> 
						<th class="EncabezadoColumna" align="center">&nbsp;</th>						
						<th class="EncabezadoColumna" align="center">Grado</th>
						<th class="EncabezadoColumna" align="center">Código</th> 
						<th class="EncabezadoColumna" align="center">Nombre</th>
						<th class="EncabezadoColumna" align="center">Cupo</th>
						<th class="EncabezadoColumna" align="center">Director de Grupo</th>
						<th class="EncabezadoColumna" align="center">Orden</th>
					</tr>
					<c:forEach begin="0" items="${requestScope.listaGrupos}" var="lista" varStatus="st">
						<tr>
						  <th class='Fila<c:out value="${st.count%2}"/>' width="40px;">
							<a href='javaScript:editar(<c:out value="${lista.codJerGrado}"/>,<c:out value="${lista.codigo}"/>);'><img border='0' src='<c:url value="/etc/img/editar.png"/>' width='15' height='15'></a>
							<c:if test="${sessionScope.NivelPermiso==2 }">
							<a href='javaScript:eliminar(<c:out value="${lista.codJerGrado}"/>,<c:out value="${lista.codigo}"/>);'><img border='0' src='<c:url value="/etc/img/eliminar.png"/>' width='15' height='15'></a></c:if>
						   </th>							 
							<td class='Fila<c:out value="${st.count%2}"/>'  align="center" ><c:out value="${lista.nombreGrado}"/> </td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"  >&nbsp;<c:out value="${lista.codigo}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"  >&nbsp;<c:out value="${lista.nombre}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"  >&nbsp;<c:out value="${lista.cupo}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"  >&nbsp;<c:out value="${lista.nombreDirector}"/></td>
							<td class='Fila<c:out value="${st.count%2}"/>' align="center"  >&nbsp;<c:out value="${lista.orden}"/></td>							
						</tr>
					</c:forEach>
				</c:if>
			</table>
		</div>		 
	 </form>	
	 
	 <form method="post" name="frmAux" action='<c:url value="/grupo/Ajax.do"/>'>
						<input type="hidden" name="cmd" value='<c:out value="${parametros.CMD_CARGAR}"/>'>
						<input type="hidden" name="cod_inst" value="">
						<input type="hidden" name="cod_sede" value="">
						<input type="hidden" name="cod_jornada" value="">
						<input type="hidden" name="cod_metod" value="">
						<input type="hidden" name="cod_tipo_esp" value="">
						<input type="hidden" name="cmd1" value='<c:out value="${paramsVO.CMD_JORN}"/>'>
						<input type="hidden" name="cmd2" value='<c:out value="${paramsVO.CMD_GRADO}"/>'>
						<input type="hidden" name="cmd3" value='<c:out value="${paramsVO.CMD_ESPACIO}"/>'>
						<INPUT TYPE="hidden" NAME="tipo" VALUE='<c:out value="${paramsVO.FICHA_GRUPOS}"/>'>
		</form>
		 
	</body>
</html>