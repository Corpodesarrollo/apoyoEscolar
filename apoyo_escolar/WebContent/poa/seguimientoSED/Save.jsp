<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="seguimientoSEDVO" class="poa.seguimientoSED.vo.SeguimientoVO" scope="session"/>
<jsp:setProperty name="seguimientoSEDVO" property="*"/>
<jsp:useBean id="filtroSeguimientoSEDVO" class="poa.seguimientoSED.vo.FiltroSeguimientoVO" scope="session"/>
<jsp:setProperty name="filtroSeguimientoSEDVO" property="*"/>
<c:import url="/poa/seguimientoSED/Nuevo.do"/>