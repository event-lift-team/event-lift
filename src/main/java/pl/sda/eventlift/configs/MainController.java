package pl.sda.eventlift.configs;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sda.eventlift.events.model.Countries;

@Controller
public class MainController {

    @RequestMapping("/")
    public String showIndexPage(Model model){
        model.addAttribute("countries", Countries.values());
        return "index";
    }

    @RequestMapping("/login")
    public String showLoginPage(Model model){
        model.addAttribute("countries", Countries.values());
        return "login-page";
    }
}
