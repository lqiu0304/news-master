<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>


<rapid:override name="title">
    <title>搜索结果</title>
</rapid:override>

<rapid:override name="breadcrumb">
    <%--面包屑导航 start--%>
    <nav class="breadcrumb">
        <a class="crumbs" href="/">
            <i class="fa fa-home"></i>首页</a>
            <i class="fa fa-angle-right"></i>
        搜索 ${param.keywords} 找到 ${pageInfo.total} 个与之相关的新闻
    </nav>
    <%--面包屑导航 end--%>
</rapid:override>

<rapid:override name="left">
    <%--新闻主体 start--%>
    <section id="content" class="site-content shadow">
        <%--新闻主体-左侧正文 start--%>
        <section id="primary" class="content-area">
            <main id="main" class="site-main">
                <c:choose>
                    <c:when test="${pageInfo.list.size() != 0}">
                        <%--新闻列表-start--%>
                        <c:forEach items="${pageInfo.list}" var="n">

                            <article class="post">

                                <figure class="thumbnail">
                                    <a href="/news/${n.newsId}">
                                        <img width="280" height="210"
                                             src="${n.newsThumbnail}"
                                             class="attachment-content size-content wp-post-image"
                                             alt="${n.newsTitle}">
                                    </a>
                                    <span class="cat">
                                              <a href="/category/${n.categoryList[0].categoryId}">${n.categoryList[0].categoryName}</a>
                                        </span>
                                </figure>

                                <header class="entry-header">
                                    <h2 class="entry-title">
                                        <a href="/news/${n.newsId}" rel="bookmark">
                                                ${n.newsTitle}
                                        </a>
                                    </h2>
                                </header><!-- .entry-header -->

                                <div class="entry-content">
                                    <div class="archive-content">
                                            ${n.newsSummary}...
                                    </div>
                                    <span class="title-l"></span>
                                    <span class="new-icon">
                                                    <c:choose>
                                                        <c:when test="${n.newsStatus == 2}">
                                                            <i class="fa fa-bookmark-o"></i>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <jsp:useBean id="nowDate"
                                                                         class="java.util.Date"/> <%--当前时间--%>
                                                            <c:set var="interval"
                                                                   value="${nowDate.time - n.newsCreateTime.time}"/><%--时间差毫秒数--%>
                                                            <fmt:formatNumber value="${interval/1000/60/60/24}"
                                                                              pattern="#0"
                                                                              var="days"/><%--取天数整数--%>
                                                            <c:if test="${days <= 7}">NEW</c:if>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </span>
                                    <span class="entry-meta">
                                                    <span class="date">
                                                         <fmt:formatDate value="${n.newsCreateTime}"
                                                                         pattern="yyyy年MM月dd日"/>
                                                        &nbsp;&nbsp;
                                                    </span>
                                                    <span class="views">
                                                        <i class="fa fa-eye"></i>
                                                            ${n.newsViewCount} views
                                                    </span>
                                                    <span class="comment">&nbsp;&nbsp;
                                                        <a href="/news/${n.newsId}#comments"
                                                           rel="external nofollow">
                                                          <i class="fa fa-comment-o"></i>
                                                            <c:choose>
                                                                <c:when test="${n.newsCommentCount==0}">
                                                                    发表评论
                                                                </c:when>
                                                                <c:otherwise>
                                                                    ${n.newsCommentCount}
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </a>
                                                    </span>
                                                </span>
                                    <div class="clear"></div>
                                </div><!-- .entry-content -->
                                <span class="entry-more">
                                            <a href="/news/${n.newsId}"
                                               rel="bookmark">阅读全文
                                            </a>
                                        </span>
                            </article>
                        </c:forEach>
                        <%--新闻列表-end--%>
                    </c:when>
                </c:choose>
            </main>
            <%@ include file="../Public/part/paging.jsp" %>

        </section>
    </section>
</rapid:override>



<%@ include file="../Public/framework.jsp" %>