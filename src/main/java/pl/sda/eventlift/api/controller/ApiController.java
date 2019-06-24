package pl.sda.eventlift.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.sda.eventlift.api.service.RestService;
import pl.sda.eventlift.stakeholders.dto.DriverDTO;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class ApiController {

    private RestService restService;

    @Autowired
    public ApiController(RestService restService) {
        this.restService = restService;
    }

    @GetMapping("/drivers")
    public Set<DriverDTO> getDriversWithActiveOffers() {
        return restService.getAllDriversWithActiveOffer();
    }

}
