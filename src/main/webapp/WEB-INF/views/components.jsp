<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ include file="/template/header.jspf" %>

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
                        <c:out value="${cat.name}"/>
                </option>
            </c:forEach>
        </select>
    </label>
    <button type="submit">Фильтр</button>
</form>

<hr>

<table border="1" cellpadding="6">
    <tr>
        <th>Фото</th>
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
            <td>
                <c:choose>
                    <c:when test="${not empty comp.imagePath}">
                        <img src="${pageContext.request.contextPath}/img/components/${comp.imagePath}"
                             alt="<c:out value='${comp.name}'/>" width="60" height="60" style="object-fit: contain;">
                    </c:when>
                    <c:otherwise>
                        <span style="color: #666; font-size: 12px;">Нет фото</span>
                   </c:otherwise>
                </c:choose>
            </td>
            <td><c:out value="${comp.name}"/></td>
            <td>
                <c:forEach var="cat" items="${categories}">
                    <c:if test="${cat.id == comp.categoryId}">
                        <c:out value="${cat.name}"/>
                    </c:if>
                </c:forEach>
            </td>
            <td><my:price value="${comp.price}"/></td>
            <td><c:out value="${comp.socket}"/></td>
            <td><c:out value="${comp.ramType}"/></td>
            <td>${comp.powerWatts}</td>
            <c:if test="${sessionScope.currentUser.role == 'ADMIN'}">
                <td>
                    <a href="${pageContext.request.contextPath}/components/edit?id=${comp.id}" class="icon-btn" title="Редактировать">
                        <img src="${pageContext.request.contextPath}/img/icons/edit.png" alt="Редактировать" width="20" height="20">
                    </a>
                    <form method="post" action="${pageContext.request.contextPath}/components/delete" class="form-delete" data-confirm="Удалить этот компонент?" style="display:inline;">
                        <input type="hidden" name="id" value="${comp.id}">
                        <button type="submit" class="icon-btn" title="Удалить">
                            <img src="${pageContext.request.contextPath}/img/icons/delete.png" alt="Удалить" width="20" height="20">
                        </button>
                    </form>
                </td>
            </c:if>

        </tr>
    </c:forEach>
</table>

<c:if test="${empty components}">
    <p>Комплектующих нет.</p>
</c:if>

<%@ include file="/template/footer.jspf" %>