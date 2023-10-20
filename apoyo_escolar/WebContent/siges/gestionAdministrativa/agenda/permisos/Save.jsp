<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="siges.gestionAdministrativa.agenda.vo.ParamsVO" scope="page"/>

<jsp:useBean id="permisosAct" class="siges.gestionAdministrativa.agenda.vo.PermisosVO" scope="session"/>
<jsp:setProperty name="permisosAct" property="*"/>

<jsp:useBean id="permisosFiltro" class="siges.gestionAdministrativa.agenda.vo.FiltroPermisosVO" scope="session"/>
<jsp:setProperty name="permisosFiltro" property="*"/>

<c:import url="/siges/gestionAdministrativa/agenda.do"/>