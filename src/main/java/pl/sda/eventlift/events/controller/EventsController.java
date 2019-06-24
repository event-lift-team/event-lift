package pl.sda.eventlift.events.controller;

import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.jsf.FacesContextUtils;
import pl.sda.eventlift.events.model.Countries;
import pl.sda.eventlift.events.pojo.EventDTO;
import pl.sda.eventlift.events.services.EventsService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



@Controller
public class EventsController  {

    private EventsService eventsService;

    @Autowired
    public EventsController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @GetMapping(value = "/events")
    public String getEvents(@RequestParam(required = false) String invalidKeyword,
                            @RequestParam(required = false) String message,
                            @RequestParam(required = false) String invalidCity,
                            @RequestParam Optional<Integer> page,
                            @RequestParam Optional<Integer> size,
                            Model model) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(12);
        Page<EventDTO> eventDTOPage = eventsService.findEventsPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("eventPage", eventDTOPage);

        pageNumberAttributePreparation(model, eventDTOPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("countries", Countries.values());
        model.addAttribute("notFoundMessage", message);
        model.addAttribute("invalidKeyword", invalidKeyword);
        model.addAttribute("invalidCity", invalidCity);
        return "events";
    }

    @GetMapping(value = "/events-browser")
    public String getEventsByKeyword(@RequestParam(required = false) String keyword,
                                     @RequestParam(required = false) Countries country,
                                     @RequestParam(required = false) String city,
                                     @RequestParam Optional<Integer> page,
                                     @RequestParam Optional<Integer> size,
                                     Model model) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(12);
        Page<EventDTO> eventDTOPage = eventsService.findEventsByKeywordPaginated(PageRequest.of(currentPage - 1, pageSize),
                keyword, country, city);

        model.addAttribute("eventPage", eventDTOPage);
        if (eventDTOPage.isEmpty()) {
            return "redirect:/events?message=notFound&invalidKeyword=" + keyword + "&invalidCity=" + city;
        }
        pageNumberAttributePreparation(model, eventDTOPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("countries", Countries.values());
        model.addAttribute("keyword", keyword);
        model.addAttribute("country", country);
        model.addAttribute("city", city);
        return "events-browser";
    }

    private void pageNumberAttributePreparation(Model model, Page<EventDTO> eventDTOPage) {
        int totalPages = eventDTOPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }
}
