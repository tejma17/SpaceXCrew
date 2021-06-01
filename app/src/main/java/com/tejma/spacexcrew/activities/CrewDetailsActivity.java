package com.tejma.spacexcrew.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.tejma.spacexcrew.R;
import com.tejma.spacexcrew.api.ApiClient;
import com.tejma.spacexcrew.databinding.ActivityCrewDetailsBinding;
import com.tejma.spacexcrew.pojo.Crew;
import com.tejma.spacexcrew.room.CrewDao;
import com.tejma.spacexcrew.room.CrewRepository;
import com.tejma.spacexcrew.room.CrewViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrewDetailsActivity extends AppCompatActivity {

    private ActivityCrewDetailsBinding binding;
    private CrewViewModel viewModel;
    private CrewRepository repository;
    public Crew savedCrew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCrewDetailsBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(CrewViewModel.class);

        repository = new CrewRepository(getApplication());

        String id = getIntent().getStringExtra("id");

        binding.toolbar.setNavigationOnClickListener(view -> finish());

        getData(id);
    }

    private void getData(String id) {
        binding.parentLayout.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);

        getCrewMemberDetail(id);
    }

    private void getCrewMemberDetail(String id) {
        Call<Crew> userRequestCall = ApiClient.getUserService().getCrewMember(id);
        userRequestCall.enqueue(new Callback<Crew>() {
            @Override
            public void onResponse(Call<Crew> call, Response<Crew> response) {
                if(response.isSuccessful()) {
                    Crew crew = response.body();
                    setValues(crew);
                } else {
                    Toast.makeText(CrewDetailsActivity.this, "Request unsuccessful", Toast.LENGTH_SHORT).show();
                    binding.progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<Crew> call, Throwable t) {
                binding.parentLayout.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                new GetCrewMemberAsyncTask().execute(id);
                if(t instanceof NetworkErrorException)
                    Toast.makeText(CrewDetailsActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setValues(Crew crew) {
        binding.name.setText(crew.getName());
        binding.agency.setText(crew.getAgency());
        binding.status.setText(crew.getStatus());
        binding.wiki.setText(crew.getWikipedia());
        if(crew.getStatus().equals("active"))
            binding.status.setTextColor(getResources().getColor(R.color.green));
        else
            binding.status.setTextColor(getResources().getColor(R.color.yellow));

        if(crew.getImage()!=null)
            Glide.with(this).load(crew.getImage())
                    .placeholder(R.drawable.ic_user_profile)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(binding.profileImg);
        else
            binding.profileImg.setBackgroundResource(R.drawable.ic_user_profile);

        binding.parentLayout.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);
    }


    public class GetCrewMemberAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            savedCrew = viewModel.getCrewMember(strings[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            setValues(savedCrew);
        }
    }


}