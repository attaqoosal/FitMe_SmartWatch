package com.test.fitme.fragment;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.takisoft.datetimepicker.DatePickerDialog;
import com.test.fitme.GoalVO;
import com.test.fitme.R;
import com.test.fitme.RunVO;
import com.test.fitme.SQLiteHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;


/**
 * A simple {@link Fragment} subclass.
 */
public class RunFragment extends Fragment implements View.OnClickListener{
    private TextView dateTv, countTv, distTv, kcalTv;
    private ImageButton leftBtn, rightBtn;
    private Calendar cal;
    private PieChart mChart, mChart2, mChart3;
    private SQLiteHelper helper;

    public RunFragment() {
        // Required empty public constructor
    }

    public static RunFragment newInstance() {
        RunFragment runFragment = new RunFragment();
        return runFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_run, container, false);
        helper = new SQLiteHelper(getActivity());

        cal = Calendar.getInstance();
        String today = String.format("%d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
        dateTv = v.findViewById(R.id.run_date);
        dateTv.setText(today);
        dateTv.setOnClickListener(this);
        countTv = v.findViewById(R.id.run_count);
        distTv = v.findViewById(R.id.run_dist);
        kcalTv = v.findViewById(R.id.run_kcal);

        leftBtn = v.findViewById(R.id.run_leftbtn);
        leftBtn.setOnClickListener(this);
        rightBtn = v.findViewById(R.id.run_rightbtn);
        rightBtn.setOnClickListener(this);

        //Chart1
        mChart = v.findViewById(R.id.run_chart1);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 5, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(50f);
        mChart.setTransparentCircleRadius(61f);
        mChart.setDrawCenterText(true);
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        Legend l = mChart.getLegend();
        l.setEnabled(false);
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTextSize(12f);

        //Chart2
        mChart2 = v.findViewById(R.id.run_chart2);
        mChart2.setUsePercentValues(true);
        mChart2.getDescription().setEnabled(false);
        mChart2.setExtraOffsets(0, 0, 3, 0);
        mChart2.setDragDecelerationFrictionCoef(0.95f);
        mChart2.setCenterText(generateCenterSpannableText());
        mChart2.setDrawHoleEnabled(true);
        mChart2.setHoleColor(Color.WHITE);
        mChart2.setTransparentCircleColor(Color.WHITE);
        mChart2.setTransparentCircleAlpha(110);
        mChart2.setHoleRadius(50f);
        mChart2.setTransparentCircleRadius(61f);
        mChart2.setDrawCenterText(true);
        mChart2.setRotationAngle(0);
        mChart2.setRotationEnabled(true);
        mChart2.setHighlightPerTapEnabled(true);

        Legend l2 = mChart2.getLegend();
        l2.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l2.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l2.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l2.setDrawInside(false);
        l2.setXEntrySpace(7f);
        l2.setYEntrySpace(0f);
        l2.setYOffset(30f);

        // entry label styling
        mChart2.setEntryLabelColor(Color.parseColor("#ECEFF1"));
        mChart2.setEntryLabelTextSize(12f);

        //Chart3
        mChart3 = v.findViewById(R.id.run_chart3);
        mChart3.setUsePercentValues(true);
        mChart3.getDescription().setEnabled(false);
        mChart3.setExtraOffsets(3, 0, 0, 0);
        mChart3.setDragDecelerationFrictionCoef(0.95f);
        mChart3.setCenterText(generateCenterSpannableText2());
        mChart3.setDrawHoleEnabled(true);
        mChart3.setHoleColor(Color.WHITE);
        mChart3.setTransparentCircleColor(Color.WHITE);
        mChart3.setTransparentCircleAlpha(110);
        mChart3.setHoleRadius(50f);
        mChart3.setTransparentCircleRadius(61f);
        mChart3.setDrawCenterText(true);
        mChart3.setRotationAngle(0);
        mChart3.setRotationEnabled(true);
        mChart3.setHighlightPerTapEnabled(true);

        Legend l3 = mChart3.getLegend();
        l3.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l3.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l3.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l3.setDrawInside(false);
        l3.setXEntrySpace(7f);
        l3.setYEntrySpace(0f);
        l3.setYOffset(30f);

        // entry label styling
        mChart3.setEntryLabelColor(Color.WHITE);
        mChart3.setEntryLabelTextSize(12f);

        setData(today);
        return v;
    }

