package com.smartcontactmanager.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smartcontactmanager.Entity.User;
import com.smartcontactmanager.Helper.Message;
import com.smartcontactmanager.Services.UserServices;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping(name = "/smartContactManager")
public class HomeController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserServices userServices;

    // http://localhost:8080/smartContactManager/home
    @GetMapping("/home")
    public String homeHandler(Model model) {
        model.addAttribute("title", " SmartContact Manager");
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "Contact Manager");
        return "about";
    }

    // http://localhost:8080/smartContactManager/home/login
    @GetMapping("/home/login")
    public String loginpage() {
        return "login";
    }

    // no need this as there is .loginProcessingUrl("/doLogin") in Config.class
    // //login processing for username and login
    // @PostMapping("/home/login")
    // public String loginHandler(@ModelAttribute("user") User user) {
    // User foundUser = this.userServices.findUserByUsername(user.getUsername());
    // if (foundUser != null && passwordEncoder.matches(user.getPassword(),
    // foundUser.getPassword())) {
    // return "redirect:/home";
    // } else {
    // return "redirect:/home/login";
    // }
    // }

    // http://localhost:8080/smartContactManager/home/signup
    @GetMapping("/home/signup")
    public String signuppage(Model model, HttpSession session) {
        model.addAttribute("user", new User());
        Message message = (Message) session.getAttribute("message");
        if (message != null) {
            model.addAttribute("message", message);
            session.removeAttribute("message");
        }
        return "signup";
    }

    // handler for registration of user
    // this handler saves the user data in database
    @PostMapping("/home/signup")
    public String signUpHandler(@Valid @ModelAttribute("user") User user,
            BindingResult result,
            @RequestParam(value = "isAgreed", defaultValue = "false") boolean isAgreed,
            @RequestParam("userImage") MultipartFile userImage,
            Model model,
            HttpSession session,
            HttpServletResponse response) {
        try {
            // Validate raw password length
            String rawPassword = user.getPassword();
            if (rawPassword.length() < 3 || rawPassword.length() > 16) {
                result.rejectValue("password", "error.user", "Password must be between 3 and 16 characters");
                return "signup";
            }
            if (!isAgreed) {
                throw new Exception("You have not agreed to the terms and conditions");
            }
            if (!userImage.isEmpty()) {
                String uploadDir="src/main/resources/static/images/user/";
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }
                String fileName=userImage.getOriginalFilename();
                Path path=Paths.get(uploadDir+fileName);
                Files.copy(userImage.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
                user.setImageUrl(fileName);
            }else{
                user.setImageUrl("default.jpg");
            }

            // Encode password and save user
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setPosition("ROLE_USER");
            user.setEnable(true);
            this.userServices.saveUser(user);

            session.setAttribute("message", new Message("Successfully registered", "alert-success"));
            response.setHeader("refresh", "3; URL=/smartContactManager/home/login");
            return "signup";
        } catch (DataIntegrityViolationException e) {
            session.setAttribute("message",
                    new Message("The email or username is already in use. Please choose another.", "alert-danger"));
            return "signup";
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", new Message("Something went wrong: " + e.getMessage(), "alert-danger"));
            return "signup";
        }
    }
    @GetMapping("/user/profile")
    public String profileHandler(Principal principal,Model model)
    {
        model.addAttribute("title", "Smart Contact Manager");
        String username=principal.getName();
        User getUser=this.userServices.findUserByUsername(username);
        model.addAttribute("user", getUser);
        return "profile";
    }

}
