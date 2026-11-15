<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
</head>
<body>
    <h1>Регистрация</h1>
    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/register">
        <label>логин: <input type="text" name="username" required></label><br>
        <label>пароль: <input type="password" name="password" required></label><br>
        <button type="submit">Создать аккаунт</button>
    </form>
    <p><a href="${pageContext.request.contextPath}/login">Уже есть аккаунт? Войти</a></p>
</body>
</html>