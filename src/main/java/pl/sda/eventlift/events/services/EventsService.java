package pl.sda.eventlift.events.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.sda.eventlift.events.model.Countries;
import pl.sda.eventlift.events.pojo.Embedded;
import pl.sda.eventlift.events.pojo.EventDTO;
import retrofit2.Retrofit;
import retrofit2.adapter.java8.Java8CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class EventsService {
    private final String API_KEY = "YttHkGurL8C5Ci4ARmAm0zvOUtBEjSO5";
    private final String BASE_URL = "https://app.ticketmaster.com/";
    private final String COUNTRY_CODE = "PL";
    private final String SORT = "date,desc";
    private final String SIZE = "5";

    public List<EventDTO> prepareEventList() {
        TicketmasterDiscovery ticketmasterDiscovery = prepareTicketmasterDiscovery();
        CompletableFuture<EventsWrapper> events = ticketmasterDiscovery.findActualEvents(
                COUNTRY_CODE,
                SORT,
                SIZE,
                API_KEY);
        EventsWrapper eventsWrapper = events.join();
        return eventsWrapper.getEmbedded().getEvents();
    }

    public List<EventDTO> prepareEventListByKeyword(String keyword, Countries countries){
        if(countries == null){
            return Collections.emptyList();
        }
        TicketmasterDiscovery ticketmasterDiscovery = prepareTicketmasterDiscovery();
        CompletableFuture<EventsWrapper> eventsByKeyword = ticketmasterDiscovery.findEventsByKeyword(
                countries,
                keyword,
                SORT,
                SIZE,
                API_KEY);
        EventsWrapper eventsWrapper = eventsByKeyword.join();
        Embedded embedded = eventsWrapper.getEmbedded();
        if(embedded == null) {
            return Collections.emptyList();
        }
        return embedded.getEvents();
    }

    public EventDTO findEventDtoById(String id) {
        TicketmasterDiscovery ticketmasterDiscovery = prepareTicketmasterDiscovery();
        CompletableFuture<EventDTO> eventById = ticketmasterDiscovery.findEventById(id, API_KEY);
        EventDTO eventDTO = eventById.join();
        return eventDTO;
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
