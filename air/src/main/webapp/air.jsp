<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>천식</title>
<div style = "text-align : center;"><H1>조회</H1></div>
</head>
<body>
	<div style = "text_align : center">
    <table style="margin: auto;" border="1">
     <thead>
	   <tr>
	    <th>ADDRESS</th>
	    <th>BASC_DT</th>
	    <th>LAT</th>
	    <th>LNG</th>
	    <th>TEMP</th>
	    <th>WEATHER</th>
	    <th>AQIUS</th>
	    <th>ASTHMA</th>
	   </tr>
	 </thead>
	 <tbody>
	   <c:forEach var="DBDO" items="${sessionScope.airlist}">
	   <tr>
	     <td>${DBDO.ADDRESS}</td>
	     <td>${DBDO.BASC_DT}</td>
	     <td>${DBDO.LAT}</td>
	     <td>${DBDO.LNG}</td>
	     <td>${DBDO.TEMP}</td>
	     <td>${DBDO.WEATHER}</td>
	     <td>${DBDO.AQIUS}</td>
	     <td>${DBDO.ASTHMA}</td>
	   </tr>
	   </c:forEach>
	 </tbody>
	 </table>
	</div>
	 
	<div style = "text-align : center">
		<button type="button" onclick="location.href='airquality.jsp'" >메인화면</button>
	</div>
</body>
</html>