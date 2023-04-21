package top.defaults.pickerviewapp;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

import top.defaults.pickerviewapp.dialog.ActionListener;
import top.defaults.pickerviewapp.dialog.BaseDialogFragment;
import top.defaults.pickerviewapp.dialog.DatePickerDialog;
import top.defaults.pickerviewapp.dialog.DivisionPickerDialog;
import top.defaults.pickerviewapp.dialog.SimplePickerDialog;
import top.defaults.view.Division;

public class PickerDialogActivity extends AppCompatActivity {
    private TextView textView;
    private RadioGroup sampleChooser;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void onCancelClick(BaseDialogFragment dialog) {}

        @Override
        public void onDoneClick(BaseDialogFragment dialog) {
            if (dialog instanceof SimplePickerDialog) {
                textView.setText(((SimplePickerDialog) dialog).getSelectedItem().getText());
            } else if (dialog instanceof DivisionPickerDialog) {
                Division division = ((DivisionPickerDialog) dialog).getSelectedDivision();
                StringBuilder text = new StringBuilder(division.getText());
                while (division.getParent() != null) {
                    division = division.getParent();
                    text.insert(0, division.getText());
                }
                textView.setText(text.toString());
            } else if (dialog instanceof DatePickerDialog) {
                textView.setText(getDateString(((DatePickerDialog) dialog).getSelectedDate()));
            }
        }
    };

    BaseDialogFragment choosePicker(int type) {
        BaseDialogFragment picker;

        switch (sampleChooser.getCheckedRadioButtonId()) {
            case R.id.division:
                picker = DivisionPickerDialog.newInstance(type, actionListener);
                break;
            case R.id.date:
                picker = DatePickerDialog.newInstance(type, actionListener);
                break;
            case R.id.simple:
            default:
                picker = SimplePickerDialog.newInstance(type, actionListener);
                break;
        }

        return picker;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_dialog);
        textView = findViewById(R.id.textView);
        sampleChooser = findViewById(R.id.sampleChooser);
        findViewById(R.id.withView).setOnClickListener(v -> {
            choosePicker(BaseDialogFragment.TYPE_VIEW).show(getFragmentManager(), "view");
        });

        findViewById(R.id.withDialog).setOnClickListener(v -> {
            choosePicker(BaseDialogFragment.TYPE_DIALOG).show(getFragmentManager(), "dialog");
        });
    }

    private String getDateString(Calendar date) {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int dayOfMonth = date.get(Calendar.DAY_OF_MONTH);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);
        return String.format(Locale.getDefault(), "%d年%02d月%02d日%02d时%02d分", year, month + 1, dayOfMonth, hour, minute);
    }
}