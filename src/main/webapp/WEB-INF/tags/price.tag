<%@ tag body-content="empty" %>
<%@ attribute name="value" required="true" type="java.math.BigDecimal" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<fmt:formatNumber value="${value}" type="number" maxFractionDigits="2" minFractionDigits="2"/> &#8381;