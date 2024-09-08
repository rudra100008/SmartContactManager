package com.smartcontactmanager.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.smartcontactmanager.Entity.Contact;
import com.smartcontactmanager.Entity.User;
import com.smartcontactmanager.Helper.Message;
import com.smartcontactmanager.Services.ContactServices;
import com.smartcontactmanager.Services.UserServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServices userServices;
    @Autowired
    private ContactServices contactServices;

    //method for adding common data in UserController
    @ModelAttribute
    public void addCommonData(Model model, Principal principal)
    {
        String username=principal.getName();
        User user=userServices.findUserByUsername(username);

        model.addAttribute("user", user);
    }

    //http://localhost:8080/smartContactManager/user/dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model,Principal principal) {
        model.addAttribute("title", "DashBoard");
        String username=principal.getName();
        User user=userServices.findUserByUsername(username);
        List<Contact> contacts=this.contactServices.getContactsByUser(user);
        model.addAttribute("contacts", contacts);
        return "normal/dashboard";
    } 
    
    @GetMapping("/addContact")
    public String contactFormHandler(Model model,HttpSession session)
    {
        model.addAttribute("title", "addContact");
        model.addAttribute("contact", new Contact());
        Message message=(Message)session.getAttribute("message");
        if (message !=null) {
            model.addAttribute("message",message);
            session.removeAttribute("message");
        }
        return "normal/contactForm";
    }
    @PostMapping("/addContact")
    public String formProcessing(@Valid @ModelAttribute("contact") Contact contact,
                             BindingResult result,
                             @RequestParam("profileImage") MultipartFile imageFile,
                             Model model,
                             Principal principal,
                             HttpSession session) {
    try {
        model.addAttribute("title", "addContact");
        model.addAttribute("contact", contact);
        String username = principal.getName();
        User user = this.userServices.findUserByUsername(username);
        if (result.hasErrors()) {
            model.addAttribute("contact", contact);
        }
        if (!imageFile.isEmpty() && contact !=null) {
            String uploadDir = "src/main/resources/static/images/";
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs(); 
            }
            String fileName = imageFile.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName); // File path
            Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            model.addAttribute("contact",contact);
            contact.setImage(fileName);
        } else {
            contact.setImage(null);
            model.addAttribute("contact",contact);
        }
        contact.setUser(user);
        user.getContacts().add(contact);
        this.userServices.saveUser(user);
        session.setAttribute("message", new Message("Contact is registred", "alert-success"));
        return "normal/contactForm";

    } catch (Exception e) {
        e.printStackTrace();
        session.setAttribute("message", new Message("Something went wrong: "+e.getLocalizedMessage(), "alert-danger"));
        return "normal/contactForm";
    }
}

}
