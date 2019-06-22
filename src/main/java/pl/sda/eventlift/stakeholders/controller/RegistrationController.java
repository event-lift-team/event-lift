package pl.sda.eventlift.stakeholders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.eventlift.events.model.Countries;
import pl.sda.eventlift.stakeholders.dto.StakeholderDTO;
import pl.sda.eventlift.stakeholders.services.StakeholderRegistrationService;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private StakeholderRegistrationService stakeholderRegistrationService;

    @GetMapping(value = "/register")
    public String showForm(Model model){
        StakeholderDTO dto = new StakeholderDTO();
        model.addAttribute("countries", Countries.values());
        model.addAttribute("dto", dto);
        return "register-page";
    }

    @PostMapping(value = "/register")
    public String register(@ModelAttribute(name = "dto") @Valid StakeholderDTO dto, BindingResult result, Model model){
        if (result.hasErrors()){
            return "register-page";
        }
        stakeholderRegistrationService.registerStakeholder(dto);
        model.addAttribute("countries", Countries.values());
        return "index";
    }
}
