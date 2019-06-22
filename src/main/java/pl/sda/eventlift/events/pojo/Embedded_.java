
package pl.sda.eventlift.events.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Embedded_ {

    @SerializedName("venues")
    @Expose
    private List<Venue_> venues = null;
    @SerializedName("attractions")
    @Expose
    private List<Attraction_> attractions = null;

    public Venue_ getVenue() {
        return venues.get(0);
    }

    public List<Venue_> getVenues() {
        return venues;
    }

    public void setVenues(List<Venue_> venues) {
        this.venues = venues;
    }

    public List<Attraction_> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<Attraction_> attractions) {
        this.attractions = attractions;
    }

}
