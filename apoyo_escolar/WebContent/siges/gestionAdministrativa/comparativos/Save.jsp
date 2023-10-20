<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroReportesVO" class="siges.gestionAdministrativa.comparativos.vo.FiltroReportesVO" scope="session"/>
<jsp:setProperty name="filtroReportesVO" property="*"/>
<c:import url="/siges/gestionAdministrativa/reportesComparativos.do"/>