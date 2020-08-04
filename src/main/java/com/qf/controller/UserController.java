package com.qf.controller;


import com.qf.pojo.User;
import com.qf.service.UserService;
import com.qf.utils.ImageCut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping("login")
//    @ResponseBody
    public void login(User user, HttpServletRequest request,HttpServletResponse response)
            throws Exception {
        user = service.login(user);
//        System.out.println(user);
        if (null != user) {
            HttpSession session = request.getSession();
            session.setAttribute("userAccount", user.getEmail());
            request.getRequestDispatcher("/").forward(request,response);
        }else{
            response.sendRedirect("/");
        }
    }

    @RequestMapping("loginOut2")
    public void loginOut2(HttpServletRequest request,HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.removeAttribute("userAccount");
        response.sendRedirect("/");
    }

    @RequestMapping("insertUser")
    @ResponseBody
    public String insertUser(User user, HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        user.setCreateTime(new Date());
        if (service.insertUser(user)) {
            HttpSession session = request.getSession();
            session.setAttribute("userAccount", user.getEmail());
            return "success";

//            request.setAttribute("msg","<script>alert('注册成功');window.location.href='${request.getContextPath()}/index.jsp'</script>");
//        request.getRequestDispatcher("msg.jsp").forward(request,response);
        }
        throw new RuntimeException("用户注册失败");
    }

    @RequestMapping("passwordSafe")
    public ModelAndView passwordSafe(HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("password_safe.jsp");
        return modelAndView;
    }

    @RequestMapping("validatePassword")
    @ResponseBody
    public String validatePassword(String password, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (password.equals(user.getPassword())) {
            return "success";
        }
        return "failed";
    }

    @RequestMapping("updatePassword")
    public void updatePassword(String newPassword, HttpServletRequest request
            ,HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        user.setPassword(newPassword);
        service.updateUser(user);
        response.sendRedirect("/user/showMyProfile");
    }

    @RequestMapping("forgetPassword")
    public ModelAndView forgetPassword(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("forget_password.jsp");
        return modelAndView;
    }

//    @RequestMapping("validateEmailCode")
//    public ModelAndView validateEmailCode(String email, String code, HttpServletRequest request,
//                                          HttpServletResponse response) {
//        HttpSession session = request.getSession();
//        ModelAndView modelAndView = new ModelAndView();
//        String sessionEmail = (String) session.getAttribute("email");
//        String sessionCode = (String) session.getAttribute("code");
//        System.out.println(sessionEmail);
//        System.out.println(email);
//
//        if (sessionEmail.equals(email) && sessionCode.equals(code)) {
//            modelAndView.setViewName("reset_password.jsp");
//        } else{
//            modelAndView.setViewName("forget_password.jsp");
//        }
//        return modelAndView;
//    }


    @RequestMapping("validateEmailCode")
    public String validateEmailCode(String email, String code, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionEmail = (String) session.getAttribute("email");
        String sessionCode = (String) session.getAttribute("code");
        if (sessionEmail.equals(email) && sessionCode.equals(code)) {
            System.out.println("执行了");
            return "reset_password.jsp";
        }
        return "reset_password.jsp";
    }


    @RequestMapping("resetPassword")
    public void resetPassword(String email, String password, HttpServletRequest request
    ,HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("email");
        session.removeAttribute("code");
        User user = service.selectUserByEmail(email);
        if (null != user) {
            user.setPassword(password);
            service.updateUser(user);
            session.setAttribute("userAccount", user.getEmail());
        }
        response.sendRedirect("/");
    }

    @RequestMapping("validateEmail")
    @ResponseBody
    public String validateEmail(String email) {
        User user =service.selectUserByEmail(email);
        if (null == user) {
            return "success";
        }
        return "hasUser";
    }

    @RequestMapping("sendEmail")
    @ResponseBody
    public String sendEmail(String email, HttpServletRequest request) {
        //验证用户是否存在
        if ("success".equals(validateEmail(email))) {
            return "hasNoUser";
        }

        String code = com.qf.utils.MailUtils.getValidateCode(6);
        boolean flag = com.qf.utils.MailUtils.sendMail(email, "测试邮件随机生成的验证码是：" + code, "你好，这是一封测试邮件，无需回复。");
        if (flag) {
            HttpSession session = request.getSession();
            session.setAttribute("email", email);
            session.setAttribute("code", code);
            return "success";
        }
        return "failed";
    }


    @RequestMapping("showMyProfile")
    public ModelAndView showMyProfile(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = service.selectUserByEmail((String) session.getAttribute("userAccount"));
        session.setAttribute("user", user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("my_profile.jsp");
        return modelAndView;
    }

    @RequestMapping("changeProfile")
    public ModelAndView changeProfile() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("change_profile.jsp");
        return modelAndView;
    }

    @RequestMapping("updateUser")
    public void updateUser(String nickName, String sex, String birthday, String address,
                           HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        System.out.println(nickName);
        User user = (User) session.getAttribute("user");
        user.setNickName(nickName);
        user.setSex(sex);
        user.setBirthday(birthday);
        user.setAddress(address);
        service.updateUser(user);
        request.getRequestDispatcher("showMyProfile").forward(request,response);
    }

    @RequestMapping("changeAvatar")
    public ModelAndView changeAvatar(HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("change_avatar.jsp");
        return modelAndView;
    }


    @RequestMapping("uploadImage")
    public void uploadImage(MultipartFile imgUrl,String x1, String x2, String y1, String y2,HttpServletRequest request
                            ,HttpServletResponse response) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        String path="D:\\server\\apache-tomcat-8.5.31\\webapps\\video";
        System.out.println(imgUrl);

        String fileName = imgUrl.getOriginalFilename();
//        System.out.println(fileName);

        File file = new File(path);
        if(! file.exists()){
            file.mkdir();
        }
        fileName = fileName.substring(fileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString().replace("-", "");
        fileName = uuid + fileName;
        imgUrl.transferTo(new File(path, fileName));
//        System.out.println(x1+"--"+x2+"--"+y1+"--"+y2);


        int x1Int = (int) Double.parseDouble(x1);
        int x2Int = (int) Double.parseDouble(x2);
        int y1Int = (int) Double.parseDouble(y1);
        int y2Int = (int) Double.parseDouble(y2);
        new ImageCut().cutImage(path + "/" + fileName, x1Int, y1Int, x2Int - x1Int, y2Int - y1Int);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        user.setImgUrl(fileName);
        service.updateUser(user);
        response.sendRedirect("showMyProfile");

    }

}
