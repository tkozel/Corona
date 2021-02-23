package cz.uhk.corona.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DayStats {
    @SerializedName("datum")
    private Date day;
    @SerializedName("pocet_PCR_testy")
    private int pcr;
    @SerializedName("pocet_AG_testy")
    private int anti;
    @SerializedName("incidence_pozitivni")
    private int positive;

    public DayStats() {
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public int getPcr() {
        return pcr;
    }

    public void setPcr(int pcr) {
        this.pcr = pcr;
    }

    public int getAnti() {
        return anti;
    }

    public void setAnti(int anti) {
        this.anti = anti;
    }

    public int getPositive() {
        return positive;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }
}
