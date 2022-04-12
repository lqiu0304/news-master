<%--保留此处 start--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%--保留此处 end--%>
<rapid:override name="title">
    - 修改新闻
</rapid:override>
<rapid:override name="header-style">

</rapid:override>

<rapid:override name="content">
    <blockquote class="layui-elem-quote">
        <span class="layui-breadcrumb" lay-separator="/">
              <a href="/admin">首页</a>
            <c:if test="${news.newsStatus==2&&sessionScope.user.userRole == 'admin'}">
                <a href="/admin/news/audit">审核新闻列表</a>
                <a><cite>审核</cite></a></li>
            </c:if>
            <c:if test="${news.newsStatus!=2}">
                <a href="/admin/news">新闻列表</a>
                <a><cite>修改新闻</cite></a>
            </c:if>

        </span>
    </blockquote>


    <form class="layui-form" method="post" id="myForm" action="/admin/news/editSubmit">
        <input type="hidden" name="newsId" value="${news.newsId}">
        <div class="layui-form-item">
            <label class="layui-form-label">标题 <span style="color: #FF5722; ">*</span></label>
            <div class="layui-input-block">
                <input type="text" name="newsTitle" lay-verify="title" id="title" value="${news.newsTitle}"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" pane=""><%--关键字--%>
            <label class="layui-form-label">关键字</label>
            <div class="layui-input-block">
                <input type="text" name="newsKeywords" lay-verify="keyword" id="keywords" autocomplete="off"
                       placeholder="请输入关键字"
                       class="layui-input" value="${keywordName}">(多个关键字用空格隔开)

            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">分类 <span style="color: #FF5722; ">*</span></label>
            <div class="layui-input-inline">
                <select name="newsParentCategoryId" id="newsParentCategoryId"
                        lay-filter="newsParentCategoryId">
                    <option value="">一级分类</option>
                    <c:forEach items="${categoryList}" var="c">
                        <c:if test="${c.categoryPid == 0}">
                            <option value="${c.categoryId}"
                                    <c:if test="${news.categoryList[0].categoryId == c.categoryId}">
                                        selected
                                    </c:if>
                            >${c.categoryName}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>

            <div class="layui-input-inline">
                <select name="newsChildCategoryId" id="newsChildCategoryId" lay-filter="newsChildCategoryId">
                    <c:forEach items="${categoryList}" var="c">
                        <c:if test="${c.categoryPid == news.categoryList[0].categoryId}">
                            <option value="${c.categoryId}"
                                    <c:if test="${news.categoryList[1].categoryId == c.categoryId}">selected</c:if>>${c.categoryName}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">order</label>
            <div class="layui-input-inline">
                <input type="number" name="newsOrder" value="${news.newsOrder}" autocomplete="off"
                       class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">输入1-10的数字,order越大排序越前</div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">缩略图</label>
            <div class="layui-input-inline">
                <div class="layui-upload">
                    <div class="layui-upload-list" style="">
                        <img class="layui-upload-img" id="demo1" width="100" src="${news.newsThumbnail}"
                             height="100">
                        <p id="demoText"></p>
                    </div>
                    <button type="button" class="layui-btn" id="test1">上传图片</button>
                    <input type="hidden" name="newsThumbnail" id="newsThumbnail" value="${news.newsThumbnail}">
                </div>
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">内容 <span style="color: #FF5722; ">*</span></label>
            <div class="layui-input-block">
                <textarea class="layui-textarea layui-hide" name="newsContent"
                          id="content">${news.newsContent}</textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">状态</label>
            <div class="layui-input-block">

                <c:choose>
                    <%--管理员审核--%>
                    <c:when test="${news.newsStatus==2&&sessionScope.user != null && sessionScope.user.userRole == 'admin'}">
                        <input type="radio" name="newsStatus" value="1" title="通过"
                               <c:if test="${news.newsStatus==1}">checked</c:if>>
                        <input type="radio" name="newsStatus" value="3" title="不通过"
                               <c:if test="${news.newsStatus==3}">checked</c:if>>
                    </c:when>

                    <%--如果是管理员则直接修成功--%>
                    <c:when test="${sessionScope.user != null && sessionScope.user.userRole == 'admin'}">
                        <input type="radio" name="newsStatus" value="1" title="发布"
                               <c:if test="${news.newsStatus==1}">checked</c:if>>
                        <input type="radio" name="newsStatus" value="0" title="草稿"
                               <c:if test="${news.newsStatus==0}">checked</c:if>>
                    </c:when>

                    <%--如果是普通用户则重新默认为2  待审核状态--%>
                    <c:when test="${sessionScope.user != null && sessionScope.user.userRole == 'user'}">
                        <input type="radio" name="newsStatus" value="2" title="投稿"
                               <c:if test="${news.newsStatus==2}">checked</c:if>>
                        <input type="radio" name="newsStatus" value="4" title="草稿"
                               <c:if test="${news.newsStatus==4}">checked</c:if>>
                    </c:when>

                </c:choose>


            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit="" lay-filter="demo1">保存</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>


