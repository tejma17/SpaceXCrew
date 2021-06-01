package com.tejma.spacexcrew.api;

import com.tejma.spacexcrew.pojo.Crew;
import com.tejma.spacexcrew.responses.CrewResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface UserService {

        @GET("/v4/crew")
        Call<List<Crew>> getAllCrew();

        @GET("/v4/crew/{ID}")
        Call<Crew> getCrewMember(@Path("ID") String id);

}
