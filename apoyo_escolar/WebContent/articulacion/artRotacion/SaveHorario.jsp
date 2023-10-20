<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="artRotHorarioVO" class="articulacion.artRotacion.vo.HorarioVO" scope="session"/>
<jsp:useBean id="artRotFiltroHorarioVO" class="articulacion.artRotacion.vo.FiltroHorarioVO" scope="session"/>
<jsp:setProperty name="artRotHorarioVO" property="*"/>
<jsp:setProperty name="artRotFiltroHorarioVO" property="*"/>
<c:import url="/articulacion/artRotacion/Nuevo.do"/>