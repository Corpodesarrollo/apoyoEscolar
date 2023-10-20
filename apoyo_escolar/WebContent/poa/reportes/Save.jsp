<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroReportesPOA" class="poa.reportes.vo.FiltroReportesVO" scope="session"/>
<jsp:setProperty name="filtroReportesPOA" property="*"/>
<c:import url="/poa/reportes/Nuevo.do"/>