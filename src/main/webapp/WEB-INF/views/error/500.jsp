<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ include file="/template/header.jspf" %>
<div style="text-align: center; padding: 50px;">
    <h1 style="font-size: 80px; color: #ff6b81;">500</h1>
    <h2>Что-то пошло не так</h2>
    <p>Произошла внутренняя ошибка сервера.</p>
    <a href="${pageContext.request.contextPath}/components" class="btn">На главную</a>
</div>
<%@ include file="/template/footer.jspf" %>