<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="desarrolloCurricularPEIVO" class="pei.registro.vo.DesarrolloCurricularVO" scope="session"/>
<jsp:setProperty name="desarrolloCurricularPEIVO" property="*"/>
<c:import url="/pei/registro/Nuevo.do"/>