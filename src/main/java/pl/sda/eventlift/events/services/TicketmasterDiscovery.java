package pl.sda.eventlift.events.services;

import pl.sda.eventlift.events.model.Countries;
import pl.sda.eventlift.events.pojo.EventDTO;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.concurrent.CompletableFuture;

public interface TicketmasterDiscovery {

    @GET("discovery/v2/events.json")
    CompletableFuture<EventsWrapper> findActualEvents(
            @Query("countryCode") String countryCode,
            @Query("sort") String sort,
            @Query("size") String size,
            @Query("apikey") String apiKey
    );

    @GET("discovery/v2/events.json")
    CompletableFuture<EventsWrapper> findEventsByKeyword(
            @Query("countryCode") Countries countryCode,
            @Query("city") String city,
            @Query("keyword") String keyword,
            @Query("sort") String sort,
            @Query("size") String size,
            @Query("apikey") String apiKey
    );

    @GET("discovery/v2/events/{id}.json")
    CompletableFuture<EventDTO> findEventById(
            @Path(value = "id", encoded = true) String id,
            @Query("apikey") String apiKey
    );
}