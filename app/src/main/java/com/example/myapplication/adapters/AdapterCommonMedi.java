package com.example.myapplication.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Retrofit.ItemClickListener;
import com.example.myapplication.model_classes.medi;

import java.util.ArrayList;
import java.util.List;


public class AdapterCommonMedi extends RecyclerView.Adapter<AdapterCommonMedi.RecyclerViewAdapter> {

    Context context;
    List<medi> mediCommonAdapter;
    String type;
    String clickedId;
    public static ArrayList<String> listofId = new ArrayList<String>();

    public AdapterCommonMedi(Context context, List<medi> mediCommonAdapter) {
        this.context = context;
        this.mediCommonAdapter = mediCommonAdapter;
    }

    @NonNull
    @Override
    public AdapterCommonMedi.RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.layout_rv_card, viewGroup, false);

        // View view = LayoutInflater.from(context).inflate(R.layout.layout_rv_card, viewGroup, false);
        return new AdapterCommonMedi.RecyclerViewAdapter(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter recyclerViewAdapter, int position) {
        final medi currentitem = mediCommonAdapter.get(position);
        recyclerViewAdapter.mediName.setText(currentitem.getMediName());
        recyclerViewAdapter.mediMg.setText(currentitem.getMediMg());
        recyclerViewAdapter.mediPrice.setText(currentitem.getMediPrice() + " PKR");
        recyclerViewAdapter.mediType.setText(currentitem.getMediType());

        type = currentitem.getMediType();


        if (type.equals("pills")) {
            recyclerViewAdapter.imgMedi.setImageResource(R.drawable.comonpills);
        } else if (type.equals("syrup")) {
            recyclerViewAdapter.imgMedi.setImageResource(R.drawable.syrup);
        } else if (type.equals("injection")) {
            recyclerViewAdapter.imgMedi.setImageResource(R.drawable.injection);
        }


        recyclerViewAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, final int position, boolean isLongClick) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ;
                builder.setTitle("Confirm:");
                builder.setMessage("Do you want to add to order!");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        recyclerViewAdapter.getAdapterPosition();
                        dialog.dismiss();

                        clickedId = currentitem.getMid();
                        listofId.add(clickedId);

                        Toast.makeText(context, clickedId, Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

    }


    @Override
    public int getItemCount() {
        return mediCommonAdapter.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        TextView mid, mediName, mediMg, mediPrice, mediType;
        ImageView imgMedi;
        CardView cardView;
        ItemClickListener itemClickListener;


        public RecyclerViewAdapter(@NonNull View itemView) {
            super(itemView);

            mid = itemView.findViewById(R.id.tv_mid);
            mediName = itemView.findViewById(R.id.tv_mediName);
            mediMg = itemView.findViewById(R.id.tv_mg);
            mediType = itemView.findViewById(R.id.tv_type);
            mediPrice = itemView.findViewById(R.id.tv_price);
            cardView = itemView.findViewById(R.id.cv_card);
            imgMedi = itemView.findViewById(R.id.img_medi);
            mediType.setVisibility(View.GONE);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return true;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);

        }
    }

}

