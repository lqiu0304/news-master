<%--
    一般用于正文侧边栏：
    包括 搜索，热评新闻，所有关键字，随机新闻 等小工具
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%--新闻主体-右侧侧边栏 start--%>
<div id="sidebar" class="widget-area all-sidebar"
     style="position: relative; overflow: visible; box-sizing: border-box; min-height: 1px;">

        <%--搜索框--%>
        <aside class="widget widget_search">
            <div class="searchbar">
                <form method="get" id="searchform1" action="/search">
                    <span> <input type="text" value="" name="keywords" id="s1" placeholder="输入搜索内容" required="">
                        <button type="submit" id="searchsubmit1">搜索</button>
                    </span>
                </form>
            </div>
            <div class="clear"></div>
        </aside>
        <%--搜索框--%>

        <%--热评新闻 start--%>
        <aside class="widget hot_comment" >
            <h3 class="widget-title">
                <i class="fa fa-bars"></i>热评新闻
            </h3>
            <div id="hot_comment_widget">
                <ul>
                    <c:forEach items="${mostCommentNewsList}" var="m">
                        <li>
                            <a href="/news/${m.newsId}" rel="bookmark" title=" (${m.newsCommentCount}条评论)">
                                    ${m.newsTitle}
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="clear"></div>
        </aside>
        <%--热评新闻 end--%>

        <%--所有关键字 start--%>
        <aside class="widget">
            <h3 class="widget-title">
                <i class="fa fa-bars"></i>所有关键字
            </h3>
            <div class="keywordcloud">
                <c:forEach items="${allKeywordList}" var="t">
                    <a href="/keyword/${t.keywordId}"
                       class="keyword-link-129 keyword-link-position-1"
                       style="font-size: 14px;">
                            ${t.keywordName}
                    </a>
                </c:forEach>
                <div class="clear"></div>
            </div>
            <div class="clear"></div>
        </aside>
        <%--所有关键字 end--%>

        <%--新闻主体-右侧侧边栏-随机新闻 start--%>
        <aside id="random_post-7" class="widget random_post wow fadeInUp" data-wow-delay="0.3s">
            <h3 class="widget-title">
                <i class="fa fa-bars"></i>随机新闻
            </h3>
            <div id="random_post_widget">
                <ul>
                    <c:forEach items="${randomNewsList}" var="r">
                        <li>
                            <a href="/news/${r.newsId}" rel="bookmark">
                                    ${r.newsTitle}
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="clear"></div>
        </aside>
        <%--新闻主体-右侧侧边栏-近期新闻 end--%>

</div>
<%--新闻主体-右侧侧边栏 end--%>