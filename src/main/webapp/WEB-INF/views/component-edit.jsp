<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>
        <c:choose>
            <c:when test="${not empty component.id}">Редактирование компонента</c:when>
            <c:otherwise>Добавление компонента</c:otherwise>
        </c:choose>
    </title>
</head>
<body>

<h1>
    <c:choose>
        <c:when test="${not empty component.id}">Редактирование компонента</c:when>
        <c:otherwise>Добавление компонента</c:otherwise>
    </c:choose>
</h1>

<c:if test="${not empty error}">
    <p style="color: red; font-weight: bold;">${error}</p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/components/edit">
    <input type="hidden" name="id" value="${component.id}">

    <p>
        <label>Название:<br>
            <input type="text" name="name" value="${component.name}" required>
        </label>
    </p>

    <p>
        <label>Категория:<br>
            <select name="categoryId" required>
                <option value="">-- Выберите категорию --</option>
                <c:forEach var="cat" items="${categories}">
                    <option value="${cat.id}"
                            <c:if test="${cat.id == component.categoryId}">selected</c:if>>
                        ${cat.name}
                    </option>
                </c:forEach>
            </select>
        </label>
    </p>

    <p>
        <label>Цена:<br>
            <input type="number" step="0.01" name="price" value="${component.price}" required>
        </label>
    </p>

    <p>
        <label>Сокет (для CPU/MB):<br>
            <input type="text" name="socket" value="${component.socket}">
        </label>
    </p>

    <p>
        <label>Тип RAM (для MB/RAM):<br>
            <input type="text" name="ramType" value="${component.ramType}">
        </label>
    </p>

    <p>
        <label>Мощность, Вт (для CPU/GPU/PSU):<br>
            <input type="number" name="powerWatts" value="${component.powerWatts}">
        </label>
    </p>

    <button type="submit">Сохранить</button>
    <a href="${pageContext.request.contextPath}/components">Отмена</a>
</form>

</body>
</html>