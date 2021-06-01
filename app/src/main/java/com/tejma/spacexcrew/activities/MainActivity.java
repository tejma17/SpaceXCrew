package com.tejma.spacexcrew.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.tejma.spacexcrew.R;
import com.tejma.spacexcrew.adapters.CrewRVAdapter;
import com.tejma.spacexcrew.api.ApiClient;
import com.tejma.spacexcrew.databinding.ActivityMainBinding;
import com.tejma.spacexcrew.pojo.Crew;
import com.tejma.spacexcrew.room.CrewRepository;
import com.tejma.spacexcrew.room.CrewViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private CrewRVAdapter rvAdapter;
    private List<Crew> list;

    private CrewViewModel viewModel;
    private CrewRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        list = new ArrayList<>();

        viewModel = new ViewModelProvider(this).get(CrewViewModel.class);
        viewModel.getAllCrew().observe(this, new Observer<List<Crew>>() {
            @Override
            public void onChanged(List<Crew> crews) {
                rvAdapter.updateAdapter(crews);
                list = crews;
            }
        });

        repository = new CrewRepository(getApplication());
        rvAdapter = new CrewRVAdapter(this, list, clickListener);
        binding.crewRv.setAdapter(rvAdapter);

        binding.deleteBtn.setOnClickListener(this::OnClick);
        binding.refreshBtn.setOnClickListener(this::OnClick);
        getData();
    }

    private void OnClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.delete_btn:
                repository.deleteAll();
                break;
            case R.id.refresh_btn:
                getData();
                break;
        }
    }

    private void getData() {
        binding.parentLayout.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);

        getCrew();
    }

    private void getCrew() {

        Call<List<Crew>> userRequestCall = ApiClient.getUserService().getAllCrew();
        userRequestCall.enqueue(new Callback<List<Crew>>() {
            @Override
            public void onResponse(Call<List<Crew>> call, Response<List<Crew>> response) {
                if(response.isSuccessful()) {
                    list = response.body();
                    repository.insert(list);
                    binding.parentLayout.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(MainActivity.this, "Request unsuccessful", Toast.LENGTH_SHORT).show();
                }
                binding.progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<List<Crew>> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.parentLayout.setVisibility(View.VISIBLE);
                if(t instanceof NetworkErrorException)
                    Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }


   CrewRVAdapter.OnClickListener clickListener = new CrewRVAdapter.OnClickListener() {
       @Override
       public void onClick(int position, View v) {
           startActivity(new Intent(MainActivity.this, CrewDetailsActivity.class).putExtra("id", list.get(position).getId()));
       }

       @Override
       public void onLongClick(int position, View v) {

       }
   };

}