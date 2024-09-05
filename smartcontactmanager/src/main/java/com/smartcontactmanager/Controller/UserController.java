package com.smartcontactmanager.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartcontactmanager.Entity.Contact;
import com.smartcontactmanager.Entity.User;
import com.smartcontactmanager.Services.UserServices;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServices userServices;

    //method for adding common data in UserController
    @ModelAttribute
    public void addCommonData(Model model, Principal principal)
    {
        String username=principal.getName();
        User user=userServices.findUserByUsername(username);
        System.out.println(user);
        model.addAttribute("user", user);
    }

    //http://localhost:8080/smartContactManager/user/dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model,Principal principal) {
        model.addAttribute("title", "DashBoard");
        return "normal/dashboard";
    } 

    @GetMapping("/addContact")
    public String contactFormHandler(Model model)
    {
        model.addAttribute("title", "addContact");
        model.addAttribute("contact", new Contact());
        return "normal/contactForm";
    }
    @PostMapping("/addContact")
    public String formProcessing()
    {
        return "normal/contactForm";
    }
}
