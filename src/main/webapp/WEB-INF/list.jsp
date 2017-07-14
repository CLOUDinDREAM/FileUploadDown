<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
  <head>
  <jsp:include page="/WEB-INF/common/top.jsp"></jsp:include>
    <title>文件列表</title>
  </head>
  
  <body>
	<table border="1" align ="center">
		<tr>
			<th>序号</th>
			<th>文件名</th>
			<th>操作</th>
		</tr>
		<c:forEach var="en" items="${requestScope.fileNames}" varStatus = "vs">
			<tr>
				<th>${vs.count}</th>
				<th>${en.value}</th>
				<th>
					<%--<a href="${pageContext.request.contextPath }/fileServlet?method=down&..">下载</a>--%>
                    <!-- 构建一个地址  -->
                    <c:url var="url" value="downServlet">
                    	<c:param name="fileName" value="${en.key}"></c:param>
                    </c:url>
                    <a href="${url}">下载</a>
				</th>
			</tr>
		</c:forEach>
	</table>
    
    </body>
</html>