</rapid:override>


<rapid:override name="footer-script">

    <script>
        //上传图片
        layui.use('upload', function () {
            var $ = layui.jquery,
                upload = layui.upload;
            var uploadInst = upload.render({
                elem: '#test1',
                url: '/admin/upload/img',
                before: function (obj) {
                    obj.preview(function (index, file, result) {
                        $('#demo1').attr('src', result);
                    });
                },
                done: function (res) {
                    $("#newsThumbnail").attr("value", res.data.src);
                    if (res.code > 0) {
                        return layer.msg('上传失败');
                    }
                },
                error: function () {
                    var demoText = $('#demoText');
                    demoText.html('' +
                        '<span style="color: #FF5722;">上传失败</span>' +
                        ' <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
                    demoText.find('.demo-reload').on('click', function () {
                        uploadInst.upload();
                    });
                }
            });

        });
    </script>

    <script>


        layui.use(['form', 'layedit', 'laydate'], function () {
            var form = layui.form
                , layer = layui.layer
                , layedit = layui.layedit
                , laydate = layui.laydate;


            //上传图片,必须放在 创建一个编辑器前面
            layedit.set({
                uploadImage: {
                    url: '/admin/upload/img' //接口url
                    , type: 'post' //默认post
                }
            });


            //创建一个编辑器
            var editIndex = layedit.build('content', {
                    height: 350,
                }
            );

//            layui.code({
//                elem: 'pre', //默认值为.layui-code
//            });

            //自定义验证规则
            form.verify({
                title: function (value) {
                    if (value.length < 5) {
                        return '标题至少得5个字符啊';
                    }
                }
                , content: function (value) {
                    layedit.sync(editIndex);
                }
            });
            layedit.build('content', {
                tool: [
                    'strong' //加粗
                    , 'italic' //斜体
                    , 'underline' //下划线
                    , 'del' //删除线

                    , '|' //分割线

                    , 'left' //左对齐
                    , 'center' //居中对齐
                    , 'right' //右对齐
                    , 'link' //超链接
                    , 'unlink' //清除链接
                    , 'face' //表情
                    , 'image' //插入图片
                    , 'code'
                ]
            });


            //二级联动
            form.on("select(newsParentCategoryId)", function () {
                var str = "";
                var newsParentCategoryId = $("#newsParentCategoryId").val();
                <c:forEach items="${categoryList}" var="c">
                if (newsParentCategoryId ==${c.categoryPid}) {
                    str += "<option name='childCategory' value='${c.categoryId}'<c:if test='${news.categoryList[1].categoryId == c.categoryId}'>selected</c:if>>${c.categoryName}</option>";
                }
                </c:forEach>
                $("#newsChildCategoryId").html("  <option value=''selected>二级分类</option>" + str);
                form.render('select'); //这个很重要
            })

        });
        //end


    </script>

</rapid:override>


<%--此句必须放在最后--%>
<%@ include file="../Public/framework.jsp" %>

