<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="artRotFiltroRecesoVO" class="articulacion.artRotacion.vo.FiltroRecesoVO" scope="session"/>
<jsp:useBean id="artRotRecesoVO" class="articulacion.artRotacion.vo.RecesoVO" scope="session"/>
<jsp:setProperty name="artRotFiltroRecesoVO" property="*"/>
<jsp:setProperty name="artRotRecesoVO" property="*"/>
<c:import url="/articulacion/artRotacion/Nuevo.do"/>