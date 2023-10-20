<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="indicadorLogroVO" class="siges.indicadores.vo.LogroVO" scope="session"/>
<jsp:setProperty name="indicadorLogroVO" property="*"/>
<jsp:useBean id="filtroLogroVO" class="siges.indicadores.vo.FiltroLogroVO" scope="session"/>
<jsp:setProperty name="filtroLogroVO" property="*"/>
<c:import url="/indicadores/Nuevo.do"/>