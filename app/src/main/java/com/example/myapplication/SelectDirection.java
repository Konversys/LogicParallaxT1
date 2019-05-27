package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.model.RealmHandler;
import com.example.myapplication.model.logic.Tools;
import com.example.myapplication.model.models.plx_link_api.Direction;
import com.example.myapplication.model.models.realm.Flight;
import com.example.myapplication.model.models.yandex_api.direction.DirectionYandex;
import com.example.myapplication.model.models.yandex_api.direction.Segment;
import com.example.myapplication.model.models.yandex_api.station.StationList;
import com.example.myapplication.model.web.ApiInstanse;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectDirection extends AppCompatActivity {

    private static Flight flight;
    private static Context context;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_select_direction);
        status = findViewById(R.id.AcSelectDirectionStatus);
        flight = new Flight();
        initSearchAcTextView();
        initDirectionDatepicker();
    }

    private void initSearchAcTextView() {
        searchDirectionTextView((AutoCompleteTextView) findViewById(R.id.AcSelectDirectionSearch));
    }

    private void searchDirectionTextView(final AutoCompleteTextView search) {
        search.setAdapter(new AdapterDirection(this));
        search.setOnItemClickListener((parent, view, position, id) -> {
            Object item = parent.getItemAtPosition(position);
            if (item instanceof Direction) {
                flight.setDirection((Direction) item);
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
                    Date date = Tools.getFormattedDateWithoutTime(date_ship_millis);
                    ((Button) findViewById(R.id.AcSelectDirectionDatepicker)).setText(Tools.getFormattedStringDateDots(date));
                    flight.setDate(date);
                }
        );
        //set dark theme
        datePicker.setThemeDark(true);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.setMinDate(min_calendar);
        datePicker.show(getSupportFragmentManager(), "Datepickerdialog");
    }


    public void StartClick(View view) {
        if (flight.getDate() == null || flight.getDirection() == null) {
            Toast.makeText(this, "Все хуйня давай по новой", Toast.LENGTH_LONG);
        } else {
            GetYandexDir();
        }
    }

    static void GetYandexDir() {
        ApiInstanse.getYandexApi().getDirections(
                flight.getDirection().getFrom(),
                flight.getDirection().getTo(),
                Tools.getFormattedStringDateLine(flight.getDate()))
                .enqueue(
                        new Callback<DirectionYandex>() {
                            @Override
                            public void onResponse(Call<DirectionYandex> call, Response<DirectionYandex> response) {
                                ArrayList<Segment> segmentsYandex = new ArrayList<>(response.body().getSegments().stream().filter(
                                        x -> x.getThread().getNumber().equals(flight.getDirection().getValue())
                                ).collect(Collectors.toList()));
                                if (segmentsYandex == null || segmentsYandex.size() <= 0) {
                                    Toast.makeText(context, "Ссылка хуйня давай по новой", Toast.LENGTH_LONG);
                                } else {
                                    flight.setThread(segmentsYandex.get(0).getThread());
                                    GetYandexStations();
                                }

                            }

                            @Override
                            public void onFailure(Call<DirectionYandex> call, Throwable t) {
                            }
                        }
                );
    }

    static void GetYandexStations() {
        ApiInstanse.getYandexApi().getStations(flight.getThread().getUid(), Tools.getFormattedStringDateLine(flight.getDate())).enqueue(
                new Callback<StationList>() {
                    @Override
                    public void onResponse(Call<StationList> call, Response<StationList> response) {
                        RealmHandler.SaveFlight(flight, response.body());
                        Intent intent = new Intent(context, StationsActivity.class);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<StationList> call, Throwable t) {

                    }
                }
        );
    }
}
