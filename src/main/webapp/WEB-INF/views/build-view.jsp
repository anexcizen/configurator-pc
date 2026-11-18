<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ include file="/template/header.jspf" %>

<p><a href="${pageContext.request.contextPath}/builds">← Назад к сборкам</a></p>

<h1>${build.title}</h1>

<p><b>Описание:</b> ${build.description}</p>
<p><b>Публичная:</b>
    <c:choose>
        <c:when test="${build.pub}">Да</c:when>
        <c:otherwise>Нет</c:otherwise>
    </c:choose>
</p>

<hr>

<c:if test="${build.userId == sessionScope.currentUser.id}">
    <h2>Редактировать информацию о сборке</h2>
    <form method="post" action="${pageContext.request.contextPath}/builds">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="${build.id}">

        <p>
            <label>Название: <input type="text" name="title" value="${build.title}" required></label>
        </p>
        <p>
            <label>Описание: <input type="text" name="description" value="${build.description}"></label>
        </p>
        <p>
            <label>
                <input type="checkbox" name="isPublic" value="true" <c:if test="${build.pub}">checked</c:if>>
                Публичная сборка
            </label>
        </p>
        <button type="submit">Сохранить изменения</button>
    </form>
    <hr>
</c:if>

<h2>Компоненты в сборке</h2>
<p><b>Итого:</b> <my:price value="${totalPrice}"/></p>

<c:if test="${empty buildComponents}">
    <p>В сборке пока нет компонентов.</p>
</c:if>

<c:if test="${not empty buildComponents}">
    <div class="build-layout">
        <c:forEach var="entry" items="${groupedComponents}">
            <div class="category-block">
                <h3>${entry.key}</h3>
                <c:choose>
                    <c:when test="${not empty entry.value}">
                        <c:forEach var="comp" items="${entry.value}">
                            <div class="build-item">
                                <c:if test="${not empty comp.imagePath}">
                                    <img src="${pageContext.request.contextPath}/img/components/${comp.imagePath}"
                                         alt="${comp.name}" class="build-item-img">
                                </c:if>
                                <c:if test="${empty comp.imagePath}">
                                    <div class="build-item-img empty-img">Нет фото</div>
                                </c:if>

                                <div class="build-item-info">
                                    <p>${comp.name}</p>
                                    <b><my:price value="${comp.price}"/></b>

                                    <c:if test="${build.userId == sessionScope.currentUser.id}">
                                        <form method="post" action="${pageContext.request.contextPath}/builds" style="display:inline; background:none; padding:0; box-shadow:none; margin:0;">
                                            <input type="hidden" name="action" value="removeComponent">
                                            <input type="hidden" name="buildId" value="${build.id}">
                                            <input type="hidden" name="componentId" value="${comp.id}">
                                            <button type="submit" class="btn-link text-danger" onclick="return confirm('Убрать компонент из сборки?')">Убрать</button>
                                        </form>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p class="empty">Не выбрано</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:forEach>
    </div>
</c:if>

<hr>

<c:if test="${build.userId == sessionScope.currentUser.id}">
    <h2>Добавить компонент в сборку</h2>
    <c:if test="${empty allComponents}">
        <p>Нет доступных комплектующих. Сначала добавьте их в каталог.</p>
    </c:if>

    <c:if test="${not empty allComponents}">
        <form method="post" action="${pageContext.request.contextPath}/builds">
            <input type="hidden" name="action" value="addComponent">
            <input type="hidden" name="buildId" value="${build.id}">

            <p>
                <label>Компонент:<br>
                    <select name="componentId" required>
                        <c:forEach var="comp" items="${allComponents}">
                            <option value="${comp.id}">
                                ${comp.name} — <my:price value="${comp.price}"/>
                            </option>
                        </c:forEach>
                    </select>
                </label>
            </p>

            <button type="submit">Добавить в сборку</button>
        </form>
    </c:if>
</c:if>

<hr>
<h2>Комментарии</h2>

<c:if test="${empty comments}">
    <p>Пока нет комментариев.</p>
</c:if>

<c:forEach var="c" items="${comments}">
    <div class="comment">
        <p><b>${c.username}:</b> ${c.text}</p>
    </div>
</c:forEach>

<form method="post" action="${pageContext.request.contextPath}/comments">
    <input type="hidden" name="buildId" value="${build.id}">
    <textarea name="commentText" rows="3" required placeholder="Ваш комментарий..."></textarea>
    <button type="submit" class="btn">Оставить комментарий(удалить нельзя)</button>
</form>

<%@ include file="/template/footer.jspf" %>