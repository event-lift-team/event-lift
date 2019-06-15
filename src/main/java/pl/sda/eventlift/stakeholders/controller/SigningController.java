package pl.sda.eventlift.stakeholders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.eventlift.events.pojo.EventDTO;
import pl.sda.eventlift.events.services.EventsService;
import pl.sda.eventlift.stakeholders.model.Stakeholder;
import pl.sda.eventlift.stakeholders.services.SigningService;
import pl.sda.eventlift.stakeholders.services.StakeholderService;

import java.util.Set;

@Controller
public class SigningController {

    @Autowired
    private SigningService signingService;

    @Autowired
    private EventsService eventsService;

    @Autowired
    private StakeholderService stakeholderService;

    @GetMapping(value = "/sign-up-for-event/{id}")
    public String getSignUpForEventForm(@PathVariable String id, Model model){
        EventDTO eventDtoById = eventsService.findEventDtoById(id);
        model.addAttribute("eventDto", eventDtoById);
        return "sign-up-for-event";
    }

    @PostMapping(value = "/sign-up-for-event/{id}")
    public String signUpForEvent(@PathVariable String id, @ModelAttribute(value = "stakeholderDtoEmail") String stakeholderDtoEmail) {
        signingService.relateStakeholderWithEvent(stakeholderDtoEmail, id);
        return "redirect:/events";
    }

    @GetMapping(value = "/who-goes/{id}")
    private String checkWhoGoes(@PathVariable (required = false) String id, Model model){
        if(signingService.getStakeholdersByEventId(id) == null){
            return "redirect:/nobody-goes";
        }
        Set<Stakeholder> stakeholders = signingService.getStakeholdersByEventId(id);
        model.addAttribute("stakeholders", stakeholders);
        return "who-goes";
    }

    @GetMapping(value = "nobody-goes")
    public String nobodyGoes(){
        return "nobody-goes";
    }
}
