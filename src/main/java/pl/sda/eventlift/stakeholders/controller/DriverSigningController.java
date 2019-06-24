package pl.sda.eventlift.stakeholders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.sda.eventlift.events.model.Countries;
import pl.sda.eventlift.events.pojo.EventDTO;
import pl.sda.eventlift.events.services.EventsService;
import pl.sda.eventlift.information.dto.TransportInfoDTO;
import pl.sda.eventlift.stakeholders.services.DriverSigningService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
public class DriverSigningController {

    private DriverSigningService driverSigningService;
    private EventsService eventsService;

    @Autowired
    public DriverSigningController(DriverSigningService driverSigningService, EventsService eventsService) {
        this.driverSigningService = driverSigningService;
        this.eventsService = eventsService;
    }

    @GetMapping(value = "/sign-up-for-event/{id}")
    public String getSignUpForEventAsDriverForm(@PathVariable String id, @RequestParam(required = false) String signInMessage, Model model) {
        EventDTO eventDtoById = eventsService.findEventDtoById(id);
        String latitude = eventDtoById.getEmbedded().getVenue().getLocation().getLatitude();
        String longitude = eventDtoById.getEmbedded().getVenue().getLocation().getLongitude();
        String cityAndName = eventDtoById.getEmbedded().getVenue().getCityAndName();
        LocalDate today = LocalDate.now();
        model.addAttribute("cityAndName", cityAndName);
        model.addAttribute("latitude", latitude);
        model.addAttribute("longitude", longitude);
        model.addAttribute("countries", Countries.values());
        model.addAttribute("today", today);
        model.addAttribute("signInMessage", signInMessage);
        model.addAttribute("eventDto", eventDtoById);
        return "sign-up-for-event-as-driver";
    }

    @PostMapping(value = "/sign-up-for-event/{id}")
    public String signUpForEventAsDriver(@PathVariable String id,
                                         @ModelAttribute(value = "stakeholderDtoEmail") String stakeholderDtoEmail,
                                         @ModelAttribute(value = "startLocation") String startLocation,
                                         @ModelAttribute(value = "noOfSeats") Integer numberOfSeats,
                                         @ModelAttribute(value = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                         @ModelAttribute(value = "startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                                         @ModelAttribute(value = "additionalInf") String additionalInformation) {
        TransportInfoDTO informationDto = TransportInfoDTO.builder()
                .startLocation(startLocation)
                .numberOfSeats(numberOfSeats)
                .startTime(LocalDateTime.of(startDate, startTime))
                .additionalInformation(additionalInformation).build();
        if (driverSigningService.relateDriverWithEvent(stakeholderDtoEmail, id, informationDto).equals("ok")) {
            return "redirect:/events";
        } else if (driverSigningService.relateDriverWithEvent(stakeholderDtoEmail, id, informationDto).equals("signedAsHitch-hiker")) {
            return "redirect:/sign-up-for-event/" + id + "?signInMessage=cannotSign";
        } else {
            return "redirect:/sign-up-for-event/" + id + "?signInMessage=alreadySigned";
        }
    }
}
