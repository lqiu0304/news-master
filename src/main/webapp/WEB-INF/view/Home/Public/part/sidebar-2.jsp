<%--
    一般用于首页侧边栏：
    包括 关于本站，网站概况，热评新闻，所有关键字，随机新闻 等小工具

--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%--新闻主体-右侧侧边栏 start--%>
<div id="sidebar" class="widget-area all-sidebar"
     style="position: relative; overflow: visible; box-sizing: border-box; min-height: 1px;">

    <%--关于本站 start--%>
    <aside class="widget about">
        <h3 class="widget-title">
            <i class="fa fa-bars"></i>天气提醒
        </h3>
        <div id="feed_widget">
            <div class="feed-about">
                <div class="about-main">
                    <iframe width="250" scrolling="no" height="90" frameborder="0" allowtransparency="true"
                            src="https://i.tianqi.com?c=code&id=7&bgc=%23FFFFFF&bdc=%23&icon=1&py=huangshi&site=12"></iframe>
                </div>
                <div class="clear"></div>
            </div>
        </div>
        <div class="clear"></div>
    </aside>
    <%--关于本站 start--%>

    <%--网站概况 start--%>
<%--    <aside id="php_text-22" class="widget php_text">
        <h3 class="widget-title">
            <i class="fa fa-bars"></i>网站概况
        </h3>
        <div class="textwidget widget-text">
            <ul class="site-profile">
                <li><i class="fa fa-file-o"></i> 新闻总数：${siteBasicStatistics[0]} 篇</li>
                <li><i class="fa fa-commenting-o"></i> 留言数量：${siteBasicStatistics[1]} 条</li>
                <li><i class="fa fa-folder-o"></i> 分类数量：${siteBasicStatistics[2]} 个</li>
                <li><i class="fa fa-tags"></i> 关键字总数：${siteBasicStatistics[3]} 个</li>
                <li><i class="fa fa-link"></i> 链接数量：${siteBasicStatistics[4]} 个</li>
                <li><i class="fa fa-eye"></i> 浏览总量：${siteBasicStatistics[5]} 次</li>
                <li><i class="fa fa-pencil-square-o"></i> 最后更新：
                    <span style="color:#2F889A">
                                        <fmt:formatDate value="${lastUpdateNews.newsUpdateTime}" pattern="yyyy年MM月dd日"/>

                                   </span>
                </li>
            </ul>
        </div>
        <div class="clear"></div>
    </aside>--%>
    <%--网站概况 end--%>

    <%--所有关键字 start--%>
    <aside class="widget">
        <h3 class="widget-title">
            <i class="fa fa-bars"></i>所有关键字
        </h3>
        <div class="tagcloud">
            <c:forEach items="${allKeywordList}" var="keyword">
                <a href="/keyword/${keyword.keywordId}"
                   class="tag-link-129 tag-link-position-1"
                   style="font-size: 14px;">
                        ${keyword.keywordName}
                </a>
            </c:forEach>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </aside>
    <%--所有关键字 end--%>


    <%--最新评论 start--%>
    <aside id="recent_comments-2" class="widget recent_comments wow fadeInUp" data-wow-delay="0.3s"><h3
            class="widget-title"><i class="fa fa-bars"></i>近期评论</h3>
        <div id="message" class="message-widget">
            <ul>
                <c:forEach items="${recentCommentList}" var="r">
                    <li style="border: none;">
                        <a href="/news/${r.commentNewsId}/#anchor-comment-${r.commentId}" rel="external nofollow">
                                <%--<img alt="" src="${r.commentAuthorAvatar}" class="avatar avatar-64 photo" height="64" width="64">--%>
                            <span class="comment_author">
                            <strong>${r.commentAuthorName}</strong>
                        </span>
                                ${r.commentContent}
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="clear"></div>
    </aside>
    <%--最新评论 end--%>

</div>


<%--新闻主体-右侧侧边栏 end--%>
