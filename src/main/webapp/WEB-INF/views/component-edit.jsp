<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/template/header.jspf" %>

<h1>
    <c:choose>
        <c:when test="${not empty component and component.id > 0}">Редактирование компонента</c:when>
        <c:otherwise>Добавление компонента</c:otherwise>
    </c:choose>
</h1>

<c:if test="${not empty error}">
    <div class="alert alert-danger"><c:out value="${error}"/></div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/components/edit">
    <input type="hidden" name="id" value="${component.id > 0 ? component.id : ''}">

    <p>
        <label>Название:<br>
            <input type="text" name="name" value="<c:out value='${component.name}'/>" required>
        </label>
    </p>

    <p>
        <label>Категория:<br>
            <select name="categoryId" id="categorySelect" required>
                <option value=""> Выберите категорию </option>
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
        <label>Имя файла картинки (необязательно):<br>
            <input type="text" name="imagePath" value="<c:out value='${component.imagePath}'/>" placeholder="например, rtx4070.png">
        </label>
    </p>

    <p id="socketField">
        <label>Сокет (для CPU/MB):<br>
            <input type="text" name="socket" value="<c:out value='${component.socket}'/>">
        </label>
    </p>

    <p id="ramTypeField">
        <label>Тип RAM (для MB/RAM):<br>
            <input type="text" name="ramType" value="<c:out value='${component.ramType}'/>">
        </label>
    </p>

    <p id="powerWattsField">
        <label>Мощность, Вт (для CPU/GPU/PSU):<br>
            <input type="number" name="powerWatts" value="${component.powerWatts}">
        </label>
    </p>

    <button type="submit" class="btn">Сохранить</button>
    <a href="${pageContext.request.contextPath}/components">Отмена</a>
</form>

<%@ include file="/template/footer.jspf" %>