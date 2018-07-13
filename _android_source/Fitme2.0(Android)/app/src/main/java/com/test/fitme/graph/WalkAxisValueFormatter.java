package com.test.fitme.graph;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class WalkAxisValueFormatter implements IAxisValueFormatter {

    private String[] mValues;

    public WalkAxisValueFormatter(String[] values) {
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mValues[(int) value % mValues.length];
    }
}