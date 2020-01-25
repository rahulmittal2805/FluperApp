package com.example.fluperapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import com.example.fluperapplication.R;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    LinearLayout llshowProduct,llcreateProduct;

    public static final String DATABASE_NAME = "myproductsdatabase";

    // Table Names
    static final String DB_TABLE = "product10";

    // column names
    static final String NAME = "Pname";
    static final String DISCRIPTION = "Pdiscription";
    static final String REGULARPRICE = "Pregularprice";
    static final String SALEPRICE = "Psaleprise";
    static final String COLOR = "Pcolor";
    static final String IMAGE = "Pimage";
    static final String STORE = "Pstore";
    static SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
    }

    public void initView(){
        llshowProduct=findViewById(R.id.llshowProduct);
        llcreateProduct=findViewById(R.id.llcreateProduct);

        //creating a database
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        createEmployeeTable();

        llcreateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,CreateProduct.class);
                startActivity(i);

            }
        });

        llshowProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,ShowProduct.class);
                startActivity(i);
            }
        });
    }

    private void createEmployeeTable() {

        String CREATE_TABLE_PRODUCT = "CREATE TABLE IF NOT EXISTS " + DB_TABLE + "(" +
                NAME + " TEXT," +
                DISCRIPTION + " TEXT," +
                REGULARPRICE + " TEXT," +
                SALEPRICE + " TEXT," +
                COLOR + " TEXT," +
                IMAGE + " BLOB," +
                STORE + " TEXT);";

        mDatabase.execSQL(CREATE_TABLE_PRODUCT);
    }
}
