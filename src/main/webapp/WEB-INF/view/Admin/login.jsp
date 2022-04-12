<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html>
<!--[if IE 8]>
<html xmlns="http://www.w3.org/1999/xhtml" class="ie8" lang="zh-CN">
<![endif]-->
<!--[if !(IE 8) ]><!-->
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<!--<![endif]-->
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${options.optionSiteTitle}登录</title>
    <%--   <link rel="stylesheet" href="/plugin/font-awesome/css/font-awesome.min.css">
       <link rel="shortcut icon" href="/img/logo.png">
       <link rel='stylesheet' id='dashicons-css' href='/plugin/login/dashicons.min.css' type='text/css' media='all'/>
       <link rel='stylesheet' id='buttons-css' href='/plugin/login/buttons.min.css' type='text/css' media='all'/>
       <link rel='stylesheet' id='forms-css' href='/plugin/login/forms.min.css' type='text/css' media='all'/>
       <link rel='stylesheet' id='l10n-css' href='/plugin/login/l10n.min.css' type='text/css' media='all'/>
       <link rel='stylesheet' id='login-css' href='/plugin/login/login.min.css' type='text/css' media='all'/>--%>


    <link href="${pageContext.request.contextPath}/resource/login/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resource/login/css/a3common.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/login/css/login.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/login/css/font_43459_lbtux0zjkr6yldi.css">

    <%-- <style type="text/css">
         body {
             font-family: "Microsoft YaHei", Helvetica, Arial, Lucida Grande, Tahoma, sans-serif;
             background: url(/img/loginBg.jpg);
             width: 100%;
             height: 100%;
         }

         .login h1 a {
             background-size: 220px 50px;
             width: 220px;
             height: 50px;
             padding: 0;
             margin: 0 auto 1em;
         }

         .login form {
             background: #fff;
             background: rgba(255, 255, 255, 0.6);
             border-radius: 2px;
             border: 1px solid #fff;
         }

         .login label {
             color: #000;
             font-weight: bold;
         }

         #backtoblog a, #nav a {
             color: #fff !important;
         }

     </style>--%>
    <meta name='robots' content='noindex,follow'/>
    <meta name="viewport" content="width=device-width"/>
    <%--
       <style>
           body {
               background-repeat: no-repeat;
               background-size: 100% 100%;
               background-attachment: fixed;
           }
       </style>--%>
</head>
<body class="login login-action-login wp-core-ui  locale-zh-cn">

<div id="main" class="main-warp">
    <div class="main-content">

        <div class="formDiv">
            <h1><a href="/" title="欢迎您光临本网站！" tabindex="-1">${options.optionSiteTitle}</a></h1>
            <%
                String username = "";
                String password = "";
                //获取当前站点的所有Cookie
                Cookie[] cookies = request.getCookies();
                for (int i = 0; i < cookies.length; i++) {//对cookies中的数据进行遍历，找到用户名、密码的数据
                    if ("username".equals(cookies[i].getName())) {
                        username = cookies[i].getValue();
                    } else if ("password".equals(cookies[i].getName())) {
                        password = cookies[i].getValue();
                    }
                }
            %>
            <br/>
            <h2 class="text-center">登录</h2>
            <form name="loginForm" id="loginForm" method="post">
                <div class="dataform">
                    <div class="input-warp gap">
                        <span class="input-icon iconfont icon-yonghu1"></span>
                        <input id="user_login" name="username" type="text" class="inputs" placeholder="用户名或邮箱"
                               maxlength="64" value="<%=username%>">
                    </div>
                    <div class="error-content">
                        <span id="userNameErr" class="errMsg"></span>
                    </div>

                    <div class="input-warp gap">
                        <span class="input-icon iconfont icon-baomi"></span>
                        <input class="inputs" type="password" name="password" placeholder="密码" id="user_pass"
                               maxlength="20" value="<%=password%>">
                    </div>
                    <div class="error-content">
                        <span id="passwordErr" class="errMsg"></span>
                    </div>

                    <div class="btn-warp gap">
                        <div class="text-center">
                            <input type="hidden" value="jsform" id="_app"/>
                            <button type="button" id="submit-btn" name="wp-submit" class="btn btn-block lgbtn blue">登录
                            </button>
                        </div>
                    </div>
                    <div class="gap">

                        <div class="pull-right" style="margin-top: 6px"><a href="javascript:;"
                                                                           class="link">忘记密码</a><span
                                class="split-space">|</span><a href="/register" class="link">新用户注册</a></div>

                        <div class="pretty-box">
                            <input type="checkbox" value="1" name="REMEMBER" id="remember" class="">
                            <label for="remember" style="font-weight: normal">记住我</label>
                        </div>
                    </div>


                    <div class="biggap third-party-title">
                        <br/>
                        <h5 class="text-center"><span>第三方账号登录</span></h5>
                    </div>
                    <div class="third-auth">

                        <a title="用钉钉登录" class="dt" href="javascript:;"></a>
                        <a title="用微信账户登录" class="wx" href="javascript:;"></a>
                        <a title="用QQ账户登录" class="qq" href="javascript:;"></a>

                    </div>
                    <h4 id="backtoblog"><a href="/">&larr; 返回新闻首页</a></h4>

                </div>
            </form>

        </div>
    </div>
