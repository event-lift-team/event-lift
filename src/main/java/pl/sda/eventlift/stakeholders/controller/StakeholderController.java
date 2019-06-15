package pl.sda.eventlift.stakeholders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.sda.eventlift.stakeholders.services.StakeholderService;

@Controller
public class StakeholderController {

    @Autowired
    private StakeholderService stakeholderService;

    @GetMapping(value = "stakeholder-events/{id}")
    public String showStakeholderEvents(@PathVariable(value = "id") Long stakeholderId, Model model) {
        model.addAttribute("stakeholderEvents", stakeholderService.getStakeholderEvents(stakeholderId));
        return "stakeholder-events";
    }

}
