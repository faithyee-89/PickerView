package top.defaults.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ClockPickerView extends PickerViewGroup {

    private final DivisionAdapter hourAdapter = new DivisionAdapter();
    private final DivisionAdapter minuteAdapter = new DivisionAdapter();

    private PickerView hourPicker;
    private PickerView minutePicker;

    public ClockPickerView(Context context) {
        this(context, null);
    }

    public ClockPickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DivisionPickerView);
        typedArray.recycle();

        hourPicker = new PickerView(context);
        settlePickerView(hourPicker, false, PickerView.PICKER_VIEW_DIRECTION_LEFT);

        minutePicker = new PickerView(context);
        settlePickerView(minutePicker, false, PickerView.PICKER_VIEW_DIRECTION_RIGHT);

        List<ClockPickerBean> hours = new ArrayList<>();
        List<ClockPickerBean> minutes = new ArrayList<>();
        for (int i = 1; i <= 24; i++) {
            hours.add(new ClockPickerBean(i + ""));
        }
        for (int i = 1; i <= 60; i++) {
            minutes.add(new ClockPickerBean(i + ""));
        }
        hourAdapter.setDivisions(hours);
        hourPicker.setAdapter(hourAdapter);

        minuteAdapter.setDivisions(minutes);
        minutePicker.setAdapter(minuteAdapter);

        PickerView.OnSelectedItemChangedListener listener = (pickerView, previousPosition, selectedItemPosition) -> {
            if (onSelectedDivisionChangedListener != null) {
                onSelectedDivisionChangedListener.onSelectedDivisionChanged(getSelectedDivision());
            }
        };

        hourPicker.setOnSelectedItemChangedListener(listener);
        minutePicker.setOnSelectedItemChangedListener(listener);

        initCurrentTime();
    }

    private void initCurrentTime() {
        final Calendar tempCalendar = Calendar.getInstance();
        SimpleDateFormat HHmm = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Log.i("PickView", HHmm.format(tempCalendar.getTime()));
        String result = HHmm.format(tempCalendar.getTime());
        String[] split = result.split(":");
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);
        Log.i("PickView", "hour = " + hour + " minute = " + minute);
        setCurrentTime(hour, minute);
    }

    public void setCurrentTime(int hour, int minute) {
        if (hour == 0 || minute == 0) {
            return;
        }
        hourPicker.setSelectedItemPosition(hour - 1);
        minutePicker.setSelectedItemPosition(minute - 1);
    }

    public interface OnSelectedDivisionChangedListener {
        void onSelectedDivisionChanged(Division division);
    }

    private OnSelectedDivisionChangedListener onSelectedDivisionChangedListener;

    public void setOnSelectedDateChangedListener(OnSelectedDivisionChangedListener onSelectedDivisionChangedListener) {
        this.onSelectedDivisionChangedListener = onSelectedDivisionChangedListener;
    }

    public PickerView getHourPicker() {
        return hourPicker;
    }

    public PickerView getMinutePicker() {
        return minutePicker;
    }

    public Division getSelectedDivision() {
        String hour = hourPicker.getSelectedItem(Division.class).getName();
        String minute = minutePicker.getSelectedItem(Division.class).getName();
        return new ClockPickerBean(hour + " : " + minute);
    }
}
