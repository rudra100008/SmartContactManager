package com.smartcontactmanager.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    //http://localhost:8080/smartContactManager/user/dashboard
    @GetMapping("/dashboard")
    public String dashboard() {
        return "normal/dashboard";
    } 
}
