
package pl.sda.eventlift.events.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpcomingEvents {

    @SerializedName("_total")
    @Expose
    private Integer total;
    @SerializedName("mfx-pl")
    @Expose
    private Integer mfxPl;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getMfxPl() {
        return mfxPl;
    }

    public void setMfxPl(Integer mfxPl) {
        this.mfxPl = mfxPl;
    }

}
