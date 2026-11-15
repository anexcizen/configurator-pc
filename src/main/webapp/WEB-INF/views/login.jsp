<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Вход</title>
</head>
<body>
    <h1>Вход</h1>
    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <label>Логин: <input type="text" name="username" required></label><br>
        <label>Пароль: <input type="password" name="password" required></label><br>
        <button type="submit">Войти</button>
    </form>
    <p><a href="${pageContext.request.contextPath}/register">Регистрация</a></p>
</body>
</html>