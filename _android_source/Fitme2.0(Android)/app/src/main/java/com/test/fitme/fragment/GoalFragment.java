package com.test.fitme.fragment;


import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.takisoft.datetimepicker.DatePickerDialog;
import com.test.fitme.GoalVO;
import com.test.fitme.NumberPickerDialogFragment;
import com.test.fitme.R;
import com.test.fitme.RegisterDialogFragment;
import com.test.fitme.SQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoalFragment extends Fragment implements View.OnClickListener{
    private ImageButton rightBtn, leftBtn;
    private TextView dateTv, walkTv, runTv, dumbbellTv, barbellTv, jumpropeTv, situpTv;
    private Calendar cal;
    private SQLiteHelper helper;
    private String nowDate;
    private final int RESULT_WALK_VALUE = 1;
    private final int RESULT_RUN_VALUE = 2;
    private final int RESULT_DUMBBELL_VALUE = 3;
    private final int RESULT_BARBELL_VALUE = 4;
    private final int RESULT_JUMPROPE_VALUE = 5;
    private final int RESULT_SITUP_VALUE = 6;

    public GoalFragment() {
        // Required empty public constructor
    }

    public static GoalFragment newInstance() {
        GoalFragment goalFragment = new GoalFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return goalFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_goal, container, false);
        helper = new SQLiteHelper(getActivity());
        cal = Calendar.getInstance();
        nowDate = String.format("%d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));

        rightBtn = v.findViewById(R.id.goal_right);
        rightBtn.setOnClickListener(this);
        leftBtn = v.findViewById(R.id.goal_left);
        leftBtn.setOnClickListener(this);
        dateTv = v.findViewById(R.id.goal_date);
        dateTv.setOnClickListener(this);
        dateTv.setText(nowDate);
        walkTv = v.findViewById(R.id.goal_walk);
        walkTv.setOnClickListener(this);
        runTv = v.findViewById(R.id.goal_run);
        runTv.setOnClickListener(this);
        dumbbellTv = v.findViewById(R.id.goal_dumbbell);
        dumbbellTv.setOnClickListener(this);
        barbellTv = v.findViewById(R.id.goal_barbell);
        barbellTv.setOnClickListener(this);
        jumpropeTv = v.findViewById(R.id.goal_jumprope);
        jumpropeTv.setOnClickListener(this);
        situpTv = v.findViewById(R.id.goal_situp);
        situpTv.setOnClickListener(this);

        GoalVO goal = helper.select_goal(nowDate);
        walkTv.setText(String.valueOf(goal.getgWalk_count()));
        runTv.setText(String.valueOf(goal.getgRun_count()));
        dumbbellTv.setText(String.valueOf(goal.getgDumbbell_count()));
        barbellTv.setText(String.valueOf(goal.getgBarbell_count()));
        jumpropeTv.setText(String.valueOf(goal.getgJumprope_count()));
        situpTv.setText(String.valueOf(goal.getgSitup_count()));
        return v;
    }

    @Override
    public void onClick(View v) {
        StringTokenizer st = new StringTokenizer(dateTv.getText().toString(), "-");
        int mYear = Integer.parseInt(st.nextToken());
        int mMonth = Integer.parseInt(st.nextToken())-1;
        int mDate = Integer.parseInt(st.nextToken());
        switch (v.getId()) {
            case R.id.goal_date:
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), (view1, year, month, dayOfMonth) -> {
                    nowDate = String.format("%d-%02d-%02d", year, month+1, dayOfMonth);
                    dateTv.setText(nowDate);
                    cal.set(year, month, dayOfMonth);
                    GoalVO goal = null;
                    if((goal = helper.select_goal(nowDate)) == null) {
                        helper.insert(goal = new GoalVO(nowDate, 1000, 1000, 100, 100, 100, 100));
                    }
                    walkTv.setText(String.valueOf(goal.getgWalk_count()));
                    runTv.setText(String.valueOf(goal.getgRun_count()));
                    dumbbellTv.setText(String.valueOf(goal.getgDumbbell_count()));
                    barbellTv.setText(String.valueOf(goal.getgBarbell_count()));
                    jumpropeTv.setText(String.valueOf(goal.getgJumprope_count()));
                    situpTv.setText(String.valueOf(goal.getgSitup_count()));
                    //Toast.makeText(getActivity(), String.format("%d", year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth), Toast.LENGTH_SHORT).show();
                }, mYear, mMonth, mDate);
                dpd.show();
                break;
            case R.id.goal_left: {
                    cal.add(Calendar.DATE, -1);
                    nowDate = String.format("%d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));
                    dateTv.setText(nowDate);
                    GoalVO goal = null;
                    if ((goal = helper.select_goal(nowDate)) == null) {
                        helper.insert(goal = new GoalVO(nowDate, 1000, 1000, 100, 100, 100, 100));
                    }
                    walkTv.setText(String.valueOf(goal.getgWalk_count()));
                    runTv.setText(String.valueOf(goal.getgRun_count()));
                    dumbbellTv.setText(String.valueOf(goal.getgDumbbell_count()));
                    barbellTv.setText(String.valueOf(goal.getgBarbell_count()));
                    jumpropeTv.setText(String.valueOf(goal.getgJumprope_count()));
                    situpTv.setText(String.valueOf(goal.getgSitup_count()));
                }
                break;
            case R.id.goal_right: {
                    cal.add(Calendar.DATE, 1);
                    nowDate = String.format("%d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));
                    dateTv.setText(nowDate);
                    GoalVO goal = null;
                    if ((goal = helper.select_goal(nowDate)) == null) {
                        helper.insert(goal = new GoalVO(nowDate, 1000, 1000, 100, 100, 100, 100));
                    }
                    walkTv.setText(String.valueOf(goal.getgWalk_count()));
                    runTv.setText(String.valueOf(goal.getgRun_count()));
                    dumbbellTv.setText(String.valueOf(goal.getgDumbbell_count()));
                    barbellTv.setText(String.valueOf(goal.getgBarbell_count()));
                    jumpropeTv.setText(String.valueOf(goal.getgJumprope_count()));
                    situpTv.setText(String.valueOf(goal.getgSitup_count()));
                }
                break;
            case R.id.goal_walk:
            case R.id.goal_run:
            case R.id.goal_dumbbell:
            case R.id.goal_barbell:
            case R.id.goal_jumprope:
            case R.id.goal_situp:
                TextView tv = null;
                int requestCode = 0;
                if(v.getId() == R.id.goal_walk) {
                    tv = walkTv;
                    requestCode = RESULT_WALK_VALUE;
                }else if(v.getId() == R.id.goal_run) {
                    tv = runTv;
                    requestCode = RESULT_RUN_VALUE;
                }else if(v.getId() == R.id.goal_dumbbell) {
                    tv = dumbbellTv;
                    requestCode = RESULT_DUMBBELL_VALUE;
                }else if(v.getId() == R.id.goal_barbell) {
                    tv = barbellTv;
                    requestCode = RESULT_BARBELL_VALUE;
                }else if(v.getId() == R.id.goal_jumprope) {
                    tv = jumpropeTv;
                    requestCode = RESULT_JUMPROPE_VALUE;
                }else if(v.getId() == R.id.goal_situp) {
                    tv = situpTv;
                    requestCode = RESULT_SITUP_VALUE;
                }
                String present = new SimpleDateFormat("yyy-MM-dd").format(new Date(System.currentTimeMillis()));
                if(nowDate.compareTo(present) >= 0) {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    NumberPickerDialogFragment numberPickerDialogFragment = NumberPickerDialogFragment.newInstance(Integer.parseInt(tv.getText().toString()));
                    numberPickerDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
                    numberPickerDialogFragment.setTargetFragment(this, requestCode);
                    numberPickerDialogFragment.show(ft, "");
                }else {
                    Toast.makeText(getActivity(), "지난 날짜의 목표치는 변경할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("태그","resultCode:" + resultCode + " " + "requestCode:" + requestCode);
        int cnt = data.getIntExtra("num", 0);
        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RESULT_WALK_VALUE:
                    walkTv.setText("" + cnt);
                    helper.update(cnt, RESULT_WALK_VALUE, nowDate);
                    break;
                case RESULT_RUN_VALUE:
                    runTv.setText("" + cnt);
                    helper.update(cnt, RESULT_RUN_VALUE, nowDate);
                    break;
                case RESULT_DUMBBELL_VALUE:
                    dumbbellTv.setText("" + cnt);
                    helper.update(cnt, RESULT_DUMBBELL_VALUE, nowDate);
                    break;
                case RESULT_BARBELL_VALUE:
                    barbellTv.setText("" + cnt);
                    helper.update(cnt, RESULT_BARBELL_VALUE, nowDate);
                    break;
                case RESULT_JUMPROPE_VALUE:
                    jumpropeTv.setText("" + cnt);
                    helper.update(cnt, RESULT_JUMPROPE_VALUE, nowDate);
                    break;
                case RESULT_SITUP_VALUE:
                    situpTv.setText("" + cnt);
                    helper.update(cnt, RESULT_SITUP_VALUE, nowDate);
                    break;
            }
        }
    }
}
