 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.code}" />
<%@ include file="../common/head.jspf"%>
 
  <style>


    /* 추가적인 스타일을 여기에 추가할 수 있습니다. */
  </style>
</head>
<body>
<div style="height: 200px;"></div>
  <table class="shadow w-3/6 mt-24">
    <thead>
      <tr>
        <th class="py-3 px-6 bg-gray-100 font-bold uppercase">제목 1</th>
        <th class="py-3 px-6 bg-gray-100 font-bold uppercase">제목 2</th>
        <th class="py-3 px-6 bg-gray-100 font-bold uppercase">제목 3</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td class="py-4 px-6 border-b">내용 1-1</td>
        <td class="py-4 px-6 border-b">내용 1-2</td>
        <td class="py-4 px-6 border-b">내용 1-3</td>
      </tr>
      <tr>
        <td class="py-4 px-6 border-b bg-gray-50">내용 2-1</td>
        <td class="py-4 px-6 border-b bg-gray-50">내용 2-2</td>
        <td class="py-4 px-6 border-b bg-gray-50">내용 2-3</td>
      </tr>
      <!-- 추가적인 행들을 여기에 추가 -->
    </tbody>
  </table>
</body>