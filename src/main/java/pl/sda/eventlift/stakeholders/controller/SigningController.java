package pl.sda.eventlift.stakeholders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.sda.eventlift.events.model.Countries;
import pl.sda.eventlift.information.services.TransportInfoService;
import pl.sda.eventlift.stakeholders.model.Driver;
import pl.sda.eventlift.stakeholders.model.Hitchhiker;
import pl.sda.eventlift.stakeholders.services.DriverService;
import pl.sda.eventlift.stakeholders.services.DriverSigningService;
import pl.sda.eventlift.stakeholders.services.HitchhikerSigningService;

import java.util.Set;

@Controller
public class SigningController {

    @Autowired
    private DriverSigningService driverSigningService;

    @Autowired
    private HitchhikerSigningService hitchhikerSigningService;

    @Autowired
    private TransportInfoService transportInfoService;

    @Autowired
    private DriverService driverService;

    @GetMapping(value = "/who-goes/{id}")
    public String checkWhoGoes(@PathVariable(required = false) String id, Model model) {
        if (driverSigningService.getDriversByEventId(id) == null) {
            return "redirect:/nobody-goes/" + id;
        }
        Set<Driver> drivers = driverSigningService.getDriversByEventId(id);
        Set<Hitchhiker> hitchhikers = hitchhikerSigningService.getHitchhikersByEventId(id);
        model.addAttribute("transportInfoService", transportInfoService);
        model.addAttribute("driverService", driverService);
        model.addAttribute("countries", Countries.values());
        model.addAttribute("drivers", drivers);
        model.addAttribute("eventUuid", id);
        return "who-goes";
    }

    @GetMapping(value = "nobody-goes/{id}")
    public String nobodyGoes(@PathVariable String id, Model model) {
        model.addAttribute("eventUuid", id);
        model.addAttribute("countries", Countries.values());
        return "nobody-goes";
    }
}
