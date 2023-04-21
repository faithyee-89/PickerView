package top.defaults.pickerviewapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.defaultPicker).setOnClickListener(v -> {
            startActivity(new Intent(this, DefaultPickerActivity.class));
        });
        findViewById(R.id.divisionPicker).setOnClickListener(v -> {
            startActivity(new Intent(this, DivisionPickerActivity.class));
        });
        findViewById(R.id.datePicker).setOnClickListener(v -> {
            startActivity(new Intent(this, DatePickerActivity.class));
        });
        findViewById(R.id.pickerDialog).setOnClickListener(v -> {
            startActivity(new Intent(this, PickerDialogActivity.class));
        });
    }
}
