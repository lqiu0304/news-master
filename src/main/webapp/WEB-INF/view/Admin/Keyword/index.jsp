<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>

<rapid:override name="title">
    关键字列表
</rapid:override>
<rapid:override name="header-style">
    <style>
        /*覆盖 layui*/
        .layui-input-block {
            margin: 0px 10px;
        }

        .layui-table {
            margin-top: 0;
        }

        .layui-col-md4 {
            padding: 10px;
        }

        .layui-col-md8 {
            padding: 10px;
        }

        .layui-btn {
            margin: 2px 0 !important;
        }
    </style>
</rapid:override>

<rapid:override name="content">

    <blockquote class="layui-elem-quote">
        <span class="layui-breadcrumb" lay-separator="/">
              <a href="/admin">首页</a>
              <a><cite>关键字列表</cite></a>
        </span>
    </blockquote>
    <div class="layui-row">
        <%--<div class="layui-col-md4">
            <form class="layui-form" method="post" id="myForm" action="/admin/keyword/insertSubmit">
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <strong>添加关键字</strong>
                    </div>
                    <div class="layui-input-block">
                        名称 <span style="color: #FF5722; ">*</span>
                        <input type="text" name="keywordName" placeholder="请输入关键字名称" autocomplete="off"
                               class="layui-input" required>
                    </div>
                    <br>
                    <div class="layui-input-block">
                        关键字描述
                        <input type="text" name="keywordDescription" placeholder="请输入关键字描述" autocomplete="off"
                               class="layui-input">
                    </div>
                    <br>
                    <div class="layui-input-block">
                        <button class="layui-btn" lay-filter="formDemo" type="submit">添加</button>
                    </div>
                </div>
            </form>
            <blockquote class="layui-elem-quote layui-quote-nm">
                温馨提示：
                <ul>
                    <li>1、关键字名必选，建议不要太长</li>
                    <li>2、关键字名勿重复</li>
                </ul>
            </blockquote>
        </div>--%>
        <div class="layui-col-md8">

            <table class="layui-table">
                <colgroup>
                    <col width="300">
                    <col width="50">
                    <col width="100">
                    <col width="50">
                </colgroup>
                <thead>
                <tr>
                    <th>名称</th>
                    <th>新闻数</th>
                    <th>操作</th>
                    <th>ID</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${keywordList}" var="c">

                    <tr>
                        <td>
                            <a href="/keyword/${c.keywordId}" target="_blank">${c.keywordName}</a>
                        </td>
                        <td>
                            <a href="/keyword/${c.keywordId}" target="_blank" lay-data="{sort:true}">${c.newsCount}</a>
                        </td>
                        <td>
                            <a href="/admin/keyword/edit/${c.keywordId}" class="layui-btn layui-btn-mini">编辑</a>
                            <c:if test="${c.newsCount==0}">
                                <a href="/admin/keyword/delete/${c.keywordId}"
                                   class="layui-btn layui-btn-danger layui-btn-mini"
                                   onclick="return confirmDelete()">删除</a>
                            </c:if>

                        </td>
                        <td>${c.keywordId}</td>
                    </tr>

                </c:forEach>
                </tbody>
            </table>
            <blockquote class="layui-elem-quote layui-quote-nm">
                温馨提示：
                <ul>
                    <li>如果该关键字包含新闻，将不可删除</li>
                </ul>
            </blockquote>
        </div>
    </div>


</rapid:override>
<rapid:override name="footer-script">
    <script>

    </script>
</rapid:override>

<%@ include file="../Public/framework.jsp" %>
