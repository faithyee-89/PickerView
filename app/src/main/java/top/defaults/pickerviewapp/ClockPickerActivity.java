package top.defaults.pickerviewapp;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.lang.reflect.Field;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import top.defaults.pickerviewapp.dialog.ActionListener;
import top.defaults.pickerviewapp.dialog.BaseDialogFragment;
import top.defaults.pickerviewapp.dialog.DatePickerDialog;
import top.defaults.pickerviewapp.dialog.DivisionPickerDialog;
import top.defaults.pickerviewapp.dialog.SimplePickerDialog;
import top.defaults.view.ClockPickerBean;
import top.defaults.view.ClockPickerView;
import top.defaults.view.Division;

public class ClockPickerActivity extends AppCompatActivity {
    private TextView textView;
    private TimePicker clockPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_picker);
        textView = findViewById(R.id.textView);
        clockPickerView = findViewById(R.id.clockPicker);
        Resources systemResources = Resources.getSystem();
        int hourNumberPickerId = systemResources.getIdentifier("hour", "id", "android");
        int minuteNumberPickerId = systemResources.getIdentifier("minute", "id", "android");
        NumberPicker hourNumberPicker = (NumberPicker) clockPickerView.findViewById(hourNumberPickerId);
        NumberPicker minuteNumberPicker = (NumberPicker) clockPickerView.findViewById(minuteNumberPickerId);
        hourNumberPicker.setMinValue(8);   //设置最小hour
        hourNumberPicker.setMaxValue(12);  //设置最大hour
        minuteNumberPicker .setMinValue(0);  //设置最小minute
        minuteNumberPicker .setMaxValue(30);  //设置最大minute
        clockPickerView.setBackgroundColor(Color.parseColor("#222F2F"));// 修改背景颜色
        clockPickerView.setIs24HourView(true);
        ViewGroup view = (ViewGroup) clockPickerView.getChildAt(0);
        ViewGroup view2 = (ViewGroup) view.getChildAt(1);
        view2.getChildAt(1).setVisibility(View.GONE);

        final int count = clockPickerView.getChildCount();
        for(int i = 0; i < count; i++){
            try{
                Field dividerField = clockPickerView.getClass().getDeclaredField("mSelectionDivider");
                dividerField.setAccessible(true);
                ColorDrawable colorDrawable = new ColorDrawable(
                        ContextCompat.getColor(this, android.R.color.white));
                dividerField.set(clockPickerView,colorDrawable);
                clockPickerView.invalidate();
            }
            catch(NoSuchFieldException | IllegalAccessException | IllegalArgumentException e){
                Log.w("setNumberPickerTxtClr", e);
            }
        }
    }

}