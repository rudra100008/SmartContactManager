package com.smartcontactmanager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontactmanager.Entity.User;
import com.smartcontactmanager.Helper.Message;
import com.smartcontactmanager.Services.UserServices;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;




@Controller
@RequestMapping( name = "/smartContactManager")
public class HomeController {
    @Autowired
    private UserServices userServices;
    //http://localhost:8080/smartContactManager/home
    @GetMapping("/home")
    public String homeHandler(Model model)
    {
        model.addAttribute("title", "Contact Manager");
        return "index";
    }
    @GetMapping("/about")
    public String about(Model model)
    {
        model.addAttribute("title", "Contact Manager");
        return "about";
    }
    //http://localhost:8080/smartContactManager/home/login
    @GetMapping("/home/login")
    public String loginpage()
    {
        return "login";
    }
    @PostMapping("/home/login")
    public String loginHandler(@ModelAttribute("user") User user)
    {
        User foundUser=this.userServices.findUser(user.getUsername());
        if (foundUser !=null &&  user.getPassword().equals(foundUser.getPassword())) {
            return "redirect:/home";
        }else{
            return "redirect:/home/login";
        }
    }
   // http://localhost:8080/smartContactManager/home/signup
    @GetMapping("/home/signup")
    public String signuppage(Model model,HttpSession session)
    {
        model.addAttribute("user", new User());
        Message message=(Message) session.getAttribute("message");
        if (message !=null) {
           model.addAttribute("message", message);
           session.removeAttribute("message");
        }
        return "signup";
    }
    //handler for registration of user
    //this hanlder saves the user data in database
 @PostMapping("/home/signup")
public String signUpHandler(@Valid @ModelAttribute("user") User user,
                            BindingResult result,
                            @RequestParam(value = "isAgreed", defaultValue = "false") boolean isAgreed,
                            Model model,
                            HttpSession session,
                            HttpServletResponse response) 
    {
                           
    try {
        if (!isAgreed) {
            throw new Exception("You have not agreed to the terms and conditions");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", new User());
            return "signup";
        } 
        user.setPosition("User");
        user.setEnable(true);

        this.userServices.saveUser(user);
        session.setAttribute("message", new Message("Successfully registered", "alert-success"));
        response.setHeader("refresh","3; URL=/smartContactManager/home/login"); // 3 sec delay before redirecting
        return "signup";
    } catch (DataIntegrityViolationException e) {
        // Handle unique constraint violation (e.g., duplicate email or username)
        session.setAttribute("message", new Message("The email or username is already in use. Please choose another.", "Alert-Danger"));
        return "signup";
    } catch (Exception e) {
        e.printStackTrace();
        session.setAttribute("message", new Message("Something went wrong: " + e.getMessage(), " alert-danger"));
        return "signup";
    }
}
    


}
