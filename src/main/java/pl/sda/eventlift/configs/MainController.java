package pl.sda.eventlift.configs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String showIndexPage(){
        return "index";
    }

    @RequestMapping("/login")
    public String showLoginPage(){
        return "login-page";
    }
}
