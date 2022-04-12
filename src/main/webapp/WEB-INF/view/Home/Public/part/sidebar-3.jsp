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



</div>
<%--新闻主体-右侧侧边栏 end--%>