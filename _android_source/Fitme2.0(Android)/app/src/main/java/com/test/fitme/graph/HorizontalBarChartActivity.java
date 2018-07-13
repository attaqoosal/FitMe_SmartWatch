
package com.test.fitme.graph;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.test.fitme.R;

import java.util.ArrayList;

public class HorizontalBarChartActivity extends DemoBase implements OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    protected HorizontalBarChart mChart;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_horizontalbarchart);

        tvX = findViewById(R.id.tvXMax);
        tvY = findViewById(R.id.tvYMax);

        mSeekBarX = findViewById(R.id.seekBar1);
        mSeekBarY = findViewById(R.id.seekBar2);

        mChart = findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);
        // mChart.setHighlightEnabled(false);

        mChart.setDrawBarShadow(false);

        mChart.setDrawValueAboveBar(true);

        mChart.getDescription().setEnabled(true);
        mChart.getDescription().setText("비율");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        mChart.setDrawGridBackground(false); //배경그림자

        XAxis xl = mChart.getXAxis(); //x축
        xl.setDrawLabels(false);
        xl.setPosition(XAxisPosition.BOTTOM);
        xl.setTypeface(mTfLight);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(10f);

        YAxis yl = mChart.getAxisLeft();
        yl.setTypeface(mTfLight);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
        yl.setDrawLabels(false);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        YAxis yr = mChart.getAxisRight();
        yr.setTypeface(mTfLight);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(true);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        yr.setAxisMaximum(100f);
//        yr.setInverted(true);

        setData(1, 100);
        mChart.setFitBars(true);
        mChart.animateY(2500);

        // setting data
        mSeekBarY.setProgress(50);
        mSeekBarX.setProgress(12);

        mSeekBarY.setOnSeekBarChangeListener(this);
        mSeekBarX.setOnSeekBarChangeListener(this);

        //막대 설명문 위치
        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] {"aaa", "bbb"});
//        ArrayList<LegendEntry> al = new ArrayList<>();
//        al.add(new LegendEntry());
        l.setFormSize(8f);
        l.setXEntrySpace(4f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        switch (item.getItemId()) {
//            case R.id.actionToggleValues: {
//                List<IBarDataSet> sets = mChart.getData()
//                        .getDataSets();
//
//                for (IBarDataSet iSet : sets) {
//
//                    IBarDataSet set = (BarDataSet) iSet;
//                    set.setDrawValues(!set.isDrawValuesEnabled());
//                }
//
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleIcons: {
//                List<IBarDataSet> sets = mChart.getData()
//                        .getDataSets();
//
//                for (IBarDataSet iSet : sets) {
//
//                    IBarDataSet set = (BarDataSet) iSet;
//                    set.setDrawIcons(!set.isDrawIconsEnabled());
//                }
//
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleHighlight: {
//                if(mChart.getData() != null) {
//                    mChart.getData().setHighlightEnabled(!mChart.getData().isHighlightEnabled());
//                    mChart.invalidate();
//                }
//                break;
//            }
//            case R.id.actionTogglePinch: {
//                if (mChart.isPinchZoomEnabled())
//                    mChart.setPinchZoom(false);
//                else
//                    mChart.setPinchZoom(true);
//
//                mChart.invalidate();
//                break;
//            }
//            case R.id.actionToggleAutoScaleMinMax: {
//                mChart.setAutoScaleMinMaxEnabled(!mChart.isAutoScaleMinMaxEnabled());
//                mChart.notifyDataSetChanged();
//                break;
//            }
//            case R.id.actionToggleBarBorders: {
//                for (IBarDataSet set : mChart.getData().getDataSets())
//                    ((BarDataSet)set).setBarBorderWidth(set.getBarBorderWidth() == 1.f ? 0.f : 1.f);
//
//                mChart.invalidate();
//                break;
//            }
//            case R.id.animateX: {
//                mChart.animateX(3000);
//                break;
//            }
//            case R.id.animateY: {
//                mChart.animateY(3000);
//                break;
//            }
//            case R.id.animateXY: {
//
//                mChart.animateXY(3000, 3000);
//                break;
//            }
//            case R.id.actionSave: {
//                if (mChart.saveToGallery("title" + System.currentTimeMillis(), 50)) {
//                    Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
//                            Toast.LENGTH_SHORT).show();
//                } else
//                    Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
//                            .show();
//                break;
//            }
//        }
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText("" + (mSeekBarX.getProgress() + 1));
        tvY.setText("" + (mSeekBarY.getProgress()));

        setData(mSeekBarX.getProgress() + 1, mSeekBarY.getProgress());
        mChart.setFitBars(true);
        mChart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    private void setData(int count, float range) {
        //count 화면에 보여지는 막대 수
        float barWidth = 1f;
        float spaceForBar = 1f;
        float groupSpace = 1f; //막대그룹 사이 사이 여백

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals3 = new ArrayList<BarEntry>();


        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range);
            yVals1.add(new BarEntry(i * spaceForBar, 77));
            yVals2.add(new BarEntry((i+1) * spaceForBar, 55));
            yVals3.add(new BarEntry((i+2) * spaceForBar, 66));
        }

        BarDataSet set1, set2, set3;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
//            set1 = (BarDataSet)mChart.getData().getDataSetByIndex(0);
//            set1.setValues(yVals1);
//            mChart.getData().notifyDataChanged();
//            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "걸음");
            set1.setDrawIcons(false); //막대에 그려지는 아이콘
            set1.setColor(Color.rgb(104, 241, 175));
            set2 = new BarDataSet(yVals2, "거리");
            set2.setColor(Color.rgb(164, 228, 251));
            set3 = new BarDataSet(yVals3, "칼로리");
            set3.setColor(Color.rgb(242, 247, 158));

//            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>(); //라벨과 막대가 합쳐진 리스트
//            dataSets.add(set1);
//            dataSets.add(set1);
//            BarData data = new BarData(dataSets);

            BarData data = new BarData(set1, set2, set3);
            data.setValueFormatter(new LargeValueFormatter());

            data.setValueTextSize(10f); //막대옆 글씨크기
            data.setValueTypeface(mTfLight);
            data.setBarWidth(barWidth);
            mChart.setData(data);
        }
    }

    protected RectF mOnValueSelectedRectF = new RectF();
    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;

        RectF bounds = mOnValueSelectedRectF;
        mChart.getBarBounds((BarEntry) e, bounds);

        MPPointF position = mChart.getPosition(e, mChart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency());

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {
    };
}
