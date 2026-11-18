<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/template/header.jspf" %>
<div style="text-align: center; padding: 50px;">
    <h1 style="font-size: 80px; color: #5ce1e6;">404</h1>
    <h2>Страница не найдена</h2>
    <p>Возможно, вы ошиблись в адресе.</p>
    <a href="${pageContext.request.contextPath}/components" class="btn">На главную</a>
</div>
<%@ include file="/template/footer.jspf" %>