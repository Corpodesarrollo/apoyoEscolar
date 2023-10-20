<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="seguimientoVO" class="poa.seguimiento.vo.SeguimientoVO" scope="session"/>
<jsp:setProperty name="seguimientoVO" property="*"/>
<jsp:useBean id="filtroSeguimientoVO" class="poa.seguimiento.vo.FiltroSeguimientoVO" scope="session"/>
<jsp:setProperty name="filtroSeguimientoVO" property="*"/>
<c:import url="/poa/seguimiento/Nuevo.do"/>