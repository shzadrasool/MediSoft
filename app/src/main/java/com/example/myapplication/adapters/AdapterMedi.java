package com.example.myapplication.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Retrofit.ItemClickListener;
import com.example.myapplication.model_classes.medi;

import java.util.ArrayList;
import java.util.List;


public class AdapterMedi extends RecyclerView.Adapter<AdapterMedi.RecyclerViewAdapter> {

    Context context;
    List<medi> mediAdapter;
    String type;
    String clickedId;


    public static ArrayList<String> listofId = new ArrayList<String>();


    public AdapterMedi(Context context, List<medi> mediAdapter) {
        this.context = context;
        this.mediAdapter = mediAdapter;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycle_item_show_medi, viewGroup, false);
        return new AdapterMedi.RecyclerViewAdapter(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter recyclerViewAdapter, int position) {
        final medi currentitem = mediAdapter.get(position);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Confirm:");
                builder.setMessage("Do you want to add to order!");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        recyclerViewAdapter.getAdapterPosition();
                        dialog.dismiss();


                        clickedId = currentitem.getMid();
                        listofId.add(clickedId);


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
        return mediAdapter.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        TextView mid, mediName, mediMg, mediPrice, mediType;
        ImageView imgMedi;
        CardView cardView;
        ItemClickListener itemClickListener;


        public RecyclerViewAdapter(@NonNull View itemView) {
            super(itemView);
            mid = itemView.findViewById(R.id.mediId);
            mediName = itemView.findViewById(R.id.mediName);
            mediMg = itemView.findViewById(R.id.mediMg);
            mediType = itemView.findViewById(R.id.mediType);
            mediPrice = itemView.findViewById(R.id.mediPrice);
            cardView = itemView.findViewById(R.id.cardview);
            imgMedi = itemView.findViewById(R.id.mediImg);
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

