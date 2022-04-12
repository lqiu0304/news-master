<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<script src="${pageContext.request.contextPath}resource/assets/js/jquery.min.js"
        crossorigin="anonymous"></script>
<link href="${pageContext.request.contextPath}resource/login/css/bootstrap.min.css"
      rel="stylesheet">
<link href="${pageContext.request.contextPath}/resource/assets/css/index_style.css"
      rel="stylesheet">

<%--后台首页内容--%>
<rapid:override name="content">
    <div class="wrapper wrapper-content">

            <%--管理员显示的页面--%>
        <c:if test="${sessionScope.user != null && sessionScope.user.userRole == 'admin'}">
            <div class="row">
                <div class="col-sm-3">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <span class="label label-success pull-right"><i class="fa fa-file-o"></i></span>
                            <h5 style="color: #00aed6">新闻总数</h5>
                        </div>
                        <div class="ibox-content">
                            <h1 class="allBlog no-margins " style="text-align: center;;font-size:30px;"><a
                                    href="/admin/news">${siteBasicStatistics[0]} 篇</a></h1>
                            <div class="allBlogPercent stat-percent font-bold text-navy"></div>
                            <small>已发表</small>
                        </div>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <span class="label label-danger pull-right"><i class="fa fa-commenting-o"></i></span>
                            <h5 style="color: #00b16a">评论总数</h5>
                        </div>
                        <div class="ibox-content">
                            <h1 class="draft no-margins" style="text-align: center;;font-size:30px;"><a
                                    href="/admin/comment">${siteBasicStatistics[1]} 条</a></h1>
                            <div class="draftPercent stat-percent font-bold text-warning">

                            </div>
                            <small>评论</small>
                        </div>
                    </div>
                </div>

                <div class="col-sm-3">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <span class="label label-warning pull-right"><i class="fa fa-folder-o"></i></span>
                            <h5 style="color: #8A2BE2">新闻类型</h5>
                        </div>
                        <div class="ibox-content">
                            <h1 class="nowBlog no-margins" style="text-align: center;;font-size:30px;"><a
                                    href="/admin/category">${siteBasicStatistics[2]} 个</a></h1>
                            <div class="nowBlogPercent stat-percent font-bold text-danger">

                            </div>
                            <small>分类</small>
                        </div>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <span class="label label-info pull-right"><i class="fa fa-tags"></i> </span>
                            <h5 style="color: #007DDB">关键字总数</h5>
                        </div>
                        <div class="ibox-content">
                            <h1 class="nowVisitors no-margins" style="text-align: center;;font-size:30px;"><a
                                    href="/admin/keyword">${siteBasicStatistics[3]} 个</a></h1>
                            <div
                                    class="nowVisitorsPercent stat-percent font-bold text-success">

                            </div>
                            <small>关键字</small>
                        </div>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <span class="label label-success pull-right"><i class="fa fa-link"></i> </span>
                            <h5 style="color: #8A2BE2">链接数量</h5>
                        </div>
                        <div class="ibox-content">
                            <h1 class="resource no-margins" style="text-align: center;;font-size:30px;"><a
                                    href="/admin/link">${siteBasicStatistics[4]} 个</a></h1>
                            <div class="resourcePercent stat-percent font-bold text-navy">
                            </div>
                            <small>链接</small>
                        </div>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <span class="label label-danger pull-right"><i class="fa fa-eye"></i> </span>
                            <h5 style="color: #63d490">浏览总量</h5>
                        </div>
                        <div class="ibox-content">
                            <h1 class="delete no-margins "
                                style="text-align: center; color: #c9302c;font-size:30px;">${siteBasicStatistics[5]} 次</h1>
                            <div class="deletePercent stat-percent font-bold text-warning">

                            </div>
                            <small>浏览</small>
                        </div>
                    </div>
                </div>

                <div class="col-sm-3">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <span class="label label-warning pull-right"><i class="fa fa-pencil-square-o"></i> </span>
                            <h5 style="color: #00b16a">最后更新</h5>
                        </div>
                        <div class="ibox-content">
                            <h1 class="yesBlog no-margins" style="text-align: center;">
                                <span style="color:#2F889A;font-size:30px;"><fmt:formatDate value="${lastUpdateNews.newsUpdateTime}"
                                                                             pattern="yyyy年MM月dd日"/> </span>
                            </h1>
                            <div class="yesBlogPercent stat-percent font-bold text-danger">
                            </div>
                            <small>更新</small>
                        </div>
                    </div>
                </div>
            </div>

        </c:if>

            <%--非管理员功能页面显示--%>
        <c:if test="${sessionScope.user != null && sessionScope.user.userRole == 'user'}">
            <div class="layui-row">
                <div class="layui-col-md6">
                    <div id="dashboard_activity" class="postbox ">
                        <div class="inside">
                            <div id="activity-widget">
                                <div id="published-posts" class="activity-block"><h3>最近发布</h3> <br>
                                    <ul>
                                        <c:forEach items="${newsList}" begin="0" end="4" step="1" var="n">
                                            <li><span><fmt:formatDate value="${n.newsCreateTime}"
                                                                      pattern="HH:mm MM月dd日"/> </span>
                                                <a href="/news/${n.newsId}"
                                                >${n.newsTitle}</a>
                                            </li>
                                        </c:forEach>

                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-col-md6">
                    <div id="dashboard_quick_press" class="postbox ">
                        <div class="inside">
                            <div id="latest-comments" class="activity-block"><h3>近期评论</h3>
                                <ul id="the-comment-list" data-wp-lists="list:comment">
                                    <c:forEach items="${commentList}" begin="0" end="4" step="1" var="c">
                                        <li class="comment   thread-even comment-item approved">

                                            <img alt="" src="${c.commentAuthorAvatar}"
                                                 class="avatar avatar-50 photo" height="50" width="50">
                                            <div class="dashboard-comment-wrap has-row-actions">
                                                <p class="comment-meta">
                                                    由<cite class="comment-author">
                                                    <a href="${c.commentAuthorUrl}"
                                                       rel="external nofollow"
                                                       class="url">${c.commentAuthorName}</a>
                                                </cite>发表在
                                                    《<a href="/news/${c.commentNewsId}">${c.news.newsTitle}</a>》
                                                </p>

                                                <blockquote><p>${c.commentContent}</p></blockquote>
                                                <p class="row-actions">|
                                                    <span class="">
                                <a data-comment-id="1268"
                                   href="/admin/comment/reply/${c.commentId}">
                                    回复
                                </a>
                                </span>
                                                    <span class=""> |
                                    <a href="/admin/comment/edit/${c.commentId}">编辑</a>
                                </span>
                                                    <span class=""> |
                                    <a href="javascript:void(0)"
                                       onclick="deleteComment(${c.commentId})">删除</a>
                                </span>
                                                </p>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
    </div>

</rapid:override>
<rapid:override name="footer-script">
</rapid:override>
<%@ include file="Public/framework.jsp" %>
