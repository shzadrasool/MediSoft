package com.example.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Retrofit.Constants;
import com.example.myapplication.Retrofit.RequestInterface;
import com.example.myapplication.Retrofit.ServerRequest;
import com.example.myapplication.Retrofit.ServerResponse;
import com.example.myapplication.adapters.AdapterMedi;
import com.example.myapplication.adapters.MediGetAdapter;
import com.example.myapplication.model_classes.medi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.myapplication.Retrofit.Constants.BASE_URL;

public class Make_order extends AppCompatActivity {

    AdapterMedi adapter;
    ArrayList<String> mid;
    ArrayList<String> mediName;
    ArrayList<String> mediMg;
    ArrayList<String> mediPrice;
    ArrayList<String> mediType;
    RecyclerView recyclerView;
    List<medi> mediList;
    RelativeLayout rv;


    private AdapterMedi.numbers number;

    //example


    //hurray !

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        number = new AdapterMedi.numbers();
        List<Integer> list = number.getList();

        rv = (RelativeLayout) findViewById(R.id.r_layout);
        recyclerView = findViewById(R.id.recyclerView);
        getMedicines();

        if (!isConnectedToInternet(Make_order.this)) {
            setLayoutVisible();
        }


    }

    public void setLayoutVisible() {
        if (rv.getVisibility() == View.GONE) {
            rv.setVisibility(View.VISIBLE);
        }
    }

    private boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void getMedicines() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        ServerRequest serverRequest = new ServerRequest();
        serverRequest.setOperation(Constants.GET_MEDICINES);

        Call<ServerResponse> resp = requestInterface.operation(serverRequest);
        resp.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                ServerResponse resp = response.body();

                MediGetAdapter mediGetAdapter = resp.getMediGetAdapter();
                mid = mediGetAdapter.getMid();
                mediName = mediGetAdapter.getMediName();
                mediMg = mediGetAdapter.getMediMg();
                mediPrice = mediGetAdapter.getMediPrice();
                mediType = mediGetAdapter.getMediType();

                mediList = new ArrayList<>();
                for (int i = 0; i < mediName.size(); i++) {
                    mediList.add(new medi(mid.get(i), mediName.get(i), mediMg.get(i), mediPrice.get(i), mediType.get(i)));
                    setRecyclerView();
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(Make_order.this, "Failed" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(Make_order.this));
        adapter = new AdapterMedi(Make_order.this, mediList);
        recyclerView.setAdapter(adapter);
    }
}
