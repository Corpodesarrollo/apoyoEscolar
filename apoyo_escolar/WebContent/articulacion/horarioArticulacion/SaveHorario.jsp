<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="horArtVO" class="articulacion.horarioArticulacion.vo.HorarioVO" scope="session"/>
<jsp:useBean id="filtrohorArtVO" class="articulacion.horarioArticulacion.vo.FiltroHorarioVO" scope="session"/>
<jsp:setProperty name="horArtVO" property="*"/>
<jsp:setProperty name="filtrohorArtVO" property="*"/>
<c:import url="/articulacion/horarioArticulacion/Nuevo.do"/>