package com.example.myapplication.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Make_order;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.Constants;
import com.example.myapplication.Retrofit.RequestInterface;
import com.example.myapplication.Retrofit.ServerRequest;
import com.example.myapplication.Retrofit.ServerResponse;
import com.example.myapplication.adapters.AdapterCommonMedi;
import com.example.myapplication.adapters.MediCommonGetAdapter;
import com.example.myapplication.model_classes.medi;
import com.example.myapplication.order_image;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.myapplication.Retrofit.Constants.BASE_URL;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ImageButton btn_image, btn_placeOrder;

    AdapterCommonMedi adapter;
    ArrayList<String> mid;
    ArrayList<String> mediName;
    ArrayList<String> mediMg;
    ArrayList<String> mediPrice;
    ArrayList<String> mediType;
    RecyclerView recyclerView;
    List<medi> mediListCommon;
    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        btn_image = root.findViewById(R.id.image_btn);
        btn_placeOrder = root.findViewById(R.id.img_place_order);
        recyclerView = root.findViewById(R.id.rv_View);
        getCommonMedicines();


        btn_image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), order_image.class);
                startActivity(intent);

            }

        });

        btn_placeOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Make_order.class);
                startActivity(intent);

            }

        });


        return root;
    }

    private void getCommonMedicines() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        ServerRequest serverRequest = new ServerRequest();
        serverRequest.setOperation(Constants.GET_COMMONMEDI);

        Call<ServerResponse> resp = requestInterface.operation(serverRequest);
        resp.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                ServerResponse resp = response.body();

                MediCommonGetAdapter mediGetCommonAdapter = resp.getMediGetCommonAdapter();
                mid = mediGetCommonAdapter.getMid();
                mediName = mediGetCommonAdapter.getMediName();
                mediMg = mediGetCommonAdapter.getMediMg();
                mediPrice = mediGetCommonAdapter.getMediPrice();
                mediType = mediGetCommonAdapter.getMediType();

                mediListCommon = new ArrayList<>();
                for (int i = 0; i < mediName.size(); i++) {
                    mediListCommon.add(new medi(mid.get(i), mediName.get(i), mediMg.get(i), mediPrice.get(i), mediType.get(i)));
                    setRecyclerView();
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Failed" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterCommonMedi(context, mediListCommon);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }


}