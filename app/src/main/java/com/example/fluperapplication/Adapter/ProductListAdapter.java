package com.example.fluperapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fluperapplication.Activity.ViewProductActivity;
import com.example.fluperapplication.Model.Product;
import com.example.fluperapplication.R;

import java.util.ArrayList;

import static com.example.fluperapplication.Utils.DbBitmapUtility.getImage;


public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {


    ArrayList<Product> _data;
    private Activity _activity;
    int position1;

    String name, desc, reglularprice, saleprice, color, store;

    public ProductListAdapter(Activity _activity, ArrayList<Product> data) {
        this._activity = _activity;
        this._data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_product_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        position1 = position;
        //getting employee of the specified position
        Product product = _data.get(position);


        holder.tvProdctName.setText(product.getName());
        holder.tvProdctDiscription.setText(product.getDisc());
        holder.tvRealPrice.setPaintFlags(holder.tvRealPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvRealPrice.setText("INR. "+product.getRp());
        holder.tvSalePrice.setText("INR. "+product.getSp());

        Bitmap image = getImage(product.getImage());

        holder.productImage.setImageBitmap(image);


    }

    @Override
    public int getItemCount() {
        return _data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvProdctName, tvProdctDiscription, tvRealPrice, tvSalePrice;
        ImageView productImage;
        RelativeLayout rlList;

        public MyViewHolder(@NonNull View view) {
            super(view);

            tvProdctName = view.findViewById(R.id.tvProdctName);
            tvProdctDiscription = view.findViewById(R.id.tvProdctDiscription);
            tvRealPrice = view.findViewById(R.id.tvRealPrice);
            tvSalePrice = view.findViewById(R.id.tvSalePrice);
            productImage = view.findViewById(R.id.productImage);

            rlList = view.findViewById(R.id.rlList);

            view.setClickable(true);
            view.setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(_activity, ViewProductActivity.class);
            intent.putExtra("Name", _data.get(getAdapterPosition()).getName());
            intent.putExtra("Desc", _data.get(getAdapterPosition()).getDisc());
            intent.putExtra("RP", _data.get(getAdapterPosition()).getRp());
            intent.putExtra("SP", _data.get(getAdapterPosition()).getSp());
            intent.putExtra("Color", _data.get(getAdapterPosition()).getColor());
            intent.putExtra("Store", _data.get(getAdapterPosition()).getStore());
            intent.putExtra("Image", _data.get(getAdapterPosition()).getImage());

            _activity.startActivity(intent);
        }
    }


}
