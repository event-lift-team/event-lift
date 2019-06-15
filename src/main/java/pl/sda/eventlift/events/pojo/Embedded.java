
package pl.sda.eventlift.events.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Embedded {

    @SerializedName("events")
    @Expose
    private List<EventDTO> events = null;

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }

}
