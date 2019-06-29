package pl.sda.eventlift.stakeholders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.sda.eventlift.events.model.Countries;
import pl.sda.eventlift.events.services.EventsService;
import pl.sda.eventlift.information.services.TransportInfoService;
import pl.sda.eventlift.stakeholders.services.HitchhikerService;
import pl.sda.eventlift.stakeholders.services.StakeholderService;

@Controller
public class StakeholderController {

    private StakeholderService stakeholderService;
    private TransportInfoService transportInfoService;
    private HitchhikerService hitchhikerService;

    public StakeholderController(StakeholderService stakeholderService, TransportInfoService transportInfoService, HitchhikerService hitchhikerService) {
        this.stakeholderService = stakeholderService;
        this.transportInfoService = transportInfoService;
        this.hitchhikerService = hitchhikerService;
    }

    @GetMapping(value = "stakeholder-events/driver/{id}")
    public String showDriverEvents(@PathVariable(value = "id") Long stakeholderId, Model model) {
        model.addAttribute("countries", Countries.values());
        model.addAttribute("transportInfoService", transportInfoService);
        model.addAttribute("driver", stakeholderService.getStakeholderAsDriver(stakeholderId));
        model.addAttribute("stakeholderEventsAsDriver", stakeholderService.getActualStakeholderEvents(stakeholderId, "Driver"));
        return "stakeholder-events-as-driver";
    }

    @GetMapping(value = "stakeholder-events/hitch-hiker/{id}")
    public String showHitchhikerEvents(@PathVariable(value = "id") Long stakeholderId, Model model) {
        model.addAttribute("countries", Countries.values());
        model.addAttribute("transportInfoService", transportInfoService);
        model.addAttribute("hitchhikerService", hitchhikerService);
        model.addAttribute("hitchhiker", stakeholderService.getStakeholderAsHitchhiker(stakeholderId));
        model.addAttribute("stakeholderEventsAsHitchhiker", stakeholderService.getActualStakeholderEvents(stakeholderId, "Hitch-hiker"));
        return "stakeholder-events-as-hitch-hiker";
    }

}
