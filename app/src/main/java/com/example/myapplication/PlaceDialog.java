package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.RealmHandler;
import com.example.myapplication.model.models.plx_link_api.Direction;
import com.example.myapplication.model.models.realm.Place;
import com.example.myapplication.model.models.realm.Station;

import io.realm.RealmList;

public class PlaceDialog {
    private Dialog dialog;
    private Context context;
    private Place place;
    private WindowManager.LayoutParams lp;
    private Station from;
    private Station to;
    private AdapterWagonCoupe adapterWagonCoupe;

    public PlaceDialog(Context context, AdapterWagonCoupe adapterWagonCoupe) {
        this.context = context;
        this.adapterWagonCoupe = adapterWagonCoupe;
        initDialog();
    }

    void initDialog() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_set_place);
        dialog.setCancelable(true);

        lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final AutoCompleteTextView fromACTV = (AutoCompleteTextView) dialog.findViewById(R.id.DiSetPlaceFrom);
        final AutoCompleteTextView toACTV = (AutoCompleteTextView) dialog.findViewById(R.id.DiSetPlaceTo);
        final CheckBox animal = (CheckBox) dialog.findViewById(R.id.DiSetPlaceAnimal);
        final CheckBox children = (CheckBox) dialog.findViewById(R.id.DiSetPlaceChildren);
        final CheckBox linen = (CheckBox) dialog.findViewById(R.id.DiSetPlaceLinen);

        fromACTV.setAdapter(new AdapterDiStation(dialog.getContext()));
        fromACTV.setOnItemClickListener((parent, view, position, id) -> {
                    Object item = parent.getItemAtPosition(position);
                    if (item instanceof Station) {
                        this.from = (Station) item;
                    }
                }
        );

        toACTV.setAdapter(new AdapterDiStation(dialog.getContext()));
        toACTV.setOnItemClickListener((parent, view, position, id) -> {
                    Object item = parent.getItemAtPosition(position);
                    if (item instanceof Station) {
                        this.to = (Station) item;
                    }
                }
        );


        ((Button) dialog.findViewById(R.id.DiSetPlaceCancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ((Button) dialog.findViewById(R.id.DiSetPlaceRemove)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                place.clean();
                dialog.dismiss();
                adapterWagonCoupe.notifyDataSetChanged();
            }
        });
        ((Button) dialog.findViewById(R.id.DiSetPlaceSubmit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from == null && to == null) {
                    Toast.makeText(context.getApplicationContext(), "Выберите старции отправления и прибытия", Toast.LENGTH_SHORT).show();
                } else if (from == null) {
                    Toast.makeText(context.getApplicationContext(), "Выберите старции отправления", Toast.LENGTH_SHORT).show();
                } else if (to == null) {
                    Toast.makeText(context.getApplicationContext(), "Выберите старции прибытия", Toast.LENGTH_SHORT).show();
                } else {
                    place.setFrom(from);
                    place.setTo(to);
                    place.setWithAnimal(animal.isChecked());
                    place.setWithLinen(linen.isChecked());
                    place.setWithChildren(children.isChecked());
                    RealmHandler.SetPlace(place);
                }
                adapterWagonCoupe.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

    public void showDialog(Place place) {
        this.place = place;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public void closeDialog() {
        dialog.dismiss();
    }
}
