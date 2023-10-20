<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="aprobacionActividadesSinVO" class="poa.aprobacionActividades.vo.PlaneacionVO" scope="session"/>
<jsp:setProperty name="aprobacionActividadesSinVO" property="*"/>
<jsp:useBean id="filtroAprobacionActividadesVO" class="poa.aprobacionActividades.vo.FiltroPlaneacionVO" scope="session"/>
<jsp:setProperty name="filtroAprobacionActividadesVO" property="*"/>
<c:import url="/poa/aprobacionActividades/Nuevo.do"/>