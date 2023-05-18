<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="API Test4" />

<%@ include file="../common/head.jspf"%>



<div id="beach-container"></div>

<script>
  const API_KEY = 'VrARjVnAz0YAUHJLVqj8GZ7XpxHHlvVUKCE1vRY0pWvbSavB0Pl0zPRI1waC0B3ZsJm5N%2FTlAhDP06JVqUokdw%3D%3D';

  async function getData() {
    const url = 'http://apis.data.go.kr/1360000/BeachInfoservice/getWhBuoyBeach' + API_KEY;
    const response = await fetch(url);
    const data = await response.json();
	
  }

  getData();
</script>