</div>


<%--<div id="login">
    <h1><a href="/" title="欢迎您光临本网站！" tabindex="-1">${options.optionSiteTitle}</a></h1>
    <%
        String username = "";
        String password = "";
        //获取当前站点的所有Cookie
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {//对cookies中的数据进行遍历，找到用户名、密码的数据
            if ("username".equals(cookies[i].getName())) {
                username = cookies[i].getValue();
            } else if ("password".equals(cookies[i].getName())) {
                password = cookies[i].getValue();
            }
        }
    %>
    <form name="loginForm" id="loginForm" method="post">
        <p>
            <label for="user_login">用户名或电子邮件地址<br/>
                <input type="text" name="username" id="user_login" class="input" value="<%=username%>" size="20"
                       required/></label>
        </p>
        <p>
            <label for="user_pass">密码<br/>
                <input type="password" name="password" id="user_pass" class="input" value="<%=password%>" size="20"
                       required/>
            </label>
        </p>
        <p class="forgetmenot"><label for="rememberme"><input name="rememberme" type="checkbox" id="rememberme"
                                                              value="1" checked/> 记住密码</label></p>
        <p class="submit">
            <input type="button" name="wp-submit" id="submit-btn" class="button button-primary button-large"
                   value="登录"/>
        </p>
    </form>



    <p id="backtoblog"><a href="/">&larr; 返回新闻首页</a> | <a href="/register">注册</a></p>

</div>--%>

<div class="clear"></div>


</body>
<script src="/js/jquery.min.js"></script>

<script type="text/javascript">
    function wp_attempt_focus() {
        setTimeout(function () {
            try {
                d = document.getElementById('user_login');
                d.focus();
                d.select();
            } catch (e) {
            }
        }, 200);
    }

    wp_attempt_focus();
    if (typeof wpOnload == 'function') wpOnload();
</script>


<script type="text/javascript">
    <%--登录验证--%>
    $("#submit-btn").click(function () {
        var user = $("#user_login").val();
        var password = $("#user_pass").val();
        if (user == "") {
            alert("用户名不可为空!");
        } else if (password == "") {
            alert("密码不可为空!");
        } else {
            $.ajax({
                async: false,//同步，待请求完毕后再执行后面的代码
                type: "POST",
                url: '/loginVerify',
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                data: $("#loginForm").serialize(),
                dataType: "json",
                success: function (data) {
                    if (data.code == 0) {
                        alert(data.msg);
                    } else {
                        window.location.href = "/admin";

                    }
                },
                error: function () {
                    alert("数据获取失败")
                }
            })
        }
    })

</script>
</html>

