package com.test.fitme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;

public class NumberPickerDialogFragment extends DialogFragment {
    private NumberPicker numberPicker;
    private static  int initNum;
    public static NumberPickerDialogFragment newInstance(int num) {
        initNum = num;
        NumberPickerDialogFragment numberPickerDialogFragment = new NumberPickerDialogFragment();
        return numberPickerDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialogfragment_numberpicker, container, false);
        //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setTitle("숫자 선택");

        numberPicker = v.findViewById(R.id.numberpicker);
        numberPicker.setValue(initNum);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            }
        });
        numberPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "선택완료", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("num", numberPicker.getValue());
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                getDialog().dismiss();
            }
        });
        return v;
    }
}
