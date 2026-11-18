<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/template/header.jspf" %>

<h1>Вход</h1>

<c:if test="${not empty requestScope.error}">
    <div class="alert alert-danger"><c:out value="${requestScope.error}"/></div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/login">
    <p>
        <label>Логин:
            <input type="text" name="username" required>
        </label>
    </p>
    <p>
        <label>Пароль:
            <input type="password" name="password" required>
        </label>
    </p>
    <button type="submit" class="btn">Войти</button>
</form>

<p><a href="${pageContext.request.contextPath}/register">Регистрация</a></p>

<%@ include file="/template/footer.jspf" %>