package com.test.fitme.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.takisoft.datetimepicker.DatePickerDialog;
import com.test.fitme.BarbellVO;
import com.test.fitme.DumbBellVO;
import com.test.fitme.GoalVO;
import com.test.fitme.JumpRopeVO;
import com.test.fitme.SQLiteHelper;
import com.test.fitme.SitUpVO;
import com.test.fitme.graph.HealthAxisValueFormatter;
import com.test.fitme.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;


/**
 * A simple {@link Fragment} subclass.
 */
public class HealthFragment extends Fragment implements View.OnClickListener, OnChartValueSelectedListener {
    private TextView dateTv;
    private ImageButton leftBtn, rightBtn;
    private Calendar cal;
    protected BarChart mChart;
    private SQLiteHelper helper;
    private String today;

    public HealthFragment() {
        // Required empty public constructor
    }
    public static HealthFragment newInstance() {
        HealthFragment healthFragment = new HealthFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return healthFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_health, container, false);
        helper = new SQLiteHelper(getActivity());

        cal = Calendar.getInstance();
        today = String.format("%d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));

        dateTv = v.findViewById(R.id.health_date);
        dateTv.setText(today);
        dateTv.setOnClickListener(this);

        leftBtn = v.findViewById(R.id.health_leftbtn);
        leftBtn.setOnClickListener(this);
        rightBtn = v.findViewById(R.id.health_rightbtn);
        rightBtn.setOnClickListener(this);

        mChart = v.findViewById(R.id.health_chart);
        mChart.setOnChartValueSelectedListener(this);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        String[] values = {"줄넘기", "싯업", "덤벨", "바벨"};
        IAxisValueFormatter xAxisFormatter = new HealthAxisValueFormatter(values);
        //IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(4);
        xAxis.setValueFormatter(xAxisFormatter);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(5, false);
        // leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setAxisMaximum(100f);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxis.setAxisMaximum(100f);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

//        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
//        mv.setChartView(mChart); // For bounds control
//        mChart.setMarker(mv); // Set the marker to the chart

        getGraphInfo(today);
        return v;
    }

    private void getGraphInfo(String date) {
        GoalVO goal = helper.select_goal(date);
        SitUpVO situp = helper.select_situp(date);
        DumbBellVO dumbBell = helper.select_dumbBell(date);
        BarbellVO barbell = helper.select_barbell(date);
        JumpRopeVO jumpRope = helper.select_jumpRope(date);

        float sRate = 0, dRate = 0, bRate = 0, jRate = 0;
        if(goal != null) {
            if (situp != null && situp.getCount() > 0) {
                sRate = (float) situp.getCount() / (float) goal.getgSitup_count() * 100.0f;
                if (sRate >= 100.0f) {
                    sRate = 100.0f;
                }
            }
            if (dumbBell != null && dumbBell.getCount() > 0) {
                dRate = (float) dumbBell.getCount() / (float) goal.getgDumbbell_count() * 100.0f;
                if (dRate >= 100.0f) {
                    dRate = 100.0f;
                }
            }
            if (barbell != null && barbell.getCount() > 0) {
                bRate = (float) barbell.getCount() / (float) goal.getgBarbell_count() * 100.0f;
                if (bRate >= 100.0f) {
                    bRate = 100.0f;
                }
            }
            if (jumpRope != null && jumpRope.getCount() > 0) {
                jRate = (float) jumpRope.getCount() / (float) goal.getgJumprope_count() * 100.0f;
                if (jRate >= 100.0f) {
                    jRate = 100.0f;
                }
            }
        }
        setData(new float[] {sRate, dRate, bRate, jRate});
        mChart.setFitBars(true);
        mChart.animateY(1500);
    }

    private void setData(float[] value) {
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        Log.d("확인", value[0] + " " + value[1] + " " + value[2] + " " + value[3]);
        yVals1.add(new BarEntry(1f, value[0]));
        yVals1.add(new BarEntry(2f, value[1]));
        yVals1.add(new BarEntry(3f, value[2]));
        yVals1.add(new BarEntry(4f, value[3]));

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "하루 기준 목표 달성률");
            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(12f);
            data.setBarWidth(0.5f);

            mChart.setData(data);
        }
    }

    @Override
    public void onClick(View v) {
        StringTokenizer st = new StringTokenizer(dateTv.getText().toString(), "-");
        int mYear = Integer.parseInt(st.nextToken());
        int mMonth = Integer.parseInt(st.nextToken())-1;
        int mDate = Integer.parseInt(st.nextToken());
        switch (v.getId()) {
            case R.id.health_date:
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), (view1, year, month, dayOfMonth) -> {
                    String selectDate = String.format("%d-%02d-%02d", year, month+1, dayOfMonth);
                    dateTv.setText(selectDate);
                    cal.set(year, month, dayOfMonth);
                    getGraphInfo(selectDate);
                    //Toast.makeText(getActivity(), String.format("%d", year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth), Toast.LENGTH_SHORT).show();
                }, mYear, mMonth, mDate);
                dpd.show();

                break;
            case R.id.health_leftbtn:
                cal.add(Calendar.DATE, -1);
                String yesterday = String.format("%d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
                dateTv.setText(yesterday);
                getGraphInfo(yesterday);
                break;
            case R.id.health_rightbtn:
                cal.add(Calendar.DATE, 1);
                String tomorrow = String.format("%d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
                dateTv.setText(tomorrow);
                getGraphInfo(tomorrow);
                break;
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
