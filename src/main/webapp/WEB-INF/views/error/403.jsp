<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/template/header.jspf" %>
<div style="text-align: center; padding: 50px;">
    <h1 style="font-size: 80px; color: #ff6b81;">403</h1>
    <h2>Доступ запрещён</h2>
    <p>У вас нет прав для просмотра этой страницы.</p>
    <a href="${pageContext.request.contextPath}/components" class="btn">На главную</a>
</div>
<%@ include file="/template/footer.jspf" %>