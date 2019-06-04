package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.RealmHandler;
import com.example.myapplication.model.models.plx_link_api.Product;
import com.example.myapplication.model.models.realm.SellProduct;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import io.realm.Realm;

public class SellActivity extends AppCompatActivity {

    TextView totalSum;
    TextView soldSum;
    Button sub;
    Button add;
    RecyclerView productsView;
    RecyclerView sellProductsView;
    AdapterDiProducts mAdapterDiProducts;
    AdapterSellProducts mAdapterSellProducts;
    WindowManager.LayoutParams lp;
    Dialog dialogAdd;
    Dialog dialogSell;
    FloatingActionButton fab;
    NumberPicker numberPicker;
    NumberPicker sellPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        initProductList();
        initAddProductDialog();

        totalSum = (TextView) findViewById(R.id.AcSellTotal);
        soldSum = (TextView) findViewById(R.id.AcSellTotalSelled);

        totalSum.setText(String.valueOf(RealmHandler.SumTotalSellProducts()));
        soldSum.setText(String.valueOf(RealmHandler.SumSoldSellProducts()));

        fab = (FloatingActionButton) findViewById(R.id.AcSellFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAdd.show();
                dialogAdd.getWindow().setAttributes(lp);
            }
        });
    }

    public void initSellProductDialog() {
        dialogSell = new Dialog(this);
        dialogSell.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialogSell.setContentView(R.layout.dialog_add_product);
        dialogSell.setCancelable(true);
        sellPicker = (NumberPicker) dialogSell.findViewById(R.id.DiSellProductNumber);
        sub = (Button) dialogSell.findViewById(R.id.DiSellProductSub);
        add = (Button) dialogSell.findViewById(R.id.DiSellProductAdd);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogSell.dismiss();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSell.dismiss();
            }
        });
    }

    private void initProductList() {
        sellProductsView = (RecyclerView) findViewById(R.id.AcSellProducts);
        sellProductsView.setLayoutManager(new LinearLayoutManager(this));
        sellProductsView.setHasFixedSize(true);

        ArrayList<SellProduct> products = RealmHandler.GetSellProducts();

        //set data and list adapter
        mAdapterSellProducts = new AdapterSellProducts(this, products);
        sellProductsView.setAdapter(mAdapterSellProducts);
        mAdapterSellProducts.setOnItemClickListener(new AdapterSellProducts.OnItemClickListener() {
            @Override
            public void onItemClick(View view, SellProduct obj, int position) {

            }
        });
    }

    private void initAddProductDialog(){
        dialogAdd = new Dialog(this);
        dialogAdd.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialogAdd.setContentView(R.layout.dialog_add_product);
        dialogAdd.setCancelable(true);

        lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogAdd.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        productsView = (RecyclerView) dialogAdd.findViewById(R.id.DiAddProductProducts);
        productsView.setLayoutManager(new LinearLayoutManager(this));
        productsView.setHasFixedSize(true);

        ArrayList<Product> products = RealmHandler.GetProducts();
        mAdapterDiProducts = new AdapterDiProducts(this, products);
        productsView.setAdapter(mAdapterDiProducts);

        numberPicker = (NumberPicker) dialogAdd.findViewById(R.id.DiAddProductNumber);
        numberPicker.setMaxValue(500);
        numberPicker.setMinValue(0);
        numberPicker.setValue(0);

        ((ImageButton) dialogAdd.findViewById(R.id.DiAddProductClose)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAdd.dismiss();
            }
        });

        mAdapterDiProducts.setOnItemClickListener(new AdapterDiProducts.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Product obj, int position) {
                if (numberPicker.getValue() == 0) {
                    Toast.makeText(getApplicationContext(), "Введите количество товара", Toast.LENGTH_SHORT).show();
                }
                else if (RealmHandler.IsProductEqualToSellProduct(obj)) {
                    Toast.makeText(getApplicationContext(), "Товар уже существует", Toast.LENGTH_SHORT).show();
                } else {
                    RealmHandler.AddSellProduct(new SellProduct(obj, numberPicker.getValue(), 0));
                    mAdapterSellProducts.ResetItems();
                    mAdapterSellProducts.notifyDataSetChanged();
                    totalSum.setText(String.valueOf(RealmHandler.SumTotalSellProducts()));
                    soldSum.setText(String.valueOf(RealmHandler.SumSoldSellProducts()));
                    numberPicker.setValue(0);
                    dialogAdd.dismiss();
                }
            }
        });
    }
}
