package com.example.fluperapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.fluperapplication.Adapter.ProductListAdapter;
import com.example.fluperapplication.Model.Product;
import com.example.fluperapplication.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.fluperapplication.Activity.CreateProduct.DATABASE_NAME;
import static com.example.fluperapplication.Activity.CreateProduct.DB_TABLE;
import static com.example.fluperapplication.Activity.CreateProduct.mDatabase;

public class ShowProduct extends AppCompatActivity {

    RecyclerView rlList;
    LinearLayout llBackButton;
    ArrayList<Product> productList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);
        init();
    }

    public void init() {
        rlList = findViewById(R.id.rlList);
        llBackButton = findViewById(R.id.llBackButton);
        rlList.setLayoutManager(new LinearLayoutManager(ShowProduct.this, LinearLayoutManager.VERTICAL, false));
        rlList.setItemAnimator(new DefaultItemAnimator());
        rlList.setNestedScrollingEnabled(false);
//opening the database
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursorProduct = mDatabase.rawQuery("SELECT * FROM " + DB_TABLE, null);

        //if the cursor has some data
        if (cursorProduct.moveToFirst()) {
            //looping through all the records
            do {

                productList.add(new Product(
                        cursorProduct.getString(0),
                        cursorProduct.getString(1),
                        cursorProduct.getString(2),
                        cursorProduct.getString(3),
                        cursorProduct.getString(4),
                        cursorProduct.getBlob(5),
                        cursorProduct.getString(6)
                ));
            } while (cursorProduct.moveToNext());
        }

        //closing the cursor
        cursorProduct.close();

        rlList.setAdapter(new ProductListAdapter(ShowProduct.this, productList));

        llBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
              //  finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadEmployeesFromDatabase();
        new ProductListAdapter(ShowProduct.this, productList).notifyDataSetChanged();
    }

    private void reloadEmployeesFromDatabase() {
        Cursor cursorProduct = mDatabase.rawQuery("SELECT * FROM "+ DB_TABLE, null);
        if (cursorProduct.moveToFirst()) {
            productList.clear();
            do {
                productList.add(new Product(
                        cursorProduct.getString(0),
                        cursorProduct.getString(1),
                        cursorProduct.getString(2),
                        cursorProduct.getString(3),
                        cursorProduct.getString(4),
                        cursorProduct.getBlob(5),
                        cursorProduct.getString(6)
                ));
            } while (cursorProduct.moveToNext());
        }
        cursorProduct.close();
    }
}
