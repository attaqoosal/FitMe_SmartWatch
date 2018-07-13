package com.test.fitme.fragment;


import android.graphics.Color;
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

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.takisoft.datetimepicker.DatePickerDialog;
import com.test.fitme.R;
import com.test.fitme.SQLiteHelper;
import com.test.fitme.SleepVO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.StringTokenizer;


/**
 * A simple {@link Fragment} subclass.
 */
public class SleepFragment extends Fragment implements View.OnClickListener, OnChartValueSelectedListener {
    private TextView dateTv;
    private ImageButton leftBtn, rightBtn;
    private Calendar cal;
    private LineChart mChart;
    private SQLiteHelper helper;
    private String today;


    public SleepFragment() {
        // Required empty public constructor
    }

    public static SleepFragment newInstance() {
        SleepFragment sleepFragment = new SleepFragment();
        return sleepFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sleep, container, false);
        helper = new SQLiteHelper(getActivity());

        cal = Calendar.getInstance();
        today = String.format("%d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
        dateTv = v.findViewById(R.id.sleep_date);
        dateTv.setText(today);
        dateTv.setOnClickListener(this);

        leftBtn = v.findViewById(R.id.sleep_leftbtn);
        leftBtn.setOnClickListener(this);
        rightBtn = v.findViewById(R.id.sleep_rightbtn);
        rightBtn.setOnClickListener(this);

        mChart = v.findViewById(R.id.sleep_chart);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

        // no description text
        mChart.getDescription().setEnabled(true);
        mChart.getDescription().setText("값이 높을수록 얕은수면 낮을수록 깊은수면");

        // enable touch gestures
        mChart.setTouchEnabled(true);
        mChart.setDrawBorders(true);
        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
//        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
//        mv.setChartView(mChart); // For bounds control
//        mChart.setMarker(mv); // Set the marker to the chart

        XAxis xl = mChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setAvoidFirstLastClipping(true);
        xl.setAxisMinimum(0f);
        xl.setAxisMaximum(23f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setSpaceTop(15f);
        leftAxis.setSpaceBottom(15f);
//        leftAxis.setAxisMinimum(-15f); // this replaces setStartAtZero(true)
//        leftAxis.setAxisMaximum(15f);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        setData(today);

        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);

        return v;
    }

    @Override
    public void onClick(View v) {
        StringTokenizer st = new StringTokenizer(dateTv.getText().toString(), "-");
        int mYear = Integer.parseInt(st.nextToken());
        int mMonth = Integer.parseInt(st.nextToken())-1;
        int mDate = Integer.parseInt(st.nextToken());
        switch (v.getId()) {
            case R.id.sleep_date:
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), (view1, year, month, dayOfMonth) -> {
                    String selectDate = String.format("%d-%02d-%02d", year, month+1, dayOfMonth);
                    dateTv.setText(selectDate);
                    cal.set(year, month, dayOfMonth);
                    setData(selectDate);
                    //Toast.makeText(getActivity(), String.format("%d", year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth), Toast.LENGTH_SHORT).show();
                }, mYear, mMonth, mDate);
                dpd.show();
                break;
            case R.id.sleep_leftbtn:
                cal.add(Calendar.DATE, -1);
                String yesterday = String.format("%d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
                dateTv.setText(yesterday);
                setData(yesterday);
                break;
            case R.id.sleep_rightbtn:
                cal.add(Calendar.DATE, 1);
                String tomorrow = String.format("%d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
                dateTv.setText(tomorrow);
                setData(tomorrow);
                break;
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private void setData(String date) {
        ArrayList<Entry> entries = new ArrayList<Entry>();
        ArrayList<SleepVO> sleeps = helper.selectAll_sleep(date);
        Log.d("확인", "aa" + sleeps.size());
        if(sleeps.size() > 0) {
            for (int i = 0; i < sleeps.size(); i++) {
                entries.add(new Entry(i, sleeps.get(i).getValue()));
            }
            Collections.sort(entries, new EntryXComparator());
        }else {
            for(int i = 0; i < 24; i++) {
                entries.add(new Entry());
            }
        }
        // sort by x-value

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(entries, "수면 상태도");
        set1.setLineWidth(1.5f);
        set1.setCircleRadius(4f);
        //set1.setColor(Color.parseColor("#000000"));

        // create a data object with the datasets
        LineData data = new LineData(set1);

        // set data
        mChart.setData(data);
        mChart.animateY(2000);
        mChart.invalidate();
    }
}
