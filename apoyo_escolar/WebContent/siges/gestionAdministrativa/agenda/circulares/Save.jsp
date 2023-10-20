<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="siges.gestionAdministrativa.agenda.vo.ParamsVO" scope="page"/>

<jsp:useBean id="circularesAct" class="siges.gestionAdministrativa.agenda.vo.CircularVO" scope="session"/>
<jsp:setProperty name="circularesAct" property="*"/>

<jsp:useBean id="circularFiltro" class="siges.gestionAdministrativa.agenda.vo.FiltroCircularesVO" scope="session"/>
<jsp:setProperty name="circularFiltro" property="*"/>

<c:import url="/siges/gestionAdministrativa/agenda.do"/>