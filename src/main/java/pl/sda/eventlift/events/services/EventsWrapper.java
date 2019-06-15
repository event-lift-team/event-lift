package pl.sda.eventlift.events.services;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import pl.sda.eventlift.events.pojo.Embedded;
import pl.sda.eventlift.events.pojo.Links___;
import pl.sda.eventlift.events.pojo.Page;

public class EventsWrapper {

    @SerializedName("_embedded")
    @Expose
    private Embedded embedded;
    @SerializedName("_links")
    @Expose
    private Links___ links___;
    @SerializedName("page")
    @Expose
    private Page page;

    public Embedded getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this.embedded = embedded;
    }

    public Links___ getLinks___() {
        return links___;
    }

    public void setLinks___(Links___ links___) {
        this.links___ = links___;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
