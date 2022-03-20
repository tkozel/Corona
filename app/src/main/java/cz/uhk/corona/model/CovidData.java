package cz.uhk.corona.model;

import java.util.Date;
import java.util.List;

/**
 * Wrapper, v nemz REST WS vraci response
 * @see DayStats
 * @deprecated v nove verzi JSONu neni pouzivano
 */
@Deprecated
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
