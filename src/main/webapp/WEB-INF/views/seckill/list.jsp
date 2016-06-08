<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="../common/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="../common/head.jsp" %>
    <title>秒杀列表页</title>
</head>
<body>

<div class="container">
    <div class="panel panel-default text-center">
        <div class="panel-heading text-center">
            <h2 class="panel-title">秒杀列表</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>商品名称</th>
                    <th>商品数量</th>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>创建时间</th>
                    <th>详情页</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="seckill" items="${seckillList}" varStatus="status" step="1">
                    <tr>
                        <td>${status.index+1 }</td>
                        <td>${seckill.name}</td>
                        <td>${seckill.number}</td>
                        <td><fmt:formatDate value="${seckill.startTime}"
                                            pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td><fmt:formatDate value="${seckill.endTime}"
                                            pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td><fmt:formatDate value="${seckill.createTime}"
                                            pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td><a class="btn btn-info" href="seckill/${seckill.seckillId}/detail" target="_blank">详情页</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://cdn.bootcss.com/jquery/2.2.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="https://cdn.bootcss.com/bootstrap/4.0.0-alpha.2/js/bootstrap.min.js"></script>
</html>