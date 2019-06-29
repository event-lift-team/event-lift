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
import pl.sda.eventlift.information.model.TransportInfo;
import pl.sda.eventlift.information.services.TransportInfoService;
import pl.sda.eventlift.stakeholders.services.DriverService;
import pl.sda.eventlift.stakeholders.services.DriverSigningService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
public class DriverSigningController {

    private DriverSigningService driverSigningService;
    private EventsService eventsService;
    private DriverService driverService;
    private TransportInfoService transportInfoService;

    @Autowired
    public DriverSigningController(DriverSigningService driverSigningService, EventsService eventsService, DriverService driverService, TransportInfoService transportInfoService) {
        this.driverSigningService = driverSigningService;
        this.eventsService = eventsService;
        this.driverService = driverService;
        this.transportInfoService = transportInfoService;
    }

    @GetMapping(value = "/sign-up-for-event/{id}")
    public String getSignUpForEventAsDriverForm(@PathVariable String id, @RequestParam(required = false) String signInMessage, Model model) {
        EventDTO eventDtoById = eventsService.findEventDtoById(id);
        LocalDate today = LocalDate.now();
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

    @GetMapping(value = "/update-driver-event/{id}/{driverId}")
    public String getUpdateDriverTransportInfoForm(@PathVariable String id, @PathVariable Long driverId, Model model) {
        EventDTO eventDtoById = eventsService.findEventDtoById(id);
        LocalDate today = LocalDate.now();
        TransportInfo driverTransportInfo = transportInfoService.getDriverTransportInfo(id, driverId);
        model.addAttribute("driverTransportInfo", driverTransportInfo);
        model.addAttribute("driverId", driverId);
        model.addAttribute("countries", Countries.values());
        model.addAttribute("today", today);
        model.addAttribute("eventDto", eventDtoById);
        return "update-driver-transport-info";
    }

    @PostMapping(value = "/update-driver-event/{id}/{driverId}")
    public String updateDriverTransportInfo(@PathVariable String id,
                                            @PathVariable Long driverId,
                                            @ModelAttribute(value = "startLocation") String startLocation,
                                            @ModelAttribute(value = "noOfSeats") Integer numberOfSeats,
                                            @ModelAttribute(value = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                            @ModelAttribute(value = "startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                                            @ModelAttribute(value = "additionalInf") String additionalInformation){
        Long stakeholderId = driverService.getDriverById(driverId).getStakeholder().getId();
        TransportInfoDTO transportInfoDTO = TransportInfoDTO.builder()
                .numberOfSeats(numberOfSeats)
                .startLocation(startLocation)
                .startTime(LocalDateTime.of(startDate, startTime))
                .additionalInformation(additionalInformation)
                .build();
        driverSigningService.updateDriverTransportInfo(driverId, id, transportInfoDTO);
        return "redirect:/stakeholder-events/driver/" + stakeholderId;
    }
}
