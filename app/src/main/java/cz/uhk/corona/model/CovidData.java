package cz.uhk.corona.model;

import java.util.Date;
import java.util.List;

public class CovidData {
    private Date modified;
    private List<DayStats> data;

    public CovidData() {
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public List<DayStats> getData() {
        return data;
    }

    public void setData(List<DayStats> data) {
        this.data = data;
    }
}
