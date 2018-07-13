package com.test.fitme.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.takisoft.datetimepicker.DatePickerDialog;
import com.test.fitme.HeartBeatVO;
import com.test.fitme.R;
import com.test.fitme.SQLiteHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;


/**
 * A simple {@link Fragment} subclass.
 */
public class HeartBeatFragment extends Fragment implements View.OnClickListener, OnChartValueSelectedListener {
    private TextView dateTv, maxTv, minTv;
    private ImageButton leftBtn, rightBtn;
    private Calendar cal;
    private ScatterChart mChart;
    private SQLiteHelper helper;

    public HeartBeatFragment() {
        // Required empty public constructor
    }

    public static HeartBeatFragment newInstance() {
        HeartBeatFragment heartBeatFragment = new HeartBeatFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return heartBeatFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_heart_beat, container, false);
        helper = new SQLiteHelper(getActivity());

        cal = Calendar.getInstance();
        String today = String.format("%d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
        dateTv = v.findViewById(R.id.heartbeat_date);
        dateTv.setText(today);
        dateTv.setOnClickListener(this);
        maxTv = v.findViewById(R.id.heartbeat_max_bpm);
        minTv = v.findViewById(R.id.heartbeat_min_bpm);

        leftBtn = v.findViewById(R.id.heartbeat_leftbtn);
        leftBtn.setOnClickListener(this);
        rightBtn = v.findViewById(R.id.heartbeat_rightbtn);
        rightBtn.setOnClickListener(this);

        mChart = v.findViewById(R.id.heartbeat_chart);
        mChart.getDescription().setEnabled(false);
        mChart.setOnChartValueSelectedListener(this);

        mChart.setDrawGridBackground(true); // 그래프 배경
        mChart.setTouchEnabled(true);
        mChart.setMaxHighlightDistance(50f); //?

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        mChart.setMaxVisibleValueCount(24);
        mChart.setPinchZoom(true);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXOffset(5f);

        YAxis yl = mChart.getAxisLeft();
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        yl.setAxisMinimum(40f);

        XAxis xl = mChart.getXAxis();
        xl.setDrawGridLines(false);
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);

        setData(today);
        return v;
    }

    @Override
    public void onClick(View v) {
        StringTokenizer st = new StringTokenizer(dateTv.getText().toString(), "-");
        int mYear = Integer.parseInt(st.nextToken());
        int mMonth = Integer.parseInt(st.nextToken())-1;
        int mDate = Integer.parseInt(st.nextToken());
        switch (v.getId()) {
            case R.id.heartbeat_date:
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), (view1, year, month, dayOfMonth) -> {
                    String selectDate = String.format("%d-%02d-%02d", year, month+1, dayOfMonth);
                    dateTv.setText(selectDate);
                    cal.set(year, month, dayOfMonth);
                    setData(selectDate);
                    //Toast.makeText(getActivity(), String.format("%d", year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth), Toast.LENGTH_SHORT).show();
                }, mYear, mMonth, mDate);
                dpd.show();
                break;
            case R.id.heartbeat_leftbtn:
                cal.add(Calendar.DATE, -1);
                String yesterday = String.format("%d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
                dateTv.setText(yesterday);
                setData(yesterday);
                break;
            case R.id.heartbeat_rightbtn:
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

    public void setData(String date) {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
        ArrayList<HeartBeatVO> heartBeats = helper.selectAll_heartBeat(date);

        if(heartBeats.size() > 0) {
            int maxAvg = 0, minAvg = 0;
            for (int i = 0; i < 24; i++) {
                yVals1.add(new Entry(i, heartBeats.get(i).getMax()));
                yVals2.add(new Entry(i, heartBeats.get(i).getMin()));
                maxAvg += heartBeats.get(i).getMax();
                minAvg += heartBeats.get(i).getMin();
            }
            maxAvg = maxAvg / 24;
            minAvg = minAvg / 24;
            maxTv.setText(String.valueOf(maxAvg) + " bpm");
            minTv.setText(String.valueOf(minAvg) + " bpm");
        }else {
            maxTv.setText("0bpm");
            minTv.setText("0bpm");
        }
        // create a dataset and give it a type
        ScatterDataSet set1 = new ScatterDataSet(yVals1, "최대 심박수");
        set1.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set1.setScatterShapeHoleRadius(3f);
        set1.setColor(Color.RED);
        set1.setScatterShapeHoleColor(Color.RED);

        ScatterDataSet set2 = new ScatterDataSet(yVals2, "최소 심박수");
        set2.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set2.setScatterShapeHoleRadius(3f);
        set2.setColor(Color.BLUE);
        set2.setScatterShapeHoleColor(Color.BLUE);

        set1.setScatterShapeSize(8f);
        set2.setScatterShapeSize(8f);

        ArrayList<IScatterDataSet> dataSets = new ArrayList<IScatterDataSet>();
        dataSets.add(set1); // add the datasets
        dataSets.add(set2);

        // create a data object with the datasets
        ScatterData data = new ScatterData(dataSets);

        mChart.setData(data);
        mChart.getAxisRight().setEnabled(false);
        mChart.animateXY(2000, 2000);
        mChart.invalidate();
    }
}
