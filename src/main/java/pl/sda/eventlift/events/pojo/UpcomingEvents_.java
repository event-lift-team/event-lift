
package pl.sda.eventlift.events.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpcomingEvents_ {

    @SerializedName("_total")
    @Expose
    private Integer total;
    @SerializedName("mfx-nl")
    @Expose
    private Integer mfxNl;
    @SerializedName("ticketmaster")
    @Expose
    private Integer ticketmaster;
    @SerializedName("mfx-be")
    @Expose
    private Integer mfxBe;
    @SerializedName("mfx-pl")
    @Expose
    private Integer mfxPl;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getMfxNl() {
        return mfxNl;
    }

    public void setMfxNl(Integer mfxNl) {
        this.mfxNl = mfxNl;
    }

    public Integer getTicketmaster() {
        return ticketmaster;
    }

    public void setTicketmaster(Integer ticketmaster) {
        this.ticketmaster = ticketmaster;
    }

    public Integer getMfxBe() {
        return mfxBe;
    }

    public void setMfxBe(Integer mfxBe) {
        this.mfxBe = mfxBe;
    }

    public Integer getMfxPl() {
        return mfxPl;
    }

    public void setMfxPl(Integer mfxPl) {
        this.mfxPl = mfxPl;
    }

}
