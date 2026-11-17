<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Каталог комплектующих</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/template/style.css">
</head>
<body>

<c:if test="${not empty sessionScope.error}">
    <p style="color: red; font-weight: bold;">${sessionScope.error}</p>
    <c:remove var="error" scope="session"/>
</c:if>

<c:if test="${not empty sessionScope.currentUser}">
    <div style="text-align: right;">
        Привет, <b>${sessionScope.currentUser.username}</b>!
        <a href="${pageContext.request.contextPath}/logout">Выйти из аккаунта</a>
    </div>
</c:if>

<h1>Каталог комплектующих</h1>

<c:if test="${sessionScope.currentUser.role == 'ADMIN'}">
        <p><a href="${pageContext.request.contextPath}/components/edit">Добавить компонент</a></p>
</c:if>

<p><a href="${pageContext.request.contextPath}/builds">Перейти к сборкам</a></p>

<form method="get" action="${pageContext.request.contextPath}/components">
    <label>Категория:
        <select name="categoryId">
            <option value="">Все категории</option>
            <c:forEach var="cat" items="${categories}">
                <option value="${cat.id}"
                        <c:if test="${cat.id == selectedCategoryId}">selected</c:if>>
                        ${cat.name}
                </option>
            </c:forEach>
        </select>
    </label>
    <button type="submit">Фильтр</button>
</form>

<hr>

<table border="1" cellpadding="6">
    <tr>
        <th>Название</th>
        <th>Категория</th>
        <th>Цена</th>
        <th>Сокет</th>
        <th>Тип памяти (RAM)</th>
        <th>Мощность</th>
        <c:if test="${sessionScope.currentUser.role == 'ADMIN'}">
            <th>Действия</th>
        </c:if>
    </tr>

    <c:forEach var="comp" items="${components}">
        <tr>
            <td>${comp.name}</td>
            <td>${comp.categoryId}</td>
            <td><my:price value="${comp.price}"/></td>
            <td>${comp.socket}</td>
            <td>${comp.ramType}</td>
            <td>${comp.powerWatts}</td>
            <c:if test="${sessionScope.currentUser.role == 'ADMIN'}">
                <td>
                    <a href="${pageContext.request.contextPath}/components/edit?id=${comp.id}">✏️</a>
                    <form method="post" action="${pageContext.request.contextPath}/components/delete" style="display:inline">
                        <input type="hidden" name="id" value="${comp.id}">
                        <button type="submit" onclick="return confirm('Удалить?')">🗑️</button>
                    </form>
                </td>
            </c:if>

        </tr>
    </c:forEach>
</table>

<c:if test="${empty components}">
    <p>Комплектующих нет.</p>
</c:if>

</body>
</html>