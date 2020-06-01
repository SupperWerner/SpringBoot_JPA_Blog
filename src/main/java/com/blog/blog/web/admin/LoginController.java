package com.blog.blog.web.admin;

import com.blog.blog.po.User;
import com.blog.blog.service.admin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import static com.blog.blog.util.MD5Utils.code;

/**
 * 登录
 */

@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam  String username
                            , @RequestParam String password
                            , HttpSession session
                            , RedirectAttributes attributes) {
        User user = userService.checkUser(username, code(password)); // 验证是否存在该用户 密码经过md5加密
        if (user!=null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index"; // 登录成功，跳转到欢迎页
        } else {
            attributes.addFlashAttribute("message","用户名和密码错误！");
            return "redirect:/admin";  // 如果错误，重定向到登录页面
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin"; // 登出到，登录页面
    }

}
