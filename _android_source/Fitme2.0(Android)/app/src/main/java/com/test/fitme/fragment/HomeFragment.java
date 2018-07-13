package com.test.fitme.fragment;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.RectF;
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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.takisoft.datetimepicker.DatePickerDialog;
import com.test.fitme.GoalVO;
import com.test.fitme.RunVO;
import com.test.fitme.SQLiteHelper;
import com.test.fitme.WalkVO;
import com.test.fitme.graph.WalkAxisValueFormatter;
import com.test.fitme.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    private TextView dateTv, countTv, distTv, kcalTv;
    private ImageButton leftBtn, rightBtn;
    private Calendar cal;
    private PieChart mChart, mChart2, mChart3;
    private SQLiteHelper helper;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public HomeFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        helper = new SQLiteHelper(getActivity());
        sp = getActivity().getSharedPreferences("login", 0);
        editor = sp.edit();

        cal = Calendar.getInstance();
        dateTv = v.findViewById(R.id.home_date);
        String today = String.format("%d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
        dateTv.setText(today);
        dateTv.setOnClickListener(this);
        countTv = v.findViewById(R.id.home_count);
        distTv = v.findViewById(R.id.home_dist);
        kcalTv = v.findViewById(R.id.home_kcal);

        leftBtn = v.findViewById(R.id.home_leftbtn);
        leftBtn.setOnClickListener(this);
        rightBtn = v.findViewById(R.id.home_rightbtn);
        rightBtn.setOnClickListener(this);

        //Chart1
        mChart = v.findViewById(R.id.home_chart1);
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
        mChart2 = v.findViewById(R.id.home_chart2);
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
        mChart3 = v.findViewById(R.id.home_chart3);
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
        WalkVO walk = helper.select_walk(date);
        ArrayList<PieEntry> entries1 = new ArrayList<PieEntry>();
        ArrayList<PieEntry> entries2 = new ArrayList<PieEntry>();
        ArrayList<PieEntry> entries3 = new ArrayList<PieEntry>();

        //walk가 null이 아니면 goal도 당연히 null이 아니기 때문에 이렇게 코딩함
        if(walk != null) {
            countTv.setText(String.valueOf(walk.getCount()));
            distTv.setText(String.valueOf(walk.getDist() + "m"));
            kcalTv.setText(String.valueOf(walk.getKcal()) + "kcal");

            float wCount = goal.getgWalk_count() - walk.getCount();
            if (wCount < 0) {
                wCount = 0;
            }
            entries1.add(new PieEntry((float) walk.getCount(), "걸음수"));
            entries1.add(new PieEntry(wCount, "남은양"));

            entries2.add(new PieEntry((float) walk.getCount(), "거리"));
            entries2.add(new PieEntry(wCount, "남은양"));

            entries3.add(new PieEntry((float) walk.getCount(), "칼로리"));
            entries3.add(new PieEntry(wCount, "남은양"));
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
        dataSet1.setColors(new int[] {Color.parseColor("#01579b"), Color.parseColor("#0277bd")});

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
        dataSet2.setColors(new int[] {Color.parseColor("#ef6c00"), Color.parseColor("#ffab00")});

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
        dataSet3.setColors(new int[] {Color.parseColor("#00695c"), Color.parseColor("#00897b")});

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
            case R.id.home_date:
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), (view1, year, month, dayOfMonth) -> {
                    String selectDate = String.format("%d-%02d-%02d", year, month+1, dayOfMonth);
                    dateTv.setText(selectDate);
                    cal.set(year, month, dayOfMonth);
                    setData(selectDate);
                }, mYear, mMonth, mDate);
                dpd.show();
                break;
            case R.id.home_leftbtn:
                cal.add(Calendar.DATE, -1);
                String yesterday = String.format("%d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
                dateTv.setText(yesterday);
                setData(yesterday);
                break;
            case R.id.home_rightbtn:
                cal.add(Calendar.DATE, 1);
                String tomorrow = String.format("%d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
                dateTv.setText(tomorrow);
                setData(tomorrow);
                break;
        }
    }

//    protected RectF mOnValueSelectedRectF = new RectF();
//    @SuppressLint("NewApi")
//    @Override
//    public void onValueSelected(Entry e, Highlight h) {
//        if (e == null)
//            return;
//
//        RectF bounds = mOnValueSelectedRectF;
//        mChart.getBarBounds((BarEntry) e, bounds);
//
//        MPPointF position = mChart.getPosition(e, mChart.getData().getDataSetByIndex(h.getDataSetIndex())
//                .getAxisDependency());
//
//        Log.i("bounds", bounds.toString());
//        Log.i("position", position.toString());
//
//        MPPointF.recycleInstance(position);
//    }
//
//    @Override
//    public void onNothingSelected() {
//
//    }
}


