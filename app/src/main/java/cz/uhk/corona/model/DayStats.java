package cz.uhk.corona.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Entita dennich statistik testovani COVID-19
 * pouziva se jak pro ws/REST/JSON, tak pro ulozeni do DB Room
 * @see SerializedName
 * @see androidx.room.Room
 */
@Entity
public class DayStats {
    @SerializedName("datum")  //mapovani atributu na JSON property name
    @PrimaryKey
    private Date day;  //do DB se bude konvertovat automaticky na timestamp
                       // (viz CovidDatabase/@TypeConverters)
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
