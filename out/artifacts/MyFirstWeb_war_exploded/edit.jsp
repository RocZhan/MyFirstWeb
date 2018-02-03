<%--
  Created by IntelliJ IDEA.
  User: codingBoy
  Date: 16/10/23
  Time: 下午2:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri ="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3 align="center">编辑客户</h3>
<form action="<c:url value='/CustomerServlet'/>" method="post" >
    <input type="hidden" name="method" value="doEdit"/>
    <input type="hidden" name="id" value="${customer.id}"/>
    <table border="0" align="center" width="40%" style="margin-left: 100px">
        <tr>
            <td width="100px">客户姓名</td>
            <td width="40%">
                <input type="text" name="name" value="${customer.name}"/>
            </td>
            <td align="left">
                <label id="nameError" class="error">&nbsp;</label>
            </td>
        </tr>
        <tr>
            <td>年龄</td>
            <td>
                <input type="text" name="age" id="age" value="${customer.age}"/>
            </td>
            <td>
                <label id="ageError"class="error">&nbsp;</label>
            </td>
        </tr>
        <tr>
            <td>性别</td>
            <td>
                <input type="radio" name="gender" value="male" id="male"
                       <c:if test="${customer.gender eq 'male'}"/>checked="checked"/>
                <label for="male">男</label>
                <input type="radio" name="gender" value="female" id="female"
                        <c:if test="${customer.gender eq 'female'}"/> checked="checked"/>
                <label for="female">女</label>
            </td>
            <td>
                <label id="genderError"class="error">&nbsp;</label>
            </td>
        </tr>

        <tr>
            <td>购买产品</td>
            <td>
                <input type="text" name="product" id="product" value="${customer.product}"/>
            </td>
            <td>
                <label id="productError"class="error">&nbsp;</label>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" name="submit" value="编辑客户"/>
                <input type="reset" name="reset"/>
            </td>
        </tr>
    </table>
</form>


</body>
</html>
