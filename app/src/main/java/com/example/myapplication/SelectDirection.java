package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.model.models.plx_link_api.Direction;
import com.example.myapplication.model.web.ApiInstanse;
import com.example.myapplication.model.web.ApiPlxLink;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectDirection extends AppCompatActivity {

    static TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_direction);
        status = findViewById(R.id.AcSelectDirectionStatus);
        status.setText("Загрузка");
        ApiInstanse.getPlxLinkApi().getValidDirections().enqueue(new Callback<List<Direction>>() {
            @Override
            public void onResponse(Call<List<Direction>> call, Response<List<Direction>> response) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.where(Direction.class).findAll().deleteAllFromRealm();
                realm.copyToRealm(new ArrayList<>(response.body()));
                realm.commitTransaction();
                realm.close();
                status.setText("Успешно");
            }

            @Override
            public void onFailure(Call<List<Direction>> call, Throwable t) {
                status.setText("Ошибка");
            }
        });
    }
}
