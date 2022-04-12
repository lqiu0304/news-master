<%--保留此处 start--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%--保留此处 end--%>
<rapid:override name="title">
    发布新闻
</rapid:override>

<rapid:override name="content">
    <%--导航栏区域--%>
    <blockquote class="layui-elem-quote">
         <span class="layui-breadcrumb" lay-separator="/">
              <a href="/admin">首页</a>
              <a href="/admin/news">新闻列表</a>
              <a><cite>添加新闻</cite></a>
        </span>
    </blockquote>


    <form class="layui-form" method="post" id="myForm" action="/admin/news/insertSubmit">

        <div class="layui-form-item"><%--标题--%>
            <label class="layui-form-label">标题 <span style="color: #FF5722; ">*</span></label>
            <div class="layui-input-block">
                <input type="text" name="newsTitle" lay-verify="title" id="title" autocomplete="off"
                       placeholder="请输入标题(5个字以上)"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item" pane=""><%--关键字--%>
            <label class="layui-form-label">关键字</label>
            <div class="layui-input-block">
                <input type="text" name="newsKeywords" lay-verify="keyword" id="keywords" autocomplete="off"
                       placeholder="请输入关键字"
                       class="layui-input">(多个关键字用空格隔开)

            </div>
        </div>


        <div class="layui-form-item"><%--分类--%>
            <label class="layui-form-label">分类 <span style="color: #FF5722; ">*</span> </label>
            <div class="layui-input-inline">
                <select name="newsParentCategoryId" id="newsParentCategoryId" lay-filter="newsParentCategoryId"
                        required>
                    <option value="" selected="">一级分类</option>
                    <c:forEach items="${categoryList}" var="c">
                        <c:if test="${c.categoryPid==0}">
                            <option value="${c.categoryId}">${c.categoryName}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
            <div class="layui-input-inline">
                <select name="newsChildCategoryId" id="newsChildCategoryId">
                    <option value="" selected>二级分类</option>
                </select>
            </div>
        </div>


        <div class="layui-form-item"><%--封面--%>
            <label class="layui-form-label">封面</label>
            <div class="layui-input-inline">
                <div class="layui-upload">
                    <div class="layui-upload-list" style="">
                        <img class="layui-upload-img" id="demo1" width="100"
                             height="100">
                        <p id="demoText"></p>
                    </div>
                    <button type="button" class="layui-btn" id="test1">上传图片</button>
                    <input type="hidden" name="newsThumbnail" id="newsThumbnail">
                </div>
            </div>
        </div>


        <div class="layui-form-item layui-form-text"><%--内容--%>
            <label class="layui-form-label">内容 <span style="color: #FF5722; ">*</span></label>
            <div class="layui-input-block">
                <textarea class="layui-textarea layui-hide" name="newsContent" lay-verify="content"
                          id="content"></textarea>
            </div>
        </div>

        <div class="layui-form-item"><%--发布状态--%>
            <label class="layui-form-label">状态</label>
            <div class="layui-input-block">

                    <%--如果是管理员则直接发布--%>
                <c:if test="${sessionScope.user != null && sessionScope.user.userRole == 'admin'}">
                    <input type="radio" name="newsStatus" value="1" title="发布" checked>
                    <input type="radio" name="newsStatus" value="0" title="草稿">
                </c:if>

                    <%--如果是普通用户则默认为2  待审核状态--%>
                <c:if test="${sessionScope.user != null && sessionScope.user.userRole == 'user'}">
                    <input type="radio" name="newsStatus" value="2" title="投稿" checked>
                    <input type="radio" name="newsStatus" value="4" title="草稿">
                </c:if>

            </div>
        </div>


        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit="" lay-filter="demo1">立即发布</button>
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

            //自定义验证规则
            form.verify({
                title: function (value) {
                    if (value.length < 5) {
                        return '标题至少得5个字符';
                    }
                }
                , pass: [/(.+){6,12}$/, '密码必须6到12位']
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

            layui.use('code', function () { //加载code模块
                layui.code();
            });

            //二级联动
            form.on("select(newsParentCategoryId)", function () {
                var optionstring = "";
                var newsParentCategoryId = $("#newsParentCategoryId").val();
                <c:forEach items="${categoryList}" var="c">
                if (newsParentCategoryId ==${c.categoryPid}) {
                    optionstring += "<option name='childCategory' value='${c.categoryId}'>${c.categoryName}</option>";
                }
                </c:forEach>
                $("#newsChildCategoryId").html("<option value=''selected>二级分类</option>" + optionstring);
                form.render('select'); //这个很重要
            })

        });
        //        window.onbeforeunload = function() {
        //            return "确认离开当前页面吗？未保存的数据将会丢失";
        //        }


    </script>

</rapid:override>


<%--此句必须放在最后--%>
<%@ include file="../Public/framework.jsp" %>

