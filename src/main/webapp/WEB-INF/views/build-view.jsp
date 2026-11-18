<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ include file="/template/header.jspf" %>

<p><a href="${pageContext.request.contextPath}/builds">← Назад к сборкам</a></p>

<h1><c:out value="${build.title}"/></h1>

<p><b>Описание:</b> <c:out value="${build.description}"/></p>
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
        <input type="hidden" name="id" value="<c:out value='${build.id}'/>">

        <p>
            <label>Название: <input type="text" name="title" value="<c:out value='${build.title}'/>" required></label>
        </p>
        <p>
            <label>Описание: <input type="text" name="description" value="<c:out value='${build.description}'/>"></label>
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

<h2>Конфигуратор сборки</h2>
<p style="font-size: 18px; margin-bottom: 20px;"><b>Итоговая стоимость:</b> <span style="color: #4ade80; font-weight: 800;"><my:price value="${totalPrice}"/></span></p>

<div class="build-layout">
    <c:forEach var="cat" items="${categories}">
        <c:set var="installedList" value="${groupedBuildComps[cat.id]}"/>
        <c:set var="availableList" value="${availableByCategory[cat.id]}"/>

        <div class="category-block">
            <h3><c:out value="${cat.name}"/></h3>

            <div class="category-items">
                <c:choose>
                    <c:when test="${not empty installedList}">
                        <c:forEach var="comp" items="${installedList}">
                            <div class="build-item">
                                <c:if test="${not empty comp.imagePath}">
                                    <img src="${pageContext.request.contextPath}/img/components/${comp.imagePath}"
                                         alt="<c:out value='${comp.name}'/>" class="build-item-img">
                                </c:if>
                                <c:if test="${empty comp.imagePath}">
                                    <div class="build-item-img empty-img">Нет фото</div>
                                </c:if>

                                <div class="build-item-info">
                                    <p><c:out value="${comp.name}"/></p>
                                    <b><my:price value="${comp.price}"/></b>

                                    <c:if test="${build.userId == sessionScope.currentUser.id}">
                                        <form method="post" class="form-delete" data-confirm="Убрать компонент из сборки?" action="${pageContext.request.contextPath}/builds/component" style="display:inline; background:none; padding:0; box-shadow:none; margin:0; border:none;">
                                            <input type="hidden" name="action" value="remove">
                                            <input type="hidden" name="buildId" value="${build.id}">
                                            <input type="hidden" name="componentId" value="${comp.id}">
                                            <button class="btn-link text-danger" style="padding-left: 0 !important;">Убрать</button>
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

            <c:if test="${build.userId == sessionScope.currentUser.id}">
                <div class="category-add-section">
                    <c:choose>
                        <c:when test="${not empty availableList}">
                            <button type="button" class="btn-toggle-add">+ Добавить</button>

                            <form method="post" action="${pageContext.request.contextPath}/builds/component" class="category-add-form" style="display: none;">
                                <input type="hidden" name="action" value="add">
                                <input type="hidden" name="buildId" value="${build.id}">

                                <input type="text" class="component-search" placeholder="Поиск по названию..." autocomplete="off">

                                <select name="componentId" class="component-select" required>
                                    <c:forEach var="availComp" items="${availableList}">
                                        <option value="${availComp.id}">
                                            <c:out value="${availComp.name}"/> — <my:price value="${availComp.price}"/>
                                        </option>
                                    </c:forEach>
                                </select>

                                <div class="category-form-actions">
                                    <button type="submit" class="btn btn-sm">Выбрать</button>
                                    <button type="button" class="btn-cancel-add">Отмена</button>
                                </div>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <p class="empty" style="font-size: 12px; margin-top: 10px;">Нет компонентов в каталоге</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>
        </div>
    </c:forEach>
</div>

<hr>
<h2>Комментарии</h2>

<c:if test="${empty comments}">
    <p>Пока нет комментариев.</p>
</c:if>

<c:forEach var="c" items="${comments}">
    <div class="comment">
        <p><b><c:out value="${c.username}"/>:</b> <c:out value="${c.text}"/></p>
    </div>
</c:forEach>

<form method="post" action="${pageContext.request.contextPath}/comments">
    <input type="hidden" name="buildId" value="${build.id}">
    <textarea name="commentText" rows="3" required placeholder="Ваш комментарий..."></textarea>
    <button type="submit" class="btn">Отправить</button>
</form>

<%@ include file="/template/footer.jspf" %>