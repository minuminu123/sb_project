<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<c:set var="pageTitle" value="CAMPING" />
<%@ include file="../common/head.jspf"%>
<%@ include file="../common/toastUiEditorLib.jspf"%>
<%
List<String[]> data = (List<String[]>) request.getAttribute("data");
%>
    <meta charset="UTF-8">
    <h1>Camping List</h1>
   <div>
    <form class="flex mt-16">
        <select name="searchType" class="select select-bordered" style="width: 150px;">
            <option value="0" ${searchType == 0 ? 'selected' : ''}>주소</option>
        </select>
        <input name="searchKeyword" type="text" class="ml-2 w-96 input input-borderd" placeholder="검색어를 입력해주세요"
               maxlength="20" value="${searchKeyword}" />
        <button type="submit" class="ml-2 btn btn-ghost">검색</button>
    </form>
</div>

<h1>Beach List</h1>
<table class="table-box-type-1 table w-full mt-24" style="border-collaspe: collaspe; width: 700px;">
    <thead>
        <tr>
            <th>해수욕장 이름</th>
            <th>위치</th>
            <th>지도에서 보기</th>
        </tr>
    </thead>
    <tbody>
        <% for (String[] row : data) { %>
            <tr>
                <th><%= row[1] %></th>
                <th><%= row[6] %></th>
                <th><a href="/usr/caming/detail">지도</a></th>
            </tr>
        <% } %>
    </tbody>
</table>

<%@ include file="../common/foot.jspf"%>