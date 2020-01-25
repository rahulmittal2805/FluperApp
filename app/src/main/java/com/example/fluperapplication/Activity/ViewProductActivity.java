package com.example.fluperapplication.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.fluperapplication.R;
import static com.example.fluperapplication.Activity.MainActivity.DATABASE_NAME;
import static com.example.fluperapplication.Activity.MainActivity.DB_TABLE;
import static com.example.fluperapplication.Utils.DbBitmapUtility.getImage;

public class ViewProductActivity extends AppCompatActivity {

    TextView tvName, tvDiscription, tvRealPrice, tvSalePrice, tvColor, tvStoreName;
    ImageView ivImage;
    Bundle bundle;
    SQLiteDatabase mDatabase;
    LinearLayout llBackButton, llupdate, lldelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        initView();
    }

    public void initView() {
        tvName = findViewById(R.id.tvName);
        tvDiscription = findViewById(R.id.tvDiscription);
        tvRealPrice = findViewById(R.id.tvRealPrice);
        tvSalePrice = findViewById(R.id.tvSalePrice);
        tvColor = findViewById(R.id.tvColor);
        tvStoreName = findViewById(R.id.tvStoreName);
        ivImage = findViewById(R.id.ivImage);

        llBackButton = findViewById(R.id.llBackButton);
        llupdate = findViewById(R.id.llupdate);
        lldelete = findViewById(R.id.lldelete);
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        bundle = getIntent().getExtras();
        tvName.setText(bundle.getString("Name"));
        tvDiscription.setText(bundle.getString("Desc"));
        tvRealPrice.setPaintFlags(tvRealPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tvRealPrice.setText("INR. "+bundle.getString("RP"));
        tvSalePrice.setText("INR. "+bundle.getString("SP"));
        tvColor.setText("Color "+bundle.getString("Color"));
        tvStoreName.setText(bundle.getString("Store"));

        Bitmap image = getImage(bundle.getByteArray("Image"));

        ivImage.setImageBitmap(image);

        llBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        lldelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct();
            }
        });

        llupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProduct();
            }
        });

    }

    public void deleteProduct() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String sql = "DELETE FROM " +DB_TABLE+ " WHERE Pname = ?";
                mDatabase.execSQL(sql, new String[]{bundle.getString("Name")});
                Intent i1=new Intent(ViewProductActivity.this,ShowProduct.class);
                startActivity(i1);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void updateProduct(){
        Intent i=new Intent(ViewProductActivity.this,UpdateProduct.class);
        i.putExtra("Name",bundle.getString("Name"));
        i.putExtra("Desc",bundle.getString("Desc"));
        i.putExtra("RP",bundle.getString("RP"));
        i.putExtra("SP",bundle.getString("SP"));
        i.putExtra("Store",bundle.getString("Store"));
        System.out.println(bundle.getString("Name"));
        startActivity(i);
        finish();
    }


}
