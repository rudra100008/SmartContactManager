package com.smartcontactmanager.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping( name = "/smartContactManager")
public class MainController {
    @GetMapping("/")
    public String homeHandler()
    {
        return "index";
    }
}
