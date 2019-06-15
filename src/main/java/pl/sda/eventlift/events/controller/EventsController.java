package pl.sda.eventlift.events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sda.eventlift.events.model.Countries;
import pl.sda.eventlift.events.pojo.EventDTO;
import pl.sda.eventlift.events.services.EventsService;

import java.util.List;

@Controller
public class EventsController {

    @Autowired
    private EventsService eventsService;

    @GetMapping(value = "/events")
    public String getEvents(@RequestParam (required = false) String invalidKeyword,
                            @RequestParam(required = false) String message,
                            Model model) {
        List<EventDTO> events = eventsService.prepareEventList();
        model.addAttribute("events", events);
        model.addAttribute("countries", Countries.values());
        model.addAttribute("notFoundMessage", message);
        model.addAttribute("invalidKeyword", invalidKeyword);
        return "events";
    }

    @GetMapping(value = "/events-browser")
    public String getEventsByKeyword(@RequestParam(required = false) String keyword,
                                     @RequestParam(required = false) Countries countries, Model model) {
        if (countries.getPlName().isEmpty()){
            return "redirect:/events?message=notFound&invalidKeyword=" + keyword;
        }
        List<EventDTO> events = eventsService.prepareEventListByKeyword(keyword, countries);
        if(events.isEmpty()) {
            return "redirect:/events?message=notFound&invalidKeyword=" + keyword;
        }
        model.addAttribute("keyword", keyword);
        model.addAttribute("countries", countries);
        model.addAttribute("eventsByKeyword", events);
        return "events-browser";
    }
}
