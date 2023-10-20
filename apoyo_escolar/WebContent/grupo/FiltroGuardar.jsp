<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroGrupos" class="siges.adminGrupo.vo.FiltroGrupoVO" scope="session"/>
<jsp:setProperty name="filtroGrupos" property="*"/>
<jsp:useBean id="grupoVO" class="siges.adminGrupo.vo.GrupoVO" scope="session"/>
<jsp:setProperty name="grupoVO" property="*"/>
<c:import url="/grupo/ControllerGrupoFiltroEdit.do"/>