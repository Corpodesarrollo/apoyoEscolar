<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="paramsVO" class="siges.gestionAdministrativa.agenda.vo.ParamsVO" scope="page"/>

<jsp:useBean id="tareasAct" class="siges.gestionAdministrativa.agenda.vo.TareasVO" scope="session"/>
<jsp:setProperty name="tareasAct" property="*"/>



<c:import url="/siges/gestionAdministrativa/agenda.do"/>