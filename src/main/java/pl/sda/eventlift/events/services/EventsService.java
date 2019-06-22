package pl.sda.eventlift.events.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.sda.eventlift.events.model.Countries;
import pl.sda.eventlift.events.model.Event;
import pl.sda.eventlift.events.pojo.Embedded;
import pl.sda.eventlift.events.pojo.EventDTO;
import retrofit2.Retrofit;
import retrofit2.adapter.java8.Java8CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class EventsService {

    private final String API_KEY = "YttHkGurL8C5Ci4ARmAm0zvOUtBEjSO5";
    private final String BASE_URL = "https://app.ticketmaster.com/";
    private final String COUNTRY_CODE = "PL";
    private final String SORT = "date,asc";
    private final String SIZE = "100";

    public Page<EventDTO> findEventsByKeywordPaginated(Pageable pageable, String keyword, Countries countries, String city) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<EventDTO> subEventDTOList;
        List<EventDTO> eventDTOList = prepareEventListByKeyword(keyword, countries, city);
        subEventDTOList = getEventDTOSublist(pageSize, startItem, eventDTOList);
        return new PageImpl<>(subEventDTOList, PageRequest.of(currentPage, pageSize), eventDTOList.size());
    }

    public Page<EventDTO> findEventsPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<EventDTO> subEventDTOList;
        List<EventDTO> eventDTOList = prepareEventList();
        subEventDTOList = getEventDTOSublist(pageSize, startItem, eventDTOList);
        return new PageImpl<>(subEventDTOList, PageRequest.of(currentPage, pageSize), eventDTOList.size());
    }

    public Event eventToEntity(EventDTO eventDTO) {
        return Event.builder().name(eventDTO.getName()).uuid(eventDTO.getId()).build();
    }

    public EventDTO findEventDtoById(String id) {
        TicketmasterDiscovery ticketmasterDiscovery = prepareTicketmasterDiscovery();
        CompletableFuture<EventDTO> eventById = ticketmasterDiscovery.findEventById(id, API_KEY);
        EventDTO eventDTO = eventById.join();
        return eventDTO;
    }

    private List<EventDTO> getEventDTOSublist(int pageSize, int startItem, List<EventDTO> eventDTOList) {
        List<EventDTO> subEventDTOList;
        if (eventDTOList.size() < startItem) {
            subEventDTOList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, eventDTOList.size());
            subEventDTOList = eventDTOList.subList(startItem, toIndex);
        }
        return subEventDTOList;
    }

    private List<EventDTO> prepareEventList() {
        TicketmasterDiscovery ticketmasterDiscovery = prepareTicketmasterDiscovery();
        CompletableFuture<EventsWrapper> actualEvents = ticketmasterDiscovery.findActualEvents(
                COUNTRY_CODE,
                SORT,
                SIZE,
                API_KEY);
        EventsWrapper eventsWrapper = actualEvents.join();
        List<EventDTO> events = eventsWrapper.getEmbedded().getEvents();
        return filteredEventList(events);
    }

    private List<EventDTO> prepareEventListByKeyword(String keyword, Countries countries, String city) {
        if (countries == null) {
            return Collections.emptyList();
        }
        TicketmasterDiscovery ticketmasterDiscovery = prepareTicketmasterDiscovery();
        CompletableFuture<EventsWrapper> eventsByKeyword = ticketmasterDiscovery.findEventsByKeyword(
                countries,
                city,
                keyword,
                SORT,
                SIZE,
                API_KEY);
        EventsWrapper eventsWrapper = eventsByKeyword.join();
        Embedded embedded = eventsWrapper.getEmbedded();
        if (embedded == null) {
            return Collections.emptyList();
        }
        List<EventDTO> events = embedded.getEvents();
        return filteredEventList(events);
    }


    private List<EventDTO> filteredEventList(List<EventDTO> eventDTOList) {
        return eventDTOList.stream()
                .filter(event -> !event.getName().toLowerCase().contains("parking"))
                .filter(event -> !event.getName().toLowerCase().contains("vip"))
                .collect(Collectors.toList());
    }

    private TicketmasterDiscovery prepareTicketmasterDiscovery() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(Java8CallAdapterFactory.create())
                .build();
        return retrofit.create(TicketmasterDiscovery.class);
    }
}
