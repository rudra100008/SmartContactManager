package com.smartcontactmanager.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smartcontactmanager.Entity.Contact;
import com.smartcontactmanager.Entity.User;
import com.smartcontactmanager.Services.ContactServices;
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
public String formProcessing(@ModelAttribute("contact") Contact contact,
                             @RequestParam("profileImage") MultipartFile imageFile,
                             Model model,
                             Principal principal) {
    try {
        model.addAttribute("title", "addContact");
        model.addAttribute("contact", contact);
        String username = principal.getName();
        User user = this.userServices.findUserByUsername(username);
        if (!imageFile.isEmpty()) {
            String uploadDir = "src/main/resources/static/images/";
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs(); // Create directory if it does not exist
            }
            String fileName = imageFile.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName); // File path
            Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            contact.setImage(fileName);
        } else {
            contact.setImage(null);
        }

        contact.setUser(user);
        user.getContacts().add(contact);
        this.userServices.saveUser(user);

    } catch (Exception e) {
        e.printStackTrace();
    }

    return "normal/dashboard";
}

}
