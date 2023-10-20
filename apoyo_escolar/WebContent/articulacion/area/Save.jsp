<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="areaVO" class="articulacion.area.vo.AreaVO" scope="session"/>
<jsp:useBean id="filAreVO" class="articulacion.area.vo.FiltroVO" scope="session"/>
<jsp:setProperty name="areaVO" property="*"/>
<jsp:setProperty name="filAreVO" property="*"/>
<c:import url="/articulacion/area/Nuevo.do"/>