package cz.uhk.corona;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

import cz.uhk.corona.db.CovidDatabase;
import cz.uhk.corona.model.DayStats;

public class ChartActivity extends AppCompatActivity {
    LineChart chart;
    List<DayStats> data;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dateFormat = new SimpleDateFormat("EE d.M");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        chart = findViewById(R.id.chart);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initChart();
    }


    private void initChart() {
        Executors.newSingleThreadExecutor().execute( () -> {
            data = CovidDatabase.getInstance(this).getDayStatsDao().getAll();
            Collections.reverse(data);
            List<Entry> entries = new ArrayList<>();
            int i = 0;
            for (DayStats ds : data) {
                entries.add(new Entry((float)(ds.getDay().getTime()/3600000/24+1), ds.getPositive()));
            }

            LineDataSet dataSet = new LineDataSet(entries, "Positive");
            dataSet.setColor(Color.RED);
            LineData lineData = new LineData(dataSet);

            chart.setData(lineData);
            XAxis xAxis = chart.getXAxis();
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return dateFormat.format(new Date((long)(value*3600000*24)));
                }
            });
            xAxis.setEnabled(true);
            xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);

            chart.getDescription().setText("COVID-19");
            //chart.setAutoScaleMinMaxEnabled(true);
            chart.setPinchZoom(false);
            chart.setDragEnabled(true);
            chart.setDoubleTapToZoomEnabled(true);
            chart.setVisibleXRangeMaximum(30f);
            chart.invalidate();
        });
    }
}