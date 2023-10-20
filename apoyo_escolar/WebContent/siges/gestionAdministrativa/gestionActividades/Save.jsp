<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroGestionActividadesVO" class="siges.gestionAdministrativa.gestionActividades.vo.FiltroGestionVO" scope="session"/>
<jsp:setProperty name="filtroGestionActividadesVO" property="*"/>
<jsp:useBean id="paramsVO" class="siges.gestionAdministrativa.gestionActividades.vo.ParamsVO" scope="page"/>
<jsp:useBean id="gestionAct" class="siges.gestionAdministrativa.gestionActividades.vo.GestionActVO" scope="session"/>
<jsp:setProperty name="gestionAct" property="*"/>
<c:import url="/siges/gestionAdministrativa/gestActividades.do"/>