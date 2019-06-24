package pl.sda.eventlift.stakeholders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.sda.eventlift.events.model.Countries;
import pl.sda.eventlift.stakeholders.services.StakeholderService;

@Controller
public class StakeholderController {

    private StakeholderService stakeholderService;

    @Autowired
    public StakeholderController(StakeholderService stakeholderService) {
        this.stakeholderService = stakeholderService;
    }

    @GetMapping(value = "stakeholder-events/{id}")
    public String showStakeholderEvents(@PathVariable(value = "id") Long stakeholderId, Model model) {
        model.addAttribute("countries", Countries.values());
        model.addAttribute("stakeholderEventsAsDriver", stakeholderService.getActualStakeholderEvents(stakeholderId, "Driver"));
        model.addAttribute("stakeholderEventsAsHitchhiker", stakeholderService.getActualStakeholderEvents(stakeholderId, "Hitch-hiker"));
        return "stakeholder-events";
    }

}
