package top.defaults.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

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
        settlePickerView(hourPicker);

        minutePicker = new PickerView(context);
        settlePickerView(minutePicker);

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
