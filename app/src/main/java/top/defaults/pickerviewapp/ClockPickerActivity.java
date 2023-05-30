package top.defaults.pickerviewapp;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.lang.reflect.Field;

import top.defaults.view.ClockPickerView;
import top.defaults.view.Division;

public class ClockPickerActivity extends AppCompatActivity {
    private TextView textView;
    private TimePicker timePickerView;
    private ClockPickerView clockPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_picker);
        textView = findViewById(R.id.textView);
        initNativePickView();
        initClockPickerView();
    }

    /**
     * 初始化自定义ClockPickerView
     */
    private void initClockPickerView() {
        clockPickerView = findViewById(R.id.clockPicker);
        clockPickerView.setOnSelectedDateChangedListener(division -> {
            textView.setText(division.getText());
        });

    }

    /**
     * 初始化原生PickView
     */
    private void initNativePickView() {
        timePickerView = findViewById(R.id.timePicker);
        Resources systemResources = Resources.getSystem();
        int hourNumberPickerId = systemResources.getIdentifier("hour", "id", "android");
        int minuteNumberPickerId = systemResources.getIdentifier("minute", "id", "android");
        NumberPicker hourNumberPicker = (NumberPicker) timePickerView.findViewById(hourNumberPickerId);
        NumberPicker minuteNumberPicker = (NumberPicker) timePickerView.findViewById(minuteNumberPickerId);
        hourNumberPicker.setMinValue(8);   //设置最小hour
        hourNumberPicker.setMaxValue(12);  //设置最大hour
        minuteNumberPicker .setMinValue(0);  //设置最小minute
        minuteNumberPicker .setMaxValue(30);  //设置最大minute
        timePickerView.setBackgroundColor(Color.parseColor("#222F2F"));// 修改背景颜色
        timePickerView.setIs24HourView(true);
        ViewGroup view = (ViewGroup) timePickerView.getChildAt(0);
        ViewGroup view2 = (ViewGroup) view.getChildAt(1);
        view2.getChildAt(1).setVisibility(View.GONE);

        final int count = timePickerView.getChildCount();
        for(int i = 0; i < count; i++){
            try{
                Field dividerField = timePickerView.getClass().getDeclaredField("mSelectionDivider");
                dividerField.setAccessible(true);
                ColorDrawable colorDrawable = new ColorDrawable(
                        ContextCompat.getColor(this, android.R.color.white));
                dividerField.set(timePickerView,colorDrawable);
                timePickerView.invalidate();
            }
            catch(NoSuchFieldException | IllegalAccessException | IllegalArgumentException e){
                Log.w("setNumberPickerTxtClr", e);
            }
        }
    }

}