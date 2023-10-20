<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="filtroRecuperacionVO" class="siges.recuperacion.vo.FiltroRecuperacionVO" scope="session"/>
<jsp:setProperty name="filtroRecuperacionVO" property="*"/>
<jsp:useBean id="recuperacionVO" class="siges.recuperacion.vo.RecuperacionVO" scope="session"/>
<jsp:setProperty name="recuperacionVO" property="*"/>
<c:import url="/recuperacion/Nuevo.do"/>