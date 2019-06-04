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

public class SellActivity extends AppCompatActivity {
    Button sub;
    Button add;
    Dialog dialogSell;
    NumberPicker sellPicker;

    Dialog dialogAdd;
    TextView totalSum;
    TextView soldSum;
    RecyclerView sellProductsView;
    AdapterSellProducts mAdapterSellProducts;

    RecyclerView productsView;
    AdapterDiProducts mAdapterDiProducts;
    NumberPicker numberPicker;

    WindowManager.LayoutParams lpAdd;
    WindowManager.LayoutParams lpSell;

    SellProduct currentChangingProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        initProductList();
        initAddProductDialog();
        initSellProductDialog();

        totalSum = (TextView) findViewById(R.id.AcSellTotal);
        soldSum = (TextView) findViewById(R.id.AcSellTotalSelled);

        totalSum.setText(String.valueOf(RealmHandler.SumTotalSellProducts()));
        soldSum.setText(String.valueOf(RealmHandler.SumSoldSellProducts()));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.AcSellFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAdd.show();
                dialogAdd.getWindow().setAttributes(lpAdd);
            }
        });
    }

    public void initSellProductDialog() {
        dialogSell = new Dialog(this);

        lpSell = new WindowManager.LayoutParams();
        lpSell.copyFrom(dialogSell.getWindow().getAttributes());
        lpSell.width = WindowManager.LayoutParams.MATCH_PARENT;
        lpSell.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialogSell.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialogSell.setContentView(R.layout.dialog_sell_product);
        dialogSell.setCancelable(true);
        sellPicker = (NumberPicker) dialogSell.findViewById(R.id.DiSellProductNumber);
        sellPicker.setMinValue(0);
        sellPicker.setMaxValue(500);
        TextView product = (TextView) dialogSell.findViewById(R.id.DiSellProductProduct);
        sub = (Button) dialogSell.findViewById(R.id.DiSellProductSub);
        add = (Button) dialogSell.findViewById(R.id.DiSellProductAdd);

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentChangingProduct != null){
                    currentChangingProduct.setSold(currentChangingProduct.getSold()-sellPicker.getValue());
                    RealmHandler.UpdateSellProduct(currentChangingProduct);
                    mAdapterSellProducts.ResetItems();
                    mAdapterSellProducts.notifyDataSetChanged();
                    totalSum.setText(String.valueOf(RealmHandler.SumTotalSellProducts()));
                    soldSum.setText(String.valueOf(RealmHandler.SumSoldSellProducts()));
                }
                dialogSell.dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentChangingProduct != null){
                    currentChangingProduct.setSold(currentChangingProduct.getSold()+sellPicker.getValue());
                    RealmHandler.UpdateSellProduct(currentChangingProduct);
                    mAdapterSellProducts.ResetItems();
                    mAdapterSellProducts.notifyDataSetChanged();
                    totalSum.setText(String.valueOf(RealmHandler.SumTotalSellProducts()));
                    soldSum.setText(String.valueOf(RealmHandler.SumSoldSellProducts()));
                }
                dialogSell.dismiss();
            }
        });

        mAdapterSellProducts.setOnItemClickListener(new AdapterSellProducts.OnItemClickListener() {
            @Override
            public void onItemClick(View view, SellProduct obj, int position) {
                currentChangingProduct = obj;
                product.setText(obj.getTitle() + " " + obj.getPrice() +" руб");
                sellPicker.setValue(0);
                dialogSell.show();
                dialogSell.getWindow().setAttributes(lpSell);
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
    }

    private void initAddProductDialog(){
        dialogAdd = new Dialog(this);
        dialogAdd.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialogAdd.setContentView(R.layout.dialog_add_product);
        dialogAdd.setCancelable(true);

        lpAdd = new WindowManager.LayoutParams();
        lpAdd.copyFrom(dialogAdd.getWindow().getAttributes());
        lpAdd.width = WindowManager.LayoutParams.MATCH_PARENT;
        lpAdd.height = WindowManager.LayoutParams.MATCH_PARENT;

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
