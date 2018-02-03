<%--
  Created by IntelliJ IDEA.
  User: codingBoy
  Date: 16/10/23
  Time: 下午12:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>客户列表</title>
</head>
<body>
    <h3 align="center" >客户列表</h3>
    <table border="1" width="70%" align="center">
        <tr>
            <th>客户姓名</th>
            <th>年龄</th>
            <th>性别</th>
            <th>产品</th>
            <%--<th>描述</th>--%>
            <%--<th>操作</th>--%>
        </tr>
        <c:forEach items="${pageBean.beanList}" var="cstm">
        <tr>
            <td>${cstm.name}</td>
            <td>${cstm.age}</td>
            <td>${cstm.gender}</td>
            <td>${cstm.product}</td>
            <%--<td>${cstm.description}</td>--%>
            <td>
                <a href="<c:url value='/CustomerServlet?method=doPreEdit&id=${cstm.id}'/> ">编辑</a>
                <a href="<c:url value='/CustomerServlet?method=doDelete&id=${cstm.id}&name=${cstm.name}'/> ">删除</a>
            </td>
        </tr>
        </c:forEach>
    </table>
<br/>
<center>
    第${pageBean.pageNum}页/共${pageBean.totalPage}页
    <a href="${pageBean.url}&pageNum=1">首页</a>
    <c:if test="${pageBean.pageNum>1}">
        <a href="${pageBean.url}&pageNum=${pageBean.pageNum-1}">上一页</a>
    </c:if>

                <c:choose>
                    <c:when test="${pageBean.totalPage<=10}">
                        <c:set var="begin" value="1"/>
                        <c:set var="end" value="${pageBean.totalPage}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="begin" value="${pageBean.totalPage-5}"/>
                        <c:set var="end" value="${pageBean.totalPage+4}"/>
                        <%--头溢出--%>
                        <c:if test="${begin<1}">
                            <c:set var="begin" value="1"/>
                            <c:set var="end" value="10"/>
            </c:if>
            <%--尾溢出--%>
            <c:if test="${end>pageBean.totalPage}">
                <c:set var="end" value="${pageBean.totalPage}"/>
                <c:set var="begin" value="${pageBean.totalPage-9}"/>
            </c:if>
        </c:otherwise>
    </c:choose>

    <%--循环遍历页码列表--%>
    <c:forEach var="i" begin="${begin}" end="${end}">
        <c:choose>
            <c:when test="${i eq pageBean.pageNum}">
                [${i}]
            </c:when>
            <c:otherwise>
                <a href="${pageBean.url}&pc=${i}">[${i}]</a>
            </c:otherwise>
        </c:choose>

    </c:forEach>


    <c:if test="${pageBean.pageNum<pageBean.totalPage}">
    <a href="${pageBean.url}&pc=${pageBean.pageNum+1}">下一页</a>
    </c:if>
    <a href="${pageBean.url}&pc=${pageBean.totalPage}">尾页</a>

</center>

</body>
</html>
