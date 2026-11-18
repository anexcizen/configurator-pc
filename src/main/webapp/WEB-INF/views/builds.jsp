<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/template/header.jspf" %>

<h1>Сборки</h1>

<div style="margin-bottom: 20px;">
    <a href="${pageContext.request.contextPath}/builds" class="btn-link">Мои сборки</a> |
    <a href="${pageContext.request.contextPath}/builds?public=true" class="btn-link">Публичные сборки</a>
</div>

<c:if test="${isPublicView}">
    <h2>Публичные сборки сообщества</h2>
</c:if>
<c:if test="${!isPublicView}">
    <h2>Создать новую сборку</h2>
    <form method="post" action="${pageContext.request.contextPath}/builds">
        <input type="hidden" name="action" value="create">

        <p>
            <label>Название:<br>
                <input type="text" name="title" required>
            </label>
        </p>

        <p>
            <label>Описание:<br>
                <input type="text" name="description">
            </label>
        </p>

        <p>
            <label>
                <input type="checkbox" name="isPublic" value="true" checked>
                Публичная сборка
            </label>
        </p>

        <button type="submit">Создать сборку</button>
    </form>
</c:if>

<hr>

<h2>Список сборок</h2>

<c:if test="${empty builds}">
    <p>У вас пока нет сборок. Создайте первую!</p>
</c:if>

<c:if test="${not empty builds}">
    <table>
        <tr>
            <th>Автор</th>
            <th>Название</th>
            <th>Описание</th>
            <th>Публичная</th>
            <th>Действия</th>
        </tr>
        <c:forEach var="b" items="${builds}">
            <tr>
                <td><c:out value="${b.username}"/></td>
                <td><c:out value="${b.title}"/></td>
                <td><c:out value="${b.description}"/></td>
                <td>
                    <c:choose>
                        <c:when test="${b.pub}">Да</c:when>
                        <c:otherwise>Нет</c:otherwise>
                    </c:choose>
                </td>
                <td class="actions">
                    <a href="${pageContext.request.contextPath}/builds?id=${b.id}" class="btn-link">Открыть</a>
                    <c:if test="${b.userId == sessionScope.currentUser.id}">
                        <form method="post" action="${pageContext.request.contextPath}/builds" class="form-delete" data-confirm="Вы уверены, что хотите удалить эту сборку?" style="display:inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="${b.id}">
                            <button type="submit" class="btn-link text-danger">Удалить</button>
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<%@ include file="/template/footer.jspf" %>