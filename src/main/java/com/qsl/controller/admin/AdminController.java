package com.qsl.controller.admin;


import com.alibaba.fastjson.JSONObject;
import com.qsl.dto.JsonResult;
import com.qsl.entity.*;
import com.qsl.enums.LinkStatus;
import com.qsl.enums.NewsStatus;
import com.qsl.enums.UserRole;
import com.qsl.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

import static com.qsl.util.MyUtils.getIpAddr;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private CommentService commentService;


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private OptionsService optionsService;

    @Autowired
    private MenuService menuService;

    /**
     * 后台首页
     *
     * @return
     */
    @RequestMapping("/admin")
    public String index(Model model, HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        /*----------------------管理员后台首页显示-----------------------------------*/
        // 菜单显示
        List<Menu> menuList = menuService.listMenu();
        request.setAttribute("menuList", menuList);

        List<Category> categoryList = categoryService.listCategory();
        request.setAttribute("allCategoryList", categoryList);

        //获得网站概况
        List<String> siteBasicStatistics = new ArrayList<String>();
        siteBasicStatistics.add(newsService.countNews(NewsStatus.PUBLISH.getValue()) + "");
        siteBasicStatistics.add(newsService.countNewsComment() + "");
        siteBasicStatistics.add(categoryService.countCategory() + "");
        siteBasicStatistics.add(keywordService.countKeyword() + "");
        siteBasicStatistics.add(linkService.countLink(LinkStatus.NORMAL.getValue()) + "");
        siteBasicStatistics.add(newsService.countNewsView() + "");
        request.setAttribute("siteBasicStatistics", siteBasicStatistics);
        //最后更新的新闻
        News lastUpdateNews = newsService.getLastUpdateNews();
        request.setAttribute("lastUpdateNews", lastUpdateNews);
        /*-----------------------------------------------------------------------------------*/
        /*如果是普通用户转发到新闻列表*/
        if (!UserRole.ADMIN.getValue().equals(user.getUserRole())) {
           return "redirect:admin/news";
        }
        return "Admin/index_v1";

    }

    /**
     * 普通用户后台近期发布和评论
     *
     * @return
     */
    @RequestMapping("/user")
    public String indexUser(Model model, HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        Integer userId = null;
        if (!UserRole.ADMIN.getValue().equals(user.getUserRole())) {
            // 用户查询自己的新闻, 管理员查询所有的
            userId = user.getUserId();
        }
        /*普通用户-------------------------------------------------*/
        //新闻列表
        List<News> newsList = newsService.listRecentNews(userId, 5);
        model.addAttribute("newsList", newsList);

        //评论列表
        List<Comment> commentList = commentService.listRecentComment(userId, 5);
        model.addAttribute("commentList", commentList);
        /*普通用户-------------------------------------------------------*/

        return "Admin/index_v1";

    }





    /**
     * 登录页面显示
     *
     * @return
     */
    @RequestMapping("/login")
    public String
    loginPage() {
        return "Admin/login";
    }


    /**
     * 登录页面显示
     *
     * @return
     */
    @RequestMapping("/register")
    public String registerPage() {
        return "Admin/register";
    }

    /**
     * 登录验证
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/loginVerify", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String loginVerify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberme = request.getParameter("rememberme");
        User user = userService.getUserByNameOrEmail(username);
        if (user == null) {
            map.put("code", 0);
            map.put("msg", "用户名无效！");
        } else if (!user.getUserPass().equals(password)) {
            map.put("code", 0);
            map.put("msg", "密码错误！");
        } else if (user.getUserStatus() == 0) {
            map.put("code", 0);
            map.put("msg", "账号已禁用！");
        } else {
            //登录成功
            map.put("code", 1);
            map.put("msg", "");
            //添加session
            request.getSession().setAttribute("user", user);
            //添加cookie
            if (rememberme != null) {
                //创建两个Cookie对象
                Cookie nameCookie = new Cookie("username", username);
                //设置Cookie的有效期为3天
                nameCookie.setMaxAge(60 * 60 * 24 * 3);
                Cookie pwdCookie = new Cookie("password", password);
                pwdCookie.setMaxAge(60 * 60 * 24 * 3);
                response.addCookie(nameCookie);
                response.addCookie(pwdCookie);
            }
            user.setUserLastLoginTime(new Date());
            user.setUserLastLoginIp(getIpAddr(request));
            userService.updateUser(user);

        }
        String result = new JSONObject(map).toString();
        return result;
    }

    /**
     * 登录验证
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/registerSubmit", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult registerSubmit(HttpServletRequest request) {
        String username = request.getParameter("username");
        String nickname = request.getParameter("nickname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User checkUserName = userService.getUserByName(username);
        if (checkUserName != null) {
            return new JsonResult().fail("用户名已存在");
        }
        User checkEmail = userService.getUserByEmail(username);
        if (checkEmail != null) {
            return new JsonResult().fail("电子邮箱已存在");
        }

        // 添加用户
        User user = new User();
        user.setUserAvatar("/img/avatar/avatar.png");
        user.setUserName(username);
        user.setUserNickname(nickname);
        user.setUserPass(password);
        user.setUserEmail(email);
        user.setUserStatus(1);
        user.setNewsCount(0);
        user.setUserRole(UserRole.USER.getValue());
        try {
            userService.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult().fail("系统异常");
        }
        return new JsonResult().ok("注册成功");
    }

    /**
     * 退出登录
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/admin/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        session.invalidate();
        return "redirect:/login";
    }


    /**
     * 基本信息页面显示
     *
     * @return
     */
    @RequestMapping(value = "/admin/profile")
    public ModelAndView userProfileView(HttpSession session) {

        ModelAndView modelAndView = new ModelAndView();
        User sessionUser = (User) session.getAttribute("user");
        User user = userService.getUserById(sessionUser.getUserId());
        modelAndView.addObject("user", user);

        modelAndView.setViewName("Admin/User/profile");
        return modelAndView;
    }

    /**
     * 编辑个人信息页面显示
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/admin/profile/edit")
    public ModelAndView editUserView(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        User loginUser = (User) session.getAttribute("user");
        User user = userService.getUserById(loginUser.getUserId());
        modelAndView.addObject("user", user);

        modelAndView.setViewName("Admin/User/editProfile");
        return modelAndView;
    }


    /**
     * 编辑用户提交
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/admin/profile/save", method = RequestMethod.POST)
    public String saveProfile(User user, HttpSession session) {
        User dbUser = (User) session.getAttribute("user");

        user.setUserId(dbUser.getUserId());
        userService.updateUser(user);
        return "redirect:/admin/profile";
    }


}
