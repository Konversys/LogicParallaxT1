package com.example.myapplication;

import android.os.Bundle;

import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.model.logic.Tools;
import com.example.myapplication.model.models.plx_link_api.Direction;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class SelectDirection extends AppCompatActivity {

    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_direction);
        status = findViewById(R.id.AcSelectDirectionStatus);
        initSearchAcTextView();
        initDirectionDatepicker();
    }

    private void initSearchAcTextView(){
        searchDirectionTextView((AutoCompleteTextView) findViewById(R.id.AcSelectDirectionSearch));
    }

    private void searchDirectionTextView(final AutoCompleteTextView search){
        search.setAdapter(new AdapterDirection(this));
        search.setOnItemClickListener((parent, view, position, id) -> {
            Object item = parent.getItemAtPosition(position);
            if (item instanceof Direction){
                Direction direction =(Direction) item;
                status.setText(direction.getValue());
            }
        });
    }

    private void initDirectionDatepicker() {
        ((Button) findViewById(R.id.AcSelectDirectionDatepicker)).setOnClickListener(view -> dialogDirectionDatePicker((Button) view));
    }

    private void dialogDirectionDatePicker(final Button bt) {
        Calendar min_calendar = GregorianCalendar.getInstance();
        min_calendar.set(Calendar.YEAR, 2018);

        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                (view, year, monthOfYear, dayOfMonth) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    long date_ship_millis = calendar.getTimeInMillis();
                    ((TextView) findViewById(R.id.AcSelectDirectionStatus)).setText(Tools.getFormattedDateSimple(date_ship_millis));
                }
        );
        //set dark theme
        datePicker.setThemeDark(true);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.setMinDate(min_calendar);
        datePicker.show(getSupportFragmentManager(), "Datepickerdialog");
    }
}
