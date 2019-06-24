package pl.sda.eventlift.stakeholders.controller;

import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.sda.eventlift.events.model.Countries;
import pl.sda.eventlift.events.pojo.EventDTO;
import pl.sda.eventlift.events.services.EventsService;
import pl.sda.eventlift.information.model.TransportInfo;
import pl.sda.eventlift.information.services.TransportInfoService;
import pl.sda.eventlift.stakeholders.model.Driver;
import pl.sda.eventlift.stakeholders.services.DriverService;
import pl.sda.eventlift.stakeholders.services.HitchhikerSigningService;

@Controller
public class HitchhikerSigningController {

    private HitchhikerSigningService hitchhikerSigningService;
    private EventsService eventsService;
    private TransportInfoService transportInfoService;
    private DriverService driverService;

    @Autowired
    public HitchhikerSigningController(HitchhikerSigningService hitchhikerSigningService,
                                       EventsService eventsService,
                                       TransportInfoService transportInfoService,
                                       DriverService driverService) {
        this.hitchhikerSigningService = hitchhikerSigningService;
        this.eventsService = eventsService;
        this.transportInfoService = transportInfoService;
        this.driverService = driverService;
    }

    @GetMapping(value = "/sign-up-for-event/hitch-hiking/{eventUuid}/{driverId}")
    public String getSignUpForEventFormAsHitchhiker(@PathVariable String eventUuid,
                                                    @PathVariable Long driverId,
                                                    @RequestParam(required = false) String signInMessage, Model model) throws ObjectNotFoundException {
        EventDTO eventDtoById = eventsService.findEventDtoById(eventUuid);
        TransportInfo transportInfo = transportInfoService.getDriverTransportInfo(eventUuid, driverId);
        Driver driver = driverService.getDriverById(driverId);
        model.addAttribute("countries", Countries.values());
        model.addAttribute("transportInfo", transportInfo);
        model.addAttribute("driver", driver);
        model.addAttribute("eventDto", eventDtoById);
        model.addAttribute("signInMessage", signInMessage);
        return "sign-up-for-event-as-hitch-hiker";
    }

    @PostMapping(value = "sign-up-for-event/hitch-hiking/{id}/{driverId}")
    public String signUpForEventAsHitchhiker(@PathVariable String id,
                                             @PathVariable Long driverId,
                                             @ModelAttribute(value = "stakeholderDtoEmail") String stakeholderDtoEmail) {
        if (hitchhikerSigningService.relateHitchhikerWithEvent(stakeholderDtoEmail, id).equals("ok")) {
            hitchhikerSigningService.relateHitchhikerWithDriver(stakeholderDtoEmail, driverId);
            transportInfoService.reduceNoOfSeats(id, driverId);
            return "redirect:/events";
        } else if (hitchhikerSigningService.relateHitchhikerWithEvent(stakeholderDtoEmail, id).equals("signedAsDriver")) {
            return "redirect:/sign-up-for-event/hitch-hiking/" + id + "/" + driverId + "?signInMessage=cannotSign";
        } else {
            return "redirect:/sign-up-for-event/hitch-hiking/" + id + "/" + driverId + "?signInMessage=alreadySigned";
        }
    }
}
