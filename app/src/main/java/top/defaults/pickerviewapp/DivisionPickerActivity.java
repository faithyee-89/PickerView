package top.defaults.pickerviewapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import top.defaults.view.Division;
import top.defaults.view.DivisionPickerView;

public class DivisionPickerActivity extends AppCompatActivity {

    private DivisionPickerView divisionPicker;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_division_picker);
        divisionPicker = findViewById(R.id.divisionPicker);
        textView = findViewById(R.id.textView);
        ToggleButton toggleButton = findViewById(R.id.toggleType);
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            divisionPicker.setType(isChecked ? DivisionPickerView.TYPE_ALL : DivisionPickerView.TYPE_PROVINCE_AND_CITY);
        });
        final List<DivisionModel> divisions = Divisions.get(this);
        divisionPicker.setDivisions(divisions);
        divisionPicker.setOnSelectedDateChangedListener(division -> textView.setText(Division.Helper.getCanonicalName(division)));
    }
}