//package com.test.fitme;
//
//
//        import android.annotation.SuppressLint;
//        import android.graphics.Color;
//        import android.graphics.RectF;
//        import android.os.Bundle;
//        import android.support.v4.app.Fragment;
//        import android.util.Log;
//        import android.view.LayoutInflater;
//        import android.view.View;
//        import android.view.ViewGroup;
//        import android.widget.Button;
//        import android.widget.TextView;
//        import android.widget.Toast;
//
//        import com.github.mikephil.charting.charts.HorizontalBarChart;
//        import com.github.mikephil.charting.components.Legend;
//        import com.github.mikephil.charting.components.XAxis;
//        import com.github.mikephil.charting.components.YAxis;
//        import com.github.mikephil.charting.data.BarData;
//        import com.github.mikephil.charting.data.BarDataSet;
//        import com.github.mikephil.charting.data.BarEntry;
//        import com.github.mikephil.charting.data.Entry;
//        import com.github.mikephil.charting.formatter.LargeValueFormatter;
//        import com.github.mikephil.charting.highlight.Highlight;
//        import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
//        import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
//        import com.github.mikephil.charting.utils.MPPointF;
//        import com.takisoft.datetimepicker.DatePickerDialog;
//        import com.takisoft.datetimepicker.widget.DatePicker;
//
//        import java.util.ArrayList;
//        import java.util.Calendar;
//        import java.util.Date;
//        import java.util.StringTokenizer;
//
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link HomeFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class HomeFragment extends Fragment implements View.OnClickListener, OnChartValueSelectedListener{
//    private TextView dateTv;
//    private Button leftBtn, rightBtn;
//    private Calendar cal;
//    protected HorizontalBarChart mChart;
//
//    public HomeFragment() {
//        // Required empty public constructor
//    }
//    // TODO: Rename and change types and number of parameters
//    public static HomeFragment newInstance() {
//        HomeFragment homeFragment = new HomeFragment();
////        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
////        fragment.setArguments(args);
//        return homeFragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
////            mParam1 = getArguments().getString(ARG_PARAM1);
////            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View v = inflater.inflate(R.layout.fragment_home, container, false);
//        cal = Calendar.getInstance();
//        dateTv = v.findViewById(R.id.home_date);
//        dateTv.setText(String.format("%d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE)));
//        dateTv.setOnClickListener(this);
//
//        leftBtn = v.findViewById(R.id.home_leftbtn);
//        leftBtn.setOnClickListener(this);
//        rightBtn = v.findViewById(R.id.home_rightbtn);
//        rightBtn.setOnClickListener(this);
//
//        mChart = v.findViewById(R.id.chart1);
//        mChart.setOnChartValueSelectedListener(this);
//
//        mChart.setDrawBarShadow(false); //막대 그림자
//
//        mChart.setDrawValueAboveBar(true);
//
//        mChart.getDescription().setEnabled(true);
//        mChart.getDescription().setText("비율");
//
//
//        mChart.setMaxVisibleValueCount(3);
//
//        mChart.setPinchZoom(false);
//
//        mChart.setDrawGridBackground(false); //배경그림자
//
//        XAxis xl = mChart.getXAxis(); //x축
//        xl.setEnabled(false);
//        xl.setSpaceMax(10f);
//        xl.setSpaceMin(10f);
////        xl.setDrawLabels(false);
////        xl.setDrawAxisLine(false);
////        xl.setDrawGridLines(false);
////        xl.setDrawLabels(false); //축 옆 글씨
////        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
//////        xl.setTypeface(mTfLight);
////        xl.setGranularity(10f);
//
//        YAxis yl = mChart.getAxisLeft();
////        yl.setTypeface(mTfLight);
//        yl.setDrawAxisLine(true); //겉선 한줄
//        yl.setDrawGridLines(false); //내부 선
//        yl.setDrawLabels(false); //축 옆 글씨
//        yl.setAxisMinimum(0f); // //없으면 값 이상함
//
//        YAxis yr = mChart.getAxisRight();
//        yr.setDrawAxisLine(true);
//        yr.setDrawGridLines(true);
//        yr.setAxisMinimum(0f); // 표시되는 최소값
//        yr.setAxisMaximum(100f); // 쵸시되는 최대값
//
//        setData(1, 100);
//        mChart.setFitBars(true);
//        mChart.animateY(2500);
//
//        // setting data
//
//        //아래 문구
//        Legend l = mChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//
//        l.setDrawInside(false);
//        l.setYEntrySpace(5f); //이거 맞는데 안먹힘...
//        l.setXEntrySpace(10f);
////        l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] {"aaa", "bbb"});
////        ArrayList<LegendEntry> al = new ArrayList<>();
////        al.add(new LegendEntry());
////        l.setFormSize(20f);//아이콘크기
////        l.setXEntrySpace(20f); //사이거리
//        l.setWordWrapEnabled(true);
//        return v;
//    }
//
//    private void setData(int count, float range) {
//        //count 화면에 보여지는 막대 수
//        float barWidth = 2f;
//        float spaceForBar = 10f;
//        float groupSpace = 1f; //막대그룹 사이 사이 여백
//
//        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
////        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
////        ArrayList<BarEntry> yVals3 = new ArrayList<BarEntry>();
////
////        for (int i = 0; i < count; i++) {
////            float val = (float) (Math.random() * range);
////            yVals1.add(new BarEntry(i * spaceForBar, 77));
////            yVals2.add(new BarEntry((i+1) * spaceForBar, 55));
////            yVals3.add(new BarEntry((i+2) * spaceForBar, 66));
////        }
//        yVals1.add(new BarEntry(0, 77));
//        yVals1.add(new BarEntry(10, 55  ));
//        yVals1.add(new BarEntry(20, 66));
//
//        BarDataSet set1, set2, set3;
//        if (mChart.getData() != null &&
//                mChart.getData().getDataSetCount() > 0) {
////            set1 = (BarDataSet)mChart.getData().getDataSetByIndex(0);
////            set1.setValues(yVals1);
////            mChart.getData().notifyDataChanged();
////            mChart.notifyDataSetChanged();
//        } else {
//            set1 = new BarDataSet(yVals1, "걸음");
//            set1.setColors(Color.rgb(104, 241, 175), Color.rgb(164, 228, 251), Color.rgb(242, 247, 158));
//            //set1.setStackLabels(new String[]{"거리", "칼로리"});
//
//            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
//            dataSets.add(set1);
//            BarData data = new BarData(dataSets);
//            data.setDrawValues(true);
//            data.setBarWidth(barWidth);
//            data.setValueTextSize(20f);
//            mChart.setData(data);
////            set1 = new BarDataSet(yVals1, "걸음");
////
////           // set1.setDrawIcons(false); //막대에 그려지는 아이콘
////            set1.setColor(Color.rgb(104, 241, 175));
////            //set1.setDrawValues(true);
//////            set1.setFormSize(20f);
//////            set1.setValueTextSize(20);
////            set2 = new BarDataSet(yVals2, "거리");
////            set2.setColor(Color.rgb(164, 228, 251));
//////            set2.setFormSize(20f);
//////            set2.setValueTextSize(20);
////            set3 = new BarDataSet(yVals3, "칼로리");
////            set3.setColor(Color.rgb(242, 247, 158));
//////            set3.setFormSize(20f);
//////            set3.setValueTextSize(20);
//////            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
//////            dataSets.add(set1);
//////            BarData data = new BarData(dataSets);
////
////            BarData data = new BarData(set1, set2, set3);
////            data.groupBars(5f, 0f, 3f);
////            //data.setValueFormatter(new LargeValueFormatter());
////            data.setDrawValues(true);
////            data.setValueTextSize(1f); //막대옆 글씨크기
//////            data.setValueTypeface(mTfLight);
////            data.setBarWidth(barWidth);
////            mChart.setData(data);
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        StringTokenizer st = new StringTokenizer(dateTv.getText().toString(), "-");
//        int mYear = Integer.parseInt(st.nextToken());
//        int mMonth = Integer.parseInt(st.nextToken())-1;
//        int mDate = Integer.parseInt(st.nextToken());
//        switch (v.getId()) {
//            case R.id.home_date:
//                DatePickerDialog dpd = new DatePickerDialog(getActivity(), (view1, year, month, dayOfMonth) -> {
//                    dateTv.setText(String.format("%d-%02d-%02d", year, month+1, dayOfMonth));
//                    cal.set(year, month, dayOfMonth);
//                    Toast.makeText(getActivity(), String.format("%d", year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth), Toast.LENGTH_SHORT).show();
//                }, mYear, mMonth, mDate);
//                dpd.show();
////                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
////                    @Override
////                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
////
////                        String msg = String.format("%d 년 %d 월 %d 일", year, month+1, date);
////                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
////                    }
////                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
////
////                dialog.getDatePicker().setMaxDate(new Date().getTime());    //입력한 날짜 이후로 클릭 안되게 옵션
////                dialog.show();
//                break;
//            case R.id.home_leftbtn:
//                cal.add(Calendar.DATE, -1);
//                dateTv.setText(String.format("%d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE)));
//                break;
//            case R.id.home_rightbtn:
//                cal.add(Calendar.DATE, 1);
//                dateTv.setText(String.format("%d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE)));
//                break;
//        }
//    }
//
//    protected RectF mOnValueSelectedRectF = new RectF();
//    @SuppressLint("NewApi")
//    @Override
//    public void onValueSelected(Entry e, Highlight h) {
//        if (e == null)
//            return;
//
//        RectF bounds = mOnValueSelectedRectF;
//        mChart.getBarBounds((BarEntry) e, bounds);
//
//        MPPointF position = mChart.getPosition(e, mChart.getData().getDataSetByIndex(h.getDataSetIndex())
//                .getAxisDependency());
//
//        Log.i("bounds", bounds.toString());
//        Log.i("position", position.toString());
//
//        MPPointF.recycleInstance(position);
//    }
//
//    @Override
//    public void onNothingSelected() {
//
//    }
//}

