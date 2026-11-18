<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/template/header.jspf" %>

<h1>
    <c:choose>
        <c:when test="${not empty component.id}">Редактирование компонента</c:when>
        <c:otherwise>Добавление компонента</c:otherwise>
    </c:choose>
</h1>

<c:if test="${not empty error}">
    <p style="color: red; font-weight: bold;"><c:out value="${error}"/></p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/components/edit">
    <input type="hidden" name="id" value="${component.id}">

    <p>
        <label>Название:<br>
            <input type="text" name="name" value="<c:out value='${component.name}'/>" required>
        </label>
    </p>

    <p>
        <label>Категория:<br>
            <select name="categoryId" required>
                <option value="">-- Выберите категорию --</option>
                <c:forEach var="cat" items="${categories}">
                    <option value="${cat.id}"
                            <c:if test="${cat.id == component.categoryId}">selected</c:if>>
                        <c:out value="${cat.name}"/>
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
            <input type="text" name="socket" value="<c:out value='${component.socket}'/>">
        </label>
    </p>

    <p>
        <label>Тип RAM (для MB/RAM):<br>
            <input type="text" name="ramType" value="<c:out value='${component.ramType}'/>">
        </label>
    </p>

    <p>
        <label>Мощность, Вт (для CPU/GPU/PSU):<br>
            <input type="number" name="powerWatts" value="${component.powerWatts}">
        </label>
    </p>

    <p>
        <label>Имя файла картинки (например, cpu1.jpg):<br>
            <input type="text" name="imagePath" value="<c:out value='${component.imagePath}'/>">
        </label>
    </p>

    <button type="submit">Сохранить</button>
    <a href="${pageContext.request.contextPath}/components">Отмена</a>
</form>

<%@ include file="/template/footer.jspf" %>