    private void setData(String date) {
        GoalVO goal = helper.select_goal(date);
        RunVO run = helper.select_run(date);
        ArrayList<PieEntry> entries1 = new ArrayList<PieEntry>();
        ArrayList<PieEntry> entries2 = new ArrayList<PieEntry>();
        ArrayList<PieEntry> entries3 = new ArrayList<PieEntry>();

        if(run != null) {
            Log.d("확인", "ㅇㅇ" + goal.getgRun_count());
            countTv.setText(String.valueOf(run.getCount()));
            distTv.setText(String.valueOf(run.getDist() + "m"));
            kcalTv.setText(String.valueOf(run.getKcal()) + "kcal");

            float rCount = goal.getgRun_count() - run.getCount();
            if (rCount < 0) {
                rCount = 0;
            }
            entries1.add(new PieEntry((float) run.getCount(), "걸음수"));
            entries1.add(new PieEntry(rCount, "남은양"));

            entries2.add(new PieEntry((float) run.getCount(), "거리"));
            entries2.add(new PieEntry(rCount, "남은양"));

            entries3.add(new PieEntry((float) run.getCount(), "칼로리"));
            entries3.add(new PieEntry(rCount, "남은양"));
        }else {
            countTv.setText(String.valueOf(0));
            distTv.setText("0m");
            kcalTv.setText("0kcal");

            entries1.add(new PieEntry(0.0f, "걸음수"));
            entries1.add(new PieEntry(100.0f, "남은양"));
            entries2.add(new PieEntry(0.0f, "거리"));
            entries2.add(new PieEntry(100.0f, "남은양"));
            entries3.add(new PieEntry(0.0f, "칼로리"));
            entries3.add(new PieEntry(100.0f, "남은양"));
        }

        PieDataSet dataSet1 = new PieDataSet(entries1, "");
        dataSet1.setDrawIcons(false);
        dataSet1.setSliceSpace(3f);
        dataSet1.setIconsOffset(new MPPointF(0, 40));
        dataSet1.setSelectionShift(0f);
        dataSet1.setColors(new int[] {Color.parseColor("#1A237E"), Color.parseColor("#0D47A1")});

        PieData data1 = new PieData(dataSet1);
        data1.setValueFormatter(new PercentFormatter());
        data1.setValueTextSize(11f);
        data1.setValueTextColor(Color.parseColor("#ECEFF1"));

        mChart.setData(data1);
        mChart.highlightValues(null);
        mChart.animateY(1000);
        mChart.invalidate();

        PieDataSet dataSet2 = new PieDataSet(entries2, "");
        dataSet2.setDrawIcons(false);
        dataSet2.setSliceSpace(3f);
        dataSet2.setIconsOffset(new MPPointF(0, 40));
        dataSet2.setSelectionShift(0f);
        dataSet2.setColors(new int[] {Color.parseColor("#004D40"), Color.parseColor("#2E7D32")});

        PieData data2 = new PieData(dataSet2);
        data2.setValueFormatter(new PercentFormatter());
        data2.setValueTextSize(11f);
        data2.setValueTextColor(Color.parseColor("#ECEFF1"));

        mChart2.setData(data2);
        mChart2.highlightValues(null);
        mChart2.animateY(1000);
        mChart2.invalidate();

        PieDataSet dataSet3 = new PieDataSet(entries3, "");
        dataSet3.setDrawIcons(false);
        dataSet3.setSliceSpace(3f);
        dataSet3.setIconsOffset(new MPPointF(0, 40));
        dataSet3.setSelectionShift(0f);
        dataSet3.setColors(new int[] {Color.parseColor("#d50000"), Color.parseColor("#FF3D00")});

        PieData data3 = new PieData(dataSet3);
        data3.setValueFormatter(new PercentFormatter());
        data3.setValueTextSize(11f);
        data3.setValueTextColor(Color.parseColor("#ECEFF1"));

        mChart3.setData(data3);
        mChart3.highlightValues(null);
        mChart3.animateY(1000);
        mChart3.invalidate();
    }

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("목표치 대비\n이동거리");
        s.setSpan(new RelativeSizeSpan(1.0f), 0, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length() , 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
        return s;
    }

    private SpannableString generateCenterSpannableText2() {
        SpannableString s = new SpannableString("목표치 대비\n칼로리");
        s.setSpan(new RelativeSizeSpan(1.0f), 0, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length() , 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
        return s;
    }

    @Override
    public void onClick(View v) {
        StringTokenizer st = new StringTokenizer(dateTv.getText().toString(), "-");
        int mYear = Integer.parseInt(st.nextToken());
        int mMonth = Integer.parseInt(st.nextToken())-1;
        int mDate = Integer.parseInt(st.nextToken());
        switch (v.getId()) {
            case R.id.run_date:
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), (view1, year, month, dayOfMonth) -> {
                    String selectDate = String.format("%d-%02d-%02d", year, month+1, dayOfMonth);
                    dateTv.setText(selectDate);
                    cal.set(year, month, dayOfMonth);
                    setData(selectDate);
                }, mYear, mMonth, mDate);
                dpd.show();
                break;
            case R.id.run_leftbtn:
                cal.add(Calendar.DATE, -1);
                String yesterday = String.format("%d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
                dateTv.setText(yesterday);
                setData(yesterday);
                break;
            case R.id.run_rightbtn:
                cal.add(Calendar.DATE, 1);
                String tomorrow = String.format("%d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
                dateTv.setText(tomorrow);
                setData(tomorrow);
                break;
        }
    }
}
