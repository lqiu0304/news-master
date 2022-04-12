<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<rapid:override name="title">
    新闻列表
</rapid:override>
<rapid:override name="header-style">
    <style>
        /*覆盖 layui*/
        .layui-input {
            display: inline-block;
            width: 33.333% !important;
        }

        .layui-input-block {
            margin: 0px 10px;
        }
    </style>

</rapid:override>

<rapid:override name="content">
    <blockquote class="layui-elem-quote">
        <span class="layui-breadcrumb" lay-separator="/">
          <a href="/admin">首页</a>
          <a><cite>新闻列表</cite></a>

        </span>
        <a href="/admin/news?status=1" class="layui-btn layui-btn-mini">已发布</a>
        <a href="/admin/news?status=2" class="layui-btn layui-btn-danger layui-btn-mini">待审核</a>
        <c:if test="${sessionScope.user != null && sessionScope.user.userRole == 'admin'}">
            <a href="/admin/news?status=0" class="layui-btn layui-btn-mini">草稿箱</a>
        </c:if>
        <c:if test="${sessionScope.user != null && sessionScope.user.userRole == 'user'}">
            <a href="/admin/news?status=4" class="layui-btn layui-btn-mini">草稿箱</a>
        </c:if>
        <a href="/admin/news?status=3" class="layui-btn layui-btn-danger layui-btn-mini">审核不通过</a>
    </blockquote>


    <div class="layui-tab layui-tab-card">
        <form id="newsForm" method="post">
            <input type="hidden" name="currentUrl" id="currentUrl" value="">
            <table class="layui-table">
                <colgroup>
                    <col width="100">
                    <col width="300">
                    <col width="150">
                    <col width="100">
                    <col width="150">
                    <col width="100">
                    <col width="50">
                </colgroup>
                <thead>
                <tr>
                    <th>用户</th>
                    <th>标题</th>
                    <th>所属分类</th>
                    <th>状态</th>
                    <th>发布时间</th>
                    <th>操作</th>
                    <th>id</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${pageInfo.list}" var="n">
                    <tr>
                        <td><%--用户名称--%>
                            <a title="${n.user.userName}" href="/admin/user/edit/${n.newsUserId}"
                               target="_blank">${n.user.userNickname}</a>
                        </td>

                        <td><%--标题--%>
                            <c:choose>
                                <c:when test="${n.newsStatus==1}">

                                    <a href="/news/${n.newsId}"> ${n.newsTitle} </a>
                                </c:when>
                                <c:otherwise>

                                    <a>${n.newsTitle}</a>

                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td><%--新闻分类--%>
                            <c:forEach items="${n.categoryList}" var="c">
                                <a href="/category/${c.categoryId}">${c.categoryName}</a>
                                &nbsp;
                            </c:forEach>
                        </td>


                        <td><%--新闻状态--%>
                            <c:choose>
                                <c:when test="${n.newsStatus == 1}">
                                    <a href="/admin/news?status=1">
                                        <span style="color:#5FB878;">已发布</span>
                                    </a>
                                </c:when>
                                <c:when test="${n.newsStatus == 2}">
                                    <a href="/admin/news?status=2">
                                        <span style="color:#c9302c;">待审核</span>
                                    </a>
                                </c:when>
                                <c:when test="${n.newsStatus == 3}">
                                    <a href="/admin/news?status=3">
                                        <span style="color:#c9302c;">审核不通过</span>
                                    </a>
                                </c:when>
                                <c:when test="${n.newsStatus == 4}">
                                    <a href="/admin/news?status=4">
                                        <span style="color:#c9302c;">草稿</span>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="/admin/news?status=0">
                                        <span style="color:#FF5722;">草稿</span>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </td>


                        <td><%--发布时间--%>
                            <fmt:formatDate value="${n.newsCreateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>

                        <td><%--操作--%>
                            <c:choose>
                                <%--审核按钮：如果是管理员并且状态是2待审核--%>
                                <c:when test="${n.newsStatus==2 && sessionScope.user != null && sessionScope.user.userRole == 'admin'}">
                                    <a href="/admin/news/edit/${n.newsId}"
                                       class="layui-btn layui-btn-mini">审核</a>
                                    <a href="javascript:void(0)"
                                       onclick="deleteNews(${n.newsId})"
                                       class="layui-btn layui-btn-danger layui-btn-mini">删除</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="/admin/news/edit/${n.newsId}"
                                       class="layui-btn layui-btn-mini">编辑</a>
                                    <a href="javascript:void(0)"
                                       onclick="deleteNews(${n.newsId})"
                                       class="layui-btn layui-btn-danger layui-btn-mini">删除</a>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td>${n.newsId}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </form>
            <%--分页--%>
        <%@ include file="../Public/paging.jsp" %>
    </div>

    <%--普通用户显示自己的新闻列表--%>
    <c:if test="${sessionScope.user != null && sessionScope.user.userRole == 'user'}">
    </c:if>

</rapid:override>
<rapid:override name="footer-script">
    <script type="text/javascript">
        function deleteNews(id) {
            if (confirmDelete() == true) {
                $.ajax({
                    async: false,
                    type: "POST",
                    url: '/admin/news/delete/' + id,
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    dataType: "text",
                    complete: function () {
                        window.location.reload();
                    }
                })
            }
        }
    </script>
</rapid:override>
<%@ include file="../Public/framework.jsp" %>
