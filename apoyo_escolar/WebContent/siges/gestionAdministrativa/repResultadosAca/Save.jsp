<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroRepResultadosVO" class="siges.gestionAdministrativa.repResultadosAca.vo.FiltroRepResultadosVO" scope="session"/>
<jsp:setProperty name="filtroRepResultadosVO" property="*"/>
<c:import url="/siges/gestionAdministrativa/repResultadosAca.do"/>