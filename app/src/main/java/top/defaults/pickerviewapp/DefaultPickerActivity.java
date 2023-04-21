package top.defaults.pickerviewapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import top.defaults.view.PickerView;

public class DefaultPickerActivity extends AppCompatActivity {

    private PickerView pickerView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_picker);
        pickerView = findViewById(R.id.pickerView);
        textView = findViewById(R.id.textView);

        pickerView.setItems(Item.sampleItems(), item -> textView.setText(item.getText()));
        pickerView.setSelectedItemPosition(4);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "SpaceMono-Regular.ttf");
        pickerView.setTypeface(typeface);
    }
}